package com.siteone.core.services.impl;

import com.siteone.core.cronjob.dao.WebServiceAuditLogCleanUpCronJobDao;
import com.siteone.core.cronjob.dao.impl.DefaultWebServiceAuditLogCleanUpCronJobDao;
import com.siteone.core.jobs.audit.WebServiceAuditLogCleanUpCronJob;
import com.siteone.core.model.WebserviceAuditLogModel;
import com.siteone.core.services.WebServiceAuditLogCleanUpCronJobService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.Config;

import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DefaultWebServiceAuditLogCleanUpCronJobService implements WebServiceAuditLogCleanUpCronJobService {

    private WebServiceAuditLogCleanUpCronJobDao webServiceAuditLogCleanUpCronJobDao;
    private ModelService modelService;
    private static final Logger LOG = Logger.getLogger(DefaultWebServiceAuditLogCleanUpCronJobService.class);
    
    private static final Integer NO_OF_DAYS = Config.getInt("webservice.audit.history.delete.days", 7);   
    private static final Integer BATCH_SIZE = Config.getInt("webservice.audit.history.delete.batch.size", 10000);

    @Override
    public boolean  cleanUpObsoleteLogs() 
    {
   	 try 
   	 {
          final Calendar calendar = Calendar.getInstance();
          calendar.setTime(new Date());
          calendar.add(Calendar.DAY_OF_YEAR, -NO_OF_DAYS);
          final Date dateToRemoveRecords = calendar.getTime();

          purgeLogs(dateToRemoveRecords);
          return true;
          
      } 
   	 catch (Exception e) 
   	 {
          LOG.error("An error occurred while cleaning up obsolete logs: ", e);
      }
      return false;
        
    }
    
    public void purgeLogs(final Date dateToRemoveRecords) {
       boolean purging = true;

       while (purging) {
      	 List<WebserviceAuditLogModel> webserviceAuditLogList = webServiceAuditLogCleanUpCronJobDao.getAuditLogsByDate(dateToRemoveRecords, BATCH_SIZE);

           LOG.info("Audit logs removal batch size: " + webserviceAuditLogList.size());

           if (webserviceAuditLogList.size() < BATCH_SIZE) {
               purging = false;
           }
           

           modelService.removeAll(webserviceAuditLogList);
       }
   }

    public WebServiceAuditLogCleanUpCronJobDao getWebServiceAuditLogCleanUpCronJobDao() {
        return webServiceAuditLogCleanUpCronJobDao;
    }

    public void setWebServiceAuditLogCleanUpCronJobDao(WebServiceAuditLogCleanUpCronJobDao webServiceAuditLogCleanUpCronJobDao) {
        this.webServiceAuditLogCleanUpCronJobDao = webServiceAuditLogCleanUpCronJobDao;
    }

    public ModelService getModelService() {
        return modelService;
    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }
}
