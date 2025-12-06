package com.siteone.integration.jobs.product;

import org.apache.log4j.Logger;

import com.siteone.core.model.ProductFeedCronJobModel;
import com.siteone.integration.jobs.product.service.ProductFeedCronJobService;
import com.siteone.integration.jobs.product.service.impl.DefaultProductFeedCronJobService;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;


public class ProductFeedCronJob extends AbstractJobPerformable<ProductFeedCronJobModel>{
	
	private ProductFeedCronJobService productFeedCronJobService;
	private static final Logger LOG = Logger.getLogger(DefaultProductFeedCronJobService.class);

	@Override
    public PerformResult perform(ProductFeedCronJobModel productFeedCronJobModel) {
        try {
            getProductFeedCronJobService().exportProductFeed(productFeedCronJobModel);
        } catch (Exception e) {
            // Log the error
            LOG.error("Error exporting product feed: " + e.getMessage());
            LOG.info("Error exporting product feeds: " + e.getMessage());
        }

        try {
            getProductFeedCronJobService().exportVariantProductFeed(productFeedCronJobModel);
        } catch (Exception e) {
            // Log the error
            LOG.error("Error exporting variant product feed: " + e.getMessage());
            LOG.info("Error exporting variant product feeds: " + e.getMessage());
        }

        try {
            getProductFeedCronJobService().exportBaseProductFeed(productFeedCronJobModel);
        } catch (Exception e) {
            // Log the error
            LOG.error("Error exporting base product feed: " + e.getMessage());
           LOG.info("Error exporting base product feeds: " + e.getMessage());
       }
        

        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }


	/**
	 * @return the productFeedCronJobService
	 */
	public ProductFeedCronJobService getProductFeedCronJobService() {
		return productFeedCronJobService;
	}

	/**
	 * @param productFeedCronJobService the productFeedCronJobService to set
	 */
	public void setProductFeedCronJobService(ProductFeedCronJobService productFeedCronJobService) {
		this.productFeedCronJobService = productFeedCronJobService;
	}
	

}
