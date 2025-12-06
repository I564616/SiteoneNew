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
public class OrderB2BUnitCellDecorator implements CSVCellDecorator
{
	@Override
	public String decorate(final int position, final Map srcLine)
	{
		final String b2bUnit = (String) srcLine.get(Integer.valueOf(position));
		if (b2bUnit == null || b2bUnit.length() == 0)
		{
			return b2bUnit;
		}

		final String[] b2BUnit = b2bUnit.split("_");

		if ("1".equalsIgnoreCase(b2BUnit[1]) || "US".equalsIgnoreCase(b2BUnit[1]) || "JDL".equalsIgnoreCase(b2BUnit[1]))
		{
			b2BUnit[1] = SiteoneCoreConstants.US_ISO_CODE;
		}
		else if ("2".equalsIgnoreCase(b2BUnit[1]) || "CA".equalsIgnoreCase(b2BUnit[1]) || "JDLC".equalsIgnoreCase(b2BUnit[1]))
		{
			b2BUnit[1] = SiteoneCoreConstants.CA_ISO_CODE;
		}

		return b2BUnit[0] + "_" + b2BUnit[1];

	}
}

