/**
 *
 */
package com.siteone.core.search.solrfacetsearch.index.dao.impl;

import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.search.restriction.impl.DefaultSearchRestrictionService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.variants.model.VariantProductModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;

import com.siteone.core.search.solrfacetsearch.index.dao.IndexProductPriceDAO;
import com.siteone.core.search.solrfacetsearch.provider.util.IndexProductPriceContainer;
import com.siteone.core.search.solrfacetsearch.provider.util.IndexProductPriceData;


/**
 * @author i849388
 *
 */
public class IndexProductPriceDAOImpl implements IndexProductPriceDAO
{

	private FlexibleSearchService flexibleSearchService;
	@Resource(name = "searchRestrictionService")
	private DefaultSearchRestrictionService searchRestrictionService;
	private static final String QUERY = buildQuery();

	@Override
	public IndexProductPriceContainer loadProductPricesFromActivePO(final ProductModel baseProduct)
	{

		final IndexProductPriceContainer cont = new IndexProductPriceContainer();

		cont.setBaseProductId(baseProduct.getCode());

		final List<String> productIds = new ArrayList<String>();

		productIds.add(baseProduct.getCode());
		final Map<String, Boolean> productInventoryCheck = new HashMap<>();

		if (CollectionUtils.isNotEmpty(baseProduct.getVariants()))
		{
			for (final VariantProductModel variant : baseProduct.getVariants())
			{
				if ((variant.getHideList() == null || (!variant.getHideList().booleanValue()))
						&& (variant.getIsProductDiscontinued() == null || (!variant.getIsProductOffline().booleanValue())))
				{
					productIds.add(variant.getCode());
					productInventoryCheck.put(variant.getCode(),
							variant.getInventoryCheck() == null ? Boolean.FALSE : variant.getInventoryCheck());
				}
			}
		}
		searchRestrictionService.disableSearchRestrictions();
		final FlexibleSearchQuery query = new FlexibleSearchQuery(QUERY);
		query.addQueryParameter("productIDs", productIds);

		query.setResultClassList(Arrays.asList(String.class, Double.class, String.class, String.class));
		
		final SearchResult<List<Object>> result = flexibleSearchService.search(query);
                searchRestrictionService.enableSearchRestrictions();
		for (final List<Object> row : result.getResult())
		{

			final IndexProductPriceData priceData = new IndexProductPriceData();
			priceData.setProductId((String) row.get(0));
			priceData.setPrice((Double) row.get(1));
			priceData.setStoreId((String) row.get(2));
			priceData.setCurrencyIsoCode((String) row.get(3));
			final String productId = (String) row.get(0);
			final String storeId = (String) row.get(2);
			if (productInventoryCheck.get(productId) != null && productInventoryCheck.get(productId).booleanValue())
			{
				if (getStockLevel(productId, storeId))
				{
					cont.addProductPriceData(priceData);
				}
			}
			else
			{
				cont.addProductPriceData(priceData);
			}
		}

		return cont;
	}

	/**
	 * @return the flexibleSearchService
	 */
	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	/**
	 * @param flexibleSearchService
	 *           the flexibleSearchService to set
	 */
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}

	private static String buildQuery()
	{

		final StringBuilder buffer = new StringBuilder();


		buffer.append("select {pr.productId}, {pr.price}, {po.storeId}, {cur.isocode} ");
		buffer.append(" from {").append(PriceRowModel._TYPECODE).append(" as pr ");
		buffer.append(" join ").append(PointOfServiceModel._TYPECODE).append(" as po on {pr.pointOfService} = {po.PK} ");
		buffer.append(" join ").append(CurrencyModel._TYPECODE).append(" as cur on {pr.currency} = {cur.pk} }");
		buffer.append("where {po.isActive} = 1 and {pr.minqtd} = 1 and {pr.productId} in (?productIDs)");

		return buffer.toString();
	}


	public boolean getStockLevel(final String pCode, final String storeId)
	{
		final String queryString = "select {w.pk} from {warehouse as w} where exists ({{select {s.pk} from {StockLevel as s} where {s.warehouse} = {w.pk} and {s.productCode} = ?pCode and ({s.available} > 0 or {s.quantityLevel} > 0)}}) and {w.code} = ?storeId";
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(queryString);
		fQuery.addQueryParameter("pCode", pCode);
		fQuery.addQueryParameter("storeId", storeId);
		final SearchResult<WarehouseModel> result = getFlexibleSearchService().search(fQuery);
		return result.getCount() > 0;
	}

}
