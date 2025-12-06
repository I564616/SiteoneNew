package com.siteone.integration.services.ue;

import java.util.List;
import java.util.Map;

import com.siteone.integration.price.data.SiteOneWsPriceRequestData;
import com.siteone.integration.price.data.SiteOneWsPriceResponseData;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.core.model.product.ProductModel;
import org.springframework.http.HttpHeaders;

/**
 * @author 1230514
 *
 */
public interface SiteOnePriceWebService {

	SiteOneWsPriceResponseData getPrice(Map<ProductModel,Integer> products,Map<ProductModel,String> productsUoms,String storeId,List<PointOfServiceData> nearbyStoresList,B2BCustomerModel b2bCustomerModel,String currencyIso,boolean isNewBoomiEnv, List<String> d365Branches, boolean isD365NewUrl, String branchRetailID);

	SiteOneWsPriceResponseData callPricingService(SiteOneWsPriceRequestData siteOneWsPriceRequestData, String apiUrl, HttpHeaders httpHeaders);

	SiteOneWsPriceResponseData callFallbackPricingService(SiteOneWsPriceRequestData siteOneWsPriceRequestData);
	
}
