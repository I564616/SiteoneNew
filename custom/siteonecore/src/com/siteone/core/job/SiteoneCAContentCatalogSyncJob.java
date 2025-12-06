/**
 *
 */
package com.siteone.core.job;

import de.hybris.platform.commerceservices.setup.SetupSyncJobService;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.util.Config;

import jakarta.annotation.Resource;


/**
 *
 */
public class SiteoneCAContentCatalogSyncJob extends AbstractJobPerformable<CronJobModel>
{
	private static final String CONTENT_CATALOG = "siteoneCAContentCatalog";

	@Resource
	private SetupSyncJobService setupSyncJobService;

	@Override
	public PerformResult perform(final CronJobModel cronJob)
	{
		/**
		 * Implemented based on AbstractDataImportService > synchronizeProductCatalog
		 */
		setupSyncJobService.createContentCatalogSyncJob(CONTENT_CATALOG);
		return setupSyncJobService.executeCatalogSyncJob(CONTENT_CATALOG);
	}

}
