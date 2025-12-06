/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.indexer.IndexerBatchContext;
import de.hybris.platform.solrfacetsearch.indexer.spi.InputDocument;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractValueResolver;
import de.hybris.platform.wishlist2.model.Wishlist2EntryModel;


/**
 * @author AA04994
 *
 */
public class ListProductShortDescValueResolver extends AbstractValueResolver<Wishlist2EntryModel, Object, Object>
{

	@Override
	protected void addFieldValues(final InputDocument document, final IndexerBatchContext indexerBatchContext,
			final IndexedProperty indexedProperty, final Wishlist2EntryModel listEntryModel,
			final ValueResolverContext<Object, Object> valueResolverContext) throws FieldValueProviderException
	{

		document.addField(indexedProperty, listEntryModel.getProduct().getProductShortDesc(),
				valueResolverContext.getFieldQualifier());
	}

}
