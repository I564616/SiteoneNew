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

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.cms2.servicelayer.services.CMSPageService;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.converters.Populator;

import java.util.List;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.siteone.contentsearch.ContentData;



/**
 * @author 965504
 *
 *         Converter implementation for
 *         {@link de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData} as source and
 *         {@link de.hybris.platform.commercefacades.product.data.ProductData} as target type.
 */
public class SearchResultContentPopulator implements Populator<SearchResultValueData, ContentData>
{

	private static final Logger LOG = Logger.getLogger(SearchResultContentPopulator.class);

	@Resource
	private CMSPageService cmsPageService;


	@Override
	public void populate(final SearchResultValueData source, final ContentData target)
	{
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");
		final ContentPageModel contentPageModel;
		// Pull the values directly from the SearchResult object
		target.setUrl(this.<String> getValue(source, "sopageurl"));
		target.setTitle(this.<String> getValue(source, "sopagetitle"));
		target.setUid(this.<String> getValue(source, "sopagecode"));
		target.setName(this.<String> getValue(source, "sopagename"));
		target.setContent(this.<String> getValue(source, "sopagecontent"));
		target.setDescription(this.<String> getValue(source, "sopagedescription"));
		final ImageData previewImage = createImageData(source);
		if (null != previewImage)
		{
			target.setPreviewImage(previewImage);
		}
		target.setModifiedTime(this.<Long> getValue(source, "sopagemodifiedtime"));
		target.setTags(this.<List<String>> getValue(source, "soContentTags"));
		target.setCategory(this.<String> getValue(source, "soArticleCategory"));
		target.setContentType(this.<String> getValue(source, "soContentType"));

		target.setCategoryName(this.<String> getValue(source, "soCategoryName"));
		target.setPreviewTitle(this.<String> getValue(source, "soPreviewTitle"));

		// Get non indexed attributes from content page
		try
		{
			if (target.getUid() != null)
			{
				contentPageModel = (ContentPageModel) cmsPageService.getPageForId(target.getUid());
				target.setFullPath(contentPageModel != null ? contentPageModel.getFullPagePath() : null);
				target.setPathingChannel(contentPageModel != null ? contentPageModel.getPathingChannel() : null);
				target.setHomepage(contentPageModel != null ? Boolean.valueOf(contentPageModel.isHomepage()) : null);
			}
		}
		catch (final CMSItemNotFoundException e)
		{
			LOG.error(e);
		}
	}

	protected ImageData createImageData(final SearchResultValueData source)
	{
		final String mediaUrl = this.<String> getValue(source, "soPreviewImageUrl");
		final String soPreviewTitle = this.<String> getValue(source, "soPreviewTitle");

		if (mediaUrl != null && !mediaUrl.isEmpty())
		{
			final ImageData imageData = new ImageData();
			imageData.setUrl(mediaUrl);
			imageData.setAltText(soPreviewTitle);
			return imageData;
		}

		return null;
	}


	protected <T> T getValue(final SearchResultValueData source, final String propertyName)
	{
		if (source.getValues() == null)
		{
			return null;
		}

		// DO NOT REMOVE the cast (T) below, while it should be unnecessary it is required by the javac compiler
		return (T) source.getValues().get(propertyName);
	}

}
