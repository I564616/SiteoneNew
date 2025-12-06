/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.commerceservices.stock.CommerceStockService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.servicelayer.session.SessionService;
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
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.siteone.core.store.dao.SiteOnePointOfServiceDao;


/**
 * @author 1129929
 *
 */
public class NLAProductStockValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider, Serializable
{
	private StockService stockService;
	private CommerceStockService commerceStockService;
	private FieldNameProvider fieldNameProvider;
	private SiteOnePointOfServiceDao siteOnePointOfServiceDao;
	private SessionService sessionService;


	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		if (model instanceof ProductModel)
		{
			final ProductModel product = (ProductModel) model;
			final Collection<FieldValue> fieldValues = new ArrayList<FieldValue>();

			final Collection<VariantProductModel> variants = product.getVariants();
			if (CollectionUtils.isEmpty(variants) || (CollectionUtils.isNotEmpty(variants) && variants.size() > 1))
			{
				if (!product.getIsProductDiscontinued())
				{
					final Collection<PointOfServiceModel> pointOfServiceList = product.getStores();
					if (CollectionUtils.isNotEmpty(pointOfServiceList))
					{
						for (final PointOfServiceModel pointOfService : pointOfServiceList)
						{
							fieldValues.addAll(createFieldValue(product, pointOfService, indexedProperty, pointOfService.getStoreId()));
						}
					}
				}
			}
			else
			{
				for (final VariantProductModel variant : variants)
				{
					if (!variant.getIsProductDiscontinued())
					{
						final Collection<PointOfServiceModel> pointOfServiceList = variant.getStores();
						if (CollectionUtils.isNotEmpty(pointOfServiceList))
						{
							for (final PointOfServiceModel pointOfService : pointOfServiceList)
							{
								fieldValues
										.addAll(createFieldValue(variant, pointOfService, indexedProperty, pointOfService.getStoreId()));
							}
						}
					}
				}
			}

			return fieldValues;
		}
		else
		{
			throw new FieldValueProviderException("Cannot get stock of non-product item");
		}
	}

	protected List<FieldValue> createFieldValue(final ProductModel product, final PointOfServiceModel pointOfService,
			final IndexedProperty indexedProperty, final String storeId)
	{
		final List<FieldValue> fieldValues = new ArrayList<FieldValue>();
		if (pointOfService != null)
		{
			addFieldValues(fieldValues, indexedProperty, Boolean.valueOf(isSellableInventoryHit(product, pointOfService)), storeId);
		}
		return fieldValues;
	}


	protected boolean isSellableInventoryHit(final ProductModel product, final PointOfServiceModel pointOfService)
	{
		boolean isSellable = true;
		Integer inventoryHitCount = Integer.valueOf(0);
		Long stockLevel = Long.valueOf(0);
		final Collection<StockLevelModel> stockLevelList = getStockService().getStockLevels(product,
				pointOfService.getWarehouses());
		if (CollectionUtils.isNotEmpty(stockLevelList))
		{
			inventoryHitCount = (((List<StockLevelModel>) stockLevelList).get(0).getInventoryHit());
			stockLevel = getCommerceStockService().getStockLevelForProductAndPointOfService(product, pointOfService);
			if ((null != stockLevel && stockLevel <= Long.valueOf(0))
					&& ((null != inventoryHitCount && inventoryHitCount <= 4) || (null == inventoryHitCount)))
			{
				isSellable = false;
			}
		}
		else if (CollectionUtils.isEmpty(stockLevelList))
		{
			isSellable = false;

		}
		return isSellable;
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
	 * @return the siteOnePointOfServiceDao
	 */
	public SiteOnePointOfServiceDao getSiteOnePointOfServiceDao()
	{
		return siteOnePointOfServiceDao;
	}

	/**
	 * @param siteOnePointOfServiceDao
	 *           the siteOnePointOfServiceDao to set
	 */
	public void setSiteOnePointOfServiceDao(final SiteOnePointOfServiceDao siteOnePointOfServiceDao)
	{
		this.siteOnePointOfServiceDao = siteOnePointOfServiceDao;
	}

	/**
	 * @return the sessionService
	 */
	public SessionService getSessionService()
	{
		return sessionService;
	}

	/**
	 * @param sessionService
	 *           the sessionService to set
	 */
	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}

}

