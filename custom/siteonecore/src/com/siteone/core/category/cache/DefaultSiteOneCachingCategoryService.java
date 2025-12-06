package com.siteone.core.category.cache;

import com.siteone.core.category.service.impl.DefaultSiteOneCategoryService;

import org.apache.log4j.Logger;

import de.hybris.platform.regioncache.region.impl.DefaultCacheRegion;

/**
 * @author Abdul Rahman Sheikh M
 *
 */
public class DefaultSiteOneCachingCategoryService extends DefaultSiteOneCategoryService
{

    private CategoryProductCountCacheValueLoader categoryProductCountCacheValueLoader;
    private DefaultCacheRegion categoryProductCountCacheRegion;

    public CategoryProductCountCacheValueLoader getCategoryProductCountCacheValueLoader() {
        return categoryProductCountCacheValueLoader;
    }

    public void setCategoryProductCountCacheValueLoader(CategoryProductCountCacheValueLoader categoryProductCountCacheValueLoader) {
        this.categoryProductCountCacheValueLoader = categoryProductCountCacheValueLoader;
    }

    public DefaultCacheRegion getCategoryProductCountCacheRegion() {
        return categoryProductCountCacheRegion;
    }

    public void setCategoryProductCountCacheRegion(DefaultCacheRegion categoryProductCountCacheRegion) {
        this.categoryProductCountCacheRegion = categoryProductCountCacheRegion;
    }

    @Override
    public Integer getProductCountForCategory(String code)
    {
        try
        {
            CategoryProductCountCacheKey cacheKey = new CategoryProductCountCacheKey(code);
            Integer count = (Integer) getCategoryProductCountCacheRegion().getWithLoader(cacheKey, getCategoryProductCountCacheValueLoader());
            return count;
        }
        catch (Exception e)
        {
            return super.getProductCountForCategory(code);
        }
    }
}
