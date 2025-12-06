package com.siteone.core.jobs.product;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

import com.siteone.core.model.ConvertedProductMappingCronJobModel;
import com.siteone.core.services.ConvertedProductCronJobService;


/**
 * @author SD02010
 *
 */
public class ConvertedProductMappingCronJob extends AbstractJobPerformable<ConvertedProductMappingCronJobModel>
{

	/**
	 * @return the convertedProductCronJobService
	 */
	public ConvertedProductCronJobService getConvertedProductCronJobService()
	{
		return convertedProductCronJobService;
	}

	/**
	 * @param convertedProductCronJobService
	 *           the convertedProductCronJobService to set
	 */
	public void setConvertedProductCronJobService(final ConvertedProductCronJobService convertedProductCronJobService)
	{
		this.convertedProductCronJobService = convertedProductCronJobService;
	}

	private ConvertedProductCronJobService convertedProductCronJobService;

	@Override
	public PerformResult perform(final ConvertedProductMappingCronJobModel mappingModel)
	{
		getConvertedProductCronJobService().convertedProductMapping(mappingModel);
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}



}