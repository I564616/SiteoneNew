package com.siteone.core.event;

import de.hybris.platform.acceleratorservices.site.AbstractAcceleratorSiteEventListener;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.enums.SiteChannel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;

import com.siteone.core.model.EWalletNotificationEmailProcessModel;


/**
 * @author pelango
 *
 */
public class EwalletNotificationEventListener extends AbstractAcceleratorSiteEventListener<EwalletNotificationEvent>
{

	private ModelService modelService;
	private BusinessProcessService businessProcessService;

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

	@Override
	protected SiteChannel getSiteChannelForEvent(final EwalletNotificationEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}

	@Override
	protected void onSiteEvent(final EwalletNotificationEvent event)
	{
		final EWalletNotificationEmailProcessModel requestProcessModel = (EWalletNotificationEmailProcessModel) getBusinessProcessService()
				.createProcess("eWalletNotificationEmailProcess-" + event.getFirstName() + "-" + System.currentTimeMillis(),
						"eWalletNotificationEmailProcess");
		requestProcessModel.setSite(event.getSite());
		requestProcessModel.setLanguage(event.getLanguage());
		requestProcessModel.setFirstName(event.getFirstName());
		requestProcessModel.setCustomerEmail(event.getEmail());
		requestProcessModel.setOperationType(event.getOperationType());
		requestProcessModel.setCardNumber(event.getCardNumber());
		requestProcessModel.setCardType(event.getCardType());
		requestProcessModel.setCcEmails(event.getCcMail());
		requestProcessModel.setNickName(event.getNickName());
		getModelService().save(requestProcessModel);
		getBusinessProcessService().startProcess(requestProcessModel);

	}

}
