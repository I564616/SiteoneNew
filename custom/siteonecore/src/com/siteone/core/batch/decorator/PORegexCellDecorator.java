/**
 *
 */
package com.siteone.core.batch.decorator;

import de.hybris.platform.util.CSVCellDecorator;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.sap.security.core.server.csi.util.URLDecoder;


/**
 * @author SMondal
 *
 */
public class PORegexCellDecorator implements CSVCellDecorator
{
	@Override
	public String decorate(final int position, final Map srcLine)
	{
		final String poRegex = (String) srcLine.get(Integer.valueOf(position));
		if (StringUtils.isEmpty(poRegex))
		{
			return poRegex;
		}
		else
		{
			return URLDecoder.decode(poRegex);
		}
	}
}
