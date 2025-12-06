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

import com.siteone.core.model.QuoteApprovalProcessModel;


/**
 * @author AA04994
 *
 */
public class QuoteApprovalEmailContext extends AbstractEmailContext<QuoteApprovalProcessModel>
{
	private static final Logger LOG = Logger.getLogger(QuoteApprovalEmailContext.class);

	public static final String EMAIL_SUBJECT = "subject";
	private static final String QUOTE_NUMBER = "quoteNumber";
	private static final String QUOTE_ID = "quoteId";
	private static final String ITEM_COUNT = "itemCount";
	private static final String QUOTE_DETAILS = "itemDetails";
	private static final String MODF_QUOTE_DETAILS = "modifiedItemDetails";
	private static final String CUSTOMER_NAME = "customerName";
	private static final String CUSTOMER_EMAIL_ADDRESS = "customerEmailAddress";
	public static final String ACCOUNT_ID = "accountId";
	private static final String PHONE_NUMBER = "phoneNumber";
	private static final String PO_NUMBER = "poNumber";
	private static final String ACCOUNT_NAME = "accountName";
	private static final String IS_FULL_QUOTES = "isFullQuotes";
	private static final String APPROVER_NAME = "approverName";
	private static final String APPROVER_EMAIL_ADDRESS = "approverEmailAddress";
	//private static final String APPROVER_EMAIL = "bcooper@siteone.com";
	private static final String APPROVER_EMAIL = "Customersupport@siteone.com";
	private static final String NOTES = "optionalNotes";
	private static final String COUNTRY_BASESITE_ID = "countryBaseSiteID";





	@Override
	public void init(final QuoteApprovalProcessModel businessProcessModel, final EmailPageModel emailPageModel)
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
		if (businessProcessModel.getAccountManagerEmail() != null || businessProcessModel.getInsideSalesRepEmail() != null || businessProcessModel.getBranchManagerEmail() != null
				|| businessProcessModel.getWriterEmail() != null || businessProcessModel.getPricerEmail() != null)
		{
			final String emailRecipients = businessProcessModel.getAccountManagerEmail() + ";" + businessProcessModel.getInsideSalesRepEmail() + ";" + businessProcessModel.getBranchManagerEmail() + ";" + businessProcessModel.getWriterEmail() + ";"
					+ businessProcessModel.getPricerEmail();
			businessProcessModel.setToEmails(emailRecipients);
			put(EMAIL, emailRecipients);
		}
		else
		{
			put(EMAIL, APPROVER_EMAIL);
		}
		if(businessProcessModel.getQuoteNumber()!=null)
		{
		put(EMAIL_SUBJECT, "Quote Approval: " + businessProcessModel.getAccountName()+" #"+businessProcessModel.getQuoteNumber());
		}
		else
		{
			put(EMAIL_SUBJECT, "Quote Approval: " + businessProcessModel.getAccountName());
		}
		put(DISPLAY_NAME, businessProcessModel.getCustomerEmailAddress());
		put(ACCOUNT_ID, businessProcessModel.getAccountId());
		put(PHONE_NUMBER, businessProcessModel.getPhoneNumber());
		put(PO_NUMBER, businessProcessModel.getPoNumber());
		put(ACCOUNT_NAME, businessProcessModel.getAccountName());
		put(QUOTE_NUMBER, businessProcessModel.getQuoteNumber());
		put(QUOTE_DETAILS, businessProcessModel.getItemDetails());
		put(MODF_QUOTE_DETAILS, businessProcessModel.getModifiedItemDetails());
		put(QUOTE_ID, businessProcessModel.getQuoteId());
		put(ITEM_COUNT, businessProcessModel.getItemCount());
		put(CUSTOMER_NAME, businessProcessModel.getCustomerName());
		put(CUSTOMER_EMAIL_ADDRESS, businessProcessModel.getCustomerEmailAddress());
		put(IS_FULL_QUOTES, businessProcessModel.getIsFullQuote());
		put(APPROVER_NAME, businessProcessModel.getApproverName());
		put(APPROVER_EMAIL_ADDRESS, businessProcessModel.getApproverEmailAddress());
		put(NOTES, businessProcessModel.getOptionalNotes());
		put(COUNTRY_BASESITE_ID, baseSite.getUid());

	}

	@Override
	protected BaseSiteModel getSite(final QuoteApprovalProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getSite();
	}


	@Override
	protected CustomerModel getCustomer(final QuoteApprovalProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return null;
	}


	@Override
	protected LanguageModel getEmailLanguage(final QuoteApprovalProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
	}
}
