/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.acceleratorservices.site.AbstractAcceleratorSiteEventListener;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.enums.SiteChannel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;

import org.apache.log4j.Logger;

import com.siteone.core.model.FeedFileMonitorNotificationProcessModel;


/**
 * @author rpalanisamy
 *
 */
public class SiteOneFeedFileNotificationEventListener
		extends AbstractAcceleratorSiteEventListener<SiteOneFeedFileNotificationEvent>
{

	private static final Logger LOG = Logger.getLogger(SiteOneFeedFileNotificationEventListener.class);

	private ModelService modelService;
	private BusinessProcessService businessProcessService;


	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.acceleratorservices.site.AbstractAcceleratorSiteEventListener#getSiteChannelForEvent(de.hybris.
	 * platform.servicelayer.event.events.AbstractEvent)
	 */
	@Override
	protected SiteChannel getSiteChannelForEvent(final SiteOneFeedFileNotificationEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.commerceservices.event.AbstractSiteEventListener#onSiteEvent(de.hybris.platform.servicelayer.
	 * event.events.AbstractEvent)
	 */
	@Override
	protected void onSiteEvent(final SiteOneFeedFileNotificationEvent event)
	{

		LOG.info("Enter into onSiteEvent");

		final FeedFileMonitorNotificationProcessModel processModel = (FeedFileMonitorNotificationProcessModel) getBusinessProcessService()
				.createProcess("feedFileMonitorNotificationEmailProcess-" + System.currentTimeMillis(),
						"feedFileMonitorNotificationEmailProcess");

		processModel.setFeedFileList(event.getFeedFiles());
		processModel.setEmailReceiver(event.getEmailReceiver());
		processModel.setSite(event.getSite());
		processModel.setStore(event.getBaseStore());
		processModel.setLanguage(event.getLanguage());
		getModelService().save(processModel);
		getBusinessProcessService().startProcess(processModel);
	}

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	/**
	 * @return the businessProcessService
	 */
	public BusinessProcessService getBusinessProcessService()
	{
		return businessProcessService;
	}

	/**
	 * @param businessProcessService
	 *           the businessProcessService to set
	 */
	public void setBusinessProcessService(final BusinessProcessService businessProcessService)
	{
		this.businessProcessService = businessProcessService;
	}


}
