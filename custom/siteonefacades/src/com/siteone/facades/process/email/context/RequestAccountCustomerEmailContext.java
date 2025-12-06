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

import java.util.Calendar;

import org.apache.log4j.Logger;

import com.siteone.core.model.RequestAccountProcessModel;


/**
 * @author 965504
 *
 */
public class RequestAccountCustomerEmailContext extends AbstractEmailContext<RequestAccountProcessModel>
{
	private static final Logger LOG = Logger.getLogger(RequestAccountCustomerEmailContext.class);
	public static final String EMAIL_SUBJECT = "subject";
	private static final String FIRST_NAME = "firstName";
	public static final String CUSTOMER_EMAIL = "customeremail";
	public static final String CC_EMAIL = "ccEmail";
	public static final String CC_DISPLAY_NAME = "ccDisplayName";
	private static final String COPY_RIGHT_YEAR = "copyRightYear";
	private static final String SUPPORT_EMAIL = "supportEmail";
	private static final String IS_EMAIL_UNIQUE = "isEmailUniqueInHybris";
	private static final String COUNTRY_BASESITE_ID = "countryBaseSiteID";

	@Override
	public void init(final RequestAccountProcessModel businessProcessModel, final EmailPageModel emailPageModel)
	{
		//final Map<String, String> ccEmails = new HashMap<>();

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
			put(COUNTRY_BASESITE_ID, baseSite.getUid());
		}
		final LanguageModel language = businessProcessModel.getLanguage();
		LOG.info(businessProcessModel.getLanguage().getIsocode());
		if (language != null)
		{
			put(EMAIL_LANGUAGE, language);
		}
		put(FROM_EMAIL, emailPageModel.getFromEmail());
		put(FROM_DISPLAY_NAME, emailPageModel.getFromName());

		put(FIRST_NAME, businessProcessModel.getFirstName());

		put(EMAIL, businessProcessModel.getEmailAddress());
		put(DISPLAY_NAME, businessProcessModel.getEmailAddress());

		//ccEmails.put(Config.getString("siteone.info.email", null), "CustomerSupport");
		//businessProcessModel.setCcEmails(ccEmails);

		put(COPY_RIGHT_YEAR, "©" + Calendar.getInstance().get(Calendar.YEAR) + " ");
		put(SUPPORT_EMAIL, Config.getString("siteone.info.email1", null));
		put(IS_EMAIL_UNIQUE, businessProcessModel.getIsEmailUniqueInHybris());
	}

	@Override
	protected BaseSiteModel getSite(final RequestAccountProcessModel businessProcessModel)
	{
		return businessProcessModel.getSite();
	}

	@Override
	protected CustomerModel getCustomer(final RequestAccountProcessModel businessProcessModel)
	{
		return null;
	}

	@Override
	protected LanguageModel getEmailLanguage(final RequestAccountProcessModel businessProcessModel)
	{
		return businessProcessModel.getLanguage();
	}

}
