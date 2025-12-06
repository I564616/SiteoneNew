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

import org.apache.log4j.Logger;

import com.siteone.core.model.ListEditEmailProcessModel;


/**
 * @author 1003567
 *
 */
public class ListEditEmailContext extends AbstractEmailContext<ListEditEmailProcessModel>
{
	private static final Logger LOG = Logger.getLogger(ListEditEmailContext.class);
	public static final String EMAIL_SUBJECT = "subject";
	public static final String LIST_NAME = "listName";
	public static final String UPDATE_LIST_NAME = "updateListName";
	public static final String LIST_CODE = "listCode";
	public static final String TOWN = "town";
	public static final String CONTACT_NUMBER = "contactNumber";
	public static final String SUPPORT_EMAIL = "supportEmail";
	public static final String STORE_ID = "storeId";
	public static final String CUSTOMER_NAME = "customerName";


	@Override
	public void init(final ListEditEmailProcessModel businessProcessModel, final EmailPageModel emailPageModel)
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

		/*
		 * PointOfServiceData sessionPos = ((SiteOneCustomerFacade) customerFacade).getPreferredStore(); if (sessionPos ==
		 * null) { sessionPos = storeSessionFacade.getSessionStore(); }
		 */
		final LanguageModel language = businessProcessModel.getLanguage();
		LOG.info(businessProcessModel.getLanguage().getIsocode());
		if (language != null)
		{
			put(EMAIL_LANGUAGE, language);
		}
		put(FROM_EMAIL, emailPageModel.getFromEmail());
		put(FROM_DISPLAY_NAME, emailPageModel.getFromName());
		put(TOWN, businessProcessModel.getStoreCity());
		put(STORE_ID, businessProcessModel.getStoreId());
		put(CONTACT_NUMBER, businessProcessModel.getContactNumber());
		put(LIST_CODE, businessProcessModel.getListCode());
		put(LIST_NAME, businessProcessModel.getListName());
		put(UPDATE_LIST_NAME, businessProcessModel.getUpdatelistName());
		put(EMAIL, businessProcessModel.getEmailAddress());
		put(DISPLAY_NAME, businessProcessModel.getEmailAddress());
		put(EMAIL_SUBJECT, "One of your SiteOne lists has been changed.");
		put(SUPPORT_EMAIL, Config.getString("siteone.info.email1", null));
		put(CUSTOMER_NAME, businessProcessModel.getCustomerName());


	}

	@Override
	protected BaseSiteModel getSite(final ListEditEmailProcessModel businessProcessModel)
	{
		return businessProcessModel.getSite();
	}

	@Override
	protected CustomerModel getCustomer(final ListEditEmailProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return null;
	}

	@Override
	protected LanguageModel getEmailLanguage(final ListEditEmailProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
	}

}
