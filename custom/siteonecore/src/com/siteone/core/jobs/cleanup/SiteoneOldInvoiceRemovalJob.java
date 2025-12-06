package com.siteone.core.jobs.cleanup;

import com.siteone.core.services.impl.DefaultSiteoneOldInvoiceRemovalCronJobService;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import org.apache.log4j.Logger;

public class SiteoneOldInvoiceRemovalJob extends AbstractJobPerformable<CronJobModel> {

    private static final Logger LOG = Logger.getLogger(SiteoneOldInvoiceRemovalJob.class);

    private DefaultSiteoneOldInvoiceRemovalCronJobService siteoneOldInvoiceRemovalCronJobService;
    @Override
    public PerformResult perform(final CronJobModel cronJob) {
        LOG.info("starting old Invoice removal job");
        try
        {
            boolean success = siteoneOldInvoiceRemovalCronJobService.removeOldInvoices();
            if (success){
                LOG.info("finished old Invoice removal job");
                return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
            }else {
                return new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
            }
        }
        catch (final Exception e)
        {
            LOG.error("Exception Occurred while running old Invoice removal job", e);
            return new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
        }
    }


    public DefaultSiteoneOldInvoiceRemovalCronJobService getsiteoneOldInvoiceRemovalCronJobService() {
        return siteoneOldInvoiceRemovalCronJobService;
    }

    public void setsiteoneOldInvoiceRemovalCronJobService(DefaultSiteoneOldInvoiceRemovalCronJobService siteoneOldInvoiceRemovalCronJobService) {
        this.siteoneOldInvoiceRemovalCronJobService = siteoneOldInvoiceRemovalCronJobService;
    }
}
