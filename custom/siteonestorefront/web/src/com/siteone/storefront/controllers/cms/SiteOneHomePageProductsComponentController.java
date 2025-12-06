/**
 *
 */
package com.siteone.storefront.controllers.cms;

import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.siteone.core.model.SiteOneHomePageProductsComponentModel;
import com.siteone.storefront.controllers.ControllerConstants;


/**
 * @author SMondal
 *
 */

@Controller("SiteOneHomePageProductsComponentController")
@RequestMapping(value = ControllerConstants.Actions.Cms.SiteOneHomePageProductsComponent)
public class SiteOneHomePageProductsComponentController
		extends AbstractAcceleratorCMSComponentController<SiteOneHomePageProductsComponentModel>
{
	protected static final List<ProductOption> PRODUCT_OPTIONS = Arrays.asList(ProductOption.BASIC, ProductOption.CUSTOMER_PRICE,
			ProductOption.PRICE, ProductOption.SUMMARY, ProductOption.STOCK, ProductOption.AVAILABILITY_MESSAGE,
			ProductOption.PRICE_RANGE, ProductOption.URL, ProductOption.PROMOTIONS);

	@Resource(name = "accProductFacade")
	private ProductFacade productFacade;

	@Override
	protected void fillModel(final HttpServletRequest request, final Model model,
			final SiteOneHomePageProductsComponentModel component)
	{
		final List<ProductData> products = new ArrayList<>();

		final Set<ProductModel> featureProduct = component.getProducts();
		featureProduct.forEach(product -> {
			ProductData productData = new ProductData();
			productData = productFacade.getProductForOptions(product, PRODUCT_OPTIONS);
			products.add(productData);
		});
		model.addAttribute("recommendedProducts", products);
		model.addAttribute("title", component.getTitle());
	}


}
