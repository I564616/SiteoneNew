package com.siteone.event;

import jakarta.annotation.Resource;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.site.BaseSiteService;

public class SiteOnePasswordResetUtil {
	
	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;
	
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;
	
	public void forgottenPassword(String email, BaseSiteModel baseSite){
		baseSiteService.setCurrentBaseSite(baseSite, true);
		customerFacade.forgottenPassword(email);
	}
	
}
