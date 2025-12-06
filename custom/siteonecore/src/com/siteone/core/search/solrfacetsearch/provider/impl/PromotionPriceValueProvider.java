/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import com.siteone.core.services.SiteOneProductService;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.promotionengineservices.model.PromotionSourceRuleModel;
import de.hybris.platform.promotionengineservices.model.RuleBasedPromotionModel;
import de.hybris.platform.promotions.PromotionsService;
import de.hybris.platform.promotions.model.AbstractPromotionModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;
import de.hybris.platform.variants.model.VariantProductModel;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.siteone.integration.jobs.promotions.data.action.PromotionContainerAction;
import com.siteone.integration.jobs.promotions.data.action.PromotionFixedPriceAction;

import jakarta.annotation.Resource;


/**
 * This ValueProvider will provide a list of promotion codes associated with the product. This implementation uses only
 * the DefaultPromotionGroup.
 */
public class PromotionPriceValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider, Serializable
{
	private FieldNameProvider fieldNameProvider;
	private PromotionsService promotionsService;
	private FlexibleSearchService flexibleSearchService;
	@Resource(name = "siteOneProductService")
	private SiteOneProductService siteOneProductService;

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		if (model instanceof ProductModel)
		{
			final ProductModel product = (ProductModel) model;

			final Collection<FieldValue> fieldValues = new ArrayList<FieldValue>();

			final Collection<VariantProductModel> variants = siteOneProductService.getActiveVariantProducts(product);
			if (CollectionUtils.isEmpty(variants) || (CollectionUtils.isNotEmpty(variants) && variants.size() > 1))
			{
				fieldValues.addAll(createFieldValue(product, indexConfig, indexedProperty));
			}
			else
			{
				for (final VariantProductModel variant : variants)
				{
					fieldValues.addAll(createFieldValue(variant, indexConfig, indexedProperty));
				}
			}

			return fieldValues;
		}
		else
		{
			throw new FieldValueProviderException("Cannot get promotion codes of non-product item");
		}
	}


	protected List<FieldValue> createFieldValue(final ProductModel product, final IndexConfig indexConfig,
			final IndexedProperty indexedProperty)
	{
		final List<FieldValue> fieldValues = new ArrayList<FieldValue>();
		final BaseSiteModel baseSiteModel = indexConfig.getBaseSite();
		if (baseSiteModel != null && baseSiteModel.getDefaultPromotionGroup() != null)
		{
			final List<? extends AbstractPromotionModel> listOfPromotions = getPromotionsService().getAbstractProductPromotions(
					Collections.singletonList(baseSiteModel.getDefaultPromotionGroup()), product, true, new Date());

			final AbstractPromotionModel promotion = listOfPromotions.get(0);

			final String description = ((RuleBasedPromotionModel) promotion).getMessageFired();

			final HashMap params = new HashMap();
			params.put("code", promotion.getTitle());
			final SearchResult searchResult = getFlexibleSearchService()
					.search("SELECT {pk} FROM {PromotionSourceRule} WHERE {code} = ?code", params);
			if (searchResult != null)
			{
				final PromotionSourceRuleModel promotionSourceRuleModel = (PromotionSourceRuleModel) searchResult.getResult().get(0);

				final Type actionListType = new TypeToken<ArrayList<PromotionFixedPriceAction>>()
				{}.getType();
				String salePrice = null;
				final Type containerActionListType = new TypeToken<ArrayList<PromotionContainerAction>>()
				{}.getType();
				final Gson gson = new Gson();
				if (!promotionSourceRuleModel.getConditions().contains("y_qualifying_coupons"))
				{
					if (promotionSourceRuleModel.getActions().contains("y_order_entry_percentage_discount")
							|| promotionSourceRuleModel.getActions().contains("y_partner_order_entry_percentage_discount"))
					{
						final List<PromotionContainerAction> promotionContainerAction = gson
								.fromJson(promotionSourceRuleModel.getActions(), containerActionListType);
						if (promotionContainerAction.get(0).getDefinitionId().equalsIgnoreCase("y_order_entry_percentage_discount"))
						{
							final String definitionid = promotionContainerAction.get(0).getDefinitionId();
							final PromotionContainerAction.Parameters param = promotionContainerAction.get(0).getParameters();
							if (null != param.getValue())
							{
								salePrice = promotionContainerAction.get(0).getParameters().getValue().getValue().toString();
								addFieldValues(fieldValues, indexedProperty, null, definitionid, salePrice, description);
							}
						}
						else if (promotionContainerAction.get(0).getDefinitionId()
								.equalsIgnoreCase("y_partner_order_entry_percentage_discount"))
						{
							final String definitionid = promotionContainerAction.get(0).getDefinitionId();
							final PromotionContainerAction.Parameters param = promotionContainerAction.get(0).getParameters();
							if (null != param.getValue())
							{
								salePrice = promotionContainerAction.get(0).getParameters().getValue().getValue().toString();
								addFieldValues(fieldValues, indexedProperty, null, definitionid, salePrice, description);
							}
						}
					}

					else
					{
						if (promotionSourceRuleModel.getActions().contains("y_order_entry_fixed_price")
								|| promotionSourceRuleModel.getActions().contains("y_order_entry_fixed_discount")
								|| promotionSourceRuleModel.getActions().contains("y_partner_order_entry_fixed_price")
								|| promotionSourceRuleModel.getActions().contains("y_target_bundle_price"))
						{

							final List<PromotionFixedPriceAction> promotionFixedPriceAction = gson
									.fromJson(promotionSourceRuleModel.getActions(), actionListType);

							if (promotionFixedPriceAction.get(0).getDefinitionId().equalsIgnoreCase("y_order_entry_fixed_price"))
							{
								final String definitionid = promotionFixedPriceAction.get(0).getDefinitionId();
								final PromotionFixedPriceAction.Parameters param = promotionFixedPriceAction.get(0).getParameters();
								if (null != param.getValue())
								{
									salePrice = promotionFixedPriceAction.get(0).getParameters().getValue().getValue().getUSD().toString();
									addFieldValues(fieldValues, indexedProperty, null, definitionid, salePrice, description);
								}
							}
							else if (promotionFixedPriceAction.get(0).getDefinitionId().equalsIgnoreCase("y_order_entry_fixed_discount"))
							{
								final String definitionid = promotionFixedPriceAction.get(0).getDefinitionId();
								final PromotionFixedPriceAction.Parameters param = promotionFixedPriceAction.get(0).getParameters();
								if (null != param.getValue())
								{
									salePrice = promotionFixedPriceAction.get(0).getParameters().getValue().getValue().getUSD().toString();
									addFieldValues(fieldValues, indexedProperty, null, definitionid, salePrice, description);
								}
							}
							else if (promotionFixedPriceAction.get(0).getDefinitionId()
									.equalsIgnoreCase("y_partner_order_entry_fixed_price"))
							{
								final String definitionid = promotionFixedPriceAction.get(0).getDefinitionId();
								final PromotionFixedPriceAction.Parameters param = promotionFixedPriceAction.get(0).getParameters();
								if (null != param.getValue())
								{
									salePrice = String.valueOf(0);
									addFieldValues(fieldValues, indexedProperty, null, definitionid, salePrice, description);
								}
							}

							else if (promotionFixedPriceAction.get(0).getDefinitionId().equalsIgnoreCase("y_target_bundle_price"))
							{
								final String definitionid = promotionFixedPriceAction.get(0).getDefinitionId();
								final PromotionFixedPriceAction.Parameters param = promotionFixedPriceAction.get(0).getParameters();
								if (null != param.getValue())
								{
									salePrice = String.valueOf(0);
									addFieldValues(fieldValues, indexedProperty, null, definitionid, salePrice, description);
								}
							}
						}
					}
				}
			}

		}
		return fieldValues;
	}


	protected void addFieldValues(final List<FieldValue> fieldValues, final IndexedProperty indexedProperty,
			final LanguageModel language, final String definitionid, final String salePrice, final String description)
	{
		final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty,
				language == null ? null : language.getIsocode());
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, definitionid + "||" + salePrice + "||" + description));
		}
	}

	protected FieldNameProvider getFieldNameProvider()
	{
		return fieldNameProvider;
	}

	public void setFieldNameProvider(final FieldNameProvider fieldNameProvider)
	{
		this.fieldNameProvider = fieldNameProvider;
	}

	/**
	 * @return the flexibleSearchService
	 */
	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	/**
	 * @param flexibleSearchService
	 *           the flexibleSearchService to set
	 */
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}

	/**
	 * @return the promotionsService
	 */
	public PromotionsService getPromotionsService()
	{
		return promotionsService;
	}

	/**
	 * @param promotionsService
	 *           the promotionsService to set
	 */
	public void setPromotionsService(final PromotionsService promotionsService)
	{
		this.promotionsService = promotionsService;
	}


}