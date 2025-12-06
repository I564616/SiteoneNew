package com.siteone.storefront.util;


import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.search.facetdata.FacetData;
import de.hybris.platform.commerceservices.search.facetdata.FacetValueData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.servicelayer.session.SessionService;

import java.util.Arrays;
import java.util.List;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.siteone.facade.ListSearchPageData;

/**
 * @author nmangal
 *
 */


import com.siteone.facades.category.SiteOneCategoryFacade;
import com.siteone.facades.savedList.data.SavedListEntryData;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;


@Component("siteOneSearchUtils")
public class SiteOneSearchUtils
{

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "siteOnecategoryFacade")
	private SiteOneCategoryFacade siteOnecategoryFacade;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	public void setInStockAndNearbyStoresFlagAndData(final String nearbyCheckOn, final String selectedNearbyStores,
			final String inStockFilterSelected, final String nearbyDisabled)
	{
		if (inStockFilterSelected != null && inStockFilterSelected.equals("on"))
		{
			storeSessionFacade.setInstockFilterSelected(true);
		}
		else
		{
			storeSessionFacade.setInstockFilterSelected(false);
		}
		final List<PointOfServiceData> nearbyStoresList = storeSessionFacade.getNearbyStoresFromSession();
		final List<PointOfServiceData> nurseryNearbyStores = storeSessionFacade.getNurseryNearbyBranchesFromSession();
		if (CollectionUtils.isNotEmpty(nearbyStoresList))
		{
			storeSessionFacade.setNearbyStoresInSession(setNearbyStoreSelected(nearbyStoresList, nearbyCheckOn, selectedNearbyStores, nearbyDisabled));
		}
		if (CollectionUtils.isNotEmpty(nurseryNearbyStores))
		{
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

	public void attachImageUrls(final ProductSearchPageData<SearchStateData, ProductData> searchPageData, final Model model)
	{
		final List<FacetData<SearchStateData>> facets = searchPageData.getFacets();
		int selectionCount = 0;

		if (CollectionUtils.isNotEmpty(facets))
		{
			for (final FacetData<SearchStateData> facetData : facets)
			{
				if ("socategory".equalsIgnoreCase(facetData.getCode()))
				{
					for (final FacetValueData<SearchStateData> facetValueData : facetData.getValues())
					{
						final CategoryData categoryData = siteOnecategoryFacade.getCategoryDataForCode(facetValueData.getCode());
						final ImageData imageData = categoryData.getImage();
						if (imageData != null)
						{
							facetValueData.setImageUrl(imageData.getUrl());
						}

						if (facetValueData.isSelected())
						{
							selectionCount++;
						}
					}
					model.addAttribute("categoryFacetSelectionCount", selectionCount);
				}
			}
		}
	}

	public void attachfacetValueName(final ListSearchPageData<SearchStateData, SavedListEntryData> searchPageData,
			final Model model)
	{
		final List<FacetData<SearchStateData>> facets = searchPageData.getFacets();

		if (CollectionUtils.isNotEmpty(facets))
		{
			for (final FacetData<SearchStateData> facetData : facets)
			{
				if ("socategory".equalsIgnoreCase(facetData.getCode()))
				{
					for (final FacetValueData<SearchStateData> facetValueData : facetData.getValues())
					{
						final CategoryData categoryData = siteOnecategoryFacade.getCategoryDataForCode(facetValueData.getCode());
						if (categoryData != null)
						{
							facetValueData.setName(categoryData.getName());
						}
					}
				}
			}
		}
	}

	public void populateModelForInventory(final ProductSearchPageData<SearchStateData, ProductData> searchPageData,
			final Model model)
	{
		boolean inStockAtNearbyStores = false;
		boolean expressShippingAvailable = false;

		final String expressShipping = sessionService.getAttribute("expressShipping") != null
				? sessionService.getAttribute("expressShipping")
				: null;

		if (searchPageData.getPagination() != null)
		{
			inStockAtNearbyStores = searchPageData.getPagination().getInStockCount() > 0;
			expressShippingAvailable = searchPageData.getPagination().getShippableCount() > 0;
		}

		model.addAttribute("inStockAtNearbyStores", inStockAtNearbyStores);
		model.addAttribute("expressShippingAvailable", expressShippingAvailable);
		model.addAttribute("expressShipping", expressShipping != null && expressShipping.equalsIgnoreCase("true") ? "on" : null);
		model.addAttribute("availableInStoresCount", searchPageData.getPagination().getInStockCount());
		model.addAttribute("shippingAvailableCount", searchPageData.getPagination().getShippableCount());
	}

}
