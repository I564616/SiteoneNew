/**
 *
 */
package com.siteone.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;

import java.util.Arrays;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;

import com.siteone.core.model.SharedProductProcessModel;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import de.hybris.platform.search.restriction.SearchRestrictionService;




/**
 * @author 1188173
 *
 */
public class SharedProductEmailContext extends AbstractEmailContext<SharedProductProcessModel>
{

	private static final Logger LOG = Logger.getLogger(RequestAccountEmailContext.class);
	public static final String EMAIL_SUBJECT = "subject";
	private static final String USER_NAME = "username";
	private static final String BASE_URL = "baseurl";
	private static final String PRODUCT = "product";
	private static final String STOCK_AVAILABILITY_MESSAGE = "stockavailabilitymessage";
	private static final String COUNTRY_BASESITE_ID = "countryBaseSiteID";


	@Resource(name = "accProductFacade")
	private ProductFacade productFacade;

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	@Resource(name = "siteOneFacadeMessageSource")
	private MessageSource messageSource;
	
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;
	
	@Resource(name="searchRestrictionService")
 	private SearchRestrictionService searchRestrictionService;

	@Override
	public void init(final SharedProductProcessModel businessProcessModel, final EmailPageModel emailPageModel)
	{
		try
		{
			this.searchRestrictionService.disableSearchRestrictions();
			if(businessProcessModel.getCustomer() instanceof B2BCustomerModel) {
				B2BCustomerModel customer = (B2BCustomerModel) businessProcessModel.getCustomer();
				if(customer.getDefaultB2BUnit().getUid().endsWith("_CA")) {
					storeSessionFacade.setCurrentBaseStore("CA");
				}
				if(customer.getDefaultB2BUnit().getUid().endsWith("_US")) {
					storeSessionFacade.setCurrentBaseStore("US");
				}
			}
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

			final LanguageModel language = businessProcessModel.getLanguage();
			LOG.info(businessProcessModel.getLanguage().getIsocode());
			if (language != null)
			{
				put(EMAIL_LANGUAGE, language);
			}
			put(FROM_EMAIL, emailPageModel.getFromEmail());
			put(FROM_DISPLAY_NAME, emailPageModel.getFromName());

			put(PRODUCT, productFacade.getProductForOptions(businessProcessModel.getProduct(),
					Arrays.asList(ProductOption.BASIC, ProductOption.URL, ProductOption.IMAGES)));
			put(USER_NAME, businessProcessModel.getUsername());
			put(STOCK_AVAILABILITY_MESSAGE, businessProcessModel.getStockavailabilitymessage());
			put(EMAIL, businessProcessModel.getEmailAddress());

			put(DISPLAY_NAME, businessProcessModel.getEmailAddress());
			put(COUNTRY_BASESITE_ID, baseSite.getUid());
		}
		
		finally {
			this.searchRestrictionService.enableSearchRestrictions();
		}
			
		
   	if (businessProcessModel.getCustomer() instanceof B2BCustomerModel)
		{
			put(EMAIL_SUBJECT,
					((B2BCustomerModel) businessProcessModel.getCustomer()).getFirstName()
							+ getMessageSource().getMessage("shared.product.siteone", null,
									getCommonI18NService().getLocaleForIsoCode(businessProcessModel.getLanguage().getIsocode())));
		}

		else
		{
			put(EMAIL_SUBJECT, getMessageSource().getMessage("shared.product.siteone1", null,
					getCommonI18NService().getLocaleForIsoCode(businessProcessModel.getLanguage().getIsocode())));
		}


	}



	@Override
	protected BaseSiteModel getSite(final SharedProductProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
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
	protected CustomerModel getCustomer(final SharedProductProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getCustomer();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getEmailLanguage(de.hybris.
	 * platform .processengine.model.BusinessProcessModel)
	 */
	@Override
	protected LanguageModel getEmailLanguage(final SharedProductProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
	}





	/**
	 * @return the messageSource
	 */
	public MessageSource getMessageSource()
	{
		return messageSource;
	}



	/**
	 * @param messageSource
	 *           the messageSource to set
	 */
	public void setMessageSource(final MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}



	/**
	 * @return the commonI18NService
	 */
	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}



	/**
	 * @param commonI18NService
	 *           the commonI18NService to set
	 */
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}




}
