package com.siteone.core.jobs.audit;

import com.siteone.core.services.WebServiceAuditLogCleanUpCronJobService;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import org.apache.log4j.Logger;

public class WebServiceAuditLogCleanUpCronJob  extends AbstractJobPerformable<CronJobModel> {

    private WebServiceAuditLogCleanUpCronJobService webServiceAuditLogCleanUpCronJobService;
    private static final Logger LOG = Logger.getLogger(WebServiceAuditLogCleanUpCronJob.class);

    @Override
    public PerformResult perform(final CronJobModel cronJob)
    {
   	 LOG.info("starting webservice audit clean up job");
        try
        {
      	  boolean success = webServiceAuditLogCleanUpCronJobService.cleanUpObsoleteLogs();
      	  if (success) {
              LOG.info("Finished webservice audit log job successfully");
              return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
          } else {
              
              return new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
          }

        }
        catch (final Exception e)
        {
            LOG.error("Exception Occurred while deleting audit records for webservice audit" , e);
            return new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
        }

    }

    public WebServiceAuditLogCleanUpCronJobService getWebServiceAuditLogCleanUpCronJobService() {
        return webServiceAuditLogCleanUpCronJobService;
    }

    public void setWebServiceAuditLogCleanUpCronJobService(WebServiceAuditLogCleanUpCronJobService webServiceAuditLogCleanUpCronJobService) {
        this.webServiceAuditLogCleanUpCronJobService = webServiceAuditLogCleanUpCronJobService;
    }
}
