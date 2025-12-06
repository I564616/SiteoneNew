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

import com.siteone.core.model.CCPAProcessModel;


/**
 * @author BS
 *
 */
public class CCPAEventListener extends AbstractAcceleratorSiteEventListener<CCPAEvent>
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
	protected SiteChannel getSiteChannelForEvent(final CCPAEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}


	@Override
	protected void onSiteEvent(final CCPAEvent event)
	{
		final CCPAProcessModel requestProcessModel = (CCPAProcessModel) getBusinessProcessService()
				.createProcess("ccpaEmailProcess-" + event.getFirstName() + "-" + System.currentTimeMillis(), "ccpaEmailProcess");
		requestProcessModel.setCompanyName(event.getCompanyName());
		requestProcessModel.setFirstName(event.getFirstName());
		requestProcessModel.setLastName(event.getLastName());
		requestProcessModel.setPrivacyRequestType(event.getPrivacyRequestType());
		requestProcessModel.setAccountNumber(event.getAccountNumber());
		requestProcessModel.setAddressLine1(event.getAddressLine1());
		requestProcessModel.setAddressLine2(event.getAddressLine2());
		requestProcessModel.setPhoneNumber(event.getPhoneNumber());
		requestProcessModel.setZipcode(event.getZipcode());
		requestProcessModel.setEmailAddress(event.getEmailAddress());
		requestProcessModel.setSite(event.getSite());
		requestProcessModel.setLanguage(event.getLanguage());
		requestProcessModel.setCity(event.getCity());
		requestProcessModel.setCustomerState(event.getState());
		getModelService().save(requestProcessModel);
		getBusinessProcessService().startProcess(requestProcessModel);


	}

}
