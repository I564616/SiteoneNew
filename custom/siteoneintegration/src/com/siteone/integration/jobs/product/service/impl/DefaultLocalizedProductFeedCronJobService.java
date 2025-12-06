/**
 * 
 */
package com.siteone.integration.jobs.product.service.impl;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.siteone.core.model.LocalizedProductFeedCronJobModel;
import com.siteone.integration.jobs.product.service.LocalizedProductFeedCronJobService;
import com.siteone.integration.jobs.product.service.ProductFeedService;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.model.ModelService;

/**
 * @author SD02010
 *
 */
public class DefaultLocalizedProductFeedCronJobService implements LocalizedProductFeedCronJobService{

	private ProductFeedService productFeedService;
	private ModelService modelService;
	private static final Logger LOG = Logger.getLogger(DefaultLocalizedProductFeedCronJobService.class);
	
	@Override
	public void exportLocalizedProductFeed(LocalizedProductFeedCronJobModel localizedProductFeedCronJobModel) {
		try {
			getProductFeedService().exportLocalizedProductFeed();
		}
		catch (IOException ioException) {			
			localizedProductFeedCronJobModel.setResult(CronJobResult.FAILURE);
			localizedProductFeedCronJobModel.setStatus(CronJobStatus.ABORTED);
			LOG.error("error in export product feed service :: "+ioException);
		} finally {
			Date date = new Date();
			localizedProductFeedCronJobModel.setLastExecutionTime(date);
			getModelService().save(localizedProductFeedCronJobModel);
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
