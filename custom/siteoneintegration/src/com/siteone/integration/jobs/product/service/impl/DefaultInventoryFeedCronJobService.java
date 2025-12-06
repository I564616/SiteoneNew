package com.siteone.integration.jobs.product.service.impl;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.siteone.core.model.InventoryFeedCronJobModel;
import com.siteone.integration.jobs.product.service.InventoryFeedCronJobService;
import com.siteone.integration.jobs.product.service.ProductFeedService;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.model.ModelService;

public class DefaultInventoryFeedCronJobService implements InventoryFeedCronJobService{

	private ProductFeedService productFeedService;
	private ModelService modelService;
	private static final Logger LOG = Logger.getLogger(DefaultInventoryFeedCronJobService.class);

	@Override
	public void exportInventoryFeed(InventoryFeedCronJobModel inventoryFeedCronJobModel) {
		try {
			getProductFeedService().exportInventoryFeed();
		}
		catch (IOException ioException) {			
			inventoryFeedCronJobModel.setResult(CronJobResult.FAILURE);
			inventoryFeedCronJobModel.setStatus(CronJobStatus.ABORTED);
			LOG.error("error in export product feed service :: "+ioException);
		} finally {
			Date date = new Date();
			inventoryFeedCronJobModel.setLastExecutionTime(date);
			getModelService().save(inventoryFeedCronJobModel);
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
