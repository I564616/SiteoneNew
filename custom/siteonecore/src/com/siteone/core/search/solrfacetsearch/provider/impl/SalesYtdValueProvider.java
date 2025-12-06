/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import com.siteone.core.model.ProductSalesInfoModel;
import com.siteone.core.services.SiteOneProductSalesService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import jakarta.annotation.Resource;
import java.io.Serializable;
import java.util.*;

/**
 * @author 1229803
 */


public class SalesYtdValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider, Serializable
{
	private FieldNameProvider fieldNameProvider;

	@Resource(name = "siteOneProductSalesService")
	private transient SiteOneProductSalesService siteOneProductSalesService;

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		if (model instanceof ProductModel)
		{
			final ProductModel product = (ProductModel) model;
			final Collection<FieldValue> fieldValues = new ArrayList<>();
			final Collection<ProductSalesInfoModel> productSalesInfoList = siteOneProductSalesService.getSalesByProductCode(product.getCode());

			if (CollectionUtils.isNotEmpty(productSalesInfoList))
			{
				for (final ProductSalesInfoModel productSalesInfo : productSalesInfoList)
				{
					if (StringUtils.isEmpty(productSalesInfo.getRegion()))
					{
						fieldValues.addAll(createFieldValue(productSalesInfo.getYtdSales(), indexedProperty));
						break;
					}
				}
			}

			return fieldValues;
		}
		else
		{
			throw new FieldValueProviderException("Cannot get value of non-product item");
		}
	}

	private Collection<? extends FieldValue> createFieldValue(final Double ytdSales, final IndexedProperty indexedProperty)
	{
		final List<FieldValue> fieldValues = new ArrayList<>();
		final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, null);
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, ytdSales));
		}
		return fieldValues;
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
	 * @return the fieldNameProvider
	 */
	public FieldNameProvider getFieldNameProvider()
	{
		return fieldNameProvider;
	}

}

