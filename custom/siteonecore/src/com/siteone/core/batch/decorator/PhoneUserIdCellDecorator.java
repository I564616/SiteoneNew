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
public class PhoneUserIdCellDecorator implements CSVCellDecorator
{

	@Override
	public String decorate(final int position, final Map srcLine)
	{
		final String uid = (String) srcLine.get(Integer.valueOf(position));
		if (null != uid)
		{
			return uid.toLowerCase();
		}
		return null;
	}

}
