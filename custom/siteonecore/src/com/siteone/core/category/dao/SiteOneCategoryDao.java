/**
 *
 */
package com.siteone.core.category.dao;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.daos.CategoryDao;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.model.product.ProductModel;

import java.util.List;

import com.siteone.core.model.GlobalProductNavigationNodeModel;


/**
 * @author Abdul Rahman Sheikh M
 *
 */
public interface SiteOneCategoryDao extends CategoryDao
{
	public Integer getProductCountForCategory(String categoryCode, CatalogVersionModel catalogVersion);

	public Integer getProductCountForCategoryNav(String categoryCode, CatalogVersionModel catalogVersion);

	public List<ProductModel> getProductForCategoryCode(final String categoryCode);

	List<GlobalProductNavigationNodeModel> getChildrenProductNavNodesForCategory(final String categoryCode);

	GlobalProductNavigationNodeModel getProductNavNodesForCategory(final String categoryCode);

	public List<CategoryModel> getAllCategories();

	/**
	 * @return
	 */
}
