package com.siteone.integration.jobs.promotion;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.google.gson.JsonParseException;
import com.siteone.core.model.PromotionFeedCronJobModel;
import com.siteone.integration.jobs.promotion.service.PromotionFeedCronJobService;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

public class PromotionFeedCronJob extends AbstractJobPerformable<PromotionFeedCronJobModel>{ 
	
	private PromotionFeedCronJobService promotionFeedCronJobService;
	
	private static final Logger LOG = Logger.getLogger(PromotionFeedCronJob.class);
	
	public PerformResult perform(PromotionFeedCronJobModel promotionFeedCronJobModel) {
		
		LOG.error("Inside promotion cron job "+promotionFeedCronJobModel.getCode());
		try {
			
			getPromotionFeedCronJobService().exportPromotionFeed(promotionFeedCronJobModel);
		}
		catch (JsonParseException | IOException exception)
		{
			LOG.error("Exception occured in promotion cron job ", exception);
		}
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	public PromotionFeedCronJobService getPromotionFeedCronJobService() {
		return promotionFeedCronJobService;
	}

	public void setPromotionFeedCronJobService(PromotionFeedCronJobService promotionFeedCronJobService) {
		this.promotionFeedCronJobService = promotionFeedCronJobService;
	}

}
