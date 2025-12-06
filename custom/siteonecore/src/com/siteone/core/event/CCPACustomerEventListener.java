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

import com.siteone.core.model.CCPACustomerProcessModel;


/**
 * @author BS
 *
 */
public class CCPACustomerEventListener extends AbstractAcceleratorSiteEventListener<CCPACustomerEvent>
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
	protected SiteChannel getSiteChannelForEvent(final CCPACustomerEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}


	@Override
	protected void onSiteEvent(final CCPACustomerEvent event)
	{
		final CCPACustomerProcessModel requestProcessModel = (CCPACustomerProcessModel) getBusinessProcessService().createProcess(
				"ccpaCustomerEmailProcess-" + event.getFirstName() + "-" + System.currentTimeMillis(), "ccpaCustomerEmailProcess");
		requestProcessModel.setFirstName(event.getFirstName());
		requestProcessModel.setEmailAddress(event.getEmailAddress());
		requestProcessModel.setSite(event.getSite());
		requestProcessModel.setLanguage(event.getLanguage());
		getModelService().save(requestProcessModel);
		getBusinessProcessService().startProcess(requestProcessModel);



	}

}
