/**
 *
 */
package com.siteone.core.recentscan.service;

import java.util.List;

import com.siteone.core.model.RecentScanProductsModel;


/**
 * @author LR03818
 *
 */
public interface SiteOneRecentScanService
{
	public void createRecentScanProducts(final RecentScanProductsModel recentScanProducts);

	public List<String> getRecentScanProductsByUser(final String accountNumber);
}
