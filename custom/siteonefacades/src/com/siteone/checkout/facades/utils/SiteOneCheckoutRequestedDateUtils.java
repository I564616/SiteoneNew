/**
 *
 */
package com.siteone.checkout.facades.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;


/**
 * @author 1219341
 *
 */
public class SiteOneCheckoutRequestedDateUtils
{
	static final Logger LOGGER = Logger.getLogger(SiteOneCheckoutRequestedDateUtils.class);

	public Date convertDateToUTC(final Date date)
	{
		final DateFormat formatterUTC = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		formatterUTC.setTimeZone(TimeZone.getTimeZone("UTC")); // UTC timezone
		Date utcDate = null;
		try
		{
			utcDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(formatterUTC.format(date));
		}
		catch (final ParseException e)
		{
			LOGGER.error(e);
		}

		return utcDate;
	}
}
