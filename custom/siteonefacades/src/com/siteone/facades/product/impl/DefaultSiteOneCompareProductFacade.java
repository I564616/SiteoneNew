/**
 *
 */
package com.siteone.facades.product.impl;

import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.annotation.Resource;

import com.siteone.facades.product.SiteOneCompareProductFacade;
import com.siteone.facades.product.SiteOneProductFacade;


public class DefaultSiteOneCompareProductFacade implements SiteOneCompareProductFacade
{

	private static final String COMMA = ",";


	@Resource(name = "productFacade")
	private ProductFacade productFacade;

	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;


	@Override
	public List<ProductData> fetchCompareProductDetails(final String compProductCodes)
	{
		{
			final String productCodes[] = compProductCodes.split(COMMA);

			final List<ProductData> compareProductList = new ArrayList<ProductData>();
			ProductData productData;
			for (final String code : productCodes)
			{

				final List<ProductOption> options = new ArrayList<>(Arrays.asList(ProductOption.BASIC, ProductOption.URL,
						ProductOption.PRICE, ProductOption.SUMMARY, ProductOption.DESCRIPTION, ProductOption.GALLERY,
						ProductOption.CATEGORIES, ProductOption.REVIEW, ProductOption.CLASSIFICATION, ProductOption.STOCK,
						ProductOption.VOLUME_PRICES, ProductOption.PRICE_RANGE, ProductOption.DELIVERY_MODE_AVAILABILITY,
						ProductOption.AVAILABILITY_MESSAGE, ProductOption.CUSTOMER_PRICE));

				productData = productFacade.getProductForCodeAndOptions(code, options);
				siteOneProductFacade.updateUOMPriceForSingleUOM(productData);

				compareProductList.add(productData);
			}


			return compareProductList;


		}

	}

}