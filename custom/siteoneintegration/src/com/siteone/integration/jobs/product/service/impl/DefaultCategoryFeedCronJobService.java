package com.siteone.integration.jobs.product.service.impl;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.siteone.core.model.CategoryFeedCronJobModel;
import com.siteone.integration.jobs.product.service.CategoryFeedCronJobService;
import com.siteone.integration.jobs.product.service.ProductFeedService;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.model.ModelService;

public class DefaultCategoryFeedCronJobService implements CategoryFeedCronJobService{

	private ProductFeedService productFeedService;
	private ModelService modelService;
	private static final Logger LOG = Logger.getLogger(DefaultCategoryFeedCronJobService.class);

	@Override
	public void exportCategoryFeed(CategoryFeedCronJobModel categoryFeedCronJobModel) {
		try {
			getProductFeedService().exportCategoryFeed();
		}
		catch (IOException ioException) {			
			categoryFeedCronJobModel.setResult(CronJobResult.FAILURE);
			categoryFeedCronJobModel.setStatus(CronJobStatus.ABORTED);
			LOG.error("error in export category feed service :: "+ioException);
		} finally {
			Date date = new Date();
			categoryFeedCronJobModel.setLastExecutionTime(date);
			getModelService().save(categoryFeedCronJobModel);
		}

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


}