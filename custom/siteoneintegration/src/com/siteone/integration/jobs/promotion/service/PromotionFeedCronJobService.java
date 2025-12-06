package com.siteone.integration.jobs.promotion.service;

import java.io.IOException;

import com.google.gson.JsonParseException;
import com.siteone.core.model.PromotionFeedCronJobModel;

import de.hybris.platform.promotionengineservices.model.PromotionSourceRuleModel;

public interface PromotionFeedCronJobService {
  
	public void exportPromotionFeed(PromotionFeedCronJobModel promotionFeedCronJobModel) throws JsonParseException,IOException;
	
	public PromotionSourceRuleModel getPromotionSourceRuleByTitle(String title);
}
