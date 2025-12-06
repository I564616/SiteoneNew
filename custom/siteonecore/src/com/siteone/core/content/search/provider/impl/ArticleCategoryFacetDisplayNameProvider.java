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
package com.siteone.core.content.search.provider.impl;

import de.hybris.platform.category.CategoryService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractFacetValueDisplayNameProvider;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;

import jakarta.annotation.Resource;


/**
 * @author RP01944
 *
 */
public class ArticleCategoryFacetDisplayNameProvider extends AbstractFacetValueDisplayNameProvider
{

	@Resource
	private CategoryService categoryService;


	@Override
	public String getDisplayName(final SearchQuery query, final IndexedProperty property, final String facetValue)
	{

			final CategoryModel category = categoryService.getCategoryForCode(facetValue);
			if (category != null)
			{
				return category.getName();
			}

		return facetValue;
	}


}
