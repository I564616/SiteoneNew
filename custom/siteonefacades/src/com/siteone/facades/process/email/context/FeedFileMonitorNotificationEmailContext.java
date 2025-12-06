/**
 *
 */
package com.siteone.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.util.Config;

import org.apache.log4j.Logger;

import com.siteone.core.model.FeedFileMonitorNotificationProcessModel;


/**
 * @author rpalanisamy
 *
 */

public class FeedFileMonitorNotificationEmailContext extends AbstractEmailContext<FeedFileMonitorNotificationProcessModel>
{

	private static final Logger LOG = Logger.getLogger(FeedFileMonitorNotificationEmailContext.class);

	public static final String CRONJOB_PREVIEW = "cronjobPreview";
	public static final String CRONJOB_CODE = "cronjobCode";
	public static final String FILENAME_LIST = "fileNames";
	public static final String CRONJOB_RESULT = "cronjobResult";
	public static final String EMAIL_SUBJECT = "subject";
	public static final String ENVIRONMENT = "environment";
	public static final String IMPEX_TRANSFORMER_LOG = "impexTransformerLog";
	public static final String FILENAMES = "fileNames";

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getSite(de.hybris.platform.
	 * processengine.model.BusinessProcessModel)
	 */
	@Override
	protected BaseSiteModel getSite(final FeedFileMonitorNotificationProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getSite();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getCustomer(de.hybris.platform.
	 * processengine.model.BusinessProcessModel)
	 */
	@Override
	protected CustomerModel getCustomer(final FeedFileMonitorNotificationProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getEmailLanguage(de.hybris.
	 * platform.processengine.model.BusinessProcessModel)
	 */
	@Override
	protected LanguageModel getEmailLanguage(final FeedFileMonitorNotificationProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
	}

	@Override
	public void init(final FeedFileMonitorNotificationProcessModel businessProcessModel, final EmailPageModel emailPageModel)
	{
		final BaseSiteModel baseSite = getSite(businessProcessModel);
		if (baseSite == null)
		{
			LOG.error("Failed to lookup Site for BusinessProcess [" + businessProcessModel + "]");
		}
		else
		{
			put(BASE_SITE, baseSite);
			setUrlEncodingAttributes(getUrlEncoderService().getUrlEncodingPatternForEmail(businessProcessModel));
			// Lookup the site specific URLs
			put(BASE_URL, getSiteBaseUrlResolutionService().getWebsiteUrlForSite(baseSite, getUrlEncodingAttributes(), false, ""));
			put(BASE_THEME_URL, getSiteBaseUrlResolutionService().getWebsiteUrlForSite(baseSite, false, ""));
			put(SECURE_BASE_URL,
					getSiteBaseUrlResolutionService().getWebsiteUrlForSite(baseSite, getUrlEncodingAttributes(), true, ""));
			put(MEDIA_BASE_URL, getSiteBaseUrlResolutionService().getMediaUrlForSite(baseSite, false));
			put(MEDIA_SECURE_BASE_URL, getSiteBaseUrlResolutionService().getMediaUrlForSite(baseSite, true));

			put(THEME, baseSite.getTheme() != null ? baseSite.getTheme().getCode() : null);


		}
		if (null != businessProcessModel)
		{
			if (null != emailPageModel)
			{
				put(ENVIRONMENT, Config.getString("website.siteone.https", Config.getString("website.siteone.http", "NA")));
				put(FROM_EMAIL, emailPageModel.getFromEmail());
				put(FROM_DISPLAY_NAME, emailPageModel.getFromName());
				put(EMAIL, businessProcessModel.getEmailReceiver());
				put(DISPLAY_NAME, "Batch Notification Team");
				put(FILENAMES, businessProcessModel.getFeedFileList());
				put(EMAIL_SUBJECT, "Hybris Feed file missing notification");
				put(EMAIL_SUBJECT, Config.getString("cronjob.status.report.env", null) + "Siteone Feed Files Status Report");
			}

		}
		final LanguageModel language = businessProcessModel.getLanguage();
		if (language != null)
		{
			put(EMAIL_LANGUAGE, language);
		}
	}

}

