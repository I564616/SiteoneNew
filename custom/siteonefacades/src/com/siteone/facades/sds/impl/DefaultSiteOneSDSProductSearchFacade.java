/**
 *
 */
package com.siteone.facades.sds.impl;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commercefacades.search.solrfacetsearch.impl.DefaultSolrProductSearchFacade;
import de.hybris.platform.commerceservices.search.ProductSearchService;
import de.hybris.platform.commerceservices.search.facetdata.ProductCategorySearchPageData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.threadcontext.ThreadContextService;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.Assert;

import com.siteone.core.services.SiteOneSDSProductSearchService;
import com.siteone.facades.sds.SiteOneSDSProductSearchFacade;
import com.siteone.core.model.SiteOneCuratedPLPHSProductComponentModel;


/**
 * @author 1229803
 *
 */
public class DefaultSiteOneSDSProductSearchFacade<ITEM extends ProductData> extends DefaultSolrProductSearchFacade
		implements SiteOneSDSProductSearchFacade<ITEM>
{
	private SiteOneSDSProductSearchService siteOneSDSProductSearchService;
	private ProductSearchService<SolrSearchQueryData, SearchResultValueData, ProductCategorySearchPageData<SolrSearchQueryData, SearchResultValueData, CategoryModel>> siteoneProductSearchService;
	private Converter<ProductCategorySearchPageData<SolrSearchQueryData, SearchResultValueData, CategoryModel>, ProductCategorySearchPageData<SearchStateData, ITEM, CategoryData>> siteoneProductCategorySearchPageConverter;
	private Converter<ProductCategorySearchPageData<SolrSearchQueryData, SearchResultValueData, CategoryModel>, ProductCategorySearchPageData<SearchStateData, ITEM, CategoryData>> sdsProductCategorySearchPageConverter;

	@Override
	public ProductSearchPageData<SearchStateData, ITEM> sdsTextSearch(final SearchStateData searchState,
			final PageableData pageableData)
	{
		Assert.notNull(searchState, "SearchStateData must not be null.");
		return getThreadContextService().executeInContext(
				new ThreadContextService.Executor<ProductSearchPageData<SearchStateData, ITEM>, ThreadContextService.Nothing>()
				{
					@Override
					public ProductSearchPageData<SearchStateData, ITEM> execute()
					{
						return getSdsProductCategorySearchPageConverter().convert(
								(ProductCategorySearchPageData<SolrSearchQueryData, SearchResultValueData, CategoryModel>) getSiteOneSDSProductSearchService()
										.sdsSearchAgain(decodeState(searchState, null), pageableData));
					}
				});
	}

	@Override
	public ProductSearchPageData<SearchStateData, ITEM> curatedPLPSearch(final SearchStateData searchState,
			final PageableData pageableData, final String code)
	{
		Assert.notNull(searchState, "SearchStateData must not be null.");

		return getThreadContextService().executeInContext(
				new ThreadContextService.Executor<ProductSearchPageData<SearchStateData, ITEM>, ThreadContextService.Nothing>()
				{
					@Override
					public ProductSearchPageData<SearchStateData, ITEM> execute()
					{

						return getSiteoneProductCategorySearchPageConverter().convert(
								(ProductCategorySearchPageData<SolrSearchQueryData, SearchResultValueData, CategoryModel>) getProductSearchService()
										.searchAgain(decodeState(searchState, code), pageableData));
					}
				});
	}
	
	@Override
	public List<ProductData> sortByItemNumber(final SiteOneCuratedPLPHSProductComponentModel component, 
			final ProductSearchPageData<SearchStateData, ProductData> searchResults)
	{
		List<ProductData> products = new ArrayList<>();
		products = searchResults.getResults();
		String productCodes = component.getProductCodes();
		final List<String> listOfProductCodes = Arrays.asList(productCodes.split(","));
		List<ProductData> productsNewList = new ArrayList<>();
		for(String productCode : listOfProductCodes) {
			for(ProductData productData : products) {
				if(productCode.equalsIgnoreCase(productData.getItemNumber())) {
					productsNewList.add(productData);
					continue;
				}
			}
		}
		return productsNewList;
	}

	/**
	 * @return the siteOneSDSProductSearchService
	 */
	public SiteOneSDSProductSearchService getSiteOneSDSProductSearchService()
	{
		return siteOneSDSProductSearchService;
	}

	/**
	 * @param siteOneSDSProductSearchService
	 *           the siteOneSDSProductSearchService to set
	 */
	public void setSiteOneSDSProductSearchService(final SiteOneSDSProductSearchService siteOneSDSProductSearchService)
	{
		this.siteOneSDSProductSearchService = siteOneSDSProductSearchService;
	}

	/**
	 * @return the sdsProductCategorySearchPageConverter
	 */
	public Converter<ProductCategorySearchPageData<SolrSearchQueryData, SearchResultValueData, CategoryModel>, ProductCategorySearchPageData<SearchStateData, ITEM, CategoryData>> getSdsProductCategorySearchPageConverter()
	{
		return sdsProductCategorySearchPageConverter;
	}

	/**
	 * @param sdsProductCategorySearchPageConverter
	 *           the sdsProductCategorySearchPageConverter to set
	 */
	public void setSdsProductCategorySearchPageConverter(
			final Converter<ProductCategorySearchPageData<SolrSearchQueryData, SearchResultValueData, CategoryModel>, ProductCategorySearchPageData<SearchStateData, ITEM, CategoryData>> sdsProductCategorySearchPageConverter)
	{
		this.sdsProductCategorySearchPageConverter = sdsProductCategorySearchPageConverter;
	}

	public ProductSearchService<SolrSearchQueryData, SearchResultValueData, ProductCategorySearchPageData<SolrSearchQueryData, SearchResultValueData, CategoryModel>> getSiteoneProductSearchService()
	{
		return siteoneProductSearchService;
	}

	/**
	 * @param siteoneProductSearchService
	 *           the siteoneProductSearchService to set
	 */
	public void setSiteoneProductSearchService(
			final ProductSearchService<SolrSearchQueryData, SearchResultValueData, ProductCategorySearchPageData<SolrSearchQueryData, SearchResultValueData, CategoryModel>> siteoneProductSearchService)
	{
		this.siteoneProductSearchService = siteoneProductSearchService;
	}

	/**
	 * @return the siteoneProductCategorySearchPageConverter
	 */
	public Converter<ProductCategorySearchPageData<SolrSearchQueryData, SearchResultValueData, CategoryModel>, ProductCategorySearchPageData<SearchStateData, ITEM, CategoryData>> getSiteoneProductCategorySearchPageConverter()
	{
		return siteoneProductCategorySearchPageConverter;
	}

	/**
	 * @param siteoneProductCategorySearchPageConverter
	 *           the siteoneProductCategorySearchPageConverter to set
	 */
	public void setSiteoneProductCategorySearchPageConverter(
			final Converter<ProductCategorySearchPageData<SolrSearchQueryData, SearchResultValueData, CategoryModel>, ProductCategorySearchPageData<SearchStateData, ITEM, CategoryData>> siteoneProductCategorySearchPageConverter)
	{
		this.siteoneProductCategorySearchPageConverter = siteoneProductCategorySearchPageConverter;
	}





	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.siteone.facades.sds.SiteOneSDSProductSearchFacade#sdsTextSearch(de.hybris.platform.commercefacades.search.data
	 * .SearchStateData, de.hybris.platform.commerceservices.search.pagedata.PageableData)
	 */

}

