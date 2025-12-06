/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.siteone.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.coupon.data.CouponData;
import de.hybris.platform.commercefacades.order.data.ConsignmentData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.PromotionResultData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.order.payment.SiteoneCreditCardPaymentInfoModel;
import de.hybris.platform.core.model.order.payment.SiteonePOAPaymentInfoModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.Config;
import de.hybris.platform.search.restriction.SearchRestrictionService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.servicelayer.session.SessionService;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.tools.generic.NumberTool;

import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.customer.SiteOneCustomerAccountService;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.populators.PromotionPriceUtils;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.integration.open.order.data.OpenOrdersInfoResponseData;
import com.siteone.integration.open.order.data.OpenOrdersLandingPageRequestData;
import com.siteone.integration.open.order.data.OpenOrdersLandingPageResponseData;
import com.siteone.integration.open.order.data.OpenOrdersShipmentInfoResponseData;


/**
 * Velocity context for a order notification email.
 */
public class OrderNotificationEmailContext extends AbstractEmailContext<OrderProcessModel>
{
	private static final Logger LOG = Logger.getLogger(OrderNotificationEmailContext.class);
	public static final String REQ_DATE = "requestedDate";
	public static final String Discounted_Value = "discountedValue";
	public static final String Unit = "unit";
	private Converter<OrderModel, OrderData> orderConverter;
	private OrderData orderData;
	private CouponData couponData;
	private B2BCustomerModel customerModel;

	public static final String CC_EMAIL = "ccEmail";
	public static final String CC_DISPLAY_NAME = "ccDisplayName";
	private static final String ORDER_STATUS = "status";

	public static final String CC_EMAIL_SITEONE = "ccEmailSiteone";
	public static final String CC_SITEONE_DISPLAY_NAME = "ccSiteoneDisplayName";
	public static final String ACC_NO = "accountNum";

	private static final String FIRST_NAME = "firstName";
	private static final String SHOW_COUPON = "showCoupon";
	private static final String NUMBER_TOOL = "numberTool";
	private static final String CURRENCY_UNITPRICE_FORMAT = "unitpriceFormat";
	private static final String CURRENCY_UNITPRICE_FORMAT_VAL = Config.getString("currency.unitprice.formattedDigits", "#0.000");
	private static final String PAYMENTTYPE = "paymentType";
	private static final String CARDNUMBER = "cardNumber";
	private static final String CARDTYPE = "cardType";
	private static final String TERMSCODE = "termsCode";
	private static final String SHOWDELIVERYFEE = "showDeliveryFee";
	private static final String PICKUP_CONSIGNMENTS = "pickupConsignments";
	private static final String SHIPPING_CONSIGNMENTS = "deliveryConsignments";
	private static final String DELIVERY_CONSIGNMENTS = "shippingConsignments";
	private static final String IS_GUEST_CUSTOMER = "isGuestCustomer";
	private static final String EXEMPTDELIVERYFEE = "exemptDeliveryFee";
	private static final String APPROVAL_COMMENTS = "approvalComments";
	private static final String UNIT_ID = "unitId";
	private static final String SHIPMENT_NUMBER = "shipmentNumber";
	private static final String BRANCH_NUMBER = "branchNumber";
	private static final String SHIPMENT_COUNT = "shipmentCount";
	private static final String IS_SHIPPING_FEE_BRANCH = "isShippingFeeBranch";
	private static final String SITEONE_US_BASESTORE = "siteone";
	private static final String SITEONE_CA_BASESTORE = "siteoneCA";
	private static final String COUNTRY_ISO_CODE = "countryCode";
	private static final String BOOMI_PLATFORM = "boomiPlatformLinux";

	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "accProductFacade")
	private ProductFacade productFacade;
	
	@Resource(name = "storeFinderFacade")
	private SiteOneStoreFinderFacade storeFinderFacade;
	
	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;
	
	@Resource(name = "searchRestrictionService")
	private SearchRestrictionService searchRestrictionService;
	
	@Resource(name = "baseStoreService")
	protected BaseStoreService baseStoreService;
	
	@Resource(name = "sessionService")
	private SessionService sessionService;
	
	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
	
	@Resource(name = "customerAccountService")
	private CustomerAccountService customerAccountService;
	
	@Override
	public void init(final OrderProcessModel orderProcessModel, final EmailPageModel emailPageModel)
	{
		super.init(orderProcessModel, emailPageModel);
		PointOfServiceData posData = new PointOfServiceData();
		B2BUnitData b2bUnitData = new B2BUnitData();
		try {
			searchRestrictionService.disableSearchRestrictions();
   		if(orderProcessModel != null && orderProcessModel.getOrder() != null && orderProcessModel.getOrder().getPointOfService() != null
   				&& orderProcessModel.getOrder().getPointOfService().getStoreId() != null)
   		{
   				posData = storeFinderFacade.getStoreForId(orderProcessModel.getOrder().getPointOfService().getStoreId());
   			storeSessionFacade.setSessionStore(posData);
   			storeSessionFacade.setCurrentBaseStore(orderProcessModel.getOrder().getPointOfService().getDivision().getUid());
   		}
		}
		finally {
			searchRestrictionService.enableSearchRestrictions();
		}
		if(orderProcessModel != null && orderProcessModel.getOrder() != null && orderProcessModel.getOrder().getOrderingAccount() != null
				&& orderProcessModel.getOrder().getOrderingAccount().getUid() != null)
		{
			b2bUnitData = b2bUnitFacade.getUnitForUid(orderProcessModel.getOrder().getOrderingAccount().getUid());
			storeSessionFacade.setSessionShipTo(b2bUnitData);
			
			
			String shipmentNumber = null;
			String branchNumber = null;
			Integer shipmentCount = null;
			try {
	   		if (siteOneFeatureSwitchCacheService.getValueForSwitch("OrderApiTimespan") != null)
	   		{
	   		final Integer orderApiTiming = Integer.valueOf(siteOneFeatureSwitchCacheService.getValueForSwitch("OrderApiTimespan"));
	   		long start = System.currentTimeMillis(); 
	   		long end = start + orderApiTiming.intValue() * 1000;
	      		while(System.currentTimeMillis() < end)
	      		{
	      			final OpenOrdersLandingPageRequestData openOrderRequest = new OpenOrdersLandingPageRequestData();
	      			
	      			openOrderRequest.setSortBy(Integer.valueOf("0"));
	      			openOrderRequest.setSearchPeriod(Integer.valueOf("30"));	
	      			openOrderRequest.setSortDirection(Integer.valueOf("0"));
	      			openOrderRequest.setPage(Integer.valueOf("1"));
	      			openOrderRequest.setRows(Integer.valueOf("5"));
	      			openOrderRequest.setIncludeMobileProOrders(Boolean.TRUE);

	      			OpenOrdersLandingPageResponseData orderResponse = null;
	      			if (StringUtils.isNotBlank(orderProcessModel.getOrder().getOrderingAccount().getUid()))
	      			{
	      				String uid = orderProcessModel.getOrder().getOrderingAccount().getUid().trim().split("_")[0];
	         			orderResponse = ((SiteOneCustomerAccountService) customerAccountService)
	         					.getOpenOrderList(openOrderRequest, uid, getDivisionId(orderProcessModel.getOrder().getOrderingAccount().getUid()), Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));

	      			}
	      			
	         		if (orderResponse != null && CollectionUtils.isNotEmpty(orderResponse.getData()))
	         		{
	         			for (final OpenOrdersInfoResponseData data : orderResponse.getData())
	         			{
	         				if (data.getHybrisOrderNumber() != null && orderProcessModel.getOrder().getCode() != null && data.getHybrisOrderNumber().equalsIgnoreCase(orderProcessModel.getOrder().getCode()))
	         				{
	         					if (data.getBranchNumber() != null)
	         					{
	         						branchNumber = data.getBranchNumber();
	         					}
	         					if (CollectionUtils.isNotEmpty(data.getShipments()))
	         					{
	         						shipmentCount = Integer.valueOf(data.getShipments().size());
	         						OpenOrdersShipmentInfoResponseData shipment = data.getShipments().get(0);
	         						
	         						if (shipment.getShipmentNumber() != null)
	         						{
	         							shipmentNumber = shipment.getShipmentNumber();
	         							LOG.error("Shipment number found: " + shipmentNumber);
	         							break;
	         						}
	         						else
	         						{
	         							LOG.error("Shipment found but shipment number is null, will retry.... ");
	         						}
	         					}
	         				}
	         			}
	         		}
	      		else {
	      			continue;
	      		}
	      		
	      		if (shipmentNumber != null)
	      		{
	      			break;
	      		}
	      		
	      		try
	      		{
	      			Thread.sleep(5000);
	      		}
	      		catch (InterruptedException e)
	      		{
	      			LOG.error("Polling interrupted", e);
	      			Thread.currentThread().interrupt();
	         		break;
	      		}      		
	   		}
	      		
	   		long actualRunTime = System.currentTimeMillis() - start;
	   		double actualRunTimeSeconds = actualRunTime / 1000.0;
	   		if (shipmentNumber == null)
	   		{
	   			LOG.error("Shipment not found within the configured time interval of " + orderApiTiming + " seconds. Actual run time was " + actualRunTimeSeconds + " seconds.");
	   		}
	   		}
			}		
				catch (final NumberFormatException numberFormatException) {
				LOG.error("Number format exception occured", numberFormatException);
			}
			LOG.error("Final shipmentNumber is " + shipmentNumber);
			
			put(SHIPMENT_NUMBER, shipmentNumber);
			put(BRANCH_NUMBER, branchNumber);
			put(SHIPMENT_COUNT, String.valueOf(shipmentCount));
			put(UNIT_ID, orderProcessModel.getOrder().getOrderingAccount().getUid());
		}		 
		
		orderData = getOrderConverter().convert(orderProcessModel.getOrder());
		
		boolean showDeliveryFee = true;
		boolean isPickupConsignment = false;
		boolean isDeliveryConsignment = false;
		boolean isShippingConsignment = false;
		final List<ConsignmentData> pickupConsignments = new ArrayList<>();
		final List<ConsignmentData> deliveryConsignments = new ArrayList<>();
		final List<ConsignmentData> shippingConsignments = new ArrayList<>();
		for (final ConsignmentData consignmentData : orderData.getConsignments())
		{
			if (consignmentData.getDeliveryMode().equalsIgnoreCase("Pick-up")
					|| consignmentData.getDeliveryMode().equalsIgnoreCase("PICKUP")
					|| consignmentData.getDeliveryMode().equalsIgnoreCase("Future Pick-up")
					|| consignmentData.getDeliveryMode().equalsIgnoreCase(SiteoneCoreConstants.PICKUP_DELIVERYMODE_CODE))
			{
				isPickupConsignment = true;
				pickupConsignments.add(consignmentData);
			}
			if (consignmentData.getDeliveryMode().equalsIgnoreCase("Delivery")
					|| consignmentData.getDeliveryMode().equalsIgnoreCase("Store Delivery")
					|| consignmentData.getDeliveryMode().equalsIgnoreCase("Standard Delivery")
					|| consignmentData.getDeliveryMode().equalsIgnoreCase(SiteoneCoreConstants.DELIVERY_DELIVERYMODE_CODE))
			{
				isDeliveryConsignment = true;
				deliveryConsignments.add(consignmentData);
			}
			if (consignmentData.getDeliveryMode().equalsIgnoreCase("Direct ship")
					|| consignmentData.getDeliveryMode().equalsIgnoreCase("Shipping")
					|| consignmentData.getDeliveryMode().equalsIgnoreCase(SiteoneCoreConstants.SHIPPING_DELIVERYMODE_CODE))
			{
				isShippingConsignment = true;
				shippingConsignments.add(consignmentData);
			}
		}
		if(isDeliveryConsignment && isShippingConsignment && orderData.getShippingFreight() != null)
		{
			final NumberFormat fmt = NumberFormat.getCurrencyInstance(Locale.US);
			Double shippingFreight = Double.parseDouble(orderData.getShippingFreight());
			BigDecimal totalPriceWithTax = orderData.getTotalPriceWithTax().getValue();
			totalPriceWithTax = totalPriceWithTax.add(BigDecimal.valueOf(shippingFreight));
			orderData.getTotalPriceWithTax().setValue(totalPriceWithTax);
			orderData.getTotalPriceWithTax().setFormattedValue(fmt.format(totalPriceWithTax));
		}
		put(PICKUP_CONSIGNMENTS, pickupConsignments);
		put(SHIPPING_CONSIGNMENTS, deliveryConsignments);
		put(DELIVERY_CONSIGNMENTS, shippingConsignments);
		put("isPickupConsignment", isPickupConsignment);
		put("isDeliveryConsignment", isDeliveryConsignment);
		put("isShippingConsignment", isShippingConsignment);
		put(IS_SHIPPING_FEE_BRANCH, orderProcessModel.getIsShippingFeeBranch());
		put(COUNTRY_ISO_CODE, orderProcessModel.getOrder().getPointOfService().getDivision().getUid());

		if (null == orderProcessModel.getOrder().getGuestContactPerson())
		{
			customerModel = (B2BCustomerModel) orderProcessModel.getOrder().getUser();
			final B2BUnitModel unit = orderProcessModel.getOrder().getOrderingAccount();
			final Map<String, String> ccEmails = new HashMap<>();
			final Map<String, String> bccEmails = new HashMap<>();

			if (null != unit)
			{
				final Set<B2BCustomerModel> adminUser = ((SiteOneB2BUnitService) b2bUnitService).getAdminUserForUnit(unit);

				for (final B2BCustomerModel b2bAdmin : adminUser)
				{
					if (b2bAdmin.getOrderPromoOption())
					{
						ccEmails.put(b2bAdmin.getUid(), b2bAdmin.getName());
					}
				}
				//ccEmails.put(Config.getString("siteone.support.email", null), "customersupport@siteone.com");
				orderProcessModel.setCcEmails(ccEmails);

				final String bccEmail = Config.getString("order.confirmation.bcc.email", null);
				if (StringUtils.isNotEmpty(bccEmail))
				{
					final String[] bccEmailList = bccEmail.split(",");
					for (final String email : bccEmailList)
					{
						bccEmails.put(email, "customersupport@siteone.com");
					}
				}
				orderProcessModel.setBccEmails(bccEmails);
			}
		}
		else
		{
			final String guestEmail = orderProcessModel.getOrder().getGuestContactPerson().getContactEmail();
			orderProcessModel.setToEmails(guestEmail);
		}

		showDeliveryFee = (isDeliveryConsignment && StringUtils.isNotBlank(orderData.getFreight()));

		put(SHOWDELIVERYFEE, showDeliveryFee);
		put(EXEMPTDELIVERYFEE, (null != orderData.getOrderingAccount() && orderData.getOrderingAccount().isExemptDeliveryFee()));
		modelService.save(orderProcessModel);
		modelService.refresh(orderProcessModel);

		final Optional<PromotionResultData> optional = orderData.getAppliedOrderPromotions().stream()
				.filter(x -> CollectionUtils.isNotEmpty(x.getGiveAwayCouponCodes())).findFirst();
		if (optional.isPresent())
		{
			final PromotionResultData giveAwayCouponPromotion = optional.get();
			final List<CouponData> giftCoupons = giveAwayCouponPromotion.getGiveAwayCouponCodes();
			couponData = giftCoupons.get(0);
		}

		put(REQ_DATE, new SimpleDateFormat("MMMM dd, YYYY").format(orderData.getCreated()));


		final String discounts = orderData.getTotalDiscounts().getFormattedValue();
		put(Discounted_Value, discounts.substring(1, discounts.length()));
		if (null == orderProcessModel.getOrder().getGuestContactPerson())
		{
			put(Unit, orderProcessModel.getOrder().getOrderingAccount());
			String accNo = "";
			if (null != orderData.getOrderingAccount() && orderData.getOrderingAccount().getUid().contains("_"))
			{
				accNo = orderData.getOrderingAccount().getUid().trim().split("_")[0];
			}
			put(ACC_NO, accNo);
		}

		orderData.getEntries().forEach(entries -> {

			if (null != entries && StringUtils.isNotEmpty(entries.getQuantityText()))
			{
				try
				{
					final double quantity = Double.parseDouble(entries.getQuantityText());
					final DecimalFormat formatter = new DecimalFormat("#0.00");
					entries.setQuantityText(formatter.format(quantity));
				}
				catch (final NumberFormatException numberFormatException)
				{
					LOG.error("Number Format Exception occured", numberFormatException);
				}
			}
		});

		if (null != orderData)
		{
			final List<OrderEntryData> orderDataEntries = orderData.getEntries();
			final List<String> showVoucherList = new ArrayList<>();
			if (null != orderData.getAppliedVouchers())
			{
				showVoucherList.addAll(orderData.getAppliedVouchers());
			}
			if (orderData.getEntries() != null && !orderData.getEntries().isEmpty())
			{
				orderData.getEntries().forEach(entry -> {
					final String productCode = entry.getProduct().getCode();
					final ProductData product = getProductFacade().getProductForCodeAndOptions(productCode,
							Arrays.asList(ProductOption.PROMOTIONS));
					entry.getProduct().setCouponCode(product.getCouponCode());
					if (null != entry.getProduct().getCouponCode() && showVoucherList.contains(entry.getProduct().getCouponCode()))
					{
						showVoucherList.remove(entry.getProduct().getCouponCode());
					}
				});
			}
			put(SHOW_COUPON, showVoucherList);

			final List<PromotionResultData> appliedPromotions = orderData.getAppliedProductPromotions();
			if (null != orderDataEntries && null != appliedPromotions)
			{
				PromotionPriceUtils.findSalePriceForOrderEntries(orderDataEntries, orderData.getAppliedProductPromotions());
			}
		}

		if (orderData.isGuestCustomer())
		{
			put(FIRST_NAME, orderData.getB2bCustomerData().getFirstName());
			put(IS_GUEST_CUSTOMER, true);
		}
		else
		{
			if (null != orderProcessModel.getOrder().getContactPerson()
					&& null != orderProcessModel.getOrder().getContactPerson().getFirstName())
			{
				put(FIRST_NAME, orderProcessModel.getOrder().getContactPerson().getFirstName());
			}
			final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) orderProcessModel.getOrder().getUser();
			if (b2bCustomerModel != null && b2bCustomerModel.getNeedsOrderApproval() != null
					&& Boolean.TRUE.equals(b2bCustomerModel.getNeedsOrderApproval()))
			{
				if (null != orderProcessModel.getOrder().getStatus())
				{
					if (orderProcessModel.getOrder().getStatus().equals(OrderStatus.REJECTED))
					{
						put(ORDER_STATUS, "rejected");
					}
					else
					{
						put(ORDER_STATUS, "approved");
					}
				}
				put(APPROVAL_COMMENTS, orderProcessModel.getOrder().getApprovalComments());

			}

		}

		if (StringUtils.isEmpty(orderData.getSpecialInstruction()))
		{
			orderData.setSpecialInstruction("NA");
		}
		LOG.info(" orderData.getSpecialInstruction==" + orderData.getSpecialInstruction());
		LOG.info("SpecialInstruction==" + orderProcessModel.getOrder().getSpecialInstruction());



		String paymentType = "";
		String cardNumber = "";
		String termsCode = "";
		final List<SiteoneCreditCardPaymentInfoModel> paymentInfoList = orderProcessModel.getOrder().getPaymentInfoList();
		final List<SiteonePOAPaymentInfoModel> poaPaymentInfoList = orderProcessModel.getOrder().getPoaPaymentInfoList();
		if (CollectionUtils.isNotEmpty(paymentInfoList))
		{
			final SiteoneCreditCardPaymentInfoModel creditCardPaymentInfo = paymentInfoList.get(0);
			paymentType = creditCardPaymentInfo.getPaymentType();
			cardNumber = "XX" + creditCardPaymentInfo.getLast4Digits();
			LOG.info("card details>>>>>>>>>>>>>>>>>>>>>>>" + cardNumber + paymentType);

		}
		else if (CollectionUtils.isNotEmpty(poaPaymentInfoList))
		{
			final SiteonePOAPaymentInfoModel poaPaymentInfo = poaPaymentInfoList.get(0);
			paymentType = poaPaymentInfo.getPaymentType();
			termsCode = poaPaymentInfo.getTermsCode();

			LOG.info("POA details>>>>>>>>>>>>>>>>>>>>>>>" + termsCode + paymentType);
		}
		else
		{
			paymentType = "2";
		}
		put(PAYMENTTYPE, paymentType);
		put(CARDNUMBER, cardNumber);
		put(TERMSCODE, termsCode);
		/**
		 * @comment In a B2B Scenario - guestContactPerson is Null.
		 */
		if (null == orderProcessModel.getOrder().getGuestContactPerson())
		{
			final String userId = ((B2BCustomerModel) orderProcessModel.getOrder().getUser()).getUid();
			if (null != orderProcessModel.getOrder().getContactPerson()
					&& userId.equals(orderProcessModel.getOrder().getContactPerson().getUid()))

			{
				orderProcessModel.setToEmails(userId);
				put(EMAIL, userId);
			}
			else if (null != orderProcessModel.getOrder().getContactPerson()
					&& !(userId.equals(orderProcessModel.getOrder().getContactPerson().getUid())))
			{
				final String emailRecipients = orderProcessModel.getOrder().getContactPerson().getUid() + ";" + userId;
				orderProcessModel.setToEmails(emailRecipients);
				put(EMAIL, emailRecipients);
			}

		}
		else
		{
			final String userId = orderProcessModel.getOrder().getGuestContactPerson().getContactEmail();
			orderProcessModel.setToEmails(userId);
			put(EMAIL, userId);

		}
		put(NUMBER_TOOL, new NumberTool());
		put(CURRENCY_UNITPRICE_FORMAT, CURRENCY_UNITPRICE_FORMAT_VAL);
		storeSessionFacade.removeCurrentBaseStore();
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

	public SiteOneStoreSessionFacade getStoreSessionFacade()
	{
		return storeSessionFacade;
	}

	public void setStoreSessionFacade(SiteOneStoreSessionFacade storeSessionFacade)
	{
		this.storeSessionFacade = storeSessionFacade;
	}

	@Override
	protected BaseSiteModel getSite(final OrderProcessModel orderProcessModel)
	{
		return orderProcessModel.getOrder().getSite();
	}

	@Override
	protected CustomerModel getCustomer(final OrderProcessModel orderProcessModel)
	{
		if (null == orderProcessModel.getOrder().getGuestContactPerson())
		{
			return (CustomerModel) orderProcessModel.getOrder().getUser();
		}
		else
		{
			return orderProcessModel.getOrder().getGuestContactPerson();
		}
	}

	protected Converter<OrderModel, OrderData> getOrderConverter()
	{
		return orderConverter;
	}

	public void setOrderConverter(final Converter<OrderModel, OrderData> orderConverter)
	{
		this.orderConverter = orderConverter;
	}

	public OrderData getOrder()
	{
		return orderData;
	}

	public Boolean getOrderPromo()
	{
		return customerModel.getOrderPromoOption();
	}

	@Override
	protected LanguageModel getEmailLanguage(final OrderProcessModel orderProcessModel)
	{
		return orderProcessModel.getOrder().getLanguage();
	}

	public CouponData getCoupon()
	{
		return couponData;
	}


	/**
	 * @return the productFacade
	 */
	public ProductFacade getProductFacade()
	{
		return productFacade;
	}


	/**
	 * @param productFacade
	 *           the productFacade to set
	 */
	public void setProductFacade(final ProductFacade productFacade)
	{
		this.productFacade = productFacade;
	}

}
