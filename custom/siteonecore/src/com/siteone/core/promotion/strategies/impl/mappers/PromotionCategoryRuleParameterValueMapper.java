/**
 *
 */
package com.siteone.core.promotion.strategies.impl.mappers;

import de.hybris.platform.ruleengineservices.rule.strategies.RuleParameterValueMapper;
import de.hybris.platform.ruleengineservices.rule.strategies.RuleParameterValueMapperException;
import de.hybris.platform.servicelayer.util.ServicesUtil;

import com.siteone.core.model.PromotionProductCategoryModel;
import com.siteone.core.services.SiteOnePromotionSourceRuleService;


/**
 * @author BS
 *
 */
public class PromotionCategoryRuleParameterValueMapper implements RuleParameterValueMapper<PromotionProductCategoryModel>
{
	private SiteOnePromotionSourceRuleService siteOnePromotionSourceRuleService;

	public String toString(final PromotionProductCategoryModel category)
	{
		ServicesUtil.validateParameterNotNull(category, "Object cannot be null");
		return category.getCode();
	}

	public PromotionProductCategoryModel fromString(final String value)
	{
		ServicesUtil.validateParameterNotNull(value, "String value cannot be null");
		final PromotionProductCategoryModel category = this.siteOnePromotionSourceRuleService.getPromotionCategoryForCode(value);
		if (category == null)
		{
			throw new RuleParameterValueMapperException("Cannot find user group with the UID: " + value);
		}
		else
		{
			return category;
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
