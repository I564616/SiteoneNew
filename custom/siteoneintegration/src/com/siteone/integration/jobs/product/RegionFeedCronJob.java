package com.siteone.integration.jobs.product;

import com.siteone.core.model.RegionFeedCronJobModel;
import com.siteone.integration.jobs.product.service.RegionFeedCronJobService;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

public class RegionFeedCronJob extends AbstractJobPerformable<RegionFeedCronJobModel> {

private RegionFeedCronJobService regionFeedCronJobService;
	
	@Override
	public PerformResult perform(RegionFeedCronJobModel regionFeedCronJobModel) {
		getRegionFeedCronJobService().exportRegionFeed(regionFeedCronJobModel);
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}


	/**
	 * @return the regionFeedCronJobService
	 */
	public RegionFeedCronJobService getRegionFeedCronJobService() {
		return regionFeedCronJobService;
	}

	/**
	 * @param regionFeedCronJobService the regionFeedCronJobService to set
	 */
	public void setRegionFeedCronJobService(RegionFeedCronJobService regionFeedCronJobService) {
		this.regionFeedCronJobService = regionFeedCronJobService;
	}
}
