/**
 *
 */
package com.siteone.core.sds.service.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commerceservices.search.facetdata.ProductCategorySearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SearchQueryPageableData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchRequest;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchResponse;
import de.hybris.platform.commerceservices.search.solrfacetsearch.impl.DefaultSolrProductSearchService;

import org.springframework.core.convert.converter.Converter;

import com.siteone.core.services.SiteOneSDSProductSearchService;




/**
 * @author 1229803
 *
 */
public class DefaultSiteOneSDSProductSearchService<ITEM> extends DefaultSolrProductSearchService implements
		SiteOneSDSProductSearchService<SolrSearchQueryData, ITEM, ProductCategorySearchPageData<SolrSearchQueryData, ITEM, CategoryModel>>
{
	private Converter<SearchQueryPageableData<SolrSearchQueryData>, SolrSearchRequest> sdsSearchQueryPageableConverter;
	private Converter<SolrSearchRequest, SolrSearchResponse> sdsSearchRequestConverter;
	private Converter<SolrSearchResponse, ProductCategorySearchPageData<SolrSearchQueryData, ITEM, CategoryModel>> sdsSearchResponseConverter;

	@Override
	public ProductCategorySearchPageData<SolrSearchQueryData, ITEM, CategoryModel> sdsSearchAgain(
			final SolrSearchQueryData searchQueryData, final PageableData pageableData)
	{
		return sdsDoSearch(searchQueryData, pageableData);
	}

	/**
	 * @param searchQueryData
	 * @param pageableData
	 * @return
	 */
	private ProductCategorySearchPageData<SolrSearchQueryData, ITEM, CategoryModel> sdsDoSearch(
			final SolrSearchQueryData searchQueryData, final PageableData pageableData)
	{
		validateParameterNotNull(searchQueryData, "SearchQueryData cannot be null");

		// Create the SearchQueryPageableData that contains our parameters
		final SearchQueryPageableData<SolrSearchQueryData> searchQueryPageableData = buildSearchQueryPageableData(searchQueryData,
				pageableData);

		// Build up the search request
		final SolrSearchRequest solrSearchRequest = getSdsSearchQueryPageableConverter().convert(searchQueryPageableData);

		// Execute the search
		final SolrSearchResponse solrSearchResponse = getSdsSearchRequestConverter().convert(solrSearchRequest);

		// Convert the response
		return getSdsSearchResponseConverter().convert(solrSearchResponse);

	}

	/**
	 * @return the sdsSearchQueryPageableConverter
	 */
	public Converter<SearchQueryPageableData<SolrSearchQueryData>, SolrSearchRequest> getSdsSearchQueryPageableConverter()
	{
		return sdsSearchQueryPageableConverter;
	}

	/**
	 * @param sdsSearchQueryPageableConverter
	 *           the sdsSearchQueryPageableConverter to set
	 */
	public void setSdsSearchQueryPageableConverter(
			final Converter<SearchQueryPageableData<SolrSearchQueryData>, SolrSearchRequest> sdsSearchQueryPageableConverter)
	{
		this.sdsSearchQueryPageableConverter = sdsSearchQueryPageableConverter;
	}

	/**
	 * @return the sdsSearchRequestConverter
	 */
	public Converter<SolrSearchRequest, SolrSearchResponse> getSdsSearchRequestConverter()
	{
		return sdsSearchRequestConverter;
	}

	/**
	 * @param sdsSearchRequestConverter
	 *           the sdsSearchRequestConverter to set
	 */
	public void setSdsSearchRequestConverter(final Converter<SolrSearchRequest, SolrSearchResponse> sdsSearchRequestConverter)
	{
		this.sdsSearchRequestConverter = sdsSearchRequestConverter;
	}

	/**
	 * @return the sdsSearchResponseConverter
	 */
	public Converter<SolrSearchResponse, ProductCategorySearchPageData<SolrSearchQueryData, ITEM, CategoryModel>> getSdsSearchResponseConverter()
	{
		return sdsSearchResponseConverter;
	}

	/**
	 * @param sdsSearchResponseConverter
	 *           the sdsSearchResponseConverter to set
	 */
	public void setSdsSearchResponseConverter(
			final Converter<SolrSearchResponse, ProductCategorySearchPageData<SolrSearchQueryData, ITEM, CategoryModel>> sdsSearchResponseConverter)
	{
		this.sdsSearchResponseConverter = sdsSearchResponseConverter;
	}






}
