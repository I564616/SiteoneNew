/**
 *
 */
package com.siteone.core.batch.translator;

import de.hybris.platform.impex.jalo.translators.AbstractValueTranslator;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;


/**
 * @author 1085284
 *
 */
public class SiteOnePriceTranslator extends AbstractValueTranslator
{

	@SuppressWarnings("boxing")
	@Override
	public Object importValue(final String valueExpr, final Item toItem) throws JaloInvalidParameterException
	{
		clearStatus();
		Double result = null;
		if (!StringUtils.isBlank(valueExpr))
		{
			try
			{
				final Double price = Double.valueOf(valueExpr);
				final DecimalFormat decimalFormat = new DecimalFormat("#.###");
				decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
				result = Double.parseDouble(decimalFormat.format(price));
			}
			catch (final NumberFormatException exc)
			{
				setError();
			}
			if (result != null && result.doubleValue() < 0.0)
			{
				setError();
			}
		}
		return result;
	}

	@Override
	public String exportValue(final Object value) throws JaloInvalidParameterException
	{
		return value == null ? "" : value.toString();
	}
}
