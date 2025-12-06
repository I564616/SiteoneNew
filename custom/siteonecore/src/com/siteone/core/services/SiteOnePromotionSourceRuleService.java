/**
 *
 */
package com.siteone.core.services;

import de.hybris.platform.promotionengineservices.model.PromotionSourceRuleModel;

import com.siteone.core.model.PromotionProductCategoryModel;


/**
 * @author 1091124
 *
 */
public interface SiteOnePromotionSourceRuleService
{

	PromotionSourceRuleModel getPromotionSourceRuleByCode(final String code);

	PromotionProductCategoryModel getPromotionCategoryForCode(String code);
}
