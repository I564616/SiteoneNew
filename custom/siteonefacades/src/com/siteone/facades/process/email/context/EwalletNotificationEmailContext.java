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

import java.util.Calendar;

import org.apache.log4j.Logger;

import com.siteone.core.model.EWalletNotificationEmailProcessModel;


/**
 * @author pelango
 *
 */
public class EwalletNotificationEmailContext extends AbstractEmailContext<EWalletNotificationEmailProcessModel>
{

	private Converter<UserModel, CustomerData> customerConverter;
	private CustomerData customerData;

	private static final Logger LOG = Logger.getLogger(EwalletNotificationEmailContext.class);
	public static final String EMAIL_SUBJECT = "subject";
	private static final String REASON_FOR_CONTACT = "reasonForContact";
	private static final String FIRST_NAME = "firstName";
	private static final String CUSTOMER_EMAIL = "customerEmail";
	private static final String COPY_RIGHT_YEAR = "copyRightYear";
	private static final String SUPPORT_EMAIL = "supportEmail";
	private static final String OPERATION = "operation";
	private static final String CARD_NUMBER = "cardNumber";
	private static final String CARD_TYPE = "cardType";
	private static final String NICKNAME = "nickName";


	@Override
	public void init(final EWalletNotificationEmailProcessModel eWalletNotificationProcessModel,
			final EmailPageModel emailPageModel)
	{
		final BaseSiteModel baseSite = getSite(eWalletNotificationProcessModel);
		if (baseSite == null)
		{
			LOG.error("Failed to lookup Site for BusinessProcess [" + eWalletNotificationProcessModel + "]");
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
		put(EMAIL_SUBJECT, "Important: A new credit card has been added to your eWallet.");
		final LanguageModel language = eWalletNotificationProcessModel.getLanguage();
		LOG.info(eWalletNotificationProcessModel.getLanguage().getIsocode());
		if (language != null)
		{
			put(EMAIL_LANGUAGE, language);
		}
		put(FROM_EMAIL, emailPageModel.getFromEmail());
		put(FROM_DISPLAY_NAME, emailPageModel.getFromName());
		put(DISPLAY_NAME, Config.getString("siteone.support.email", null));
		put(REASON_FOR_CONTACT, "EWallet Notification");
		put(EMAIL, eWalletNotificationProcessModel.getCustomerEmail());
		put(FIRST_NAME, eWalletNotificationProcessModel.getFirstName());
		put(COPY_RIGHT_YEAR, "©" + Calendar.getInstance().get(Calendar.YEAR) + " ");
		put(SUPPORT_EMAIL, Config.getString("siteone.support.email", null));
		put(OPERATION, eWalletNotificationProcessModel.getOperationType());
		put(CARD_NUMBER, "XX"+eWalletNotificationProcessModel.getCardNumber());
		put(CARD_TYPE, eWalletNotificationProcessModel.getCardType());
		put(NICKNAME, eWalletNotificationProcessModel.getNickName());
	}

	@Override
	protected BaseSiteModel getSite(final EWalletNotificationEmailProcessModel eWalletNotificationProcessModel)
	{
		return eWalletNotificationProcessModel.getSite();
	}

	@Override
	protected CustomerModel getCustomer(final EWalletNotificationEmailProcessModel eWalletNotificationProcessModel)
	{
		return eWalletNotificationProcessModel.getCustomer();
	}

	protected Converter<UserModel, CustomerData> getCustomerConverter()
	{
		return customerConverter;
	}

	public void setCustomerConverter(final Converter<UserModel, CustomerData> customerConverter)
	{
		this.customerConverter = customerConverter;
	}

	public CustomerData getCustomer()
	{
		return customerData;
	}

	@Override
	protected LanguageModel getEmailLanguage(final EWalletNotificationEmailProcessModel eWalletNotificationProcessModel)
	{
		return eWalletNotificationProcessModel.getLanguage();
	}
}