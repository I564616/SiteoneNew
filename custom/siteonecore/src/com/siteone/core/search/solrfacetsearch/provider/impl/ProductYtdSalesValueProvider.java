/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import com.siteone.core.model.ProductSalesInfoModel;
import com.siteone.core.services.SiteOneProductSalesService;
import com.siteone.core.services.SiteOneStockLevelService;
import com.siteone.core.store.dao.SiteOnePointOfServiceDao;
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
import org.apache.commons.collections4.CollectionUtils;

import jakarta.annotation.Resource;
import java.io.Serializable;
import java.util.*;


/**
 * @author 1129929
 *
 */
public class ProductYtdSalesValueProvider extends AbstractPropertyFieldValueProvider
		implements FieldValueProvider, Serializable
{
	private StockService stockService;
	private CommerceStockService commerceStockService;
	private FieldNameProvider fieldNameProvider;
	private SiteOnePointOfServiceDao siteOnePointOfServiceDao;
	private SessionService sessionService;

	@Resource(name = "siteOneStockLevelService")
	private SiteOneStockLevelService siteOneStockLevelService;

	@Resource(name = "siteOneProductSalesService")
	private SiteOneProductSalesService siteOneProductSalesService;

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		if (model instanceof ProductModel)
		{
			final ProductModel product = (ProductModel) model;
			final Collection<FieldValue> fieldValues = new ArrayList<FieldValue>();
			final Collection<ProductSalesInfoModel> productSalesInfoList = siteOneProductSalesService.getSalesByProductCode(product.getCode());

			if (CollectionUtils.isNotEmpty(productSalesInfoList))
			{
				for (final ProductSalesInfoModel productSalesInfo : productSalesInfoList)
				{
					fieldValues.addAll(createFieldValue(product, productSalesInfo, indexedProperty));
				}
			}

			return fieldValues;
		}
		else
		{
			throw new FieldValueProviderException("Cannot get stock of non-product item");
		}
	}


	protected List<FieldValue> createFieldValue(final ProductModel product,
			final ProductSalesInfoModel productSalesInfoModel, final IndexedProperty indexedProperty)
	{
		final List<FieldValue> fieldValues = new ArrayList<FieldValue>();
		addFieldValues(fieldValues, indexedProperty, productSalesInfoModel.getYtdSales(), productSalesInfoModel.getRegion());
		return fieldValues;
	}


	protected void addFieldValues(final List<FieldValue> fieldValues, final IndexedProperty indexedProperty, final double sales, final String region)
	{
		final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, region);
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, sales));
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

