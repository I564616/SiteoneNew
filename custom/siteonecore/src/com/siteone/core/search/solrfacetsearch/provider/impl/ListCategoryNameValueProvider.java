/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

/**
 * @author BS
 *
 */

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.CategorySource;
import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.impl.CategoryCodeValueProvider;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.wishlist2.model.Wishlist2EntryModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;

import com.siteone.core.category.source.SiteOneCategorySource;


/**
 * Category code value provider. Value provider that generates field values for category codes. This implementation uses
 * a {@link CategorySource} to provide the list of categories.
 */
public class ListCategoryNameValueProvider extends CategoryCodeValueProvider
{
	private static final Logger LOG = Logger.getLogger(ListCategoryNameValueProvider.class);
	private SiteOneCategorySource categorySource;
	@Resource(name = "modelService")
	private ModelService modelService;

	/**
	 * @return the categorySource
	 */
	@Override
	public SiteOneCategorySource getCategorySource()
	{
		return categorySource;
	}

	/**
	 * @param categorySource
	 *           the categorySource to set
	 */
	public void setCategorySource(final SiteOneCategorySource categorySource)
	{
		this.categorySource = categorySource;
	}

	private FieldNameProvider fieldNameProvider;
	private CommonI18NService commonI18NService;

	@Override
	protected FieldNameProvider getFieldNameProvider()
	{
		return fieldNameProvider;
	}

	@Override
	public void setFieldNameProvider(final FieldNameProvider fieldNameProvider)
	{
		this.fieldNameProvider = fieldNameProvider;
	}

	@Override
	protected CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	@Override
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		final Wishlist2EntryModel listEntry = (Wishlist2EntryModel) model;
		final Collection<CategoryModel> categories = getCategorySource().getCategoriesForConfigAndProperty(indexConfig,
				indexedProperty, listEntry.getProduct());
		if (categories != null && !categories.isEmpty())
		{
			final Collection<FieldValue> fieldValues = new ArrayList<FieldValue>();

			if (indexedProperty.isLocalized())
			{
				final Collection<LanguageModel> languages = indexConfig.getLanguages();
				for (final LanguageModel language : languages)
				{
					for (final CategoryModel category : categories)
					{
						fieldValues.addAll(createFieldValue(category, language, indexedProperty));
					}
				}
			}
			else
			{
				for (final CategoryModel category : categories)
				{
					fieldValues.addAll(createFieldValue(category, null, indexedProperty));
				}
			}
			return fieldValues;
		}
		else
		{
			LOG.error("ListCategoryNameValueProvider : Category Empty " + listEntry.getProduct().getCode());
			final List<FieldValue> fieldValues = new ArrayList<FieldValue>();
			final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, null);
			for (final String fieldName : fieldNames)
			{
				fieldValues.add(new FieldValue(fieldName, ""));
			}
			return fieldValues;
		}
	}


	@Override
	protected List<FieldValue> createFieldValue(final CategoryModel category, final LanguageModel language,
			final IndexedProperty indexedProperty)
	{
		final List<FieldValue> fieldValues = new ArrayList<FieldValue>();
		final Object value = getPropertyValue(category);
		final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, null);
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, value));
		}

		return fieldValues;
	}

	@Override
	protected Object getPropertyValue(final Object model)
	{
		return getPropertyValue(model, "name");
	}

	@Override
	protected Object getPropertyValue(final Object model, final String propertyName)
	{
		return modelService.getAttributeValue(model, propertyName);
	}
}
