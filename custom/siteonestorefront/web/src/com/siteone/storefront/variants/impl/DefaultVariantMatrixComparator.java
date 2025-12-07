/**
 *
 */
package com.siteone.storefront.variants.impl;

import java.util.Comparator;

import org.apache.commons.lang3.math.NumberUtils;


/**
 * @author ASaha
 *
 */
public class DefaultVariantMatrixComparator implements Comparator<String>
{
	@Override
	public int compare(final String variant1, final String variant2)
	{
		return Double.compare(Double.parseDouble(variant1), Double.parseDouble(variant2));
	}

	protected int getResult(final String variant1, final String variant2)
	{
		if (variant1 == null && variant2 == null)
		{
			return 0;
		}
		else if (variant1 == null)
		{
			return -1;
		}
		else if (variant2 == null)
		{
			return 1;
		}
		return variant1.toString().compareTo(variant2.toString());
	}
}
