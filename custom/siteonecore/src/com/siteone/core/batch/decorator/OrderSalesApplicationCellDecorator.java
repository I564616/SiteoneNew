/**
 *
 */
package com.siteone.core.batch.decorator;


import de.hybris.platform.commerceservices.enums.SalesApplication;
import de.hybris.platform.util.CSVCellDecorator;

import java.util.Map;


/**
 * @author 1085284
 *
 */
public class OrderSalesApplicationCellDecorator implements CSVCellDecorator
{
	@Override
	public String decorate(final int position, final Map srcLine)
	{
		final String externalSystemId = (String) srcLine.get(Integer.valueOf(position));

		if (externalSystemId != null)
		{
			if (externalSystemId.equalsIgnoreCase("2") || externalSystemId.equalsIgnoreCase("20"))
			{
				return SalesApplication.WEB.getCode();
			}
			else if (externalSystemId.equalsIgnoreCase("13"))
			{
				return SalesApplication.ECOMMOBILEAPP.getCode();
			}
			else if (externalSystemId.equalsIgnoreCase("1"))
			{
				return SalesApplication.STORE.getCode();
			}
			else if (externalSystemId.equalsIgnoreCase("7"))
			{
				return SalesApplication.PUNCHOUT.getCode();
			}
		}
		return null;


	}
}

