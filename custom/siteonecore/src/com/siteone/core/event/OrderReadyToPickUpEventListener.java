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

import com.siteone.core.model.OrderReadyToPickUpEmailProcessModel;


/**
 * @author 1099417
 *
 */
public class OrderReadyToPickUpEventListener extends AbstractAcceleratorSiteEventListener<OrderReadyToPickUpEvent>
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.acceleratorservices.site.AbstractAcceleratorSiteEventListener#getSiteChannelForEvent(de.hybris.
	 * platform.servicelayer.event.events.AbstractEvent)
	 */
	@Override
	protected SiteChannel getSiteChannelForEvent(final OrderReadyToPickUpEvent event)
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
	protected void onSiteEvent(final OrderReadyToPickUpEvent event)
	{
		// YTODO Auto-generated method stub
		final OrderReadyToPickUpEmailProcessModel orderReadyToPickUpProcessModel = (OrderReadyToPickUpEmailProcessModel) getBusinessProcessService()
				.createProcess("orderReadyToPickUpEmailProcess-" + event.getOrder() + "-" + System.currentTimeMillis(),
						"orderReadyToPickUpEmailProcess");
		orderReadyToPickUpProcessModel.setSite(event.getSite());
		orderReadyToPickUpProcessModel.setCustomer(event.getCustomer());
		orderReadyToPickUpProcessModel.setOrder(event.getOrder());
		orderReadyToPickUpProcessModel.setConsignment(event.getConsignment());
		orderReadyToPickUpProcessModel.setLanguage(event.getLanguage());
		orderReadyToPickUpProcessModel.setCurrency(event.getCurrency());
		orderReadyToPickUpProcessModel.setStore(event.getBaseStore());

		getModelService().save(orderReadyToPickUpProcessModel);
		getBusinessProcessService().startProcess(orderReadyToPickUpProcessModel);
	}
}
