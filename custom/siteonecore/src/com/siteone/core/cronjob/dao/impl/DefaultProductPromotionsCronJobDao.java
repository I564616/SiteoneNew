/**
 *
 */
package com.siteone.core.cronjob.dao.impl;

import de.hybris.platform.promotionengineservices.model.PromotionSourceRuleModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.util.Config;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.siteone.core.cronjob.dao.ProductPromotionsCronJobDao;


/**
 * @author 1124932
 *
 */
public class DefaultProductPromotionsCronJobDao extends AbstractItemDao implements ProductPromotionsCronJobDao
{
	@Override
	public List<PromotionSourceRuleModel> getModifiedPromotions()
	{
		final List<PromotionSourceRuleModel> promotionSourceRuleList = getFlexibleSearchService()
				.<PromotionSourceRuleModel> search(Config.getString("product.promotions.cronjob.query", null)).getResult();
		if (CollectionUtils.isNotEmpty(promotionSourceRuleList))
		{
			return promotionSourceRuleList;
		}
		return null;
	}

}
