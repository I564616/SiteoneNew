/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.classification.ClassificationService;
import de.hybris.platform.classification.features.FeatureList;
import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.impl.CommerceClassificationPropertyValueProvider;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.classification.features.Feature;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


/**
 * @author i849388
 *
 */
public class SiteOneWireSizeConductorsValueProvider extends CommerceClassificationPropertyValueProvider
{

	private FieldNameProvider fieldNameProvider;
	private ClassificationService classificationService;
	private static final Logger LOG = Logger.getLogger(SiteOneWireSizeConductorsValueProvider.class);
	private String INDEX_ATTRIBUTE = "irgWireSizeConductors";
	private String WIRE_SIZE = "irgwiresize";
	private String NUMBER_OF_CONDUCTORS = "irgnumberofconductors";

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		final Collection<FieldValue> fieldValues = new ArrayList<>();
		if (model instanceof ProductModel && indexedProperty.getName().equalsIgnoreCase(INDEX_ATTRIBUTE))
		{
			final ProductModel baseProductModel = (ProductModel) model;
			final List<String> wireSizeConductorsValueList = getWireSizeConductors(baseProductModel);

			for (final String wireSizeConductorsValue : wireSizeConductorsValueList)
			{
				fieldValues.addAll(createFieldValue(wireSizeConductorsValue, indexedProperty));
			}
		}

		return fieldValues;
	}


	/**
	 * @param wireSizeConductor
	 * @param indexedProperty
	 * @return fieldValues
	 */
	@SuppressWarnings("boxing")
	private Collection<? extends FieldValue> createFieldValue(final String wireSizeConductor,
			final IndexedProperty indexedProperty)
	{
		final List<FieldValue> fieldValues = new ArrayList<>();
		final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, null);
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, wireSizeConductor));
		}
		return fieldValues;
	}

	private List<String> getWireSizeConductors(final ProductModel product)
	{

		final List<String> wireSizeConductorsValueList = new ArrayList<String>();
		String wireSize = null;
		String numberOfConductor = null;
		final FeatureList featureLists = getClassificationService().getFeatures(product);
		final List<Feature> featureList = featureLists.getFeatures();
		for (Feature feature : featureList)
		{
			if (feature.getCode().toLowerCase().indexOf(WIRE_SIZE.toLowerCase()) > -1 && feature.getValue() != null && feature.getValue().getValue() !=null)
			{
				wireSize = (String)feature.getValue().getValue();
			}

			if (feature.getCode().toLowerCase().indexOf(NUMBER_OF_CONDUCTORS.toLowerCase()) > -1 )
			{

				if( feature.getValue() == null || feature.getValue().getValue() == null || StringUtils.isEmpty(feature.getValue().getValue().toString())){
					numberOfConductor = "1";
				}else{
					try {
						double tempDouble= (double) feature.getValue().getValue();
						int tempInt = (int)tempDouble;
						numberOfConductor = String.valueOf(tempInt);
					}catch (NumberFormatException e) {
						LOG.error("Number of Conductors conversation exception "+ e.getMessage());
					}
				}
			}
		}

		if (wireSize != null && numberOfConductor != null)
		{
			wireSizeConductorsValueList.add(wireSize + "/" + numberOfConductor);
			wireSizeConductorsValueList.add(wireSize + "-" + numberOfConductor);
		}

		return wireSizeConductorsValueList;
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
	public void setFieldNameProvider(FieldNameProvider fieldNameProvider)
	{
		this.fieldNameProvider = fieldNameProvider;
	}



}