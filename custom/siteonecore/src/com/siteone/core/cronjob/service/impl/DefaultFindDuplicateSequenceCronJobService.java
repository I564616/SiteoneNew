/**
 *
 */
package com.siteone.core.cronjob.service.impl;

import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.variants.model.VariantValueCategoryModel;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.siteone.core.cronjob.dao.FindDuplicateSequenceCronJobDao;
import com.siteone.core.cronjob.service.FindDuplicateSequenceCronJobService;
import com.siteone.core.model.FindDuplicateSequenceCronJobModel;


/**
 * @author LR03818
 *
 */
public class DefaultFindDuplicateSequenceCronJobService implements FindDuplicateSequenceCronJobService
{
	private static final Logger LOG = Logger.getLogger(DefaultFindDuplicateSequenceCronJobService.class);

	private FindDuplicateSequenceCronJobDao findDuplicateSequenceCronJobDao;
	private ModelService modelService;

	@Override
	public void findAndFixDuplicateSequence(final FindDuplicateSequenceCronJobModel model)
	{
		final List<List<Object>> variantCategories = findDuplicateSequenceCronJobDao.getVariantCategoriesWithDublicateSequence();
		if (!variantCategories.isEmpty())
		{
			for (final List<Object> variantCategory : variantCategories)
			{
				final String variantCategoryCode = (String) variantCategory.get(0);
				final String sequence = (String) variantCategory.get(1);
				LOG.info("variantCategoryCode:::" + variantCategoryCode + "::sequence::" + sequence);
				final List<VariantValueCategoryModel> variantValueCategories = findDuplicateSequenceCronJobDao
						.getVariantValueCategoryBySequence(variantCategoryCode, sequence);
				if (!variantValueCategories.isEmpty())
				{
					int maxSequence = Integer
							.parseInt(findDuplicateSequenceCronJobDao.getMaxSequenceByVariantCategory(variantCategoryCode));
					for (final VariantValueCategoryModel variantValueCategory : variantValueCategories)
					{
						maxSequence++;
						variantValueCategory.setSequence(Integer.valueOf(maxSequence));
						getModelService().save(variantValueCategory);
					}
				}
			}
		}
		final Date date = new Date();
		model.setLastExecutionTime(date);
		getModelService().save(model);
	}

	/**
	 * @return the findDuplicateSequenceCronJobDao
	 */
	public FindDuplicateSequenceCronJobDao getFindDuplicateSequenceCronJobDao()
	{
		return findDuplicateSequenceCronJobDao;
	}

	/**
	 * @param findDuplicateSequenceCronJobDao
	 *           the findDuplicateSequenceCronJobDao to set
	 */
	public void setFindDuplicateSequenceCronJobDao(final FindDuplicateSequenceCronJobDao findDuplicateSequenceCronJobDao)
	{
		this.findDuplicateSequenceCronJobDao = findDuplicateSequenceCronJobDao;
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
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

}
