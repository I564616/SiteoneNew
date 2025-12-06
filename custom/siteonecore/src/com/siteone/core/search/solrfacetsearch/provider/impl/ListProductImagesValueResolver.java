/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.media.MediaFormatModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.wishlist2.model.Wishlist2EntryModel;

import java.util.Collection;
import java.util.Collections;

import org.apache.log4j.Logger;


/**
 * @author AA04994
 *
 */
public class ListProductImagesValueResolver extends SiteOneImageValueProvider
{

	private static final Logger LOG = Logger.getLogger(ListProductImagesValueResolver.class);

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		if (model instanceof Wishlist2EntryModel)
		{

			final ProductModel product = ((Wishlist2EntryModel) model).getProduct();
			final MediaFormatModel mediaFormatModel = getMediaService().getFormat(getMediaFormat());
			if (mediaFormatModel != null)
			{
				final MediaModel media = findMedia(product, mediaFormatModel);
				if (media != null)
				{
					return createFieldValues(indexedProperty, media);
				}
				if (LOG.isDebugEnabled())
				{
					LOG.debug("No [" + mediaFormatModel.getQualifier() + "] image found for product ["
							+ ((ProductModel) model).getCode() + "]");
				}
			}
		}
		return Collections.emptyList();
	}
}