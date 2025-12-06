/**
 *
 */
package com.siteone.core.jobs.customer.dao;

import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.product.ProductModel;

import java.util.List;
import java.util.Map;

import com.siteone.core.model.RegulatoryStatesModel;


/**
 * @author 1124932
 *
 */
public interface RegulatoryStatesCronJobDao
{
	public List<RegulatoryStatesModel> getAllRegulatoryStates();

	public List<ProductModel> getRegulatedProductCodes();

	Boolean getRupBySkuAndState(String sku, RegionModel state);

	public int getAllRegulatoryStatesInBatches();

	public List<RegulatoryStatesModel> getAllRegulatoryStatesInBatchesForDeletion(int batchSize, int offset);

	public Map<String, Boolean> getRupForPLPByState(List<String> productList, RegionModel region);

}
