package com.siteone.integration.services.mpos;

import com.siteone.integration.scan.product.data.SiteOneScanProductResponse;
import com.siteone.integration.scan.product.data.SiteOneWsScanProductRequestData;

public interface SiteOneScanProductWebService {
	
	SiteOneScanProductResponse getScanProduct(SiteOneWsScanProductRequestData scanProductRequest, boolean isNewBoomiEnv);
}
