/**
 * 
 */
package com.siteone.integration.jobs.product;

import com.siteone.core.model.ProductRegionFeedCronJobModel;
import com.siteone.integration.jobs.product.service.ProductRegionFeedCronJobService;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

/**
 * @author SD02010
 *
 */
public class ProductRegionFeedCronJob extends AbstractJobPerformable<ProductRegionFeedCronJobModel>{

	private ProductRegionFeedCronJobService productRegionFeedCronJobService; 
	
	@Override
	public PerformResult perform(ProductRegionFeedCronJobModel productRegionFeedCronJobModel) {
		getProductRegionFeedCronJobService().exportProductRegionFeed(productRegionFeedCronJobModel);
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	/**
	 * @return the productRegionFeedCronJobService
	 */
	public ProductRegionFeedCronJobService getProductRegionFeedCronJobService() {
		return productRegionFeedCronJobService;
	}

	/**
	 * @param productRegionFeedCronJobService the productRegionFeedCronJobService to set
	 */
	public void setProductRegionFeedCronJobService(ProductRegionFeedCronJobService productRegionFeedCronJobService) {
		this.productRegionFeedCronJobService = productRegionFeedCronJobService;
	}

}
