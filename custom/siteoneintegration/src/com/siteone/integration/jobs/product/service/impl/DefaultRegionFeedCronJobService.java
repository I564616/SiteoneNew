package com.siteone.integration.jobs.product.service.impl;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.siteone.core.model.RegionFeedCronJobModel;
import com.siteone.integration.jobs.product.service.RegionFeedCronJobService;
import com.siteone.integration.jobs.product.service.ProductFeedService;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.model.ModelService;

public class DefaultRegionFeedCronJobService implements RegionFeedCronJobService{
	private ProductFeedService productFeedService;
	private ModelService modelService;
	private static final Logger LOG = Logger.getLogger(DefaultRegionFeedCronJobService.class);
	
	@Override
	public void exportRegionFeed(RegionFeedCronJobModel regionFeedCronJobModel) {
		try {
			getProductFeedService().exportRegionFeed();
		}
		catch (IOException ioException) {			
			regionFeedCronJobModel.setResult(CronJobResult.FAILURE);
			regionFeedCronJobModel.setStatus(CronJobStatus.ABORTED);
			LOG.error("error in export region feed service :: "+ioException);
		} finally {
			Date date = new Date();
			regionFeedCronJobModel.setLastExecutionTime(date);
			getModelService().save(regionFeedCronJobModel);
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
