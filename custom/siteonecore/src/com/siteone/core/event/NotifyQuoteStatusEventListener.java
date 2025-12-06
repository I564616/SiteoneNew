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

import com.siteone.core.model.NotifyQuoteStatusProcessModel;


/**
 * @author PElango
 *
 */
public class NotifyQuoteStatusEventListener extends AbstractAcceleratorSiteEventListener<NotifyQuoteStatusEvent>
{
	private ModelService modelService;
	private BusinessProcessService businessProcessService;

	@Override
	protected SiteChannel getSiteChannelForEvent(final NotifyQuoteStatusEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}

	@Override
	protected void onSiteEvent(final NotifyQuoteStatusEvent event)
	{
		final NotifyQuoteStatusProcessModel requestProcessModel = (NotifyQuoteStatusProcessModel) getBusinessProcessService()
				.createProcess("notifyQuoteStatusEmailProcess-" + event.getQuoteNumber() + "-" + System.currentTimeMillis(),
						"notifyQuoteStatusEmailProcess");
		requestProcessModel.setSite(event.getSite());
		requestProcessModel.setLanguage(event.getLanguage());
		requestProcessModel.setQuoteNumber(event.getQuoteNumber());
		requestProcessModel.setAccountManagerName(event. getAccountManagerName());
		requestProcessModel.setAccountManagerMobile(event.getAccountManagerMobile());
		requestProcessModel.setAccountManagerMail(event.getAccountManagerMail());
		requestProcessModel.setJobName(event.getJobName());
		requestProcessModel.setDateSubmitted(event.getDateSubmitted());
		requestProcessModel.setExpDate(event.getExpDate());
		requestProcessModel.setToMails(event.getToMail());
		requestProcessModel.setMongoId(event.getMongoId());
		requestProcessModel.setAccountAdminEmail(event.getAccountAdminEmail());
		requestProcessModel.setCustomMessage(event.getCustomMessage());
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
