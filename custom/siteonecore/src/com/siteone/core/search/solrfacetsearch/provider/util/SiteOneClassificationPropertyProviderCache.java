/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.util;

import de.hybris.platform.catalog.jalo.classification.ClassAttributeAssignment;
import de.hybris.platform.catalog.jalo.classification.util.FeatureContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author i849388
 *
 */
public class SiteOneClassificationPropertyProviderCache
{

	private String baseProductCode;

	private boolean haveAllProperties = false;

	private final Map<String, List<ClassAttributeAssignment>> indexedPropertyAssignmentCache = new HashMap<String, List<ClassAttributeAssignment>>();

	private final List<ClassAttributeAssignment> allClassAttributeAssignments = new ArrayList<ClassAttributeAssignment>();

	private final List<FeatureContainer> productFeatureContainerCache = new ArrayList<FeatureContainer>();

	public void addIndexedProperty(final String indexedPropertyName, final List<ClassAttributeAssignment> classAssignments)
	{

		this.indexedPropertyAssignmentCache.put(indexedPropertyName, classAssignments);
		this.allClassAttributeAssignments.addAll(classAssignments);

	}

	public List<ClassAttributeAssignment> getIndexedPropertyAssignments(final String indexedPropertyName)
	{
		return this.indexedPropertyAssignmentCache.get(indexedPropertyName);
	}

	public boolean hasIndexedPropertyName(final String indexedPropertyName)
	{
		return this.indexedPropertyAssignmentCache.containsKey(indexedPropertyName);
	}

	public List<ClassAttributeAssignment> getAllClassAttributeAssignments()
	{
		return this.allClassAttributeAssignments;
	}

	/**
	 * @return the productFeatureContainerCache
	 */
	public List<FeatureContainer> getProductFeatureContainerCache()
	{
		return productFeatureContainerCache;
	}

	/**
	 * @return the baseProductCode
	 */
	public String getBaseProductCode()
	{
		return baseProductCode;
	}

	/**
	 * @param baseProductCode
	 *           the baseProductCode to set
	 */
	public void setBaseProductCode(final String baseProductCode)
	{
		this.baseProductCode = baseProductCode;
	}

	/**
	 * @return the haveAllProperties
	 */
	public boolean haveAllProperties()
	{
		return haveAllProperties;
	}

	/**
	 * @param haveAllProperties
	 *           the haveAllProperties to set
	 */
	public void setHaveAllProperties(final boolean haveAllProperties)
	{
		this.haveAllProperties = haveAllProperties;
	}



}
