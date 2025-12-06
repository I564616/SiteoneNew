/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.catalog.model.classification.ClassAttributeAssignmentModel;
import de.hybris.platform.classification.features.Feature;
import de.hybris.platform.classification.features.FeatureList;
import de.hybris.platform.classification.features.FeatureValue;
import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.impl.ProductClassificationAttributesValueResolver;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.indexer.IndexerBatchContext;
import de.hybris.platform.solrfacetsearch.indexer.spi.InputDocument;
import de.hybris.platform.solrfacetsearch.provider.impl.ValueProviderParameterUtils;
import de.hybris.platform.variants.model.VariantProductModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;


/**
 * @author i849388
 *
 */
public class SiteOneClassificationPropertyValueResolver extends ProductClassificationAttributesValueResolver
{


	private final ThreadLocal<List<ClassAttributeAssignmentModel>> clsCache = ThreadLocal.withInitial(() -> {
		final List<ClassAttributeAssignmentModel> list = new ArrayList<ClassAttributeAssignmentModel>();
		return list;
	});

	final DecimalFormat df = new DecimalFormat("###.######");

	@Override
	protected FeatureList loadData(final IndexerBatchContext batchContext, final Collection<IndexedProperty> indexedProperties,
			final ProductModel product)
	{

		if (this.clsCache.get().isEmpty())
		{
			init(indexedProperties);
		}

		final FeatureList featureList = getClassificationService().getFeatures(product, this.clsCache.get());


		return featureList;

	}


	@Override
	public void resolve(final InputDocument document, final IndexerBatchContext batchContext,
			final Collection<IndexedProperty> indexedProperties, final ProductModel model) throws FieldValueProviderException
	{

		if (this.clsCache.get().isEmpty())
		{
			init(indexedProperties);
		}

		//Variants will be parsed from the product itself
		if (!(model instanceof VariantProductModel))
		{
			super.resolve(document, batchContext, indexedProperties, model);
		}

	}

	@Override
	protected void addFieldValues(final InputDocument document, final IndexerBatchContext batchContext,
			final IndexedProperty indexedProperty, final ProductModel model,
			final ValueResolverContext<FeatureList, Object> resolverContext) throws FieldValueProviderException
	{
		boolean hasValue = false;

		final Set<Object> values = new HashSet<Object>();

		final Collection<VariantProductModel> variants = model.getVariants();

		if (CollectionUtils.isNotEmpty(variants))
		{

			for (final VariantProductModel variantProductModel : variants)
			{
				values.addAll(getProductValues(indexedProperty, variantProductModel, resolverContext));
			}

		}

		values.addAll(getProductValues(indexedProperty, model, resolverContext));

		for (final Object attributeValue : values)
		{

			hasValue |= filterAndAddFieldValues(document, batchContext, indexedProperty, attributeValue,
					resolverContext.getFieldQualifier());
		}


		if (!hasValue)
		{
			final boolean isOptional = ValueProviderParameterUtils.getBoolean(indexedProperty, OPTIONAL_PARAM,
					OPTIONAL_PARAM_DEFAULT_VALUE);
			if (!isOptional)
			{
				throw new FieldValueProviderException("No value resolved for indexed property " + indexedProperty.getName());
			}
		}
	}


	private Set<Object> getProductValues(final IndexedProperty indexedProperty, final ProductModel product,
			final ValueResolverContext<FeatureList, Object> resolverContext) throws FieldValueProviderException
	{

		final Set<Object> values = new HashSet<Object>();

		//get the Feature list for this product, which could be a variant.
		final FeatureList featureList = getClassificationService().getFeatures(product, this.clsCache.get());

		if (featureList != null)
		{
			if (indexedProperty.getClassAttributeAssignments() != null)
			{
				for (final ClassAttributeAssignmentModel assignment : indexedProperty.getClassAttributeAssignments())
				{
					final Feature feature = featureList.getFeatureByAssignment(assignment);

					if (feature != null && CollectionUtils.isNotEmpty(feature.getValues()))
					{
						final List<FeatureValue> featureValues = getFeatureValues(feature, resolverContext.getQualifier());
						if ((featureValues != null) && !featureValues.isEmpty())
						{
							for (final FeatureValue featureValue : featureValues)
							{
								final Object attributeValue = featureValue.getValue();

								Object resultValue = null;

								if (attributeValue instanceof Double)
								{

									resultValue = df.format(((Double) attributeValue).doubleValue());
								}
								else
								{
									resultValue = attributeValue;
								}

								if (featureValue.getUnit() != null)
								{
									resultValue = resultValue + " " + featureValue.getUnit().getSymbol();
								}

								values.add(resultValue);
							}
						}
					}
				}
			}
		}

		return values;

	}


	@Override
	protected FeatureList loadFeatures(final Collection<IndexedProperty> indexedProperties, final ProductModel productModel)
	{

		return getClassificationService().getFeatures(productModel, this.clsCache.get());
	}


	private void init(final Collection<IndexedProperty> indexedProperties)
	{

		final List<ClassAttributeAssignmentModel> allClassAttributeAssignments = new ArrayList<ClassAttributeAssignmentModel>();

		for (final IndexedProperty indexedProperty : indexedProperties)
		{
			if (indexedProperty.getClassAttributeAssignments() != null)
			{
				allClassAttributeAssignments.addAll(indexedProperty.getClassAttributeAssignments());
			}
		}

		this.clsCache.get().addAll(allClassAttributeAssignments);

	}


}
