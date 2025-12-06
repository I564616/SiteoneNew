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

import com.siteone.core.model.ShareCartEmailProcessModel;


/**
 * @author 1219341
 *
 */
public class ShareCartEventListener extends AbstractAcceleratorSiteEventListener<ShareCartEvent>
{
	private ModelService modelService;
	private BusinessProcessService businessProcessService;

	@Override
	protected SiteChannel getSiteChannelForEvent(final ShareCartEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.site", site);
		return site.getChannel();
	}

	@Override
	protected void onSiteEvent(final ShareCartEvent event)
	{
		//YTODO Auto-generated method stub

		final ShareCartEmailProcessModel shareCartEmailProcessModel = (ShareCartEmailProcessModel) getBusinessProcessService()
				.createProcess("shareCartEmailProcess-" + event.getCart() + "-" + System.currentTimeMillis(),
						"shareCartEmailProcess");
		shareCartEmailProcessModel.setSite(event.getSite());
		shareCartEmailProcessModel.setEmailAddress(event.getEmailAddress());
		shareCartEmailProcessModel.setCart(event.getCart());
		shareCartEmailProcessModel.setLanguage(event.getLanguage());
		shareCartEmailProcessModel.setCurrency(event.getCurrency());
		shareCartEmailProcessModel.setStore(event.getBaseStore());
		shareCartEmailProcessModel.setAccountName(event.getAccountName());
		shareCartEmailProcessModel.setAccountNumber(event.getAccountNumber());
		shareCartEmailProcessModel.setCustomerName(event.getCustomerName());
		shareCartEmailProcessModel.setCustomerEmail(event.getCustomerEmail());
		shareCartEmailProcessModel.setCustomer(event.getCustomer());
		getModelService().save(shareCartEmailProcessModel);
		getBusinessProcessService().startProcess(shareCartEmailProcessModel);



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
