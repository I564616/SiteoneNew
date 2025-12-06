/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.util;

import de.hybris.platform.classification.features.FeatureList;


/**
 *
 */
public class SiteOneUnifiedClassificationPropertyProviderCache
{
	private String baseProductCode;

	private FeatureList featureList = new FeatureList();

	private String leafCategoryPrefix;

	/**
	 * @return the leafCategoryPrefix
	 */
	public String getLeafCategoryPrefix()
	{
		return leafCategoryPrefix;
	}

	/**
	 * @param leafCategoryPrefix
	 *           the LeafCategoryPrefix to set
	 */
	public void setLeafCategoryPrefix(String leafCategoryPrefix)
	{
		this.leafCategoryPrefix = leafCategoryPrefix;
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
	 * @return the featureList
	 */
	public FeatureList getFeatureList()
	{
		return featureList;
	}

	/**
	 * @param featureList
	 *           the featureList to set
	 */
	public void setFeatureList(final FeatureList featureList)
	{
		this.featureList = featureList;
	}


}
