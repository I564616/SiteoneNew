/**
 *
 */
package com.siteone.core.event;

/**
 * @author NMangal
 *
 */


import de.hybris.platform.acceleratorservices.site.AbstractAcceleratorSiteEventListener;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.enums.SiteChannel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;

import com.siteone.core.model.SiteoneJobsStatusProcessModel;


public class SiteoneJobsStatusEventListener extends AbstractAcceleratorSiteEventListener<SiteoneJobsStatusEvent>
{
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


	@Override
	protected SiteChannel getSiteChannelForEvent(final SiteoneJobsStatusEvent event)
	{

		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();

	}


	@Override
	protected void onSiteEvent(final SiteoneJobsStatusEvent event)
	{

		final SiteoneJobsStatusProcessModel requestProcessModel = (SiteoneJobsStatusProcessModel) getBusinessProcessService()
				.createProcess("siteoneJobsStatusEmailProcess-" + "-" + System.currentTimeMillis(), "siteoneJobsStatusEmailProcess");
		requestProcessModel.setEmailReceiver(event.getEmailReceiver());
		requestProcessModel.setSite(event.getSite());
		requestProcessModel.setSiteoneJobsStatusList(event.getCronJobList());
		requestProcessModel.setLanguage(event.getSite().getDefaultLanguage());
		//requestProcessModel.set);


		getModelService().save(requestProcessModel);
		getBusinessProcessService().startProcess(requestProcessModel);


	}
}
