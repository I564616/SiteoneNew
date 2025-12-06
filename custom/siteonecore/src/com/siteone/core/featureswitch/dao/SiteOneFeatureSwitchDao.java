/**
 *
 */
package com.siteone.core.featureswitch.dao;

/**
 * @author KArasan
 *
 */
public interface SiteOneFeatureSwitchDao
{

	/**
	 * @param name
	 * @return
	 */
	String getSwitchValue(String name);

	String getSwitchLongValue(String key);
}
