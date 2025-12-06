package com.siteone.facades.savedList.populator;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.wishlist2.model.Wishlist2Model;

import org.springframework.util.Assert;

import com.siteone.facades.savedList.data.ListHeaderData;


public class SiteoneHeaderListPopulator implements Populator<Wishlist2Model, ListHeaderData>
{
	@Override
	public void populate(final Wishlist2Model source, final ListHeaderData target) throws ConversionException
	{
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");
		target.setCode(source.getPk().toString());
		target.setName(source.getName());
		target.setModifiedTime(source.getModifiedtime());
	}
}