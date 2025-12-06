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

import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;

import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.OrderTrackingLinkEmailProcessModel;


/**
 * @author 1099417
 *
 */
public class OrderTrackingLinkEventListener extends AbstractAcceleratorSiteEventListener<OrderTrackingLinkEvent>
{
	private ModelService modelService;
	private BusinessProcessService businessProcessService;
	private static final String DC_SHIPPING_FEE_BRANCHES = "DCShippingFeeBranches";

	private static final Logger LOG = Logger.getLogger(OrderTrackingLinkEventListener.class);


	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
	
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
	protected SiteChannel getSiteChannelForEvent(final OrderTrackingLinkEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		LOG.error("Order tracking test getSiteChannelForEvent method ");
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
	protected void onSiteEvent(final OrderTrackingLinkEvent event)
	{
		// YTODO Auto-generated method stub
		final OrderTrackingLinkEmailProcessModel orderTrackingLinkEmailProcessModel = (OrderTrackingLinkEmailProcessModel) getBusinessProcessService()
				.createProcess("orderTrackingLinkEmailProcess-" + event.getOrder() + "-" + System.currentTimeMillis(),
						"orderTrackingLinkEmailProcess");
		orderTrackingLinkEmailProcessModel.setSite(event.getSite());
		orderTrackingLinkEmailProcessModel.setCustomer(event.getCustomer());
		orderTrackingLinkEmailProcessModel.setOrder(event.getOrder());
		orderTrackingLinkEmailProcessModel.setConsignment(event.getConsignment());
		orderTrackingLinkEmailProcessModel.setLanguage(event.getLanguage());
		orderTrackingLinkEmailProcessModel.setCurrency(event.getCurrency());
		orderTrackingLinkEmailProcessModel.setStore(event.getBaseStore());
		final Map<String, String> shippingFee = siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping(DC_SHIPPING_FEE_BRANCHES);
		if (event.getOrder()!=null && event.getOrder().getPointOfService()!=null && event.getOrder().getPointOfService().getStoreId() != null
				&& shippingFee.containsKey(event.getOrder().getPointOfService().getStoreId()) )
		{
			orderTrackingLinkEmailProcessModel.setIsShippingFeeBranch(Boolean.TRUE);
		}
		LOG.error("Order tracking test onSiteEvent method - " + orderTrackingLinkEmailProcessModel.getCustomer());
		getModelService().save(orderTrackingLinkEmailProcessModel);
		getBusinessProcessService().startProcess(orderTrackingLinkEmailProcessModel);
	}
}
