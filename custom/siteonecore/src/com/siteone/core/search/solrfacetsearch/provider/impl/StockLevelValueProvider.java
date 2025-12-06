/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.commerceservices.stock.CommerceStockService;

/**
 * @author 1229803
 *
 */

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.variants.model.VariantProductModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;

import com.siteone.core.model.InventoryUPCModel;
import com.siteone.core.services.SiteOneStockLevelService;
import com.siteone.core.store.dao.SiteOnePointOfServiceDao;



public class StockLevelValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider, Serializable
{

	private CommerceStockService commerceStockService;
	private FieldNameProvider fieldNameProvider;
	private SiteOnePointOfServiceDao siteOnePointOfServiceDao;
	private SessionService sessionService;
	@Resource(name = "siteOneStockLevelService")
	private SiteOneStockLevelService siteOneStockLevelService;

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		if (model instanceof ProductModel)
		{

			final ProductModel product = (ProductModel) model;
			final Map<String, Object> storeIds = new HashMap<String, Object>();
			final Collection<VariantProductModel> variants = product.getVariants();
			final Collection<FieldValue> fieldValues = new ArrayList<FieldValue>();
			final Collection<PointOfServiceModel> pointOfServiceList = product.getStores();

			if (CollectionUtils.isEmpty(variants))
			{
				if (CollectionUtils.isNotEmpty(pointOfServiceList))
				{
					for (final PointOfServiceModel pointOfService : pointOfServiceList)
					{
						fieldValues.addAll(createFieldValue(product, pointOfService, indexedProperty, pointOfService.getStoreId()));
					}
				}
			}
			else
			{
				for (final VariantProductModel variantProduct : variants)
				{
					final Collection<PointOfServiceModel> varientsPointOfServiceList = variantProduct.getStores();
					if(CollectionUtils.isNotEmpty(varientsPointOfServiceList))
					{
						for (final PointOfServiceModel pointOfService : varientsPointOfServiceList)
						{
							if (isInStock(variantProduct, pointOfService))
							{
								storeIds.put(pointOfService.getStoreId(), pointOfService);
							}
						}
					}
				}

				for (final Map.Entry<String, Object> entry : storeIds.entrySet())
				{
					final String storeId = entry.getKey();
					final List<FieldValue> locfieldValues = new ArrayList<FieldValue>();
					addFieldValues(locfieldValues, indexedProperty, Boolean.TRUE, storeId);
					fieldValues.addAll(locfieldValues);
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
			addFieldValues(fieldValues, indexedProperty, Boolean.valueOf(isInStock(product, pointOfService)), storeId);
		}
		return fieldValues;
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

	protected Long getProductStockLevelStatus(final ProductModel product, final PointOfServiceModel pointOfService)
	{
		return getCommerceStockService().getStockLevelForProductAndPointOfService(product, pointOfService);
	}

	protected boolean isInStock(final ProductModel product, final PointOfServiceModel pointOfService)
	{
		return isInStock(product, getProductStockLevelStatus(product, pointOfService));
	}

	protected boolean isInStock(final ProductModel product, final Long stockLevel)
	{
		boolean UOMCheck = false;

		if (CollectionUtils.isNotEmpty(product.getUpcData()))
		{
			final Collection<InventoryUPCModel> count = product.getUpcData().stream()
					.filter(upcData -> BooleanUtils.isNotTrue(upcData.getHideUPCOnline())).collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(count) && count.size() <= 1)
			{
				for (final InventoryUPCModel upcData : product.getUpcData())
				{
					if (BooleanUtils.isNotTrue(upcData.getBaseUPC()) && BooleanUtils.isNotTrue(upcData.getHideUPCOnline()))
					{
						if (stockLevel < upcData.getInventoryMultiplier())
						{
							UOMCheck = true;
						}
					}
				}
			}
		}
		return (stockLevel != null && !stockLevel.equals(Long.valueOf(0)) && !UOMCheck);
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
	 * @param fieldNameProvider
	 *           the fieldNameProvider to set
	 */

	public void setFieldNameProvider(final FieldNameProvider fieldNameProvider)
	{
		this.fieldNameProvider = fieldNameProvider;
	}

	/**
	 * @return the fieldNameProvider
	 */
	public FieldNameProvider getFieldNameProvider()
	{
		return fieldNameProvider;
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
}

