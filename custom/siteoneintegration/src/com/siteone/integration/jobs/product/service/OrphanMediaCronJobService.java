package com.siteone.integration.jobs.product.service;


import com.siteone.core.model.OrphanMediaCronJobModel;

public interface OrphanMediaCronJobService {

	void cleanUpOrphanMedia(OrphanMediaCronJobModel orphanMediaCronJobModel);

}
