/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.entity.SolrPriceRange;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.variants.model.VariantProductModel;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.siteone.core.model.InventoryUPCModel;
import com.siteone.core.search.solrfacetsearch.provider.util.IndexProductPriceData;
import com.siteone.core.services.SiteOneProductUOMService;


/**
 * @author i849388
 *
 */
public class ProductPriceCachingRangeStoreValueProvider extends AbstractProductPriceCachingValueProvider
		implements FieldValueProvider, Serializable

{
	private static final Logger log = Logger.getLogger(ProductPriceCachingRangeStoreValueProvider.class);

	private SiteOneProductUOMService siteOneProductUOMService;
	private static DecimalFormat decimalFormatMultiplier = new DecimalFormat(".##");

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{

		final List<FieldValue> fieldValues = new ArrayList<>();
		Float hideUomInventoryMultiplier = Float.valueOf("0.0");
		boolean hideUom = false;

		if (model instanceof ProductModel)
		{
			final ProductModel product = (ProductModel) model;

			loadProductCache(product);

			final InventoryUPCModel inventory = product.getUpcData().stream().filter(upc -> upc.getBaseUPC().booleanValue())
					.findFirst().orElse(null);

			// make sure you have the baseProduct because variantProducts won't have other variants
			final ProductModel baseProduct = getBaseProduct(product);

			final List<InventoryUPCModel> inventoryUOM = baseProduct.getUpcData().stream()
					.filter(upc -> !upc.getBaseUPC().booleanValue()).collect(Collectors.toList());
			for (final InventoryUPCModel inventoryUOMModelData : inventoryUOM)
			{
				if (inventoryUOMModelData.getHideUPCOnline() != null && inventoryUOMModelData.getHideUPCOnline())
				{
					hideUom = true;
				}
				else
				{
					hideUomInventoryMultiplier = inventoryUOMModelData.getInventoryMultiplier();
					log.debug("Inside inventoryUOM list ==" + hideUomInventoryMultiplier);
				}
			}
			if (!hideUom && inventory != null && inventory.getHideUPCOnline() != null && inventory.getHideUPCOnline())
			{
				hideUom = true;
				log.debug("Inside inventory object ==" + hideUom);
			}

			if (CollectionUtils.isNotEmpty(baseProduct.getVariants()))
			{
				final Collection<String> allStores = threadLocalCache.get().getAllStores();
				for (final String storeId : allStores)
				{
					final String priceRange = getPriceRangeString(threadLocalCache.get().getStorePrices(storeId), hideUom,
							hideUomInventoryMultiplier);

					if (StringUtils.isNotEmpty(priceRange))
					{
						addFieldValues(fieldValues, indexedProperty, priceRange, storeId);
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

	protected String getPriceRangeString(final SortedSet<IndexProductPriceData> priceDataSet, final boolean hideUom,
			final Float hideUomInventoryMultiplier)
	{

		final IndexProductPriceData lowest = getLowestStorePriceNotZero(priceDataSet);
		if (lowest == null)
		{
			return null;
		}

		final IndexProductPriceData highest = priceDataSet.last();


		if (hideUom && hideUomInventoryMultiplier > 0.0f)
		{
			log.debug("inside pricerange multiplier ==" + hideUomInventoryMultiplier);
			final double multiplier = Double.parseDouble(decimalFormatMultiplier.format(hideUomInventoryMultiplier.doubleValue()));
			return SolrPriceRange.buildSolrPropertyFromPriceRows(lowest.getPrice().doubleValue() * multiplier,
					lowest.getCurrencyIsoCode(), highest.getPrice().doubleValue() * multiplier, highest.getCurrencyIsoCode());
		}
		else
		{
			return SolrPriceRange.buildSolrPropertyFromPriceRows(lowest.getPrice().doubleValue(), lowest.getCurrencyIsoCode(),
					highest.getPrice().doubleValue(), highest.getCurrencyIsoCode());
		}
	}


	protected void addFieldValues(final List<FieldValue> fieldValues, final IndexedProperty indexedProperty, final Object value,
			final String storeId)
	{
		if (value != null)
		{
			final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, storeId);
			for (final String fieldName : fieldNames)
			{
				fieldValues.add(new FieldValue(fieldName, value));
			}
		}
	}

	public ProductModel getBaseProduct(final ProductModel model)
	{
		final ProductModel baseProduct;
		if (model instanceof VariantProductModel)
		{
			final VariantProductModel variant = (VariantProductModel) model;
			baseProduct = variant.getBaseProduct();
		}
		else
		{
			baseProduct = model;
		}
		return baseProduct;
	}

	private IndexProductPriceData getLowestStorePriceNotZero(final SortedSet<IndexProductPriceData> storePrices)
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


	/**
	 * @return the siteOneProductUOMService
	 */
	public SiteOneProductUOMService getSiteOneProductUOMService()
	{
		return siteOneProductUOMService;
	}

	/**
	 * @param siteOneProductUOMService
	 *           the siteOneProductUOMService to set
	 */
	public void setSiteOneProductUOMService(final SiteOneProductUOMService siteOneProductUOMService)
	{
		this.siteOneProductUOMService = siteOneProductUOMService;
	}
}