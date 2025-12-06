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

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;

import com.siteone.core.featureswitch.dao.impl.DefaultSiteOneFeatureSwitchDao;
import com.siteone.core.model.PIMBatchFailureReportNotificationProcessModel;


/**
 * @author SR02012
 *
 */
public class PIMBatchFailureReportNotificationEmailContext
		extends AbstractEmailContext<PIMBatchFailureReportNotificationProcessModel>
{
	private static final Logger LOG = Logger.getLogger(PIMBatchFailureReportNotificationEmailContext.class);
	public static final String CRONJOB_PREVIEW = "cronjobPreview";
	public static final String CRONJOB_CODE = "cronjobCode";
	public static final String CRONJOB_RESULT = "cronjobResult";
	public static final String EMAIL_SUBJECT = "subject";
	public static final String ENVIRONMENT = "environment";
	public static final String IMPEX_TRANSFORMER_LOG = "impexTransformerLog";
	public static final String DISPLAY_MESSAGE = "PIM-Hybris product upload failures.";
			
	@Resource(name = "defaultSiteOneFeatureSwitchDao")
	DefaultSiteOneFeatureSwitchDao defaultSiteOneFeatureSwitchDao;
	
	@Override
	protected BaseSiteModel getSite(final PIMBatchFailureReportNotificationProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getSite();
	}

	@Override
	protected LanguageModel getEmailLanguage(final PIMBatchFailureReportNotificationProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
	}

	@Override
	protected CustomerModel getCustomer(final PIMBatchFailureReportNotificationProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(final PIMBatchFailureReportNotificationProcessModel businessProcessModel, final EmailPageModel emailPageModel)
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
		if (null != businessProcessModel )
		{
			   if(null != emailPageModel)
			   {
				put(ENVIRONMENT, Config.getString("website.siteone.https", Config.getString("website.siteone.http", "NA")));
				put(FROM_EMAIL, emailPageModel.getFromEmail());
				put(FROM_DISPLAY_NAME, emailPageModel.getFromName());
				final String emailRecipients = defaultSiteOneFeatureSwitchDao.getSwitchLongValue("PIMReportMailAddress").replace(",",";");
				businessProcessModel.setEmailReceiver(emailRecipients);
				put(EMAIL, emailRecipients);
				put(DISPLAY_NAME, DISPLAY_MESSAGE);
				put(EMAIL_SUBJECT, DISPLAY_MESSAGE);
				put(EMAIL_SUBJECT, Config.getString("cronjob.status.report.env", null) + DISPLAY_MESSAGE);
			   }

		if (null !=  businessProcessModel.getLanguage())
		{
		final LanguageModel language = businessProcessModel.getLanguage();
		if (language != null)
		{
			put(EMAIL_LANGUAGE, language);
		}
		}
		}
	}
}
