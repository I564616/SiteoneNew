package com.siteone.core.inspire.service;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import com.siteone.core.model.InspirationModel;


public interface SiteOneInspirationService
{
	public InspirationModel getInspirationListByCode(final String inspirationCode);

	public SearchPageData<InspirationModel> getInspiration(final PageableData pageableData);

}
