/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import com.siteone.core.model.InventoryUPCModel;
import com.siteone.core.order.services.SiteOneOrderService;
import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.order.OrderService;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.indexer.IndexerBatchContext;
import de.hybris.platform.solrfacetsearch.indexer.spi.InputDocument;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractValueResolver;

import jakarta.annotation.Resource;
import java.util.List;


/**
 * @author BS
 *
 */
public class BuyItAgainOrderNumberValueResolver extends AbstractValueResolver<OrderEntryModel, Object, Object>
{

	@Override
	protected void addFieldValues(final InputDocument document, final IndexerBatchContext indexerBatchContext,
			final IndexedProperty indexedProperty, final OrderEntryModel orderEntryModel,
			final ValueResolverContext<Object, Object> valueResolverContext) throws FieldValueProviderException
	{
		document.addField(indexedProperty, orderEntryModel.getOrder().getCode(),
				valueResolverContext.getFieldQualifier());
	}

}
