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

import com.siteone.core.model.HomeOwnerProcessModel;


public class HomeOwnerEventListener extends AbstractAcceleratorSiteEventListener<HomeOwnerEvent>
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
	protected SiteChannel getSiteChannelForEvent(final HomeOwnerEvent event)
	{

		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();

	}


	@Override
	protected void onSiteEvent(final HomeOwnerEvent event)
	{

		final HomeOwnerProcessModel requestProcessModel = (HomeOwnerProcessModel) getBusinessProcessService().createProcess(
				"homeOwnerEmailProcess-" + event.getFirstName() + "-" + System.currentTimeMillis(), "homeOwnerEmailProcess");
		requestProcessModel.setFirstName(event.getFirstName());

		requestProcessModel.setFirstName(event.getFirstName());
		requestProcessModel.setLastName(event.getLastName());
		requestProcessModel.setEmailAddr(event.getEmailAddr());
		requestProcessModel.setPhone(event.getPhone());
		requestProcessModel.setAddress(event.getAddress());
		requestProcessModel.setCustomerCity(event.getCustomerCity());
		requestProcessModel.setCustomerState(event.getCustomerState());
		requestProcessModel.setCustomerZipCode(event.getCustomerZipCode());

		requestProcessModel.setBestTimeToCall(event.getBestTimeToCall());
		requestProcessModel.setServiceType(event.getServiceType());
		requestProcessModel.setReferalsNo(event.getReferalsNo());
		requestProcessModel.setLookingFor(event.getLookingFor());
		requestProcessModel.setLookingForOthers(event.getLookingForOthers());
		requestProcessModel.setLanguage(event.getLanguage());


		requestProcessModel.setSite(event.getSite());


		getModelService().save(requestProcessModel);
		getBusinessProcessService().startProcess(requestProcessModel);


	}
}
