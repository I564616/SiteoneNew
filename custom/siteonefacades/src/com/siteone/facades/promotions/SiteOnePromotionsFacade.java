/**
 *
 */
package com.siteone.facades.promotions;

import de.hybris.platform.commercefacades.product.data.PromotionData;


/**
 * @author 1124932
 *
 */
public interface SiteOnePromotionsFacade
{
	PromotionData getPromotionsByCode(String promotionCode);
}
