/**
 *
 */
package com.siteone.facade.invoice;

import static org.mockito.BDDMockito.given;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.url.UrlResolver;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.site.BaseSiteService;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.services.SiteOneProductService;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.integration.invoice.order.data.SiteoneInvoiceDeliveryInfoResponseData;
import com.siteone.integration.invoice.order.data.SiteoneInvoiceDetailsResponseData;
import com.siteone.integration.invoice.order.data.SiteoneInvoiceInfoResponseData;
import com.siteone.integration.invoice.order.data.SiteoneInvoiceItemsInfoResponseData;
import com.siteone.integration.invoice.order.data.SiteoneInvoicePaymentsInfoResponseData;
import com.siteone.integration.invoice.order.data.SiteoneInvoiceRequestData;
import com.siteone.integration.invoice.order.data.SiteoneInvoiceResponseData;
import com.siteone.integration.services.invoice.SiteOneInvoiceWebService;

import junit.framework.Assert;


/**
 *
 */
@UnitTest
public class DefaultInvoiceFacadeTest
{

	@Mock
	private BaseSiteService baseSiteService;

	@Mock
	private SiteOneInvoiceWebService siteOneInvoiceWebService;

	@Mock
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Mock
	private SiteOneStoreFinderService siteOneStoreFinderService;

	@Mock
	private ProductFacade productFacade;

	@Mock
	private B2BUnitService b2bUnitService;

	@Mock
	private SiteOneProductService siteOneProductService;

	@Mock
	private SiteOneB2BUnitFacade siteOneB2BUnitFacade;

	@Mock
	protected B2BUnitFacade b2bUnitFacade;

	@Mock
	private UrlResolver<ProductModel> productModelUrlResolver;

	@Mock
	private ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator;

	@Mock
	private Converter<ProductModel, ProductData> productConverter;

	@Mock
	private SiteOneProductFacade siteOneProductFacade;

	@Mock
	DefaultInvoiceFacade defaultInvoiceFacade;

	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		defaultInvoiceFacade = new DefaultInvoiceFacade();
		defaultInvoiceFacade.setSiteOneInvoiceWebService(siteOneInvoiceWebService);
		defaultInvoiceFacade.setB2bUnitFacade(b2bUnitFacade);
		defaultInvoiceFacade.setSiteOneFeatureSwitchCacheService(siteOneFeatureSwitchCacheService);
	}

	@Test
	public void invoiceLandingInitialTestCase()
	{
		final SiteoneInvoiceRequestData invoiceRequest = new SiteoneInvoiceRequestData();
		final SiteoneInvoiceResponseData invoiceResponse = new SiteoneInvoiceResponseData();
		final List<SiteoneInvoiceInfoResponseData> listData = new ArrayList<>();
		final SiteoneInvoiceInfoResponseData data = new SiteoneInvoiceInfoResponseData();

		invoiceRequest.setStartDate("2025-06-02T13:24:38.950Z");
		invoiceRequest.setEndDate("2025-09-02T13:24:38.950Z");
		invoiceRequest.setSort("0");
		invoiceRequest.setSortDirection("0");
		invoiceRequest.setPage(Integer.valueOf(1));
		invoiceRequest.setRows(Integer.valueOf(25));
		invoiceRequest.setIncludeShipTos(true);

		invoiceResponse.setTotalRecords("387");
		invoiceResponse.setPageNumber("1");
		invoiceResponse.setRowsPerPage("25");
		invoiceResponse.setTotalPages("16");

		data.setCustomerNumber("2060119");
		data.setOrderingCustomerNumber("2060119-3000");
		data.setHybrisOrderNumber(null);
		data.setInvoiceNumber("157802797-001");
		data.setInvoiceDate("2025-08-29T16:00:33.827");
		data.setOrderNumber("M157802797");
		data.setPoNumber("BTR30-HD Sod Roller");
		data.setTotal(Double.valueOf(8567.9900));
		data.setBalance(Double.valueOf(8567.9900));
		data.setActualOrderShipmentID("ba5b3fb2-6bd0-4e0a-9939-ba84f14423c4");
		data.setBranchName("Plano TX");
		data.setBranchNumber("212");

		listData.add(data);
		invoiceResponse.setData(listData);

		final B2BUnitData defaultUnit = new B2BUnitData();
		defaultUnit.setUid("2060119_US");
		final String uid = defaultUnit.getUid().trim().split("[_]")[0].trim();
		final String boomi = "false";

		given(siteOneB2BUnitFacade.getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
		given(siteOneInvoiceWebService.getInvoicesData(invoiceRequest, uid, Integer.valueOf(1), false)).willReturn(invoiceResponse);
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi);

		Assert.assertNotNull(invoiceResponse);
		Assert.assertEquals(data.getOrderNumber(), invoiceResponse.getData().get(0).getOrderNumber());
	}

	@Test
	public void invoiceOrderNumberSearch()
	{
		final SiteoneInvoiceRequestData invoiceRequest = new SiteoneInvoiceRequestData();
		final SiteoneInvoiceResponseData invoiceResponse = new SiteoneInvoiceResponseData();
		final List<SiteoneInvoiceInfoResponseData> listData = new ArrayList<>();
		final SiteoneInvoiceInfoResponseData data = new SiteoneInvoiceInfoResponseData();

		invoiceRequest.setStartDate("2025-06-02T13:24:38.950Z");
		invoiceRequest.setEndDate("2025-09-02T13:24:38.950Z");
		invoiceRequest.setOrderNumber("M157802797");
		invoiceRequest.setSort("0");
		invoiceRequest.setSortDirection("0");
		invoiceRequest.setPage(Integer.valueOf(1));
		invoiceRequest.setRows(Integer.valueOf(25));
		invoiceRequest.setIncludeShipTos(true);

		invoiceResponse.setTotalRecords("387");
		invoiceResponse.setPageNumber("1");
		invoiceResponse.setRowsPerPage("25");
		invoiceResponse.setTotalPages("16");

		data.setCustomerNumber("2060119");
		data.setOrderingCustomerNumber("2060119-3000");
		data.setHybrisOrderNumber(null);
		data.setInvoiceNumber("157802797-001");
		data.setInvoiceDate("2025-08-29T16:00:33.827");
		data.setOrderNumber("M157802797");
		data.setPoNumber("BTR30-HD Sod Roller");
		data.setTotal(Double.valueOf(8567.9900));
		data.setBalance(Double.valueOf(8567.9900));
		data.setActualOrderShipmentID("ba5b3fb2-6bd0-4e0a-9939-ba84f14423c4");
		data.setBranchName("Plano TX");
		data.setBranchNumber("212");

		listData.add(data);
		invoiceResponse.setData(listData);

		final B2BUnitData defaultUnit = new B2BUnitData();
		defaultUnit.setUid("2060119_US");
		final String uid = defaultUnit.getUid().trim().split("[_]")[0].trim();
		final String boomi = "false";

		given(siteOneB2BUnitFacade.getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
		given(siteOneInvoiceWebService.getInvoicesData(invoiceRequest, uid, Integer.valueOf(1), false)).willReturn(invoiceResponse);
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi);

		Assert.assertEquals(data.getOrderNumber(), invoiceResponse.getData().get(0).getOrderNumber());
		Assert.assertEquals(1, invoiceResponse.getData().size());
	}

	@Test
	public void invoiceInvoiceNumberSearch()
	{
		final SiteoneInvoiceRequestData invoiceRequest = new SiteoneInvoiceRequestData();
		final SiteoneInvoiceResponseData invoiceResponse = new SiteoneInvoiceResponseData();
		final List<SiteoneInvoiceInfoResponseData> listData = new ArrayList<>();
		final SiteoneInvoiceInfoResponseData data = new SiteoneInvoiceInfoResponseData();

		invoiceRequest.setStartDate("2025-06-02T13:24:38.950Z");
		invoiceRequest.setEndDate("2025-09-02T13:24:38.950Z");
		invoiceRequest.setInvoiceNumber("157802797-001");
		invoiceRequest.setSort("0");
		invoiceRequest.setSortDirection("0");
		invoiceRequest.setPage(Integer.valueOf(1));
		invoiceRequest.setRows(Integer.valueOf(25));
		invoiceRequest.setIncludeShipTos(true);

		invoiceResponse.setTotalRecords("387");
		invoiceResponse.setPageNumber("1");
		invoiceResponse.setRowsPerPage("25");
		invoiceResponse.setTotalPages("16");

		data.setCustomerNumber("2060119");
		data.setOrderingCustomerNumber("2060119-3000");
		data.setHybrisOrderNumber(null);
		data.setInvoiceNumber("157802797-001");
		data.setInvoiceDate("2025-08-29T16:00:33.827");
		data.setOrderNumber("M157802797");
		data.setPoNumber("BTR30-HD Sod Roller");
		data.setTotal(Double.valueOf(8567.9900));
		data.setBalance(Double.valueOf(8567.9900));
		data.setActualOrderShipmentID("ba5b3fb2-6bd0-4e0a-9939-ba84f14423c4");
		data.setBranchName("Plano TX");
		data.setBranchNumber("212");

		listData.add(data);
		invoiceResponse.setData(listData);

		final B2BUnitData defaultUnit = new B2BUnitData();
		defaultUnit.setUid("2060119_US");
		final String uid = defaultUnit.getUid().trim().split("[_]")[0].trim();
		final String boomi = "false";

		given(siteOneB2BUnitFacade.getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
		given(siteOneInvoiceWebService.getInvoicesData(invoiceRequest, uid, Integer.valueOf(1), false)).willReturn(invoiceResponse);
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi);

		Assert.assertEquals(data.getInvoiceNumber(), invoiceResponse.getData().get(0).getInvoiceNumber());
		Assert.assertEquals(1, invoiceResponse.getData().size());
	}

	@Test
	public void invoicePONumberSearch()
	{
		final SiteoneInvoiceRequestData invoiceRequest = new SiteoneInvoiceRequestData();
		final SiteoneInvoiceResponseData invoiceResponse = new SiteoneInvoiceResponseData();
		final List<SiteoneInvoiceInfoResponseData> listData = new ArrayList<>();
		final SiteoneInvoiceInfoResponseData data = new SiteoneInvoiceInfoResponseData();

		invoiceRequest.setStartDate("2025-06-02T13:24:38.950Z");
		invoiceRequest.setEndDate("2025-09-02T13:24:38.950Z");
		invoiceRequest.setPoNumber("BTR30-HD Sod Roller");
		invoiceRequest.setSort("0");
		invoiceRequest.setSortDirection("0");
		invoiceRequest.setPage(Integer.valueOf(1));
		invoiceRequest.setRows(Integer.valueOf(25));
		invoiceRequest.setIncludeShipTos(true);

		invoiceResponse.setTotalRecords("387");
		invoiceResponse.setPageNumber("1");
		invoiceResponse.setRowsPerPage("25");
		invoiceResponse.setTotalPages("16");

		data.setCustomerNumber("2060119");
		data.setOrderingCustomerNumber("2060119-3000");
		data.setHybrisOrderNumber(null);
		data.setInvoiceNumber("157802797-001");
		data.setInvoiceDate("2025-08-29T16:00:33.827");
		data.setOrderNumber("M157802797");
		data.setPoNumber("BTR30-HD Sod Roller");
		data.setTotal(Double.valueOf(8567.9900));
		data.setBalance(Double.valueOf(8567.9900));
		data.setActualOrderShipmentID("ba5b3fb2-6bd0-4e0a-9939-ba84f14423c4");
		data.setBranchName("Plano TX");
		data.setBranchNumber("212");

		listData.add(data);
		invoiceResponse.setData(listData);

		final B2BUnitData defaultUnit = new B2BUnitData();
		defaultUnit.setUid("2060119_US");
		final String uid = defaultUnit.getUid().trim().split("[_]")[0].trim();
		final String boomi = "false";

		given(siteOneB2BUnitFacade.getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
		given(siteOneInvoiceWebService.getInvoicesData(invoiceRequest, uid, Integer.valueOf(1), false)).willReturn(invoiceResponse);
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi);

		Assert.assertEquals(data.getPoNumber(), invoiceResponse.getData().get(0).getPoNumber());
		Assert.assertEquals(1, invoiceResponse.getData().size());
	}

	@Test
	public void invoiceDetailInitialTestCase()
	{
		final SiteoneInvoiceDetailsResponseData response = new SiteoneInvoiceDetailsResponseData();
		final SiteoneInvoiceDeliveryInfoResponseData deliveryResponse = new SiteoneInvoiceDeliveryInfoResponseData();
		final List<SiteoneInvoicePaymentsInfoResponseData> paymentInfoList = new ArrayList<>();
		final SiteoneInvoicePaymentsInfoResponseData paymentInfo = new SiteoneInvoicePaymentsInfoResponseData();
		final List<SiteoneInvoiceItemsInfoResponseData> lineItemList = new ArrayList<>();
		final SiteoneInvoiceItemsInfoResponseData lineItem = new SiteoneInvoiceItemsInfoResponseData();

		response.setInvoiceNumber("157812509-001");
		response.setOrderShipmentNumber("157812509-001");
		response.setInvoiceDate("2025-08-29T08:09:20.65");
		response.setPoNumber("stoney hollow");
		response.setSubTotal(Double.valueOf(63.80));
		response.setFreight(Double.valueOf(0.00));
		response.setTaxTotal(Double.valueOf(0.00));
		response.setInvoiceTotal(Double.valueOf(63.80));
		response.setAmountDue("63.80");

		deliveryResponse.setFullfillmentType("Pick-up");
		deliveryResponse.setContactName("eneas Ortiz");
		deliveryResponse.setContactPhone("2144606773");
		deliveryResponse.setContactEmail(null);
		deliveryResponse.setSpecialInstructions(null);
		deliveryResponse.setRequestedShipmentDate("2025-08-29T08:09:00");
		deliveryResponse.setDeliveryPeriod("Any time");
		deliveryResponse.setDeliveryMode("Customer Pick up");
		deliveryResponse.setDeliveryStatus(null);
		deliveryResponse.setHomeBranchNumber("212");
		deliveryResponse.setStoreName("Plano TX");
		deliveryResponse.setCouponDescription(null);
		deliveryResponse.setCouponCode(null);
		deliveryResponse.setAddressName(null);
		deliveryResponse.setAddress1("1200 Summit Ave");
		deliveryResponse.setAddress2("");
		deliveryResponse.setCounty("Collin");
		deliveryResponse.setCity("Plano");
		deliveryResponse.setStateProvince("TX");
		deliveryResponse.setPostalCode("75074-8510");
		deliveryResponse.setGeoCode("440852310");
		deliveryResponse.setCountry("US");

		paymentInfo.setBranchName("Plano TX");
		paymentInfo.setPaymentMethod("On Account");
		paymentInfo.setAmountPaid(Double.valueOf(63.8000));
		paymentInfo.setBillingTerms("5% 15TH PROX");
		paymentInfo.setLast4CreditCardDigits(null);
		paymentInfo.setCreditCardType(null);
		paymentInfo.setAddressName(null);
		paymentInfo.setAddress1("945 Stockton Dr Ste 8110");
		paymentInfo.setAddress2(null);
		paymentInfo.setCounty("Collin");
		paymentInfo.setCity("Allen");
		paymentInfo.setStateProvince("TX");
		paymentInfo.setPostalCode("75013");
		paymentInfo.setGeoCode("440853543");
		paymentInfo.setCountry("US");
		paymentInfoList.add(paymentInfo);

		lineItem.setLineNumber("1");
		lineItem.setSkuid(Integer.valueOf(95672));
		lineItem.setItemNumber("PGV151");
		lineItem.setItemDescription("Hunter PGV Globe/Angle Valve Plastic 1-1/2 in. w/ Flow Control FIPT x FIPT");
		lineItem.setUnitPrice(Double.valueOf(63.8030));
		lineItem.setOriginalUnitPrice(Double.valueOf(63.8030));
		lineItem.setDiscountedUnitPrice(Double.valueOf(63.8030));
		lineItem.setUomName("EA");
		lineItem.setQuantityOrdered(Double.valueOf(1.0000));
		lineItem.setQuantityBackOrdered(Double.valueOf(0.0000));
		lineItem.setQuantityShipped(Double.valueOf(1.0000));
		lineItem.setTotalPrice(Double.valueOf(0.0000));
		lineItemList.add(lineItem);

		response.setDeliveryInformation(deliveryResponse);
		response.setPaymentsInformation(paymentInfoList);
		response.setLineItems(lineItemList);

		final B2BUnitData defaultUnit = new B2BUnitData();
		defaultUnit.setUid("2060119_US");
		final String uid = defaultUnit.getUid().trim().split("[_]")[0].trim();
		final String boomi = "false";

		given(siteOneB2BUnitFacade.getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
		given(siteOneInvoiceWebService.getInvoiceDetailsData(uid, "157812509-001", false)).willReturn(response);
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi);

		Assert.assertNotNull(response);
		Assert.assertEquals(lineItem.getSkuid(), response.getLineItems().get(0).getSkuid());

	}

}
