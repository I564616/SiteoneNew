/**
 *
 */
package com.siteone.core.search.solrfacetsearch.index.dao.impl;

import de.hybris.platform.commerceservices.stock.strategies.CommerceAvailabilityCalculationStrategy;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.search.restriction.impl.DefaultSearchRestrictionService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.variants.model.VariantProductModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;

import com.siteone.core.search.solrfacetsearch.index.dao.IndexProductInventoryDAO;
import com.siteone.core.search.solrfacetsearch.provider.util.IndexProductInventoryContainer;
import com.siteone.core.search.solrfacetsearch.provider.util.IndexProductPosInventoryData;


/**
 * @author i849388
 *
 */
public class IndexProductInventoryDAOImpl implements IndexProductInventoryDAO
{

	private FlexibleSearchService flexibleSearchService;

	private CommerceAvailabilityCalculationStrategy commerceStockLevelCalculationStrategy;

	@Resource(name = "searchRestrictionService")
	private DefaultSearchRestrictionService searchRestrictionService;

	private static final String QUERY = buildQuery();

	@Override
	public IndexProductInventoryContainer loadProductInventoryFromActivePO(final ProductModel baseProduct)
	{
		final IndexProductInventoryContainer cont = new IndexProductInventoryContainer();

		final Map<String, ProductModel> code2Prod = new HashMap<String, ProductModel>();

		code2Prod.put(baseProduct.getCode(), baseProduct);

		if (CollectionUtils.isNotEmpty(baseProduct.getVariants()))
		{
			for (final VariantProductModel variant : baseProduct.getVariants())
			{
				code2Prod.put(variant.getCode(), variant);
			}
		}
		searchRestrictionService.disableSearchRestrictions();
		final FlexibleSearchQuery query = new FlexibleSearchQuery(QUERY);
		query.addQueryParameter("productIDs", code2Prod.keySet());

		query.setResultClassList(Arrays.asList(StockLevelModel.class, String.class, Boolean.class, Boolean.class));
		
		final SearchResult<List<Object>> result = flexibleSearchService.search(query);
                searchRestrictionService.enableSearchRestrictions();
		//strategy needs a list, to avoid creating a new one all the time, we reuse this
		final List<StockLevelModel> list = new ArrayList<StockLevelModel>();

		ProductModel prod = null;

		//the following code takes advantage that there is only one warehouse per store, so (product,storeid) is unique
		for (final List<Object> row : result.getResult())
		{
			final StockLevelModel stockLevel = (StockLevelModel) row.get(0);
			final String storeId = (String) row.get(1);
			list.add(stockLevel);

			final IndexProductPosInventoryData inventoryData = getInventoryData(storeId, list);

			prod = code2Prod.get(inventoryData.getProductCode());
			inventoryData.setInventoryCheckFlag((null != prod.getInventoryCheck() && prod.getInventoryCheck()));

			inventoryData.setForceInStock(BooleanUtils.isTrue((Boolean) row.get(2))
					&& (BooleanUtils.isNotTrue(prod.getInventoryCheck()) || BooleanUtils.isNotTrue((Boolean) row.get(3))));
			cont.add(inventoryData);

			list.clear();

		}


		return cont;
	}

	private IndexProductPosInventoryData getInventoryData(final String storeId, final List<StockLevelModel> list)
	{

		final IndexProductPosInventoryData inventoryData = new IndexProductPosInventoryData();

		inventoryData.setProductCode(list.get(0).getProductCode());
		inventoryData.setStoreId(storeId);

		final Integer inventoryHitCount = list.get(0).getQuantityLevel();
		inventoryData.setInventoryHitCount(inventoryHitCount);

		final Long stockLevel = getCommerceStockLevelCalculationStrategy().calculateAvailability(list);

		inventoryData.setStockLevel(stockLevel);

		inventoryData.setStockLevelFlag((null != stockLevel && stockLevel <= Long.valueOf(0)));
		inventoryData.setInventoryHitCountFlag((null != inventoryHitCount && inventoryHitCount <= 0));

		return inventoryData;

	}

	private static String buildQuery()
	{

		final StringBuilder buffer = new StringBuilder();

		buffer.append("select {st.pk}, {pos.storeid}, {st.forceInStock}, {st.respectInventory} ");
		buffer.append(" from { ");
		buffer.append(" Product as p ");
		buffer.append(" join Product2PointOfServiceRelation as p2prel on {p.pk} = {p2prel.source} ");
		buffer.append(" join PointOfService as pos on {pos.pk} = {p2prel.target} ");
		buffer.append(" join PoS2WarehouseRel as p2w on {pos.pk} = {p2w.source} ");
		buffer.append(" join Warehouse as w on {w.pk} = {p2w.target} ");
		buffer.append(" join StockLevel as st on ({w.pk} = {st.warehouse} and {st.productcode} = {p.code}) ");
		buffer.append(" }");
		buffer.append(" where {pos.isActive} = 1 and {p.code} in (?productIDs)");

		return buffer.toString();
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

	/**
	 * @return the commerceStockLevelCalculationStrategy
	 */
	public CommerceAvailabilityCalculationStrategy getCommerceStockLevelCalculationStrategy()
	{
		return commerceStockLevelCalculationStrategy;
	}

	/**
	 * @param commerceStockLevelCalculationStrategy
	 *           the commerceStockLevelCalculationStrategy to set
	 */
	public void setCommerceStockLevelCalculationStrategy(
			final CommerceAvailabilityCalculationStrategy commerceStockLevelCalculationStrategy)
	{
		this.commerceStockLevelCalculationStrategy = commerceStockLevelCalculationStrategy;
	}




}
