package com.siteone.core.promotion.dao;

import de.hybris.platform.promotionengineservices.model.PromotionSourceRuleModel;
import de.hybris.platform.promotionengineservices.model.RuleBasedPromotionModel;

import com.siteone.core.model.PromotionProductCategoryModel;


public interface SiteOnePromotionSourceRuleDao
{

	public PromotionSourceRuleModel getPromotionSourceRuleByCode(String code);

	public RuleBasedPromotionModel getPromotionByCode(String code);

	public RuleBasedPromotionModel getPromotionForPreviousRule(String code);

	public PromotionProductCategoryModel findPromotionCategoryForCode(String code);

}
