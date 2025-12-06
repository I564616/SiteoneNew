/**
 *
 */
package com.siteone.fulfilmentprocess.service;

import com.siteone.core.model.PurchasedProductModel;
import com.siteone.fulfilmentprocess.purchasedproduct.SiteOnePurchasedProductsDao;


/**
 * @author PBurnwal
 *
 */
public class DefaultPurchasedProductService implements PurchasedProductService
{

	private SiteOnePurchasedProductsDao siteOnePurchasedProductsDao;

	/**
	 * @return the siteOnePurchasedProductsDao
	 */
	public SiteOnePurchasedProductsDao getSiteOnePurchasedProductsDao()
	{
		return siteOnePurchasedProductsDao;
	}

	/**
	 * @param siteOnePurchasedProductsDao
	 *           the siteOnePurchasedProductsDao to set
	 */
	public void setSiteOnePurchasedProductsDao(final SiteOnePurchasedProductsDao siteOnePurchasedProductsDao)
	{
		this.siteOnePurchasedProductsDao = siteOnePurchasedProductsDao;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.fulfilmentprocess.service.PurchasedProductService#getPurchasedProductForSku(java.lang.String)
	 */
	@Override
	public PurchasedProductModel getPurchasedProductForSku(final String code)
	{
		return siteOnePurchasedProductsDao.getPurchasedProductForSku(code);
	}


}
