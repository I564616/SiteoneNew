/**
 *
 */
package com.siteone.core.product.dao.impl;


import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.search.exceptions.FlexibleSearchException;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.site.BaseSiteService;

import java.util.List;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;

import com.siteone.core.model.VerticalBarComponentModel;
import com.siteone.core.product.dao.SiteOneProductDao;



/**
 * @author 1091124
 *
 */
public class DefaultSiteOneProductDao implements SiteOneProductDao
{
	private static final Logger logger = Logger.getLogger(DefaultSiteOneProductDao.class);
	private static final String CATALOG_VERSION = "Online";
	private static final String CATALOG_US = "siteoneContentCatalog";
	private static final String CATALOG_CA = "siteoneCAContentCatalog";
	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Resource(name = "catalogVersionService")
	private CatalogVersionService catalogVersionService;

	@Override
	public List<ProductModel> findProductsBySearchTerm(final String searchTerm)
	{
		final String capSearchTerm = searchTerm != null ? searchTerm.toUpperCase() : "";

		final FlexibleSearchQuery query = new FlexibleSearchQuery("SELECT {PK} FROM {Product} WHERE ({code}=?searchTerm "
				+ "OR {itemNumber} LIKE ?capSearchTerm) AND {isProductDiscontinued} = 0");
		query.addQueryParameter("searchTerm", searchTerm);
		query.addQueryParameter("capSearchTerm", capSearchTerm);
		final SearchResult<ProductModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}

	@Override
	public List<ProductModel> findProductsByItemNumber(final String code)
	{
		final String capCode = code != null ? code.toUpperCase() : "";

		final FlexibleSearchQuery query = new FlexibleSearchQuery(
				"SELECT {PK} FROM {Product} WHERE {itemNumber} LIKE ?code AND {isProductDiscontinued} = 0 AND {catalogVersion} = ?catalogVersion");
		query.addQueryParameter("code", capCode);
		query.addQueryParameter("catalogVersion", getSessionService().getAttribute("currentCatalogVersion"));
		final SearchResult<ProductModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}

	@Override
	public List<ProductModel> findProductsByCodeandCatalog(final CatalogVersionModel catalogVersion, final String code)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(
				"SELECT {PK} FROM {Product} WHERE {code} = ?code AND {catalogVersion} = ?catalogVersion");
		query.addQueryParameter("code", code);
		query.addQueryParameter("catalogVersion", catalogVersion);
		logger.info("query product ca " + query);
		final SearchResult<ProductModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}

	@Override
	public List<ProductModel> findProductsByItemNumberForBulk(final String code)
	{
		final String capCode = code != null ? code.toUpperCase() : "";

		final FlexibleSearchQuery query = new FlexibleSearchQuery(
				"SELECT {PK} FROM {Product} WHERE ({code} LIKE ?code OR {itemNumber} LIKE ?capCode) AND {isProductDiscontinued} = 0 AND {catalogversion}=?catalogversion");
		query.addQueryParameter("code", code);
		query.addQueryParameter("capCode", capCode);
		query.addQueryParameter("catalogVersion", getCatalogVersionService().getCatalogVersion("siteoneProductCatalog", "Online"));
		final SearchResult<ProductModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}

	@Override
	public List<ProductModel> findProductsForQuickOrder(final String code)
	{
		final String capCode = code != null ? code.toUpperCase() : "";

		final FlexibleSearchQuery query = new FlexibleSearchQuery("SELECT {PK} FROM {Product} WHERE {code} LIKE ?code "
				+ "OR {itemNUmber} LIKE ?capCode AND {isProductDiscontinued} = 0");
		query.addQueryParameter("code", code);
		query.addQueryParameter("capCode", capCode);
		final SearchResult<ProductModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.product.dao.SiteOneProductDao#retrieveAllProducts(java.util.List)
	 */
	@Override
	public List<ProductModel> findAllProductsByCodes(final List<String> productcodeList)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery("select {pk} from {product} where  {code} IN (?productCodeList)");
		query.addQueryParameter("productCodeList", productcodeList);
		final SearchResult<ProductModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}

	/**
	 * @return List<ProductModel>
	 */
	@Override
	public List<ProductModel> findAllProducts()
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery("select {pk} from {product}");
		final SearchResult<ProductModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}

	/**
	 * @param productCodes
	 * @return List<ProductModel>
	 */
	@Override
	public List<ProductModel> getAllRecommProducts(final String productCodes)
	{
		final StringBuilder queryString = new StringBuilder();
		queryString.append("select {pk} from {product} where {code} IN (");
		queryString.append(prepareSKUsString(productCodes));
		queryString.append(")");
		final FlexibleSearchQuery searchQuery = new FlexibleSearchQuery(queryString.toString());
		final SearchResult<ProductModel> result = getFlexibleSearchService().search(searchQuery);
		return result.getResult();
	}

	/**
	 * @param productCode
	 * @return String
	 */
	@Override
	public String getProductPrice(final String productCode)
	{
		final String singleQuote = "'";
		try
		{
			final StringBuilder queryString = new StringBuilder();
			queryString.append("select {pk} from {pricerow} where {productid} = '");
			queryString.append(productCode);
			queryString.append(singleQuote);
			queryString.append(" order by {price} desc");
			final FlexibleSearchQuery searchQuery = new FlexibleSearchQuery(queryString.toString());
			final SearchResult<PriceRowModel> searchResult = getFlexibleSearchService().search(searchQuery);
			if (null != searchResult && searchResult.getCount() != 0)
			{
				final PriceRowModel priceRowModel = searchResult.getResult().get(0);
				return priceRowModel.getPrice().toString();

			}
		}
		catch (final FlexibleSearchException e)
		{
			logger.error("Unable to get product price for Product ID" + productCode, e);
		}
		return null;
	}

	/**
	 * @param categoriesCode
	 * @return List<CategoryModel>
	 */
	@Override
	public List<CategoryModel> getAllRecommCategories(final String categoriesCode)
	{
		final StringBuilder queryString = new StringBuilder();
		queryString.append("select {pk} from {Category} where {pimCategoryId} IN (");
		queryString.append(prepareSKUsString(categoriesCode));
		queryString.append(")");
		final FlexibleSearchQuery searchQuery = new FlexibleSearchQuery(queryString.toString());
		final SearchResult<CategoryModel> result = getFlexibleSearchService().search(searchQuery);
		return result.getResult();
	}

	@Override
	public List<ProductModel> getProductByItemNumberWithZero(final String itemNumber)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery("SELECT {PK} FROM {Product} "
				+ "WHERE substring({itemNumber}, patindex('%[^0]%', {itemNumber}), len({itemNumber})) = ?code "
				+ "AND {isProductDiscontinued} = 0 AND {catalogversion}=?catalogversion");
		query.addQueryParameter("code", itemNumber);
		query.addQueryParameter("catalogVersion", getCatalogVersionService().getCatalogVersion("siteoneProductCatalog", "Online"));
		final SearchResult<ProductModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}


	private String prepareSKUsString(final String skuIds)
	{
		final StringBuilder finalSKUIds = new StringBuilder();
		final String singleQuote = "'";
		final String comma = ",";
		if (skuIds != null && skuIds.contains(","))
		{
			final String[] splitCodes = skuIds.split(",");
			int count = 0;
			for (int i = 0; splitCodes.length > i; i++)
			{
				finalSKUIds.append(singleQuote + splitCodes[i] + singleQuote);
				count = count + 1;
				if (count != splitCodes.length)
				{
					finalSKUIds.append(comma);
				}
			}
		}
		else if (skuIds != null)
		{
			finalSKUIds.append(singleQuote + skuIds + singleQuote);
		}
		return finalSKUIds.toString();
	}

	@Override
	public VerticalBarComponentModel getVerticalBarComponent()
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery("SELECT {VBC.PK} FROM {VerticalBarComponent AS VBC} "
				+ "WHERE {VBC.catalogVersion} = ?catalogVersion AND {VBC.uid} = ?uid");
		final BaseSiteModel basesite = baseSiteService.getCurrentBaseSite();
		if (basesite.getUid().equals("siteone-us"))
		{
			query.addQueryParameter("catalogVersion", catalogVersionService.getCatalogVersion(CATALOG_US, CATALOG_VERSION));
		}
		if (basesite.getUid().equals("siteone-ca"))
		{
			query.addQueryParameter("catalogVersion", catalogVersionService.getCatalogVersion(CATALOG_CA, CATALOG_VERSION));
		}
		query.addQueryParameter("uid", "VerticalBarComponent");
		final SearchResult<VerticalBarComponentModel> result = getFlexibleSearchService().search(query);
		return result.getCount() > 0 ? (VerticalBarComponentModel) result.getResult().iterator().next() : null;

	}

	/**
	 * @return the flexibleSearchService
	 */
	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	/**
	 * @param flexibleSearchService
	 *           the flexibleSearchService to set
	 */
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}

	/**
	 * @return the sessionService
	 */
	public SessionService getSessionService()
	{
		return sessionService;
	}

	/**
	 * @param sessionService
	 *           the sessionService to set
	 */
	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}

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

}
