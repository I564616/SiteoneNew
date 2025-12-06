package com.siteone.facades.store.impl;

import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.storefinder.StoreFinderService;
import de.hybris.platform.commerceservices.storefinder.data.PointOfServiceDistanceData;
import de.hybris.platform.commerceservices.storefinder.data.StoreFinderSearchPageData;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import com.microsoft.sqlserver.jdbc.StringUtils;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.facades.store.SiteOneStoreDetailsFacade;
import com.siteone.integration.constants.SiteoneintegrationConstants;

import jakarta.annotation.Resource;
import com.siteone.facades.product.SiteOneProductFacade;


/**
 * @author 532681
 */
public class DefaultSiteOneStoreDetailsFacade implements SiteOneStoreDetailsFacade
{

	private static final Logger LOG = Logger.getLogger(DefaultSiteOneStoreDetailsFacade.class);

	private BaseStoreService baseStoreService;

	private Converter<PointOfServiceModel, PointOfServiceData> pointOfServiceSiteOneConverter;

	private Converter<PointOfServiceModel, PointOfServiceData> pointOfServiceSiteOneTimeConverter;
	
	@Resource(name = "defaultSiteOneStoreFinderService")
	private SiteOneStoreFinderService siteOneStoreFinderService;
	
	@Resource(name = "pointOfServiceConverter")
	private Converter<PointOfServiceModel, PointOfServiceData> pointOfServiceConverter;
	
	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;
	
	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	private StoreFinderService<PointOfServiceDistanceData, StoreFinderSearchPageData<PointOfServiceDistanceData>> storeFinderService;

	/**
	 * @return the storeFinderService
	 */
	public StoreFinderService<PointOfServiceDistanceData, StoreFinderSearchPageData<PointOfServiceDistanceData>> getStoreFinderService()
	{
		return storeFinderService;
	}


	/**
	 * @param storeFinderService
	 *           the storeFinderService to set
	 */
	public void setStoreFinderService(
			final StoreFinderService<PointOfServiceDistanceData, StoreFinderSearchPageData<PointOfServiceDistanceData>> storeFinderService)
	{
		this.storeFinderService = storeFinderService;
	}


	/**
	 * @return the baseStoreService
	 */
	public BaseStoreService getBaseStoreService()
	{
		return baseStoreService;
	}


	/**
	 * @param baseStoreService
	 *           the baseStoreService to set
	 */
	public void setBaseStoreService(final BaseStoreService baseStoreService)
	{
		this.baseStoreService = baseStoreService;
	}


	/**
	 * @return the pointOfServiceSiteOneConverter
	 */
	public Converter<PointOfServiceModel, PointOfServiceData> getPointOfServiceSiteOneConverter()
	{
		return pointOfServiceSiteOneConverter;
	}


	/**
	 * @param pointOfServiceSiteOneConverter
	 *           the pointOfServiceSiteOneConverter to set
	 */
	public void setPointOfServiceSiteOneConverter(
			final Converter<PointOfServiceModel, PointOfServiceData> pointOfServiceSiteOneConverter)
	{
		this.pointOfServiceSiteOneConverter = pointOfServiceSiteOneConverter;
	}

	@Override
	public PointOfServiceData fetchSiteOnePointOfService(final String storeCode)
	{
		// YTODO Auto-generated method stub

		LOG.debug("Inside SiteOneStoreDetailsFacade::::");

		final PointOfServiceModel pointOfServiceModel = ((SiteOneStoreFinderService) getStoreFinderService())
				.getStoreDetailForId(storeCode);


		if (null != pointOfServiceModel)
		{


			final PointOfServiceData posData = pointOfServiceSiteOneConverter.convert(pointOfServiceModel);

			if (posData != null)
			{
				LOG.debug("PointOfService data is:::" + posData.getName());
			}


			return posData;
		}
		return null;

	}


	@Override
	public List<PointOfServiceData> fetchListofPointOfService(final List<String> storeCodes)
	{
		LOG.debug("Inside SiteOneStoreDetailsFacade::::");

		final List<PointOfServiceModel> pointOfServiceModel = ((SiteOneStoreFinderService) getStoreFinderService())
				.getListofStores(storeCodes);
		final List<PointOfServiceData> posDatas = Converters.convertAll(pointOfServiceModel, getPointOfServiceSiteOneConverter());

		return posDatas;

	}
	
	@Override
	public PointOfServiceData searchStoreDetailForPopup(final String storeId, final String productCode) {
	
	final PointOfServiceModel pointOfServiceModel = siteOneStoreFinderService.getStoreDetailForId(storeId);
	if (pointOfServiceModel == null)
	{
		LOG.info("No Store Found.");
		return null ;
	}
	else
	{
		final PointOfServiceData pointOfServiceData=pointOfServiceSiteOneConverter.convert(pointOfServiceModel);
		if(pointOfServiceData!=null) {
			if(!StringUtils.isEmpty(productCode)) {	   			
   				final String stockDetail = siteOneProductFacade.populateStoreLevelStockInfoDataForPopup(productCode,pointOfServiceData);
   				pointOfServiceData.setStockDetail(stockDetail);	   			
			} else {					
					pointOfServiceData.setStockDetail("");
			}				
		}
		 return pointOfServiceData;
	}
	}
	
	@Override
	public boolean getIsNurseryCenter(PointOfServiceData pointOfServiceData)
	{
		final List<String> storeSpecialities = pointOfServiceData.getStoreSpecialities();
		final String[] nurseryStores = configurationService.getConfiguration()
				.getString(SiteoneintegrationConstants.NURSERY_STORE).split(",");
		if (storeSpecialities != null && !CollectionUtils.isEmpty(storeSpecialities) && nurseryStores != null)
		{
			for (final String nurseryStore : storeSpecialities)
			{
				for (final String ss : nurseryStores)
				{
					if (nurseryStore.equalsIgnoreCase(ss))
					{
						return true;
					}
				}
			}
		}
		return false;
	}
}
