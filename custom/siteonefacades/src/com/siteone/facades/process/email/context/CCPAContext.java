/**
 *
 */
package com.siteone.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.Config;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.siteone.core.model.CCPAProcessModel;


/**
 * @author BS
 *
 */
public class CCPAContext extends AbstractEmailContext<CCPAProcessModel>
{
	private static final Logger LOG = Logger.getLogger(ContactUsContext.class);
	public static final String EMAIL_SUBJECT = "subject";
	private static final String PRIVACY_REQUEST_TYPE = "privacyRequestType";
	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";
	private static final String CONTACT_NUMBER = "phoneNumber";
	private static final String CUSTOMER_EMAIL = "emailAddress";
	private static final String COMPANY_NAME = "companyName";
	private static final String ZIPCODE = "zipcode";
	private static final String ACCOUNT_NUMBER = "accountNumber";
	private static final String ADDRERSS_LINE1 = "addressLine1";
	private static final String ADDRERSS_LINE2 = "addressLine2";
	private static final String COPY_RIGHT_YEAR = "copyRightYear";
	private static final String SUPPORT_EMAIL = "supportEmail";
	private static final String CUSTOMER_CITY = "city";
	private static final String CUSTOMER_STATE = "state";
	private static final String COUNTRY_BASESITE_ID = "countryBaseSiteID";

	@Resource(name = "modelService")
	private ModelService modelService;

	@Override
	public void init(final CCPAProcessModel businessProcessModel, final EmailPageModel emailPageModel)
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
		put(EMAIL_SUBJECT, businessProcessModel.getPrivacyRequestType());
		final LanguageModel language = businessProcessModel.getLanguage();
		LOG.info(businessProcessModel.getLanguage().getIsocode());
		if (language != null)
		{
			put(EMAIL_LANGUAGE, language);
		}
		put(TITLE, "Questions? Contact us.");
		put(DISPLAY_NAME, Config.getString("siteone.support.email", null));
		put(FROM_DISPLAY_NAME, emailPageModel.getFromName());
		put(PRIVACY_REQUEST_TYPE, businessProcessModel.getPrivacyRequestType());
		put(EMAIL, Config.getString("siteone.support.email", null));
		put(FROM_EMAIL, emailPageModel.getFromEmail());
		put(FIRST_NAME, businessProcessModel.getFirstName());
		put(LAST_NAME, businessProcessModel.getLastName());
		put(CONTACT_NUMBER, businessProcessModel.getPhoneNumber());
		put(CUSTOMER_EMAIL, businessProcessModel.getEmailAddress());
		put(CUSTOMER_CITY, businessProcessModel.getCity());
		put(CUSTOMER_STATE, businessProcessModel.getCustomerState());
		put(ACCOUNT_NUMBER, businessProcessModel.getAccountNumber());
		put(ADDRERSS_LINE1, businessProcessModel.getAddressLine1());
		put(ADDRERSS_LINE2, businessProcessModel.getAddressLine2());
		put(COMPANY_NAME, businessProcessModel.getCompanyName());
		put(ZIPCODE, businessProcessModel.getZipcode());
		put(COPY_RIGHT_YEAR, "©" + Calendar.getInstance().get(Calendar.YEAR) + " ");
		put(SUPPORT_EMAIL, Config.getString("siteone.support.email", null));

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
	protected BaseSiteModel getSite(final CCPAProcessModel businessProcessModel)
	{
		return businessProcessModel.getSite();
	}


	@Override
	protected CustomerModel getCustomer(final CCPAProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return null;
	}


	@Override
	protected LanguageModel getEmailLanguage(final CCPAProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
	}

}
