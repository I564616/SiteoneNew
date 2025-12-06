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

import com.siteone.core.model.RequestQuoteProcessModel;


/**
 * @author AA04994
 *
 */
public class RequestQuoteEventListener extends AbstractAcceleratorSiteEventListener<RequestQuoteEvent>
{

	private ModelService modelService;
	private BusinessProcessService businessProcessService;

	@Override
	protected SiteChannel getSiteChannelForEvent(final RequestQuoteEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}

	@Override
	protected void onSiteEvent(final RequestQuoteEvent event)
	{
		final RequestQuoteProcessModel requestQuoteProcessModel = (RequestQuoteProcessModel) getBusinessProcessService()
				.createProcess("requestQuoteEmailProcess-" + event.getQuoteHeaderID() + "-" + System.currentTimeMillis(), "requestQuoteEmailProcess");

		requestQuoteProcessModel.setJobName(event.getJobName());
		requestQuoteProcessModel.setJobDescription(event.getJobDescription());
		requestQuoteProcessModel.setJobStartDate(event.getJobStartDate());
		requestQuoteProcessModel.setBranch(event.getBranch());
		requestQuoteProcessModel.setAccountName(event.getAccountName());
		requestQuoteProcessModel.setAccountId(event.getAccountId());
		requestQuoteProcessModel.setCustomerName(event.getCustomerName());
		requestQuoteProcessModel.setCustomerEmailAddress(event.getCustomerEmailAddress());
		requestQuoteProcessModel.setPhoneNumber(event.getPhoneNumber());
		requestQuoteProcessModel.setQuoteHeaderID(event.getQuoteHeaderID());
		requestQuoteProcessModel.setComment(event.getComment());
		requestQuoteProcessModel.setAccountManagerEmail(event.getAccountManagerEmail());
		requestQuoteProcessModel.setInsideSalesRepEmail(event.getInsideSalesRepEmail());
		requestQuoteProcessModel.setBranchManagerEmail(event.getBranchManagerEmail());
		requestQuoteProcessModel.setItemDetails(event.getItemDetails());
		requestQuoteProcessModel.setCustomItemDetails(event.getCustomItemDetails());
		requestQuoteProcessModel.setLanguage(event.getLanguage());
		requestQuoteProcessModel.setSite(event.getSite());
		requestQuoteProcessModel.setStore(event.getBaseStore());
		getModelService().save(requestQuoteProcessModel);
		getBusinessProcessService().startProcess(requestQuoteProcessModel);
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
