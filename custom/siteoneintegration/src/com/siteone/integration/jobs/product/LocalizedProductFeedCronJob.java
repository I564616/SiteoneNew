/**
 * 
 */
package com.siteone.integration.jobs.product;

import com.siteone.core.model.LocalizedProductFeedCronJobModel;
import com.siteone.integration.jobs.product.service.LocalizedProductFeedCronJobService;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

/**
 * @author SD02010
 *
 */
public class LocalizedProductFeedCronJob extends AbstractJobPerformable<LocalizedProductFeedCronJobModel>{
	
	private LocalizedProductFeedCronJobService localizedProductFeedCronJobService;

	@Override
	public PerformResult perform(LocalizedProductFeedCronJobModel localizedProductFeedCronJobModel) {
		getLocalizedProductFeedCronJobService().exportLocalizedProductFeed(localizedProductFeedCronJobModel);
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	/**
	 * @return the localizedProductFeedCronJobService
	 */
	public LocalizedProductFeedCronJobService getLocalizedProductFeedCronJobService() {
		return localizedProductFeedCronJobService;
	}

	/**
	 * @param localizedProductFeedCronJobService the localizedProductFeedCronJobService to set
	 */
	public void setLocalizedProductFeedCronJobService(
			LocalizedProductFeedCronJobService localizedProductFeedCronJobService) {
		this.localizedProductFeedCronJobService = localizedProductFeedCronJobService;
	}



}
