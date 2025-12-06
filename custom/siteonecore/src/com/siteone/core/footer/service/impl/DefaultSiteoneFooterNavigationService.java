/**
 * 
 */
package com.siteone.core.footer.service.impl;

import de.hybris.platform.acceleratorcms.model.components.FooterNavigationComponentModel;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;

import com.siteone.core.footer.dao.SiteoneFooterNavigationDAO;
import com.siteone.core.footer.service.SiteoneFooterNavigationService;

/**
 * 
 */
public class DefaultSiteoneFooterNavigationService implements SiteoneFooterNavigationService
{
	private static final Logger LOGGER = Logger.getLogger(DefaultSiteoneFooterNavigationService.class);
	
	@Resource(name="siteoneFooterNavigationDAO")
	private SiteoneFooterNavigationDAO siteoneFooterNavigationDAO;

	@Override
	public FooterNavigationComponentModel getFooterNode()
	{
		return siteoneFooterNavigationDAO.getFooterNode();
	}

}
