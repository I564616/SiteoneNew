/**
 *
 */
package com.siteone.core.cronjob.dao;

import java.util.List;


/**
 * @author HR03708
 *
 */
public interface NoVisibleUomCronJobDao
{
	List<String> getAllProducts();

	boolean isNoVisibleUomProduct(String pCode);

	boolean isZeroMultiplierProduct(String pCode);

}
