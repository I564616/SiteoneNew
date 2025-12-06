/**
 *
 */
package com.siteone.core.search.solrfacetsearch.index.dao;

import de.hybris.platform.core.model.product.ProductModel;

import com.siteone.core.search.solrfacetsearch.provider.util.IndexProductPriceContainer;

/**
 * @author i849388
 *
 */
public interface IndexProductPriceDAO
{

	IndexProductPriceContainer loadProductPricesFromActivePO(ProductModel model);
}
