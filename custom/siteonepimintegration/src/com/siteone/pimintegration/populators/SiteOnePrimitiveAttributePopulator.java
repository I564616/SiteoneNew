/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.siteone.pimintegration.populators;

import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.inboundservices.persistence.PersistenceContext;
import de.hybris.platform.inboundservices.persistence.PrimitiveValueHandler;
import de.hybris.platform.inboundservices.persistence.populator.AbstractAttributePopulator;
import de.hybris.platform.inboundservices.persistence.populator.MissingRequiredAttributeValueException;
import de.hybris.platform.inboundservices.persistence.populator.NullPrimitiveValueHandler;
import de.hybris.platform.integrationservices.model.TypeAttributeDescriptor;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.variants.model.GenericVariantProductModel;




public class SiteOnePrimitiveAttributePopulator extends AbstractAttributePopulator
{
	private PrimitiveValueHandler primitiveValueHandler = new NullPrimitiveValueHandler();
	private ModelService modelService;
	private ProductModel convertedModel;
	
	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService the modelService to set
	 */
	public void setModelService(ModelService modelService)
	{
		this.modelService = modelService;
	}
	
	@Override
	protected boolean isApplicable(final TypeAttributeDescriptor attribute, final PersistenceContext context)
	{
		return attribute.isPrimitive() && !attribute.isCollection();
	}

	@Override
	protected void populateAttribute(final ItemModel item, final TypeAttributeDescriptor attribute,
			final PersistenceContext context)
	{

		if((item instanceof ProductModel || item instanceof GenericVariantProductModel) && null !=modelService.getAttributeValue(item, "convertedProduct"))
		{
			convertedModel=modelService.getAttributeValue(item, "convertedProduct");
			if (convertedModel instanceof ProductModel || (convertedModel instanceof GenericVariantProductModel)
					&& !item.getItemModelContext().getDirtyAttributes().contains("productKind"))
			{
				invokeAttributeAccessor(item, "productKind", context, null);
			}
			if (convertedModel instanceof ProductModel && !(convertedModel instanceof GenericVariantProductModel)
					&& !item.getItemModelContext().getDirtyAttributes().contains("supercategories"))
			{
				invokeAttributeAccessor(item, "superCategoryClassPath", context, null);
			}
			if (convertedModel instanceof GenericVariantProductModel && !item.getItemModelContext().getDirtyAttributes().contains("baseProduct"))
			{
				invokeAttributeAccessor(item, "baseProductCode", context, null);
			}
			if (!existingKeyAttribute(item, attribute) && !attribute.getAttributeName().equalsIgnoreCase("superCategoryClassPath")
					&& !attribute.getAttributeName().equalsIgnoreCase("baseProductCode"))
			{
				invokeAttributeAccessor(item, null, context, attribute);
			}
		}
		else
		{
			if (item instanceof ProductModel && !(item instanceof GenericVariantProductModel)
					&& !item.getItemModelContext().getDirtyAttributes().contains("supercategories"))
			{
				invokeAttributeAccessor(item, "superCategoryClassPath", context, null);
			}
			if (item instanceof GenericVariantProductModel && !item.getItemModelContext().getDirtyAttributes().contains("baseProduct"))
			{
				invokeAttributeAccessor(item, "baseProductCode", context, null);
			}
			if (!existingKeyAttribute(item, attribute) && !attribute.getAttributeName().equalsIgnoreCase("superCategoryClassPath")
					&& !attribute.getAttributeName().equalsIgnoreCase("baseProductCode"))
			{
				invokeAttributeAccessor(item, null, context, attribute);
			}
		}

		}
	public void invokeAttributeAccessor(final ItemModel item, final String attributeName, final PersistenceContext context,
			TypeAttributeDescriptor attributeDescriptor)
	{
		if (attributeName != null)
		{
			final TypeAttributeDescriptor attributeDesc = context.getIntegrationItem().getAttributes().stream()
					.filter(a -> a.getAttributeName().equalsIgnoreCase(attributeName)).findFirst().orElse(null);
			attributeDescriptor = attributeDesc;
		}
		if (null != attributeDescriptor)
		{
			final Object payloadValue = context.getIntegrationItem().getAttribute(attributeDescriptor);
			nonNullableAttributeValueValidation(attributeDescriptor, context, payloadValue);
			final var attrValue = primitiveValueHandler.convert(attributeDescriptor, payloadValue);
			attributeDescriptor.accessor().setValue(item, attrValue);
		}
	}

	private void nonNullableAttributeValueValidation(final TypeAttributeDescriptor attribute, final PersistenceContext context,
			final Object payloadValue)
	{
		if (payloadValue == null && !attribute.isNullable())
		{
			throw new MissingRequiredAttributeValueException(attribute, context);
		}
	}

	private boolean existingKeyAttribute(final ItemModel item, final TypeAttributeDescriptor attribute)
	{
		return !isNew(item) && attribute.isKeyAttribute();
	}

	public void setPrimitiveValueHandler(final PrimitiveValueHandler primitiveValueHandler)
	{
		this.primitiveValueHandler = primitiveValueHandler;
	}
}
