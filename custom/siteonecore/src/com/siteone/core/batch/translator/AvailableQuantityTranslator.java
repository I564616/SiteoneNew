/**
 *
 */
package com.siteone.core.batch.translator;

import de.hybris.platform.impex.jalo.translators.AbstractValueTranslator;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;

import org.apache.commons.lang3.StringUtils;


public class AvailableQuantityTranslator extends AbstractValueTranslator
{


	@Override
	public String exportValue(final Object arg0) throws JaloInvalidParameterException
	{
		return arg0.toString();
	}


	@SuppressWarnings("boxing")
	@Override
	public Object importValue(final String arg0, final Item arg1) throws JaloInvalidParameterException
	{
		if (StringUtils.isNotEmpty(arg0))
		{
			return (int) Math.round(Math.floor(Float.valueOf(arg0)));

		}
		return null;
	}

}
