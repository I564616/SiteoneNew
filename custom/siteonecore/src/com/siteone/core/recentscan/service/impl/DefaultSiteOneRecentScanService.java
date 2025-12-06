/**
 * 
 */
package com.siteone.core.recentscan.service.impl;

import de.hybris.platform.servicelayer.model.ModelService;

import java.util.Date;
import java.util.List;

import jakarta.annotation.Resource;

import com.siteone.core.model.RecentScanProductsModel;
import com.siteone.core.recentscan.dao.SiteOneRecentScanDao;
import com.siteone.core.recentscan.service.SiteOneRecentScanService;

/**
 * @author LR03818
 *
 */
public class DefaultSiteOneRecentScanService implements SiteOneRecentScanService
{
	private SiteOneRecentScanDao siteOneRecentScanDao;
	
	@Resource(name = "modelService")
	private ModelService modelService;

	@Override
	public void createRecentScanProducts(RecentScanProductsModel recentScanProducts)
	{
		RecentScanProductsModel existingRecentScanProducts = siteOneRecentScanDao.getExistingRecentScanProduct(
				recentScanProducts.getAccountNumber(), recentScanProducts.getProductCode());
		if(existingRecentScanProducts==null) {
			recentScanProducts.setScanDate(new Date());
			modelService.save(recentScanProducts);
		} else {
			existingRecentScanProducts.setScanDate(new Date());
			modelService.save(existingRecentScanProducts);
		}
		
	}

	@Override
	public List<String> getRecentScanProductsByUser(final String accountNumber)
	{
		return siteOneRecentScanDao.getRecentScanProductsByUser(accountNumber);
	}

	/**
	 * @return the siteOneRecentScanDao
	 */
	public SiteOneRecentScanDao getSiteOneRecentScanDao()
	{
		return siteOneRecentScanDao;
	}

	/**
	 * @param siteOneRecentScanDao the siteOneRecentScanDao to set
	 */
	public void setSiteOneRecentScanDao(SiteOneRecentScanDao siteOneRecentScanDao)
	{
		this.siteOneRecentScanDao = siteOneRecentScanDao;
	}

}
