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

import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.media.MediaContainerModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.siteone.pcm.constants.SiteonepcmConstants;



/**
 *
 */
public class RemoveDuplicateMediaAssociationToProductJob extends AbstractJobPerformable<CronJobModel>
{

	private static final Logger logger = Logger.getLogger(RemoveDuplicateMediaAssociationToProductJob.class);

	private static ModelService modelService;
	private static ProductService productService;
	private static FlexibleSearchService flexibleSearchService;

	@Override
	public PerformResult perform(final CronJobModel cronJob)
	{
		try
		{
			logger.info("Removed Duplicate Gallery Image For PRODUCT Start");
			final String productWithImagesQuery = "SELECT {BaseProd.PK} FROM   {Product AS BaseProd},        {CatalogVersion AS CatVersion},        {catalog} WHERE  {BaseProd:catalogVersion} =  {CatVersion:PK} AND    {CatVersion:version}      =  'Staged' AND    {CatVersion:catalog}      =  {catalog:PK} AND    {catalog:id}              =  'siteoneProductCatalog'";
			final SearchResult searchResult = getFlexibleSearchService().search(productWithImagesQuery);
			final String productCode = "";
			if (null != searchResult && searchResult.getCount() != 0)
			{
				for (int i = 0; i < searchResult.getCount(); i++)
				{
					try
					{
						final ProductModel baseProduct = (ProductModel) searchResult.getResult().get(i);
						if (baseProduct.getGalleryImages() != null)
						{
							final Map<String, MediaContainerModel> galleryImagesMap = new HashMap<String, MediaContainerModel>();
							for (final MediaContainerModel mediaContainer : baseProduct.getGalleryImages())
							{
								if (mediaContainer.getMedias() != null)
								{
									if (mediaContainer.getConversionStatus().getCode().equalsIgnoreCase("EMPTY"))
									{
									getModelService().remove(mediaContainer);
								}
								else {
									galleryImagesMap.put(mediaContainer.getQualifier(), mediaContainer);
								}
							}
								
							}
							final List<MediaContainerModel> galleryImages = new ArrayList<MediaContainerModel>(
									galleryImagesMap.values());
							/*logger.info("Removed Duplicate Gallery Image For PRODUCT =>" + baseProduct.getCode());*/
							baseProduct.setGalleryImages(galleryImages);
							getModelService().save(baseProduct);
						}

						if (baseProduct.getData_sheet() != null)
						{
							final Map<String, MediaModel> dataSheetMap = new HashMap<String, MediaModel>();
							for (final MediaModel datasheet : baseProduct.getData_sheet())
							{
								if (datasheet != null)
								{
									dataSheetMap.put(datasheet.getCode(), datasheet);
								}
							}
							final List<MediaModel> dataSheets = new ArrayList<MediaModel>(dataSheetMap.values());
							/*logger.info("Removed Duplicate Gallery Image For PRODUCT =>" + baseProduct.getCode());*/
							baseProduct.setData_sheet(dataSheets);
							getModelService().save(baseProduct);
						}
					}
					catch (final Exception ex)

					{
						logger.error("Remove Duplicate Gallery Image For PRODUCT [****FAILED****]=>" + productCode);
						logger.error("exception message--> " + ex);
						continue;
					}

				}
			}
			logger.info("Removed Duplicate Gallery Image For PRODUCT End's");
			//RemoveDettachedMediaJob
			logger.info("Remove Dettached Media Image For PRODUCT Start");
			final String mediasWithNoContainerQuery = "SELECT distinct {m.pk} FROM {Media as m},{catalogversion as cv},{catalog as c},{mediaformat as mf},{mediacontainer as mc} WHERE  {m.mime} =  'image/jpeg' AND {m.mediaformat} IS NOT NULL AND {m.mediacontainer} IS NULL AND {m.catalogversion}  =  {cv.pk} AND {cv.catalog} =  {c.pk} AND {cv.version} = 'Staged' AND {c.id} =  'siteoneProductCatalog' AND {m.mediaformat} = {mf.pk} AND {mc.catalogversion} =  {cv.pk}";
			final SearchResult searchResultset = getFlexibleSearchService().search(mediasWithNoContainerQuery);
			final String productMediaCode = "";
			if (null != searchResultset && searchResultset.getCount() != 0)
			{
				for (int i = 0; i < searchResultset.getCount(); i++)
				{
					try
					{
						final MediaModel media = (MediaModel) searchResultset.getResult().get(i);
						getModelService().remove(media);

					}
					catch (final Exception ex)

					{
						logger.error("Gallery Image TO PRODUCT [****ASSOCIATION FAILED****]=>" + productMediaCode);
						logger.error("exception message--> " + ex);
						continue;
					}

				}
			}
			logger.info("Remove Dettached Media Image For PRODUCT End's");	
			logger.info("Remove Empty Media  contatiner Start's");
		final String ContainerQueryWithNomedias = "SELECT   {mc.pk} FROM {MediaContainer as mc},{Catalog as c}, {CatalogVersion as cv} WHERE    {mc.catalogversion} = {cv.pk} AND  {cv.catalog}    = {c.pk} AND  {c.id}  = 'siteoneProductCatalog' AND  {cv.version}='Staged' AND  NOT EXISTS ({{ SELECT 1 FROM {Media as m} WHERE {m.mediacontainer}= {mc.pk} AND  {m.catalogversion}  = {cv.pk} AND  {m.catalog} = {c.pk} }})";
		final SearchResult searchResultContainerset = getFlexibleSearchService().search(ContainerQueryWithNomedias);
		if (null != searchResultContainerset && searchResultContainerset.getCount() != 0)
		{
			for (int i = 0; i < searchResultContainerset.getCount(); i++)
			{
				try
				{
					final MediaContainerModel mediaContainer = (MediaContainerModel) searchResultContainerset.getResult().get(i);
					getModelService().remove(mediaContainer);

				}
				catch (final Exception ex)

				{
		
					logger.error("exception message--> " + ex);
					continue;
				}

			}
		}
		logger.info("Remove Empty Media  contatiner End's");
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
		return RemoveDuplicateMediaAssociationToProductJob.modelService;
	}

	public ProductService getProductService()
	{
		productService = (ProductService) Registry.getApplicationContext().getBean(SiteonepcmConstants.PRODUCTSERVICE);
		return RemoveDuplicateMediaAssociationToProductJob.productService;
	}

	public FlexibleSearchService getFlexibleSearchService()
	{
		flexibleSearchService = (FlexibleSearchService) Registry.getApplicationContext()
				.getBean(SiteonepcmConstants.FLEXIBLESEARCHSERVICE);
		return RemoveDuplicateMediaAssociationToProductJob.flexibleSearchService;
	}

	public void setProductService(final ProductService productService)

	{
		RemoveDuplicateMediaAssociationToProductJob.productService = productService;
	}
}
