/**
 *
 */
package com.siteone.facades.category;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.facetdata.ProductCategorySearchPageData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.siteone.commerceservice.category.data.CategoryProductSearchData;
import com.siteone.core.dto.navigation.GlobalProductNavigationNodeData;


/**
 * @author 1003567
 *
 */
public interface SiteOneCategoryFacade
{

	public List<CategoryData> getSubcategories(Collection<CategoryModel> categories, String categoryCode);

	public List<ProductData> getSubcategoryProduct(CategoryModel category);

	public List<CategoryData> getNavigationCategories(String navigationRoot);

	public List<GlobalProductNavigationNodeData> getGlobalNavigationCategories(String navigationRoot);

	public CategoryData getCategoryDataForCode(String code);

	void filterProductsForParcelShippingCategories(
			final ProductCategorySearchPageData<SearchStateData, ProductData, CategoryData> searchPageData,
			final String expressShipping);

	void filterProductsForParcelShippingSearch(final ProductSearchPageData<SearchStateData, ProductData> searchPageData,
			final String expressShipping);
	
	void populateModelForInventory(final ProductSearchPageData<SearchStateData, ProductData> searchPageData,
			final CategoryProductSearchData categorySearchData);

	void updateSearchPageData(final ProductSearchPageData<SearchStateData, ProductData> searchPageData);

	void updatePriorityBrandFacet(final ProductSearchPageData<SearchStateData, ProductData> searchPageData);

	void updateSalesInfoBackorderForProduct(final ProductSearchPageData<SearchStateData, ProductData> searchPageData);

	public void updateStockFlagsForRegulatedProducts(ProductSearchPageData<SearchStateData, ProductData> searchPageData);

	public void getVariantProducts(final Map<String, List<String>> baseVariantMap, final int count,final PageableData pageableData,final Model model);
	
}