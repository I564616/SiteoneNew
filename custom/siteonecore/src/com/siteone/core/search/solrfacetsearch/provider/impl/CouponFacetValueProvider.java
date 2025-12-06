/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.promotionengineservices.model.PromotionSourceRuleModel;
import de.hybris.platform.promotions.PromotionsService;
import de.hybris.platform.promotions.model.AbstractPromotionModel;
import de.hybris.platform.promotions.model.PromotionGroupModel;
import de.hybris.platform.servicelayer.time.TimeService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.siteone.core.services.SiteOnePromotionSourceRuleService;


/**
 * @author SD02010
 *
 */
public class CouponFacetValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider, Serializable
{

	private FieldNameProvider fieldNameProvider;
	private PromotionsService promotionService;
	private BaseSiteService baseSiteService;
	@Resource(name = "timeService")
	private TimeService timeService;
	@Resource(name = "siteOnePromotionSourceRuleService")
	private SiteOnePromotionSourceRuleService siteOnePromotionSourceRuleService;

	/**
	 * @return the siteOnePromotionSourceRuleService
	 */
	public SiteOnePromotionSourceRuleService getSiteOnePromotionSourceRuleService()
	{
		return siteOnePromotionSourceRuleService;
	}


	/**
	 * @param siteOnePromotionSourceRuleService
	 *           the siteOnePromotionSourceRuleService to set
	 */
	public void setSiteOnePromotionSourceRuleService(final SiteOnePromotionSourceRuleService siteOnePromotionSourceRuleService)
	{
		this.siteOnePromotionSourceRuleService = siteOnePromotionSourceRuleService;
	}



	/**
	 * @return the timeService
	 */
	public TimeService getTimeService()
	{
		return timeService;
	}


	/**
	 * @param timeService
	 *           the timeService to set
	 */
	public void setTimeService(final TimeService timeService)
	{
		this.timeService = timeService;
	}


	/**
	 * @return the baseSiteService
	 */
	public BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}


	/**
	 * @param baseSiteService
	 *           the baseSiteService to set
	 */
	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}


	/**
	 * @return the promotionService
	 */
	public PromotionsService getPromotionsService()
	{
		return promotionService;
	}


	/**
	 * @param promotionService
	 *           the promotionService to set
	 */
	public void setPromotionsService(final PromotionsService promotionService)
	{
		this.promotionService = promotionService;
	}


	/**
	 * @return the fieldNameProvider
	 */
	public FieldNameProvider getFieldNameProvider()
	{
		return fieldNameProvider;
	}


	/**
	 * @param fieldNameProvider
	 *           the fieldNameProvider to set
	 */
	public void setFieldNameProvider(final FieldNameProvider fieldNameProvider)
	{
		this.fieldNameProvider = fieldNameProvider;
	}


	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		if (model instanceof ProductModel)
		{
			final ProductModel product = (ProductModel) model;
			final Collection<FieldValue> fieldValues = new ArrayList<>();
			final BaseSiteModel baseSiteModel = getBaseSiteService().getCurrentBaseSite();
			if (baseSiteModel != null)
			{
				final PromotionGroupModel defaultPromotionGroup = baseSiteModel.getDefaultPromotionGroup();
				final Date currentTimeRoundedToMinute = DateUtils.round(getTimeService().getCurrentTime(), Calendar.MINUTE);

				if (defaultPromotionGroup != null)
				{
					final List<AbstractPromotionModel> promotions = (List<AbstractPromotionModel>) getPromotionsService()
							.getAbstractProductPromotions(Collections.singletonList(defaultPromotionGroup), product, true,
									currentTimeRoundedToMinute);

					if (CollectionUtils.isNotEmpty(promotions))
					{
						final PromotionSourceRuleModel promotionSourceRuleModel = getSiteOnePromotionSourceRuleService()
								.getPromotionSourceRuleByCode(promotions.get(promotions.size() - 1).getTitle());

						if (null != promotionSourceRuleModel.getProductCoupon())
						{
							fieldValues.addAll(createFieldValue("Coupons", indexedProperty));
						}

					}
				}
			}

			return fieldValues;
		}
		else
		{
			throw new FieldValueProviderException("not product model instance");
		}

	}

	protected Collection<FieldValue> createFieldValue(final String couponEnabled, final IndexedProperty indexedProperty)
	{
		final List<FieldValue> fieldValues = new ArrayList<>();
		final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, null);
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, couponEnabled));
		}
		return fieldValues;
	}



}