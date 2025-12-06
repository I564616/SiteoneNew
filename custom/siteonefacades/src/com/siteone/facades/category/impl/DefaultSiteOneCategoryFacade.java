/**
 *
 */
package com.siteone.facades.category.impl;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.model.navigation.CMSNavigationNodeModel;
import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.category.CommerceCategoryService;
import de.hybris.platform.commerceservices.search.facetdata.FacetData;
import de.hybris.platform.commerceservices.search.facetdata.FacetValueData;
import de.hybris.platform.commerceservices.search.facetdata.ProductCategorySearchPageData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.Model;

import com.siteone.commerceservice.category.data.CategoryProductSearchData;
import com.siteone.core.category.service.SiteOneCategoryService;
import com.siteone.core.dto.navigation.GlobalProductNavigationNodeData;
import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.GlobalProductNavigationNodeModel;
import com.siteone.core.services.RegulatoryStatesCronJobService;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.facades.category.SiteOneCategoryFacade;
import com.siteone.facades.sds.SiteOneSDSProductSearchFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.integration.product.data.SiteOneSalesReponseItems;
import com.siteone.integration.product.data.SiteOneSalesRequestData;
import com.siteone.integration.product.data.SiteOneSalesResponseData;
import com.siteone.integration.services.ue.impl.DefaultSiteOneSalesDataWebService;


public class DefaultSiteOneCategoryFacade implements SiteOneCategoryFacade
{
	private static final String SEGMENT_LEVEL_SHIPPING_ENABLED = "segmentLevelShippingEnabled";
	
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneCategoryFacade.class);
	
	private Converter<CategoryModel, CategoryData> siteOneCategoryConverter;

	private CommerceCategoryService commerceCategoryService;

	private Converter<CategoryModel, CategoryData> categoryNavigationConverter;

	private Converter<CMSNavigationNodeModel, GlobalProductNavigationNodeData> defaultGlobalProductNavigationNodeConverter;

	@Resource(name = "SiteOneProductConverter")
	private Converter<ProductModel, ProductData> SiteOneProductConverter;
	@Resource(name = "categoryService")
	private SiteOneCategoryService categoryService;
	
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;
	
	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
	
	@Resource(name = "siteOneSalesDataWebService")
	private DefaultSiteOneSalesDataWebService siteOneSalesDataWebService;
	
	@Resource(name = "storeFinderService")
	private SiteOneStoreFinderService siteOneStoreFinderService;
	
	@Resource(name = "regulatoryStatesCronJobService")
	private RegulatoryStatesCronJobService regulatoryStatesCronJobService;

	@Resource(name = "siteOneSDSProductSearchFacade")
	private SiteOneSDSProductSearchFacade siteOneSDSProductSearchFacade;

	public SessionService getSessionService()
	{
		return sessionService;
	}
	
	@Override
	public List<CategoryData> getSubcategories(final Collection<CategoryModel> categories, final String categoryCode)
	{
		List<CategoryData> subcategories = new ArrayList<CategoryData>();
		final List<CategoryModel> displayL1subCategory = new ArrayList<CategoryModel>();
		for (final CategoryModel subcategory : categories)
		{
			for (final CategoryModel superCategories : subcategory.getSupercategories())
			{
				if (superCategories.getCode().equals(categoryCode))
				{
					displayL1subCategory.add(subcategory);
				}
			}

			subcategories = siteOneCategoryConverter.convertAll(displayL1subCategory);
			Collections.sort(subcategories, (c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));

		}
		return subcategories;
	}

	@Override
	public List<ProductData> getSubcategoryProduct(final CategoryModel category)
	{
		return SiteOneProductConverter.convertAll(category.getProducts());
	}

	/**
	 * @return the siteOneCategoryConverter
	 */
	public Converter<CategoryModel, CategoryData> getSiteOneCategoryConverter()
	{
		return siteOneCategoryConverter;
	}

	/**
	 * @param siteOneCategoryConverter
	 *           the siteOneCategoryConverter to set
	 */
	public void setSiteOneCategoryConverter(final Converter<CategoryModel, CategoryData> siteOneCategoryConverter)
	{
		this.siteOneCategoryConverter = siteOneCategoryConverter;
	}


	@Override
	public List<CategoryData> getNavigationCategories(final String navigationRoot)
	{

		List<CategoryData> levelOneCategoryDatas = new ArrayList<CategoryData>();
		// YTODO Auto-generated method stub
		final CategoryModel rootCategoryModel = getCommerceCategoryService().getCategoryForCode(navigationRoot);

		if (null != rootCategoryModel)
		{

			//Converting level1 categories
			levelOneCategoryDatas = Converters.convertAll(rootCategoryModel.getCategories(), getCategoryNavigationConverter());

			levelOneCategoryDatas.forEach(levelOneCategoryData -> {
				// YTODO Auto-generated method stub
				final CategoryModel levelOnecategoryModel = getCommerceCategoryService()
						.getCategoryForCode(levelOneCategoryData.getCode());
				if (null != levelOnecategoryModel)
				{
					//Converting level2 categories
					final List<CategoryData> levelTwoCategoryDatas = Converters.convertAll(levelOnecategoryModel.getCategories(),
							getCategoryNavigationConverter());


					levelTwoCategoryDatas.forEach(levelTwoCategoryData -> {
						// YTODO Auto-generated method stub
						final CategoryModel levelTwoCategoryModel = getCommerceCategoryService()
								.getCategoryForCode(levelTwoCategoryData.getCode());
						if (null != levelTwoCategoryModel)
						{
							//Converting level3 categories
							final List<CategoryData> levelThreeCategoryDatas = Converters
									.convertAll(levelTwoCategoryModel.getCategories(), getCategoryNavigationConverter());
							levelTwoCategoryData.setSubCategories(levelThreeCategoryDatas);
						}


					});

					levelOneCategoryData.setSubCategories(levelTwoCategoryDatas);
				}


			});
		}
		return levelOneCategoryDatas;
	}

	@Override
	public List<GlobalProductNavigationNodeData> getGlobalNavigationCategories(final String navigationRoot)
	{

		List<GlobalProductNavigationNodeData> levelOneCategoryDatas = new ArrayList<GlobalProductNavigationNodeData>();
		// YTODO Auto-generated method stub
		final GlobalProductNavigationNodeModel rootCategoryModel = categoryService.getProductNavNodesForCategory(navigationRoot);

		if (null != rootCategoryModel)
		{

			//Converting level1 categories
			levelOneCategoryDatas = Converters.convertAll(rootCategoryModel.getChildren(),
					getDefaultGlobalProductNavigationNodeConverter());
			Collections.sort(levelOneCategoryDatas, (o1, o2) -> ((GlobalProductNavigationNodeData) o1).getSequenceNumber()
					.compareTo(((GlobalProductNavigationNodeData) o2).getSequenceNumber()));
			levelOneCategoryDatas = levelOneCategoryDatas.stream()
					.filter(node -> (node.isVisible() && node.getCategoryProductCount() > 0)).collect(Collectors.toList());
			levelOneCategoryDatas.forEach(levelOneCategoryData -> {
				// YTODO Auto-generated method stub
				final GlobalProductNavigationNodeModel levelOnecategoryModel = categoryService
						.getProductNavNodesForCategory(levelOneCategoryData.getCategoryCode());
				if (null != levelOnecategoryModel)
				{
					//Converting level2 categories
					List<GlobalProductNavigationNodeData> levelTwoCategoryDatas = Converters
							.convertAll(levelOnecategoryModel.getChildren(), getDefaultGlobalProductNavigationNodeConverter());
					Collections.sort(levelTwoCategoryDatas, (o1, o2) -> ((GlobalProductNavigationNodeData) o1).getSequenceNumber()
							.compareTo(((GlobalProductNavigationNodeData) o2).getSequenceNumber()));
					levelTwoCategoryDatas = levelTwoCategoryDatas.stream()
							.filter(node -> (node.isVisible() && node.getCategoryProductCount() > 0)).collect(Collectors.toList());
					levelTwoCategoryDatas.forEach(levelTwoCategoryData -> {
						// YTODO Auto-generated method stub
						final GlobalProductNavigationNodeModel levelTwoCategoryModel = categoryService
								.getProductNavNodesForCategory(levelTwoCategoryData.getCategoryCode());
						if (null != levelTwoCategoryModel)
						{
							//Converting level3 categories
							List<GlobalProductNavigationNodeData> levelThreeCategoryDatas = Converters
									.convertAll(levelTwoCategoryModel.getChildren(), getDefaultGlobalProductNavigationNodeConverter());
							Collections.sort(levelThreeCategoryDatas, (o1, o2) -> ((GlobalProductNavigationNodeData) o1)
									.getSequenceNumber().compareTo(((GlobalProductNavigationNodeData) o2).getSequenceNumber()));
							levelThreeCategoryDatas = levelThreeCategoryDatas.stream()
									.filter(node -> (node.isVisible() && node.getCategoryProductCount() > 0)).collect(Collectors.toList());
							levelTwoCategoryData.setChildren(levelThreeCategoryDatas);
						}


					});

					levelOneCategoryData.setChildren(levelTwoCategoryDatas);
				}


			});
		}





		return levelOneCategoryDatas;
	}

	@Override
	public CategoryData getCategoryDataForCode(final String code)
	{
		final CategoryModel categoryModel = commerceCategoryService.getCategoryForCode(code);
		return siteOneCategoryConverter.convert(categoryModel);
	}

	/**
	 * Filter products by parcel shipping filter in ui
	 *
	 * @param searchPageData
	 */
	public void filterProductsForParcelShippingCategories(
			final ProductCategorySearchPageData<SearchStateData, ProductData, CategoryData> searchPageData,
			final String expressShipping)
	{

		if (expressShipping != null && expressShipping.equalsIgnoreCase("true"))
		{
			if (searchPageData != null && CollectionUtils.isNotEmpty(searchPageData.getResults())
					&& searchPageData.getPagination() != null)
			{
				searchPageData.getResults().removeIf(productData -> productData != null && !productData.getIsShippable());
				searchPageData.getPagination().setTotalNumberOfResults(searchPageData.getResults().size());
				if (CollectionUtils.isNotEmpty(searchPageData.getFacets()))
				{
					searchPageData.getFacets().stream().filter(facet -> "socategory".equalsIgnoreCase(facet.getCode()))
							.forEach(facetData -> {

								if (CollectionUtils.isNotEmpty(facetData.getValues()))
								{

									final List<CategoryData> categoryDataList = searchPageData.getResults().stream()
											.map(ProductData::getCategories).flatMap(Collection::stream).collect(Collectors.toList());
									final Set<String> categoryCodesSet = new HashSet<>();
									if (CollectionUtils.isNotEmpty(categoryDataList))
									{
										categoryCodesSet
												.addAll(categoryDataList.stream().map(CategoryData::getCode).collect(Collectors.toSet()));
									}


									if (CollectionUtils.isNotEmpty(categoryCodesSet))
									{
										facetData.getValues().removeIf(factValueData -> factValueData.getCode() != null
												&& !categoryCodesSet.contains(factValueData.getCode()));
									}


									if (CollectionUtils.isNotEmpty(facetData.getValues()))
									{

										facetData.getValues().forEach(factValueData -> {
											factValueData.setCount(0);

											searchPageData.getResults().forEach(productData -> {
												final boolean categoryMatchWithProduct = CollectionUtils
														.isNotEmpty(productData.getCategories())
														&& productData.getCategories().stream().anyMatch(category -> category.getCode() != null
																&& category.getCode().equalsIgnoreCase(factValueData.getCode()));
												if (categoryMatchWithProduct)
												{
													factValueData.setCount(factValueData.getCount() + 1);
												}
											});

										});

										facetData.getValues().removeIf(factValueData -> factValueData.getCode() != null
												&& "false".equalsIgnoreCase(factValueData.getCode()));

									}


								}

							});

				}
			}

		}
		else
		{

			// fix for parcel shipping filter count
			if (searchPageData != null && CollectionUtils.isNotEmpty(searchPageData.getResults()))
			{

				final long count = searchPageData.getResults().stream()
						.filter(productData -> productData != null && productData.getIsShippable()).count();
				if (count == 0 && CollectionUtils.isNotEmpty(searchPageData.getFacets()))
				{
					searchPageData.getFacets().removeIf(facet -> "soisShippable".equalsIgnoreCase(facet.getCode()));
				}

				if (CollectionUtils.isNotEmpty(searchPageData.getFacets()))
				{
					searchPageData.getFacets().stream().filter(facet -> "soisShippable".equalsIgnoreCase(facet.getCode())).findAny()
							.ifPresent(facetData -> {
								if (CollectionUtils.isNotEmpty(facetData.getValues()))
								{
									facetData.getValues().forEach(factValueData -> {
										factValueData.setCount(count);
									});
								}
							});
				}
			}
		}
	}

	/**
	 * Filter products by parcel shipping filter in ui
	 *
	 * @param searchPageData
	 */
	public void filterProductsForParcelShippingSearch(final ProductSearchPageData<SearchStateData, ProductData> searchPageData,
			final String expressShipping)
	{
		if (expressShipping != null && expressShipping.equalsIgnoreCase("true"))
		{
			if (searchPageData != null && CollectionUtils.isNotEmpty(searchPageData.getResults())
					&& searchPageData.getPagination() != null)
			{
				searchPageData.getResults().removeIf(productData -> productData != null && !productData.getIsShippable());
				if (CollectionUtils.isNotEmpty(searchPageData.getFacets()))
				{
					searchPageData.getFacets().stream().filter(facet -> "soisShippable".equalsIgnoreCase(facet.getCode())).findAny()
							.ifPresent(facetData -> {
								if (CollectionUtils.isNotEmpty(facetData.getValues()))
								{
									facetData.getValues().forEach(factValueData -> {
										factValueData.setCount(searchPageData.getResults().size());
									});
								}
							});

					searchPageData.getFacets().stream().filter(facet -> "socategory".equalsIgnoreCase(facet.getCode()))
							.forEach(facetData -> {

								if (CollectionUtils.isNotEmpty(facetData.getValues()))
								{

									final List<CategoryData> categoryDataList = searchPageData.getResults().stream()
											.map(ProductData::getCategories).flatMap(Collection::stream).collect(Collectors.toList());
									final Set<String> categoryCodesSet = new HashSet<>();
									if (CollectionUtils.isNotEmpty(categoryDataList))
									{
										categoryCodesSet
												.addAll(categoryDataList.stream().map(CategoryData::getCode).collect(Collectors.toSet()));
									}


									if (CollectionUtils.isNotEmpty(categoryCodesSet))
									{
										facetData.getValues()
												.removeIf(factValueData -> !categoryCodesSet.contains(factValueData.getCode()));
									}
								}

							});

				}
			}

		}
		else
		{

			// fix for parcel shipping filter count
			if (searchPageData != null && CollectionUtils.isNotEmpty(searchPageData.getResults()))
			{

				final long count = searchPageData.getResults().stream()
						.filter(productData -> productData != null && productData.getIsShippable()).count();
				if (count == 0 && CollectionUtils.isNotEmpty(searchPageData.getFacets()))
				{
					searchPageData.getFacets().removeIf(facet -> "soisShippable".equalsIgnoreCase(facet.getCode()));
				}

				if (CollectionUtils.isNotEmpty(searchPageData.getFacets()))
				{
					searchPageData.getFacets().stream().filter(facet -> "soisShippable".equalsIgnoreCase(facet.getCode())).findAny()
							.ifPresent(facetData -> {
								if (CollectionUtils.isNotEmpty(facetData.getValues()))
								{
									facetData.getValues().forEach(factValueData -> {
										factValueData.setCount(count);
									});
								}
							});
				}
			}
		}

	}

	@Override
	public void populateModelForInventory(final ProductSearchPageData<SearchStateData, ProductData> searchPageData,
			 CategoryProductSearchData categorySearchData)
	{
		boolean inStockAtNearbyStores = false;
		boolean expressShippingAvailable = false;

		if (searchPageData.getPagination() != null)
		{
			inStockAtNearbyStores = searchPageData.getPagination().getInStockCount() > 0;
			expressShippingAvailable = searchPageData.getPagination().getShippableCount() > 0;
		}

		final String expressShipping = sessionService.getAttribute("expressShipping") != null
				? sessionService.getAttribute("expressShipping")
				: null;

		categorySearchData.setExpressShippingAvailable(expressShippingAvailable);
		categorySearchData.setExpressShipping(expressShipping != null && expressShipping.equalsIgnoreCase("true") ? "on" : null);
		categorySearchData.setInStockAtNearbyStores(inStockAtNearbyStores);
		categorySearchData.setAvailableInStoresCount(searchPageData.getPagination().getInStockCount());
		boolean segmentLevelShippingEnabled = false;
		if ((getSessionService().getAttribute(SEGMENT_LEVEL_SHIPPING_ENABLED) != null))
		{
			segmentLevelShippingEnabled = (boolean) getSessionService().getAttribute(SEGMENT_LEVEL_SHIPPING_ENABLED);
		}
		if(segmentLevelShippingEnabled) {
			categorySearchData.setShippingAvailableCount(searchPageData.getPagination().getShippableCount());
		}
	}
	
	@Override
	public void updateSearchPageData(final ProductSearchPageData<SearchStateData, ProductData> searchPageData)
	{
		final Iterator<ProductData> searchResults = searchPageData.getResults().iterator();
		while (searchResults.hasNext())
		{
			final ProductData productData = searchResults.next();
			if (productData != null && productData.getCode() == null)
			{
				searchResults.remove();
			}
		}
		//remove unused facets from searchPageData
		final List<FacetData<SearchStateData>> facets = searchPageData.getFacets();
		if (CollectionUtils.isNotEmpty(facets))
		{
			FacetData<SearchStateData> soIsStockAvailableInStoresFacetData = null;
			FacetData<SearchStateData> soavailableInStoresFacetData = null;
			FacetData<SearchStateData> soisShippableFacetData = null;

			for (final FacetData<SearchStateData> facetData : facets)
			{
				if ("soIsStockAvailableInStores".equalsIgnoreCase(facetData.getCode()))
				{
					soIsStockAvailableInStoresFacetData = facetData;
				}
				if ("soavailableInStores".equalsIgnoreCase(facetData.getCode()))
				{
					soavailableInStoresFacetData = facetData;
				}
				if ("soisShippable".equalsIgnoreCase(facetData.getCode()))
				{
					soisShippableFacetData = facetData;
				}
			}

			if (null != soIsStockAvailableInStoresFacetData){
				facets.remove(soIsStockAvailableInStoresFacetData);
			}

			if (null != soavailableInStoresFacetData){
				facets.remove(soavailableInStoresFacetData);
			}
			if (null != soisShippableFacetData)
			{
				facets.remove(soisShippableFacetData);
			}
		}
	}
	
	@Override
	public void updateStockFlagsForRegulatedProducts(ProductSearchPageData<SearchStateData, ProductData> searchPageData)
	{
		final Iterator<ProductData> searchResults = searchPageData.getResults().iterator();
		List<String> productList = new ArrayList();
		Map<String, Boolean> rupMap = new HashMap<>();
		while (searchResults.hasNext())
		{
			final ProductData productData = searchResults.next();
			if (productData != null && productData.getCode() == null)
			{
				searchResults.remove();
			}
			else
			{
				if(productData != null && BooleanUtils.isTrue(productData.getIsRegulateditem()) && CollectionUtils.isNotEmpty(productData.getRegulatoryStates()))
				{
					Boolean isProductSellable = Boolean.FALSE;
					for (final String state : productData.getRegulatoryStates())
					{
						if (null != storeSessionFacade.getSessionStore().getAddress()
								&& state.equalsIgnoreCase(storeSessionFacade.getSessionStore().getAddress().getRegion().getIsocodeShort()))
						{
							isProductSellable = Boolean.TRUE;
						}
			      }
					if(BooleanUtils.isNotTrue(isProductSellable))
					{
						productData.setIsRegulatedAndNotSellable(true);
					}
					else if(storeSessionFacade.getSessionStore() != null && !storeSessionFacade.getSessionStore().getIsLicensed())
					{
						productList.add(productData.getCode());
					}
			    }
	       }
	    }
		if(CollectionUtils.isNotEmpty(productList))
		{
			final PointOfServiceModel pos = siteOneStoreFinderService.getStoreForId(storeSessionFacade.getSessionStore().getStoreId());
			rupMap = regulatoryStatesCronJobService.getRupForPLPByState(productList, pos.getAddress().getRegion());
		}
		if(!rupMap.isEmpty() && CollectionUtils.isNotEmpty(productList))
		{
			for(String sku : productList)
			{
				if(rupMap.containsKey(sku) && BooleanUtils.isTrue(rupMap.get(sku)))
				{
					searchPageData.getResults().stream().forEach(prd -> {
						if(sku.equalsIgnoreCase(prd.getCode()))
						{
							prd.setIsRegulatedAndNotSellable(true);
						}
					} );
				}
			}
		}
	}
	
	@Override
	public void updateSalesInfoBackorderForProduct(final ProductSearchPageData<SearchStateData, ProductData> searchPageData)
	{
		if(siteOneFeatureSwitchCacheService.getValueForSwitch(SiteoneCoreConstants.ENABLE_BO_LOGIC_P3) != null && 
				siteOneFeatureSwitchCacheService.getValueForSwitch(SiteoneCoreConstants.ENABLE_BO_LOGIC_P3).equalsIgnoreCase("on"))
		{
			final Iterator<ProductData> searchResults = searchPageData.getResults().iterator();
			List<ProductData> productList = new ArrayList();
			int i = 0;
			while (searchResults.hasNext())
			{
				final ProductData productData = searchResults.next();
				if (productData != null && productData.getCode() == null)
				{
					searchResults.remove();
				}
				else
				{
					if(productData != null && BooleanUtils.isNotTrue(productData.getIsStockAvailable()) && BooleanUtils.isNotTrue(productData.getIsEligibleForBackorder()) &&
							BooleanUtils.isNotTrue(productData.getIsSellableInventoryHit()) && productData.getNearestStore() == null)
					{
						if(productData.getVariantCount() == null || productData.getVariantCount()==0)
						{
							productList.add(productData);
						   i++;
						}
						else if(productData.getVariantCount() <= 20)
						{
							List<String> codes = productData.getVariantSkus();
							String productCodes = codes.stream().map(prdData->prdData).collect(Collectors.joining(","));
							if(BooleanUtils.isTrue(getUpdatedBackOrderEligibility(searchPageData,productCodes,true)))
							{
								searchPageData.getResults().forEach(prdData ->{
									if(productData.getCode().equalsIgnoreCase(prdData.getCode())) 
									{
										prdData.setIsEligibleForBackorder(true);
										prdData.setIsSellableInventoryHit(true);
									}
								});
							}
						}
						else
						{
							List<String> codes = productData.getVariantSkus();
							Iterator<String> codeIterator = codes.iterator();
							String codesList = null;
							int j = 0;
							while(codeIterator.hasNext())
							{
								if(codesList == null)
								{
									codesList = codeIterator.next();
									j++;
								}
								else
								{
									codesList = codesList.concat(",").concat(codeIterator.next());
								   j++;
								}
							    if(j==20 || !codeIterator.hasNext())
							    {
							   	 if(BooleanUtils.isTrue(getUpdatedBackOrderEligibility(searchPageData,codesList,true)))
										{
											searchPageData.getResults().forEach(prdData ->{
												if(productData.getCode().equalsIgnoreCase(prdData.getCode())) 
												{
													prdData.setIsEligibleForBackorder(true);
													prdData.setIsSellableInventoryHit(true);
												}
											});
											break;
										}
							   	 codesList = null;
							   	 j=0;
							    }
							}
						}
					}
					if((i==20 || !searchResults.hasNext()) && CollectionUtils.isNotEmpty(productList))
					{
						String productCodes = productList.stream().map(prdData->prdData.getCode()).collect(Collectors.joining(","));
						getUpdatedBackOrderEligibility(searchPageData,productCodes,false);
						productList = new ArrayList();
						i=0;					
					}
				}
			}
		}
	}

	public boolean getUpdatedBackOrderEligibility(final ProductSearchPageData<SearchStateData, ProductData> searchPageData, String productCodes , boolean isVariant)
	{
		SiteOneSalesRequestData siteOneSalesRequestData  = new SiteOneSalesRequestData();
		siteOneSalesRequestData.setStoreNumber(Integer.valueOf(storeSessionFacade.getSessionStore().getStoreId()));
		siteOneSalesRequestData.setCorrelationID(UUID.randomUUID().toString());
		siteOneSalesRequestData.setItems(productCodes);
		try
		{
			SiteOneSalesResponseData salesResponseData = siteOneSalesDataWebService.getSalesData(siteOneSalesRequestData);
			if(salesResponseData != null && CollectionUtils.isNotEmpty(salesResponseData.getItems())) 
			{
				if(!isVariant)
				{
					salesResponseData.getItems().forEach(responseItem ->{

						searchPageData.getResults().forEach(prdData ->{
							if(responseItem.getSkuID().equalsIgnoreCase(prdData.getCode()) && BooleanUtils.isTrue(responseItem.getIsBackOrderEligible())) 
							{
								prdData.setIsEligibleForBackorder(true);
								prdData.setIsSellableInventoryHit(true);
							}
						});

					});
					return true;
				}
				else
				{
					boolean retVal = false;
					for(SiteOneSalesReponseItems items : salesResponseData.getItems())
					{
						if(BooleanUtils.isTrue(items.getIsBackOrderEligible())) 
						{
							retVal = true;
							break;
						}
					}
					return retVal;
				}
			}
		}
		catch (Exception ex)
		{
			LOG.error("Exception occured while calling the sales data API "+siteOneSalesRequestData.getCorrelationID()+" : "+ex);
		}
		return false;
	}
	
	@Override
	public void updatePriorityBrandFacet(final ProductSearchPageData<SearchStateData, ProductData> searchPageData)
	{
		if (searchPageData != null && CollectionUtils.isNotEmpty(searchPageData.getFacets()))
		{
		final FacetData<SearchStateData> ourBrandFacet = new FacetData<>();
		final List<FacetValueData<SearchStateData>> brandFaceValueData = new ArrayList<>();

		for (final FacetData<SearchStateData> facet : searchPageData.getFacets())
		{
			if (facet.getCode().equalsIgnoreCase("soproductBrandNameFacet"))
			{
				for (final FacetValueData<SearchStateData> facetValue : facet.getValues())
				{
					if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("Our_Brands", facetValue.getName()))
					{
						brandFaceValueData.add(facetValue);
					}
				}
			}
		}
		if (CollectionUtils.isNotEmpty(brandFaceValueData))
		{
			ourBrandFacet.setValues(brandFaceValueData);
			ourBrandFacet.setName("Our Brands");
			ourBrandFacet.setCode("sopriorityBrandNameFacet");
			ourBrandFacet.setMultiSelect(true);
			ourBrandFacet.setVisible(true);
			searchPageData.getFacets().add(
					searchPageData.getFacets()
							.indexOf(searchPageData.getFacets().stream()
									.filter(facet -> "soproductBrandNameFacet".equalsIgnoreCase(facet.getCode())).findFirst().orElse(null)),
					ourBrandFacet);
		}
		}
	}

	public CommerceCategoryService getCommerceCategoryService()
	{
		return commerceCategoryService;
	}

	/**
	 * @param commerceCategoryService
	 *           the commerceCategoryService to set
	 */
	public void setCommerceCategoryService(final CommerceCategoryService commerceCategoryService)
	{
		this.commerceCategoryService = commerceCategoryService;
	}

	public Converter<CategoryModel, CategoryData> getCategoryNavigationConverter()
	{
		return categoryNavigationConverter;
	}

	/**
	 * @param categoryNavigationConverter
	 *           the categoryNavigationConverter to set
	 */
	public void setCategoryNavigationConverter(final Converter<CategoryModel, CategoryData> categoryNavigationConverter)
	{
		this.categoryNavigationConverter = categoryNavigationConverter;
	}

	/**
	 * @return the defaultGlobalProductNavigationNodeConverter
	 */
	public Converter<CMSNavigationNodeModel, GlobalProductNavigationNodeData> getDefaultGlobalProductNavigationNodeConverter()
	{
		return defaultGlobalProductNavigationNodeConverter;
	}

	/**
	 * @param defaultGlobalProductNavigationNodeConverter
	 *           the defaultGlobalProductNavigationNodeConverter to set
	 */
	public void setDefaultGlobalProductNavigationNodeConverter(
			final Converter<CMSNavigationNodeModel, GlobalProductNavigationNodeData> defaultGlobalProductNavigationNodeConverter)
	{
		this.defaultGlobalProductNavigationNodeConverter = defaultGlobalProductNavigationNodeConverter;
	}

	@Override
	public void getVariantProducts(final Map<String, List<String>> baseVariantMap, final int count,
			final PageableData pageableData, final Model model)
	{
		if (MapUtils.isNotEmpty(baseVariantMap))
		{
			final Map<String, List<ProductData>> variantProducts = new HashMap<>();
			String variantList = "";
			List<ProductData> variants;
			for (final List<String> skuList : baseVariantMap.values())
			{
				variantList = variantList.concat(skuList.stream().collect(Collectors.joining(","))).concat(",");
			}

			ProductSearchPageData<SearchStateData, ProductData> searchPageData = null;
			final SearchQueryData searchQueryData = new SearchQueryData();

			if (StringUtils.isNotBlank(variantList)) {
				searchQueryData.setValue(variantList);
				final SearchStateData searchState = new SearchStateData();
				searchState.setQuery(searchQueryData);
				searchPageData = siteOneSDSProductSearchFacade.curatedPLPSearch(searchState, pageableData,
						"VariantProduct" + "," + Integer.toString(count));
			}

			if (null != searchPageData && CollectionUtils.isNotEmpty(searchPageData.getResults()))
			{
				for (final String baseProductCode : baseVariantMap.keySet())
				{
					variants = searchPageData.getResults().stream().filter(
							product -> null != product.getBaseProduct() && product.getBaseProduct().equalsIgnoreCase(baseProductCode))
							.collect(Collectors.toList());
					variantProducts.put(baseProductCode, variants);
					searchPageData.getResults().removeAll(variants);
				}

				model.addAttribute("baseVariantMap", variantProducts);
			}
		}
	}


}