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

import com.siteone.core.model.ShareAssemblyProcessModel;


/**
 * @author 1003567
 *
 */
public class ShareAssemblyEventListener extends AbstractAcceleratorSiteEventListener<ShareAssemblyEvent>
{
	private ModelService modelService;
	private BusinessProcessService businessProcessService;

	@Override
	protected SiteChannel getSiteChannelForEvent(final ShareAssemblyEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}

	@Override
	protected void onSiteEvent(final ShareAssemblyEvent event)
	{

		final ShareAssemblyProcessModel shareAssemblyProcessModel = (ShareAssemblyProcessModel) getBusinessProcessService()
				.createProcess("shareAssemblyEmailProcess-" + event.getUserName() + "-" + System.currentTimeMillis(),
						"shareAssemblyEmailProcess");

		shareAssemblyProcessModel.setEmailAddress(event.getEmail());
		shareAssemblyProcessModel.setUsername(event.getUserName());
		shareAssemblyProcessModel.setAssemblyName(event.getAssemblyName());
		shareAssemblyProcessModel.setAssemblyCode(event.getAssemblyCode());
		shareAssemblyProcessModel.setSite(event.getSite());
		shareAssemblyProcessModel.setStore(event.getBaseStore());
		shareAssemblyProcessModel.setLanguage(event.getLanguage());
		getModelService().save(shareAssemblyProcessModel);
		getBusinessProcessService().startProcess(shareAssemblyProcessModel);

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
