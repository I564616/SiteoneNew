package com.siteone.core.store.dao.impl;

import com.siteone.core.store.dao.SiteOneStoreUtilityDao;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

public class DefaultSiteOneStoreUtilityDao extends AbstractItemDao implements SiteOneStoreUtilityDao {

        @Override
        public PointOfServiceModel getStoreForSupplyChainNodeId(final String supplyChainNodeId)
        {
            final String queryString = "SELECT {p:" + PointOfServiceModel.PK + "}" + "FROM {" + PointOfServiceModel._TYPECODE
                    + " AS p} " + "WHERE " + "{p:" + PointOfServiceModel.SUPPLYCHAINNODEID + "}=?supplyChainNodeId AND {p:" + PointOfServiceModel.ISACTIVE
                    + "}=?isActive";

            final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
            query.addQueryParameter("supplyChainNodeId", supplyChainNodeId);
            query.addQueryParameter("isActive", Boolean.TRUE);
            final PointOfServiceModel result = getFlexibleSearchService().searchUnique(query);
            return result;
        }

        @Override
        public PointOfServiceModel getActiveNonActiveStoreForSupplyChainNode(final String supplyChainNodeId)
        {
            final String queryString = "SELECT {p:" + PointOfServiceModel.PK + "}" + "FROM {" + PointOfServiceModel._TYPECODE
                    + " AS p} " + "WHERE " + "{p:" + PointOfServiceModel.SUPPLYCHAINNODEID + "}=?supplyChainNodeId";

            final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
            query.addQueryParameter("supplyChainNodeId", supplyChainNodeId);
            final PointOfServiceModel result = getFlexibleSearchService().searchUnique(query);
            return result;
        }
        
        @Override
        public PointOfServiceModel getStoreForPK(final String pk) 
        {
      	  final String queryString = "SELECT {p:" + PointOfServiceModel.PK + "}" + "FROM {" + PointOfServiceModel._TYPECODE
                 + " AS p} " + "WHERE " + "{p:" + PointOfServiceModel.PK + "}=?pk";

         final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
         query.addQueryParameter("pk", pk);
         final PointOfServiceModel result = getFlexibleSearchService().searchUnique(query);
         return result;
        }
}
