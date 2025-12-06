/**
 *
 */
package com.siteone.core.batch.decorator;

import de.hybris.platform.util.CSVCellDecorator;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;


public class DoubleQuoteCellDecorator implements CSVCellDecorator
{
	private static String QUOT_STR = "&quot";

	@Override
	public String decorate(final int position, final Map srcLine)
	{

		final String csvCell = (String) srcLine.get(Integer.valueOf(position));

		if (StringUtils.isNotEmpty(csvCell) && csvCell.contains(QUOT_STR))
		{
			final String stringWithQuotes = csvCell.replaceAll(QUOT_STR, "\"");
			return stringWithQuotes;
		}
		return csvCell;

	}
}