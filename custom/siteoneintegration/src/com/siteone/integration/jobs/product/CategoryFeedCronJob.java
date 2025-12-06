package com.siteone.integration.jobs.product;

import com.siteone.core.model.CategoryFeedCronJobModel;
import com.siteone.integration.jobs.product.service.CategoryFeedCronJobService;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;


public class CategoryFeedCronJob extends AbstractJobPerformable<CategoryFeedCronJobModel>{

	private CategoryFeedCronJobService categoryFeedCronJobService;

	@Override
	public PerformResult perform(CategoryFeedCronJobModel categoryFeedCronJobModel) {
		getCategoryFeedCronJobService().exportCategoryFeed(categoryFeedCronJobModel);
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}
	
	

	/**
	 * @return the productFeedCronJobService
	 */
	public CategoryFeedCronJobService getCategoryFeedCronJobService() {
		return categoryFeedCronJobService;
	}

	/**
	 * @param productFeedCronJobService the productFeedCronJobService to set
	 */
	public void setcategoryFeedCronJobService(CategoryFeedCronJobService categoryFeedCronJobService) {
		this.categoryFeedCronJobService = categoryFeedCronJobService;
	}

	
}	
	