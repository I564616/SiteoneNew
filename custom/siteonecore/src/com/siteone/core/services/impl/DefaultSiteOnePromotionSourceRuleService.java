/**
 *
 */
package com.siteone.core.services.impl;

import de.hybris.platform.promotionengineservices.model.PromotionSourceRuleModel;

import com.siteone.core.model.PromotionProductCategoryModel;
import com.siteone.core.promotion.dao.SiteOnePromotionSourceRuleDao;
import com.siteone.core.services.SiteOnePromotionSourceRuleService;


/**
 * @author 1091124
 *
 */
public class DefaultSiteOnePromotionSourceRuleService implements SiteOnePromotionSourceRuleService
{
	private SiteOnePromotionSourceRuleDao siteOnePromotionSourceRuleDao;

	@Override
	public PromotionSourceRuleModel getPromotionSourceRuleByCode(final String code)
	{
		return getSiteOnePromotionSourceRuleDao().getPromotionSourceRuleByCode(code);
	}

	@Override
	public PromotionProductCategoryModel getPromotionCategoryForCode(final String code)
	{
		// YTODO Auto-generated method stub
		return getSiteOnePromotionSourceRuleDao().findPromotionCategoryForCode(code);
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



}
