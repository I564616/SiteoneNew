/**
 *
 */
package com.siteone.core.jobs.customer;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import com.siteone.core.category.service.SiteOneCategoryService;
import com.siteone.core.model.CategoryProductCountCronJobModel;


/**
 * @author BS
 *
 */
public class CategoryProductCountCronJob extends AbstractJobPerformable<CategoryProductCountCronJobModel>
{
	private static final Logger LOG = Logger.getLogger(CategoryProductCountCronJob.class);

	private SiteOneCategoryService siteOneCategoryService;

	private ModelService modelService;

	private CatalogVersionService catalogVersionService;

	/**
	 * Gets product count and sets to {@link CategoryModel}
	 *
	 * @param categoryProductCountCronJobModel
	 */
	@SuppressWarnings("boxing")
	@Override
	public PerformResult perform(final CategoryProductCountCronJobModel categoryProductCountCronJobModel)
	{
		try
		{
			final List<CategoryModel> categories = getSiteOneCategoryService().getAllCategories();
			if (CollectionUtils.isNotEmpty(categories))
			{
				for (final CategoryModel categoryModel : categories)
				{
					LOG.info("CategoryProductCountCronJob :: starttime - " + System.currentTimeMillis());
					if (categoryModel.getCode().matches("(?i)^(?!sh18).*"))
					{
						final Integer productCount = getSiteOneCategoryService().getProductCountForCategoryNav(categoryModel.getCode(),
								categoryModel.getCatalogVersion());
						LOG.info("CategoryProductCountCronJob :: code - " + categoryModel.getCode() + " count - " + productCount
								+ " endtime - " + System.currentTimeMillis());
						categoryModel.setProductCount(productCount);
						getModelService().save(categoryModel);
					}
				}
			}

			LOG.info("CategoryProductCountCronJob executed successfully!");
		}
		catch (final Exception exception)
		{
			LOG.error("Exception occured in setting product count in category", exception);
			categoryProductCountCronJobModel.setResult(CronJobResult.FAILURE);
			categoryProductCountCronJobModel.setStatus(CronJobStatus.ABORTED);
		}
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}


	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	@Override
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	/**
	 * @return the catalogVersionService
	 */
	public CatalogVersionService getCatalogVersionService()
	{
		return catalogVersionService;
	}

	/**
	 * @param catalogVersionService
	 *           the catalogVersionService to set
	 */
	public void setCatalogVersionService(final CatalogVersionService catalogVersionService)
	{
		this.catalogVersionService = catalogVersionService;
	}


	/**
	 * @return the siteOneCategoryService
	 */
	public SiteOneCategoryService getSiteOneCategoryService()
	{
		return siteOneCategoryService;
	}


	/**
	 * @param siteOneCategoryService
	 *           the siteOneCategoryService to set
	 */
	public void setSiteOneCategoryService(final SiteOneCategoryService siteOneCategoryService)
	{
		this.siteOneCategoryService = siteOneCategoryService;
	}

}

