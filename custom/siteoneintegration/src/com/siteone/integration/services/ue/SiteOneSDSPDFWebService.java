package com.siteone.integration.services.ue;

/**
 * @author VenkatB
 *
 */

public interface SiteOneSDSPDFWebService {

	 byte[] getPDFByResourceId(String skuId, String resourceId);
}
