/**
 * 
 */
package com.siteone.facade.requestaccount.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;

import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.*;

import com.siteone.core.event.RequestAccountEvent;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.SiteoneRequestAccountModel;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.facade.SiteoneRequestAccountData;
import com.siteone.integration.customer.createCustomer.data.SiteOneWsCreateCustomerResponseData;
import com.siteone.integration.services.ue.SiteOneCreateCustomerWebService;

/**
 * @author AA04994
 *
 */
@UnitTest
public class DefaultSiteoneRequestAccountFacadeTest
{

	@InjectMocks	
   private DefaultSiteoneRequestAccountFacade defaultSiteoneRequestAccountFacade;

   @Mock
   private Converter<SiteoneRequestAccountData, SiteoneRequestAccountModel> siteoneRequestAccountReverseConverter;

   @Mock
   private SiteOneCreateCustomerWebService siteOneCreateCustomerWebService;

   @Mock
   private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
   
   @Mock
   private BaseSiteService baseSiteService;
   
   @Mock
   private CommonI18NService commonI18NService;
   
   @Mock
   private EventService eventService;
   
   @Mock
   private SiteOneStoreFinderService storeFinderService;
   
   @Mock
   private B2BCustomerService b2bCustomerService;
   
   @Mock
	private BaseStoreService baseStoreService;
   
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		defaultSiteoneRequestAccountFacade = new DefaultSiteoneRequestAccountFacade();
		defaultSiteoneRequestAccountFacade.setSiteOneFeatureSwitchCacheService(siteOneFeatureSwitchCacheService);
		defaultSiteoneRequestAccountFacade.setSiteoneRequestAccountReverseConverter(siteoneRequestAccountReverseConverter);
		defaultSiteoneRequestAccountFacade.setSiteOneCreateCustomerWebService(siteOneCreateCustomerWebService);
		defaultSiteoneRequestAccountFacade.setBaseSiteService(baseSiteService);
		defaultSiteoneRequestAccountFacade.setCommonI18NService(commonI18NService);
		defaultSiteoneRequestAccountFacade.setEventService(eventService);
		defaultSiteoneRequestAccountFacade.setStoreFinderService(storeFinderService);
		defaultSiteoneRequestAccountFacade.setB2bCustomerService(b2bCustomerService);
		defaultSiteoneRequestAccountFacade.setBaseStoreService(baseStoreService);
	}

   @Test
   public void testCreateCustomerWhenLandscapingIndustryIsTrue() 
   {
       SiteoneRequestAccountData requestAccountData = new SiteoneRequestAccountData();
       RequestAccountEvent mockedEvent = new RequestAccountEvent();
       requestAccountData.setAccountNumber("");
       requestAccountData.setAcctGroupCode("");
       requestAccountData.setAddressLine1("11618 N Dale Mabry Hwy");
       requestAccountData.setAddressLine2("");
       requestAccountData.setCity("Tampa");
       requestAccountData.setCompanyName("Joes And Sons Olive Oils");
       requestAccountData.setContrEmpCount("10");
       requestAccountData.setContrPrimaryBusiness("23000/23050|Specialty/Other");
       requestAccountData.setContrYearsInBusiness("10");
       requestAccountData.setEmailAddress("vijayaindra.k@gmail.com");
       requestAccountData.setEnrollInPartnersProgram(true);
       requestAccountData.setFirstName("Joe");
       requestAccountData.setHasAccountNumber(false);
       requestAccountData.setIsAccountOwner(true);
       requestAccountData.setLanguagePreference("English");
       requestAccountData.setLastName("Son");
       requestAccountData.setPhoneNumber("813-284-7489");
       requestAccountData.setState("US-FL");
       requestAccountData.setStoreNumber("406");
       requestAccountData.setTypeOfCustomer("Contractor");
       requestAccountData.setZipcode("33618-3502");
       requestAccountData.setLandscapingIndustry(true);
       SiteOneWsCreateCustomerResponseData responseData = new SiteOneWsCreateCustomerResponseData();
       boolean isAccountExistsInUE = false;
       String boomi = "false";

       when(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).thenReturn(boomi);
       when(siteoneRequestAccountReverseConverter.convert(any(), any())).thenReturn(new SiteoneRequestAccountModel());
       when(storeFinderService.getStoreForId(any())).thenReturn(null);
       when(b2bCustomerService.getUserForUID(any())).thenReturn(null, null);
       when(siteOneCreateCustomerWebService.createCustomer(any(), anyBoolean())).thenReturn(responseData);
       when(baseSiteService.getBaseSiteForUID(any())).thenReturn(null, null);
       when(baseStoreService.getBaseStoreForUid(any())).thenReturn(null, null);
       when(commonI18NService.getCurrentLanguage()).thenReturn(null);
       doNothing().when(eventService).publishEvent(any());

       String result = defaultSiteoneRequestAccountFacade.createCustomer(requestAccountData, responseData, isAccountExistsInUE);

       Assert.assertEquals(true,requestAccountData.getLandscapingIndustry());
       Assert.assertNotEquals(false,requestAccountData.getLandscapingIndustry());
       assertNotNull(responseData);
   }

   @Test
   public void testCreateCustomerWhenLandscapingIndustryIsFalse() 
   {
       SiteoneRequestAccountData requestAccountData = new SiteoneRequestAccountData();
       RequestAccountEvent mockedEvent = new RequestAccountEvent();
       requestAccountData.setAccountNumber("");
       requestAccountData.setAcctGroupCode("");
       requestAccountData.setAddressLine1("11618 N Dale Mabry Hwy");
       requestAccountData.setAddressLine2("");
       requestAccountData.setCity("Tampa");
       requestAccountData.setCompanyName("Joes And Sons Olive Oils");
       requestAccountData.setContrEmpCount("10");
       requestAccountData.setContrPrimaryBusiness("23000/23050|Specialty/Other");
       requestAccountData.setContrYearsInBusiness("10");
       requestAccountData.setEmailAddress("vijayaindra.k@gmail.com");
       requestAccountData.setEnrollInPartnersProgram(true);
       requestAccountData.setFirstName("Joe");
       requestAccountData.setHasAccountNumber(false);
       requestAccountData.setIsAccountOwner(true);
       requestAccountData.setLanguagePreference("English");
       requestAccountData.setLastName("Son");
       requestAccountData.setPhoneNumber("813-284-7489");
       requestAccountData.setState("US-FL");
       requestAccountData.setStoreNumber("406");
       requestAccountData.setTypeOfCustomer("Contractor");
       requestAccountData.setZipcode("33618-3502");
       requestAccountData.setLandscapingIndustry(false); 
       SiteOneWsCreateCustomerResponseData responseData = new SiteOneWsCreateCustomerResponseData();
       boolean isAccountExistsInUE = false;
       String boomi = "false";

       when(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).thenReturn(boomi);
       when(siteoneRequestAccountReverseConverter.convert(any(), any())).thenReturn(new SiteoneRequestAccountModel());
       when(storeFinderService.getStoreForId(any())).thenReturn(null);
       when(b2bCustomerService.getUserForUID(any())).thenReturn(null, null);
       when(siteOneCreateCustomerWebService.createCustomer(any(), anyBoolean())).thenReturn(responseData);
       when(baseSiteService.getBaseSiteForUID(any())).thenReturn(null, null);
       when(baseStoreService.getBaseStoreForUid(any())).thenReturn(null, null);
       when(commonI18NService.getCurrentLanguage()).thenReturn(null);
       doNothing().when(eventService).publishEvent(any());

       String result = defaultSiteoneRequestAccountFacade.createCustomer(requestAccountData, responseData, isAccountExistsInUE);

       Assert.assertEquals(false,requestAccountData.getLandscapingIndustry());
       Assert.assertNotEquals(true,requestAccountData.getLandscapingIndustry());
       assertNotNull(responseData);
   }
}