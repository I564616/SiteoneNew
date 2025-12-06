/**
 *
 */
package com.siteone.storefront.variants;

import de.hybris.platform.commercefacades.product.data.VariantMatrixElementData;

import java.util.Comparator;
import java.util.Map;


/**
 * @author ASaha
 *
 */
public interface VariantMatrixSortStrategy extends Comparator<VariantMatrixElementData>
{
	/**
	 * Map of attribute - comparator. Where attribute is variant attribute to compare for sorting
	 *
	 * @param comparators
	 */
	//void setComparators(Map<String, Comparator<String>> comparators);

	/**
	 * @param defaultComparator
	 *           for comparing variants' values - used as fallback, provide your own in {@link #setComparators(Map)}
	 */
	void setDefaultComparator(Comparator<String> defaultComparator);
}
