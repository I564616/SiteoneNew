/**
 *
 */
package com.siteone.facade.order.impl;

import static org.mockito.BDDMockito.given;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.b2b.services.B2BWorkflowIntegrationService;
import de.hybris.platform.b2bacceleratorfacades.order.B2BOrderFacade;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BOrderApprovalData;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.commercefacades.order.data.OrderHistoryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.user.data.SiteOnePOAPaymentInfoData;
import de.hybris.platform.commercefacades.user.data.SiteOnePaymentInfoData;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.order.payment.SiteoneCreditCardPaymentInfoModel;
import de.hybris.platform.core.model.order.payment.SiteonePOAPaymentInfoModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.workflow.model.WorkflowActionModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.siteone.core.customer.SiteOneCustomerAccountService;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.PurchProductAndOrdersModel;
import com.siteone.core.order.services.SiteOneOrderService;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.savedList.SiteoneSavedListFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.integration.open.order.data.OpenOrdersInfoResponseData;
import com.siteone.integration.open.order.data.OpenOrdersLandingPageRequestData;
import com.siteone.integration.open.order.data.OpenOrdersLandingPageResponseData;
import com.siteone.integration.open.order.data.OpenOrdersShipmentInfoResponseData;
import com.siteone.integration.open.order.data.OrderDetailsLineItemsResponseData;
import com.siteone.integration.open.order.data.OrderDetailsOrderHeaderResponseData;
import com.siteone.integration.open.order.data.OrderDetailsOrderPaymentsResponseData;
import com.siteone.integration.open.order.data.OrderDetailsPaymentsResponseData;
import com.siteone.integration.open.order.data.OrderDetailsResponseData;
import com.siteone.integration.open.order.data.SiteoneOrderDetailsResponseData;

import junit.framework.Assert;


/**
 *
 */
@UnitTest
public class DefaultSiteOneOrderFacadeTest
{

	@Mock
	private CustomerAccountService customerAccountService;

	@Mock
	private CartService cartService;

	@Mock
	private SiteOneProductFacade siteOneProductFacade;

	@Mock
	private Converter<PurchProductAndOrdersModel, ProductData> purchasedProductConverter;

	@Mock
	private Converter<OrderModel, OrderHistoryData> orderHistoryConverter;

	@Mock
	SiteoneSavedListFacade siteoneSavedListFacade;

	@Mock
	private ModelService modelService;

	@Mock
	private SiteOneOrderService siteOneOrderService;

	@Mock
	private Converter<SiteoneCreditCardPaymentInfoModel, SiteOnePaymentInfoData> siteOnePaymentInfoDataConverter;

	@Mock
	private Converter<SiteonePOAPaymentInfoModel, SiteOnePOAPaymentInfoData> siteOnePOAPaymentInfoDataConverter;

	@Mock
	private B2BOrderFacade b2BOrderFacade;

	@Mock
	private B2BUnitFacade b2bUnitFacade;

	@Mock
	private SiteOneB2BUnitFacade siteOneB2BUnitFacade;

	@Mock
	private SiteOneCustomerAccountService siteOneCustomerAccountService;

	@Mock
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Mock
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Mock
	private Converter<WorkflowActionModel, B2BOrderApprovalData> b2bOrderApprovalDataConverter;

	@Mock
	private B2BWorkflowIntegrationService b2bWorkflowIntegrationService;

	@Mock
	private DefaultSiteOneOrderFacade defaultSiteOneOrderFacade;


	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		defaultSiteOneOrderFacade = new DefaultSiteOneOrderFacade();
		defaultSiteOneOrderFacade.setCustomerAccountService(customerAccountService);
		defaultSiteOneOrderFacade.setB2bUnitFacade(b2bUnitFacade);
		defaultSiteOneOrderFacade.setSiteOneFeatureSwitchCacheService(siteOneFeatureSwitchCacheService);
	}

	@Test
	public void orderLandingInitialTestCase()
	{
		final OpenOrdersLandingPageRequestData openOrderRequest = new OpenOrdersLandingPageRequestData();
		final OpenOrdersLandingPageResponseData orderResponse = new OpenOrdersLandingPageResponseData();
		final List<OpenOrdersInfoResponseData> listData = new ArrayList<>();
		final OpenOrdersInfoResponseData data = new OpenOrdersInfoResponseData();
		final List<OpenOrdersShipmentInfoResponseData> shipmentListData = new ArrayList<>();
		final OpenOrdersShipmentInfoResponseData shipmentData = new OpenOrdersShipmentInfoResponseData();

		openOrderRequest.setSearchPeriod(Integer.valueOf(90));
		openOrderRequest.setSortBy(Integer.valueOf(0));
		openOrderRequest.setSortDirection(Integer.valueOf(0));
		openOrderRequest.setPage(Integer.valueOf(1));
		openOrderRequest.setRows(Integer.valueOf(25));
		openOrderRequest.setIncludeMobileProOrders(true);

		orderResponse.setTotalRecords("302");
		orderResponse.setPageNumber("1");
		orderResponse.setRowsPerPage("25");
		orderResponse.setTotalPages("13");

		data.setOrderID("32d54bff-e7c0-4453-a102-ab0832c53bc0");
		data.setOrderNumber("M157606479");
		data.setHybrisOrderNumber("265281012");
		data.setPoNumber("Surya test");
		data.setDatePlaced("2025-08-25T07:06:18.133");
		data.setOrderTotal(Double.valueOf(518.52));
		data.setCouponCode(null);
		data.setExternalSystemID("2");
		data.setBranchName("Dallas Ecommerce");
		data.setBranchNumber("8200");

		shipmentData.setShipmentID("97a875f3-b116-4cda-94cc-b9d660cc6c12");
		shipmentData.setShipmentNumber("157606479-001");
		shipmentData.setShipmentType("Store Delivery");
		shipmentData.setTransactionStatus("Shipped");
		shipmentData.setInvoiceNumber(null);
		shipmentData.setShipmentTotal(Double.valueOf(518.52));
		shipmentListData.add(shipmentData);
		data.setShipments(shipmentListData);

		listData.add(data);
		orderResponse.setData(listData);

		final B2BUnitData defaultUnit = new B2BUnitData();
		defaultUnit.setUid("2060119_US");
		final String uid = defaultUnit.getUid().trim().split("[_]")[0].trim();
		final String boomi = "false";

		given(siteOneB2BUnitFacade.getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
		given(siteOneCustomerAccountService.getOpenOrderList(openOrderRequest, uid, Integer.valueOf(1), false))
				.willReturn(orderResponse);
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi);

		Assert.assertNotNull(orderResponse);
		Assert.assertEquals(data.getOrderNumber(), orderResponse.getData().get(0).getOrderNumber());
	}

	@Test
	public void orderLandingOrderNumberSearch()
	{
		final OpenOrdersLandingPageRequestData openOrderRequest = new OpenOrdersLandingPageRequestData();
		final OpenOrdersLandingPageResponseData orderResponse = new OpenOrdersLandingPageResponseData();
		final List<OpenOrdersInfoResponseData> listData = new ArrayList<>();
		final OpenOrdersInfoResponseData data = new OpenOrdersInfoResponseData();
		final List<OpenOrdersShipmentInfoResponseData> shipmentListData = new ArrayList<>();
		final OpenOrdersShipmentInfoResponseData shipmentData = new OpenOrdersShipmentInfoResponseData();

		openOrderRequest.setSearchPeriod(Integer.valueOf(90));
		openOrderRequest.setSortBy(Integer.valueOf(0));
		openOrderRequest.setSortDirection(Integer.valueOf(0));
		openOrderRequest.setOrderNumber("M157835546");
		openOrderRequest.setPage(Integer.valueOf(1));
		openOrderRequest.setRows(Integer.valueOf(25));
		openOrderRequest.setIncludeMobileProOrders(true);

		orderResponse.setTotalRecords("302");
		orderResponse.setPageNumber("1");
		orderResponse.setRowsPerPage("25");
		orderResponse.setTotalPages("13");

		data.setOrderID("32d54bff-e7c0-4453-a102-ab0832c53bc0");
		data.setOrderNumber("M157606479");
		data.setHybrisOrderNumber("265281012");
		data.setPoNumber("Surya test");
		data.setDatePlaced("2025-08-25T07:06:18.133");
		data.setOrderTotal(Double.valueOf(518.52));
		data.setCouponCode(null);
		data.setExternalSystemID("2");
		data.setBranchName("Dallas Ecommerce");
		data.setBranchNumber("8200");

		shipmentData.setShipmentID("97a875f3-b116-4cda-94cc-b9d660cc6c12");
		shipmentData.setShipmentNumber("157606479-001");
		shipmentData.setShipmentType("Store Delivery");
		shipmentData.setTransactionStatus("Shipped");
		shipmentData.setInvoiceNumber(null);
		shipmentData.setShipmentTotal(Double.valueOf(518.52));
		shipmentListData.add(shipmentData);
		data.setShipments(shipmentListData);

		listData.add(data);
		orderResponse.setData(listData);

		final B2BUnitData defaultUnit = new B2BUnitData();
		defaultUnit.setUid("2060119_US");
		final String uid = defaultUnit.getUid().trim().split("[_]")[0].trim();
		final String boomi = "false";

		given(siteOneB2BUnitFacade.getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
		given(siteOneCustomerAccountService.getOpenOrderList(openOrderRequest, "1608411", Integer.valueOf(1), false))
				.willReturn(orderResponse);
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi);

		Assert.assertEquals(data.getOrderNumber(), orderResponse.getData().get(0).getOrderNumber());
		Assert.assertEquals(1, orderResponse.getData().size());
	}

	@Test
	public void orderLandingInvoiceNumberSearch()
	{
		final OpenOrdersLandingPageRequestData openOrderRequest = new OpenOrdersLandingPageRequestData();
		final OpenOrdersLandingPageResponseData orderResponse = new OpenOrdersLandingPageResponseData();
		final List<OpenOrdersInfoResponseData> listData = new ArrayList<>();
		final OpenOrdersInfoResponseData data = new OpenOrdersInfoResponseData();
		final List<OpenOrdersShipmentInfoResponseData> shipmentListData = new ArrayList<>();
		final OpenOrdersShipmentInfoResponseData shipmentData = new OpenOrdersShipmentInfoResponseData();

		openOrderRequest.setSearchPeriod(Integer.valueOf(90));
		openOrderRequest.setSortBy(Integer.valueOf(0));
		openOrderRequest.setSortDirection(Integer.valueOf(0));
		openOrderRequest.setInvoiceNumber("157812509-001");
		openOrderRequest.setPage(Integer.valueOf(1));
		openOrderRequest.setRows(Integer.valueOf(25));
		openOrderRequest.setIncludeMobileProOrders(true);

		orderResponse.setTotalRecords("302");
		orderResponse.setPageNumber("1");
		orderResponse.setRowsPerPage("25");
		orderResponse.setTotalPages("13");

		data.setOrderID("32d54bff-e7c0-4453-a102-ab0832c53bc0");
		data.setOrderNumber("M157606479");
		data.setHybrisOrderNumber("265281012");
		data.setPoNumber("Surya test");
		data.setDatePlaced("2025-08-25T07:06:18.133");
		data.setOrderTotal(Double.valueOf(518.52));
		data.setCouponCode(null);
		data.setExternalSystemID("2");
		data.setBranchName("Dallas Ecommerce");
		data.setBranchNumber("8200");

		shipmentData.setShipmentID("97a875f3-b116-4cda-94cc-b9d660cc6c12");
		shipmentData.setShipmentNumber("157606479-001");
		shipmentData.setShipmentType("Store Delivery");
		shipmentData.setTransactionStatus("Shipped");
		shipmentData.setInvoiceNumber(null);
		shipmentData.setShipmentTotal(Double.valueOf(518.52));
		shipmentListData.add(shipmentData);
		data.setShipments(shipmentListData);

		listData.add(data);
		orderResponse.setData(listData);

		final B2BUnitData defaultUnit = new B2BUnitData();
		defaultUnit.setUid("2060119_US");
		final String uid = defaultUnit.getUid().trim().split("[_]")[0].trim();
		final String boomi = "false";

		given(siteOneB2BUnitFacade.getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
		given(siteOneCustomerAccountService.getOpenOrderList(openOrderRequest, "1608411", Integer.valueOf(1), false))
				.willReturn(orderResponse);
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi);

		Assert.assertEquals(data.getShipments().get(0).getInvoiceNumber(),
				orderResponse.getData().get(0).getShipments().get(0).getInvoiceNumber());
		Assert.assertEquals(1, orderResponse.getData().size());
	}

	@Test
	public void orderLandingPONumberSearch()
	{
		final OpenOrdersLandingPageRequestData openOrderRequest = new OpenOrdersLandingPageRequestData();
		final OpenOrdersLandingPageResponseData orderResponse = new OpenOrdersLandingPageResponseData();
		final List<OpenOrdersInfoResponseData> listData = new ArrayList<>();
		final OpenOrdersInfoResponseData data = new OpenOrdersInfoResponseData();
		final List<OpenOrdersShipmentInfoResponseData> shipmentListData = new ArrayList<>();
		final OpenOrdersShipmentInfoResponseData shipmentData = new OpenOrdersShipmentInfoResponseData();

		openOrderRequest.setSearchPeriod(Integer.valueOf(90));
		openOrderRequest.setSortBy(Integer.valueOf(0));
		openOrderRequest.setSortDirection(Integer.valueOf(0));
		openOrderRequest.setPoNumber("stoney hollow");
		openOrderRequest.setPage(Integer.valueOf(1));
		openOrderRequest.setRows(Integer.valueOf(25));
		openOrderRequest.setIncludeMobileProOrders(true);

		orderResponse.setTotalRecords("302");
		orderResponse.setPageNumber("1");
		orderResponse.setRowsPerPage("25");
		orderResponse.setTotalPages("13");

		data.setOrderID("32d54bff-e7c0-4453-a102-ab0832c53bc0");
		data.setOrderNumber("M157606479");
		data.setHybrisOrderNumber("265281012");
		data.setPoNumber("stoney hollow");
		data.setDatePlaced("2025-08-25T07:06:18.133");
		data.setOrderTotal(Double.valueOf(518.52));
		data.setCouponCode(null);
		data.setExternalSystemID("2");
		data.setBranchName("Dallas Ecommerce");
		data.setBranchNumber("8200");

		shipmentData.setShipmentID("97a875f3-b116-4cda-94cc-b9d660cc6c12");
		shipmentData.setShipmentNumber("157606479-001");
		shipmentData.setShipmentType("Store Delivery");
		shipmentData.setTransactionStatus("Shipped");
		shipmentData.setInvoiceNumber(null);
		shipmentData.setShipmentTotal(Double.valueOf(518.52));
		shipmentListData.add(shipmentData);
		data.setShipments(shipmentListData);

		listData.add(data);
		orderResponse.setData(listData);

		final B2BUnitData defaultUnit = new B2BUnitData();
		defaultUnit.setUid("2060119_US");
		final String uid = defaultUnit.getUid().trim().split("[_]")[0].trim();
		final String boomi = "false";

		given(siteOneB2BUnitFacade.getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
		given(siteOneCustomerAccountService.getOpenOrderList(openOrderRequest, "1608411", Integer.valueOf(1), false))
				.willReturn(orderResponse);
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi);

		Assert.assertEquals(data.getPoNumber(), orderResponse.getData().get(0).getPoNumber());
		Assert.assertEquals(1, orderResponse.getData().size());
	}

	@Test
	public void orderDetailInitialTestCase() throws ParseException
	{
		final SiteoneOrderDetailsResponseData orderDetailResponse = new SiteoneOrderDetailsResponseData();
		final OrderDetailsOrderHeaderResponseData orderHeader = new OrderDetailsOrderHeaderResponseData();
		final List<OrderDetailsOrderPaymentsResponseData> listOrderPayments = new ArrayList<>();
		final OrderDetailsOrderPaymentsResponseData orderPayments = new OrderDetailsOrderPaymentsResponseData();
		final OrderDetailsResponseData details = new OrderDetailsResponseData();
		final List<OrderDetailsPaymentsResponseData> listPayments = new ArrayList<>();
		final OrderDetailsPaymentsResponseData payments = new OrderDetailsPaymentsResponseData();
		final List<OrderDetailsLineItemsResponseData> listLineItems = new ArrayList<>();
		final OrderDetailsLineItemsResponseData lineItems = new OrderDetailsLineItemsResponseData();

		orderHeader.setOrderNumber("M157606479");
		orderHeader.setOrderShipmentNumber("157606479-001");
		orderHeader.setPoNumber("Surya test");
		orderHeader.setDatePlaced("2025-08-25T07:06:18.133");
		orderHeader.setPlacedBy("chad@cblmgmt.com");
		orderHeader.setStatus("Normal Order");
		orderHeader.setOrderTotal(Double.valueOf(518.52));
		orderHeader.setExternalSystemID("2");

		orderPayments.setPaymentID("1f9d6d82-5fec-4e64-95d0-3f3841974738");
		orderPayments.setPaymentMethod("On Account");
		orderPayments.setAmountPaid(Double.valueOf(518.52));
		orderPayments.setLast4CreditCardDigits(null);
		orderPayments.setCreditCardType(null);
		listOrderPayments.add(orderPayments);

		details.setOrderShipmentType("Store Delivery");
		details.setStatus("Shipped");
		details.setContactName("Chad Schnitker");
		details.setContactPhone("2144606773");
		details.setContactEmail("chad@cblmgmt.com");
		details.setSpecialInstructions(null);
		details.setFulfillmentDate("2025-08-25T00:00:00");
		details.setDeliveryPeriod("Any time");
		details.setCarrierName(null);
		details.setDeliveryStatus(null);
		details.setScheduleDate("0001-01-01T00:00:00");
		details.setTrackingNumber(null);
		details.setSubTotal(Double.valueOf(479));
		details.setTaxTotal(Double.valueOf(39.52));
		details.setFreightAmount(Double.valueOf(0));
		details.setShipmentTotal(Double.valueOf(518.52));
		details.setTrackingLink("");
		details.setAddressName("J/City of Gainesville, TX-Fire Station No. 2");
		details.setAddress1("2010 E Broadway Street");
		details.setAddress2(null);
		details.setCounty("Cooke");
		details.setCity("Gainesville");
		details.setStateProvince("TX");
		details.setPostalCode("76240");
		details.setGeoCode("440970000");
		details.setCountry("US");

		payments.setBranchName("Dallas Ecommerce");
		payments.setPaymentMethod(null);
		payments.setAmountPaid(Double.valueOf(0));
		payments.setBillingTerms(null);
		payments.setLast4CreditCardDigits(null);
		payments.setCreditCardType(null);
		payments.setAddressName(null);
		payments.setAddress1("945 Stockton Dr Ste 8110");
		payments.setAddress2(null);
		payments.setCounty("Collin");
		payments.setCity("Allen");
		payments.setStateProvince("TX");
		payments.setPostalCode("76240");
		payments.setGeoCode("440970000");
		payments.setCountry("US");
		listPayments.add(payments);

		lineItems.setLineNumber(Integer.valueOf(1));
		lineItems.setSkuid(Integer.valueOf(769505));
		lineItems.setItemNumber("CRU03Z");
		lineItems.setItemDescription("Makita 40V MAX ConnectX Brushless Cordless String Trimmer (Tool Only)");
		lineItems.setUnitPrice(Double.valueOf(479));
		lineItems.setOriginalUnitPrice(Double.valueOf(479));
		lineItems.setDiscountedUnitPrice(Double.valueOf(479));
		lineItems.setUomName("EA");
		lineItems.setQuantityOrdered(Double.valueOf(1));
		lineItems.setQuantityCanceled(Double.valueOf(0));
		lineItems.setQuantityBackOrdered(Double.valueOf(0));
		lineItems.setQuantityShipped(Double.valueOf(0));
		lineItems.setTotalPrice(Double.valueOf(479));
		listLineItems.add(lineItems);


		orderDetailResponse.setOrderHeader(orderHeader);
		orderDetailResponse.setOrderPayments(listOrderPayments);
		orderDetailResponse.setDetails(details);
		orderDetailResponse.setPayments(listPayments);
		orderDetailResponse.setLineItems(listLineItems);

		final B2BUnitData defaultUnit = new B2BUnitData();
		defaultUnit.setUid("2060119_US");
		final String uid = defaultUnit.getUid().trim().split("[_]")[0].trim();
		final String boomi = "false";

		given(siteOneB2BUnitFacade.getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
		given(siteOneCustomerAccountService.getOrderDetailsData(uid, "157606479-001", false)).willReturn(orderDetailResponse);
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi);

		Assert.assertNotNull(orderDetailResponse);
		Assert.assertEquals(orderHeader.getOrderNumber(), orderDetailResponse.getOrderHeader().getOrderNumber());
	}

}
