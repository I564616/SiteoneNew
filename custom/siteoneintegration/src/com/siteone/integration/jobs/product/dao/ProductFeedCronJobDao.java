package com.siteone.integration.jobs.product.dao;

import java.util.List;


import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.model.media.MediaContainerModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.servicelayer.search.SearchResult;


public interface ProductFeedCronJobDao {

	public List<ProductModel> getProducts(CatalogVersionModel catalogVersionModel);
	public List<ProductModel> getProductsForGoogleFeed(CatalogVersionModel catalogVersionModel);
	public String getPriceForProduct(String productCode);
	public List<List<Object>> getInventoryData(CatalogVersionModel catalogVersionModel);
	public List<List<Object>> getInventoryDataForGoogleFeed(CatalogVersionModel catalogVersionModel, String productCode);
	public List<PointOfServiceModel> getRegions();
	public List<CategoryModel> getCategory(CatalogVersionModel catalogVersionModel);
	public String getCategoryName(String categoryCode);
	public List<List<Object>> getInventoryDataForNurseryFeed(CatalogVersionModel catalogVersionModel, String store);
	public List<ProductModel> getDataForFullProductFeed(CatalogVersionModel catalogVersion);
	public List<String> getProductCodesForInventoryFeed(CatalogVersionModel catalogVersion);
	public List<ProductModel> getVariantProductsForGoogleFeed(CatalogVersionModel catalogVersionModel);
	public List<ProductModel> getBaseProductsForGoogleFeed(CatalogVersionModel catalogVersionModel);
	public List<String> getvariantProductCodesForInventoryFeed(CatalogVersionModel catalogVersion);
	public List<List<Object>> getInventoryForDcBranch(CatalogVersionModel catalogVersionModel);
	List<ProductModel> findAllProductPk();
	List<MediaContainerModel> findProductMedia();
	List<MediaModel> findMediaWithNoContainer();
	List<MediaContainerModel> findContainerWithNoMedia();
	public List<String> getLOBAttributes(String code);
}
