package com.siteone.core.jobs.cleanup;

import com.siteone.core.services.impl.DefaultSiteoneOldOrderRemovalCronJobService;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import org.apache.log4j.Logger;

public class SiteoneOldOrderRemovalJob extends AbstractJobPerformable<CronJobModel> {

    private static final Logger LOG = Logger.getLogger(SiteoneOldOrderRemovalJob.class);

    private DefaultSiteoneOldOrderRemovalCronJobService siteoneOldOrderRemovalCronJobService;
    @Override
    public PerformResult perform(final CronJobModel cronJob) {
        LOG.info("starting old orders removal job");
        try
        {
            boolean success = siteoneOldOrderRemovalCronJobService.removeOldOrders();
            if (success){
                LOG.info("finished old orders removal job");
                return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
            }else {
                return new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
            }
        }
        catch (final Exception e)
        {
            LOG.error("Exception Occurred while running old orders removal job", e);
            return new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
        }
    }


    public DefaultSiteoneOldOrderRemovalCronJobService getSiteoneOldOrderRemovalCronJobService() {
        return siteoneOldOrderRemovalCronJobService;
    }

    public void setSiteoneOldOrderRemovalCronJobService(DefaultSiteoneOldOrderRemovalCronJobService siteoneOldOrderRemovalCronJobService) {
        this.siteoneOldOrderRemovalCronJobService = siteoneOldOrderRemovalCronJobService;
    }
}
