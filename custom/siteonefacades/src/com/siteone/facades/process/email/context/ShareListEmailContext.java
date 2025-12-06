/**
 *
 */
package com.siteone.facades.process.email.context;

import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.savedList.SiteoneSavedListFacade;
import com.siteone.facades.savedList.data.SavedListData;
import com.siteone.facades.savedList.data.SavedListEntryData;
import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;

import jakarta.annotation.Resource;

import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.Config;
import de.hybris.platform.wishlist2.model.Wishlist2EntryModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.context.MessageSource;

import com.siteone.core.model.ShareListProcessModel;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.savedList.SiteoneSavedListFacade;
import com.siteone.facades.savedList.data.SavedListData;
import com.siteone.facades.savedList.data.SavedListEntryData;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import de.hybris.platform.search.restriction.SearchRestrictionService;
import de.hybris.platform.b2b.model.B2BCustomerModel;



/**
 * @author 1003567
 *
 */
public class ShareListEmailContext extends AbstractEmailContext<ShareListProcessModel>
{

	private static final Logger LOG = Logger.getLogger(ShareListEmailContext.class);
	public static final String EMAIL_SUBJECT = "subject";
	private static final String USER_NAME = "username";
	private static final String BASE_URL = "baseurl";
	private static final String LIST_NAME = "listName";
	private static final String LIST_CODE = "listCode";
	private static final String LIST_DATA = "data";
	private static final String SHOW_RETAIL_PRICE = "showrp";
	private static final String SHOW_YOUR_PRICE = "showyp";
	private static final String RETAIL_PRICE_MAP = "rpricemap";
	private static final String CUST_PRICE_MAP = "cpricemap";
	private static final String CURRENCY_UNITPRICE_FORMAT = "unitpriceFormat";
	private static final String CURRENCY_UNITPRICE_FORMAT_VAL = Config.getString("currency.unitprice.formattedDigits", "#0.000");
	private static final String NUMBER_TOOL = "numberTool";
	private static final String PRICE_TOTAL_MAP = "ptotalmap";
	private static final String PRICE_TOTAL = "ptotal";
	private static final String HIDE_RETAIL_PRICE_MAP = "hrpmap";
	private static final String CUSTOMER_NAME = "customerName";
	private static final String ACCOUNT_NUMBER = "accountNumber";
	private static final String SENDER_EMAIL = "senderEmail";

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;
	@Resource(name = "siteOneFacadeMessageSource")
	private MessageSource messageSource;
	@Resource(name = "siteoneSavedListFacade")
	private SiteoneSavedListFacade siteoneSavedListFacade;
	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;
	@Resource(name = "siteoneSavedListEntryConverter")
	private Converter<Wishlist2EntryModel, SavedListEntryData> siteoneSavedListEntryConverter;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;
	@Resource(name = "storeFinderFacade")
	private SiteOneStoreFinderFacade storeFinderFacade;
	@Resource(name="searchRestrictionService")
 	private SearchRestrictionService searchRestrictionService;
	private static final String COUNTRY_BASESITE_ID = "countryBaseSiteID";

	@Override
	public void init(final ShareListProcessModel businessProcessModel, final EmailPageModel emailPageModel) {
		
		try {
			this.searchRestrictionService.disableSearchRestrictions();
			storeSessionFacade.setSessionStore(storeFinderFacade.getStoreForId(businessProcessModel.getStoreId()));
			if(businessProcessModel.getCustomer() instanceof B2BCustomerModel) {
				B2BCustomerModel customer = (B2BCustomerModel) businessProcessModel.getCustomer();
				if(customer.getDefaultB2BUnit().getUid().endsWith("_CA")) {
					storeSessionFacade.setCurrentBaseStore("CA");
				}
				if(customer.getDefaultB2BUnit().getUid().endsWith("_US")) {
					storeSessionFacade.setCurrentBaseStore("US");
				}
			}
		}
		finally {
			this.searchRestrictionService.enableSearchRestrictions();
		}
		userService.setCurrentUser(businessProcessModel.getCustomer());
		final BaseSiteModel baseSite = getSite(businessProcessModel);
		final SavedListData savedListData = siteoneSavedListFacade.getDetailsPage(businessProcessModel.getListCode());
		final Map<String, String> totalPriceMap = businessProcessModel.getCustPriceList();
		final Map<String, String> totalPriceMapAlt = businessProcessModel.getRetailPriceList();
		final Map<String, String> finalPriceMap = new HashMap<>(totalPriceMap.size());
		final Map<String, Boolean> hideRetailPriceMap = new HashMap<>(totalPriceMap.size());
		double[] totalCost = {0};
		List<SavedListEntryData> entryList = savedListData.getEntries();
		if (!totalPriceMap.isEmpty())
		{
			entryList.forEach(value -> {
				
				/*
				 * hideRetailPriceMap is being used for storing boolean values for each product, determining whether to hide
				 * the retail price in the .vm tmplate file , if retail price is less than csp then we will hide it
				 */
				
				if (Double.parseDouble(totalPriceMapAlt.get(value.getProduct().getCode())) < Double
						.parseDouble(totalPriceMap.get(value.getProduct().getCode())))
				{
					hideRetailPriceMap.put(value.getProduct().getCode(), Boolean.TRUE);
				}
				else
				{
					hideRetailPriceMap.put(value.getProduct().getCode(), Boolean.FALSE);
				}
				
				// finalPriceMap is being used to store the total price at item level whereas totalCost array is being used to store the total at list level.
				// We would be considering the csp price for calculating total price at item level for most of the cases except -
				//		1. If hidecsp is true for the product
				//		2. If the customer does not select the show your price checkbox in share list via email popup
				//		3. If retail price is less than csp price but for the product hidecsp is true
				// - For above mentioned 3 scenarios we would be considering the retail price for calculating total price at item level, for all other cases we would use csp price.
				// If the inventoryFlag for the product is true or both of the hidelist and hidecsp are true then we would not show any price at item level but a message
				// stating pricing is not available for this product would be shown.
				// Also if the list contains at least one product for which we get the above mentioned pricing message then we would not show the total at list level, that logic is handled in the .vm file.
				
				if ((BooleanUtils.isTrue(value.getProduct().getHideCSP())
						|| !businessProcessModel.getShowCustPrice()) && (!hideRetailPriceMap.get(value.getProduct().getCode()) || (hideRetailPriceMap.get(value.getProduct().getCode()) && BooleanUtils.isTrue(value.getProduct().getHideCSP()))))
				{
					if (null != totalPriceMapAlt.get(value.getProduct().getCode())
							&& !totalPriceMapAlt.get(value.getProduct().getCode()).isEmpty() && businessProcessModel.getShowRetailPrice()
							&& !(BooleanUtils.isTrue(value.getProduct().getHideList()))
							&& !(BooleanUtils.isTrue(value.getProduct().getInventoryFlag())))
					{

						if (null != totalPriceMapAlt.get(value.getProduct().getCode()))
						{
							Double price = Double.parseDouble(totalPriceMapAlt.get(value.getProduct().getCode()));
							final int qty = value.getQty();
							price = price * qty;
							finalPriceMap.put(value.getProduct().getCode(), String.valueOf(price));
							totalCost[0] = totalCost[0] + price;
						}
					}
				}
				else if (null!=totalPriceMap.get(value.getProduct().
						getCode()) &&!totalPriceMap.get(value.getProduct().
						getCode()).isEmpty() && businessProcessModel.getShowCustPrice()
						&& !(BooleanUtils.isTrue(value.getProduct().getHideCSP()))
						&& !(BooleanUtils.isTrue(value.getProduct().getInventoryFlag()))) {

					if (null!=totalPriceMap.get(value.getProduct().
							getCode())) {
						Double price = Double.parseDouble(totalPriceMap.get(value.getProduct().
								getCode()));
						int qty = value.getQty();
						price = price * qty;
						finalPriceMap.put(value.getProduct().getCode(), String.valueOf(price));
						totalCost[0] = totalCost[0] + price;
					}
				}
			});
			put(HIDE_RETAIL_PRICE_MAP, hideRetailPriceMap);
			put(PRICE_TOTAL_MAP,finalPriceMap);
			put(PRICE_TOTAL,totalCost[0]);
			put(COUNTRY_BASESITE_ID, baseSite.getUid());
	}


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
			put(LIST_DATA,savedListData);
			put(SHOW_RETAIL_PRICE,businessProcessModel.getShowRetailPrice());
			put(SHOW_YOUR_PRICE,businessProcessModel.getShowCustPrice());
			put(RETAIL_PRICE_MAP,businessProcessModel.getRetailPriceList());
			put(CUST_PRICE_MAP,businessProcessModel.getCustPriceList());

		}

		put(FROM_EMAIL, emailPageModel.getFromEmail());
		put(FROM_DISPLAY_NAME, emailPageModel.getFromName());
		put(CUSTOMER_NAME, businessProcessModel.getCustomerName());
		put(ACCOUNT_NUMBER, businessProcessModel.getAccountNumber());
		put(SENDER_EMAIL, businessProcessModel.getSenderEmail());
		put(USER_NAME, businessProcessModel.getUsername());
		put(LIST_NAME, businessProcessModel.getListName());
		put(LIST_CODE, businessProcessModel.getListCode());
		put(EMAIL, businessProcessModel.getEmailAddress());
		final LanguageModel language = businessProcessModel.getLanguage();
		LOG.info(businessProcessModel.getLanguage().getIsocode());
		if (language != null)
		{
			put(EMAIL_LANGUAGE, language);
		}
		put(NUMBER_TOOL, new NumberTool());
		put(CURRENCY_UNITPRICE_FORMAT, CURRENCY_UNITPRICE_FORMAT_VAL);
		put(DISPLAY_NAME, businessProcessModel.getEmailAddress());
		put(EMAIL_SUBJECT, businessProcessModel.getUsername() + getMessageSource().getMessage("shared.list.siteone", null,
				getCommonI18NService().getLocaleForIsoCode(businessProcessModel.getLanguage().getIsocode())));


	}

	@Override
	protected BaseSiteModel getSite(final ShareListProcessModel businessProcessModel)
	{
		return businessProcessModel.getSite();
	}

	@Override
	protected CustomerModel getCustomer(final ShareListProcessModel businessProcessModel)
	{
		return businessProcessModel.getCustomer();
	}

	@Override
	protected LanguageModel getEmailLanguage(final ShareListProcessModel businessProcessModel)
	{
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
