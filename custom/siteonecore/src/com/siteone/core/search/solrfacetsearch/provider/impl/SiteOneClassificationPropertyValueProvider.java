/**
 *
 */

package com.siteone.core.search.solrfacetsearch.provider.impl;

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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;


/**
 * @author 1091124
 *
 */
public class SiteOneClassificationPropertyValueProvider extends CommerceClassificationPropertyValueProvider
{

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		if (model instanceof ProductModel)
		{
			final List<FieldValue> fieldValues = new ArrayList<>();
			final List<ClassAttributeAssignmentModel> classAttributeAssignmentModels = indexedProperty
					.getClassAttributeAssignments();
			if (CollectionUtils.isNotEmpty(classAttributeAssignmentModels))
			{
				final Collection<VariantProductModel> variants = ((ProductModel) model).getVariants();

				if (CollectionUtils.isNotEmpty(variants))
				{
					for (final VariantProductModel variantProductModel : variants)
					{
						final List<ClassAttributeAssignment> classAttributeAssignmentList = new ArrayList<ClassAttributeAssignment>();
						final Product product = (Product) modelService.getSource(variantProductModel);

						for (final ClassAttributeAssignmentModel classAttrAssignmentModel : classAttributeAssignmentModels)
						{
							final ClassAttributeAssignment classAttributeAssignment = (ClassAttributeAssignment) this.modelService
									.getSource(classAttrAssignmentModel);
							classAttributeAssignmentList.add(classAttributeAssignment);
						}

						final FeatureContainer cont = FeatureContainer.loadTyped(product, classAttributeAssignmentList);
						Object value;
						for (final ClassAttributeAssignment classAttributeAssignment : classAttributeAssignmentList)
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
										final DecimalFormat df = new DecimalFormat("###.######");
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
					}
				}
				final List<ClassAttributeAssignment> classAttributeAssignmentList = new ArrayList<ClassAttributeAssignment>();
				final Product product = (Product) modelService.getSource(model);

				for (final ClassAttributeAssignmentModel classAttrAssignmentModel : classAttributeAssignmentModels)
				{
					final ClassAttributeAssignment classAttributeAssignment = (ClassAttributeAssignment) this.modelService
							.getSource(classAttrAssignmentModel);
					classAttributeAssignmentList.add(classAttributeAssignment);
				}

				final FeatureContainer cont = FeatureContainer.loadTyped(product, classAttributeAssignmentList);
				Object value;
				for (final ClassAttributeAssignment classAttributeAssignment : classAttributeAssignmentList)
				{
					if (cont.hasFeature(classAttributeAssignment))
					{
						final Feature feature = cont.getFeature(classAttributeAssignment);
						if (feature != null && !feature.isEmpty())
						{
							final List<FieldValue> featureValues = getFeaturesValues(indexConfig, feature, indexedProperty);

							if (featureValues.get(0).getValue() instanceof Double)
							{
								final DecimalFormat df = new DecimalFormat("###.######");
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
							fieldValues.addAll(featureValues);
						}
					}
				}
			}
			return fieldValues;
		}
		else
		{
			throw new FieldValueProviderException("Cannot provide classification property of non-product item");
		}
	}
}

