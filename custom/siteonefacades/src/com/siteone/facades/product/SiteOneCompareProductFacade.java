package com.siteone.facades.product;

import de.hybris.platform.commercefacades.product.data.ProductData;

import java.util.List;


public interface SiteOneCompareProductFacade
{

	List<ProductData> fetchCompareProductDetails(final String compProductCodes);
}