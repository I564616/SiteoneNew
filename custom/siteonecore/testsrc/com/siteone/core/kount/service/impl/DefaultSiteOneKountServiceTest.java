/**
 * 
 */
package com.siteone.core.kount.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.order.payment.SiteoneCreditCardPaymentInfoModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import com.kount.ris.util.CartItem;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;

import org.apache.commons.configuration2.Configuration;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.order.services.SiteOneOrderService;
import com.siteone.facade.customer.info.SiteOneCustInfoData;
import com.siteone.facades.quote.data.QuoteDetailsData;
import com.siteone.integration.customer.info.SiteOneCustInfoResponeData;
import com.siteone.integration.linktopay.order.data.SiteOneWsLinkToPayBillingInfoData;
import com.siteone.integration.linktopay.order.data.SiteOneWsLinkToPayContactInfoData;
import com.siteone.integration.linktopay.order.data.SiteOneWsLinkToPayOrderResponseData;
import com.siteone.integration.linktopay.order.data.SiteOneWsLinkToPayProductDetailsData;
import com.siteone.integration.linktopay.order.data.SiteOneWsLinkToPayResultResponseData;
import com.siteone.integration.linktopay.order.data.SiteOneWsLinkToPayShippingInfoData;
import com.siteone.integration.order.data.SiteOneQuoteShiptoRequestData;
import com.siteone.integration.services.ue.SiteOneCustInfoWebService;

import junit.framework.Assert;

/**
 * @author AA04994
 *
 */
public class DefaultSiteOneKountServiceTest
{
	@Mock
	@Resource
	protected CartService cartService;

	@Resource
	protected SessionService sessionService;

	@Resource(name = "defaultConfigurationService")
	private ConfigurationService configurationService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "siteOneOrderService")
	private SiteOneOrderService siteOneOrderService;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "siteOneCustInfoDataConverter")
	private Converter<SiteOneCustInfoResponeData, SiteOneCustInfoData> siteOneCustInfoDataConverter;

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;

	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;

	@Resource(name = "defaultSiteOneB2BUnitService")
	private SiteOneB2BUnitService defaultSiteOneB2BUnitService;

	@Resource(name = "siteOneCustInfoWebService")
	private SiteOneCustInfoWebService siteOneCustInfoWebService;
	
	@Resource
	protected CommonI18NService commonI18NService;
	
	@Mock
	private DefaultSiteOneKountService defaultSiteOneKountService;
	
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		defaultSiteOneKountService = new DefaultSiteOneKountService();
	}

	@Test
	public void getLinkToPayCartItemsNullTest()
	{
		CartItem cartItem;
		final List<CartItem> cartItemList = new ArrayList<>();
		final SiteOneWsLinkToPayOrderResponseData linkToPayOrderResponseData = new SiteOneWsLinkToPayOrderResponseData();
		final SiteOneWsLinkToPayResultResponseData siteOneWsLinkToPayResultResponseData = new SiteOneWsLinkToPayResultResponseData();
		final SiteOneWsLinkToPayContactInfoData siteOneWsLinkToPayContactInfoData = new SiteOneWsLinkToPayContactInfoData();
		final SiteOneWsLinkToPayBillingInfoData siteOneWsLinkToPayBillingInfoData = new SiteOneWsLinkToPayBillingInfoData();
		final SiteOneWsLinkToPayShippingInfoData siteOneWsLinkToPayShippingInfoData = new SiteOneWsLinkToPayShippingInfoData();
		final SiteOneWsLinkToPayProductDetailsData siteOneWsLinkToPayProductDetailsData = new SiteOneWsLinkToPayProductDetailsData();
		final List<SiteOneWsLinkToPayProductDetailsData> listProductDetails = new ArrayList<>();
		final List<String>listOfMessage = new ArrayList<>();
	   String message = new String();
		message =  "Order details fetch successful.";
		listOfMessage.add(message);
		linkToPayOrderResponseData.setCorrelationID("359ca2f3-3b12-457e-aa9a-ff05d981931b");
		linkToPayOrderResponseData.setIsSuccess(Boolean.TRUE);
		linkToPayOrderResponseData.setMessage(listOfMessage);
		
		siteOneWsLinkToPayContactInfoData.setContactEmail("aaatotallandscape.j@gmail.com");
		siteOneWsLinkToPayContactInfoData.setCustomerName("AAA Total Landscape");
		siteOneWsLinkToPayContactInfoData.setPhoneNumber("5593398036");
		
		siteOneWsLinkToPayBillingInfoData.setAddressLine1("15457 Avenue 280");
		siteOneWsLinkToPayBillingInfoData.setAddressLine2("");
		siteOneWsLinkToPayBillingInfoData.setCity("Visalia");
		siteOneWsLinkToPayBillingInfoData.setEnteredBy("Mario Salazar");
		siteOneWsLinkToPayBillingInfoData.setEnteredByEmail("MSalazar2@siteone.com");
		siteOneWsLinkToPayBillingInfoData.setPONumber("TURF");
		siteOneWsLinkToPayBillingInfoData.setState("CA");
		siteOneWsLinkToPayBillingInfoData.setZip("93292-9718");
		
		siteOneWsLinkToPayProductDetailsData.setDiscountedUnitPrice("0.0000000");
		siteOneWsLinkToPayProductDetailsData.setItemDescription("Synthetic Turf Roll Core 15 ft.");
		siteOneWsLinkToPayProductDetailsData.setLineNumber(2);
		siteOneWsLinkToPayProductDetailsData.setListPrice("12.6968");
		siteOneWsLinkToPayProductDetailsData.setPrimaryCategoryName("Landscape Supplies");
		siteOneWsLinkToPayProductDetailsData.setProductName("artificial turf core 15ft");
		siteOneWsLinkToPayProductDetailsData.setQuantity(6);
		siteOneWsLinkToPayProductDetailsData.setSelectedUOM("EA");
		siteOneWsLinkToPayProductDetailsData.setSkuId("743780");
		siteOneWsLinkToPayProductDetailsData.setUOM("EA");
		listProductDetails.add(siteOneWsLinkToPayProductDetailsData);
		
		siteOneWsLinkToPayResultResponseData.setBillingInfo(siteOneWsLinkToPayBillingInfoData);
		siteOneWsLinkToPayResultResponseData.setContactInfo(siteOneWsLinkToPayContactInfoData);
		siteOneWsLinkToPayResultResponseData.setShippingInfo(null);
		siteOneWsLinkToPayResultResponseData.setLineItems(listProductDetails);
		siteOneWsLinkToPayResultResponseData.setCorrelationId("359ca2f3-3b12-457e-aa9a-ff05d981931b");
		siteOneWsLinkToPayResultResponseData.setExternalSystemId("1");
		siteOneWsLinkToPayResultResponseData.setLinkToPayStatus("Pending");
		siteOneWsLinkToPayResultResponseData.setOrderNumber("M148713262");
		siteOneWsLinkToPayResultResponseData.setTotalAmount("10710.00");
		siteOneWsLinkToPayResultResponseData.setIsShippingSameAsBilling(Boolean.TRUE);
		siteOneWsLinkToPayResultResponseData.setTransactionStatus("Pending");
		
		linkToPayOrderResponseData.setResult(siteOneWsLinkToPayResultResponseData);
		
		final List<CartItem> actual = defaultSiteOneKountService.getLinkToPayCartItems(linkToPayOrderResponseData);
		Assert.assertNotNull(actual);
	}
	
	@Test
	public void getLinkToPayCartItemsEmptyTest()
	{
		CartItem cartItem;
		final List<CartItem> cartItemList = new ArrayList<>();
		final SiteOneWsLinkToPayOrderResponseData linkToPayOrderResponseData = new SiteOneWsLinkToPayOrderResponseData();
		final SiteOneWsLinkToPayResultResponseData siteOneWsLinkToPayResultResponseData = new SiteOneWsLinkToPayResultResponseData();
		final SiteOneWsLinkToPayContactInfoData siteOneWsLinkToPayContactInfoData = new SiteOneWsLinkToPayContactInfoData();
		final SiteOneWsLinkToPayBillingInfoData siteOneWsLinkToPayBillingInfoData = new SiteOneWsLinkToPayBillingInfoData();
		final SiteOneWsLinkToPayShippingInfoData siteOneWsLinkToPayShippingInfoData = new SiteOneWsLinkToPayShippingInfoData();
		final SiteOneWsLinkToPayProductDetailsData siteOneWsLinkToPayProductDetailsData = new SiteOneWsLinkToPayProductDetailsData();
		final List<SiteOneWsLinkToPayProductDetailsData> listProductDetails = new ArrayList<>();
		final List<String>listOfMessage = new ArrayList<>();
	   String message = new String();
		message =  "Order details fetch successful.";
		listOfMessage.add(message);
		linkToPayOrderResponseData.setCorrelationID("359ca2f3-3b12-457e-aa9a-ff05d981931b");
		linkToPayOrderResponseData.setIsSuccess(Boolean.TRUE);
		linkToPayOrderResponseData.setMessage(listOfMessage);
		
		siteOneWsLinkToPayContactInfoData.setContactEmail("aaatotallandscape.j@gmail.com");
		siteOneWsLinkToPayContactInfoData.setCustomerName("AAA Total Landscape");
		siteOneWsLinkToPayContactInfoData.setPhoneNumber("5593398036");
		
		siteOneWsLinkToPayBillingInfoData.setAddressLine1("15457 Avenue 280");
		siteOneWsLinkToPayBillingInfoData.setAddressLine2("");
		siteOneWsLinkToPayBillingInfoData.setCity("Visalia");
		siteOneWsLinkToPayBillingInfoData.setEnteredBy("Mario Salazar");
		siteOneWsLinkToPayBillingInfoData.setEnteredByEmail("MSalazar2@siteone.com");
		siteOneWsLinkToPayBillingInfoData.setPONumber("TURF");
		siteOneWsLinkToPayBillingInfoData.setState("CA");
		siteOneWsLinkToPayBillingInfoData.setZip("93292-9718");
		
		siteOneWsLinkToPayProductDetailsData.setDiscountedUnitPrice("0.0000000");
		siteOneWsLinkToPayProductDetailsData.setItemDescription("Synthetic Turf Roll Core 15 ft.");
		siteOneWsLinkToPayProductDetailsData.setLineNumber(2);
		siteOneWsLinkToPayProductDetailsData.setListPrice("12.6968");
		siteOneWsLinkToPayProductDetailsData.setPrimaryCategoryName("Landscape Supplies");
		siteOneWsLinkToPayProductDetailsData.setProductName("artificial turf core 15ft");
		siteOneWsLinkToPayProductDetailsData.setQuantity(6);
		siteOneWsLinkToPayProductDetailsData.setSelectedUOM("EA");
		siteOneWsLinkToPayProductDetailsData.setSkuId("743780");
		siteOneWsLinkToPayProductDetailsData.setUOM("EA");
		listProductDetails.add(siteOneWsLinkToPayProductDetailsData);
		
		siteOneWsLinkToPayResultResponseData.setBillingInfo(siteOneWsLinkToPayBillingInfoData);
		siteOneWsLinkToPayResultResponseData.setContactInfo(siteOneWsLinkToPayContactInfoData);
		siteOneWsLinkToPayResultResponseData.setShippingInfo(null);
		siteOneWsLinkToPayResultResponseData.setLineItems(listProductDetails);
		siteOneWsLinkToPayResultResponseData.setCorrelationId("359ca2f3-3b12-457e-aa9a-ff05d981931b");
		siteOneWsLinkToPayResultResponseData.setExternalSystemId("1");
		siteOneWsLinkToPayResultResponseData.setLinkToPayStatus("Pending");
		siteOneWsLinkToPayResultResponseData.setOrderNumber("M148713262");
		siteOneWsLinkToPayResultResponseData.setTotalAmount("10710.00");
		siteOneWsLinkToPayResultResponseData.setIsShippingSameAsBilling(Boolean.TRUE);
		siteOneWsLinkToPayResultResponseData.setTransactionStatus("Pending");
		
		linkToPayOrderResponseData.setResult(siteOneWsLinkToPayResultResponseData);
		
		final List<CartItem> actual = defaultSiteOneKountService.getLinkToPayCartItems(linkToPayOrderResponseData);
		Assert.assertFalse(actual.isEmpty());
	}
		
	@Test
	public void testGetOriginalEmail_ValidInput() {
	    DefaultSiteOneKountService service = new DefaultSiteOneKountService();
	    String result = service.getOriginalEmail("abc|user@example.com");
	    assertEquals("user@example.com", result);
	}

	@Test
	public void testGetOriginalEmail_NullInput() {
	    DefaultSiteOneKountService service = new DefaultSiteOneKountService();
	    String result = service.getOriginalEmail(null);
	    assertNull(result);
	}

	@Test
	public void testRoundDoubleToInteger() {
	    DefaultSiteOneKountService service = new DefaultSiteOneKountService();
	    int result = service.roundDoubleToInteger(123.6);
	    assertEquals(124, result);
	}

	@Test
	public void testValidateAVS_ReturnsTrue() {
	    DefaultSiteOneKountService service = new DefaultSiteOneKountService();
	    SiteoneCreditCardPaymentInfoModel model = new SiteoneCreditCardPaymentInfoModel();
	    model.setAvs("Y");
	    assertTrue(service.validateAVS(model));
	}

	@Test
	public void testValidateCVV_ReturnsTrue() {
	    DefaultSiteOneKountService service = new DefaultSiteOneKountService();
	    SiteoneCreditCardPaymentInfoModel model = new SiteoneCreditCardPaymentInfoModel();
	    model.setCvv("M");
	    assertTrue(service.validateCVV(model));
	}
		
}
