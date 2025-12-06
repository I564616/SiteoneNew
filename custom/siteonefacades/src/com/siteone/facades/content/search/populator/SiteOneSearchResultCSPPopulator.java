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
import de.hybris.platform.commercefacades.search.converters.populator.ProductCategorySearchPagePopulator;
import de.hybris.platform.commerceservices.search.facetdata.ProductCategorySearchPageData;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.converters.Converters;
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

import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.facades.product.data.InventoryUOMData;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.integration.price.data.SiteOneWsPriceResponseData;


/**
 * @author KArasan
 *
 */
public class SiteOneSearchResultCSPPopulator<QUERY, STATE, RESULT, ITEM extends ProductData, SCAT, CATEGORY>
		extends ProductCategorySearchPagePopulator<QUERY, STATE, RESULT, ITEM, SCAT, CATEGORY>
{
	//
	private Converter<List<RESULT>, List<ITEM>> siteOneCSPListingConverter;
	
	private Converter<List<RESULT>, List<ITEM>> siteOneRetailListingConverter;

	private UserService userService;


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


	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "accProductFacade")
	private ProductFacade productFacade;

	private PriceDataFactory priceDataFactory;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Override
	public void populate(final ProductCategorySearchPageData<QUERY, RESULT, SCAT> source,
			final ProductCategorySearchPageData<STATE, ITEM, CATEGORY> target)
	{
		target.setFreeTextSearch(source.getFreeTextSearch());
		target.setCategoryCode(source.getCategoryCode());

		if (source.getSubCategories() != null)
		{
			target.setSubCategories(Converters.convertAll(source.getSubCategories(), getCategoryConverter()));
		}

		if (source.getBreadcrumbs() != null)
		{
			target.setBreadcrumbs(Converters.convertAll(source.getBreadcrumbs(), getBreadcrumbConverter()));
		}

		target.setCurrentQuery(getSearchStateConverter().convert(source.getCurrentQuery()));

		if (source.getFacets() != null)
		{
			target.setFacets(Converters.convertAll(source.getFacets(), getFacetConverter()));
		}

		target.setPagination(source.getPagination());

		if (source.getResults() != null)
		{
			target.setResults(Converters.convertAll(source.getResults(), getSearchResultProductConverter()));
		}

		target.setSorts(source.getSorts());

		if (source.getSpellingSuggestion() != null)
		{
			target.setSpellingSuggestion(getSpellingSuggestionConverter().convert(source.getSpellingSuggestion()));
		}

		target.setKeywordRedirectUrl(source.getKeywordRedirectUrl());

		if ((userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser())) || (null != storeSessionFacade.getSessionStore()
				&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("RetailBranches",
						storeSessionFacade.getSessionStore().getStoreId())
				&& StringUtils.isNotBlank(storeSessionFacade.getSessionStore().getCustomerRetailId())))
		{

			target.setResults(siteOneCSPListingConverter.convert(source.getResults()));

		}
	}


	/**
	 * @return the siteOneRetailListingConverter
	 */
	public Converter<List<RESULT>, List<ITEM>> getSiteOneRetailListingConverter()
	{
		return siteOneRetailListingConverter;
	}


	/**
	 * @param siteOneRetailListingConverter the siteOneRetailListingConverter to set
	 */
	public void setSiteOneRetailListingConverter(Converter<List<RESULT>, List<ITEM>> siteOneRetailListingConverter)
	{
		this.siteOneRetailListingConverter = siteOneRetailListingConverter;
	}


	/**
	 * @return the siteOneCSPListingConverter
	 */
	public Converter<List<RESULT>, List<ITEM>> getSiteOneCSPListingConverter()
	{
		return siteOneCSPListingConverter;
	}


	/**
	 * @param siteOneCSPListingConverter
	 *           the siteOneCSPListingConverter to set
	 */
	public void setSiteOneCSPListingConverter(final Converter<List<RESULT>, List<ITEM>> siteOneCSPListingConverter)
	{
		this.siteOneCSPListingConverter = siteOneCSPListingConverter;
	}


	/**
	 * @return the userService
	 */
	public UserService getUserService()
	{
		return userService;
	}


	/**
	 * @param userService
	 *           the userService to set
	 */
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}



}
