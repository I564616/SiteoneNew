package com.siteone.core.store.cache;

import de.hybris.platform.regioncache.region.impl.DefaultCacheRegion;

import com.siteone.core.store.services.impl.DefaultSiteOneStoreUtilityService;

public class DefaultSiteoneStoreFinderCachingService extends DefaultSiteOneStoreUtilityService {


    private StoreBySupplyChainNodeIdCacheValueLoader storeBySupplyChainNodeIdCacheValueLoader;
    private DefaultCacheRegion storeBySupplyChainNodeIdCacheRegion;

    public StoreBySupplyChainNodeIdCacheValueLoader getStoreBySupplyChainNodeIdCacheValueLoader() {
        return storeBySupplyChainNodeIdCacheValueLoader;
    }

    public void setStoreBySupplyChainNodeIdCacheValueLoader(StoreBySupplyChainNodeIdCacheValueLoader storeBySupplyChainNodeIdCacheValueLoader) {
        this.storeBySupplyChainNodeIdCacheValueLoader = storeBySupplyChainNodeIdCacheValueLoader;
    }

    public DefaultCacheRegion getStoreBySupplyChainNodeIdCacheRegion() {
        return storeBySupplyChainNodeIdCacheRegion;
    }

    public void setStoreBySupplyChainNodeIdCacheRegion(DefaultCacheRegion storeBySupplyChainNodeIdCacheRegion) {
        this.storeBySupplyChainNodeIdCacheRegion = storeBySupplyChainNodeIdCacheRegion;
    }


     @Override
    public String getStoreForSupplyChainNodeId(String code)
    {
        try
        {
            StoreBySupplyChainNodeIdCacheKey cacheKey = new StoreBySupplyChainNodeIdCacheKey(code);
            String store = (String) getStoreBySupplyChainNodeIdCacheRegion().getWithLoader(cacheKey, getStoreBySupplyChainNodeIdCacheValueLoader());
            return store;
        }
        catch (Exception e)
        {
            return super.getStoreForSupplyChainNodeId(code);
        }
    }
}
