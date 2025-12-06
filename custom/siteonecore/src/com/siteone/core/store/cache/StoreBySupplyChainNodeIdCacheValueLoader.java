package com.siteone.core.store.cache;

import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.core.store.services.SiteOneStoreUtilityService;
import de.hybris.platform.core.Registry;
import de.hybris.platform.regioncache.CacheValueLoadException;
import de.hybris.platform.regioncache.CacheValueLoader;
import de.hybris.platform.regioncache.key.CacheKey;

public class StoreBySupplyChainNodeIdCacheValueLoader implements CacheValueLoader {

    private SiteOneStoreUtilityService siteOneStoreUtilityService;

    public SiteOneStoreUtilityService getSiteOneStoreUtilityService() {
        return siteOneStoreUtilityService;
    }

    public void setSiteOneStoreUtilityService(SiteOneStoreUtilityService siteOneStoreUtilityService) {
        this.siteOneStoreUtilityService = siteOneStoreUtilityService;
    }

    @Override
    public Object load(CacheKey cacheKey) throws CacheValueLoadException
    {
        if (cacheKey instanceof StoreBySupplyChainNodeIdCacheKey)
        {
            final StoreBySupplyChainNodeIdCacheKey key = (StoreBySupplyChainNodeIdCacheKey) cacheKey;

            return getSiteOneStoreUtilityService().getStoreForSupplyChainNodeId(key.getCode());
        }

        return null;
    }


}
