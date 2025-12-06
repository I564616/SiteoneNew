package com.siteone.integration.jobs.product.service.impl;

import com.siteone.integration.jobs.product.dao.ProductFeedCronJobDao;
import com.siteone.integration.jobs.product.service.OrphanMediaCronJobService;
import com.siteone.integration.jobs.product.service.ProductFeedService;
import com.siteone.integration.services.dataimport.SiteOneBlobDataImportService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.siteone.core.model.OrphanMediaCronJobModel;

import de.hybris.platform.core.PK;
import de.hybris.platform.core.model.media.MediaContainerModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;



public class DefaultOrphanMediaCronJobService extends AbstractItemDao implements OrphanMediaCronJobService {
	
		
		@Autowired
	    private ProductFeedCronJobDao productFeedCronJobDao;
		
		private ProductFeedService productFeedService;
		private ModelService modelService;
		private static final Logger LOG = Logger.getLogger(DefaultOrphanMediaCronJobService.class);
	

		@Override
		public void cleanUpOrphanMedia(OrphanMediaCronJobModel orphanMediaCronJobModel) {
            //Method 1
			
			try {
	            final List<ProductModel> product = getProductFeedCronJobDao().findAllProductPk();
	            final List<MediaContainerModel> productMediaContainer = new ArrayList<MediaContainerModel>();
	            for(final ProductModel productData : product) 
	            {
	              productMediaContainer.addAll(productData.getGalleryImages());
	            }
	            List<String> productMediaPk =productMediaContainer.stream().map(e -> e.getPk().toString())
						.collect(Collectors.toList());
	            LOG.error("productMediaPk "+productMediaPk.size() );
	             final List<MediaContainerModel> mediaContainer = getProductFeedCronJobDao().findProductMedia();
	             final List<MediaContainerModel> removeMediaCon = new ArrayList<MediaContainerModel>();
			             if(CollectionUtils.isNotEmpty(mediaContainer))
			            	 {
			            	 LOG.error("mediaContainer data "+mediaContainer.size() );
			            	   for( final MediaContainerModel mediaCon:  mediaContainer)
			            	    {
			            		 if(mediaCon==null) {
			            			 LOG.error("Skipping null MediaContainer");		
			            			 continue;
			            			 }
						         if (!productMediaPk.contains(mediaCon.getPk().toString()))
								 {
						        	 removeMediaCon.add(mediaCon);
							    	   LOG.error("List Remove Media " + mediaCon.getPk());
							    	   
							       }
								 }
						     }
			             LOG.error("removeMediaCon size "+removeMediaCon.size());      
	                getModelService().removeAll(removeMediaCon);
					
					final List<MediaModel> mediaNoCon =getProductFeedCronJobDao().findMediaWithNoContainer();
					if(CollectionUtils.isNotEmpty(mediaNoCon)) 
					{
						LOG.error("mediaNoCon size "+mediaNoCon.size());
					getModelService().removeAll(mediaNoCon); 
					}
					
					final List<MediaContainerModel> noMediaCon =getProductFeedCronJobDao().findContainerWithNoMedia();
					if(CollectionUtils.isNotEmpty(noMediaCon))
					{
					LOG.error("noMediaCon size "+noMediaCon.size());
					getModelService().removeAll(noMediaCon);
					}
					LOG.error("Remove Empty Media  contatiner END");
				orphanMediaCronJobModel.setResult(CronJobResult.SUCCESS);
                orphanMediaCronJobModel.setStatus(CronJobStatus.FINISHED);

	        } catch (Exception e) {
	            orphanMediaCronJobModel.setResult(CronJobResult.FAILURE);
	            orphanMediaCronJobModel.setStatus(CronJobStatus.ABORTED);
	            LOG.error("Error in OrphanMedia Service :: ", e);


	        } finally {
	            Date date = new Date();
	            orphanMediaCronJobModel.setLastExecutionTime(date);
	            modelService.save(orphanMediaCronJobModel);
	        }
			
		}
		

		public ProductFeedCronJobDao getProductFeedCronJobDao() {
			return productFeedCronJobDao;
		}

		public void setProductFeedCronJobDao(ProductFeedCronJobDao productFeedCronJobDao) {
			this.productFeedCronJobDao = productFeedCronJobDao;
		}
		
		/**
		 * @return the productFeedService
		 */
		public ProductFeedService getProductFeedService() {
			return productFeedService;
		}

		/**
		 * @param productFeedService the productFeedService to set
		 */
		public void setProductFeedService(ProductFeedService productFeedService) {
			this.productFeedService = productFeedService;
		}

		/**
		 * @return the modelService
		 */
		public ModelService getModelService() {
			return modelService;
		}

		/**
		 * @param modelService the modelService to set
		 */
		public void setModelService(ModelService modelService) {
			this.modelService = modelService;
		}

}
