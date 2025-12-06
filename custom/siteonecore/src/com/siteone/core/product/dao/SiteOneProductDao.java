/**
 *
 */
package com.siteone.core.product.dao;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.model.product.ProductModel;

import java.util.List;

import com.siteone.core.model.VerticalBarComponentModel;


/**
 * @author 1091124
 *
 */
public interface SiteOneProductDao
{

	public List<ProductModel> findProductsBySearchTerm(final String searchTerm);

	public List<ProductModel> findProductsForQuickOrder(String code);

	/**
	 * @param productcodeList
	 * @return
	 */
	public List<ProductModel> findAllProductsByCodes(List<String> productcodeList);

	List<ProductModel> findProductsByItemNumber(final String code);

	/**
	 * @return List<ProductModel>
	 */
	List<ProductModel> findAllProducts();

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
	List<CategoryModel> getAllRecommCategories(final String categoriesCode);

	List<ProductModel> getProductByItemNumberWithZero(final String code);

	VerticalBarComponentModel getVerticalBarComponent();

	/**
	 * @param catalogVersion
	 * @param code
	 * @return
	 */
	List<ProductModel> findProductsByCodeandCatalog(CatalogVersionModel catalogVersion, String code);

	List<ProductModel> findProductsByItemNumberForBulk(final String code);

}
