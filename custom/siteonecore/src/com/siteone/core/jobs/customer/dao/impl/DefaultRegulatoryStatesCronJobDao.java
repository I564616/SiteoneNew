/**
 *
 */
package com.siteone.core.jobs.customer.dao.impl;

import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.util.Config;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

import com.siteone.core.jobs.customer.dao.RegulatoryStatesCronJobDao;
import com.siteone.core.model.RegulatoryStatesModel;


/**
 * @author 1124932
 *
 */
public class DefaultRegulatoryStatesCronJobDao extends AbstractItemDao implements RegulatoryStatesCronJobDao
{

	@Override
	public List<RegulatoryStatesModel> getAllRegulatoryStates()
	{
		return getFlexibleSearchService().<RegulatoryStatesModel> search(Config.getString("regulatory.states.cronjob.query", null))
				.getResult();
	}

	@Override
	public int getAllRegulatoryStatesInBatches()

	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery("SELECT count({pk}) FROM {RegulatoryStates}");
		query.setResultClassList(Arrays.asList(Long.class));
		final SearchResult<Long> result = this.getFlexibleSearchService().search(query);
		Long firstElement = 0L;
		if (null != result.getResult())
		{
			firstElement = result.getResult().get(0);
		}

		return firstElement.intValue();
	}

	@Override
	public List<RegulatoryStatesModel> getAllRegulatoryStatesInBatchesForDeletion(final int batchSize, final int offset)
	{
		final String configuredQuery = Config.getString("regulatory.states.cronjob.query", null);
		final FlexibleSearchQuery query = new FlexibleSearchQuery(configuredQuery);
		query.setCount(batchSize);
		query.setStart(offset);
		final SearchResult<RegulatoryStatesModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}

	@Override
	public List<ProductModel> getRegulatedProductCodes()
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(
				"select {pk} from {product} where {isregulateditem} = '1' and  {code} not in ({{select {sku} from {regulatorystates}}})");
		final SearchResult<ProductModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}

	@Override
	public Boolean getRupBySkuAndState(final String sku, final RegionModel state)
	{
		//Migration | Query change
		final FlexibleSearchQuery query = new FlexibleSearchQuery(
				"select {pk} from {RegulatoryStates} where {sku} = ?sku and {state} = ?state and {expirationdate} >= SYSDATETIME()");
		query.addQueryParameter("sku", sku);
		query.addQueryParameter("state", state);
		final SearchResult<RegulatoryStatesModel> result = getFlexibleSearchService().search(query);
		final List<RegulatoryStatesModel> regulatoryStates = result.getResult();
		if (CollectionUtils.isNotEmpty(regulatoryStates))
		{
			return regulatoryStates.get(0).getIsRup();
		}
		return false;
	}
	
	@Override
	public Map<String, Boolean> getRupForPLPByState(List<String> productSkus, RegionModel region)
	{
		Map<String, Boolean> productRupMap = new HashMap<>();
		final FlexibleSearchQuery query = new FlexibleSearchQuery(
				"select {pk} from {RegulatoryStates} where {sku} in (?sku) and {state} = ?state and {expirationdate} >= SYSDATETIME()");
		query.addQueryParameter("sku", productSkus);
		query.addQueryParameter("state", region);
		final SearchResult<RegulatoryStatesModel> result = getFlexibleSearchService().search(query);
		final List<RegulatoryStatesModel> regulatoryStates = result.getResult();
		if (CollectionUtils.isNotEmpty(regulatoryStates))
		{
			for(RegulatoryStatesModel regStates : regulatoryStates)
			{
				productRupMap.put(regStates.getSku(), regStates.getIsRup());
			}
		}
		return productRupMap;
	}


}

