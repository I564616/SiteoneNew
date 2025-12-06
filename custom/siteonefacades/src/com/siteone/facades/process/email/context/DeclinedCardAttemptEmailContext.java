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

import java.util.HashMap;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;

import com.siteone.core.model.DeclinedCardAttemptEmailProcessModel;


/**
 * @author PP10513
 *
 */
public class DeclinedCardAttemptEmailContext extends AbstractEmailContext<DeclinedCardAttemptEmailProcessModel>
{

	private static final Logger LOG = Logger.getLogger(DeclinedCardAttemptEmailContext.class);

	public static final String EMAIL_SUBJECT = "subject";
	private static final String ORDER_AMOUNT = "orderAmount";
	private static final String ORDER_NUMBER = "orderNumber";
	private static final String DATE = "date";
	private static final String CUSTOMER_NAME = "customerName";




	@Resource(name = "modelService")
	private ModelService modelService;

	@Override
	public void init(final DeclinedCardAttemptEmailProcessModel businessProcessModel, final EmailPageModel emailPageModel)
	{
		final Map<String, String> ccEmails = new HashMap<>();
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

		put(EMAIL, businessProcessModel.getToEmails());
		put(DISPLAY_NAME, businessProcessModel.getToEmails());
		put(EMAIL_SUBJECT, "SiteOne Credit Card Decline - Order #" + businessProcessModel.getOrderNumber());
		put(DATE, businessProcessModel.getDate());
		put(ORDER_NUMBER, businessProcessModel.getOrderNumber());
		put(ORDER_AMOUNT, businessProcessModel.getOrderAmount());
		put(CUSTOMER_NAME, businessProcessModel.getCustomerName());

		modelService.save(businessProcessModel);
		modelService.refresh(businessProcessModel);

	}


	@Override
	protected BaseSiteModel getSite(final DeclinedCardAttemptEmailProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getSite();
	}

	@Override
	protected LanguageModel getEmailLanguage(final DeclinedCardAttemptEmailProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
	}


	@Override
	protected CustomerModel getCustomer(final DeclinedCardAttemptEmailProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return null;
	}
}