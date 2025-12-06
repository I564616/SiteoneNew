/**
 *
 */
package com.siteone.core.batch.decorator;

import de.hybris.platform.util.CSVCellDecorator;

import java.util.Map;


/**
 * @author 1085284
 *
 */
public class InvoicePhoneNumberCellDecorator implements CSVCellDecorator
{

	@Override
	public String decorate(final int position, final Map srcLine)
	{

		final String invoicePhoneNumber = (String) srcLine.get(Integer.valueOf(position));

		if (null != invoicePhoneNumber && invoicePhoneNumber.length() < 17 && invoicePhoneNumber.length() >= 16)
		{

			final String result = invoicePhoneNumber.replaceAll("[()-]", "");

			final String[] phoneNumber = result.split(" ");
			final String num1 = (phoneNumber[1].substring(0, 3));
			final String num2 = (phoneNumber[1].substring(3, 6));
			final String num3 = (phoneNumber[1].substring(6));
			return phoneNumber[0] + " " + num1 + "-" + num2 + "-" + num3;

		}
		else
		{
			return invoicePhoneNumber;
		}

	}
}
