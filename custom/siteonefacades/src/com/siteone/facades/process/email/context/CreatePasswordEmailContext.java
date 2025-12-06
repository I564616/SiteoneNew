/**
 *
 */
package com.siteone.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.commerceservices.model.process.StoreFrontCustomerProcessModel;
import de.hybris.platform.util.Config;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;

import com.siteone.core.model.CreatePasswordEmailProcessModel;


/**
 * @author 1099417
 *
 */
public class CreatePasswordEmailContext extends CustomerEmailContext

{

	private int expiresInMinutes;
	private String token;
	private static final String COPY_RIGHT_YEAR = "copyRightYear";
	private static final String FIRST_NAME = "firstName";

	public int getExpiresInMinutes()
	{
		return expiresInMinutes;
	}

	public void setExpiresInMinutes(final int expiresInMinutes)
	{
		this.expiresInMinutes = expiresInMinutes;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(final String token)
	{
		this.token = token;
	}

	public String getURLEncodedToken() throws UnsupportedEncodingException
	{
		return URLEncoder.encode(token, "UTF-8");
	}

	public String getSecureResetPasswordUrl() throws UnsupportedEncodingException
	{
		return getSiteBaseUrlResolutionService().getWebsiteUrlForSite(getBaseSite(), getUrlEncodingAttributes(), true,
				"/login/pw/setPassword", "token=" + getURLEncodedToken());
	}

	public String getDisplaySecureResetPasswordUrl() throws UnsupportedEncodingException
	{
		return getSiteBaseUrlResolutionService().getWebsiteUrlForSite(getBaseSite(), getUrlEncodingAttributes(), true,
				"/my-account/update-password");
	}

	public static final String SUPPORT_EMAIL = "supportEmail";

	@Override
	public void init(final StoreFrontCustomerProcessModel storeFrontCustomerProcessModel, final EmailPageModel emailPageModel)
	{
		super.init(storeFrontCustomerProcessModel, emailPageModel);
		if (storeFrontCustomerProcessModel instanceof CreatePasswordEmailProcessModel)
		{
			setToken(((CreatePasswordEmailProcessModel) storeFrontCustomerProcessModel).getToken());
			final Long expirationTimeInSeconds = ((CreatePasswordEmailProcessModel) storeFrontCustomerProcessModel)
					.getExpirationTimeInSeconds();

			setExpiresInMinutes(expirationTimeInSeconds != null ? expirationTimeInSeconds.intValue() / 60 : 30);
		}
		put(SUPPORT_EMAIL, Config.getString("siteone.support.email", null));
		put(COPY_RIGHT_YEAR, "©" + Calendar.getInstance().get(Calendar.YEAR) + " ");
		put(DISPLAY_NAME, ((B2BCustomerModel) storeFrontCustomerProcessModel.getCustomer()).getFirstName());
		put(FIRST_NAME, ((B2BCustomerModel) storeFrontCustomerProcessModel.getCustomer()).getFirstName());
	}
}
