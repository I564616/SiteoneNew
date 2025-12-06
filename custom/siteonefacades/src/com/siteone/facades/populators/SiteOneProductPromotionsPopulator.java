/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.product.converters.populator.ProductPromotionsPopulator;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.promotionengineservices.model.PromotionSourceRuleModel;
import de.hybris.platform.promotions.model.AbstractPromotionModel;
import de.hybris.platform.promotions.model.PromotionGroupModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.siteone.core.services.SiteOnePromotionSourceRuleService;


/**
 * @author 1091124
 *
 */
public class SiteOneProductPromotionsPopulator extends ProductPromotionsPopulator
{
	private SiteOnePromotionSourceRuleService siteOnePromotionSourceRuleService;

	@Override
	public void populate(final ProductModel productModel, final ProductData productData) throws ConversionException
	{

		final BaseSiteModel baseSiteModel = getBaseSiteService().getCurrentBaseSite();
		if (baseSiteModel != null)
		{
			final PromotionGroupModel defaultPromotionGroup = baseSiteModel.getDefaultPromotionGroup();
			final Date currentTimeRoundedToMinute = DateUtils.round(getTimeService().getCurrentTime(), Calendar.MINUTE);

			if (defaultPromotionGroup != null)
			{
				final List<AbstractPromotionModel> promotions = (List<AbstractPromotionModel>) getPromotionsService()
						.getAbstractProductPromotions(Collections.singletonList(defaultPromotionGroup), productModel, true,
								currentTimeRoundedToMinute);
				productData.setPotentialPromotions(getPromotionsConverter().convertAll(promotions));

				if (CollectionUtils.isNotEmpty(promotions))
				{
					final PromotionSourceRuleModel promotionSourceRuleModel = getSiteOnePromotionSourceRuleService()
							.getPromotionSourceRuleByCode(promotions.get(promotions.size() - 1).getTitle());

					if (null != promotionSourceRuleModel.getProductCoupon())
					{
						productData.setCouponCode(promotionSourceRuleModel.getProductCoupon());
					}
					if (null != promotionSourceRuleModel.getPromotionDetails())
					{
						productData.setPromoDetails(promotionSourceRuleModel.getPromotionDetails());
					}
					productData.setIsPromoDescriptionEnabled(promotionSourceRuleModel.getIsDescriptionEnabled());
				}
			}
		}
	}

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

}
