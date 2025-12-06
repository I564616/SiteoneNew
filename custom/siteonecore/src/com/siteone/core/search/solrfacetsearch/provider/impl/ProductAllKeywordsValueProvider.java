/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import com.siteone.core.model.ProductSalesInfoModel;
import com.siteone.core.services.SiteOneProductSalesService;
import com.siteone.core.services.SiteOneStockLevelService;
import com.siteone.core.store.dao.SiteOnePointOfServiceDao;
import de.hybris.platform.catalog.model.KeywordModel;
import de.hybris.platform.commerceservices.stock.CommerceStockService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;
import de.hybris.platform.stock.StockService;
import org.apache.commons.collections4.CollectionUtils;

import jakarta.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author 1129929
 *
 */
public class ProductAllKeywordsValueProvider extends AbstractPropertyFieldValueProvider
		implements FieldValueProvider, Serializable
{
	private FieldNameProvider fieldNameProvider;
	private SessionService sessionService;


	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
												 final Object model) throws FieldValueProviderException
	{
		if (model instanceof ProductModel)
		{
			final ProductModel product = (ProductModel) model;
			final Collection<FieldValue> fieldValues = new ArrayList<FieldValue>();

			final String allKeywordString = product.getAllKeywords();
			final Collection<String> allKeywords = Arrays.stream(allKeywordString.split(","))
					.map(String::trim)
					.map(s -> s.replaceAll("\\r", ""))
					.collect(Collectors.toList());

			if (CollectionUtils.isNotEmpty(allKeywords))
			{
				for (final String keyword : allKeywords)
				{
					fieldValues.addAll(createFieldValue(product, keyword, indexedProperty));
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
												final String keyword, final IndexedProperty indexedProperty)
	{
		final List<FieldValue> fieldValues = new ArrayList<FieldValue>();
		addFieldValues(fieldValues, indexedProperty, keyword);
		return fieldValues;
	}


	protected void addFieldValues(final List<FieldValue> fieldValues, final IndexedProperty indexedProperty, final String keyword)
	{
		final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, "");
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, keyword));
		}
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
