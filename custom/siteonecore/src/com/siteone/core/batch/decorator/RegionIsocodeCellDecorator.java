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
public class RegionIsocodeCellDecorator implements CSVCellDecorator
{
	private static final String SEPERATOR = "-";

	@Override
	public String decorate(final int position, final Map srcLine)
	{
		final String regionIsoCode = (String) srcLine.get(Integer.valueOf(position));
		final String[] isocode = regionIsoCode.split(SEPERATOR);

		if (isocode.length == 2)
		{
			final String usIsocode = isocode[0];
			final String regionCode = isocode[1];
			if (usIsocode.equalsIgnoreCase(SiteoneCoreConstants.UNITED_STATES_AMERICA_CODE)
					|| usIsocode.equalsIgnoreCase(SiteoneCoreConstants.AMERICA_CODE)
					|| usIsocode.equalsIgnoreCase(SiteoneCoreConstants.UNITED_STATES_CODE)
					|| usIsocode.equalsIgnoreCase(SiteoneCoreConstants.US_ISO_CODE))
			{
				final String stateCode = SiteoneCoreConstants.US_ISO_CODE.concat(SEPERATOR).concat(regionCode);
				return stateCode;
			}
			else if (usIsocode.equalsIgnoreCase(SiteoneCoreConstants.CANADA_CODE)
					|| usIsocode.equalsIgnoreCase(SiteoneCoreConstants.CA_ISO_CODE))
			{
				final String stateCode = SiteoneCoreConstants.CA_ISO_CODE.concat(SEPERATOR).concat(regionCode);
				return stateCode;
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


