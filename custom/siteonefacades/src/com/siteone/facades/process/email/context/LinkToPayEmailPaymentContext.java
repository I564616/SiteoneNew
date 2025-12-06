/**
 *
 */
package com.siteone.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import java.util.HashMap;
import java.util.Map;
import org.apache.velocity.tools.generic.NumberTool;
import org.apache.log4j.Logger;
import com.siteone.core.model.LinkToPayEmailPaymentProcessModel;
import de.hybris.platform.servicelayer.model.ModelService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
/**
 * @author SJ08640
 *  
 */
public class LinkToPayEmailPaymentContext extends AbstractEmailContext<LinkToPayEmailPaymentProcessModel>
{

	private static final Logger LOG = Logger.getLogger(LinkToPayEmailPaymentContext.class);

	public static final String EMAIL_SUBJECT = "subject";
	private static final String ORDER_AMOUNT = "amountCharged";
	private static final String ORDER_NUMBER = "orderNumber";
	private static final String DATE = "date";
	private static final String LASTFOURDIGITS = "last4Digits";
	private static final String CUSTOMER_NAME = "customerName";
	private static final String PO_NUMBER = "poNumber";
	private static final String TOOL = "tool";


	
	@Resource(name = "modelService")
	private ModelService modelService;

	@Override
	public void init(final LinkToPayEmailPaymentProcessModel businessProcessModel, final EmailPageModel emailPageModel)
	{	final Map<String, String> bccEmails = new HashMap<>();
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
		put(EMAIL_SUBJECT, "SiteOne Payment Confirmation - Order #" + businessProcessModel.getOrderNumber());
		put(DISPLAY_NAME, businessProcessModel.getToEmails());
		put(DATE, businessProcessModel.getDate());
		put(LASTFOURDIGITS, businessProcessModel.getLast4Digits());
		put(ORDER_NUMBER, businessProcessModel.getOrderNumber());
		put(ORDER_AMOUNT, businessProcessModel.getAmountCharged());
		put(CUSTOMER_NAME, businessProcessModel.getCustomerName());
		put(PO_NUMBER, businessProcessModel.getPoNumber());
		put(TOOL, new NumberTool());
		
		if (StringUtils.isNotBlank(businessProcessModel.getAssociateEmail()))
		{
		
			  final String bccEmail = businessProcessModel.getAssociateEmail();
				bccEmails.put(bccEmail, "EcommCustomerEmails@siteone.com");
			
		}
		businessProcessModel.setBccEmails(bccEmails);
		LOG.error("Emails"+businessProcessModel.getBccEmails());
		
		modelService.save(businessProcessModel);
		modelService.refresh(businessProcessModel);
		
		
	}


	@Override
	protected BaseSiteModel getSite(final LinkToPayEmailPaymentProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getSite();
	}

	@Override
	protected LanguageModel getEmailLanguage(final LinkToPayEmailPaymentProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
	}


	@Override
	protected CustomerModel getCustomer(final LinkToPayEmailPaymentProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return null;
	}
}