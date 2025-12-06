/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.product.converters.populator.ProductGalleryImagesPopulator;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.ImageDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.media.MediaContainerModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

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
public class SiteOneProductGalleryImagesPopulator<SOURCE extends ProductModel, TARGET extends ProductData>
		extends ProductGalleryImagesPopulator<SOURCE, TARGET>
{


	@Override
	public void populate(final SOURCE productModel, final TARGET productData) throws ConversionException
	{
		List<MediaContainerModel> mediaContainers = new ArrayList<>();
		collectMediaContainers(productModel, mediaContainers);

		final Map<Integer, MediaContainerModel> sortingModel = new TreeMap<>();

		for (final MediaContainerModel mediaCont : mediaContainers)
		{
			Integer qualifierKey = Integer.valueOf(mediaCont.getQualifier().split("_")[1]);
			sortingModel.put(qualifierKey, mediaCont);
		}

		mediaContainers = new ArrayList<>();
		final Iterator<Entry<Integer, MediaContainerModel>> iterator = sortingModel.entrySet().iterator();
		while (iterator.hasNext())
		{
			final Entry<Integer, MediaContainerModel> mediaContentry = iterator.next();
			mediaContainers.add(mediaContentry.getValue());
		}


		if (!mediaContainers.isEmpty())
		{
			final List<ImageData> imageList = new ArrayList<>();

			// fill our image list with the product's existing images
			if (productData.getImages() != null)
			{
				imageList.addAll(productData.getImages());
			}

			// Use all the images as gallery images
			int galleryIndex = 0;
			for (final MediaContainerModel mediaContainer : mediaContainers)
			{
				addImagesInFormats(mediaContainer, ImageDataType.GALLERY, galleryIndex++, imageList);
			}

			for (final ImageData imageData : imageList)
			{
				if (imageData.getAltText() == null)
				{
					imageData.setAltText(productModel.getProductShortDesc());
				}
			}

			// Overwrite the existing list of images
			productData.setImages(imageList);
		}
	}
}
