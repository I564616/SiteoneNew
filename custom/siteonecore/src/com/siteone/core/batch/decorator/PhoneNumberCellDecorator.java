/**
 *
 */
package com.siteone.core.batch.decorator;

import de.hybris.platform.util.CSVCellDecorator;

import java.util.Map;
import java.util.regex.Pattern;


/**
 * @author 1085284
 *
 */
public class PhoneNumberCellDecorator implements CSVCellDecorator
{

	@Override
	public String decorate(final int position, final Map srcLine)
	{

		final String phoneNumber = (String) srcLine.get(Integer.valueOf(position));
		if (null != phoneNumber)
		{
			final Pattern pattern = Pattern.compile("[^0-9]");
			final String numberOnly = pattern.matcher(phoneNumber).replaceAll("");
			if (null != numberOnly && numberOnly.length() > 6)
			{
				return numberOnly.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)-$2-$3");
			}
			else
			{
				return numberOnly;
			}
		}
		else
		{
			return phoneNumber;
		}

	}


}
