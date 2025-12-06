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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.siteone.core.model.QuoteApprovalProcessModel;
import com.siteone.core.model.QuoteItemDetailsModel;


/**
 * @author AA04994
 *
 */
public class QuoteApprovalEventListener extends AbstractAcceleratorSiteEventListener<QuoteApprovalEvent>
{

	private ModelService modelService;
	private BusinessProcessService businessProcessService;

	@Override
	protected SiteChannel getSiteChannelForEvent(final QuoteApprovalEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}

	@Override
	protected void onSiteEvent(final QuoteApprovalEvent event)
	{
		final QuoteApprovalProcessModel quoteApprovalProcessModel = (QuoteApprovalProcessModel) getBusinessProcessService()
				.createProcess("quoteApprovalEmailProcess-" + event.getQuoteNumber() + "-" + System.currentTimeMillis(), "quoteApprovalEmailProcess");

		quoteApprovalProcessModel.setQuoteNumber(event.getQuoteNumber());
		quoteApprovalProcessModel.setItemCount(event.getItemCount());
		quoteApprovalProcessModel.setItemDetails(event.getItemDetails());
		quoteApprovalProcessModel.setModifiedItemDetails(event.getModifiedItemDetails());
		quoteApprovalProcessModel.setAccountName(event.getAccountName());
		quoteApprovalProcessModel.setCustomerName(event.getCustomerName());
		quoteApprovalProcessModel.setCustomerEmailAddress(event.getCustomerEmailAddress());
		quoteApprovalProcessModel.setApproverName(event.getApproverName());
		quoteApprovalProcessModel.setApproverEmailAddress(event.getApproverEmailAddress());
		quoteApprovalProcessModel.setAccountManagerEmail(event.getAccountManagerEmail());
		quoteApprovalProcessModel.setInsideSalesRepEmail(event.getInsideSalesRepEmail());
		quoteApprovalProcessModel.setBranchManagerEmail(event.getBranchManagerEmail());
		quoteApprovalProcessModel.setWriterEmail(event.getWriterEmail());
		quoteApprovalProcessModel.setPricerEmail(event.getPricerEmail());
		quoteApprovalProcessModel.setIsFullQuote(event.getIsFullQuote());
		quoteApprovalProcessModel.setLanguage(event.getLanguage());
		quoteApprovalProcessModel.setSite(event.getSite());
		quoteApprovalProcessModel.setStore(event.getBaseStore());
		quoteApprovalProcessModel.setQuoteId(event.getQuoteId());
		quoteApprovalProcessModel.setAccountId(event.getAccountId());
		quoteApprovalProcessModel.setPhoneNumber(event.getPhoneNumber());
		quoteApprovalProcessModel.setPoNumber(event.getPoNumber());
		quoteApprovalProcessModel.setOptionalNotes(event.getOptionalNotes());
		getModelService().save(quoteApprovalProcessModel);
		getBusinessProcessService().startProcess(quoteApprovalProcessModel);
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
