package com.siteone.core.cronjob.dao.impl;

import com.siteone.core.cronjob.dao.WebServiceAuditLogCleanUpCronJobDao;
import com.siteone.core.model.WebserviceAuditLogModel;
import de.hybris.platform.promotionengineservices.model.PromotionSourceRuleModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.util.Config;
import org.apache.commons.collections4.CollectionUtils;


import java.util.Date;
import java.util.List;


public class DefaultWebServiceAuditLogCleanUpCronJobDao extends AbstractItemDao implements WebServiceAuditLogCleanUpCronJobDao {


    @Override
    public List<WebserviceAuditLogModel> getAuditLogsByDate(final Date date, final int batchSize) {
       final FlexibleSearchQuery query = new FlexibleSearchQuery(
               "SELECT DISTINCT {w:pk} FROM {webserviceauditlog AS w} WHERE {w:creationtime} < ?date");
       query.addQueryParameter("date", date);
       query.setCount(batchSize);
       query.setStart(0);

     
       return getFlexibleSearchService().<WebserviceAuditLogModel>search(query).getResult();
   }
}
