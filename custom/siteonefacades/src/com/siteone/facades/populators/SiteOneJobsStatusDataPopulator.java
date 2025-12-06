/**
 * 
 */
package com.siteone.facades.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import com.siteone.facade.customer.info.CustomerCreditInfoData;
import com.siteone.facade.customer.info.CustomerInfoStatusData;
import com.siteone.facade.customer.info.CustomerRewardsPointsInfoData;
import com.siteone.facade.customer.info.CustomerSalesInfoData;
import com.siteone.facade.customer.info.SiteOneCustInfoData;
import com.siteone.facade.reports.SiteOneJobsStatusData;
import com.siteone.integration.customer.info.SiteOneCustInfoResponeData;

/**
 * @author nmangal
 *
 */
public class SiteOneJobsStatusDataPopulator implements Populator<CronJobModel, SiteOneJobsStatusData>
{


	@Override
	public void populate(final CronJobModel source, final SiteOneJobsStatusData target) throws ConversionException
	{

		if (null != source)
		{

			target.setJobName(source.getCode());
			target.setLastEndTime(source.getEndTime());
			target.setLastStartTime(source.getStartTime());
			target.setLastStatus(source.getStatus().toString());

		}

	}
}
