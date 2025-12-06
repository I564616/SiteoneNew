package com.siteone.integration.services.vertexclient.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jakarta.annotation.Resource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.oxm.XmlMappingException;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.jobs.promotion.service.PromotionFeedCronJobService;
import com.siteone.integration.jobs.promotions.process.PromotionActionUtil;
import com.siteone.integration.jobs.promotions.process.PromotionConditionUtil;
import com.siteone.integration.services.vertexclient.TaxCalculationWebService;
import com.siteone.integration.webserviceclient.vertexclient.CustomerCodeType;
import com.siteone.integration.webserviceclient.vertexclient.CustomerType;
import com.siteone.integration.webserviceclient.vertexclient.FlexibleFields;
import com.siteone.integration.webserviceclient.vertexclient.FlexibleFields.FlexibleCodeField;
import com.siteone.integration.webserviceclient.vertexclient.LineItemQSIType;
import com.siteone.integration.webserviceclient.vertexclient.LocationType;
import com.siteone.integration.webserviceclient.vertexclient.LoginType;
import com.siteone.integration.webserviceclient.vertexclient.MeasureType;
import com.siteone.integration.webserviceclient.vertexclient.ObjectFactory;
import com.siteone.integration.webserviceclient.vertexclient.PostalAddressType;
import com.siteone.integration.webserviceclient.vertexclient.Product;
import com.siteone.integration.webserviceclient.vertexclient.QuotationRequestType;
import com.siteone.integration.webserviceclient.vertexclient.SaleTransactionType;
import com.siteone.integration.webserviceclient.vertexclient.SellerType;
import com.siteone.integration.webserviceclient.vertexclient.TaxAreaLookupType;
import com.siteone.integration.webserviceclient.vertexclient.TaxAreaRequestType;
import com.siteone.integration.webserviceclient.vertexclient.TaxAreaResultType;
import com.siteone.integration.webserviceclient.vertexclient.TaxgisRequestType;
import com.siteone.integration.webserviceclient.vertexclient.VertexEnvelope;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.promotionengineservices.model.PromotionSourceRuleModel;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.util.Config;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.site.BaseSiteService;

public class DefaultTaxCalculationWebService implements TaxCalculationWebService
{

	private WebServiceTemplate webServiceTemplate;
	private final Logger log = Logger.getLogger(DefaultTaxCalculationWebService.class);
	private PromotionFeedCronJobService promotionFeedCronJobService;
	
	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;
	
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;
	
	@Resource(name = "userFacade")
	private UserFacade userFacade;
	

	private CartService cartService;
	private SessionService sessionService;
	
	private static final String SESSION_SHIPTO = "shipTo";

	@Override
	public VertexEnvelope calculateTax(AbstractOrderModel abstractOrder) {
		VertexEnvelope vertexResponse = null;
		String uri = null;
		try
		{
				VertexEnvelope vertexRequest = vertexRequest(abstractOrder);

				if (null != vertexRequest) {
					vertexResponse = getWebServiceResponse(vertexRequest,Config.getString("ue.vertex.url", null));
				}

		}catch (Exception exception){
			log.error("Error occured in vertexClient",exception);
		}
		return vertexResponse;
	}



	
	/**
	 * @param abstractOrder
	 * @return
	 * @throws DatatypeConfigurationException
	 */
	private VertexEnvelope vertexRequest(AbstractOrderModel abstractOrder) throws DatatypeConfigurationException{
		ObjectFactory factory = new ObjectFactory();

		VertexEnvelope vertexEnvelope = factory.createVertexEnvelope();
		if (!userFacade.isAnonymousUser() && (sessionService.getAttribute("isSplitMixedCartEnabledBranch") != null && BooleanUtils.isTrue(sessionService.getAttribute("isSplitMixedCartEnabledBranch"))) 
				|| (sessionService.getAttribute("isMixedCartEnabled") != null 
				&& sessionService.getAttribute("isMixedCartEnabled").toString().equalsIgnoreCase("mixedcart"))) {
		LoginType login = factory.createLoginType();
		login.setTrustedId(Config.getString("ue.vertex.trustedId", null));
		vertexEnvelope.setLogin(login);

		QuotationRequestType quotationRequest = factory.createQuotationRequestType();

		GregorianCalendar documentDateGC = new GregorianCalendar();
		documentDateGC.setTime(new Date());
		XMLGregorianCalendar documentDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(documentDateGC);
		quotationRequest.setDocumentDate(documentDate);

		PointOfServiceModel store = abstractOrder.getPointOfService();

		quotationRequest.setTransactionType(SaleTransactionType.SALE);

		quotationRequest.setTransactionId(SiteoneintegrationConstants.TAX_REQ_TRANSACTIONID);
		final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		B2BUnitData sessionShipTo = (B2BUnitData) getSessionService().getAttribute(SESSION_SHIPTO);


		abstractOrder.getEntries().forEach(cartEntry -> {


			LineItemQSIType lineItem = factory.createLineItemQSIType();
			lineItem.setLocationCode(store.getStoreId());
			lineItem.setVendorSKU(cartEntry.getProduct().getCode());
			lineItem.setLineItemId(UUID.randomUUID().toString());
			SellerType seller = factory.createSellerType();
			final BaseSiteModel basesite = baseSiteService.getCurrentBaseSite();
			if(basesite.getUid().equalsIgnoreCase(SiteoneintegrationConstants.BASESITE_CA)) {
				seller.setCompany(SiteoneintegrationConstants.TAX_REQ_COMPANY_CA);
				seller.setDivision(SiteoneintegrationConstants.TAX_REQ_DIVISION_CA);
			}
			else {
				seller.setCompany(SiteoneintegrationConstants.TAX_REQ_COMPANY);
				seller.setDivision(SiteoneintegrationConstants.TAX_REQ_DIVISION_US);
			}
			LocationType sellerLocationType = factory.createLocationType();

			AddressModel storeAddress = store.getAddress();
			if(cartEntry.getDeliveryPointOfService()!=null) 
			{
				storeAddress=cartEntry.getDeliveryPointOfService().getAddress();
			}
			else {
				storeAddress = store.getAddress();
			}
			if(null != storeAddress){
				if(StringUtils.isNotEmpty(storeAddress.getGeoCode())) {
					sellerLocationType.setTaxAreaId(Integer.valueOf(storeAddress.getGeoCode()));
				} else {
					sellerLocationType.setStreetAddress1(storeAddress.getStreetname());
					sellerLocationType.setCity(storeAddress.getTown());
					sellerLocationType.setPostalCode(storeAddress.getPostalcode());
					if(null != storeAddress.getCountry()){
						sellerLocationType.setCountry(storeAddress.getCountry().getIsocode());
					}
				}

			}
			else
			{
				log.error("storeAddress is null for seller location");
			}


			seller.setPhysicalOrigin(sellerLocationType);

			seller.setAdministrativeOrigin(sellerLocationType);

			lineItem.setSeller(seller);

			CustomerType customer = factory.createCustomerType();

			CustomerCodeType customerCode = factory.createCustomerCodeType();
			
			//Setting accountNumber of header shipTo
			if(null != sessionShipTo && null != sessionShipTo.getUid()) {
				customerCode.setValue(StringUtils.substringBeforeLast(sessionShipTo.getUid().trim(), SiteoneintegrationConstants.SEPARATOR_UNDERSCORE));
			}else
			{
				customerCode.setValue(Config.getString("vertex.Guestuser.accountNumber.defaultvalue", null));

			}

			customer.setCustomerCode(customerCode);

			LocationType customerLocationType = factory.createLocationType();

			AddressModel deliverAddress = null;
			if(cartEntry.getDeliveryMode()!=null && (cartEntry.getDeliveryMode().getCode().equalsIgnoreCase("free-standard-shipping")
					||cartEntry.getDeliveryMode().getCode().equalsIgnoreCase("standard-net"))) 
			{
				deliverAddress=abstractOrder.getDeliveryAddress();
			}
			else {
				deliverAddress = cartEntry.getDeliveryPointOfService().getAddress();
			}

			if(null != deliverAddress){
				if(StringUtils.isNotEmpty(deliverAddress.getGeoCode())) {
					customerLocationType.setTaxAreaId(Integer.valueOf(deliverAddress.getGeoCode()));
				} else {
					customerLocationType.setStreetAddress1(deliverAddress.getStreetnumber());
					customerLocationType.setStreetAddress2(deliverAddress.getStreetname());
					customerLocationType.setCity(deliverAddress.getTown());
					customerLocationType.setPostalCode(deliverAddress.getPostalcode());
					if(null != deliverAddress.getCountry()){
						customerLocationType.setCountry(deliverAddress.getCountry().getIsocode());
					}
				}
			}
			else
			{
				log.error("storeAddress is null for customer location");
			}

			customer.setDestination(customerLocationType);
			customer.setAdministrativeDestination(customerLocationType);

			lineItem.setCustomer(customer);
          
			Product product = factory.createProduct();
			product.setValue(cartEntry.getProduct().getCategoryCode());
			product.setProductClass(cartEntry.getProduct().getItemNumber());

			lineItem.setProduct(product);
			
			Long quantity = cartEntry.getQuantity();
			MeasureType measure = factory.createMeasureType();
			measure.setValue(new BigDecimal(quantity));
			measure.setUnitOfMeasure(cartEntry.getInventoryUOM().getCode());
			
			lineItem.setQuantity(measure);

			String productSkuId = cartEntry.getProduct().getCode();
			Double bigBagIncludedPrice = 0.0d;
			final double bigBagTotal = (cartEntry.getBigBagInfo() != null 
			        && Boolean.TRUE.equals(cartEntry.getBigBagInfo().getIsChecked()) 
			        && cartEntry.getBigBagInfo().getUnitPrice() != null) 
			    ? Double.valueOf(cartEntry.getBigBagInfo().getUnitPrice()) 
			    : 0.00;
			bigBagIncludedPrice = cartEntry.getBasePrice() + bigBagTotal;
			if (null != cartEntry.getDiscountValues() && !cartEntry.getDiscountValues().isEmpty()) {

				// Calculating discount amount per unit
				Double discountAmountPerUnit = cartEntry.getDiscountValues().get(0).getAppliedValue() / quantity;

				// Calculating discounted price per unit. 
				Double discountedUnitPrice = bigBagIncludedPrice - discountAmountPerUnit;
				lineItem.setUnitPrice(new BigDecimal(discountedUnitPrice));
				BigDecimal extendedPrice = BigDecimal.valueOf(discountedUnitPrice * quantity);
				lineItem.setExtendedPrice(extendedPrice);
			} else {
				lineItem.setUnitPrice(new BigDecimal(bigBagIncludedPrice));
				BigDecimal extendedPrice = new BigDecimal(bigBagIncludedPrice * quantity);
				lineItem.setExtendedPrice(extendedPrice);
			}

			if(null != abstractOrder.getAllPromotionResults() && !abstractOrder.getAllPromotionResults().isEmpty()){

				abstractOrder.getAllPromotionResults().forEach(promotionResultModel->{


					if(null != promotionResultModel.getPromotion()) {
						String promotionTitle = promotionResultModel.getPromotion().getTitle();
						if(promotionTitle.contains(SiteoneintegrationConstants.TARGET_CUSTOMER_PERCENTAGE_DISCOUNT_CART) || promotionTitle.contains(SiteoneintegrationConstants.COUPON_CODE_PERCENTAGE_DISCOUNT_ON_CART) || promotionTitle.contains(SiteoneintegrationConstants.PRODUCT_MULTIBUY_FIXED_PRICE)
						) {
							PromotionSourceRuleModel promotionSourceRule =  promotionFeedCronJobService.getPromotionSourceRuleByTitle(promotionTitle);


							Map<String, String> promotionMap = new HashMap<String, String>();

							if(null != promotionSourceRule) {
								
								Double promoBagIncludedPrice = 0.0d;
								final double promoBagTotal = (cartEntry.getBigBagInfo() != null 
								        && Boolean.TRUE.equals(cartEntry.getBigBagInfo().getIsChecked()) 
								        && cartEntry.getBigBagInfo().getUnitPrice() != null) 
								    ? Double.valueOf(cartEntry.getBigBagInfo().getUnitPrice()) 
								    : 0.00;

								if(promotionTitle.contains(SiteoneintegrationConstants.TARGET_CUSTOMER_PERCENTAGE_DISCOUNT_CART) || promotionTitle.contains(SiteoneintegrationConstants.COUPON_CODE_PERCENTAGE_DISCOUNT_ON_CART) ){
									String couponId = "6";
									PromotionActionUtil.processAction(couponId, promotionSourceRule,promotionMap ,promotionTitle);
									if(null != promotionMap.get("PercentOff")) {
										String percentOff = promotionMap.get("PercentOff");
										double disAmount =  (((Integer.parseInt(percentOff)) * cartEntry.getBasePrice()) / 100) ;										
										promoBagIncludedPrice = cartEntry.getBasePrice() + promoBagTotal;
										double discountedUnitPrice = promoBagIncludedPrice - disAmount;
										lineItem.setUnitPrice(BigDecimal.valueOf(discountedUnitPrice));
										BigDecimal extendedPrice = BigDecimal.valueOf(discountedUnitPrice * quantity);
										lineItem.setExtendedPrice(extendedPrice);
									}
								}
								if(promotionTitle.contains(SiteoneintegrationConstants.PRODUCT_MULTIBUY_FIXED_PRICE)) {
									String couponId = "8";
									PromotionConditionUtil.processCondition(couponId, promotionSourceRule, promotionMap, SiteoneintegrationConstants.PRODUCT_MULTIBUY_FIXED_PRICE);
									String skuId = promotionMap.get("InclusiveSkuId");
									if(null != skuId && !skuId.isEmpty() && skuId.equalsIgnoreCase(productSkuId)){
										PromotionActionUtil.processAction(couponId, promotionSourceRule, promotionMap,SiteoneintegrationConstants.PRODUCT_MULTIBUY_FIXED_PRICE);
										String priceOverride = promotionMap.get("PriceOverride");
										double discountedUnitPrice = (Double.parseDouble(priceOverride) /quantity);
										discountedUnitPrice += promoBagTotal;
										lineItem.setUnitPrice(BigDecimal.valueOf(discountedUnitPrice));
										BigDecimal extendedPrice = BigDecimal.valueOf(discountedUnitPrice * quantity);
										lineItem.setExtendedPrice(extendedPrice);
									}
								}
							}
						}
					}
				});
			}


			FlexibleFields vertexRulesFlexibleField = factory.createFlexibleFields();
			FlexibleCodeField deliveryMethod = factory.createFlexibleFieldsFlexibleCodeField();
			deliveryMethod.setFieldId(SiteoneintegrationConstants.FLEXIBLE_FIELD_DELIVERY_METHOD_FIELD_ID);
			deliveryMethod.setValue(SiteoneintegrationConstants.UE_ORDERTYPES.get(cartEntry.getDeliveryMode().getCode().toUpperCase()));
			vertexRulesFlexibleField.getFlexibleCodeField().add(deliveryMethod);

			FlexibleCodeField orderType = factory.createFlexibleFieldsFlexibleCodeField();
			orderType.setFieldId(SiteoneintegrationConstants.FLEXIBLE_FIELD_ORDER_TYPE_FIELD_ID);
			orderType.setValue(SiteoneintegrationConstants.UE_ORDER);
			vertexRulesFlexibleField.getFlexibleCodeField().add(orderType);

			FlexibleCodeField orderNumber = factory.createFlexibleFieldsFlexibleCodeField();
			orderNumber.setFieldId(SiteoneintegrationConstants.FLEXIBLE_FIELD_ORDER_NUMBER_FIELD_ID);

			if(b2bCustomerModel!=null) {
				String cartId = b2bCustomerModel.getCurrentCarId();
				orderNumber.setValue(cartId);
			}else {
				if(getCartService()!=null && getCartService().hasSessionCart()){
					orderNumber.setValue(getCartService().getSessionCart().getCode());
				}else {
					orderNumber.setValue(Config.getString("vertex.Guestuser.orderNumber.defaultvalue", null));
				}
			}

			vertexRulesFlexibleField.getFlexibleCodeField().add(orderNumber);

			lineItem.setFlexibleFields(vertexRulesFlexibleField);
			quotationRequest.getLineItem().add(lineItem);

		});
		LineItemQSIType lineItemFreight = factory.createLineItemQSIType();
		lineItemFreight.setVendorSKU(Config.getString("vertex.freight.vendorSKU.defaultvalue", null));

		lineItemFreight.setLineItemId(UUID.randomUUID().toString());

		
		SellerType seller = factory.createSellerType();
		final BaseSiteModel basesite = baseSiteService.getCurrentBaseSite();
		if(basesite.getUid().equalsIgnoreCase(SiteoneintegrationConstants.BASESITE_CA)) {
			seller.setCompany(SiteoneintegrationConstants.TAX_REQ_COMPANY_CA);
			seller.setDivision(SiteoneintegrationConstants.TAX_REQ_DIVISION_CA);
		}
		else {
			seller.setCompany(SiteoneintegrationConstants.TAX_REQ_COMPANY);
			seller.setDivision(SiteoneintegrationConstants.TAX_REQ_DIVISION_US);
		}
		
		LocationType sellerLocationType = factory.createLocationType();

		AddressModel storeAddress = store.getAddress();

		if(null != storeAddress){
			if(StringUtils.isNotEmpty(storeAddress.getGeoCode())) {
				sellerLocationType.setTaxAreaId(Integer.valueOf(storeAddress.getGeoCode()));
			} else {
				sellerLocationType.setStreetAddress1(storeAddress.getStreetname());
				sellerLocationType.setCity(storeAddress.getTown());
				sellerLocationType.setPostalCode(storeAddress.getPostalcode());
				if(null != storeAddress.getCountry()){
					sellerLocationType.setCountry(storeAddress.getCountry().getIsocode());
				}
			}
		}
		else
		{
			log.error("storeAddress is null for seller location at cart level");
		}
		seller.setPhysicalOrigin(sellerLocationType);

		seller.setAdministrativeOrigin(sellerLocationType);

		lineItemFreight.setSeller(seller);

		CustomerType customer = factory.createCustomerType();

		CustomerCodeType customerCode = factory.createCustomerCodeType();
		
		if(null != sessionShipTo && null != sessionShipTo.getUid()) {
			customerCode.setValue(StringUtils.substringBeforeLast(sessionShipTo.getUid().trim(), SiteoneintegrationConstants.SEPARATOR_UNDERSCORE));
		}else
		{
			customerCode.setValue(Config.getString("vertex.Guestuser.accountNumber.defaultvalue", null));

		}

		customer.setCustomerCode(customerCode);

		LocationType customerLocationType = factory.createLocationType();
		AddressModel deliverAddress = abstractOrder.getDeliveryAddress();
		if(null != deliverAddress){
			if(StringUtils.isNotEmpty(deliverAddress.getGeoCode())) {
				customerLocationType.setTaxAreaId(Integer.valueOf(deliverAddress.getGeoCode()));
			} else {
				customerLocationType.setStreetAddress1(deliverAddress.getStreetnumber());
				customerLocationType.setStreetAddress2(deliverAddress.getStreetname());
				customerLocationType.setCity(deliverAddress.getTown());
				customerLocationType.setPostalCode(deliverAddress.getPostalcode());
				if(null != storeAddress.getCountry()){
					customerLocationType.setCountry(deliverAddress.getCountry().getIsocode());
				}
			}
		}
		else
		{
			log.error("storeAddress is null for customer location at cart level");
		}

		customer.setDestination(customerLocationType);
		customer.setAdministrativeDestination(customerLocationType);

		lineItemFreight.setCustomer(customer);

		Product product = factory.createProduct();

		product.setValue(Config.getString("vertex.freight.productClassValue.defaultvalue", null));
		product.setProductClass(Config.getString("vertex.freight.productClass.defaultvalue", null));
		MeasureType measure = factory.createMeasureType();
		measure.setValue(new BigDecimal(Config.getInt("vertex.freight.quantity.defaultvalue", 1)));
		measure.setUnitOfMeasure(Config.getString("vertex.freight.unitofmeasure.defaultvalue", null));
		lineItemFreight.setQuantity(measure);

		if(abstractOrder.getFreight()!=null){
			double freightAmount = Double.parseDouble(abstractOrder.getFreight());
			lineItemFreight.setExtendedPrice(BigDecimal.valueOf(freightAmount));
			lineItemFreight.setUnitPrice(BigDecimal.valueOf(freightAmount));
		} else {
			lineItemFreight.setExtendedPrice(new BigDecimal("0.00"));
			lineItemFreight.setUnitPrice(new BigDecimal("0.00"));
		}
		lineItemFreight.setProduct(product);

		FlexibleFields vertexRulesFlexibleFieldFreight = factory.createFlexibleFields();
		FlexibleCodeField deliveryMethod1 = factory.createFlexibleFieldsFlexibleCodeField();
		deliveryMethod1.setFieldId(SiteoneintegrationConstants.FLEXIBLE_FIELD_DELIVERY_METHOD_FIELD_ID);
		if(null!=abstractOrder.getOrderType()) 
		{
		deliveryMethod1.setValue(SiteoneintegrationConstants.UE_ORDERTYPES.get(abstractOrder.getOrderType().getCode()));
		}
		else {
			deliveryMethod1.setValue(SiteoneintegrationConstants.UE_DEFAULT_ORDERTYPE);
		}
		vertexRulesFlexibleFieldFreight.getFlexibleCodeField().add(deliveryMethod1);

		FlexibleCodeField orderType1 = factory.createFlexibleFieldsFlexibleCodeField();
		orderType1.setFieldId(SiteoneintegrationConstants.FLEXIBLE_FIELD_ORDER_TYPE_FIELD_ID);
		orderType1.setValue(SiteoneintegrationConstants.UE_ORDER);
		vertexRulesFlexibleFieldFreight.getFlexibleCodeField().add(orderType1);

		FlexibleCodeField orderNumber1 = factory.createFlexibleFieldsFlexibleCodeField();
		orderNumber1.setFieldId(SiteoneintegrationConstants.FLEXIBLE_FIELD_ORDER_NUMBER_FIELD_ID);


		if(b2bCustomerModel!=null) {
			String cartId1 = b2bCustomerModel.getCurrentCarId();
			orderNumber1.setValue(cartId1);
		} else {
			if(getCartService()!=null && getCartService().hasSessionCart()){
				orderNumber1.setValue(getCartService().getSessionCart().getCode());
			}else {
				orderNumber1.setValue(Config.getString("vertex.Guestuser.orderNumber.defaultvalue", null));
			}
		}

		vertexRulesFlexibleFieldFreight.getFlexibleCodeField().add(orderNumber1);

		lineItemFreight.setFlexibleFields(vertexRulesFlexibleFieldFreight);
		quotationRequest.getLineItem().add(lineItemFreight);

		vertexEnvelope.setQuotationRequest(quotationRequest);
		}
		else {
			LoginType login = factory.createLoginType();
			login.setTrustedId(Config.getString("ue.vertex.trustedId", null));
			vertexEnvelope.setLogin(login);

			QuotationRequestType quotationRequest = factory.createQuotationRequestType();

			GregorianCalendar documentDateGC = new GregorianCalendar();
			documentDateGC.setTime(new Date());
			XMLGregorianCalendar documentDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(documentDateGC);
			quotationRequest.setDocumentDate(documentDate);

			PointOfServiceModel store = abstractOrder.getPointOfService();

			quotationRequest.setLocationCode(store.getStoreId());

			quotationRequest.setTransactionType(SaleTransactionType.SALE);

			quotationRequest.setTransactionId(SiteoneintegrationConstants.TAX_REQ_TRANSACTIONID);

			SellerType seller = factory.createSellerType();
			final BaseSiteModel basesite = baseSiteService.getCurrentBaseSite();
			if(basesite.getUid().equalsIgnoreCase(SiteoneintegrationConstants.BASESITE_CA)) {
				seller.setCompany(SiteoneintegrationConstants.TAX_REQ_COMPANY_CA);
				seller.setDivision(SiteoneintegrationConstants.TAX_REQ_DIVISION_CA);
			}
			else {
				seller.setCompany(SiteoneintegrationConstants.TAX_REQ_COMPANY);
				seller.setDivision(SiteoneintegrationConstants.TAX_REQ_DIVISION_US);
			}
			
			LocationType sellerLocationType = factory.createLocationType();

			AddressModel storeAddress = store.getAddress();

			if(null != storeAddress){
				if(StringUtils.isNotEmpty(storeAddress.getGeoCode())) {
					sellerLocationType.setTaxAreaId(Integer.valueOf(storeAddress.getGeoCode()));
				} else {
					sellerLocationType.setStreetAddress1(storeAddress.getStreetnumber());
					sellerLocationType.setStreetAddress2(storeAddress.getStreetname());
					sellerLocationType.setCity(storeAddress.getTown());
					sellerLocationType.setPostalCode(storeAddress.getPostalcode());
					if(null != storeAddress.getCountry()){
						sellerLocationType.setCountry(storeAddress.getCountry().getIsocode());
					}
				}
			}
			else
			{
				log.error("storeAddress is null for seller location - normal cart");
			}
			seller.setPhysicalOrigin(sellerLocationType);

			seller.setAdministrativeOrigin(sellerLocationType);

			quotationRequest.setSeller(seller);

			CustomerType customer = factory.createCustomerType();

			CustomerCodeType customerCode = factory.createCustomerCodeType();

			final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
			
			B2BUnitData sessionShipTo = (B2BUnitData) getSessionService().getAttribute(SESSION_SHIPTO);
			if(null != sessionShipTo && null != sessionShipTo.getUid()) {
				customerCode.setValue(StringUtils.substringBeforeLast(sessionShipTo.getUid().trim(), SiteoneintegrationConstants.SEPARATOR_UNDERSCORE));
			}else
			{
				customerCode.setValue(Config.getString("vertex.Guestuser.accountNumber.defaultvalue", null));
			
			}

			customer.setCustomerCode(customerCode);

			LocationType customerLocationType = factory.createLocationType();

			AddressModel deliverAddress = null;
			if(abstractOrder.getOrderType() !=null && abstractOrder.getOrderType().getCode().equalsIgnoreCase("PICKUP")) 
			{
				deliverAddress = store.getAddress();
			}
			else {
				deliverAddress=abstractOrder.getDeliveryAddress();
			}

			 if(null != deliverAddress){
				 if(StringUtils.isNotEmpty(deliverAddress.getGeoCode())) {
						customerLocationType.setTaxAreaId(Integer.valueOf(deliverAddress.getGeoCode()));
					} else {
						customerLocationType.setStreetAddress1(deliverAddress.getStreetnumber());
						customerLocationType.setStreetAddress2(deliverAddress.getStreetname());
						customerLocationType.setCity(deliverAddress.getTown());
						customerLocationType.setPostalCode(deliverAddress.getPostalcode());
						if(null != deliverAddress.getCountry()){
							customerLocationType.setCountry(deliverAddress.getCountry().getIsocode());
						}
					}
			}
			else
			{
				log.error("storeAddress is null for customer location - normal cart");
			}

			customer.setDestination(customerLocationType);
			customer.setAdministrativeDestination(customerLocationType);

			quotationRequest.setCustomer(customer);
			abstractOrder.getEntries().forEach(cartEntry -> {


				LineItemQSIType lineItem = factory.createLineItemQSIType();
				lineItem.setVendorSKU(cartEntry.getProduct().getCode());
				lineItem.setLineItemId(UUID.randomUUID().toString());

				Long quantity = cartEntry.getQuantity();

				MeasureType measure = factory.createMeasureType();
				measure.setValue(new BigDecimal(quantity));
				measure.setUnitOfMeasure(cartEntry.getInventoryUOM().getCode());
				lineItem.setQuantity(measure);

				String productSkuId = cartEntry.getProduct().getCode();

				Double bigBagIncludedPrice = 0.0d;
				final double bigBagTotal = (cartEntry.getBigBagInfo() != null 
				        && Boolean.TRUE.equals(cartEntry.getBigBagInfo().getIsChecked()) 
				        && cartEntry.getBigBagInfo().getUnitPrice() != null) 
				    ? Double.valueOf(cartEntry.getBigBagInfo().getUnitPrice()) 
				    : 0.00;
				bigBagIncludedPrice = cartEntry.getBasePrice() + bigBagTotal;
				if (null != cartEntry.getDiscountValues() && !cartEntry.getDiscountValues().isEmpty()) {

					// Calculating discount amount per unit
					Double discountAmountPerUnit = cartEntry.getDiscountValues().get(0).getAppliedValue() / quantity;

					// Calculating discounted price per unit.
					Double discountedUnitPrice = bigBagIncludedPrice - discountAmountPerUnit;
					lineItem.setUnitPrice(new BigDecimal(discountedUnitPrice));
					BigDecimal extendedPrice = BigDecimal.valueOf(discountedUnitPrice * quantity);
					lineItem.setExtendedPrice(extendedPrice);
				} else {
					lineItem.setUnitPrice(new BigDecimal(bigBagIncludedPrice));
					BigDecimal extendedPrice = new BigDecimal(bigBagIncludedPrice * quantity);
					lineItem.setExtendedPrice(extendedPrice);
				}

				if(null != abstractOrder.getAllPromotionResults() && !abstractOrder.getAllPromotionResults().isEmpty()){

					abstractOrder.getAllPromotionResults().forEach(promotionResultModel->{


						if(null != promotionResultModel.getPromotion()) {
							String promotionTitle = promotionResultModel.getPromotion().getTitle();
							if(promotionTitle.contains(SiteoneintegrationConstants.TARGET_CUSTOMER_PERCENTAGE_DISCOUNT_CART) || promotionTitle.contains(SiteoneintegrationConstants.COUPON_CODE_PERCENTAGE_DISCOUNT_ON_CART) || promotionTitle.contains(SiteoneintegrationConstants.PRODUCT_MULTIBUY_FIXED_PRICE)
							) {
								PromotionSourceRuleModel promotionSourceRule =  promotionFeedCronJobService.getPromotionSourceRuleByTitle(promotionTitle);


								Map<String, String> promotionMap = new HashMap<String, String>();

								if(null != promotionSourceRule) {
									
									Double promoBagIncludedPrice = 0.0d;
									final double promoBagTotal = (cartEntry.getBigBagInfo() != null 
									        && Boolean.TRUE.equals(cartEntry.getBigBagInfo().getIsChecked()) 
									        && cartEntry.getBigBagInfo().getUnitPrice() != null) 
									    ? Double.valueOf(cartEntry.getBigBagInfo().getUnitPrice())
									    : 0.00;

									if(promotionTitle.contains(SiteoneintegrationConstants.TARGET_CUSTOMER_PERCENTAGE_DISCOUNT_CART) || promotionTitle.contains(SiteoneintegrationConstants.COUPON_CODE_PERCENTAGE_DISCOUNT_ON_CART) ){
										String couponId = "6";
										PromotionActionUtil.processAction(couponId, promotionSourceRule,promotionMap ,promotionTitle);
										if(null != promotionMap.get("PercentOff")) {
											String percentOff = promotionMap.get("PercentOff");
											double disAmount =  (((Integer.parseInt(percentOff)) * cartEntry.getBasePrice()) / 100) ;
											promoBagIncludedPrice = cartEntry.getBasePrice() + promoBagTotal;
											double discountedUnitPrice = promoBagIncludedPrice - disAmount;
											lineItem.setUnitPrice(BigDecimal.valueOf(discountedUnitPrice));
											BigDecimal extendedPrice = BigDecimal.valueOf(discountedUnitPrice * quantity);
											lineItem.setExtendedPrice(extendedPrice);
										}
									}
									if(promotionTitle.contains(SiteoneintegrationConstants.PRODUCT_MULTIBUY_FIXED_PRICE)) {
										String couponId = "8";
										PromotionConditionUtil.processCondition(couponId, promotionSourceRule, promotionMap, SiteoneintegrationConstants.PRODUCT_MULTIBUY_FIXED_PRICE);
										String skuId = promotionMap.get("InclusiveSkuId");
										if(null != skuId && !skuId.isEmpty() && skuId.equalsIgnoreCase(productSkuId)){
											PromotionActionUtil.processAction(couponId, promotionSourceRule, promotionMap,SiteoneintegrationConstants.PRODUCT_MULTIBUY_FIXED_PRICE);
											String priceOverride = promotionMap.get("PriceOverride");
											double discountedUnitPrice = (Double.parseDouble(priceOverride) /quantity);
											discountedUnitPrice += promoBagTotal;
											lineItem.setUnitPrice(BigDecimal.valueOf(discountedUnitPrice));
											BigDecimal extendedPrice = BigDecimal.valueOf(discountedUnitPrice * quantity);
											lineItem.setExtendedPrice(extendedPrice);
										}
									}
								}
							}
						}
					});
				}



				Product product = factory.createProduct();

				product.setValue(cartEntry.getProduct().getCategoryCode());
				product.setProductClass(cartEntry.getProduct().getItemNumber());

				lineItem.setProduct(product);

				FlexibleFields vertexRulesFlexibleField = factory.createFlexibleFields();
				FlexibleCodeField deliveryMethod = factory.createFlexibleFieldsFlexibleCodeField();
				deliveryMethod.setFieldId(SiteoneintegrationConstants.FLEXIBLE_FIELD_DELIVERY_METHOD_FIELD_ID);
				deliveryMethod.setValue(SiteoneintegrationConstants.UE_ORDERTYPES.get(abstractOrder.getOrderType().getCode()));
				vertexRulesFlexibleField.getFlexibleCodeField().add(deliveryMethod);

				FlexibleCodeField orderType = factory.createFlexibleFieldsFlexibleCodeField();
				orderType.setFieldId(SiteoneintegrationConstants.FLEXIBLE_FIELD_ORDER_TYPE_FIELD_ID);
				orderType.setValue(SiteoneintegrationConstants.UE_ORDER);
				vertexRulesFlexibleField.getFlexibleCodeField().add(orderType);

				FlexibleCodeField orderNumber = factory.createFlexibleFieldsFlexibleCodeField();
				orderNumber.setFieldId(SiteoneintegrationConstants.FLEXIBLE_FIELD_ORDER_NUMBER_FIELD_ID);

				if(b2bCustomerModel!=null) {
					String cartId = b2bCustomerModel.getCurrentCarId();
					orderNumber.setValue(cartId);
				}else {
					if(getCartService()!=null && getCartService().hasSessionCart()){
						orderNumber.setValue(getCartService().getSessionCart().getCode());
					}else {
						orderNumber.setValue(Config.getString("vertex.Guestuser.orderNumber.defaultvalue", null));
					}
				}

				vertexRulesFlexibleField.getFlexibleCodeField().add(orderNumber);

				lineItem.setFlexibleFields(vertexRulesFlexibleField);
				quotationRequest.getLineItem().add(lineItem);

			});

			LineItemQSIType lineItemFreight = factory.createLineItemQSIType();
			lineItemFreight.setVendorSKU(Config.getString("vertex.freight.vendorSKU.defaultvalue", null));

			lineItemFreight.setLineItemId(UUID.randomUUID().toString());

			MeasureType measure = factory.createMeasureType();
			measure.setValue(new BigDecimal(Config.getInt("vertex.freight.quantity.defaultvalue", 1)));
			measure.setUnitOfMeasure(Config.getString("vertex.freight.unitofmeasure.defaultvalue", null));
			lineItemFreight.setQuantity(measure);

			if(abstractOrder.getFreight()!=null){
				double freightAmount = Double.parseDouble(abstractOrder.getFreight());
				lineItemFreight.setExtendedPrice(BigDecimal.valueOf(freightAmount));
				lineItemFreight.setUnitPrice(BigDecimal.valueOf(freightAmount));
			} else {
				lineItemFreight.setExtendedPrice(new BigDecimal("0.00"));
				lineItemFreight.setUnitPrice(new BigDecimal("0.00"));
			}

			Product product = factory.createProduct();

			product.setValue(Config.getString("vertex.freight.productClassValue.defaultvalue", null));
			product.setProductClass(Config.getString("vertex.freight.productClass.defaultvalue", null));

			lineItemFreight.setProduct(product);

			FlexibleFields vertexRulesFlexibleFieldFreight = factory.createFlexibleFields();
			FlexibleCodeField deliveryMethod1 = factory.createFlexibleFieldsFlexibleCodeField();
			deliveryMethod1.setFieldId(SiteoneintegrationConstants.FLEXIBLE_FIELD_DELIVERY_METHOD_FIELD_ID);
			deliveryMethod1.setValue(SiteoneintegrationConstants.UE_ORDERTYPES.get(abstractOrder.getOrderType().getCode()));
			vertexRulesFlexibleFieldFreight.getFlexibleCodeField().add(deliveryMethod1);

			FlexibleCodeField orderType1 = factory.createFlexibleFieldsFlexibleCodeField();
			orderType1.setFieldId(SiteoneintegrationConstants.FLEXIBLE_FIELD_ORDER_TYPE_FIELD_ID);
			orderType1.setValue(SiteoneintegrationConstants.UE_ORDER);
			vertexRulesFlexibleFieldFreight.getFlexibleCodeField().add(orderType1);

			FlexibleCodeField orderNumber1 = factory.createFlexibleFieldsFlexibleCodeField();
			orderNumber1.setFieldId(SiteoneintegrationConstants.FLEXIBLE_FIELD_ORDER_NUMBER_FIELD_ID);


			if(b2bCustomerModel!=null) {
				String cartId1 = b2bCustomerModel.getCurrentCarId();
				orderNumber1.setValue(cartId1);
			} else {
				if(getCartService()!=null && getCartService().hasSessionCart()){
					orderNumber1.setValue(getCartService().getSessionCart().getCode());
				}else {
					orderNumber1.setValue(Config.getString("vertex.Guestuser.orderNumber.defaultvalue", null));
				}
			}

			vertexRulesFlexibleFieldFreight.getFlexibleCodeField().add(orderNumber1);

			lineItemFreight.setFlexibleFields(vertexRulesFlexibleFieldFreight);
			quotationRequest.getLineItem().add(lineItemFreight);

			vertexEnvelope.setQuotationRequest(quotationRequest);


		}

		return vertexEnvelope;

	}


	/**
	 * @return the webServiceTemplate
	 */
	public WebServiceTemplate getWebServiceTemplate() {
		return webServiceTemplate;
	}

	/**
	 * @param webServiceTemplate the webServiceTemplate to set
	 */
	public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {
		this.webServiceTemplate = webServiceTemplate;
	}

	/**
	 * @return the promotionFeedCronJobService
	 */
	public PromotionFeedCronJobService getPromotionFeedCronJobService() {
		return promotionFeedCronJobService;
	}

	/**
	 * @param promotionFeedCronJobService the promotionFeedCronJobService to set
	 */
	public void setPromotionFeedCronJobService(PromotionFeedCronJobService promotionFeedCronJobService) {
		this.promotionFeedCronJobService = promotionFeedCronJobService;
	}

	private VertexEnvelope getWebServiceResponse(VertexEnvelope vertexRequest,String uri) throws XmlMappingException, IOException{
		VertexEnvelope vertexResponse = null;
		final StringWriter writer = new StringWriter();
		final StreamResult result = new StreamResult(writer);

		getWebServiceTemplate().getMarshaller().marshal(vertexRequest, result);
		HttpComponentsMessageSender messageSender = (HttpComponentsMessageSender)getWebServiceTemplate().getMessageSenders()[0];
		messageSender.setConnectionTimeout(Config.getInt("vertex.connection.timeout", 5000));
		messageSender.setReadTimeout(Config.getInt("vertex.read.timeout", 5000));
		final String req = writer.toString();
		log.info("Vertex Req" + req);
		getWebServiceTemplate().setDefaultUri(uri);

		vertexResponse = (VertexEnvelope) webServiceTemplate.marshalSendAndReceive(vertexRequest);
		System.out.println("vertexResponse:"+vertexResponse);
		if (null != vertexResponse) {

			getWebServiceTemplate().getMarshaller().marshal(vertexResponse, result);

			final String res = writer.toString();
			log.info("Vertex Response" + res);
		}
		return vertexResponse;
	}

	/**
	 * @return the cartService
	 */
	public CartService getCartService() {
		return cartService;
	}

	/**
	 * @param cartService the cartService to set
	 */
	public void setCartService(CartService cartService) {
		this.cartService = cartService;
	}



	public SessionService getSessionService() {
		return sessionService;
	}



	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}


}
