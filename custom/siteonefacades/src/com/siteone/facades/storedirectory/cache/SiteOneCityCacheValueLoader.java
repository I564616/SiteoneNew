/**
 *
 */
package com.siteone.facades.storedirectory.cache;

import de.hybris.platform.regioncache.CacheValueLoadException;
import de.hybris.platform.regioncache.CacheValueLoader;
import de.hybris.platform.regioncache.key.CacheKey;

import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;


/**
 * @author 1230514
 *
 */
public class SiteOneCityCacheValueLoader implements CacheValueLoader
{
	private SiteOneStoreFinderFacade siteOneStoreFinderFacade;

	public SiteOneStoreFinderFacade getSiteOneStoreFinderFacade()
	{
		return siteOneStoreFinderFacade;
	}

	public void setSiteOneStoreFinderFacade(final SiteOneStoreFinderFacade storeFinderFacade)
	{
		this.siteOneStoreFinderFacade = storeFinderFacade;
	}




	@Override
	public Object load(final CacheKey cacheKey) throws CacheValueLoadException
	{
		if (cacheKey instanceof SiteOneStoreDirectoryCacheKey)
		{
			final SiteOneStoreDirectoryCacheKey siteOneCityCacheKey = (SiteOneStoreDirectoryCacheKey) cacheKey;

			return getSiteOneStoreFinderFacade().getStoreCitiesForState(siteOneCityCacheKey.getCode());
		}

		return null;
	}

}
