/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.impl.CommerceClassificationPropertyValueProvider;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;


/**
 * @author i849388
 *
 */
public class SiteOneConsolidateSeriesValueProvider extends CommerceClassificationPropertyValueProvider
{

	private SiteoneClassificationUtils siteoneClassificationUtils;
	private static final Logger LOG = Logger.getLogger(SiteOneConsolidateSeriesValueProvider.class);
	private final String CATEGORYSERIES = "Series";
	private final String INDEXATTRIBUTE = "consolidateSeriesCategories";

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		final Collection<FieldValue> fieldValues = new ArrayList<>();
		if (indexedProperty.getName().equalsIgnoreCase(INDEXATTRIBUTE))
		{
			final String consolidateSeriesCategories = siteoneClassificationUtils.getClassificationAttributeValue(model,
					CATEGORYSERIES);
			LOG.info("consolidateSeriesCategories: " + consolidateSeriesCategories);
			if (consolidateSeriesCategories != null)
			{
				fieldValues.addAll(siteoneClassificationUtils.createFieldValue(consolidateSeriesCategories, indexedProperty));
			}
		}
		return fieldValues;
	}

	/**
	 * @return the siteoneClassificationUtils
	 */
	public SiteoneClassificationUtils getSiteoneClassificationUtils()
	{
		return siteoneClassificationUtils;
	}


	/**
	 * @param siteoneClassificationUtils
	 *           the siteoneClassificationUtils to set
	 */
	public void setSiteoneClassificationUtils(final SiteoneClassificationUtils siteoneClassificationUtils)
	{
		this.siteoneClassificationUtils = siteoneClassificationUtils;
	}
}