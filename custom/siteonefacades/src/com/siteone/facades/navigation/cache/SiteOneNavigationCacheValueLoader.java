/**
 *
 */
package com.siteone.facades.navigation.cache;

import de.hybris.platform.regioncache.CacheValueLoadException;
import de.hybris.platform.regioncache.CacheValueLoader;
import de.hybris.platform.regioncache.key.CacheKey;

import com.siteone.facades.category.SiteOneCategoryFacade;


/**
 * @author 1099417
 *
 */
public class SiteOneNavigationCacheValueLoader implements CacheValueLoader
{
	private SiteOneCategoryFacade siteOneCategoryFacade;

	/**
	 * @return the siteOneCategoryFacade
	 */
	public SiteOneCategoryFacade getSiteOneCategoryFacade()
	{
		return siteOneCategoryFacade;
	}


	/*
	 * @param siteOneCategoryFacade the siteOneCategoryFacade to set
	 */
	public void setSiteOneCategoryFacade(final SiteOneCategoryFacade siteOneCategoryFacade)
	{
		this.siteOneCategoryFacade = siteOneCategoryFacade;
	}

	@Override
	public Object load(final CacheKey cacheKey) throws CacheValueLoadException
	{
		if (cacheKey instanceof SiteOneNavigationCacheKey)
		{
			final SiteOneNavigationCacheKey siteOneNavigationCacheKey = (SiteOneNavigationCacheKey) cacheKey;

			return getSiteOneCategoryFacade().getNavigationCategories(siteOneNavigationCacheKey.getCode());
		}

		return null;
	}


}
