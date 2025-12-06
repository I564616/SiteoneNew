/**
 *
 */
package com.siteone.core.services;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.variants.model.VariantProductModel;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.siteone.core.model.VerticalBarComponentModel;
import com.siteone.integration.algonomy.data.AlgonomyRequestData;
import com.siteone.integration.algonomy.data.AlgonomyResponseData;


/**
 * @author 1091124
 *
 */
public interface SiteOneProductService
{

	ProductModel getProductBySearchTermForSearch(final String searchTerm);

	List<ProductModel> getProductsForQuickOrder(String code);

	/**
	 * @param productcodeList
	 * @return List<ProductModel>
	 */
	List<ProductModel> getAllProductsByCodes(List<String> productcodeList);

	/**
	 * Updates the parcel shipping for list of products
	 *
	 * @param productcodeList
	 * @param storeId
	 */
	void updateParcelShippingForProducts(final List<ProductData> productcodeList, final String storeId);

	/**
	 * Updates the parcel shipping for single product
	 *
	 * @param productData
	 * @param storeId
	 */
	void updateParcelShippingForProduct(final ProductData productData, final String storeId);

	/**
	 * @param productcodeList
	 * @param homeStoreId
	 * @return
	 */
	Map<String, List<ProductData>> parcelShippingMessageForProducts(List<ProductData> productcodeList, String homeStoreId);

	/**
	 * @param baseProduct
	 * @return Collection<VariantProductModel>
	 */
	Collection<VariantProductModel> getActiveVariantProducts(ProductModel baseProduct);

	ProductModel getProductForList(final String searchTerm);

	ProductModel getProductByProductCode(final String code);

	/**
	 * @return List<ProductModel>
	 */
	List<ProductModel> getAllProducts();

	/**
	 * @param productCodes
	 * @return List<ProductModel>
	 */
	List<ProductModel> getAllRecommProducts(String productCodes);

	/**
	 * @param productCode
	 * @return String
	 */
	String getProductPrice(String productCode);

	/**
	 * @param categoriesCode
	 * @return List<CategoryModel>
	 */
	List<CategoryModel> getAllRecommCategories(String categoriesCodes);

	/**
	 * @param algonomyRequestData
	 * @return List<AlgonomyResponseData>
	 */
	Map<String, List<AlgonomyResponseData>> getRecommendedProducts(AlgonomyRequestData algonomyRequestData);

	String getInventoryUOMIdForUOMProductsForList(ProductModel productModel, String inentoryUOMId);

	ProductModel getProductByItemNumberWithZero(final String itemNumber);

	ProductModel getProductByProductCodeAndVersion(CatalogVersionModel catalogVersion, String code);

	VerticalBarComponentModel getVerticalBarComponent();

	ProductModel getProductForListForBulk(final String searchTerm);
}
