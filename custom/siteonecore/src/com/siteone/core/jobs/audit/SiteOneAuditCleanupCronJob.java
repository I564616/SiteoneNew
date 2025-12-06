package com.siteone.core.jobs.audit;

import com.siteone.core.jobs.audit.service.SiteOneExtendedWriteAuditGateway;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.util.Config;

import java.util.Arrays;
import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * CronJob periodically clear audit log for configured tables
 */
public class SiteOneAuditCleanupCronJob extends AbstractJobPerformable<CronJobModel>
{

	private static final Logger LOG = Logger.getLogger(SiteOneAuditCleanupCronJob.class);


	private SiteOneExtendedWriteAuditGateway auditGateway;

	private final String defaultTypeToClearAudit = "PRODUCT,USER,CART";
	private final int defaultAuditDays = 30;

	@Override
	public PerformResult perform(final CronJobModel cronJob)
	{
		LOG.info("started audit clean up job");
		String currentType = StringUtils.EMPTY;
		try
		{
			final int keepAuditDays = Config.getInt("audit.retain.days", defaultAuditDays);
			final int keepLinkToPayAuditDays = Config.getInt("audit.linktopay.days", defaultAuditDays);
			List<String> tables = Arrays.asList("USERS4SN","USERGROUPS5SN","USERAUDIT6SN","PGRELS201SN","ABSTRACTCONTACT26SN","ADDRESSES23SN","PAYMENTINFOS42SN","CMSSITE1064SN", "LINKTOPAYAUDITLOG");
			final Calendar currentDate = Calendar.getInstance();
			currentDate.set(Calendar.HOUR_OF_DAY, 0);
			currentDate.set(Calendar.MINUTE, 0);
			currentDate.set(Calendar.SECOND, 0);
			

			for (final String typeName : tables)
			{
				if(typeName.equalsIgnoreCase("LINKTOPAYAUDITLOG"))
				{
					currentDate.add(Calendar.DATE, -keepLinkToPayAuditDays);
				}
				else
				{
					currentDate.add(Calendar.DATE, -keepAuditDays);
				}
				currentType = typeName;
				final int deletedRecordsCount = auditGateway.removeAuditRecordsForTypeAndDate(typeName, currentDate.getTime());
				LOG.info(deletedRecordsCount + " Records deleted for audit table " + typeName);

			}
			LOG.info("finished audit clean up job");
			return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
		}
		catch (final Exception e)
		{
			LOG.error("Exception Occurred while deleting audit records for type " + currentType, e);
			return new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
		}

	}

	public void setAuditGateway(final SiteOneExtendedWriteAuditGateway auditGateway)
	{
		this.auditGateway = auditGateway;
	}

}