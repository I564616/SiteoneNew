package com.siteone.integration.service.impl;

import jakarta.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import com.siteone.integration.customer.data.SiteOneWsB2BCustomerResponseData;
import com.siteone.integration.services.ue.SiteOneContactWebService;

import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.servicelayer.ServicelayerBaseTest;
import de.hybris.platform.servicelayer.model.ModelService;


/**
 * @author 1230514
 *
 */
@IntegrationTest
public class DefaultSiteOneContactWebServiceTest extends ServicelayerBaseTest
{
	@Resource
	private SiteOneContactWebService siteOneContactWebService;
	
	@Resource
	private ModelService modelService;

	@Test
	public void contact() throws Exception
	{

		final B2BCustomerModel b2BCustomerModel = modelService.create(B2BCustomerModel.class);
		final B2BUnitModel b2BUnitModel = modelService.create(B2BUnitModel.class);
		b2BCustomerModel.setFirstName("test");
		b2BCustomerModel.setLastName("test");
		b2BCustomerModel.setEmail("email");
		b2BUnitModel.setGuid("123456sr432");
		b2BCustomerModel.setDefaultB2BUnit(b2BUnitModel);
		 

		final SiteOneWsB2BCustomerResponseData customerResponseData = siteOneContactWebService.createB2BCustomer(b2BCustomerModel, false);
        
		
		Assert.assertNotNull(customerResponseData);
		Assert.assertNotNull(customerResponseData.getContactId());
		Assert.assertTrue(customerResponseData.getIsSuccess());
		
	}

}
