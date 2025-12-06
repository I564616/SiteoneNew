/**
 * 
 */
package com.siteone.core.util;

/**
 * @author BR06618
 *
 */


public class SiteOneDivisionUtil {
	
	public boolean isUSContext (String division)
	{
		if ("1".equalsIgnoreCase(division) || "US".equalsIgnoreCase(division) || "JDL".equalsIgnoreCase(division))
		{
			return true;
		}
		else return false;
	}
	
	public boolean isCanadaContext (String division)
	{
		if ("2".equalsIgnoreCase(division) || "CA".equalsIgnoreCase(division) || "JDLC".equalsIgnoreCase(division))
		{
			return true;
		}
		else return false;
	}
}
