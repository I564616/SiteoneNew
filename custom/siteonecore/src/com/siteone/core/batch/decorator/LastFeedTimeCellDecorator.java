/**
 *
 */
package com.siteone.core.batch.decorator;

import de.hybris.platform.util.CSVCellDecorator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


/**
 * @author 1085284
 *
 */
public class LastFeedTimeCellDecorator implements CSVCellDecorator
{
	@Override
	public String decorate(final int position, final Map srcLine)
	{
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return dateFormat.format(new Date());
	}

}
