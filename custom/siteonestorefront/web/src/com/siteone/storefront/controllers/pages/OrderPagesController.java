/**
 *
 */
package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorfacades.ordergridform.OrderGridFormFacade;
import de.hybris.platform.acceleratorfacades.product.data.ReadOnlyOrderGridData;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.order.OrderFacade;
import de.hybris.platform.commercefacades.order.data.ConsignmentData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderHistoryData;
import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.facetdata.FacetData;
import de.hybris.platform.commerceservices.search.facetdata.FacetValueData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.sap.security.core.server.csi.XSSEncoder;
import com.sap.security.core.server.csi.util.URLDecoder;
import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.facade.BuyItAgainData;
import com.siteone.facade.BuyItAgainSearchPageData;
import com.siteone.facade.MasterHybrisOrderData;
import com.siteone.facade.order.SiteOneOrderFacade;
import com.siteone.facades.buyitagain.search.facade.BuyItAgainSearchFacade;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.product.data.InventoryUOMData;
import com.siteone.facades.savedList.SiteoneSavedListFacade;
import com.siteone.facades.savedList.data.SavedListData;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.storefront.breadcrumbs.impl.SiteOneAccountBreadcrumbBuilder;
import com.siteone.storefront.controllers.ControllerConstants;
import com.siteone.storefront.controllers.pages.AccountPageController.SelectOption;
import com.siteone.storefront.util.SiteoneXSSFilterUtil;


/**
 * @author SMondal
 *
 */
@Controller
// FRAMEWORK_UPDATE - TODO - AntPathMatcher was replaced with PathPatternParser as the new default path parser in Spring 6. Adjust this path to the new matching rules or re-enable deprecated AntPathMatcher. Consult "Adapting to PathPatternParser new default URL Matcher" JDK21 Upgrade Step in SAP Help documentation.
@RequestMapping("/**/my-account")
public class OrderPagesController extends AbstractSearchPageController
{
	private static final String UNIT_ID_PATH_VARIABLE_PATTERN = "{unitId:.*}";
	private static final String ORDER_CODE_PATH_VARIABLE_PATTERN = "{orderCode:.*}";
	private static final String TEXT_STORE_DATEFORMAT_KEY = "text.store.dateformat";
	private static final String DEFAULT_DATEFORMAT = "MM/dd/yyyy";
	private static final String BREADCRUMBS_ATTR = "breadcrumbs";
	private static final String ACCOUNT_ORDERS_CMS_PAGE = "accountOrdersPage";
	private static final String PAGINATION_NUMBER_OF_RESULTS_COUNT = "pagination.number.results.count";
	private static final String ACCOUNT_PAGE_ID = "accountPageId";
	private static final String FACET_SEPARATOR = ":";
	private static final String ORDERS_HISTORY_CMS_PAGE = "accountOrdersHistoryPage";
	private static final String SEARCH_META_DESCRIPTION_ON = "search.meta.description.on";
	private static final String SEARCH_META_DESCRIPTION_RESULTS = "search.meta.description.results";
	private static final String NO_RESULTS_CMS_PAGE_ID = "searchEmpty";
	private static final String REDIRECT_TO_ORDER_HISTORY_PAGE = REDIRECT_PREFIX + "/my-account/orders";
	private static final String ORDER_DETAIL_CMS_PAGE = "order";
	private static final String MASTER_ORDER_DETAIL_CMS_PAGE = "masterOrderPage";

	private static final String ORDER_HISTORY_FULLPAGEPATH = "analytics.fullpath.order.history";
	private static final String OPEN_ORDERS_FULLPAGEPATH = "analytics.fullpath.openorders";
	private static final String BUY_AGAIN_FULLPAGEPATH = "analytics.fullpath.buyagain";
	private static final String ORDER_DETAILS_FULLPAGEPATH = "analytics.fullpath.order.details";
	private static final String DESCENDING_ORDER = "desc";
	private static final Logger LOG = Logger.getLogger(OrderPagesController.class);

	@Resource(name = "siteOneAccountBreadcrumbBuilder")
	private SiteOneAccountBreadcrumbBuilder siteOneAccountBreadcrumbBuilder;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Resource(name = "orderFacade")
	private OrderFacade orderFacade;

	@Resource(name = "b2bUnitFacade")
	protected B2BUnitFacade b2bUnitFacade;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;

	@Resource(name = "buyItAgainSearchFacade")
	private BuyItAgainSearchFacade buyItAgainSearchFacade;

	@Resource(name = "productVariantFacade")
	private ProductFacade productFacade;

	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;

	@Resource(name = "orderGridFormFacade")
	private OrderGridFormFacade orderGridFormFacade;

	@Resource(name = "priceDataFactory")
	private PriceDataFactory priceDataFactory;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
	
	@Resource(name = "siteoneSavedListFacade")
	SiteoneSavedListFacade siteoneSavedListFacade;

	@ModelAttribute("childUnits")
	public List<SelectOption> getB2BUnits()
	{
		final B2BUnitModel parent = ((SiteOneB2BUnitService) b2bUnitService).getParentUnitForCustomer();

		return populateSelectBoxForB2BUnit(((SiteOneB2BUnitFacade) b2bUnitFacade).getShipTosForUnit(parent.getUid()));
	}

	protected List<SelectOption> populateSelectBoxForB2BUnit(final List<B2BUnitData> unitData)
	{
		final List<SelectOption> selectOptions = new ArrayList<>();

		for (final B2BUnitData unit : unitData)
		{
			selectOptions.add(new SelectOption(unit.getUid(), unit.getName()));
		}

		return selectOptions;
	}

	@GetMapping("/orders/" + UNIT_ID_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public String myOrders(@RequestParam(value = "page", defaultValue = "0")
	final int page, @PathVariable("unitId")
	final String unitId, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, @RequestParam(value = "searchParam", required = false)
	final String searchParam, @RequestParam(value = "oSearchParam", required = false)
	final String oSearchParam, @RequestParam(value = "iSearchParam", required = false)
	final String iSearchParam, @RequestParam(value = "pnSearchParam", required = false)
	final String pnSearchParam, @RequestParam(value = "sort", required = false)
	final String sortCode, @RequestParam(value = "pagesize", required = false)
	final String pageSize, @RequestParam(value = "dateSort", required = false)
	final String dateSort, final Model model, @RequestParam(value = "datarange", required = false)
	final String datarange, @RequestHeader(value = "referer", required = false)
	final String referer, @RequestParam(value = "paymentType", required = false)
	final String paymentType, final RedirectAttributes redirectModel, @RequestParam(value = "accountShiptos", required = false)
	final String accountShiptos,@RequestParam(value = "sortOrder", defaultValue = DESCENDING_ORDER, required = false)
	final String sortOrder, final HttpServletRequest request) throws CMSItemNotFoundException // NOSONAR
	{
		boolean quoteFeature = false;
		final String sanitizedunitId = SiteoneXSSFilterUtil.filter(unitId);
		String pageAccountId = "orderhistorypage";
		final Cookie accountPageId = WebUtils.getCookie(request, "accountPageId");
		if (accountPageId != null)
		{
			pageAccountId = URLDecoder.decode(accountPageId.getValue());
		}
		int viewByPageSize = getSiteConfigService().getInt("siteoneorgaddon.search.pageSize", 10);
		String fullPagePath = "";
		final Calendar f = Calendar.getInstance();
		f.setTime(new Date());
		f.add(Calendar.MONTH, -3);


		String daysArgs = null;
		daysArgs = datarange;



		final Locale currentLocale = getI18nService().getCurrentLocale();
		final String formatString = getMessageSource().getMessage(TEXT_STORE_DATEFORMAT_KEY, null, DEFAULT_DATEFORMAT,
				currentLocale);
		String trimmedSearchParam = null;
		String orderSearchParam = null;
		String invoiceSearchParam = null;
		String poNumberSearchParam = null;
		if (null != searchParam)
		{
			trimmedSearchParam = searchParam.trim();
		}

		if (StringUtils.isNotBlank(oSearchParam))
		{
			orderSearchParam = oSearchParam.trim();
		}
		if (StringUtils.isNotBlank(iSearchParam))
		{
			invoiceSearchParam = iSearchParam.trim();
		}
		if (StringUtils.isNotBlank(pnSearchParam))
		{
			poNumberSearchParam = pnSearchParam.trim();
		}
		model.addAttribute("unitId", sanitizedunitId);
		if (null != pageSize)
		{
			viewByPageSize = Integer.parseInt(pageSize);
		}
		else
		{
			viewByPageSize = Integer.parseInt("10");
		}
		final PageableData pageableData = createPageableData(page, viewByPageSize, sortCode, showMode);

		SearchPageData<OrderHistoryData> searchPageData = null;
		if (StringUtils.isNotBlank(accountShiptos))
		{
			final String trimmedInvoiceShiptos = accountShiptos.trim();
			final String[] shipToNumber = trimmedInvoiceShiptos.split("\\s+");
			final String shipToUid = shipToNumber[0];

			searchPageData = ((SiteOneOrderFacade) orderFacade).getPagedOrderHistoryForStatuses(pageableData, page,
					setCustomerNoWithDivision(shipToUid), trimmedSearchParam, orderSearchParam, invoiceSearchParam,
					poNumberSearchParam, dateSort, paymentType,sortOrder, sortCode);
		}
		else
		{
			searchPageData = ((SiteOneOrderFacade) orderFacade).getPagedOrderHistoryForStatuses(pageableData, page, sanitizedunitId,
					trimmedSearchParam, orderSearchParam, invoiceSearchParam, poNumberSearchParam, dateSort, paymentType,sortOrder, sortCode);
		}

		final B2BUnitModel parentUnit = ((SiteOneB2BUnitService) b2bUnitService).getParentUnitForCustomer();
		final List<B2BUnitData> childs = ((SiteOneB2BUnitFacade) b2bUnitFacade).getShipTosForUnit(parentUnit.getUid());
		//final Set<String> listOfShipTos = new TreeSet<>();
		final Map<String, String> shipTosList = new LinkedHashMap<>();
		final Map<String, String> shipToListUpdated = new LinkedHashMap<>();
		if (accountShiptos != null && !accountShiptos.isEmpty() && !accountShiptos.equalsIgnoreCase("All"))
		{
			final B2BUnitModel b2BUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(sanitizedunitId);
			final String shipToNameUnitID = (b2BUnitModel.getUid().contains("_CA") ? b2BUnitModel.getUid().split("_CA")[0]
					: b2BUnitModel.getUid().split("_US")[0]) + ' ' + b2BUnitModel.getName();
			shipTosList.put(b2BUnitModel.getUid(), shipToNameUnitID);
		}
		final Collection<String> assignedShipTos = ((SiteOneB2BUnitFacade) b2bUnitFacade).getModifiedAssignedShipTos();
		List<B2BUnitData> currentShipTos;
		final Map<String, String> shipToListData = new LinkedHashMap<>();

		for (final B2BUnitData child : childs)
		{
			final String shipToID = child.getUid().contains("_CA") ? child.getUid().substring(0, child.getUid().indexOf("_CA"))
					: child.getUid().substring(0, child.getUid().indexOf("_US"));
			final String shipToNameID = (child.getUid().contains("_CA") ? child.getUid().substring(0, child.getUid().indexOf("_CA"))
					: child.getUid().substring(0, child.getUid().indexOf("_US"))) + " " + child.getName();

			shipToListUpdated.put(shipToID, shipToNameID);

			currentShipTos = ((SiteOneB2BUnitFacade) b2bUnitFacade).getModifiedShipTos(child, assignedShipTos);

			if (CollectionUtils.isNotEmpty(currentShipTos))
			{
				for (final B2BUnitData shipToData : currentShipTos)
				{
					final String shipToIDNew = shipToData.getUid().contains("_CA")
							? shipToData.getUid().substring(0, shipToData.getUid().indexOf("_CA"))
							: shipToData.getUid().substring(0, shipToData.getUid().indexOf("_US"));
					final String shipToNameIDNew = (shipToData.getUid().contains("_CA")
							? shipToData.getUid().substring(0, shipToData.getUid().indexOf("_CA"))
							: shipToData.getUid().substring(0, shipToData.getUid().indexOf("_US"))) + " " + shipToData.getName();
					shipToListData.put(shipToIDNew, shipToNameIDNew);
				}
			}

		}
		if (shipToListData.isEmpty())
		{
			shipTosList.putAll(shipToListUpdated);
		}
		else
		{
			shipTosList.putAll(shipToListData);
		}

		final Set<String> listOfShipTos = new LinkedHashSet<String>(shipTosList.values());


		model.addAttribute("searchParam", trimmedSearchParam);
		model.addAttribute("oSearchParam", orderSearchParam);
		model.addAttribute("iSearchParam", invoiceSearchParam);
		model.addAttribute("pnSearchParam", poNumberSearchParam);
		model.addAttribute("dateSort", dateSort);
		model.addAttribute("sort", sortCode);
		model.addAttribute("viewByPageSize", viewByPageSize);
		model.addAttribute("listOfShipTos", listOfShipTos);
		model.addAttribute("paymentType", paymentType);
		model.addAttribute("accountShiptos", accountShiptos);
		model.addAttribute("daysArgs", daysArgs);
		if (dateSort == null)
		{
			model.addAttribute("noorderflag", Boolean.FALSE);
		}
		else
		{
			model.addAttribute("noorderflag", Boolean.TRUE);
		}
		fullPagePath = getMessageSource().getMessage(ORDER_HISTORY_FULLPAGEPATH, null, getI18nService().getCurrentLocale());
		populateModel(model, searchPageData, showMode);
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		getContentPageForLabelOrId(ACCOUNT_ORDERS_CMS_PAGE).setFullPagePath(fullPagePath);
		storeCmsPageInModel(model, getContentPageForLabelOrId(ACCOUNT_ORDERS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ACCOUNT_ORDERS_CMS_PAGE));
		final List<Breadcrumb> breadcrumbs = siteOneAccountBreadcrumbBuilder.getBreadcrumbsForAccountDashboardPage(sanitizedunitId);
		if (breadcrumbs != null)
		{
			breadcrumbs.add(new Breadcrumb("#",
					getMessageSource().getMessage("account.dashboard.orders2", null, getI18nService().getCurrentLocale()), null));
		}

		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		model.addAttribute("accountpageId", pageAccountId);
		final B2BUnitModel unit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();


		if (siteOneFeatureSwitchCacheService.isAllPresentUnderFeatureSwitch("QuotesFeatureSwitch")
				|| (null != unit && null != unit.getUid()
						&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("QuotesFeatureSwitch", unit.getUid())))
		{
			quoteFeature = true;
		}

		//quotes toggle feature switch
		model.addAttribute("quotesFeatureSwitch", quoteFeature);

		return ControllerConstants.Views.Pages.Account.AccountOrdersPage;
	}

	@GetMapping("/buy-again/" + UNIT_ID_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public String myReOrders(@RequestParam(value = "page", defaultValue = "0")
	final int page, @PathVariable("unitId")
	final String unitId, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, @RequestParam(value = "searchParam", required = false)
	final String searchParam, @RequestParam(value = "sort", required = false)
	final String sortCode, @RequestParam(value = "pagesize", required = false)
	final String pageSize, final Model model, @RequestHeader(value = "referer", required = false)
	final String referer, final RedirectAttributes redirectModel, final HttpServletRequest request) throws CMSItemNotFoundException // NOSONAR
	{
		boolean quoteFeature = false;
		final String sanitizedunitId = SiteoneXSSFilterUtil.filter(unitId);
		performBuyItAgainSearch(request, pageSize, false, searchParam, null, page, showMode, sortCode, model, sanitizedunitId);
		((SiteOneOrderFacade) orderFacade).getListDetailsForUser(model);

		final B2BUnitModel unit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();


		if (siteOneFeatureSwitchCacheService.isAllPresentUnderFeatureSwitch("QuotesFeatureSwitch")
				|| (null != unit && null != unit.getUid()
						&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("QuotesFeatureSwitch", unit.getUid())))
		{
			quoteFeature = true;
		}

		//quotes toggle feature switch
		model.addAttribute("quotesFeatureSwitch", quoteFeature);

		return ControllerConstants.Views.Pages.Account.AccountOrdersPage;
	}


	@GetMapping(value = "/buy-again/" + UNIT_ID_PATH_VARIABLE_PATTERN, params = "q")
	@RequireHardLogIn
	public String refineSearch(@RequestParam(value = "q")
	final String searchQuery, @RequestParam(value = "page", defaultValue = "0")
	final int page, @PathVariable("unitId")
	final String unitId, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, @RequestParam(value = "searchParam", required = false)
	final String searchParam, @RequestParam(value = "sort", required = false)
	final String sortCode, @RequestParam(value = "pagesize", required = false)
	final String pageSize, final Model model, @RequestHeader(value = "referer", required = false)
	final String referer, final RedirectAttributes redirectModel, final HttpServletRequest request) throws CMSItemNotFoundException // NOSONAR
	{
		boolean quoteFeature = false;
		final String sanitizedunitId = SiteoneXSSFilterUtil.filter(unitId);
		final String sanitizedquery = SiteoneXSSFilterUtil.filter(searchQuery);
		performBuyItAgainSearch(request, pageSize, true, null, sanitizedquery, page, showMode, sortCode, model, sanitizedunitId);
		((SiteOneOrderFacade) orderFacade).getListDetailsForUser(model);

		final B2BUnitModel unit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();


		if (siteOneFeatureSwitchCacheService.isAllPresentUnderFeatureSwitch("QuotesFeatureSwitch")
				|| (null != unit && null != unit.getUid()
						&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("QuotesFeatureSwitch", unit.getUid())))
		{
			quoteFeature = true;
		}

		//quotes toggle feature switch
		model.addAttribute("quotesFeatureSwitch", quoteFeature);
		return ControllerConstants.Views.Pages.Account.AccountOrdersPage;
	}


	public void performBuyItAgainSearch(final HttpServletRequest request, final String pageSize, final boolean qParam,
			final String searchParam, final String searchQuery, final int page, final ShowMode showMode, final String sortCode,
			final Model model, final String unitId) throws CMSItemNotFoundException
	{
		String pageAccountId = "";
		final Cookie accountPageId = WebUtils.getCookie(request, "accountPageId");
		if (accountPageId != null)
		{
			pageAccountId = URLDecoder.decode(accountPageId.getValue());
		}
		int viewByPageSize = getSiteConfigService().getInt("siteoneorgaddon.search.pageSize", 10);
		if (null != pageSize)
		{
			viewByPageSize = Integer.parseInt(pageSize);
		}
		BuyItAgainSearchPageData<SearchStateData, BuyItAgainData> searchPageData = null;
		try
		{
			String trimmedSearchParam = null;
			if (null != searchParam)
			{
				trimmedSearchParam = searchParam.trim();
			}
			if (qParam)
			{
				searchPageData = performSearch(searchQuery, page, showMode, sortCode, viewByPageSize);

			}
			else
			{
				searchPageData = performSearch(trimmedSearchParam, page, showMode, sortCode, viewByPageSize);
			}

		}
		catch (final ConversionException e) // NOSONAR
		{
			// nothing to do - the exception is logged in SearchSolrQueryPopulator
		}
		final List<ProductData> productList = getBuyAgainProductList(searchPageData);

		populateModelForBuyItAgain(model, searchPageData, showMode, productList);
		getContentPageForLabelOrId(ACCOUNT_ORDERS_CMS_PAGE)
				.setFullPagePath(getMessageSource().getMessage(BUY_AGAIN_FULLPAGEPATH, null, getI18nService().getCurrentLocale()));
		storeCmsPageInModel(model, getContentPageForLabelOrId(ACCOUNT_ORDERS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ACCOUNT_ORDERS_CMS_PAGE));
		final List<Breadcrumb> breadcrumbs = siteOneAccountBreadcrumbBuilder.getBreadcrumbsForAccountDashboardPage(unitId);
		if (breadcrumbs != null)
		{
			breadcrumbs.add(new Breadcrumb("#",
					getMessageSource().getMessage("account.dashboard.orders2", null, getI18nService().getCurrentLocale()), null));
		}

		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		model.addAttribute("accountpageId", pageAccountId);
		model.addAttribute("viewByPageSize", viewByPageSize);
		model.addAttribute("searchParam", searchParam);

	}

	@GetMapping("/order/" + UNIT_ID_PATH_VARIABLE_PATTERN + "/"
			+ ORDER_CODE_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public String order(@PathVariable("orderCode")
	final String orderCode, final Model model, @PathVariable("unitId")
	final String unitId, final RedirectAttributes redirectModel, @RequestHeader(value = "referer", required = false)
	final String referer, @RequestParam(value = "orderType", defaultValue = "")
	final String orderType, @RequestParam(value = "branchNo", required = false)
	final String branchNo, @RequestParam(value = "shipmentCount", required = false)
	final Integer shipmentCount, final HttpServletRequest request, final HttpServletResponse response) throws CMSItemNotFoundException
	{
		final String sanitizedunitId = SiteoneXSSFilterUtil.filter(unitId);
		final String sanitizedOrderCode = SiteoneXSSFilterUtil.filter(orderCode);

		OrderData orderDetails;
		
		try
		{
			if(sanitizedOrderCode != null && sanitizedOrderCode.startsWith("MH"))
			{
				orderDetails = ((SiteOneOrderFacade) orderFacade).getOrderDetailsPage(sanitizedOrderCode.substring(2,sanitizedOrderCode.length()).split("-")[0], sanitizedunitId);
			}
			else if (branchNo != null || shipmentCount != null)
			{
				orderDetails = ((SiteOneOrderFacade) orderFacade).getOrderDetailsPage(sanitizedOrderCode, sanitizedunitId, branchNo, shipmentCount, Boolean.FALSE);
			}
			else
			{
				orderDetails = ((SiteOneOrderFacade) orderFacade).getOrderDetailsPage(sanitizedOrderCode, sanitizedunitId, null, null, Boolean.FALSE);
			}
			//uom enhancements changes
			if (CollectionUtils.isNotEmpty(orderDetails.getUnconsignedEntries()))
			{
				siteOneProductFacade.getPriceUpdateForHideUomEntry(orderDetails, false);
			}

			model.addAttribute("orderData", orderDetails);
			model.addAttribute("unitId", sanitizedunitId);
			if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
			{
				final boolean isAssembly = false;
				final List<SavedListData> allWishlist = siteoneSavedListFacade.getAllSavedListForEdit(isAssembly);
				String wishlistName = null;
				if (CollectionUtils.isNotEmpty(allWishlist) && allWishlist.size() == 1)
				{
					wishlistName = allWishlist.get(0).getCode();
				}
				if (CollectionUtils.isEmpty(allWishlist))
				{
					model.addAttribute("createWishList", "true");
				}
				model.addAttribute("wishlistName", wishlistName);
				model.addAttribute("allWishlist", allWishlist);
				model.addAttribute("loggedUser", "true");
			}					
			
			final List<Breadcrumb> breadcrumbs = siteOneAccountBreadcrumbBuilder
					.getBreadcrumbsForAccountDashboardPage(sanitizedunitId);
			if (null != referer)
			{
				if (referer.contains("open-orders") || orderType.equalsIgnoreCase("open-orders"))
				{
					breadcrumbs.add(new Breadcrumb("/my-account/open-orders/" + sanitizedunitId,
							getMessageSource().getMessage("text.account.openorder", null, getI18nService().getCurrentLocale()), null));
				}
				else
				{
					breadcrumbs.add(new Breadcrumb("/my-account/orders/" + sanitizedunitId,
							getMessageSource().getMessage("text.account.orderHistory", null, getI18nService().getCurrentLocale()),
							null));
				}
			}
			else
			{
				breadcrumbs.add(new Breadcrumb("/my-account/open-orders/" + sanitizedunitId,
						getMessageSource().getMessage("text.account.openorder", null, getI18nService().getCurrentLocale()), null));
			}
			model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);

		}
		catch (final UnknownIdentifierException e)
		{
			LOG.warn("Attempted to load a order that does not exist or is not visible", e);
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "system.error.page.not.found", null);
			return REDIRECT_TO_ORDER_HISTORY_PAGE;
		}
		getContentPageForLabelOrId(ORDER_DETAIL_CMS_PAGE).setFullPagePath(
				getMessageSource().getMessage(ORDER_DETAILS_FULLPAGEPATH, null, getI18nService().getCurrentLocale()));
		storeCmsPageInModel(model, getContentPageForLabelOrId(ORDER_DETAIL_CMS_PAGE));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORDER_DETAIL_CMS_PAGE));
		return getViewForPage(model);
	}

	@GetMapping("/masterOrder/" + UNIT_ID_PATH_VARIABLE_PATTERN + "/"
			+ ORDER_CODE_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public String getMasterOrder(@PathVariable("orderCode")
	final String orderCode, final Model model, @PathVariable("unitId")
	final String unitId, final RedirectAttributes redirectModel, final HttpServletRequest request,
			final HttpServletResponse response) throws CMSItemNotFoundException
	{
		final String sanitizedunitId = SiteoneXSSFilterUtil.filter(unitId);
		final String sanitizedOrderCode = SiteoneXSSFilterUtil.filter(orderCode);

		try
		{
			final MasterHybrisOrderData masterHybrisOrderData = ((SiteOneOrderFacade) orderFacade)
					.getOrdersWithSameHybrisOrderNumber(sanitizedOrderCode);
			model.addAttribute("unitId", sanitizedunitId);
			model.addAttribute("masterHybrisOrderData", masterHybrisOrderData);
			final List<Breadcrumb> breadcrumbs = siteOneAccountBreadcrumbBuilder
					.getBreadcrumbsForAccountDashboardPage(sanitizedunitId);

			breadcrumbs.add(new Breadcrumb("/my-account/orders/" + sanitizedunitId,
					getMessageSource().getMessage("text.account.orderHistory", null, getI18nService().getCurrentLocale()), null));
			model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);

		}
		catch (final UnknownIdentifierException e)
		{
			LOG.warn("Attempted to load a order that does not exist or is not visible", e);
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "system.error.page.not.found", null);
			return REDIRECT_TO_ORDER_HISTORY_PAGE;
		}
		getContentPageForLabelOrId(MASTER_ORDER_DETAIL_CMS_PAGE).setFullPagePath(
				getMessageSource().getMessage(ORDER_DETAILS_FULLPAGEPATH, null, getI18nService().getCurrentLocale()));
		storeCmsPageInModel(model, getContentPageForLabelOrId(MASTER_ORDER_DETAIL_CMS_PAGE));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MASTER_ORDER_DETAIL_CMS_PAGE));
		return ControllerConstants.Views.Pages.Account.MasterOrderPage;
	}

	@GetMapping("/orderdetails/" + ORDER_CODE_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public ResponseEntity<Map<String, Object>> getOrderDetails(@PathVariable("orderCode")
	final String orderCode, @RequestParam(value = "unitId", required = false)
	final String unitId, final Model model) throws CMSItemNotFoundException
	{
		final ConsignmentData consignmentDetails;

		consignmentDetails = ((SiteOneOrderFacade) orderFacade).getMultipleShipmentPage(orderCode, unitId);
		final Map<String, Object> responseMap = new HashMap<>();
		//uom enhancements changes
		if (consignmentDetails != null && consignmentDetails.getTrackingUrl() != null)
		{
			responseMap.put("orderData", consignmentDetails);
		}
		else
		{
			responseMap.put("orderData", null);
		}
		model.addAttribute("orderData", responseMap);
		model.addAttribute("unitId", unitId);		
		return ResponseEntity.ok(responseMap);
	}

	@GetMapping("/order/" + ORDER_CODE_PATH_VARIABLE_PATTERN
			+ "/getReadOnlyProductVariantMatrix")
	@RequireHardLogIn
	public String getProductVariantMatrixForResponsive(@PathVariable("orderCode")
	final String orderCode, @RequestParam("productCode")
	final String productCode, final Model model)
	{
		final OrderData orderData = orderFacade.getOrderDetailsForCodeWithoutUser(orderCode);

		final Map<String, ReadOnlyOrderGridData> readOnlyMultiDMap = orderGridFormFacade.getReadOnlyOrderGridForProductInOrder(
				productCode, Arrays.asList(ProductOption.BASIC, ProductOption.CATEGORIES), orderData);
		model.addAttribute("readOnlyMultiDMap", readOnlyMultiDMap);

		return ControllerConstants.Views.Fragments.Checkout.ReadOnlyExpandedOrderForm;
	}

	@GetMapping("/all-ship-to-popup-orders/" + UNIT_ID_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public String getShipToPagePopupOrders(@PathVariable("unitId")
	final String unitId, @RequestParam(value = "searchParam")
	final String searchParam, @RequestParam(value = "page", defaultValue = "0")
	final int page, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, @RequestParam(value = "dateFromPopup", required = false)
	final String dateFrom, @RequestParam(value = "dateToPopup", required = false)
	final String dateTo, @RequestParam(value = "sort", defaultValue = B2BUnitModel.NAME)
	final String sortCode, @RequestParam(value = "paymentType")
	final String paymentType, final Model model, @RequestHeader(value = "referer", required = false)
	final String referer) throws CMSItemNotFoundException
	{
		final String trimmedSearchParam = searchParam.trim();
		final PageableData pageableData = createPageableData(page, this.getSearchPopUpSize(), sortCode, showMode);
		final SearchPageData<B2BUnitData> searchPageData = ((SiteOneCustomerFacade) getCustomerFacade())
				.getPagedB2BUnits(pageableData, unitId, trimmedSearchParam);
		populateModel(model, searchPageData, showMode);
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		model.addAttribute("action", "manageUnit");
		model.addAttribute("searchParam", trimmedSearchParam);
		model.addAttribute("sortShipToPopupOrder", sortCode);
		model.addAttribute("dateFromOrders", dateFrom);
		model.addAttribute("dateToOrders", dateTo);
		model.addAttribute("paymentType", paymentType);
		storeCmsPageInModel(model, getContentPageForLabelOrId("orderShipToPage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("orderShipToPage"));
		return ControllerConstants.Views.Pages.Account.OrderShipToPagePopup;
	}


	protected BuyItAgainSearchPageData<SearchStateData, BuyItAgainData> performSearch(final String searchQuery, final int page,
			final ShowMode showMode, final String sortCode, final int pageSize)
	{
		final PageableData pageableData = createPageableData(page, pageSize, sortCode, showMode);

		final SearchStateData searchState = new SearchStateData();
		final SearchQueryData searchQueryData = new SearchQueryData();
		searchQueryData.setValue(searchQuery);
		searchState.setQuery(searchQueryData);

		return encodeSearchPageData(buyItAgainSearchFacade.buyItAgainSearch(searchState, pageableData));
	}

	protected BuyItAgainSearchPageData<SearchStateData, BuyItAgainData> encodeSearchPageData(
			final BuyItAgainSearchPageData<SearchStateData, BuyItAgainData> searchPageData)
	{
		final SearchStateData currentQuery = searchPageData.getCurrentQuery();

		if (currentQuery != null)
		{
			try
			{
				final SearchQueryData query = currentQuery.getQuery();
				final String encodedQueryValue = XSSEncoder.encodeHTML(query.getValue());
				query.setValue(encodedQueryValue);
				currentQuery.setQuery(query);
				searchPageData.setCurrentQuery(currentQuery);
				searchPageData.setFreeTextSearch(XSSEncoder.encodeHTML(searchPageData.getFreeTextSearch()));

				final List<FacetData<SearchStateData>> facets = searchPageData.getFacets();
				if (CollectionUtils.isNotEmpty(facets))
				{
					processFacetData(facets);
				}
			}
			catch (final UnsupportedEncodingException e)
			{
				if (LOG.isDebugEnabled())
				{
					LOG.debug("Error occured during Encoding the Search Page data values", e);
				}
			}
		}
		return searchPageData;
	}

	@Override
	protected void processFacetData(final List<FacetData<SearchStateData>> facets) throws UnsupportedEncodingException
	{
		for (final FacetData<SearchStateData> facetData : facets)
		{
			final List<FacetValueData<SearchStateData>> topFacetValueDatas = facetData.getTopValues();
			if (CollectionUtils.isNotEmpty(topFacetValueDatas))
			{
				processFacetDatas(topFacetValueDatas);
			}
			final List<FacetValueData<SearchStateData>> facetValueDatas = facetData.getValues();
			if (CollectionUtils.isNotEmpty(facetValueDatas))
			{
				processFacetDatas(facetValueDatas);
			}
		}
	}

	@Override
	protected void processFacetDatas(final List<FacetValueData<SearchStateData>> facetValueDatas)
			throws UnsupportedEncodingException
	{
		for (final FacetValueData<SearchStateData> facetValueData : facetValueDatas)
		{
			final SearchStateData facetQuery = facetValueData.getQuery();
			final SearchQueryData queryData = facetQuery.getQuery();
			final String queryValue = queryData.getValue();
			if (StringUtils.isNotBlank(queryValue))
			{
				final String[] queryValues = queryValue.split(FACET_SEPARATOR);
				final StringBuilder queryValueBuilder = new StringBuilder();
				queryValueBuilder.append(XSSEncoder.encodeHTML(queryValues[0]));
				for (int i = 1; i < queryValues.length; i++)
				{
					queryValueBuilder.append(FACET_SEPARATOR).append(queryValues[i]);
				}
				queryData.setValue(queryValueBuilder.toString());
			}
		}
	}

	protected void updatePageTitle(final String searchText, final Model model)
	{
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveContentPageTitle(
				getMessageSource().getMessage("search.meta.title", null, "search.meta.title", getI18nService().getCurrentLocale())
						+ " " + searchText));
	}

	protected List<ProductData> getBuyAgainProductList(
			final BuyItAgainSearchPageData<SearchStateData, BuyItAgainData> searchPageData)
	{
		final List<ProductData> productList = new ArrayList<ProductData>();
		if (null != searchPageData.getResults() && !searchPageData.getResults().isEmpty())
		{
			for (final BuyItAgainData buyItAgainData : searchPageData.getResults())
			{
				if (null != buyItAgainData.getProductCode())
				{
					final ProductData product = productFacade.getProductForCodeAndOptions(buyItAgainData.getProductCode(),
							Arrays.asList(ProductOption.BASIC, ProductOption.URL, ProductOption.PRICE, ProductOption.VARIANT_MATRIX_BASE,
									ProductOption.PRICE_RANGE, ProductOption.CATEGORIES, ProductOption.STOCK, ProductOption.PROMOTIONS, ProductOption.IMAGES, ProductOption.AVAILABILITY_MESSAGE));
					
					if(product.getLevel1Category() != null && (product.getLevel1Category().equalsIgnoreCase("Nursery")
							|| product.getLevel1Category().equalsIgnoreCase("vivero")) && product.getIsEligibleForBackorder() != null
							&& product.getIsEligibleForBackorder())
						{
							product.setInventoryCheck(Boolean.FALSE);
							product.setIsSellable(Boolean.TRUE);
						}
						if (!product.getIsProductDiscontinued() && product.getPrice() != null)
					{
						productList.add(product);
					}
					if (product.getPrice() == null)
					{
						LOG.info("buy again product price is null :" + product.getCode() + ", order number: "
								+ buyItAgainData.getOrderNumber());
					}

				}
			}
			siteOneProductFacade.getCSPPriceListForProducts(searchPageData.getResults(), productList);
			siteOneProductFacade.populateAvailablityForBuyAgainPageProducts(productList);
			for(final ProductData prod : productList)
			{
            if(prod.getLevel1Category() != null && (prod.getLevel1Category().equalsIgnoreCase("Nursery")
						|| prod.getLevel1Category().equalsIgnoreCase("vivero")))
					{
						prod.setInventoryCheck(Boolean.FALSE);
						prod.setIsSellable(Boolean.TRUE);
					}
			}
		}
		return productList;
	}

	protected void populateModelForBuyItAgain(final Model model, final BuyItAgainSearchPageData searchPageData,
			final ShowMode showMode, final List<ProductData> productList)
	{
		final int numberPagesShown = getSiteConfigService().getInt(PAGINATION_NUMBER_OF_RESULTS_COUNT, 5);
		model.addAttribute("buyItAgain_numberPagesShown", Integer.valueOf(numberPagesShown));
		model.addAttribute("buyItAgainSearchPageData", searchPageData);
		model.addAttribute("productList", productList);
		model.addAttribute("buyItAgainFilter", SiteoneCoreConstants.BUYITAGAIN_FILTER);
	}

	protected int getSearchPopUpSize()
	{
		return getSiteConfigService().getInt("siteoneorgaddon.searchPopup.pageSize", 10);
	}

	public String setCustomerNoWithDivision(final String customerNumber)
	{
		final BaseSiteModel basesite = baseSiteService.getCurrentBaseSite();
		if (basesite.getUid().equalsIgnoreCase("siteone-us"))
		{
			return customerNumber.concat("_US");
		}
		else
		{
			return customerNumber.concat("_CA");
		}
	}


}
