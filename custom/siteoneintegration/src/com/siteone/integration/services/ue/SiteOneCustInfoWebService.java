package com.siteone.integration.services.ue;

import com.siteone.integration.customer.info.SiteOneCustInfoResponeData;

import de.hybris.platform.b2b.model.B2BUnitModel;

public interface SiteOneCustInfoWebService {
	
	SiteOneCustInfoResponeData getCustInfo(B2BUnitModel b2BUnitModel, boolean isNewBoomiEnv);
	
	SiteOneCustInfoResponeData getPartnerPointsInfo(B2BUnitModel b2BUnitModel, boolean isNewBoomiEnv);
	
}
