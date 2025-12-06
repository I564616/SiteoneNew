package com.siteone.integration.jobs.product;
import com.siteone.core.model.NurseryInventoryFeedCronJobModel;
import com.siteone.integration.jobs.product.service.NurseryInventoryFeedCronJobService;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;


public class NurseryInventoryFeedCronJob extends AbstractJobPerformable<NurseryInventoryFeedCronJobModel>{

	private NurseryInventoryFeedCronJobService nurseryInventoryFeedCronJobService;
	
	@Override
	public PerformResult perform(NurseryInventoryFeedCronJobModel nurseryInventoryFeedCronJobModel) {
		getNurseryInventoryFeedCronJobService().exportNurseryInventoryFeedData(nurseryInventoryFeedCronJobModel);
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	public NurseryInventoryFeedCronJobService getNurseryInventoryFeedCronJobService() {
		return nurseryInventoryFeedCronJobService;
	}

	public void setNurseryInventoryFeedCronJobService(
			NurseryInventoryFeedCronJobService nurseryInventoryFeedCronJobService) {
		this.nurseryInventoryFeedCronJobService = nurseryInventoryFeedCronJobService;
	}
}
