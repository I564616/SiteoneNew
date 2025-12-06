/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.catalog.model.classification.ClassAttributeAssignmentModel;
import de.hybris.platform.classification.ClassificationService;
import de.hybris.platform.classification.features.Feature;
import de.hybris.platform.classification.features.FeatureList;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;


/**
 * @author Admin
 *
 */
public class SiteoneClassificationUtils
{
	private ClassificationService classificationService;
	private FieldNameProvider fieldNameProvider;
	private static final Logger LOG = Logger.getLogger(SiteoneClassificationUtils.class);

	public String getClassificationAttributeValue(final Object model, final String attributeName)
	{
		String attributeValue = null;
		final ProductModel baseProductModel = (ProductModel) model;
		final FeatureList featureList = getClassificationService().getFeatures(baseProductModel);
		if (!featureList.isEmpty())
		{
			final Set<ClassAttributeAssignmentModel> classAttributeAssignmentModels = featureList.getClassAttributeAssignments();
			final Iterator<ClassAttributeAssignmentModel> classAttributeAssignmentIterator = classAttributeAssignmentModels
					.iterator();
			while (classAttributeAssignmentIterator.hasNext())
			{
				final ClassAttributeAssignmentModel attributeAssignmentModel = classAttributeAssignmentIterator.next();
				LOG.info("CategoriesName:" + attributeAssignmentModel.getClassificationAttribute().getName());
				if (attributeAssignmentModel.getClassificationAttribute().getName().equalsIgnoreCase(attributeName))
				{
					final Feature feature = featureList.getFeatureByAssignment(attributeAssignmentModel);
					if (feature != null && feature.getValue() != null && feature.getValue().getValue() != null)
					{
						LOG.info("CategoriesValue:" + feature.getValue().getValue());
						attributeValue = (String) feature.getValue().getValue();
						break;
					}

				}
			}
		}
		return attributeValue;
	}


	/**
	 * @param indexValue
	 * @param indexedProperty
	 * @return fieldValues
	 */
	@SuppressWarnings("boxing")
	public Collection<? extends FieldValue> createFieldValue(final String indexValue, final IndexedProperty indexedProperty)
	{
		final List<FieldValue> fieldValues = new ArrayList<>();
		final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, null);
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, indexValue));
		}
		return fieldValues;
	}

	/**
	 * @return the classificationService
	 */
	public ClassificationService getClassificationService()
	{
		return classificationService;
	}

	/**
	 * @param classificationService
	 *           the classificationService to set
	 */
	public void setClassificationService(final ClassificationService classificationService)
	{
		this.classificationService = classificationService;
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



}
