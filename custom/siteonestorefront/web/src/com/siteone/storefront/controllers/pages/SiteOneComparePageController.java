package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.impl.SearchBreadcrumbBuilder;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.acceleratorstorefrontcommons.forms.LoginForm;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.List;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.siteone.facades.product.SiteOneCompareProductFacade;
import com.siteone.facades.savedList.SiteoneSavedListFacade;
import com.siteone.facades.savedList.data.SavedListData;
import com.siteone.storefront.breadcrumbs.impl.SiteOneSearchBreadcrumbBuilder;
import com.siteone.storefront.controllers.ControllerConstants;


@Controller
@RequestMapping(value = "/compare")
public class SiteOneComparePageController extends AbstractSearchPageController
{

	private static final String COMPARE_PRODUCT = "compareProductPage";



	@Resource(name = "siteOneCompareProductFacade")
	private SiteOneCompareProductFacade siteOneCompareProductFacade;
	@Resource(name = "searchBreadcrumbBuilder")
	private SearchBreadcrumbBuilder searchBreadcrumbBuilder;
	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "siteoneSavedListFacade")
	private SiteoneSavedListFacade siteoneSavedListFacade;

	@GetMapping
	public String getComparePage(@RequestParam(value = "productCodes", required = false) final String productCodes,
			@RequestParam(value = "categoryCode", required = false) String categoryCode, final Model model)
			throws CMSItemNotFoundException
	{
		String url = null;
		final List<ProductData> productList = siteOneCompareProductFacade.fetchCompareProductDetails(productCodes);
		categoryCode = categoryCode.toUpperCase();
		if (CollectionUtils.isNotEmpty(productList))
		{
			for (final CategoryData category : productList.get(0).getCategories())
			{
				if (category != null)
				{
					if (category.getCode().toUpperCase().contains(categoryCode))
					{
						url = category.getUrl();
					}
				}
			}
		}

		getAllSavedList(model);
		model.addAttribute("productList", productList);
		model.addAttribute(WebConstants.BREADCRUMBS_KEY,
				((SiteOneSearchBreadcrumbBuilder) searchBreadcrumbBuilder).getBreadcrumbs(categoryCode, null, false));
		model.addAttribute("categoryCode", categoryCode);
		model.addAttribute("url", url);

		storeCmsPageInModel(model, getContentPageForLabelOrId(COMPARE_PRODUCT));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(COMPARE_PRODUCT));
		return ControllerConstants.Views.Pages.Product.CompareProductPage;


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