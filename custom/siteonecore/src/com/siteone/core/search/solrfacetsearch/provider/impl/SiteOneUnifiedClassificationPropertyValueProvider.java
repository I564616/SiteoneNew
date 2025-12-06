/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.catalog.model.classification.ClassificationClassModel;
import de.hybris.platform.classification.ClassificationService;
import de.hybris.platform.classification.features.Feature;
import de.hybris.platform.classification.features.FeatureList;
import de.hybris.platform.classification.features.FeatureValue;
import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.impl.CommerceClassificationPropertyValueProvider;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.search.restriction.SearchRestrictionService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.siteone.core.search.solrfacetsearch.provider.util.SiteOneUnifiedClassificationPropertyProviderCache;


/**
 *
 */
public class SiteOneUnifiedClassificationPropertyValueProvider extends CommerceClassificationPropertyValueProvider
{
	FieldNameProvider fieldNameProvider;
	@Resource
	ClassificationService classificationService;
	@Resource
	SessionService sessionService;
	@Resource
	UserService userService;
	@Resource
	ModelService modelService;
	@Resource(name = "searchRestrictionService")
	private SearchRestrictionService searchRestrictionService;


	private static final DecimalFormat df = new DecimalFormat("###.######");

	private static String CLASSIFICATION_PREFIX;

	private static final Logger LOG = LoggerFactory.getLogger(SiteOneUnifiedClassificationPropertyValueProvider.class);

	private final ThreadLocal<SiteOneUnifiedClassificationPropertyProviderCache> threadLocalCache = ThreadLocal.withInitial(() -> {
		final SiteOneUnifiedClassificationPropertyProviderCache cache = new SiteOneUnifiedClassificationPropertyProviderCache();
		return cache;
	});

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		if (!(model instanceof ProductModel))
		{
			throw new FieldValueProviderException("Cannot provide classification property of non-product item");
		}
		if (indexConfig.getBaseSite().getUid().equals("siteone"))
		{
			CLASSIFICATION_PREFIX = "siteoneClassification/1/";
		}
		else
		{
			CLASSIFICATION_PREFIX = "SiteOneCAClassification/1.0/";
		}
		final ProductModel base = (ProductModel) model;

		// Parse the single raw CSV from valueProviderParameter
		final String csv = indexedProperty.getValueProviderParameter();
		final List<String> targetCodes = parseCsv(csv);
		if (targetCodes.isEmpty())
		{
			return Collections.emptyList();
		}
		FeatureList combinedFeatureList;
		String leafCategoryprefix;
		final String cacheBaseProductCode = this.threadLocalCache.get().getBaseProductCode();
		if (this.threadLocalCache.get().getFeatureList().isEmpty() || !cacheBaseProductCode.equals(base.getCode()))
		{
			LOG.info("inside First load of Product Code = " + base.getCode());
			// merged FeatureList from Base And All Variants
			combinedFeatureList = mergedFeatureBaseAndVariant(base, classificationService, sessionService, searchRestrictionService);
			this.threadLocalCache.get().setFeatureList(combinedFeatureList);
			this.threadLocalCache.get().setBaseProductCode(base.getCode());

			leafCategoryprefix = getLeafCategoryPrefix(base, searchRestrictionService);
			LOG.info("inside First load FeatureListSize = " + combinedFeatureList.getFeatures().size());
			this.threadLocalCache.get().setLeafCategoryPrefix(leafCategoryprefix);
		}
		else
		{
			combinedFeatureList = this.threadLocalCache.get().getFeatureList();
			leafCategoryprefix = this.threadLocalCache.get().getLeafCategoryPrefix();
		}
		final List<FieldValue> out = new ArrayList<>();

		//get fieldNames for all locals as we have field localized
		final Collection<String> fieldNames = new LinkedHashSet<>();
		if (indexedProperty.isLocalized())
		{
			for (final LanguageModel lang : indexConfig.getLanguages())
			{
				final Collection<String> fieldname = fieldNameProvider.getFieldNames(indexedProperty, lang.getIsocode());
				fieldNames.addAll(fieldname);
			}
		}
		else
		{
			final Collection<String> fieldname = fieldNameProvider.getFieldNames(indexedProperty, (String) null);
			fieldNames.addAll(fieldname);
		}

		final Set<String> renderedValues = new HashSet<String>();

		if (!combinedFeatureList.isEmpty())
		{
			for (final String code : targetCodes)
			{
				//appending classification attribute code

				final String prefix_category_and_code = leafCategoryprefix.concat(code.toLowerCase());
				final Feature feature = combinedFeatureList.getFeatureByCode(prefix_category_and_code);
				if (feature != null)
				{
					LOG.info("feature is not null");
					final List<FeatureValue> fvals = feature.getValues();
					if (!CollectionUtils.isEmpty(fvals))
					{
						for (final FeatureValue fv : fvals)
						{
							if (fv != null)
							{
								//render values based on type with their Unit
								final String renderedValue = render(fv);
								renderedValues.add(renderedValue);
							}
						}
					}
				}
			}
		}
		for (final String fieldName : fieldNames)
		{
			for (final String value : renderedValues)
			{
				out.add(new FieldValue(fieldName, value));
			}
		}
		return out;
	}

	private static List<String> parseCsv(final String csv)
	{
		if (StringUtils.isBlank(csv))
		{
			return Collections.emptyList();
		}
		return Arrays.stream(csv.split(",")).map(StringUtils::trim).filter(StringUtils::isNotBlank).distinct()
				.collect(Collectors.toList());
	}

	private static FeatureList mergedFeatureBaseAndVariant(final ProductModel base,
			final ClassificationService classificationService, final SessionService sessionService,
			final SearchRestrictionService searchRestrictionService)
	{
		final List<ProductModel> baseAndVariants = new ArrayList<>();
		baseAndVariants.add(base);
		baseAndVariants.addAll(base.getVariants());
		LOG.info("Combined Base + Variant = " + baseAndVariants.size());
		sessionService.setAttribute("disableRestrictions", Boolean.TRUE);
		searchRestrictionService.disableSearchRestrictions();
		final List<Feature> featurelist = new ArrayList<Feature>();

		for (final ProductModel productModel : baseAndVariants)
		{
			final FeatureList fl = classificationService.getFeatures(productModel);
			if (!fl.isEmpty())
			{
				LOG.info("Feature List is not Empty for code " + productModel.getCode());
				featurelist.addAll(fl.getFeatures());
			}
		}
		return new FeatureList(featurelist);
	}

	private static String render(final FeatureValue fv)
	{
		Object value = fv.getValue();
		if (value instanceof Double)
		{
			value = df.format(((Double) value).doubleValue());
			fv.setValue(value);
		}

		if (fv.getUnit() != null)
		{
			value = value + " " + fv.getUnit().getSymbol();
			fv.setValue(value);
		}
		return value.toString();
	}

	private static String getLeafCategoryPrefix(final ProductModel base, final SearchRestrictionService searchRestrictionService)
	{
		searchRestrictionService.disableSearchRestrictions();
		String leafNode = new String();
		for (final ClassificationClassModel classificationClassModel : base.getClassificationClasses())
		{
			if (leafNode.length() < classificationClassModel.getCode().length())
			{
				leafNode = classificationClassModel.getCode();
			}
		}
		searchRestrictionService.enableSearchRestrictions();
		//building code by appending ClassificationCatalog code/SystemVersion code/classificationClass code
		final StringBuilder prefixed_category = new StringBuilder();
		prefixed_category.append(CLASSIFICATION_PREFIX);
		prefixed_category.append(leafNode);
		prefixed_category.append('.');

		return prefixed_category.toString();
	}

	/**
	 * @param fieldNameProvider
	 *           the fieldNameProvider to set
	 */
	@Override
	public void setFieldNameProvider(final FieldNameProvider fieldNameProvider)
	{
		this.fieldNameProvider = fieldNameProvider;
	}
}
