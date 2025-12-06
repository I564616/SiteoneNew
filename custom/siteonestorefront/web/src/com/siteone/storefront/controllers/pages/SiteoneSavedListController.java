/**
 *
 */
package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorfacades.device.DeviceDetectionFacade;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.ResourceBreadcrumbBuilder;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.search.facetdata.FacetData;
import de.hybris.platform.commerceservices.search.facetdata.FacetValueData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.site.BaseSiteService;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lowagie.text.DocumentException;
import com.sap.security.core.server.csi.XSSEncoder;
import com.siteone.core.savedList.service.SiteoneSavedListService;
import com.siteone.facade.BuyItAgainData;
import com.siteone.facade.BuyItAgainSearchPageData;
import com.siteone.facade.ListSearchPageData;
import com.siteone.facade.location.SiteOneRegionFacade;
import com.siteone.facades.buyitagain.search.facade.BuyItAgainSearchFacade;
import com.siteone.facades.cart.SiteOneCartFacade;
import com.siteone.facades.constants.SiteoneFacadesConstants;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.events.ListSearchFacade;
import com.siteone.facades.exceptions.PdfNotAvailableException;
import com.siteone.facades.exceptions.ServiceUnavailableException;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.product.data.InventoryUOMData;
import com.siteone.facades.savedList.SiteoneSavedListFacade;
import com.siteone.facades.savedList.data.*;
import com.siteone.facades.savedList.data.CustomerPriceData;
import com.siteone.facades.savedList.data.RecommendedListData;
import com.siteone.facades.savedList.data.SavedListData;
import com.siteone.facades.savedList.data.SavedListEntryData;
import com.siteone.facades.savedList.data.SavedListEntryUpdatedUOMData;
import com.siteone.facades.savedList.data.ShareCustomerData;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.facades.wishlist.data.CreateWishlistAndResponseAllData;
import com.siteone.facades.wishlist.data.CreateWishlistResponseData;
import com.siteone.facades.wishlist.data.WishlistAddData;
import com.siteone.storefront.controllers.ControllerConstants;
import com.siteone.storefront.forms.SiteOneEditSavedListForm;
import com.siteone.storefront.forms.SiteOneProductCommentForm;
import com.siteone.storefront.forms.SiteoneSavedListCreateForm;
import com.siteone.storefront.forms.SiteoneSavedListLogoUploadForm;
import com.siteone.storefront.forms.SiteoneShareSavedListForm;
import com.siteone.storefront.util.SiteOneInvoicePDFUtils;
import com.siteone.storefront.util.SiteOneSearchUtils;
import com.siteone.storefront.util.SiteoneXSSFilterUtil;
import com.siteone.storefront.validator.SiteOneCSVUploadListValidator;

import atg.taglib.json.util.JSONException;



/**
 * @author 1003567
 *
 */

@Controller
@Scope("tenant")
@RequestMapping(value = "/savedList")
public class SiteoneSavedListController extends AbstractSearchPageController
{
	/** Setting up Logger for the class to log the messages. */
	private static final Logger LOG = Logger.getLogger(SiteoneSavedListController.class);

	private static final String SAVED_LIST_PAGE = "savedListPage";

	private static final String SAVED_LIST_EDIT_PAGE = "editSavedListPage";
	private static final String BREADCRUMBS_ATTR = "breadcrumbs";
	private static final String CSVCONTENT_ATTR = "csvContent";
	private static final String SAVED_LIST_DETAILS_PAGE = "detailsSavedListPage";
	private static final String RECOMMENDED_LIST_DETAILS_PAGE = "detailsRecommendedListPage";
	private static final String RECOMMENDED_LIST_LANDING_PAGE = "recommendedListPage";
	private static final String GENARATE_ESTIMATE_PAGE = "genarateEstimatePage";
	private static final String TEXT_ACCOUNT_SAVEDLIST = "text.account.savedList";
	private static final String TEXT_ACCOUNT_DASHBOARD = "text.account.addressBook.dashboard";
	private static final String ERROR_ID_PATH_VARIABLE = "{errorId:.*}";
	private static final String MY_ACCOUNT_DASHBOARD_URL = "/my-account/account-dashboard";

	private static final String REDIRECT_TO_CREATE_PAGE = REDIRECT_PREFIX + "/savedList/createList";

	private static final String REDIRECT_TO_DETAILS_PAGE = REDIRECT_PREFIX + "/savedList/listDetails";

	private static final String REDIRECT_TO_EDIT_PAGE = REDIRECT_PREFIX + "/savedList/editList";

	private static final String REDIRECT_TO_LANDING_PAGE = REDIRECT_PREFIX + "/savedList";

	private static final String UPLOAD_LIST_PAGE = "importCSVSavedListPage";

	private static final String IMPORT_CSV_FILE_MAX_SIZE_BYTES_KEY = "import.csv.file.max.size.bytes";

	private static final String REDIRECT_URL_UPLOADLIST = REDIRECT_PREFIX + "/savedList/uploadList";

	private static final String REDIRECT_CART_URL = REDIRECT_PREFIX + "/cart";

	private static final String REDIRECT_TO_ESTIMATE_PAGE = REDIRECT_PREFIX + "/savedList/generateEstimate?wishListCode=";

	private static final String DESCENDING_ORDER = "desc";

	private static final String LIST_LANDING_URL = "/savedList";
	private static final String RECOMMENDEDLIST_LANDING_URL ="/savedList/recommendedList";
	private static final String LIST_DETAILS_URL = "/savedList/listDetails?code=";

	private static final String LIST_PAGE_TITLE = "Lists";
	private static final String RECOMMENDEDLIST_PAGE_TITLE = "RecommendedLists";

	private static final String LISTS_FULLPAGEPATH = "analytics.fullpath.lists";
	private static final String CREATE_LIST_FULLPAGEPATH = "analytics.fullpath.create.list";
	private static final String P_WISHLIST = "p_wishlist";


	private static final String CACHE_CONTROL = "Cache-Control";
	private static final String REVALIDATE = "must-revalidate, post-check=0, pre-check=0";
	private static final String PAGINATION_NUMBER_OF_RESULTS_COUNT = "pagination.number.results.count";
	private static final String PRAGMA = "Pragma";
	private static final String PUBLIC = "public";
	private static final String CONTENT_DISPOSITION = "Content-Disposition";
	private static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
	private static final String EXCEPTION_PDF = "Exception while creating PDF : ";
	private static final String SAVEDLISTDATA = "savedListData";

	private static final String SEARCH_META_DESCRIPTION_ON = "search.meta.description.on";
	private static final String SEARCH_META_DESCRIPTION_RESULTS = "search.meta.description.results";
	private static final String NO_RESULTS_CMS_PAGE_ID = "searchEmpty";
	
	private static final String SITEONE_US = "siteone-us";
	private static final String SITEONE_CA = "siteone-ca";

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "listSearchFacade")
	private ListSearchFacade listSearchFacade;

	@Resource(name = "deviceDetectionFacade")
	private DeviceDetectionFacade deviceDetectionFacade;

	@Resource(name = "siteoneSavedListFacade")
	private SiteoneSavedListFacade siteoneSavedListFacade;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;

	@Resource(name = "siteOneSearchUtils")
	private SiteOneSearchUtils siteOneSearchUtils;

	@Resource(name = "cartFacade")
	private SiteOneCartFacade siteOneCartFacade;

	@Resource(name = "siteOneCSVUploadListValidator")
	private SiteOneCSVUploadListValidator siteOneCSVUploadListValidator;

	@Resource(name = "eventService")
	private EventService eventService;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Resource(name = "siteoneSavedListService")
	private SiteoneSavedListService siteoneSavedListService;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	@Resource(name = "siteOneRegionFacade")
	private SiteOneRegionFacade siteOneRegionFacade;

	@Resource(name = "b2bUnitFacade")
	private SiteOneB2BUnitFacade b2bUnitFacade;

	@Resource(name = "buyItAgainSearchFacade")
	private BuyItAgainSearchFacade buyItAgainSearchFacade;


	@Resource(name = "productVariantFacade")
	private ProductFacade productFacade;

	@GetMapping
	@RequireHardLogIn
	public String getAllSavedList(final Model model, @RequestParam(value = "page", defaultValue = "0", required = false)
	final int page, @RequestParam(value = "sortOrder", defaultValue = DESCENDING_ORDER, required = false)
	final String sortOrder, @RequestParam(value = "listDeleted", required = false)
	final String listDeleted) throws CMSItemNotFoundException
	{
		final PageableData pageableData = this.createPageableData(page, this.getSearchPageSize(), null, ShowMode.Page);
		final boolean isAssembly = false;
		final SearchPageData searchPageData = siteoneSavedListFacade.getAllSavedList(pageableData, isAssembly, sortOrder);
		populateModel(model, searchPageData, ShowMode.Page);
		final List<SavedListData> savedListData = searchPageData.getResults();
		getContentPageForLabelOrId(SAVED_LIST_PAGE)
				.setFullPagePath(getMessageSource().getMessage(LISTS_FULLPAGEPATH, null, getI18nService().getCurrentLocale()));
		storeCmsPageInModel(model, getContentPageForLabelOrId(SAVED_LIST_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(SAVED_LIST_PAGE));
		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb(MY_ACCOUNT_DASHBOARD_URL,
				getMessageSource().getMessage(TEXT_ACCOUNT_DASHBOARD, null, getI18nService().getCurrentLocale()), null));
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveContentPageTitle(LIST_PAGE_TITLE));
		sessionService.setAttribute("RecommendedList", "LandingPage");
		final List<RecommendedListData> recommendedListData = performSearch(null, 0, ShowMode.Page, null, 50);
		sessionService.setAttribute("RecommendedList", "");
		model.addAttribute("recommendedList", recommendedListData);
		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);
		model.addAttribute(SAVEDLISTDATA, savedListData);
		final SiteoneShareSavedListForm siteoneShareSavedListForm = new SiteoneShareSavedListForm();
		model.addAttribute("siteoneShareSavedListForm", siteoneShareSavedListForm);
		model.addAttribute("sortOrder", sortOrder);
		model.addAttribute("isListDeleted", listDeleted);
		model.addAttribute("savedListCount", siteoneSavedListFacade.getAllSavedListCount(isAssembly));
		return ControllerConstants.Views.Pages.SavedList.SavedListLandingPage;
	}
	
	@GetMapping("/recommendedList")
	@RequireHardLogIn
	public String getAllRecommendedList(final Model model, @RequestParam(value = "page", defaultValue = "0", required = false)
	final int page, @RequestParam(value = "sortOrder", defaultValue = DESCENDING_ORDER, required = false)
	final String sortOrder) throws CMSItemNotFoundException
	{	
		final PageableData pageableData = this.createPageableData(page, this.getSearchPageSize(), null, ShowMode.Page);
		final boolean isAssembly = false;
		final SearchPageData searchPageData = siteoneSavedListFacade.getAllSavedList(pageableData, isAssembly, sortOrder);
		populateModel(model, searchPageData, ShowMode.Page);
		final List<SavedListData> savedListData = searchPageData.getResults();
		sessionService.setAttribute("RecommendedList", "LandingPage");
		final List<RecommendedListData> recommendedListData = performSearch(null, 0, ShowMode.Page, null, 50);
		sessionService.setAttribute("RecommendedList", "");
      final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
      breadcrumbs.add(new Breadcrumb(MY_ACCOUNT_DASHBOARD_URL,
		getMessageSource().getMessage(TEXT_ACCOUNT_DASHBOARD, null, getI18nService().getCurrentLocale()), null));
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveContentPageTitle(RECOMMENDEDLIST_PAGE_TITLE));
		storeCmsPageInModel(model, getContentPageForLabelOrId(RECOMMENDED_LIST_LANDING_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(RECOMMENDED_LIST_LANDING_PAGE));
		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);
		model.addAttribute("sortOrder", sortOrder);
		model.addAttribute(SAVEDLISTDATA, savedListData);
		model.addAttribute("recommendedList", recommendedListData);
		model.addAttribute("savedListCount", siteoneSavedListFacade.getAllSavedListCount(isAssembly));
		return ControllerConstants.Views.Pages.SavedList.RecommendListLandingPage;
	}

	@GetMapping("/createList")
	@RequireHardLogIn
	public String createList(final Model model) throws CMSItemNotFoundException
	{
		return getCreateorUploadListPage(model, null);
	}

	@PostMapping("/createList")
	@RequireHardLogIn
	public String createSavedList(final SiteoneSavedListCreateForm siteoneSavedListForm, final Model model,
			final RedirectAttributes redirectModel, final BindingResult bindingResult, final HttpServletResponse response,
			final HttpServletRequest request) throws IOException, CMSItemNotFoundException
	{
		final SavedListData savedListData = new SavedListData();
		final String sanitizedName = SiteoneXSSFilterUtil.filter(siteoneSavedListForm.getName());
		final String sanitizedDescription = SiteoneXSSFilterUtil.filter(siteoneSavedListForm.getDescription());
		savedListData.setName(sanitizedName);
		savedListData.setDescription(sanitizedDescription);
		final Cookie newCookie = new Cookie("uploadFail", null);
		newCookie.setMaxAge(10 * 24 * 60 * 60);
		newCookie.setPath("/");
		newCookie.setSecure(true);
		final boolean isAssembly = false;
		if (siteoneSavedListForm.getCsvFile() != null && !(siteoneSavedListForm.getCsvFile().isEmpty()))
		{
			siteOneCSVUploadListValidator.validate(siteoneSavedListForm, bindingResult);

			String csvContent = null;
			String code = null;
			if (bindingResult.hasErrors())
			{
				final String errorMessage = getMessageSource().getMessage(bindingResult.getAllErrors().get(0).getCode(), null,
						getI18nService().getCurrentLocale());
				if (errorMessage.contains("The file uploaded is empty. Please select a new file and upload again."))
				{
					newCookie.setValue("uploadEmpty");
					response.addCookie(newCookie);
				}
				if (errorMessage.contains("The file uploaded exceeds 100 products limit. Please select a new file and upload again."))
				{
					newCookie.setValue("uploadExceeded");
					response.addCookie(newCookie);
				}
				return getCreateorUploadListPage(model, savedListData);
			}
			else
			{
				try (final InputStream inputStream = siteoneSavedListForm.getCsvFile().getInputStream())
				{
					final StringBuilder listCode = siteoneSavedListFacade.createSavedListFromUploadedCSV(inputStream, savedListData);
					final String[] products = listCode.toString().split(";");
					code = products[0].trim();
					if (products.length >= 2)
					{
						final String failProducts = products[1].trim();
						csvContent = siteoneSavedListFacade.getUploadListErrorProducts(failProducts);
						model.addAttribute(CSVCONTENT_ATTR, csvContent);
						response.addCookie(newCookie);
					}
					else
					{
						if (code.equalsIgnoreCase("null"))
						{
							newCookie.setValue("uploadEmpty");
							response.addCookie(newCookie);
							return getCreateorUploadListPage(model, savedListData);
						}
					}
					if (code.equalsIgnoreCase("null") || code.equals(""))
					{

						newCookie.setValue("uploadError");
						response.addCookie(newCookie);
						model.addAttribute(CSVCONTENT_ATTR, csvContent);
						return getCreateorUploadListPage(model, savedListData);
					}

				}
				catch (final IOException e)
				{
					if (LOG.isDebugEnabled())
					{
						LOG.debug(e.getMessage(), e);
					}

				}

			}
			if (null != csvContent)
			{
				model.addAttribute(CSVCONTENT_ATTR, csvContent);
			}
			newCookie.setValue("uploadSuccess");
			response.addCookie(newCookie);
			model.addAttribute("listCode", code);
			return getCreateorUploadListPage(model, null);
		}
		final String code = siteoneSavedListFacade.createSavedList(savedListData, siteoneSavedListForm.getProduct(), isAssembly);
		if (null == code)
		{
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "saved.list.duplicate", null);
			return REDIRECT_TO_CREATE_PAGE;
		}
		getContentPageForLabelOrId(SAVED_LIST_PAGE)
				.setFullPagePath(getMessageSource().getMessage(CREATE_LIST_FULLPAGEPATH, null, getI18nService().getCurrentLocale()));
		storeCmsPageInModel(model, getContentPageForLabelOrId(SAVED_LIST_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(SAVED_LIST_PAGE));

		return REDIRECT_TO_DETAILS_PAGE + "?code=" + code;
	}

	public String getCreateorUploadListPage(final Model model, final SavedListData savedListData) throws CMSItemNotFoundException
	{
		LOG.debug("Rendering Create List");
		getPreparedCreatePage(model);
		final SiteoneSavedListCreateForm siteoneSavedListCreateForm = new SiteoneSavedListCreateForm();
		model.addAttribute("csvFileMaxSize", getSiteConfigService().getLong(IMPORT_CSV_FILE_MAX_SIZE_BYTES_KEY, 0));
		if (savedListData != null)
		{
			siteoneSavedListCreateForm.setName(savedListData.getName());
			siteoneSavedListCreateForm.setDescription(savedListData.getDescription());
		}
		model.addAttribute("siteoneSavedListCreateForm", siteoneSavedListCreateForm);
		getBreadcrumbForPage(model);
		model.addAttribute("isReturning", "returning");
		getContentPageForLabelOrId(SAVED_LIST_PAGE)
				.setFullPagePath(getMessageSource().getMessage(CREATE_LIST_FULLPAGEPATH, null, getI18nService().getCurrentLocale()));
		storeCmsPageInModel(model, getContentPageForLabelOrId(SAVED_LIST_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(SAVED_LIST_PAGE));
		return getViewForPage(model);
	}

	@GetMapping(path = "/downloadErrorCSVFile/" + ERROR_ID_PATH_VARIABLE)
	@RequireHardLogIn
	public void getUploadErrorReport(@PathVariable("errorId")
	final String errorId, final Model model, final RedirectAttributes redirectModel, final HttpServletResponse response,
			final HttpServletRequest request)
	{
		try
		{
			final byte[] csvContent = SiteOneInvoiceCSVUtils
					.createUploadListErrorProduct(siteoneSavedListService.getErrorListbyId(errorId));
			response.setHeader(CACHE_CONTROL, REVALIDATE);
			response.setHeader(PRAGMA, PUBLIC);
			response.setHeader(CONTENT_DISPOSITION, "attachment;filename=SavedListErrorReport.csv");
			response.setContentType(APPLICATION_OCTET_STREAM);
			response.setContentLength(csvContent.length);
			response.getOutputStream().write(csvContent);
			response.getOutputStream().flush();
		}
		catch (final ServiceUnavailableException serviceUnavailableException)
		{
			LOG.error("Not able to access pdf", serviceUnavailableException);
		}
		catch (final PdfNotAvailableException pdfNotAvailableException)
		{
			LOG.error("Pdf is currently not available", pdfNotAvailableException);
		}
		catch (final IOException e)
		{
			LOG.error(EXCEPTION_PDF + e);
			LOG.error(e.getMessage(), e);
		}
	}

	@GetMapping(value = "/listDetails", params = "!q")
	@RequireHardLogIn
	public String getDetailsPage(final String code, final Model model, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, @RequestParam(value = "page", defaultValue = "0", required = false)
	final int page, @RequestParam(value = "pagesize", required = false)
	final String pageSize, @RequestParam(value = "sortOrder", defaultValue = DESCENDING_ORDER, required = false)
	final String sortOrder, @RequestParam(value = "searchParam", required = false)
	final String searchParam, @RequestParam(value = "selectedProducts", required = false)
	final String selectedProducts) throws CMSItemNotFoundException
	{
		LOG.debug("POST call of SavedListDeatils");
		sessionService.setAttribute("WishlistCode", code);
		int viewByPageSize = getSiteConfigService().getInt("siteone.savedlistentrydefault.pageSize", 50);
		if (null != pageSize)
		{
			viewByPageSize = Integer.parseInt(pageSize);
		}
		final SavedListData savedListData = siteoneSavedListFacade.getListDetailsPage(code);

		final List<String> productCodes = new ArrayList<String>();
		if (null != savedListData && CollectionUtils.isNotEmpty(savedListData.getRemovedProductNameList()))
		{
			GlobalMessages.addMessage(model, GlobalMessages.CONF_MESSAGES_HOLDER, "list.product.nla.message", new Object[]
			{ savedListData.getRemovedProductNameList() });
		}
		final PageableData pageableData = this.createPageableData(page, viewByPageSize, null, ShowMode.Page);
		final SearchPageData searchPageData = siteoneSavedListFacade.getPagedDetailsPage(pageableData, code);
		final List<SavedListEntryData> savedListEntryData = searchPageData.getResults();
		SavedListData listData = new SavedListData();
		ListSearchPageData<SearchStateData, SavedListEntryData> searchListPageData = null;
		try
		{
			final String searchText = "";
			final String sortCode = "date-asc";
			String trimmedSearchParam = null;
			if (null != searchParam)
			{
				trimmedSearchParam = searchParam.trim();
			}
			if (null != trimmedSearchParam)
			{
				searchListPageData = performListSearch(trimmedSearchParam, page, showMode, sortCode, viewByPageSize);
			}
			else
			{
				searchListPageData = performListSearch(searchText, page, showMode, sortCode, viewByPageSize);
			}


		}
		catch (final ConversionException e) // NOSONAR
		{
			// nothing to do - the exception is logged in SearchSolrQueryPopulator
		}
		siteOneSearchUtils.attachfacetValueName(searchListPageData, model);
		
		//final List<SavedListEntryData> savedListEntryData = new ArrayList<>(searchPageData.getResults());
		//savedListEntryData.removeIf(entry -> (BooleanUtils.isTrue(entry.getProduct().getIsProductOffline())));

		if (CollectionUtils.isNotEmpty(savedListEntryData))
		{
			for (final SavedListEntryData data : savedListEntryData)
			{
				productCodes.add(data.getProduct().getCode());
			}
			LOG.error("Product Size List Page" + productCodes.size() + "page size" + productCodes + "product code"
					+ searchPageData.getPagination().getNumberOfPages() + "total no of result"
					+ searchPageData.getPagination().getTotalNumberOfResults());
		if (null != savedListData)
		{
			if (searchPageData.getPagination().getNumberOfPages() > 1 || savedListData.getEntries().size() >= 50)
			{
				LOG.error("Inside above 50 product");
				listData = siteoneSavedListFacade.getCSPListDetailsPage(code, productCodes);
				siteoneSavedListFacade.setPriceForEntryData(listData.getCustomerPriceData(), savedListEntryData,listData.getRetailPriceData());
			}
			else
			{
				LOG.error("Inside below 50 product");
				siteoneSavedListFacade.setPriceForEntryData(savedListData.getCustomerPriceData(), savedListEntryData,savedListData.getRetailPriceData());
			}
		}
			final int savedListLen = savedListEntryData.size();
			for (int i = 0; i < savedListLen; i++)
			{
				final ProductData productData = savedListEntryData.get(i).getProduct();
				final ProductData productDataUpdated = siteOneProductFacade.updateUOMPriceForSingleUOM(productData);

				final List<InventoryUOMData> inventoryUOMListData = productData.getSellableUoms();
				if (inventoryUOMListData != null && inventoryUOMListData.size() > 1)
				{
					siteoneSavedListFacade.updatePriceBasedOnUOM(savedListEntryData.get(i));
				}

				savedListEntryData.get(i).setProduct(productDataUpdated);
				savedListEntryData.get(i).setTotalPrice(siteoneSavedListFacade.setItemTotal(savedListEntryData.get(i)));
			}
		}

		model.addAttribute("sortOrder", sortOrder);
		model.addAttribute("selectedProducts", selectedProducts);

		List<CustomerPriceData> siteOneCSPList = null;
		if (null != savedListData)
		{
			savedListData.setListTotalPrice(siteoneSavedListFacade.setListTotal(savedListEntryData));
			if (searchPageData.getPagination().getNumberOfPages() > 1 || savedListData.getEntries().size() >= 50)
			{
				siteOneCSPList = siteoneSavedListFacade.updatePriceListBasedOnUOM(listData, savedListEntryData);
			}
			else
			{
				siteOneCSPList = siteoneSavedListFacade.updatePriceListBasedOnUOM(savedListData, savedListEntryData);
			}
		}
		model.addAttribute("savedListEntryData", savedListEntryData);
		model.addAttribute(SAVEDLISTDATA, savedListData);
		model.addAttribute("listSearchPageData", searchListPageData);
		model.addAttribute("siteOneCSPList", siteOneCSPList);
		final SiteoneShareSavedListForm siteoneShareSavedListForm = new SiteoneShareSavedListForm();
		final SiteOneProductCommentForm siteOneProductCommentForm = new SiteOneProductCommentForm();
		siteoneShareSavedListForm.setCode(code);
		siteOneProductCommentForm.setListCode(code);
		model.addAttribute("siteoneShareSavedListForm", siteoneShareSavedListForm);
		model.addAttribute("siteOneProductCommentForm", siteOneProductCommentForm);
		final boolean isAssembly = false;
		final List<SavedListData> allWishlist = siteoneSavedListFacade.getAllSavedListForEdit(isAssembly);
		int i = 0;
		for (final SavedListData data : allWishlist)
		{
			if (data.getCode().equals(code))
			{
				allWishlist.remove(i);
				break;
			}
			i++;
		}
		model.addAttribute("savedLists", allWishlist);
		model.addAttribute("isReturning", "returning");
		model.addAttribute("viewByPageSize", viewByPageSize);
		model.addAttribute("code", code);
		getBreadcrumbForPage(model);
		populateModel(model, searchPageData, ShowMode.Page);
		storeCmsPageInModel(model, getContentPageForLabelOrId(SAVED_LIST_DETAILS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(SAVED_LIST_DETAILS_PAGE));
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveContentPageTitle(savedListData.getName()));
		return ControllerConstants.Views.Pages.SavedList.SavedListDetailsPage;

	}


	@GetMapping(value = "/listDetails", params = "q")
	@RequireHardLogIn
	public String getRefineSearch(@RequestParam(value = "q")
	final String searchQuery, final Model model, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, @RequestParam(value = "page", defaultValue = "0", required = false)
	final int page, @RequestParam(value = "pagesize", required = false)
	final String pageSize, @RequestParam(value = "sortOrder", defaultValue = DESCENDING_ORDER, required = false)
	final String sortOrder, @RequestParam(value = "searchParam", required = false)
	final String searchParam, @RequestParam(value = "selectedProducts", required = false)
	final String selectedProducts) throws CMSItemNotFoundException
	{
		LOG.debug("POST call of SavedListDeatils Query");
		final String code = sessionService.getAttribute("WishlistCode");
		int viewByPageSize = getSiteConfigService().getInt("siteone.savedlistentrydefault.pageSize", 50);
		if (null != pageSize)
		{
			viewByPageSize = Integer.parseInt(pageSize);
		}
		final SavedListData savedListData = siteoneSavedListFacade.getListDetailsPage(code);
		LOG.error("Total Entries " + savedListData.getTotalEntries());
		LOG.error("Total Entries from Entries " + savedListData.getEntries().size());
		final List<String> productCodes = new ArrayList<String>();
		if (null != savedListData && CollectionUtils.isNotEmpty(savedListData.getRemovedProductNameList()))
		{
			GlobalMessages.addMessage(model, GlobalMessages.CONF_MESSAGES_HOLDER, "list.product.nla.message", new Object[]
			{ savedListData.getRemovedProductNameList() });
		}
		ListSearchPageData<SearchStateData, SavedListEntryData> searchPageData = null;
		try
		{
			final String searchText = "";
			final String sortCode = "date-asc";
			if (null != searchQuery)
			{
				searchPageData = performListSearch(searchQuery, page, showMode, sortCode, viewByPageSize);
			}
			else
			{
				searchPageData = performListSearch(searchText, page, showMode, sortCode, viewByPageSize);
			}


		}
		catch (final ConversionException e) // NOSONAR
		{
			// nothing to do - the exception is logged in SearchSolrQueryPopulator
		}
		siteOneSearchUtils.attachfacetValueName(searchPageData, model);
		final List<SavedListEntryData> savedListEntryData = new ArrayList<>(searchPageData.getResults());
		savedListEntryData.removeIf(entry -> (BooleanUtils.isTrue(entry.getProduct().getIsProductOffline())));
		SavedListData listData = new SavedListData();
		if (CollectionUtils.isNotEmpty(savedListEntryData))
		{
			for (final SavedListEntryData data : savedListEntryData)
			{
				productCodes.add(data.getProduct().getCode());
			}
			LOG.error("Product Size List Page" + productCodes.size() + "page size" + productCodes + "product code"
					+ searchPageData.getPagination().getNumberOfPages() + "total no of result"
					+ searchPageData.getPagination().getTotalNumberOfResults());

			if (searchPageData.getPagination().getNumberOfPages() > 1 || savedListData.getEntries().size() >= 50)
			{
				LOG.error("Inside above 50 product");
				listData = siteoneSavedListFacade.getCSPListDetailsPage(code, productCodes);
				siteoneSavedListFacade.setPriceForEntryData(listData.getCustomerPriceData(), savedListEntryData,listData.getRetailPriceData());
			}
			else
			{
				LOG.error("Inside below 50 product");
				siteoneSavedListFacade.setPriceForEntryData(savedListData.getCustomerPriceData(), savedListEntryData,savedListData.getRetailPriceData());
			}

			final int savedListLen = savedListEntryData.size();
			for (int i = 0; i < savedListLen; i++)
			{
				final ProductData productData = savedListEntryData.get(i).getProduct();
				final ProductData productDataUpdated = siteOneProductFacade.updateUOMPriceForSingleUOM(productData);

				final List<InventoryUOMData> inventoryUOMListData = productData.getSellableUoms();
				if (inventoryUOMListData != null && inventoryUOMListData.size() > 1)
				{
					siteoneSavedListFacade.updatePriceBasedOnUOM(savedListEntryData.get(i));
				}

				savedListEntryData.get(i).setProduct(productDataUpdated);
				savedListEntryData.get(i).setTotalPrice(siteoneSavedListFacade.setItemTotal(savedListEntryData.get(i)));
			}
		}

		model.addAttribute("sortOrder", sortOrder);
		model.addAttribute("selectedProducts", selectedProducts);

		List<CustomerPriceData> siteOneCSPList = null;
		if (null != savedListData)
		{
			savedListData.setListTotalPrice(siteoneSavedListFacade.setListTotal(savedListEntryData));
			if (searchPageData.getPagination().getNumberOfPages() > 1 || savedListData.getEntries().size() >= 50)
			{
				siteOneCSPList = siteoneSavedListFacade.updatePriceListBasedOnUOM(listData, savedListEntryData);
			}
			else
			{
				siteOneCSPList = siteoneSavedListFacade.updatePriceListBasedOnUOM(savedListData, savedListEntryData);
			}
		}
		model.addAttribute("savedListEntryData", savedListEntryData);
		model.addAttribute(SAVEDLISTDATA, savedListData);
		model.addAttribute("listSearchPageData", searchPageData);
		model.addAttribute("siteOneCSPList", siteOneCSPList);
		final SiteoneShareSavedListForm siteoneShareSavedListForm = new SiteoneShareSavedListForm();
		final SiteOneProductCommentForm siteOneProductCommentForm = new SiteOneProductCommentForm();
		siteoneShareSavedListForm.setCode(code);
		siteOneProductCommentForm.setListCode(code);
		model.addAttribute("siteoneShareSavedListForm", siteoneShareSavedListForm);
		model.addAttribute("siteOneProductCommentForm", siteOneProductCommentForm);
		final boolean isAssembly = false;
		final List<SavedListData> allWishlist = siteoneSavedListFacade.getAllSavedListForEdit(isAssembly);
		int i = 0;
		for (final SavedListData data : allWishlist)
		{
			if (data.getCode().equals(code))
			{
				allWishlist.remove(i);
				break;
			}
			i++;
		}
		model.addAttribute("savedLists", allWishlist);
		model.addAttribute("isReturning", "returning");
		model.addAttribute("viewByPageSize", viewByPageSize);
		model.addAttribute("code", code);
		getBreadcrumbForPage(model);
		populateModel(model, searchPageData, ShowMode.Page);
		storeCmsPageInModel(model, getContentPageForLabelOrId(SAVED_LIST_DETAILS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(SAVED_LIST_DETAILS_PAGE));
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveContentPageTitle(savedListData.getName()));
		return ControllerConstants.Views.Pages.SavedList.SavedListDetailsPage;

	}


	protected void getPreparedCreatePage(final Model model)
	{
		final CustomerData customerData = customerFacade.getCurrentCustomer();
		final SavedListData savedListData = new SavedListData();
		savedListData.setUser(customerData.getName());
		savedListData.setCreatedBy(customerData.getName());
		model.addAttribute("currentUser", customerData.getName());
		getBreadcrumbForPage(model);
		model.addAttribute("createdDate", siteoneSavedListFacade.createdDate());
	}

	@PostMapping("/displayProduct")
	@ResponseBody
	public ProductData getProductToDisplay(@RequestParam(value = "productCodes", required = false)
	final String productCodes, final RedirectAttributes redirectModel)
	{
		ProductData productData = new ProductData();
		if (productCodes != null)
		{
			try
			{
				productData = siteoneSavedListFacade.getProductDatatoDisplay(productCodes.trim());
			}
			catch (final UnknownIdentifierException e)
			{
				LOG.debug("Product Code not present: " + productCodes);
				LOG.error(e.getMessage(), e);
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "saved.list.product.not.found",
						null);
				productData = null;
			}
		}
		return productData;
	}

	@GetMapping("/editList")
	@RequireHardLogIn
	public String editList(@RequestParam(value = "code", required = false)
	final String code, final Model model) throws CMSItemNotFoundException
	{
		LOG.debug("Rendering Edit List");
		final SavedListData savedListData = getSavedListDetails(code);
		final SiteOneEditSavedListForm siteOneEditSavedListForm = new SiteOneEditSavedListForm();
		siteOneEditSavedListForm.setName(savedListData.getName());
		siteOneEditSavedListForm.setDescription(savedListData.getDescription());
		siteOneEditSavedListForm.setListName(savedListData.getName());
		siteOneEditSavedListForm.setOwner(savedListData.getUser());
		siteOneEditSavedListForm.setCode(savedListData.getCode());
		model.addAttribute("siteOneEditSavedListForm", siteOneEditSavedListForm);
		getBreadcrumbForPage(model);
		storeCmsPageInModel(model, getContentPageForLabelOrId(SAVED_LIST_EDIT_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(SAVED_LIST_EDIT_PAGE));
		return getViewForPage(model);
	}

	@PostMapping("/editList")
	@RequireHardLogIn
	public String editSavedList(final SiteOneEditSavedListForm siteOneEditSavedListForm, final RedirectAttributes redirectModel)
	{
		final SavedListData savedListData = new SavedListData();
		final String sanitizedName = SiteoneXSSFilterUtil.filter(siteOneEditSavedListForm.getName());
		final String sanitizedDescription = SiteoneXSSFilterUtil.filter(siteOneEditSavedListForm.getDescription());
		savedListData.setName(sanitizedName);
		savedListData.setDescription(sanitizedDescription);
		savedListData.setCode(siteOneEditSavedListForm.getCode().replaceAll(",", ""));
		final boolean isAssembly = false;
		final boolean flag = siteoneSavedListFacade.updateSavedList(savedListData, siteOneEditSavedListForm.getListName(),
				isAssembly);
		if (!flag)
		{
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "saved.list.duplicate", null);
			return REDIRECT_TO_EDIT_PAGE + "?code=" + savedListData.getCode();
		}
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "saved.list.editSuccessMessage", null);
		return REDIRECT_TO_DETAILS_PAGE + "?code=" + savedListData.getCode();
	}

	@ResponseBody
	@PostMapping("/add")
	protected WishlistAddData addtoWishlist(@RequestParam("productCode")
	final String productCode, @RequestParam(value = "quantity", required = false)
	final String quantity, @RequestParam("wishListCode")
	final String wishListCode, final Model model, @RequestParam(value = "prodQtyFlag", defaultValue = "false", required = false)
	final boolean prodQtyFlag, @RequestParam(value = "inventoryUOMId", required = false)
	final String inventoryUOMId) throws CMSItemNotFoundException
	{
		WishlistAddData wishlistAddResponseData = new WishlistAddData();
		try
		{

			wishlistAddResponseData = siteoneSavedListFacade.addtoWishlist(productCode, quantity, wishListCode, prodQtyFlag,
					inventoryUOMId);

		}
		catch (final UnknownIdentifierException e)
		{
			LOG.debug("Product Code not present: " + productCode);
			LOG.error(e.getMessage(), e);
			model.addAttribute(P_WISHLIST, Boolean.FALSE);
		}

		model.addAttribute(wishlistAddResponseData);
		model.addAttribute(P_WISHLIST, Boolean.TRUE);
		return wishlistAddResponseData;
	}

	@ResponseBody
	@PostMapping(value = "/addSelectedToNewWishlist")
	public String addSelectedToNewWishlist(@RequestParam("wishListName")
	final String wishListName, @RequestParam("productCodes")
	final String productCodes, @RequestParam("currentWishlist")
	final String currentWishlist)
	{
		return siteoneSavedListFacade.addSelectedToNewWishlist(wishListName, productCodes, currentWishlist, false);
	}

	@ResponseBody
	@PostMapping(value = "/addSingleProductToNewWishlist")
	public String addSingleProductToNewWishlist(@RequestParam("wishListName")
	final String wishListName, @RequestParam("productCodes")
	final String productCodes, @RequestParam("currentWishlist")
	final String currentWishlist)
	{
		return siteoneSavedListFacade.addSelectedToNewWishlist(wishListName, productCodes, currentWishlist, false);
	}

	@PostMapping(value = "/addSelectedToSavedWishlist")
	protected @ResponseBody List<WishlistAddData> addSelectedToSavedWishlist(@RequestParam("wishListCode")
	final String wishListCode, @RequestParam(value = "productItemNumbers", required = false)
	final String productItemNumbers, @RequestParam(value = "currentWishlist", required = false)
	final String currentWishlist, @RequestParam(value = "categoryName", required = false)
	final String categoryName, final Model model)
	{
		List<String> products = new ArrayList<>();
		if (StringUtils.isEmpty(categoryName))
		{
			products = Arrays.asList(productItemNumbers.split(" "));
		}
		else
		{
			sessionService.setAttribute("RecommendedDetailsPage", categoryName);
			final int viewByPageSize = getSiteConfigService().getInt("siteone.reccomendedlistentrydefault.pageSize", 50);
			final String productList = performSearchForCategory(null, 0, ShowMode.Page, null, viewByPageSize);
			sessionService.setAttribute("RecommendedDetailsPage", "");
			products = Arrays.asList(productList.split(","));
		}

		final List<WishlistAddData> wishlistAddResponseDataList = new ArrayList<>();

		for (final String product : products)
		{
			try
			{
				if (StringUtils.isNotEmpty(product))
				{
					final String[] arr = product.split(SiteoneFacadesConstants.PIPE);
					if (arr.length > 2)
					{
						wishlistAddResponseDataList.add(siteoneSavedListFacade.addtoWishlist(arr[SiteoneFacadesConstants.ZERO],
								arr[SiteoneFacadesConstants.ONE], wishListCode, false, arr[SiteoneFacadesConstants.TWO]));
					}
					else
					{
						wishlistAddResponseDataList.add(siteoneSavedListFacade.addtoWishlist(arr[SiteoneFacadesConstants.ZERO],
								arr[SiteoneFacadesConstants.ONE], wishListCode, false, null));
					}

					if (StringUtils.isNotEmpty(currentWishlist))
					{
						siteoneSavedListFacade.moveToSaveList(currentWishlist, arr[SiteoneFacadesConstants.ZERO], false);
					}
				}
			}
			catch (final UnknownIdentifierException e)
			{
				LOG.debug("Product Code is not present: " + product);
				LOG.error(e.getMessage(), e);
				model.addAttribute(P_WISHLIST, Boolean.FALSE);
			}
		}
		return wishlistAddResponseDataList;
	}

	@GetMapping("/deleteList")
	@RequireHardLogIn
	public String deleteSavedList(@RequestParam(value = "code", required = false)
	final String code)
	{
		siteoneSavedListFacade.deleteSavedList(code);
		return REDIRECT_TO_LANDING_PAGE + "?listDeleted=success";
	}

	@PostMapping("/shareList")
	@RequireHardLogIn
	public @ResponseBody String saveShareListData(@RequestBody
	final SiteoneShareSavedListForm siteoneShareSavedListForm)
	{
		LOG.info("Inside share");
		final String note = siteoneShareSavedListForm.getNote();
		final String owners = siteoneShareSavedListForm.getUsers();
		final String code = siteoneShareSavedListForm.getCode();
		LOG.info("Inside view edit " +siteoneShareSavedListForm.getIsViewEdit());
		final boolean isViewEdit = siteoneShareSavedListForm.getIsViewEdit();
		LOG.info("Inside isview edit " +isViewEdit);
		final boolean isAssembly = false;
		siteoneSavedListFacade.saveShareListUser(note, owners, code, isAssembly, isViewEdit);
		return REDIRECT_TO_DETAILS_PAGE + "?code=" + code;
	}

	public SavedListData getSavedListDetails(final String code)
	{
		return siteoneSavedListFacade.getDetailsPage(code);
	}

	@Override
	protected int getSearchPageSize()
	{
		return getSiteConfigService().getInt("storefront.savedList.search.pageSize", 0);
	}

	@ResponseBody
	@GetMapping("/getShareListUsers")
	@RequireHardLogIn
	public List<ShareCustomerData> getShareListUsers(@RequestParam("searchTerm")
	final String searchTerm, @RequestParam(value = "searchType", defaultValue = "Name", required = false)
	final String searchType) throws JsonProcessingException, JSONException
	{
		LOG.info("fetch share list");

		final List<CustomerData> customerList = siteoneSavedListFacade.getAllCustomersForSharedList(searchTerm, searchType);
		final CustomerData customerData = customerFacade.getCurrentCustomer();

		final List<ShareCustomerData> userList = new ArrayList<ShareCustomerData>();

		for (final CustomerData customer : customerList)
		{
			if (!(customer.getUid().equalsIgnoreCase(customerData.getUid())) && (customer.isActive() == true))
			{
				final ShareCustomerData user = new ShareCustomerData();
				user.setText(customer.getName());
				user.setValue(customer.getUid());
				userList.add(user);
			}
		}

		return userList;

	}

	@PostMapping("/addProductComment")
	public String addProductComment(final SiteOneProductCommentForm siteOneProductCommentForm)
	{
		final String productCode = siteOneProductCommentForm.getProductCode();
		final String comment = siteOneProductCommentForm.getComment();
		final String listCode = siteOneProductCommentForm.getListCode();
		siteoneSavedListFacade.addProductComment(productCode, comment, listCode);
		return REDIRECT_TO_DETAILS_PAGE + "?code=" + listCode;
	}

	@GetMapping("/removeProductComment")
	public String removeProductComment(@RequestParam(value = "productCode", required = false)
	final String productCode, @RequestParam(value = "listcode", required = false)
	final String listCode)
	{
		final boolean flag = siteoneSavedListFacade.removeProductComment(productCode, listCode);
		LOG.info("Comment Removed" + flag);
		return REDIRECT_TO_DETAILS_PAGE + "?code=" + listCode;
	}

	@ResponseBody
	@PostMapping("/createWishlist")
	public CreateWishlistResponseData createWishlist(@RequestParam(value = "productCode", required = false)
	final String productCode, @RequestParam(value = "inventoryUom", required = false)
	final String inventoryUOM, @RequestParam(value = "qty", required = false, defaultValue = "1")
	final String qty, @RequestParam("wishListName")
	final String wishListName, @RequestParam(value = "productList", required = false)
	final String productList) throws CMSItemNotFoundException
	{
		final CreateWishlistResponseData wishlistResponseData = new CreateWishlistResponseData();
		final boolean flag = siteoneSavedListFacade.checkDuplicate(wishListName, false);
		wishlistResponseData.setIsDuplicate(Boolean.valueOf(flag));
		if (!flag)
		{
			final SavedListData savedListData = new SavedListData();
			savedListData.setName(wishListName);
			//	savedListData.setDescription(MY_WIshLIST_DESP);
			final boolean isAssembly = false;
			String wishListCode = "";
			if (StringUtils.isNotEmpty(productList))
			{
				final String list = productList.trim();
				final String productsToPass = list.replace(" ", ",");
				wishListCode = siteoneSavedListFacade.createSavedList(savedListData, productsToPass, isAssembly);
			}
			else
			{
				final String productCodes = productCode + "|" + qty + "|" + inventoryUOM;
				wishListCode = siteoneSavedListFacade.createSavedList(savedListData, productCodes, isAssembly);
			}
			wishlistResponseData.setWishListCode(wishListCode);
		}
		return wishlistResponseData;
	}

	@ResponseBody
	@PostMapping("/createWishlistAndFetch")
	public CreateWishlistAndResponseAllData createWishlistAndFetch(@RequestParam("wishListName")
	final String wishListName) throws CMSItemNotFoundException
	{
		final CreateWishlistAndResponseAllData wishlistResponseData = new CreateWishlistAndResponseAllData();
		final boolean flag = siteoneSavedListFacade.checkDuplicate(wishListName, false);
		wishlistResponseData.setIsDuplicate(Boolean.valueOf(flag));
		final boolean isAssembly = false;
		if (!flag)
		{
			final SavedListData savedListData = new SavedListData();
			savedListData.setName(wishListName);
			String wishListCode = "";
			wishListCode = siteoneSavedListFacade.createSavedList(savedListData, null, isAssembly);
		}
		final List<SavedListData> allWishlist = siteoneSavedListFacade.getAllSavedListForEdit(isAssembly);
		wishlistResponseData.setAllWishlist(allWishlist);
		return wishlistResponseData;
	}


	@ResponseBody
	@PostMapping("/moveSavedList")
	protected void moveToSaveList(@RequestParam("productCode")
	final String productCode, @RequestParam("wishListCode")
	final String wishListCode) throws CMSItemNotFoundException
	{
		final boolean isAssembly = false;
		siteoneSavedListFacade.moveToSaveList(wishListCode, productCode, isAssembly);
	}

	@ResponseBody
	@PostMapping(value = "/RemoveSelectedFromSavedList")
	protected boolean removeSelectedFromSavedList(@RequestParam("wishListCode")
	final String wishListCode, @RequestParam("productItemNumbers")
	final String productItemNumbers, final Model model)
	{
		final List<String> products = new ArrayList<>(Arrays.asList(productItemNumbers.split(" ")));
		LOG.info("productsTobeAddedare: " + products.size());
		for (final String product : products)
		{
			if (StringUtils.isNotEmpty(product))
			{
				final String[] arr = product.split(SiteoneFacadesConstants.PIPE);
				siteoneSavedListFacade.moveToSaveList(wishListCode, arr[SiteoneFacadesConstants.ZERO], false);
			}
		}
		return true;
	}

	@GetMapping("/addListToCart")
	public String addProductsFromListToCart(@RequestParam("wishListCode")
	final String wishListCode, final RedirectAttributes redirectAttributes) throws CommerceCartModificationException
	{
		final boolean quantityFlag = siteoneSavedListFacade.addListToCart(wishListCode);
		if (quantityFlag)
		{
			return REDIRECT_CART_URL;
		}
		else
		{
			redirectAttributes.addFlashAttribute("quantityFlag", true);
			return REDIRECT_TO_DETAILS_PAGE + "?code=" + wishListCode;
		}
	}

	@ResponseBody
	@PostMapping(value = "/addSelectedItemsToCart")
	public boolean addSelectedProductsFromListToCart(@RequestParam("wishListCode")
	final String wishListCode, @RequestParam("productCodes")
	final String productCodes) throws CommerceCartModificationException
	{
		final List<String> products = new ArrayList<String>(Arrays.asList(productCodes.split(" ")));

		LOG.debug("productsTobeAddedare: " + productCodes);
		final boolean quantityFlag = siteoneSavedListFacade.addSelectedProductToCart(wishListCode, products);
		if (quantityFlag)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@GetMapping("/clearQuantities")
	public String clearQuantitiesFromList(@RequestParam("wishListCode")
	final String wishListCode) throws CommerceCartModificationException
	{
		siteoneSavedListFacade.clearQuantities(wishListCode);
		return REDIRECT_TO_DETAILS_PAGE + "?code=" + wishListCode;
	}

	@ResponseBody
	@PostMapping("/updateProductQuantity")
	protected boolean updateProductQuantity(@RequestParam("productCode")
	final String productCode, @RequestParam(value = "quantity", required = false)
	final String quantity, @RequestParam("wishListCode")
	final String wishListCode, final Model model) throws CommerceCartModificationException
	{
		return siteoneSavedListFacade.updateProductQuantity(productCode, quantity, wishListCode);
	}

	@ResponseBody
	@GetMapping("/listExists")
	public boolean checkListExists(@RequestParam("wishListName")
	final String wishListName)
	{
		return siteoneSavedListFacade.isListWithNameExist(wishListName, false);
	}



	protected void getBreadcrumbForPage(final Model model)
	{
		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb(MY_ACCOUNT_DASHBOARD_URL,
				getMessageSource().getMessage(TEXT_ACCOUNT_DASHBOARD, null, getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(LIST_LANDING_URL,
				getMessageSource().getMessage(TEXT_ACCOUNT_SAVEDLIST, null, getI18nService().getCurrentLocale()), null));
		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);
	}


	@GetMapping("/downloadCSVFile")
	@RequireHardLogIn
	public void createInvoicePDF(final Model model, final RedirectAttributes redirectModel, final HttpServletResponse response,
			final HttpServletRequest request) throws CMSItemNotFoundException
	{
		try
		{
			final byte[] csvContent = SiteOneInvoiceCSVUtils.createUploadListCSV();
			response.setHeader("Expires", "0");
			response.setHeader(CACHE_CONTROL, REVALIDATE);
			response.setHeader(PRAGMA, PUBLIC);

			response.setHeader(CONTENT_DISPOSITION, "attachment;filename=SavedListDownloadTemplate.csv");
			response.setContentType(APPLICATION_OCTET_STREAM);
			response.setContentLength(csvContent.length);
			response.getOutputStream().write(csvContent);
			response.getOutputStream().flush();

		}
		catch (final ServiceUnavailableException serviceUnavailableException)
		{
			LOG.error("Not able to access pdf", serviceUnavailableException);
		}
		catch (final PdfNotAvailableException pdfNotAvailableException)
		{
			LOG.error("Pdf is currently not available", pdfNotAvailableException);
		}
		catch (final IOException e)
		{
			LOG.error(EXCEPTION_PDF + e);
			LOG.error(e.getMessage(), e);
		}
	}

	@GetMapping(value = "/downloadListCSV")
	@RequireHardLogIn
	public void createListPDF(final Model model, final RedirectAttributes redirectModel, final HttpServletResponse response,
			final HttpServletRequest request, @RequestParam(value = "code")
			final String code)
	{
		final SavedListData savedListData = siteoneSavedListFacade.getListDataBasedOnCode(code);
		if (null != savedListData && !CollectionUtils.isEmpty(savedListData.getEntries()))
		{
			try
			{
				final byte[] csvContent = SiteOneInvoiceCSVUtils.createListCSV(savedListData, storeSessionFacade.getSessionStore());
				response.setHeader("Expires", "0");
				response.setHeader(CACHE_CONTROL, REVALIDATE);
				response.setHeader(PRAGMA, PUBLIC);
				response.setHeader(CONTENT_DISPOSITION,
						"attachment;filename=" + SiteOneInvoiceCSVUtils.getFileName(savedListData.getName()));
				response.setContentType(APPLICATION_OCTET_STREAM);
				response.setContentLength(csvContent.length);
				response.getOutputStream().write(csvContent);
				response.getOutputStream().flush();
			}
			catch (final IOException e)
			{
				LOG.error(EXCEPTION_PDF + e);
				LOG.error(e.getMessage(), e);
			}
		}
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

	@GetMapping(value = "/generateEstimate")
	@RequireHardLogIn
	public String getGenarateEstimatePage(@RequestParam("wishListCode")
	final String wishListCode, final Model model) throws CMSItemNotFoundException
	{
		final SavedListData savedListData = siteoneSavedListFacade.getListDataBasedOnCode(wishListCode);
		final CustomerData customerData = customerFacade.getCurrentCustomer();
		model.addAttribute(SAVEDLISTDATA, savedListData);
		model.addAttribute("customerData", customerData);
		model.addAttribute("unit", customerData.getUnit());
		final AddressData defaultBillingAddress = ((SiteOneCustomerFacade) customerFacade)
				.getDefaultBillingAddressForUnit(customerData.getUnit().getUid());
		model.addAttribute("billingAddress", defaultBillingAddress);
		List<RegionData> regionDataList = new ArrayList();
		if (baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase(SITEONE_US)) {
			regionDataList = siteOneRegionFacade.getRegionsForCountryCode("US");
		}
		if (baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase(SITEONE_CA)) {
			regionDataList = siteOneRegionFacade.getRegionsForCountryCode("CA");
		}
		regionDataList.sort(Comparator.comparing(RegionData::getName));
		model.addAttribute("states", regionDataList);
		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb(MY_ACCOUNT_DASHBOARD_URL,
				getMessageSource().getMessage(TEXT_ACCOUNT_DASHBOARD, null, getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(LIST_LANDING_URL,
				getMessageSource().getMessage(TEXT_ACCOUNT_SAVEDLIST, null, getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(LIST_DETAILS_URL + savedListData.getCode(), savedListData.getName(), null));
		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);
		storeCmsPageInModel(model, getContentPageForLabelOrId(GENARATE_ESTIMATE_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(GENARATE_ESTIMATE_PAGE));
		return ControllerConstants.Views.Pages.SavedList.GenarateEstimatePage;
	}

	@GetMapping(path = "/updateUOMforEntries")
	public @ResponseBody SavedListEntryUpdatedUOMData updateUOMforEntries(@RequestParam(value = "wishListCode", required = true)
	final String wishListCode, @RequestParam(value = "inventoryUomId", required = true)
	final String inventoryUomId, @RequestParam(value = "productCode", required = true)
	final String productCode)
	{

		final SavedListEntryUpdatedUOMData savedListEntryUpdatedUOMData = new SavedListEntryUpdatedUOMData();
		boolean isUomUpdated =  siteoneSavedListFacade.updateUOMforEntries(wishListCode, inventoryUomId, productCode);
		if(isUomUpdated)
		{
			final SavedListData savedListData = siteoneSavedListFacade.getListDetailsPage(wishListCode);

			final PageableData pageableData = this.createPageableData(0, 50, null, ShowMode.Page);
			final SearchPageData searchPageData = siteoneSavedListFacade.getPagedDetailsPage(pageableData, wishListCode);
			Double listTotalPrice = 0.0d;
			final List<SavedListEntryData> savedListEntryData = searchPageData.getResults();
			SavedListData listData = new SavedListData();
			SavedListEntryData listEntryData = new SavedListEntryData();
			final List<String> productCodes = new ArrayList<String>();
			if (CollectionUtils.isNotEmpty(savedListEntryData))
			{
				for(final SavedListEntryData data : savedListEntryData)
				{	
					productCodes.add(data.getProduct().getCode());
				}
			if (null != savedListData)
			{	
				if (searchPageData.getPagination().getNumberOfPages() > 1 || savedListData.getEntries().size() >= 50)
				{
					listData = siteoneSavedListFacade.getCSPListDetailsPage(wishListCode, productCodes);
					siteoneSavedListFacade.setPriceForEntryData(listData.getCustomerPriceData(), savedListEntryData,listData.getRetailPriceData());
				}
				else
				{
					siteoneSavedListFacade.setPriceForEntryData(savedListData.getCustomerPriceData(), savedListEntryData,savedListData.getRetailPriceData());
				}
			}	
				final int savedListLen = savedListEntryData.size();
				for (int i = 0; i < savedListLen; i++)
				{	
					final ProductData productData = savedListEntryData.get(i).getProduct();
					final ProductData productDataUpdated = siteOneProductFacade.updateUOMPriceForSingleUOM(productData);

					final List<InventoryUOMData> inventoryUOMListData = productData.getSellableUoms();
					if (inventoryUOMListData != null && inventoryUOMListData.size() > 1)
					{
						siteoneSavedListFacade.updatePriceBasedOnUOM(savedListEntryData.get(i));
					}
					savedListEntryData.get(i).setProduct(productDataUpdated);
					savedListEntryData.get(i).setTotalPrice(siteoneSavedListFacade.setItemTotal(savedListEntryData.get(i)));
					if(savedListEntryData.get(i).getTotalPrice() != null && !BooleanUtils.isTrue(savedListEntryData.get(i).getHidePrice()) && (savedListEntryData.get(i).getProduct().getCustomerPrice() != null || !BooleanUtils.isTrue(savedListEntryData.get(i).getProduct().getHideList())))
					{
						listTotalPrice = listTotalPrice + Double.valueOf(savedListEntryData.get(i).getTotalPrice().getValue().toString());
					}
					if(productDataUpdated.getCode().equalsIgnoreCase(productCode))
					{
						listEntryData = savedListEntryData.get(i);
					}
				}
			}
			savedListEntryUpdatedUOMData.setOrderQuantityInterval(listEntryData.getProduct().getOrderQuantityInterval());
			savedListEntryUpdatedUOMData.setPrice(listEntryData.getProduct().getPrice());
			if(BooleanUtils.isNotTrue(listEntryData.getProduct().getHideCSP()))
			{
			   savedListEntryUpdatedUOMData.setCustomerPrice(listEntryData.getProduct().getCustomerPrice());
			   savedListEntryUpdatedUOMData.setTotalPrice(listEntryData.getTotalPrice());
			}
			savedListEntryUpdatedUOMData.setListTotalPrice(listTotalPrice);
		}
		return savedListEntryUpdatedUOMData;
	}

	@GetMapping(path = "/updateUOMforRecommendedListEntries")
	public @ResponseBody SavedListEntryUpdatedUOMData updateUOMforEntriesForRecommendedList( @RequestParam(value = "inventoryUomId", required = true)
	final String inventoryUomId, @RequestParam(value = "productCode", required = true)
	final String productCode)
	{	
		final SavedListEntryUpdatedUOMData savedListEntryUpdatedUOMData = new SavedListEntryUpdatedUOMData();
		final List<ProductData> productList = new ArrayList<ProductData>();

					final ProductData product = productFacade.getProductForCodeAndOptions(productCode,
							Arrays.asList(ProductOption.BASIC, ProductOption.PRICE, ProductOption.VARIANT_MATRIX_BASE,
									ProductOption.STOCK, ProductOption.PRICE_RANGE, ProductOption.PROMOTIONS));
				
					if (!product.getIsProductDiscontinued()  && product.getPrice()!=null)
					{
						productList.add(product);
					}

					if (product.getPrice()==null)
					{
						LOG.info("buy again product price is null :"+product.getCode());
					}
				
					List<ProductData> p =  siteOneProductFacade.getCSPPriceListForRecommendedProducts(productList,inventoryUomId);
					if (CollectionUtils.isNotEmpty(p))
					{
						if(p.get(0).getCustomerPrice() != null)
						{			
				  savedListEntryUpdatedUOMData.setCustomerPrice(p.get(0).getCustomerPrice());
						}
						else {
				savedListEntryUpdatedUOMData.setCustomerPrice(p.get(0).getPrice());
						}
					}
					else if (product.getPrice() != null)
					{
						savedListEntryUpdatedUOMData.setCustomerPrice(product.getPrice());
					}
					LOG.info("Customer Price :"+savedListEntryUpdatedUOMData.getCustomerPrice());
		return savedListEntryUpdatedUOMData;
	}
	
	@RequireHardLogIn
	@PostMapping(path = "/uploadImage")
	public String uploadCompanyLogo(final SiteoneSavedListLogoUploadForm form, final BindingResult bindingErrors)
			throws IOException
	{
		if (form.getUploadedImage() != null)
		{
			final InputStream inputStream = form.getUploadedImage().getInputStream();
			String[] filename = null;
			if (!StringUtils.isBlank(form.getUploadedImage().getOriginalFilename()))
			{
				filename = form.getUploadedImage().getOriginalFilename().split("[.]");
				b2bUnitFacade.uploadImage(filename, inputStream);
			}

		}
		return REDIRECT_TO_ESTIMATE_PAGE + form.getWishListCode();
	}

	@RequireHardLogIn
	@PostMapping(path = "/deleteImage")
	public @ResponseBody boolean deleteCustomerLogo()
	{
		return b2bUnitFacade.deleteImage();
	}

	protected List<RecommendedListData> performSearch(final String searchQuery, final int page, final ShowMode showMode,
			final String sortCode, final int pageSize)
	{
		final PageableData pageableData = createPageableData(page, pageSize, sortCode, showMode);

		final SearchStateData searchState = new SearchStateData();
		final SearchQueryData searchQueryData = new SearchQueryData();
		searchQueryData.setValue(searchQuery);
		searchState.setQuery(searchQueryData);

		final BuyItAgainSearchPageData<SearchStateData, BuyItAgainData> buyAgainData = buyItAgainSearchFacade
				.buyItAgainSearch(searchState, pageableData);
		final Map<String, Long> productCountCategoryMap = getProductCountForCategory(buyAgainData);
		return populateRecommendedList(buyAgainData, productCountCategoryMap);
	}

	protected ListSearchPageData<SearchStateData, SavedListEntryData> performListSearch(final String searchQuery, final int page,
			final ShowMode showMode, final String sortCode, final int pageSize)
	{
		final PageableData pageableData = createPageableData(page, pageSize, sortCode, showMode);

		final SearchStateData searchState = new SearchStateData();
		final SearchQueryData searchQueryData = new SearchQueryData();
		searchQueryData.setValue(searchQuery);
		searchState.setQuery(searchQueryData);

		return encodeSearchPageData(listSearchFacade.listSearch(searchState, pageableData));
	}

	protected ListSearchPageData<SearchStateData, SavedListEntryData> encodeSearchPageData(
			final ListSearchPageData<SearchStateData, SavedListEntryData> searchPageData)
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

	protected void updatePageTitle(final String searchText, final Model model)
	{
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveContentPageTitle(
				getMessageSource().getMessage("search.meta.title", null, "search.meta.title", getI18nService().getCurrentLocale())
						+ " " + searchText));
	}

	protected void populateModelForListSearch(final Model model, final ListSearchPageData searchPageData, final ShowMode showMode)
	{
		final int numberPagesShown = getSiteConfigService().getInt(PAGINATION_NUMBER_OF_RESULTS_COUNT, 5);
		model.addAttribute("listSearch_numberPagesShown", Integer.valueOf(numberPagesShown));
		model.addAttribute("listSearchPageData", searchPageData);
		model.addAttribute("listSearch_isShowAllAllowed", calculateShowAll(searchPageData, showMode));
		model.addAttribute("listSearch_isShowPageAllowed", calculateShowPaged(searchPageData, showMode));
	}

	@GetMapping("/recommendedListDetails")
	@RequireHardLogIn
	public String reccomendedListDetails(final String categoryName, final Model model) throws CMSItemNotFoundException
	{
		final int viewByPageSize = getSiteConfigService().getInt("siteone.reccomendedlistentrydefault.pageSize", 50);
		sessionService.setAttribute("RecommendedDetailsPage", categoryName);
		BuyItAgainSearchPageData<SearchStateData, BuyItAgainData> searchPageData = null;
		searchPageData = performDetailSearch(":soPurchasedCount-desc", 0, ShowMode.Page, null, viewByPageSize);
		sessionService.setAttribute("RecommendedDetailsPage", "");
		final List<ProductData> productList = getBuyAgainProductList(searchPageData);
		final List<SavedListData> allWishlist = siteoneSavedListFacade.getAllSavedListForEdit(false);
		model.addAttribute("savedLists", allWishlist);
		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb(MY_ACCOUNT_DASHBOARD_URL,
				getMessageSource().getMessage(TEXT_ACCOUNT_DASHBOARD, null, getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(LIST_LANDING_URL,
				getMessageSource().getMessage(TEXT_ACCOUNT_SAVEDLIST, null, getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(RECOMMENDEDLIST_LANDING_URL,
				getMessageSource().getMessage("text.account.recommendedList.breadcrumb", null, getI18nService().getCurrentLocale()),
				null));
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadcrumbs);
		model.addAttribute("recommnededListDetailsData", searchPageData.getResults());
		model.addAttribute("productListData", productList);
		model.addAttribute("CategoryNameData", categoryName);
		storeCmsPageInModel(model, getContentPageForLabelOrId(RECOMMENDED_LIST_DETAILS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(RECOMMENDED_LIST_DETAILS_PAGE));
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveContentPageTitle(categoryName));
		return ControllerConstants.Views.Pages.SavedList.RecommendListDetailsPage;
	}

	protected BuyItAgainSearchPageData<SearchStateData, BuyItAgainData> performDetailSearch(final String searchQuery,
			final int page, final ShowMode showMode, final String sortCode, final int pageSize)
	{
		final PageableData pageableData = createPageableData(page, pageSize, sortCode, showMode);

		final SearchStateData searchState = new SearchStateData();
		final SearchQueryData searchQueryData = new SearchQueryData();
		searchQueryData.setValue(searchQuery);
		searchState.setQuery(searchQueryData);

		return buyItAgainSearchFacade.buyItAgainSearch(searchState, pageableData);
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
							Arrays.asList(ProductOption.BASIC, ProductOption.PRICE, ProductOption.VARIANT_MATRIX_BASE,
									ProductOption.STOCK, ProductOption.PRICE_RANGE, ProductOption.PROMOTIONS));
					product.setPurchasedCount(Integer.valueOf(buyItAgainData.getPurchasedCount()));
					product.setName(buyItAgainData.getProductDescription());
					product.setOrderCount(Integer.valueOf(buyItAgainData.getOrderCount()));
					product.setPurchasedQuantity(Integer.valueOf(buyItAgainData.getPurchasedQuantity()));
					product.setMultiUOMDesc(buyItAgainData.getProductUOMSHortDesc());
					siteOneProductFacade.setCategoriesForProduct(product);

					if (!product.getIsProductDiscontinued()  && product.getPrice()!=null)
					{
						productList.add(product);
					}

					if (product.getPrice()==null)
					{
						LOG.info("buy again product price is null :"+product.getCode()+", order number: "+buyItAgainData.getOrderNumber());
					}
				}
			}
			siteOneProductFacade.getCSPPriceListForProducts(searchPageData.getResults(), productList);
			siteOneProductFacade.populateAvailablityForBuyAgainPageProducts(productList);
		}
		return productList;
	}

	protected List<RecommendedListData> populateRecommendedList(
			final BuyItAgainSearchPageData<SearchStateData, BuyItAgainData> searchPageData,
			final Map<String, Long> productCountCategoryMap)
	{
		final List<RecommendedListData> list = new ArrayList<>();
		if (!searchPageData.getResults().isEmpty())
		{
			for (final BuyItAgainData buyAgainDate : searchPageData.getResults())
			{
				if (buyAgainDate.getCategoryName() != null && !buyAgainDate.getCategoryName().isEmpty())
				{
					final RecommendedListData recommendedList = new RecommendedListData();
					recommendedList.setCategoryName(buyAgainDate.getCategoryName());
					recommendedList.setModifiedTime(buyAgainDate.getLastPurchasedDate());
					recommendedList.setProductCount(productCountCategoryMap.get(buyAgainDate.getCategoryName()));
					list.add(recommendedList);
				}
			}
		}

		return list;
	}

	protected Map<String, Long> getProductCountForCategory(
			final BuyItAgainSearchPageData<SearchStateData, BuyItAgainData> searchPageData)
	{
		final Map<String, Long> productCountCategoryMap = new HashMap<>();
		final List<FacetData<SearchStateData>> facets = searchPageData.getFacets();
		if (CollectionUtils.isNotEmpty(facets))
		{
			for (final FacetData<SearchStateData> facetData : facets)
			{
				if (facetData.getCode().equalsIgnoreCase("soCategoryName"))
				{
					final List<FacetValueData<SearchStateData>> topFacetValueDatas = facetData.getTopValues();
					if (CollectionUtils.isNotEmpty(topFacetValueDatas))
					{
						processFacetDataForCategory(topFacetValueDatas, productCountCategoryMap);
					}
					final List<FacetValueData<SearchStateData>> facetValueDatas = facetData.getValues();
					if (CollectionUtils.isNotEmpty(facetValueDatas))
					{
						processFacetDataForCategory(facetValueDatas, productCountCategoryMap);
					}
				}
			}
		}
		return productCountCategoryMap;
	}

	protected void processFacetDataForCategory(final List<FacetValueData<SearchStateData>> facetValueDatas,
			final Map<String, Long> productCountCategoryMap)
	{
		final int viewByPageSize = getSiteConfigService().getInt("siteone.reccomendedlistentrydefault.pageSize", 50);
		for (final FacetValueData<SearchStateData> facetValueData : facetValueDatas)
		{
			if (facetValueData.getCount() <= viewByPageSize)
			{
				productCountCategoryMap.put(facetValueData.getCode(), facetValueData.getCount());
			}
			else
			{
				productCountCategoryMap.put(facetValueData.getCode(), Long.valueOf(viewByPageSize));
			}
		}
	}

	protected String performSearchForCategory(final String searchQuery, final int page, final ShowMode showMode,
			final String sortCode, final int pageSize)
	{
		final PageableData pageableData = createPageableData(page, pageSize, sortCode, showMode);

		final SearchStateData searchState = new SearchStateData();
		final SearchQueryData searchQueryData = new SearchQueryData();
		searchQueryData.setValue(searchQuery);
		searchState.setQuery(searchQueryData);

		final BuyItAgainSearchPageData<SearchStateData, BuyItAgainData> buyAgainData = buyItAgainSearchFacade
				.buyItAgainSearch(searchState, pageableData);

		return populateProductCodes(buyAgainData);
	}

	protected String populateProductCodes(final BuyItAgainSearchPageData<SearchStateData, BuyItAgainData> searchPageData)
	{
		final StringBuilder sb = new StringBuilder();
		if (!searchPageData.getResults().isEmpty())
		{
			int count = 0;
			for (final BuyItAgainData buyAgainData : searchPageData.getResults())
			{
				if (buyAgainData.getProductItemNumber() != null && !buyAgainData.getProductItemNumber().isEmpty())
				{
					if (count >= 1)
					{
						sb.append(",").append(buyAgainData.getProductItemNumber()).append("|").append("1");
					}
					else
					{
						sb.append(buyAgainData.getProductItemNumber()).append("|").append("1");
					}
					count++;
				}
			}
		}
		return sb.toString();
	}
	
	@GetMapping("/getAllLists")
	@RequireHardLogIn
	public @ResponseBody
	AllListsData getAllLists(@RequestParam(value = "numberOfLists", defaultValue = "5", required = false) final Integer numberOfLists)
	{
		final PageableData pageableData = this.createPageableData(0, numberOfLists, null, ShowMode.Page);
		final boolean isAssembly = false;
		final SearchPageData searchPageData = siteoneSavedListFacade.getRecentLists(pageableData, isAssembly, "desc");
		sessionService.setAttribute("RecommendedList", "LandingPage");
		final List<RecommendedListData> recommendedListData = performSearch(null, 0, ShowMode.Page, null, numberOfLists);
		sessionService.setAttribute("RecommendedList", "");
		AllListsData allListData = new AllListsData();
		allListData.setSavedLists(searchPageData.getResults());
		allListData.setRecommendedLists(recommendedListData);
		return allListData;
	}


@GetMapping("/getListQrPdf")
	public void listQRPdf(@RequestParam(value = "productCode", required = false)
	final String productCode, @RequestParam(value = "wishListCode", required = false)
	final String wishListCode, @RequestParam(value = "selectedItems", required = false)
	final String selectedItems, @RequestParam(value = "label", required = false)
	final String label, final HttpServletRequest request, final HttpServletResponse response) throws Exception

	{
		final List<ProductData> productList = new ArrayList<>();
		if (StringUtils.isNotEmpty(wishListCode))
		{
			final SavedListData savedListData = siteoneSavedListFacade.getListDetailsPage(wishListCode);
			if (null != savedListData)
			{
				if (CollectionUtils.isNotEmpty(savedListData.getEntries()))
				{
					for (final SavedListEntryData data : savedListData.getEntries())
					{
						productList.add(data.getProduct());
					}
				}
			}
		}
		else
		{
			if (StringUtils.isNotEmpty(productCode))
			{
				final List<String> productCodes = Arrays.asList(productCode.split(","));

				for (final String code : productCodes)
				{
					final ProductData product = productFacade.getProductForCodeAndOptions(code,
							Arrays.asList(ProductOption.BASIC, ProductOption.GALLERY, ProductOption.URL));
					productList.add(product);
				}
			}
		}
		try
		{
			final byte[] csvContent = SiteOneInvoicePDFUtils.createListQrPdf(productList, selectedItems, label);
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");

			response.setHeader("Content-Disposition", "attachment; filename=ListLabels.pdf");
			response.setContentType("application/octet-stream");
			response.setContentLength(csvContent.length);
			response.getOutputStream().write(csvContent);
			response.getOutputStream().flush();

			LOG.info(csvContent);
		}
		catch (final ServiceUnavailableException serviceUnavailableException)
		{
			LOG.error("Not able to access csv", serviceUnavailableException);
		}
		catch (final DocumentException csvNotAvailableException)
		{
			LOG.error("Csv is currently not available", csvNotAvailableException);
		}
		catch (final IOException e)
		{
			LOG.error("Exception while creating CSV : " + e);
			LOG.error(e.getMessage(), e);
		}
	}


}
