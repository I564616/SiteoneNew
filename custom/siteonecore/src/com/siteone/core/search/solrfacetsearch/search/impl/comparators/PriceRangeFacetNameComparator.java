
package com.siteone.core.search.solrfacetsearch.search.impl.comparators;

import de.hybris.platform.solrfacetsearch.search.FacetValue;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.regex.Pattern;


/**
 * @author SNavamani
 *
 */
public class PriceRangeFacetNameComparator implements Comparator<FacetValue>
{

	@Override
	public int compare(final FacetValue value1, final FacetValue value2)
	{
		if (value1 == null || value2 == null)
		{
			return 0;
		}
		if (value1.getName() == null || value2.getName() == null)
		{
			return 0;
		}
		final String priceRangeRegExp = "\\$[0-9][0-9,]*(.[0-9]{1,2})?-\\$[0-9][0-9,]*(.[0-9]{1,2})?";
		if (Pattern.matches(priceRangeRegExp, value1.getName()) && Pattern.matches(priceRangeRegExp, value2.getName()))
		{
			String value1FromPrice = value1.getName().substring(1, value1.getName().indexOf("-"));
			String value2FromPrice = value2.getName().substring(1, value2.getName().indexOf("-"));
			value1FromPrice = value1FromPrice.replaceAll(",", "");
			value2FromPrice = value2FromPrice.replaceAll(",", "");
			final BigDecimal bdValue1FromPrice = new BigDecimal(value1FromPrice);
			final BigDecimal bdValue2FromPrice = new BigDecimal(value2FromPrice);

			return bdValue1FromPrice.compareTo(bdValue2FromPrice);
		}
		return value1.getName().compareTo(value2.getName());
	}

}
