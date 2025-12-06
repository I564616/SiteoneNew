package com.siteone.core.inspire.dao;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import java.util.List;

import com.siteone.core.model.InspirationModel;


public interface SiteOneInspirationDao
{
	public List<InspirationModel> findInspirationByCode(final String code);

	public SearchPageData<InspirationModel> findInspiration(final PageableData pageableData);
}
