package com.siteone.core.jobs.cleanup;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.siteone.core.services.impl.DefaultOldInvoiceAddressRemovalCronJobService;


public class OldInvoiceAddressRemovalCronjob extends AbstractJobPerformable<CronJobModel>
{

	private static final Logger LOG = Logger.getLogger(OldInvoiceAddressRemovalCronjob.class);
	private DefaultOldInvoiceAddressRemovalCronJobService oldInvoiceAddressRemovalCronJobService;

	@Override
	public PerformResult perform(final CronJobModel cronJob)
	{
		LOG.info("starting old invoice address removal job");
		final String currentType = StringUtils.EMPTY;
		try
		{
			oldInvoiceAddressRemovalCronJobService.removeOldInvoiceAddress();
			LOG.info("finished old invoice address removal job");
			return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
		}
		catch (final Exception e)
		{
			LOG.error("Exception Occurred while running old invoice address removal cronjob", e);
			return new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
		}

	}

	public DefaultOldInvoiceAddressRemovalCronJobService getOldInvoiceAddressRemovalCronJobService()
	{
		return oldInvoiceAddressRemovalCronJobService;
	}

	public void setOldInvoiceAddressRemovalCronJobService(
			final DefaultOldInvoiceAddressRemovalCronJobService oldInvoiceAddressRemovalCronJobService)
	{
		this.oldInvoiceAddressRemovalCronJobService = oldInvoiceAddressRemovalCronJobService;
	}
}
