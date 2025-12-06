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
public class VideoUrlCellDecorator implements CSVCellDecorator
{


	private static final String YOUTUBE_PREFIX = "https://www.youtube.com/embed/";
	
	@Override
	public String decorate(final int position, final Map srcLine)
	{
		final String youtubeVideoId = (String) srcLine.get(Integer.valueOf(position));

		if (!youtubeVideoId.isEmpty())
		{
			return YOUTUBE_PREFIX + youtubeVideoId;
		}
		return null;


	}
}
