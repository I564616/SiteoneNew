/**
 *
 */
package com.siteone.core.batch.decorator;

import de.hybris.platform.util.CSVCellDecorator;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.siteone.core.constants.SiteoneCoreConstants;


/**
 * @author 1085284
 *
 */
public class RegionCellDecorator implements CSVCellDecorator
{

	@Override
	public String decorate(final int position, final Map<Integer, String> srcLine)
	{

		final String regionCode = srcLine.get(Integer.valueOf(position));



		if (!regionCode.isEmpty())
		{
			final String[] region = regionCode.split("-");

			if (region.length > 1)
			{
				if ("1".equalsIgnoreCase(region[0]) || "US".equalsIgnoreCase(region[0]) || "JDL".equalsIgnoreCase(region[0]))
				{
					region[0] = SiteoneCoreConstants.US_ISO_CODE;
				}
				else if ("2".equalsIgnoreCase(region[0]) || "CA".equalsIgnoreCase(region[0]) || "JDLC".equalsIgnoreCase(region[0]))
				{
					region[0] = SiteoneCoreConstants.CA_ISO_CODE;
				}

				return region[0] + "-" + region[1].trim();
			}
			else
			{
				return StringUtils.EMPTY;
			}


		}
		else
		{
			return StringUtils.EMPTY;
		}


	}

}

