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

import org.apache.commons.collections4.CollectionUtils;

import com.siteone.core.model.InventoryUPCModel;
import com.siteone.core.services.SiteOneProductUOMService;


/**
 * @author BS
 *
 */
public class ProductInventoryUomValueProvider extends AbstractPropertyFieldValueProvider
		implements FieldValueProvider, Serializable
{

	private FieldNameProvider fieldNameProvider;
	private SiteOneProductUOMService siteOneProductUOMService;

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		final ProductModel product = (ProductModel) model;
		final Collection<FieldValue> fieldValues = new ArrayList<>();
		InventoryUPCModel inventory = null;
		final Collection<VariantProductModel> variants = product.getVariants();
		if (CollectionUtils.isEmpty(variants) || (CollectionUtils.isNotEmpty(variants) && variants.size() > 1))
		{
			inventory = product.getUpcData().stream().filter(upc -> upc.getBaseUPC().booleanValue()).findFirst().orElse(null);
		}
		else
		{
			for (final VariantProductModel variant : variants)
			{
				inventory = variant.getUpcData().stream().filter(upc -> upc.getBaseUPC().booleanValue()).findFirst().orElse(null);
			}
		}
		String inventoryUomDesc = null;

		if (null != inventory)
		{
			if (null != inventory.getInventoryUPCDesc())
			{
				inventoryUomDesc = inventory.getInventoryUPCDesc();
			}

		}

		fieldValues.addAll(createFieldValue(inventoryUomDesc, indexedProperty));

		return fieldValues;

	}

	/**
	 * @param productModel
	 * @param indexedProperty
	 * @return
	 */
	@SuppressWarnings("boxing")
	private Collection<? extends FieldValue> createFieldValue(final String inventoryUomDesc, final IndexedProperty indexedProperty)
	{
		final List<FieldValue> fieldValues = new ArrayList<>();
		final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, null);
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, inventoryUomDesc));
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
