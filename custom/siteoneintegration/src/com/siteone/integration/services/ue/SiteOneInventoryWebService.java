package com.siteone.integration.services.ue;

import com.siteone.integration.inventory.data.SiteOneWsInventoryRequestData;
import com.siteone.integration.inventory.data.SiteOneWsInventoryResponseData;

/**
 * @author 1230514
 *
 */
public interface SiteOneInventoryWebService {

	SiteOneWsInventoryResponseData getInventory(SiteOneWsInventoryRequestData siteOneWsInventoryRequestData);
}
