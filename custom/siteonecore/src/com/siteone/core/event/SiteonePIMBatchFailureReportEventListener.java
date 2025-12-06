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

import com.siteone.core.model.PIMBatchFailureReportNotificationProcessModel;


/**
 * @author SR02012
 *
 */
public class SiteonePIMBatchFailureReportEventListener
		extends AbstractAcceleratorSiteEventListener<SiteonePIMBatchFailureReportEvent>
{
	private ModelService modelService;
	private BusinessProcessService businessProcessService;
	private EmailService emailService;

	@Override
	protected SiteChannel getSiteChannelForEvent(final SiteonePIMBatchFailureReportEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}

	@Override
	protected void onSiteEvent(final SiteonePIMBatchFailureReportEvent event)
	{

		final PIMBatchFailureReportNotificationProcessModel pimBatchFailureReportNotificationProcessModel = (PIMBatchFailureReportNotificationProcessModel) getBusinessProcessService()
				.createProcess("pimBatchFailureReportNotificationEmailProcess-" + System.currentTimeMillis(),
						"pimBatchFailureReportNotificationEmailProcess");

		pimBatchFailureReportNotificationProcessModel.setSite(event.getSite());
		final EmailAttachmentModel emailAttachment = getEmailService().createEmailAttachment(event.getFailedBatchDataStream(),
				event.getFileName(), "text/csv");
		final List<EmailAttachmentModel> emailAttachments = new ArrayList<>();
		emailAttachments.add(emailAttachment);
		pimBatchFailureReportNotificationProcessModel.setAttachments(emailAttachments);
		pimBatchFailureReportNotificationProcessModel.setLanguage(event.getLanguage());
		getModelService().save(pimBatchFailureReportNotificationProcessModel);
		getBusinessProcessService().startProcess(pimBatchFailureReportNotificationProcessModel);

	}


	public void setBusinessProcessService(final BusinessProcessService businessProcessService)
	{
		this.businessProcessService = businessProcessService;
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

	public EmailService getEmailService()
	{
		return emailService;
	}

	public void setEmailService(final EmailService emailService)
	{
		this.emailService = emailService;
	}
}
