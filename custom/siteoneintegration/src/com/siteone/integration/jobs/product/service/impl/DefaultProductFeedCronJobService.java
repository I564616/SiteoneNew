package com.siteone.integration.jobs.product.service.impl;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.siteone.core.model.ProductFeedCronJobModel;
import com.siteone.integration.jobs.product.service.ProductFeedCronJobService;
import com.siteone.integration.jobs.product.service.ProductFeedService;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.model.ModelService;

public class DefaultProductFeedCronJobService implements ProductFeedCronJobService{

	private ProductFeedService productFeedService;
	private ModelService modelService;
	private static final Logger LOG = Logger.getLogger(DefaultProductFeedCronJobService.class);
	
	@Override
	public void exportProductFeed(ProductFeedCronJobModel productFeedCronJobModel) {
		try {
			getProductFeedService().exportProductFeed();
		}
		catch (IOException ioException) {			
			productFeedCronJobModel.setResult(CronJobResult.FAILURE);
			productFeedCronJobModel.setStatus(CronJobStatus.ABORTED);
			LOG.error("error in export product feed service :: "+ioException);
		} finally {
			Date date = new Date();
			productFeedCronJobModel.setLastExecutionTime(date);
			getModelService().save(productFeedCronJobModel);
		}
		
	}
	@Override
	public void exportVariantProductFeed(ProductFeedCronJobModel productFeedCronJobModel) {
		try {
			getProductFeedService().exportVariantProductFeed();
		}
		catch (IOException ioException) {			
			productFeedCronJobModel.setResult(CronJobResult.FAILURE);
			productFeedCronJobModel.setStatus(CronJobStatus.ABORTED);
			LOG.error("error in export product feed service :: "+ioException);
		} finally {
			Date date = new Date();
			productFeedCronJobModel.setLastExecutionTime(date);
			getModelService().save(productFeedCronJobModel);
		}
		
	}

	@Override
	public void exportBaseProductFeed(ProductFeedCronJobModel productFeedCronJobModel) {
		try {
			getProductFeedService().exportBaseProductFeed();
		}
		catch (IOException ioException) {			
			productFeedCronJobModel.setResult(CronJobResult.FAILURE);
			productFeedCronJobModel.setStatus(CronJobStatus.ABORTED);
			LOG.error("error in export product feed service :: "+ioException);
		} finally {
			Date date = new Date();
			productFeedCronJobModel.setLastExecutionTime(date);
			getModelService().save(productFeedCronJobModel);
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
