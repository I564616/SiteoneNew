/**
 *
 */
package com.siteone.storefront.controllers.cms;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController.ShowMode;
import de.hybris.platform.acceleratorstorefrontcommons.forms.LoginForm;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.user.UserService;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.siteone.core.model.SiteOneCuratedPLPHSProductComponentModel;
import com.siteone.facades.category.SiteOneCategoryFacade;
import com.siteone.facades.savedList.SiteoneSavedListFacade;
import com.siteone.facades.savedList.data.SavedListData;
import com.siteone.facades.sds.SiteOneSDSProductSearchFacade;
import com.siteone.storefront.controllers.ControllerConstants;
import com.siteone.storefront.util.SiteOneSearchUtils;


/**
 * @author BS
 *
 */
@Controller("SiteOneCuratedPLPHSProductComponentController")
@RequestMapping(value = ControllerConstants.Actions.Cms.SiteOneCuratedPLPHSProductComponent)
public class SiteOneCuratedPLPHSProductComponentController
		extends AbstractAcceleratorCMSComponentController<SiteOneCuratedPLPHSProductComponentModel>
{
	private static final Logger LOG = Logger.getLogger(SiteOneCuratedPLPHSProductComponentController.class);
	private static final String CURATED_PLP = "CuratedPLP";
	private static final int TWENTYFOUR = 24;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "siteoneSavedListFacade")
	private SiteoneSavedListFacade siteoneSavedListFacade;

	@Resource(name = "siteOneSDSProductSearchFacade")
	private SiteOneSDSProductSearchFacade siteOneSDSProductSearchFacade;

	@Resource(name = "siteOneSearchUtils")
	private SiteOneSearchUtils siteOneSearchUtils;

	@Resource(name = "siteOnecategoryFacade")
	private SiteOneCategoryFacade siteOnecategoryFacade;

	@Resource(name = "imageConverter")
	private Converter<MediaModel, ImageData> imageConverter;

	@Override
	protected void fillModel(final HttpServletRequest request, final Model model,
			final SiteOneCuratedPLPHSProductComponentModel component)
	{

		int pageNumber = 0;

		ProductSearchPageData<SearchStateData, ProductData> searchResults = collectSearch(component, pageNumber, null, model);

		if (component.getProductBannerDesktop() != null)
		{
			model.addAttribute("productBannerDesktop", imageConverter.convert(component.getProductBannerDesktop()));
		}
		if (component.getProductBannerMobile() != null)
		{
			model.addAttribute("productBannerMobile", imageConverter.convert(component.getProductBannerMobile()));
		}
		if (component.getTitle() != null)
		{
			model.addAttribute("title", component.getTitle());
		}
		if (component.getHeadline() != null)
		{
			model.addAttribute("headline", component.getHeadline());
		}
		if (component.getDescription() != null)
		{
			model.addAttribute("description", component.getDescription());
		}
		if (component.getButtonLabel() != null)
		{
			model.addAttribute("buttonLabel", component.getButtonLabel());
		}
		if (component.getButtonURL() != null)
		{
			model.addAttribute("buttonURL", component.getButtonURL());
		}
		model.addAttribute("productList", siteOneSDSProductSearchFacade.sortByItemNumber(component, searchResults));
		getAllSavedList(model);
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

	private ProductSearchPageData<SearchStateData, ProductData> collectSearch(
			final SiteOneCuratedPLPHSProductComponentModel component, final int pageNumber, final String query, final Model model)
	{

		final ProductSearchPageData<SearchStateData, ProductData> searchResults = collectSearchProducts(component, pageNumber, query);
		//siteOnecategoryFacade.updatePriorityBrandFacet(searchResults);
		siteOneSearchUtils.attachImageUrls(searchResults, model);
		siteOneSearchUtils.populateModelForInventory(searchResults, model);
		siteOnecategoryFacade.updateSearchPageData(searchResults);
		return searchResults;
	}

	protected ProductSearchPageData<SearchStateData, ProductData> collectSearchProducts(
			final SiteOneCuratedPLPHSProductComponentModel component, final int pageNumber, final String query)
	{
		ProductSearchPageData<SearchStateData, ProductData> searchPageData = null;
		final SearchQueryData searchQueryData = new SearchQueryData();
		String productCodes = component.getProductCodes();
		final List<String> listOfProductCodes = Arrays.asList(productCodes.split(","));
		if(listOfProductCodes.size() > TWENTYFOUR) 
		{
			productCodes = listOfProductCodes.stream().limit(TWENTYFOUR).map(Object::toString).collect(Collectors.joining(","));
		}
		
		if (null != query)
		{

			try
			{
				searchQueryData.setValue(productCodes.toLowerCase() + "," + URLDecoder.decode(query, "UTF-8"));
			}
			catch (final UnsupportedEncodingException e)
			{
				LOG.error("context", e);
			}

		}
		else
		{
			searchQueryData.setValue(productCodes.toLowerCase());
		}
		if (searchQueryData.getValue() != null)
		{
			final SearchStateData searchState = new SearchStateData();
			searchState.setQuery(searchQueryData);
			final PageableData pageableData = createPageableData(pageNumber, 24, null, ShowMode.Page);
			searchPageData = siteOneSDSProductSearchFacade.curatedPLPSearch(searchState, pageableData, CURATED_PLP);
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
	
	
	
}
