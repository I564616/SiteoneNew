/**
 *
 */
package com.siteone.core.services.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.StockData;
import de.hybris.platform.commercefacades.product.data.VariantOptionData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.impl.DefaultProductService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.variants.model.VariantProductModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;

import com.siteone.core.model.InventoryUPCModel;
import com.siteone.core.model.VerticalBarComponentModel;
import com.siteone.core.product.dao.SiteOneProductDao;
import com.siteone.core.services.SiteOneProductService;
import com.siteone.core.services.SiteOneStockLevelService;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.integration.algonomy.SiteOneAlgonomyService;
import com.siteone.integration.algonomy.data.AlgonomyRequestData;
import com.siteone.integration.algonomy.data.AlgonomyResponseData;


/**
 * @author 1091124
 *
 */
public class DefaultSiteOneProductService extends DefaultProductService implements SiteOneProductService
{
	private SiteOneProductDao siteOneProductDao;

	@Resource(name = "storeFinderService")
	private SiteOneStoreFinderService siteOneStoreFinderService;

	@Resource(name = "siteOneStockLevelService")
	private SiteOneStockLevelService siteOneStockLevelService;

	@Resource(name = "pointOfServiceConverter")
	private Converter<PointOfServiceModel, PointOfServiceData> pointOfServiceConverter;

	@Resource(name = "siteOneAlgonomyService")
	SiteOneAlgonomyService siteOneAlgonomyService;

	private final Logger LOG = Logger.getLogger(DefaultSiteOneProductService.class);


	@Override
	public VerticalBarComponentModel getVerticalBarComponent()
	{
		return getSiteOneProductDao().getVerticalBarComponent();
	}


	@Override
	public ProductModel getProductBySearchTermForSearch(final String searchTerm)
	{
		validateParameterNotNull(searchTerm, "Parameter code must not be null");
		final List<ProductModel> products = getSiteOneProductDao().findProductsBySearchTerm(searchTerm);
		if (CollectionUtils.isNotEmpty(products) && products.size() == 1)
		{
			return products.get(0);
		}
		else
		{

			return null;
		}
	}

	@Override
	public ProductModel getProductForList(final String searchTerm)
	{
		validateParameterNotNull(searchTerm, "Parameter code must not be null");
		final List<ProductModel> products = getSiteOneProductDao().findProductsByItemNumber(searchTerm);
		if (CollectionUtils.isNotEmpty(products) && products.size() == 1)
		{
			return products.get(0);
		}
		else
		{

			return null;
		}
	}
@Override
	public ProductModel getProductForListForBulk(final String searchTerm)
	{
		validateParameterNotNull(searchTerm, "Parameter code must not be null");
		final List<ProductModel> products = getSiteOneProductDao().findProductsByItemNumberForBulk(searchTerm);
		if (CollectionUtils.isNotEmpty(products) && products.size() == 1)
		{
			return products.get(0);
		}
		else
		{

			return null;
		}
	}


	@Override
	public ProductModel getProductByProductCode(final String code)
	{
		LOG.info("Under getProductByProductCode");
		validateParameterNotNull(code, "Parameter code must not be null");
		final ProductModel product = super.getProductForCode(code);
		if (product != null)
		{
			if(Boolean.FALSE.equals(product.getIsProductDiscontinued()))
			{
   			return product;
			}
			else
			{
				return null;
			}
		}
		else
		{

			return null;
		}
	}
	@Override
	public ProductModel getProductByProductCodeAndVersion(final CatalogVersionModel catalogVersion, final String code)
	{
		LOG.info("Under getProductByProductCodeAndVersion");
		validateParameterNotNull(code, "Parameter code must not be null");
		final List<ProductModel> products = getSiteOneProductDao().findProductsByCodeandCatalog(catalogVersion, code);
		if (CollectionUtils.isNotEmpty(products) && products.size() == 1)
		{
			return products.get(0);
		}
		else
		{

			return null;
		}
	}

	@Override
	public List<ProductModel> getProductsForQuickOrder(final String code)
	{
		validateParameterNotNull(code, "Parameter code must not be null");
		return getSiteOneProductDao().findProductsForQuickOrder(code);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.services.SiteOneProductService#retrieveAllproducts(java.util.List)
	 */
	@Override
	public List<ProductModel> getAllProductsByCodes(final List<String> productcodeList)
	{
		return siteOneProductDao.findAllProductsByCodes(productcodeList);
	}

	@Override
	public void updateParcelShippingForProducts(final List<ProductData> productcodeList, final String homeStoreId)
	{

		final String hubStoreId = getHubStoreBranch(homeStoreId);

		if (CollectionUtils.isNotEmpty(productcodeList) && hubStoreId != null)
		{
			final List<String> products = productcodeList.stream()
					.filter(productData -> productData.getIsShippable() != null && productData.getIsShippable())
					.map(product -> product.getCode()).collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(products))
			{
				final List<List<Object>> stockLevelDataList = siteOneStockLevelService.getStockLevelsForHubStoresForProducts(products,
						hubStoreId);


				if (CollectionUtils.isNotEmpty(stockLevelDataList))
				{

					productcodeList.forEach(inputProduct -> {

						stockLevelDataList.forEach(stockLevelData -> {

							if (stockLevelData != null && stockLevelData.get(5) != null
									&& inputProduct.getCode().equalsIgnoreCase(stockLevelData.get(5).toString()))
							{
								updateParcelShippingByEligibility(inputProduct, stockLevelData);
							}

						});

						final boolean inputProductNotInStockList = stockLevelDataList.stream()
								.noneMatch(stockLevelData -> stockLevelData.get(5) != null
										&& stockLevelData.get(5).toString().equalsIgnoreCase(inputProduct.getCode()));

						if (inputProductNotInStockList)
						{
							inputProduct.setIsShippable(false);
						}

					});


				}
				else
				{
					productcodeList.forEach(inputProduct -> {
						inputProduct.setIsShippable(false);
					});
				}
			}

		}
		else if (CollectionUtils.isNotEmpty(productcodeList)) //no hub store
		{
			final boolean shippable = productcodeList.stream()
					.anyMatch(productData -> productData.getIsShippable() != null && productData.getIsShippable());
			if (shippable)
			{
				productcodeList.stream().filter(productData -> productData.getIsShippable() != null && productData.getIsShippable())
						.forEach(inputProduct -> {
							inputProduct.setIsShippable(false);
						});

			}
		}

	}

	@Override
	public Map<String, List<ProductData>> parcelShippingMessageForProducts(final List<ProductData> productcodeList,
			final String homeStoreId)
	{

		final String hubStoreId = getHubStoreBranch(homeStoreId);
		final Map<String, List<ProductData>> parcelShippableProduct = new HashMap();
		final List<ProductData> inStockparcelShippableProduct = new ArrayList<ProductData>();
		final List<ProductData> notInStockparcelShippableProduct = new ArrayList<ProductData>();
		if (CollectionUtils.isNotEmpty(productcodeList) && hubStoreId != null)
		{
			final List<String> products = productcodeList.stream()
					.filter(productData -> productData.getIsShippable() != null && productData.getIsShippable())
					.map(product -> product.getCode()).collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(products))
			{
				final List<List<Object>> stockLevelDataList = siteOneStockLevelService.getStockLevelsForHubStoresForProducts(products,
						hubStoreId);

				if (CollectionUtils.isNotEmpty(stockLevelDataList))
				{

					for (final ProductData inputProduct : productcodeList)
					{

						for (final List<Object> stockLevelData : stockLevelDataList)
						{

							if (stockLevelData != null && stockLevelData.get(5) != null
									&& inputProduct.getCode().equalsIgnoreCase(stockLevelData.get(5).toString()))
							{
								final boolean inStockHubFlag = ((stockLevelData.get(2) != null
										&& Long.valueOf(stockLevelData.get(2).toString()) > 0)
										|| (BooleanUtils.isNotTrue(inputProduct.getInventoryCheck()) && stockLevelData.get(6) != null
												&& BooleanUtils.isTrue((Boolean) stockLevelData.get(6))));

								if (inStockHubFlag)
								{
									inStockparcelShippableProduct.add(inputProduct);
									inputProduct.setIsAvailableInHub(true);
								}
								else
								{
									notInStockparcelShippableProduct.add(inputProduct);
									inputProduct.setIsAvailableInHub(false);
								}
							}
						}
					}

				}

			}

		}
		parcelShippableProduct.put("inStock", inStockparcelShippableProduct);
		parcelShippableProduct.put("outOfStock", notInStockparcelShippableProduct);
		return parcelShippableProduct;

	}




	@Override
	public void updateParcelShippingForProduct(final ProductData productData, final String homeStoreId)
	{

		final String hubStoreId = getHubStoreBranch(homeStoreId);
		final PointOfServiceModel pos = siteOneStoreFinderService.getStoreForId(homeStoreId);

		for (final PointOfServiceModel hubstore : pos.getShippingHubBranches())
		{
			if (productData != null && productData.getCode() != null && hubstore.getStoreId() != null)
			{
				if (productData.getIsShippable() != null && productData.getIsShippable())
				{

					final List<List<Object>> stockLevelDataList = siteOneStockLevelService
							.getStockLevelsForHubStoresForProduct(productData.getCode(), hubstore.getStoreId());

					if (CollectionUtils.isNotEmpty(stockLevelDataList))
					{
						stockLevelDataList.forEach(stockLevelData -> {
							updateParcelShippingByEligibility(productData, stockLevelData);
						});
					}
					else
					{
						if (!productData.getMultidimensional())
						{
							productData.setIsShippable(false);
						}
						else
						{
							setParcelShippingForBaseVariantProduct(productData, hubstore.getStoreId());
						}
					}
				}
				else
				{
					productData.setIsShippable(false);
				}

			}
			else if (productData != null && productData.getCode() != null) // no hub store
			{
				productData.setIsShippable(false);
			}
		}

	}

	/**
	 * Sets the shipping for Base Variant product
	 *
	 * @param productData
	 * @param hubStoreId
	 */
	private void setParcelShippingForBaseVariantProduct(final ProductData productData, final String hubStoreId)
	{
		if (CollectionUtils.isNotEmpty(productData.getVariantOptions()))
		{

			final List<String> products = productData.getVariantOptions().stream()
					.filter(variantProductData -> variantProductData.getIsShippable() != null && variantProductData.getIsShippable())
					.map(product -> product.getCode()).collect(Collectors.toList());

			if (CollectionUtils.isEmpty(products))
			{
				productData.setIsShippable(false);
			}

			if (CollectionUtils.isNotEmpty(products))
			{
				final List<List<Object>> stockLevelDataList = siteOneStockLevelService.getStockLevelsForHubStoresForProducts(products,
						hubStoreId);

				if (CollectionUtils.isNotEmpty(stockLevelDataList))
				{
					productData.getVariantOptions().forEach(inputVariantProduct -> {

						stockLevelDataList.forEach(stockLevelData -> {

							if (stockLevelData != null && stockLevelData.get(5) != null
									&& inputVariantProduct.getCode().equalsIgnoreCase(stockLevelData.get(5).toString()))
							{
								updateParcelShippingByEligibilityForVariantProduct(inputVariantProduct, stockLevelData);
							}

						});

					});

					final boolean shipableBaseProductFlag = productData.getVariantOptions().stream()
							.anyMatch(variantProduct -> variantProduct != null && variantProduct.getIsShippable() != null
									&& variantProduct.getIsShippable());
					productData.setIsShippable(shipableBaseProductFlag);
				}
				else
				{
					productData.setIsShippable(false);
				}
			}
		}


	}

	/**
	 * @param inputProduct
	 * @param stockLevelData
	 */
	private void updateParcelShippingByEligibility(final ProductData inputProduct, final List<Object> stockLevelData)
	{
		final boolean inStockHubFlag = ((stockLevelData.get(2) != null && Long.valueOf(stockLevelData.get(2).toString()) > 0)
				|| (BooleanUtils.isNotTrue(inputProduct.getInventoryCheck()) && stockLevelData.get(6) != null
						&& BooleanUtils.isTrue((Boolean) stockLevelData.get(6))));

		final StockData stockData = inputProduct.getStock();

		if (stockData == null)
		{
			inputProduct.setIsShippable(false);
		}
		else if (stockData.getIsHomeStoreStockAvailable() != null && !stockData.getIsHomeStoreStockAvailable())
		{
			boolean isHomeStoreInventoryHit = stockData.getIsHomeStoreInventoryHit() != null ? stockData.getIsHomeStoreInventoryHit() : false;

			if (stockData.getIsNearbyStoreStockAvailable() != null && stockData.getIsNearbyStoreStockAvailable())
			{
				if (inStockHubFlag)
				{
					inputProduct.setIsShippable(true);
				}
				else
				{
					inputProduct.setIsShippable(false);
				}
			}
			else
			{
				if (isHomeStoreInventoryHit && inStockHubFlag)
				{
					inputProduct.setIsShippable(true);
				}
				else
				{
					inputProduct.setIsShippable(false);
				}
			}
		}
		else if (inStockHubFlag)
		{
			inputProduct.setIsShippable(true);
		}
		else
		{
			inputProduct.setIsShippable(false);
		}
	}




	/**
	 * @param inputVariantProduct
	 * @param stockLevelData
	 */
	private void updateParcelShippingByEligibilityForVariantProduct(final VariantOptionData inputVariantProduct,
			final List<Object> stockLevelData)
	{
		final boolean inStockHubFlag = stockLevelData.get(2) != null && Long.valueOf(stockLevelData.get(2).toString()) > 0;

		final StockData stockData = inputVariantProduct.getStock();

		if (stockData == null)
		{
			inputVariantProduct.setIsShippable(false);
		}
		else if ((stockData.getIsHomeStoreStockAvailable() != null && !stockData.getIsHomeStoreStockAvailable()
				|| stockData.getIsNearbyStoreStockAvailable() != null && !stockData.getIsNearbyStoreStockAvailable()))
		{
			final boolean isHomeStoreInventoryHit = stockData.getIsHomeStoreInventoryHit() != null ? stockData.getIsHomeStoreInventoryHit() : false;

			if (isHomeStoreInventoryHit && inStockHubFlag)
			{
				inputVariantProduct.setIsShippable(true);
			}
			else
			{
				inputVariantProduct.setIsShippable(false);
			}
		}
		else if (inStockHubFlag)
		{
			inputVariantProduct.setIsShippable(true);
		}
		else
		{
			inputVariantProduct.setIsShippable(false);
		}
	}

	/**
	 * Checks the stock availability in Hub branches for the input product and current store id
	 *
	 * @target the input product
	 * @param storeId
	 * @return the product stock availability at Hub branch
	 */
	private String getHubStoreBranch(final String storeId)
	{

		if (storeId != null)
		{
			final PointOfServiceModel pos = siteOneStoreFinderService.getStoreForId(storeId);

			if (CollectionUtils.isNotEmpty(pos.getShippingHubBranches()))
			{

				final PointOfServiceModel hubPos = pos.getShippingHubBranches().stream().findFirst().get();

				return hubPos != null ? hubPos.getStoreId() : null;

			}
		}

		return null;
	}

	public Collection<VariantProductModel> getActiveVariantProducts(final ProductModel baseProduct)
	{
		return baseProduct.getVariants().stream()
				.filter(v -> v.getIsProductDiscontinued() == null || (!v.getIsProductDiscontinued().booleanValue()))
				.collect(Collectors.toList());
	}

	/**
	 * @return List<ProductModel>
	 */
	@Override
	public List<ProductModel> getAllProducts()
	{
		return siteOneProductDao.findAllProducts();
	}

	/**
	 * @param productCodes
	 * @return List<ProductModel>
	 */
	@Override
	public List<ProductModel> getAllRecommProducts(final String productCodes)
	{
		return siteOneProductDao.getAllRecommProducts(productCodes);
	}

	/**
	 * @param productCode
	 * @return String
	 */
	@Override
	public String getProductPrice(final String productCode)
	{
		return siteOneProductDao.getProductPrice(productCode);
	}

	/**
	 * @param categoriesCode
	 * @return List<CategoryModel>
	 */
	@Override
	public List<CategoryModel> getAllRecommCategories(final String categoriesCode)
	{
		return siteOneProductDao.getAllRecommCategories(categoriesCode);
	}

	/**
	 * @param algonomyRequestData
	 * @return List<AlgonomyResponseData>
	 */
	@Override
	public Map<String, List<AlgonomyResponseData>> getRecommendedProducts(final AlgonomyRequestData algonomyRequestData)
	{

		return siteOneAlgonomyService.getProductRecommendations(algonomyRequestData);
	}

	/**
	 * @return the siteOneProductDao
	 */
	public SiteOneProductDao getSiteOneProductDao()
	{
		return siteOneProductDao;
	}

	/**
	 * @param siteOneProductDao
	 *           the siteOneProductDao to set
	 */
	public void setSiteOneProductDao(final SiteOneProductDao siteOneProductDao)
	{
		this.siteOneProductDao = siteOneProductDao;
	}

@Override
	public String getInventoryUOMIdForUOMProductsForList(final ProductModel productModel, final String inventoryUOMId)
	{
		String defaultInventoryUOMId = null;
		final List<InventoryUPCModel> inventoryUOM = productModel.getUpcData();
		if (!CollectionUtils.isEmpty(inventoryUOM))
		{
			if(inventoryUOMId != null) {
				for (final InventoryUPCModel upc : inventoryUOM) {
					if(inventoryUOMId.equalsIgnoreCase(upc.getInventoryUPCID()) && BooleanUtils.isFalse(upc.getHideUPCOnline())){
						defaultInventoryUOMId = upc.getInventoryUPCID();
						return defaultInventoryUOMId;
					}
				}
				for (final InventoryUPCModel upc : inventoryUOM) {
					if (BooleanUtils.isFalse(upc.getHideUPCOnline())) {
						defaultInventoryUOMId = upc.getInventoryUPCID();
						return defaultInventoryUOMId;
					}
				}
			}

			else {
				final List<InventoryUPCModel> inventoryUPCChildList = inventoryUOM.stream()
						.filter(upc -> BooleanUtils.isFalse(upc.getHideUPCOnline())).collect(Collectors.toList());
				Collections.sort(inventoryUPCChildList,
	                     (o1, o2) -> o1.getInventoryMultiplier().intValue() - o2.getInventoryMultiplier().intValue());
	         defaultInventoryUOMId=inventoryUPCChildList.get(0).getInventoryUPCID();
			}
		}
		return defaultInventoryUOMId;
	}

	@Override
	public ProductModel getProductByItemNumberWithZero(final String itemNumber)
	{
		validateParameterNotNull(itemNumber, "Parameter code must not be null");
		final List<ProductModel> products = getSiteOneProductDao().getProductByItemNumberWithZero(itemNumber);
		if (CollectionUtils.isNotEmpty(products) && products.size() == 1)
		{
			return products.get(0);
		}
		else
		{

			return null;
		}
	}

}