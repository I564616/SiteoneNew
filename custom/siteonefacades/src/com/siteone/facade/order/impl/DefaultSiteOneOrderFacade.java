/**
 *
 */
package com.siteone.facade.order.impl;



import de.hybris.platform.b2b.process.approval.model.B2BApprovalProcessModel;
import de.hybris.platform.b2b.services.B2BWorkflowIntegrationService;
import de.hybris.platform.b2bacceleratorfacades.exception.PrincipalAssignedValidationException;
import de.hybris.platform.b2bacceleratorfacades.order.B2BOrderFacade;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BOrderApprovalData;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.commercefacades.order.data.ConsignmentData;
import de.hybris.platform.commercefacades.order.data.ConsignmentEntryData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.order.data.OrderHistoryData;
import de.hybris.platform.commercefacades.order.data.PromotionOrderEntryConsumedData;
import de.hybris.platform.commercefacades.order.impl.DefaultOrderFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.PromotionResultData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.commercefacades.user.data.SiteOnePOAPaymentInfoData;
import de.hybris.platform.commercefacades.user.data.SiteOnePaymentInfoData;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.commerceservices.enums.CustomerType;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.SortData;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.core.GenericSearchConstants.LOG;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.enums.PhoneContactInfoType;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.order.payment.SiteoneCreditCardPaymentInfoModel;
import de.hybris.platform.core.model.order.payment.SiteonePOAPaymentInfoModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.PhoneContactInfoModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.ordersplitting.model.ConsignmentEntryModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.ordersplitting.model.ConsignmentProcessModel;
import de.hybris.platform.processengine.enums.ProcessState;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.util.Config;
import de.hybris.platform.workflow.enums.WorkflowActionStatus;
import de.hybris.platform.workflow.enums.WorkflowActionType;
import de.hybris.platform.workflow.model.WorkflowActionModel;
import de.hybris.platform.basecommerce.enums.ConsignmentStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.PredicateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.Model;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.customer.SiteOneCustomerAccountService;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.PurchProductAndOrdersModel;
import com.siteone.core.order.services.SiteOneOrderService;
import com.siteone.facade.MasterHybrisOrderData;
import com.siteone.facade.order.SiteOneOrderFacade;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.savedList.SiteoneSavedListFacade;
import com.siteone.facades.savedList.data.SavedListData;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.integration.open.order.data.OpenOrdersInfoResponseData;
import com.siteone.integration.open.order.data.OpenOrdersLandingPageResponseData;
import com.siteone.integration.open.order.data.OpenOrdersShipmentInfoResponseData;
import com.siteone.integration.open.order.data.OrderDetailsLineItemsResponseData;
import com.siteone.integration.open.order.data.OrderDetailsOrderPaymentsResponseData;
import com.siteone.integration.open.order.data.OrderDetailsPaymentsResponseData;
import com.siteone.integration.open.order.data.OpenOrdersLandingPageRequestData;
import com.siteone.integration.open.order.data.SiteoneOrderDetailsResponseData;


/**
 * @author 1190626
 *
 */

public class DefaultSiteOneOrderFacade extends DefaultOrderFacade implements SiteOneOrderFacade
{
	private static final String ORDER_NOT_FOUND_FOR_USER_AND_BASE_STORE = "Order with guid %s not found for current user in current BaseStore";
	private static final String DC_SHIPPING_FEE_BRANCHES = "DCShippingFeeBranches";
	private static final String PRICE_REGEX = "[^\\P{Punct}-.]+";
	private static final String DOLLAR = "$";
	private static final String PIPE = " | ";
	private static final String PENDING_APPROVAL = "pending.approval";
	private static final String APPROVAL = "APPROVAL";
	private static final String PENDING = "PENDING";
	private static final String BOOMI_PLATFORM = "boomiPlatformLinux";
	private static final String[] ordersSortData =
	{ "byDate|Date", "byOrderNumber|Order number", "byInvoiceNumber|Invoice number", "byPONumber|PO number",
			"byAmount|Order Total" };
	private static final String[] ordersFilterData =
	{ "byLast30Days|Last 30 days", "byLast60Days|Last 60 days", "byLast90Days|Last 90 days", "byLast184days|Last 184 days",
			"byLast365Days|Last year", "byAllOrders|All orders" };
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneOrderFacade.class);

	@Resource(name = "customerAccountService")
	private CustomerAccountService customerAccountService;
	@Resource(name = "cartService")
	private CartService cartService;
	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;
	private Converter<PurchProductAndOrdersModel, ProductData> purchasedProductConverter;

	private Converter<OrderModel, OrderHistoryData> orderHistoryConverter;
	@Resource(name = "siteoneSavedListFacade")
	SiteoneSavedListFacade siteoneSavedListFacade;
	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "siteOneOrderService")
	private SiteOneOrderService siteOneOrderService;

	@Resource(name = "siteOnePaymentInfoDataConverter")
	private Converter<SiteoneCreditCardPaymentInfoModel, SiteOnePaymentInfoData> siteOnePaymentInfoDataConverter;

	@Resource(name = "siteOnePOAPaymentInfoDataConverter")
	private Converter<SiteonePOAPaymentInfoModel, SiteOnePOAPaymentInfoData> siteOnePOAPaymentInfoDataConverter;

	@Resource(name = "b2bOrderFacade")
	private B2BOrderFacade b2BOrderFacade;
	
	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
	
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	private Converter<WorkflowActionModel, B2BOrderApprovalData> b2bOrderApprovalDataConverter;
	private B2BWorkflowIntegrationService b2bWorkflowIntegrationService;


	/**
	 * @return the orderHistoryConverter
	 */
	@Override
	public Converter<OrderModel, OrderHistoryData> getOrderHistoryConverter()
	{
		return orderHistoryConverter;
	}

	/**
	 * @param orderHistoryConverter
	 *           the orderHistoryConverter to set
	 */
	@Override
	public void setOrderHistoryConverter(final Converter<OrderModel, OrderHistoryData> orderHistoryConverter)
	{
		this.orderHistoryConverter = orderHistoryConverter;
	}

	public Converter<PurchProductAndOrdersModel, ProductData> getPurchasedProductConverter()
	{
		return purchasedProductConverter;
	}

	public void setPurchasedProductConverter(final Converter<PurchProductAndOrdersModel, ProductData> purchasedProductConverter)
	{
		this.purchasedProductConverter = purchasedProductConverter;
	}

	@Override
	public OrderData getOrderDetailsForCode(final String code)
	{
		final BaseStoreModel baseStoreModel = getBaseStoreService().getCurrentBaseStore();
		final OrderModel order = getCustomerAccountService().getOrderForCode(code, baseStoreModel);
		OrderModel orderModel = null;
		if (getCheckoutCustomerStrategy().isAnonymousCheckout())
		{
			orderModel = getCustomerAccountService().getOrderDetailsForGUID(code, baseStoreModel);
		}
		if (null != order.getUser() && CustomerType.GUEST.equals(((CustomerModel) order.getUser()).getType()))
		{
			orderModel = order;
		}
		else
		{
			try
			{
				orderModel = getCustomerAccountService().getOrderForCode((CustomerModel) getUserService().getCurrentUser(), code,
						baseStoreModel);
			}
			catch (final ModelNotFoundException e)
			{
				throw new UnknownIdentifierException(String.format(ORDER_NOT_FOUND_FOR_USER_AND_BASE_STORE, code));
			}
		}

		if (orderModel == null)
		{
			throw new UnknownIdentifierException(String.format(ORDER_NOT_FOUND_FOR_USER_AND_BASE_STORE, code));
		}
		return getOrderConverter().convert(orderModel);
	}



	@Override
	public OrderData getRecentOrderForAccount(final String unitId)
	{
		OrderModel orderModel = null;
		OrderData orderdata = null;

		orderModel = ((SiteOneCustomerAccountService) customerAccountService).getRecentOrderForAccount(unitId);

		if (null != orderModel)
		{
			orderdata = getOrderConverter().convert(orderModel);
		}

		return orderdata;
	}
	
	@Override
	public List<OrderData> fetchRecentOrdersFromAPI(final String bUnit)
	{
      final OpenOrdersLandingPageRequestData openOrderRequest = new OpenOrdersLandingPageRequestData();
	   
   	openOrderRequest.setSortBy(Integer.valueOf("0"));
   	openOrderRequest.setSearchPeriod(Integer.valueOf("365"));		
		openOrderRequest.setSortDirection(Integer.valueOf("0"));
		openOrderRequest.setPage(Integer.valueOf("1"));
		openOrderRequest.setRows(Integer.valueOf("5"));
		openOrderRequest.setIncludeMobileProOrders(Boolean.TRUE);
		final OpenOrdersLandingPageResponseData orderResponse = ((SiteOneCustomerAccountService) customerAccountService)
				.getOpenOrderList(openOrderRequest, getCustomerNo(bUnit), getDivisionId(bUnit), Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));

		final List<OrderData> openOrders= populateRecentOrders(orderResponse, bUnit);
		final List<OrderData> heldOrders= getHeldOrdersFoRecentOrders(bUnit);
		if(heldOrders != null && !CollectionUtils.isEmpty(heldOrders))
		{
			openOrders.addAll(0, heldOrders);
			if(openOrders.size() > 5)
			{
				openOrders.subList(5, openOrders.size()).clear();
			}
		}
		return openOrders;
	}
	
	private List<OrderData> getHeldOrdersFoRecentOrders(String unitId)
	{
		List<OrderModel> orderModels = ((SiteOneCustomerAccountService) customerAccountService).getHeldOrdersForUnit(unitId);
		List<OrderData> heldOrders = populateHeldOrdersForRecentOrders(orderModels);
		return heldOrders;
	}
	
	private List<OrderData> populateHeldOrdersForRecentOrders(List<OrderModel> orderModels)
	{
		final List<OrderData> heldOrders = new ArrayList();
		int CURRENCY_TOTAL_PRICE_DIGITS = Config.getInt("curency.totalprice.digits", 2);
		if(orderModels != null)
		{
			for(final OrderModel orderModel : orderModels)
			{
				if(orderModel != null)
				{
					final OrderData orderData = new OrderData();
					final List<ConsignmentData> listConsData = new ArrayList<>();
					final ConsignmentData consData = new ConsignmentData();
					orderData.setIsHybrisOrder(Boolean.TRUE);
					orderData.setStatusDisplay(PENDING);
					consData.setStatusDisplay(PENDING);
					if(orderModel.getCode() != null)
					{
						orderData.setCode(PENDING);
						orderData.setShipmentNumber("MH".concat(orderModel.getCode()));
						consData.setCode("MH".concat(orderModel.getCode()));
					}
					if(orderModel.getPurchaseOrderNumber() != null)
					{
						orderData.setPurchaseOrderNumber(orderModel.getPurchaseOrderNumber());
					}
					if(orderModel.getCreationtime() != null)
					{
						orderData.setRequestedDate(getFormattedDateForHeldOrder(orderModel.getCreationtime().toString()));
					}
					if(orderModel.getPointOfService() != null)
					{
						orderData.setBranchNumber(orderModel.getPointOfService().getStoreId());
					}
					if(orderModel.getConsignments() != null)
					{
						Iterator<ConsignmentModel> i = orderModel.getConsignments().iterator();
						while(i.hasNext())
						{
							ConsignmentModel cons = i.next();
							if(cons.getConsignmentProcesses() != null)
							{
								Collection<ConsignmentProcessModel> conPs = cons.getConsignmentProcesses();
								if(conPs != null)
								{
									for(final ConsignmentProcessModel conp : conPs)
									{
										if(conp.getState() != null && conp.getState().equals(ProcessState.RUNNING))
										{
											if(cons.getTotal() != null)
											{
												final PriceData totalPrice = new PriceData();
										   	totalPrice.setValue(BigDecimal.valueOf(Double.parseDouble(cons.getTotal())).setScale(2,RoundingMode.HALF_UP));
										   	totalPrice.setFormattedValue(truncatePrice(Double.parseDouble(cons.getTotal()), CURRENCY_TOTAL_PRICE_DIGITS));								   	orderData.setTotalPrice(totalPrice);
										   	orderData.setTotalPriceWithTax(totalPrice);
										   	consData.setTotal(String.format("%.2f", Double.parseDouble(cons.getTotal())));
											}
											if(cons.getConsignmentEntries() != null && cons.getConsignmentEntries().size() > 0)
											{
												orderData.setItemCount(cons.getConsignmentEntries().size());
											}
											if(cons.getDeliveryMode() != null)
											{
												orderData.setOrderType(getOrderType(cons.getDeliveryMode().getCode()));
											}
											break;
										}
									}
								}
							}
						}
					}
					if(orderModel.getContactPerson() != null && orderModel.getContactPerson().getName() != null)
					{
						orderData.setPlacedBy(orderModel.getContactPerson().getName());
					}
					listConsData.add(consData);
					orderData.setConsignments(listConsData);
					heldOrders.add(orderData);
				}
			}
		}
		return heldOrders;
	}

	private List<OrderData> populateRecentOrders(final OpenOrdersLandingPageResponseData orderResponse, String bUnit)
	{
		final List<OrderData> orderData = new ArrayList<>();
		int CURRENCY_TOTAL_PRICE_DIGITS = Config.getInt("curency.totalprice.digits", 2);
		if (null != orderResponse)
		{
			if (CollectionUtils.isNotEmpty(orderResponse.getData()))
			{
				for (final OpenOrdersInfoResponseData data : orderResponse.getData())
				{
					final OrderData orders = new OrderData();
					List<ConsignmentData> listConsData = new ArrayList<>();
					if (data.getOrderNumber() != null)
					{
						orders.setCode(data.getOrderNumber());
					}
				   if (data.getPoNumber() != null)
				   {
				   	orders.setPurchaseOrderNumber(data.getPoNumber());
				   }
				   if (data.getDatePlaced() != null)
				   {
				   	orders.setRequestedDate(getFormattedDate(data.getDatePlaced()));
				   }
				   if (data.getBranchNumber() != null)
				   {
				   	orders.setBranchNumber(data.getBranchNumber());
				   }
				   if (data.getOrderTotal() != null)
				   {
				   	final PriceData totalPrice = new PriceData();
				   	totalPrice.setValue(BigDecimal.valueOf(data.getOrderTotal().doubleValue()).setScale(2,RoundingMode.HALF_UP));
				   	totalPrice.setFormattedValue(truncatePrice(data.getOrderTotal().doubleValue(), CURRENCY_TOTAL_PRICE_DIGITS));
				   	orders.setTotalPrice(totalPrice);
				   	orders.setTotalPriceWithTax(totalPrice);
				   }
				   SiteoneOrderDetailsResponseData detailResponse =  populateTrackingLink(data, bUnit);
				   for (final OpenOrdersShipmentInfoResponseData shipmentData : data.getShipments())
					{
				   	if (shipmentData != null)
				   	{
				   		if (shipmentData.getShipmentNumber() != null)
					   	{
				   			orders.setShipmentNumber(shipmentData.getShipmentNumber());
					   		SiteoneOrderDetailsResponseData allDetailResp = getDetailsResponse(shipmentData.getShipmentNumber(), bUnit);
						   	if (allDetailResp != null)
						   	{
						   		if (allDetailResp.getDetails() != null && StringUtils.isNotBlank(allDetailResp.getDetails().getContactName()))
						   		{
						   			orders.setPlacedBy(allDetailResp.getDetails().getContactName());
						   		}
						   		if (allDetailResp.getLineItems() != null)
						   		{
						   			orders.setItemCount(Integer.valueOf(allDetailResp.getLineItems().size()));
						   		}
						   	}
					   	}
					   	ConsignmentData consData = new ConsignmentData();
					   	if (shipmentData.getShipmentType() != null)
					   	{
					   		String type = shipmentData.getShipmentType();
							   String orderType = getOrderType(type);
					   		orders.setOrderType(orderType);
					   	}
					   	if (shipmentData.getTransactionStatus() != null)
					   	{
					   		String tranStatus = shipmentData.getTransactionStatus();
								String statusDisplay = getStatusDisplay(tranStatus);
					   		orders.setStatusDisplay(statusDisplay);
					   		consData.setStatusDisplay(statusDisplay);
					   	}
					   	if (shipmentData.getShipmentNumber() != null)
					   	{
					   		consData.setCode(shipmentData.getShipmentNumber());
					   	}
					   	if (shipmentData.getShipmentTotal() != null)
					   	{
					   		consData.setTotal(String.format("%.2f", shipmentData.getShipmentTotal()));
					   	}
					   	final List<URL> trackingUrl = new ArrayList<URL>();
					   	if (detailResponse != null && detailResponse.getDetails() != null && StringUtils.isNotBlank(detailResponse.getDetails().getTrackingLink()))
					   	{
								try
								{
									trackingUrl.add(URI.create(detailResponse.getDetails().getTrackingLink()).toURL());
									LOG.info("Tracking URL is "+ trackingUrl);
								}
								catch (final MalformedURLException e)
								{
									LOG.error("Invalid Tracking URL", e);
								}
								consData.setTrackingUrl(trackingUrl.get(0));
							}
							else
							{
								consData.setTrackingUrl(null);
							}
					   	listConsData.add(consData);
				   	}
					}
				   orders.setConsignments(listConsData);
				   orderData.add(orders);
				}
			}
		}
		return orderData;
	}
	
	private SiteoneOrderDetailsResponseData getDetailsResponse(final String code, final String bUnit)
	{
		final SiteoneOrderDetailsResponseData detailResponse = ((SiteOneCustomerAccountService) customerAccountService).getOrderDetailsData(getCustomerNo(bUnit),
				code, Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
		return detailResponse;
	}



	@Override
	public SearchPageData<ProductData> getPagedPurchasedProduct(final PageableData pageableData, final String unitId)
	{
		final SearchPageData<PurchProductAndOrdersModel> purchasedProducts = ((SiteOneCustomerAccountService) customerAccountService)
				.getPagedPurchasedOrder(pageableData, unitId);
		return convertPageData(purchasedProducts, getPurchasedProductConverter());
	}

	@Override
	protected <S, T> SearchPageData<T> convertPageData(final SearchPageData<S> source, final Converter<S, T> converter)
	{
		final SearchPageData<T> result = new SearchPageData<T>();
		result.setPagination(source.getPagination());
		result.setSorts(source.getSorts());
		result.setResults(Converters.convertAll(source.getResults(), converter));
		return result;
	}


	@Override
	public SearchPageData<OrderHistoryData> getPagedOrderHistoryForStatuses(final PageableData pageableData, final int page, final String unitId,
			final String trimmedSearchParam, final String oSearchParam, final String iSearchParam, final String pnSearchParam,
			final String dateSort, final String paymentType, final String sortOrder,final String sortCode, final OrderStatus... statuses)
	{
		List<OrderHistoryData> heldOrdersData = new ArrayList();
		if(page == 0 && (oSearchParam == null || oSearchParam.isBlank())
				&& (iSearchParam == null || iSearchParam.isBlank()) && (pnSearchParam == null || pnSearchParam.isBlank())
				&& (!baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase("siteone-ca")))
		{
			heldOrdersData = getHeldOrdersForLandingPage(unitId);
		}

		final SearchPageData<OrderHistoryData> searchPageData = getOpenOrdersLandingPage(pageableData, unitId, trimmedSearchParam,
				oSearchParam, iSearchParam, pnSearchParam, dateSort, null,sortOrder, sortCode, statuses);
		
		final List<OrderHistoryData> ordersFromApi = searchPageData.getResults();
		final List<OrderHistoryData> ordersToDisplay = new ArrayList();
		
		if(!CollectionUtils.isEmpty(heldOrdersData))
		{
			ordersToDisplay.addAll(heldOrdersData);
			if(!CollectionUtils.isEmpty(ordersFromApi))
			{
				ordersToDisplay.addAll(ordersFromApi);
			}
			searchPageData.setResults(ordersToDisplay);
			
		}
		return searchPageData;
	}

	private List<OrderHistoryData> getHeldOrdersForLandingPage(String unitId)
	{
		List<OrderModel> orderModels = ((SiteOneCustomerAccountService) customerAccountService).getHeldOrdersForUnit(unitId);
		List<OrderHistoryData> heldOrders = populateHeldOrders(orderModels);
		return heldOrders;
	}

	private String getOpenOrdersSortCode(final String sortCode)
	{
		String sp = new String();

		if (StringUtils.isBlank(sortCode))
		{
			sp = "90";
		}
		else if (StringUtils.isNotBlank(sortCode) && sortCode.equalsIgnoreCase("byLast30Days"))
		{
			sp = "30";
		}
		else if (StringUtils.isNotBlank(sortCode) && sortCode.equalsIgnoreCase("byLast60Days"))
		{
			sp = "60";
		}
		else if (StringUtils.isNotBlank(sortCode) && sortCode.equalsIgnoreCase("byLast90Days"))
		{
			sp = "90";
		}
		else if (StringUtils.isNotBlank(sortCode) && sortCode.equalsIgnoreCase("byLast184days"))
		{
			sp = "180";
		}
		else if (StringUtils.isNotBlank(sortCode) && sortCode.equalsIgnoreCase("byLast365Days"))
		{
			sp = "365";
		}
		else if (StringUtils.isNotBlank(sortCode) && sortCode.equalsIgnoreCase("byAllOrders"))
		{
			sp = "730";
		}
		return sp;
	}

	private String getOpenHistoryDateSort(final String dateSort)
	{
		String ds = new String();

		if (StringUtils.isBlank(dateSort))
		{
			ds = "90";
		}
		else if (StringUtils.isNotBlank(dateSort) && dateSort.equalsIgnoreCase("by30Days"))
		{
			ds = "30";
		}
		else if (StringUtils.isNotBlank(dateSort) && dateSort.equalsIgnoreCase("by60Days"))
		{
			ds = "60";
		}
		else if (StringUtils.isNotBlank(dateSort) && dateSort.equalsIgnoreCase("by90Days"))
		{
			ds = "90";
		}
		else if (StringUtils.isNotBlank(dateSort) && dateSort.equalsIgnoreCase("by184Days"))
		{
			ds = "180";
		}
		else if (StringUtils.isNotBlank(dateSort) && dateSort.equalsIgnoreCase("by365Days"))
		{
			ds = "365";
		}
		else if (StringUtils.isNotBlank(dateSort) && dateSort.equalsIgnoreCase("by730Days"))
		{
			ds = "730";
		}
		return ds;
	}

	private String getStatusDisplay(final String status)
	{
		String statusDisplay = new String();
		if (status.equalsIgnoreCase("Open"))
		{
			statusDisplay = "open";
		}
		else if (status.equalsIgnoreCase("Shipped"))
		{
			statusDisplay = "SHIPPED";
		}
		else if (status.equalsIgnoreCase("Received"))
		{
			statusDisplay = "completed";
		}
		else if (status.equalsIgnoreCase("Invoiced"))
		{
			statusDisplay = "INVOICED";
		}
		else if (status.equalsIgnoreCase("Cancelled"))
		{
			statusDisplay = "cancelled";
		}
		else if (status.equalsIgnoreCase("Closed"))
		{
			statusDisplay = "closed";
		}
		return statusDisplay;
	}

	private String getDeliveryMode(final String type)
	{
		String orderType = new String();
		if (type.equalsIgnoreCase("pickup"))
		{
			orderType = "PICKUP";
		}
		else
		{
			orderType = "DELIVERY";
		}
		return orderType;
	}
	
	private String getOrderType(final String type)
	{
		String orderType = new String();
		if (type.equalsIgnoreCase("Future Pick-up"))
		{
			orderType = "FUTURE_PICKUP";
		}
		else if (type.equalsIgnoreCase("Direct ship"))
		{
			orderType = "DIRECT_SHIP";
		}
		else if (type.equalsIgnoreCase("Pick-up"))
		{
			orderType = "PICKUP";
		}
		else if (type.equalsIgnoreCase("Store Delivery"))
		{
			orderType = "STORE_DELIVERY";
		}
		else if (type.equalsIgnoreCase("Shipping"))
		{
			orderType = "SHIPPING";
		}
		else if (type.equalsIgnoreCase("pickup"))
		{
			orderType = "FUTURE_PICKUP";
		}
		else
		{
			orderType = "DELIVERY";
		}
		return orderType;
	}

	@Override
	public SearchPageData<OrderHistoryData> getOpenOrdersLandingPage(final PageableData pageableData, final String unitId,
			final String trimmedSearchParam, final String oSearchParam, final String iSearchParam, final String pnSearchParam,
			final String dateSort, final String paymentType,final String sortOrder, final String sortCode, final OrderStatus... statuses)
	{
		final OpenOrdersLandingPageRequestData openOrderRequest = new OpenOrdersLandingPageRequestData();

		openOrderRequest.setSortBy(Integer.valueOf("0"));
		openOrderRequest.setOrderStatus(Integer.valueOf("0"));
		openOrderRequest.setSearchPeriod(Integer.valueOf(getOpenHistoryDateSort(dateSort)));
		if (StringUtils.isBlank(sortCode) || sortCode.equalsIgnoreCase("byDate"))
		{
			openOrderRequest.setSortBy(Integer.valueOf("0"));
		}
		else if (sortCode.equalsIgnoreCase("byOrderNumber"))
		{
			openOrderRequest.setSortBy(Integer.valueOf("1"));
		}
		else if (sortCode.equalsIgnoreCase("byInvoiceNumber"))
		{
			openOrderRequest.setSortBy(Integer.valueOf("2"));
		}
		else if (sortCode.equalsIgnoreCase("byPONumber"))
		{
			openOrderRequest.setSortBy(Integer.valueOf("3"));
		}
		else if (sortCode.equalsIgnoreCase("byAmount"))
		{
			openOrderRequest.setSortBy(Integer.valueOf("4"));
		}

		if (StringUtils.isNotBlank(oSearchParam))
		{
			openOrderRequest.setOrderNumber(oSearchParam);
		}
		if (StringUtils.isNotBlank(iSearchParam))
		{
			openOrderRequest.setInvoiceNumber(iSearchParam);
		}
		if (StringUtils.isNotBlank(pnSearchParam))
		{
			openOrderRequest.setPoNumber(pnSearchParam);
		}

		if(StringUtils.isNotBlank(sortOrder))
		{
		openOrderRequest.setSortDirection((sortOrder.equalsIgnoreCase("asc")? Integer.valueOf("1"): Integer.valueOf("0")));
		}
		else
		{
		openOrderRequest.setSortDirection(Integer.valueOf("0"));
		}
		openOrderRequest.setPage(Integer.valueOf(pageableData.getCurrentPage() + 1));
		openOrderRequest.setRows(Integer.valueOf(25));
		openOrderRequest.setIncludeMobileProOrders(Boolean.TRUE);
		
		B2BUnitData bUnit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
		String bUnitPrefix = null;
		String cusNumberPrefix = null;
		SearchPageData<OrderHistoryData> openOrders = createEmptySearchPageData();
		if (bUnit != null && bUnit.getUid() != null)
		{
			bUnitPrefix = bUnit.getUid().trim().split("[-_]")[0].trim();
		}
		if (unitId != null)
		{
			cusNumberPrefix = unitId.trim().split("[-_]")[0].trim();
		}
		if (StringUtils.isNotBlank(bUnitPrefix) && StringUtils.isNotBlank(cusNumberPrefix) && bUnitPrefix.equalsIgnoreCase(cusNumberPrefix))
		{
			final OpenOrdersLandingPageResponseData orderResponse = ((SiteOneCustomerAccountService) customerAccountService)
					.getOpenOrderList(openOrderRequest, getCustomerNo(unitId), getDivisionId(unitId),
							Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));

			openOrders = populateOpenOrders(orderResponse, sortCode, unitId);
		}
		return openOrders;
	}
	
	public SearchPageData<OrderHistoryData> createEmptySearchPageData()
	{
		SearchPageData<OrderHistoryData> data = new SearchPageData<>();
		final PaginationData pagination = new PaginationData();
		pagination.setCurrentPage(0);
		pagination.setNumberOfPages(0);
		pagination.setPageSize(0);
		pagination.setSort("");
		pagination.setTotalNumberOfResults(0);
		data.setResults(Collections.emptyList());
		data.setPagination(pagination);
		data.setSorts(getOrdersFilterData());
		data.setSorts(getOrdersSortData());
		return data;
	}

	public String getCustomerNo(final String customerNumber)
	{

		if (customerNumber.contains("_US"))
		{
			return customerNumber.replace("_US", "");
		}
		else
		{
			return customerNumber.replace("_CA", "");
		}
	}

	public Integer getDivisionId(final String accountNumber)
	{

		if (accountNumber.contains("_US"))
		{
			return Integer.valueOf(1);
		}
		else
		{
			return Integer.valueOf(2);
		}
	}

	private SiteoneOrderDetailsResponseData populateTrackingLink(final OpenOrdersInfoResponseData data, final String unitId)
	{
		if (data.getShipments() != null && !data.getShipments().isEmpty() && data.getShipments().size() == 1)
		{
			final OpenOrdersShipmentInfoResponseData shipmentData = data.getShipments().get(0);
			if (shipmentData != null && shipmentData.getShipmentType() != null && shipmentData.getShipmentNumber() != null
					&& (shipmentData.getShipmentType().equalsIgnoreCase("Direct ship")
							|| shipmentData.getShipmentType().equalsIgnoreCase("Store Delivery")))
			{
				final SiteoneOrderDetailsResponseData detailResponse = ((SiteOneCustomerAccountService) customerAccountService)
						.getOrderDetailsData(getCustomerNo(unitId), shipmentData.getShipmentNumber(),
								Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
				return detailResponse;
			}
			else
			{
				return null;
			}
		}
		return null;
	}
	
	private List<OrderHistoryData> populateHeldOrders(List<OrderModel> orderModels)
	{
		final List<OrderHistoryData> heldOrders = new ArrayList();
		if(orderModels != null)
		{
			for(final OrderModel orderModel : orderModels)
			{
				if(orderModel != null)
				{
					final OrderHistoryData orderData = new OrderHistoryData();
					final List<ConsignmentData> listConsData = new ArrayList<>();
					final ConsignmentData consData = new ConsignmentData();
					orderData.setIsHybrisOrder(Boolean.TRUE);
					orderData.setStatusDisplay(PENDING);
					consData.setStatusDisplay(PENDING);
					if(orderModel.getCode() != null)
					{
						orderData.setCode(PENDING);
						consData.setCode(PENDING);
						orderData.setShipmentNumber("MH".concat(orderModel.getCode()));
						consData.setShipmentNumber("MH".concat(orderModel.getCode()));
					}
					if(orderModel.getPurchaseOrderNumber() != null)
					{
						orderData.setPurchaseOrderNumber(orderModel.getPurchaseOrderNumber());
					}
					if(orderModel.getCreationtime() != null)
					{
						orderData.setPlaced(getFormattedDateForHeldOrder(orderModel.getCreationtime().toString()));
					}
					if(orderModel.getPointOfService() != null)
					{
						orderData.setBranchNumber(orderModel.getPointOfService().getStoreId());
					}
					if(orderModel.getConsignments() != null)
					{
						Iterator<ConsignmentModel> i = orderModel.getConsignments().iterator();
						while(i.hasNext())
						{
							ConsignmentModel cons = i.next();
							if(cons.getConsignmentProcesses() != null)
							{
								Collection<ConsignmentProcessModel> conPs = cons.getConsignmentProcesses();
								if(conPs != null)
								{
									for(final ConsignmentProcessModel conp : conPs)
									{
										if(conp.getState() != null && conp.getState().equals(ProcessState.RUNNING))
										{
											if(cons.getTotal() != null)
											{
												final PriceData totalPrice = new PriceData();
										   	totalPrice.setValue(BigDecimal.valueOf(Double.parseDouble(cons.getTotal())).setScale(2,RoundingMode.HALF_UP));
										   	totalPrice.setFormattedValue(BigDecimal.valueOf(Double.parseDouble(cons.getTotal())).setScale(2,RoundingMode.HALF_UP).toString());
										   	orderData.setTotal(totalPrice);
										   	orderData.setTotalPriceWithTax(totalPrice);
										   	consData.setTotal(String.format("%.2f", Double.parseDouble(cons.getTotal())));
											}
											if(cons.getDeliveryMode() != null)
											{
												orderData.setOrderType(getOrderType(cons.getDeliveryMode().getCode()));
											}
											if(cons.getDeliveryPointOfService() != null && cons.getDeliveryPointOfService().getStoreId() != null)
											{
												orderData.setBranchNumber(cons.getDeliveryPointOfService().getStoreId());
											}
											break;
										}
									}
								}
							}
						}
					}
					listConsData.add(consData);
					orderData.setConsignments(listConsData);
					heldOrders.add(orderData);
				}
			}
		}
		return heldOrders;
	}

	private SearchPageData<OrderHistoryData> populateOpenOrders(final OpenOrdersLandingPageResponseData orderResponse,
			final String sortCode, final String unitId)
	{
		final List<OrderHistoryData> opOrders = new ArrayList<>();
		final SearchPageData<OrderHistoryData> searchPageData = new SearchPageData<OrderHistoryData>();
		final PaginationData pagination = new PaginationData();
		if (null != orderResponse)
		{
			pagination.setNumberOfPages(Integer.parseInt(orderResponse.getTotalPages()));
			pagination.setTotalNumberOfResults(Long.valueOf(orderResponse.getTotalRecords()));
			pagination.setPageSize(Integer.parseInt(orderResponse.getPageNumber()));
			pagination.setCurrentPage(Integer.parseInt(orderResponse.getPageNumber()) - 1);
			pagination.setSort(sortCode);
			if (CollectionUtils.isNotEmpty(orderResponse.getData()))
			{
				for (final OpenOrdersInfoResponseData data : orderResponse.getData())
				{
					final OrderHistoryData orders = new OrderHistoryData();
					final List<ConsignmentData> listConsData = new ArrayList<>();
					if (data.getOrderNumber() != null && data.getOrderNumber().startsWith("M"))
					{
						orders.setIsHybrisOrder(Boolean.FALSE);
					}
					else
					{
						orders.setIsHybrisOrder(Boolean.TRUE);
					}
					if (data.getHybrisOrderNumber() != null)
					{
					orders.setHybrisOrderNumber(data.getHybrisOrderNumber());
					}
					if (data.getOrderNumber() != null)
					{
					orders.setCode(data.getOrderNumber());
					}
				   if (data.getPoNumber() != null)
				   {
				   	orders.setPurchaseOrderNumber(data.getPoNumber());
				   }
				   if (data.getDatePlaced() != null)
				   {
				   	orders.setPlaced(getFormattedDate(data.getDatePlaced()));
				   }
				   if (data.getBranchNumber() != null)
				   {
				   	orders.setBranchNumber(data.getBranchNumber());
				   }
				   if (data.getOrderTotal() != null)
				   {
				   	final PriceData totalPrice = new PriceData();
				   	totalPrice.setValue(BigDecimal.valueOf(data.getOrderTotal().doubleValue()).setScale(2,RoundingMode.HALF_UP));
				   	totalPrice.setFormattedValue(BigDecimal.valueOf(data.getOrderTotal().doubleValue()).setScale(2,RoundingMode.HALF_UP).toString());
				   	orders.setTotal(totalPrice);
				   	orders.setTotalPriceWithTax(totalPrice);
				   }
				   SiteoneOrderDetailsResponseData detailResponse =  populateTrackingLink(data, unitId);
				   for (final OpenOrdersShipmentInfoResponseData shipmentData : data.getShipments())
					{
				   	ConsignmentData consData = new ConsignmentData();
				   	if (shipmentData.getShipmentType() != null)
				   	{
				   		String type = shipmentData.getShipmentType();
						   String orderType = getOrderType(type);
				   		orders.setOrderType(orderType);
				   	}
				   	if (shipmentData.getTransactionStatus() != null)
				   	{
				   		String tranStatus = shipmentData.getTransactionStatus();
							String statusDisplay = getStatusDisplay(tranStatus);
				   		orders.setStatusDisplay(statusDisplay);
				   		consData.setStatusDisplay(statusDisplay);
				   	}
				   	if (shipmentData.getInvoiceNumber() != null)
				   	{
				   		if (data.getShipments() != null && !data.getShipments().isEmpty() && data.getShipments().size() == 1)
				   		{
				   			orders.setInvoiceNumber(shipmentData.getInvoiceNumber());
				   		}
				   		consData.setInvoiceNumber(shipmentData.getInvoiceNumber());
				   	}
				   	if (shipmentData.getShipmentNumber() != null)
				   	{
				   		orders.setShipmentNumber(shipmentData.getShipmentNumber());
				   		consData.setShipmentNumber(shipmentData.getShipmentNumber());
				   		consData.setCode(shipmentData.getShipmentNumber());
				   	}
				   	if (shipmentData.getShipmentTotal() != null)
				   	{
				   		consData.setTotal(String.format("%.2f", shipmentData.getShipmentTotal()));
				   	}
				   	final List<URL> trackingUrl = new ArrayList<URL>();
				   	if (detailResponse != null && detailResponse.getDetails() != null && StringUtils.isNotBlank(detailResponse.getDetails().getTrackingLink()))
				   	{
							try
							{
								trackingUrl.add(URI.create(detailResponse.getDetails().getTrackingLink()).toURL());
								LOG.info("Tracking URL is "+ trackingUrl);
							}
							catch (final MalformedURLException e)
							{
								LOG.error("Invalid Tracking URL", e);
							}
							consData.setTrackingUrl(trackingUrl.get(0));
						}
						else
						{
							consData.setTrackingUrl(null);
						}
				   	listConsData.add(consData);
					}
				   orders.setConsignments(listConsData);
				   opOrders.add(orders);
				}
			}
		}	
		searchPageData.setResults(opOrders);
		searchPageData.setPagination(pagination);
      searchPageData.setSorts(getOrdersFilterData());
      searchPageData.setSorts(getOrdersSortData());
				
		return searchPageData;
	}
	
	private List<SortData> getOrdersSortData()
	{
		final List<SortData> sorts = new ArrayList<>();
		for (final String sort : ordersSortData)
		{
			final SortData data = new SortData();
			final String[] sortDatas = sort.split("\\|");
			data.setCode(sortDatas[0].trim());
			data.setName(sortDatas[1].trim());
			sorts.add(data);
		}
		return sorts;
	}

	private List<SortData> getOrdersFilterData()
	{
		final List<SortData> sorts = new ArrayList<>();
		for (final String sort : ordersFilterData)
		{
			final SortData data = new SortData();
			final String[] sortDatas = sort.split("\\|");
			data.setCode(sortDatas[0].trim());
			data.setName(sortDatas[1].trim());
			sorts.add(data);
		}
		return sorts;
	}
	private String truncatePrice(final Double priceToTruncate, final int toDigits)
	{
		if (null != priceToTruncate)
		{
			final DecimalFormat decimalFormat;
			if (toDigits == 3)
			{
				decimalFormat = new DecimalFormat("#,##0.000");
			}
			else
			{
				decimalFormat = new DecimalFormat("#,##0.00");
			}
			final String truncatedPrice = decimalFormat
					.format(BigDecimal.valueOf(priceToTruncate.doubleValue()).setScale(toDigits, BigDecimal.ROUND_HALF_UP));
			return truncatedPrice;
		}
		else
		{
			return null;
		}
	}

	private Date getFormattedDate(final String inputDateStr)
	{
		Date inputDate = null;
		final DateFormat dateFormatFrom = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try
		{
			inputDate = dateFormatFrom.parse(inputDateStr, new ParsePosition(0));
		}
		catch (final Exception exception)
		{
			LOG.error(exception.getMessage(), exception);
		}

		return inputDate;
	}
	
	private Date getFormattedDateForHeldOrder(final String inputDateStr)
	{
		Date inputDate = null;
		SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
      SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM dd, yyyy");
		try
		{
			Date parsedDate = inputFormat.parse(inputDateStr);
         String formattedDateString = outputFormat.format(parsedDate);
         inputDate = outputFormat.parse(formattedDateString);
		}
		catch (final Exception exception)
		{
			LOG.error(exception.getMessage(), exception);
		}

		return inputDate;
	}
	
	private Date getFormattedDateForHeldOrderDetail(final String inputDateStr)
	{
		Date inputDate = null;
		SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
      SimpleDateFormat outputFormat = new SimpleDateFormat("MM/dd/yyyy");
		try
		{
			Date parsedDate = inputFormat.parse(inputDateStr);
			String formattedDateString = outputFormat.format(parsedDate);
         inputDate = outputFormat.parse(formattedDateString);
		}
		catch (final Exception exception)
		{
			LOG.error(exception.getMessage(), exception);
		}

		return inputDate;
	}
	
	@Override
	public OrderData getOrderDetailsPage(final String code, final String unitId)
	{
		OrderData orders = new OrderData();
		List<OrderModel> heldOrder = ((SiteOneCustomerAccountService) customerAccountService).getHeldOrderWithOrderNumber(code);
		if(heldOrder != null && !CollectionUtils.isEmpty(heldOrder))
		{
			orders = populateHeldOrderDetails(heldOrder.get(0));
		}
		return orders;
	}

	@Override
	public OrderData getOrderDetailsPage(final String code, final String uid, final String branchNo, final Integer shipmentCount, final Boolean fromEmail)
	{
		B2BUnitData bUnit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
		String bUnitPrefix = null;
		String cusNumberPrefix = null;
		OrderData orders = new OrderData();
		if (bUnit != null && bUnit.getUid() != null)
		{
			bUnitPrefix = bUnit.getUid().trim().split("[-_]")[0].trim();
		}
		if (uid != null)
		{
			cusNumberPrefix = uid.trim().split("[-_]")[0].trim();
		}
		if (StringUtils.isNotBlank(bUnitPrefix) && StringUtils.isNotBlank(cusNumberPrefix) && bUnitPrefix.equalsIgnoreCase(cusNumberPrefix) && !fromEmail)
		{
			final SiteoneOrderDetailsResponseData response = ((SiteOneCustomerAccountService) customerAccountService)
					.getOrderDetailsData(getCustomerNo(uid), code,
							Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));

			orders = populateOrderDetailDataResponse(response, code, uid, branchNo, shipmentCount);
		}
		else if (fromEmail)
		{
			final SiteoneOrderDetailsResponseData response = ((SiteOneCustomerAccountService) customerAccountService)
					.getOrderDetailsData(getCustomerNo(uid), code,
							Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));

			orders = populateOrderDetailDataResponse(response, code, uid, branchNo, shipmentCount);
		}
		
		return orders;
	}

	@Override
	public ConsignmentData getMultipleShipmentPage(final String code, final String uid)
	{
		final SiteoneOrderDetailsResponseData response = ((SiteOneCustomerAccountService) customerAccountService)
				.getOrderDetailsData(getCustomerNo(uid), code,
						Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));

		final ConsignmentData consignment = populateMulShipmentDetailDataResponse(response, code);
		if (response != null && response.getDetails() != null && response.getDetails().getStatus() != null)
		{
			final String tranStatus = response.getDetails().getStatus();
			final String statusDisplay = getStatusDisplay(tranStatus);
			consignment.setStatusDisplay(statusDisplay);
		}

		return consignment;
	}

	/**
	 * @param response
	 * @param code
	 * @return
	 */
	private ConsignmentData populateMulShipmentDetailDataResponse(final SiteoneOrderDetailsResponseData response,
			final String code)
	{
		final ConsignmentData consData = new ConsignmentData();
		if (null != response)
		{
			final List<URL> trackingUrl = new ArrayList<URL>();
	   	if (response.getDetails() != null && StringUtils.isNotBlank(response.getDetails().getTrackingLink()))
	   	{
				try
				{
					trackingUrl.add(URI.create(response.getDetails().getTrackingLink()).toURL());
					LOG.info("Tracking URL is " + trackingUrl);
				}
				catch (final MalformedURLException e)
				{
					LOG.error("Invalid Tracking URL", e);
				}
				consData.setTrackingUrl(trackingUrl.get(0));
			}
			else
			{
				consData.setTrackingUrl(null);
			}
		}
		return consData;
	}
	
	private OrderData populateHeldOrderDetails(OrderModel orderModel)
	{
		final OrderData orderdata = new OrderData();
		final PointOfServiceData sessionStore = storeSessionFacade.getSessionStore();

		if (orderModel != null && null != sessionStore && null != sessionStore.getStoreId())
		{
			final Map<String, String> shippingFee = siteOneFeatureSwitchCacheService
					.getPunchoutB2BUnitMapping(DC_SHIPPING_FEE_BRANCHES);
			final Map<String, String> hubStoreShippingFee = siteOneFeatureSwitchCacheService
					.getPunchoutB2BUnitMapping(SiteoneCoreConstants.HUBSTORE_SHIPPING_FEE_BRANCHES);
			if (null != sessionStore.getHubStores() && null != sessionStore.getHubStores().get(0))
			{
				if (null != shippingFee.get(sessionStore.getHubStores().get(0))
						|| null != hubStoreShippingFee.get(sessionStore.getHubStores().get(0)))
				{
					orderdata.setIsShippingFeeBranch(Boolean.TRUE);
				}
				else
				{
					orderdata.setIsShippingFeeBranch(Boolean.FALSE);
				}
			}
			else
			{
				orderdata.setIsShippingFeeBranch(Boolean.FALSE);
			}
			if(orderModel.getPointOfService() != null && orderModel.getPointOfService().getStoreId() != null
					&& null != shippingFee.get(orderModel.getPointOfService().getStoreId()))
			{
				orderdata.setIsServicedByDC(Boolean.TRUE);
			}
			if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("ShippingandDeliveryFeeBranches",
					sessionStore.getStoreId()) && BooleanUtils.isNotTrue(orderdata.getIsShippingFeeBranch()))
			{
				orderdata.setIsTampaBranch(Boolean.TRUE);
			}
			else
			{
				orderdata.setIsTampaBranch(Boolean.FALSE);
			}

			if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("ShippingandDeliveryLAFeeBranches",
					sessionStore.getStoreId()) && BooleanUtils.isNotTrue(orderdata.getIsShippingFeeBranch()))
			{
				orderdata.setIsLABranch(Boolean.TRUE);
			}
			else
			{
				orderdata.setIsLABranch(Boolean.FALSE);
			}
		}
		
		if(orderModel != null)
		{
			orderdata.setIsHybrisOrder(Boolean.TRUE);
			orderdata.setStatusDisplay(PENDING);
			if(orderModel.getCode() != null)
			{
				orderdata.setCode(PENDING);
			}
			if(orderModel.getPurchaseOrderNumber() != null)
			{
				orderdata.setPurchaseOrderNumber(orderModel.getPurchaseOrderNumber());
			}
			if(orderModel.getCreationtime() != null)
			{
				orderdata.setCreated(getFormattedDateForHeldOrder(orderModel.getCreationtime().toString()));
			}
			if(orderModel.getOrderType() != null)
			{
				orderdata.setOrderType(orderModel.getOrderType().getCode());
			}
			if(orderModel.getContactPerson() != null && orderModel.getContactPerson().getName() != null)
			{
				orderdata.setStoreUserName(orderModel.getContactPerson().getName());
			}
			
			PointOfServiceData posData = new PointOfServiceData(); 
			AddressData adData = new AddressData();
			AddressData billAdData = new AddressData();
			CustomerData custData = new CustomerData();
			ConsignmentData consData = new ConsignmentData();
			List<ConsignmentData> listConsData = new ArrayList<>();
			if(orderModel.getDeliveryAddress() != null)
			{
				AddressModel address = orderModel.getDeliveryAddress();
				if(address.getLine1() != null)
				{
					adData.setLine1(address.getLine1());
				}
				if(address.getPostalcode() != null)
				{
					adData.setPostalCode(address.getPostalcode());
				}
				if(address.getTown() != null)
				{
					adData.setTown(address.getTown());
				}
				if(address.getRegion() != null)
				{
					final RegionData reg = new RegionData();
					reg.setIsocodeShort(address.getRegion().getIsocodeShort());
					adData.setRegion(reg);
				}	
			}
			if (orderModel.getSpecialInstruction() != null)
			{
				orderdata.setSpecialInstruction(orderModel.getSpecialInstruction());
			}
			if (orderModel.getContactPerson() != null && orderModel.getContactPerson().getName() != null)
			{
				custData.setName(orderModel.getContactPerson().getName());
				custData.setFirstName(orderModel.getContactPerson().getName());
			}
			if (orderModel.getContactPerson() != null && orderModel.getContactPerson().getEmail() != null)
			{
				custData.setEmail(orderModel.getContactPerson().getEmail());
			}
			if (orderModel.getContactPerson() != null && null != orderModel.getContactPerson().getContactInfos())
			{
				orderModel.getContactPerson().getContactInfos().forEach(info -> {
					if (info instanceof PhoneContactInfoModel)
					{
						final PhoneContactInfoModel phoneInfo = (PhoneContactInfoModel) info;
						if (PhoneContactInfoType.MOBILE.equals(phoneInfo.getType()))
						{
							custData.setContactNumber(phoneInfo.getPhoneNumber());
						}
					}
				});
			}
			if (orderModel.getRequestedDate() != null)
			{
				orderdata.setRequestedDate(getFormattedDateForHeldOrderDetail(orderModel.getRequestedDate().toString()));
				consData.setRequestedDate(getFormattedDateForHeldOrderDetail(orderModel.getRequestedDate().toString()));
			}
			if (orderModel.getRequestedMeridian() != null)
			{
				orderdata.setRequestedMeridian(orderModel.getRequestedMeridian().getCode());
			}
			consData.setTrackingUrl(null);
			if (orderModel.getOrderType() != null)
			{
				consData.setDeliveryMode(orderModel.getOrderType().getCode());
			}
			consData.setStatusDisplay(PENDING);
			consData.setStatus(null);
			consData.setConsignmentAddress(adData);
			listConsData.add(consData);
			orderdata.setConsignments(listConsData);
			posData.setAddress(adData);
			if (orderModel.getPointOfService() != null && orderModel.getPointOfService().getStoreId() != null)
			{
				posData.setStoreId(orderModel.getPointOfService().getStoreId());
			}
			orderdata.setPointOfService(posData);
			orderdata.setDeliveryAddress(adData);
			orderdata.setContactPerson(custData);
			orderdata.setB2bCustomerData(custData);
			
			final List<OrderEntryData> listEntryData = new ArrayList<>();
			double totalDiscountedPrice = 0.0d;
			BigDecimal totalDiscPrice = BigDecimal.ZERO;
			if(orderModel.getConsignments() != null)
			{
				Iterator<ConsignmentModel> i = orderModel.getConsignments().iterator();
				while(i.hasNext())
				{
					ConsignmentModel cons = i.next();
					if(cons.getConsignmentProcesses() != null)
					{
						Collection<ConsignmentProcessModel> conPs = cons.getConsignmentProcesses();
						if(conPs != null)
						{
							for(final ConsignmentProcessModel conp : conPs)
							{
								if(conp.getState() != null && conp.getState().equals(ProcessState.RUNNING))
								{
									if(cons.getTotal() != null)
									{
										final PriceData totalPrice = new PriceData();
								   	totalPrice.setValue(BigDecimal.valueOf(Double.parseDouble(cons.getTotal())).setScale(2,RoundingMode.HALF_UP));
								   	totalPrice.setFormattedValue(BigDecimal.valueOf(Double.parseDouble(cons.getTotal())).setScale(2,RoundingMode.HALF_UP).toString());
								   	orderdata.setTotalPrice(totalPrice);
								   	orderdata.setTotalPriceWithTax(totalPrice);
								   	consData.setTotal(String.format("%.2f", Double.parseDouble(cons.getTotal())));
									}
									if(cons.getDeliveryMode() != null)
									{
										orderdata.setOrderType(getOrderType(cons.getDeliveryMode().getCode()));
										consData.setDeliveryMode(getDeliveryMode(cons.getDeliveryMode().getCode()));
									}
									if(cons.getConsignmentEntries() != null)
									{
										for (final ConsignmentEntryModel consignmentEntryModel : cons.getConsignmentEntries())
										{
											final OrderEntryData entryData = new OrderEntryData();
											final ProductData prdData = new ProductData();
											final AbstractOrderEntryModel entryModel = consignmentEntryModel.getOrderEntry();
											if (entryModel != null)
											{
												if (entryModel.getProduct() != null && entryModel.getProduct().getCode() != null)
												{
													prdData.setCode(entryModel.getProduct().getCode());
												}
												if (entryModel.getProduct() != null && entryModel.getProduct().getItemNumber() != null)
												{
													prdData.setItemNumber(entryModel.getProduct().getItemNumber());
												}
												if (entryModel.getProduct() != null && entryModel.getProduct().getProductShortDesc() != null)
												{
													prdData.setName(entryModel.getProduct().getProductShortDesc());
												}
												if (entryModel.getInventoryUOM() != null && entryModel.getInventoryUOM().getCode() != null)
												{
													entryData.setUomMeasure(entryModel.getInventoryUOM().getCode());
												}
												if (entryModel.getQuantity() != null)
												{
													final String orderedQuantity = new DecimalFormat("#").format(entryModel.getQuantity());
													entryData.setQuantity(Long.parseLong(orderedQuantity));
												}
												if (entryModel.getTotalPrice() != null)
												{
													final BigDecimal totPriceVal = BigDecimal.valueOf(entryModel.getTotalPrice().doubleValue()).setScale(3, RoundingMode.HALF_UP);
													final PriceData totalPrice = new PriceData();
													totalPrice.setValue(totPriceVal);
													totalPrice.setFormattedValue(totPriceVal.toString());
													entryData.setTotalPrice(totalPrice);
												}
												if (entryModel.getBasePrice() != null)
												{
													final BigDecimal unitPriceVal = BigDecimal.valueOf(entryModel.getBasePrice().doubleValue()).setScale(3, RoundingMode.HALF_UP);
													final PriceData unitPrice = new PriceData();
													unitPrice.setValue(unitPriceVal);
													unitPrice.setFormattedValue(unitPriceVal.toString());
													if (entryModel.getDiscountAmount() != null && entryModel.getBasePrice().doubleValue() > entryModel.getDiscountAmount().doubleValue())
													{
														final PriceData originalPrice = new PriceData();
														final BigDecimal originalUnitPrice = BigDecimal.valueOf(entryModel.getBasePrice().doubleValue());
														final BigDecimal dicountUnitPrice = BigDecimal.valueOf(entryModel.getDiscountAmount().doubleValue());
														final BigDecimal priceDifference = originalUnitPrice.subtract(dicountUnitPrice);
														originalPrice.setValue(dicountUnitPrice.setScale(3, RoundingMode.HALF_UP));
														originalPrice.setFormattedValue(dicountUnitPrice.setScale(3, RoundingMode.HALF_UP).toString());
														if (priceDifference.compareTo(BigDecimal.ZERO) != 0)
														{
															entryData.setBasePrice(originalPrice);
															entryData.setActualItemCost(unitPrice);
														}
														else
														{
															entryData.setBasePrice(unitPrice);
														}
													}
													else
													{
														entryData.setBasePrice(unitPrice);
													}
												}
												if (entryModel.getBasePrice() != null && entryModel.getDiscountAmount() != null
														&& entryModel.getBasePrice().doubleValue() > entryModel.getDiscountAmount().doubleValue())
												{
													double adjPrice = 0.0d;
													final PriceData discPrice = new PriceData();
													final BigDecimal originalUnitPrice = BigDecimal.valueOf(entryModel.getBasePrice().doubleValue());
													final BigDecimal dicountUnitPrice = BigDecimal.valueOf(entryModel.getDiscountAmount().doubleValue());
													final BigDecimal quant = BigDecimal.valueOf(entryData.getQuantity());
													final BigDecimal priceDifference = originalUnitPrice.subtract(dicountUnitPrice).multiply(quant);
													discPrice.setValue(priceDifference.setScale(2, RoundingMode.HALF_UP));
													discPrice.setFormattedValue(priceDifference.setScale(2, RoundingMode.HALF_UP).toString());
													entryData.setDiscountAmount(discPrice);
													if (discPrice.getValue() != null)
													{
														final BigDecimal currentDiscount = discPrice.getValue();
														totalDiscPrice = totalDiscPrice.add(currentDiscount);
														totalDiscountedPrice = totalDiscPrice.doubleValue();
													}
													final PromotionResultData promo = new PromotionResultData();
													final PromotionOrderEntryConsumedData promoEntry = new PromotionOrderEntryConsumedData();
													final List<PromotionResultData> listPromo = new ArrayList<>();
													final List<PromotionOrderEntryConsumedData> listPromoEntry = new ArrayList<>();
													adjPrice = entryModel.getDiscountAmount().doubleValue();
													promoEntry.setAdjustedUnitPrice(Double.valueOf(adjPrice));
													listPromoEntry.add(promoEntry);
													promo.setConsumedEntries(listPromoEntry);
													listPromo.add(promo);
													orderdata.setAppliedProductPromotions(listPromo);
												}
												else
												{
													orderdata.setAppliedProductPromotions(null);
												}
												if (entryModel.getProduct() != null && entryModel.getProduct().getCode() != null)
												{
													final ProductData productdata = siteOneProductFacade
															.getProductBySearchTermForSearch(entryModel.getProduct().getCode(), Arrays.asList(ProductOption.BASIC,ProductOption.CATEGORIES,
																	ProductOption.URL, ProductOption.IMAGES, ProductOption.STOCK, ProductOption.AVAILABILITY_MESSAGE));
													if (productdata != null)
													{
														if (productdata.getIsForceInStock() != null)
														{
															prdData.setIsForceInStock(productdata.getIsForceInStock());
														}
														if (productdata.getIsRegulateditem() != null)
														{
															prdData.setIsRegulateditem(productdata.getIsRegulateditem());
														}
														if (productdata.getInStockImage() != null)
														{
															prdData.setInStockImage(productdata.getInStockImage());
														}
														if (productdata.getOutOfStockImage() != null)
														{
															prdData.setOutOfStockImage(productdata.getOutOfStockImage());
														}
														if (productdata.getNotInStockImage() != null)
														{
															prdData.setNotInStockImage(productdata.getNotInStockImage());
														}
														if (productdata.getIsStockInNearbyBranch() != null)
														{
															prdData.setIsStockInNearbyBranch(productdata.getIsStockInNearbyBranch());
														}
														if (productdata.getStoreStockAvailabilityMsg() != null)
														{
															prdData.setStoreStockAvailabilityMsg(productdata.getStoreStockAvailabilityMsg());
														}
														if (productdata.getNearestStore() != null)
														{
															prdData.setNearestStore(productdata.getNearestStore());
														}
														if (productdata.getStock() != null)
														{
															prdData.setStock(productdata.getStock());
														}
														if (productdata.getImages() != null)
														{
															prdData.setImages(productdata.getImages());
														}
														if (productdata.getUrl() != null)
														{
															prdData.setUrl(productdata.getUrl());
														}
														if (productdata.getIsProductDiscontinued() != null)
														{
															prdData.setIsProductDiscontinued(productdata.getIsProductDiscontinued());
														}
														if (productdata.getIsSellable() != null)
														{
															prdData.setIsSellable(productdata.getIsSellable());
														}
														if (productdata.getInventoryCheck() != null)
														{
															prdData.setInventoryCheck(productdata.getInventoryCheck());
														}
														if (productdata.getInventoryFlag() != null)
														{
															prdData.setInventoryFlag(productdata.getInventoryFlag());
														}
														if (productdata.getLevel1Category() != null && (productdata.getLevel1Category().equalsIgnoreCase("Nursery")
																|| productdata.getLevel1Category().equalsIgnoreCase("vivero")))
														{
															prdData.setInventoryFlag(Boolean.FALSE);
															prdData.setInventoryCheck(Boolean.FALSE);
														}
														if (productdata.getIsEligibleForBackorder()	!= null)
														{
															prdData.setIsEligibleForBackorder(productdata.getIsEligibleForBackorder());
														}
														if (productdata.getIsTransferrable() != null)
														{
															prdData.setIsTransferrable(productdata.getIsTransferrable());
														}
														if (entryModel.getProduct().getCode().equals("9999999"))
														{
															prdData.setAvailableStatus(Boolean.FALSE);
														}
														else
														{
															prdData.setAvailableStatus(Boolean.TRUE);
														}
													}
												}
											}
											entryData.setProduct(prdData);
											listEntryData.add(entryData);
										}
									}
									final PriceData totDiscPrice = new PriceData();
									BigDecimal totalDiscountedBD = BigDecimal.valueOf(totalDiscountedPrice);
									totDiscPrice.setValue(totalDiscountedBD.setScale(2,RoundingMode.HALF_UP));
									totDiscPrice.setFormattedValue(totalDiscountedBD.setScale(2,RoundingMode.HALF_UP).toString());
									if (totDiscPrice.getValue().compareTo(BigDecimal.ZERO) != 0)
									{
										orderdata.setTotalDiscounts(totDiscPrice);
										orderdata.setTotalDiscountAmount(totDiscPrice);
									}
									orderdata.setUnconsignedEntries(listEntryData);
									orderdata.setEntries(listEntryData);
									if (cons.getSubTotal() != null)
									{
										BigDecimal subTotVal = BigDecimal.valueOf(Double.parseDouble(cons.getSubTotal())).setScale(2,
												RoundingMode.HALF_UP);
										final BigDecimal discountPrc = totDiscPrice.getValue().setScale(2, RoundingMode.HALF_UP);
										subTotVal = subTotVal.add(discountPrc);
										final PriceData subTotal = new PriceData();
										subTotal.setValue(subTotVal);
										subTotal.setFormattedValue(subTotVal.toString());
										orderdata.setSubTotal(subTotal);
									}
									if (orderModel.getFreight() != null)
									{
										BigDecimal freightAmount = new BigDecimal(String.valueOf(orderModel.getFreight()));
										freightAmount = freightAmount.setScale(2, RoundingMode.DOWN);
										orderdata.setDeliveryFreight(freightAmount.toPlainString());
										orderdata.setShippingFreight(freightAmount.toPlainString());
										orderdata.setFreight(freightAmount.toPlainString());
									}
									if (cons.getTax() != null)
									{
										final BigDecimal totTaxVal = BigDecimal.valueOf(Double.parseDouble(cons.getTax())).setScale(2,
												RoundingMode.HALF_UP);
										final PriceData totalTax = new PriceData();
										totalTax.setValue(totTaxVal);
										totalTax.setFormattedValue(totTaxVal.toString());
										orderdata.setTotalTax(totalTax);
									}
									if (cons.getTotal() != null)
									{
										final BigDecimal shipTotVal = BigDecimal.valueOf(Double.parseDouble(cons.getTotal())).setScale(2,
												RoundingMode.HALF_UP);
										final PriceData total = new PriceData();
										total.setValue(shipTotVal);
										total.setFormattedValue(shipTotVal.toString());
										orderdata.setTotalPriceWithTax(total);
									}
								}
							}
						}
					}
				}
			}
			
			if(consData.getDeliveryMode().equalsIgnoreCase("PICKUP") && orderModel.getPickupAddress() != null)
			{
				AddressModel address = orderModel.getPickupAddress();
				if(address.getLine1() != null)
				{
					adData.setLine1(address.getLine1());
				}
				if(address.getPostalcode() != null)
				{
					adData.setPostalCode(address.getPostalcode());
				}
				if(address.getTown() != null)
				{
					adData.setTown(address.getTown());
				}
				if(address.getRegion() != null)
				{
					final RegionData reg = new RegionData();
					reg.setIsocodeShort(address.getRegion().getIsocodeShort());
					adData.setRegion(reg);
				}
				orderdata.setDeliveryAddress(adData);
				posData.setAddress(adData);
				orderdata.setPointOfService(posData);
			}
			
			//Payment info
			if (CollectionUtils.isNotEmpty(orderModel.getPaymentInfoList()))
			{
				final SiteoneCreditCardPaymentInfoModel paymentInfo = orderModel.getPaymentInfoList().get(0);
				orderdata.setSiteOnePaymentInfoData(siteOnePaymentInfoDataConverter.convert(paymentInfo));

			}
			else if (CollectionUtils.isNotEmpty(orderModel.getPoaPaymentInfoList()))
			{
				final SiteonePOAPaymentInfoModel poaPaymentInfo = orderModel.getPoaPaymentInfoList().get(0);
				orderdata.setSiteOnePOAPaymentInfoData(siteOnePOAPaymentInfoDataConverter.convert(poaPaymentInfo));
			}
			else
			{
				final SiteOnePaymentInfoData paymentData = new SiteOnePaymentInfoData();
				paymentData.setPaymentType("2");
				orderdata.setSiteOnePaymentInfoData(paymentData);
			}
			
			if (orderModel.getPaymentAddress() != null)
			{
				AddressModel paymentAddress = orderModel.getPaymentAddress();
				if(paymentAddress.getLine1() != null)
				{
					billAdData.setLine1(paymentAddress.getLine1());
				}
				if (paymentAddress.getLine2() != null)
				{
					billAdData.setLine2(paymentAddress.getLine2());
				}
				if (paymentAddress.getTown() != null)
				{
					billAdData.setTown(paymentAddress.getTown());
				}
				if (paymentAddress.getRegion() != null && paymentAddress.getRegion().getIsocodeShort() != null)
				{
					final RegionData billRgData = new RegionData();
					billRgData.setIsocodeShort(paymentAddress.getRegion().getIsocodeShort());
					billAdData.setRegion(billRgData);
				}
				if (paymentAddress.getPostalcode() != null)
				{
					billAdData.setPostalCode(paymentAddress.getPostalcode());
				}
			}
			orderdata.setBillingAddress(billAdData);
			if (billAdData != null)
			{
				final StringBuilder formattedAdrsBuilder = new StringBuilder();
				if (StringUtils.isNotBlank(billAdData.getLine1()))
				{
					formattedAdrsBuilder.append(billAdData.getLine1().strip());
				}
				if (StringUtils.isNotBlank(billAdData.getLine2()))
				{
					if (formattedAdrsBuilder.length() > 0)
					{
						formattedAdrsBuilder.append(", ");
					}
					formattedAdrsBuilder.append(billAdData.getLine2().strip());
				}
				if (StringUtils.isNotBlank(billAdData.getTown()))
				{
					if (formattedAdrsBuilder.length() > 0)
					{
						formattedAdrsBuilder.append(", ");
					}
					formattedAdrsBuilder.append(billAdData.getTown().strip());
				}
				if (StringUtils.isNotBlank(billAdData.getPostalCode()))
				{
					if (formattedAdrsBuilder.length() > 0)
					{
						formattedAdrsBuilder.append(", ");
					}
					formattedAdrsBuilder.append(billAdData.getPostalCode().strip());
				}
				billAdData.setFormattedAddress(formattedAdrsBuilder.toString());
				if (StringUtils.isNotBlank(billAdData.getFormattedAddress()) && orderdata.getBillingAddress() != null)
				{
					orderdata.getBillingAddress().setFormattedAddress(billAdData.getFormattedAddress());
				}
			}
			
		}
		
		return orderdata;
	}

	/**
	 * @param response
	 * @return
	 */
	private OrderData populateOrderDetailDataResponse(SiteoneOrderDetailsResponseData response, String code, String uid, String branchNo, Integer shipmentCount)
	{
		// YTODO Auto-generated method stub
		final OrderData orderdata = new OrderData();
		final PointOfServiceData sessionStore = storeSessionFacade.getSessionStore();

		if (null != sessionStore && null != sessionStore.getStoreId())
		{
			final Map<String, String> shippingFee = siteOneFeatureSwitchCacheService
					.getPunchoutB2BUnitMapping(DC_SHIPPING_FEE_BRANCHES);
			final Map<String, String> hubStoreShippingFee = siteOneFeatureSwitchCacheService
					.getPunchoutB2BUnitMapping(SiteoneCoreConstants.HUBSTORE_SHIPPING_FEE_BRANCHES);
			if (null != sessionStore.getHubStores() && null != sessionStore.getHubStores().get(0))
			{
				if (null != shippingFee.get(sessionStore.getHubStores().get(0))
						|| null != hubStoreShippingFee.get(sessionStore.getHubStores().get(0)))
				{
					orderdata.setIsShippingFeeBranch(Boolean.TRUE);
				}
				else
				{
					orderdata.setIsShippingFeeBranch(Boolean.FALSE);
				}
			}
			else
			{
				orderdata.setIsShippingFeeBranch(Boolean.FALSE);
			}
			if(branchNo != null && null != shippingFee.get(branchNo))
			{
				orderdata.setIsServicedByDC(Boolean.TRUE);
			}
			if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("ShippingandDeliveryFeeBranches",
					sessionStore.getStoreId()) && BooleanUtils.isNotTrue(orderdata.getIsShippingFeeBranch()))
			{
				orderdata.setIsTampaBranch(Boolean.TRUE);
			}
			else
			{
				orderdata.setIsTampaBranch(Boolean.FALSE);
			}

			if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("ShippingandDeliveryLAFeeBranches",
					sessionStore.getStoreId()) && BooleanUtils.isNotTrue(orderdata.getIsShippingFeeBranch()))
			{
				orderdata.setIsLABranch(Boolean.TRUE);
			}
			else
			{
				orderdata.setIsLABranch(Boolean.FALSE);
			}
		}

		if (null != response)
		{
			if (response.getOrderHeader() != null)
			{
				if (response.getOrderHeader().getOrderNumber() != null && response.getOrderHeader().getOrderNumber().startsWith("M"))
				{
					orderdata.setIsHybrisOrder(Boolean.FALSE);
				}
				else
				{
					orderdata.setIsHybrisOrder(Boolean.TRUE);
				}
				if (response.getOrderHeader().getOrderNumber() != null)
				{
					orderdata.setCode(response.getOrderHeader().getOrderNumber());
				}
				if (response.getOrderHeader().getPoNumber() != null)
				{
					orderdata.setPurchaseOrderNumber(response.getOrderHeader().getPoNumber());
				}
				if (response.getOrderHeader().getDatePlaced() != null)
				{
					orderdata.setCreated(getFormattedDate(response.getOrderHeader().getDatePlaced()));
				}
			}
			if (response.getDetails() != null)
			{
				if (response.getDetails().getContactName() != null)
				{
					orderdata.setStoreUserName(response.getDetails().getContactName());
				}

				if (response.getDetails().getOrderShipmentType() != null)
				{
					final String type = response.getDetails().getOrderShipmentType();
					final String orderType = getOrderType(type);
					orderdata.setOrderType(orderType);
				}

				if (response.getDetails().getStatus() != null)
				{
					final String ordStatus = response.getDetails().getStatus();
					final String statusDisplay = getStatusDisplay(ordStatus);
					orderdata.setStatusDisplay(statusDisplay);
				}
				if (response.getDetails().getShipmentTotal() != null)
				{
					final BigDecimal shpTotVal = BigDecimal.valueOf(response.getDetails().getShipmentTotal()).setScale(2,
							RoundingMode.HALF_UP);
					final PriceData totalPrice = new PriceData();
					totalPrice.setValue(shpTotVal);
					totalPrice.setFormattedValue(shpTotVal.toString());
					orderdata.setTotalPriceWithTax(totalPrice);
				}
			}
			if (code != null && orderdata.getStatusDisplay() != null && orderdata.getStatusDisplay().equalsIgnoreCase("closed"))
			{
				orderdata.setInvoiceNumber(code);
			}
			if (code != null)
			{
				orderdata.setInvoiceCode(code);
			}
			if (uid != null)
			{
				orderdata.setUid(uid);
			}
			if (code != null && shipmentCount != null)
			{
				List<String> codes = new ArrayList<>();
				String[] parts = code.split("-");
				if (parts.length == 2)
				{
					String prefix = parts[0];
					for (int i = 1; i<= shipmentCount.intValue(); i++)
					{
						String suffix = String.format("%03d", i);
						codes.add(prefix + "-" +suffix);
					}
				}			
				orderdata.setShipmentCodes(codes);
			}
// Fulfillment info
			PointOfServiceData posData = new PointOfServiceData(); 
			AddressData adData = new AddressData();
			AddressData billAdData = new AddressData();
			CustomerData custData = new CustomerData();
			ConsignmentData consData = new ConsignmentData();
			List<ConsignmentData> listConsData = new ArrayList<>();
			if (response.getDetails() != null)
			{
				if (response.getDetails().getAddress1() != null)
				{
					adData.setLine1(response.getDetails().getAddress1());
				}
				if (response.getDetails().getCity() != null)
				{
					adData.setTown(response.getDetails().getCity());
				}
				if (response.getDetails().getStateProvince() != null)
				{
					final RegionData rgData = new RegionData();
					rgData.setIsocodeShort(response.getDetails().getStateProvince());
					adData.setRegion(rgData);
				}
				if (response.getDetails().getPostalCode() != null)
				{
					adData.setPostalCode(response.getDetails().getPostalCode());
				}
				if (response.getDetails().getSpecialInstructions() != null)
				{
					orderdata.setSpecialInstruction(response.getDetails().getSpecialInstructions());
				}
				if (response.getDetails().getContactName() != null)
				{
					custData.setName(response.getDetails().getContactName());
				}
				if (response.getDetails().getContactName() != null)
				{
					custData.setFirstName(response.getDetails().getContactName());
				}
				if (response.getDetails().getContactPhone() != null)
				{
					final String input = response.getDetails().getContactPhone();
					final String number = input.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3");
					custData.setContactNumber(number);
				}
				if (response.getDetails().getContactEmail() != null)
				{
					custData.setEmail(response.getDetails().getContactEmail());
				}
				if (response.getDetails().getFulfillmentDate() != null)
				{
					if (!response.getDetails().getFulfillmentDate().startsWith("0"))
					{
						orderdata.setRequestedDate(getFormattedDate(response.getDetails().getFulfillmentDate()));
					}
					else
					{
						orderdata.setRequestedDate(null);
					}
				}
				if (response.getDetails().getDeliveryPeriod() != null)
				{
					orderdata.setRequestedMeridian(response.getDetails().getDeliveryPeriod());
				}
				final List<URL> trackingUrl = new ArrayList<URL>();
				if (StringUtils.isNotBlank(response.getDetails().getTrackingLink()))
				{
					try
					{
						trackingUrl.add(URI.create(response.getDetails().getTrackingLink()).toURL());
						LOG.info("Tracking URL is " + trackingUrl);
					}
					catch (final MalformedURLException e)
					{
						LOG.error("Invalid Tracking URL", e);
					}
					consData.setTrackingUrl(trackingUrl.get(0));
				}
				else
				{
					consData.setTrackingUrl(null);
				}
				if (response.getDetails().getOrderShipmentType() != null)
				{
					final String type = response.getDetails().getOrderShipmentType();
					final String mode = getOrderType(type);
					consData.setDeliveryMode(mode);
				}
				if (response.getDetails().getStatus() != null)
				{
					consData.setStatusDisplay(response.getDetails().getStatus());
				}
				if (response.getDetails().getFulfillmentDate() != null)
				{
					if (!response.getDetails().getFulfillmentDate().startsWith("0"))
					{
						consData.setRequestedDate(getFormattedDate(response.getDetails().getFulfillmentDate()));
					}
					else
					{
						consData.setRequestedDate(null);
					}
				}
				if (code != null && orderdata.getStatusDisplay() != null && orderdata.getStatusDisplay().equalsIgnoreCase("closed"))
				{
					consData.setInvoiceNumber(code);
				}
			}

			consData.setStatus(null);
			consData.setConsignmentAddress(adData);
			listConsData.add(consData);
			orderdata.setConsignments(listConsData);
			posData.setAddress(adData);
			if (branchNo != null)
			{
				posData.setStoreId(branchNo);
			}
			if (shipmentCount != null)
			{
				orderdata.setShipmentCount(shipmentCount);
			}
			orderdata.setPointOfService(posData);
			orderdata.setDeliveryAddress(adData);
			orderdata.setContactPerson(custData);
			orderdata.setB2bCustomerData(custData);

			// Order Summary
			final List<OrderEntryData> listEntryData = new ArrayList<>();
			double totalDiscountedPrice = 0.0d;
			BigDecimal totalDiscPrice = BigDecimal.ZERO;
			for (final OrderDetailsLineItemsResponseData itemData : response.getLineItems())
			{
				final OrderEntryData entryData = new OrderEntryData();
				final ProductData prdData = new ProductData();
				if (itemData != null)
				{
					if (itemData.getSkuid() != null)
					{
						prdData.setCode(itemData.getSkuid().toString());
					}
					if (itemData.getItemNumber() != null)
					{
						prdData.setItemNumber(itemData.getItemNumber());
					}
					if (itemData.getItemDescription() != null)
					{
						prdData.setName(itemData.getItemDescription());
					}
					if (itemData.getUomName() != null)
					{
						entryData.setUomMeasure(itemData.getUomName());
					}
					if (itemData.getQuantityShipped() != null && itemData.getQuantityShipped() != 0.0)
					{
						final String shippedQuantity = new DecimalFormat("#").format(itemData.getQuantityShipped());
						entryData.setQuantity(Long.parseLong(shippedQuantity));
					}
					else if (itemData.getQuantityOrdered() != null)
					{
						final String orderedQuantity = new DecimalFormat("#").format(itemData.getQuantityOrdered());
						entryData.setQuantity(Long.parseLong(orderedQuantity));
					}
					if (itemData.getTotalPrice() != null)
					{
						final BigDecimal totPriceVal = BigDecimal.valueOf(itemData.getTotalPrice()).setScale(3, RoundingMode.HALF_UP);
						final PriceData totalPrice = new PriceData();
						totalPrice.setValue(totPriceVal);
						totalPrice.setFormattedValue(totPriceVal.toString());
						entryData.setTotalPrice(totalPrice);
					}
					if (itemData.getUnitPrice() != null)
					{
						final BigDecimal unitPriceVal = BigDecimal.valueOf(itemData.getUnitPrice()).setScale(3, RoundingMode.HALF_UP);
						final PriceData unitPrice = new PriceData();
						unitPrice.setValue(unitPriceVal);
						unitPrice.setFormattedValue(unitPriceVal.toString());
						if (itemData.getDiscountedUnitPrice() != null && itemData.getUnitPrice() > itemData.getDiscountedUnitPrice())
						{
							final PriceData originalPrice = new PriceData();
							final BigDecimal originalUnitPrice = BigDecimal.valueOf(itemData.getUnitPrice());
							final BigDecimal dicountUnitPrice = BigDecimal.valueOf(itemData.getDiscountedUnitPrice());
							final BigDecimal priceDifference = originalUnitPrice.subtract(dicountUnitPrice);
							originalPrice.setValue(dicountUnitPrice.setScale(3, RoundingMode.HALF_UP));
							originalPrice.setFormattedValue(dicountUnitPrice.setScale(3, RoundingMode.HALF_UP).toString());
							if (priceDifference.compareTo(BigDecimal.ZERO) != 0)
							{
								entryData.setBasePrice(originalPrice);
								entryData.setActualItemCost(unitPrice);
							}
							else
							{
								entryData.setBasePrice(unitPrice);
							}
						}
						else
						{
							entryData.setBasePrice(unitPrice);
						}
					}
					if (itemData.getUnitPrice() != null && itemData.getDiscountedUnitPrice() != null
							&& itemData.getUnitPrice() > itemData.getDiscountedUnitPrice())
					{
						double adjPrice = 0.0d;
						final PriceData discPrice = new PriceData();
						final BigDecimal originalUnitPrice = BigDecimal.valueOf(itemData.getUnitPrice());
						final BigDecimal dicountUnitPrice = BigDecimal.valueOf(itemData.getDiscountedUnitPrice());
						final BigDecimal quant = BigDecimal.valueOf(entryData.getQuantity());
						final BigDecimal priceDifference = originalUnitPrice.subtract(dicountUnitPrice).multiply(quant);
						discPrice.setValue(priceDifference.setScale(2, RoundingMode.HALF_UP));
						discPrice.setFormattedValue(priceDifference.setScale(2, RoundingMode.HALF_UP).toString());
						entryData.setDiscountAmount(discPrice);
						if (discPrice.getValue() != null)
						{
							final BigDecimal currentDiscount = discPrice.getValue();
							totalDiscPrice = totalDiscPrice.add(currentDiscount);
							totalDiscountedPrice = totalDiscPrice.doubleValue();
						}
						final PromotionResultData promo = new PromotionResultData();
						final PromotionOrderEntryConsumedData promoEntry = new PromotionOrderEntryConsumedData();
						final List<PromotionResultData> listPromo = new ArrayList<>();
						final List<PromotionOrderEntryConsumedData> listPromoEntry = new ArrayList<>();
						adjPrice = itemData.getDiscountedUnitPrice().doubleValue();
						promoEntry.setAdjustedUnitPrice(Double.valueOf(adjPrice));
						listPromoEntry.add(promoEntry);
						promo.setConsumedEntries(listPromoEntry);
						listPromo.add(promo);
						orderdata.setAppliedProductPromotions(listPromo);
					}
					else
					{
						orderdata.setAppliedProductPromotions(null);
					}
					if (itemData.getSkuid() != null)
					{
						final ProductData productdata = siteOneProductFacade
								.getProductBySearchTermForSearch(itemData.getSkuid().toString(), Arrays.asList(ProductOption.BASIC,ProductOption.CATEGORIES,
										ProductOption.URL, ProductOption.IMAGES, ProductOption.STOCK, ProductOption.AVAILABILITY_MESSAGE));
						if (productdata != null)
						{
							if (productdata.getIsForceInStock() != null)
							{
								prdData.setIsForceInStock(productdata.getIsForceInStock());
							}
							if (productdata.getIsRegulateditem() != null)
							{
								prdData.setIsRegulateditem(productdata.getIsRegulateditem());
							}
							if (productdata.getInStockImage() != null)
							{
								prdData.setInStockImage(productdata.getInStockImage());
							}
							if (productdata.getOutOfStockImage() != null)
							{
								prdData.setOutOfStockImage(productdata.getOutOfStockImage());
							}
							if (productdata.getNotInStockImage() != null)
							{
								prdData.setNotInStockImage(productdata.getNotInStockImage());
							}
							if (productdata.getIsStockInNearbyBranch() != null)
							{
								prdData.setIsStockInNearbyBranch(productdata.getIsStockInNearbyBranch());
							}
							if (productdata.getStoreStockAvailabilityMsg() != null)
							{
								prdData.setStoreStockAvailabilityMsg(productdata.getStoreStockAvailabilityMsg());
							}
							if (productdata.getNearestStore() != null)
							{
								prdData.setNearestStore(productdata.getNearestStore());
							}
							if (productdata.getStock() != null)
							{
								prdData.setStock(productdata.getStock());
							}
							if (productdata.getImages() != null)
							{
								prdData.setImages(productdata.getImages());
							}
							if (productdata.getUrl() != null)
							{
								prdData.setUrl(productdata.getUrl());
							}
							if (productdata.getIsProductDiscontinued() != null)
							{
								prdData.setIsProductDiscontinued(productdata.getIsProductDiscontinued());
							}
							if (productdata.getIsSellable() != null)
							{
								prdData.setIsSellable(productdata.getIsSellable());
							}
							if (productdata.getInventoryCheck() != null)
							{
								prdData.setInventoryCheck(productdata.getInventoryCheck());
							}
							if (productdata.getInventoryFlag() != null)
							{
								prdData.setInventoryFlag(productdata.getInventoryFlag());
							}
							if (productdata.getLevel1Category() != null && (productdata.getLevel1Category().equalsIgnoreCase("Nursery")
									|| productdata.getLevel1Category().equalsIgnoreCase("vivero")))
							{
								prdData.setInventoryFlag(Boolean.FALSE);
								prdData.setInventoryCheck(Boolean.FALSE);
							}
							if (productdata.getIsEligibleForBackorder()	!= null)
							{
								prdData.setIsEligibleForBackorder(productdata.getIsEligibleForBackorder());
							}
							if (productdata.getIsTransferrable() != null)
							{
								prdData.setIsTransferrable(productdata.getIsTransferrable());
							}
							if (itemData.getSkuid().equals("9999999"))
							{
								prdData.setAvailableStatus(Boolean.FALSE);
							}
							else
							{
								prdData.setAvailableStatus(Boolean.TRUE);
							}
						}
					}
				}
				entryData.setProduct(prdData);
				listEntryData.add(entryData);
			}
			final PriceData totDiscPrice = new PriceData();
			BigDecimal totalDiscountedBD = BigDecimal.valueOf(totalDiscountedPrice);
			totDiscPrice.setValue(totalDiscountedBD.setScale(2,RoundingMode.HALF_UP));
			totDiscPrice.setFormattedValue(totalDiscountedBD.setScale(2,RoundingMode.HALF_UP).toString());
			if (totDiscPrice.getValue().compareTo(BigDecimal.ZERO) != 0)
			{
				orderdata.setTotalDiscounts(totDiscPrice);
				orderdata.setTotalDiscountAmount(totDiscPrice);
			}
			orderdata.setUnconsignedEntries(listEntryData);
			orderdata.setEntries(listEntryData);
			// Payment details
			for (final OrderDetailsPaymentsResponseData paymentData : response.getPayments())
			{
				for (final OrderDetailsOrderPaymentsResponseData orderPayments : response.getOrderPayments())
				{

					SiteOnePOAPaymentInfoData accountPayment = null;
					SiteOnePaymentInfoData siteOnePaymentInfoData = null;

					if (paymentData != null && orderPayments != null)
					{
						if (paymentData.getAddress1() != null)
						{
							billAdData.setLine1(paymentData.getAddress1());
						}
						if (paymentData.getAddress2() != null)
						{
							billAdData.setLine2(paymentData.getAddress2());
						}
						if (paymentData.getCity() != null)
						{
							billAdData.setTown(paymentData.getCity());
						}
						if (paymentData.getStateProvince() != null)
						{
							final RegionData billRgData = new RegionData();
							billRgData.setIsocodeShort(paymentData.getStateProvince());
							billAdData.setRegion(billRgData);
						}
						if (paymentData.getPostalCode() != null)
						{
							billAdData.setPostalCode(paymentData.getPostalCode());
						}
						orderdata.setBillingAddress(billAdData);

						final String paymentMethod1 = orderPayments.getPaymentMethod();
						final Double amountPaid1 = orderPayments.getAmountPaid();
						final String last4CreditCardDigits1 = orderPayments.getLast4CreditCardDigits();
						final String creditCardType1 = orderPayments.getCreditCardType();

						final String paymentMethod2 = paymentData.getPaymentMethod();
						final Double amountPaid2 = paymentData.getAmountPaid();
						final String last4CreditCardDigits2 = paymentData.getLast4CreditCardDigits();
						final String creditCardType2 = paymentData.getCreditCardType();

						final String finalPaymentMethod = (paymentMethod1 != null) ? paymentMethod1 : paymentMethod2;
						final Double finalAmountPaid = (amountPaid1 != null) ? amountPaid1 : amountPaid2;
						final String finalLast4CreditCardDigits = (last4CreditCardDigits1 != null) ? last4CreditCardDigits1
								: last4CreditCardDigits2;
						final String finalCreditCardType = (creditCardType1 != null) ? creditCardType1 : creditCardType2;

						if (finalPaymentMethod != null)
						{
							if (finalPaymentMethod.equalsIgnoreCase("On Account"))
							{
								accountPayment = new SiteOnePOAPaymentInfoData();
								accountPayment.setPaymentType("1");
								if (finalAmountPaid != null)
								{
									accountPayment.setAmountCharged(finalAmountPaid);
								}
								if (paymentData.getBillingTerms() != null)
								{
									accountPayment.setTermsCode(paymentData.getBillingTerms());
								}
							}
							else if (finalPaymentMethod.equalsIgnoreCase("Credit Card"))
							{
								siteOnePaymentInfoData = new SiteOnePaymentInfoData();
								siteOnePaymentInfoData.setPaymentType("3");
								if (finalCreditCardType != null)
								{
									siteOnePaymentInfoData.setCardType(finalCreditCardType);
									siteOnePaymentInfoData.setApplicationLabel(finalCreditCardType);
								}
								if (finalAmountPaid != null)
								{
									siteOnePaymentInfoData.setAmountCharged(finalAmountPaid);
								}
								if (finalLast4CreditCardDigits != null)
								{
									siteOnePaymentInfoData.setCardNumber(finalLast4CreditCardDigits);
								}
							}
							else
							{
								siteOnePaymentInfoData = new SiteOnePaymentInfoData();
								siteOnePaymentInfoData.setPaymentType("2");
							}
						}
						else
						{
							siteOnePaymentInfoData = new SiteOnePaymentInfoData();
							siteOnePaymentInfoData.setPaymentType("2");
						}
					}
					else
					{
						siteOnePaymentInfoData = new SiteOnePaymentInfoData();
						siteOnePaymentInfoData.setPaymentType("2");
					}
					if (response.getDetails() != null)
					{
						if (response.getDetails().getSubTotal() != null)
						{
							BigDecimal subTotVal = BigDecimal.valueOf(response.getDetails().getSubTotal()).setScale(2,
									RoundingMode.HALF_UP);
							final BigDecimal discountPrc = totDiscPrice.getValue().setScale(2, RoundingMode.HALF_UP);
							subTotVal = subTotVal.add(discountPrc);
							final PriceData subTotal = new PriceData();
							subTotal.setValue(subTotVal);
							subTotal.setFormattedValue(subTotVal.toString());
							orderdata.setSubTotal(subTotal);
						}
						if (response.getDetails().getFreightAmount() != null)
						{
							BigDecimal freightAmount = new BigDecimal(String.valueOf(response.getDetails().getFreightAmount()));
							freightAmount = freightAmount.setScale(2, RoundingMode.DOWN);
							orderdata.setDeliveryFreight(freightAmount.toPlainString());
							orderdata.setShippingFreight(freightAmount.toPlainString());
							orderdata.setFreight(freightAmount.toPlainString());
						}
						if (response.getDetails().getTaxTotal() != null)
						{
							final BigDecimal totTaxVal = BigDecimal.valueOf(response.getDetails().getTaxTotal()).setScale(2,
									RoundingMode.HALF_UP);
							final PriceData totalTax = new PriceData();
							totalTax.setValue(totTaxVal);
							totalTax.setFormattedValue(totTaxVal.toString());
							orderdata.setTotalTax(totalTax);
						}
						if (response.getDetails().getShipmentTotal() != null)
						{
							final BigDecimal shipTotVal = BigDecimal.valueOf(response.getDetails().getShipmentTotal()).setScale(2,
									RoundingMode.HALF_UP);
							final PriceData total = new PriceData();
							total.setValue(shipTotVal);
							total.setFormattedValue(shipTotVal.toString());
							orderdata.setTotalPriceWithTax(total);
						}
					}
					if (siteOnePaymentInfoData != null)
					{
						orderdata.setSiteOnePaymentInfoData(siteOnePaymentInfoData);
					}
					if (accountPayment != null)
					{
						orderdata.setSiteOnePOAPaymentInfoData(accountPayment);
					}
					if (billAdData != null)
					{
						final StringBuilder formattedAdrsBuilder = new StringBuilder();
						if (StringUtils.isNotBlank(billAdData.getLine1()))
						{
							formattedAdrsBuilder.append(billAdData.getLine1().strip());
						}
						if (StringUtils.isNotBlank(billAdData.getLine2()))
						{
							if (formattedAdrsBuilder.length() > 0)
							{
								formattedAdrsBuilder.append(", ");
							}
							formattedAdrsBuilder.append(billAdData.getLine2().strip());
						}
						if (StringUtils.isNotBlank(billAdData.getTown()))
						{
							if (formattedAdrsBuilder.length() > 0)
							{
								formattedAdrsBuilder.append(", ");
							}
							formattedAdrsBuilder.append(billAdData.getTown().strip());
						}
						if (StringUtils.isNotBlank(paymentData.getCity()))
						{
							if (formattedAdrsBuilder.length() > 0)
							{
								formattedAdrsBuilder.append(", ");
							}
							formattedAdrsBuilder.append(paymentData.getCity().strip());
						}
						if (StringUtils.isNotBlank(billAdData.getPostalCode()))
						{
							if (formattedAdrsBuilder.length() > 0)
							{
								formattedAdrsBuilder.append(", ");
							}
							formattedAdrsBuilder.append(billAdData.getPostalCode().strip());
						}
						billAdData.setFormattedAddress(formattedAdrsBuilder.toString());
						if (StringUtils.isNotBlank(billAdData.getFormattedAddress()) && orderdata.getBillingAddress() != null)
						{
							orderdata.getBillingAddress().setFormattedAddress(billAdData.getFormattedAddress());
						}
					}

				}
			}
		}
		return orderdata;
	}


	@Override
	public List<OrderData> ordersInTransit()
	{
		List<OrderData> orderDataList = null;
		final List<OrderModel> orderModelList = ((SiteOneCustomerAccountService) customerAccountService).getOrdersInTransit();

		if (null != orderModelList)
		{
			orderDataList = getOrderConverter().convertAll(orderModelList);
		}

		return orderDataList;
	}

	@Override
	public List<OrderHistoryData> getRecentOrders(final String UnitId, final Integer numberOfOrders)
	{
		List<OrderHistoryData> recentOrderList = null;
		final List<OrderModel> orderModelList = ((SiteOneCustomerAccountService) customerAccountService).getRecentOrders(UnitId,
				numberOfOrders);

		if (null != orderModelList)
		{
			recentOrderList = getOrderHistoryConverter().convertAll(orderModelList);
		}

		return recentOrderList;
	}

	@Override
	public MasterHybrisOrderData getOrdersWithSameHybrisOrderNumber(final String hybrisOrderNumber)
	{
		final MasterHybrisOrderData masterHybrisOrderData = new MasterHybrisOrderData();
		List<OrderData> orderDataList = null;
		final List<OrderModel> orderModelList = ((SiteOneCustomerAccountService) customerAccountService)
				.getOrdersWithSameHybrisOrderNumber(hybrisOrderNumber);
		if (!CollectionUtils.isEmpty(orderModelList) && orderModelList.size() > 1)
		{
			masterHybrisOrderData.setMasterOrderNumber(hybrisOrderNumber);
			orderDataList = getOrderConverter().convertAll(orderModelList);
			if (!CollectionUtils.isEmpty(orderDataList))
			{
				final Map<String, List<ConsignmentEntryData>> pickupEntries = new LinkedHashMap<>();
				final Map<String, List<ConsignmentEntryData>> deliveryEntries = new LinkedHashMap<>();
				final List<ConsignmentEntryData> shippingEntries = new ArrayList<>();
				String pickupInstructions = null;
				String deliveryInstructions = null;
				String shippingInstructions = null;
				CustomerData pickupContact = null;
				CustomerData deliveryContact = null;
				CustomerData shippingContact = null;
				AddressData deliveryAddress = null;
				AddressData shippingAddress = null;
				double deliveryFee = 0.0d;
				double shippingFee = 0.0d;
				double tax = 0.0d;
				double subtotal = 0.0d;
				double total = 0.0d;

				for (final OrderData orderData : orderDataList)
				{
					//uom enhancements changes
					if (CollectionUtils.isNotEmpty(orderData.getUnconsignedEntries()))
					{
						siteOneProductFacade.getPriceUpdateForHideUomEntry(orderData, false);
					}
					if (!CollectionUtils.isEmpty(orderData.getConsignments()))
					{
						for (final ConsignmentData consignmentData : orderData.getConsignments())
						{
							if (null != consignmentData && !StringUtils.isBlank(consignmentData.getDeliveryMode()))
							{
								if (consignmentData.getDeliveryMode().equalsIgnoreCase("Pick-up")
										|| consignmentData.getDeliveryMode().equalsIgnoreCase("PICKUP")
										|| consignmentData.getDeliveryMode().equalsIgnoreCase("Future Pick-up"))
								{
									final String pickupMapKey = consignmentData.getPointOfService().getName() + PIPE
											+ consignmentData.getPointOfService().getAddress().getLine1() + PIPE
											+ consignmentData.getPointOfService().getAddress().getTown() + ", "
											+ consignmentData.getPointOfService().getAddress().getRegion().getIsocodeShort() + " "
											+ consignmentData.getPointOfService().getAddress().getPostalCode() + PIPE
											+ consignmentData.getPointOfService().getAddress().getPhone();

									if (pickupEntries.isEmpty())
									{
										pickupEntries.put(pickupMapKey, consignmentData.getEntries());
									}
									else
									{
										if (pickupEntries.containsKey(pickupMapKey))
										{
											final List<ConsignmentEntryData> tempEntryList = pickupEntries.get(pickupMapKey);
											tempEntryList.addAll(consignmentData.getEntries());
											pickupEntries.put(pickupMapKey, tempEntryList);
										}
										else
										{
											pickupEntries.put(pickupMapKey, consignmentData.getEntries());
										}
									}

									if (null == pickupInstructions)
									{
										pickupInstructions = consignmentData.getSpecialInstructions();
									}
									if (null == pickupContact)
									{
										pickupContact = orderData.getContactPerson();
									}

								}
								if (consignmentData.getDeliveryMode().equalsIgnoreCase("Delivery")
										|| consignmentData.getDeliveryMode().equalsIgnoreCase("Store Delivery")
										|| consignmentData.getDeliveryMode().equalsIgnoreCase("Standard Delivery"))
								{
									final String deliveryMapKey = consignmentData.getPointOfService().getName();
									if (deliveryEntries.isEmpty())
									{
										deliveryEntries.put(deliveryMapKey, consignmentData.getEntries());
									}
									else
									{
										if (deliveryEntries.containsKey(deliveryMapKey))
										{
											final List<ConsignmentEntryData> tempEntryList = deliveryEntries.get(deliveryMapKey);
											tempEntryList.addAll(consignmentData.getEntries());
											deliveryEntries.put(deliveryMapKey, tempEntryList);
										}
										else
										{
											deliveryEntries.put(deliveryMapKey, consignmentData.getEntries());
										}
									}

									if (null == deliveryInstructions)
									{
										deliveryInstructions = consignmentData.getSpecialInstructions();
									}
									if (null == deliveryContact)
									{
										deliveryContact = orderData.getContactPerson();
									}
									if (null == deliveryAddress)
									{
										deliveryAddress = consignmentData.getConsignmentAddress();
									}
									if (!StringUtils.isBlank(consignmentData.getFreight()))
									{
										deliveryFee = deliveryFee + Double.valueOf(consignmentData.getFreight().contains(DOLLAR)
												? consignmentData.getFreight().replaceAll(PRICE_REGEX, "")
												: consignmentData.getFreight());
									}
								}
								if (consignmentData.getDeliveryMode().equalsIgnoreCase("Direct ship")
										|| consignmentData.getDeliveryMode().equalsIgnoreCase("Shipping"))
								{
									shippingEntries.addAll(consignmentData.getEntries());
									if (null == shippingInstructions)
									{
										shippingInstructions = consignmentData.getSpecialInstructions();
									}
									if (null == shippingContact)
									{
										shippingContact = orderData.getContactPerson();
									}
									if (null == shippingAddress)
									{
										shippingAddress = consignmentData.getConsignmentAddress();
									}
									if (!StringUtils.isBlank(consignmentData.getFreight()))
									{
										shippingFee = shippingFee + Double.valueOf(consignmentData.getFreight().contains(DOLLAR)
												? consignmentData.getFreight().replaceAll(PRICE_REGEX, "")
												: consignmentData.getFreight());
									}
								}
								if (!StringUtils.isBlank(consignmentData.getTax()))
								{
									tax = tax + Double.valueOf(
											consignmentData.getTax().contains(DOLLAR) ? consignmentData.getTax().replaceAll(PRICE_REGEX, "")
													: consignmentData.getTax());
								}
								if (!StringUtils.isBlank(consignmentData.getSubTotal()))
								{
									subtotal = subtotal + Double.valueOf(consignmentData.getSubTotal().contains(DOLLAR)
											? consignmentData.getSubTotal().replaceAll(PRICE_REGEX, "")
											: consignmentData.getSubTotal());
								}
								if (!StringUtils.isBlank(consignmentData.getTotal()))
								{
									total = total + Double.valueOf(consignmentData.getTotal().contains(DOLLAR)
											? consignmentData.getTotal().replaceAll(PRICE_REGEX, "")
											: consignmentData.getTotal());
								}
							}
						}
					}
					if (null != orderData.getTotalDiscountAmount())
					{
						masterHybrisOrderData.setTotalDiscountAmount(orderData.getTotalDiscountAmount());
					}
					if (null != orderData.getBillingAddress())
					{
						masterHybrisOrderData.setBillingAddress(orderData.getBillingAddress());
					}

					if (null != orderData.getSiteOnePOAPaymentInfoData())
					{
						masterHybrisOrderData.setSiteOnePOAPaymentInfoData(orderData.getSiteOnePOAPaymentInfoData());
					}
					if (null != orderData.getSiteOnePaymentInfoData())
					{
						masterHybrisOrderData.setSiteOnePaymentInfoData(orderData.getSiteOnePaymentInfoData());
					}
				}

				masterHybrisOrderData.setPickupInstructions(pickupInstructions);
				masterHybrisOrderData.setDeliveryInstructions(deliveryInstructions);
				masterHybrisOrderData.setShippingInstructions(shippingInstructions);
				masterHybrisOrderData.setPickupContact(pickupContact);
				masterHybrisOrderData.setDeliveryContact(deliveryContact);
				masterHybrisOrderData.setShippingContact(shippingContact);
				masterHybrisOrderData.setDeliveryAddress(deliveryAddress);
				masterHybrisOrderData.setShippingAddress(shippingAddress);
				masterHybrisOrderData.setPickupEntries(pickupEntries);
				masterHybrisOrderData.setDeliveryEntries(deliveryEntries);
				masterHybrisOrderData.setShippingEntries(shippingEntries);


				BigDecimal roundedDeliveryFee = BigDecimal.valueOf(deliveryFee);
				roundedDeliveryFee = roundedDeliveryFee.setScale(2, BigDecimal.ROUND_HALF_UP);
				masterHybrisOrderData.setDeliveryFee(roundedDeliveryFee);

				BigDecimal roundedShippingFee = BigDecimal.valueOf(shippingFee);
				roundedShippingFee = roundedShippingFee.setScale(2, BigDecimal.ROUND_HALF_UP);
				masterHybrisOrderData.setShippingFee(roundedShippingFee);

				masterHybrisOrderData.setTax(tax);
				masterHybrisOrderData.setSubtotal(subtotal);
				masterHybrisOrderData.setTotal(total);

			}
		}
		return masterHybrisOrderData;

	}

	@Override
	public void getListDetailsForUser(final Model model)
	{
		if (getUserService().getCurrentUser() != null && !getUserService().isAnonymousUser(getUserService().getCurrentUser()))
		{

			final boolean isAssembly = false;
			final List<SavedListData> allWishlist = siteoneSavedListFacade.getAllSavedListForEdit(isAssembly);

			String wishlistName = null;
			if (CollectionUtils.isNotEmpty(allWishlist) && allWishlist.size() == 1)
			{
				wishlistName = allWishlist.get(0).getCode();
			}

			if (CollectionUtils.isEmpty(allWishlist))
			{
				model.addAttribute("createWishList", "true");
			}


			model.addAttribute("wishlistName", wishlistName);
			model.addAttribute("allWishlist", allWishlist);
			model.addAttribute("loggedUser", "true");
		}
	}

	@Override
	public void updateFulfillmentAndPaymentInfoForApprovalOrders(final List<B2BOrderApprovalData> orderApprovalList)
	{
		if (!CollectionUtils.isEmpty(orderApprovalList))
		{
			for (final B2BOrderApprovalData b2bOrderApprovalData : orderApprovalList)
			{
				if (null != b2bOrderApprovalData && null != b2bOrderApprovalData.getB2bOrderData())
				{
					updateFulfillmentAndPaymentInfoForApprovalOrder(b2bOrderApprovalData);
				}
			}
		}
	}

	@Override
	public void updateFulfillmentAndPaymentInfoForApprovalOrder(final B2BOrderApprovalData b2bOrderApprovalData)
	{
		final OrderModel orderModel = siteOneOrderService.getOrderForCode(b2bOrderApprovalData.getB2bOrderData().getCode());
		if (null != orderModel)
		{
			b2bOrderApprovalData.getB2bOrderData().setOrderType(orderModel.getOrderType().getCode());
			if (CollectionUtils.isNotEmpty(orderModel.getPaymentInfoList()))
			{
				final SiteoneCreditCardPaymentInfoModel paymentInfo = orderModel.getPaymentInfoList().get(0);
				b2bOrderApprovalData.getB2bOrderData()
						.setSiteOnePaymentInfoData(siteOnePaymentInfoDataConverter.convert(paymentInfo));

			}
			else if (CollectionUtils.isNotEmpty(orderModel.getPoaPaymentInfoList()))
			{
				final SiteonePOAPaymentInfoModel poaPaymentInfo = orderModel.getPoaPaymentInfoList().get(0);
				b2bOrderApprovalData.getB2bOrderData()
						.setSiteOnePOAPaymentInfoData(siteOnePOAPaymentInfoDataConverter.convert(poaPaymentInfo));
			}
			else
			{
				final SiteOnePaymentInfoData paymentData = new SiteOnePaymentInfoData();
				paymentData.setPaymentType("2");
				b2bOrderApprovalData.getB2bOrderData().setSiteOnePaymentInfoData(paymentData);
			}
		}
	}

	@Override
	public int getPendingApprovalCount(final PageableData pageableData)
	{
		final SearchPageData<B2BOrderApprovalData> searchPageData = b2BOrderFacade
				.getPagedOrdersForApproval(new WorkflowActionType[]
				{ WorkflowActionType.START }, pageableData);
		int count = 0;
		for (final B2BOrderApprovalData b2BOrderApprovalData : searchPageData.getResults())
		{
			if (b2BOrderApprovalData.getB2bOrderData().getStatusDisplay().equalsIgnoreCase(PENDING_APPROVAL))
			{
				count++;
			}
		}
		return count;
	}

	@Override
	public boolean updatePurchaseOrderNumber(final String orderNumber, final String PO, final String status)
	{
		final BaseStoreModel baseStoreModel = getBaseStoreService().getCurrentBaseStore();
		final OrderModel order = getCustomerAccountService().getOrderForCode(orderNumber, baseStoreModel);
		if (null != status && null != order)
		{

			if (null != order.getOrderingAccount().getIsPONumberRequired() && order.getOrderingAccount().getIsPONumberRequired())
			{
				if (!StringUtils.isBlank(order.getOrderingAccount().getPoRegex())
						&& Pattern.matches(order.getOrderingAccount().getPoRegex(), PO))
				{
					return updatePONumber(order, PO, status);
				}
				else if (StringUtils.isBlank(order.getOrderingAccount().getPoRegex()))
				{
					return updatePONumber(order, PO, status);
				}
			}
			else
			{
				return updatePONumber(order, PO, status);

			}
		}

		return false;
	}

	public boolean updatePONumber(final OrderModel order, final String po, final String status)
	{
		if (status.equalsIgnoreCase("update"))
		{
			order.setPurchaseOrderNumber(po);

		}
		else if (status.equalsIgnoreCase("delete"))
		{
			order.setPurchaseOrderNumber(null);
		}
		getModelService().save(order);
		return true;
	}

	@Override
	public B2BOrderApprovalData setOrderApprovalDecision(final B2BOrderApprovalData b2bOrderApprovalData)
	{
		final WorkflowActionModel workflowActionModel = getB2bWorkflowIntegrationService()
				.getActionForCode(b2bOrderApprovalData.getWorkflowActionModelCode());
		if ((workflowActionModel != null)
				&& !Objects.equals(workflowActionModel.getPrincipalAssigned(), getUserService().getCurrentUser()))
		{
			throw new PrincipalAssignedValidationException("Assigned principal of WorkflowAction does not match current user");
		}
		if (workflowActionModel != null)
		{
			final B2BApprovalProcessModel attachment = (B2BApprovalProcessModel) CollectionUtils
					.find(workflowActionModel.getAttachmentItems(), PredicateUtils.instanceofPredicate(B2BApprovalProcessModel.class));
			if (null != attachment.getOrder())
			{
				final List<WorkflowActionModel> workflowAction = getB2bWorkflowIntegrationService()
						.getWorkflowForOrder(attachment.getOrder()).getActions();
				if (CollectionUtils.isNotEmpty(workflowAction))
				{
					for (final WorkflowActionModel actionModel : workflowAction)
					{
						if (actionModel.getName().equalsIgnoreCase(APPROVAL)
								&& !(actionModel.getStatus().getCode().equalsIgnoreCase(WorkflowActionStatus.COMPLETED.getCode())))
						{
							getB2bWorkflowIntegrationService().decideAction(actionModel, B2BWorkflowIntegrationService.DECISIONCODES
									.valueOf(b2bOrderApprovalData.getSelectedDecision().toUpperCase()).name());
						}
					}
				}
				if (null != b2bOrderApprovalData.getApprovalComments())
				{
					attachment.getOrder().setApprovalComments(b2bOrderApprovalData.getApprovalComments());
					getModelService().save(attachment.getOrder());
				}
				((SiteOneCustomerAccountService) customerAccountService).getDuplicateApprovalOrdersToRemove(attachment.getOrder());



			}
		}

		return getB2bOrderApprovalDataConverter().convert(workflowActionModel);
	}

	/**
	 * @return the b2bOrderApprovalDataConverter
	 */
	public Converter<WorkflowActionModel, B2BOrderApprovalData> getB2bOrderApprovalDataConverter()
	{
		return b2bOrderApprovalDataConverter;
	}

	/**
	 * @param b2bOrderApprovalDataConverter
	 *           the b2bOrderApprovalDataConverter to set
	 */
	public void setB2bOrderApprovalDataConverter(
			final Converter<WorkflowActionModel, B2BOrderApprovalData> b2bOrderApprovalDataConverter)
	{
		this.b2bOrderApprovalDataConverter = b2bOrderApprovalDataConverter;
	}

	/**
	 * @return the b2bWorkflowIntegrationService
	 */
	public B2BWorkflowIntegrationService getB2bWorkflowIntegrationService()
	{
		return b2bWorkflowIntegrationService;
	}

	/**
	 * @param b2bWorkflowIntegrationService
	 *           the b2bWorkflowIntegrationService to set
	 */
	public void setB2bWorkflowIntegrationService(final B2BWorkflowIntegrationService b2bWorkflowIntegrationService)
	{
		this.b2bWorkflowIntegrationService = b2bWorkflowIntegrationService;
	}

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}
	
	/**
	 * @return the b2bUnitFacade
	 */
	public B2BUnitFacade getB2bUnitFacade()
	{
		return b2bUnitFacade;
	}

	/**
	 * @param b2bUnitFacade the b2bUnitFacade to set
	 */
	public void setB2bUnitFacade(B2BUnitFacade b2bUnitFacade)
	{
		this.b2bUnitFacade = b2bUnitFacade;
	}
	
	/**
	 * @return the siteOneFeatureSwitchCacheService
	 */
	public SiteOneFeatureSwitchCacheService getSiteOneFeatureSwitchCacheService()
	{
		return siteOneFeatureSwitchCacheService;
	}

	/**
	 * @param siteOneFeatureSwitchCacheService the siteOneFeatureSwitchCacheService to set
	 */
	public void setSiteOneFeatureSwitchCacheService(SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService)
	{
		this.siteOneFeatureSwitchCacheService = siteOneFeatureSwitchCacheService;
	}
}
