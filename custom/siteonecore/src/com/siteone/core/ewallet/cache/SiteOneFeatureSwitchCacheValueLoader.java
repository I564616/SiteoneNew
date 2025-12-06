package com.siteone.core.ewallet.cache;

import de.hybris.platform.regioncache.CacheValueLoadException;
import de.hybris.platform.regioncache.CacheValueLoader;
import de.hybris.platform.regioncache.key.CacheKey;

import com.siteone.core.ewallet.service.SiteOneEwalletService;


/**
 * @author KARASAN
 *
 */
public class SiteOneFeatureSwitchCacheValueLoader implements CacheValueLoader
{
	private SiteOneEwalletService siteOneEwalletService;

	public SiteOneEwalletService getSiteOneEwalletService()
	{
		return siteOneEwalletService;
	}

	public void setSiteOneEwalletService(final SiteOneEwalletService siteOneEwalletService)
	{
		this.siteOneEwalletService = siteOneEwalletService;
	}

	@Override
	public Object load(final CacheKey cacheKey) throws CacheValueLoadException
	{
		if (cacheKey instanceof SiteOneFeatureSwitchCacheKey)
		{
			final SiteOneFeatureSwitchCacheKey switchKey = (SiteOneFeatureSwitchCacheKey) cacheKey;

			return getSiteOneEwalletService().getValueForSwitch(switchKey.getKey());
		}

		return null;
	}
}
