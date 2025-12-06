package com.siteone.core.store.dao;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

public interface SiteOneStoreUtilityDao {

    PointOfServiceModel getStoreForSupplyChainNodeId(final String supplyChainNodeId);
    PointOfServiceModel getActiveNonActiveStoreForSupplyChainNode(final String supplyChainNodeId);
    PointOfServiceModel getStoreForPK(final String pk);
}
