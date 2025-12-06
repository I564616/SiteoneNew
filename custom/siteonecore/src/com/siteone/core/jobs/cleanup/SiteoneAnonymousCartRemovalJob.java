package com.siteone.core.jobs.cleanup;

import com.siteone.core.services.impl.DefaultAnonymousCartRemovalCronJobService;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


public class SiteoneAnonymousCartRemovalJob extends AbstractJobPerformable<CronJobModel> {

    private static final Logger LOG = Logger.getLogger(SiteoneAnonymousCartRemovalJob.class);
    private DefaultAnonymousCartRemovalCronJobService siteoneAnonymousCartRemovalCronJobService;
    @Override
    public PerformResult perform(final CronJobModel cronJob)
    {
        LOG.info("starting empty anonymous cart removal job");
        String currentType = StringUtils.EMPTY;
        try
        {
            siteoneAnonymousCartRemovalCronJobService.removeObsoleteEmptyAnonymousCarts();
            LOG.info("finished empty anonymous cart removal job");
            return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
        }
        catch (final Exception e)
        {
            LOG.error("Exception Occurred while running empty anonymous cart removal job", e);
            return new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
        }

    }

    public DefaultAnonymousCartRemovalCronJobService getSiteoneAnonymousCartRemovalCronJobService() {
        return siteoneAnonymousCartRemovalCronJobService;
    }

    public void setSiteoneAnonymousCartRemovalCronJobService(DefaultAnonymousCartRemovalCronJobService siteoneAnonymousCartRemovalCronJobService) {
        this.siteoneAnonymousCartRemovalCronJobService = siteoneAnonymousCartRemovalCronJobService;
    }
}
