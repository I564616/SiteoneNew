/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import com.siteone.core.model.InventoryUPCModel;
import com.siteone.core.services.SiteOneProductUOMService;


/**
 * @author 1219341
 *
 */
public class ProductPriceRangeStoreValueProvider extends AbstractPropertyFieldValueProvider
		implements FieldValueProvider, Serializable
{

	private static final Logger log = Logger.getLogger(ProductPriceRangeStoreValueProvider.class);

	private SiteOneProductUOMService siteOneProductUOMService;
	private FieldNameProvider fieldNameProvider;
	private static DecimalFormat decimalFormatMultiplier = new DecimalFormat(".##");

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		final List<FieldValue> fieldValues = new ArrayList<FieldValue>();
		Float hideUomInventoryMultiplier = Float.valueOf("0.0");
		boolean hideUom = false;

		if (model instanceof ProductModel)
		{

			final ProductModel product = (ProductModel) model;
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
			final Collection<VariantProductModel> variants = baseProduct.getVariants();
			if (CollectionUtils.isNotEmpty(variants))
			{
				final List<PriceRowModel> allPricesInfos = new ArrayList<>();
				final Map<String, List<PriceRowModel>> storePriceMap = new HashMap<>();

				// collect all price infos
				for (final VariantProductModel variant : variants)
				{
					for (final PriceRowModel priceRow : variant.getEurope1Prices())
					{
						if (priceRow.getMinqtd() == 1
								&& (null != priceRow.getPointOfService() && priceRow.getPointOfService().getIsActive()))
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
					final String priceRange = getPriceRangeString(storePriceMapEntry.getValue(), hideUom, hideUomInventoryMultiplier);
					addFieldValues(fieldValues, indexedProperty, priceRange, storePriceMapEntry.getKey());
				}
			}
		}
		else
		{
			throw new FieldValueProviderException("Cannot get field for non-product item");
		}

		return fieldValues;
	}


	protected String getPriceRangeString(final List<PriceRowModel> allPricesInfos, final boolean hideUom,
			final Float hideUomInventoryMultiplier)
	{
		Collections.sort(allPricesInfos, PriceRangeComparator.INSTANCE);

		final PriceRowModel lowest = allPricesInfos.get(0);
		final PriceRowModel highest = allPricesInfos.get(allPricesInfos.size() - 1);
		if (hideUom && hideUomInventoryMultiplier > 0.0f)
		{
			log.debug("inside pricerange multiplier ==" + hideUomInventoryMultiplier);
			final double multiplier = Double.parseDouble(decimalFormatMultiplier.format(hideUomInventoryMultiplier.doubleValue()));
			return SolrPriceRange.buildSolrPropertyFromPriceRows(lowest.getPrice().doubleValue() * multiplier,
					lowest.getCurrency().getIsocode(), highest.getPrice().doubleValue() * multiplier,
					highest.getCurrency().getIsocode());
		}
		else
		{
			return SolrPriceRange.buildSolrPropertyFromPriceRows(lowest.getPrice().doubleValue(), lowest.getCurrency().getIsocode(),
					highest.getPrice().doubleValue(), highest.getCurrency().getIsocode());
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