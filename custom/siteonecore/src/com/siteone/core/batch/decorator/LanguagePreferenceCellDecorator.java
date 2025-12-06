/**
 *
 */
package com.siteone.core.batch.decorator;

import de.hybris.platform.util.CSVCellDecorator;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;


/**
 * @author BS
 *
 */
public class LanguagePreferenceCellDecorator implements CSVCellDecorator
{
	private final String ENGLISH = "English";
	private final String SPANISH = "Spanish";

	@Override
	public String decorate(final int position, final Map srcLine)
	{
		final String languagePreference = (String) srcLine.get(Integer.valueOf(position));

		if (!languagePreference.isEmpty())
		{
			if (languagePreference.equalsIgnoreCase(ENGLISH))
			{
				return "en";
			}
			else if (languagePreference.equalsIgnoreCase(SPANISH))
			{
				return "es";
			}
		}

		return StringUtils.EMPTY;
	}


}
