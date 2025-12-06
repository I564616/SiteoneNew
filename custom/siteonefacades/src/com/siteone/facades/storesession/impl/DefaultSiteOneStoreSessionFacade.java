/**
 *
 */
package com.siteone.facades.storesession.impl;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.store.services.SiteOneStoreFinderService;

import de.hybris.platform.acceleratorfacades.customerlocation.CustomerLocationFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.storesession.impl.DefaultStoreSessionFacade;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.storelocator.GPS;
import de.hybris.platform.storelocator.impl.DefaultGPS;
import de.hybris.platform.storelocator.impl.GeometryUtils;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.context.Theme;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;

import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.store.NearbyStoreSearchFacade;

import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.integration.geolocation.SiteOneIPGeolocationService;


/**
 * @author 965504
 *
 *
 */
public class DefaultSiteOneStoreSessionFacade extends DefaultStoreSessionFacade implements SiteOneStoreSessionFacade
{
	private static final String SESSION_STORE = "sessionStore";
	private static final String PAGE_SIZE = "pageSize";
	private static final String SESSION_SHIPTO = "shipTo";
	private static final String SESSION_TAB = "tabId";
	private static final String NEARBY = "NEARBY";
	private static final String CURRENT_BASE_STORE = "currentBaseStore";

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "storeFinderFacade")
	private SiteOneStoreFinderFacade storeFinderFacade;

	@Resource(name = "customerLocationFacade")
	private CustomerLocationFacade customerLocationFacade;
	
	@Resource(name = "nearbyStoreSearchFacade")
	private NearbyStoreSearchFacade<PointOfServiceData> nearbyStoreSearchFacade;

	@Resource(name = "siteOneIPGeolocationService")
	SiteOneIPGeolocationService siteOneIPGeolocationService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "storeFinderService")
	private SiteOneStoreFinderService storeFinderService;

	@Resource(name = "siteOnePointOfServiceConverter")
	private Converter<PointOfServiceModel, PointOfServiceData> siteOnePointOfServiceConverter;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.siteone.facades.storesession.SiteOneStoreSessionFacade#setSessionStore(de.hybris.platform.commercefacades.
	 * storelocator.data.PointOfServiceData)
	 */
	@Override
	public void setSessionStore(final PointOfServiceData pointOfServiceData)
	{
	getSessionService().setAttribute(SESSION_STORE, pointOfServiceData);

		if((null!=pointOfServiceData && getNearbyStoresFromSession()==null) ||(null!=pointOfServiceData && getNearbyStoresFromSession()!=null && !pointOfServiceData.getStoreId().equals(getNearbyStoresFromSession().get(0).getStoreId()))){
		//if(null!=pointOfServiceData){
			List<PointOfServiceData> nearByStores=new ArrayList<>();
				if(null != nearbyStoreSearchFacade.getNearbyStores().get(NEARBY))
			{
				nearByStores.addAll(nearbyStoreSearchFacade.getNearbyStores().get(NEARBY));
			if (CollectionUtils.isNotEmpty(nearByStores) && StringUtils.isNotBlank(pointOfServiceData.getExcludeBranches()))
			{
				nearByStores.removeIf(store -> (null != pointOfServiceData.getStoreId() && !store.getStoreId().equals(pointOfServiceData.getStoreId()) && ArrayUtils.contains(pointOfServiceData.getExcludeBranches().trim().split(","),store.getStoreId())));
			}
			}
			setNearbyStoresInSession(CollectionUtils.isNotEmpty(nearByStores)?nearByStores:nearbyStoreSearchFacade.getNearbyStores().get(NEARBY));
			setExtendedNearbyStoresInSession(nearbyStoreSearchFacade.getNearbyStores().get("EXTENDED_NEARBY"));
			if(pointOfServiceData.getNurseryBuyingGroup() != null)
			{
				final List<PointOfServiceModel> nurseryBuyingGroup = storeFinderService.getNurseryNearbyBranches(pointOfServiceData.getNurseryBuyingGroup());
				final List<PointOfServiceData> nurseryNearbyStores = new ArrayList<>();
				if(!CollectionUtils.isEmpty(nurseryBuyingGroup))
				{
					nurseryNearbyStores.add(pointOfServiceData);
					for(final PointOfServiceModel pos : nurseryBuyingGroup)
					{
						final PointOfServiceData posData = siteOnePointOfServiceConverter.convert(pos);
						if(posData!=null && !posData.getStoreId().equalsIgnoreCase(pointOfServiceData.getStoreId()) && 
								posData.getStoreSpecialities() != null &&
								!posData.getStoreSpecialities().stream().filter(speciality -> 
								speciality.equalsIgnoreCase("Nursery Center")).collect(Collectors.toList()).isEmpty())
						{
							posData.setIsNearbyStoreSelected(Boolean.TRUE);
							nurseryNearbyStores.add(posData);
						}
					}
				}
				setNurseryNearbyBranches(nurseryNearbyStores);
			}
	
		}
	}

	@Override
	public void setGeolocatedSessionStore()
	{

		final ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (requestAttributes != null)
		{
			final HttpServletRequest request = requestAttributes.getRequest();

			if (!(null != request && null != request.getHeader("User-Agent")
					&& StringUtils.containsIgnoreCase(request.getHeader("User-Agent"), "SiteOneEcomApp")))
			{
				final Cookie cookie = WebUtils.getCookie(request, "gls");

				if ((cookie != null && cookie.getValue() != null && cookie.getValue().isEmpty())
					|| null == getSessionStore() || (cookie == null || cookie.getValue() == null))
				{
					final PointOfServiceData sessionStore = siteOnePointOfServiceConverter.convert(
						storeFinderService.doSearch(baseStoreService.getCurrentBaseStore(), siteOneIPGeolocationService.getGeoPoint()));
					if (null != cookie)
					{
						cookie.setValue(StringUtils.normalizeSpace(sessionStore.getStoreId()));
						cookie.setMaxAge(10 * 24 * 60 * 60);
						cookie.setSecure(true);
						((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse().addCookie(cookie);
					}
					else
					{
						final Cookie newCookie = new Cookie("gls", StringUtils.normalizeSpace(sessionStore.getStoreId()));
						newCookie.setMaxAge(10 * 24 * 60 * 60);
						newCookie.setPath("/");
						newCookie.setSecure(true);
						((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse().addCookie(newCookie);
					}
					setSessionStore(sessionStore);
				}
			}
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facades.storesession.SiteOneStoreSessionFacade#getSessionStore()
	 */
	@Override
	public PointOfServiceData getSessionStore()
	{
		return (PointOfServiceData) getSessionService().getAttribute(SESSION_STORE);
	}
	
	@Override
	public void setNearbyStoresInSession(List<PointOfServiceData> nearbyStoresList)
	{
		
		getSessionService().setAttribute(SiteoneCoreConstants.NEARBY_SESSION_STORES, nearbyStoresList);
	}
	@Override
	public List<PointOfServiceData> getNearbyStoresFromSession(){
		return (List<PointOfServiceData>) getSessionService().getAttribute(SiteoneCoreConstants.NEARBY_SESSION_STORES);
	}

	@Override
	public void setExtendedNearbyStoresInSession(final List<PointOfServiceData> extendedNearbyStoresList)
	{

		getSessionService().setAttribute(SiteoneCoreConstants.EXTENDED_NEARBY_SESSION_STORES, extendedNearbyStoresList);

	}
	
	@Override
	public void setNurseryNearbyBranches(final List<PointOfServiceData> nurseryNearbyStores)
	{
		getSessionService().setAttribute(SiteoneCoreConstants.NURSERY_NEARBY_SESSION_STORES, nurseryNearbyStores);
	}
	

	@Override
	public List<PointOfServiceData> getNurseryNearbyBranchesFromSession()
	{
		return (List<PointOfServiceData>) getSessionService().getAttribute(SiteoneCoreConstants.NURSERY_NEARBY_SESSION_STORES);
	}

	@Override
	public List<PointOfServiceData> getExtendedNearbyStoresFromSession()
	{
		return (List<PointOfServiceData>) getSessionService().getAttribute(SiteoneCoreConstants.EXTENDED_NEARBY_SESSION_STORES);
	}

	@Override
	public PointOfServiceData getNearestStoreFromHomeBranch()
    {
   	 PointOfServiceData homeStore = getSessionStore();
   	 if (null !=homeStore)
   	 {   		
 			List<PointOfServiceData> nearbyStores = new ArrayList<>(getExtendedNearbyStoresFromSession());
 			if(!CollectionUtils.isEmpty(nearbyStores))
 			{
    			for(final PointOfServiceData storeData : nearbyStores) 
    			{
    				final GPS homeStoreGPS = new DefaultGPS(homeStore.getGeoPoint().getLatitude(), homeStore.getGeoPoint().getLongitude());
               final GPS currentStoreGPS = new DefaultGPS(storeData.getGeoPoint().getLatitude(), storeData.getGeoPoint().getLongitude());
               final double distanceInKm = GeometryUtils.getElipticalDistanceKM(homeStoreGPS, currentStoreGPS);
               storeData.setDistanceKm(distanceInKm);
    			}
    			nearbyStores.sort(Comparator.comparingDouble(PointOfServiceData::getDistanceKm));
    			return (null != nearbyStores.get(1) ? nearbyStores.get(1) : null);
 			}
 		}
   	 return null;
    }
	
	@Override
	public List<PointOfServiceData> sortNearbyStoresBasedOnDistanceFromHomeBranch(boolean isPDP)
	{
		 PointOfServiceData homeStore = getSessionStore();
   	 if (null !=homeStore)
   	 {   		
 			List<PointOfServiceData> nearbyStores = new ArrayList<>(isPDP ? getNearbyStoresFromSession() : getExtendedNearbyStoresFromSession());
 			if(!CollectionUtils.isEmpty(nearbyStores))
 			{
    			for(final PointOfServiceData storeData : nearbyStores) 
    			{
    				final GPS homeStoreGPS = new DefaultGPS(homeStore.getGeoPoint().getLatitude(), homeStore.getGeoPoint().getLongitude());
               final GPS currentStoreGPS = new DefaultGPS(storeData.getGeoPoint().getLatitude(), storeData.getGeoPoint().getLongitude());
               final double distanceInKm = GeometryUtils.getElipticalDistanceKM(homeStoreGPS, currentStoreGPS);
               storeData.setDistanceKm(distanceInKm);
    			}
    			nearbyStores.sort(Comparator.comparingDouble(PointOfServiceData::getDistanceKm));
    			return nearbyStores;
 			}
 		}
   	 return null;
	}
	
	@Override
	public List<PointOfServiceData> sortHubStoresBasedOnDistanceFromHomeBranch()
	{	
		List<PointOfServiceData> hubStores = new ArrayList<>();
		final PointOfServiceData homeStore = getSessionStore();
		if ((homeStore != null && CollectionUtils.isNotEmpty(homeStore.getHubStores())))
		{
			final List<String> hubStoresList = homeStore.getHubStores();
			for(String hubStore: hubStoresList)
			{
				PointOfServiceData hubPointOfServiceData = storeFinderFacade.getStoreForId(hubStore);
				if(hubPointOfServiceData!=null) {
					hubStores.add(hubPointOfServiceData);
				}
			}
		}
		
		if (null != homeStore)
		{
			if (!CollectionUtils.isEmpty(hubStores))
			{
				for (final PointOfServiceData storeData : hubStores)
				{
					final GPS homeStoreGPS = new DefaultGPS(homeStore.getGeoPoint().getLatitude(),
							homeStore.getGeoPoint().getLongitude());
					final GPS currentStoreGPS = new DefaultGPS(storeData.getGeoPoint().getLatitude(),
							storeData.getGeoPoint().getLongitude());
					final double distanceInKm = GeometryUtils.getElipticalDistanceKM(homeStoreGPS, currentStoreGPS);
					storeData.setDistanceKm(distanceInKm);
				}
				hubStores.sort(Comparator.comparingDouble(PointOfServiceData::getDistanceKm));
				return hubStores;
			}
		}
		return null;
	}
	
	public void setInstockFilterSelected(boolean instockFilterSelected){
		getSessionService().setAttribute(SiteoneCoreConstants.INSTOCK_FILTER_SELECTED, instockFilterSelected);
	}
	public boolean getInstockFilterSelected(){

				return getSessionService().getAttribute(SiteoneCoreConstants.INSTOCK_FILTER_SELECTED)!=null?getSessionService().getAttribute(SiteoneCoreConstants.INSTOCK_FILTER_SELECTED):false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facades.storesession.SiteOneStoreSessionFacade#removeSession(de.hybris.platform.commercefacades.
	 * storelocator.data.PointOfServiceData)
	 */
	@Override
	public void removeSessionStore()
	{
		// YTODO Auto-generated method stub
		getSessionService().removeAttribute(SESSION_STORE);

	}

	@Override
	public void setSessionPageSize(final String pageSize)
	{
		getSessionService().setAttribute(PAGE_SIZE, pageSize);
	}

	@Override
	public String getSessionPageSize()
	{
		return getSessionService().getAttribute(PAGE_SIZE);
	}

	@Override
	public void setSessionShipTo(final B2BUnitData unitData)
	{
		getSessionService().setAttribute(SESSION_SHIPTO, unitData);
	}

	@Override
	public B2BUnitData getSessionShipTo()
	{
		return (B2BUnitData) getSessionService().getAttribute(SESSION_SHIPTO);
	}

	@Override
	public void removeSessionShipTo()
	{
		// YTODO Auto-generated method stub
		getSessionService().removeAttribute(SESSION_SHIPTO);

	}

	@Override
	public void setCurrentBaseStore(String baseStoreId) {
		if(baseStoreId.equalsIgnoreCase(SiteoneCoreConstants.CA_ISO_CODE)) {
			getSessionService().setAttribute(CURRENT_BASE_STORE,baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_CA_BASESTORE).getPk());
		}
		else {
			getSessionService().setAttribute(CURRENT_BASE_STORE,baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_US_BASESTORE).getPk());
		}
	}
	
	@Override
	public void removeCurrentBaseStore() {
		getSessionService().removeAttribute(CURRENT_BASE_STORE);
	}
	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facades.storesession.SiteOneStoreSessionFacade#setTabId(java.lang.String)
	 */
	@Override
	public void setSessionTabId(final String tabId)
	{
		getSessionService().setAttribute(SESSION_TAB, tabId);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facades.storesession.SiteOneStoreSessionFacade#getTabId()
	 */
	@Override
	public String getSessionTabId()
	{
		// YTODO Auto-generated method stub
		return getSessionService().getAttribute(SESSION_TAB);
	}

	/**
	 * @return the storeFinderFacade
	 */
	public SiteOneStoreFinderFacade getStoreFinderFacade()
	{
		return storeFinderFacade;
	}

	/**
	 * @param storeFinderFacade the storeFinderFacade to set
	 */
	public void setStoreFinderFacade(SiteOneStoreFinderFacade storeFinderFacade)
	{
		this.storeFinderFacade = storeFinderFacade;
	}

}
