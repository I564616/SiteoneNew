/**
 *
 */
package com.siteone.core.product.dao.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;

import jakarta.annotation.Resource;

import com.siteone.core.model.InventoryUPCModel;
import com.siteone.core.product.dao.SiteOneProductUOMDao;


/**
 * @author 1091124
 *
 */
public class DefaultSiteOneProductUOMDao extends AbstractItemDao implements SiteOneProductUOMDao
{
	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.product.dao.SiteOneProductUOMDao#getInventoryUOMByCode(java.lang.String)
	 */
	@Resource(name = "productService")
	private ProductService productService;

	@Override
	public List<InventoryUPCModel> getSellableUOMsById(final String code)
	{
		final ProductModel product = productService.getProductForCode(code);
		return product.getUpcData();
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.product.dao.SiteOneProductUOMDao#getInventoryUOMById(java.lang.Integer)
	 */
	@Override
	public InventoryUPCModel getInventoryUOMById(final String id)
	{

		final String queryString = "SELECT {" + Item.PK + "} " //
				+ "FROM {" + InventoryUPCModel._TYPECODE + "} " //
				+ "WHERE {" + InventoryUPCModel.INVENTORYUPCID + "}=?id";
		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("id", id);
		final SearchResult<InventoryUPCModel> result = getFlexibleSearchService().search(query);
		final int resultCount = result.getTotalCount();
		if (resultCount == 0)
		{
			return null;
		}
		else
		{
			return result.getResult().get(0);
		}

	}



}
