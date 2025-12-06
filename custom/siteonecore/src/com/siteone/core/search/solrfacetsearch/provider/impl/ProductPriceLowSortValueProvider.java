
/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import com.siteone.core.services.SiteOneProductService;
import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.impl.ProductPriceRangeValueProvider.PriceRangeComparator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;
import de.hybris.platform.variants.model.VariantProductModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

import jakarta.annotation.Resource;


/**
 * @author 1219341
 *
 */
public class ProductPriceLowSortValueProvider extends AbstractPropertyFieldValueProvider
		implements FieldValueProvider, Serializable
{
	private FieldNameProvider fieldNameProvider;

	private PriceRowModel lowestPriceRow;
	@Resource(name = "siteOneProductService")
	private SiteOneProductService siteOneProductService;

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		final List<FieldValue> fieldValues = new ArrayList<>();
		final ProductModel baseProduct = (ProductModel) model;
		final Collection<VariantProductModel> variants = siteOneProductService.getActiveVariantProducts(baseProduct);

		if (CollectionUtils.isEmpty(variants))
		{
			for (final PriceRowModel priceRow : baseProduct.getEurope1Prices())
			{
				if (priceRow.getMinqtd() == 1 && null != priceRow.getPointOfService() && priceRow.getPointOfService().getIsActive())
				{
					fieldValues.addAll(createFieldValue(priceRow, indexedProperty));
				}
			}
		}
		else
		{
			final List<PriceRowModel> allPricesInfos = new ArrayList<>();
			final Map<String, List<PriceRowModel>> storePriceMap = new HashMap<>();
			for (final VariantProductModel variant : variants)
			{
				for (final PriceRowModel priceRow : variant.getEurope1Prices())
				{
					if (priceRow.getMinqtd() == 1 && null != priceRow.getPointOfService()
							&& priceRow.getPointOfService().getIsActive())
					{
						allPricesInfos.add(priceRow);
					}
				}
			}
			allPricesInfos.removeIf(priceRow -> BigDecimal.valueOf(priceRow.getPrice()).compareTo(BigDecimal.ZERO) == 0);
			for (final PriceRowModel priceRowModel : allPricesInfos)
			{
				if (null != priceRowModel.getPointOfService())
				{
					final String storeId = priceRowModel.getPointOfService().getStoreId();
					if (storePriceMap.get(storeId) != null)
					{
						storePriceMap.get(storeId).add(priceRowModel);
					}
					else
					{
						final List<PriceRowModel> storesPriceRows = new ArrayList<>();
						storesPriceRows.add(priceRowModel);
						storePriceMap.put(storeId, storesPriceRows);
					}
				}
			}
			for (final Map.Entry<String, List<PriceRowModel>> storePriceMapEntry : storePriceMap.entrySet())
			{
				Collections.sort(storePriceMapEntry.getValue(), PriceRangeComparator.INSTANCE);
				lowestPriceRow = storePriceMapEntry.getValue().get(0);
				fieldValues.addAll(createFieldValue(lowestPriceRow, indexedProperty));
			}
		}
		return fieldValues;
	}



	protected List<FieldValue> createFieldValue(final PriceRowModel value, final IndexedProperty indexedProperty)
			throws FieldValueProviderException
	{
		final List<FieldValue> fieldValues = new ArrayList<>();
		final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty,
				value.getPointOfService().getStoreId());

		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, value.getPrice()));

		}
		return fieldValues;
	}



	/**
	 * @return the fieldNameProvider
	 */
	public FieldNameProvider getFieldNameProvider()
	{
		return fieldNameProvider;
	}



	/**
	 * @param fieldNameProvider
	 *           the fieldNameProvider to set
	 */
	public void setFieldNameProvider(final FieldNameProvider fieldNameProvider)
	{
		this.fieldNameProvider = fieldNameProvider;
	}

}
