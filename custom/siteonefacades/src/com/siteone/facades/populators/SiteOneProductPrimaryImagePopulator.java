/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.product.converters.populator.ProductPrimaryImagePopulator;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.ImageDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.media.MediaContainerModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;


/**
 * @author 1091124
 *
 */
public class SiteOneProductPrimaryImagePopulator<SOURCE extends ProductModel, TARGET extends ProductData>
		extends ProductPrimaryImagePopulator<SOURCE, TARGET>
{

	@Override
	public void populate(final SOURCE productModel, final TARGET productData) throws ConversionException
	{
		MediaContainerModel primaryImageMediaContainer = null;

        //we need to iterate over the available images to get the first valid image.
		primaryImageMediaContainer = productModel.getGalleryImages().stream()
				.sorted((s1, s2) -> Integer.compare(Integer.parseInt(StringUtils.substringAfter(s1.getQualifier(), "_")),
						Integer.parseInt(StringUtils.substringAfter(s2.getQualifier(), "_")))).findFirst().orElse(null);

		if(primaryImageMediaContainer!=null){
		
		final List<ImageData> imageList = new ArrayList<ImageData>();

			addImagesInFormats(primaryImageMediaContainer, ImageDataType.PRIMARY, 0, imageList);

			for (final ImageData imageData : imageList)
			{
				if (imageData.getAltText() == null)
				{
					imageData.setAltText(productModel.getProductShortDesc());
				}
			}
			productData.setImages(imageList);
			
           }
	}

}
