package com.siteone.v2.controller;

import de.hybris.platform.acceleratorfacades.flow.impl.SessionOverrideCheckoutFlowFacade;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.ewallet.data.SiteOneEwalletData;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.order.OrderFacade;
import de.hybris.platform.commercefacades.order.data.AbstractOrderData;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commerceservices.enums.SalesApplication;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.data.SiteOnePOAPaymentInfoData;
import de.hybris.platform.commercefacades.user.data.SiteOnePaymentInfoData;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commercewebservicescommons.dto.order.CartWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.order.OrderWsDTO;
import de.hybris.platform.commerceservices.enums.SalesApplication;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import de.hybris.platform.servicelayer.session.SessionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.siteone.commerceservice.checkout.data.OrderReviewInfoDTO;
import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.ewallet.dao.impl.DefaultSiteOneEwalletDao;
import com.siteone.core.model.SiteoneEwalletCreditCardModel;
import com.siteone.core.order.services.SiteOneOrderService;
import com.siteone.facades.cart.SiteOneCartFacade;
import com.siteone.facades.checkout.SiteOneB2BCheckoutFacade;
import com.siteone.facades.constants.CreditCardNameMapping;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.utils.CayanBoarcardResponseForm;
import com.siteone.utils.SiteOneEwalletDataUtil;
import com.siteone.utils.SiteOnePaymentInfoUtil;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.SiteoneCCPaymentAuditLogModel;
import com.siteone.core.model.LinkToPayCayanResponseModel;
import java.util.Date;
import de.hybris.platform.servicelayer.time.TimeService;
import java.util.Calendar;
import com.siteone.core.services.SiteoneLinkToPayAuditLogService;

/**
 * @author pelango
 *
 */
@Controller
@RequestMapping(value = "/{baseSiteId}/checkout/order-summary")
@Tag(name = "Siteone Order Checkout")
public class SiteOneOrderSummaryCheckoutController extends BaseController {

	private static final Logger LOG = Logger.getLogger(SiteOneOrderSummaryCheckoutController.class);

	public static final String VOUCHER_FORM = "voucherForm";
	private static final String PAYMENTTYPE_THREE = "3";
	private static final String PAYMENTTYPE_ONE = "1";
	private static final String FAILURE = "Failure";
	private static final String PAYMENTTYPE_TWO = "2";
	private static final String MIXEDCART_ENABLED_BRANCHES = "MixedCartEnabledBranches";

	@Resource(name = "b2bCheckoutFacade")
	private CheckoutFacade b2bCheckoutFacade;
	
	@Resource(name = "accProductFacade")
	private ProductFacade productFacade;
	
	@Resource(name = "priceDataFactory")
	private PriceDataFactory priceDataFactory;
	
	@Resource(name = "siteOneOrderService")
	private SiteOneOrderService siteOneOrderService;
	
	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;
	
	@Resource(name = "defaultSiteOnePaymentInfoUtil")
	private SiteOnePaymentInfoUtil siteOnePaymentInfoUtil;
	
	@Resource(name = "orderFacade")
	private OrderFacade orderFacade;

	@Resource(name = "defaultSiteOneEwalletDao")
	private DefaultSiteOneEwalletDao defaultSiteOneEwalletDao;
	
	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;
	
	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;
	
	@Resource(name = "creditCardNameMapping")
	private CreditCardNameMapping creditCardNameMapping;
	
	@Resource(name = "defaultSiteOneEwalletDataUtil")
	private SiteOneEwalletDataUtil siteOneEwalletDataUtil;
	
	@Resource(name = "storeFinderFacade")
	private SiteOneStoreFinderFacade storeFinderFacade;
		
	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "userFacade")
	private UserFacade userFacade;
	
	@Resource(name = "cartFacade")
	private CartFacade cartFacade;
	
	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;
	
	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
	
	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;
	
	@Resource(name = "siteoneLinkToPayAuditLogService")
	private SiteoneLinkToPayAuditLogService siteoneLinkToPayAuditLogService;
	
	@Resource(name = "timeService")
	private TimeService timeService;
	
	@PostMapping("/orderReview")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "orderReview", summary = "Get the Order reivew details", description = "Get the Order reivew details")
	public OrderReviewInfoDTO orderReview(@RequestParam(value = "storeId", required = true)	final String storeId,
			@Parameter(description = "unitId") @RequestParam(required = false) final String unitId,
			@RequestParam(value = "paymentType", required = true)	final String paymentType,
			@RequestParam(value = "vaultToken", required = false)	final String vaultToken,
			@Parameter(description = "eWallet", required = false) @RequestBody CayanBoarcardResponseForm cayanResponseForm, 
			@RequestParam(value = "isOneTimeCard", defaultValue="false") final Boolean isOneTimeCard,
			@RequestParam(value = "guid", required = false)	final String guid) throws CommerceCartModificationException
	{
		try
		{
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);		
			final B2BUnitData unit;
			if (!StringUtils.isEmpty(unitId))
			{
				unit = b2bUnitFacade.getUnitForUid(unitId);
			} else {
				unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
			}		
			storeSessionFacade.setSessionShipTo(unit);
			((SiteOneCartFacade) cartFacade).restoreSessionCart(guid);
			if (null != storeId && siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(MIXEDCART_ENABLED_BRANCHES,storeId)){
				sessionService.setAttribute("isMixedCartEnabled","mixedcart");
			}
					
			final CartData cartData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getCheckoutCart();
			((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).updateDeliveryFee(cartData);
			if (Integer.parseInt(paymentType) == 3)
			{
				final SiteOnePaymentInfoData siteOnePaymentData = new SiteOnePaymentInfoData();
				siteOnePaymentData.setPaymentType(PAYMENTTYPE_THREE);
				if(isOneTimeCard){
					final String cardNumber = cayanResponseForm.getCardNumber();
					siteOnePaymentData.setCardNumber(cardNumber.substring(cardNumber.length() - 4));
					siteOnePaymentData.setApplicationLabel(creditCardNameMapping.getCardTypeShortName().get(cayanResponseForm.getCardType()));
				}else{
					final SiteoneEwalletCreditCardModel ewalletCardData = defaultSiteOneEwalletDao.getCreditCardDetails(vaultToken);
					siteOnePaymentData.setCardNumber(ewalletCardData.getLast4Digits());
					siteOnePaymentData.setApplicationLabel(ewalletCardData.getCreditCardType());
				}
				cartData.setSiteOnePaymentInfoData(siteOnePaymentData);
			}
			else if (Integer.parseInt(paymentType) == 1)
			{
				final B2BUnitModel b2bUnit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
				final SiteOnePOAPaymentInfoData siteOnePaymentData = new SiteOnePOAPaymentInfoData();
				siteOnePaymentData.setPaymentType(PAYMENTTYPE_ONE);
				siteOnePaymentData.setTermsCode(b2bUnit.getCreditTermCode());
				cartData.setSiteOnePOAPaymentInfoData(siteOnePaymentData);
			}
			else{
				final SiteOnePaymentInfoData siteOnePaymentData = new SiteOnePaymentInfoData();
				siteOnePaymentData.setPaymentType(PAYMENTTYPE_TWO);
				cartData.setSiteOnePaymentInfoData(siteOnePaymentData);
			}
			final List<String> showVoucherList = new ArrayList<>();
			if (null != cartData.getAppliedVouchers())
			{
				showVoucherList.addAll(cartData.getAppliedVouchers());
			}
			
			if (cartData.getEntries() != null && !cartData.getEntries().isEmpty())
			{
				for (final OrderEntryData entry : cartData.getEntries())
				{
					final String productCode = entry.getProduct().getCode();
					final ProductData product = productFacade.getProductForCodeAndOptions(productCode,
							Arrays.asList(ProductOption.BASIC, ProductOption.URL, ProductOption.PRICE, ProductOption.VARIANT_MATRIX_BASE,
									ProductOption.PRICE_RANGE, ProductOption.CATEGORIES, ProductOption.PROMOTIONS,
									ProductOption.STOCK, ProductOption.AVAILABILITY_MESSAGE));
					entry.setProduct(product);
					if (null != entry.getProduct().getCouponCode() && showVoucherList.contains(entry.getProduct().getCouponCode()))
					{
						showVoucherList.remove(entry.getProduct().getCouponCode());
					}
				}
				
				List<ProductData> productDataList = siteOneProductFacade.updateSalesInfoBackorderForOrderEntry(cartData.getEntries());
				for (final OrderEntryData entryData : cartData.getEntries())
				{
					if(CollectionUtils.isNotEmpty(productDataList)) {
						productDataList.forEach(product -> {
							if(product.getCode().equalsIgnoreCase(entryData.getProduct().getCode())) {
								entryData.setProduct(product);
							}
						});
					}
				}
			}
			if(!CollectionUtils.isEmpty(cartData.getPickupAndDeliveryEntries()) && !CollectionUtils.isEmpty(cartData.getShippingOnlyEntries()))
			{
				final Map<String, Boolean> fulfilmentStatus = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).populateFulfilmentStatus(cartData);
				((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).populateFreights(cartData, fulfilmentStatus);
			}
			final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
			OrderReviewInfoDTO reviewInfoDTO = new OrderReviewInfoDTO();
			MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
			mapperFactory.classMap(CartData.class, CartWsDTO.class);
		    MapperFacade mapper = mapperFactory.getMapperFacade();
		    CartWsDTO cartWsDTO = mapper.map(cartData, CartWsDTO.class);
		    reviewInfoDTO.setCartData(cartWsDTO);
		    reviewInfoDTO.setShowVoucherList(showVoucherList);
		    if(null != customer)
		    {
		    	reviewInfoDTO.setNeedsOrderApproval(customer.getNeedsOrderApproval());
		    }    
		    
		    
			if (userFacade.isAnonymousUser() && Integer.parseInt(paymentType) == 3)
			{
				if (null != cartData.getB2bCustomerData() && null != cartData.getB2bCustomerData().getDisplayUid()) 
				{
					String originalID = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade)
							.getOriginalEmail(cartData.getB2bCustomerData().getDisplayUid());

					if (null != cayanResponseForm && (cayanResponseForm.getStatus().equalsIgnoreCase("FAILED")
							|| cayanResponseForm.getStatus().equalsIgnoreCase("DECLINED"))) {
						final LinkToPayCayanResponseModel cayanResponse = new LinkToPayCayanResponseModel();

						cayanResponse.setCustomerName("Guest User");
						cayanResponse.setEmail(originalID);
						cayanResponse.setToEmails(originalID);

						cayanResponse.setLast4Digits(String.valueOf(cayanResponseForm.getCardNumber()
								.substring(cayanResponseForm.getCardNumber().length() - 4)));
						cayanResponse.setCreditCardZip(cayanResponseForm.getZipCode());
						cayanResponse.setTransactionStatus(cayanResponseForm.getStatus());
						cayanResponse.setTransactionStatus("Decline");

						cayanResponse.setOrderNumber(originalID);
						cayanResponse.setCartID(originalID);

						siteoneLinkToPayAuditLogService.saveSiteoneCCAuditLog(cayanResponse);

						LOG.error("Invalid Boardcard response 1 : " + cayanResponseForm.getStatus());

						final SiteoneCCPaymentAuditLogModel auditModel = ((SiteOneCustomerFacade) customerFacade)
								.getSiteoneCCAuditDetails(originalID);
						if (null != auditModel) {
							final int declineCountLimit = Integer.parseInt(
									siteOneFeatureSwitchCacheService.getValueForSwitch("PaymentDeclineCount"));
							final int declineCount = auditModel.getDeclineCount().intValue();
							LOG.error("review order declineCountLimit :" + declineCountLimit + "declineCount : " + declineCount);
							if (declineCount < declineCountLimit) {
								reviewInfoDTO.setIsCreditPaymentBlockedForGuest(false);
							} else {
								LOG.error("Cayan response is declined in review order ");
								try {
									final int creditCardBlockedSpan = Integer.parseInt(siteOneFeatureSwitchCacheService
											.getValueForSwitch("CreditCardBlockedSpan"));

									final Date lastModification = auditModel.getModifiedtime();
									final Date currentDate = timeService.getCurrentTime();
									final Calendar threshold = Calendar.getInstance();
									threshold.setTime(currentDate);
									threshold.add(Calendar.HOUR, -creditCardBlockedSpan);
									if (lastModification.before(threshold.getTime())) {
										siteoneLinkToPayAuditLogService.resetSiteoneCCAuditLog(originalID);
										reviewInfoDTO.setIsCreditPaymentBlockedForGuest(false);
									} else {
										reviewInfoDTO.setIsCreditPaymentBlockedForGuest(true);
									}
								} catch (final Exception e) {
									LOG.error("Failed to add Credit Card details", e);
									reviewInfoDTO.setIsCreditPaymentBlockedForGuest(false);
								}
							}

						}
					} else {
						reviewInfoDTO.setIsCreditPaymentBlockedForGuest(false);
					}
				}
			} 
		    
			return reviewInfoDTO;
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method orderReview");
	    }
		
	}

	
	@PostMapping("/placeOrder")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "placeOrder", summary = "Submit the order", description = "Submit the order")
	public String placeOrder(@RequestParam(value = "storeId", required = true)	final String storeId,
			@Parameter(description = "unitId") @RequestParam(required = false) final String unitId,
			@RequestParam(value = "paymentType", required = true)	final String paymentType,
			@RequestParam(value = "vaultToken", required = false)	final String vaultToken,
			@Parameter(description = "eWallet", required = false) @RequestBody CayanBoarcardResponseForm cayanResponseForm, 
			@RequestParam(value = "isOneTimeCard", defaultValue="false") final Boolean isOneTimeCard,
			@RequestParam(value = "guid", required = false)	final String guid,
			@Parameter(description = "posId") @RequestParam(required = false) final String posId) 
					throws InvalidCartException, CommerceCartModificationException
	{
		Gson gson = new Gson();
        try
		{
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);		
			if(!userFacade.isAnonymousUser()){
				final B2BUnitData unit;
				if (!StringUtils.isEmpty(unitId))
				{
					unit = b2bUnitFacade.getUnitForUid(unitId);
				} else {
					unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
				}
				storeSessionFacade.setSessionShipTo(unit);
			} else{
				sessionService.setAttribute("guestUser","guest");
			}
			if(posId != null)
			{
				sessionService.setAttribute("geoPointStore", posId);
			}
			if (null != storeId && siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(MIXEDCART_ENABLED_BRANCHES,storeId)){
				sessionService.setAttribute("isMixedCartEnabled","mixedcart");
			}
			((SiteOneCartFacade) cartFacade).restoreSessionCart(guid);
			
			final CartData cartData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getCheckoutCart();
			if(cartData != null && !CollectionUtils.isEmpty(cartData.getPickupAndDeliveryEntries()) && !CollectionUtils.isEmpty(cartData.getShippingOnlyEntries()))
			{
				sessionService.setAttribute("isSplitMixedCartEnabledBranch", true);
			}
			if (Integer.parseInt(paymentType) == 3)
			{
				return gson.toJson(placeOnlinePaymentOrder(cartData,vaultToken,cayanResponseForm,isOneTimeCard));
			}
			
			if (Integer.parseInt(paymentType) == 1)
			{
				final B2BUnitModel b2bUnit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
				final SiteOnePOAPaymentInfoData siteOnePaymentData = new SiteOnePOAPaymentInfoData();
				siteOnePaymentData.setPaymentType(PAYMENTTYPE_ONE);
				siteOnePaymentData.setTermsCode(b2bUnit.getCreditTermCode());
				cartData.setSiteOnePOAPaymentInfoData(siteOnePaymentData);
				return gson.toJson(placePOAPaymentOrder(cartData));
			}
			
			final AbstractOrderData orderData;
            orderData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).placeOrder(SalesApplication.ECOMMOBILEAPP);
			orderData.setAppliedVouchers(cartData.getAppliedVouchers());
			
			return gson.toJson(orderData.getCode());

		}
		catch (final Exception e)
		{
			LOG.error("Failed to place Order", e);
			return gson.toJson(FAILURE);
            
		}
		
	}
	
	@GetMapping("/orderConfirmation")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "orderConfirmation", summary = "Display order confirmation message", description = "Display order confirmation message")
	public OrderWsDTO orderConfirmation(@RequestParam(value = "orderCode", required = true)	final String orderCode,@RequestParam(name = "kountSessionId", required = false) final String kountSessionId,
			@RequestParam(name = "transactionId", required = false) final String transactionId,@RequestParam(value = "storeId") final String storeId) throws CommerceCartModificationException
	{
		try
		{
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);
			if(kountSessionId != null && transactionId != null) {
				((SiteOneCustomerFacade) customerFacade).updateKountDetails(orderCode,transactionId,kountSessionId);
			}
			SessionOverrideCheckoutFlowFacade.resetSessionOverrides();
			final OrderData orderDetails;
			final OrderModel orderModel;
			orderDetails = orderFacade.getOrderDetailsForCode(orderCode);
			orderModel = siteOneOrderService.getOrderForCode(orderCode);
			
			for (final AbstractOrderEntryModel entry : orderModel.getEntries()) {
			if (orderDetails.getTotalPrice() != null) {
			 BigDecimal subTotal = BigDecimal.valueOf(orderDetails.getSubTotal().getValue().doubleValue());
		     BigDecimal totalPrice = BigDecimal.valueOf(orderDetails.getTotalPriceWithTax().getValue().doubleValue());
		     BigDecimal bigBagPrice = (entry.getBigBagInfo() != null 
		          && Boolean.TRUE.equals(entry.getBigBagInfo().getIsChecked()) 
		          && entry.getBigBagInfo().getLocalTotal() != null) 
		      ? entry.getBigBagInfo().getLocalTotal() 
		      : BigDecimal.ZERO;
		    
		    orderDetails.setSubTotal(createPrice(orderModel, subTotal.add(bigBagPrice).doubleValue()));
		    orderDetails.setTotalPriceWithTax(createPrice(orderModel, totalPrice.add(bigBagPrice).doubleValue()));		}
			}
			
			if(!CollectionUtils.isEmpty(orderDetails.getEntries())) {
				for (final OrderEntryData entry : orderDetails.getEntries()) {
					if(null != entry.getDeliveryPointOfService() && 
							null != entry.getProduct().getPickupHomeStoreInfo() && 
							null != entry.getProduct().getPickupNearbyStoreInfo() && 
							null != entry.getProduct().getDeliveryStoreInfo() && 
							null != entry.getProduct().getShippingStoreInfo() &&
							((!entry.getDeliveryPointOfService().getStoreId().equalsIgnoreCase(entry.getProduct().getPickupHomeStoreInfo().getStoreId())) || 
							(!entry.getDeliveryPointOfService().getStoreId().equalsIgnoreCase(entry.getProduct().getPickupNearbyStoreInfo().getStoreId())) || 
							(!entry.getDeliveryPointOfService().getStoreId().equalsIgnoreCase(entry.getProduct().getDeliveryStoreInfo().getStoreId())) ||
							(!entry.getDeliveryPointOfService().getStoreId().equalsIgnoreCase(entry.getProduct().getShippingStoreInfo().getStoreId()))))
						{
						((SiteOneCartFacade) cartFacade).updateFullfillmentMessage(entry.getProduct(), entry.getDeliveryPointOfService());
						((SiteOneCartFacade) cartFacade).updateShippingStore(entry.getProduct(), entry.getDeliveryPointOfService());
						}
				}
			}
			
			return getDataMapper().map(orderDetails, OrderWsDTO.class);
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method orderConfirmation");
	    }
		
	}
	
	protected PriceData createPrice(final AbstractOrderModel source, final Double val)
	{
		if (source == null)
		{
			throw new IllegalArgumentException("source order must not be null");
		}

		final CurrencyModel currency = source.getCurrency();
		if (currency == null)
		{
			throw new IllegalArgumentException("source order currency must not be null");
		}

		// Get double value, handle null as zero
		final double priceValue = val != null ? val.doubleValue() : 0d;

		return priceDataFactory.create(PriceDataType.BUY, BigDecimal.valueOf(priceValue), currency);
	}

	protected String placePOAPaymentOrder(final CartData cartData)
	{
		final SiteOnePOAPaymentInfoData siteOnePaymentData;
		siteOnePaymentData = cartData.getSiteOnePOAPaymentInfoData();
		AbstractOrderData orderData;
		try
		{
			orderData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).placeOrderWithPOAPayment(siteOnePaymentData, SalesApplication.ECOMMOBILEAPP);
			orderData.setAppliedVouchers(cartData.getAppliedVouchers());
		}
		catch (final Exception e)
		{
			LOG.error("Failed to place Order", e);
			return FAILURE;
		}
		
		return orderData.getCode();
	}
	
	protected String placeOnlinePaymentOrder(final CartData cartData,final String vaultToken,
				final CayanBoarcardResponseForm cayanResponseForm, final Boolean isOneTimeCard)
	{
		final PointOfServiceData pos = sessionService.getAttribute("sessionStore");
		final boolean flag = pos != null && pos.getHubStores() != null && pos.getHubStores().get(0) != null
				&& cartData.getOrderType() != null && cartData.getOrderType().equalsIgnoreCase("PARCEL_SHIPPING")
				&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("CCEnabledDCShippingBranches",
						pos.getHubStores().get(0));
		SiteOnePaymentInfoData siteOnePaymentData = null;
		if(!flag) 
		{
			if(isOneTimeCard){
				final SiteOneEwalletData ewalletData = siteOneEwalletDataUtil.convert(cayanResponseForm, null);
				siteOnePaymentData = siteOnePaymentInfoUtil.processPaymentInfoData(cayanResponseForm.getVaultToken(), 
						cartData, ewalletData);
			}else{
				siteOnePaymentData = siteOnePaymentInfoUtil.processPaymentInfoData(vaultToken, cartData, null);
			}
			

			if (null == siteOnePaymentData)
			{
				LOG.error("Invalid cayan response");
				return FAILURE;
			}
			
		}else {
			if(isOneTimeCard){
				sessionService.setAttribute("vaultToken",cayanResponseForm.getVaultToken());
				sessionService.setAttribute("isEwalletCard",false);
				final SiteOneEwalletData ewalletData = siteOneEwalletDataUtil.convert(cayanResponseForm, null);
				sessionService.setAttribute("ewalletData",ewalletData);
			}else{
				sessionService.setAttribute("vaultToken",vaultToken);
				sessionService.setAttribute("isEwalletCard",true);
			}
		}
		
		
		AbstractOrderData orderData;
		try
		{
			orderData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).placeOrderWithOnlinePayment(siteOnePaymentData, SalesApplication.ECOMMOBILEAPP);
			orderData.setAppliedVouchers(cartData.getAppliedVouchers());
		}
		catch (final Exception e)
		{
			LOG.error("Failed to place Order", e);
			return FAILURE;
		}
		
		return orderData.getCode();
	}
	
}