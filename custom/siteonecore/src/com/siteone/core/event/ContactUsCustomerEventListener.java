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

import com.siteone.core.model.ContactUsCustomerProcessModel;


/**
 * @author 1003567
 *
 */
public class ContactUsCustomerEventListener extends AbstractAcceleratorSiteEventListener<ContactUsCustomerEvent>
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
	protected SiteChannel getSiteChannelForEvent(final ContactUsCustomerEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}


	@Override
	protected void onSiteEvent(final ContactUsCustomerEvent event)
	{
		final ContactUsCustomerProcessModel requestProcessModel = (ContactUsCustomerProcessModel) getBusinessProcessService()
				.createProcess("contactUsCustomerEmailProcess-" + event.getFirstName() + "-" + System.currentTimeMillis(),
						"contactUsCustomerEmailProcess");
		requestProcessModel.setFirstName(event.getFirstName());
		requestProcessModel.setEmailAddress(event.getEmailAddress());
		requestProcessModel.setSite(event.getSite());
		requestProcessModel.setLanguage(event.getLanguage());
		getModelService().save(requestProcessModel);
		getBusinessProcessService().startProcess(requestProcessModel);



	}

}
