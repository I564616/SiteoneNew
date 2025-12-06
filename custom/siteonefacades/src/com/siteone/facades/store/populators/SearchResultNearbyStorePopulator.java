package com.siteone.facades.store.populators;

import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.commerceservices.store.data.GeoPoint;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author nmangal
 *
 */
public class SearchResultNearbyStorePopulator implements Populator<SearchResultValueData, PointOfServiceData>
{

    @Override
    public void populate(final SearchResultValueData source, final PointOfServiceData target) throws ConversionException
    {
        target.setName(this.<String> getValue(source, "soStoreName"));
        target.setDisplayName(this.<String> getValue(source, "soStoreDisplayName"));
        target.setStoreId(this.<String> getValue(source, "soStoreId"));
        target.setRegionId(this.<String> getValue(source, "soRegionId"));
        target.setIsNearbyStoreSelected(Boolean.TRUE);
        target.setExcludeBranches(this.<String> getValue(source, "soExcludeBranches"));
		
        GeoPoint gp = new GeoPoint();

        double latitude = Double.parseDouble(this.<String> getValue(source, "soLattitude"));
        double longitude = Double.parseDouble(this.<String> getValue(source, "soLongitude"));

        gp.setLatitude(latitude);
        gp.setLongitude(longitude);
        target.setGeoPoint(gp);
       }

    protected <T> T getValue(final SearchResultValueData source, final String propertyName)
    {
        if (source.getValues() == null)
        {
            return null;
        }

        // DO NOT REMOVE the cast (T) below, while it should be unnecessary it is required by the javac compiler
        return (T) source.getValues().get(propertyName);
    }

}

