
package com.siteone.core.search.solrfacetsearch.search.impl.comparators;

import de.hybris.platform.solrfacetsearch.search.FacetValue;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.regex.Pattern;


/**
 * @author SNavamani
 *
 */
public class FacetDisplayNameIgnoreCaseComparator implements Comparator<FacetValue>
{

	@Override
	public int compare(FacetValue value1, FacetValue value2) {
		if (value1 != null && value2 != null) {
			return !StringUtils.isEmpty(value1.getDisplayName()) && !StringUtils.isEmpty(value2.getDisplayName()) ? value1.getDisplayName().compareToIgnoreCase(value2.getDisplayName()) : value1.getName().compareToIgnoreCase(value2.getName());
		} else {
			return 0;
		}
	}

}
