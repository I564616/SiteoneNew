/**
 *
 */
package com.siteone.core.category.service.impl;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.impl.DefaultCategoryService;
import de.hybris.platform.category.model.CategoryModel;

import java.util.List;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;

import com.siteone.core.category.dao.SiteOneCategoryDao;
import com.siteone.core.category.service.SiteOneCategoryService;
import com.siteone.core.model.GlobalProductNavigationNodeModel;


/**
 * @author Abdul Rahman Sheikh M
 *
 */
public class DefaultSiteOneCategoryService extends DefaultCategoryService implements SiteOneCategoryService
{
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneCategoryService.class);
	private static final String CATALOG_ID = "siteoneProductCatalog";
	private static final String VERSION_ONLINE = "Online";
	private SiteOneCategoryDao siteOneCategoryDao;

	@Resource(name = "categoryService")
	private transient SiteOneCategoryService categoryService;
	@Resource(name = "catalogVersionService")
	private CatalogVersionService catalogVersionService;

	@Override
	public Integer getProductCountForCategory(final String categoryCode)
	{
		final CatalogVersionModel catalogVersionModel = getSessionService().getAttribute("currentCatalogVersion");
		final CategoryModel categoryModel = categoryService.getCategoryForCode(catalogVersionModel, categoryCode);
		int result = 0;

		if (categoryModel.getProductCount() != null)
		{
			result = categoryModel.getProductCount();
		}

		return result;
	}

	@Override
	public Integer getProductCountForCategoryNav(final String categoryCode, final CatalogVersionModel catalogVersion)
	{
		return siteOneCategoryDao.getProductCountForCategoryNav(categoryCode, catalogVersion);

	}

	/**
	 * @return the siteOneCategoryDao
	 */
	public SiteOneCategoryDao getSiteOneCategoryDao()
	{
		return siteOneCategoryDao;
	}

	/**
	 * @param siteOneCategoryDao
	 *           the siteOneCategoryDao to set
	 */
	public void setSiteOneCategoryDao(final SiteOneCategoryDao siteOneCategoryDao)
	{
		this.siteOneCategoryDao = siteOneCategoryDao;
	}

	public List<GlobalProductNavigationNodeModel> getChildrenProductNavNodesForCategory(final String categoryCode)
	{

		return siteOneCategoryDao.getChildrenProductNavNodesForCategory(categoryCode);
	}

	public GlobalProductNavigationNodeModel getProductNavNodesForCategory(final String categoryCode)
	{

		return siteOneCategoryDao.getProductNavNodesForCategory(categoryCode);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.category.service.SiteOneCategoryService#getAllCategories(de.hybris.platform.catalog.model.
	 * CatalogVersionModel)
	 */
	@Override
	public List<CategoryModel> getAllCategories()
	{
		// YTODO Auto-generated method stub
		return siteOneCategoryDao.getAllCategories();
	}
}
