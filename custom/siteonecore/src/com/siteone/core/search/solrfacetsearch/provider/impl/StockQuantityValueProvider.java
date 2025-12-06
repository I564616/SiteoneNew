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
import de.hybris.platform.search.restriction.impl.DefaultSearchRestrictionService;
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

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;

import com.google.common.collect.Lists;
import com.siteone.core.store.dao.SiteOnePointOfServiceDao;

public class StockQuantityValueProvider extends AbstractPropertyFieldValueProvider
		implements FieldValueProvider, Serializable {

	private CommerceStockService commerceStockService;
	private FieldNameProvider fieldNameProvider;
	private SiteOnePointOfServiceDao siteOnePointOfServiceDao;
	private SessionService sessionService;
	@Resource(name = "searchRestrictionService")
	private DefaultSearchRestrictionService searchRestrictionService;

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException {
		if (model instanceof ProductModel) {

			final ProductModel product = (ProductModel) model;
			final Map<String, Object> storeIds = new HashMap<String, Object>();
			searchRestrictionService.disableSearchRestrictions();
			Collection<VariantProductModel> variants = Lists.newArrayList(product.getVariants());
			final Collection<FieldValue> fieldValues = new ArrayList<FieldValue>();
			final Collection<PointOfServiceModel> pointOfServiceList = product.getStores();
			
			if(CollectionUtils.isNotEmpty(variants))
			{
				variants.removeIf(v -> BooleanUtils.isTrue(v.getIsProductDiscontinued()));
			}
			if (CollectionUtils.isEmpty(variants)) {
				if (CollectionUtils.isNotEmpty(pointOfServiceList)) {
					for (final PointOfServiceModel pointOfService : pointOfServiceList) {
						fieldValues.addAll(createFieldValue(product, pointOfService, indexedProperty,
								pointOfService.getStoreId()));
					}
				}
			}
			if(CollectionUtils.isNotEmpty(variants) && variants.size()==1)
					{
				final Collection<PointOfServiceModel> variantPointOfServiceList = variants.iterator().next().getStores();
				if (CollectionUtils.isNotEmpty(variantPointOfServiceList)) {
					for (final PointOfServiceModel pointOfService : variantPointOfServiceList) {
						fieldValues.addAll(createFieldValue(variants.iterator().next(), pointOfService, indexedProperty,
								pointOfService.getStoreId()));
					}
					}
					}
			searchRestrictionService.enableSearchRestrictions();
			return fieldValues;
		} else {
			throw new FieldValueProviderException("Cannot get stock of non-product item");
		}
	}

	protected List<FieldValue> createFieldValue(final ProductModel product, final PointOfServiceModel pointOfService,
			final IndexedProperty indexedProperty, final String storeId) {
		final List<FieldValue> fieldValues = new ArrayList<FieldValue>();
		Long stockLevel = getProductStockLevelStatus(product, pointOfService);

		if (pointOfService != null) {
			addFieldValues(fieldValues, indexedProperty, stockLevel == null ? 0 : stockLevel, storeId);
		}
		return fieldValues;
	}

	protected void addFieldValues(final List<FieldValue> fieldValues, final IndexedProperty indexedProperty,
			final Object value, final String storeId) {
		final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, storeId);
		for (final String fieldName : fieldNames) {
			fieldValues.add(new FieldValue(fieldName, value));
		}
	}

	protected Long getProductStockLevelStatus(final ProductModel product, final PointOfServiceModel pointOfService) {
		return getCommerceStockService().getStockLevelForProductAndPointOfService(product, pointOfService);
	}

	/*
	 * protected boolean InStockQuantity(final ProductModel product, final
	 * PointOfServiceModel pointOfService) { return
	 * InStockQuantity(getProductStockLevelStatus(product, pointOfService)); }
	 * 
	 * protected boolean InStockQuantity(final Long stockLevel) { return stockLevel
	 * != null && !stockLevel.equals(Long.valueOf(0)); }
	 */

	/**
	 * @return the commerceStockService
	 */
	public CommerceStockService getCommerceStockService() {
		return commerceStockService;
	}

	/**
	 * @param commerceStockService the commerceStockService to set
	 */
	public void setCommerceStockService(final CommerceStockService commerceStockService) {
		this.commerceStockService = commerceStockService;
	}

	/**
	 * @param fieldNameProvider the fieldNameProvider to set
	 */

	public void setFieldNameProvider(final FieldNameProvider fieldNameProvider) {
		this.fieldNameProvider = fieldNameProvider;
	}

	/**
	 * @return the fieldNameProvider
	 */
	public FieldNameProvider getFieldNameProvider() {
		return fieldNameProvider;
	}

	/**
	 * @return the sessionService
	 */
	public SessionService getSessionService() {
		return sessionService;
	}

	/**
	 * @param sessionService the sessionService to set
	 */
	public void setSessionService(final SessionService sessionService) {
		this.sessionService = sessionService;
	}

	/**
	 * @return the siteOnePointOfServiceDao
	 */
	public SiteOnePointOfServiceDao getSiteOnePointOfServiceDao() {
		return siteOnePointOfServiceDao;
	}

	/**
	 * @param siteOnePointOfServiceDao the siteOnePointOfServiceDao to set
	 */
	public void setSiteOnePointOfServiceDao(final SiteOnePointOfServiceDao siteOnePointOfServiceDao) {
		this.siteOnePointOfServiceDao = siteOnePointOfServiceDao;
	}
}
