package com.siteone.integration.jobs.product.service;

import java.io.IOException;

import com.siteone.core.model.SalesFeedCronJobModel;
import jakarta.validation.ValidationException;


public interface SalesFeedCronJobService {
	
	void generateSalesDataImport(SalesFeedCronJobModel salesFeedCronJobModel) throws IOException, ValidationException;

}
