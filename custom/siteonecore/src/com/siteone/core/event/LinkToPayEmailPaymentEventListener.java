/**
 *
 */
package com.siteone.core.event;

/**
*
*/

import de.hybris.platform.acceleratorservices.site.AbstractAcceleratorSiteEventListener;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.enums.SiteChannel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;

import com.siteone.core.model.LinkToPayEmailPaymentProcessModel;


/**
 * @author SJ08640
 *
 */
public class LinkToPayEmailPaymentEventListener extends AbstractAcceleratorSiteEventListener<LinkToPayEmailPaymentEvent>
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
	protected SiteChannel getSiteChannelForEvent(final LinkToPayEmailPaymentEvent event)
	{
		final BaseSiteModel site = event.getProcess().getCayanResponseForm().getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}

	@Override
	protected void onSiteEvent(final LinkToPayEmailPaymentEvent event)
	{
		// YTODO Auto-generated method stub
		final LinkToPayEmailPaymentProcessModel linkToPayEmailPaymentProcessModel = (LinkToPayEmailPaymentProcessModel) getBusinessProcessService()
				.createProcess("LinkToPayEmailPaymentProcess-" + System.currentTimeMillis(), "linkToPayEmailPaymentProcess");
		linkToPayEmailPaymentProcessModel.setToEmails(event.getProcess().getCayanResponseForm().getToEmails());
		linkToPayEmailPaymentProcessModel.setDate(event.getProcess().getCayanResponseForm().getDate());
		linkToPayEmailPaymentProcessModel.setLast4Digits(event.getProcess().getCayanResponseForm().getLast4Digits());
		linkToPayEmailPaymentProcessModel.setAmountCharged(event.getProcess().getCayanResponseForm().getAmountCharged());
		linkToPayEmailPaymentProcessModel.setOrderNumber(event.getProcess().getCayanResponseForm().getOrderNumber());
		linkToPayEmailPaymentProcessModel.setCustomerName(event.getProcess().getCayanResponseForm().getCustomerName());
		linkToPayEmailPaymentProcessModel.setSite(event.getProcess().getCayanResponseForm().getSite());
		linkToPayEmailPaymentProcessModel.setLanguage(event.getProcess().getCayanResponseForm().getLanguage());
		linkToPayEmailPaymentProcessModel.setPoNumber(event.getProcess().getCayanResponseForm().getPoNumber());
		linkToPayEmailPaymentProcessModel.setAssociateEmail(event.getProcess().getCayanResponseForm().getEmail());

		getModelService().save(linkToPayEmailPaymentProcessModel);
		getBusinessProcessService().startProcess(linkToPayEmailPaymentProcessModel);


	}



}




