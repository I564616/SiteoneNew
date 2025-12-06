/**
 *
 */
package com.siteone.core.cronjob.dao;

import de.hybris.platform.promotionengineservices.model.PromotionSourceRuleModel;

import java.util.List;


/**
 * @author 1124932
 *
 */
public interface ProductPromotionsCronJobDao
{
	public List<PromotionSourceRuleModel> getModifiedPromotions();

}
