/**
 *
 */
package com.siteone.core.cronjob.dao;

import de.hybris.platform.variants.model.VariantValueCategoryModel;

import java.util.List;


/**
 * @author LR03818
 *
 */
public interface FindDuplicateSequenceCronJobDao
{
	List<List<Object>> getVariantCategoriesWithDublicateSequence();

	List<VariantValueCategoryModel> getVariantValueCategoryBySequence(String variantCategoryCode, String sequence);

	String getMaxSequenceByVariantCategory(String variantCategoryCode);
}
