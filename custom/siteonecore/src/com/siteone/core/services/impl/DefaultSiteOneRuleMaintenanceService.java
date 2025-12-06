/**
 *
 */
package com.siteone.core.services.impl;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.promotionengineservices.model.PromotionSourceRuleModel;
import de.hybris.platform.promotionengineservices.model.RuleBasedPromotionModel;
import de.hybris.platform.ruleengine.RuleEngineActionResult;
import de.hybris.platform.ruleengine.init.InitializationFuture;
import de.hybris.platform.ruleengine.model.AbstractRuleEngineRuleModel;
import de.hybris.platform.ruleengine.model.AbstractRulesModuleModel;
import de.hybris.platform.ruleengine.model.DroolsKIEModuleModel;
import de.hybris.platform.ruleengine.model.DroolsRuleModel;
import de.hybris.platform.ruleengine.util.RuleMappings;
import de.hybris.platform.ruleengineservices.RuleEngineServiceException;
import de.hybris.platform.ruleengineservices.compiler.RuleCompilerException;
import de.hybris.platform.ruleengineservices.compiler.RuleCompilerResult;
import de.hybris.platform.ruleengineservices.compiler.RuleCompilerService;
import de.hybris.platform.ruleengineservices.maintenance.RuleCompilerPublisherResult;
import de.hybris.platform.ruleengineservices.maintenance.RuleCompilerPublisherResult.Result;
import de.hybris.platform.ruleengineservices.maintenance.impl.DefaultRuleCompilerPublisherResult;
import de.hybris.platform.ruleengineservices.maintenance.impl.DefaultRuleMaintenanceService;
import de.hybris.platform.ruleengineservices.model.AbstractRuleModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.util.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.siteone.core.category.dao.SiteOneCategoryDao;
import com.siteone.core.promotion.dao.SiteOnePromotionSourceRuleDao;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.jobs.promotion.service.PromotionFeedCronJobService;
import com.siteone.integration.jobs.promotions.process.PromotionConditionUtil;

import org.json.JSONArray;
import org.json.JSONObject;



/**
 * @author 1124932
 *
 */
public class DefaultSiteOneRuleMaintenanceService extends DefaultRuleMaintenanceService
{
	private PromotionFeedCronJobService promotionFeedCronJobService;
	private SiteOneCategoryDao siteOneCategoryDao;
	private ModelService modelService;
	private ProductService productService;
	private CatalogVersionService catalogVersionService;
	private SiteOnePromotionSourceRuleDao siteOnePromotionSourceRuleDao;
	private RuleCompilerService ruleCompilerService;


	@SuppressWarnings("deprecation")
	public RuleCompilerPublisherResult compileAndPublishRules(final List<? extends AbstractRuleModel> rules)
			throws RuleEngineServiceException
	{

		ServicesUtil.validateParameterNotNull(rules, "Rules list cannot be null");
		Result result = Result.SUCCESS;
		final ArrayList compilerResults = new ArrayList();
		final LinkedHashSet modulesToInitialize = new LinkedHashSet();
		final Map<String, String> promotionRow = new HashMap<String, String>();
		final Set<String> allProductCodes = new CopyOnWriteArraySet<>();
		final Set<String> allCategoryCodes = new CopyOnWriteArraySet<>();
		String inclusiveSkuIds = null;
		String exclusiveSkuIds = null;
		String inclusiveCategories = null;
		String exclusiveCategories = null;
		final Set<String> allInclusiveSkuIds = new HashSet<>();
		final Set<String> allInclusiveCategories = new HashSet<>();

		String moduleName = null;
		if (CollectionUtils.isNotEmpty(rules))
		{
			moduleName = this.getRulesModuleResolver().lookupForModuleName(
					this.getRuleService().getEngineRuleTypeForRuleType(((AbstractRuleModel) rules.get(0)).getClass()));
		}

		RuleCompilerResult compilerResult;
		for (final Iterator module = rules.iterator(); module.hasNext(); compilerResults.add(compilerResult))
		{
			final AbstractRuleModel publisherResults = (AbstractRuleModel) module.next();

			try
			{
				compilerResult = getRuleCompilerService().compile(publisherResults, moduleName);
			}
			catch (final RuleCompilerException arg9)
			{
				throw new RuleEngineServiceException(arg9);
			}

			if (compilerResult.getResult() == de.hybris.platform.ruleengineservices.compiler.RuleCompilerResult.Result.ERROR)
			{
				result = Result.COMPILER_ERROR;
			}
			else
			{
				final AbstractRuleEngineRuleModel modulePublishResult = this.getRuleEngineService()
						.getRuleForUuid(publisherResults.getUuid());
				final AbstractRulesModuleModel ruleModuleForRule = this.getRulesModuleDao().findByName(moduleName);
				modulesToInitialize.add(ruleModuleForRule);
			}
		}

		if (result.equals(Result.COMPILER_ERROR))
		{
			return new DefaultRuleCompilerPublisherResult(result, compilerResults, Lists.newArrayList());
		}
		else
		{
			final DroolsKIEModuleModel module = this.getRulesModuleByName(moduleName);
			final InitializationFuture initializationFuture = this.getRuleEngineService()
					.initialize(Lists.newArrayList(new AbstractRulesModuleModel[]
					{ module }), true, true);
			final List<RuleEngineActionResult> ruleEngineActionResults = initializationFuture.waitForInitializationToFinish()
					.getResults();
			result = ruleEngineActionResults.stream().filter(RuleEngineActionResult::isActionFailed).findAny().map((r) -> {
				return Result.PUBLISHER_ERROR;
			}).orElse(Result.SUCCESS);


			if (result != Result.PUBLISHER_ERROR)
			{

				final PromotionSourceRuleModel currentVersionpromotion = (PromotionSourceRuleModel) rules.get(0);
				final RuleBasedPromotionModel olderVersionPromotion = siteOnePromotionSourceRuleDao
						.getPromotionForPreviousRule(currentVersionpromotion.getCode());
				if (null != olderVersionPromotion)
				{
					final String ruleParameters = olderVersionPromotion.getRule().getRuleParameters();
					final JSONArray jsonArray = new JSONArray(ruleParameters);
					for (int i = 0; i < jsonArray.length(); i++)
					{
						final JSONObject jsonObject1 = jsonArray.getJSONObject(i);
						if (jsonObject1.toString().contains("List(ItemType(Product))"))
						{
							if (jsonObject1.toString().contains("value"))
							{
								final JSONArray array = jsonObject1.getJSONArray("value");
								for (int j = 0; j < array.length(); j++)
								{
									allProductCodes.add((String) array.get(j));
								}
							}
						}

						if (jsonObject1.toString().contains("List(ItemType(Category))"))
						{
							if (jsonObject1.toString().contains("value"))
							{
								final JSONArray array = jsonObject1.getJSONArray("value");
								for (int j = 0; j < array.length(); j++)
								{
									allCategoryCodes.add((String) array.get(j));
								}
							}
						}
					}
				}
				final String couponName = currentVersionpromotion.getCode();
				final String couponType = findCouponType(couponName);
				Set<String> skuIdsList = null;
				if (!couponType.isEmpty())
				{
					PromotionConditionUtil.processCondition(couponType, currentVersionpromotion, promotionRow, couponName);
					inclusiveSkuIds = promotionRow.get("InclusiveSkuId");
					if (null != inclusiveSkuIds && !inclusiveSkuIds.equals(""))
					{
						if (inclusiveSkuIds.contains(","))
						{
							skuIdsList = new HashSet<>(Arrays.asList(inclusiveSkuIds.split(",")));
							allProductCodes.addAll(skuIdsList);
						}
						else
						{
							allProductCodes.add(inclusiveSkuIds);
						}
					}
					exclusiveSkuIds = promotionRow.get("ExclusiveSkuId");
					if (null != exclusiveSkuIds && !exclusiveSkuIds.equals(""))
					{
						if (exclusiveSkuIds.contains(","))
						{
							skuIdsList = new HashSet<>(Arrays.asList(exclusiveSkuIds.split(",")));
							allProductCodes.addAll(skuIdsList);
						}
						else
						{
							allProductCodes.add(exclusiveSkuIds);
						}
					}
					inclusiveCategories = promotionRow.get("InclusiveProducttypeId");
					if (null != inclusiveCategories && !inclusiveCategories.equals(""))
					{
						if (inclusiveCategories.contains(","))
						{
							skuIdsList = new HashSet<>(Arrays.asList(inclusiveCategories.split(",")));
							allCategoryCodes.addAll(skuIdsList);
						}
						else
						{
							allCategoryCodes.add(exclusiveSkuIds);
						}
					}
					exclusiveCategories = promotionRow.get("ExclusiveProducttypeId");
					if (null != exclusiveCategories && !exclusiveCategories.equals(""))
					{
						if (exclusiveCategories.contains(","))
						{
							skuIdsList = new HashSet<>(Arrays.asList(exclusiveCategories.split(",")));
							allCategoryCodes.addAll(skuIdsList);
						}
						else
						{
							allCategoryCodes.add(exclusiveCategories);
						}
					}
				}
				if (CollectionUtils.isNotEmpty(allInclusiveSkuIds))
				{
					allProductCodes.addAll(allInclusiveSkuIds);
				}
				if (CollectionUtils.isNotEmpty(allInclusiveCategories))
				{
					allCategoryCodes.addAll(allInclusiveCategories);
				}

				if (CollectionUtils.isNotEmpty(allCategoryCodes))
				{
					for (final String categoryCode : allCategoryCodes)
					{
						final Set<ProductModel> products = new HashSet<>(siteOneCategoryDao.getProductForCategoryCode(categoryCode));
						for (final ProductModel product : products)
						{
							product.setLastModifiedTimeStamp(new Date());
							getModelService().save(product);
							getModelService().refresh(product);
						}
					}
				}

				if (CollectionUtils.isNotEmpty(allProductCodes))
				{
					for (final String code : allProductCodes)
					{
						ProductModel product = getProductService()
								.getProductForCode(getCatalogVersionService().getCatalogVersion("siteoneProductCatalog", "Online"), code);
						if (null == product)
						{
							product = getProductService().getProductForCode(
									getCatalogVersionService().getCatalogVersion("siteoneCAProductCatalog", "Online"), code);
						}
						if (null != product)
						{
							product.setLastModifiedTimeStamp(new Date());
							getModelService().save(product);
							getModelService().refresh(product);
						}
					}
				}
			}
			return new DefaultRuleCompilerPublisherResult(result, compilerResults, ruleEngineActionResults);
		}
	}

	private String findCouponType(final String couponName)
	{
		if (couponName.contains(SiteoneintegrationConstants.PRODUCT_PERCENTAGE_DISCOUNT))
		{
			return Config.getString(SiteoneintegrationConstants.PRODUCT_PERCENTAGE_DISCOUNT_ID, StringUtils.EMPTY);
		}
		else if (couponName.contains(SiteoneintegrationConstants.PRODUCT_FIXED_PRICE))
		{
			return Config.getString(SiteoneintegrationConstants.PRODUCT_FIXED_PRICE_ID, StringUtils.EMPTY);
		}
		else if (couponName.contains(SiteoneintegrationConstants.PRODUCT_FIXED_DISCOUNT))
		{
			return Config.getString(SiteoneintegrationConstants.PRODUCT_FIXED_DISCOUNT_ID, StringUtils.EMPTY);
		}
		else if (couponName.contains(SiteoneintegrationConstants.TARGET_CUSTOMER_PERCENTAGE_DISCOUNT_CART))
		{
			return Config.getString(SiteoneintegrationConstants.TARGET_CUSTOMER_PERCENTAGE_DISCOUNT_CART_ID, StringUtils.EMPTY);
		}
		else if (couponName.contains(SiteoneintegrationConstants.PRODUCT_BUY_X_GET_Y_FREE))
		{
			return Config.getString(SiteoneintegrationConstants.PRODUCT_BUY_X_GET_Y_FREE_ID, StringUtils.EMPTY);
		}
		else if (couponName.contains(SiteoneintegrationConstants.PRODUCT_PERFECT_PARTNER_FIXED_PRICE))
		{
			return Config.getString(SiteoneintegrationConstants.PRODUCT_PERFECT_PARTNER_FIXED_PRICE_ID, StringUtils.EMPTY);
		}
		else if (couponName.contains(SiteoneintegrationConstants.PRODUCT_MULTIBUY_FIXED_PRICE))
		{
			return Config.getString(SiteoneintegrationConstants.PRODUCT_MULTIBUY_FIXED_PRICE_ID, StringUtils.EMPTY);
		}
		else if (couponName.contains(SiteoneintegrationConstants.PRODUCT_BUY_X_OF_A_GET_Y_OF_B_FREE))
		{
			return Config.getString(SiteoneintegrationConstants.PRODUCT_BUY_X_OF_A_GET_Y_OF_B_FREE_ID, StringUtils.EMPTY);
		}
		else if (couponName.contains(SiteoneintegrationConstants.COUPON_CODE_PERCENTAGE_DISCOUNT_ON_CART))
		{
			return Config.getString(SiteoneintegrationConstants.COUPON_CODE_PERCENTAGE_DISCOUNT_ON_CART_ID, StringUtils.EMPTY);
		}
		else if (couponName.contains(SiteoneintegrationConstants.COUPON_CODE_PERCENTAGE_DISCOUNT_ON_PRODUCTS))
		{
			return Config.getString(SiteoneintegrationConstants.COUPON_CODE_PERCENTAGE_DISCOUNT_ON_PRODUCTS_ID, StringUtils.EMPTY);
		}

		return StringUtils.EMPTY;
	}


	/**
	 * @return the promotionFeedCronJobService
	 */
	public PromotionFeedCronJobService getPromotionFeedCronJobService()
	{
		return promotionFeedCronJobService;
	}

	/**
	 * @param promotionFeedCronJobService
	 *           the promotionFeedCronJobService to set
	 */
	public void setPromotionFeedCronJobService(final PromotionFeedCronJobService promotionFeedCronJobService)
	{
		this.promotionFeedCronJobService = promotionFeedCronJobService;
	}

	/**
	 * @return the siteOneCategoryDao
	 */
	public SiteOneCategoryDao getSiteOneCategoryDao()
	{
		return siteOneCategoryDao;
	}

	/**
	 * @param siteOneCategoryDao
	 *           the siteOneCategoryDao to set
	 */
	public void setSiteOneCategoryDao(final SiteOneCategoryDao siteOneCategoryDao)
	{
		this.siteOneCategoryDao = siteOneCategoryDao;
	}

	/**
	 * @return the modelService
	 */
	@Override
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	@Override
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	/**
	 * @return the productService
	 */
	public ProductService getProductService()
	{
		return productService;
	}

	/**
	 * @param productService
	 *           the productService to set
	 */
	public void setProductService(final ProductService productService)
	{
		this.productService = productService;
	}

	/**
	 * @return the catalogVersionService
	 */
	public CatalogVersionService getCatalogVersionService()
	{
		return catalogVersionService;
	}

	/**
	 * @param catalogVersionService
	 *           the catalogVersionService to set
	 */
	public void setCatalogVersionService(final CatalogVersionService catalogVersionService)
	{
		this.catalogVersionService = catalogVersionService;
	}

	/**
	 * @return the siteOnePromotionSourceRuleDao
	 */
	public SiteOnePromotionSourceRuleDao getSiteOnePromotionSourceRuleDao()
	{
		return siteOnePromotionSourceRuleDao;
	}

	/**
	 * @param siteOnePromotionSourceRuleDao
	 *           the siteOnePromotionSourceRuleDao to set
	 */
	public void setSiteOnePromotionSourceRuleDao(final SiteOnePromotionSourceRuleDao siteOnePromotionSourceRuleDao)
	{
		this.siteOnePromotionSourceRuleDao = siteOnePromotionSourceRuleDao;
	}

	/**
	 * @return the ruleComplilerService
	 */
	public RuleCompilerService getRuleCompilerService()
	{
		return ruleCompilerService;
	}


	//	/**
	//	 * @param ruleComplilerService the ruleComplilerService to set
	//	 */
	public void setRuleCompilerService(final RuleCompilerService ruleCompilerService)
	{
		this.ruleCompilerService = ruleCompilerService;
	}



}
