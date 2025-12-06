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

import com.siteone.core.model.SharedProductProcessModel;


/**
 * @author 1188173
 *
 */
public class SharedProductEventListener extends AbstractAcceleratorSiteEventListener<SharedProductEvent>
{

	private ModelService modelService;
	private BusinessProcessService businessProcessService;

	@Override
	protected SiteChannel getSiteChannelForEvent(final SharedProductEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}




	@Override
	protected void onSiteEvent(final SharedProductEvent event)
	{

		final SharedProductProcessModel sharedProductProcessModel = (SharedProductProcessModel) getBusinessProcessService()
				.createProcess("sharedProductEmailProcess-" + event.getUserName() + "-" + System.currentTimeMillis(),
						"sharedProductEmailProcess");

		sharedProductProcessModel.setEmailAddress(event.getEmail());
		sharedProductProcessModel.setUsername(event.getUserName());
		sharedProductProcessModel.setProduct(event.getProductModel());
		sharedProductProcessModel.setStockavailabilitymessage(event.getStockavailabilitymessage());
		sharedProductProcessModel.setLanguage(event.getLanguage());
		sharedProductProcessModel.setSite(event.getSite());
		sharedProductProcessModel.setStore(event.getBaseStore());
		getModelService().save(sharedProductProcessModel);
		getBusinessProcessService().startProcess(sharedProductProcessModel);

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
