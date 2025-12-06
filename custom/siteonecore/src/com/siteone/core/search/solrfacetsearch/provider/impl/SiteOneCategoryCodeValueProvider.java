/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.impl.CategoryCodeValueProvider;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import jakarta.annotation.Resource;


/**
 * @author 1124932
 *
 */
public class SiteOneCategoryCodeValueProvider extends CategoryCodeValueProvider
{
	@Resource(name = "configurationService")
	private ConfigurationService configurationService;
	private static final String SALES_SITEONE_ROOT_CATEGORY = "sales.hierarchy.root.category";
	private static final String DISPLAY_SITEONE_ROOT_CATEGORY = "display.hierarchy.root.category";

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		final String salesRootCategoryCode = configurationService.getConfiguration().getString(SALES_SITEONE_ROOT_CATEGORY);
		final String displayRootCategoryCode = configurationService.getConfiguration().getString(DISPLAY_SITEONE_ROOT_CATEGORY);
		final Collection<CategoryModel> categories = getCategorySource().getCategoriesForConfigAndProperty(indexConfig,
				indexedProperty, model);
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
						if (!category.getCode().startsWith(displayRootCategoryCode)
								&& !salesRootCategoryCode.equalsIgnoreCase(category.getCode()))
						{
							fieldValues.addAll(createFieldValue(category, language, indexedProperty));
						}
					}
				}
			}
			else
			{
				for (final CategoryModel category : categories)
				{
					if (!category.getCode().startsWith(displayRootCategoryCode)
							&& !salesRootCategoryCode.equalsIgnoreCase(category.getCode()))
					{
						fieldValues.addAll(createFieldValue(category, null, indexedProperty));
					}
				}
			}
			return fieldValues;
		}
		else
		{
			return Collections.emptyList();
		}
	}

	@Override
	protected List<FieldValue> createFieldValue(final CategoryModel category, final LanguageModel language,
			final IndexedProperty indexedProperty)
	{
		final List<FieldValue> fieldValues = new ArrayList<FieldValue>();

		if (language != null)
		{
			final Locale locale = i18nService.getCurrentLocale();
			Object value = null;
			try
			{
				i18nService.setCurrentLocale(getCommonI18NService().getLocaleForLanguage(language));
				value = category.getCode();
			}
			finally
			{
				i18nService.setCurrentLocale(locale);
			}

			final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, language.getIsocode());
			for (final String fieldName : fieldNames)
			{
				fieldValues.add(new FieldValue(fieldName, value));
			}
		}
		else
		{
			final Object value = category.getCode();
			final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, null);
			for (final String fieldName : fieldNames)
			{
				fieldValues.add(new FieldValue(fieldName, value));
			}
		}

		return fieldValues;
	}
}