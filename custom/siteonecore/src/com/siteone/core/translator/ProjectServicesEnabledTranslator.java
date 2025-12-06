/**
 *
 */
package com.siteone.core.translator;

import de.hybris.platform.impex.jalo.translators.AbstractValueTranslator;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;

import org.apache.commons.lang3.StringUtils;

/**
 *
 */
public class ProjectServicesEnabledTranslator extends AbstractValueTranslator
{

	@Override
	public Object importValue(final String cellValue, final Item b2bCustomer) throws JaloInvalidParameterException
	{

		if (StringUtils.isNotEmpty(cellValue))
		{
			if ("6".equals(cellValue))
			{
				return Boolean.TRUE;
			}
			else if ("2".equals(cellValue))
			{
				return Boolean.FALSE;
			}
		}

		return null;
	}

	@Override
	public String exportValue(final Object arg0) throws JaloInvalidParameterException
	{
		throw new UnsupportedOperationException();
	}

}

