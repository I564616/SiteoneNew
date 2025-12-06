/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2016 SAP SE or an SAP affiliate company.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 *
 *
 */
package com.siteone.facades.content.search.populator;


import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchResponse;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import com.siteone.contentsearch.ContentSearchPageData;



/**
 * @author 965504
 */
public class ContentSearchResponseFreeTextSearchPopulator<STATE, ITEM>
		implements Populator<SolrSearchResponse, ContentSearchPageData<STATE, ITEM>>
{

	@Override
	public void populate(final SolrSearchResponse source, final ContentSearchPageData<STATE, ITEM> target)
			throws ConversionException
	{
		target.setFreeTextSearch(source.getRequest().getSearchText());

	}

}
