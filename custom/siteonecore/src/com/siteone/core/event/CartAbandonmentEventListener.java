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

import com.siteone.core.model.CartAbandonmentEmailProcessModel;



/**
 * @author 1219341
 *
 */
public class CartAbandonmentEventListener extends AbstractAcceleratorSiteEventListener<CartAbandonmentEvent>
{
	private ModelService modelService;
	private BusinessProcessService businessProcessService;

	@Override
	protected SiteChannel getSiteChannelForEvent(final CartAbandonmentEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.site", site);
		return site.getChannel();
	}


	@Override
	protected void onSiteEvent(final CartAbandonmentEvent event)
	{
		//YTODO Auto-generated method stub

		final CartAbandonmentEmailProcessModel cartAbandonmentEmailProcessModel = (CartAbandonmentEmailProcessModel) getBusinessProcessService()
				.createProcess("cartAbandonmentEmailProcess-" + event.getCart() + "-" + System.currentTimeMillis(),
						"cartAbandonmentEmailProcess");
		cartAbandonmentEmailProcessModel.setSite(event.getSite());
		cartAbandonmentEmailProcessModel.setCustomer(event.getCustomer());
		cartAbandonmentEmailProcessModel.setCart(event.getCart());
		cartAbandonmentEmailProcessModel.setLanguage(event.getLanguage());
		cartAbandonmentEmailProcessModel.setCurrency(event.getCurrency());
		cartAbandonmentEmailProcessModel.setStore(event.getBaseStore());

		getModelService().save(cartAbandonmentEmailProcessModel);
		getBusinessProcessService().startProcess(cartAbandonmentEmailProcessModel);



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
