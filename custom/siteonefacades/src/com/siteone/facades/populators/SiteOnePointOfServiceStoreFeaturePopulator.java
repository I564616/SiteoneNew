/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.model.storelocator.StoreLocatorFeatureModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.annotation.Resource;

import com.siteone.facade.StoreLocatorFeatureData;


/**
 * @author PElango
 *
 */
public class SiteOnePointOfServiceStoreFeaturePopulator implements Populator<PointOfServiceModel, PointOfServiceData>
{

	@Resource(name = "imagePopulator")
	private Populator<MediaModel, ImageData> imagePopulator;

	@Override
	public void populate(final PointOfServiceModel source, final PointOfServiceData target) throws ConversionException
	{
		final Set<StoreLocatorFeatureModel> storeSpecialty = source.getFeatures();
		if (storeSpecialty != null && !storeSpecialty.isEmpty())
		{
			final List<StoreLocatorFeatureData> storeLocatorFeatureList = new ArrayList<>();
			source.getFeatures().forEach(storeLocatorFeature -> {
				final StoreLocatorFeatureData data = new StoreLocatorFeatureData();
				data.setCode(storeLocatorFeature.getCode());
				data.setName(storeLocatorFeature.getName());
				if (storeLocatorFeature.getIcon() != null)
				{
					final ImageData iconImageData = new ImageData();
					imagePopulator.populate(storeLocatorFeature.getIcon(), iconImageData);
					data.setIcon(iconImageData);
				}
				data.setDescription(storeLocatorFeature.getDescription());
				if (storeLocatorFeature.getImage() != null)
				{
					final ImageData imageData = new ImageData();
					imagePopulator.populate(storeLocatorFeature.getImage(), imageData);
					if(null == imageData.getWidth()) {
						imageData.setWidth(0);
					}
					data.setImage(imageData);
				}
				storeLocatorFeatureList.add(data);
			});
			target.setStoreSpecialityDetails(storeLocatorFeatureList);
		}
	}
}
