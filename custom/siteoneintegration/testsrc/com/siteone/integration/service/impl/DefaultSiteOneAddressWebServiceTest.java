package com.siteone.integration.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.siteone.integration.address.data.SiteOneWsAddressResponseData;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.rest.client.SiteOneRestClient;
import com.siteone.integration.services.ue.SiteOneAddressWebService;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.ServicelayerBaseTest;
import de.hybris.platform.servicelayer.model.ModelService;

public class DefaultSiteOneAddressWebServiceTest extends ServicelayerBaseTest {

	@Resource
	private SiteOneAddressWebService siteOneAddressWebService;

	@Resource
	private ModelService modelService;

	private static final Logger LOG = Logger.getLogger(DefaultSiteOneAddressWebServiceTest.class);

	@Test
	public void createAddress() throws Exception {
		AddressModel addressModel = createAddressModel();
		B2BUnitModel unitModel = createUnitModel();
		final SiteOneWsAddressResponseData addressResponseData = siteOneAddressWebService.createOrUpdateAddress(
				addressModel, unitModel, SiteoneintegrationConstants.OPERATION_TYPE_CREATE, false);
		assertThat(addressResponseData).isNotNull();
		assertThat(addressResponseData.getMessage()).isNotNull();

		LOG.info("addressResponseData.getMessage()" + addressResponseData.getMessage());
		LOG.info("addressResponseData.getIsSucess()" + addressResponseData.getIsSucess());

	}

	@Test
	public void updateAddress() throws Exception {
		AddressModel addressModel = createAddressModel();
		B2BUnitModel unitModel = createUnitModel();

		addressModel.setStreetname("FER");
		addressModel.setStreetnumber("975");
		final SiteOneWsAddressResponseData addressResponseData = siteOneAddressWebService.createOrUpdateAddress(
				addressModel, unitModel, SiteoneintegrationConstants.OPERATION_TYPE_UPDATE, false);

		LOG.info("addressResponseData.getMessage()" + addressResponseData.getMessage());
		LOG.info("addressResponseData.getIsSucess()" + addressResponseData.getIsSucess());

		assertThat(addressResponseData).isNotNull();
		assertThat(addressResponseData.getMessage()).isNotNull();

	}

	@Test
	public void deleteAddress() throws Exception {
		AddressModel addressModel = createAddressModel();
		B2BUnitModel unitModel = createUnitModel();

		final SiteOneWsAddressResponseData addressResponseData = siteOneAddressWebService
				.deleteAddress(addressModel.getGuid(), unitModel.getGuid(), false);
		assertThat(addressResponseData).isNotNull();
		assertThat(addressResponseData.getMessage()).isNotNull();

		LOG.info("addressResponseData.getMessage()" + addressResponseData.getMessage());
		LOG.info("addressResponseData.getIsSucess()" + addressResponseData.getIsSucess());

	}

	public AddressModel createAddressModel() throws Exception {
		final RegionModel region = modelService.create(RegionModel.class);
		final CountryModel country = modelService.create(CountryModel.class);

		AddressModel addressModel = new AddressModel();
		addressModel.setStreetname("Abc");
		addressModel.setStreetnumber("123");
		addressModel.setTown("Derbyshire");
		addressModel.setPostalcode("30061");
		addressModel.setDistrict("Atlanta");
		region.setName("Georgia");
		region.setIsocode("US-GA");
		country.setName("US");
		country.setIsocode("US");
		addressModel.setRegion(region);
		addressModel.setCountry(country);
		addressModel.setPostalcode("30014");

		return addressModel;
	}

	public B2BUnitModel createUnitModel() throws Exception {
		B2BUnitModel unitModel = new B2BUnitModel();
		unitModel.setGuid("DCF3BD1F-C982-48DF-AD34-2A531E3324B0");

		return unitModel;
	}

}
