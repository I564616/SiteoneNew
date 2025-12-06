/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.commerceservices.search.solrfacetsearch.populators.CommerceIndexedPropertyPopulator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.model.config.SolrIndexedPropertyModel;


/**
 * @author 1091124
 *
 */
public class SiteOneCommerceIndexedTypePropertyPopulator extends CommerceIndexedPropertyPopulator
{

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.commerceservices.search.solrfacetsearch.populators.CommerceIndexedPropertyPopulator#populate(de
	 * .hybris.platform.solrfacetsearch.model.config.SolrIndexedPropertyModel,
	 * de.hybris.platform.solrfacetsearch.config.IndexedProperty)
	 */
	@Override
	public void populate(final SolrIndexedPropertyModel property, final IndexedProperty indexedProperty) throws ConversionException
	{
		super.populate(property, indexedProperty);
		indexedProperty.setClassAttributeAssignments(property.getClassAttributeAssignments());
	}

}
