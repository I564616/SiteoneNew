package com.siteone.integration.jobs.promotion.dao;

import java.util.List;

import de.hybris.platform.couponservices.model.SingleCodeCouponModel;
import de.hybris.platform.promotionengineservices.model.PromotionSourceRuleModel;
import de.hybris.platform.promotionengineservices.model.RuleBasedPromotionModel;

public interface PromotionFeedCronJobDao {

	public List<RuleBasedPromotionModel> getPromotions();
	public PromotionSourceRuleModel getPromotionSourceRule(String Title);
	public SingleCodeCouponModel getCouponByCode(String couponCode);
}
