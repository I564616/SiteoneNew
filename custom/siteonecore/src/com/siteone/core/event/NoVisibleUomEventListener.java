/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.acceleratorservices.email.EmailService;
import de.hybris.platform.acceleratorservices.model.email.EmailAttachmentModel;
import de.hybris.platform.acceleratorservices.site.AbstractAcceleratorSiteEventListener;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.enums.SiteChannel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;

import java.util.ArrayList;
import java.util.List;

import com.siteone.core.model.NoVisibleUomProductProcessModel;


/**
 * @author HR03708
 *
 */
public class NoVisibleUomEventListener extends AbstractAcceleratorSiteEventListener<NoVisibleUomEvent>
{

	private ModelService modelService;
	private BusinessProcessService businessProcessService;
	private EmailService emailService;


	/**
	 * @return the emailService
	 */
	public EmailService getEmailService()
	{
		return emailService;
	}

	/**
	 * @param emailService
	 *           the emailService to set
	 */
	public void setEmailService(final EmailService emailService)
	{
		this.emailService = emailService;
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

	@Override
	protected SiteChannel getSiteChannelForEvent(final NoVisibleUomEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}

	@Override
	protected void onSiteEvent(final NoVisibleUomEvent event)
	{
		// YTODO Auto-generated method stub
		final NoVisibleUomProductProcessModel noVisibleUomProductProcessModel = (NoVisibleUomProductProcessModel) getBusinessProcessService()
				.createProcess("noVisibleUomProductProcess-" + System.currentTimeMillis(), "noVisibleUomProductEmailProcess");

		final EmailAttachmentModel emailAttachment = getEmailService().createEmailAttachment(event.getNoVisibleUomDataStream(),
				event.getFileName(), "text/csv");
		final List<EmailAttachmentModel> emailAttachments = new ArrayList<>();
		emailAttachments.add(emailAttachment);
		noVisibleUomProductProcessModel.setSite(event.getSite());
		noVisibleUomProductProcessModel.setLanguage(event.getLanguage());
		noVisibleUomProductProcessModel.setAttachments(emailAttachments);
		getModelService().save(noVisibleUomProductProcessModel);
		getBusinessProcessService().startProcess(noVisibleUomProductProcessModel);


	}

}
