/**
 *
 */
package com.siteone.facades.promotion.populator;

import de.hybris.platform.commercefacades.product.converters.populator.PromotionsPopulator;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.PromotionData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.promotionengineservices.model.RuleBasedPromotionModel;
import de.hybris.platform.promotions.model.AbstractPromotionModel;

import org.springframework.util.Assert;


/**
 * @author 1124932
 *
 */
public class SiteOnePromotionPopulator extends PromotionsPopulator
{
	private Populator<MediaModel, ImageData> imagePopulator;
	private final ImageData imageData = new ImageData();

	@Override
	public void populate(final AbstractPromotionModel source, final PromotionData target)
	{
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");
		target.setDescription(((RuleBasedPromotionModel) source).getPromotionDescription());
		target.setCode(source.getCode());
		target.setDisplayName(source.getName());
		target.setTitle(source.getTitle());
		target.setEndDate(source.getEndDate());
		target.setPromotionType(source.getPromotionType());
		target.setNotes(source.getNotes());
		processPromotionMessages(source, target);

		if (null != source.getImage())
		{
			getImagePopulator().populate(source.getImage(), imageData);
			target.setProductBanner(imageData);
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
