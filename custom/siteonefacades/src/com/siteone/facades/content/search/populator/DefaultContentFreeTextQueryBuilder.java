/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.siteone.facades.content.search.populator;


import de.hybris.platform.commerceservices.search.solrfacetsearch.querybuilder.impl.AbstractFreeTextQueryBuilder;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.IndexedType;
import de.hybris.platform.solrfacetsearch.search.RawQuery;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import de.hybris.platform.solrfacetsearch.search.SearchQuery.Operator;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.util.ClientUtils;


/**
 * @author 965504
 *
 *         Default implementation of FreeTextQueryBuilder provides for simple querying of a property.
 */
public class DefaultContentFreeTextQueryBuilder extends AbstractFreeTextQueryBuilder
{
	private static final Logger LOG = Logger.getLogger(DefaultContentFreeTextQueryBuilder.class);

	private String propertyName;
	private int boost;
	private List<String> attributes;

	protected String getPropertyName()
	{
		return propertyName;
	}

	public void setPropertyName(final String propertyName)
	{
		this.propertyName = propertyName;
	}

	protected int getBoost()
	{
		return boost;
	}

	public void setBoost(final int boost)
	{
		this.boost = boost;
	}

	@Override
	public void addFreeTextQuery(final SearchQuery searchQuery, final String fullText, final String[] textWords)
	{
		final IndexedType indexedType = searchQuery.getIndexedType();
		if (indexedType != null)
		{
			final IndexedProperty indexedProperty = indexedType.getIndexedProperties().get(getPropertyName());
			if (indexedProperty != null)
			{
				this.addFreeTextQuery(searchQuery, indexedProperty, fullText, textWords, getBoost());
			}
		}
	}

	@Override
	protected void addFreeTextQuery(final SearchQuery searchQuery, final IndexedProperty indexedProperty, final String fullText,
			final String[] textWords, final int boost)
	{
		this.addFreeTextQuery(searchQuery, indexedProperty, fullText, boost * 2.0d);

		if (textWords != null && textWords.length > 1)
		{
			for (final String word : textWords)
			{
				this.addFreeTextQuery(searchQuery, indexedProperty, word, boost);
			}
		}
	}

	@Override
	protected void addFreeTextQuery(final SearchQuery searchQuery, final IndexedProperty indexedProperty, final String value,
			final double boost)
	{
		final String field = indexedProperty.getName();

		if (field.equalsIgnoreCase("sopage"))
		{
			for (final String attribute : attributes)
			{
				if (!indexedProperty.isFacet())
				{
					if ("text".equalsIgnoreCase(indexedProperty.getType()))
					{
						addFreeTextQuery(searchQuery, field + "_" + attribute + "_" + indexedProperty.getType(), value.toLowerCase(),
								"", boost);
						addFreeTextQuery(searchQuery, field + "_" + attribute + "_" + indexedProperty.getType(), value.toLowerCase(),
								"*", boost / 2.0d);
						addFreeTextQuery(searchQuery, field + "_" + attribute + "_" + indexedProperty.getType(), value.toLowerCase(),
								"~", boost / 4.0d);
					}
					else
					{
						addFreeTextQuery(searchQuery, field + "_" + attribute + "_" + indexedProperty.getType(), value.toLowerCase(),
								"", boost);
						addFreeTextQuery(searchQuery, field + "_" + attribute + "_" + indexedProperty.getType(), value.toLowerCase(),
								"*", boost / 2.0d);
					}
				}
				else
				{
					LOG.warn("Not searching " + indexedProperty
							+ ". Free text search not available in facet property. Configure an additional text property for searching.");
				}
			}
		}
		else
		{
			if (!indexedProperty.isFacet())
			{
				if ("text".equalsIgnoreCase(indexedProperty.getType()))
				{
					addFreeTextQuery(searchQuery, field, value.toLowerCase(), "", boost);
					addFreeTextQuery(searchQuery, field, value.toLowerCase(), "*", boost / 2.0d);
					addFreeTextQuery(searchQuery, field, value.toLowerCase(), "~", boost / 4.0d);
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

	}

	@Override
	protected void addFreeTextQuery(final SearchQuery searchQuery, final String field, final String value, final String suffixOp,
			final double boost)
	{
		if (StringUtils.isNotEmpty(suffixOp))
		{
			final RawQuery rawQuery = new RawQuery(field, suffixOp + ClientUtils.escapeQueryChars(value) + suffixOp + "^" + boost,
					Operator.OR);
			searchQuery.addRawQuery(rawQuery);
		}
		else
		{
			final RawQuery rawQuery = new RawQuery(field, ClientUtils.escapeQueryChars(value) + suffixOp + "^" + boost, Operator.OR);
			searchQuery.addRawQuery(rawQuery);
		}

	}

	/**
	 * @return the attributes
	 */
	public List<String> getAttributes()
	{
		return attributes;
	}

	/**
	 * @param attributes
	 *           the attributes to set
	 */
	public void setAttributes(final List<String> attributes)
	{
		this.attributes = attributes;
	}
}
