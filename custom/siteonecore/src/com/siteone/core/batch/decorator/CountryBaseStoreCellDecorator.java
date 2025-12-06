/**
 *
 */
package com.siteone.core.batch.decorator;

import de.hybris.platform.util.CSVCellDecorator;

import java.util.Map;


/**
 * @author BR06618
 *
 */

public class CountryBaseStoreCellDecorator implements CSVCellDecorator
{
	private static final String SITEONE_US = "siteone";
	private static final String SITEONE_CA = "siteoneCA";

	@Override
	public String decorate(final int position, final Map srcLine)
	{

		final String division = (String) srcLine.get(Integer.valueOf(position));
		if (division == null || division.length() == 0)
		{
			return SITEONE_US;
		}

		if ("1".equalsIgnoreCase(division) || "US".equalsIgnoreCase(division) || "JDL".equalsIgnoreCase(division))
		{
			return SITEONE_US;
		}
		else if ("2".equalsIgnoreCase(division) || "CA".equalsIgnoreCase(division) || "JDLC".equalsIgnoreCase(division))
		{
			return SITEONE_CA;
		}

		return division;
	}
}
