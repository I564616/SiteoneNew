/**
 *
 */
package com.siteone.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.util.Config;

import java.util.Calendar;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;

import com.siteone.core.model.ContactUsCustomerProcessModel;


/**
 * @author 1003567
 *
 */
public class ContactUsCustomerContext extends AbstractEmailContext<ContactUsCustomerProcessModel>
{
	private static final Logger LOG = Logger.getLogger(ContactUsCustomerContext.class);
	public static final String EMAIL_SUBJECT = "subject";
	private static final String FIRST_NAME = "firstName";
	public static final String SUPPORT_EMAIL = "supportEmail";
	private static final String COPY_RIGHT_YEAR = "copyRightYear";
	private static final String COUNTRY_BASESITE_ID = "countryBaseSiteID";
	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	@Resource(name = "siteOneFacadeMessageSource")
	private MessageSource messageSource;



	/**
	 * @return the messageSource
	 */
	public MessageSource getMessageSource()
	{
		return messageSource;
	}

	/**
	 * @param messageSource
	 *           the messageSource to set
	 */
	public void setMessageSource(final MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}

	@Override
	public void init(final ContactUsCustomerProcessModel businessProcessModel, final EmailPageModel emailPageModel)
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
		put(EMAIL_SUBJECT, getMessageSource().getMessage("thanks.contact.siteone", null,
				getCommonI18NService().getLocaleForIsoCode(businessProcessModel.getLanguage().getIsocode())));
		final LanguageModel language = businessProcessModel.getLanguage();
		LOG.info(businessProcessModel.getLanguage().getIsocode());
		if (language != null)
		{
			put(EMAIL_LANGUAGE, language);
		}
		put(TITLE, "Questions? Contact us.");
		put(FROM_EMAIL, emailPageModel.getFromEmail());
		put(DISPLAY_NAME, businessProcessModel.getEmailAddress());
		put(FROM_DISPLAY_NAME, emailPageModel.getFromName());
		put(EMAIL, businessProcessModel.getEmailAddress());
		put(FIRST_NAME, businessProcessModel.getFirstName());
		put(SUPPORT_EMAIL, Config.getString("siteone.info.email1", null));
		put(COPY_RIGHT_YEAR, "©" + Calendar.getInstance().get(Calendar.YEAR) + " ");
		put(COUNTRY_BASESITE_ID, baseSite.getUid());
	}

	@Override
	protected BaseSiteModel getSite(final ContactUsCustomerProcessModel businessProcessModel)
	{
		return businessProcessModel.getSite();
	}


	@Override
	protected CustomerModel getCustomer(final ContactUsCustomerProcessModel businessProcessModel)
	{
		return businessProcessModel.getCustomer();
	}

	@Override
	protected LanguageModel getEmailLanguage(final ContactUsCustomerProcessModel businessProcessModel)
	{
		return businessProcessModel.getLanguage();
	}

	/**
	 * @return the commonI18NService
	 */
	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	/**
	 * @param commonI18NService
	 *           the commonI18NService to set
	 */
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

}
