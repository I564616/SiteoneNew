package com.siteone.integration.services.ue;

import com.siteone.integration.product.data.SiteOneSalesRequestData;
import com.siteone.integration.product.data.SiteOneSalesResponseData;

public interface SiteOneSalesDataWebService {
	
	
	SiteOneSalesResponseData getSalesData(SiteOneSalesRequestData siteOneSalesRequestData);
	
}
