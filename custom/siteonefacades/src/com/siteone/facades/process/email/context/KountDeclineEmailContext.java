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

import com.siteone.core.featureswitch.dao.impl.DefaultSiteOneFeatureSwitchDao;
import com.siteone.core.model.KountDeclineProcessModel;


/**
 * @author BS
 *
 */
public class KountDeclineEmailContext extends AbstractEmailContext<KountDeclineProcessModel>
{

	private static final Logger LOG = Logger.getLogger(KountDeclineEmailContext.class);

	public static final String EMAIL_SUBJECT = "subject";
	private static final String CUSTOMER_NAME = "customerName";
	private static final String ACCOUNT_NUMBER = "accountNumber";
	private static final String ACCOUNT_NAME = "accountName";
	private static final String CUSTOMER_EMAIL = "customerEmail";
	private static final String ORDER_NUMBER = "orderNumber";
	private static final String DISPLAY_MESSAGE = "Kount Decline Email";

	@Resource(name = "defaultSiteOneFeatureSwitchDao")
	DefaultSiteOneFeatureSwitchDao defaultSiteOneFeatureSwitchDao;

	@Override
	public void init(final KountDeclineProcessModel businessProcessModel, final EmailPageModel emailPageModel)
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
		final String emailRecipients = defaultSiteOneFeatureSwitchDao.getSwitchLongValue("KountDeclineMailAddress").replace(",",
				";");
		businessProcessModel.setToEmails(emailRecipients);
		put(EMAIL, emailRecipients);
		put(EMAIL_SUBJECT, DISPLAY_MESSAGE);
		put(DISPLAY_NAME, DISPLAY_MESSAGE);
		put(ACCOUNT_NAME, businessProcessModel.getAccountName());
		put(ACCOUNT_NUMBER, businessProcessModel.getAccountNumber());
		put(CUSTOMER_EMAIL, businessProcessModel.getCustomerEmailAddress());
		put(CUSTOMER_NAME, businessProcessModel.getCustomerName());
		put(ORDER_NUMBER, businessProcessModel.getOrderNumber());

	}

	@Override
	protected BaseSiteModel getSite(final KountDeclineProcessModel businessProcessModel)
	{
		return businessProcessModel.getSite();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getCustomer(de.hybris.platform.
	 * processengine.model.BusinessProcessModel)
	 */
	@Override
	protected CustomerModel getCustomer(final KountDeclineProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getEmailLanguage(de.hybris.
	 * platform.processengine.model.BusinessProcessModel)
	 */
	@Override
	protected LanguageModel getEmailLanguage(final KountDeclineProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
	}
}
