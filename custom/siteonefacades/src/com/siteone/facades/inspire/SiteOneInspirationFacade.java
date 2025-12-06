package com.siteone.facades.inspire;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import com.siteone.facade.InspirationData;


public interface SiteOneInspirationFacade
{
	public InspirationData getInspirationListByCode(final String inspirationCode);

	public SearchPageData<InspirationData> inspirationSearch(final PageableData pageableData);

}
