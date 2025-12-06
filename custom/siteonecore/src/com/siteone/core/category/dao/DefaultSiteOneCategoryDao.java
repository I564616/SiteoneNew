/**
 *
 */
package com.siteone.core.category.dao;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.daos.impl.DefaultCategoryDao;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.util.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import com.siteone.core.model.GlobalProductNavigationNodeModel;


/**
 * @author Abdul Rahman Sheikh M
 *
 */
public class DefaultSiteOneCategoryDao extends DefaultCategoryDao implements SiteOneCategoryDao
{
	private static final String DEFAULT_CATEGORY_PRODUCT_COUNT_QUERY = "SELECT DISTINCT {P.PK} FROM {PRODUCT! AS P}, {CATEGORYPRODUCTRELATION AS CPR} WHERE {CPR.TARGET} = {P.PK} AND {CPR.SOURCE} IN "
			+ "({{SELECT {CT.PK} FROM {CATEGORY AS CT} WHERE {CT.CODE} = ?categoryCode AND {CT.CATALOGVERSION} = ?catalogVersion}} "
			+ "UNION {{SELECT {CCR.TARGET} FROM {CATEGORYCATEGORYRELATION AS CCR}, {CATEGORY AS CT} WHERE {CT.CODE} = ?categoryCode AND {CT.CATALOGVERSION} = ?catalogVersion AND {CT.PK} = {CCR.SOURCE}}} "
			+ "UNION {{SELECT {CCR1.TARGET} FROM {CATEGORYCATEGORYRELATION AS CCR}, {CATEGORY AS CT}, {CATEGORYCATEGORYRELATION AS CCR1} WHERE {CCR.TARGET} = {CCR1.SOURCE} AND {CT.CODE} = ?categoryCode AND {CT.CATALOGVERSION} = ?catalogVersion AND {CT.PK} = {CCR.SOURCE}}} "
			+ "UNION {{SELECT {CCR2.TARGET} FROM {CATEGORYCATEGORYRELATION AS CCR}, {CATEGORY AS CT}, {CATEGORYCATEGORYRELATION AS CCR1}, {CATEGORYCATEGORYRELATION AS CCR2} WHERE {CCR.TARGET} = {CCR1.SOURCE} AND {CCR1.TARGET} = {CCR2.SOURCE} AND {CT.CODE} = ?categoryCode AND {CT.CATALOGVERSION} = ?catalogVersion AND {CT.PK} = {CCR.SOURCE}}}) "
			+ "AND {P.PK} IN ({{SELECT {Base.PK} FROM {Product! as Base} WHERE {Base.PK} IN ({{ SELECT {Variant.baseProduct} AS basePK FROM {GenericVariantProduct as Variant} WHERE {Variant.isProductOffline} = '0' AND {Variant.isProductDiscontinued} = '0' }}) AND {Base.isProductOffline} = '0' AND {Base.isProductDiscontinued} = '0' }} "
			+ "UNION ({{ SELECT {PK} FROM {Product!} where {variantType} IS NULL AND {isProductOffline} = '0' AND {isProductDiscontinued} = '0' }}))";

	private static final String CATEGORYCODE = "categoryCode";
	private static final String CATALOGVERSION = "catalogVersion";
	private static final String CATALOGID = "siteoneProductCatalog";
	private static final String CATALOGVERSIONNAME = "Online";
	@Resource(name = "sessionService")
	private SessionService sessionService;
	private CatalogVersionService catalogVersionService;

	public CatalogVersionService getCatalogVersionService()
	{
		return catalogVersionService;
	}

	public void setCatalogVersionService(final CatalogVersionService catalogVersionService)
	{
		this.catalogVersionService = catalogVersionService;
	}

	@SuppressWarnings("boxing")
	@Override
	public Integer getProductCountForCategory(final String categoryCode, final CatalogVersionModel catalogVersion)
	{
		final String query = Config.getString("siteone.category.product.count.query", DEFAULT_CATEGORY_PRODUCT_COUNT_QUERY);
		final Map<String, Object> params = new HashMap<>();
		params.put(CATEGORYCODE, categoryCode);
		params.put(CATALOGVERSION, catalogVersion);

		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(query);
		fQuery.addQueryParameters(params);
		final SearchResult<ProductModel> result = getFlexibleSearchService().search(fQuery);

		return null != result ? result.getCount() : 0;
	}

	@SuppressWarnings("boxing")
	@Override
	public Integer getProductCountForCategoryNav(final String categoryCode, final CatalogVersionModel catalogVersion)
	{
		return getProductCountForCategory(categoryCode, catalogVersion);
	}

	@Override
	public List<ProductModel> getProductForCategoryCode(final String categoryCode)
	{
		final String query = Config.getString("siteone.category.product.count.query", DEFAULT_CATEGORY_PRODUCT_COUNT_QUERY);
		final Map<String, Object> params = new HashMap<>();
		params.put(CATEGORYCODE, categoryCode);
		params.put(CATALOGVERSION, getSessionService().getAttribute("currentCatalogVersion"));

		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(query);
		fQuery.addQueryParameters(params);
		final List<ProductModel> result = getFlexibleSearchService().<ProductModel> search(fQuery).getResult();
		return result;

	}

	@Override
	public List<GlobalProductNavigationNodeModel> getChildrenProductNavNodesForCategory(final String categoryCode)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(
				"SELECT {GPN.PK} FROM {GlobalProductNavigationNode AS GPN}, {Category AS CT}, {CATEGORYCATEGORYRELATION AS CCR} "
						+ "WHERE {GPN.CATEGORY} = {CCR.TARGET}  AND {CT.PK} = {CCR.SOURCE} AND {CT.CODE} = ?code ORDER BY {GPN.SEQUENCENUMBER}");
		query.addQueryParameter("code", categoryCode);
		final SearchResult<GlobalProductNavigationNodeModel> result = getFlexibleSearchService().search(query);
		return result.getResult();

	}


	@Override
	public GlobalProductNavigationNodeModel getProductNavNodesForCategory(final String categoryCode)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(
				"SELECT {GPN.PK} FROM {GlobalProductNavigationNode AS GPN}, {Category AS CT} "
						+ "WHERE {GPN.CATEGORY} = {CT.PK} AND {CT.CODE} = ?code ORDER BY {GPN.SEQUENCENUMBER}");
		query.addQueryParameter("code", categoryCode);
		final SearchResult<GlobalProductNavigationNodeModel> result = getFlexibleSearchService().search(query);
		return result.getCount() > 0 ? (GlobalProductNavigationNodeModel) result.getResult().iterator().next() : null;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.category.dao.SiteOneCategoryDao#getAllCategories(de.hybris.platform.catalog.model.
	 * CatalogVersionModel)
	 */

	@Override
	public List<CategoryModel> getAllCategories()
	{
		final String queryString = "SELECT {pk} FROM {Category!} WHERE {code} LIKE 'SH%'";
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(queryString);
		final SearchResult result = flexibleSearchService.search(fQuery);
		return result.getResult();
	}

	public SessionService getSessionService()
	{
		return sessionService;
	}

	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}

}