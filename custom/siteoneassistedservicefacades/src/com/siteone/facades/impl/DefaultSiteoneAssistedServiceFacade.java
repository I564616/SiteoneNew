package com.siteone.facades.impl;

import com.siteone.facades.checkout.impl.DefaultSiteOneB2BCheckoutFacade;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import de.hybris.platform.assistedservicefacades.impl.DefaultAssistedServiceFacade;
import de.hybris.platform.assistedserviceservices.exception.AssistedServiceException;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.site.BaseSiteService;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;

public class DefaultSiteoneAssistedServiceFacade extends DefaultAssistedServiceFacade {
	private static final Logger LOG = Logger.getLogger(DefaultSiteoneAssistedServiceFacade.class);
    @Resource(name = "storeSessionFacade")
    private SiteOneStoreSessionFacade storeSessionFacade;

    @Resource(name = "b2bUnitFacade")
    private B2BUnitFacade b2bUnitFacade;
    
    @Resource
    BaseSiteService baseSiteService;

    @Override
    public void emulateCustomer(String customerId, String cartId, String orderId) throws AssistedServiceException {
        super.emulateCustomer(customerId, cartId, orderId);
        BaseSiteModel basesite =null;
        final String b2bUnit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer().getUid();
        storeSessionFacade.setSessionShipTo(((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer());
        if(null!=b2bUnit && b2bUnit.contains("_US"))
        {
        	basesite = baseSiteService.getBaseSiteForUID("siteone-us");
        }
        else {
        	basesite = baseSiteService.getBaseSiteForUID("siteone-ca");
        }
        this.getSessionService().setAttribute("currentSite", basesite);
         LOG.error("ASM basesite "+basesite.getUid());
    }

    @Override
    public void logoutAssistedServiceAgent() throws AssistedServiceException {
        stopEmulateCustomer();

        storeSessionFacade.removeSessionShipTo();

        super.logoutAssistedServiceAgent();
    }
}
