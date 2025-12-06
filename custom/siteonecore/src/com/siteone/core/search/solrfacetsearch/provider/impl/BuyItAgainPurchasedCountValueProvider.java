/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


/**
 * @author pelango
 *
 */
public class BuyItAgainPurchasedCountValueProvider extends BuyItAgainProductCountCacheValueProvider
		implements FieldValueProvider, Serializable
{
	private static final Logger LOG = Logger.getLogger(BuyItAgainPurchasedCountValueProvider.class);

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		final OrderEntryModel orderEntry = (OrderEntryModel) model;
		final Collection<FieldValue> fieldValues = new ArrayList<>();
		fieldValues.addAll(createFieldValue(orderEntry, indexedProperty));
		return fieldValues;

	}

	protected Collection<FieldValue> createFieldValue(final OrderEntryModel orderEntry, final IndexedProperty indexedProperty)
	{
		final List<FieldValue> fieldValues = new ArrayList<>();
		final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, null);
		loadProductCache(orderEntry);

		final Map<String, Integer> productCountMap = this.threadLocalCache.get()
				.getAllProductCountForB2BUnit(orderEntry.getOrder().getOrderingAccount().getUid());

		Integer count = productCountMap.get(orderEntry.getProduct().getCode());
		if (count == null)
		{
			LOG.error("BuyItAgainPurchasedCountValueProvider count is null for " + orderEntry.getOrder().getCode() + " : "
					+ orderEntry.getProduct().getCode());
			count = 0;
		}
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, count.intValue()));
		}
		return fieldValues;
	}

}

