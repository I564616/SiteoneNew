/**
 *
 */
package com.siteone.facades.storefinder.populators;

import de.hybris.platform.commercefacades.storelocator.converters.populator.SpecialOpeningDayPopulator;
import de.hybris.platform.commercefacades.storelocator.data.SpecialOpeningDayData;
import de.hybris.platform.storelocator.model.SpecialOpeningDayModel;

import com.ibm.icu.text.SimpleDateFormat;


/**
 * @author SD02010
 *
 */
public class SiteOneSpecialOpeningDayPopulator extends SpecialOpeningDayPopulator
{
	@Override
	public void populate(final SpecialOpeningDayModel source, final SpecialOpeningDayData target)
	{
		final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d");
		populateBase(source, target);
		target.setClosed(source.isClosed());
		target.setName(source.getName());
		target.setComment(source.getMessage());
		if (source.getDate() != null)
		{
			target.setDate(source.getDate());
			target.setFormattedDate(dateFormat.format(source.getDate()));
		}
	}

}
