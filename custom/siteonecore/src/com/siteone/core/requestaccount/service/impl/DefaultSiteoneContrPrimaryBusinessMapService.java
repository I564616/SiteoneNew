/**
 *
 */
package com.siteone.core.requestaccount.service.impl;

import java.util.List;

import jakarta.annotation.Resource;

import com.siteone.core.model.SiteOneContrPrimaryBusinessModel;
import com.siteone.core.requestaccount.dao.SiteoneContrPrimaryBusinessMapDao;
import com.siteone.core.requestaccount.service.SiteoneContrPrimaryBusinessMapService;


/**
 * @author SNavamani
 *
 */
public class DefaultSiteoneContrPrimaryBusinessMapService implements SiteoneContrPrimaryBusinessMapService
{
	@Resource(name = "siteoneContrPrimaryBusinessMapDao")
	private SiteoneContrPrimaryBusinessMapDao siteoneContrPrimaryBusinessMapDao;

	@Override
	public List<SiteOneContrPrimaryBusinessModel> getPrimaryBusinessMap()
	{
		return siteoneContrPrimaryBusinessMapDao.getPrimaryBusinessMap();
	}

	public List<SiteOneContrPrimaryBusinessModel> getChildPrimaryBusinessMap(final String code)
	{
		return siteoneContrPrimaryBusinessMapDao.getChildPrimaryBusinessMap(code);
	}
}
