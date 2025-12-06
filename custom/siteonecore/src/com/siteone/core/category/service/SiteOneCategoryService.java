/**
 *
 */
package com.siteone.core.category.service;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.CategoryService;
import de.hybris.platform.category.model.CategoryModel;

import java.util.List;

import com.siteone.core.model.GlobalProductNavigationNodeModel;


/**
 * @author Abdul Rahman Sheikh M
 *
 */
public interface SiteOneCategoryService extends CategoryService
{
	public Integer getProductCountForCategory(String categoryCode);

	List<GlobalProductNavigationNodeModel> getChildrenProductNavNodesForCategory(final String categoryCode);

	GlobalProductNavigationNodeModel getProductNavNodesForCategory(final String categoryCode);

	/**
	 * @param categoryCode
	 * @return
	 */
	public Integer getProductCountForCategoryNav(String categoryCode, CatalogVersionModel catalogVersion);

	public List<CategoryModel> getAllCategories();

}
