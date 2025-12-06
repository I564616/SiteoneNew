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

import com.siteone.core.model.QuoteToOrderStatusProcessModel;


/**
 *
 */
public class QuoteToOrderStatusEventListener extends AbstractAcceleratorSiteEventListener<QuoteToOrderStatusEvent>
{
	private ModelService modelService;
	private BusinessProcessService businessProcessService;

	@Override
	protected SiteChannel getSiteChannelForEvent(final QuoteToOrderStatusEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}

	@Override
	protected void onSiteEvent(final QuoteToOrderStatusEvent event)
	{
		final QuoteToOrderStatusProcessModel requestProcessModel = (QuoteToOrderStatusProcessModel) getBusinessProcessService()
				.createProcess("quoteToOrderStatusEmailProcess-" + event.getQuoteNumber() + "-" + System.currentTimeMillis(),
						"quoteToOrderStatusEmailProcess");

		requestProcessModel.setSite(event.getSite());
		requestProcessModel.setLanguage(event.getLanguage());
		requestProcessModel.setOrderNumber(event.getOrderNumber());
		requestProcessModel.setPoNumber(event.getPoNumber());
		requestProcessModel.setJobName(event.getJobName());
		requestProcessModel.setQuoteNumber(event.getQuoteNumber());
		requestProcessModel.setQuoteWriter(event.getQuoteWriter());
		requestProcessModel.setToMails(event.getToMail());
		requestProcessModel.setAccountManager(event.getAccountManager());
		requestProcessModel.setAccountManagerPhone(event.getAccountManagerPhone());
		requestProcessModel.setAccountManagerEmail(event.getAccountManagerEmail());
		requestProcessModel.setAccountAdminEmail(event.getAccountAdminEmail());
		requestProcessModel.setOrderDate(event.getOrderDate());
		requestProcessModel.setAccountNumber(event.getAccountNumber());
		requestProcessModel.setFirstName(event.getFirstName());
		requestProcessModel.setStore(event.getBaseStore());
		requestProcessModel.setOrderId(event.getOrderId());
		requestProcessModel.setConsignmentId(event.getConsignmentId());
		requestProcessModel.setStatus(event.getStatus());
		requestProcessModel.setIsQuoteToOrderEmailsent(true);
		requestProcessModel.setToMails(event.getToMail());

		getModelService().save(requestProcessModel);
		getBusinessProcessService().startProcess(requestProcessModel);
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


