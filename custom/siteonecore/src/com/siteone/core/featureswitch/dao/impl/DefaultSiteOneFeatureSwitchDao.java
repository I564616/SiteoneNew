/**
 *
 */
package com.siteone.core.featureswitch.dao.impl;

import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.HashMap;

import org.apache.commons.collections4.CollectionUtils;

import com.siteone.core.featureswitch.dao.SiteOneFeatureSwitchDao;
import com.siteone.core.model.SiteOneFeatureSwitchModel;


/**
 * @author KArasan
 *
 */
public class DefaultSiteOneFeatureSwitchDao extends AbstractItemDao implements SiteOneFeatureSwitchDao
{

	@Override
	public String getSwitchValue(final String key)
	{
		final HashMap queryParams = new HashMap(2);
		queryParams.put("key", key);

		final String FIND_Siteone_config_data = "select {pk} from {SiteOneFeatureSwitch} where {key}=?key";
		final SearchResult<SiteOneFeatureSwitchModel> result = getFlexibleSearchService()
				.search(new FlexibleSearchQuery(FIND_Siteone_config_data, queryParams));
		final String switchValue = !CollectionUtils.isEmpty(result.getResult())?result.getResult().get(0).getValue():null;
		return switchValue;
	}

	@Override
	public String getSwitchLongValue(final String key)
	{
		final HashMap queryParams = new HashMap(2);
		queryParams.put("key", key);

		final String FIND_Siteone_config_data = "select {pk} from {SiteOneFeatureSwitch} where {key}=?key";
		final SearchResult<SiteOneFeatureSwitchModel> result = getFlexibleSearchService()
				.search(new FlexibleSearchQuery(FIND_Siteone_config_data, queryParams));
		final String switchValue =   !CollectionUtils.isEmpty(result.getResult())?result.getResult().get(0).getLongValue():null;
		return switchValue; 
	}
}
