/**
 *
 */
package com.siteone.core.search.solrfacetsearch.search.impl.comparators;

import de.hybris.platform.solrfacetsearch.search.FacetValue;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;


/**
 * @author SNavamani
 *
 */
public class UomFacetNameComparator implements Comparator<FacetValue>
{

	@Override
	public int compare(final FacetValue value1, final FacetValue value2)
	{

		final String unitKey1 = extractUnit(value1.getName());
		final String unitKey2 = extractUnit(value2.getName());
		String unitValue1;
		String unitValue2;
		if ( StringUtils.isNotEmpty(unitKey1)&& StringUtils.isNotEmpty(unitKey2))
		{
			if (unitKey1.equals(unitKey2))
			{
				final String unitValueRegExp = "[^,][0-9,]*(\\.[0-9]{1,})?";
				unitValue1 = value1.getName().replaceAll(unitKey1, "");
				unitValue2 = value2.getName().replaceAll(unitKey2, "");
				unitValue1 = unitValue1.trim();
				unitValue2 = unitValue2.trim();
				if (Pattern.matches(unitValueRegExp, unitValue1) && Pattern.matches(unitValueRegExp, unitValue2))
				{
					unitValue1 = unitValue1.replaceAll(",", "");
					unitValue2 = unitValue2.replaceAll(",", "");
					final BigDecimal bdUnitValue1 = new BigDecimal(unitValue1);
					final BigDecimal bdUnitValue2 = new BigDecimal(unitValue2);
					// Unit value comparison
					return bdUnitValue1.compareTo(bdUnitValue2);
				}
				else
				{
					// Generic comparison
					return value1.getName().compareTo(value2.getName());
				}
			}
			else
			{
				// Unit key comparison
				return unitKey1.compareTo(unitKey2);
			}
		}
		// Generic comparison
		return value1.getName().compareTo(value2.getName());
	}


	public String extractUnit(final String facetValueName)
	{
		final Pattern unitPattern = Pattern.compile("([a-zA-Z][[^0-9/,\\t\\n\\x0b\\r\\f][a-zA-Z]?]*)");
		final Matcher unitMatcher = unitPattern.matcher(facetValueName);
		String unitKey = StringUtils.EMPTY;
		while (unitMatcher.find())
		{
			unitKey = unitKey + unitMatcher.group();
		}
		unitKey = unitKey.trim();
		return unitKey;
	}


}
