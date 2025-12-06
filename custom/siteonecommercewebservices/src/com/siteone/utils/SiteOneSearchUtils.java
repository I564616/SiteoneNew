package com.siteone.utils;

import com.siteone.commerceservice.category.data.CategoryProductSearchData;

import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.search.facetdata.FacetData;
import de.hybris.platform.commerceservices.search.facetdata.FacetValueData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;

import java.util.Arrays;
import java.util.List;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;


import com.siteone.facades.category.SiteOneCategoryFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
public class SiteOneSearchUtils {

    @Resource(name = "storeSessionFacade")
    private SiteOneStoreSessionFacade storeSessionFacade;

    @Resource(name = "siteOnecategoryFacade")
    private SiteOneCategoryFacade siteOnecategoryFacade;

    public void setInStockAndNearbyStoresFlagAndData(String nearbyCheckOn, String selectedNearbyStores, String inStockFilterSelected, String nearbyDisabled) {
        if (inStockFilterSelected != null && inStockFilterSelected.equals("on")) {
            storeSessionFacade.setInstockFilterSelected(true);
        } else {
            storeSessionFacade.setInstockFilterSelected(false);
        }
        final List<PointOfServiceData> nearbyStoresList = storeSessionFacade.getNearbyStoresFromSession();
        final List<PointOfServiceData> nurseryNearbyStores = storeSessionFacade.getNurseryNearbyBranchesFromSession();
        if (CollectionUtils.isNotEmpty(nearbyStoresList)) {
        	storeSessionFacade.setNearbyStoresInSession(setNearbyStoreSelected(nearbyStoresList, nearbyCheckOn, selectedNearbyStores, nearbyDisabled));
        }
        if (CollectionUtils.isNotEmpty(nurseryNearbyStores)) {
        	storeSessionFacade.setNurseryNearbyBranches(setNearbyStoreSelected(nurseryNearbyStores, nearbyCheckOn, selectedNearbyStores, nearbyDisabled));
        }
    }
    
    public List<PointOfServiceData> setNearbyStoreSelected(final List<PointOfServiceData> stores, final String nearbyCheckOn,
			final String selectedNearbyStores, final String nearbyDisabled)
	{
		if ("on".equals(nearbyCheckOn))
		{
			if (selectedNearbyStores != null)
			{
				final List<String> selectedStoreList = Arrays.asList(selectedNearbyStores.split("\\s*,\\s*"));

				for (final PointOfServiceData pos : stores)
				{
					if (selectedStoreList.contains(pos.getStoreId())
							|| pos.getStoreId().equals(storeSessionFacade.getSessionStore().getStoreId()))
					{
						pos.setIsNearbyStoreSelected(true);
					}
					else
					{
						pos.setIsNearbyStoreSelected(false);
					}
				}
			}
		}
		if (nearbyDisabled != null && "on".equals(nearbyDisabled))
		{
			for (final PointOfServiceData pos : stores)
			{
				if (!pos.getStoreId().equals(storeSessionFacade.getSessionStore().getStoreId()))
				{
					pos.setIsNearbyStoreSelected(false);
				}
			}
		}
		return stores;
	}

    public void attachImageUrls(final ProductSearchPageData<SearchStateData, ProductData> searchPageData,
    		 CategoryProductSearchData categorySearchData) {
        final List<FacetData<SearchStateData>> facets = searchPageData.getFacets();
        int selectionCount = 0;

        if (CollectionUtils.isNotEmpty(facets))
        {
            for (final FacetData<SearchStateData> facetData : facets) {
                if ("socategory".equalsIgnoreCase(facetData.getCode()))
                {
                    for (final FacetValueData<SearchStateData> facetValueData : facetData.getValues())
                    {
                        CategoryData categoryData = siteOnecategoryFacade.getCategoryDataForCode(facetValueData.getCode());
                        ImageData imageData = categoryData.getImage();
                        if (imageData != null) {
                            facetValueData.setImageUrl(imageData.getUrl());
                        }

                        if (facetValueData.isSelected()) {
                            selectionCount++;
                        }
                    }
                    categorySearchData.setCategoryFacetSelectionCount(selectionCount);
                }
            }
        }
    }

}