package com.siteone.integration.jobs.product.service.impl;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.siteone.core.model.NurseryInventoryFeedCronJobModel;
import com.siteone.integration.jobs.product.service.NurseryInventoryFeedCronJobService;
import com.siteone.integration.jobs.product.service.ProductFeedService;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.model.ModelService;

public class DefaultNurseryInventoryFeedCronJobService implements NurseryInventoryFeedCronJobService{

	private ProductFeedService productFeedService;
	private ModelService modelService;
	private static final Logger LOG = Logger.getLogger(DefaultNurseryInventoryFeedCronJobService.class);

	@Override
	public void exportNurseryInventoryFeedData(NurseryInventoryFeedCronJobModel nurseryInventoryFeedCronJobModel) {
		try {
			getProductFeedService().exportNurseryInventoryFeedData();
		}
		catch (IOException ioException) {			
			nurseryInventoryFeedCronJobModel.setResult(CronJobResult.FAILURE);
			nurseryInventoryFeedCronJobModel.setStatus(CronJobStatus.ABORTED);
			LOG.error("error in export product feed service :: "+ioException);
		} finally {
			Date date = new Date();
			nurseryInventoryFeedCronJobModel.setLastExecutionTime(date);
			getModelService().save(nurseryInventoryFeedCronJobModel);
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
