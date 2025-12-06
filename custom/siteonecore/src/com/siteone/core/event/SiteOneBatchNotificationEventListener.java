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

import com.siteone.core.model.BatchNotificationProcessModel;


/**
 * @author 1230514
 *
 */
public class SiteOneBatchNotificationEventListener extends AbstractAcceleratorSiteEventListener<SiteOneBatchNotificationEvent>
{

	private static final Logger LOG = Logger.getLogger(SiteOneBatchNotificationEventListener.class);

	private ModelService modelService;
	private BusinessProcessService businessProcessService;

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

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.acceleratorservices.site.AbstractAcceleratorSiteEventListener#getSiteChannelForEvent(de.hybris.
	 * platform.servicelayer.event.events.AbstractEvent)
	 */
	@Override
	protected SiteChannel getSiteChannelForEvent(final SiteOneBatchNotificationEvent event)
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
	protected void onSiteEvent(final SiteOneBatchNotificationEvent event)
	{

		LOG.info("Enter into onSiteEvent");

		final BatchNotificationProcessModel processModel = (BatchNotificationProcessModel) getBusinessProcessService()
				.createProcess("batchNotificationEmailProcess-" + event.getFileName() + System.currentTimeMillis(),
						"batchNotificationEmailProcess");

		processModel.setFileName(event.getFileName());
		processModel.setSite(event.getSite());
		processModel.setStore(event.getBaseStore());
		processModel.setEmailReceiver(event.getEmailReceiver());
		if (null != event.getImpexTransformerLog() && !event.getImpexTransformerLog().isEmpty())
		{
			processModel.setImpexTransformerLog(event.getImpexTransformerLog());
		}
		if (null != event.getImportCronjob())
		{
			processModel.setImportCronjob(event.getImportCronjob());
		}
		processModel.setLanguage(event.getSite().getDefaultLanguage());
		getModelService().save(processModel);
		getBusinessProcessService().startProcess(processModel);
	}
}
