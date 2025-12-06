/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import com.siteone.core.model.SiteOneNewsModel;
import com.siteone.facade.NewsData;


/**
 * @author 191179
 *
 */
public class SiteOneNewsPopulator implements Populator<SiteOneNewsModel, NewsData>
{
	private Populator<MediaModel, ImageData> imagePopulator;

	@Override
	public void populate(final SiteOneNewsModel source, final NewsData target) throws ConversionException
	{

		final ImageData imageData = new ImageData();
		//final SimpleDateFormat fmt = new SimpleDateFormat("EEEE, MMMM dd");

		target.setLongDescription(source.getLongDescription());

		target.setNewsURL(source.getNewsURL());
		target.setShortDesc(source.getShortDesc());
		target.setTitle(source.getTitle());
		//target.setNewsPublishDate(fmt.format(source.getNewsPublishDate()));
		target.setNewsPublishDate(source.getNewsPublishDate());
		if (source.getPrevNews() != null)
		{
			target.setPreviousNews(source.getPrevNews().getNewsCode());
			target.setPreviousNewsTitle(source.getPrevNews().getTitle());
		}
		if (source.getNextNews() != null)
		{
			target.setNextNews(source.getNextNews().getNewsCode());
			target.setNextNewsTitle(source.getNextNews().getTitle());
		}



		if (null != source.getNewsCode())
		{
			target.setNewsCode(source.getNewsCode());
		}

		if (null != source.getNewsMedia())
		{
			getImagePopulator().populate(source.getNewsMedia(), imageData);
			target.setNewsMedia(imageData);
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
