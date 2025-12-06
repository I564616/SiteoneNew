/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.catalog.model.classification.ClassificationClassModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.impl.CategoryPathValueProvider;
import de.hybris.platform.servicelayer.config.ConfigurationService;

import java.util.List;
import java.util.Set;

import jakarta.annotation.Resource;


/**
 * @author 1124932
 *
 */
public class SiteOneCategoryPathValueProvider extends CategoryPathValueProvider
{

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	private static final String SALES_SITEONE_ROOT_CATEGORY = "sales.hierarchy.root.category";
	private static final String DISPLAY_SITEONE_ROOT_CATEGORY = "display.hierarchy.root.category";

	@Override
	protected void accumulateCategoryPaths(final List<CategoryModel> categoryPath, final Set<String> output)
	{
		final String salesRootCategoryCode = configurationService.getConfiguration().getString(SALES_SITEONE_ROOT_CATEGORY);
		final String displayRootCategoryCode = configurationService.getConfiguration().getString(DISPLAY_SITEONE_ROOT_CATEGORY);
		final StringBuilder accumulator = new StringBuilder();
		for (final CategoryModel category : categoryPath)
		{
			if (category instanceof ClassificationClassModel)
			{
				break;
			}
			if (!(category.getCode().startsWith(displayRootCategoryCode)
					&& !salesRootCategoryCode.equalsIgnoreCase(category.getCode())))
			{
				accumulator.append('/').append(category.getCode());
				output.add(accumulator.toString());
			}
		}
	}
}
