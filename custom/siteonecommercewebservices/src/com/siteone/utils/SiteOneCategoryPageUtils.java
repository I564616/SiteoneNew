package com.siteone.utils;

import de.hybris.platform.catalog.model.classification.ClassificationClassModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.variants.model.VariantCategoryModel;
import de.hybris.platform.variants.model.VariantValueCategoryModel;

import java.util.Collection;
import java.util.List;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;


/**
 * @author pelango
 *
 */
public class SiteOneCategoryPageUtils
{
	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	private static final String SITEONE_ROOT_CATEGORY = "primary.hierarchy.root.category";


	public boolean isPHCategory(final CategoryModel category)
	{
		return !(category instanceof ClassificationClassModel) && !(category instanceof VariantCategoryModel)
				&& !(category instanceof VariantValueCategoryModel) && (isPartOfPrimaryHierarchy(category.getAllSupercategories()));
	}



	public boolean isPartOfPrimaryHierarchy(final Collection<CategoryModel> supercategories)
	{
		boolean isPartOfPrimaryHierarchy = false;
		for (final CategoryModel superCategory : supercategories)
		{
			if (configurationService.getConfiguration().getString(SITEONE_ROOT_CATEGORY).equalsIgnoreCase(superCategory.getCode()))
			{
				isPartOfPrimaryHierarchy = true;
				break;
			}
		}
		return isPartOfPrimaryHierarchy;
	}

	/**
	 * @param category
	 * @return
	 */
	public boolean isCategoryBaseCategory(final CategoryModel category)
	{
		final List<CategoryModel> superCategories = category.getSupercategories();
		final String rootCategoryCode = configurationService.getConfiguration().getString(SITEONE_ROOT_CATEGORY);

		if (null != superCategories && CollectionUtils.isNotEmpty(superCategories))
		{
			for (final CategoryModel categoryModel : superCategories)
			{
				if (rootCategoryCode.equalsIgnoreCase(categoryModel.getCode()))
				{
					return true;
				}

			}
		}
		return false;
	}

	public boolean isCMSCategory(final CategoryModel category)
	{
		boolean status = false;

		if (isCategoryBaseCategory(category))
		{
			status = true;
		}
		else
		{
			final List<CategoryModel> superCategories = category.getSupercategories();
			status = superCategories.stream().anyMatch(cat -> isCategoryBaseCategory(cat));
		}

		return status;
	}


}