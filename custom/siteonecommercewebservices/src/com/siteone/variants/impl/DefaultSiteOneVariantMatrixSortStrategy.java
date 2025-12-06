/**
 *
 */
package com.siteone.variants.impl;

import de.hybris.platform.commercefacades.product.data.VariantMatrixElementData;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.siteone.variants.VariantMatrixSortStrategy;


/**
 * @author 191179
 *
 */
public class DefaultSiteOneVariantMatrixSortStrategy implements VariantMatrixSortStrategy
{
	private List<String> sortingFieldsOrder;
	//private Map<String, Comparator<String>> comparators;
	private Comparator<String> defaultComparator;

	protected List<String> getSortingFieldsOrder()
	{
		return sortingFieldsOrder;
	}

	/*
	 * protected Map<String, Comparator<String>> getComparators() { return comparators; }
	 *
	 * @Override
	 *
	 * @Required public void setComparators(final Map<String, Comparator<String>> comparators) { this.comparators =
	 * comparators; }
	 */

	protected Comparator<String> getDefaultComparator()
	{
		return defaultComparator;
	}

	@Override
	public void setDefaultComparator(final Comparator<String> defaultComparator)
	{
		this.defaultComparator = defaultComparator;
	}

	@Override
	public int compare(final VariantMatrixElementData product1, final VariantMatrixElementData product2)
	{
		final int result = getComparator(null).compare(getVariantValue(product1), getVariantValue(product2));
		if (result != 0)
		{
			return result;
		}

		return -1;
	}

	protected String getVariantValue(final VariantMatrixElementData variant)
	{
		final Pattern p = Pattern.compile("(\\d+(\\.\\d+)?)");
		final Matcher m = p.matcher(variant.getVariantValueCategory().getName());
		while (m.find())
		{
			return m.group();
		}
		return null;
	}

	protected Comparator<String> getComparator(final String field)
	{
		return new DefaultVariantMatrixComparator();
	}
}