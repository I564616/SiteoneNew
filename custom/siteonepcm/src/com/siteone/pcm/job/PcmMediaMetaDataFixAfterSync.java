/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2017 SAP SE
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * Hybris ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with SAP Hybris.
 */
package com.siteone.pcm.job;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.Date;



import org.apache.log4j.Logger;

import com.siteone.pcm.constants.SiteonepcmConstants;



/**
 *
 */
public class PcmMediaMetaDataFixAfterSync extends AbstractJobPerformable<CronJobModel>
{

	private static final Logger logger = Logger.getLogger(PcmMediaMetaDataFixAfterSync.class);

	private static ModelService modelService;	
	private static  MediaService mediaService;
	private static CatalogVersionService catalogVersionService;
	private static FlexibleSearchService flexibleSearchService;

	@Override
	public PerformResult perform(final CronJobModel cronJob)
	{
		try
		{
			final String mediaModelWithInvalidMimeQuery = "SELECT  {m.pk} FROM {Media as m},{catalogversion as cv},{catalog as c} WHERE  {m.mime} =  'application/octet-stream'  AND {m.catalogversion}  =  {cv.pk} AND {cv.catalog} =  {c.pk} AND {cv.version} = 'Online' AND {c.id} =  'siteoneProductCatalog'";
			final SearchResult searchResult = getFlexibleSearchService().search(mediaModelWithInvalidMimeQuery);
			final String productCode = "";
			if (null != searchResult && searchResult.getCount() != 0)
			{
				for (int i = 0; i < searchResult.getCount(); i++)
				{
					try
					{
						final MediaModel media = (MediaModel) searchResult.getResult().get(i);
						MediaModel MediaModel = getMediaService().getMedia(getCatalogVersionService().getCatalogVersion("siteoneProductCatalog", "Staged"), media.getCode());
						media.setMime(MediaModel.getMime());
						getModelService().save(media);
					}
					catch (final Exception ex)

					{
						logger.error("Remove Duplicate Gallery Image For PRODUCT [****FAILED****]=>" + productCode);
						logger.error("exception message--> " + ex);
						continue;
					}

				}
			}
		}
		catch (final Exception ex)
		{
			logger.error("exception message" + ex);
			cronJob.setStatus(CronJobStatus.ABORTED);
			cronJob.setResult(CronJobResult.FAILURE);
			return new PerformResult(CronJobResult.FAILURE, CronJobStatus.ABORTED);
		}
		final Date date = new Date();
		cronJob.setEndTime(date);
		cronJob.setLastExecutionTime(date);

		getModelService().save(cronJob);
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	public ModelService getModelService()
	{
		modelService = (ModelService) Registry.getApplicationContext().getBean(SiteonepcmConstants.MODELSERVICE);
		return PcmMediaMetaDataFixAfterSync.modelService;
	}

	
	
	public MediaService getMediaService()
	{
		PcmMediaMetaDataFixAfterSync.mediaService = (MediaService) Registry.getApplicationContext().getBean(SiteonepcmConstants.MEDIASERVICE);
		return PcmMediaMetaDataFixAfterSync.mediaService;
	}
	
	public CatalogVersionService getCatalogVersionService()
	{
		PcmMediaMetaDataFixAfterSync.catalogVersionService = (CatalogVersionService) Registry.getApplicationContext().getBean(SiteonepcmConstants.CATALOGVERSIONSERVICE);
		return PcmMediaMetaDataFixAfterSync.catalogVersionService;
	}

	public FlexibleSearchService getFlexibleSearchService()
	{
		flexibleSearchService = (FlexibleSearchService) Registry.getApplicationContext()
				.getBean(SiteonepcmConstants.FLEXIBLESEARCHSERVICE);
		return PcmMediaMetaDataFixAfterSync.flexibleSearchService;
	}

	public void setModelService(ModelService modelService) {
		PcmMediaMetaDataFixAfterSync.modelService = modelService;
	}

	public void setMediaService(MediaService mediaService) {
		PcmMediaMetaDataFixAfterSync.mediaService = mediaService;
	}

	public void setCatalogVersionService(CatalogVersionService catalogVersionService) {
		PcmMediaMetaDataFixAfterSync.catalogVersionService = catalogVersionService;
	}

	public void setFlexibleSearchService(FlexibleSearchService flexibleSearchService) {
		PcmMediaMetaDataFixAfterSync.flexibleSearchService = flexibleSearchService;
	}
	
}
