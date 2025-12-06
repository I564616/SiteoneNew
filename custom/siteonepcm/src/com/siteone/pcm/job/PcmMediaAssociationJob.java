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

import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.siteone.pcm.constants.SiteonepcmConstants;

public class PcmMediaAssociationJob extends AbstractJobPerformable<CronJobModel> {

	private static final Logger logger = Logger.getLogger(PcmMediaAssociationJob.class);
	private static  ModelService modelService;
	private  static ProductService productService;
	private  static  FlexibleSearchService flexibleSearchService;

	@Override
	public PerformResult perform(final CronJobModel cronJob) {
		
		try {
		
			final String productWithNoPictureQuery = "SELECT {BaseProd.PK} FROM {Product AS BaseProd},{CatalogVersion AS CatVersion},{catalog} "
					+ "WHERE  {BaseProd:Picture} IS NULL AND {BaseProd:catalogVersion} = {CatVersion:PK} "
					+ "AND {CatVersion:version} = 'Staged' AND {CatVersion:catalog} = {catalog:PK} "
					+ "AND {catalog:id} = 'siteoneProductCatalog'";
			final SearchResult searchResult = getFlexibleSearchService().search(productWithNoPictureQuery);
			String productCode = "";

			if (null != searchResult && searchResult.getCount() != 0) {
				for (int i = 0; i < searchResult.getCount(); i++) {

					final ProductModel productModel = (ProductModel) searchResult.getResult().get(i);
					productCode = productModel.getCode();
					if (productModel.getGalleryImages() != null && productModel.getGalleryImages().size() > 0) {
						try {
							final MediaContainerModel productMediaContainer = productModel.getGalleryImages().get(0);
							final Iterator<MediaModel> productMediaIterator = productMediaContainer.getMedias()
									.iterator();
							while (productMediaIterator.hasNext()) {
								final MediaModel mediaModel = productMediaIterator.next();
								if (mediaModel != null && mediaModel.getCode().contains("_300Wx300H")
										&& productModel.getGalleryImages() != null
										&& productModel.getGalleryImages().size() > 0) {
									logger.info("Picture TO Product =>" + productCode);
									productModel.setPicture(mediaModel);
								}
							}
							getModelService().save(productModel);
						} catch (final Exception ex) {
							logger.error("Picture TO Product [****ASSOCIATION FAILED****]=>" + productCode);
							logger.error("exception message--> " + ex);
							continue;
						}
					}

				}
			}

		} catch (final Exception ex) {
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

	public static ModelService getModelService() {
		modelService = (ModelService) Registry.getApplicationContext().getBean(SiteonepcmConstants.MODELSERVICE);
		return modelService;
	}

	public static ProductService getProductService() {
		productService = (ProductService) Registry.getApplicationContext().getBean(SiteonepcmConstants.PRODUCTSERVICE);
		return productService;
	}

	public static FlexibleSearchService getFlexibleSearchService() {
		flexibleSearchService = (FlexibleSearchService) Registry.getApplicationContext()
				.getBean(SiteonepcmConstants.FLEXIBLESEARCHSERVICE);
		return flexibleSearchService;
	}

	public static void setProductService(final ProductService productService)

	{
		PcmMediaAssociationJob.productService = productService;
	}
}
