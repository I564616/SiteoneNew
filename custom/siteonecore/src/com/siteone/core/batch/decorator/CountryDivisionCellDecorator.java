/**
 *
 */
package com.siteone.core.batch.decorator;

import de.hybris.platform.util.CSVCellDecorator;

import java.util.Map;

import com.siteone.core.constants.SiteoneCoreConstants;


/**
 * @author 1085284
 *
 */
public class CountryDivisionCellDecorator implements CSVCellDecorator
{

	@Override
	public String decorate(final int position, final Map srcLine)
	{
		final String division = (String) srcLine.get(Integer.valueOf(position));
		if (division == null || division.length() == 0)
		{
			return division;
		}

		if ("1".equalsIgnoreCase(division) || "US".equalsIgnoreCase(division) || "JDL".equalsIgnoreCase(division))
		{
			return SiteoneCoreConstants.US_ISO_CODE;
		}
		else if ("2".equalsIgnoreCase(division) || "CA".equalsIgnoreCase(division) || "JDLC".equalsIgnoreCase(division))
		{
			return SiteoneCoreConstants.CA_ISO_CODE;
		}

		return division;
	}
}

