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

import com.siteone.core.model.ListEditEmailProcessModel;


/**
 * @author 1003567
 *
 */
public class ListEditEventListener extends AbstractAcceleratorSiteEventListener<ListEditEvent>
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
	protected SiteChannel getSiteChannelForEvent(final ListEditEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}


	@Override
	protected void onSiteEvent(final ListEditEvent event)
	{
		final ListEditEmailProcessModel requestProcessModel = (ListEditEmailProcessModel) getBusinessProcessService().createProcess(
				"listEditEmailProcess-" + event.getListName() + "-" + System.currentTimeMillis(), "listEditEmailProcess");
		requestProcessModel.setListCode(event.getListCode());
		requestProcessModel.setListName(event.getListName());
		requestProcessModel.setSite(event.getSite());
		requestProcessModel.setEmailAddress(event.getEmailAddress());
		requestProcessModel.setUpdatelistName(event.getUpdateListName());
		requestProcessModel.setStoreCity(event.getStoreAddress());
		requestProcessModel.setStoreId(event.getStoreId());
		requestProcessModel.setContactNumber(event.getContactNumber());
		requestProcessModel.setCustomerName(event.getCustomerName());
		requestProcessModel.setLanguage(event.getLanguage());
		getModelService().save(requestProcessModel);
		getBusinessProcessService().startProcess(requestProcessModel);

	}

}
