package com.siteone.integration.jobs.product.dao.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import com.siteone.integration.jobs.product.dao.ProductFeedCronJobDao;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.PK;
import de.hybris.platform.core.model.media.MediaContainerModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.search.exceptions.FlexibleSearchException;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

public class DefaultProductFeedCronJobDao extends AbstractItemDao implements ProductFeedCronJobDao {

	private static final Logger logger = Logger.getLogger(DefaultProductFeedCronJobDao.class);
	private static final String CATALOG_VERSION = "catalogVersion";
	
	
	
	@Override
	public List<ProductModel> findAllProductPk()
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery("select {pk} from {product} where {galleryImages} !='' ");
		final SearchResult<ProductModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	
	}
	@Override
	public List<MediaContainerModel> findProductMedia()
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery( "SELECT {pk} FROM {MediaContainer as mc},{Catalog as c}, {CatalogVersion as cv} WHERE    {mc.catalogversion} = {cv.pk} AND  {cv.catalog}    = {c.pk} AND  {c.id}  = 'siteoneProductCatalog' ");
		final SearchResult<MediaContainerModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}
    
	
	@Override
	public List<ProductModel> getProducts(CatalogVersionModel catalogVersionModel) {
		String queryString="select {pk} from {product} where {catalogversion}=?catalogVersion  and ({isProductOffline})=0 and ({isProductDiscontinued}) =0";
		return getProductData(catalogVersionModel,queryString);
	}
	
	@Override
	public List<ProductModel> getProductsForGoogleFeed(CatalogVersionModel catalogVersionModel) {
		String queryString="select {pk} from {product} where {catalogversion}=?catalogVersion  and {isProductOffline}=0  and {isProductDiscontinued} =0 and ({secretsku}=0 or {secretsku} is null) and {productkind}='Simple'";
		return getProductData(catalogVersionModel,queryString);
	}
	
	@Override
	public List<ProductModel> getVariantProductsForGoogleFeed(CatalogVersionModel catalogVersionModel) {
		String queryString="select {pk} from {product} where {catalogversion}=?catalogVersion  and {isProductOffline}=0  and {isProductDiscontinued} =0 and ({secretsku}=0 or {secretsku} is null) and {productkind}='Variant'";
		return getProductData(catalogVersionModel,queryString);
	}
	
	@Override
	public List<ProductModel> getBaseProductsForGoogleFeed(CatalogVersionModel catalogVersionModel) {
		String queryString="select {pk} from {product} where {catalogversion}=?catalogVersion  and {isProductOffline}=0  and {isProductDiscontinued} =0 and ({secretsku}=0 or {secretsku} is null) and {productkind}='Base'";
		return getProductData(catalogVersionModel,queryString);
	}
	
	@Override
	public List<ProductModel> getDataForFullProductFeed(CatalogVersionModel catalogVersionModel) {
		String queryString="select {pk} from {product} where {catalogversion}=?catalogVersion  and {isProductOffline}=0  and {isProductDiscontinued} =0 " ;
		return getProductData(catalogVersionModel,queryString);
	}
	
	@Override
	public List<String> getProductCodesForInventoryFeed(CatalogVersionModel catalogVersion) {
		String queryString="select {code} from {product} where {catalogversion}=?catalogVersion  and {isProductOffline}=0  and {isProductDiscontinued} =0 "
				+ " and ({secretsku}=0 or {secretsku} is null) and {productkind}='Simple' order by CAST({code} as int)" ;
		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter(CATALOG_VERSION, catalogVersion);
		query.setResultClassList(Arrays.asList(String.class));
		final SearchResult<String> result = this.getFlexibleSearchService().search(query);
		if (null != result.getResult())
		{
			return result.getResult();
		}		
		return Collections.emptyList();
	}
	
	@Override
	public List<String> getvariantProductCodesForInventoryFeed(CatalogVersionModel catalogVersion) {
		String queryString="select {code} from {product} where {catalogversion}=?catalogVersion  and {isProductOffline}=0  and {isProductDiscontinued} =0 "
				+ " and ({secretsku}=0 or {secretsku} is null) and {productkind}='Variant' order by CAST({code} as int)" ;
		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter(CATALOG_VERSION, catalogVersion);
		query.setResultClassList(Arrays.asList(String.class));
		final SearchResult<String> result = this.getFlexibleSearchService().search(query);
		if (null != result.getResult())
		{
			return result.getResult();
		}		
		return Collections.emptyList();
	}
	
	@Override
	public String getCategoryName(String categoryCode) {
		String queryString="SELECT {c.name} FROM {Category as c}, {CatalogVersion as cv}, {Catalog as cat} WHERE {cv.catalog}={cat.PK} AND {cat:id}='siteoneProductCatalog' AND {cv.version}='Online' AND {c.catalogVersion}='8796093153881' and {c.code}=?categoryCode";
		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.setResultClassList(
				Arrays.asList(String.class));
		query.addQueryParameter("categoryCode", categoryCode);
		final SearchResult<String> result = this.getFlexibleSearchService().search(query);
		if (null != result.getResult() && CollectionUtils.isNotEmpty(result.getResult()))
		{
			return result.getResult().get(0);
		}		
			return null;		
	}
	
	List<ProductModel> getProductData(CatalogVersionModel catalogVersionModel,String queryString)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter(CATALOG_VERSION, catalogVersionModel);
		return getFlexibleSearchService().<ProductModel> search(query).getResult();
	}

	@Override
	public String getPriceForProduct(String productCode) {
		try
		{
		FlexibleSearchQuery query = new FlexibleSearchQuery("select {pk} from {pricerow as p JOIN pointofservice as po on {p.pointofservice}={po.pk}} where {p.productid}=?productCode and {po.storeid}='172'");
		query.addQueryParameter("productCode", productCode);
		SearchResult<PriceRowModel> searchResult = getFlexibleSearchService().search(query);
		if(null != searchResult && searchResult.getCount() != 0)
		{
			final PriceRowModel priceRowModel = searchResult.getResult().get(0);
			return priceRowModel.getPrice().toString();
		}
		else
		{
			query = new FlexibleSearchQuery("select {pk} from {pricerow} where {productid}=?productCode ORDER BY {price} DESC");
			query.addQueryParameter("productCode", productCode);
			searchResult = getFlexibleSearchService().search(query);
			if(null!=searchResult && searchResult.getCount()!=0)
			{
				final PriceRowModel priceRowModel = searchResult.getResult().get(0);
				return priceRowModel.getPrice().toString();
			}
		}
		}
		catch(final FlexibleSearchException e)
		{
			logger.error("ProductFeed Cronjob - Unable to get product price for product ID"+productCode,e);
		}
		return null;
	}

	@Override
	public List<List<Object>> getInventoryData(CatalogVersionModel catalogVersionModel) {
		String queryString="select {ps.storeid}, {p.code}, {pr.price}, {sl.available} from {product as p}, {pointofservice as ps LEFT JOIN pricerow as pr on {pr.pointofservice}={ps.pk}}, {warehouse as wh LEFT JOIN stocklevel as sl on {sl.warehouse}={wh.pk}} where {ps.storeid}={wh.code} and {pr.productid}={p.code} and {sl.productcode}={p.code} and {p.catalogversion}=?catalogVersion  and {p.isProductOffline}=0 and {p.isProductDiscontinued}=0 and {ps.isActive}=1 order by CAST({ps.storeid} as int), CAST({p.code} as int)";
		return getInventory(catalogVersionModel,queryString,null);
	}
	
	@Override
	public List<List<Object>> getInventoryDataForGoogleFeed(CatalogVersionModel catalogVersionModel, String productCode) {
		String queryString = "select {ps.storeid}, {p.code}, {pr.price}, {sl.available} from {product as p}, {pointofservice as ps LEFT JOIN pricerow as pr on {pr.pointofservice}={ps.pk}}, {warehouse as wh LEFT JOIN stocklevel as sl on {sl.warehouse}={wh.pk}} where {ps.storeid}={wh.code} and {pr.productid}={p.code} and {sl.productcode}={p.code} and {p.catalogversion}=?catalogVersion and {ps.isActive}=1 and {p.code}=?productCode order by CAST({ps.storeid} as int)";
		return getInventory(catalogVersionModel,queryString,productCode);		
	}
	@Override
	public List<List<Object>> getInventoryForDcBranch(CatalogVersionModel catalogVersionModel)
	{
		String queryString = "select {ps.storeid}, {p.code}, {sl.available} from {product as p}, {warehouse as wh LEFT JOIN stocklevel as sl on {sl.warehouse}={wh.pk} join pointofservice as ps on {ps.storeid}={wh.code} } where {sl.productcode}={p.code} and {p.catalogversion}=?catalogVersion and {ps.isActive}=1 and {ps.isdcbranch}=1 ";
		return getInventoryDc(catalogVersionModel,queryString);
	}
		
	 List<List<Object>> getInventoryDc(CatalogVersionModel catalogVersionModel, String queryString)
	{
		try
		{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.setResultClassList(Arrays.asList(String.class,String.class,String.class));
		query.addQueryParameter(CATALOG_VERSION, catalogVersionModel);
		SearchResult<List<Object>> searchResult = getFlexibleSearchService().search(query);
		if(null!=searchResult && searchResult.getCount()!=0)
		{
			return searchResult.getResult();
		}
		}
		catch(final FlexibleSearchException e)
		{
			logger.error("InventoryFeed DC Cronjob - Unable to get DC inventory data",e);
		}
		return Collections.emptyList();
	}
	List<List<Object>> getInventory(CatalogVersionModel catalogVersionModel,String queryString,String productCode)
	{
		try
		{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.setResultClassList(Arrays.asList(String.class,String.class,Double.class,String.class));
		query.addQueryParameter(CATALOG_VERSION, catalogVersionModel);
		if(productCode!=null) {
			query.addQueryParameter("productCode", productCode);
		}
		SearchResult<List<Object>> searchResult = getFlexibleSearchService().search(query);
		if(null!=searchResult && searchResult.getCount()!=0)
		{
			return searchResult.getResult();
		}
		}
		catch(final FlexibleSearchException e)
		{
			logger.error("InventoryFeed Cronjob - Unable to get inventory data",e);
		}
		return Collections.emptyList();
	}
	
	@Override
	public List<PointOfServiceModel> getRegions() {
		String queryString="select {pk} from {pointofservice as ps} where {ps.isActive}=1 order by CAST({ps.storeid} as int)";
		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		return getFlexibleSearchService().<PointOfServiceModel> search(query).getResult();
	}
	
	@Override
	public List<CategoryModel> getCategory(CatalogVersionModel catalogVersionModel) {
	    String queryString="select {pk} from {category} where {pimCategoryId} is not null and {catalogversion} =?catalogVersion";
		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter(CATALOG_VERSION, catalogVersionModel);
		return getFlexibleSearchService().<CategoryModel> search(query).getResult();
	}
	
    @Override
	public List<List<Object>> getInventoryDataForNurseryFeed(CatalogVersionModel catalogVersionModel, String store) {
		String queryString = "select  {p.itemnumber} , {p.productshortdesc}, {sl.available}, {p.pk} from {product as p},{pointofservice as ps} ,{warehouse as wh LEFT JOIN stocklevel as sl on {sl.warehouse}={wh.pk}} where {ps.storeid}={wh.code}  and {sl.productcode}={p.code} and {wh.code}=?code and{p.catalogversion}=?catalogversion and {p.producttype}='Nursery' and {p.isProductOffline}=0 and {p.isProductDiscontinued}=0 and {ps.isActive}=1 and {sl.available}>0 order by {p.productshortdesc}";
		try
		{
			final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
			query.setResultClassList(Arrays.asList(String.class,String.class,String.class,ProductModel.class));
			query.addQueryParameter("code", store);
			query.addQueryParameter(CATALOG_VERSION, catalogVersionModel);
			SearchResult<List<Object>> searchResult = getFlexibleSearchService().search(query);
			if(null!=searchResult && searchResult.getCount()!=0)
			{
				return searchResult.getResult();
			}
		}
		catch(final FlexibleSearchException e)
		{
			logger.error("NurseryInventoryFeed Cronjob - Unable to get inventory data",e);
		}
		return Collections.emptyList();
	}
	@Override
	public List<MediaModel> findMediaWithNoContainer() {
		final FlexibleSearchQuery query = new FlexibleSearchQuery("SELECT distinct {m.pk} FROM {Media as m},{catalogversion as cv},{catalog as c},{mediacontainer as mc},{mediafolder as mf} WHERE {m.mime} = 'image/jpeg' AND {m.folder}={mf.pk} AND {mf.qualifier}='PimProductImages' AND {m.mediacontainer} IS NULL AND {m.catalogversion} = {cv.pk} AND {cv.catalog} = {c.pk} AND {c.id} = 'siteoneProductCatalog' AND {mc.catalogversion} = {cv.pk}");
		final SearchResult<MediaModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}
	
	@Override
	public List<MediaContainerModel> findContainerWithNoMedia() {
		final FlexibleSearchQuery query = new FlexibleSearchQuery("SELECT   {mc.pk} FROM {MediaContainer as mc},{Catalog as c}, {CatalogVersion as cv} WHERE    {mc.catalogversion} = {cv.pk} AND  {cv.catalog}    = {c.pk} AND  {c.id}  = 'siteoneProductCatalog' AND  NOT EXISTS ({{ SELECT 1 FROM {Media as m} WHERE {m.mediacontainer}= {mc.pk} AND  {m.catalogversion}  = {cv.pk} AND  {m.catalog} = {c.pk} }})");
		final SearchResult<MediaContainerModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}
	
	@Override
	public List<String> getLOBAttributes(String code) {
		String queryString="select distinct {code} from {ClassificationAttribute} where {code} like ?code" ;
		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("code", code);
		query.setResultClassList(Arrays.asList(String.class));
		final SearchResult<String> result = this.getFlexibleSearchService().search(query);
		if (null != result.getResult())
		{
			return result.getResult();
		}		
		return Collections.emptyList();
	}
	
	
}
