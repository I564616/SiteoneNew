/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import com.siteone.core.services.SiteOneProductService;
import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.entity.SolrPriceRange;
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
public class ProductPriceStoreFacetValueProvider extends AbstractPropertyFieldValueProvider
		implements FieldValueProvider, Serializable
{

	private FieldNameProvider fieldNameProvider;
	@Resource(name = "siteOneProductService")
	private SiteOneProductService siteOneProductService;

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

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.solrfacetsearch.provider.FieldValueProvider#getFieldValues(de.hybris.platform.solrfacetsearch.
	 * config.IndexConfig, de.hybris.platform.solrfacetsearch.config.IndexedProperty, java.lang.Object)
	 */
	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		if (model instanceof ProductModel)
		{
			final ProductModel product = (ProductModel) model;
			final Collection<VariantProductModel> variants = siteOneProductService.getActiveVariantProducts(product);

			final Collection<FieldValue> fieldValues = new ArrayList<>();
			if (CollectionUtils.isEmpty(variants))
			{
				for (final PriceRowModel priceRow : product.getEurope1Prices())
				{
					if (priceRow.getMinqtd() == 1 && null != priceRow.getPointOfService()
							&& priceRow.getPointOfService().getIsActive())
					{
						fieldValues.addAll(createFieldValue(priceRow, indexedProperty));
					}
				}
			}
			else
			{
				final List<PriceRowModel> allPricesInfos = new ArrayList<>();
				final Map<String, List<PriceRowModel>> storePriceMap = new HashMap<>();

				// collect all price infos
				for (final VariantProductModel variant : variants)
				{
					if (variant.getHideList() == null || (!variant.getHideList().booleanValue()))
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
				}
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
					fieldValues.addAll(createFieldValue(storePriceMapEntry.getValue(), indexedProperty));
				}
			}


			return fieldValues;
		}
		else
		{
			throw new FieldValueProviderException("Cannot get sku of non-product item");
		}
	}

	protected String getPriceRangeString(final List<PriceRowModel> allPricesInfos)
	{
		allPricesInfos.removeIf(priceInfo -> BigDecimal.valueOf(priceInfo.getPrice()).compareTo(BigDecimal.ZERO) == 0);
		Collections.sort(allPricesInfos, PriceRangeComparator.INSTANCE);

		final PriceRowModel lowest = allPricesInfos.get(0);
		final PriceRowModel highest = allPricesInfos.get(allPricesInfos.size() - 1);
		return SolrPriceRange.buildSolrPropertyFromPriceRows(lowest.getPrice().doubleValue(), lowest.getCurrency().getIsocode(),
				highest.getPrice().doubleValue(), highest.getCurrency().getIsocode());
	}

	protected List<FieldValue> createFieldValue(final PriceRowModel value, final IndexedProperty indexedProperty)
			throws FieldValueProviderException
	{
		final List<FieldValue> fieldValues = new ArrayList<>();
		if (BigDecimal.valueOf(value.getPrice()).compareTo(BigDecimal.ZERO) != 0)
		{
			final List<String> rangeNameList = getRangeNameList(indexedProperty, value.getPrice(), value.getCurrency().getIsocode());
			final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty,
					value.getPointOfService().getStoreId() + "_" + value.getCurrency().getIsocode());
			addFieldValues(fieldValues, rangeNameList, value.getPrice(), fieldNames);
		}
		return fieldValues;
	}

	protected List<FieldValue> createFieldValue(final List<PriceRowModel> priceRows, final IndexedProperty indexedProperty)
			throws FieldValueProviderException
	{
		priceRows.removeIf(priceRow -> BigDecimal.valueOf(priceRow.getPrice()).compareTo(BigDecimal.ZERO) == 0);
		final List<FieldValue> fieldValues = new ArrayList<>();
		for (final PriceRowModel priceRow : priceRows)
		{
			final List<String> rangeNameList = getRangeNameList(indexedProperty, priceRow.getPrice(),
					priceRow.getCurrency().getIsocode());
			final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty,
					priceRow.getPointOfService().getStoreId() + "_" + priceRow.getCurrency().getIsocode());
			addFieldValues(fieldValues, rangeNameList, priceRow.getPrice(), fieldNames);
		}

		return fieldValues;
	}

	protected void addFieldValues(final Collection<FieldValue> fieldValues, final List<String> rangeNameList, final Double value,
			final Collection<String> fieldNames)
	{
		for (final String fieldName : fieldNames)
		{
			if (rangeNameList.isEmpty())
			{
				fieldValues.add(new FieldValue(fieldName, value));
			}
			else
			{
				for (final String rangeName : rangeNameList)
				{
					fieldValues.add(new FieldValue(fieldName, rangeName == null ? value : rangeName));
				}
			}
		}
	}







}

