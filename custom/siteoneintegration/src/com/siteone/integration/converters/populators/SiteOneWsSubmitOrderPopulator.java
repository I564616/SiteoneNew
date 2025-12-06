package com.siteone.integration.converters.populators;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;

import com.google.common.math.DoubleMath;
import com.siteone.core.enums.OrderTypeEnum;
import com.siteone.core.model.BagInfoModel;
import com.siteone.core.model.InventoryUPCModel;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.jobs.promotion.service.PromotionFeedCronJobService;
import com.siteone.integration.jobs.promotions.process.PromotionActionUtil;
import com.siteone.integration.jobs.promotions.process.PromotionConditionUtil;
import com.siteone.integration.submitOrder.data.SiteOneWsDeliveryInfoData;
import com.siteone.integration.submitOrder.data.SiteOneWsGuestUserInfoData;
import com.siteone.integration.submitOrder.data.SiteOneWsLineItemsData;
import com.siteone.integration.submitOrder.data.SiteOneWsPaymentInfoData;
import com.siteone.integration.submitOrder.data.SiteOneWsPickupInfoData;
import com.siteone.integration.submitOrder.data.SiteOneWsShipmentData;
import com.siteone.integration.submitOrder.data.SiteOneWsShippingInfoData;
import com.siteone.integration.submitOrder.data.SiteOneWsSubmitOrderRequestData;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.enums.CustomerType;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.enums.PhoneContactInfoType;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.order.payment.SiteoneCreditCardPaymentInfoModel;
import de.hybris.platform.core.model.order.payment.SiteonePOAPaymentInfoModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.PhoneContactInfoModel;
import de.hybris.platform.jalo.order.OrderEntry;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.promotionengineservices.model.PromotionSourceRuleModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.util.Config;
import de.hybris.platform.util.DiscountValue;

public class SiteOneWsSubmitOrderPopulator implements Populator<ConsignmentModel, SiteOneWsSubmitOrderRequestData>{
	
	private static final Logger LOG = Logger.getLogger(SiteOneWsSubmitOrderPopulator.class);
	final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;
	private PromotionFeedCronJobService promotionFeedCronJobService;
	private static final double EPSILON = 0.01d;
	private static final Integer Null_value= 0;
	
	@Override
	public void populate(ConsignmentModel source, SiteOneWsSubmitOrderRequestData target) throws ConversionException {
		target.setCorrelationId(UUID.randomUUID().toString());
		OrderModel order = (OrderModel)source.getOrder();
		if(null != order.getUser() && !(CustomerType.GUEST.equals(((CustomerModel) order.getUser()).getType()))) {
			B2BCustomerModel userModel = (B2BCustomerModel) order.getUser();
				final String customerName = null != userModel.getDefaultB2BUnit() ? userModel.getDefaultB2BUnit().getName() : userModel.getName();
				target.setCustomerName(customerName.replaceAll("[^a-zA-Z0-9]", " "));
				target.setUserEmail(userModel.getContactEmail());
				
				if(null != source.getDeliveryMode() 
						&& SiteoneintegrationConstants.DELIVERYMODE_SHIPPING.equalsIgnoreCase(source.getDeliveryMode().getCode()) && order.getHomeStoreNumber() != null)
				{
					target.setHomeStoreNumber(order.getHomeStoreNumber());
			    }
	     }
		if(null != order.getUser() && (CustomerType.GUEST.equals(((CustomerModel) order.getUser()).getType()))) {
			CustomerModel userModel = (CustomerModel) order.getUser();
			final String customerName =  "Guest User";
			target.setCustomerName(customerName);
			target.setUserEmail(userModel.getContactEmail());
			if(null != source.getDeliveryMode() && 
					SiteoneintegrationConstants.DELIVERYMODE_SHIPPING.equalsIgnoreCase(source.getDeliveryMode().getCode()) 
					&& order.getHomeStoreNumber() != null)
			{
				target.setHomeStoreNumber(order.getHomeStoreNumber());
			}
		}
		OrderTypeEnum orderType = order.getOrderType();
		if(order.getUnit()!=null && null !=order.getUnit().getDivision()){			
		    if(order.getUnit().getDivision().getUid().equalsIgnoreCase("US")){
			    target.setDivisionId("1");
     		}else if(order.getUnit().getDivision().getUid().equalsIgnoreCase("CA")){
     			target.setDivisionId("2");
     		}
		}	
		//se-6391 - Change hybris submit order process to UE- modify submit request Json to pass ExternalSystemID
		boolean isPunchoutUser= false;
		if(order.getUnit()!=null && order.getUnit().getIsPunchOutAccount()!=null && order.getUnit().getIsPunchOutAccount().booleanValue()){
			isPunchoutUser = true;
		}
		
		//se-6391 ends
		if(order.getSalesApplication() != null && order.getSalesApplication().toString().equalsIgnoreCase("ECOMMOBILEAPP")){
			target.setExternalSystemId("13");
		}
		else if(order.getUnit()!=null && null !=order.getUnit().getDivision() && order.getUnit().getDivision().getUid().equalsIgnoreCase("CA"))
		{
			target.setExternalSystemId(SiteoneintegrationConstants.EXTERNAL_CA_SYSTEM_ID);
		}
		else{
			target.setExternalSystemId(isPunchoutUser?"7":"2");
		}
		
		
		target.setHybrisOrderNumber(order.getCode());
		String createdDate= dateFormat.format(order.getCreationtime());
		target.setCreatedDate(createdDate);
		
		if(null != order.getUnit()){
			if(order.getUnit().getReportingOrganization() != null){
				target.setBillingNodeId(order.getUnit().getReportingOrganization().getGuid());
			}else{
				target.setBillingNodeId(order.getUnit().getGuid());
			}
		}
		if(null != order.getOrderingAccount())
		{
		target.setOrderingNodeId(order.getOrderingAccount().getGuid());
	    }
		if(null != order.getUser() && !(CustomerType.GUEST.equals(((CustomerModel) order.getUser()).getType()))) {
			LOG.error("Created by contact id " + ((B2BCustomerModel)order.getUser()).getGuid());
			target.setCreatedByContactId(((B2BCustomerModel)order.getUser()).getGuid());
		}
		
		if (null != order.getPurchaseOrderNumber()){
		target.setPurchaseOrderNumber(order.getPurchaseOrderNumber());
		}
		B2BCustomerModel contactPerson = null;
		if(orderType == null) {
		if(null != order.getContactPerson() && SiteoneintegrationConstants.DELIVERYMODE_PICKUP.equalsIgnoreCase(source.getDeliveryMode().getCode())){
			contactPerson =  order.getContactPerson();
		}
		if(null != order.getDeliveryContactPerson() && SiteoneintegrationConstants.DELIVERYMODE_DELIVERY.equalsIgnoreCase(source.getDeliveryMode().getCode())){
			contactPerson =  order.getDeliveryContactPerson();
		}
		if(null != order.getShippingContactPerson() && SiteoneintegrationConstants.DELIVERYMODE_SHIPPING.equalsIgnoreCase(source.getDeliveryMode().getCode())){
			contactPerson =  order.getShippingContactPerson();
		}
		} else {
			contactPerson =  order.getContactPerson();
		}
		
		
		if (null!= contactPerson)	{
			target.setContactName(contactPerson.getName());
			LOG.error("Contact person guid " + contactPerson.getGuid());
			target.setContactId(contactPerson.getGuid());		
			if (null != contactPerson.getContactInfos())
			{
				contactPerson.getContactInfos().forEach(info -> {
					if (info instanceof PhoneContactInfoModel)
					{
						final PhoneContactInfoModel phoneInfo = (PhoneContactInfoModel) info;
						if (PhoneContactInfoType.MOBILE.equals(phoneInfo.getType()))
						{
							target.setContactPhoneId(phoneInfo.getPhoneId());
						}
					}
				});
			}
		}	
		if(null != order.getCurrency()){
			
			if("USD".equalsIgnoreCase(order.getCurrency().getIsocode())){
				target.setCurrencyId("1");
			}else if("CAD".equalsIgnoreCase(order.getCurrency().getIsocode())){
				target.setCurrencyId("2");
			}
		}
		
		if(orderType!=null)	{
			if(null != order.getTotalTax()) {
				target.setTax(Double.toString(order.getTotalTax()));
			}else {
				target.setTax("0.0");
			}
		} else {
		if(null != source.getTax()) {
				target.setTax(source.getTax());
			}else {
				target.setTax("0.0");
		}
		}
		/*int countOfConsignments = order.getConsignments().size();
		final double orderDiscountAmount = 	getOrderDiscountsAmount(order);
		final double consignmentDiscountAmount  = orderDiscountAmount / countOfConsignments;
		target.setSubTotal(Double.toString(Double.parseDouble(source.getSubTotal())-consignmentDiscountAmount));		
		
		
		LOG.error("Order subtotal " + target.getSubTotal());
		LOG.error("Tax value in order " + target.getTax());
		LOG.error("Total order price " + source.getTotal());
		LOG.error("Order subtotal " + target.getSubTotal());
		
*/
		
		if(null != source.getDeliveryPointOfService()){
			target.setStoreNumber(source.getDeliveryPointOfService().getStoreId());
		}				
		if(null != source.getDeliveryMode()){
			if(SiteoneintegrationConstants.DELIVERYMODE_SHIPPING.equalsIgnoreCase(source.getDeliveryMode().getCode())) {
				target.setDeliveryMode(SiteoneintegrationConstants.UE_ORDERTYPES.get(source.getDeliveryMode().getCode().toUpperCase()));
			}else if(SiteoneintegrationConstants.DELIVERYMODE_DELIVERY.equalsIgnoreCase(source.getDeliveryMode().getCode())) {
				target.setDeliveryMode(SiteoneintegrationConstants.UE_ORDERTYPES.get(source.getDeliveryMode().getCode().toUpperCase()));
			}else {
				target.setDeliveryMode(source.getDeliveryMode().getCode().toLowerCase());
			}
		}else {
			LOG.error("No delivery mode present for the order");
		}
		
		if(orderType!= null)	{
		if(null != order.getDeliveryAddress() && 
				(SiteoneintegrationConstants.DELIVERYMODE_DELIVERY.equalsIgnoreCase(source.getDeliveryMode().getCode()) || SiteoneintegrationConstants.DELIVERYMODE_SHIPPING.equalsIgnoreCase(source.getDeliveryMode().getCode()))){			
			target.setDeliveryAddressId(order.getDeliveryAddress().getGuid());		
		}
		} else {
			if(null != order.getDeliveryAddress() && (SiteoneintegrationConstants.DELIVERYMODE_DELIVERY.equalsIgnoreCase(source.getDeliveryMode().getCode()))) {
				target.setDeliveryAddressId(order.getDeliveryAddress().getGuid());	
			} 	
			else if (null != order.getShippingAddress() && (SiteoneintegrationConstants.DELIVERYMODE_SHIPPING.equalsIgnoreCase(source.getDeliveryMode().getCode()))) {
				target.setDeliveryAddressId(order.getShippingAddress().getGuid());	
			}
		}
		
		if(orderType== null)	{
		if(null != order.getDeliveryInstruction() && SiteoneintegrationConstants.DELIVERYMODE_DELIVERY.equalsIgnoreCase(source.getDeliveryMode().getCode())){
			target.setDeliveryInstructions(order.getDeliveryInstruction());
		} 
		if(null != order.getPickupInstruction() && SiteoneintegrationConstants.DELIVERYMODE_PICKUP.equalsIgnoreCase(source.getDeliveryMode().getCode())){
			target.setDeliveryInstructions(order.getPickupInstruction());
		}
		if(null != order.getShippingInstruction() && SiteoneintegrationConstants.DELIVERYMODE_SHIPPING.equalsIgnoreCase(source.getDeliveryMode().getCode())){
			target.setDeliveryInstructions(order.getShippingInstruction());
		}
		}
		if(null != order.getSpecialInstruction() && !SiteoneintegrationConstants.DELIVERYMODE_SHIPPING.equalsIgnoreCase(source.getDeliveryMode().getCode()))
		{
			target.setDeliveryInstructions(order.getSpecialInstruction());
		}
		
		if(orderType!= null)	{
			if(null != order.getRequestedDate()){
				String deliveryDate=dateFormat.format(order.getRequestedDate());
				target.setDeliveryDate(deliveryDate);
			}else if(SiteoneintegrationConstants.DELIVERYMODE_SHIPPING.equalsIgnoreCase(source.getDeliveryMode().getCode())) {
				target.setDeliveryDate(dateFormat.format(new Date()));	
			}else if(SiteoneintegrationConstants.DELIVERYMODE_PICKUP.equalsIgnoreCase(source.getDeliveryMode().getCode())){
				target.setDeliveryDate(getTomorrowDate());
				target.setDeliveryPeriodId("2");
			}
		}else {
			if(SiteoneintegrationConstants.DELIVERYMODE_SHIPPING.equalsIgnoreCase(source.getDeliveryMode().getCode())) {
				target.setDeliveryDate(dateFormat.format(new Date()));	
			}
			else if(null != order.getRequestedDate()){
				String deliveryDate=dateFormat.format(order.getRequestedDate());
				target.setDeliveryDate(deliveryDate);
			}
			else{
			target.setDeliveryDate(getTomorrowDate());
			}
		}
		
		
		if(null != order.getRequestedMeridian() && !SiteoneintegrationConstants.DELIVERYMODE_SHIPPING.equalsIgnoreCase(source.getDeliveryMode().getCode()))
		{
			if(order.getRequestedMeridian().getCode().equalsIgnoreCase("AM"))
			{
				target.setDeliveryPeriodId("2");
			}
			else if(order.getRequestedMeridian().getCode().equalsIgnoreCase("PM"))
			{
				target.setDeliveryPeriodId("3");
			}		
			else if(order.getRequestedMeridian().getCode().equalsIgnoreCase("ANYTIME"))
			{
				target.setDeliveryPeriodId("1");
			}
		} 
		else 
		{
			target.setDeliveryPeriodId("2");
		}
		  
		double[] subTotal = {0};
		List<SiteOneWsLineItemsData> siteOneWsLineItemsDatas = new ArrayList<SiteOneWsLineItemsData>();
		List<OrderEntryModel> sortedEntries = source.getConsignmentEntries().stream()
			    .map(consignmentEntry -> (OrderEntryModel) consignmentEntry.getOrderEntry())
			    .sorted(Comparator.comparing(orderEntry -> orderEntry.getEntryNumber() != null ? orderEntry.getEntryNumber() : Integer.MAX_VALUE))
			    .collect(Collectors.toList());
		sortedEntries.forEach(orderEntry->{
      		if(orderType==null) {
      		if (orderEntry.getDiscountAmount() != null && orderEntry.getTotalPrice() != null) {
      			subTotal[0] += orderEntry.getTotalPrice() - orderEntry.getDiscountAmount();
      		} else if (orderEntry.getTotalPrice() != null){
      			subTotal[0] += orderEntry.getTotalPrice();
      		}
      		}
      	      		
			SiteOneWsLineItemsData itemsData = new SiteOneWsLineItemsData();
			String productSkuId = orderEntry.getProduct().getCode();
			itemsData.setSkuId(productSkuId);
			String quantity = Long.toString(orderEntry.getQuantity());
			itemsData.setQuantity(quantity);
			itemsData.setListPrice(Double.toString(orderEntry.getBasePrice()));
			InventoryUPCModel inventoryUOM = null;
			boolean isSimilarUOM = true;
		        Double baseListPrice = 0.0;
			if(null !=orderEntry.getInventoryUOM()){
				itemsData.setInventoryUOMId(Null_value);
				itemsData.setSelectedUOM(orderEntry.getInventoryUOM().getCode());
				itemsData.setUomMultiplier(String.valueOf(orderEntry.getInventoryUOM().getInventoryMultiplier()));
				inventoryUOM =  orderEntry.getProduct().getUpcData().stream().filter(upc->upc.getBaseUPC().booleanValue()).findFirst().orElse(null);
				if(null!=inventoryUOM && null!=inventoryUOM.getCode()){
					itemsData.setBaseUOM(inventoryUOM.getCode());
				}
				baseListPrice = orderEntry.getBasePrice()/orderEntry.getInventoryUOM().getInventoryMultiplier();

			}else{
				//Fallback to product uom id
				//TODO need to be tested
				LOG.error("InventoryUOM id not available for the order entry " + orderEntry.getProduct().getCode()+ "fall back to product uom id");
				inventoryUOM = orderEntry.getProduct().getUpcData().stream().filter(upc->upc.getBaseUPC().booleanValue()).findFirst().orElse(null);
				if(null!=inventoryUOM){
				itemsData.setInventoryUOMId(Null_value);
				itemsData.setSelectedUOM(inventoryUOM.getCode());
				itemsData.setUomMultiplier(String.valueOf(inventoryUOM.getInventoryMultiplier()));
				itemsData.setBaseUOM(inventoryUOM.getCode());
				}
			}	
			
			if(null != orderEntry.getDiscountValues() && !orderEntry.getDiscountValues().isEmpty()){
				
				
				// Calculating discount amount per unit
				Double discountAmountPerUnit = orderEntry.getDiscountValues().get(0).getAppliedValue()
						/ orderEntry.getQuantity();
				itemsData.setDiscountAmount(roundOffPrice(discountAmountPerUnit));

				// Calculating discounted price per unit.
				Double discountedUnitPrice = orderEntry.getBasePrice() - discountAmountPerUnit;
				itemsData.setDiscountedUnitPrice(roundOffPrice(discountedUnitPrice));
				
				// Calculating base discount amount per unit, need to send values for base upc always
				Double baseDiscountAmountPerUnit = null;
				if(null !=orderEntry.getInventoryUOM())
				{
					baseDiscountAmountPerUnit = discountAmountPerUnit
							/orderEntry.getInventoryUOM().getInventoryMultiplier() ;
				}
				else
				{
					LOG.error("InventoryUOM id not available for the order entry " + orderEntry.getProduct().getCode()+ "fall back to product uom id");
					if(null!=inventoryUOM)
					{
						baseDiscountAmountPerUnit = discountAmountPerUnit
								/inventoryUOM.getInventoryMultiplier() ;
					}
					else
					{
						baseDiscountAmountPerUnit = discountAmountPerUnit;
					}
				}
				
					
				Double baseDiscountedUnitPrice = baseListPrice - baseDiscountAmountPerUnit;
				itemsData.setBaseDiscountAmount(baseDiscountAmountPerUnit);
				itemsData.setBaseDiscountedUnitPrice(baseDiscountedUnitPrice);
				itemsData.setBaseListPrice(baseListPrice);

				 
			}	else {
				itemsData.setDiscountedUnitPrice(Double.toString(orderEntry.getBasePrice()));
				itemsData.setDiscountAmount("0");

				//set always for base upc
				itemsData.setBaseDiscountAmount(0.0);
				itemsData.setBaseDiscountedUnitPrice(baseListPrice);
				itemsData.setBaseListPrice(baseListPrice);

			}

			try {
				if(CollectionUtils.isNotEmpty(order.getAppliedCouponCodes())){
					target.setAppliedCouponCodes(new ArrayList<>(order.getAppliedCouponCodes()));
				}
				if(null != order.getPaymentType()){
					target.setPaymentType(order.getPaymentType().getCode());
				}
			} catch (Exception e) {
				LOG.error("Error in applying coupon codes/payment Type ", e);
			}
			int entryNumber = orderEntry.getEntryNumber().intValue();
			if(null != order.getAllPromotionResults() && !order.getAllPromotionResults().isEmpty()){
				
				order.getAllPromotionResults().forEach(promotionResultModel->{				
					promotionResultModel.getConsumedEntries().forEach(promotionOrderEntryConsumedModel->{											
						if(null != promotionOrderEntryConsumedModel && null != promotionOrderEntryConsumedModel.getOrderEntry()){							
							//LOG.info("promotion entry"+promotionOrderEntryConsumedModel.getOrderEntry().getEntryNumber());							
							int promotionEntryNumber = promotionOrderEntryConsumedModel.getOrderEntry().getEntryNumber().intValue();							
							if(promotionEntryNumber == entryNumber){
								String title = promotionResultModel.getPromotion().getTitle();
								
								PromotionSourceRuleModel promotionSourceRule =  promotionFeedCronJobService.getPromotionSourceRuleByTitle(title);
								itemsData.setCouponId(promotionSourceRule.getUuid());
								
								if(title.contains(SiteoneintegrationConstants.PRODUCT_BUY_X_OF_A_GET_Y_OF_B_FREE)) {
									
									Map<String, String> promotionMap = new HashMap<String, String>();
									
									   
										PromotionActionUtil.processAction(Config.getString(SiteoneintegrationConstants.PRODUCT_BUY_X_OF_A_GET_Y_OF_B_FREE_ID,StringUtils.EMPTY), promotionSourceRule, promotionMap, SiteoneintegrationConstants.PRODUCT_BUY_X_OF_A_GET_Y_OF_B_FREE);
										PromotionConditionUtil.processCondition(Config.getString(SiteoneintegrationConstants.PRODUCT_BUY_X_OF_A_GET_Y_OF_B_FREE_ID,
												StringUtils.EMPTY), promotionSourceRule, promotionMap, SiteoneintegrationConstants.PRODUCT_BUY_X_OF_A_GET_Y_OF_B_FREE);
									
									
									
									String itemB = promotionMap.get("ItemB");
									
									
									if(null != itemB && itemB.equalsIgnoreCase(productSkuId)) {
										itemsData.setCouponId(null);
										itemsData.setListPrice("0");
										itemsData.setDiscountedUnitPrice("0");
										itemsData.setDiscountAmount("0");
									}
								}
								
								
							}														
						}
					});
					
					if(null != promotionResultModel.getPromotion()) {
						String promotionTitle = promotionResultModel.getPromotion().getTitle();
						if(promotionTitle.contains(SiteoneintegrationConstants.TARGET_CUSTOMER_PERCENTAGE_DISCOUNT_CART) || promotionTitle.contains(SiteoneintegrationConstants.COUPON_CODE_PERCENTAGE_DISCOUNT_ON_CART) || promotionTitle.contains(SiteoneintegrationConstants.PRODUCT_MULTIBUY_FIXED_PRICE)
								) {
							PromotionSourceRuleModel promotionSourceRule =  promotionFeedCronJobService.getPromotionSourceRuleByTitle(promotionTitle);
							
							
							Map<String, String> promotionMap = new HashMap<String, String>();
							
							if(null != promotionSourceRule) {
								
									if(promotionTitle.contains(SiteoneintegrationConstants.TARGET_CUSTOMER_PERCENTAGE_DISCOUNT_CART) || promotionTitle.contains(SiteoneintegrationConstants.COUPON_CODE_PERCENTAGE_DISCOUNT_ON_CART) ){
									String couponId = "6";
									PromotionActionUtil.processAction(couponId, promotionSourceRule,promotionMap ,promotionTitle);									
									if(null != promotionMap.get("PercentOff")) {										
									String percentOff = promotionMap.get("PercentOff");
									double disAmount =  (((Integer.parseInt(percentOff)) * orderEntry.getBasePrice()) / 100) ;
									double discountedUnitPrice = orderEntry.getBasePrice() - disAmount;
									itemsData.setDiscountAmount(roundOffPrice(disAmount));
									itemsData.setDiscountedUnitPrice(roundOffPrice(discountedUnitPrice));	
									itemsData.setCouponId(promotionSourceRule.getUuid());
									}														
								}
									if(promotionTitle.contains(SiteoneintegrationConstants.PRODUCT_MULTIBUY_FIXED_PRICE)) {
										String couponId = "8";	
										PromotionConditionUtil.processCondition(couponId, promotionSourceRule, promotionMap, SiteoneintegrationConstants.PRODUCT_MULTIBUY_FIXED_PRICE);
										String skuId = promotionMap.get("InclusiveSkuId");
										if(null != skuId && !skuId.isEmpty() && skuId.equalsIgnoreCase(productSkuId)){
											PromotionActionUtil.processAction(couponId, promotionSourceRule, promotionMap,SiteoneintegrationConstants.PRODUCT_MULTIBUY_FIXED_PRICE);
											String priceOverride = promotionMap.get("PriceOverride");
											double disAmount = (orderEntry.getBasePrice() - Double.parseDouble(priceOverride) / Double.parseDouble(quantity));
											double discountedUnitPrice = (Double.parseDouble(priceOverride) / Double.parseDouble(quantity));
											itemsData.setDiscountAmount(roundOffPrice(disAmount));
											itemsData.setDiscountedUnitPrice(roundOffPrice(discountedUnitPrice));	
											itemsData.setCouponId(promotionSourceRule.getUuid());																			
										}
									}
								}
							
							
					
						}
					}
				});
			}
			else {
				if (order.getUser()!=null && CustomerType.GUEST.equals(((CustomerModel) order.getUser()).getType()))
				{
					itemsData.setCouponId(SiteoneintegrationConstants.DEFAULT_UE_GUID);
					itemsData.setDiscountedUnitPrice("0");
					itemsData.setDiscountAmount("0");
				}
			}
			siteOneWsLineItemsDatas.add(itemsData);
			
			if (orderEntry.getBigBagInfo() != null && Boolean.TRUE.equals(orderEntry.getBigBagInfo().getIsChecked()))
			{
				BagInfoModel bigBagInfo = orderEntry.getBigBagInfo();
				SiteOneWsLineItemsData bigBagItemsData = new SiteOneWsLineItemsData();
				
				if (bigBagInfo.getSkuId() != null)
				{
					bigBagItemsData.setSkuId(bigBagInfo.getSkuId());
				}				
				if (bigBagInfo.getNumberOfBags() != null)
				{
					bigBagItemsData.setQuantity(bigBagInfo.getNumberOfBags().toString());
				}
				else
				{
					bigBagItemsData.setQuantity(String.valueOf(1));
				}
				if (bigBagInfo.getListPrice() != null)
				{
					bigBagItemsData.setListPrice(Double.toString(bigBagInfo.getListPrice()));
					bigBagItemsData.setDiscountedUnitPrice(Double.toString(bigBagInfo.getListPrice()));
					bigBagItemsData.setBaseDiscountedUnitPrice(bigBagInfo.getListPrice());
					bigBagItemsData.setBaseListPrice(bigBagInfo.getListPrice());
				}
				else
				{
					bigBagItemsData.setListPrice("0.00");
					bigBagItemsData.setDiscountedUnitPrice("0.00");
					bigBagItemsData.setBaseDiscountedUnitPrice(0.00);
					bigBagItemsData.setBaseListPrice(0.00);
				}
				bigBagItemsData.setInventoryUOMId(Null_value);
				bigBagItemsData.setBaseUOM("BG");
				bigBagItemsData.setSelectedUOM("BG");
				bigBagItemsData.setUomMultiplier("1.0");
				bigBagItemsData.setDiscountAmount("0");
				bigBagItemsData.setBaseDiscountAmount(0.0);
				siteOneWsLineItemsDatas.add(bigBagItemsData);
			}
		});
      	if(orderType == null) {
      	target.setSubTotal(Double.toString(subTotal[0]));	
      	} else {
      		final double orderDiscountAmount = 	getOrderDiscountsAmount(order);
    		target.setSubTotal(Double.toString(order.getSubtotal().doubleValue()-orderDiscountAmount));		
    		
      	}
        if(orderType == null)	{
        double totalOrderPrice = subTotal[0] +  Double.parseDouble(target.getTax());
        boolean firstConsignment = isFirstFulfilmentConsignment(source);
        if  (SiteoneintegrationConstants.DELIVERYMODE_DELIVERY.equalsIgnoreCase(source.getDeliveryMode().getCode()) && order.getDeliveryFreight() != null && firstConsignment) 
      	{
      		totalOrderPrice = totalOrderPrice + Double.parseDouble(order.getDeliveryFreight());
      	} 
      	else if  (SiteoneintegrationConstants.DELIVERYMODE_SHIPPING.equalsIgnoreCase(source.getDeliveryMode().getCode()) && order.getShippingFreight() != null && firstConsignment) 
      	{
      		totalOrderPrice = totalOrderPrice + Double.parseDouble(order.getShippingFreight());
      	}	
      	target.setTotalOrderPrice(Double.toString(totalOrderPrice));
      	String deliveryMode = source.getDeliveryMode() != null ? source.getDeliveryMode().getCode() : "null";        
      	
      	LOG.error("Order Code=" + order.getCode()
      	    + ", Subtotal=" + subTotal[0]
      	    + ", Tax=" + target.getTax()
      	    + ", Order DeliveryFreight=" + order.getDeliveryFreight()
      	    + ", Order ShippingFreight=" + order.getShippingFreight()
      	    + ", DeliveryMode=" + deliveryMode      	    
      	    + ", FinalTotalOrderPrice=" + target.getTotalOrderPrice());

      	if (order.getDeliveryFreight() == null && SiteoneintegrationConstants.DELIVERYMODE_DELIVERY.equalsIgnoreCase(source.getDeliveryMode().getCode())) 
      	{
      	  LOG.error("Missing delivery freight for delivery mode");
      	}
      	if (Double.parseDouble(target.getTotalOrderPrice()) <= 0) 
      	{
      	  LOG.error("TotalOrderPrice is zero or negative: " + target.getTotalOrderPrice());
      	}
      	
      	} 
        
        else{
        	BigDecimal totalOrderPrice = BigDecimal.valueOf(order.getTotalPrice());
    		target.setTotalOrderPrice(Double.toString(totalOrderPrice.doubleValue()));
    		LOG.error("Total order price with tax :" + totalOrderPrice);
    		if(source.getFreight()!=null){
    			totalOrderPrice = totalOrderPrice.add(BigDecimal.valueOf(Double.valueOf(source.getFreight())));
    			target.setTotalOrderPrice(Double.toString(totalOrderPrice.doubleValue()));
    			LOG.error("Total order price with tax and freight :" + totalOrderPrice);
    		}
    		
        }
		target.setLineItems(siteOneWsLineItemsDatas);	
		
		 //Changes for SE-9818
		if (order.getUser()!=null && CustomerType.GUEST.equals(((CustomerModel) order.getUser()).getType()))
		{
			target.setIsGuest(true);			
			if(target.getIsGuest()) {
				List<AddressModel> addressList=new ArrayList<>(((CustomerModel) order.getUser()).getAddresses());
				
				SiteOneWsGuestUserInfoData siteOneWsGuestUserInfoData = populateGuestUserInfo(target, order, addressList);
				if(siteOneWsGuestUserInfoData!=null) {
					target.setGuestUserInfo(siteOneWsGuestUserInfoData);
				}
				
				if(CollectionUtils.isNotEmpty(addressList)) {
					addressList.stream().findFirst().ifPresent(address->{
						target.setContactName(order.getGuestContactPerson().getName());
						String[] user = order.getGuestContactPerson().getUid().split("\\|");
						target.setCreatedByContactId(user[0]);
						if(address.getCountry()!=null) {
							String country=address.getCountry().getIsocode();
							if(country!=null)
							{
							if(country.equalsIgnoreCase("US")){
							    target.setDivisionId("1");
							    target.setExternalSystemId(SiteoneintegrationConstants.EXTERNAL_SYSTEM_ID);
				     		}else if(country.equalsIgnoreCase("CA")){
				     			target.setDivisionId("2");
				     			target.setExternalSystemId(SiteoneintegrationConstants.EXTERNAL_CA_SYSTEM_ID);
				     		}
							}
							if(target.getDivisionId()==null) {
								target.setDivisionId("1");
							}
							
						}						
					});
					
				}
				target.setBillingNodeId(SiteoneintegrationConstants.DEFAULT_UE_GUID);
				target.setOrderingNodeId(SiteoneintegrationConstants.DEFAULT_UE_GUID);
				target.setContactId(SiteoneintegrationConstants.DEFAULT_UE_GUID);
				target.setContactPhoneId(SiteoneintegrationConstants.DEFAULT_UE_GUID);
				target.setDeliveryAddressId(SiteoneintegrationConstants.DEFAULT_UE_GUID);
				
			}
		}
		//Payment Info BOL
		final PointOfServiceModel pos = source.getDeliveryPointOfService();
		String hubStore = null;
		if (pos != null)
		{
			hubStore = pos.getStoreId();
		}
		final List<SiteoneCreditCardPaymentInfoModel> sourceCreditCardInfoList = order.getPaymentInfoList();
		final List<SiteonePOAPaymentInfoModel> sourcePoaInfoList = order.getPoaPaymentInfoList();
		if(CollectionUtils.isNotEmpty(sourceCreditCardInfoList)){
			final boolean flag = hubStore != null && source.getDeliveryMode() != null
					&& source.getDeliveryMode().getCode().equalsIgnoreCase("free-standard-shipping")
					&& order.getConsignments().size() > 1 && null != source.getDeliveryMode() 
					&& SiteoneintegrationConstants.DELIVERYMODE_SHIPPING.equalsIgnoreCase(source.getDeliveryMode().getCode());
			List<SiteOneWsPaymentInfoData> paymentInfoList = new ArrayList<SiteOneWsPaymentInfoData>();
			sourceCreditCardInfoList.forEach(sourceCreditCardInfo ->{
				SiteoneCreditCardPaymentInfoModel sourcePaymentInfo = (SiteoneCreditCardPaymentInfoModel)sourceCreditCardInfo;
				if(Integer.parseInt(sourcePaymentInfo.getPaymentType())== 3 && paymentInfoList.size()<1) {
					if(flag) 
					{
						if(sourcePaymentInfo.getPaymentTransaction()!=null 
								&& sourcePaymentInfo.getPaymentTransaction().equalsIgnoreCase("Capture")) {
							SiteOneWsPaymentInfoData paymentInfoData = populatePaymentInfo(sourcePaymentInfo,target);
							paymentInfoList.add(paymentInfoData);
						}
					}
					else {
						SiteOneWsPaymentInfoData paymentInfoData = populatePaymentInfo(sourcePaymentInfo,target);
						paymentInfoList.add(paymentInfoData);
					}
				}
			});
			target.setPaymentInfo(paymentInfoList);
		}
		if(CollectionUtils.isNotEmpty(sourcePoaInfoList)){
			List<SiteOneWsPaymentInfoData> paymentInfoList = new ArrayList<SiteOneWsPaymentInfoData>();
			sourcePoaInfoList.forEach(sourcePoaInfo ->{
				SiteonePOAPaymentInfoModel sourcePaymentInfo = (SiteonePOAPaymentInfoModel)sourcePoaInfo;
				SiteOneWsPaymentInfoData paymentInfoData = new SiteOneWsPaymentInfoData();
				if(Integer.parseInt(sourcePaymentInfo.getPaymentType())== 1){
					paymentInfoData.setPaymentTypeID(1);
					//paymentInfoData.setAmountCharged(String.valueOf(sourcePaymentInfo.getAmountCharged()));
					paymentInfoData.setAmountCharged(String.valueOf(target.getTotalOrderPrice()));
					paymentInfoList.add(paymentInfoData);
				}
			
			});
			target.setPaymentInfo(paymentInfoList);
		}
		
		target.setExpedite(order.getIsExpedited());		
		target.setConsignmentId(source.getCode());
		target.setNumberOfConsignment(order.getConsignments().size());
		populateShipment(order, target, isFirstFulfilmentConsignment(source));	
	}
	
	private SiteOneWsPaymentInfoData populatePaymentInfo(SiteoneCreditCardPaymentInfoModel sourcePaymentInfo,
			SiteOneWsSubmitOrderRequestData target) 
	{
		SiteOneWsPaymentInfoData paymentInfoData = new SiteOneWsPaymentInfoData();
		paymentInfoData.setPaymentTypeID(3);
		paymentInfoData.setAuthCode(sourcePaymentInfo.getAuthCode());
		paymentInfoData.setLast4Digits(sourcePaymentInfo.getLast4Digits());
		if (null!=target.getDivisionId() && target.getDivisionId().equalsIgnoreCase("1"))
		{
		paymentInfoData.setExpDate(sourcePaymentInfo.getExpDate().substring(0, 2)+"/"+sourcePaymentInfo.getExpDate().substring(2, 4));
		}
		else
		{
			paymentInfoData.setExpDate(sourcePaymentInfo.getExpDate());
		}
		paymentInfoData.setCreditCardType(SiteoneintegrationConstants.cardTypeShortName.get(sourcePaymentInfo.getCreditCardType()));
		paymentInfoData.setToken(sourcePaymentInfo.getAuthToken());
		paymentInfoData.setVaultToken(sourcePaymentInfo.getVaultToken());
		if (null != sourcePaymentInfo.getCreditCardZip()) {
			paymentInfoData.setCreditCardZip(sourcePaymentInfo.getCreditCardZip());
		} else {
			paymentInfoData.setCreditCardZip("");
		}
		paymentInfoData.setTransactionStatus(sourcePaymentInfo.getTransactionStatus());
		paymentInfoData.setTransportKey(sourcePaymentInfo.getTransportKey());
		paymentInfoData.setAid(0.0);
		paymentInfoData.setApplicationLabel(sourcePaymentInfo.getApplicationLabel());
		paymentInfoData.setPinStatement(0.0);
		paymentInfoData.setTransactionReferenceNumber(sourcePaymentInfo.getTransactionReferenceNumber());
		paymentInfoData.setIseWalletCard(sourcePaymentInfo.getIseWalletCard());
		if(null == paymentInfoData.getIseWalletCard())
		{
			paymentInfoData.setIseWalletCard(Boolean.FALSE);
		}
		paymentInfoData.setAmountCharged(String.valueOf(target.getTotalOrderPrice()));
		return paymentInfoData;
	}

	/**
	 * Populate the Shipment information for order submit
	 * @param source the order model
	 * @param target the Ws order request data
	 */
	private void populateShipment(OrderModel source, SiteOneWsSubmitOrderRequestData target, boolean firstConsignment) {		
		
		SiteOneWsShipmentData siteOneWsShipmentData=new SiteOneWsShipmentData();

		if(SiteoneintegrationConstants.ORDERTYPE_DELIVERY.equalsIgnoreCase(target.getDeliveryMode())) {
				SiteOneWsDeliveryInfoData siteOneWsDeliveryInfoData=new SiteOneWsDeliveryInfoData();				
				siteOneWsDeliveryInfoData.setLift(source.getChooseLift());
				String freight = getFreight(source,firstConsignment,true);
				siteOneWsDeliveryInfoData.setDeliveryCost(freight!=null?Double.valueOf(freight):0.00D);
				siteOneWsShipmentData.setDeliveryInfo(siteOneWsDeliveryInfoData);
		} else if(SiteoneintegrationConstants.ORDERTYPE_PICKUP.equalsIgnoreCase(target.getDeliveryMode()))
		{
			SiteOneWsPickupInfoData siteOneWsPickupInfoData=new SiteOneWsPickupInfoData();
			siteOneWsPickupInfoData.setPickupInstructions(target.getDeliveryInstructions());
			siteOneWsPickupInfoData.setPickupDate(target.getDeliveryDate());
			siteOneWsPickupInfoData.setPickupPeriodId(target.getDeliveryPeriodId());
			siteOneWsShipmentData.setPickupInfo(siteOneWsPickupInfoData);
		}else if(SiteoneintegrationConstants.ORDERTYPE_SHIPPING.equalsIgnoreCase(target.getDeliveryMode())) {
			
			SiteOneWsShippingInfoData siteOneWsShippingInfoData=new SiteOneWsShippingInfoData();
			siteOneWsShippingInfoData.setShippingAddressId(target.getDeliveryAddressId());
			String freight = getFreight(source,firstConsignment,false);
			siteOneWsShippingInfoData.setShippingFee(freight!=null?freight:"0.00");
			siteOneWsShipmentData.setShippingInfo(siteOneWsShippingInfoData);			
		}

		if(siteOneWsShipmentData!=null) {
			target.setShipment(siteOneWsShipmentData);
		}
				
	}
	
	private String getFreight(OrderModel source, boolean firstConsignment, boolean isDelivery) {
		OrderTypeEnum orderType = source.getOrderType();
		String freight = null;
		if (orderType != null) {
			freight = source.getFreight();
		} else if(firstConsignment && isDelivery) {
			freight = source.getDeliveryFreight();
		} else if(firstConsignment && !isDelivery) {
			freight = source.getShippingFreight();
		}
		return freight;
	}

	/**
	 * Populates the Guest user information
	 * @param source the orderModel
	 * @return the SiteOneWsGuestuserInfoData
	 */
	private SiteOneWsGuestUserInfoData populateGuestUserInfo(SiteOneWsSubmitOrderRequestData request, OrderModel source, List<AddressModel> addressList) {
		SiteOneWsGuestUserInfoData siteOneWsGuestuserInfoData=new SiteOneWsGuestUserInfoData();
		
		// map shipping address		
		AddressModel deliveryAddress= null;
		if (request.getDeliveryMode() != null)	{
		if (SiteoneintegrationConstants.ORDERTYPE_PICKUP.equalsIgnoreCase(request.getDeliveryMode()))	{
			deliveryAddress = (null != source.getPickupAddress() ? source.getPickupAddress() : source.getDeliveryAddress());
		} else if (SiteoneintegrationConstants.ORDERTYPE_DELIVERY.equalsIgnoreCase(request.getDeliveryMode()))	{
			deliveryAddress = source.getDeliveryAddress();
		} else if (SiteoneintegrationConstants.ORDERTYPE_SHIPPING.equalsIgnoreCase(request.getDeliveryMode()))	{
			deliveryAddress = (null != source.getShippingAddress() ? source.getShippingAddress() : source.getDeliveryAddress());
		}
		}
		
		if(deliveryAddress!=null) {
			siteOneWsGuestuserInfoData.setShippingAddress1(deliveryAddress.getLine1()!=null?deliveryAddress.getLine1():"");
			siteOneWsGuestuserInfoData.setShippingAddress2(deliveryAddress.getLine2()!=null? deliveryAddress.getLine2():"");
			siteOneWsGuestuserInfoData.setShippingCity(deliveryAddress.getTown()!=null?deliveryAddress.getTown():"");
			siteOneWsGuestuserInfoData.setShippingState(deliveryAddress.getRegion()!=null ? deliveryAddress.getRegion().getIsocodeShort():"");
			siteOneWsGuestuserInfoData.setShippingZipCode(deliveryAddress.getPostalcode()!=null? deliveryAddress.getPostalcode():"");
			siteOneWsGuestuserInfoData.setContactPhone(getPhoneNumber(deliveryAddress.getPhone1()));
			siteOneWsGuestuserInfoData.setContactEmail(deliveryAddress.getEmail()!=null?deliveryAddress.getEmail():"");
		}
		
		siteOneWsGuestuserInfoData.setIsShippingSameAsBilling(source.getIsSameaddressforParcelShip()!=null?source.getIsSameaddressforParcelShip():false);
		
		if(deliveryAddress!=null && siteOneWsGuestuserInfoData.getIsShippingSameAsBilling() && !(SiteoneintegrationConstants.ORDERTYPE_PICKUP.equalsIgnoreCase(request.getDeliveryMode()))) {
			siteOneWsGuestuserInfoData.setBillingAddress1(deliveryAddress.getLine1()!=null?deliveryAddress.getLine1():"");
			siteOneWsGuestuserInfoData.setBillingAddress2(deliveryAddress.getLine2()!=null?deliveryAddress.getLine2():"");
			siteOneWsGuestuserInfoData.setBillingCity(deliveryAddress.getTown()!=null?deliveryAddress.getTown():"");
			siteOneWsGuestuserInfoData.setBillingState(deliveryAddress.getRegion()!=null ? deliveryAddress.getRegion().getIsocodeShort(): null);
			siteOneWsGuestuserInfoData.setBillingZipCode(deliveryAddress.getPostalcode()!=null?deliveryAddress.getPostalcode():"");
			siteOneWsGuestuserInfoData.setContactPhone(getPhoneNumber(deliveryAddress.getPhone1()));
			siteOneWsGuestuserInfoData.setContactEmail(deliveryAddress.getEmail()!=null?deliveryAddress.getEmail():"");
			}else {
				// map contact address
				if(CollectionUtils.isNotEmpty(addressList)) {
				addressList.stream().findFirst().ifPresent(contactAddress->{					
						siteOneWsGuestuserInfoData.setBillingAddress1(contactAddress.getLine1()!=null?contactAddress.getLine1():"");
						siteOneWsGuestuserInfoData.setBillingAddress2(contactAddress.getLine2()!=null? contactAddress.getLine2():"");
						siteOneWsGuestuserInfoData.setBillingCity(contactAddress.getTown()!=null?contactAddress.getTown():"");
						siteOneWsGuestuserInfoData.setBillingState(contactAddress.getRegion()!=null ? contactAddress.getRegion().getIsocodeShort():"");
						siteOneWsGuestuserInfoData.setBillingZipCode(contactAddress.getPostalcode()!=null? contactAddress.getPostalcode():"");
						siteOneWsGuestuserInfoData.setContactPhone(getPhoneNumber(contactAddress.getPhone1()));
						siteOneWsGuestuserInfoData.setContactEmail(contactAddress.getEmail()!=null?contactAddress.getEmail():"");
						siteOneWsGuestuserInfoData.setCompanyName(contactAddress.getCompany()!=null?contactAddress.getCompany():"");					
				});
				}
			}
		
		
		return siteOneWsGuestuserInfoData;
	}

	protected double getOrderDiscountsAmount(final AbstractOrderModel source)
	{
		double discounts = 0.0d;
		final List<DiscountValue> discountList = source.getGlobalDiscountValues(); // discounts on the cart itself
		if (discountList != null && !discountList.isEmpty())
		{
			for (final DiscountValue discount : discountList)
			{
				final double value = discount.getAppliedValue();
				if (DoubleMath.fuzzyCompare(value, 0, EPSILON) > 0
						&& !SiteoneintegrationConstants.QUOTE_DISCOUNT_CODE.equals(discount.getCode()))
				{
					discounts += value;
				}
			}
		}
		return discounts;
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
	
	
	/**
	 * This method is used to round off the price into 3.
	 *
	 * @param priceToRoundOff
	 *           - price needs to be round off.
	 *
	 * @return rounding off price
	 */
	private String roundOffPrice(final Double priceToRoundOff)
	{
		if (null != priceToRoundOff)
		{
			final DecimalFormat decimalFormat = new DecimalFormat("#.###");
			decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

			final String roundedOffPrice = decimalFormat.format(priceToRoundOff);
			return roundedOffPrice;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * @param phoneNumber
	 * @return
	 */
	private String getPhoneNumber(String phoneNumber) {
		if(null !=phoneNumber){
			String formattedPhoneNumber = phoneNumber.replaceAll("\\-", "");
			return formattedPhoneNumber;
		}else{
			return "";
		}
		
	}
	
	protected String getTomorrowDate() 
	{
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		calendar.add(Calendar.DATE, 1);
		date = calendar.getTime();
		return dateFormat.format(date);
	}
	
	protected boolean isFirstFulfilmentConsignment(ConsignmentModel source)
	{
		boolean firstConsignment = false;
		OrderModel order = (OrderModel) source.getOrder();
		if(order.getOrderType() == null) {
		String deliveryMode = source.getDeliveryMode().getCode();
		int minConsignmentCode  =-1;
		String consignmentCode = source.getCode();
		String firstConsignmentCode = null;
		for (final ConsignmentModel consignment : order.getConsignments())
		{
			if((deliveryMode!=null && deliveryMode.equals(consignment.getDeliveryMode().getCode())) && (minConsignmentCode==-1 ||  (consignment.getCode()!=null && consignment.getCode().split("-").length == 2 && minConsignmentCode > Integer.parseInt(consignment.getCode().split("-")[1])))) 
			{
					 minConsignmentCode = Integer.parseInt(consignment.getCode().split("-")[1]);
					 firstConsignmentCode = consignment.getCode();
			}
			
		}
		if(firstConsignmentCode!=null && firstConsignmentCode.equals(consignmentCode)) {
			firstConsignment = true;
		}
		}
		
		return firstConsignment;
	}
	
}
