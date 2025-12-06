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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.siteone.core.model.ContactUsProcessModel;


/**
 * @author 1003567
 *
 */
public class ContactUsContext extends AbstractEmailContext<ContactUsProcessModel>
{
	private static final Logger LOG = Logger.getLogger(ContactUsContext.class);
	public static final String EMAIL_SUBJECT = "subject";
	private static final String REASON_FOR_CONTACT = "reasonForContact";
	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";
	private static final String CONTACT_NUMBER = "contactNumber";
	private static final String CUSTOMER_EMAIL = "customerEmail";
	private static final String MESSAGE = "message";
	private static final String CUSTOMER_NUMBER = "customerNumber";
	private static final String TYPE_OF_CUSTOMER = "typeOfCustomer";
	private static final String COPY_RIGHT_YEAR = "copyRightYear";
	private static final String SUPPORT_EMAIL = "supportEmail";
	private static final String HARDSCAPES_EMAIL = "customersupport@siteone.com";
	private static final String CONTACT_HARDSCAPES = "Hardscapes";
	private static final String CONTACT_DRAINAGE_SUPPORT = "Request design support for Drainage";
	private static final String CONTACT_DRAINAGE_CALLBACK = "Request a call back from SiteOne for Drainage";
	private static final String CONTACT_LIGHTING_CALLBACK = "Request a call back from SiteOne for Lighting";
	private static final String CONTACT_LIGHTING_DEMO = "Request a demo for Lighting";
	private static final String CONTACT_LIGHTING_SUPPORT = "Request design support for Lighting";
	private static final String CONTACT_LIGHTING_OUTDOOR = "Outdoor Lighting";
	private static final String CUSTOMER_CITY = "customerCity";
	private static final String CUSTOMER_STATE = "customerState";
	private static final String CUSTOMER_ZIPCODE = "projectZipcode";
	private static final String PROJECTSTARTDATE = "projectStartDate";
	private static final String PHASEOF = "inPhaseOf";
	private static final String MYPROJECT = "myProject";
	private static final String MYBUDGET = "myBudget";
	private static final String IDENTITY = "identity";
	private static final String IDENTITY_NAME = "identityName";
	private static final String STREET_ADDRESS = "streetAddress";
	private static final String COUNTRY_BASESITE_ID = "countryBaseSiteID";



	@Override
	public void init(final ContactUsProcessModel businessProcessModel, final EmailPageModel emailPageModel)
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
		put(EMAIL_SUBJECT, businessProcessModel.getReasonForContact());
		final LanguageModel language = businessProcessModel.getLanguage();
		LOG.info(businessProcessModel.getLanguage().getIsocode());
		if (language != null)
		{
			put(EMAIL_LANGUAGE, language);
		}
		put(TITLE, "Questions? Contact us.");
		put(DISPLAY_NAME, Config.getString("siteone.support.email", null));
		put(FROM_DISPLAY_NAME, emailPageModel.getFromName());
		put(REASON_FOR_CONTACT, businessProcessModel.getReasonForContact());
		put(CUSTOMER_CITY, businessProcessModel.getCustomerCity());
		put(CUSTOMER_STATE, businessProcessModel.getCustomerState());
		put(CUSTOMER_ZIPCODE, businessProcessModel.getProjectZipcode());
		if (StringUtils.isNotEmpty(businessProcessModel.getStreetAddress()))
		{
			put(STREET_ADDRESS, businessProcessModel.getStreetAddress());
		}
		if (businessProcessModel.getReasonForContact().equalsIgnoreCase(CONTACT_HARDSCAPES))
		{
			put(EMAIL, HARDSCAPES_EMAIL);
			getProjectDetails(businessProcessModel);
		}
		else if ((businessProcessModel.getReasonForContact().equalsIgnoreCase(CONTACT_LIGHTING_CALLBACK))
				|| (businessProcessModel.getReasonForContact().equalsIgnoreCase(CONTACT_LIGHTING_DEMO))
				|| (businessProcessModel.getReasonForContact().equalsIgnoreCase(CONTACT_LIGHTING_SUPPORT))
				|| (businessProcessModel.getReasonForContact().equalsIgnoreCase(CONTACT_LIGHTING_OUTDOOR)))
		{
			put(EMAIL, Config.getString("lighting.support.email", null));
			getProjectDetails(businessProcessModel);
		}
		else if ((businessProcessModel.getReasonForContact().equalsIgnoreCase(CONTACT_DRAINAGE_CALLBACK))
				|| (businessProcessModel.getReasonForContact().equalsIgnoreCase(CONTACT_DRAINAGE_SUPPORT)))
		{
			put(EMAIL, Config.getString("siteone.drainage.support.email", null));
			getProjectDetails(businessProcessModel);
		}
		else
		{

			put(EMAIL, Config.getString("siteone.support.email", null));
		}
		put(FROM_EMAIL, emailPageModel.getFromEmail());
		put(FIRST_NAME, businessProcessModel.getFirstName());
		put(LAST_NAME, businessProcessModel.getLastName());
		put(CONTACT_NUMBER, businessProcessModel.getContactNumber());
		put(CUSTOMER_EMAIL, businessProcessModel.getCustomerEmail());
		put(MESSAGE, businessProcessModel.getMessageforContactUS());
		put(CUSTOMER_NUMBER, businessProcessModel.getCustomerNumber());
		put(TYPE_OF_CUSTOMER, businessProcessModel.getTypeOfCustomer());
		put(COPY_RIGHT_YEAR, "©" + Calendar.getInstance().get(Calendar.YEAR) + " ");
		put(SUPPORT_EMAIL, Config.getString("siteone.support.email", null));
		put(COUNTRY_BASESITE_ID, baseSite.getUid());

	}

	private final void getProjectDetails(final ContactUsProcessModel businessProcessModel)
	{
		put(PROJECTSTARTDATE, businessProcessModel.getProjectStartDate());
		put(MYPROJECT, businessProcessModel.getMyProject());
		put(MYBUDGET, businessProcessModel.getMyBudget());
		put(PHASEOF, businessProcessModel.getInPhaseOf());
		put(IDENTITY, businessProcessModel.getIdentity());
		put(IDENTITY_NAME, businessProcessModel.getIdentityName());
	}

	@Override
	protected BaseSiteModel getSite(final ContactUsProcessModel businessProcessModel)
	{
		return businessProcessModel.getSite();
	}


	@Override
	protected CustomerModel getCustomer(final ContactUsProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return null;
	}


	@Override
	protected LanguageModel getEmailLanguage(final ContactUsProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
	}

}