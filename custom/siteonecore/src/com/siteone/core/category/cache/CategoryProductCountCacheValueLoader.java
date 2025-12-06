package com.siteone.core.category.cache;

import com.siteone.core.category.service.SiteOneCategoryService;
import de.hybris.platform.regioncache.CacheValueLoadException;
import de.hybris.platform.regioncache.CacheValueLoader;
import de.hybris.platform.regioncache.key.CacheKey;

/**
 * @author Abdul Rahman Sheikh M
 *
 */
public class CategoryProductCountCacheValueLoader implements CacheValueLoader
{
    private SiteOneCategoryService siteOneCategoryService;

    public SiteOneCategoryService getSiteOneCategoryService() {
        return siteOneCategoryService;
    }

    public void setSiteOneCategoryService(SiteOneCategoryService siteOneCategoryService) {
        this.siteOneCategoryService = siteOneCategoryService;
    }

    @Override
    public Object load(CacheKey cacheKey) throws CacheValueLoadException
    {
        if (cacheKey instanceof CategoryProductCountCacheKey)
        {
            final CategoryProductCountCacheKey key = (CategoryProductCountCacheKey) cacheKey;

            return getSiteOneCategoryService().getProductCountForCategory(key.getCode());
        }

        return null;
    }
}
