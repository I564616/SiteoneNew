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
import com.siteone.integration.jobs.promotions.data.condition.PromotionFeedCondition;
import com.siteone.integration.jobs.promotions.data.condition.PromotionFeedCondition.Parameters;

import de.hybris.platform.promotionengineservices.model.PromotionSourceRuleModel;
import de.hybris.platform.util.Config;


public class PromotionConditionUtil {
	
	   private static final Logger LOG = Logger.getLogger(PromotionConditionUtil.class);
	   
	   
	   public static void processCondition(String couponType,PromotionSourceRuleModel promotionSourceRule, Map<String, String> promotionRow, String couponName){
		    Gson gson = new Gson();
		   Type conditionListType = new TypeToken<ArrayList<PromotionFeedCondition>>() {
			}.getType();
			List<PromotionFeedCondition> promotionFeedConditions = gson
					.fromJson(promotionSourceRule.getConditions(), conditionListType);
			
			promotionFeedConditions.forEach(condition -> {
				LOG.info("definitionId" + condition.getDefinitionId());
			
				processConditionDefinition(condition, promotionRow,couponType,couponName);
				
				if(!couponType.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_BUY_X_GET_Y_FREE_ID,StringUtils.EMPTY)) && !couponName.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_MULTIBUY_FIXED_PRICE_ID,StringUtils.EMPTY)) &&
						!couponType.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_BUY_X_OF_A_GET_Y_OF_B_FREE_ID,StringUtils.EMPTY))){
		        	
					setDefaultMaxQuantity(promotionRow);
		        }
				if(couponType.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_FIXED_PRICE_ID,StringUtils.EMPTY)) || couponType.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_MULTIBUY_FIXED_PRICE_ID,StringUtils.EMPTY))){
					promotionRow.put("MinimumOrderAmount","1");
				}
				
				
			});
	   }

	
	    private static void setDefaultMaxQuantity(Map<String, String> promotionRow) {
	    	
	    	String maxQuantity = promotionRow.get("MaximumPurchaseQuantity");
	    	
	    	if(StringUtils.isEmpty(maxQuantity)) {
	    		promotionRow.put("MaximumPurchaseQuantity","9999");
	    	}
		
		
	     }


		// Process Promotion Condition
	    private static void processConditionDefinition(PromotionFeedCondition condition, Map<String, String> promotionRow,String couponType, String couponName) {
			
	    	//Container
			if (condition.getDefinitionId().equalsIgnoreCase(SiteoneintegrationConstants.PROMOTION_COND_DEF_Y_CONTAINER)) {
				String container = condition.getParameters().getId().getValue();
				promotionRow.put("currentContainer", container);
				condition.getChildren().forEach(groupcondition -> {
					processConditionDefinition(groupcondition, promotionRow,couponType,couponName);
				});
			}

			// Groups
			if (condition.getDefinitionId().equalsIgnoreCase(SiteoneintegrationConstants.PROMOTION_COND_DEF_Y_GROUP)) {
				LOG.info("Y Group operator" + condition.getParameters().getOperator().getValue());
				condition.getChildren().forEach(groupcondition -> {
					processConditionDefinition(groupcondition, promotionRow,couponType,couponName);
				});
			}

			// Coupon
			if (condition.getDefinitionId()
					.equalsIgnoreCase(SiteoneintegrationConstants.PROMOTION_COND_DEF_Y_QUALIFYING_COUPONS)) {
				processCoupon(condition, promotionRow);
			}

			// Target customers
			if (condition.getDefinitionId()
					.equalsIgnoreCase(SiteoneintegrationConstants.PROMOTION_COND_DEF_Y_TARGET_CUSTOMERS)) {
				processTargetCustomer(condition, promotionRow);
			}

			// qualifying products
			if (condition.getDefinitionId()
					.equalsIgnoreCase(SiteoneintegrationConstants.PROMOTION_COND_DEF_Y_QUALIFYING_PRODUCTS)){
				processQualifyingProducts(condition, promotionRow,couponType,couponName);
			}

			// qualifying categories
			if (condition.getDefinitionId()
					.equalsIgnoreCase(SiteoneintegrationConstants.PROMOTION_COND_DEF_Y_QUALIFYING_CAT)) {
				processQualifyingCategories(condition, promotionRow,couponType,couponName);
			}
		}
		
		private static void processQualifyingProducts(PromotionFeedCondition condition, Map<String, String> promotionRow,String couponType, String couponName) {
			Parameters param = condition.getParameters();
			
			
			if (null != param.getProducts() && null != param.getProducts().getValue()
					&& param.getProductsOperator().getValue()
							.equalsIgnoreCase(SiteoneintegrationConstants.PROMOTION_COND_PARM_OPERATOR_CONTAINS_ANY)
					|| param.getProductsOperator().getValue()
							.equalsIgnoreCase(SiteoneintegrationConstants.PROMOTION_COND_PARM_OPERATOR_CONTAINS_All) && !param.getProducts().getValue().isEmpty()) {
				List<String> products = param.getProducts().getValue();
				
				if(couponType.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_BUY_X_GET_Y_FREE_ID,StringUtils.EMPTY)) || couponType.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_PERFECT_PARTNER_FIXED_PRICE_ID,StringUtils.EMPTY)) || couponType.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_BUY_X_OF_A_GET_Y_OF_B_FREE_ID,StringUtils.EMPTY))){
					if(promotionRow.get("currentContainer").equalsIgnoreCase(promotionRow.get("qualifying_container"))){
						processInclusiveProducts(products, promotionRow);
					}else if(promotionRow.get("currentContainer").equalsIgnoreCase(promotionRow.get("target_container")) && !couponType.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_BUY_X_GET_Y_FREE_ID,StringUtils.EMPTY)) ){
						processItemBProducts(products, promotionRow);
					}
				}else{
					processInclusiveProducts(products, promotionRow);
				}
				
			} else if (null != param.getProducts() && null != param.getProducts().getValue()
					&& param.getProductsOperator().getValue()
							.equalsIgnoreCase(SiteoneintegrationConstants.PROMOTION_COND_PARM_OPERATOR_DOES_NOT_CONTAIN) && !param.getProducts().getValue().isEmpty()) {
				List<String> products = param.getProducts().getValue();
				processExcludedProducts(products, promotionRow);
			}      
			if(!couponType.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_BUY_X_GET_Y_FREE_ID,StringUtils.EMPTY)) && !couponType.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_PERFECT_PARTNER_FIXED_PRICE_ID,StringUtils.EMPTY)) && !couponName.contains(SiteoneintegrationConstants.PRODUCT_MULTIBUY_FIXED_PRICE) &&
					!couponType.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_BUY_X_OF_A_GET_Y_OF_B_FREE_ID,StringUtils.EMPTY))){
	        	processQuantity(param, promotionRow);
	        }
			
		}

		private static void processItemBProducts(List<String> products, Map<String, String> promotionRow) {
			StringBuffer product = new StringBuffer(StringUtils.EMPTY);
			if(null !=products){
			for (String pro : products) {
				if (product.length() != 0) {
					product.append(",");
				}
				product.append(pro);
			}
			
			if(StringUtils.isNotBlank(product.toString())){
				if (promotionRow.containsKey("ItemB")) {
					String existingItemB = promotionRow.get("ItemB");
					if (!existingItemB.isEmpty()) {
						promotionRow.put("ItemB", existingItemB + "," + product.toString());
					}
				} else {
					promotionRow.put("ItemB", product.toString());
				}
			}
			}
		}

		private static void processQualifyingCategories(PromotionFeedCondition condition, Map<String, String> promotionRow,String couponType, String couponName) {
			Parameters param = condition.getParameters();
			boolean isValidCategoryCondition = false;
			if (null != param.getCategories() && null != param.getCategories().getValue()
					&& param.getCategoriesOperator().getValue()
							.equalsIgnoreCase(SiteoneintegrationConstants.PROMOTION_COND_PARM_OPERATOR_CONTAINS_ANY)
					|| param.getCategoriesOperator().getValue()
							.equalsIgnoreCase(SiteoneintegrationConstants.PROMOTION_COND_PARM_OPERATOR_CONTAINS_All) && !param.getCategories().getValue().isEmpty()) {
				List<String> categories = param.getCategories().getValue();
				if(!categories.isEmpty()) {
					
					if(couponType.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_BUY_X_GET_Y_FREE_ID,StringUtils.EMPTY)) || couponType.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_PERFECT_PARTNER_FIXED_PRICE_ID,StringUtils.EMPTY)) || couponType.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_BUY_X_OF_A_GET_Y_OF_B_FREE_ID,StringUtils.EMPTY))){
						if(promotionRow.get("currentContainer").equalsIgnoreCase(promotionRow.get("qualifying_container"))){
							processInclusiveCategories(categories, promotionRow);
						}else if(promotionRow.get("currentContainer").equalsIgnoreCase(promotionRow.get("target_container")) && !couponType.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_BUY_X_GET_Y_FREE_ID,StringUtils.EMPTY)) ){
							processItemBCategories(categories, promotionRow);
						}
					}else{
						processInclusiveCategories(categories, promotionRow);
					}
					
					isValidCategoryCondition =true;
				}
				
			} else if (null != param.getCategories() && null != param.getCategories().getValue()
					&& param.getCategoriesOperator().getValue()
							.equalsIgnoreCase(SiteoneintegrationConstants.PROMOTION_COND_PARM_OPERATOR_DOES_NOT_CONTAIN) && !param.getCategories().getValue().isEmpty()) {
				List<String> categories = condition.getParameters().getCategories().getValue();
				processExcludedCategories(categories, promotionRow);
				isValidCategoryCondition = true;
			}
			if (null != param.getExcludedCategories() && null != param.getExcludedCategories().getValue() && !param.getExcludedCategories().getValue().isEmpty()) {
				List<String> excategories = param.getExcludedCategories().getValue();
				processExcludedCategories(excategories, promotionRow);
				isValidCategoryCondition = true;
			}

			if (null != param.getExcludedProducts() && null != param.getExcludedProducts().getValue() && !param.getExcludedProducts().getValue().isEmpty()) {
				List<String> excludedProducts = param.getExcludedProducts().getValue();
				processExcludedProducts(excludedProducts, promotionRow);
				isValidCategoryCondition = true;
			}

			if(!couponType.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_BUY_X_GET_Y_FREE_ID,StringUtils.EMPTY)) && !couponType.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_PERFECT_PARTNER_FIXED_PRICE_ID,StringUtils.EMPTY)) && !couponName.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_MULTIBUY_FIXED_PRICE,StringUtils.EMPTY)) &&
					!couponType.equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.PRODUCT_BUY_X_OF_A_GET_Y_OF_B_FREE_ID,StringUtils.EMPTY)) && isValidCategoryCondition){
	        	processQuantity(param, promotionRow);
	        }
		}

		private static void processItemBCategories(List<String> categories, Map<String, String> promotionRow) {
			StringBuffer cat = new StringBuffer(StringUtils.EMPTY);
			if(null !=categories){
			for (String category : categories) {
				if (cat.length() != 0) {
					cat.append(",");
				}
				cat.append(category);			
			}
			
			if(StringUtils.isNotBlank(cat.toString())){
				if (promotionRow.containsKey("ItemB")) {
					String existingItemB = promotionRow.get("ItemB");
					if (!existingItemB.isEmpty()) {
						promotionRow.put("ItemB", existingItemB + "," + cat.toString());
					}
				} else {
					promotionRow.put("ItemB", cat.toString());
				}	
			}
				
		}
		}

		private static void processExcludedCustomer(List<String> customerGroup, Map<String, String> promotionRow) {
			StringBuffer excludedCustomer = new StringBuffer(StringUtils.EMPTY);
			for (String customer : customerGroup) {
				if (excludedCustomer.length() != 0) {
					excludedCustomer.append(",");
				}
				String name = StringUtils.substringBeforeLast(customer.trim(),
						SiteoneintegrationConstants.SEPARATOR_UNDERSCORE);
				excludedCustomer.append(name);
			}
			if (promotionRow.containsKey("ExclusiveCustTreeNodeId")) {

				String excludedCustomerExist = promotionRow.get("ExclusiveCustTreeNodeId");
				if (!excludedCustomerExist.isEmpty()) {
					promotionRow.put("ExclusiveCustTreeNodeId", excludedCustomerExist + "," + excludedCustomer.toString());
				}
			} else {
				promotionRow.put("ExclusiveCustTreeNodeId", excludedCustomer.toString());
			}
		}

		private static void processInclusiveCustomer(List<String> customerGroup, Map<String, String> promotionRow) {
			StringBuffer customerGroups = new StringBuffer(StringUtils.EMPTY);
			for (String customer : customerGroup) {
				if (customerGroups.length() != 0) {
					customerGroups.append(",");
				}
				String name = StringUtils.substringBeforeLast(customer.trim(),
						SiteoneintegrationConstants.SEPARATOR_UNDERSCORE);
				customerGroups.append(name);
			}
			promotionRow.put("InclusiveCustTreeNodeId", customerGroups.toString());
		}

		private static void processInclusiveProducts(List<String> products, Map<String, String> promotionRow) {
			StringBuffer product = new StringBuffer(StringUtils.EMPTY);
			for (String pro : products) {
				if (product.length() != 0) {
					product.append(",");
				}
				product.append(pro);
			}
			promotionRow.put("InclusiveSkuId", product.toString());
		}

		private static void processExcludedProducts(List<String> products, Map<String, String> promotionRow) {
			StringBuffer excludedProduct = new StringBuffer(StringUtils.EMPTY);
			for (String pro : products) {
				if (excludedProduct.length() != 0) {
					excludedProduct.append(",");
				}
				excludedProduct.append(pro);
			}
			if (promotionRow.containsKey("ExclusiveSkuId")) {

				String excludedProductExist = promotionRow.get("ExclusiveSkuId");
				if (!excludedProductExist.isEmpty()) {
					promotionRow.put("ExclusiveSkuId", excludedProductExist + "," + excludedProduct.toString());
				}
			} else {
				promotionRow.put("ExclusiveSkuId", excludedProduct.toString());
			}
		}

		private static void processInclusiveCategories(List<String> categories, Map<String, String> promotionRow) {
			StringBuffer cat = new StringBuffer(StringUtils.EMPTY);
			for (String category : categories) {
				if (cat.length() != 0) {
					cat.append(",");
				}
				cat.append(category);
			}
			promotionRow.put("InclusiveProducttypeId", cat.toString());
		}

		private static void processExcludedCategories(List<String> categories, Map<String, String> promotionRow) {
			StringBuffer excludedCategory = new StringBuffer(StringUtils.EMPTY);
			for (String pro : categories) {
				if (excludedCategory.length() != 0) {
					excludedCategory.append(",");
				}
				excludedCategory.append(pro);
			}
			if (promotionRow.containsKey("ExclusiveProducttypeId")) {

				String excludedProductExist = promotionRow.get("ExclusiveProducttypeId");
				if (!excludedProductExist.isEmpty()) {
					promotionRow.put("ExclusiveProducttypeId", excludedProductExist + "," + excludedCategory.toString());
				}
			} else {
				promotionRow.put("ExclusiveProducttypeId", excludedCategory.toString());
			}
		}

		private static void processQuantity(Parameters param, Map<String, String> promotionRow) {
			if (null != param.getQuantity() && null != param.getQuantity().getValue()) {
				Integer quantity = param.getQuantity().getValue();
				
				if (param.getOperator().getValue()
						.equalsIgnoreCase(SiteoneintegrationConstants.PROMOTION_COND_PARM_OPERATOR_GREATER_THAN_OR_EQUAL)) {
					promotionRow.put("MinimumPurchaseQuantity", Integer.toString(quantity));
				} else if (param.getOperator().getValue()
						.equalsIgnoreCase(SiteoneintegrationConstants.PROMOTION_COND_PARM_OPERATOR_LESS_THAN_OR_EQUAL)) {
					promotionRow.put("MaximumPurchaseQuantity", Integer.toString(quantity));
				} else if (param.getOperator().getValue()
						.equalsIgnoreCase(SiteoneintegrationConstants.PROMOTION_COND_PARM_OPERATOR_LESS_THAN)) {
					quantity = quantity - 1;
					promotionRow.put("MaximumPurchaseQuantity", Integer.toString(quantity));
				} else if (param.getOperator().getValue()
						.equalsIgnoreCase(SiteoneintegrationConstants.PROMOTION_COND_PARM_OPERATOR_GREATER_THAN)) {
					quantity = quantity + 1;
					promotionRow.put("MinimumPurchaseQuantity", Integer.toString(quantity));
				}
				
				
				
			}
		}
		
		private static void processCoupon(PromotionFeedCondition condition, Map<String, String> promotionRow) {
			Parameters param = condition.getParameters();

			if (null != param.getCoupons()) {
				List<String> couponCodes = param.getCoupons().getValue();
				StringBuffer coupons = new StringBuffer();
				for (String couponCode : couponCodes) {
					if (coupons.length() != 0) {
						coupons.append(",");
					}
					coupons.append(couponCode);
				}
				promotionRow.put("Code", coupons.toString());
			}
		}

		private static void processTargetCustomer(PromotionFeedCondition condition, Map<String, String> promotionRow) {
			Parameters param = condition.getParameters();

			if (null != param.getCustomerGroups() && null != param.getCustomerGroups().getValue()
					&& param.getCustomerGroupsOperator().getValue()
							.equalsIgnoreCase(SiteoneintegrationConstants.PROMOTION_COND_PARM_OPERATOR_CONTAINS_ANY)
					|| param.getCustomerGroupsOperator().getValue()
							.equalsIgnoreCase(SiteoneintegrationConstants.PROMOTION_COND_PARM_OPERATOR_CONTAINS_All)) {
				List<String> customerGroup = param.getCustomerGroups().getValue();
				processInclusiveCustomer(customerGroup, promotionRow);
			} else if (null != param.getCustomerGroups() && null != param.getCustomerGroups().getValue()
					&& param.getCustomerGroupsOperator().getValue()
							.equalsIgnoreCase(SiteoneintegrationConstants.PROMOTION_COND_PARM_OPERATOR_DOES_NOT_CONTAIN)) {
				List<String> customerGroup = param.getCustomerGroups().getValue();
				processExcludedCustomer(customerGroup, promotionRow);
			}

			if (null != param.getExcludedCustomerGroups() && null != param.getExcludedCustomerGroups().getValue()) {
				List<String> excludedCustomerGroup = param.getExcludedCustomerGroups().getValue();
				processExcludedCustomer(excludedCustomerGroup, promotionRow);
			}
		}

}
