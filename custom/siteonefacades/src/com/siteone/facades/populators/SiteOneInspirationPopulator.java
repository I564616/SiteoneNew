package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import com.siteone.core.model.InspirationModel;
import com.siteone.facade.InspirationData;



public class SiteOneInspirationPopulator implements Populator<InspirationModel, InspirationData>
{

	private Populator<MediaModel, ImageData> imagePopulator;



	@Override
	public void populate(final InspirationModel source, final InspirationData target) throws ConversionException
	{

		final ImageData imageData = new ImageData();

		target.setTitle(source.getTitle());
		target.setLocation(source.getLocation());
		target.setDesignedBy(source.getDesignedBy());
		target.setSnippetOfTheStory(source.getSnippetOfTheStoryNew());


		if (null != source.getInspirationCode())
		{
			target.setInspirationCode(source.getInspirationCode());
		}

		if (null != source.getInspirationMedia())
		{

			getImagePopulator().populate(source.getInspirationMedia(), imageData);
			target.setInspirationMedia(imageData);
		}



	}

	/**
	 * @return the imagePopulator
	 */
	public Populator<MediaModel, ImageData> getImagePopulator()
	{
		return imagePopulator;
	}



	/**
	 * @param imagePopulator
	 *           the imagePopulator to set
	 */
	public void setImagePopulator(final Populator<MediaModel, ImageData> imagePopulator)
	{
		this.imagePopulator = imagePopulator;
	}





}
