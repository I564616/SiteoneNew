/**
 *
 */
package com.siteone.core.services;

import de.hybris.platform.core.model.c2l.RegionModel;

import java.util.List;
import java.util.Map;

import com.siteone.core.model.RegulatoryStatesCronJobModel;


/**
 * @author 1124932
 *
 */
public interface RegulatoryStatesCronJobService
{
	void importProductRegulatoryStates(RegulatoryStatesCronJobModel regulatoryStatesCronJobModel);

	Boolean getRupBySkuAndState(String sku, RegionModel state);

	Map<String, Boolean> getRupForPLPByState(List<String> productList, RegionModel region);
}

