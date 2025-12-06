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

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;

import com.siteone.core.model.ShareAssemblyProcessModel;


/**
 * @author 1003567
 *
 */
public class ShareAssemblyEmailContext extends AbstractEmailContext<ShareAssemblyProcessModel>
{
	private static final Logger LOG = Logger.getLogger(ShareListEmailContext.class);
	public static final String EMAIL_SUBJECT = "subject";
	private static final String USER_NAME = "username";
	private static final String BASE_URL = "baseurl";
	private static final String ASSEMBLY_NAME = "assemblyName";
	private static final String ASSEMBLY_CODE = "assemblyCode";

	@Resource(name = "siteOneFacadeMessageSource")
	private MessageSource messageSource;

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	@Override
	public void init(final ShareAssemblyProcessModel businessProcessModel, final EmailPageModel emailPageModel)
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
		final LanguageModel language = businessProcessModel.getLanguage();
		LOG.info(businessProcessModel.getLanguage().getIsocode());
		if (language != null)
		{
			put(EMAIL_LANGUAGE, language);
		}
		put(FROM_EMAIL, emailPageModel.getFromEmail());
		put(FROM_DISPLAY_NAME, emailPageModel.getFromName());
		put(USER_NAME, businessProcessModel.getUsername());
		put(ASSEMBLY_NAME, businessProcessModel.getAssemblyName());
		put(ASSEMBLY_CODE, businessProcessModel.getAssemblyCode());
		put(EMAIL, businessProcessModel.getEmailAddress());

		put(DISPLAY_NAME, businessProcessModel.getEmailAddress());
		put(EMAIL_SUBJECT, businessProcessModel.getUsername() + getMessageSource().getMessage("shared.assembly.siteone", null,
				getCommonI18NService().getLocaleForIsoCode(businessProcessModel.getLanguage().getIsocode())));


	}

	@Override
	protected BaseSiteModel getSite(final ShareAssemblyProcessModel businessProcessModel)
	{
		return businessProcessModel.getSite();
	}

	@Override
	protected CustomerModel getCustomer(final ShareAssemblyProcessModel businessProcessModel)
	{
		return businessProcessModel.getCustomer();
	}

	@Override
	protected LanguageModel getEmailLanguage(final ShareAssemblyProcessModel businessProcessModel)
	{
		return businessProcessModel.getLanguage();
	}



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
