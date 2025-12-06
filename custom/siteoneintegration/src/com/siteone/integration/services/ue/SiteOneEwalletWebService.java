package com.siteone.integration.services.ue;

import java.util.List;

import org.springframework.web.client.ResourceAccessException;

import com.siteone.core.model.SiteoneEwalletCreditCardModel;
import com.siteone.integration.ewallet.data.SiteOneWsEwalletResponseData;
import com.siteone.integration.scan.product.data.SiteOneCAGlobalPaymentRequest;
import com.siteone.integration.global.data.SiteOneCAGlobalPaymentResponse;
import com.siteone.integration.global.data.SiteOneCAGlobalPaymentVoidRequest;


import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.commercefacades.user.data.AddressData;



public interface SiteOneEwalletWebService {

		SiteOneWsEwalletResponseData createOrUpdOrDelEwallet(SiteoneEwalletCreditCardModel creditCardModel, String operationType, boolean isNewBoomiEnv) throws ResourceAccessException;

		SiteOneWsEwalletResponseData assignOrRevokeEwallet(SiteoneEwalletCreditCardModel creditCardModel,
				String operationType,List<B2BCustomerModel> customerModel, boolean isNewBoomiEnv) throws ResourceAccessException;
		SiteOneCAGlobalPaymentResponse addGlobalPayment(String authToken,SiteOneCAGlobalPaymentRequest requestData);
		 void updateVoidPayment(SiteOneCAGlobalPaymentVoidRequest voidRequest, String referenceID,String authToken) ;
		
}
