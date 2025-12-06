/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.product.converters.populator.ProductVariantMatrixPopulator;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.VariantMatrixElementData;
import de.hybris.platform.core.model.product.ProductModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import de.hybris.platform.variants.model.GenericVariantProductModel;
import de.hybris.platform.variants.model.VariantCategoryModel;
import de.hybris.platform.variants.model.VariantProductModel;
import de.hybris.platform.variants.model.VariantValueCategoryModel;
import org.apache.commons.collections4.CollectionUtils;


/**
 * @author 1124932
 *
 */
public class SiteOneProductVariantMatrixPopulator<SOURCE extends ProductModel, TARGET extends ProductData>
		extends ProductVariantMatrixPopulator<SOURCE, TARGET>
{

	@Override
	public void populate(final ProductModel productModel, final ProductData productData)
	{
		super.populate(productModel, productData);
	}

	@Override
	protected void orderTree(final List<VariantMatrixElementData> elementsList)
	{
		for (final VariantMatrixElementData element : elementsList)
		{
			if (CollectionUtils.isNotEmpty(element.getElements()))
			{
				orderTree(element.getElements());
			}
		}
		Collections.sort(elementsList,
				(p1, p2) -> p1.getVariantValueCategory().getName().compareTo(p2.getVariantValueCategory().getName()));
	}

	/**
	 * @author Abdul Rahman Sheikh M
	 *
	 * Get the list of {@link VariantValueCategoryModel} related to a specific {@link GenericVariantProductModel}.
	 * And arrange it in the same order how the categories were in the base product.
	 *
	 * @param productModel
	 *           the variant product.
	 * @return The variant value categories, ordered by variant category priority.
	 */
	protected List<VariantValueCategoryModel> getVariantValuesCategories(final ProductModel productModel)
	{
		final List<VariantValueCategoryModel> variantValueCategories = new ArrayList<>();
		for (final CategoryModel categoryProductModel : productModel.getSupercategories())
		{
			if (categoryProductModel instanceof VariantValueCategoryModel)
			{
				variantValueCategories.add((VariantValueCategoryModel) categoryProductModel);
			}
		}
		Collections.sort(variantValueCategories, getValueCategoryComparator());

		return this.arrangeVariantValuesBasedOnVariantCategoryOrder(productModel, variantValueCategories);
	}


	/**
	 * @author Abdul Rahman Sheikh M
	 *
	 * Get the list of {@link VariantValueCategoryModel} related to a specific {@link ProductModel}.
	 * 	 *
	 * @param productModel
	 *           the base product.
	 *
	 * @param variantValueCategories
	 *           variantValueCategories from the variant.
	 *
	 * @return variant value categories, order by variant category.
	 */
	protected List<VariantValueCategoryModel> arrangeVariantValuesBasedOnVariantCategoryOrder(final ProductModel productModel, List<VariantValueCategoryModel> variantValueCategories)
	{
		ProductModel baseProduct = ((GenericVariantProductModel) productModel).getBaseProduct();
		List<VariantCategoryModel> baseVariantCategories = this.getVariantCategoriesForProduct(baseProduct);

		final List<VariantValueCategoryModel> arrangedVariantValueCategories = new ArrayList<>();

		for (VariantCategoryModel variantCategory :baseVariantCategories)
		{
			for (VariantValueCategoryModel variantValueCategory :variantValueCategories)
			{
				if(variantValueCategory.getSupercategories().get(0).getCode().equalsIgnoreCase(variantCategory.getCode()))
				{
					arrangedVariantValueCategories.add(variantValueCategory);
					break;
				}
			}
		}

		return arrangedVariantValueCategories;
	}

	/**
	 * @author Abdul Rahman Sheikh M
	 *
	 * Get the list of {@link VariantCategoryModel} related to a specific {@link ProductModel}.
	 * 	 *
	 * @param productModel
	 *           the base product.
	 * @return base variant categories.
	 */
	protected List<VariantCategoryModel> getVariantCategoriesForProduct(final ProductModel productModel)
	{
		return productModel.getSupercategories()
				.stream()
				.filter(categoryModel -> categoryModel instanceof VariantCategoryModel)
				.map(categoryModel -> (VariantCategoryModel)categoryModel)
				.collect(Collectors.toList());
	}

}
