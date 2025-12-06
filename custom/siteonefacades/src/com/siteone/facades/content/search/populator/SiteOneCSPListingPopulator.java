 /**
 *
 */
package com.siteone.facades.content.search.populator;

import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.user.UserService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.product.data.InventoryUOMData;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.integration.price.data.SiteOneWsPriceResponseData;


/**
 * @author KArasan
 *
 */
public class SiteOneCSPListingPopulator implements Populator<List<SearchResultValueData>, List<ProductData>>
{

	/** The search result product converter. */
	private Converter<SearchResultValueData, ProductData> searchResultProductConverter;

	private SiteOneProductFacade siteOneProductFacade;

	private PriceDataFactory priceDataFactory;

	private SiteOneStoreSessionFacade storeSessionFacade;
	
	private UserService userService;

	private static final Logger LOG = Logger.getLogger(SiteOneCSPListingPopulator.class);

	@Resource(name = "accProductFacade")
	private ProductFacade productFacade;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final List<SearchResultValueData> source, final List<ProductData> target) throws ConversionException
	{
		final List<String> productCodeList = new ArrayList<>();
		final Map<String, Integer> productCodeWithQunatity = new HashMap<>();
		final LinkedHashMap<String, String> productCodeWithStore = new LinkedHashMap<>();
		final Map<String, String> productUomCodeId = new HashMap<>();
		boolean isMixedCart = false;
		boolean isGuestUser = true;
		SiteOneWsPriceResponseData cspResponseData = null;
		SiteOneWsPriceResponseData retailResponseData=null;
		Map<String, SiteOneWsPriceResponseData> mixedcartcspresponse = new HashMap<String, SiteOneWsPriceResponseData>();
		Map<String, SiteOneWsPriceResponseData> mixedcartRetailresponse= new HashMap<String, SiteOneWsPriceResponseData>();
		if (null != storeSessionFacade.getSessionStore() && siteOneFeatureSwitchCacheService
				.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches", storeSessionFacade.getSessionStore().getStoreId()))
		{
			isMixedCart = true;
		}
		final LinkedHashMap<String, String> productNearByCodeList = new LinkedHashMap<>();
		source.forEach(searchResultValueData -> {
			final ProductData productData = searchResultProductConverter.convert(searchResultValueData);
			productCodeList.add(productData.getCode());
			Integer qunatity = 0;
			if(productData.getSellableUoms() != null)
			{
				qunatity = Integer.valueOf(Math.round(productData.getSellableUoms().get(0).getInventoryMultiplier()));
				productUomCodeId.put(productData.getCode(), productData.getSellableUoms().get(0).getInventoryUOMID());
			}
			if(qunatity > 0) {
				productCodeWithQunatity.put(productData.getCode(), qunatity);
			}
			else {
				productCodeWithQunatity.put(productData.getCode(), 1);
			}
			if (null!=storeSessionFacade.getSessionStore() && siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",
					storeSessionFacade.getSessionStore().getStoreId()))
			{
				final List<ProductOption> options = new ArrayList<>(Arrays.asList(ProductOption.STOCK));

				final ProductData productDataStock = productFacade.getProductForCodeAndOptions(productData.getCode(), options);
				if (productDataStock.getStock() != null)
				{
					productNearByCodeList.put(productData.getCode(), productDataStock.getStock().getFullfillmentStoreId());
				}
			}
			else
			{
				productCodeWithStore.put(productData.getCode(), storeSessionFacade.getSessionStore().getStoreId());
			}
			target.add(productData);
		});
		if(userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
		{
			isGuestUser=false;
		}
		if (CollectionUtils.isNotEmpty(productCodeList))
		{
			if (isMixedCart && !isGuestUser)
			{
				mixedcartcspresponse = siteOneProductFacade.getCSPResponse(productCodeList, productNearByCodeList,
						productCodeWithQunatity, productUomCodeId, null);
			}
			else if(!isGuestUser)
			{
				cspResponseData = siteOneProductFacade.getCSPResponseData(productCodeList, productCodeWithQunatity, productUomCodeId,
						null);
			}
			if (null != storeSessionFacade.getSessionStore()
					&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("RetailBranches",
							storeSessionFacade.getSessionStore().getStoreId())
					&& StringUtils.isNotBlank(storeSessionFacade.getSessionStore().getCustomerRetailId()))
			{
				if (isMixedCart)
				{
					mixedcartRetailresponse = siteOneProductFacade.getCSPResponse(productCodeList, productNearByCodeList,
							productCodeWithQunatity, productUomCodeId, storeSessionFacade.getSessionStore().getCustomerRetailId());
				}
				else
				{
					retailResponseData = siteOneProductFacade.getCSPResponseData(productCodeList, productCodeWithQunatity, productUomCodeId,
							storeSessionFacade.getSessionStore().getCustomerRetailId());
				}
			}

			if (isMixedCart)
			{
				if (null != mixedcartcspresponse)
				{
					populatorMixedCSPtoTarget(mixedcartcspresponse, target, productNearByCodeList);
				}
				if(null !=mixedcartRetailresponse)
				{
					populatorMixedRetailtoTarget(mixedcartRetailresponse, target, productNearByCodeList);
				}
				
			}
			else
			{
				if (null != cspResponseData)
				{
					populatorCSPtoTarget(cspResponseData, target);
				}
				if(null !=retailResponseData)
				{
					populatorRetailtoTarget(retailResponseData, target);
				}
			}
		}
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService()
	{
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService)
	{
		this.userService = userService;
	}

	/**
	 * @param mixedcartcspresponse
	 * @param target
	 * @param productNearByCodeList
	 */
	private void populatorMixedRetailtoTarget(Map<String, SiteOneWsPriceResponseData> mixedcartcspresponse,
			List<ProductData> target, LinkedHashMap<String, String> productNearByCodeList)
	{
		final String currencyIso = storeSessionFacade.getCurrentCurrency().getIsocode();
		SiteOneWsPriceResponseData cspResponseData = null;
		for (final ProductData productData : target)
		{
			final String productCode = productData.getCode();
			final String storeId = productNearByCodeList.get(productCode);
			if (storeId != null)
			{
				cspResponseData = mixedcartcspresponse.get(storeId);
			}
			if (cspResponseData != null && CollectionUtils.isNotEmpty(cspResponseData.getPrices()))
			{
				cspResponseData.getPrices().forEach(resp -> {
					if (productCode.equals(resp.getSkuId()))
					{
						final PriceData priceData = getPriceDataFactory().create(PriceDataType.BUY, new BigDecimal(resp.getPrice()),
								currencyIso);

						productData.setPrice(priceData);
					}
					final List<InventoryUOMData> inventoryUOMListData = productData.getSellableUoms();

					if (productData.getSellableUomsCount() != null && productData.getSellableUomsCount().intValue() > 0)
					{
						if (CollectionUtils.isNotEmpty(inventoryUOMListData))
						{
							siteOneProductFacade.getRetailAPIPriceByUOMPLPPriceRange(productData);
						}
					}

				});
			}
		}	
	}

	/**
	 * @param cspResponseData
	 * @param target
	 */
	private void populatorRetailtoTarget(SiteOneWsPriceResponseData cspResponseData, List<ProductData> target)
	{
		final String currencyIso = storeSessionFacade.getCurrentCurrency().getIsocode();

		cspResponseData.getPrices().forEach(resp -> {
			for (final ProductData productData : target)
			{
				final String productCode = productData.getCode();
				if (productCode.equals(resp.getSkuId()))
				{
					final PriceData priceData = getPriceDataFactory().create(PriceDataType.BUY, new BigDecimal(resp.getPrice()),
							currencyIso);

					productData.setPrice(priceData);

					final List<InventoryUOMData> inventoryUOMListData = productData.getSellableUoms();

					if (productData.getSellableUomsCount() != null && productData.getSellableUomsCount().intValue() > 0)
					{
						if (CollectionUtils.isNotEmpty(inventoryUOMListData))
						{
							siteOneProductFacade.getRetailAPIPriceByUOMPLPPriceRange(productData);
						}
					}
				}

			}
		});
	}

	/**
	 * @param mixedcartcspresponse
	 * @param target
	 */
	private void populatorMixedCSPtoTarget(final Map<String, SiteOneWsPriceResponseData> mixedcartcspresponse,
			final List<ProductData> target, final LinkedHashMap<String, String> productNearByCodeList)
	{
		final String currencyIso = storeSessionFacade.getCurrentCurrency().getIsocode();
		SiteOneWsPriceResponseData cspResponseData = null;
		for (final ProductData productData : target)
		{
			final String productCode = productData.getCode();
			final String storeId = productNearByCodeList.get(productCode);
			if (storeId != null)
			{
				cspResponseData = mixedcartcspresponse.get(storeId);
			}
			if (cspResponseData != null && CollectionUtils.isNotEmpty(cspResponseData.getPrices()))
			{
				cspResponseData.getPrices().forEach(resp -> {
					if (productCode.equals(resp.getSkuId()))
					{
						final PriceData priceData = getPriceDataFactory().create(PriceDataType.BUY, new BigDecimal(resp.getPrice()),
								currencyIso);

						productData.setCustomerPrice(priceData);
					}
					final List<InventoryUOMData> inventoryUOMListData = productData.getSellableUoms();

					if (productData.getSellableUomsCount() != null && productData.getSellableUomsCount().intValue() > 0)
					{
						if (CollectionUtils.isNotEmpty(inventoryUOMListData))
						{
							siteOneProductFacade.getProductByUOMPLPPriceRange(productData);
						}
					}

				});
			}
		}
	}

	/**
	 * @param cspResponseData
	 * @param target
	 */
	private void populatorCSPtoTarget(final SiteOneWsPriceResponseData cspResponseData, final List<ProductData> target)
	{
		final String currencyIso = storeSessionFacade.getCurrentCurrency().getIsocode();

		cspResponseData.getPrices().forEach(resp -> {
			for (final ProductData productData : target)
			{
				final String productCode = productData.getCode();
				if (productCode.equals(resp.getSkuId()))
				{
					final PriceData priceData = getPriceDataFactory().create(PriceDataType.BUY, new BigDecimal(resp.getPrice()),
							currencyIso);

					productData.setCustomerPrice(priceData);

					final List<InventoryUOMData> inventoryUOMListData = productData.getSellableUoms();

					if (productData.getSellableUomsCount() != null && productData.getSellableUomsCount().intValue() > 0)
					{
						if (CollectionUtils.isNotEmpty(inventoryUOMListData))
						{
							siteOneProductFacade.getProductByUOMPLPPriceRange(productData);
						}
					}
				}

			}
		});
	}

	/**
	 * @return the priceDataFactory
	 */
	public PriceDataFactory getPriceDataFactory()
	{
		return priceDataFactory;
	}

	/**
	 * @param priceDataFactory
	 *           the priceDataFactory to set
	 */
	public void setPriceDataFactory(final PriceDataFactory priceDataFactory)
	{
		this.priceDataFactory = priceDataFactory;
	}

	/**
	 * @return the storeSessionFacade
	 */
	public SiteOneStoreSessionFacade getStoreSessionFacade()
	{
		return storeSessionFacade;
	}

	/**
	 * @param storeSessionFacade
	 *           the storeSessionFacade to set
	 */
	public void setStoreSessionFacade(final SiteOneStoreSessionFacade storeSessionFacade)
	{
		this.storeSessionFacade = storeSessionFacade;
	}

	/**
	 * @return the searchResultProductConverter
	 */
	public Converter<SearchResultValueData, ProductData> getSearchResultProductConverter()
	{
		return searchResultProductConverter;
	}

	/**
	 * @param searchResultProductConverter
	 *           the searchResultProductConverter to set
	 */
	public void setSearchResultProductConverter(final Converter<SearchResultValueData, ProductData> searchResultProductConverter)
	{
		this.searchResultProductConverter = searchResultProductConverter;
	}

	/**
	 * @return the siteOneProductFacade
	 */
	public SiteOneProductFacade getSiteOneProductFacade()
	{
		return siteOneProductFacade;
	}

	/**
	 * @param siteOneProductFacade
	 *           the siteOneProductFacade to set
	 */
	public void setSiteOneProductFacade(final SiteOneProductFacade siteOneProductFacade)
	{
		this.siteOneProductFacade = siteOneProductFacade;
	}

}