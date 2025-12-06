/**
 *
 */
package com.siteone.facades.product;

import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.ProductHighlights;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.core.model.product.ProductModel;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.siteone.facade.BuyItAgainData;
import com.siteone.facade.StoreLevelStockInfoData;
import com.siteone.facades.customer.price.SiteOneCspResponse;
import com.siteone.facades.customer.price.SiteOnePdpPriceDataUom;
import com.siteone.integration.price.data.SiteOneWsPriceResponseData;
import com.siteone.integration.product.data.SiteOneWsCategoryResponseData;
import com.siteone.integration.product.data.SiteOneWsProductResponseData;




/**
 * @author 1091124
 *
 */
public interface SiteOneProductFacade
{

	ProductData getProductBySearchTermForSearch(String searchTerm, Collection<ProductOption> options);

	SiteOneCspResponse getPriceForCustomer(final String productCode, String inventoryUOMDataCode, Integer quantity);

	SiteOneCspResponse getPriceForCustomer(final String productCode, Integer quantity, final String storeId,
			final String retailBranchId);

	SiteOnePdpPriceDataUom getCustomerPriceforUom(final String productCode, final Integer quantity, final String inventoryUomId,
			Collection<ProductOption> productOptions);

	ProductData getProductByUOM(String productCode, float inventoryMultiplier, String inventoryUOMDataCode);

	ProductData getProductByUOMPLP(String productCode, float inventoryMultiplier);

	ProductData getProductByUOMPriceRange(ProductData productData);

	ProductData getRetailAPIPriceByUOMPLPPriceRange(ProductData productData);

	ProductData getProductByUOMPLPPriceRange(ProductData productData);

	ProductData updateUOMPriceForSingleUOM(ProductData productData);

	List<ProductData> getProductsForQuickOrder(String code, Collection<ProductOption> options);

	public SiteOneWsPriceResponseData getProductPriceForCustomer(final Map<ProductModel, Integer> products);

	String getCustomerSpecificPrice(String code, Integer quantity);

	OrderData getPriceUpdateForHideUomEntry(OrderData orderDetails, boolean isTotalPrice);

	void updateInventoryUOMDataForHideUomEntry(final String code, ProductData productData);

	void trackRetailCSPPrice(final String itemNumber, final String customerUnit, final String emailAddress,
			final String retailPrice, final String cspPrice, final String branchId);

	/**
	 * @param productCodeList
	 * @return
	 */
	SiteOneWsPriceResponseData getCSPResponse(List<String> productCodeList);

	SiteOneWsPriceResponseData getCSPResponseData(List<String> productCodeList, final Map<String, Integer> productCodeWithQunatity,
			final Map<String, String> productUomCodeId, final String branchRetailID);

	Map<String, SiteOneWsPriceResponseData> getCSPResponseForUoms(List<String> productCodeList,
			final Map<String, String> productCodeWithStore, final Map<ProductModel, String> productsUoms,
			final String retailBranchId);

	SiteOneCspResponse getVariantCSPForCustomer(final String productCode);

	void populatVariantProductsForDisplay(final ProductData productData, final Model model);

	void setCustomerPriceForVariants(final ProductData productData, final String retailBranchId);

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

	ProductData getScanProductDetails(String productCode, String supplyChainNodeId, Map<String, String> variantProduct,
			String storeId);

	SiteOneCspResponse getStoreProductPriceForCustomer(final String productCode, Integer quantity, String inventoryUOMId);

	void populateAvailablityForBuyAgainPageProducts(final List<ProductData> productDataList);

	Boolean isRestrictedUsePesticide(String productCode, String storeId);

	void getCSPPriceListForProducts(final List<BuyItAgainData> buyItAgainDataList, final List<ProductData> productDataList);

	/**
	 * @param searchTerm
	 * @param options
	 * @return
	 */
	ProductData getProductForList(String searchTerm, Collection<ProductOption> options);

	List<StoreLevelStockInfoData> populateStoreLevelStockInfoData(String productCode, boolean isPDP);

	StoreLevelStockInfoData populateHubStoresStockInfoData(String productCode, boolean isPDP, boolean isForcedInStock);

	/**
	 * @return List<SiteOneWsProductResponseData>
	 */
	List<SiteOneWsProductResponseData> getAllProducts();

	/**
	 * @param productCodes
	 * @return List<SiteOneWsProductResponseData>
	 */
	List<SiteOneWsProductResponseData> getAllRecommProducts(final String productCodes);

	/**
	 * @param categoriesCode
	 * @return List<SiteOneWsCategoryResponseData>
	 */
	List<SiteOneWsCategoryResponseData> getAllRecommCategories(final String categoriesCode);

	Object[] getRecommendedProductsToDisplay(final String placementPage, final String categoryId, final String productId,
			final String sessionId, final String pagePosition);

	Object[] getRecommendedProductsToDisplay(final String placementPage, final String categoryId, final String productId,
			final String sessionId, final String pagePosition, final String orderId, final String productPrices,
			final String productPricesCents, final String quantities, final String searchTerm);

	void getVariantProductMultiplier(ProductData productData);

	void getPriceRangeForVariant(ProductData productData, final List<Double> prices);

	void createRecentScanProducts(String accountNumber, String productCode);

	List<String> getRecentScanProductsByUser(final String accountNumber);

	/**
	 * @param productCode
	 * @param store
	 * @return
	 */
	String populateStoreLevelStockInfoDataForPopup(String productCode, PointOfServiceData store);

	/**
	 * @param productcodeList
	 * @param productCodeWithStore
	 * @param productCodeWithQunatity
	 * @param productCodeWithUom
	 * @return
	 */
	Map<String, SiteOneWsPriceResponseData> getCSPResponse(List<String> productcodeList, Map<String, String> productCodeWithStore,
			Map<String, Integer> productCodeWithQunatity, Map<String, String> productCodeWithUom, final String branchRetailID);

	List<ProductData> updateSalesInfoBackorderForOrderEntry(List<OrderEntryData> orderEntryList);

	void updateSalesInfoBackorderForProduct(List<ProductData> productList);

	/**
	 * @param productDataList
	 * @param inventoryUomId
	 */
	List<ProductData> getCSPPriceListForRecommendedProducts(final List<ProductData> productDataList, String inventoryUomId);

	/**
	 * @param productData
	 */
	void setCategoriesForProduct(ProductData productData);

	/**
	 * @param productCode
	 * @param inventoryUOMDataCode
	 * @param quantity
	 * @return
	 */
	PriceData getRetailPriceBranch(String productCode, String inventoryUOMDataCode, Integer quantity);

	/**
	 * @param productData
	 * @param customerRetailId
	 */
	void setRetailPriceForVariants(ProductData productData, String customerRetailId);

	/**
	 * @param porductModel
	 * @return
	 */
	List<ProductHighlights> getHighLights(ProductModel productModel);



}
