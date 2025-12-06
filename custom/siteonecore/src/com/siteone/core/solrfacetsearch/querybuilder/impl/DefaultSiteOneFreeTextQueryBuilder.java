/**
 *
 */
package com.siteone.core.solrfacetsearch.querybuilder.impl;

import de.hybris.platform.commerceservices.search.solrfacetsearch.querybuilder.impl.DefaultFreeTextQueryBuilder;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;

import org.apache.log4j.Logger;


/**
 * @author 1124932
 *
 */
public class DefaultSiteOneFreeTextQueryBuilder extends DefaultFreeTextQueryBuilder
{
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneFreeTextQueryBuilder.class);
	private String propertyName;

	@Override
	protected void addFreeTextQuery(final SearchQuery searchQuery, final IndexedProperty indexedProperty, final String fullText,
			final String[] textWords, final int boost)
	{
		addFreeTextQuery(searchQuery, indexedProperty, fullText, boost * 2.0d);

		if (textWords != null && textWords.length > 1)
		{
			for (final String word : textWords)
			{
				addFreeTextQuery(searchQuery, indexedProperty, word, boost);
			}
		}
	}

	@Override
	protected void addFreeTextQuery(final SearchQuery searchQuery, final IndexedProperty indexedProperty, final String value,
			final double boost)
	{
		final String field = indexedProperty.getName();
		if (!indexedProperty.isFacet())
		{
			if ("text".equalsIgnoreCase(indexedProperty.getType()))
			{
				addFreeTextQuery(searchQuery, field, value.toLowerCase(), "", boost);
				addFreeTextQuery(searchQuery, field, value.toLowerCase(), "*", boost / 2.0d);
			}
			else
			{
				addFreeTextQuery(searchQuery, field, value.toLowerCase(), "", boost);
				addFreeTextQuery(searchQuery, field, value.toLowerCase(), "*", boost / 2.0d);
			}
		}
		else
		{
			LOG.warn("Not searching " + indexedProperty
					+ ". Free text search not available in facet property. Configure an additional text property for searching.");
		}
	}

	/**
	 * @return the propertyName
	 */
	@Override
	public String getPropertyName()
	{
		return propertyName;
	}

	/**
	 * @param propertyName
	 *           the propertyName to set
	 */
	@Override
	public void setPropertyName(final String propertyName)
	{
		this.propertyName = propertyName;
	}
}
