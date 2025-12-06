/**
 *
 */
package com.siteone.facades.sds.populator;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.util.List;


/**
 * @author 1229803
 *
 */
public class SDSSearchResultProductPopulator implements Populator<SearchResultValueData, ProductData>
{

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final SearchResultValueData source, final ProductData target) throws ConversionException
	{
		target.setCode(this.<String> getValue(source, "socode"));
		target.setProductLongDesc(this.<String> getValue(source, "soproductLongDesc"));
		target.setSds(this.<List> getValue(source, "sosds"));
		target.setLabel(this.<List> getValue(source, "solabel"));
		target.setItemNumber(this.<String> getValue(source, "soitemNumber"));
		target.setProductShortDesc(this.<String> getValue(source, "soproductShortDesc"));
	}

	protected <T> T getValue(final SearchResultValueData source, final String propertyName)
	{
		if (source.getValues() == null)
		{
			return null;
		}

		// DO NOT REMOVE the cast (T) below, while it should be unnecessary it is required by the javac compiler
		return (T) source.getValues().get(propertyName);
	}

}
