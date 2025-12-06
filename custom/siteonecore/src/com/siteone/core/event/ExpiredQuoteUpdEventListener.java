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

import com.siteone.core.model.ExpiredQuoteUpdProcessModel;

/**
 * 
 */
public class ExpiredQuoteUpdEventListener extends AbstractAcceleratorSiteEventListener<ExpiredQuoteUpdEvent>
{

	private ModelService modelService;
	private BusinessProcessService businessProcessService;

	@Override
	protected SiteChannel getSiteChannelForEvent(final ExpiredQuoteUpdEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}

	@Override
	protected void onSiteEvent(final ExpiredQuoteUpdEvent event)
	{
		final ExpiredQuoteUpdProcessModel expiredQuoteUpdProcessModel = (ExpiredQuoteUpdProcessModel) getBusinessProcessService()
				.createProcess("expiredQuoteUpdEmailProcess-" + event.getQuoteNumber() + "-" + System.currentTimeMillis(), "expiredQuoteUpdEmailProcess");

		expiredQuoteUpdProcessModel.setQuoteNumber(event.getQuoteNumber());
		expiredQuoteUpdProcessModel.setNotes(event.getNotes());
		expiredQuoteUpdProcessModel.setCustomerName(event.getCustomerName());
		expiredQuoteUpdProcessModel.setCustomerEmailAddress(event.getCustomerEmailAddress());
		expiredQuoteUpdProcessModel.setAccountId(event.getAccountId());
		expiredQuoteUpdProcessModel.setAccountName(event.getAccountName());
		expiredQuoteUpdProcessModel.setPhoneNumber(event.getPhoneNumber());
		expiredQuoteUpdProcessModel.setJobName(event.getJobName());
		expiredQuoteUpdProcessModel.setExpDate(event.getExpDate());
		expiredQuoteUpdProcessModel.setQuoteTotal(event.getQuoteTotal());
		expiredQuoteUpdProcessModel.setAccountManagerEmail(event.getAccountManagerEmail());
		expiredQuoteUpdProcessModel.setInsideSalesRepEmail(event.getInsideSalesRepEmail());
		expiredQuoteUpdProcessModel.setBranchManagerEmail(event.getBranchManagerEmail());
		expiredQuoteUpdProcessModel.setWriterEmail(event.getWriterEmail());
		expiredQuoteUpdProcessModel.setPricerEmail(event.getPricerEmail());
		expiredQuoteUpdProcessModel.setLanguage(event.getLanguage());
		expiredQuoteUpdProcessModel.setSite(event.getSite());
		expiredQuoteUpdProcessModel.setStore(event.getBaseStore());
		getModelService().save(expiredQuoteUpdProcessModel);
		getBusinessProcessService().startProcess(expiredQuoteUpdProcessModel);
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