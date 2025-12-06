/**
 *
 */
package com.siteone.core.requestaccount.service;

import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;

import com.siteone.commerceservices.dto.createCustomer.CreateCustomerResponseWsDTO;
import com.siteone.core.model.SiteoneRequestAccountModel;


/**
 * @author SMondal
 *
 */
public interface SiteoneRequestAccountService
{
	void saveSiteoneRequestAccountModel(final SiteoneRequestAccountModel siteoneRequestAccountModel);

	CountryModel getCountryByCountryCode(String countryCode);

	RegionModel getRegionByIsoCode(CountryModel country, String stateIsoCode);

	void exportRealtimeLogFile(Object siteoneWsUpdateAccountInfoWsDTO, CreateCustomerResponseWsDTO response);

}
