/**
 *
 */
package com.siteone.core.content.search.provider.impl;



import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;


/**
 * @author Ravi P
 *
 */
public class ContentPageCategoryNameProvider extends AbstractPropertyFieldValueProvider
		implements Serializable, FieldValueProvider
{
	private static final Logger LOG = Logger.getLogger(ContentPageMediaValueProvider.class);


	private FieldNameProvider fieldNameProvider;
	private CommonI18NService commonI18NService;
	private I18NService i18nService;

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

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		final List<FieldValue> fieldValues = new ArrayList<FieldValue>();

		if (model instanceof ContentPageModel)
		{
			final ContentPageModel page = (ContentPageModel) model;

			final CategoryModel category = page.getCategory();

			if (indexedProperty.isLocalized())
			{
				final Collection<LanguageModel> languages = indexConfig.getLanguages();
				for (final LanguageModel language : languages)
				{
					fieldValues.addAll(createFieldValues(indexedProperty, category, language));
				}
			}

		}

		return fieldValues;
	}


	protected Collection<FieldValue> createFieldValues(final IndexedProperty indexedProperty, final CategoryModel category,
			final LanguageModel language)
	{
		final List<FieldValue> fieldValues = new ArrayList<FieldValue>();

		i18nService.setCurrentLocale(commonI18NService.getLocaleForLanguage(language));

		final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty,
				language == null ? null : language.getIsocode());
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, category.getName()));
		}

		return fieldValues;
	}


	/**
	 * @return the i18nService
	 */
	public I18NService getI18nService()
	{
		return i18nService;
	}


	/**
	 * @param i18nService
	 *           the i18nService to set
	 */
	@Override
	public void setI18nService(final I18NService i18nService)
	{
		this.i18nService = i18nService;
	}

	/**
	 * @return the commonI18NService
	 */
	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}


	/**
	 * @param commonI18NService
	 *           the commonI18NService to set
	 */
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}


}
