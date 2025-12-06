/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.siteone.pimintegration.valueSetter;

import de.hybris.platform.commerceservices.category.CommerceCategoryService;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.integrationservices.model.AttributeValueSetter;
import de.hybris.platform.integrationservices.model.AttributeValueSetterFactory;
import de.hybris.platform.integrationservices.model.ClassificationAttributeValueConverter;
import de.hybris.platform.integrationservices.model.ClassificationAttributeValueHandler;
import de.hybris.platform.integrationservices.model.TypeAttributeDescriptor;
import de.hybris.platform.integrationservices.model.impl.NullAttributeValueSetter;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.List;

import jakarta.annotation.Resource;

import org.assertj.core.util.Lists;


public class SiteOneAttributeValueSetterFactory implements AttributeValueSetterFactory
{
	private static final AttributeValueSetter DEFAULT_SETTER = new NullAttributeValueSetter();
	private ModelService modelService;
	private List<ClassificationAttributeValueHandler> valueHandlers;
	private List<ClassificationAttributeValueConverter> valueConverters;
	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "commerceCategoryService")
	private CommerceCategoryService commerceCategoryService;
	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;

	@Override
	public AttributeValueSetter create(final TypeAttributeDescriptor descriptor)
	{
		// if required, add missing codes from AttributeValueSetterFactory in order to use classification-api features

		if (descriptor != null)
		{
			return new SiteOneStandardAttributeValueSetter(descriptor, getModelService(), commerceCategoryService, userService,
					flexibleSearchService, enumerationService);
		}
		return DEFAULT_SETTER;
	}

	protected ModelService getModelService()
	{
		return modelService;
	}

	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	public List<ClassificationAttributeValueHandler> getValueHandlers()
	{
		return valueHandlers != null ? valueHandlers : Lists.emptyList();
	}

	public void setValueHandlers(final List<ClassificationAttributeValueHandler> valueHandlers)
	{
		this.valueHandlers = valueHandlers;
	}

	protected List<ClassificationAttributeValueConverter> getValueConverters()
	{
		return valueConverters != null ? valueConverters : Lists.emptyList();
	}

	public void setValueConverters(final List<ClassificationAttributeValueConverter> valueConverters)
	{
		this.valueConverters = valueConverters;
	}
}
