package com.siteone.fulfilmentprocess.purchasedproduct;

import com.siteone.core.model.PurchasedProductModel;


/**
 * @author Abdul Rahman Sheikh M
 *
 */
public interface SiteOnePurchasedProductsDao
{
	public PurchasedProductModel getPurchasedProductForSku(String code);

}
