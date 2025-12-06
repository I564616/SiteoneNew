/**
 *
 */
package com.siteone.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;

import org.apache.log4j.Logger;

import com.siteone.core.model.LinkToPayProcessModel;




/**
 * @author 1197861
 *
 */
public class LinkToPayEmailContext extends AbstractEmailContext<LinkToPayProcessModel>
{

	private static final Logger LOG = Logger.getLogger(LinkToPayEmailContext.class);

	public static final String EMAIL_SUBJECT = "subject";
	private static final String ORDER_AMOUNT = "orderAmount";
	private static final String ORDER_NUMBER = "orderNumber";
	private static final String DATE = "date";
	private static final String TIME = "time";






	@Override
	public void init(final LinkToPayProcessModel businessProcessModel, final EmailPageModel emailPageModel)
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
		
		put(EMAIL, businessProcessModel.getEmail());
		put(EMAIL_SUBJECT, "link to pay confirmation notification");
		put(DISPLAY_NAME, businessProcessModel.getEmail());
		put(DATE, businessProcessModel.getDate());
		put(TIME, businessProcessModel.getTime());
		put(ORDER_NUMBER, businessProcessModel.getOrderNumber());
		put(ORDER_AMOUNT, businessProcessModel.getOrderAmount());

	}


	@Override
	protected BaseSiteModel getSite(final LinkToPayProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getSite();
	}

	@Override
	protected LanguageModel getEmailLanguage(final LinkToPayProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
	}


	@Override
	protected CustomerModel getCustomer(LinkToPayProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return null;
	}
}