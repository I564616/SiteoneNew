/**
 *
 */
package com.siteone.facades.events.populators;

import de.hybris.platform.commerceservices.search.facetdata.BreadcrumbData;
import de.hybris.platform.commerceservices.search.facetdata.FacetData;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import com.siteone.facade.EventData;
import com.siteone.facade.EventSearchPageData;


/**
 * @author 1124932
 *
 */
public class EventSearchPagePopulator<QUERY, STATE, RESULT, ITEM extends EventData, SCAT, CATEGORY>
		implements Populator<EventSearchPageData<QUERY, RESULT>, EventSearchPageData<STATE, ITEM>>
{
	private Converter<QUERY, STATE> searchStateConverter;
	private Converter<BreadcrumbData<QUERY>, BreadcrumbData<STATE>> breadcrumbConverter;
	private Converter<FacetData<QUERY>, FacetData<STATE>> facetConverter;
	private Converter<RESULT, ITEM> searchResultContentConverter;



	@Override
	public void populate(final EventSearchPageData<QUERY, RESULT> source, final EventSearchPageData<STATE, ITEM> target)
			throws ConversionException
	{
		target.setFreeTextSearch(source.getFreeTextSearch());

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
			target.setResults(Converters.convertAll(source.getResults(), getSearchResultContentConverter()));
		}

	}


	/**
	 * @return the searchStateConverter
	 */
	public Converter<QUERY, STATE> getSearchStateConverter()
	{
		return searchStateConverter;
	}


	/**
	 * @param searchStateConverter
	 *           the searchStateConverter to set
	 */
	public void setSearchStateConverter(final Converter<QUERY, STATE> searchStateConverter)
	{
		this.searchStateConverter = searchStateConverter;
	}


	/**
	 * @return the breadcrumbConverter
	 */
	public Converter<BreadcrumbData<QUERY>, BreadcrumbData<STATE>> getBreadcrumbConverter()
	{
		return breadcrumbConverter;
	}



	/**
	 * @param breadcrumbConverter
	 *           the breadcrumbConverter to set
	 */
	public void setBreadcrumbConverter(final Converter<BreadcrumbData<QUERY>, BreadcrumbData<STATE>> breadcrumbConverter)
	{
		this.breadcrumbConverter = breadcrumbConverter;
	}



	/**
	 * @return the facetConverter
	 */
	public Converter<FacetData<QUERY>, FacetData<STATE>> getFacetConverter()
	{
		return facetConverter;
	}



	/**
	 * @param facetConverter
	 *           the facetConverter to set
	 */
	public void setFacetConverter(final Converter<FacetData<QUERY>, FacetData<STATE>> facetConverter)
	{
		this.facetConverter = facetConverter;
	}



	/**
	 * @return the searchResultContentConverter
	 */
	public Converter<RESULT, ITEM> getSearchResultContentConverter()
	{
		return searchResultContentConverter;
	}



	/**
	 * @param searchResultContentConverter
	 *           the searchResultContentConverter to set
	 */
	public void setSearchResultContentConverter(final Converter<RESULT, ITEM> searchResultContentConverter)
	{
		this.searchResultContentConverter = searchResultContentConverter;
	}

}
