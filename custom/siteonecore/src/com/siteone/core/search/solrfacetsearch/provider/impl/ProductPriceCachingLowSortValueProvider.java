/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import com.siteone.core.services.SiteOneProductService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;

import de.hybris.platform.variants.model.VariantProductModel;
import org.apache.commons.collections4.CollectionUtils;

import com.siteone.core.search.solrfacetsearch.provider.util.IndexProductPriceData;

import jakarta.annotation.Resource;


/**
 * @author i849388
 *
 */
public class ProductPriceCachingLowSortValueProvider extends AbstractProductPriceCachingValueProvider
		implements FieldValueProvider, Serializable
{
	@Resource(name = "siteOneProductService")
	private SiteOneProductService siteOneProductService;

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{

		final List<FieldValue> fieldValues = new ArrayList<>();

		if (model instanceof ProductModel)
		{

			final ProductModel baseProduct = (ProductModel) model;

			final Collection<VariantProductModel> variants = siteOneProductService.getActiveVariantProducts(baseProduct);

			loadProductCache(baseProduct);

			if (CollectionUtils.isEmpty(variants)) {

				for (final IndexProductPriceData priceData : this.threadLocalCache.get().getProductPrices(baseProduct.getCode()))
				{
					fieldValues.addAll(createFieldValue(priceData, indexedProperty));
				}
			}
			else
			{
				final Collection<String> allStores = threadLocalCache.get().getAllStores();
				for (final String storeId : allStores)
				{
					final IndexProductPriceData lowestPrice = getLowestStorePrice(threadLocalCache.get().getStorePrices(storeId));

					if (lowestPrice != null)
					{
						fieldValues.addAll(createFieldValue(lowestPrice, indexedProperty));
					}
				}
			}


		}
		else
		{
			throw new FieldValueProviderException("Cannot get sku of non-product item");
		}

		return fieldValues;
	}

	private IndexProductPriceData getLowestStorePrice(final SortedSet<IndexProductPriceData> storePrices)
	{

		IndexProductPriceData result = null;

		for (final IndexProductPriceData priceData : storePrices)
		{
			if (priceData.getPrice() > 0.0D)
			{
				result = priceData;
				break;
			}
		}

		return result;
	}

	protected List<FieldValue> createFieldValue(final IndexProductPriceData priceData, final IndexedProperty indexedProperty)
			throws FieldValueProviderException
	{
		final List<FieldValue> fieldValues = new ArrayList<>();
		final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty,
				priceData.getStoreId());

		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, priceData.getPrice()));

		}
		return fieldValues;
	}

}
