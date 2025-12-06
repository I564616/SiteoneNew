package com.siteone.integration.services.ue;

import java.util.List;
import java.util.Map;

import com.siteone.integration.price.data.SiteOneWsPriceResponseData;
import com.siteone.integration.product.data.PurchasedProduct;
import com.siteone.integration.product.data.SiteOneWsPurchasedProductData;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.core.model.product.ProductModel;

public interface SiteOnePurchasedProductWebService {
	
	List<PurchasedProduct> getPurchasedProducts(String AccountGuid, boolean isBillingAccount);

}
