/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;
import de.hybris.platform.variants.model.VariantProductModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;

import com.google.common.collect.Lists;
import com.siteone.core.model.InventoryUPCModel;
import com.siteone.core.services.SiteOneProductUOMService;


/**
 * @author 1124932
 *
 */
public class SellableUomValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider, Serializable
{

	private FieldNameProvider fieldNameProvider;
	private SiteOneProductUOMService siteOneProductUOMService;

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		ProductModel product = (ProductModel) model;
		final Collection<FieldValue> fieldValues = new ArrayList<>();
		final Collection<VariantProductModel> variants = Lists.newArrayList(product.getVariants());
		if(CollectionUtils.isNotEmpty(variants))
		{
			variants.removeIf(v -> BooleanUtils.isTrue(v.getIsProductDiscontinued()));
		}
		InventoryUPCModel inventory = null;
		if (CollectionUtils.isEmpty(variants) || (CollectionUtils.isNotEmpty(variants) && variants.size() > 1))
		{
			inventory = product.getUpcData().stream().filter(upc -> upc.getBaseUPC().booleanValue()).findFirst().orElse(null);
		}
		else
		{
			for (final VariantProductModel variant : variants)
			{
				inventory = variant.getUpcData().stream().filter(upc -> upc.getBaseUPC().booleanValue()).findFirst().orElse(null);
				if(variants.size()==1)
				{
					product=variant;
				}
			}
		}

		final List<InventoryUPCModel> inventoryUOM = product.getUpcData().stream().filter(upc -> !upc.getBaseUPC().booleanValue())
				.collect(Collectors.toList());
		final List<InventoryUPCModel> inventoryUOMnew = new ArrayList();
		boolean hideUom = false;
		for (final InventoryUPCModel inventoryUOMModelData : inventoryUOM)
		{
			if (inventoryUOMModelData.getHideUPCOnline() != null && inventoryUOMModelData.getHideUPCOnline())
			{
				hideUom = true;
			}
			else
			{
				inventoryUOMnew.add(inventoryUOMModelData);
			}
		}

		if (CollectionUtils.isNotEmpty((inventoryUOMnew)) && !hideUom && inventory != null && inventory.getHideUPCOnline() != null
				&& inventory.getHideUPCOnline())
		{
			hideUom = true;
		}

		if (inventory != null && inventory.getHideUPCOnline() != null && !inventory.getHideUPCOnline().booleanValue())
		{
			inventoryUOMnew.add(inventory);
		}

		if (CollectionUtils.isNotEmpty((inventoryUOMnew)))
		{
			fieldValues.addAll(createFieldValue(inventoryUOMnew.size(), indexedProperty));

		}

		return fieldValues;

	}

	/**
	 * @param productModel
	 * @param indexedProperty
	 * @return
	 */
	@SuppressWarnings("boxing")
	private Collection<? extends FieldValue> createFieldValue(final int sellableUOMCount, final IndexedProperty indexedProperty)
	{
		final List<FieldValue> fieldValues = new ArrayList<>();
		final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, null);
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, sellableUOMCount));
		}
		return fieldValues;
	}

	/**
	 * @return the fieldNameProvider
	 */
	public FieldNameProvider getFieldNameProvider()
	{
		return fieldNameProvider;
	}

	/**
	 * @param fieldNameProvider
	 *           the fieldNameProvider to set
	 */
	public void setFieldNameProvider(final FieldNameProvider fieldNameProvider)
	{
		this.fieldNameProvider = fieldNameProvider;
	}

	/**
	 * @return the siteOneProductUOMService
	 */
	public SiteOneProductUOMService getSiteOneProductUOMService()
	{
		return siteOneProductUOMService;
	}

	/**
	 * @param siteOneProductUOMService
	 *           the siteOneProductUOMService to set
	 */
	public void setSiteOneProductUOMService(final SiteOneProductUOMService siteOneProductUOMService)
	{
		this.siteOneProductUOMService = siteOneProductUOMService;
	}

}
