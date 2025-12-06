/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.commerceservices.stock.CommerceStockService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;
import de.hybris.platform.stock.StockService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.variants.model.VariantProductModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;

import com.google.common.collect.Lists;
import com.siteone.core.services.SiteOneStockLevelService;


/**
 * @author SMondal
 *
 */
public class ProductInventoryFlagValueProvider extends AbstractPropertyFieldValueProvider
		implements FieldValueProvider, Serializable
{
	private StockService stockService;
	private CommerceStockService commerceStockService;
	private FieldNameProvider fieldNameProvider;

	@Resource(name = "siteOneStockLevelService")
	private SiteOneStockLevelService siteOneStockLevelService;

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		if (model instanceof ProductModel)
		{
			final ProductModel product = (ProductModel) model;
			final Collection<FieldValue> fieldValues = new ArrayList<FieldValue>();
			final Collection<VariantProductModel> variants = Lists.newArrayList(product.getVariants());
			final Collection<PointOfServiceModel> pointOfServiceList = product.getStores();
			final Map<String, Object> storeIds = new HashMap<String, Object>();

			if (CollectionUtils.isNotEmpty(variants))
			{
				variants.removeIf(v -> BooleanUtils.isTrue(v.getIsProductDiscontinued()));
			}
			if (CollectionUtils.isEmpty(variants))
			{
				if (CollectionUtils.isNotEmpty(pointOfServiceList))
				{
					for (final PointOfServiceModel pointOfService : pointOfServiceList)
					{
						fieldValues.addAll(
								createFieldValueForSimpleProduct(product, pointOfService, indexedProperty, pointOfService.getStoreId()));
					}
				}
			}
			else if (CollectionUtils.isNotEmpty(variants) && variants.size() == 1)
			{
				final Collection<PointOfServiceModel> variantPointOfServiceList = variants.iterator().next().getStores();
				if (CollectionUtils.isNotEmpty(variantPointOfServiceList))
				{
					for (final PointOfServiceModel pointOfService : variantPointOfServiceList)
					{
						fieldValues.addAll(createFieldValueForSimpleProduct(variants.iterator().next(), pointOfService, indexedProperty,
								pointOfService.getStoreId()));
					}
				}
			}
			else
			{

				for (final VariantProductModel variantProductModel : variants)
				{
					boolean inventoryFlag = false;
					final Collection<PointOfServiceModel> variantsPointOfServiceList = variantProductModel.getStores();
					if (CollectionUtils.isNotEmpty(variantsPointOfServiceList))
					{
						for (final PointOfServiceModel pointOfServiceModel : variantsPointOfServiceList)
						{
							inventoryFlag = isInventoryFlag(variantProductModel, pointOfServiceModel);
							if (inventoryFlag)
							{
								storeIds.put(pointOfServiceModel.getStoreId(), pointOfServiceModel);
							}
						}
					}
				}
				for (final Map.Entry<String, Object> entry : storeIds.entrySet())
				{
					final String storeId = entry.getKey();
					final List<FieldValue> locfieldValues = new ArrayList<FieldValue>();
					fieldValues.addAll(addVariantFieldValues(locfieldValues, indexedProperty, Boolean.TRUE, storeId));
				}
			}
			return fieldValues;
		}
		else
		{
			throw new FieldValueProviderException("Cannot get inventoryFlag of non-product item");
		}
	}



	protected List<FieldValue> createFieldValueForSimpleProduct(final ProductModel product,
			final PointOfServiceModel pointOfService, final IndexedProperty indexedProperty, final String storeId)
	{
		final List<FieldValue> fieldValues = new ArrayList<FieldValue>();
		if (pointOfService != null)
		{
			addFieldValues(fieldValues, indexedProperty, Boolean.valueOf(isInventoryFlag(product, pointOfService)), storeId);
		}
		return fieldValues;
	}


	protected boolean isInventoryFlag(final ProductModel product, final PointOfServiceModel pointOfService)
	{
		boolean inventoryFlag = false;
		Long stockLevel = Long.valueOf(0);
		final Collection<StockLevelModel> stockLevelList = getStockService().getStockLevels(product,
				pointOfService.getWarehouses());
		if (CollectionUtils.isNotEmpty(stockLevelList))
		{
			Integer quantityLevel = (((List<StockLevelModel>) stockLevelList).get(0).getQuantityLevel());
			stockLevel = getCommerceStockService().getStockLevelForProductAndPointOfService(product, pointOfService);
			Boolean forceInStock = (BooleanUtils.isTrue(((List<StockLevelModel>) stockLevelList).get(0).getForceInStock())
					&& (BooleanUtils.isNotTrue(product.getInventoryCheck()) || BooleanUtils.isNotTrue(((List<StockLevelModel>) stockLevelList).get(0).getRespectInventory())));
			if ((null != stockLevel && stockLevel <= Long.valueOf(0)) && (null != quantityLevel && quantityLevel <= 0)
					&& (null != product.getInventoryCheck() && product.getInventoryCheck()) && BooleanUtils.isNotTrue(forceInStock))
			{
				inventoryFlag = true;
			}
		}
		else if (CollectionUtils.isEmpty(stockLevelList))
		{
			inventoryFlag = false;
		}
		return inventoryFlag;
	}

	protected void addFieldValues(final List<FieldValue> fieldValues, final IndexedProperty indexedProperty, final Object value,
			final String storeId)
	{
		final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, storeId);
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, value));
		}
	}

	protected List<FieldValue> addVariantFieldValues(final List<FieldValue> fieldValues, final IndexedProperty indexedProperty,
			final Object value, final String storeId)
	{
		final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, storeId);
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, value));
		}
		return fieldValues;
	}

	/**
	 * @return the stockService
	 */
	public StockService getStockService()
	{
		return stockService;
	}

	/**
	 * @param stockService
	 *           the stockService to set
	 */
	public void setStockService(final StockService stockService)
	{
		this.stockService = stockService;
	}

	/**
	 * @return the commerceStockService
	 */
	public CommerceStockService getCommerceStockService()
	{
		return commerceStockService;
	}

	/**
	 * @param commerceStockService
	 *           the commerceStockService to set
	 */
	public void setCommerceStockService(final CommerceStockService commerceStockService)
	{
		this.commerceStockService = commerceStockService;
	}

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