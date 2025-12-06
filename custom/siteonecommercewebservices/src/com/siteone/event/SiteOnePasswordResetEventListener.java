package com.siteone.event;


import jakarta.annotation.Resource;

import org.apache.log4j.Logger;

import de.hybris.platform.acceleratorservices.site.AbstractAcceleratorSiteEventListener;
import de.hybris.platform.commerceservices.enums.SiteChannel;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.util.ServicesUtil;

public class SiteOnePasswordResetEventListener extends AbstractAcceleratorSiteEventListener<SiteOnePasswordResetEvent>{

	private static final Logger LOG = Logger.getLogger(SiteOnePasswordResetEventListener.class);

	@Resource(name = "siteOnePasswordResetUtil")
	private SiteOnePasswordResetUtil siteOnePasswordResetUtil;
	
	@Override
	protected boolean shouldHandleEvent(final SiteOnePasswordResetEvent event)
	{
		return true;
	}
	
	@Override
	protected SiteChannel getSiteChannelForEvent(SiteOnePasswordResetEvent event) {
		final BaseSiteModel site = event.getSite();
		return site.getChannel();
	}

	@Override
	protected void onSiteEvent(SiteOnePasswordResetEvent event) {
		
		siteOnePasswordResetUtil.forgottenPassword(event.getEmail(),event.getSite());
	}

}
