package com.siteone.facades.inspire.impl;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import java.util.ArrayList;

import jakarta.annotation.Resource;

import com.siteone.core.inspire.service.SiteOneInspirationService;
import com.siteone.core.model.InspirationModel;
import com.siteone.facade.InspirationData;
import com.siteone.facades.inspire.SiteOneInspirationFacade;


public class DefaultSiteOneInspirationFacade implements SiteOneInspirationFacade
{

	@Resource(name = "siteOneInspirationService")
	private SiteOneInspirationService siteOneInspirationService;

	@Resource(name = "siteoneInspirationConverter")
	private Converter<InspirationModel, InspirationData> siteoneInspirationConverter;

	@Override
	public InspirationData getInspirationListByCode(final String inspirationCode)
	{
		final InspirationModel inspirationModel = siteOneInspirationService.getInspirationListByCode(inspirationCode);
		return siteoneInspirationConverter.convert(inspirationModel);

	}

	@Override
	public SearchPageData<InspirationData> inspirationSearch(final PageableData pageableData)
	{
		final SearchPageData<InspirationData> searchPageData = new SearchPageData<InspirationData>();
		final SearchPageData<InspirationModel> searchPageModel = siteOneInspirationService.getInspiration(pageableData);

		for (final InspirationModel inspirationModel : searchPageModel.getResults())
		{
			if (searchPageData.getResults() == null)
			{
				searchPageData.setResults(new ArrayList());
			}
			searchPageData.getResults().add(siteoneInspirationConverter.convert(inspirationModel));
		}

		searchPageData.setPagination(searchPageModel.getPagination());

		return searchPageData;
	}
}
