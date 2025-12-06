/**
 *
 */
package com.siteone.core.category.source;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.impl.DefaultCategorySource;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;



/**
 * @author 1124932
 *
 */
public class SiteOneCategorySource extends DefaultCategorySource
{
	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	private static final String SALES_SITEONE_ROOT_CATEGORY = "sales.hierarchy.root.category";

	@Override
	public Collection<CategoryModel> getCategoriesForConfigAndProperty(final IndexConfig indexConfig,
			final IndexedProperty indexedProperty, final Object model)
	{
		final String salesRootCategoryCode = configurationService.getConfiguration().getString(SALES_SITEONE_ROOT_CATEGORY);
		final Set<ProductModel> products = getProducts(model);
		final Set<CategoryModel> directCategories = getDirectSuperCategories(products);

		if (directCategories != null && !directCategories.isEmpty())
		{
			// Lookup the root categories - null if no root categories
			//	final Set<CategoryModel> rootCategories = lookupRootCategories(indexConfig.getCatalogVersions());
			final Collection<CatalogVersionModel> catalogVersions = Collections
					.singletonList(((ProductModel) model).getCatalogVersion());
			final Set<CategoryModel> rootCategories = lookupRootCategories(catalogVersions);

			final Set<CategoryModel> allCategories = new HashSet<CategoryModel>();
			for (final CategoryModel category : directCategories)
			{
				//final Collection<CategoryModel> subCategoryList = category.getAllSubcategories();

				if (category.getCode().startsWith(salesRootCategoryCode))
				{
					allCategories.addAll(getAllCategories(category, rootCategories));
				}

			}
			return allCategories;
		}
		else
		{
			return Collections.emptyList();
		}
	}

	public Collection<CategoryModel> getCategoriesForConfigAndPropertyForFacet(final IndexConfig indexConfig,
			final IndexedProperty indexedProperty, final Object model)
	{
		final String salesRootCategoryCode = configurationService.getConfiguration().getString(SALES_SITEONE_ROOT_CATEGORY);
		final Set<ProductModel> products = getProducts(model);
		final Set<CategoryModel> directCategories = getDirectSuperCategories(products);

		if (directCategories != null && !directCategories.isEmpty())
		{
			// Lookup the root categories - null if no root categories
			//	final Set<CategoryModel> rootCategories = lookupRootCategories(indexConfig.getCatalogVersions());
			final Collection<CatalogVersionModel> catalogVersions = Collections
					.singletonList(((ProductModel) model).getCatalogVersion());
			final Set<CategoryModel> rootCategories = lookupRootCategories(catalogVersions);

			final Set<CategoryModel> allCategories = new HashSet<CategoryModel>();
			for (final CategoryModel category : directCategories)
			{
				if (category.getCode().startsWith(salesRootCategoryCode))
				{
					final Collection<CategoryModel> superCategories = category.getAllSupercategories();
					for (final CategoryModel categoryModel : superCategories)
					{
						if (isCMSCategory(categoryModel))
						{
							allCategories.addAll(getAllCategories(categoryModel, rootCategories));
						}
					}
				}
			}
			return allCategories;
		}
		else
		{
			return Collections.emptyList();
		}
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

	public boolean isCategoryBaseCategory(final CategoryModel category)
	{
		final List<CategoryModel> superCategories = category.getSupercategories();
		final String rootCategoryCode = configurationService.getConfiguration().getString(SALES_SITEONE_ROOT_CATEGORY);
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

}
