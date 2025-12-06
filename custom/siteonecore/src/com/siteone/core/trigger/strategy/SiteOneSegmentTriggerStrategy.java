package com.siteone.core.trigger.strategy;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.personalizationservices.model.CxVariationModel;
import de.hybris.platform.personalizationservices.trigger.strategy.CxTriggerStrategy;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.core.trigger.dao.SiteOneSegmentTriggerDao;
import com.siteone.integration.geolocation.SiteOneIPGeolocationService;


public class SiteOneSegmentTriggerStrategy implements CxTriggerStrategy
{
	@Resource(name = "siteOneSegmentTriggerDao")
	private SiteOneSegmentTriggerDao siteOneSegmentTriggerDao;

	@Resource(name = "userFacade")
	private UserFacade userFacade;

	@Resource(name = "siteOneIPGeolocationService")
	SiteOneIPGeolocationService siteOneIPGeolocationService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "storeFinderService")
	private SiteOneStoreFinderService storeFinderService;

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;

	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	private static final Logger LOG = Logger.getLogger(SiteOneSegmentTriggerStrategy.class);

	@Override
	public Collection<CxVariationModel> getVariations(final UserModel user, final CatalogVersionModel catalogVersion)
	{
		if (Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch("enablePersonalization")))
		{
			final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
			String region = "";

			if (userFacade.isAnonymousUser())
			{
				region = getGuestUserRegion();
			}
			else if (customer != null)
			{
				if (customer.getPreferredStore() != null)
				{
					region = customer.getPreferredStore().getStoreId();
				}
				else if (customer.getHomeBranch() != null)
				{
					region = customer.getHomeBranch();
				}
				else
				{
					region = findGeolocatedStore();
				}
			}
			return siteOneSegmentTriggerDao.findApplicableVariations(catalogVersion, region, customer);
		}
		else
		{
			return new ArrayList<>();
		}
	}

	public String getGuestUserRegion()
	{
		String region = "";
		final ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (requestAttributes != null)
		{
			final HttpServletRequest request = requestAttributes.getRequest();
			if (!(null != request.getHeader("User-Agent")
					&& StringUtils.containsIgnoreCase(request.getHeader("User-Agent"), "SiteOneEcomApp")))
			{
				region = findGeolocatedStore();
			}
			else
			{
				region = sessionService.getAttribute("gls");
			}
		}
		else
		{
			region = findGeolocatedStore();
		}
		return region;
	}

	public String findGeolocatedStore()
	{
		String region = "";
		region = getStoreFromCookie();
		if (region == null)
		{
			final BaseStoreModel currentBaseStore = baseStoreService.getCurrentBaseStore();
			region = storeFinderService.doSearch(currentBaseStore, siteOneIPGeolocationService.getGeoPoint()).getStoreId();
			LOG.debug("Geolocated region: " + region);
		}

		return region;
	}

	public String getStoreFromCookie()
	{
		final PointOfServiceData sessionStore = sessionService.getAttribute("sessionStore");

		if (sessionStore != null && StringUtils.isNotEmpty(sessionStore.getStoreId()))
		{
			return sessionStore.getStoreId();
		}
		else{
			final ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			if (requestAttributes != null)
			{
   			Cookie cookie = WebUtils.getCookie(requestAttributes.getRequest(), "gls");
   			if (cookie != null && cookie.getValue() != null && !cookie.getValue().isEmpty())
   			{
   				return cookie.getValue();
   			}
			}
		}

		return null;
	}

}