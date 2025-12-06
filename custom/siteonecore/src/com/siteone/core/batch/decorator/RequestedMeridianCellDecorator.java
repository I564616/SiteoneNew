/**
 *
 */
package com.siteone.core.batch.decorator;

import de.hybris.platform.util.CSVCellDecorator;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.siteone.core.enums.MeridianTimeEnum;


/**
 * @author 1085284
 *
 */
public class RequestedMeridianCellDecorator implements CSVCellDecorator
{

	@Override
	public String decorate(final int position, final Map<Integer, String> srcLine)
	{

		final String requestedMeridian = srcLine.get(position);

		if ("AM".equalsIgnoreCase(requestedMeridian))
		{
			return MeridianTimeEnum.AM.getCode();
		}
		else if ("PM".equalsIgnoreCase(requestedMeridian))
		{
			return MeridianTimeEnum.PM.getCode();
		}
		else if ("ANY TIME".equalsIgnoreCase(requestedMeridian) || null == requestedMeridian
				|| StringUtils.EMPTY.equalsIgnoreCase(requestedMeridian))
		{
			return MeridianTimeEnum.ANYTIME.getCode();
		}
		return requestedMeridian;
	}

}

