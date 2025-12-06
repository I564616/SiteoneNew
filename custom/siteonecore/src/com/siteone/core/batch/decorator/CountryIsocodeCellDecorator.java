/**
 *
 */
package com.siteone.core.batch.decorator;

import de.hybris.platform.util.CSVCellDecorator;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.siteone.core.constants.SiteoneCoreConstants;


/**
 * @author BS
 *
 */
public class CountryIsocodeCellDecorator implements CSVCellDecorator
{

	@Override
	public String decorate(final int position, final Map srcLine)
	{
		final String regionIsoCode = (String) srcLine.get(Integer.valueOf(position));

		if (!regionIsoCode.isEmpty())
		{
			if (regionIsoCode.equalsIgnoreCase(SiteoneCoreConstants.UNITED_STATES_AMERICA_CODE)
					|| regionIsoCode.equalsIgnoreCase(SiteoneCoreConstants.AMERICA_CODE)
					|| regionIsoCode.equalsIgnoreCase(SiteoneCoreConstants.UNITED_STATES_CODE)
					|| regionIsoCode.equalsIgnoreCase(SiteoneCoreConstants.US_ISO_CODE))
			{
				return SiteoneCoreConstants.US_ISO_CODE;
			}
			else if (regionIsoCode.equalsIgnoreCase(SiteoneCoreConstants.CANADA_CODE)
					|| regionIsoCode.equalsIgnoreCase(SiteoneCoreConstants.CA_ISO_CODE))
			{
				return SiteoneCoreConstants.CA_ISO_CODE;
			}
			else
			{
				return regionIsoCode;
			}
		}
		else
		{
			return StringUtils.EMPTY;
		}
	}


}


