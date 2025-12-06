/**
 *
 */
package com.siteone.facades.article.populators;

import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.Assert;

import com.siteone.contentsearch.ContentData;
import com.siteone.contentsearch.ContentSearchPageData;




/**
 * @author Ravi-so
 *
 */
public class SiteOneArticleCategoryPopulator
		implements Populator<ContentPageModel, ContentSearchPageData<SearchStateData, ContentData>>
{


	@Override
	public void populate(final ContentPageModel source, final ContentSearchPageData<SearchStateData, ContentData> target)
			throws ConversionException
	{

		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");

		if (source != null && CollectionUtils.isNotEmpty(target.getResults()))
		{
			final ContentData featureArticle = target.getResults().stream()
					.filter(result -> source.getFeaturedArticle().equalsIgnoreCase(result.getUid())).findFirst().orElse(null);
			if (CollectionUtils.isNotEmpty(target.getContentCategoryData()))
			{
			target.getContentCategoryData().forEach(contentCategoryData -> {
				contentCategoryData.setFeaturedArticle(featureArticle);
			});
			}
		}

	}

}
