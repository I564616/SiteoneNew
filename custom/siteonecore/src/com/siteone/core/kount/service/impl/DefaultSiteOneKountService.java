package com.siteone.core.kount.service.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.catalog.model.classification.ClassificationClassModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commerceservices.category.CommerceCategoryService;
import de.hybris.platform.commerceservices.enums.CustomerType;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.order.payment.SiteoneCreditCardPaymentInfoModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.AbstractContactInfoModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.PhoneContactInfoModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.Config;
import de.hybris.platform.variants.model.GenericVariantProductModel;
import de.hybris.platform.variants.model.VariantCategoryModel;
import de.hybris.platform.variants.model.VariantProductModel;
import de.hybris.platform.variants.model.VariantValueCategoryModel;

import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.ObjectUtils;

import com.kount.ris.Inquiry;
import com.kount.ris.KountRisClient;
import com.kount.ris.Response;
import com.kount.ris.Update;
import com.kount.ris.util.Address;
import com.kount.ris.util.AuthorizationStatus;
import com.kount.ris.util.BankcardReply;
import com.kount.ris.util.CartItem;
import com.kount.ris.util.InquiryMode;
import com.kount.ris.util.MerchantAcknowledgment;
import com.kount.ris.util.payment.NoPayment;
import com.kount.ris.util.payment.Payment;
import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.enums.OrderTypeEnum;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.kount.service.SiteOneKountService;
import com.siteone.core.model.SiteoneCCPaymentAuditLogModel;
import com.siteone.core.model.SiteoneKountDataModel;
import com.siteone.core.order.dao.SiteOneOrderDao;
import com.siteone.core.order.services.SiteOneOrderService;
import com.siteone.facade.customer.info.SiteOneCustInfoData;
import com.siteone.integration.customer.info.SiteOneCustInfoResponeData;
import com.siteone.integration.linktopay.order.data.SiteOneWsLinkToPayBillingInfoData;
import com.siteone.integration.linktopay.order.data.SiteOneWsLinkToPayOrderResponseData;
import com.siteone.integration.linktopay.order.data.SiteOneWsLinkToPayProductDetailsData;
import com.siteone.integration.linktopay.order.data.SiteOneWsLinkToPayShippingInfoData;
import com.siteone.integration.services.ue.SiteOneCustInfoWebService;


public class DefaultSiteOneKountService implements SiteOneKountService
{

	private static final Logger logger = LogManager.getLogger(DefaultSiteOneKountService.class);
	private static final String BOOMI_PLATFORM = "boomiPlatformLinux";
	private static final String l2PAy_token = "6034646318";

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

	@Resource(name = "siteOneOrderDao")
	private SiteOneOrderDao siteOneOrderDao;

	private CommerceCategoryService commerceCategoryService;

	private static final String CAYAN_RESPONSE_DECLINED = "N";

	private static final String DEFAULT_KOUNT_EMAIL = "noemail@kount.com";

	private static final String RETAIL_TRADECLASS = "409033";

	@Override
	public String evaluateInquiry(final CartData cartData) throws Exception
	{
		Response response = null;

		try
		{
			sessionService.removeAttribute(SiteoneCoreConstants.INQUIRY_RESPONSE_SESSION_ATTRIBUTE);
			final CartModel cartModel = cartService.getSessionCart();

			setKountSystemProperties();
			
			final Inquiry inquiry = new Inquiry();

			setMerchantInformation(inquiry, cartModel);
			setCustomerInformation(inquiry, cartData, cartModel);
			setPurchaseInformation(inquiry, cartModel);

			final KountRisClient client = new KountRisClient(
					URI.create(configurationService.getConfiguration().getString(SiteoneCoreConstants.API_URL)).toURL(),
					configurationService.getConfiguration().getString(SiteoneCoreConstants.API_KEY)); // 1


			logger.error("Kount RIS Inquiry Request: " + inquiry.getParams());

			response = client.process(inquiry);

			logger.error("Kount RIS Inquiry Response: " + "Customer Email: " + cartModel.getUser().getUid() + ", Customer Name: "
					+ cartModel.getUser().getName() + ", Cart Id: " + cartModel.getCode() + ", Auto Approval Status: "
					+ response.getAuto() + ", Persona Score: " + response.getScore() + ", Omniscore: " + response.getOmniscore());
  			
			
			if (StringUtils.isNotBlank(cartModel.getUser().getUid()) && StringUtils.isNotBlank(cartModel.getUser().getName())
					&& StringUtils.isNotBlank(cartModel.getCode()) && StringUtils.isNotBlank(response.getAuto())
					&& StringUtils.isNotBlank(response.getScore()) && StringUtils.isNotBlank(response.getOmniscore()))
			{
				logger.error("Kount RIS Inquiry Response: " + "Customer Email: " + cartModel.getUser().getUid() + ", Customer Name: "
						+ cartModel.getUser().getName() + ", Cart Id: " + cartModel.getCode() + ", Auto Approval Status: "
						+ response.getAuto() + ", Persona Score: " + response.getScore() + ", Omniscore: " + response.getOmniscore());
			}

			sessionService.setAttribute(SiteoneCoreConstants.INQUIRY_RESPONSE_SESSION_ATTRIBUTE, response);
		}
		catch (final Exception exception)
		{
			logger.error(exception);
		}

		if (null != response && StringUtils.isNotBlank(response.getAuto()))
		{

			return response.getAuto() + '_' + response.getTransactionId();
		}
		else
		{
			return CAYAN_RESPONSE_DECLINED;
		}
	}

	@Override
   public void updateKountDetails(final String status, final String avsStatus, final String cvv, final String orderNumber)
           throws Exception
   {		
		 setKountSystemProperties();

       final Response preResponse = sessionService.getAttribute(SiteoneCoreConstants.INQUIRY_RESPONSE_SESSION_ATTRIBUTE);           		
       final Update kountUpdate = new Update();

       kountUpdate.setMerchantId(configurationService.getConfiguration().getInt(SiteoneCoreConstants.MERCHANT_ID));
       kountUpdate.setSessionId(preResponse.getSessionId());
       kountUpdate.setTransactionId(preResponse.getTransactionId());
       kountUpdate.setMerchantAcknowledgment(MerchantAcknowledgment.YES);       
       if (null != sessionService.getAttribute("orderNumber"))
       {   
    	 kountUpdate.setOrderNumber(sessionService.getAttribute("orderNumber"));
       }
       else
       {
           if (null != orderNumber)
           {
               kountUpdate.setOrderNumber(orderNumber);
           }
       }
       if (StringUtils.isNotBlank(status) && SiteoneCoreConstants.CAYAN_PAYMENT_APPROVED_STATUS.equalsIgnoreCase(status))
       {
           kountUpdate.setAuthorizationStatus(AuthorizationStatus.APPROVED);
       }
       else
       {
           kountUpdate.setAuthorizationStatus(AuthorizationStatus.DECLINED);
       }
  kountUpdate.setPayment(new NoPayment());

       final KountRisClient client = new KountRisClient(
				URI.create(configurationService.getConfiguration().getString(SiteoneCoreConstants.API_URL)).toURL(),
               configurationService.getConfiguration().getString(SiteoneCoreConstants.API_KEY)); // 1

       logger.error("Kount RIS Update Request" + kountUpdate.getParams());

       final Response response = client.process(kountUpdate);

       logger.error("Kount RIS Update Response: " + "Auto Approval Status: " + response.getAuto() + "Persona Score: "
             + response.getScore() + "Omniscore: " + response.getOmniscore());  
        
       if (null != response && StringUtils.isNotBlank(response.getAuto()) && StringUtils.isNotBlank(response.getScore())
               && StringUtils.isNotBlank(response.getOmniscore()))
       {
           logger.error("Kount RIS Update Response: " + "Auto Approval Status: " + response.getAuto() + "Persona Score: "
                   + response.getScore() + "Omniscore: " + response.getOmniscore());
       }

   }
	@Override
	public void updateKountDetails(final String orderCode) throws Exception
	{
		final Response preResponse = sessionService.getAttribute(SiteoneCoreConstants.INQUIRY_RESPONSE_SESSION_ATTRIBUTE);
		final OrderModel orderModel = siteOneOrderService.getOrderForCode(orderCode);

		setKountSystemProperties();

		if (null != preResponse)
		{
			final Update kountUpdate = new Update();

			kountUpdate.setMerchantId(configurationService.getConfiguration().getInt(SiteoneCoreConstants.MERCHANT_ID));
			kountUpdate.setSessionId(preResponse.getSessionId());
			kountUpdate.setTransactionId(preResponse.getTransactionId());
			kountUpdate.setMerchantAcknowledgment(MerchantAcknowledgment.YES);
			kountUpdate.setAuthorizationStatus(AuthorizationStatus.APPROVED);
			kountUpdate.setOrderNumber(orderCode);
			final List<Payment> list = new ArrayList<>();
			final List<SiteoneCreditCardPaymentInfoModel> sourceCreditCardInfoList = orderModel.getPaymentInfoList();
			if (CollectionUtils.isNotEmpty(sourceCreditCardInfoList))
			{
				sourceCreditCardInfoList.forEach(sourceCreditCardInfo -> {
					final SiteoneCreditCardPaymentInfoModel sourcePaymentInfo = sourceCreditCardInfo;
					Payment payment = null;
					if (Integer.parseInt(sourcePaymentInfo.getPaymentType()) == 3)
					{
						payment = new Payment("TOKEN", sourcePaymentInfo.getAuthToken());
						payment.setKhashed(false);
					}
					list.add(payment);
				});
			}
			if (list.size() > 0)
			{
				kountUpdate.setPayment(list.get(0));
			}
			else
			{
				kountUpdate.setPayment(new NoPayment());
			}
			getKountResponse(kountUpdate, orderCode);
		}
	}

	@Override
	public void updateUserKountDetails(final String orderCode, final String transactionId, final String kountSessionId)
	{
		setKountSystemProperties();

		final Update kountUpdate = new Update();
		kountUpdate.setMerchantId(configurationService.getConfiguration().getInt(SiteoneCoreConstants.MERCHANT_ID));
		kountUpdate.setMerchantAcknowledgment(MerchantAcknowledgment.YES);
		kountUpdate.setAuthorizationStatus(AuthorizationStatus.APPROVED);
		kountUpdate.setSessionId(kountSessionId);
		kountUpdate.setTransactionId(transactionId);
		kountUpdate.setOrderNumber(orderCode);
		try
		{
			getKountResponse(kountUpdate, orderCode);
		}
		catch (final Exception e)
		{
			logger.error("Exception Occured in :" + e);
		}
	}

	public void getKountResponse(final Update kountUpdate, final String orderCode) throws Exception
	{
		setKountSystemProperties();

		final KountRisClient client = new KountRisClient(
				URI.create(configurationService.getConfiguration().getString(SiteoneCoreConstants.API_URL)).toURL(),
				configurationService.getConfiguration().getString(SiteoneCoreConstants.API_KEY)); // 1

		logger.error("Kount RIS Update Request" + kountUpdate.getParams());

		final Response response = client.process(kountUpdate);

		if (null != response && StringUtils.isNotBlank(response.getMode()))
		{
			logger.error("Kount RIS Update Success for order: " + orderCode);
		}
	}

	@Override
	public void setMerchantInformation(final Inquiry inquiry, final CartModel cart)
	{
		setKountSystemProperties();

		final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();

		inquiry.setMerchantId(configurationService.getConfiguration().getInt(SiteoneCoreConstants.MERCHANT_ID));

		if (customer != null && customer.getDefaultB2BUnit() != null && customer.getDefaultB2BUnit().getTradeClass() != null
				&& (!customer.getDefaultB2BUnit().getTradeClass().equals(RETAIL_TRADECLASS)))
		{
			inquiry.setWebsite(configurationService.getConfiguration().getString(SiteoneCoreConstants.CONTRACTOR_SITE_ID));
		}
		else if (customer != null && customer.getDefaultB2BUnit() != null && customer.getDefaultB2BUnit().getTradeClass() != null
				&& (customer.getDefaultB2BUnit().getTradeClass().equals(RETAIL_TRADECLASS)))
		{
			inquiry.setWebsite(configurationService.getConfiguration().getString(SiteoneCoreConstants.RETAIL_SITE_ID));
		}
		else if (cart != null && null != cart.getUser() && CustomerType.GUEST.equals(((CustomerModel) cart.getUser()).getType()))
		{
			inquiry.setWebsite(configurationService.getConfiguration().getString(SiteoneCoreConstants.GUEST_SITE_ID));
		}
		else
		{
			inquiry.setWebsite(configurationService.getConfiguration().getString(SiteoneCoreConstants.SITE_ID));
		}
		inquiry.setMode(InquiryMode.INITIAL_INQUIRY); // 2
		inquiry.setAuthorizationStatus(AuthorizationStatus.APPROVED);

		inquiry.setMerchantAcknowledgment(MerchantAcknowledgment.YES);
	}

	@Override
	public void setCustomerInformation(final Inquiry inquiry, final CartData cartData, final CartModel cartModel)
			throws UnknownHostException
	{
		setKountSystemProperties();

		final String kountSessionId = sessionService.getAttribute(SiteoneCoreConstants.KOUNT_SESSION_ID_ATTRIBUTE);
		final B2BUnitModel unit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();

		inquiry.setSessionId(kountSessionId); // 3
		inquiry.setIpAddress(sessionService.getAttribute("clientIp"));// 4
		if (userService.getCurrentUser() != null && userService.isAnonymousUser(userService.getCurrentUser()))
		{
			//inquiry.setUniqueCustomerId(getOriginalEmail(cartModel.getUser().getUid()));
			if (unit != null && unit.getUid() != null)
			{
				inquiry.setUniqueCustomerId(unit.getUid());
				
			}
			else
			{
				inquiry.setUniqueCustomerId("");
			}
			if (cartData.getDeliveryAddress() != null)
			{
				cartData.setBillingAddress(cartData.getDeliveryAddress());
			}

			if (OrderTypeEnum.PICKUP.equals(cartModel.getOrderType()) && cartData.getGuestContactPerson() != null
					&& cartData.getGuestContactPerson().getDefaultAddress() != null)
			{
				cartData.setBillingAddress(cartData.getGuestContactPerson().getDefaultAddress());
			}
		}
		else if (unit != null && unit.getUid() != null)
		{
			inquiry.setUniqueCustomerId(unit.getUid());
			
				SiteOneCustInfoData custInfoData = null;
				final SiteOneCustInfoResponeData data = siteOneCustInfoWebService.getCustInfo(unit,
						Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
				if (data != null)
				{
					custInfoData = siteOneCustInfoDataConverter.convert(data);
				}

				if (custInfoData != null && custInfoData.getCustomerSalesInfo().getTwelveMonthSales() != null)
			   {
					logger.error("Customer Sales Info, customer sales amount is "
							+ custInfoData.getCustomerSalesInfo().getTwelveMonthSales().toString());
				   if (custInfoData.getCustomerSalesInfo().getTwelveMonthSales() > Double.valueOf(24999))
				   {
					   inquiry.setUserDefinedField("CustomerSalesAmount", "Y");
				   }
				   else
				   {
					   inquiry.setUserDefinedField("CustomerSalesAmount", "N");
				   }
			   }		
		}

		if (StringUtils.isBlank(System.getProperty(SiteoneCoreConstants.KOUNT_CONGIF_KEY, null)))
		{
			System.setProperty(SiteoneCoreConstants.KOUNT_CONGIF_KEY,
					configurationService.getConfiguration().getString(SiteoneCoreConstants.KOUNT_CONGIF_KEY));
		}

		if (OrderTypeEnum.DELIVERY.equals(cartModel.getOrderType()) && null != cartData.getDeliveryAddress())
		{
			setShippingAddressAndPhoneNumber(cartData, inquiry, cartModel);
		}

		if (null != cartData.getBillingAddress())
		{
			setBillingAddressAndPhoneNumber(cartData, cartModel, inquiry);
		}
		else if (null != cartModel.getUser() && !CollectionUtils.isEmpty(cartModel.getUser().getContactInfos()))
		{
			final Collection<AbstractContactInfoModel> contactInfos = cartModel.getUser().getContactInfos();

			setBillingPhoneNumber(contactInfos, inquiry);
		}

		//Payment Info BOL
		final List<Payment> list = new ArrayList<>();
		final List<SiteoneCreditCardPaymentInfoModel> sourceCreditCardInfoList = cartModel.getPaymentInfoList();
		if (CollectionUtils.isNotEmpty(sourceCreditCardInfoList))
		{
			sourceCreditCardInfoList.forEach(sourceCreditCardInfo -> {
				final SiteoneCreditCardPaymentInfoModel sourcePaymentInfo = sourceCreditCardInfo;
				Payment payment = null;
				if (Integer.parseInt(sourcePaymentInfo.getPaymentType()) == 3)
				{
					payment = new Payment("TOKEN", sourcePaymentInfo.getAuthToken());
					payment.setKhashed(false);
					if (validateAVS(sourcePaymentInfo))
					{
						inquiry.setAvsAddressReply(BankcardReply.MATCH);
						inquiry.setAvsZipReply(BankcardReply.MATCH);
					}
					else
					{
						inquiry.setAvsAddressReply(BankcardReply.NO_MATCH);
						inquiry.setAvsZipReply(BankcardReply.NO_MATCH);
					}
					if (validateCVV(sourcePaymentInfo))
					{
						inquiry.setCvvReply(BankcardReply.MATCH);
					}
					else
					{
						inquiry.setCvvReply(BankcardReply.NO_MATCH);
					}
					inquiry.setAuthorizationStatus(AuthorizationStatus.APPROVED);
				}
				else
				{
					payment = new NoPayment();
				}
				list.add(payment);
			});
		}
		if (list.size() > 0)
		{
			inquiry.setPayment(list.get(0));
		}
		else
		{
			inquiry.setPayment(new NoPayment());
		}
		inquiry.setCustomerName(cartModel.getUser().getName());
		if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
		{
			final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) cartModel.getUser();
			inquiry.setEmail(b2bCustomerModel.getEmail());
		}
		else
		{
			final CustomerModel customerModel = cartModel.getGuestContactPerson();
			final List<AddressModel> addressList = (List<AddressModel>) customerModel.getAddresses();
			inquiry.setEmail(addressList.get(0).getEmail());
		}

	}

	public boolean validateAVS(final SiteoneCreditCardPaymentInfoModel siteOnePaymentForm)
	{
		return (siteOnePaymentForm.getAvs().equals("Y") || siteOnePaymentForm.getAvs().equals("X"));

	}

	public boolean validateCVV(final SiteoneCreditCardPaymentInfoModel siteOnePaymentForm)
	{
		return (siteOnePaymentForm.getCvv().equals("Y") || siteOnePaymentForm.getCvv().equals("M"));

	}


	@Override
	public void setPurchaseInformation(final Inquiry inquiry, final CartModel cartModel)
	{
		setKountSystemProperties();

		inquiry.setCurrency(cartModel.getCurrency().getIsocode());
		if (null != cartModel.getTotalPrice() && null != cartModel.getTotalTax())
		{
			inquiry.setTotal(
					roundDoubleToInteger((cartModel.getTotalPrice().doubleValue() + cartModel.getTotalTax().doubleValue())) * 100); // 6
		}
		else
		{
			inquiry.setTotal(0);
		}

		inquiry.setCart(getCartItems(cartModel));
	}

	public void setShippingAddressAndPhoneNumber(final CartData cartData, final Inquiry inquiry, final CartModel cartModel)
	{
		setKountSystemProperties();

		String contactPhoneNumber = null;
		final Address shippingAddress = new Address();
		contactPhoneNumber = getContactPhoneNumber(cartData);
		shippingAddress.setAddress1(cartData.getDeliveryAddress().getLine1());
		shippingAddress.setAddress2(cartData.getDeliveryAddress().getLine2());
		shippingAddress.setCity(cartData.getDeliveryAddress().getTown());
		shippingAddress.setCountry(cartData.getDeliveryAddress().getCountry().getIsocode());
		shippingAddress.setPostalCode(cartData.getDeliveryAddress().getPostalCode());
		shippingAddress.setState(cartData.getDeliveryAddress().getRegion().getName());
		if (StringUtils.isNotBlank(contactPhoneNumber))
		{
			inquiry.setShippingPhoneNumber(contactPhoneNumber);
		}
		else
		{
			// Set Guest User Phone number
			inquiry.setShippingPhoneNumber(cartModel.getDeliveryAddress().getPhone1());
		}
		inquiry.setShippingAddress(shippingAddress);
	}

	public void setBillingAddressAndPhoneNumber(final CartData cartData, final CartModel cartModel, final Inquiry inquiry)
	{
		setKountSystemProperties();

		final Address billingAddress = new Address();
		billingAddress.setAddress1(cartData.getBillingAddress().getLine1());
		billingAddress.setAddress2(cartData.getBillingAddress().getLine2());
		billingAddress.setCity(cartData.getBillingAddress().getTown());
		billingAddress.setCountry(cartData.getBillingAddress().getCountry().getIsocode());
		billingAddress.setPostalCode(cartData.getBillingAddress().getPostalCode());
		billingAddress.setState(cartData.getBillingAddress().getRegion().getName());
		if (null != cartModel.getUser() && !CollectionUtils.isEmpty(cartModel.getUser().getContactInfos()))
		{
			final Collection<AbstractContactInfoModel> contactInfos = cartModel.getUser().getContactInfos();

			setBillingPhoneNumber(contactInfos, inquiry);
		}
		else
		{
			// Set Guest User Phone number
			inquiry.setBillingPhoneNumber(cartModel.getDeliveryAddress().getPhone1());
		}
		inquiry.setBillingAddress(billingAddress);
	}

	public void setBillingPhoneNumber(final Collection<AbstractContactInfoModel> contactInfos, final Inquiry inquiry)
	{
		setKountSystemProperties();

		String billingContactPhoneNumber = null;
		PhoneContactInfoModel phoneInfo = null;
		if (CollectionUtils.isNotEmpty(contactInfos))
		{
			for (final AbstractContactInfoModel info : contactInfos)
			{
				if (info instanceof PhoneContactInfoModel)
				{
					phoneInfo = (PhoneContactInfoModel) info;
					billingContactPhoneNumber = phoneInfo.getPhoneNumber();
					if (StringUtils.isNotBlank(billingContactPhoneNumber))
					{
						inquiry.setBillingPhoneNumber(billingContactPhoneNumber);
						break;
					}

				}
			}
		}
	}

	public int roundDoubleToInteger(final double price)
	{
		return ((int) Math.round(price));
	}

	public String getContactPhoneNumber(final CartData cartData)
	{
		if (null != cartData.getContactPerson())
		{
			return cartData.getContactPerson().getContactNumber();
		}

		return null;
	}

	public List<CartItem> getCartItems(final CartData cartData)
	{
		CartItem cartItem;
		final List<CartItem> cartItemList = new ArrayList<>();
		if (null != cartData.getEntries())
		{
			for (final OrderEntryData item : cartData.getEntries())
			{
				cartItem = new CartItem(item.getProduct().getLevel2Category(), item.getProduct().getName(),
						item.getProduct().getProductShortDesc(), Integer.parseInt(item.getQuantity().toString()),
						roundDoubleToInteger(item.getTotalPrice().getValue().doubleValue()) * 100);
				cartItemList.add(cartItem);
			}
		}

		return cartItemList;
	}


	public List<CartItem> getCartItems(final CartModel cartModel)
	{
		CartItem cartItem;
		final List<CartItem> cartItemList = new ArrayList<>();
		if (null != cartModel.getEntries())
		{
			for (final AbstractOrderEntryModel item : cartModel.getEntries())
			{
				final CategoryModel categoryModel;
				if (item.getProduct() instanceof GenericVariantProductModel)
				{
					final ProductModel baseProductModel = ((VariantProductModel) item.getProduct()).getBaseProduct();
					categoryModel = getPrimaryCategoryForProduct(baseProductModel);
				}
				else
				{
					final ProductModel productModel = item.getProduct();
					categoryModel = getPrimaryCategoryForProduct(productModel);
				}

				cartItem = new CartItem(categoryModel.getName(), item.getProduct().getName(), item.getProduct().getProductShortDesc(),
						Integer.parseInt(item.getQuantity().toString()), roundDoubleToInteger(item.getBasePrice().doubleValue()) * 100);
				cartItemList.add(cartItem);
			}
		}

		return cartItemList;
	}

	protected final CategoryModel getPrimaryCategoryForProduct(final ProductModel product)
	{
		final String displayRootCategoryCode = Config.getString("display.hierarchy.root.category", null);
		// Get the first super-category from the product that isn't a classification category
		for (final CategoryModel category : product.getSupercategories())
		{
			if (!category.getCode().startsWith(displayRootCategoryCode) && (!(category instanceof ClassificationClassModel)
					&& !(category instanceof VariantCategoryModel) && !(category instanceof VariantValueCategoryModel)))
			{
				return category;
			}
		}
		return null;
	}

	/**
	 * @param displayUid
	 * @return
	 */
	public String getOriginalEmail(final String displayUid)
	{
		if (displayUid != null)
		{
			final String[] SplitUID = displayUid.split("\\|");
			final String originalEmailID = SplitUID[1];
			return originalEmailID;
		}
		else
		{
			return null;
		}
	}

	@Override
	public String linkToPayEvaluateInquiry(final SiteOneWsLinkToPayOrderResponseData linkToPayOrderResponseData,
			final String sessionID)
	{
		Response response = null;

		try
		{

			setKountSystemProperties();

			sessionService.removeAttribute(SiteoneCoreConstants.INQUIRY_RESPONSE_SESSION_ATTRIBUTE);
			final Inquiry inquiry = new Inquiry();

			setMerchantInformation(inquiry, null);
			setLinkToPayCustomerInformation(inquiry, linkToPayOrderResponseData,sessionID);
			setLinkToPayPurchaseInformation(inquiry, linkToPayOrderResponseData);

			final KountRisClient client = new KountRisClient(
					URI.create(configurationService.getConfiguration().getString(SiteoneCoreConstants.API_URL)).toURL(),
					configurationService.getConfiguration().getString(SiteoneCoreConstants.API_KEY)); // 1
			
			logger.error("Kount RIS L2P Inquiry Request: " + inquiry.getParams());

			response = client.process(inquiry);

			if (linkToPayOrderResponseData.getResult().getContactInfo() != null)
			{
				logger.error("Contact Email: "
						+ linkToPayOrderResponseData.getResult().getContactInfo().getContactEmail());
			}
			if(response != null)
			{
				logger.error("Kount RIS Inquiry Response:  Order Number: "
						+ linkToPayOrderResponseData.getResult().getOrderNumber() + ", Total Amount: "
						+ linkToPayOrderResponseData.getResult().getTotalAmount() + ", Auto Approval Status: " + response.getAuto()
						+ ", Persona Score: " + response.getScore() + ", Omniscore: " + response.getOmniscore());
			}

			sessionService.setAttribute(SiteoneCoreConstants.INQUIRY_RESPONSE_SESSION_ATTRIBUTE, response);
			
		}
		catch (final Exception exception)
		{
			logger.error(exception);
		}

		if (null != response && StringUtils.isNotBlank(response.getAuto()))
		{

			return response.getAuto() + '_' + response.getTransactionId();
		}
		else
		{
			return CAYAN_RESPONSE_DECLINED;
		}
	}

	@Override
	public void setLinkToPayCustomerInformation(final Inquiry inquiry,
			final SiteOneWsLinkToPayOrderResponseData linkToPayOrderResponseData, String sessionId) throws UnknownHostException
	{
		setKountSystemProperties();
		final B2BUnitModel unit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();

		inquiry.setSessionId(sessionId); // 3
		inquiry.setIpAddress(sessionService.getAttribute("clientIp"));// 4
		inquiry.setWebsite(configurationService.getConfiguration().getString(SiteoneCoreConstants.LINK_TO_PAY_SITE_ID));
		if (null != linkToPayOrderResponseData
				&& StringUtils.isNotBlank(linkToPayOrderResponseData.getResult().getContactInfo().getContactEmail()))
		{
			inquiry.setUniqueCustomerId(linkToPayOrderResponseData.getResult().getContactInfo().getContactEmail());
		}
		else
		{
			inquiry.setUniqueCustomerId(DEFAULT_KOUNT_EMAIL);
		}
		if (StringUtils.isBlank(System.getProperty(SiteoneCoreConstants.KOUNT_CONGIF_KEY, null)))
		{
			System.setProperty(SiteoneCoreConstants.KOUNT_CONGIF_KEY,
					configurationService.getConfiguration().getString(SiteoneCoreConstants.KOUNT_CONGIF_KEY));
		}

		if (null != linkToPayOrderResponseData && (!ObjectUtils.isEmpty(linkToPayOrderResponseData.getResult().getShippingInfo())))
		{
			setLinkToPayShippingAddressAndPhoneNumber(linkToPayOrderResponseData, inquiry);
		}

		if (null != linkToPayOrderResponseData && (!ObjectUtils.isEmpty(linkToPayOrderResponseData.getResult().getBillingInfo())))
		{
			setLinkToPayBillingAddressAndPhoneNumber(linkToPayOrderResponseData, inquiry);
		}


		if (null != linkToPayOrderResponseData
				&& (StringUtils.isNotBlank(linkToPayOrderResponseData.getResult().getContactInfo().getContactEmail())))
		{
			inquiry.setEmail(linkToPayOrderResponseData.getResult().getContactInfo().getContactEmail());
		}
		else
		{
			inquiry.setEmail(DEFAULT_KOUNT_EMAIL);
		}
		
		if (null != linkToPayOrderResponseData
		&& (StringUtils.isNotBlank(linkToPayOrderResponseData.getResult().getContactInfo().getCustomerName())))
		{
			inquiry.setCustomerName(linkToPayOrderResponseData.getResult().getContactInfo().getCustomerName());
		}
						
		Payment payment = null;
		payment = new Payment("NONE", null);
		if (Config.getBoolean("kount.pay.enable",false))	
		{
			payment.setKhashed(false);
		}
		else
		{
			payment.setKhashed(true);
		}
				
	  	inquiry.setPayment(payment);
	  	
		SiteOneCustInfoData custInfoData = null;
		SiteOneCustInfoResponeData data = null;
		if (unit != null)
		{
			data = siteOneCustInfoWebService.getCustInfo(unit,
					Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
		}
		if (data != null)
		{
			custInfoData = siteOneCustInfoDataConverter.convert(data);
		}

		if (custInfoData != null && custInfoData.getCustomerSalesInfo().getTwelveMonthSales() != null)
		{
			logger.error("Customer Sales Info for LinkToPay Request, customer sales amount is "
					+ custInfoData.getCustomerSalesInfo().getTwelveMonthSales().toString());
			if (custInfoData.getCustomerSalesInfo().getTwelveMonthSales() > Double.valueOf(24999))
			{
				inquiry.setUserDefinedField("CustomerSalesAmount", "Y");
			}
			else
			{
				inquiry.setUserDefinedField("CustomerSalesAmount", "N");
			}
		}
	}

	@Override
	public void setLinkToPayPurchaseInformation(final Inquiry inquiry,
			final SiteOneWsLinkToPayOrderResponseData linkToPayOrderResponseData)
	{
		setKountSystemProperties();

		inquiry.setCurrency(commonI18NService.getCurrency("USD").getIsocode());
		if (null != linkToPayOrderResponseData.getResult().getTotalAmount())

		{
			inquiry.setTotal((int) Math.round(Double.parseDouble(linkToPayOrderResponseData.getResult().getTotalAmount()) * 100)); // 6
		}
		else
		{
			inquiry.setTotal(0);
		}

		inquiry.setCart(getLinkToPayCartItems(linkToPayOrderResponseData));
	}


	public List<CartItem> getLinkToPayCartItems(final SiteOneWsLinkToPayOrderResponseData linkToPayOrderResponseData)
	{
		CartItem cartItem;
		final List<CartItem> cartItemList = new ArrayList<>();
		if (null != linkToPayOrderResponseData.getResult().getLineItems())
		{
			for (final SiteOneWsLinkToPayProductDetailsData item : linkToPayOrderResponseData.getResult().getLineItems())
			{
				String productName = StringUtils.isNotBlank(item.getProductName()) ? item.getProductName() : "Product Name Unavailable";
				String categoryName = StringUtils.isNotBlank(item.getPrimaryCategoryName()) ? item.getPrimaryCategoryName() : "Category Name Unavailable";
				String itemDesc = StringUtils.isNotBlank(item.getItemDescription()) ? item.getItemDescription() : "Item Description Unavailable";

				cartItem = new CartItem(categoryName, productName, itemDesc,
						((item.getQuantity() <= 0) ? 1 : Math.round(item.getQuantity())),
						((int) Math.round(Double.parseDouble(item.getListPrice()) * 100)));
				cartItemList.add(cartItem);
			}
		}
		return cartItemList;
	}





	public void setLinkToPayShippingAddressAndPhoneNumber(final SiteOneWsLinkToPayOrderResponseData linkToPayOrderResponseData,
			final Inquiry inquiry)
	{
		setKountSystemProperties();

		final Address shippingAddress = new Address();
		final SiteOneWsLinkToPayShippingInfoData shippingInfo = linkToPayOrderResponseData.getResult().getShippingInfo();
		shippingAddress.setAddress1(shippingInfo.getAddressLine1());
		shippingAddress.setAddress2(shippingInfo.getAddressLine2());
		shippingAddress.setCity(shippingInfo.getCity());
		shippingAddress.setPostalCode(shippingInfo.getZip());
		shippingAddress.setState(shippingInfo.getState());

		if ((StringUtils.isNotBlank(linkToPayOrderResponseData.getResult().getContactInfo().getPhoneNumber()))
				&& (!(linkToPayOrderResponseData.getResult().getContactInfo().getPhoneNumber().equals("0"))))
		{
			inquiry.setShippingPhoneNumber(linkToPayOrderResponseData.getResult().getContactInfo().getPhoneNumber());
		}
		inquiry.setShippingAddress(shippingAddress);
	}

	public void setLinkToPayBillingAddressAndPhoneNumber(final SiteOneWsLinkToPayOrderResponseData linkToPayOrderResponseData,
			final Inquiry inquiry)
	{
		setKountSystemProperties();

		final Address billingAddress = new Address();
		final SiteOneWsLinkToPayBillingInfoData billingInfo = linkToPayOrderResponseData.getResult().getBillingInfo();
		billingAddress.setAddress1(billingInfo.getAddressLine1());
		billingAddress.setAddress2(billingInfo.getAddressLine2());
		billingAddress.setCity(billingInfo.getCity());
		billingAddress.setPostalCode(billingInfo.getZip());
		billingAddress.setState(billingInfo.getState());
		if (Boolean.TRUE.equals(linkToPayOrderResponseData.getResult().getIsShippingSameAsBilling()))
		{
			if ((StringUtils.isNotBlank(linkToPayOrderResponseData.getResult().getContactInfo().getPhoneNumber()))
					&& (!(linkToPayOrderResponseData.getResult().getContactInfo().getPhoneNumber().equals("0"))))
			{
				inquiry.setBillingPhoneNumber(linkToPayOrderResponseData.getResult().getContactInfo().getPhoneNumber());
				inquiry.setShippingPhoneNumber(linkToPayOrderResponseData.getResult().getContactInfo().getPhoneNumber());
			}
			inquiry.setBillingAddress(billingAddress);
			inquiry.setShippingAddress(billingAddress);
		}
		else
		{
			if ((StringUtils.isNotBlank(linkToPayOrderResponseData.getResult().getContactInfo().getPhoneNumber()))
					&& (!(linkToPayOrderResponseData.getResult().getContactInfo().getPhoneNumber().equals("0"))))
			{
				inquiry.setBillingPhoneNumber(linkToPayOrderResponseData.getResult().getContactInfo().getPhoneNumber());
			}
			inquiry.setBillingAddress(billingAddress);
		}

	}
	
	private void setKountSystemProperties()
	{
		System.setProperty(SiteoneCoreConstants.MIGRATION_MODE_ENABLED,Config.getString(SiteoneCoreConstants.MIGRATION_MODE_ENABLED, "true"));
		System.setProperty(SiteoneCoreConstants.API_KEY,configurationService.getConfiguration().getString(SiteoneCoreConstants.API_KEY));
		System.setProperty(SiteoneCoreConstants.API_URL,configurationService.getConfiguration().getString(SiteoneCoreConstants.API_URL));
		System.setProperty(SiteoneCoreConstants.AUTH_URL,configurationService.getConfiguration().getString(SiteoneCoreConstants.AUTH_URL));
		System.setProperty(SiteoneCoreConstants.MERCHANT_ID,configurationService.getConfiguration().getString(SiteoneCoreConstants.MERCHANT_ID));
	}

	@Override
	public SiteoneCCPaymentAuditLogModel getSiteoneCCAuditDetails(final String orderNumber)
	{
		return siteOneOrderDao.getCCAuditDetails(orderNumber,orderNumber);
	}

	@Override
	public SiteoneKountDataModel getKountInquiryCallDetails(final String orderNumber)
	{
		return siteOneOrderDao.getKountInquiryCallDetails(orderNumber);
	}
		
}
