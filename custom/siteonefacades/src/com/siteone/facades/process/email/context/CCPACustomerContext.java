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
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.Config;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;

import com.siteone.core.model.CCPACustomerProcessModel;


/**
 * @author BS
 *
 */
public class CCPACustomerContext extends AbstractEmailContext<CCPACustomerProcessModel>
{
	private static final Logger LOG = Logger.getLogger(CCPACustomerContext.class);
	public static final String EMAIL_SUBJECT = "subject";
	private static final String FIRST_NAME = "firstName";
	public static final String SUPPORT_EMAIL = "supportEmail";
	private static final String COPY_RIGHT_YEAR = "copyRightYear";
	private static final String COUNTRY_BASESITE_ID = "countryBaseSiteID";
	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	@Resource(name = "siteOneFacadeMessageSource")
	private MessageSource messageSource;

	@Resource(name = "modelService")
	private ModelService modelService;

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
	public void init(final CCPACustomerProcessModel businessProcessModel, final EmailPageModel emailPageModel)
	{
		final BaseSiteModel baseSite = getSite(businessProcessModel);
		final Map<String, String> bccEmails = new HashMap<>();
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
		put(EMAIL_SUBJECT, getMessageSource().getMessage("thanks.privacy.siteone", null,
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

		final String bccEmail = Config.getString("bcc.email.address", null);
		if (StringUtils.isNotEmpty(bccEmail))
		{
			final String[] bccEmailList = bccEmail.split(",");
			for (final String email : bccEmailList)
			{
				bccEmails.put(email, "EcommCustomerEmails@siteone.com");
			}
		}
		businessProcessModel.setBccEmails(bccEmails);

		modelService.save(businessProcessModel);
		modelService.refresh(businessProcessModel);
	}

	@Override
	protected BaseSiteModel getSite(final CCPACustomerProcessModel businessProcessModel)
	{
		return businessProcessModel.getSite();
	}


	@Override
	protected CustomerModel getCustomer(final CCPACustomerProcessModel businessProcessModel)
	{
		return businessProcessModel.getCustomer();
	}

	@Override
	protected LanguageModel getEmailLanguage(final CCPACustomerProcessModel businessProcessModel)
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

