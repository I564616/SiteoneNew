package com.siteone.integration.jobs.promotion.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.siteone.core.model.PromotionFeedCronJobModel;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.jobs.promotion.dao.PromotionFeedCronJobDao;
import com.siteone.integration.jobs.promotion.service.PromotionFeedCronJobService;
import com.siteone.integration.jobs.promotions.process.PromotionActionUtil;
import com.siteone.integration.jobs.promotions.process.PromotionConditionUtil;
import com.siteone.integration.services.dataimport.SiteOneBlobDataImportService;

import de.hybris.platform.couponservices.model.SingleCodeCouponModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.promotionengineservices.model.PromotionSourceRuleModel;
import de.hybris.platform.promotionengineservices.model.RuleBasedPromotionModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.util.Config;

public class DefaultPromotionFeedCronJobService implements PromotionFeedCronJobService {

	private static final Logger LOG = Logger.getLogger(DefaultPromotionFeedCronJobService.class);

	private static final String[] FILE_HEADER = { "CouponId", "CouponTypeId", "Code", "Description", "EffectiveDate",
			"ExpirationDate", "SingleUse", "ShowCouponCodeOnInvoice", "AmountOff", "MinimumPurchaseQuantity",
			"MaximumPurchaseQuantity", "GetQuantity", "PercentOff", "ItemB", "Hidden", "MinimumOrderAmount",
			"PriceOverride", "InclusiveCustTreeNodeId", "InclusiveProducttypeId", "InclusiveSkuId",
			"ExclusiveCustTreeNodeId", "ExclusiveProducttypeId", "ExclusiveSkuId" ,"DivisionId","Message"};

	private static final String FIELD_DELIMITER = "|";

	private static final String DATE_FORMATTER = "yyyy-MM-dd";

	private PromotionFeedCronJobDao promotionFeedCronJobDao;

	private ModelService modelService;

	/* The Blob dataImport service*/
	private SiteOneBlobDataImportService blobDataImportService;

	/* The configuration service*/
	private ConfigurationService configurationService;

	public void exportPromotionFeed(PromotionFeedCronJobModel promotionFeedCronJobModel)throws JsonParseException,IOException {

		List<RuleBasedPromotionModel> ruleBasedPromotionModels = getPromotionFeedCronJobDao().getPromotions();
		final String promotionFeedContainer= getConfigurationService().getConfiguration().getString(SiteoneintegrationConstants.PROMOTION_FEED_TARGET_LOCATION);

		PrintWriter printWriter = null;
		File file = null;

		try {

			file= File.createTempFile(getFileName(),".txt");
			printWriter = new PrintWriter(file);

			printHeader(printWriter);

			for (RuleBasedPromotionModel promotion : ruleBasedPromotionModels) {

				Map<String, String> promotionRow = new HashMap<String, String>();

				String couponName = promotion.getCode();
				String couponType = findCouponType(couponName);             	
				
				if (!couponType.isEmpty()) {
					// Setting promotion message
					promotionRow.put("Message", StringUtils.isNotBlank(promotion.getMessageFired(Locale.ENGLISH))? promotion.getMessageFired(Locale.ENGLISH) :"");
					// Setting coupon Type of promotion
					promotionRow.put("CouponTypeId", couponType);

					PromotionSourceRuleModel promotionSourceRule = getPromotionSourceRuleByTitle(promotion.getTitle());
					
					promotionRow.put("Description", null != promotionSourceRule.getDescription(Locale.ENGLISH) ? promotionSourceRule.getDescription(Locale.ENGLISH) :"");		
					
					// Processing promotion headers
					processPromotionHeader(promotion, promotionRow, couponType,promotionSourceRule);

					// Processing promotion Dates
					processPromotionDates(promotionSourceRule, promotionRow);

					try {
                        
						//Processing promotion feed action
						PromotionActionUtil.processAction(couponType, promotionSourceRule, promotionRow,couponName);

						//Processing Promotion feed Conditions
						PromotionConditionUtil.processCondition(couponType, promotionSourceRule, promotionRow,couponName);
						
						processCouponMaxRedemption(promotionRow,promotion.getCode());

					} catch (JsonSyntaxException jsonSyntaxException) {
						LOG.error("Exception occured while parsing json", jsonSyntaxException);
					} catch (JsonParseException jsonParseException) {
						LOG.error("Exception occured in promotion feed File ", jsonParseException);
					}
					printRow(promotionRow, printWriter);
				}
			}

		} catch (IOException ioException) {
			LOG.error("Exception occured in promotion feed File " + (Config.getString(SiteoneintegrationConstants.PROMOTION_FEED_TARGET_LOCATION,
					StringUtils.EMPTY) + getFileName()), ioException);
			promotionFeedCronJobModel.setResult(CronJobResult.FAILURE);
			promotionFeedCronJobModel.setStatus(CronJobStatus.ABORTED);
		} finally {
			Date date = new Date();
			promotionFeedCronJobModel.setLastExecutionTime(date);
			getModelService().save(promotionFeedCronJobModel);
			if(null != printWriter){
				printWriter.close();
			}			
		}
		// Migration | Write  Blob
		getBlobDataImportService().writeBlob(file,promotionFeedContainer);
	}
	public PromotionFeedCronJobDao getPromotionFeedCronJobDao() {
		return promotionFeedCronJobDao;
	}

	public void setPromotionFeedCronJobDao(PromotionFeedCronJobDao promotionFeedCronJobDao) {
		this.promotionFeedCronJobDao = promotionFeedCronJobDao;
	}

	public ModelService getModelService() {
		return modelService;
	}

	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}


	private void processCouponMaxRedemption(Map<String, String> promotionRow,String couponId) {
		if(couponId.contains(SiteoneintegrationConstants.COUPON_CODE_PERCENTAGE_DISCOUNT_ON_CART) || couponId.contains(SiteoneintegrationConstants.COUPON_CODE_PERCENTAGE_DISCOUNT_ON_PRODUCTS)){
			String couponCode= promotionRow.get("Code");
			SingleCodeCouponModel singleCodeCouponModel = getPromotionFeedCronJobDao().getCouponByCode(couponCode);
		if(null != singleCodeCouponModel && singleCodeCouponModel.getMaxRedemptionsPerCustomer() != null  && singleCodeCouponModel.getMaxRedemptionsPerCustomer() == 1){
			promotionRow.put("SingleUse","True");
		}else{
			promotionRow.put("SingleUse","False");
		}
	  }
	}

	private String getFileName() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy_hhmmss");
		String currentServerDate = dateFormat.format(cal.getTime());
		String fileName = Config.getString("promotion.feed.fileName", "promotion")
				+ SiteoneintegrationConstants.SEPARATOR_UNDERSCORE + currentServerDate;
		return fileName;
	}

	private void processPromotionDates(PromotionSourceRuleModel promotionSourceRule, Map<String, String> promotionRow) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMATTER);
		promotionRow.put("EffectiveDate", dateFormat.format(null != promotionSourceRule.getStartDate() ? promotionSourceRule.getStartDate():""));
		promotionRow.put("ExpirationDate", dateFormat.format(null != promotionSourceRule.getEndDate() ? promotionSourceRule.getEndDate() : ""));
	}

	private void processPromotionHeader(RuleBasedPromotionModel promotion, Map<String, String> promotionRow,
			String couponType,PromotionSourceRuleModel promotionSourceRule) {
		    
		   promotionRow.put("CouponId", promotionSourceRule.getUuid());
		   
		   if("siteonePromoGrp".equalsIgnoreCase(promotionSourceRule.getWebsite().getIdentifier())){
			   promotionRow.put("DivisionId", "1");
		   }
		   else
		   {
			   promotionRow.put("DivisionId", "1"); 
		   }
		
		if (null == promotionRow.get("Code") || promotionRow.get("Code").isEmpty()) {
			promotionRow.put("Code", promotionSourceRule.getCouponCode());			
		}
		// Need to check
		promotionRow.put("ShowCouponCodeOnInvoice", StringUtils.EMPTY);
	}

	private String findCouponType(String promotionCode) {

		if (promotionCode.contains(SiteoneintegrationConstants.PRODUCT_PERCENTAGE_DISCOUNT)) {
			return Config.getString(SiteoneintegrationConstants.PRODUCT_PERCENTAGE_DISCOUNT_ID, StringUtils.EMPTY);
		} else if (promotionCode.contains(SiteoneintegrationConstants.PRODUCT_FIXED_PRICE)) {
			return Config.getString(SiteoneintegrationConstants.PRODUCT_FIXED_PRICE_ID, StringUtils.EMPTY);
		} else if (promotionCode.contains(SiteoneintegrationConstants.PRODUCT_FIXED_DISCOUNT)) {
			return Config.getString(SiteoneintegrationConstants.PRODUCT_FIXED_DISCOUNT_ID, StringUtils.EMPTY);
		} else if (promotionCode.contains(SiteoneintegrationConstants.TARGET_CUSTOMER_PERCENTAGE_DISCOUNT_CART)) {
			return Config.getString(SiteoneintegrationConstants.TARGET_CUSTOMER_PERCENTAGE_DISCOUNT_CART_ID,
					StringUtils.EMPTY);
		} else if (promotionCode.contains(SiteoneintegrationConstants.PRODUCT_BUY_X_GET_Y_FREE)) {
			return Config.getString(SiteoneintegrationConstants.PRODUCT_BUY_X_GET_Y_FREE_ID, StringUtils.EMPTY);
		} else if (promotionCode.contains(SiteoneintegrationConstants.PRODUCT_PERFECT_PARTNER_FIXED_PRICE)) {
			return Config.getString(SiteoneintegrationConstants.PRODUCT_PERFECT_PARTNER_FIXED_PRICE_ID,
					StringUtils.EMPTY);
		} else if (promotionCode.contains(SiteoneintegrationConstants.PRODUCT_MULTIBUY_FIXED_PRICE)) {
			return Config.getString(SiteoneintegrationConstants.PRODUCT_MULTIBUY_FIXED_PRICE_ID, StringUtils.EMPTY);
		} else if (promotionCode.contains(SiteoneintegrationConstants.PRODUCT_BUY_X_OF_A_GET_Y_OF_B_FREE)) {
			return Config.getString(SiteoneintegrationConstants.PRODUCT_BUY_X_OF_A_GET_Y_OF_B_FREE_ID,
					StringUtils.EMPTY);
		} else if (promotionCode.contains(SiteoneintegrationConstants.COUPON_CODE_PERCENTAGE_DISCOUNT_ON_CART)) {
			return Config.getString(SiteoneintegrationConstants.COUPON_CODE_PERCENTAGE_DISCOUNT_ON_CART_ID,
					StringUtils.EMPTY);
		} else if (promotionCode.contains(SiteoneintegrationConstants.COUPON_CODE_PERCENTAGE_DISCOUNT_ON_PRODUCTS)) {
			return Config.getString(SiteoneintegrationConstants.COUPON_CODE_PERCENTAGE_DISCOUNT_ON_PRODUCTS_ID,
					StringUtils.EMPTY);
		}

		return StringUtils.EMPTY;
	}

	private void printHeader(PrintWriter printWriter) {
		StringBuffer fileHeader = new StringBuffer();
		for (String header : FILE_HEADER) {
			if (fileHeader.length() != 0) {
				fileHeader.append(FIELD_DELIMITER);
			}
			fileHeader.append(header);
		}

		printWriter.println(fileHeader);
	}

	private void printRow(Map<String, String> promotionRow, PrintWriter printWriter) {
		StringBuffer feedRow = new StringBuffer();
		for (String header : FILE_HEADER) {
			if (feedRow.length() != 0) {
				feedRow.append(FIELD_DELIMITER);
			}
			feedRow.append((promotionRow.get(header) != null) ? promotionRow.get(header) : StringUtils.EMPTY);
		}
		printWriter.println(feedRow);
	}
	
	
	@Override
	public PromotionSourceRuleModel getPromotionSourceRuleByTitle(String title) {
		PromotionSourceRuleModel promotionSourceRule = getPromotionFeedCronJobDao().getPromotionSourceRule(title);
		return promotionSourceRule;
	}

	/**
	 * Getter method for blobDataImportService
	 *
	 * @return the blobDataImportService
	 */
	public SiteOneBlobDataImportService getBlobDataImportService() {
		return blobDataImportService;
	}

	/**
	 * Setter method for  blobDataImportService
	 *
	 * @param blobDataImportService
	 *            the blobDataImportService to set
	 */
	public void setBlobDataImportService(SiteOneBlobDataImportService blobDataImportService) {
		this.blobDataImportService = blobDataImportService;
	}

	/**
	 * Getter method for configurationService
	 *
	 * @return the configurationService
	 */
	public ConfigurationService getConfigurationService() {
		return configurationService;
	}

	/**
	 * Setter method for  configurationService
	 *
	 * @param configurationService
	 *            the configurationService to set
	 */
	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

}
