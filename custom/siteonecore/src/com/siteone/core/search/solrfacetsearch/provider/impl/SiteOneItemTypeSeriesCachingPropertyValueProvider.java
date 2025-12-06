/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import com.siteone.core.search.solrfacetsearch.provider.util.SiteOneClassificationPropertyProviderCache;
import de.hybris.platform.catalog.jalo.classification.ClassAttributeAssignment;
import de.hybris.platform.catalog.jalo.classification.util.Feature;
import de.hybris.platform.catalog.jalo.classification.util.FeatureContainer;
import de.hybris.platform.catalog.model.classification.ClassAttributeAssignmentModel;
import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.impl.CommerceClassificationPropertyValueProvider;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.variants.model.VariantProductModel;
import org.apache.commons.collections4.CollectionUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author i849388
 *
 */
public class SiteOneItemTypeSeriesCachingPropertyValueProvider extends CommerceClassificationPropertyValueProvider
{

	private final ThreadLocal<SiteOneClassificationPropertyProviderCache> threadLocalCache = ThreadLocal.withInitial(() -> {
		final SiteOneClassificationPropertyProviderCache cache = new SiteOneClassificationPropertyProviderCache();
		return cache;
	});

	final DecimalFormat df = new DecimalFormat("###.######");


	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		if (model instanceof ProductModel)
		{

			final SiteOneClassificationPropertyProviderCache cache = threadLocalCache.get();

			// cache the property assignments for future use
			if (!cache.hasIndexedPropertyName(indexedProperty.getName()))
			{
				cacheIndexedPropertyAssigments(indexedProperty);
			}
			else
			{
				cache.setHaveAllProperties(true);
			}

			final ProductModel baseProductModel = (ProductModel) model;

			// cache the product if it's a new base product and we have cached all property assignments otherwise the featureContainer won't load

			if (cache.haveAllProperties())
			{
				final String cachedProductCode = cache.getBaseProductCode();
				if (!baseProductModel.getCode().equals(cachedProductCode))
				{
					cacheFeatureContainers(baseProductModel);

					cache.setBaseProductCode(baseProductModel.getCode());

				}
			}


			final List<FieldValue> fieldValues = new ArrayList<>();

			final List<ClassAttributeAssignment> indexPropertyAssignments = cache
					.getIndexedPropertyAssignments(indexedProperty.getName());

			if (cache.haveAllProperties())
			{

				//process all cached FeatureContainer against the current property's assignments
				final Collection<FeatureContainer> featureContainers = cache.getProductFeatureContainerCache();

				for (final FeatureContainer cont : featureContainers)
				{
					fieldValues.addAll(getValuesFromFeatureContainer(cont, indexConfig, indexedProperty));
				}

			}
			else
			{
				//first time around, we don't have all property assignments yet

				final Product product = (Product) modelService.getSource(baseProductModel);

				final FeatureContainer cont = FeatureContainer.loadTyped(product, indexPropertyAssignments);

				fieldValues.addAll(getValuesFromFeatureContainer(cont, indexConfig, indexedProperty));
			}


			return fieldValues;
		}
		else
		{
			throw new FieldValueProviderException("Cannot provide classification property of non-product item");
		}
	}

	private Collection<FieldValue> getValuesFromFeatureContainer(final FeatureContainer cont,
			final IndexConfig indexConfig, final IndexedProperty indexedProperty) throws FieldValueProviderException
	{

		final List<FieldValue> fieldValues = new ArrayList<>();

		final Collection<ClassAttributeAssignment> indexPropertyAssignments = this.threadLocalCache.get()
				.getIndexedPropertyAssignments(indexedProperty.getName());

		Object value;
		for (final ClassAttributeAssignment classAttributeAssignment : indexPropertyAssignments)
		{
			if (cont.hasFeature(classAttributeAssignment))
			{
				final Feature feature = cont.getFeature(classAttributeAssignment);
				if (feature != null && !feature.isEmpty())
				{
					boolean duplicate = false;
					final List<FieldValue> featureValues = getFeaturesValues(indexConfig, feature, indexedProperty);

					if (featureValues.get(0).getValue() instanceof Double)
					{
						value = df.format(((Double) featureValues.get(0).getValue()).doubleValue());
						featureValues.set(0, new FieldValue(featureValues.get(0).getFieldName(), value));
					}
					else
					{
						value = featureValues.get(0).getValue();
					}

					if (feature.getValue(0).getUnit() != null)
					{
						value = value + " " + feature.getValue(0).getUnit().getSymbol();
						featureValues.set(0, new FieldValue(featureValues.get(0).getFieldName(), value));
					}

					for (final FieldValue fieldValue : fieldValues)
					{
						if (fieldValue.getValue().equals(value))
						{
							duplicate = true;
							break;
						}
					}
					if (!duplicate)
					{
						fieldValues.addAll(featureValues);
					}
				}
			}
		}


		return fieldValues;
	}

	private void cacheIndexedPropertyAssigments(final IndexedProperty indexedProperty) {

		final List<ClassAttributeAssignmentModel> classAttributeAssignmentModels = indexedProperty
				.getClassAttributeAssignments();

		final List<ClassAttributeAssignment> classAttributeAssignmentList = new ArrayList<ClassAttributeAssignment>();

		if (CollectionUtils.isNotEmpty(classAttributeAssignmentModels)) {
			for (final ClassAttributeAssignmentModel classAttrAssignmentModel : classAttributeAssignmentModels)
			{
				final ClassAttributeAssignment classAttributeAssignment = (ClassAttributeAssignment) this.modelService
						.getSource(classAttrAssignmentModel);

				classAttributeAssignmentList.add(classAttributeAssignment);

			}
		}

		this.threadLocalCache.get().addIndexedProperty(indexedProperty.getName(), classAttributeAssignmentList);
	}

	private List<FeatureContainer> getBaseAndVariantFeatureContainers(final ProductModel baseProductModel,
			final List<ClassAttributeAssignment> assignments)
	{

		final List<FeatureContainer> containers = new ArrayList<FeatureContainer>();

		final Product baseProduct = (Product) modelService.getSource(baseProductModel);

		final FeatureContainer cont = FeatureContainer.loadTyped(baseProduct, assignments);

		containers.add(cont);

		return containers;
	}

	private void cacheFeatureContainers(final ProductModel baseProductModel)
	{

		final List<ClassAttributeAssignment> allAssignments = this.threadLocalCache.get().getAllClassAttributeAssignments();

		final List<FeatureContainer> containers = getBaseAndVariantFeatureContainers(baseProductModel, allAssignments);

		//clear the previous product and variants from cache
		this.threadLocalCache.get().getProductFeatureContainerCache().clear();

		this.threadLocalCache.get().getProductFeatureContainerCache().addAll(containers);
	}
}