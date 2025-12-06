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

import com.siteone.core.model.NoVisibleUomProductProcessModel;




/**
 * @author 1197861
 *
 */
public class NoVisibleUomEmailContext extends AbstractEmailContext<NoVisibleUomProductProcessModel>
{
	public static final String EMAIL_SUBJECT = "subject";
	private static final Logger LOG = Logger.getLogger(NoVisibleUomEmailContext.class);
	public static final String ENVIRONMENT = "environment";

	@Override
	public void init(final NoVisibleUomProductProcessModel businessProcessModel, final EmailPageModel emailPageModel)
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
		if (businessProcessModel.getLanguage() != null)
		{
			put(EMAIL_LANGUAGE, businessProcessModel.getLanguage());
		}
		put(ENVIRONMENT, Config.getString("website.siteone.https", Config.getString("website.siteone.http", "NA")));
		put(EMAIL, Config.getString("email.nouom.recipients.to", null));
		put(EMAIL_SUBJECT, "No Visible UOM Products Email Notification");
		put(DISPLAY_NAME, Config.getString("email.nouom.recipients.to", null));

	}


	@Override
	protected BaseSiteModel getSite(final NoVisibleUomProductProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getSite();
	}

	@Override
	protected LanguageModel getEmailLanguage(final NoVisibleUomProductProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
	}


	@Override
	protected CustomerModel getCustomer(final NoVisibleUomProductProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return null;
	}
}