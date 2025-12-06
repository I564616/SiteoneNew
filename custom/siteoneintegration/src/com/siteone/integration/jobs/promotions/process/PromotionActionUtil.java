package com.siteone.integration.jobs.promotions.process;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.jobs.promotions.data.action.PromotionContainerAction;
import com.siteone.integration.jobs.promotions.data.action.PromotionContainerPriceAction;
import com.siteone.integration.jobs.promotions.data.action.PromotionFixedPriceAction;

import de.hybris.platform.promotionengineservices.model.PromotionSourceRuleModel;
import de.hybris.platform.util.Config;

public class PromotionActionUtil {
	
	private static final Logger LOG = Logger.getLogger(PromotionActionUtil.class);
	
	
	  public static void processAction(String couponType,PromotionSourceRuleModel promotionSourceRule, Map<String, String> promotionRow, String couponName){
		  
		     //Action call for Buy X get Y promotion
			if(couponType.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_BUY_X_GET_Y_FREE_ID, StringUtils.EMPTY)) || couponType.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_BUY_X_OF_A_GET_Y_OF_B_FREE_ID, StringUtils.EMPTY))){
				PromotionActionUtil.processPromotionContainerAction(promotionSourceRule, promotionRow);
			}
			
			//Action call for Buy X of A Get Y of B with Price Override
			if(couponType.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_PERFECT_PARTNER_FIXED_PRICE_ID, StringUtils.EMPTY))){
				PromotionActionUtil.processPromotionPriceOverrideAction(promotionSourceRule, promotionRow);
			}
			
			//Action call for fixed price or discount
			if ((couponType.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_FIXED_PRICE_ID, StringUtils.EMPTY))
					|| couponType.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_FIXED_DISCOUNT_ID, StringUtils.EMPTY))) && !couponName.contains(SiteoneintegrationConstants.PRODUCT_MULTIBUY_FIXED_PRICE)) {
				PromotionActionUtil.processPriceActionDefinition(promotionSourceRule, promotionRow);
			} 
			
			//Action call for percent discount
			if (couponType.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_PERCENTAGE_DISCOUNT_ID, StringUtils.EMPTY))
					 || couponType.equalsIgnoreCase(Config.getString(
									SiteoneintegrationConstants.COUPON_CODE_PERCENTAGE_DISCOUNT_ON_CART_ID, StringUtils.EMPTY)) || couponType.equalsIgnoreCase(Config.getString(
											SiteoneintegrationConstants.COUPON_CODE_PERCENTAGE_DISCOUNT_ON_PRODUCTS_ID, StringUtils.EMPTY)) || couponType.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.TARGET_CUSTOMER_PERCENTAGE_DISCOUNT_CART_ID, StringUtils.EMPTY))) {
				PromotionActionUtil.processActionDefinition(promotionSourceRule, promotionRow);
			}
			
			//Action call for multi-buy fixed price
			if (couponType.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_MULTIBUY_FIXED_PRICE_ID, StringUtils.EMPTY)) && !couponName.contains(SiteoneintegrationConstants.PRODUCT_FIXED_PRICE)){
				PromotionActionUtil.processContainerActionDefinition(promotionSourceRule, promotionRow);
			} 			  
	        }
	
	
     	// Process promotion Action
	    private static void processContainerActionDefinition(PromotionSourceRuleModel promotionSourceRule,
			Map<String, String> promotionRow) {
	    	Gson gson = new Gson();
			Type actionListType = new TypeToken<ArrayList<PromotionContainerPriceAction>>(){}.getType();
			List<PromotionContainerPriceAction> promotionContainerAction = gson.fromJson(promotionSourceRule.getActions(), actionListType);
			promotionContainerAction.forEach(action -> {
				LOG.info("definitionId" + action.getDefinitionId());
				if(SiteoneintegrationConstants.PROMOTION_ACT_DEF_Y_TARGET_BUNDLE_PRICE.equalsIgnoreCase(action.getDefinitionId())){					
					Map<String,Integer> qualifyingContainers = action.getParameters().getQualifyingContainers().getValue();
					qualifyingContainers.forEach((container,quantity)->{
						promotionRow.put("MinimumPurchaseQuantity", Integer.toString(quantity));
						promotionRow.put("qualifying_container", container);
					});
					promotionRow.put("PriceOverride",action.getParameters().getValue().getValue().getUSD().toString());
				}
			});		
	    }
 		
		private static void processActionDefinition(PromotionSourceRuleModel promotionSourceRule, Map<String, String> promotionRow) {
			Gson gson = new Gson();
			
			Type actionListType = new TypeToken<ArrayList<PromotionContainerAction>>() {
			}.getType();
			
			List<PromotionContainerAction> promotionContainerAction = gson
					.fromJson(promotionSourceRule.getActions(), actionListType);
			promotionContainerAction.forEach(action -> {
				LOG.info("definitionId" + action.getDefinitionId());
				if (action.getDefinitionId()
						.equalsIgnoreCase(SiteoneintegrationConstants.PROMOTION_ACT_DEF_Y_ORDER_ENTRY_PERCENTAGE_DISCOUNT)
						|| action.getDefinitionId()
								.equalsIgnoreCase(SiteoneintegrationConstants.PROMOTION_ACT_DEF_Y_ORDER_PERCENTAGE_DISCOUNT)) {
					com.siteone.integration.jobs.promotions.data.action.PromotionContainerAction.Parameters param = action
							.getParameters();
					if (null != param.getValue()) {
						promotionRow.put("PercentOff", param.getValue().getValue().toString());
					}
				}
			});
			
			
		}

		private static void processPriceActionDefinition(PromotionSourceRuleModel promotionSourceRule, Map<String, String> promotionRow) {
			Gson gson = new Gson();
			Type actionListType = new TypeToken<ArrayList<PromotionFixedPriceAction>>() {
			}.getType();
			List<PromotionFixedPriceAction> promotionFeedPriceActions = gson
					.fromJson(promotionSourceRule.getActions(), actionListType);
			promotionFeedPriceActions.forEach(action -> {
				LOG.info("definitionId" + action.getDefinitionId());
				if (action.getDefinitionId()
						.equalsIgnoreCase(SiteoneintegrationConstants.PROMOTION_ACT_DEF_Y_ORDER_ENTRY_FIXED_PRICE)) {
					com.siteone.integration.jobs.promotions.data.action.PromotionFixedPriceAction.Parameters param = action
							.getParameters();
					if (null != param.getValue()) {

						promotionRow.put("PriceOverride", param.getValue().getValue().getUSD().toString());
					}
				} else if (action.getDefinitionId()
						.equalsIgnoreCase(SiteoneintegrationConstants.PROMOTION_ACT_DEF_Y_ORDER_ENTRY_FIXED_DISCOUNT)) {
					com.siteone.integration.jobs.promotions.data.action.PromotionFixedPriceAction.Parameters param = action
							.getParameters();
					if (null != param.getValue()) {

						promotionRow.put("AmountOff", param.getValue().getValue().getUSD().toString());
					}
				}
			});
			
			
		}
		
		private static void processPromotionContainerAction(PromotionSourceRuleModel promotionSourceRule, Map<String, String> promotionRow) {
			Gson gson = new Gson();
			Type actionListType = new TypeToken<ArrayList<PromotionContainerAction>>(){}.getType();
			List<PromotionContainerAction> promotionContainerAction = gson.fromJson(promotionSourceRule.getActions(), actionListType);
			promotionContainerAction.forEach(action -> {
				LOG.info("definitionId" + action.getDefinitionId());
				if(SiteoneintegrationConstants.PROMOTION_ACT_DEF_Y_PARTNER_ORDER_ENTRY_PERCENTAGE_DISCOUNT.equalsIgnoreCase(action.getDefinitionId())){
					
					Map<String,Integer> qualifyingContainers = action.getParameters().getQualifyingContainers().getValue();
					qualifyingContainers.forEach((container,quantity)->{
						promotionRow.put("MinimumPurchaseQuantity", Integer.toString(quantity));
						promotionRow.put("qualifying_container", container);
					});
					
					Map<String,Integer> targetContainers = action.getParameters().getTargetContainers().getValue();
					targetContainers.forEach((container,quantity)->{
						promotionRow.put("GetQuantity", Integer.toString(quantity));
						promotionRow.put("target_container", container);
					});
				}
			});
		}
			
		
		private static void processPromotionPriceOverrideAction(PromotionSourceRuleModel promotionSourceRule, Map<String, String> promotionRow) {
			Gson gson = new Gson();
			Type actionListType = new TypeToken<ArrayList<PromotionFixedPriceAction>>(){}.getType();
			List<PromotionFixedPriceAction> promotionFixedPriceAction = gson.fromJson(promotionSourceRule.getActions(), actionListType);
			promotionFixedPriceAction.forEach(action -> {
				LOG.info("definitionId" + action.getDefinitionId());
               if(SiteoneintegrationConstants.PROMOTION_ACT_DEF_Y_PARTNER_ORDER_ENTRY_FIXED_PRICE.equalsIgnoreCase(action.getDefinitionId())){
					
					Map<String,Integer> qualifyingContainers = action.getParameters().getQualifyingContainers().getValue();
					qualifyingContainers.forEach((container,quantity)->{
						promotionRow.put("MinimumPurchaseQuantity", Integer.toString(quantity));
						promotionRow.put("qualifying_container", container);
					});
					
					Map<String,Integer> targetContainers = action.getParameters().getTargetContainers().getValue();
					targetContainers.forEach((container,quantity)->{
						promotionRow.put("GetQuantity", Integer.toString(quantity));
						promotionRow.put("target_container", container);
					});
					String price=action.getParameters().getValue().getValue().getUSD().toString();
					promotionRow.put("PriceOverride", price);
				}
			});
			
				
		}

}
