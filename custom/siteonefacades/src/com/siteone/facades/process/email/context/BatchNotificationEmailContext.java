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

import com.siteone.core.model.BatchNotificationProcessModel;


/**
 * @author 1230514
 *
 */

public class BatchNotificationEmailContext extends AbstractEmailContext<BatchNotificationProcessModel>
{

	private static final Logger LOG = Logger.getLogger(BatchNotificationEmailContext.class);

	public static final String CRONJOB_PREVIEW = "cronjobPreview";
	public static final String CRONJOB_CODE = "cronjobCode";
	public static final String FILENAME = "fileName";
	public static final String CRONJOB_RESULT = "cronjobResult";
	public static final String EMAIL_SUBJECT = "subject";
	public static final String ENVIRONMENT = "environment";
	public static final String IMPEX_TRANSFORMER_LOG = "impexTransformerLog";

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getSite(de.hybris.platform.
	 * processengine.model.BusinessProcessModel)
	 */
	@Override
	protected BaseSiteModel getSite(final BatchNotificationProcessModel businessProcessModel)
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
	protected CustomerModel getCustomer(final BatchNotificationProcessModel businessProcessModel)
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
	protected LanguageModel getEmailLanguage(final BatchNotificationProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
	}

	@Override
	public void init(final BatchNotificationProcessModel businessProcessModel, final EmailPageModel emailPageModel)
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
				put(FILENAME, businessProcessModel.getFileName());
			}
			if (businessProcessModel.getImpexTransformerLog() != null)
			{
				put(IMPEX_TRANSFORMER_LOG, businessProcessModel.getImpexTransformerLog());
				put(EMAIL_SUBJECT, "Batch Failure." + " File:" + businessProcessModel.getFileName());
			}
			if (businessProcessModel.getImportCronjob() != null)
			{
				put(CRONJOB_PREVIEW,
						businessProcessModel.getImportCronjob().getUnresolvedDataStore() != null
								? businessProcessModel.getImportCronjob().getUnresolvedDataStore().getPreview()
								: null);
				put(CRONJOB_RESULT, businessProcessModel.getImportCronjob().getResult());
				put(CRONJOB_CODE, businessProcessModel.getImportCronjob().getCode());
				put(EMAIL_SUBJECT, "Batch Failure for Job:" + businessProcessModel.getImportCronjob().getCode() + " File:"
						+ businessProcessModel.getFileName());
			}
			final LanguageModel language = businessProcessModel.getLanguage();
			if (language != null)
			{
				put(EMAIL_LANGUAGE, language);
			}

		}

	}

}

