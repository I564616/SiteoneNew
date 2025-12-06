/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import com.siteone.core.search.solrfacetsearch.provider.util.IndexProductPosInventoryData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * @author i849388
 *
 */
public class ProductInventoryFlagCachingValueProvider extends AbstractProductInventoryCachingValueProvider
        implements FieldValueProvider, Serializable {

    @Override
    public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty, final Object model)
            throws FieldValueProviderException {
        if (model instanceof ProductModel) {
            final ProductModel product = (ProductModel) model;
            final List<FieldValue> fieldValues = new ArrayList<>();

            loadProductCache(product);


            final Collection<String> allStores = threadLocalCache.get().getAllStores();

            boolean inventoryFlag = false;
            if (!CollectionUtils.isEmpty(allStores)) {
                for (final String storeID : allStores) {
                    inventoryFlag = isInventoryFlag(storeID);
                }
            }
            addFieldValues(fieldValues, indexedProperty, inventoryFlag);

            return fieldValues;
        } else {
            throw new FieldValueProviderException("Cannot get stock of non-product item");
        }
    }

    private boolean isInventoryFlag(final String storeID) {

        boolean inventoryFlag = false;

        final Collection<IndexProductPosInventoryData> inventoryCol = threadLocalCache.get().getAllInventoryForStore(storeID);

        if (!CollectionUtils.isEmpty(inventoryCol)) {

            for (final IndexProductPosInventoryData inventoryData : inventoryCol) {
                inventoryFlag |= isInventoryFlag(inventoryData);

                //stop at first one found
                if (inventoryFlag) {
                    break;
                }
            }
        }

        return inventoryFlag;
    }


    private boolean isInventoryFlag(final IndexProductPosInventoryData inventoryData) {
        return inventoryData.getStockLevelFlag()
                && inventoryData.getInventoryHitCountFlag() && inventoryData.getInventoryCheckFlag();
    }

    protected void addFieldValues(final List<FieldValue> fieldValues, final IndexedProperty indexedProperty, final Object value) {
        final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, null);
        for (final String fieldName : fieldNames) {
            fieldValues.add(new FieldValue(fieldName, value));
        }
    }


}
