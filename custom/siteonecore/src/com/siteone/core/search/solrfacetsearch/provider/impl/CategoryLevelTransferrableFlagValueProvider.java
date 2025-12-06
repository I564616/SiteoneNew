package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;
import de.hybris.platform.variants.model.GenericVariantProductModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;




public class CategoryLevelTransferrableFlagValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider, Serializable
{
	private FieldNameProvider fieldNameProvider;

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{

		final ProductModel product;
		if (model instanceof GenericVariantProductModel)
		{
			final GenericVariantProductModel variant = (GenericVariantProductModel) model;
			product = variant.getBaseProduct();
		}
		else
		{
			product = (ProductModel) model;
		}

		List<CategoryModel> categoryModels = product.getSupercategories().stream().filter(cat ->
        cat.getCode().startsWith("S") && cat.getCode().length() == 9).collect(Collectors.toList());
		CategoryModel categoryModel=null;
      List<CategoryModel> categoryModelList = product.getSupercategories().stream().filter(cat ->
         cat.getCode().startsWith("S")).collect(Collectors.toList());
      if(categoryModelList != null && !CollectionUtils.isEmpty(categoryModelList) 
      		&& (categoryModels == null || CollectionUtils.isEmpty(categoryModels)))
      {
      	Collection<CategoryModel> categories = categoryModelList.get(0).getAllSupercategories();
      	List<CategoryModel> superCategoryModels = categories.stream().filter(cat ->
      	  cat.getCode().startsWith("S") && cat.getCode().length() == 9).collect(Collectors.toList());
      	if(CollectionUtils.isNotEmpty(superCategoryModels)) 
      	{
      		categoryModel = superCategoryModels.get(0);
      	}
      	else
      	{
      		categoryModel = categoryModelList.get(0);
      	}
      }
      else if(categoryModels != null && !CollectionUtils.isEmpty(categoryModels))
      {
      	categoryModel = categoryModels.get(0);
      }  

		boolean isTransferrableCategory = true;
		if (categoryModel != null && categoryModel.getIsTransferrableCategory() != null
				&& BooleanUtils.isNotTrue(categoryModel.getIsTransferrableCategory()))
		{
			isTransferrableCategory = false;
		}


		return createFieldValue(indexedProperty, isTransferrableCategory);
	}

	private List<FieldValue> createFieldValue(final IndexedProperty indexedProperty, final boolean isTransferrableByCategory)
	{
		final List<FieldValue> fieldValues = new ArrayList<>();
		final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, null);
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, isTransferrableByCategory));
		}

		return fieldValues;
	}

	public FieldNameProvider getFieldNameProvider()
	{
		return fieldNameProvider;
	}

	public void setFieldNameProvider(final FieldNameProvider fieldNameProvider)
	{
		this.fieldNameProvider = fieldNameProvider;
	}
}