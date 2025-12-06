/**
 * 
 */
package com.siteone.integration.jobs.product.service.impl;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.siteone.core.model.ProductRegionFeedCronJobModel;
import com.siteone.integration.jobs.product.service.ProductFeedService;
import com.siteone.integration.jobs.product.service.ProductRegionFeedCronJobService;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.model.ModelService;

/**
 * @author SD02010
 *
 */
public class DefaultProductRegionFeedCronJobService implements ProductRegionFeedCronJobService {
	
	private ProductFeedService productFeedService;
	private ModelService modelService;
	private static final Logger LOG = Logger.getLogger(DefaultProductRegionFeedCronJobService.class);

	@Override
	public void exportProductRegionFeed(ProductRegionFeedCronJobModel productRegionFeedCronJobModel) {
		try {
			getProductFeedService().exportProductRegionFeed();
		}
		catch (IOException ioException) {			
			productRegionFeedCronJobModel.setResult(CronJobResult.FAILURE);
			productRegionFeedCronJobModel.setStatus(CronJobStatus.ABORTED);
			LOG.error("error in export product region feed service :: "+ioException);
		} finally {
			Date date = new Date();
			productRegionFeedCronJobModel.setLastExecutionTime(date);
			getModelService().save(productRegionFeedCronJobModel);
		}
		
	}

	/**
	 * @return the productFeedService
	 */
	public ProductFeedService getProductFeedService() {
		return productFeedService;
	}

	/**
	 * @param productFeedService the productFeedService to set
	 */
	public void setProductFeedService(ProductFeedService productFeedService) {
		this.productFeedService = productFeedService;
	}

	/**
	 * @return the modelService
	 */
	public ModelService getModelService() {
		return modelService;
	}

	/**
	 * @param modelService the modelService to set
	 */
	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}

}
