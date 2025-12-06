/**
 *
 */
package com.siteone.facades.promotion.impl;

import de.hybris.platform.commercefacades.product.data.PromotionData;
import de.hybris.platform.commerceservices.promotion.CommercePromotionService;
import de.hybris.platform.promotions.model.AbstractPromotionModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import com.siteone.facades.promotions.SiteOnePromotionsFacade;


/**
 * @author 1124932
 *
 */
public class DefaultSiteOnePromotionsFacade implements SiteOnePromotionsFacade
{

	private CommercePromotionService commercePromotionService;
	private Converter<AbstractPromotionModel, PromotionData> promotionsConverter;

	@Override
	public PromotionData getPromotionsByCode(final String promotionCode)
	{
		final AbstractPromotionModel promotion = commercePromotionService.getPromotion(promotionCode);
		return getPromotionsConverter().convert(promotion);
	}

	/**
	 * @return the commercePromotionService
	 */
	public CommercePromotionService getCommercePromotionService()
	{
		return commercePromotionService;
	}

	/**
	 * @param commercePromotionService
	 *           the commercePromotionService to set
	 */
	public void setCommercePromotionService(final CommercePromotionService commercePromotionService)
	{
		this.commercePromotionService = commercePromotionService;
	}

	/**
	 * @return the promotionsConverter
	 */
	public Converter<AbstractPromotionModel, PromotionData> getPromotionsConverter()
	{
		return promotionsConverter;
	}

	/**
	 * @param promotionsConverter
	 *           the promotionsConverter to set
	 */
	public void setPromotionsConverter(final Converter<AbstractPromotionModel, PromotionData> promotionsConverter)
	{
		this.promotionsConverter = promotionsConverter;
	}
}