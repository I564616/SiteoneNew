/**
 *
 */
package com.siteone.facades.sds.populator;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.search.facetdata.ProductCategorySearchPageData;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;


/**
 * @author 1229803
 *
 */
public class SDSProductCategorySearchPagePopulator<QUERY, STATE, RESULT, ITEM extends ProductData, SCAT, CATEGORY> implements
		Populator<ProductCategorySearchPageData<QUERY, RESULT, SCAT>, ProductCategorySearchPageData<STATE, ITEM, CATEGORY>>
{
	private Converter<RESULT, ITEM> sdsSearchResultProductConverter;

	@Override
	public void populate(final ProductCategorySearchPageData<QUERY, RESULT, SCAT> source,
			final ProductCategorySearchPageData<STATE, ITEM, CATEGORY> target) throws ConversionException
	{
		target.setFreeTextSearch(source.getFreeTextSearch());
		target.setPagination(source.getPagination());

		if (source.getResults() != null)
		{
			target.setResults(Converters.convertAll(source.getResults(), getSdsSearchResultProductConverter()));
		}

	}

	/**
	 * @return the sdsSearchResultProductConverter
	 */
	public Converter<RESULT, ITEM> getSdsSearchResultProductConverter()
	{
		return sdsSearchResultProductConverter;
	}

	/**
	 * @param sdsSearchResultProductConverter
	 *           the sdsSearchResultProductConverter to set
	 */
	public void setSdsSearchResultProductConverter(final Converter<RESULT, ITEM> sdsSearchResultProductConverter)
	{
		this.sdsSearchResultProductConverter = sdsSearchResultProductConverter;
	}


}
