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

import com.siteone.core.model.OrderDetailEmailProcessModel;


/**
 * @author 1190626
 *
 */
public class SendOrderDetailsEventListener extends AbstractAcceleratorSiteEventListener<SendOrderDetailsEvent>
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
	protected SiteChannel getSiteChannelForEvent(final SendOrderDetailsEvent event)
	{

		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}

	@Override
	protected void onSiteEvent(final SendOrderDetailsEvent event)
	{
		final OrderDetailEmailProcessModel orderDetailEmailProcessModel = (OrderDetailEmailProcessModel) getBusinessProcessService()
				.createProcess("orderDetailEmailProcess-" + event.getOrderCode() + "-" + System.currentTimeMillis(),
						"orderDetailEmailProcess");
		orderDetailEmailProcessModel.setSite(event.getSite());
		orderDetailEmailProcessModel.setCustomer(event.getCustomer());
		orderDetailEmailProcessModel.setShipmentCode(event.getCode());
		orderDetailEmailProcessModel.setLanguage(event.getLanguage());
		orderDetailEmailProcessModel.setCurrency(event.getCurrency());
		orderDetailEmailProcessModel.setStore(event.getBaseStore());
		orderDetailEmailProcessModel.setEmailAddress(event.getEmailAddress());
		orderDetailEmailProcessModel.setUid(event.getUid());

		getModelService().save(orderDetailEmailProcessModel);
		getBusinessProcessService().startProcess(orderDetailEmailProcessModel);
	}

}
