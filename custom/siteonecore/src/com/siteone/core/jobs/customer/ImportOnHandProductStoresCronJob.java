
package com.siteone.core.jobs.customer;

import com.siteone.core.services.ImportOnHandProductStoresCronJobService;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

import org.apache.log4j.Logger;

import com.siteone.core.model.ImportOnHandProductStoresCronJobModel;



/**
 * @author nmangal
 * */
public class ImportOnHandProductStoresCronJob extends AbstractJobPerformable<ImportOnHandProductStoresCronJobModel>
{
    private static final Logger LOG = Logger.getLogger(ImportOnHandProductStoresCronJob.class);
    private ImportOnHandProductStoresCronJobService importOnHandProductStoresCronJobService;

    /*
     * (non-Javadoc)
     *
     * @see
     * de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable#perform(de.hybris.platform.cronjob.model.CronJobModel)
     */
    @Override
    public PerformResult perform(final ImportOnHandProductStoresCronJobModel model)
    {
        try
        {
            getImportOnHandProductStoresCronJobService().importOnHandProductStore(model);
        }
        catch (final Exception exception)
        {
            LOG.error("Exception occured in Import OnHand Product Store CronJob ", exception);
        }
        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    public ImportOnHandProductStoresCronJobService getImportOnHandProductStoresCronJobService() {
        return importOnHandProductStoresCronJobService;
    }

    public void setImportOnHandProductStoresCronJobService(ImportOnHandProductStoresCronJobService importOnHandProductStoresCronJobService) {
        this.importOnHandProductStoresCronJobService = importOnHandProductStoresCronJobService;
    }
}
