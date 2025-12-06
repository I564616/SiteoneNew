package com.siteone.core.store.services.impl;


import com.siteone.core.store.dao.SiteOneStoreUtilityDao;
import com.siteone.core.store.services.SiteOneStoreUtilityService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

public class DefaultSiteOneStoreUtilityService implements SiteOneStoreUtilityService {


    private SiteOneStoreUtilityDao siteOneStoreUtilityDao;

    public SiteOneStoreUtilityDao getSiteOneStoreUtilityDao() {
        return siteOneStoreUtilityDao;
    }

    public void setSiteOneStoreUtilityDao(SiteOneStoreUtilityDao siteOneStoreUtilityDao) {
        this.siteOneStoreUtilityDao = siteOneStoreUtilityDao;
    }

    @Override
    public String getStoreForSupplyChainNodeId(final String supplyChainNodeId)
    {
        final PointOfServiceModel pointOfServiceModel = getSiteOneStoreUtilityDao().getStoreForSupplyChainNodeId(supplyChainNodeId);
        if (null != pointOfServiceModel)
        {
            return pointOfServiceModel.getStoreId();

        }
        return null;
    }


}
