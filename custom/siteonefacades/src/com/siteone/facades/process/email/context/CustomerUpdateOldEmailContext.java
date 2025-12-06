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
import de.hybris.platform.util.Config;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

import com.siteone.core.model.CustomerUpdateOldEmailProcessModel;



/**
 * @author ASaha
 *
 */
public class CustomerUpdateOldEmailContext extends AbstractEmailContext<CustomerUpdateOldEmailProcessModel>
{
	private static final Logger LOG = Logger.getLogger(CustomerUpdateOldEmailContext.class);
	public static final String EMAIL_SUBJECT = "subject";
	private Converter<UserModel, CustomerData> customerConverter;
	private String token;
	private int expiresInMinutes;

	private static final String OLD_EMAIL_ID = "oldEmailId";
	private static final String NEW_EMAIL_ID = "newEmailId";


	private static final String FIRST_NAME = "firstName";
	private static final String SUPPORT_EMAIL = "supportEmail";
	private static final String COUNTRY_BASESITE_ID = "countryBaseSiteID";


	@Override
	public void init(final CustomerUpdateOldEmailProcessModel businessProcessModel, final EmailPageModel emailPageModel)
	{
		super.init(businessProcessModel, emailPageModel);
		setToken(businessProcessModel.getToken());
		final Long expirationTimeInSeconds = businessProcessModel.getExpirationTimeInSeconds();

		setExpiresInMinutes(expirationTimeInSeconds != null ? expirationTimeInSeconds.intValue() / 60 : 30);


		LOG.info("CustomerUpdateOldEmailContext");

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

		put(EMAIL, businessProcessModel.getOldEmail());
		//put(EMAIL, Config.getString("siteone.support.email", null));
		put(FROM_DISPLAY_NAME, emailPageModel.getFromName());

		put(DISPLAY_NAME, Config.getString("siteone.info.email", null));
		put(EMAIL_SUBJECT, "Email Change in UE");

		put(OLD_EMAIL_ID, businessProcessModel.getOldEmail());
		put(NEW_EMAIL_ID, businessProcessModel.getNewEmail());
		put(FIRST_NAME, businessProcessModel.getFirstName());
		put(SUPPORT_EMAIL, Config.getString("siteone.support.email", null));
	}

	public String getURLEncodedToken() throws UnsupportedEncodingException
	{
		return URLEncoder.encode(token, "UTF-8");
	}

	public String getRequestResetPasswordUrl() throws UnsupportedEncodingException
	{
		return getSiteBaseUrlResolutionService().getWebsiteUrlForSite(getBaseSite(), getUrlEncodingAttributes(), false,
				"/login/pw/request/external");
	}

	public String getSecureRequestResetPasswordUrl() throws UnsupportedEncodingException
	{
		return getSiteBaseUrlResolutionService().getWebsiteUrlForSite(getBaseSite(), getUrlEncodingAttributes(), true,
				"/login/pw/request/external");
	}

	public String getResetPasswordUrl() throws UnsupportedEncodingException
	{
		return getSiteBaseUrlResolutionService().getWebsiteUrlForSite(getBaseSite(), getUrlEncodingAttributes(), false,
				"/login/pw/change", "token=" + getURLEncodedToken());
	}

	public String getSecureResetPasswordUrl() throws UnsupportedEncodingException
	{
		return getSiteBaseUrlResolutionService().getWebsiteUrlForSite(getBaseSite(), getUrlEncodingAttributes(), true,
				"/login/pw/change", "token=" + getURLEncodedToken());
	}

	public String getDisplayResetPasswordUrl() throws UnsupportedEncodingException
	{
		return getSiteBaseUrlResolutionService().getWebsiteUrlForSite(getBaseSite(), getUrlEncodingAttributes(), false,
				"/my-account/update-password");
	}

	public String getDisplaySecureResetPasswordUrl() throws UnsupportedEncodingException
	{
		return getSiteBaseUrlResolutionService().getWebsiteUrlForSite(getBaseSite(), getUrlEncodingAttributes(), true,
				"/my-account/update-password");
	}

	@Override
	protected BaseSiteModel getSite(final CustomerUpdateOldEmailProcessModel businessProcessModel)
	{
		return businessProcessModel.getSite();
	}

	@Override
	protected CustomerModel getCustomer(final CustomerUpdateOldEmailProcessModel businessProcessModel)
	{
		return null;
	}


	@Override
	protected LanguageModel getEmailLanguage(final CustomerUpdateOldEmailProcessModel businessProcessModel)
	{
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

	/**
	 * @return the token
	 */
	public String getToken()
	{
		return token;
	}

	/**
	 * @param token
	 *           the token to set
	 */
	public void setToken(final String token)
	{
		this.token = token;
	}

	/**
	 * @return the expiresInMinutes
	 */
	public int getExpiresInMinutes()
	{
		return expiresInMinutes;
	}

	/**
	 * @param expiresInMinutes
	 *           the expiresInMinutes to set
	 */
	public void setExpiresInMinutes(final int expiresInMinutes)
	{
		this.expiresInMinutes = expiresInMinutes;
	}



}