package com.siteone.integration.services.ue.impl;

import org.springframework.http.HttpMethod;

import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.inventory.data.SiteOneWsInventoryRequestData;
import com.siteone.integration.inventory.data.SiteOneWsInventoryResponseData;
import com.siteone.integration.rest.client.SiteOneRestClient;
import com.siteone.integration.services.ue.SiteOneInventoryWebService;

import de.hybris.platform.util.Config;

/**
 * @author 1230514
 *
 */
public class DefaultSiteOneInventoryWebService implements SiteOneInventoryWebService {



	private SiteOneRestClient<SiteOneWsInventoryRequestData, SiteOneWsInventoryResponseData> siteOneRestClient;

	@Override
	public SiteOneWsInventoryResponseData getInventory(final SiteOneWsInventoryRequestData siteOneWsInventoryRequestData)
	{
		// YTODO Auto-generated method stub
		final SiteOneWsInventoryResponseData siteOneWsInventoryResponseData = getSiteOneRestClient().execute(Config.getString("ue.inventory.url", null),
				HttpMethod.POST, siteOneWsInventoryRequestData, SiteOneWsInventoryResponseData.class,siteOneWsInventoryRequestData.getCorrelationId(),SiteoneintegrationConstants.INVENTORY_SERVICE_NAME,null);

		return siteOneWsInventoryResponseData;
	}

	/**
	 * @return the siteOneRestClient
	 */
	public SiteOneRestClient<SiteOneWsInventoryRequestData, SiteOneWsInventoryResponseData> getSiteOneRestClient()
	{
		return siteOneRestClient;
	}

	/**
	 * @param siteOneRestClient
	 *           the siteOneRestClient to set
	 */
	public void setSiteOneRestClient(
			final SiteOneRestClient<SiteOneWsInventoryRequestData, SiteOneWsInventoryResponseData> siteOneRestClient)
	{
		this.siteOneRestClient = siteOneRestClient;
	}

}
