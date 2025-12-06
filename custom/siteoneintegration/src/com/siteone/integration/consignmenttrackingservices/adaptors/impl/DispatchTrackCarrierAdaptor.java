/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.siteone.integration.consignmenttrackingservices.adaptors.impl;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.siteone.integration.services.carriers.CarrierTrackingService;
import com.siteone.integration.services.carriers.impl.DispatchTrackConsignmentTrackingService;

import de.hybris.platform.acceleratorservices.urlresolver.SiteBaseUrlResolutionService;
import de.hybris.platform.consignmenttrackingservices.adaptors.CarrierAdaptor;
import de.hybris.platform.consignmenttrackingservices.delivery.data.ConsignmentEventData;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.site.BaseSiteService;


/**
 * This is Dispatch Track Carrier Adaptor class
 * 
 * @author Ravi P(RP01944)
 *
 */
public class DispatchTrackCarrierAdaptor implements CarrierAdaptor
{
	private static final Logger LOG = LoggerFactory.getLogger(DispatchTrackCarrierAdaptor.class);
	private static final String TRACKING_URL_KEY = "dispatchtrack.carrier.tracking.url";
	private static final String DELIVERY_LEAD_TIME_KEY = "default.delivery.lead.time";
	private static final String SITEONE_DISPATCHTRACK_KEY = "siteone.dispatchtrack.id";
	private ConfigurationService configurationService;
	private SiteBaseUrlResolutionService siteBaseUrlResolutionService;
	private BaseSiteService baseSiteService;
	private CarrierTrackingService carrierTrackingService;
	
	@Override
	public List<ConsignmentEventData> getConsignmentEvents(final String trackingId)
	{	
		return getCarrierTrackingService().getConsignmentEvents(trackingId);
	}

	@Override
	public URL getTrackingUrl(final String trackingID)
	{
		final Configuration config = getConfigurationService().getConfiguration();
		final String trackingUrl = config.getString(TRACKING_URL_KEY, StringUtils.EMPTY);
		final String siteoneDispatchTrackKey = config.getString(SITEONE_DISPATCHTRACK_KEY, StringUtils.EMPTY);
		if(StringUtils.isNotEmpty(trackingID) && StringUtils.isNotBlank(trackingUrl))
		{
			try
			{
				URL url=URI.create(trackingUrl + "/" + siteoneDispatchTrackKey + "/" + trackingID).toURL();
				LOG.info("Tracking URL", url);
				return url;
			}
			catch (final MalformedURLException e)
			{
				LOG.error("Invalid Tracking URL", e);
			}
		}

		return null;
	}

	@Override
	public int getDeliveryLeadTime(final ConsignmentModel consignment)
	{
		final Configuration config = getConfigurationService().getConfiguration();
		return config.getInt(DELIVERY_LEAD_TIME_KEY, 0);
	}

	protected ConfigurationService getConfigurationService()
	{
		return configurationService;
	}

	public void setConfigurationService(final ConfigurationService configurationService)
	{
		this.configurationService = configurationService;
	}

	protected SiteBaseUrlResolutionService getSiteBaseUrlResolutionService()
	{
		return siteBaseUrlResolutionService;
	}

	public void setSiteBaseUrlResolutionService(final SiteBaseUrlResolutionService siteBaseUrlResolutionService)
	{
		this.siteBaseUrlResolutionService = siteBaseUrlResolutionService;
	}

	protected BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}

	protected CarrierTrackingService getCarrierTrackingService() {
		return carrierTrackingService;
	}

	public void setCarrierTrackingService(CarrierTrackingService carrierTrackingService) {
		this.carrierTrackingService = carrierTrackingService;
	}	

}
