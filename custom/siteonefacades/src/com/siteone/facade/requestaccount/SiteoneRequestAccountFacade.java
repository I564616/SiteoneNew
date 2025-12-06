/**
 *
 */
package com.siteone.facade.requestaccount;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.siteone.commerceservice.customer.dto.SiteOneContrPrimaryBusinessListDTO;
import com.siteone.commerceservices.dto.createCustomer.CreateCustomerResponseWsDTO;
import com.siteone.commerceservices.dto.createCustomer.SiteoneWsUpdateAccountInfoWsDTO;
import com.siteone.facade.SiteoneRequestAccountData;
import com.siteone.integration.customer.createCustomer.data.SiteOneWsCreateCustomerResponseData;
import com.siteone.integration.customer.createCustomer.data.SiteOneWsSelfServeOnlineAccessRequestData;

import com.siteone.commerceservices.dto.quotes.SiteoneWsNotifyQuoteWsDTO;


/**
 * @author SMondal
 *
 */
public interface SiteoneRequestAccountFacade
{
	void populateSiteoneRequestAccountDataToModel(final SiteoneRequestAccountData siteoneRequestAccountData);

	public String createCustomer(final SiteoneRequestAccountData siteoneRequestAccountData,
			SiteOneWsCreateCustomerResponseData siteOneWsCreateCustomerResponseData, final boolean isAccountExistsInUE);

	public String createParentCustomer(final SiteoneRequestAccountData siteoneRequestAccountData,SiteOneWsCreateCustomerResponseData siteOneWsCreateCustomerResponseData,final boolean isAccountExistsInUE );

	List<String> getLightingReasonOf();

	String getReasonForContact(String reason);


	CreateCustomerResponseWsDTO getUpdateCustomerResponse(final SiteoneWsUpdateAccountInfoWsDTO siteoneWsUpdateAccountInfoWsDTO,
			final SiteoneRequestAccountData siteoneRequestAccountData);

	String getContrPrimaryBuisness(Map<String, SiteOneContrPrimaryBusinessListDTO> primaryBusinessDTOMap,
			final SiteoneWsUpdateAccountInfoWsDTO siteoneWsUpdateAccountInfoWsDTO);

	public String getSelfServeResponse(final SiteOneWsSelfServeOnlineAccessRequestData siteOneWsSelfServeOnlineAccessRequestData);

	CreateCustomerResponseWsDTO getUpdateParentResponse(final SiteoneWsUpdateAccountInfoWsDTO siteoneWsUpdateAccountInfoWsDTO,final SiteoneRequestAccountData siteoneRequestAccountData);

	void logRequestError(Object siteoneWsUpdateAccountInfoWsDTO, CreateCustomerResponseWsDTO response);

	CreateCustomerResponseWsDTO validateRequest(SiteoneWsUpdateAccountInfoWsDTO siteoneWsUpdateAccountInfoWsDTO);
	
	String notifyQuoteEmailStatus(final SiteoneWsNotifyQuoteWsDTO siteoneWsNotifyQuoteWsDTO);
	
	StringBuilder decryptData(InputStream inputStream) throws IOException;

}
