/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.siteone.storefront.filters;

import com.sap.security.core.server.csi.util.URLDecoder;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.storefront.controllers.pages.ProductPageController;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.apache.log4j.Logger;
/**
 * @author Abdul Rahman Sheikh M
 *
 */
public class SessionStoreRestorationFilter extends OncePerRequestFilter
{
	private static final Logger LOG = Logger.getLogger(SessionStoreRestorationFilter.class);

	private static final String GEO_LOCATED_STORE_COOKIE = "gls";

	private static final String CONFIRMED_STORE_COOKIE = "csc";

	private SiteOneStoreSessionFacade storeSessionFacade;

	private SiteOneStoreFinderFacade storeFinderFacade;

	private SiteOneCustomerFacade customerFacade;

	private B2BCustomerService b2bCustomerService;
	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;
	
	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Override
	public void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException
	{
		final BaseStoreModel currentBaseStore = getBaseStoreService().getCurrentBaseStore();
		if (null != currentBaseStore)
		{
			getSessionService().setAttribute("currentBaseStore", currentBaseStore.getPk());
		}
		String geoLocatedStoreId = null;
		String uid = null;

		final Cookie confirmedStoreCookie = WebUtils.getCookie(request, CONFIRMED_STORE_COOKIE);
		final Cookie geoLocatedStoreCookie = WebUtils.getCookie(request, GEO_LOCATED_STORE_COOKIE);

		final Cookie softloginCookie = WebUtils.getCookie(request, "j_username");
		String softLoginStoreid = null;
		if( softloginCookie!=null && softloginCookie.getValue()!=null) {

			uid = URLDecoder.decode(softloginCookie.getValue(),"UTF-8");
			uid = uid.trim().toLowerCase();
			try{
			final B2BCustomerModel b2bCustomer = (B2BCustomerModel)userService.getUserForUID(uid);
			if(b2bCustomer!=null && b2bCustomer.getPreferredStore()!=null ){
				softLoginStoreid = b2bCustomer.getPreferredStore().getStoreId();
			}
			}catch(Exception e){
				LOG.error("Exception in SessionStoreRestorationFilter : uid = " + uid + ":: Message : " ,  e );
			}

		}

		if (null != confirmedStoreCookie)
		{
			customerFacade.makeMyStore(confirmedStoreCookie.getValue());

			if (null != b2bCustomerService.getCurrentB2BCustomer()
					|| !confirmedStoreCookie.getValue().equalsIgnoreCase(storeSessionFacade.getSessionStore().getStoreId()))
			{
				eraseCookie(confirmedStoreCookie, response);
			}
		}

		if (null != geoLocatedStoreCookie)
		{
			geoLocatedStoreId = geoLocatedStoreCookie.getValue();
		}
		if(null!=softLoginStoreid){
			geoLocatedStoreId = softLoginStoreid;
		}

		if (null == storeSessionFacade.getSessionStore() || null != b2bCustomerService.getCurrentB2BCustomer())
		{
			customerFacade.syncSessionStore(geoLocatedStoreId);
		}

		filterChain.doFilter(request, response);
	}


	/**
	 * @param confirmedStoreCookie
	 * @param response
	 */
	protected void eraseCookie(final Cookie confirmedStoreCookie, final HttpServletResponse response)
	{
		confirmedStoreCookie.setValue(null);
		confirmedStoreCookie.setMaxAge(0);
		response.addCookie(confirmedStoreCookie);

	}

	/**
	 * @return the storeSessionFacade
	 */
	public SiteOneStoreSessionFacade getStoreSessionFacade()
	{
		return storeSessionFacade;
	}

	/**
	 * @param storeSessionFacade
	 *           the storeSessionFacade to set
	 */
	public void setStoreSessionFacade(final SiteOneStoreSessionFacade storeSessionFacade)
	{
		this.storeSessionFacade = storeSessionFacade;
	}

	/**
	 * @return the storeFinderFacade
	 */
	public SiteOneStoreFinderFacade getStoreFinderFacade()
	{
		return storeFinderFacade;
	}

	/**
	 * @param storeFinderFacade
	 *           the storeFinderFacade to set
	 */
	public void setStoreFinderFacade(final SiteOneStoreFinderFacade storeFinderFacade)
	{
		this.storeFinderFacade = storeFinderFacade;
	}


	/**
	 * @return the customerFacade
	 */
	public SiteOneCustomerFacade getCustomerFacade()
	{
		return customerFacade;
	}


	/**
	 * @param customerFacade
	 *           the customerFacade to set
	 */
	public void setCustomerFacade(final SiteOneCustomerFacade customerFacade)
	{
		this.customerFacade = customerFacade;
	}


	/**
	 * @return the b2bCustomerService
	 */
	public B2BCustomerService getB2bCustomerService()
	{
		return b2bCustomerService;
	}


	/**
	 * @param b2bCustomerService
	 *           the b2bCustomerService to set
	 */
	public void setB2bCustomerService(final B2BCustomerService b2bCustomerService)
	{
		this.b2bCustomerService = b2bCustomerService;
	}


	/**
	 * @return the sessionService
	 */
	public SessionService getSessionService()
	{
		return sessionService;
	}


	/**
	 * @param sessionService the sessionService to set
	 */
	public void setSessionService(SessionService sessionService)
	{
		this.sessionService = sessionService;
	}


	/**
	 * @return the baseStoreService
	 */
	public BaseStoreService getBaseStoreService()
	{
		return baseStoreService;
	}


	/**
	 * @param baseStoreService the baseStoreService to set
	 */
	public void setBaseStoreService(BaseStoreService baseStoreService)
	{
		this.baseStoreService = baseStoreService;
	}

}
