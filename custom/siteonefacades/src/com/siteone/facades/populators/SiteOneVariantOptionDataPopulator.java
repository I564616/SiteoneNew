/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.product.converters.populator.VariantOptionDataPopulator;
import de.hybris.platform.commercefacades.product.data.VariantOptionData;
import de.hybris.platform.commercefacades.product.data.VariantOptionQualifierData;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.variants.model.VariantAttributeDescriptorModel;
import de.hybris.platform.variants.model.VariantProductModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.Assert;


/**
 * @author 1124932
 *
 */
public class SiteOneVariantOptionDataPopulator extends VariantOptionDataPopulator
{

	@Override
	public void populate(final VariantProductModel source, final VariantOptionData target)
	{
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");

		if (source.getBaseProduct() != null)
		{
			final List<VariantAttributeDescriptorModel> descriptorModels = getVariantsService()
					.getVariantAttributesForVariantType(source.getBaseProduct().getVariantType());

			final Collection<VariantOptionQualifierData> variantOptionQualifiers = new ArrayList<VariantOptionQualifierData>();
			for (final VariantAttributeDescriptorModel descriptorModel : descriptorModels)
			{
				// Create the variant qualifier
				final VariantOptionQualifierData variantOptionQualifier = new VariantOptionQualifierData();
				final String qualifier = descriptorModel.getQualifier();
				variantOptionQualifier.setQualifier(qualifier);
				variantOptionQualifier.setName(descriptorModel.getName());
				// Lookup the value
				final Object variantAttributeValue = lookupVariantAttributeName(source, qualifier);
				variantOptionQualifier.setValue(variantAttributeValue == null ? "" : variantAttributeValue.toString());

				// Add to list of variants
				variantOptionQualifiers.add(variantOptionQualifier);
			}
			target.setVariantOptionQualifiers(variantOptionQualifiers);
			target.setCode(source.getCode());
			target.setUrl(getProductModelUrlResolver().resolve(source));
			target.setStock(getStockConverter().convert(source));
			target.setItemNumber(source.getItemNumber());
			target.setProductType(source.getProductType());
			target.setIsRegulatedItem(source.getIsRegulatedItem());
			target.setStockLevel(target.getStock().getStockLevel());
			target.setHideCSP(source.getHideCSP());
			target.setHideList(source.getHideList());
			target.setInventoryCheck(source.getInventoryCheck());
			final List<String> storeIdList = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(source.getStores()))
			{
				for (final PointOfServiceModel pointOfServiceModel : source.getStores())
				{
					storeIdList.add(pointOfServiceModel.getStoreId());
				}
				target.setStores(storeIdList);
			}
			final List<String> regionDataList = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(source.getRegulatoryStates()))
			{
				for (final RegionModel regionModel : source.getRegulatoryStates())
				{
					regionDataList.add(regionModel.getIsocodeShort());
				}
				target.setRegulatoryStates(regionDataList);

			}
			target.setIsShippable(source.getIsShippable());
		}
	}

	@Override
	protected Object lookupVariantAttributeName(final VariantProductModel productModel, final String attribute)
	{
		final Object value = getVariantsService().getVariantAttributeValue(productModel, attribute);
		if (value == null)
		{
			final ProductModel baseProduct = productModel.getBaseProduct();
			if (baseProduct instanceof VariantProductModel)
			{
				return lookupVariantAttributeName((VariantProductModel) baseProduct, attribute);
			}
		}
		return value;
	}
}
