/**
 *
 */
package com.siteone.storefront.controllers.cms;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.servicelayer.session.SessionService;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.siteone.core.category.service.SiteOneCategoryService;
import com.siteone.core.model.SiteOneFeatureProductComponentModel;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.storefront.controllers.ControllerConstants;


/**
 * @author 1190626
 *
 */
@Controller("SiteOneFeatureProductComponentController")
@RequestMapping(value = ControllerConstants.Actions.Cms.SiteOneFeatureProductComponent)
public class SiteOneFeatureProductComponentController
		extends AbstractAcceleratorCMSComponentController<SiteOneFeatureProductComponentModel>
{
	private static final Logger LOG = Logger.getLogger(SiteOneFeatureProductComponentController.class);
	private static final String CATALOG_ID = "siteoneProductCatalog";
	private static final String CATALOG_VERSION_ONLINE = "Online";


	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;

	@Resource(name = "categoryService")
	private SiteOneCategoryService siteOneCategoryService;

	@Resource
	CatalogVersionService catalogVersionService;

	@Override
	protected void fillModel(final HttpServletRequest request, final Model model,
			final SiteOneFeatureProductComponentModel component)
	{
		String placementPage = "homePage";
		String categoryId = "";
		String productId = "";
		final String sessionId = request.getSession().getId();
		final String request_uri = (String) request.getAttribute("jakarta.servlet.forward.request_uri");
		final String[] uriArr = request_uri.split("/");
		if (uriArr.length >= 5)
		{
			if ("c".equalsIgnoreCase(uriArr[3]))
			{
				String hybrisCategoryId = "";
				if (!ObjectUtils.isEmpty(uriArr[4]))
				{
					hybrisCategoryId = uriArr[4].toUpperCase();
				}

				final CatalogVersionModel catalogVersionModel = getSessionService().getAttribute("currentCatalogVersion");
				final CategoryModel category = siteOneCategoryService.getCategoryForCode(catalogVersionModel, hybrisCategoryId);
				categoryId = category.getPimCategoryId();
				placementPage = "categoryPage";
			}
			if ("p".equalsIgnoreCase(uriArr[3]))
			{
				productId = uriArr[4];
				placementPage = "itemPage";
			}
		}
		final String pagePosition = component.getTitle();
		final Object[] recommProduct = siteOneProductFacade.getRecommendedProductsToDisplay(placementPage, categoryId, productId,
				sessionId, pagePosition);

		List<ProductData> productList = new ArrayList<>();

		if (recommProduct[1] != null)
		{
			productList = (List<ProductData>) recommProduct[1];
		}
		model.addAttribute("recommendationTitle", recommProduct[2]);
		model.addAttribute("pagePosition", pagePosition);
		model.addAttribute("categoryProduct", productList);
	}

	public SessionService getSessionService()
	{
		return sessionService;
	}

	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}

}
