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

import com.siteone.core.model.ContactSellerProcessModel;


/**
 * @author AA04994
 *
 */
public class ContactSellerEventListener extends AbstractAcceleratorSiteEventListener<ContactSellerEvent>
{

	private ModelService modelService;
	private BusinessProcessService businessProcessService;

	@Override
	protected SiteChannel getSiteChannelForEvent(final ContactSellerEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}

	@Override
	protected void onSiteEvent(final ContactSellerEvent event)
	{
		final ContactSellerProcessModel contactSellerProcessModel = (ContactSellerProcessModel) getBusinessProcessService()
				.createProcess("contactSellerEmailProcess-" + event.getQuoteNumber() + "-" + System.currentTimeMillis(), "contactSellerEmailProcess");

		contactSellerProcessModel.setQuoteNumber(event.getQuoteNumber());
		contactSellerProcessModel.setQuoteComments(event.getQuoteComments());
		contactSellerProcessModel.setCustomerName(event.getCustomerName());
		contactSellerProcessModel.setApproverName(event.getApproverName());
		contactSellerProcessModel.setQuoteId(event.getQuoteId());
		contactSellerProcessModel.setCustomerEmailAddress(event.getCustomerEmailAddress());
		contactSellerProcessModel.setAccountManagerEmail(event.getAccountManagerEmail());
		contactSellerProcessModel.setInsideSalesRepEmail(event.getInsideSalesRepEmail());
		contactSellerProcessModel.setBranchManagerEmail(event.getBranchManagerEmail());
		contactSellerProcessModel.setWriterEmail(event.getWriterEmail());
		contactSellerProcessModel.setPricerEmail(event.getPricerEmail());
		contactSellerProcessModel.setAccountName(event.getAccountName());
		contactSellerProcessModel.setAccountId(event.getAccountId());
		contactSellerProcessModel.setPhoneNumber(event.getPhoneNumber());
		contactSellerProcessModel.setLanguage(event.getLanguage());
		contactSellerProcessModel.setSite(event.getSite());
		contactSellerProcessModel.setStore(event.getBaseStore());
		getModelService().save(contactSellerProcessModel);
		getBusinessProcessService().startProcess(contactSellerProcessModel);
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
