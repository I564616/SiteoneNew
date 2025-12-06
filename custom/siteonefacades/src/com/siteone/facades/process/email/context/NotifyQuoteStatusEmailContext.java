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

import com.siteone.core.model.NotifyQuoteStatusProcessModel;


/**
 * @author AA04994
 *
 */
public class NotifyQuoteStatusEmailContext extends AbstractEmailContext<NotifyQuoteStatusProcessModel>
{
	private static final Logger LOG = Logger.getLogger(NotifyQuoteStatusEmailContext.class);

	public static final String EMAIL_SUBJECT = "subject";
	private static final String QUOTE_NUMBER = "quoteNumber";
	private static final String MONGO_ID = "mongoId";
	private static final String ACCOUNTMANAGER_NAME = "accountManagerName";
	private static final String ACCOUNTMANAGER_MOBILE = "accountManagerMobile";
	private static final String ACCOUNTMANAGER_MAIL = "accountManagerMail";
	private static final String JOB_NAME = "jobName";
	private static final String EXP_DATE = "expDate";
	private static final String SUBMITTED_DATE = "submittedDate";
	private static final String ACCOUNT_ADMIN_EMAIL = "accountAdminEmail";
	private static final String CUSTOM_MESSAGE = "customMessage";
	private static final String COUNTRY_BASESITE_ID = "countryBaseSiteID";


	@Override
	public void init(final NotifyQuoteStatusProcessModel businessProcessModel, final EmailPageModel emailPageModel)
	{
		final BaseSiteModel baseSite = getSite(businessProcessModel);
		if (baseSite == null)
		{
			LOG.error("Failed to lookup Site for BusinessProcess [" + businessProcessModel + "]");
		}
		else
		{
			put(BASE_SITE, baseSite);
			// Lookup the site specific URLs
			put(BASE_URL, getSiteBaseUrlResolutionService().getWebsiteUrlForSite(baseSite, getUrlEncodingAttributes(), false, ""));
			put(BASE_THEME_URL, getSiteBaseUrlResolutionService().getWebsiteUrlForSite(baseSite, false, ""));
			put(SECURE_BASE_URL,
					getSiteBaseUrlResolutionService().getWebsiteUrlForSite(baseSite, getUrlEncodingAttributes(), true, ""));
			put(MEDIA_BASE_URL, getSiteBaseUrlResolutionService().getMediaUrlForSite(baseSite, false));
			put(MEDIA_SECURE_BASE_URL, getSiteBaseUrlResolutionService().getMediaUrlForSite(baseSite, true));

			put(THEME, baseSite.getTheme() != null ? baseSite.getTheme().getCode() : null);
		}

		put(FROM_EMAIL, emailPageModel.getFromEmail());
		put(FROM_DISPLAY_NAME, emailPageModel.getFromName());
		final LanguageModel language = businessProcessModel.getLanguage();
		LOG.info(businessProcessModel.getLanguage().getIsocode());
		if (language != null)
		{
			put(EMAIL_LANGUAGE, language);
		}
		put(EMAIL, businessProcessModel.getToMails());
		put(DISPLAY_NAME, Config.getString("siteone.support.email", null));
		put(QUOTE_NUMBER, businessProcessModel.getQuoteNumber());
		put(ACCOUNTMANAGER_NAME, businessProcessModel.getAccountManagerName());
		put(MONGO_ID, businessProcessModel.getMongoId());
		put(ACCOUNTMANAGER_MAIL, businessProcessModel.getAccountManagerMail());
		put(ACCOUNTMANAGER_MOBILE, businessProcessModel.getAccountManagerMobile());
		put(EXP_DATE, businessProcessModel.getExpDate());
		put(SUBMITTED_DATE, businessProcessModel.getDateSubmitted());
		put(JOB_NAME, businessProcessModel.getJobName());
		put(ACCOUNT_ADMIN_EMAIL, businessProcessModel.getAccountAdminEmail());
		put(CUSTOM_MESSAGE, businessProcessModel.getCustomMessage());
		put(COUNTRY_BASESITE_ID, baseSite.getUid());
	}

	@Override
	protected BaseSiteModel getSite(final NotifyQuoteStatusProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getSite();
	}


	@Override
	protected CustomerModel getCustomer(final NotifyQuoteStatusProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return null;
	}


	@Override
	protected LanguageModel getEmailLanguage(final NotifyQuoteStatusProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
	}
}
