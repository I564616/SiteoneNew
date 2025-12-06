package com.siteone.facades.populators;

import de.hybris.platform.catalog.model.classification.ClassificationClassModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.product.converters.populator.ProductCategoriesPopulator;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.impl.DefaultProductService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.variants.model.VariantCategoryModel;
import de.hybris.platform.variants.model.VariantValueCategoryModel;

import java.util.Collection;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;


public class SiteOneProductCategoriesPopulator extends ProductCategoriesPopulator
{
	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "productService")
	private DefaultProductService productService;

	private static final String DISPLAY_SITEONE_ROOT_CATEGORY = "display.hierarchy.root.category";

	@Override
	public void populate(final ProductModel source, final ProductData target) throws ConversionException
	{
		ProductModel baseProduct = source;

		if (StringUtils.isNotEmpty(target.getBaseProduct()))			
		{			
			baseProduct = productService.getProductForCode(target.getBaseProduct());
			target.setParentCategory(baseProduct.getProductType());
		}
		
		final String displayRootCategoryCode = configurationService.getConfiguration().getString(DISPLAY_SITEONE_ROOT_CATEGORY);
		final Collection<CategoryModel> categories = getCommerceProductService()
				.getSuperCategoriesExceptClassificationClassesForProduct(baseProduct);

		CategoryModel level1Category = null;
		CategoryModel level2Category = null;
		CategoryModel level3Category = null;
		for (final CategoryModel categoryModel : categories)
		{
			if ((!categoryModel.getCode().startsWith(displayRootCategoryCode))
					&& (!(categoryModel instanceof ClassificationClassModel) && !(categoryModel instanceof VariantCategoryModel)
							&& !(categoryModel instanceof VariantValueCategoryModel)))
			{
				level1Category = categoryModel;
				level2Category = categoryModel;
				level3Category = categoryModel;
				while (CollectionUtils.isNotEmpty(level1Category.getSupercategories()))
				{
					if (level1Category.getSupercategories().get(0).getName().equalsIgnoreCase("Primary"))
					{
						target.setLevel1Category(level1Category.getName());
						target.setLevel2Category(level2Category.getName());
						break;
					}
					else
					{
						level3Category = level2Category;
						level2Category = level1Category;
						level1Category = level1Category.getSupercategories().get(0);
					}
				}
				break;
			}
		}
		if(level3Category != null && level3Category.getIsTransferrableCategory() != null &&
				BooleanUtils.isNotTrue(level3Category.getIsTransferrableCategory()))
   	{
   	  target.setIsTransferrable(false);
   	}
	}

}