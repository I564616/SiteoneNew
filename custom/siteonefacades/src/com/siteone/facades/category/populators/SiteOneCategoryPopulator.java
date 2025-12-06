package com.siteone.facades.category.populators;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commerceservices.url.UrlResolver;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.siteone.core.category.service.SiteOneCategoryService;
import com.siteone.core.model.GlobalProductNavigationNodeModel;
import com.siteone.core.model.SiteOneMarketingBannerComponentModel;



/**
 * @author 1003567
 *
 */
public class SiteOneCategoryPopulator implements Populator<CategoryModel, CategoryData>
{
	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "imageConverter")
	private Converter<MediaModel, ImageData> imageConverter;

	private UrlResolver<CategoryModel> categoryModelUrlResolver;

	@Resource(name = "categoryService")
	private SiteOneCategoryService categoryService;

	protected UrlResolver<CategoryModel> getCategoryModelUrlResolver()
	{
		return categoryModelUrlResolver;
	}

	public void setCategoryModelUrlResolver(final UrlResolver<CategoryModel> categoryModelUrlResolver)
	{
		this.categoryModelUrlResolver = categoryModelUrlResolver;
	}


	@Override
	public void populate(final CategoryModel source, final CategoryData target) throws ConversionException
	{
		final String SALES_SITEONE_ROOT_CATEGORY = "sales.hierarchy.root.category";
		final String salesRootCategoryCode = configurationService.getConfiguration().getString(SALES_SITEONE_ROOT_CATEGORY);
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");
		target.setName(source.getName());
		target.setCode(source.getCode());
		target.setDescription(source.getDescription());
		target.setUrl(getCategoryModelUrlResolver().resolve(source));
		if (source.getThumbnail() != null)
		{
			target.setImage(imageConverter.convert(source.getThumbnail()));
		}
		target.setUrlLink(source.getUrlLink());

		if (source.getProductCount() != null)
		{
			target.setProductCount(source.getProductCount());
		}

		final GlobalProductNavigationNodeModel navNode = categoryService
				.getProductNavNodesForCategory(source.getCode());

		if (navNode != null && navNode.getSequenceNumber() != null)
		{
			target.setSequence(navNode.getSequenceNumber());
		}

		final List<CategoryModel> subcategoryList = source.getCategories();
		final List<CategoryData> subcategoryListData = new ArrayList();

		final List<GlobalProductNavigationNodeModel> childNavNodes = categoryService
				.getChildrenProductNavNodesForCategory(source.getCode());

		for (final CategoryModel category : subcategoryList)
		{
			if (category.getCode().startsWith(salesRootCategoryCode))
			{
				final CategoryData subcategoryData = populateSubcategoryData(category, childNavNodes);
				subcategoryListData.add(subcategoryData);
			}
		}
		target.setSubCategories(subcategoryListData);
		target.setSeoEditableText(source.getSeoEditableText());
		if (source.getMarketingBanner() != null)
		{
			target.setMarketingBanner(imageConverter.convert(source.getMarketingBanner()));
		}
		target.setMarketingBannerLink(source.getMarketingBannerLink());

		if (Objects.nonNull(source.getBannerList()))
		{
			final List<ImageData> bannerImages = new ArrayList<>();

			for (final SiteOneMarketingBannerComponentModel model : source.getBannerList())
			{
				bannerImages.add(imageConverter.convert(model.getMarketingBanner()));
			}
			target.setMarketingBannerList(bannerImages);
		}
	}


	private CategoryData populateSubcategoryData(final CategoryModel category,
			final List<GlobalProductNavigationNodeModel> childNavNodes)
	{
		final CategoryData subcategoryData = new CategoryData();
		subcategoryData.setCode(category.getCode());
		subcategoryData.setName(category.getName());
		subcategoryData.setDescription(category.getDescription());
		subcategoryData.setUrl(getCategoryModelUrlResolver().resolve(category));
		//No need to count products under L1 or L3 categories, only L2 needs to be counted, -- REMOVED ON 3/7/25 for Global header
		// L2 category code is of the format ofSH1211, SH1111 etc.
		if (StringUtils.isNotEmpty(category.getCode()))
		{
			subcategoryData.setProductCount(category.getProductCount());
		}
		if (category.getThumbnail() != null)
		{
			subcategoryData.setImage(imageConverter.convert(category.getThumbnail()));
		}

		childNavNodes.forEach(childNavNodesEntry -> {

			if (subcategoryData.getCode().equals(childNavNodesEntry.getCategory().getCode()))
			{
				subcategoryData.setSequence(childNavNodesEntry.getSequenceNumber());
			}

		});

		return subcategoryData;
	}

}
