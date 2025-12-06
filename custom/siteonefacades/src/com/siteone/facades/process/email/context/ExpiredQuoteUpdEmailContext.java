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

import com.siteone.core.model.ExpiredQuoteUpdProcessModel;


/**
 *
 */
public class ExpiredQuoteUpdEmailContext extends AbstractEmailContext<ExpiredQuoteUpdProcessModel>
{
	private static final Logger LOG = Logger.getLogger(ExpiredQuoteUpdEmailContext.class);

	public static final String EMAIL_SUBJECT = "subject";
	private static final String QUOTE_NUMBER = "quoteNumber";
	private static final String NOTES = "notes";
	private static final String CUSTOMER_NAME = "customerName";
	private static final String CUSTOMER_EMAIL_ADDRESS = "customerEmailAddress";
	private static final String ACCOUNT_NAME = "accountName";
	private static final String ACCOUNT_ID = "accountId";
	private static final String PHONE_NUMBER = "phoneNumber";
	private static final String JOB_NAME = "jobName";
	private static final String EXP_DATE = "expDate";
	private static final String QUOTE_TOTAL = "quoteTotal";
	private static final String APPROVER_EMAIL = "Customersupport@siteone.com";
	private static final String COUNTRY_BASESITE_ID = "countryBaseSiteID";





	@Override
	public void init(final ExpiredQuoteUpdProcessModel businessProcessModel, final EmailPageModel emailPageModel)
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
		if (businessProcessModel.getAccountManagerEmail() != null || businessProcessModel.getInsideSalesRepEmail() != null
				|| businessProcessModel.getBranchManagerEmail() != null || businessProcessModel.getWriterEmail() != null
				|| businessProcessModel.getPricerEmail() != null)
		{
			final String emailRecipients = businessProcessModel.getAccountManagerEmail() + ";"
					+ businessProcessModel.getInsideSalesRepEmail() + ";" + businessProcessModel.getBranchManagerEmail() + ";"
					+ businessProcessModel.getWriterEmail() + ";" + businessProcessModel.getPricerEmail();
			businessProcessModel.setToEmails(emailRecipients);
			put(EMAIL, emailRecipients);
		}
		else
		{
			put(EMAIL, APPROVER_EMAIL);
		}
		if (businessProcessModel.getQuoteNumber() != null)
		{
			put(EMAIL_SUBJECT,
					"Expired Quote Update: " + businessProcessModel.getAccountName() + " #" + businessProcessModel.getQuoteNumber());
		}
		else
		{
			put(EMAIL_SUBJECT, "Expired Quote Update: " + businessProcessModel.getAccountName());
		}
		put(QUOTE_NUMBER, businessProcessModel.getQuoteNumber());
		put(NOTES, businessProcessModel.getNotes());
		put(CUSTOMER_NAME, businessProcessModel.getCustomerName());
		put(CUSTOMER_EMAIL_ADDRESS, businessProcessModel.getCustomerEmailAddress());
		put(ACCOUNT_NAME, businessProcessModel.getAccountName());
		put(ACCOUNT_ID, businessProcessModel.getAccountId());
		put(PHONE_NUMBER, businessProcessModel.getPhoneNumber());
		put(JOB_NAME, businessProcessModel.getJobName());
		put(EXP_DATE, businessProcessModel.getExpDate());
		put(QUOTE_TOTAL, businessProcessModel.getQuoteTotal());
		put(DISPLAY_NAME, businessProcessModel.getCustomerName());
		put(COUNTRY_BASESITE_ID, baseSite.getUid());

	}

	@Override
	protected BaseSiteModel getSite(final ExpiredQuoteUpdProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getSite();
	}


	@Override
	protected CustomerModel getCustomer(final ExpiredQuoteUpdProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return null;
	}


	@Override
	protected LanguageModel getEmailLanguage(final ExpiredQuoteUpdProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
	}
}
