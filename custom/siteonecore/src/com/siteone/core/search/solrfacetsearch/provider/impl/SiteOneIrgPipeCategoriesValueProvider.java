/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.impl.CommerceClassificationPropertyValueProvider;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;


/**
 * @author i849388
 *
 */
public class SiteOneIrgPipeCategoriesValueProvider extends CommerceClassificationPropertyValueProvider
{
	private SiteoneClassificationUtils siteoneClassificationUtils;
	private static final Logger LOG = Logger.getLogger(SiteOneIrgPipeCategoriesValueProvider.class);
	private final String INDEXATTRIBUTE = "irgPipeCategories";
	private final String PIPECATEGORY = "Pipe Category";


	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		final Collection<FieldValue> fieldValues = new ArrayList<>();
		if (model instanceof ProductModel && indexedProperty.getName().equalsIgnoreCase(INDEXATTRIBUTE))
		{
			final String pipeCategory = siteoneClassificationUtils.getClassificationAttributeValue(model, PIPECATEGORY);
			if (pipeCategory != null)
			{
				LOG.info("pipeCategory: " + pipeCategory);
				final List<String> pipeCategoriesList = getPipeCategoriesList(pipeCategory);
				for (final String pipeCategoryValue : pipeCategoriesList)
				{
					fieldValues.addAll(siteoneClassificationUtils.createFieldValue(pipeCategoryValue, indexedProperty));

				}
			}
		}
		return fieldValues;
	}

	private List<String> getPipeCategoriesList(final String pipeCategory)
	{

		final String whiteSpace = " ";
		final String removeWhiteSpace = "";
		final List<String> pipeCategoriesList = new ArrayList<String>();
		pipeCategoriesList.add(pipeCategory);
		if (pipeCategory.contains(" "))
		{
			pipeCategoriesList.add(pipeCategory.replace(whiteSpace, removeWhiteSpace));
			final String[] pipeCategoriesArray = pipeCategory.split(whiteSpace);
			final String firstPhase = pipeCategoriesArray[0];
			final String secondPhase = pipeCategoriesArray[1];

			LOG.info("firstPhase:" + firstPhase);
			LOG.info("secondPhase:" + secondPhase);

			final String relatedString = getRelatedString(firstPhase);
			LOG.info("relatedString:" + relatedString);

			if (relatedString != null)
			{
				pipeCategoriesList.add(relatedString + secondPhase);
				pipeCategoriesList.add(relatedString + whiteSpace + secondPhase);
			}
		}
		return pipeCategoriesList;
	}

	private static String getRelatedString(final String pipeCategory)
	{
		if (pipeCategory.equalsIgnoreCase("CL"))
		{
			return "Class";
		}
		if (pipeCategory.equalsIgnoreCase("Schedule"))
		{
			return "sch";
		}
		return null;
	}

	/**
	 * @return the siteoneClassificationUtils
	 */
	public SiteoneClassificationUtils getSiteoneClassificationUtils()
	{
		return siteoneClassificationUtils;
	}


	/**
	 * @param siteoneClassificationUtils
	 *           the siteoneClassificationUtils to set
	 */
	public void setSiteoneClassificationUtils(final SiteoneClassificationUtils siteoneClassificationUtils)
	{
		this.siteoneClassificationUtils = siteoneClassificationUtils;
	}
}