package com.siteone.integration.jobs.product;
import com.siteone.core.model.FullProductFeedCronJobModel;
import com.siteone.integration.jobs.product.service.FullProductFeedCronJobService;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;


public class FullProductFeedCronJob extends AbstractJobPerformable<FullProductFeedCronJobModel>{

	private FullProductFeedCronJobService fullProductFeedCronJobService;
	
	@Override
	public PerformResult perform(FullProductFeedCronJobModel fullProductFeedCronJobModel) {
		getFullProductFeedCronJobService().exportFullProductFeedFeedData(fullProductFeedCronJobModel);
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	public FullProductFeedCronJobService getFullProductFeedCronJobService() {
		return fullProductFeedCronJobService;
	}

	public void setFullProductFeedCronJobService(
			FullProductFeedCronJobService fullProductFeedCronJobService) {
		this.fullProductFeedCronJobService = fullProductFeedCronJobService;
	}
}
