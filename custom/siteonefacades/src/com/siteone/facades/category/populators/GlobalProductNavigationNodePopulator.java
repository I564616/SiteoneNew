/**
 *
 */
package com.siteone.facades.category.populators;

import de.hybris.platform.cms2.model.navigation.CMSNavigationNodeModel;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.util.Assert;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import java.util.ArrayList;
import com.siteone.core.category.service.SiteOneCategoryService;
import java.util.List;

import jakarta.annotation.Resource;

import com.siteone.core.dto.navigation.GlobalProductNavigationNodeData;
import com.siteone.core.model.GlobalProductNavigationNodeModel;
import com.thoughtworks.xstream.converters.ConversionException;


public class GlobalProductNavigationNodePopulator implements Populator<CMSNavigationNodeModel, GlobalProductNavigationNodeData>
{

	@Resource(name = "categoryService")
	private SiteOneCategoryService categoryService;

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "imageConverter")
	private Converter<MediaModel, ImageData> imageConverter;

	private static final String CATALOGROOT = "SH1";

	@Override
	public void populate(final CMSNavigationNodeModel source, final GlobalProductNavigationNodeData target)
			throws ConversionException //NOSONAR
	{

		final String SALES_SITEONE_ROOT_CATEGORY = "sales.hierarchy.root.category";
		final String salesRootCategoryCode = configurationService.getConfiguration().getString(SALES_SITEONE_ROOT_CATEGORY);
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");

		target.setUid(source.getUid());
		target.setName(source.getName());
		target.setVisible(source.isVisible());
		target.setUrl(((GlobalProductNavigationNodeModel) source).getUrl());
		target.setCategoryCode(((GlobalProductNavigationNodeModel) source).getCategory().getCode());
		target.setCategoryName(((GlobalProductNavigationNodeModel) source).getCategory().getName());
		target.setCategoryProductCount(((GlobalProductNavigationNodeModel) source).getCategory().getProductCount());
		target.setSequenceNumber(((GlobalProductNavigationNodeModel) source).getSequenceNumber());

		if (((GlobalProductNavigationNodeModel) source).getCategory().getThumbnail() != null)
		{
			final ImageData imgData = imageConverter
					.convert(((GlobalProductNavigationNodeModel) source).getCategory().getThumbnail());
			target.setImageUrl(imgData.getUrl());
		}
		final List<CMSNavigationNodeModel> subcategoryList = source.getChildren();
		final List<GlobalProductNavigationNodeData> subcategoryListData = new ArrayList();

		final List<GlobalProductNavigationNodeModel> childNavNodes = categoryService
				.getChildrenProductNavNodesForCategory(((GlobalProductNavigationNodeModel) source).getCategory().getCode());

		for (final CMSNavigationNodeModel category : subcategoryList)
		{
			final GlobalProductNavigationNodeData subcategoryData = populateSubcategoryData(category);
			subcategoryListData.add(subcategoryData);

		}
		target.setChildren(subcategoryListData);

	}



	private GlobalProductNavigationNodeData populateSubcategoryData(final CMSNavigationNodeModel category)
	{
		final GlobalProductNavigationNodeData subcategoryData = new GlobalProductNavigationNodeData();


		subcategoryData.setUid(category.getUid());
		subcategoryData.setName(category.getName());
		subcategoryData.setVisible(category.isVisible());
		subcategoryData.setUrl(((GlobalProductNavigationNodeModel) category).getUrl());
		subcategoryData.setCategoryCode(((GlobalProductNavigationNodeModel) category).getCategory().getCode());
		subcategoryData.setCategoryName(((GlobalProductNavigationNodeModel) category).getCategory().getName());
		//LOG.info("Category Name " + ((GlobalProductNavigationNodeModel) category).getCategory().getName());
		subcategoryData.setCategoryProductCount(((GlobalProductNavigationNodeModel) category).getCategory().getProductCount());
		subcategoryData.setSequenceNumber(((GlobalProductNavigationNodeModel) category).getSequenceNumber());


		return subcategoryData;
	}


}
