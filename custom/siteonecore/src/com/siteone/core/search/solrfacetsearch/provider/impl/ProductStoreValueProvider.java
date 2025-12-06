/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import com.siteone.core.services.SiteOneProductService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.search.restriction.impl.DefaultSearchRestrictionService;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.variants.model.VariantProductModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import jakarta.annotation.Resource;


/**
 * @author 1229803
 *
 */
public class ProductStoreValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider, Serializable
{
	private static final Logger LOG = Logger.getLogger(ProductStoreValueProvider.class);
	private FieldNameProvider fieldNameProvider;
	@Resource(name = "siteOneProductService")
	private SiteOneProductService siteOneProductService;
	@Resource(name = "searchRestrictionService")
	private DefaultSearchRestrictionService searchRestrictionService;

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

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.solrfacetsearch.provider.FieldValueProvider#getFieldValues(de.hybris.platform.solrfacetsearch.
	 * config.IndexConfig, de.hybris.platform.solrfacetsearch.config.IndexedProperty, java.lang.Object)
	 */
	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		if (model instanceof ProductModel)
		{
			final ProductModel product = (ProductModel) model;
			final Set<String> storeIds = new HashSet<String>();
			final Collection<FieldValue> fieldValues = new ArrayList<>();
			LOG.info("Product code ---" + product.getCode());
			boolean isenabled = searchRestrictionService.isSearchRestrictionsEnabled();
			LOG.error("Current Restriction" + isenabled);
			searchRestrictionService.disableSearchRestrictions();
			isenabled = searchRestrictionService.isSearchRestrictionsEnabled();
			final Collection<VariantProductModel> variants = siteOneProductService.getActiveVariantProducts(product);
			
			if (CollectionUtils.isEmpty(variants) && product.getStores() != null)
			{
				
				for (final PointOfServiceModel pos : product.getStores())
					{
					
						if (pos.getIsActive())
						{
							
							fieldValues.addAll(createFieldValue(pos.getStoreId(), indexedProperty));
						}
					}
			}
			else
			{
				for (final VariantProductModel variantProduct : variants)
				{

						final Collection<PointOfServiceModel> varientsPointOfServiceList = variantProduct.getStores();
						if(CollectionUtils.isNotEmpty(varientsPointOfServiceList))
						{
							for (final PointOfServiceModel variantPointOfServiceModel : varientsPointOfServiceList)
							{
								if (variantPointOfServiceModel.getIsActive())
								{
									storeIds.add(variantPointOfServiceModel.getStoreId());
								}
							}
						}
				}

				for (final String storeId : storeIds)
				{
					fieldValues.addAll(createFieldValue(storeId, indexedProperty));
				}

			}
			searchRestrictionService.enableSearchRestrictions();
			return fieldValues;
		}
		else
		{
			LOG.info("Non Product Model");
			throw new FieldValueProviderException("Cannot get sku of non-product item");
		}
	}


	protected List<FieldValue> createFieldValue(final Object value, final IndexedProperty indexedProperty)
	{
		final List<FieldValue> fieldValues = new ArrayList<>();
		final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, null);
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, value));
		}
		return fieldValues;
	}
}
