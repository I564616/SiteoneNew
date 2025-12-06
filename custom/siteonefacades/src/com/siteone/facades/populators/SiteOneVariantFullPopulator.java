/**
 * 
 */
package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.product.converters.populator.VariantFullPopulator;
import de.hybris.platform.commercefacades.product.data.BaseOptionData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.VariantOptionData;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.variants.model.VariantProductModel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

/**
 * @author SMondal
 *
 */
public class SiteOneVariantFullPopulator<SOURCE extends ProductModel, TARGET extends ProductData> extends VariantFullPopulator<SOURCE, TARGET> 
{
	@Override
	public void populate(final SOURCE productModel, final TARGET productData) throws ConversionException
	{
		// Populate the list of available child variant options
		if (productModel.getVariantType() != null && CollectionUtils.isNotEmpty(productModel.getVariants()))
		{
			final List<VariantOptionData> variantOptions = new ArrayList<VariantOptionData>();
			for (final VariantProductModel variantProductModel : productModel.getVariants())
			{
				if(!(null != variantProductModel.getIsProductDiscontinued() &&  variantProductModel.getIsProductDiscontinued()))
				{
					variantOptions.add(getVariantOptionDataConverter().convert(variantProductModel));
				}
			}
			productData.setVariantOptions(variantOptions);
		}

		// Populate the list of base options
		final List<BaseOptionData> baseOptions = new ArrayList<BaseOptionData>();
		ProductModel currentProduct = productModel;

		while (currentProduct instanceof VariantProductModel)
		{
			final ProductModel baseProduct = ((VariantProductModel) currentProduct).getBaseProduct();

			final BaseOptionData baseOptionData = getBaseOptionDataConverter().convert((VariantProductModel) currentProduct);

			// Fill out the list of available product options
			baseOptionData.setOptions(Converters.convertAll(baseProduct.getVariants(), getVariantOptionDataConverter()));

			baseOptions.add(baseOptionData);
			currentProduct = baseProduct;
		}
		productData.setBaseOptions(baseOptions);
	}
}
