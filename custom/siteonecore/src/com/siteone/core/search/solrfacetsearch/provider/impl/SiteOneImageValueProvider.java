/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.impl.ImageValueProvider;
import de.hybris.platform.core.model.media.MediaContainerModel;
import de.hybris.platform.core.model.media.MediaFormatModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException;
import de.hybris.platform.variants.model.VariantProductModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;


/**
 * @author 1091124
 *
 */
public class SiteOneImageValueProvider extends ImageValueProvider
{

	@Override
	protected MediaModel findMedia(final ProductModel product, final MediaFormatModel mediaFormat)
	{
		if (product != null && mediaFormat != null)
		{
			List<MediaContainerModel> galleryImages = product.getGalleryImages();
			final Map<String, MediaContainerModel> sortingModel = new TreeMap<>();

			for (final MediaContainerModel mediaCont : galleryImages)
			{
				sortingModel.put(mediaCont.getQualifier(), mediaCont);
			}

			galleryImages = new ArrayList<>();
			final Iterator<Entry<String, MediaContainerModel>> iterator = sortingModel.entrySet().iterator();
			while (iterator.hasNext())
			{
				final Entry<String, MediaContainerModel> mediaContentry = iterator.next();
				galleryImages.add(mediaContentry.getValue());
			}
			if (!galleryImages.isEmpty())
			{
				// Search each media container in the gallery for an image of the right format
				for (final MediaContainerModel container : galleryImages)
				{
					try
					{
						final MediaModel media = getMediaContainerService().getMediaForFormat(container, mediaFormat);
						if (media != null)
						{
							return media;
						}
					}
					catch (final ModelNotFoundException ignore) //NOPMD
					{
						// ignore
					}
				}
			}

			// Failed to find media in product
			if (product instanceof VariantProductModel)
			{
				// Look in the base product
				return findMedia(((VariantProductModel) product).getBaseProduct(), mediaFormat);
			}
		}
		return null;
	}

}
