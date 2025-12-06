/**
 *
 */

package com.siteone.facades.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import com.siteone.core.model.EmailSubscriptionsModel;
import com.siteone.facades.emailsubscriptions.data.EmailSubscriptionsData;


/**
 * @author 1091124
 *
 */
public class EmailSubscriptionsReversePopulator implements Populator<EmailSubscriptionsData, EmailSubscriptionsModel>
{
	@Override
	public void populate(final EmailSubscriptionsData source, final EmailSubscriptionsModel target) throws ConversionException
	{ // YTODO Auto-generated method stub
		target.setEmailId(source.getEmail());

	}

}
