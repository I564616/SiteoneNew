/**
 *
 */
package com.siteone.core.recentscan.dao;

import java.util.List;

import com.siteone.core.model.RecentScanProductsModel;


/**
 * @author LR03818
 *
 */
public interface SiteOneRecentScanDao
{
	public List<String> getRecentScanProductsByUser(final String accountNumber);

	/**
	 * @param accountNumber
	 * @param productCode
	 * @return
	 */
	public RecentScanProductsModel getExistingRecentScanProduct(final String accountNumber, final String productCode);
}
