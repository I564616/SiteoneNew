
package com.siteone.core.search.solrfacetsearch.search.impl.comparators;

import de.hybris.platform.solrfacetsearch.search.FacetValue;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.regex.Pattern;


/**
 * @author SNavamani
 *
 */
public class FacetNameIgnoreCaseComparator implements Comparator<FacetValue>
{

	@Override
	public int compare(FacetValue value1, FacetValue value2) {
		return value1 != null && value2 != null ? value1.getName().compareToIgnoreCase(value2.getName()) : 0;
	}

}
