package com.siteone.integration.jobs.product;


import com.siteone.core.model.OrphanMediaCronJobModel;
import com.siteone.integration.jobs.product.service.OrphanMediaCronJobService;

import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

public class OrphanMediaCronJob extends AbstractJobPerformable<OrphanMediaCronJobModel> {
	
      private OrphanMediaCronJobService orphanMediaCronJobService;

		    @Override
		    public PerformResult perform(OrphanMediaCronJobModel cronJobModel) {
		        orphanMediaCronJobService.cleanUpOrphanMedia(cronJobModel);
		        return new PerformResult(cronJobModel.getResult(), cronJobModel.getStatus());
		    }

		    public void setOrphanMediaCronJobService(OrphanMediaCronJobService orphanMediaCronJobService) {
		        this.orphanMediaCronJobService = orphanMediaCronJobService;
		    }
		}



