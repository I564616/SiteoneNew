/**
 *
 */
package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.ResourceBreadcrumbBuilder;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.siteone.facades.cart.SiteOneCartFacade;
import com.siteone.facades.savedList.SiteoneSavedListFacade;
import com.siteone.facades.savedList.data.SavedListData;
import com.siteone.facades.savedList.data.SavedListEntryData;
import com.siteone.facades.savedList.data.ShareCustomerData;
import com.siteone.facades.wishlist.data.WishlistAddData;
import com.siteone.storefront.controllers.ControllerConstants;
import com.siteone.storefront.forms.SiteOneEditSavedListForm;
import com.siteone.storefront.forms.SiteOneProductCommentForm;
import com.siteone.storefront.forms.SiteoneSavedListCreateForm;
import com.siteone.storefront.forms.SiteoneShareSavedListForm;

import atg.taglib.json.util.JSONException;


/**
 * @author 1003567
 *
 */

@Controller
@Scope("tenant")
@RequestMapping(value = "/assembly")
public class SiteoneAssemblyController extends AbstractSearchPageController
{
	/** Setting up Logger for the class to log the messages. */
	private static final Logger LOG = Logger.getLogger(SiteoneAssemblyController.class);

	private static final String ASSEMBLY_PAGE = "assemblyPage";

	private static final String BREADCRUMBS_ATTR = "breadcrumbs";

	private static final String ASSEMBLY_EDIT_PAGE = "editAssemblyPage";

	private static final String ASSEMBLY_DETAILS_PAGE = "detailsAssemblyPage";

	private static final String TEXT_ACCOUNT_ASSEMBLY = "text.account.assembly";

	private static final String TEXT_ACCOUNT_DASHBOARD = "text.account.addressBook.dashboard";

	private static final String MY_ACCOUNT_DASHBOARD_URL = "/my-account/account-dashboard";

	private static final String REDIRECT_TO_CREATE_PAGE = REDIRECT_PREFIX + "/assembly/createList";

	private static final String REDIRECT_TO_DETAILS_PAGE = REDIRECT_PREFIX + "/assembly/listDetails";

	private static final String REDIRECT_TO_EDIT_PAGE = REDIRECT_PREFIX + "/assembly/editList";

	private static final String REDIRECT_TO_LANDING_PAGE = REDIRECT_PREFIX + "/assembly";

	private static final String REDIRECT_CART_URL = REDIRECT_PREFIX + "/cart";

	private static final String DESCENDING_ORDER = "desc";

	private static final String ASSEMBLY_LANDING_URL = "/assembly";

	private static final String ASSEMBLY_PAGE_TITLE = "Assemblies";

	private static final String PIPE = "\\|";
	private static final Integer ZERO = 0;
	private static final Integer ONE = 1;

	private static final String ASSEMBLYS_FULLPAGEPATH = "analytics.fullpath.assemblies";
	private static final String CREATE_ASSEMBLY_FULLPAGEPATH = "analytics.fullpath.create.assembly";
	
	private static final Integer TWO = 2;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;

	@Resource(name = "siteoneSavedListFacade")
	private SiteoneSavedListFacade siteoneSavedListFacade;

	@Resource(name = "cartFacade")
	private SiteOneCartFacade siteOneCartFacade;


	@GetMapping
	@RequireHardLogIn
	public String getAllSavedList(final Model model,
			@RequestParam(value = "page", defaultValue = "0", required = false) final int page,
			@RequestParam(value = "sortOrder", defaultValue = DESCENDING_ORDER, required = false) final String sortOrder,
			@RequestParam(value = "listDeleted", required = false) final String listDeleted) throws CMSItemNotFoundException
	{
		final PageableData pageableData = this.createPageableData(page, this.getSearchPageSize(), null, ShowMode.Page);
		final boolean isAssembly = true;
		final SearchPageData searchPageData = siteoneSavedListFacade.getAllSavedList(pageableData, isAssembly, sortOrder);
		populateModel(model, searchPageData, ShowMode.Page);
		final List<SavedListData> savedListData = searchPageData.getResults();
		getContentPageForLabelOrId(ASSEMBLY_PAGE)
				.setFullPagePath(getMessageSource().getMessage(ASSEMBLYS_FULLPAGEPATH, null, getI18nService().getCurrentLocale()));
		storeCmsPageInModel(model, getContentPageForLabelOrId(ASSEMBLY_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ASSEMBLY_PAGE));
		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb(MY_ACCOUNT_DASHBOARD_URL,
				getMessageSource().getMessage(TEXT_ACCOUNT_DASHBOARD, null, getI18nService().getCurrentLocale()), null));
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveContentPageTitle(ASSEMBLY_PAGE_TITLE));
		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);
		model.addAttribute("savedListData", savedListData);
		model.addAttribute("sortOrder", sortOrder);
		model.addAttribute("isListDeleted", listDeleted);
		return ControllerConstants.Views.Pages.SavedList.AssemblyLandingPage;
	}

	@GetMapping("/createList")
	@RequireHardLogIn
	public String createList(final Model model) throws CMSItemNotFoundException
	{
		LOG.debug("Rendering Create List");
		getPreparedCreatePage(model);
		final SiteoneSavedListCreateForm siteoneSavedListCreateForm = new SiteoneSavedListCreateForm();
		model.addAttribute("siteoneAssemblyCreateForm", siteoneSavedListCreateForm);
		getContentPageForLabelOrId(ASSEMBLY_PAGE).setFullPagePath(
				getMessageSource().getMessage(CREATE_ASSEMBLY_FULLPAGEPATH, null, getI18nService().getCurrentLocale()));
		storeCmsPageInModel(model, getContentPageForLabelOrId(ASSEMBLY_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ASSEMBLY_PAGE));
		return getViewForPage(model);
	}

	@ResponseBody
	@PostMapping("/checkDuplicate")
	@RequireHardLogIn
	public String checkDuplicate(final SiteoneSavedListCreateForm siteoneSavedListForm)
	{
		LOG.debug("CheckDuplicate");
		final SavedListData savedListData = new SavedListData();
		savedListData.setName(siteoneSavedListForm.getName());
		savedListData.setDescription(siteoneSavedListForm.getDescription());
		final boolean isAssembly = true;
		final boolean isDuplicate = siteoneSavedListFacade.checkDuplicate(savedListData.getName(), isAssembly);
		return Boolean.toString(isDuplicate);
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

	@PostMapping("/createList")
	@RequireHardLogIn
	public String createSavedList(final SiteoneSavedListCreateForm siteoneSavedListForm, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		final SavedListData savedListData = new SavedListData();
		savedListData.setName(siteoneSavedListForm.getName());
		savedListData.setDescription(siteoneSavedListForm.getDescription());
		final boolean isAssembly = true;
		final String code = siteoneSavedListFacade.createSavedList(savedListData, siteoneSavedListForm.getProduct(), isAssembly);
		if (null == code)
		{
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "assembly.list.duplicate", null);
			return REDIRECT_TO_CREATE_PAGE;
		}
		getContentPageForLabelOrId(ASSEMBLY_PAGE).setFullPagePath(
				getMessageSource().getMessage(CREATE_ASSEMBLY_FULLPAGEPATH, null, getI18nService().getCurrentLocale()));
		storeCmsPageInModel(model, getContentPageForLabelOrId(ASSEMBLY_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ASSEMBLY_PAGE));

		return REDIRECT_TO_DETAILS_PAGE + "?code=" + code;
	}

	@PostMapping("/displayProduct")
	@ResponseBody
	public ProductData getProductToDisplay(@RequestParam(value = "productCodes", required = false) final String productCodes,
			final RedirectAttributes redirectModel)
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

	@GetMapping("/listDetails")
	@RequireHardLogIn
	public String getDetailsPage(final String code, final Model model) throws CMSItemNotFoundException
	{
		final SavedListData savedListData = siteoneSavedListFacade.getDetailsPage(code);
		final List<SavedListEntryData> products = new CopyOnWriteArrayList<>(savedListData.getEntries());
		for (final SavedListEntryData savedListEntryData : products)
		{
			final Boolean offlineProduct = savedListEntryData.getProduct().getIsProductOffline();
			if (null != offlineProduct && Boolean.TRUE.equals(offlineProduct))
			{
				GlobalMessages.addMessage(model, GlobalMessages.CONF_MESSAGES_HOLDER, "assembly.product.nla.message", new Object[]
				{ savedListEntryData.getProduct().getName() });
				products.remove(savedListEntryData);
			}
		}
		savedListData.setEntries(products);
		model.addAttribute("savedListData", savedListData);
		final SiteoneShareSavedListForm siteoneShareSavedListForm = new SiteoneShareSavedListForm();
		final SiteOneProductCommentForm siteOneProductCommentForm = new SiteOneProductCommentForm();
		siteoneShareSavedListForm.setCode(code);
		siteOneProductCommentForm.setListCode(code);
		model.addAttribute("siteoneShareSavedListForm", siteoneShareSavedListForm);
		model.addAttribute("siteOneProductCommentForm", siteOneProductCommentForm);
		final boolean isAssembly = true;
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

		final List<SavedListData> allSaveLists = siteoneSavedListFacade.getAllSavedListForEdit(false);

		String wishlistName = null;
		if (CollectionUtils.isNotEmpty(allSaveLists) && allSaveLists.size() == 1)
		{
			wishlistName = allSaveLists.get(0).getCode();

		}


		if (CollectionUtils.isEmpty(allSaveLists))
		{
			model.addAttribute("createWishList", "true");
		}


		model.addAttribute("wishlistName", wishlistName);
		model.addAttribute("allWishlist", allSaveLists);
		getBreadcrumbForPage(model);
		storeCmsPageInModel(model, getContentPageForLabelOrId(ASSEMBLY_DETAILS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ASSEMBLY_DETAILS_PAGE));
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveContentPageTitle(savedListData.getName()));
		return ControllerConstants.Views.Pages.SavedList.AssemblyDetailsPage;

	}

	@GetMapping("/editList")
	@RequireHardLogIn
	public String editList(@RequestParam(value = "code", required = false) final String code, final Model model)
			throws CMSItemNotFoundException
	{
		LOG.debug("Rendering Edit List");
		final SavedListData savedListData = siteoneSavedListFacade.getDetailsPage(code);
		final SiteOneEditSavedListForm siteOneEditSavedListForm = new SiteOneEditSavedListForm();
		siteOneEditSavedListForm.setName(savedListData.getName());
		siteOneEditSavedListForm.setDescription(savedListData.getDescription());
		siteOneEditSavedListForm.setListName(savedListData.getName());
		siteOneEditSavedListForm.setOwner(savedListData.getUser());
		siteOneEditSavedListForm.setCode(savedListData.getCode());
		model.addAttribute("siteOneEditSavedListForm", siteOneEditSavedListForm);
		getBreadcrumbForPage(model);
		storeCmsPageInModel(model, getContentPageForLabelOrId(ASSEMBLY_EDIT_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ASSEMBLY_EDIT_PAGE));
		return getViewForPage(model);
	}

	@PostMapping("/editList")
	@RequireHardLogIn
	public String editSavedList(final SiteOneEditSavedListForm siteOneEditSavedListForm, final RedirectAttributes redirectModel)
	{
		final SavedListData savedListData = new SavedListData();
		savedListData.setName(siteOneEditSavedListForm.getName());

		savedListData.setDescription(siteOneEditSavedListForm.getDescription());
		savedListData.setCode(siteOneEditSavedListForm.getCode().replaceAll(",", ""));
		final boolean isAssembly = true;
		final boolean flag = siteoneSavedListFacade.updateSavedList(savedListData, siteOneEditSavedListForm.getListName(),
				isAssembly);
		if (!flag)
		{
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "assembly.list.duplicate", null);
			return REDIRECT_TO_EDIT_PAGE + "?code=" + savedListData.getCode();
		}
		return REDIRECT_TO_DETAILS_PAGE + "?code=" + savedListData.getCode();
	}

	@GetMapping("/deleteList")
	@RequireHardLogIn
	public String deleteSavedList(@RequestParam(value = "code", required = false) final String code)
	{
		siteoneSavedListFacade.deleteSavedList(code);
		return REDIRECT_TO_LANDING_PAGE + "?listDeleted=success";
	}

	@PostMapping("/shareList")
	@RequireHardLogIn
	public String saveShareListData(final SiteoneShareSavedListForm siteoneShareSavedListForm)
	{
		LOG.info("Share List");
		final String note = siteoneShareSavedListForm.getNote();
		final String owners = siteoneShareSavedListForm.getUsers();
		final String code = siteoneShareSavedListForm.getCode();
		final boolean isAssembly = true;
		siteoneSavedListFacade.saveShareListUser(note, owners, code, isAssembly,false);
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
	public List<ShareCustomerData> getShareListUsers() throws JsonProcessingException, JSONException
	{
		LOG.info("fetch share list");

		final List<CustomerData> customerList = siteoneSavedListFacade.getAllCustomersForSharedList();
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
	@RequireHardLogIn
	public String addProductComment(final SiteOneProductCommentForm siteOneProductCommentForm)
	{
		final String productCode = siteOneProductCommentForm.getProductCode();
		final String comment = siteOneProductCommentForm.getComment();
		final String listCode = siteOneProductCommentForm.getListCode();
		siteoneSavedListFacade.addProductComment(productCode, comment, listCode);
		return REDIRECT_TO_DETAILS_PAGE + "?code=" + listCode;
	}

	@GetMapping("/removeProductComment")
	public String removeProductComment(@RequestParam(value = "productCode", required = false) final String productCode,
			@RequestParam(value = "listcode", required = false) final String listCode)
	{
		final boolean flag = siteoneSavedListFacade.removeProductComment(productCode, listCode);
		LOG.info("Comment Removed" + flag);
		return REDIRECT_TO_DETAILS_PAGE + "?code=" + listCode;
	}

	@ResponseBody
	@PostMapping("/add")
	protected WishlistAddData addtoWishlist(@RequestParam("productCode") final String productCode,
			@RequestParam(value = "quantity", required = false) final String quantity,
			@RequestParam("wishListCode") final String wishListCode, final Model model,
			@RequestParam(value = "prodQtyFlag", defaultValue = "false", required = false) final boolean prodQtyFlag,
			@RequestParam(value = "inventoryUOMId", required = false) final String inventoryUOMId) throws CMSItemNotFoundException
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
			model.addAttribute("p_wishlist", Boolean.FALSE);
		}
		model.addAttribute(wishlistAddResponseData);
		if (null != wishlistAddResponseData)
		{
			model.addAttribute("p_wishlist", Boolean.TRUE);
		}

		return wishlistAddResponseData;
	}

	@ResponseBody
	@PostMapping("/addAssemblyToList")
	protected WishlistAddData addAssemblyToList(@RequestParam(value = "productCodes", required = true) final String productCodes,
			@RequestParam("wishListCode") final String wishListCode, final Model model,
			@RequestParam(value = "prodQtyFlag", defaultValue = "false", required = false) final boolean prodQtyFlag)
			throws CMSItemNotFoundException
	{
		WishlistAddData wishlistAddResponseData = new WishlistAddData();
		final List<String> products = new ArrayList<String>(Arrays.asList(productCodes.split(" ")));
		LOG.debug("productsTobeAddedare: " + productCodes);
		for (final String productCode : products)
		{
			if (StringUtils.isNotEmpty(productCode))
			{
				final String arr[] = productCode.split(PIPE);
				wishlistAddResponseData = siteoneSavedListFacade.addtoWishlist(arr[ZERO].trim(), arr[ONE], wishListCode, prodQtyFlag,
						arr[TWO]);
			}
		}

		wishlistAddResponseData.setMessage("Assembly Added to List");
		model.addAttribute(wishlistAddResponseData);
		model.addAttribute("p_wishlist", Boolean.TRUE);
		return wishlistAddResponseData;
	}

	@ResponseBody
	@PostMapping("/createWishlist")
	public void createWishlist(@RequestParam("productCode") final String productCode,
			@RequestParam("wishListName") final String wishListName) throws CMSItemNotFoundException
	{
		final SavedListData savedListData = new SavedListData();
		savedListData.setName(wishListName);
		//savedListData.setDescription(MY_WIshLIST_DESP);
		final boolean isAssembly = true;
		siteoneSavedListFacade.createSavedList(savedListData, productCode, isAssembly);

	}

	@ResponseBody
	@PostMapping("/moveSavedList")
	protected void moveToSaveList(@RequestParam("productCode") final String productCode,
			@RequestParam("wishListCode") final String wishListCode) throws CMSItemNotFoundException
	{
		final boolean isAssembly = true;
		siteoneSavedListFacade.moveToSaveList(wishListCode, productCode, isAssembly);
	}

	@GetMapping("/addAssemblyToCart")
	public String addProductsFromAssemblyToCart(@RequestParam(value = "wishListCode") final String wishListCode,
			@RequestParam(value = "assemblyCount", required = false, defaultValue = "1") final int assemblyCount )
			throws CommerceCartModificationException

	{
		boolean addAssemblyListToCartStatus = false;
		LOG.debug("wishListCode: " + wishListCode);
		if (StringUtils.isNotEmpty(wishListCode) && assemblyCount > 0)
		{
			addAssemblyListToCartStatus = siteoneSavedListFacade.addAssemblyListToCart(wishListCode, assemblyCount);
		}
		if (addAssemblyListToCartStatus)
		{
			return REDIRECT_CART_URL;
		}
		else
		{
			return REDIRECT_TO_DETAILS_PAGE + "?code=" + wishListCode;
		}
	}


	@GetMapping("/getAllLists")
	public String getAllLists(final Model model) throws CMSItemNotFoundException
	{
		LOG.info(siteoneSavedListFacade.getAllSavedListForEdit(false));
		if (null != siteoneSavedListFacade.getAllSavedListForEdit(false))
		{
			model.addAttribute("allLists", siteoneSavedListFacade.getAllSavedListForEdit(false));
		}
		return ControllerConstants.Views.Pages.SavedList.ListOfListsPage;
	}

	protected void getBreadcrumbForPage(final Model model)
	{
		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb(MY_ACCOUNT_DASHBOARD_URL,
				getMessageSource().getMessage(TEXT_ACCOUNT_DASHBOARD, null, getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(ASSEMBLY_LANDING_URL,
				getMessageSource().getMessage(TEXT_ACCOUNT_ASSEMBLY, null, getI18nService().getCurrentLocale()), null));
		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);
	}

}
