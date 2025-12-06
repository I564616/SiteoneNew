package com.siteone.integration.jobs.product;

import com.siteone.core.model.InventoryFeedCronJobModel;
import com.siteone.integration.jobs.product.service.InventoryFeedCronJobService;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;


public class InventoryFeedCronJob extends AbstractJobPerformable<InventoryFeedCronJobModel>{

	private InventoryFeedCronJobService inventoryFeedCronJobService;
	
	@Override
	public PerformResult perform(InventoryFeedCronJobModel inventoryFeedCronJobModel) {
		getInventoryFeedCronJobService().exportInventoryFeed(inventoryFeedCronJobModel);
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}


	/**
	 * @return the inventoryFeedCronJobService
	 */
	public InventoryFeedCronJobService getInventoryFeedCronJobService() {
		return inventoryFeedCronJobService;
	}

	/**
	 * @param inventoryFeedCronJobService the inventoryFeedCronJobService to set
	 */
	public void setInventoryFeedCronJobService(InventoryFeedCronJobService inventoryFeedCronJobService) {
		this.inventoryFeedCronJobService = inventoryFeedCronJobService;
	}

}
