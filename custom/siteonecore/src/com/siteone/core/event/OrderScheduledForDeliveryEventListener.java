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

import com.siteone.core.model.OrderScheduledForDeliveryEmailProcessModel;


/**
 * @author 1099417
 *
 */
public class OrderScheduledForDeliveryEventListener extends AbstractAcceleratorSiteEventListener<OrderScheduledForDeliveryEvent>
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
	protected SiteChannel getSiteChannelForEvent(final OrderScheduledForDeliveryEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}


	@Override
	protected void onSiteEvent(final OrderScheduledForDeliveryEvent event)
	{
		// YTODO Auto-generated method stub
		final OrderScheduledForDeliveryEmailProcessModel orderScheduledDeliveryProcessModel = (OrderScheduledForDeliveryEmailProcessModel) getBusinessProcessService()
				.createProcess("orderScheduledForDeliveryEmailProcess-" + event.getOrder() + "-" + System.currentTimeMillis(),
						"orderScheduledForDeliveryEmailProcess");
		orderScheduledDeliveryProcessModel.setSite(event.getSite());
		orderScheduledDeliveryProcessModel.setCustomer(event.getCustomer());
		orderScheduledDeliveryProcessModel.setOrder(event.getOrder());
		orderScheduledDeliveryProcessModel.setConsignment(event.getConsignment());
		orderScheduledDeliveryProcessModel.setLanguage(event.getLanguage());
		orderScheduledDeliveryProcessModel.setCurrency(event.getCurrency());
		orderScheduledDeliveryProcessModel.setStore(event.getBaseStore());

		getModelService().save(orderScheduledDeliveryProcessModel);
		getBusinessProcessService().startProcess(orderScheduledDeliveryProcessModel);
	}

}
