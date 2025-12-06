/**
 *
 */
package com.siteone.core.services.impl;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.promotionengineservices.model.PromotionSourceRuleModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.Config;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.siteone.core.category.dao.SiteOneCategoryDao;
import com.siteone.core.cronjob.dao.ProductPromotionsCronJobDao;
import com.siteone.core.model.ProductPromotionsCronJobModel;
import com.siteone.core.services.ProductPromotionsCronJobService;
import com.siteone.core.services.SiteOneProductService;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.jobs.promotion.service.PromotionFeedCronJobService;
import com.siteone.integration.jobs.promotions.process.PromotionConditionUtil;


/**
 * @author 1124932
 *
 */
public class DefaultProductPromotionsCronJobService implements ProductPromotionsCronJobService
{
	private final Logger LOG = Logger.getLogger(DefaultProductPromotionsCronJobService.class);
	private ProductPromotionsCronJobDao productPromotionsCronJobDao;
	private PromotionFeedCronJobService promotionFeedCronJobService;
	@Resource(name = "siteOneProductService")
	private SiteOneProductService siteOneProductService;
	private SiteOneCategoryDao siteOneCategoryDao;
	private ModelService modelService;
	private ProductService productService;
	private CatalogVersionService catalogVersionService;

	@Override
	public void updateProductPromotions(final ProductPromotionsCronJobModel productPromotionsCronJobModel)
	{
		final Map<String, String> promotionRow = new HashMap<String, String>();
		final List<PromotionSourceRuleModel> promotions = getProductPromotionsCronJobDao().getModifiedPromotions();
		Set<PromotionSourceRuleModel> modifiedPromotions = null;
		Date startDate = null;
		Date endDate = null;
		if (null != promotions)
		{
			modifiedPromotions = new CopyOnWriteArraySet<>(promotions);
		}
		final Map<String, PromotionSourceRuleModel> allProductCodes = new HashMap<String, PromotionSourceRuleModel>();
		try
		{
			if (null != modifiedPromotions)
			{
				for (final PromotionSourceRuleModel promotion : modifiedPromotions)
				{

					final String couponName = promotion.getCode();
					final String couponType = findCouponType(couponName);
					if (!couponType.isEmpty())
					{
						//promotionRow.put("CouponTypeId", couponType);
						PromotionConditionUtil.processCondition(couponType, promotion, promotionRow, couponName);
						final String inclusiveSkuIds = promotionRow.get("InclusiveSkuId");
						final String exclusiveSkuIds = promotionRow.get("ExclusiveSkuId");
						final String inclusiveCategories = promotionRow.get("InclusiveProducttypeId");
						final String exclusiveCategories = promotionRow.get("ExclusiveProducttypeId");
						Set<String> skuIdsList = null;
						Set<String> categoryCodesList = null;
						if (null != inclusiveSkuIds)
						{
							if (!inclusiveSkuIds.isEmpty())
							{
								if (inclusiveSkuIds.contains(","))
								{
									skuIdsList = new HashSet<>(Arrays.asList(inclusiveSkuIds.split(",")));
									for (final String skuId : skuIdsList)
									{
										allProductCodes.put(skuId, promotion);
									}
								}
								else
								{
									allProductCodes.put(inclusiveSkuIds, promotion);
								}
							}
						}
						if (null != exclusiveSkuIds)
						{
							if (!exclusiveSkuIds.isEmpty())
							{
								if (exclusiveSkuIds.contains(","))
								{
									skuIdsList = new HashSet<>(Arrays.asList(exclusiveSkuIds.split(",")));
									for (final String skuId : skuIdsList)
									{
										allProductCodes.put(skuId, promotion);
									}
								}
								else
								{
									allProductCodes.put(exclusiveSkuIds, promotion);
								}
							}
						}
						if (null != inclusiveCategories)
						{
							if (!inclusiveCategories.isEmpty())
							{
								if (inclusiveCategories.contains(","))
								{
									categoryCodesList = new HashSet<>(Arrays.asList(inclusiveCategories.split(",")));
									for (final String categoryCode : categoryCodesList)
									{
										final Set<ProductModel> products = new HashSet<>(
												siteOneCategoryDao.getProductForCategoryCode(categoryCode));
										for (final ProductModel product : products)
										{
											allProductCodes.put(product.getCode(), promotion);
										}
									}
								}
								else
								{
									final Set<ProductModel> product = new HashSet<>(
											siteOneCategoryDao.getProductForCategoryCode(inclusiveCategories));
									for (final ProductModel productModel : product)
									{
										allProductCodes.put(productModel.getCode(), promotion);
									}
								}
							}
						}
						if (null != exclusiveCategories)
						{
							if (!exclusiveCategories.isEmpty())
							{
								if (exclusiveCategories.contains(","))
								{
									categoryCodesList = new HashSet<>(Arrays.asList(exclusiveCategories.split(",")));
									for (final String categoryCode : categoryCodesList)
									{
										final Set<ProductModel> products = new HashSet<>(
												siteOneCategoryDao.getProductForCategoryCode(categoryCode));
										for (final ProductModel product : products)
										{
											allProductCodes.put(product.getCode(), promotion);
										}
									}
								}
								else
								{
									final Set<ProductModel> product = new HashSet<>(
											siteOneCategoryDao.getProductForCategoryCode(exclusiveCategories));
									for (final ProductModel productModel : product)
									{
										allProductCodes.put(productModel.getCode(), promotion);
									}
								}
							}
						}
					}
				}
				if (Config.getBoolean("product.promotions.enable", true))
				{
					if (!allProductCodes.isEmpty())
					{
						for (final Map.Entry<String, PromotionSourceRuleModel> productPromotion : allProductCodes.entrySet())
						{
							final String code = productPromotion.getKey();

							ProductModel product = getSiteOneProductService().getProductByProductCodeAndVersion(
									getCatalogVersionService().getCatalogVersion("siteoneProductCatalog", "Online"), code);
							if (null == product)
							{
								product = getProductService().getProductForCode(
										getCatalogVersionService().getCatalogVersion("siteoneCAProductCatalog", "Online"), code);
							}
							final PromotionSourceRuleModel promotion = productPromotion.getValue();
							startDate = promotion.getStartDate();
							endDate = promotion.getEndDate();
							if (null != product)
							{
								final Date modifiedTime = product.getLastModifiedTimeStamp();
								if (null != modifiedTime)
								{
									if (modifiedTime.before(startDate))
									{
										product.setLastModifiedTimeStamp(new Date());
									}
									else if (endDate.before(new Date()) && (startDate.after(modifiedTime) && modifiedTime.before(endDate)))
									{
										product.setLastModifiedTimeStamp(new Date());
									}
								}
								else
								{
									product.setLastModifiedTimeStamp(new Date());
								}
								getModelService().save(product);
								getModelService().refresh(product);
							}

						}
					}
				}
				else
				{
					if (!allProductCodes.isEmpty())
					{
						for (final Map.Entry<String, PromotionSourceRuleModel> productPromotion : allProductCodes.entrySet())
						{
							final String code = productPromotion.getKey();
							ProductModel product = getProductService().getProductForCode(
									getCatalogVersionService().getCatalogVersion("siteoneProductCatalog", "Online"), code);
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
			}
		}
		catch (

		final Exception e)
		{
			LOG.error("Exception occured ProductPromotionsStoresCronJob ", e);
			productPromotionsCronJobModel.setResult(CronJobResult.FAILURE);
			productPromotionsCronJobModel.setStatus(CronJobStatus.ABORTED);
		}
		productPromotionsCronJobModel.setLastExecutionTime(new Date());

		getModelService().save(productPromotionsCronJobModel);

	}

	/**
	 * @param couponName
	 * @return
	 */
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
	 * @return the productPromotionsCronJobDao
	 */
	public ProductPromotionsCronJobDao getProductPromotionsCronJobDao()
	{
		return productPromotionsCronJobDao;
	}

	/**
	 * @param productPromotionsCronJobDao
	 *           the productPromotionsCronJobDao to set
	 */
	public void setProductPromotionsCronJobDao(final ProductPromotionsCronJobDao productPromotionsCronJobDao)
	{
		this.productPromotionsCronJobDao = productPromotionsCronJobDao;
	}

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
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

	public SiteOneProductService getSiteOneProductService()
	{
		return siteOneProductService;
	}

	public void setSiteOneProductService(final SiteOneProductService siteOneProductService)
	{
		this.siteOneProductService = siteOneProductService;
	}

}
