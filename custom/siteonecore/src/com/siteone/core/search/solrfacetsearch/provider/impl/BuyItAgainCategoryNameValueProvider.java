/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.impl.CategoryCodeValueProvider;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;

import com.siteone.core.category.source.SiteOneCategorySource;


/**
 * @author pelango
 *
 */
public class BuyItAgainCategoryNameValueProvider extends CategoryCodeValueProvider
{
	private static final Logger LOG = Logger.getLogger(BuyItAgainCategoryNameValueProvider.class);

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;
	private static final String SALES_SITEONE_ROOT_CATEGORY = "sales.hierarchy.root.category";
	private static final String DISPLAY_SITEONE_ROOT_CATEGORY = "display.hierarchy.root.category";
	private SiteOneCategorySource categorySource;
	@Resource(name = "modelService")
	private ModelService modelService;

	@Override
	public SiteOneCategorySource getCategorySource()
	{
		return categorySource;
	}

	public void setCategorySource(final SiteOneCategorySource categorySource)
	{
		this.categorySource = categorySource;
	}

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		final OrderEntryModel orderEntry = (OrderEntryModel) model;
		final ProductModel productModel = orderEntry.getProduct();
		final String salesRootCategoryCode = configurationService.getConfiguration().getString(SALES_SITEONE_ROOT_CATEGORY);
		final String displayRootCategoryCode = configurationService.getConfiguration().getString(DISPLAY_SITEONE_ROOT_CATEGORY);

		final Collection<CategoryModel> categories = getCategorySource().getCategoriesForConfigAndProperty(indexConfig,
				indexedProperty, productModel);
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
								&& !salesRootCategoryCode.equalsIgnoreCase(category.getCode()) && category.getCode().length() == 4)
						{
							fieldValues.addAll(createFieldValue(category, language, indexedProperty));
							break;
						}
					}
				}
			}
			else
			{
				for (final CategoryModel category : categories)
				{
					if (!category.getCode().startsWith(displayRootCategoryCode)
							&& !salesRootCategoryCode.equalsIgnoreCase(category.getCode()) && category.getCode().length() == 4)
					{
						fieldValues.addAll(createFieldValue(category, null, indexedProperty));
						break;
					}
				}
			}
			return fieldValues;
		}
		else
		{
			LOG.error("BuyItAgainCategoryNameValueProvider : Category Empty " + orderEntry.getPk());
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

		if (language != null)
		{
			Object value = null;
			final Locale locale = i18nService.getCurrentLocale();
			try
			{
				i18nService.setCurrentLocale(getCommonI18NService().getLocaleForLanguage(language));
				value = getPropertyValue(category);
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
			final Object value = getPropertyValue(category);
			final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, null);
			for (final String fieldName : fieldNames)
			{
				fieldValues.add(new FieldValue(fieldName, value));
			}

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
