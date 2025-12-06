/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.indexer.IndexerBatchContext;
import de.hybris.platform.solrfacetsearch.indexer.spi.InputDocument;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractValueResolver;


/**
 * @author BS
 *
 */
public class BuyItAgainLastPurchasedDateValueResolver extends AbstractValueResolver<OrderEntryModel, Object, Object>
{

	@Override
	protected void addFieldValues(final InputDocument document, final IndexerBatchContext indexerBatchContext,
			final IndexedProperty indexedProperty, final OrderEntryModel orderEntryModel,
			final ValueResolverContext<Object, Object> valueResolverContext) throws FieldValueProviderException
	{

		document.addField(indexedProperty, orderEntryModel.getOrder().getCreationtime(), valueResolverContext.getFieldQualifier());
	}

}