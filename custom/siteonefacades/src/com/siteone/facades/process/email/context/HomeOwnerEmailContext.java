/**
 *
 */
package com.siteone.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.util.Config;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;

import com.siteone.core.model.HomeOwnerProcessModel;


public class HomeOwnerEmailContext extends AbstractEmailContext<HomeOwnerProcessModel>
{
	private Converter<UserModel, CustomerData> customerConverter;
	private static final Logger LOG = Logger.getLogger(HomeOwnerEmailContext.class);
	public static final String EMAIL_SUBJECT = "subject";


	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";
	private static final String PHONE = "phone";
	private static final String EMAIL_ADDRESS = "emailAddress";
	private static final String ADDRESS = "address";
	private static final String CUSTOMER_CITY = "customerCity";
	private static final String CUSTOMER_STATE = "customerState";
	private static final String CUSTOMER_ZIPCODE = "customerZipCode";
	private static final String BEST_TIME_TO_CALL = "bestTimeToCall";
	private static final String SERVICE_TYPE = "serviceType";
	private static final String REFERRAL_NO = "referalsNo";
	private static final String LOOKING_FOR_SERVICE = "lookingFor";
	private static final String LOOKING_FOR_OTHERS = "lookingForOthers";
	private static final String COUNTRY_BASESITE_ID = "countryBaseSiteID";

	@Resource(name = "siteOneFacadeMessageSource")
	private MessageSource messageSource;

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;


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

	@Override
	public void init(final HomeOwnerProcessModel businessProcessModel, final EmailPageModel emailPageModel)
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
			put(COUNTRY_BASESITE_ID, baseSite.getUid());
		}

		put(FROM_EMAIL, emailPageModel.getFromEmail());
		//put(FROM_EMAIL, emailPageModel.getFromEmail());
		put(EMAIL, Config.getString("siteone.info.email", null));
		//put(EMAIL, Config.getString("siteone.support.email", null));
		put(FROM_DISPLAY_NAME, emailPageModel.getFromName());
		final LanguageModel language = businessProcessModel.getLanguage();
		LOG.info(businessProcessModel.getLanguage().getIsocode());
		if (language != null)
		{
			put(EMAIL_LANGUAGE, language);
		}
		put(FIRST_NAME, businessProcessModel.getFirstName());
		put(LAST_NAME, businessProcessModel.getLastName());
		put(EMAIL_ADDRESS, businessProcessModel.getEmailAddr());
		put(PHONE, businessProcessModel.getPhone());
		put(ADDRESS, businessProcessModel.getAddress());

		put(CUSTOMER_CITY, businessProcessModel.getCustomerCity());
		put(CUSTOMER_STATE, businessProcessModel.getCustomerState());
		put(CUSTOMER_ZIPCODE, businessProcessModel.getCustomerZipCode());

		put(BEST_TIME_TO_CALL, businessProcessModel.getBestTimeToCall());
		put(SERVICE_TYPE, businessProcessModel.getServiceType());
		put(REFERRAL_NO, businessProcessModel.getReferalsNo());
		put(LOOKING_FOR_SERVICE, businessProcessModel.getLookingFor());
		put(LOOKING_FOR_OTHERS, businessProcessModel.getLookingForOthers());

		put(DISPLAY_NAME, Config.getString("siteone.info.email", null));
		put(EMAIL_SUBJECT, getMessageSource().getMessage("homeowner.subject", null,
				getCommonI18NService().getLocaleForIsoCode(businessProcessModel.getLanguage().getIsocode())));


	}

	@Override
	protected BaseSiteModel getSite(final HomeOwnerProcessModel businessProcessModel)
	{
		return businessProcessModel.getSite();
	}

	@Override
	protected CustomerModel getCustomer(final HomeOwnerProcessModel businessProcessModel)
	{
		return null;
	}


	@Override
	protected LanguageModel getEmailLanguage(final HomeOwnerProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
	}

	protected Converter<UserModel, CustomerData> getCustomerConverter()
	{
		return customerConverter;
	}

	public void setCustomerConverter(final Converter<UserModel, CustomerData> customerConverter)
	{
		this.customerConverter = customerConverter;
	}

}
