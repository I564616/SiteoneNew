/**
 *
 */
package com.siteone.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;

import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.RequestQuoteProcessModel;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.order.data.SiteOneQuoteDetailResponseData;
import com.siteone.integration.services.ue.SiteOneQuotesWebService;


/**
 * @author AA04994
 *
 */
public class RequestQuoteEmailContext extends AbstractEmailContext<RequestQuoteProcessModel>
{
	private static final Logger LOG = Logger.getLogger(RequestQuoteEmailContext.class);
	
	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
	
	@Resource(name = "siteOneQuotesWebService")
	private SiteOneQuotesWebService siteOneQuotesWebService;
	
	private static final String BOOMI_PLATFORM = "boomiPlatformLinux";
	private static final String JOB_NAME = "jobName";
	private static final String JOB_START_DATE = "jobStartDate";
	private static final String BRANCH = "branch";
	private static final String JOB_DESCRIPTION = "jobDescription";
	private static final String ACCOUNT_NAME = "accountName";
	private static final String ACCOUNT_ID = "accountId";
	private static final String CUSTOMER_NAME = "customerName";
	private static final String CUSTOMER_EMAIL_ADDRESS = "customerEmailAddress";
	private static final String PHONE_NUMBER = "phoneNumber";
	private static final String COMMENT = "comment";
	private static final String QUOTE_HEADER_ID = "quoteHeaderId";
	private static final String ITEM_DETAILS = "itemDetails";
	private static final String CUSTOM_ITEM_DETAILS = "customItemDetails";
	private static final String EMAIL_SUBJECT = "subject";
	//private static final String APPROVER_EMAIL = "bcooper@siteone.com";
	private static final String APPROVER_EMAIL = "Customersupport@siteone.com";
	private static final String COUNTRY_BASESITE_ID = "countryBaseSiteID";






	@Override
	public void init(final RequestQuoteProcessModel businessProcessModel, final EmailPageModel emailPageModel)
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
		if (businessProcessModel.getAccountManagerEmail() != null || businessProcessModel.getInsideSalesRepEmail() != null || businessProcessModel.getBranchManagerEmail() != null)
		{
			final String emailRecipients = businessProcessModel.getAccountManagerEmail() + ";" + businessProcessModel.getInsideSalesRepEmail() + ";" + businessProcessModel.getBranchManagerEmail();
			businessProcessModel.setToEmails(emailRecipients);
			put(EMAIL, emailRecipients);
		}
		else
		{
			businessProcessModel.setToEmails(APPROVER_EMAIL);
			put(EMAIL, APPROVER_EMAIL);
		}
		if (businessProcessModel.getQuoteHeaderID() != null)
		{
		put(EMAIL_SUBJECT, "Online Quote Request - " + businessProcessModel.getQuoteHeaderID());
		}
		else
		{
		put(EMAIL_SUBJECT, "Online Quote Request");
		}
		put(DISPLAY_NAME, businessProcessModel.getCustomerEmailAddress());
		put(JOB_NAME, businessProcessModel.getJobName());
		put(JOB_START_DATE, businessProcessModel.getJobStartDate());
		put(BRANCH, businessProcessModel.getBranch());
		put(JOB_DESCRIPTION, businessProcessModel.getJobDescription());
		put(ACCOUNT_NAME, businessProcessModel.getAccountName());
		put(ACCOUNT_ID, businessProcessModel.getAccountId());
		put(CUSTOMER_NAME, businessProcessModel.getCustomerName());
		put(CUSTOMER_EMAIL_ADDRESS, businessProcessModel.getCustomerEmailAddress());
		put(PHONE_NUMBER, businessProcessModel.getPhoneNumber());
		put(COMMENT, businessProcessModel.getComment());
		put(QUOTE_HEADER_ID, businessProcessModel.getQuoteHeaderID());
		put(ITEM_DETAILS, businessProcessModel.getItemDetails());
		put(CUSTOM_ITEM_DETAILS, businessProcessModel.getCustomItemDetails());
		put(COUNTRY_BASESITE_ID, baseSite.getUid());

	}

	@Override
	protected BaseSiteModel getSite(final RequestQuoteProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getSite();
	}


	@Override
	protected CustomerModel getCustomer(final RequestQuoteProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return null;
	}


	@Override
	protected LanguageModel getEmailLanguage(final RequestQuoteProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
	}
}
