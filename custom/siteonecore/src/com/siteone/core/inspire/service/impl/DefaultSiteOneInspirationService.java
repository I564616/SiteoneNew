package com.siteone.core.inspire.service.impl;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import java.util.List;

import jakarta.annotation.Resource;

import com.siteone.core.inspire.dao.SiteOneInspirationDao;
import com.siteone.core.inspire.service.SiteOneInspirationService;
import com.siteone.core.model.InspirationModel;


public class DefaultSiteOneInspirationService implements SiteOneInspirationService
{

	@Resource(name = "siteOneInspirationDao")
	private SiteOneInspirationDao siteOneInspirationDao;


	@Override
	public InspirationModel getInspirationListByCode(final String inspirationCode)
	{
		final List<InspirationModel> inspiration = siteOneInspirationDao.findInspirationByCode(inspirationCode);

		if (inspiration != null && !inspiration.isEmpty())
		{
			return inspiration.get(0);
		}
		else
		{
			return null;
		}
	}

	@Override
	public SearchPageData<InspirationModel> getInspiration(final PageableData pageableData)
	{
		return siteOneInspirationDao.findInspiration(pageableData);
	}

}
