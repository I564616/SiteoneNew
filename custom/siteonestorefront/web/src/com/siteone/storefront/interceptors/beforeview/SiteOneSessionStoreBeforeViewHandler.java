/**
 *
 */
package com.siteone.storefront.interceptors.beforeview;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.acceleratorstorefrontcommons.forms.ForgottenPwdForm;
import de.hybris.platform.acceleratorstorefrontcommons.interceptors.BeforeViewHandler;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.core.model.user.EmployeeModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.util.Config;

import java.util.List;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;

import com.siteone.core.asm.dao.ASMAgentRetrieveDao;
import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.facades.cart.SiteOneCartFacade;
import com.siteone.facades.checkout.impl.DefaultSiteOneB2BCheckoutFacade;
import com.siteone.facades.constants.SiteoneFacadesConstants;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.integration.jobs.analytics.service.AdobeAnalyticsCustomerExportCronJobService;
import com.siteone.storefront.forms.SiteOneStoreFinderForm;


/**
 * @author Abdul Rahman Sheikh M
 *
 */
public class SiteOneSessionStoreBeforeViewHandler implements BeforeViewHandler
{

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Resource(name = "catalogVersionService")
	private CatalogVersionService catalogVersionService;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "userFacade")
	private UserFacade userFacade;

	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;

	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;

	@Resource(name = "asmAgentRetrieveDao")
	private ASMAgentRetrieveDao asmAgentRetrieveDao;

	public ASMAgentRetrieveDao getAsmAgentRetrieveDao()
	{
		return asmAgentRetrieveDao;
	}

	public void setAsmAgentRetrieveDao(final ASMAgentRetrieveDao asmAgentRetrieveDao)
	{
		this.asmAgentRetrieveDao = asmAgentRetrieveDao;
	}

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "b2bCheckoutFacade")
	private DefaultSiteOneB2BCheckoutFacade b2bCheckoutFacade;

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "i18nService")
	private I18NService i18nService;

	@Resource(name = "cartFacade")
	private SiteOneCartFacade siteOneCartFacade;

	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;

	@Resource(name = "adobeAnalyticsCustomerExportCronJobService")
	private AdobeAnalyticsCustomerExportCronJobService adobeAnalyticsCustomerExportCronJobService;


	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	/**
	 * @return the sessionService
	 */
	public SessionService getSessionService()
	{
		return sessionService;
	}

	/**
	 * @param sessionService
	 *           the sessionService to set
	 */
	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}


	/**
	 * @return the i18nService
	 */
	public I18NService getI18nService()
	{
		return i18nService;
	}

	/**
	 * @param i18nService
	 *           the i18nService to set
	 */
	public void setI18nService(final I18NService i18nService)
	{
		this.i18nService = i18nService;
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

	@Resource(name = "messageSource")
	private MessageSource messageSource;

	private static final String PROD_ANALYTICS = "prodAnalytics";
	private static final String GOOGLE_API_KEY_FOR_CLIENT_SIDE = "googleApiKeyForClientSide";
	private static final String ACCOUNT_CLASSIFICATION = "account_classification";
	private static final String SEGMENT_LEVEL_SHIPPING_ENABLED = "segmentLevelShippingEnabled";
	private static final String TRADE_CLASS = "tradeClass";
	private static final Logger LOG = Logger.getLogger(SiteOneSessionStoreBeforeViewHandler.class.getName());

	@Override
	public void beforeView(final HttpServletRequest request, final HttpServletResponse response, final ModelAndView modelAndView)
			throws Exception
	{
		boolean orderingAccount = true;
		boolean quoteFeature = false;
		final B2BUnitModel unit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
		boolean isRetailBranchPrice = false;

		if (siteOneFeatureSwitchCacheService.isAllPresentUnderFeatureSwitch("QuotesFeatureSwitch")
				|| (null != unit && null != unit.getUid()
						&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("QuotesFeatureSwitch", unit.getUid())))
		{
			quoteFeature = true;
		}

		if (null != storeSessionFacade.getSessionStore()
				&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("RetailBranches",
						storeSessionFacade.getSessionStore().getStoreId())
				&& StringUtils.isNotBlank(storeSessionFacade.getSessionStore().getCustomerRetailId()))
		{
			isRetailBranchPrice = true;
		}
		modelAndView.addObject("isRetailBranchPrice", isRetailBranchPrice);

		if (null != getSessionService().getAttribute(SiteoneFacadesConstants.ASM_SESSION_PARAMETER)
				&& b2bCustomerService.getCurrentB2BCustomer() != null)
		{
			final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
			final List<EmployeeModel> agentList = getAsmAgentRetrieveDao().getAgentPk(customer.getPk().toString());
			if (!agentList.isEmpty())
			{
				modelAndView.addObject("asmAgentId", agentList.get(0).getUid());
			}
		}

		//quotes toggle feature switch
		if (!userFacade.isAnonymousUser())
		{
			modelAndView.addObject("quotesFeatureSwitch", quoteFeature);
		}
		else
		{
			modelAndView.addObject("quotesFeatureSwitch", Boolean.FALSE);
		}

		modelAndView.addObject("sessionStore", storeSessionFacade.getSessionStore());

		modelAndView.addObject(SiteoneCoreConstants.NEARBY_SESSION_STORES, storeSessionFacade.getNearbyStoresFromSession());
		if(storeSessionFacade.getNurseryNearbyBranchesFromSession() != null)
		{
			modelAndView.addObject(SiteoneCoreConstants.NURSERY_NEARBY_SESSION_STORES, storeSessionFacade.getNurseryNearbyBranchesFromSession());
		}
		final SiteOneStoreFinderForm siteOneStoreFinderForm = new SiteOneStoreFinderForm();
		modelAndView.addObject("siteOneStorePopupForm", siteOneStoreFinderForm);
		final ForgottenPwdForm forgottenPwdForm = new ForgottenPwdForm();
		modelAndView.addObject("forgottenPwdForm", forgottenPwdForm);
		modelAndView.addObject("isAnonymous", userFacade.isAnonymousUser());
		modelAndView.addObject("googleApiKeyForClientSide",
				configurationService.getConfiguration().getString(GOOGLE_API_KEY_FOR_CLIENT_SIDE));

		modelAndView.addObject("trackRetailCSPPricing",
				configurationService.getConfiguration().getString("track.retailCSP.pricing"));
		modelAndView.addObject("globalPaymentApiKey",
				configurationService.getConfiguration().getString("ue.ca.global.payment.api.key"));
		modelAndView.addObject("globalPaymentApiEnv",
				configurationService.getConfiguration().getString("ue.ca.global.payment.environment"));
			modelAndView.addObject("caPartnersPointsReedem",
				configurationService.getConfiguration().getString("ue.ca.partner.points.url"));
		modelAndView.addObject("prodAnalytics", configurationService.getConfiguration().getString(PROD_ANALYTICS));

		final CartData cartDatas = siteOneCartFacade.getSessionCart();
		if (cartDatas.getEntries() != null)
		{
			modelAndView.addObject("numberOfItemsInCart", Integer.valueOf(cartDatas.getEntries().size()));
		}

		if (userFacade.isAnonymousUser())
		{
			modelAndView.addObject("isGuestCheckoutEnabled", siteOneCartFacade.isGuestCheckoutEnabled());
		}
		if (sessionService.getAttribute("consentForm") != null)
		{
			modelAndView.addObject("consentForm", (sessionService.getAttribute("consentForm")));
		}
		sessionService.setAttribute("agroWebEnabled", true);
		if (!userFacade.isAnonymousUser())
		{
			final B2BUnitModel parent = ((SiteOneB2BUnitService) b2bUnitService).getParentUnitForCustomer();
			if (null != parent
					&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("EnabledB2BUnitForAgroAI", parent.getUid()))
			{
				sessionService.setAttribute("isEnabledForAgroAI", true);
			}
			if (null != parent)
			{
				modelAndView.addObject("parentUnitId", parent.getUid());
				modelAndView.addObject("listOfShipTo",
						((SiteOneB2BUnitFacade) b2bUnitFacade).getShipTosForUnitBasedOnCustomer(parent.getUid()));
			}
			modelAndView.addObject("isAdmin", ((SiteOneB2BUnitService) b2bUnitService).isAdminUser());
			if(null == storeSessionFacade.getSessionShipTo()) {
				LOG.error("Was not able to find store session id.");
			}
			modelAndView.addObject("sessionShipTo", storeSessionFacade.getSessionShipTo());			
			if(sessionService.getAttribute("selectedShipTo") != null)
			{
				modelAndView.addObject("selectedShipTo", sessionService.getAttribute("selectedShipTo"));
			}
			if (storeSessionFacade.getSessionShipTo() != null)
			{
				modelAndView.addObject("tradeClass", storeSessionFacade.getSessionShipTo().getTradeClass());
			}
			final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
			if (customer != null && !userFacade.isAnonymousUser())
			{
				modelAndView.addObject("InvoicePermission", customer.getInvoicePermissions());
			}
			else
			{
				modelAndView.addObject("InvoicePermission", Boolean.FALSE);
			}
			final Boolean projectServices = ((SiteOneCustomerFacade) customerFacade).isHavingProjectServices();
			String psUrl = null;
			if (projectServices != null && Boolean.TRUE.equals(projectServices))
			{
				String baseUrl = configurationService.getConfiguration().getString(("project.services.url.base"));
				String sessionToken = (String)getSessionService().getAttribute(SiteoneCoreConstants.OKTA_SESSION_TOKEN);
				psUrl = baseUrl + "?sessionToken=" + sessionToken;

				LOG.error("The all session token is " + sessionToken);
			}
			LOG.error("The Session url is " + psUrl);
			modelAndView.addObject("projectServicesUrl", psUrl);
			final String email = !StringUtils.isBlank(customer.getUid()) ? customer.getUid() : "NA";
			modelAndView.addObject("hashedEmailID", adobeAnalyticsCustomerExportCronJobService.convertToHex(email));
			modelAndView.addObject("encryptedEmailID",
					((SiteOneCustomerFacade) customerFacade).encrypt(email, Config.getString("encryption.aes.secret", null)));
			modelAndView.addObject("customerType", ((SiteOneCustomerFacade) customerFacade).getCustomerTypeByOrderCreation());
			if (null != customer.getUid() && customer.getDefaultB2BUnit().getActive())
			{
				modelAndView.addObject("isCreditCodeValid",
						((SiteOneCustomerFacade) customerFacade).isCreditCodeValid(customer.getUid()));
				modelAndView.addObject("isPayBillEnabled",
						((SiteOneCustomerFacade) customerFacade).isPayBillEnabled(customer.getUid()));
			}
			if (!customer.getDefaultB2BUnit().getActive())
			{
				modelAndView.addObject("isCreditCodeValid", Boolean.TRUE);
				modelAndView.addObject("isPayBillEnabled", Boolean.FALSE);
			}
			if (storeSessionFacade.getSessionShipTo() != null)
			{
				modelAndView.addObject("customerSegment", storeSessionFacade.getSessionShipTo().getCustomerSegment());
			}
			if (storeSessionFacade.getSessionShipTo() != null
					&& StringUtils.isNotEmpty(storeSessionFacade.getSessionShipTo().getUid()))
			{
				orderingAccount = ((SiteOneB2BUnitService) b2bUnitService)
						.isOrderingAccount(storeSessionFacade.getSessionShipTo().getUid());
			}
			if (sessionService.getAttribute("changeShiptoUnit") != null)
			{
				final String shipToId = sessionService.getAttribute("changeShiptoUnit");
				final B2BUnitModel shipToUnit = (B2BUnitModel) b2bUnitService.getUnitForUid(shipToId);
				final PointOfServiceData b2bUnitStore = ((SiteOneB2BUnitFacade) b2bUnitFacade)
						.getHomeBranchForUnit(shipToUnit.getUid());
				modelAndView.addObject("localizedBranch", b2bUnitStore);
				sessionService.setAttribute("changeShiptoUnit", null);
			}
			modelAndView.addObject("hasSignedUp", ((SiteOneCustomerFacade) customerFacade).isEmailOpted());
			modelAndView.addObject("orderOnlinePermissions", ((SiteOneCustomerFacade) customerFacade).isHavingPlaceOrder());
			modelAndView.addObject("contPayBillOnline", ((SiteOneCustomerFacade) customerFacade).isHavingPayBillOnline());
			modelAndView.addObject("acctPayBillOnline",
					(((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer()).getPayBillOnline());

			final String RETAIL_TRADECLASS = "409033";
			String tradeClass = "";
			if (customer.getDefaultB2BUnit() != null && customer.getDefaultB2BUnit().getTradeClass() != null
					&& (!customer.getDefaultB2BUnit().getTradeClass().equals(RETAIL_TRADECLASS)))
			{
				modelAndView.addObject(ACCOUNT_CLASSIFICATION, "contractor");
				tradeClass = "contractor";
				sessionService.setAttribute(TRADE_CLASS, tradeClass);
			}
			else if (customer.getDefaultB2BUnit() != null && customer.getDefaultB2BUnit().getTradeClass() != null
					&& (customer.getDefaultB2BUnit().getTradeClass().equals(RETAIL_TRADECLASS)))
			{
				modelAndView.addObject(ACCOUNT_CLASSIFICATION, "homeowner");
				tradeClass = "homeowner";
				sessionService.setAttribute(TRADE_CLASS, tradeClass);
			}
			else
			{
				modelAndView.addObject(ACCOUNT_CLASSIFICATION, "");
				modelAndView.addObject(SEGMENT_LEVEL_SHIPPING_ENABLED, true);
				sessionService.setAttribute(SEGMENT_LEVEL_SHIPPING_ENABLED, true);
			}
			if (!tradeClass.isEmpty())
			{
				final Boolean segmentLevelShippingEnabled = isSegmentLevelShippingEnabled(tradeClass);
				modelAndView.addObject(SEGMENT_LEVEL_SHIPPING_ENABLED, segmentLevelShippingEnabled);
				sessionService.setAttribute(SEGMENT_LEVEL_SHIPPING_ENABLED, segmentLevelShippingEnabled);
			}

		}
		else
		{
			final CartData cartData = siteOneCartFacade.getSessionCart();
			modelAndView.addObject("cartData", cartData);
			modelAndView.addObject("orderOnlinePermissions", true);
			final Boolean segmentLevelShippingEnabled = isSegmentLevelShippingEnabled("guest");
			modelAndView.addObject(SEGMENT_LEVEL_SHIPPING_ENABLED, segmentLevelShippingEnabled);
			sessionService.setAttribute(SEGMENT_LEVEL_SHIPPING_ENABLED, segmentLevelShippingEnabled);
		}


		modelAndView.addObject("isOrderingAccount", orderingAccount);
		modelAndView.addObject("orderingAccountMsg",
				getMessageSource().getMessage("ordering.account.message", null, getI18nService().getCurrentLocale()));

		final AbstractPageModel requestedPage = (AbstractPageModel) modelAndView.getModel()
				.get(AbstractPageController.CMS_PAGE_MODEL);
		if (null != requestedPage && (requestedPage.getUid().equalsIgnoreCase("searchGrid")
				|| requestedPage.getUid().equalsIgnoreCase("search") || requestedPage.getUid().equalsIgnoreCase("productGrid")
				|| requestedPage.getUid().equalsIgnoreCase("detailsSavedListPage")
				|| requestedPage.getUid().equalsIgnoreCase("siteOnePromotionSearchPage")))
		{
			modelAndView.addObject("callStoreForAvailabilityMsg",
					getMessageSource().getMessage("product.callStoreForAvailability", null, getI18nService().getCurrentLocale()));
			modelAndView.addObject("instockMsg",
					getMessageSource().getMessage("product.instock.homestore.new", null, getI18nService().getCurrentLocale()));
			modelAndView.addObject("instockAtNearbyBranchMsg",
					getMessageSource().getMessage("product.instock.nearbystore.new", null, getI18nService().getCurrentLocale()));
			modelAndView.addObject("instockAtNearbyBranchMsgNew",
					getMessageSource().getMessage("product.instock.nearbystore", null, getI18nService().getCurrentLocale()));
			modelAndView.addObject("outOfStockInvHitMsg",
					getMessageSource().getMessage("product.outofstock.invhit.new", null, getI18nService().getCurrentLocale()));
			modelAndView.addObject("outOfStockInvHitForceStockMsg",
					getMessageSource().getMessage("product.outofstock.invhit.forcestock", null, getI18nService().getCurrentLocale()));
			modelAndView.addObject("outOfStockNoInvHitMsg",
					getMessageSource().getMessage("product.outofstock.noinventoryhit.new", null, getI18nService().getCurrentLocale()));
			modelAndView.addObject("outOfStockNoInvHitMsgNew",
					getMessageSource().getMessage("product.outofstock.noinventoryhit", null, getI18nService().getCurrentLocale()));
			modelAndView.addObject("regulatedItemNotApprovedMsg",
					getMessageSource().getMessage("product.regulatedItem.notApproved", null, getI18nService().getCurrentLocale()));
			modelAndView.addObject("regulatedItemApprovedMsg", getMessageSource()
					.getMessage("product.regulatedItem.approved.notSelectedStore", null, getI18nService().getCurrentLocale()));
			modelAndView.addObject("regulatedMsgForExpiredLicense",
					getMessageSource().getMessage("product.regulatedItem.licenseExpired", null, getI18nService().getCurrentLocale()));
		}
		modelAndView.addObject("priceUnavailableMsg",
				getMessageSource().getMessage("product.priceUnavailable", null, getI18nService().getCurrentLocale()));


		if (null != requestedPage && requestedPage.getUid().equalsIgnoreCase("cartPage"))
		{
			//Messages For Regular Item Cart Entries
			modelAndView.addObject("cartRegularItemInsufficientStockMsg", getMessageSource()
					.getMessage("cart.entry.regularproduct.insufficientstock", null, getI18nService().getCurrentLocale()));
			modelAndView.addObject("cartRegularItemOutOfStockMsg", getMessageSource()
					.getMessage("cart.entry.regularproduct.oosinselectedstore", null, getI18nService().getCurrentLocale()));
			modelAndView.addObject("cartRegularItemInStockMsg", getMessageSource()
					.getMessage("cart.entry.regularproduct.inStockinselectedstore", null, getI18nService().getCurrentLocale()));
			modelAndView.addObject("cartRegularItemPriceNotAvailable", getMessageSource()
					.getMessage("cart.entry.regularproduct.pricenotavailable", null, getI18nService().getCurrentLocale()));
			modelAndView.addObject("cartRegularItemNotAvailableInStore", getMessageSource()
					.getMessage("cart.entry.regularproduct.notinselectedstore", null, getI18nService().getCurrentLocale()));


			//Messages For Regulated Item Cart Entries
			modelAndView.addObject("cartRegulatoryItemNotSellableInStateMsg", getMessageSource()
					.getMessage("cart.entry.regulatoryproduct.notsellableinstate", null, getI18nService().getCurrentLocale()));
			modelAndView.addObject("cartRegulatoryItemLicenseExpiredMsg", getMessageSource()
					.getMessage("cart.entry.regulatoryproduct.licenseExpired", null, getI18nService().getCurrentLocale()));
			modelAndView.addObject("cartRegulatoryItemNotInSelectedStoreMsg", getMessageSource()
					.getMessage("cart.entry.regulatoryproduct.notinselectedstore", null, getI18nService().getCurrentLocale()));
			modelAndView.addObject("cartRegulatoryItemInSufficientStockMsg", getMessageSource()
					.getMessage("cart.entry.regulatoryproduct.insufficientstock", null, getI18nService().getCurrentLocale()));
			modelAndView.addObject("cartRegulatoryItemInStockMsg", getMessageSource()
					.getMessage("cart.entry.regulatoryproduct.instockinselectedstore", null, getI18nService().getCurrentLocale()));
			modelAndView.addObject("cartRegulatoryItemOutOfStockMsg", getMessageSource()
					.getMessage("cart.entry.regulatoryproduct.ooutofstockinselectedstore", null, getI18nService().getCurrentLocale()));
			modelAndView.addObject("cartRegulatoryItemPriceNotAvailablekMsg", getMessageSource()
					.getMessage("cart.entry.regulatoryproduct.pricenotavailable", null, getI18nService().getCurrentLocale()));

			//Messages For NLA Item Cart Entries
			modelAndView.addObject("cartNLAItemSufficientStockMsg", getMessageSource()
					.getMessage("cart.entry.nla.product.sufficientstock", null, getI18nService().getCurrentLocale()));
			modelAndView.addObject("cartNLAItemInSufficientMsg", getMessageSource()
					.getMessage("cart.entry.nla.product.insufficientstock", null, getI18nService().getCurrentLocale()));
			modelAndView.addObject("cartNLAItemInStockMsg",
					getMessageSource().getMessage("cart.entry.nla.product.instock", null, getI18nService().getCurrentLocale()));
			modelAndView.addObject("cartNLAItemOutOfStockMsg",
					getMessageSource().getMessage("cart.entry.nla.product.outofstock", null, getI18nService().getCurrentLocale()));
			modelAndView.addObject("cartNLAItemNotCarriedMsg",
					getMessageSource().getMessage("cart.entry.nla.product.notCarried", null, getI18nService().getCurrentLocale()));


		}

		//email address
		modelAndView.addObject("siteoneSupportEmail", Config.getString("siteone.support.email", null));

		//mixed cart feature switch
		if (null != storeSessionFacade.getSessionStore() && siteOneFeatureSwitchCacheService
				.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches", storeSessionFacade.getSessionStore().getStoreId()))
		{
			modelAndView.addObject("isMixedCartEnabled", Boolean.TRUE);
			getSessionService().setAttribute("isMixedCartEnabled", "mixedcart");
		}
		else
		{
			modelAndView.addObject("isMixedCartEnabled", Boolean.FALSE);
			getSessionService().setAttribute("isMixedCartEnabled", null);
		}

		final BaseStoreModel currentBaseStore = getBaseStoreService().getCurrentBaseStore();
		if (null != currentBaseStore)
		{
			getSessionService().setAttribute("currentBaseStore", currentBaseStore.getPk());
		}

		final BaseSiteModel basesite = baseSiteService.getCurrentBaseSite();
		CatalogVersionModel catalogVersion = null, content_catalogversion = null;
		if (basesite.getUid().equalsIgnoreCase("siteone-us"))
		{
			modelAndView.addObject("googleAnalyticsKey",
					configurationService.getConfiguration().getString("google.analytics.us.key"));
					
			catalogVersion = catalogVersionService.getCatalogVersion(SiteoneCoreConstants.CATALOG_US,
					SiteoneCoreConstants.CATALOG_VERSION);
			content_catalogversion = catalogVersionService.getCatalogVersion(SiteoneCoreConstants.CONTENT_CATALOG_US,
					SiteoneCoreConstants.CATALOG_VERSION);

		}

		else if (basesite.getUid().equalsIgnoreCase("siteone-ca"))
		{
			modelAndView.addObject("googleAnalyticsKey",
					configurationService.getConfiguration().getString("google.analytics.ca.key"));
					
			catalogVersion = catalogVersionService.getCatalogVersion(SiteoneCoreConstants.CATALOG_CA,
					SiteoneCoreConstants.CATALOG_VERSION);
			content_catalogversion = catalogVersionService.getCatalogVersion(SiteoneCoreConstants.CONTENT_CATALOG_CA,
					SiteoneCoreConstants.CATALOG_VERSION);

		}
		if (catalogVersion != null)
		{
			getSessionService().setAttribute("currentCatalogVersion", catalogVersion);
		}
		if(content_catalogversion != null) 
		{
			getSessionService().setAttribute("currentContentCatalogVersion", content_catalogversion);
		}

		//bulk delivery branches feature switch
		if (null != storeSessionFacade.getSessionStore() && siteOneFeatureSwitchCacheService
				.isBranchPresentUnderFeatureSwitch("BulkDeliveryBranches", storeSessionFacade.getSessionStore().getStoreId()))
		{
			modelAndView.addObject("isBulkDeliveryBranch", Boolean.TRUE);
		}
		else
		{
			modelAndView.addObject("isBulkDeliveryBranch", Boolean.FALSE);
		}
		// feature switch for split/mixed pickup branches
		if (!userFacade.isAnonymousUser() && null != storeSessionFacade.getSessionStore() && siteOneFeatureSwitchCacheService
				.isBranchPresentUnderFeatureSwitch("SplitMixedPickupBranches", storeSessionFacade.getSessionStore().getStoreId()))
		{
			modelAndView.addObject("isSplitMixedCartEnabledBranch", Boolean.TRUE);
		}
		else
		{
			modelAndView.addObject("isSplitMixedCartEnabledBranch", Boolean.FALSE);
		}
		
		//Nursery Buying Group Flag at branch level
		if(null != storeSessionFacade.getSessionStore() && null != storeSessionFacade.getSessionStore().getNurseryBuyingGroup())
		{
			modelAndView.addObject("isNurseryBuyingGroupBranch", Boolean.TRUE);
		}
		else
		{
			modelAndView.addObject("isNurseryBuyingGroupBranch", Boolean.FALSE);
		}
		
		//hardscape toggle feature switch
		modelAndView.addObject("hardscapeFeatureSwitch",
				siteOneFeatureSwitchCacheService.getValueForSwitch("HardscapeToggleFeatureSwitch"));
		modelAndView.addObject("currentBaseStoreId", currentBaseStore.getUid());

		//curated PLP toggle feature switch
		modelAndView.addObject("isCMSPage", siteOneFeatureSwitchCacheService.getValueForSwitch("IsCMSPage"));
		modelAndView.addObject("enableToggleFilter", siteOneFeatureSwitchCacheService.getValueForSwitch("InStockToggle"));
		modelAndView.addObject("linkOrderNumber", getSessionService().getAttribute("orderNumber"));
		modelAndView.addObject("linkOrderAmount", getSessionService().getAttribute("amount"));
		modelAndView.addObject("oktaSessionToken", getSessionService().getAttribute(SiteoneCoreConstants.OKTA_SESSION_TOKEN));

        //Project Services
        boolean isProjectServicesEnabledForB2BUnit = false;
        modelAndView.addObject("isProjectServicesEnabled",siteOneFeatureSwitchCacheService.getValueForSwitch("isProjectServicesEnabled"));

        if (null != unit
                && siteOneFeatureSwitchCacheService.isB2BUnitPresentUnderFeatureSwitch("EnabledB2BUnitsForProjectServices", unit.getUid()))
        {
            isProjectServicesEnabledForB2BUnit = true;
        }
        modelAndView.addObject("isProjectServicesEnabledForB2BUnit",isProjectServicesEnabledForB2BUnit);

	}

	private Boolean isSegmentLevelShippingEnabled(final String tradeClass)
	{
		if (siteOneFeatureSwitchCacheService.getValueForSwitch("SegmentLevelShippingEnabled") != null)
		{
			boolean segmentLevelShippingEnabled = false;
			final String isSegmentLevelShipping = siteOneFeatureSwitchCacheService.getValueForSwitch("SegmentLevelShippingEnabled");
			if (isSegmentLevelShipping != null)
			{
				final String segmentLevelShipping[] = isSegmentLevelShipping.split(",");
				for (final String trade : segmentLevelShipping)
				{
					if (trade.equalsIgnoreCase(tradeClass))
					{
						segmentLevelShippingEnabled = true;
						break;
					}
				}
			}
			return segmentLevelShippingEnabled;
		}
		return true;
	}

	public BaseStoreService getBaseStoreService()
	{
		return baseStoreService;
	}

	public void setBaseStoreService(final BaseStoreService baseStoreService)
	{
		this.baseStoreService = baseStoreService;
	}

}
