/**
 *
 */
package com.siteone.core.util;

import org.apache.commons.lang3.StringUtils;


/**
 * @author 1230514 Utility class for B2BUnit
 *
 */
public class SiteOneB2BUnitUtil
{
	private SiteOneB2BUnitUtil()
	{
	}

	private static final String SEPARATOR_UNDERSCORE = "_";

	/**
	 * This method converts the unit Id as display unit Id. i.e Remove the division from unit ID.
	 *
	 * @param unitId
	 *           - actual unit id in hybris DB
	 * @return displayUnitId - unit id for display purpose
	 */
	public static String unitIdWithoutDivision(final String unitId)
	{

		String displayUnitId = "";

		if (null != unitId)
		{
			displayUnitId = StringUtils.substringBeforeLast(unitId.trim(), SEPARATOR_UNDERSCORE);
		}

		return displayUnitId;

	}

}
