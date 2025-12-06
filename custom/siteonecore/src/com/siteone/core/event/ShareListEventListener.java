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

import com.siteone.core.model.ShareListProcessModel;


/**
 * @author 1003567
 *
 */
public class ShareListEventListener extends AbstractAcceleratorSiteEventListener<ShareListEvent>
{
	private ModelService modelService;
	private BusinessProcessService businessProcessService;

	@Override
	protected SiteChannel getSiteChannelForEvent(final ShareListEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}

	@Override
	protected void onSiteEvent(final ShareListEvent event)
	{

		final ShareListProcessModel shareListProcessModel = (ShareListProcessModel) getBusinessProcessService().createProcess(
				"shareListEmailProcess-" + event.getUserName() + "-" + System.currentTimeMillis(), "shareListEmailProcess");

		shareListProcessModel.setEmailAddress(event.getEmail());
		shareListProcessModel.setUsername(event.getUserName());
		shareListProcessModel.setListName(event.getListName());
		shareListProcessModel.setListCode(event.getListCode());
		shareListProcessModel.setLanguage(event.getLanguage());
		shareListProcessModel.setSite(event.getSite());
		shareListProcessModel.setCustomer(event.getCustomer());
		shareListProcessModel.setStore(event.getBaseStore());
		shareListProcessModel.setShowCustPrice(event.getShowCustPrice());
		shareListProcessModel.setShowRetailPrice(event.getShowRetailPrice());
		shareListProcessModel.setCustPriceList(event.getCustPriceList());
		shareListProcessModel.setRetailPriceList(event.getRetailPriceList());
		shareListProcessModel.setStoreId(event.getStoreId());
		shareListProcessModel.setCustomerName(event.getCustomerName());
		shareListProcessModel.setAccountNumber(event.getAccountNumber());
		shareListProcessModel.setSenderEmail(event.getSenderEmail());
		getModelService().save(shareListProcessModel);
		getBusinessProcessService().startProcess(shareListProcessModel);

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
