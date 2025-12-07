/**
 *
 */
package com.siteone.storefront.controllers.cms;

import de.hybris.platform.acceleratorservices.config.SiteConfigService;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController.ShowMode;
import de.hybris.platform.acceleratorstorefrontcommons.forms.LoginForm;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.search.facetdata.FacetData;
import de.hybris.platform.commerceservices.search.facetdata.FacetValueData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.SiteOneCuratedPLPComponentModel;
import com.siteone.facades.category.SiteOneCategoryFacade;
import com.siteone.facades.savedList.SiteoneSavedListFacade;
import com.siteone.facades.savedList.data.SavedListData;
import com.siteone.facades.sds.SiteOneSDSProductSearchFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.storefront.controllers.ControllerConstants;
import com.siteone.storefront.util.SiteOneSearchUtils;


/**
 * @author BS
 *
 */
@Controller("SiteOneCuratedPLPComponentController")
@RequestMapping(value = ControllerConstants.Actions.Cms.SiteOneCuratedPLPComponent)
public class SiteOneCuratedPLPComponentController
		extends AbstractAcceleratorCMSComponentController<SiteOneCuratedPLPComponentModel>
{
	private static final Logger LOG = Logger.getLogger(SiteOneCuratedPLPComponentController.class);
	private static final String CURATED_PLP = "CuratedPLP";
	private static final String EXPRESS_SHIPPING = "expressShipping";
	private static final String PAGINATION_NUMBER_OF_RESULTS_COUNT = "pagination.number.results.count";
	private static final int L2_CATEGORY_LENGTH = 6;
	private static final int L3_CATEGORY_LENGTH = 9;
	private static final String REFERER = "jakarta.servlet.forward.request_uri";
	private static final String NEAR_BY = "nearby";
	private static final String INSTOCK = "inStock";
	private static final String INSTOCKTOGGLE = "InStockToggle";
	private static final String PLP_CARD_VARIANT_COUNT = "plp.card.variant.count";

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "siteoneSavedListFacade")
	private SiteoneSavedListFacade siteoneSavedListFacade;

	@Resource(name = "siteOneSDSProductSearchFacade")
	private SiteOneSDSProductSearchFacade siteOneSDSProductSearchFacade;

	@Resource(name = "siteConfigService")
	private SiteConfigService siteConfigService;

	@Resource(name = "siteOneSearchUtils")
	private SiteOneSearchUtils siteOneSearchUtils;

	@Resource(name = "siteOnecategoryFacade")
	private SiteOneCategoryFacade siteOnecategoryFacade;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "imageConverter")
	private Converter<MediaModel, ImageData> imageConverter;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Override
	protected void fillModel(final HttpServletRequest request, final Model model, final SiteOneCuratedPLPComponentModel component)
	{

		int pageNumber = 0;
		String query = null;
		String nearbyCheckOn = null;
		String selectedNearbyStores = null;
		String inStockFilterSelected = null;
		String searchQuery = null;
		String nearbyDisabled = null;
		String expressShipping = null;
		Boolean isToggle = Boolean.FALSE;

		if (request.getParameter("page") != null)
		{
			pageNumber = Integer.parseInt(request.getParameter("page"));
		}
		if (request.getQueryString() != null)
		{
			final String[] queryStrings = request.getQueryString().split("&");
			if (queryStrings.length > 1)
			{
				query = queryStrings[0].trim().replace("q=", "");
			}
		}

		final String[] pageName = request.getAttribute(REFERER).toString().split("/");



		if (StringUtils.isNotBlank(siteOneFeatureSwitchCacheService.getValueForSwitch("IsCMSPage")))
		{
			for (final String name : siteOneFeatureSwitchCacheService.getValueForSwitch("IsCMSPage").split(","))
			{
				if (name.equalsIgnoreCase(pageName[2]))
				{
					isToggle = Boolean.TRUE;
				}
			}
		}
		nearbyCheckOn = request.getParameter(NEAR_BY);
		selectedNearbyStores = request.getParameter("selectedNearbyStores");
		inStockFilterSelected = request.getParameter(INSTOCK);
		searchQuery = request.getParameter("q");
		nearbyDisabled = request.getParameter("nearbyDisabled");
		expressShipping = request.getParameter(EXPRESS_SHIPPING);

		final String displayall = request.getParameter("displayall");
		if (displayall != null && "on".equalsIgnoreCase(displayall))
		{
			getSessionService().setAttribute("displayAll", "on");
		}
		if (displayall != null && "off".equalsIgnoreCase(displayall))
		{
			getSessionService().setAttribute("displayAll", "off");
		}

		if (!(StringUtils.isNotBlank(searchQuery) && searchQuery.equalsIgnoreCase(":relevance")
				&& StringUtils.isNotBlank(request.getParameter("viewtype"))
				&& request.getParameter("viewtype").equalsIgnoreCase("All"))
				&& (BooleanUtils.isTrue(isToggle)
						&& StringUtils.isNotBlank(siteOneFeatureSwitchCacheService.getValueForSwitch(INSTOCKTOGGLE))
						&& siteOneFeatureSwitchCacheService.getValueForSwitch(INSTOCKTOGGLE).equalsIgnoreCase("true"))
				&& !(StringUtils.isNotBlank(request.getParameter(NEAR_BY)) && request.getParameter(NEAR_BY).equalsIgnoreCase("on")
						&& StringUtils.isEmpty(request.getParameter(INSTOCK))))
		{
			selectedNearbyStores = "";
			inStockFilterSelected = "on";
			nearbyCheckOn = "on";

			for (final PointOfServiceData pos : storeSessionFacade.getNearbyStoresFromSession())
			{
				if (pos.getStoreId() != null)
				{
					selectedNearbyStores = selectedNearbyStores.concat(pos.getStoreId()).concat(",");
				}
			}

			selectedNearbyStores = StringUtils.substring(selectedNearbyStores, 0, selectedNearbyStores.length() - 1);
		}



		ProductSearchPageData<SearchStateData, ProductData> searchResults = collectSearch(component, pageNumber, query, model,
				nearbyCheckOn, selectedNearbyStores, inStockFilterSelected, nearbyDisabled, expressShipping, searchQuery);
		if (searchResults.getPagination().getTotalNumberOfResults() <= 0 && BooleanUtils.isTrue(isToggle)
				&& StringUtils.isNotBlank(siteOneFeatureSwitchCacheService.getValueForSwitch(INSTOCKTOGGLE))
				&& siteOneFeatureSwitchCacheService.getValueForSwitch(INSTOCKTOGGLE).equalsIgnoreCase("true"))
		{
			nearbyCheckOn = request.getParameter(NEAR_BY);
			selectedNearbyStores = request.getParameter("selectedNearbyStores");
			inStockFilterSelected = request.getParameter(INSTOCK);
			nearbyDisabled = request.getParameter("nearbyDisabled");
			expressShipping = request.getParameter(EXPRESS_SHIPPING);

			searchResults = collectSearch(component, pageNumber, query, model, nearbyCheckOn, selectedNearbyStores,
					inStockFilterSelected, nearbyDisabled, expressShipping, searchQuery);

		}
		Map<String, List<String>> baseVariantSku = new HashMap<>();
		int baseCount = 0;
		List<ProductData> baseSku = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(searchResults.getResults()))
		{
			baseCount = searchResults.getResults().stream()
					.filter(product -> null != product.getVariantCount() && product.getVariantCount() > 1).collect(Collectors.toList())
					.size();
			if (searchResults.getResults().size() >= 4)
			{
				baseSku = searchResults.getResults().subList(0, 4);
			}
			else
			{
				baseSku = searchResults.getResults().subList(0, searchResults.getResults().size());
			}
		}
		if (CollectionUtils.isNotEmpty(baseSku) && 0 != baseCount)
		{
			baseVariantSku = baseSku.stream().filter(product -> null != product.getVariantCount() && product.getVariantCount() > 1)
					.collect(Collectors.toMap(ProductData::getCode, ProductData::getVariantCodes));
			final PageableData variantPageableData = createPageableDataForVariants(0, 150, null, ShowMode.All);
			siteOnecategoryFacade.getVariantProducts(baseVariantSku, siteConfigService.getInt(PLP_CARD_VARIANT_COUNT, 4),
					variantPageableData, model);
		}

		searchResults.getBreadcrumbs().forEach(temp -> {
			if (temp != null && temp.getRemoveQuery() != null && request.getAttribute(REFERER) != null)
			{
				final String[] url = request.getAttribute(REFERER).toString().split("/");
				temp.getRemoveQuery().setUrl(temp.getRemoveQuery().getUrl().replace("search", url[2]));
			}
		});
		if (request.getAttribute(REFERER) != null)
		{
			final String[] url = request.getAttribute(REFERER).toString().split("/");
			searchResults.getCurrentQuery().setUrl(searchResults.getCurrentQuery().getUrl().replace("search", url[2]));
		}
		if (component.getMarketingBanner() != null)
		{
			model.addAttribute("marketingBanner", imageConverter.convert(component.getMarketingBanner()));
		}
		if (component.getMarketingBannerLink() != null)
		{
			model.addAttribute("marketingBannerLink", component.getMarketingBannerLink());
		}
		if (component.getTitle() != null)
		{
			model.addAttribute("title", component.getTitle());
		}
		model.addAttribute("pageName", pageName[2]);
		model.addAttribute("productList", searchResults.getResults());
		model.addAttribute("paginationData", searchResults);
		model.addAttribute("numberPagesShown", siteConfigService.getInt(PAGINATION_NUMBER_OF_RESULTS_COUNT, 5));
		getAllSavedList(model);
	}

	protected PageableData createPageableDataForVariants(final int pageNumber, final int pageSize, final String sortCode,
			final ShowMode showMode)
	{
		final PageableData pageableData = new PageableData();
		pageableData.setCurrentPage(pageNumber);
		pageableData.setSort(sortCode);
		pageableData.setPageSize(pageSize);
		return pageableData;
	}

	/**
	 * @return
	 */
	public SessionService getSessionService()
	{
		return sessionService;
	}

	protected PageableData createPageableData(final int pageNumber, final int pageSize, final String sortCode,
			final ShowMode showMode)
	{
		final PageableData pageableData = new PageableData();
		pageableData.setCurrentPage(pageNumber);
		pageableData.setSort(sortCode);

		if (ShowMode.All == showMode)
		{
			pageableData.setPageSize(24);
		}
		else
		{
			pageableData.setPageSize(pageSize);
		}
		return pageableData;
	}

	private ProductSearchPageData<SearchStateData, ProductData> collectSearch(final SiteOneCuratedPLPComponentModel component,
			final int pageNumber, final String query, final Model model, final String nearbyCheckOn,
			final String selectedNearbyStores, final String inStockFilterSelected, final String nearbyDisabled,
			final String expressShipping, final String searchQuery)
	{

		siteOneSearchUtils.setInStockAndNearbyStoresFlagAndData(nearbyCheckOn, selectedNearbyStores, inStockFilterSelected,
				nearbyDisabled);
		if (expressShipping != null && expressShipping.equalsIgnoreCase("on")
				|| searchQuery != null && searchQuery.contains("soisShippable:true"))
		{
			getSessionService().setAttribute(EXPRESS_SHIPPING, "true");
		}
		else
		{
			getSessionService().removeAttribute(EXPRESS_SHIPPING);
		}


		final ProductSearchPageData<SearchStateData, ProductData> searchResults = collectSearchProducts(component, pageNumber,
				query);
		//siteOnecategoryFacade.updatePriorityBrandFacet(searchResults);
		siteOneSearchUtils.attachImageUrls(searchResults, model);
		siteOneSearchUtils.populateModelForInventory(searchResults, model);
		siteOnecategoryFacade.updateSearchPageData(searchResults);
		return searchResults;
	}

	protected ProductSearchPageData<SearchStateData, ProductData> collectSearchProducts(
			final SiteOneCuratedPLPComponentModel component, final int pageNumber, final String query)
	{
		ProductSearchPageData<SearchStateData, ProductData> searchPageData = null;
		final SearchQueryData searchQueryData = new SearchQueryData();
		if (null != query)
		{

			try
			{
				searchQueryData.setValue(component.getProductCodes().toLowerCase() + "," + URLDecoder.decode(query, "UTF-8"));
			}
			catch (final UnsupportedEncodingException e)
			{
				LOG.error("context", e);
			}

		}
		else
		{
			searchQueryData.setValue(component.getProductCodes().toLowerCase());
		}
		if (searchQueryData.getValue() != null)
		{
			final SearchStateData searchState = new SearchStateData();
			searchState.setQuery(searchQueryData);
			final PageableData pageableData = createPageableData(pageNumber, 24, null, ShowMode.Page);
			searchPageData = siteOneSDSProductSearchFacade.curatedPLPSearch(searchState, pageableData, CURATED_PLP);
			cleanCategoryFacets(searchPageData);
		}

		if (null != searchPageData)
		{
			return searchPageData;
		}
		else
		{
			return null;
		}
	}

	private void getAllSavedList(final Model model)
	{
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
		}

		final LoginForm loginForm = new LoginForm();
		model.addAttribute(loginForm);
	}

	protected void cleanCategoryFacets(final ProductSearchPageData<SearchStateData, ProductData> searchPageData)
	{
		final List<FacetData<SearchStateData>> facets = searchPageData.getFacets();
		if (CollectionUtils.isNotEmpty(facets))
		{
			//Clean up the category facets
			for (final FacetData<SearchStateData> facetData : facets)
			{
				if ("socategory".equalsIgnoreCase(facetData.getCode()))
				{
					final List<FacetValueData> facetValueDatasToDelete = new ArrayList();
					for (final FacetValueData facetValueData : facetData.getValues())
					{
						//Remove all SH17 categories or any categoy that's not L2 or L3
						if (facetValueData.getCode().startsWith("SH17") || (facetValueData.getCode().length() != L2_CATEGORY_LENGTH
								&& facetValueData.getCode().length() != L3_CATEGORY_LENGTH))
						{
							facetValueDatasToDelete.add(facetValueData);
						}
					}
					facetData.getValues().removeAll(facetValueDatasToDelete);
				}
			}
		}
	}
}
