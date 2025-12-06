package com.siteone.integration.jobs.product.service.impl;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.siteone.core.model.FullProductFeedCronJobModel;
import com.siteone.integration.jobs.product.service.FullProductFeedCronJobService;
import com.siteone.integration.jobs.product.service.ProductFeedService;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.model.ModelService;

public class DefaultFullProductFeedCronJobService implements FullProductFeedCronJobService{

	private ProductFeedService productFeedService;
	private ModelService modelService;
	private static final Logger LOG = Logger.getLogger(DefaultFullProductFeedCronJobService.class);

	@Override
	public void exportFullProductFeedFeedData(FullProductFeedCronJobModel fullProductFeedCronJobModel) {
		try {
			getProductFeedService().exportFullProductFeed();
		}
		catch (IOException ioException) {			
			fullProductFeedCronJobModel.setResult(CronJobResult.FAILURE);
			fullProductFeedCronJobModel.setStatus(CronJobStatus.ABORTED);
			LOG.error("error in export full product feed service :: "+ioException);
		} finally {
			Date date = new Date();
			fullProductFeedCronJobModel.setLastExecutionTime(date);
			getModelService().save(fullProductFeedCronJobModel);
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
