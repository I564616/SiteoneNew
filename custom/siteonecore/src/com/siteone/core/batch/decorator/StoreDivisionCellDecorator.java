/**
 *
 */
package com.siteone.core.batch.decorator;

import de.hybris.platform.util.CSVCellDecorator;

import java.util.Map;

import com.siteone.core.constants.SiteoneCoreConstants;


/**
 * @author 1099417
 *
 */
public class StoreDivisionCellDecorator implements CSVCellDecorator
{

	@Override
	public String decorate(final int position, final Map<Integer, String> srcLine)
	{

		final String storeDivision = srcLine.get(Integer.valueOf(position));

		//log.info("StoreDivision :" + storeDivision);

		final String[] divisionId = storeDivision.split(":");

		if ("1".equalsIgnoreCase(divisionId[1]) || "US".equalsIgnoreCase(divisionId[1]) || "JDL".equalsIgnoreCase(divisionId[1]))
		{
			divisionId[1] = SiteoneCoreConstants.US_ISO_CODE;
		}
		else if ("2".equalsIgnoreCase(divisionId[1]) || "CA".equalsIgnoreCase(divisionId[1])
				|| "JDLC".equalsIgnoreCase(divisionId[1]))
		{
			divisionId[1] = SiteoneCoreConstants.CA_ISO_CODE;
		}

		return divisionId[0] + ":" + divisionId[1];
	}

}
