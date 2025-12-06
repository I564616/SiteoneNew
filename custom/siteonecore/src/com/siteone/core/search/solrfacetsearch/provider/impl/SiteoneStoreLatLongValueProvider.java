package com.siteone.core.search.solrfacetsearch.provider.impl;


import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * @author nmangal
 *
 */
public class SiteoneStoreLatLongValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider, Serializable
{
    private FieldNameProvider fieldNameProvider;
    private ModelService modelService;
    private EnumerationService enumerationService;

    @Override
    public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
                                                 final Object model) throws FieldValueProviderException
    {
        final PointOfServiceModel store = (PointOfServiceModel) model;
        final Collection<FieldValue> fieldValues = new ArrayList<>();

        fieldValues.addAll(createFieldValue(store.getLatitude()+","+store.getLongitude(), indexedProperty));


        return fieldValues;
    }

    /**
     * @param storeLatLong
     * @param indexedProperty
     * @return
     */
    private Collection<? extends FieldValue> createFieldValue(final String storeLatLong, final IndexedProperty indexedProperty)
    {
        final List<FieldValue> fieldValues = new ArrayList<>();
        final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, null);
        for (final String fieldName : fieldNames)
        {
            if (null != storeLatLong)
            {
                fieldValues.add(new FieldValue(fieldName, storeLatLong));
            }
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
     * @return the modelService
     */
    public ModelService getModelService()
    {
        return modelService;
    }

    /**
     * @param modelService
     *           the modelService to set
     */
    @Override
    public void setModelService(final ModelService modelService)
    {
        this.modelService = modelService;
    }


}
