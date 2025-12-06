package com.siteone.integration.jobs.product.service;

import com.siteone.core.model.ProductFeedCronJobModel;

public interface ProductFeedCronJobService {
  
	public void exportProductFeed(ProductFeedCronJobModel productFeedCronJobModel);

	public void exportVariantProductFeed(ProductFeedCronJobModel productFeedCronJobModel);

	public void exportBaseProductFeed(ProductFeedCronJobModel productFeedCronJobModel);
	
}
