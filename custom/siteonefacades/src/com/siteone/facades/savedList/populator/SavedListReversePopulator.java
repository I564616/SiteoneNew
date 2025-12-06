/**
 *
 */
package com.siteone.facades.savedList.populator;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.wishlist2.model.Wishlist2Model;

import org.springframework.util.Assert;

import com.siteone.facades.savedList.data.SavedListData;


/**
 * @author 1003567
 *
 */


public class SavedListReversePopulator implements Populator<SavedListData, Wishlist2Model>
{


	@Override
	public void populate(final SavedListData source, final Wishlist2Model target) throws ConversionException
	{
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");
		target.setName(source.getName());
		target.setDescription(source.getDescription());

	}

}