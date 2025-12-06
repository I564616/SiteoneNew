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

import com.siteone.core.model.InvoiceDetailsProcessModel;


/**
 * @author 1129929
 *
 */
public class InvoiceDetailsEventListener extends AbstractAcceleratorSiteEventListener<InvoiceDetailsEvent>
{

	private ModelService modelService;

	private BusinessProcessService businessProcessService;

	@Override
	protected SiteChannel getSiteChannelForEvent(final InvoiceDetailsEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}

	@Override
	protected void onSiteEvent(final InvoiceDetailsEvent event)
	{
		final InvoiceDetailsProcessModel invoiceDetailsProcessModel = (InvoiceDetailsProcessModel) getBusinessProcessService()
				.createProcess("invoiceDetailsEmailProcess-" + event.getInvoiceNumber() + "-" + System.currentTimeMillis(),
						"invoiceDetailsEmailProcess");
		invoiceDetailsProcessModel.setCurrency(event.getCurrency());
		invoiceDetailsProcessModel.setLanguage(event.getLanguage());
		invoiceDetailsProcessModel.setStore(event.getBaseStore());
		invoiceDetailsProcessModel.setSite(event.getSite());
		invoiceDetailsProcessModel.setEmailAddress(event.getEmailAddress());
		invoiceDetailsProcessModel.setUid(event.getUid());
		invoiceDetailsProcessModel.setInvoicNum(event.getInvoiceNumber());
		getModelService().save(invoiceDetailsProcessModel);
		getBusinessProcessService().startProcess(invoiceDetailsProcessModel);

	}

	public ModelService getModelService()
	{
		return modelService;
	}


	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	public BusinessProcessService getBusinessProcessService()
	{
		return businessProcessService;
	}

	public void setBusinessProcessService(final BusinessProcessService businessProcessService)
	{
		this.businessProcessService = businessProcessService;
	}

}
