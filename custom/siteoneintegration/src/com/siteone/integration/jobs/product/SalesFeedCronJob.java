package com.siteone.integration.jobs.product;
import org.apache.log4j.Logger;

import com.siteone.core.model.SalesFeedCronJobModel;
import com.siteone.integration.jobs.product.service.SalesFeedCronJobService;
import com.siteone.integration.jobs.product.service.impl.DefaultSalesFeedCronJobService;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import java.io.IOException;
import jakarta.validation.ValidationException;


public class SalesFeedCronJob extends AbstractJobPerformable<SalesFeedCronJobModel>{
	
	private static final Logger LOG = Logger.getLogger(SalesFeedCronJob.class);

	private SalesFeedCronJobService salesFeedCronJobService;
	
	@Override
	public PerformResult perform(SalesFeedCronJobModel salesFeedCronJobModel) {
		
		LOG.info("SalesFeedCronJob is triggered ");
		try {
			getSalesFeedCronJobService().generateSalesDataImport(salesFeedCronJobModel);
		} catch (ValidationException | IOException e) {
			LOG.info("SalesFeedCronJob is failed due to IO Exception ");
		}
		LOG.info("SalesFeedCronJob is End ");
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	public SalesFeedCronJobService getSalesFeedCronJobService() {
		return salesFeedCronJobService;
	}

	public void setSalesFeedCronJobService(
			SalesFeedCronJobService salesFeedCronJobService) {
		this.salesFeedCronJobService = salesFeedCronJobService;
	}

	
}
