/**
 *
 */
package com.siteone.core.batch.decorator;

import de.hybris.platform.util.CSVCellDecorator;

import java.util.Map;

import com.siteone.core.constants.SiteoneCoreConstants;


/**
 *
 */
public class PriceCurrencyCellDecorator implements CSVCellDecorator
{

	@Override
	public String decorate(final int position, final Map srcLine)
	{
		final String currency = (String) srcLine.get(Integer.valueOf(position));
		if (currency == null || currency.length() == 0)
		{
			return currency;
		}

		if ("CAD".equalsIgnoreCase(currency))
		{
			return SiteoneCoreConstants.CURRENCY_CODE_CA;
		}
		else
		{
			return currency;
		}

	}
}
