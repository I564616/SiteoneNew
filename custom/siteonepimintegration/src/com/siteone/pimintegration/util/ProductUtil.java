package com.siteone.pimintegration.util;

import de.hybris.platform.catalog.ClassificationUtils;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.catalog.model.ProductFeatureModel;
import de.hybris.platform.catalog.model.classification.ClassAttributeAssignmentModel;
import de.hybris.platform.catalog.model.classification.ClassificationAttributeModel;
import de.hybris.platform.catalog.model.classification.ClassificationClassModel;
import de.hybris.platform.catalog.model.classification.ClassificationSystemVersionModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.enumeration.EnumerationValueModel;

import de.hybris.platform.core.model.media.MediaFolderModel;
import de.hybris.platform.core.model.media.MediaFormatModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.search.exceptions.FlexibleSearchException;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.solrfacetsearch.enums.SolrIndexedPropertyFacetType;
import de.hybris.platform.solrfacetsearch.enums.SolrPropertiesTypes;
import de.hybris.platform.solrfacetsearch.model.config.SolrIndexedPropertyModel;
import de.hybris.platform.solrfacetsearch.model.config.SolrIndexedTypeModel;
import de.hybris.platform.util.Config;
import de.hybris.platform.variants.model.VariantCategoryModel;
import de.hybris.platform.variants.model.VariantValueCategoryModel;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.siteone.core.model.GlobalProductNavigationNodeModel;
import com.siteone.pcm.enums.BrandNameEnum;
import com.siteone.pimintegration.model.SiteOneCategoryLevelModel;


/**
 * @author RSivaram
 *
 */
public class ProductUtil
{
	private static final Logger LOG = LoggerFactory.getLogger(ProductUtil.class);
	//private static Class className;
	private static UserService userService;
	private static FlexibleSearchService flexibleSearchService;
	private static String classPath;

	public List<Object> getFlexResult(final String query, final Class className)
	{

		List<Object> resultList = null;
		try
		{
			LOG.info("ProductUtil getflexresult " + query);
			final FlexibleSearchQuery flexQuery = new FlexibleSearchQuery(query);
			flexQuery.setUser(userService.getAdminUser());
			flexQuery.setResultClassList(Arrays.asList(Class.forName(className.getName())));
			final SearchResult<Object> searchResult = flexibleSearchService.search(flexQuery);
			if (null != searchResult && searchResult.getCount() != 0)
			{
				LOG.info("ProductUtil getflexresult result count " + searchResult.getCount());
				resultList = searchResult.getResult();
			}
		}
		catch (final FlexibleSearchException | ClassNotFoundException e)
		{
			LOG.info("ProductUtil getflexresult exception block ");
			LOG.info(e.getMessage());
		}

		return resultList;
	}

	public Object getBrandCode(final String colValue)
	{

		Object searchResult = null;
		LOG.info("ProductUtil getbrandcode null");
		if (!colValue.isEmpty())
		{
			LOG.info("ProductUtil getbrandcode" + colValue);
			searchResult = retrieveBrandCode(colValue);
			// System.out.println(searchResult);
		}
		return searchResult;
	}

	public void setBrandEnum(final String colValue)
	{

		final ModelService modelService = (ModelService) Registry.getApplicationContext().getBean("modelService");
		final EnumerationValueModel newBrandName = (EnumerationValueModel) modelService.create("BrandNameEnum");
		if (!StringUtils.isBlank(colValue))
		{
			newBrandName.setCode("Coreproductbrandname".concat(colValue.toLowerCase().replaceAll("\\s+", "_")));
			newBrandName.setName(colValue);
			modelService.save(newBrandName);
			modelService.refresh(newBrandName);
		}
	}

	private Object retrieveBrandCode(String colValue)
	{
		List<Object> resultBrandCode = null;
		String flexQuery = "";
		Object brandCode = null;
		LOG.info("ProductUtil retrieveBrandCode" + colValue);
		if (colValue.equalsIgnoreCase("Coreproductbrandnamenull"))
		{
			flexQuery = Config.getParameter("QUERY_FOR_BRAND_PK");
			if (flexQuery != null)
			{
				flexQuery = MessageFormat.format(flexQuery, colValue);
				LOG.info("cross-check brand query  :-" + flexQuery.toString());
				resultBrandCode = getFlexResult(flexQuery, BrandNameEnum.class);
				if (resultBrandCode != null && resultBrandCode.get(0) != null)
				{
					LOG.info("ProductUtil retrieveBrandCode resultBrandCode if block");
					brandCode = resultBrandCode.get(0);
				}
			}
		}
		else
		{

			flexQuery = Config.getParameter("QUERY_FOR_BRANDCODE_PK");
			if (flexQuery != null)
			{
				if (colValue.contains("'"))
				{
					colValue = colValue.replaceAll("'", "''");
				}
				flexQuery = MessageFormat.format(flexQuery, colValue);
				LOG.info("cross-check brand query  :-" + flexQuery.toString());
				resultBrandCode = getFlexResult(flexQuery, BrandNameEnum.class);
				if (resultBrandCode != null && resultBrandCode.get(0) != null)
				{
					LOG.info("ProductUtil retrieveBrandCode resultBrandCode else block");
					brandCode = resultBrandCode.get(0);
				}
			}
		}
		return brandCode;

	}

	public List<String> retrieveCode(final String combinedPath, final String catalog)
	{
	    String phCode = "PH1";
	    String flexQuery="";
	    List<Object> resultList=null;
	    final List<String> codes = new ArrayList<>();


	    final String[] levels = combinedPath.split(">");
	    LOG.info("ProductUtil retrieveCode" + combinedPath);
		// L1>L2>L3>L4 code fetch

	    int index = 0;
	    for (String level : levels) {
	        final String[] parts = level.split("\\|");
	        if (parts.length != 2) {
	            LOG.warn("Invalid format in superCategoryClassPath level: " + level);
	            throw new IllegalArgumentException(level);
	        }


	        flexQuery = Config.getParameter("QUERY_CATEGORY_PATH");
	        LOG.info("ProductUtil retrieveCode inside for loop");
	        if (flexQuery != null) {
	        	LOG.info("ProductUtil retrieveCode inside if block");
	            flexQuery = MessageFormat.format(flexQuery, phCode + "%", parts[1].trim(), catalog);
	            resultList = getFlexResult(flexQuery, String.class);


	            if (resultList != null && resultList.get(0) != null) {
	                phCode = (String) resultList.get(0);
	                if (levels.length == index + 1) {
	                	LOG.info(flexQuery);
						LOG.info(phCode + " PH-Code");
	                    codes.add(phCode);
	                    final List<String> shCodes = retrieveSHCode(phCode, catalog);
	                    if (shCodes != null) {
	                        codes.addAll(shCodes);
	                    }
	                }
	                ++index;
	            } else {
	                LOG.warn("No match for name=" + parts[0].trim() + ", pimId=" + parts[1].trim());
	            }
	        }
	    }


	    return CollectionUtils.isEmpty(codes) ? null : codes;
	}

	public List<CategoryModel> categoryExist(final SiteOneCategoryLevelModel category)
	{
		final List<Object> resultSHCode = null;
		String flexQuery = "";
		final String SHCode = null;
		List<Object> resultList = null;
		final List<CategoryModel> categoryList = new ArrayList();
		flexQuery = Config.getParameter("GET_CATEGORY_CODE");
		if (flexQuery != null)
		{
			flexQuery = MessageFormat.format(flexQuery, category.getId());
			resultList = getFlexResult(flexQuery, CategoryModel.class);
			if (resultList != null && resultList.get(0) != null)
			{
				for (final Object result : resultList)
				{
					categoryList.add((CategoryModel) result);
				}
				return categoryList;
			}
			else
			{
				return null;
			}
		}
		return null;

	}

	private List<String> retrieveSHCode(final String colValue, final String catalog)
	{
		List<Object> resultSHCode = null;
		String flexQuery = "";
		String SHCode = null;
		LOG.info("ProductUtil retrieveSHCode ");
		flexQuery = Config.getParameter("QUERY_FOR_SHCODE");
		if (flexQuery != null)
		{
			flexQuery = MessageFormat.format(flexQuery, colValue, catalog);
			resultSHCode = getFlexResult(flexQuery, String.class);
			LOG.info("ProductUtil retrieveSHCode resultSHCode");
			if (resultSHCode != null && resultSHCode.get(0) != null)
			{
				SHCode = resultSHCode.get(0).toString();
				LOG.info("ProductUtil retrieveSHCode resultSHCode" + resultSHCode.get(0).toString());
				LOG.error(SHCode);
			}
		}
		if (null != SHCode)
		{
			return SHCode.contains(":") ? Arrays.asList(SHCode.split(":")) : Arrays.asList(SHCode);
		}
		else
		{
			return null;
		}
	}

	public List<VariantCategoryModel> validateVariantAttributeCodePcm(final String variantAttribute)
	{
		final String categoryName = classPath.substring(0, classPath.indexOf('>')).toLowerCase().replaceAll("\\s+", "");
		final String catogriesAttributeCode = Config.getParameter(categoryName);
		final List<VariantCategoryModel> variantCategories = new ArrayList<>();
		VariantCategoryModel category;
		if (!variantAttribute.contains("@"))
		{
			LOG.info("TEST " + variantAttribute.trim() + ": " + catogriesAttributeCode);
			category = retrieveVariantCode(variantAttribute.trim(), catogriesAttributeCode);
			LOG.info(category + " Msg 1");
			if (category == null)
			{
				LOG.error("Invalid Product :--Variant attribute does not exist:-  ");
			}
			else
			{
				variantCategories.add(category);
			}
		}
		else
		{
			final String[] variantAttributeArray = variantAttribute.split("@");
			for (final String attribute : variantAttributeArray)
			{
				category = retrieveVariantCode(attribute.trim(), catogriesAttributeCode);
				LOG.info(category + " Msg 2");
				if (category == null)
				{
					LOG.error("Invalid Product :--Variant attribute does not exist:-  ");
				}
				else
				{
					variantCategories.add(category);
				}
			}
			//category = variantCode.toString();
		}
		LOG.info("Final return: " + variantCategories);
		return variantCategories;

	}

	public VariantCategoryModel retrieveVariantCode(final String colValue, final String attribueCode)
	{
		List<Object> resultList = null;
		String flexQuery = "";
		VariantCategoryModel variantCategory = null;
		flexQuery = Config.getParameter("QUERY_VARIANT_ATTRIBUTE_PK");
		LOG.info("productutil retrieveVariantCode ");
		if (flexQuery != null)
		{
			flexQuery = MessageFormat.format(flexQuery, colValue.trim());
			LOG.info("retrieveVariantCode Query" + flexQuery);
			resultList = getFlexResult(flexQuery, VariantCategoryModel.class);
			if (resultList != null && resultList.get(0) != null)
			{
				variantCategory = (VariantCategoryModel) resultList.get(0);
				LOG.info("retrieveVariantCode VariantCode " + variantCategory);
			}
		}
		return variantCategory;
	}

	public List<VariantValueCategoryModel> validateVariantValueCodePcm(final String[] superCategories,
			final String variantAttributeValues, final ProductModel baseProduct)
	{
		final Map<Integer, String> superCategoriesName = new HashMap<Integer, String>();
		LOG.info("Productutil validateVariantValueCodePcm #1");
		if (superCategories != null)
		{
			LOG.info("Productutil validateVariantValueCodePcm #2");
			int i = 1;
			for (final String SuperCategoriesCode : superCategories)
			{
				superCategoriesName.put(Integer.valueOf(i), SuperCategoriesCode);
				i++;
			}
		}
		VariantValueCategoryModel variantvalueCodeMsg;
		final List<VariantValueCategoryModel> variantValueList = new ArrayList<>();
		final String variantValueCode = "";
		final int numberOfAttribute = numberofSuperCatorgiesValue(variantAttributeValues);
		if (superCategoriesName.size() == 0)
		{
			LOG.info("Productutil validateVariantValueCodePcm #3");
			return null;
		}
		final boolean validSuperCatogries = validateSuperCategories(superCategoriesName.size(), numberOfAttribute);
		if (validSuperCatogries)
		{
			LOG.info("Productutil validateVariantValueCodePcm #4");
			if (numberOfAttribute == 1)
			{
				variantvalueCodeMsg = retrieveValueCode(superCategoriesName.get(1), variantAttributeValues);
				if (variantvalueCodeMsg == null)
				{
					LOG.info("Productutil validateVariantValueCodePcm #5");
					variantvalueCodeMsg = autoCreationValues(superCategoriesName.get(1), variantAttributeValues.trim(), baseProduct);
				}
				variantValueList.add(variantvalueCodeMsg);
				return variantValueList;
			}
			else
			{
				final String[] variantAttributeArray = variantAttributeValues
						.split(Config.getParameter("variantValuesCode.delimiter"));
				int i = 1;
				for (final String attribute : variantAttributeArray)
				{
					variantvalueCodeMsg = retrieveValueCode(superCategoriesName.get(i), attribute);
					if (variantvalueCodeMsg == null)
					{
						variantvalueCodeMsg = autoCreationValues(superCategoriesName.get(i), attribute.trim(), baseProduct);
					}

					variantValueList.add(variantvalueCodeMsg);
					i = i + 1;
				}
			}
		}
		else
		{
			return null;
		}
		return variantValueList;
	}

	public VariantValueCategoryModel autoCreationValues(final String superCategories, final String lovValues,
			final ProductModel baseProduct)
	{
		String variantLovCode = "";
		final VariantValueCategoryModel categoryValue = new VariantValueCategoryModel();
		int sequence = 0;

		List<Object> resultList = null;
		//ProductUtil.setClassName(String.class);
		String flexQuery = "";
		flexQuery = Config.getParameter("QUERY_FOR_MAXSEQUENCE");
		LOG.info("Productutil autoCreationValues #1");
		if (flexQuery != null)
		{

			flexQuery = MessageFormat.format(flexQuery, superCategories.trim() + "%");
			resultList = getFlexResult(flexQuery, String.class);
			if (resultList != null && resultList.get(0) != null)
			{
				LOG.info("Productutil autoCreationValues #2");
				sequence = Integer.parseInt(resultList.get(0).toString());
			}
		}
		sequence = sequence + 1;


		boolean isSplCharPresent;
		if (variantLovCode.isEmpty())
		{
			isSplCharPresent = checkSplChar(lovValues);
			if (!isSplCharPresent)
			{
				variantLovCode = getFormattedLovCode(lovValues, superCategories);
			}
			else
			{
				LOG.info("Productutil autoCreationValues exception");
				throw new DuplicateKeyException(lovValues);
			}
			final boolean isCodePresent = checkForCodeExit(superCategories, variantLovCode);
			if (!isCodePresent)
			{

				categoryValue.setCode(variantLovCode);
				categoryValue.setName(lovValues, Locale.ENGLISH);
				categoryValue.setSequence(sequence);
				categoryValue.setSupercategories(baseProduct.getSupercategories().stream()
						.filter(s -> s instanceof VariantCategoryModel && s.getCode().equalsIgnoreCase(superCategories))
						.collect(Collectors.toList()));
				categoryValue.setCatalogVersion(baseProduct.getCatalogVersion());
			}
			else
			{
				throw new DuplicateKeyException(lovValues);
			}
		}
		return categoryValue;

	}

	private String getFormattedLovCode(final String lovName, final String lovCategoriesCode)
	{

		final StringBuilder lovCode = new StringBuilder(lovCategoriesCode);
		final Map<Character, String> formatMap = new HashMap<Character, String>();

		try
		{

			// Splitting the pattern based on 'SEP'
			final String patternFormatters = Config.getParameter("lov.patternFormatters");//SiteonepcmConstants.PATTERNFORMATTERS
			final int counter = patternFormatters.split("SEP").length;//SiteonepcmConstants.PATTERN_SEPARATOR
			final String[] splitPatternn = patternFormatters.split("SEP");//SiteonepcmConstants.PATTERN_SEPARATOR

			// Splitting the Array Patterns and Mapping to map based on 'DEM'

			for (int i = 0; i < counter; i++)
			{
				final String[] strings = splitPatternn[i].split("DEM");//MAPPING_SEPARATOR
				if (strings.length == 2)
				{
					formatMap.put(strings[0].charAt(0), strings[1]);
				}
			}

			// Iterating the pattern Map for replacing the special characters to
			// a definition
			final Iterator<Map.Entry<Character, String>> mapIterator = formatMap.entrySet().iterator();
			while (mapIterator.hasNext())
			{
				final Map.Entry<Character, String> entry = mapIterator.next();
				replacement[entry.getKey()] = entry.getValue();
			}
			for (int i = 0; i < lovName.length(); i++)
			{
				lovCode.append(replacement[lovName.charAt(i)]);
			}
		}
		catch (final Exception e)
		{
			return "Please Check the Property File" + e.getMessage();
		}

		String finalLovCode = lovCode.toString();
		finalLovCode = finalLovCode.replaceAll("[^a-zA-Z0-9_]", "");
		return StringUtils.deleteWhitespace(finalLovCode);
	}

	final static String[] replacement = new String[Character.MAX_VALUE + 1];
	static
	{

		for (int i = Character.MIN_VALUE; i <= Character.MAX_VALUE; i++)
		{
			replacement[i] = Character.toString(Character.toLowerCase((char) i));
		}
	}

	public boolean checkForCodeExit(final String superCategories, String valueCode)
	{
		List<Object> resultList = null;
		boolean isCodePresent = false;
		String flexQuery = "";
		flexQuery = Config.getParameter("QUERY_FOR_CODECHECKING");
		LOG.info("Productutil checkForCodeExit" + valueCode);
		if (flexQuery != null)
		{

			if (valueCode.contains("'"))
			{
				valueCode = valueCode.replaceAll("'", "''");
			}
			LOG.info("Productutil checkForCodeExit" + superCategories);
			flexQuery = MessageFormat.format(flexQuery, valueCode, superCategories.trim() + "%");
			resultList = getFlexResult(flexQuery, String.class);
			if (resultList != null && resultList.get(0) != null)
			{
				isCodePresent = true;
				LOG.info("Productutil checkForCodeExit true block");
			}
		}
		return isCodePresent;
	}

	private boolean checkSplChar(final String lovName)
	{
		boolean isSplCharPresent = false;
		LOG.info("Productutil checkSplChar #1" + lovName);
		final Pattern pattern = Pattern.compile("[ a-zxA-Z0-9$#%=!{},`~&*()/'<>?._|/é®\u2122-]*[^@\\t\\r\\n]*");

		// Pattern.compile(Config.getParameter(SiteonepcmConstants.SPL_CHAR_REGEX));
		final Matcher matcher = pattern.matcher(lovName);
		final boolean matchStatus = matcher.matches();
		if (!matchStatus)
		{
			LOG.info("Productutil checkSplChar #1 true");
			isSplCharPresent = true;
		}
		return isSplCharPresent;

	}

	public VariantValueCategoryModel retrieveValueCode(final String attributeCode, String attributeValue)
	{
		List<Object> resultList = null;
		//ProductUtil.setClassName(VariantValueCategoryModel.class);
		String flexQuery = "";
		VariantValueCategoryModel VariantCode = null;
		LOG.info("Productutil retrieveValueCode #1");
		flexQuery = Config.getParameter("QUERY_VARIANT_VALUES_PK");
		if (flexQuery != null)
		{
			if (attributeValue.contains("'"))
			{
				attributeValue = attributeValue.replaceAll("'", "''");
			}
			flexQuery = MessageFormat.format(flexQuery, attributeCode + "%", attributeValue.trim());
			resultList = getFlexResult(flexQuery, VariantValueCategoryModel.class);
			LOG.info("Productutil retrieveValueCode #2");
			if (resultList != null && resultList.get(0) != null)
			{
				LOG.info("Productutil retrieveValueCode #3");
				VariantCode = (VariantValueCategoryModel) resultList.get(0);
			}
		}
		return VariantCode;
	}

	public int numberofSuperCatorgiesValue(final String AttributeValues)
	{
		if (AttributeValues.contains(Config.getParameter("variantValuesCode.delimiter")))
		{
			return AttributeValues.split(Config.getParameter("variantValuesCode.delimiter")).length;
		}
		else
		{
			return 1;
		}
	}

	public boolean validateSuperCategories(final int superCatorgies, final int Attribute)
	{
		if (superCatorgies == Attribute)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static UserService getUserService()
	{
		return userService;
	}

	public static void setUserService(final UserService userService)
	{
		ProductUtil.userService = userService;
	}

	public static FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	public static void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		ProductUtil.flexibleSearchService = flexibleSearchService;
	}

	public static String getClassPath()
	{
		return classPath;
	}

	public static void setClassPath(final String classPath)
	{
		ProductUtil.classPath = classPath;
	}

	/**
	 * @param modelService
	 * @param value
	 *
	 */
	public ProductModel setProductFeature(final ProductModel product, final ClassAttributeAssignmentModel assignment,
			final String code, final ModelService modelService, final String value)
	{
		final List<ProductFeatureModel> features = getProductFeaturesForAttributeAssignment(product, assignment);
		if ((features.isEmpty() && StringUtils.isNotEmpty(value)) || features.size() > 1)
		{
			final ProductFeatureModel newFeature = createNewFeature(product, assignment, value, modelService,
					Integer.valueOf(features.size() + 1));
			addNewFeatureToProductFeatures(product, newFeature);
		}
		else if (features.size() == 1)
		{
			if (StringUtils.isNotEmpty(value))
			{
				if (assignment.getAttributeType().getCode().equalsIgnoreCase("number"))
				{
					final Double num = Double.valueOf(value);
					features.get(0).setValue(num);
				}
				else
				{
					setValueSafelyOnExistingFeature(features.get(0), value);
				}
			}
			else
			{
				removeFeatureFromProductFeatures(product, features.get(0));
			}

		}
		//		addProductToClassificationClassIfNotPresent(product, assignment);
		return product;
	}

	void addProductToClassificationClassIfNotPresent(final ProductModel product, final ClassAttributeAssignmentModel assignment)
	{
		final List<ProductModel> existingClassProducts = assignment.getClassificationClass().getProducts();

		final List<ProductModel> products = existingClassProducts != null ? Lists.newArrayList(existingClassProducts)
				: Lists.newArrayList();
		if (!products.contains(product))
		{
			addProductToClassifyingClass(product, products, assignment);
		}
	}

	private void addProductToClassifyingClass(final ProductModel product, final List<ProductModel> existingClassProducts,
			final ClassAttributeAssignmentModel assignment)
	{
		existingClassProducts.add(product);
		assignment.getClassificationClass().setProducts(existingClassProducts);
	}

	List<ProductFeatureModel> getProductFeaturesForAttributeAssignment(final ProductModel product,
			final ClassAttributeAssignmentModel assignment)
	{
		return Stream.ofNullable(product.getFeatures()).flatMap(Collection::stream)
				.filter(f -> assignment.equals(f.getClassificationAttributeAssignment())).collect(Collectors.toList());
	}

	ProductFeatureModel createNewFeature(final ProductModel product, final ClassAttributeAssignmentModel classAttributeAssignment,
			final Object value, final ModelService modelService, final Integer position)
	{
		Preconditions.checkArgument(product != null, "ProductModel cannot be null");
		Preconditions.checkArgument(classAttributeAssignment != null, "ClassAttributeAssignmentModel cannot be null");
		Preconditions.checkArgument(value != null, "value cannot be null");

		final ProductFeatureModel feature = modelService.create(ProductFeatureModel.class);
		feature.setProduct(product);
		feature.setClassificationAttributeAssignment(classAttributeAssignment);
		feature.setQualifier(getFeatureQualifier(classAttributeAssignment));
		feature.setValuePosition(position);
		feature.setUnit(classAttributeAssignment.getUnit());
		if (classAttributeAssignment.getAttributeType().getCode().equalsIgnoreCase("number"))
		{
			final Double num = Double.valueOf(value.toString());
			feature.setValue(num);
		}
		else
		{
			feature.setValue(value);
		}
		return feature;
	}

	private String getFeatureQualifier(final ClassAttributeAssignmentModel classAttributeAssignment)
	{
		return ClassificationUtils.createFeatureQualifier(classAttributeAssignment);
	}

	void setValueSafelyOnExistingFeature(final ProductFeatureModel feature, final Object value)
	{
		feature.setValue(value);
	}

	private void addNewFeatureToProductFeatures(final ProductModel product, final ProductFeatureModel newFeature)
	{
		final List<ProductFeatureModel> productFeatures = product.getFeatures() != null ? Lists.newArrayList(product.getFeatures())
				: Lists.newArrayList();
		productFeatures.add(newFeature);
		product.setFeatures(productFeatures);
	}

	private void removeFeatureFromProductFeatures(final ProductModel product, final ProductFeatureModel newFeature)
	{
		final List<ProductFeatureModel> productFeatures = product.getFeatures() != null ? Lists.newArrayList(product.getFeatures())
				: Lists.newArrayList();
		productFeatures.remove(newFeature);
		product.setFeatures(productFeatures);
	}

	public List<MediaFormatModel> retrieveMediaFormat()
	{
		List<Object> resultList = null;
		//ProductUtil.setClassName(MediaFormatModel.class);
		final String flexQuery = Config.getParameter("pim.query.mediaformat");
		LOG.info("The query is : " + flexQuery);
		final List<MediaFormatModel> mediaFormats = new ArrayList();
		if (flexQuery != null)
		{
			resultList = getFlexResult(flexQuery, MediaFormatModel.class);
			if (resultList != null && resultList.get(0) != null)
			{
				for (final Object mediaformat : resultList)
				{
					mediaFormats.add((MediaFormatModel) mediaformat);
				}
				return mediaFormats;
			}
		}
		return mediaFormats;
	}

	public MediaFolderModel retrieveMediaFolder()
	{
		List<Object> resultList = null;
		//ProductUtil.setClassName(MediaFolderModel.class);
		final String flexQuery = Config.getParameter("pim.query.mediafolder");
		LOG.info("The query is : " + flexQuery);
		MediaFolderModel mediaFolder = null;
		if (flexQuery != null)
		{
			resultList = getFlexResult(flexQuery, MediaFolderModel.class);
			if (resultList != null && resultList.get(0) != null)
			{
				mediaFolder = (MediaFolderModel) resultList.get(0);
			}
		}
		return mediaFolder;
	}

	public List<ClassAttributeAssignmentModel> removeAssignmentList(final String classificationCode,
			final ModelService modelService, final String pk)
	{

		final String flexQuery = Config.getParameter("EXISTING_ASSIGNMENT_LIST");
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(flexQuery);
		fQuery.addQueryParameter("code", classificationCode);
		fQuery.addQueryParameter("pk", pk);
		final SearchResult<ClassAttributeAssignmentModel> result = getFlexibleSearchService().search(fQuery);
		if (result != null && result.getCount() > 0)
		{
			return result.getResult();
		}
		return Collections.emptyList();
	}

	/**
	 * @param string
	 *
	 */
	public Object getClassificationAttributeCode(final String name, final String category)
	{
		List<Object> resultClassAttrCode = null;
		String flexQuery = "";
		Object code = null;
		LOG.info("Productutil getClassificationAttributeCode");
		flexQuery = Config.getParameter("QUERY_CLASS_ATTR_CODE");
		if (flexQuery != null)
		{
			flexQuery = MessageFormat.format(flexQuery, name, category.toLowerCase().replace(" ", "").trim() + "%");
			LOG.info("cross-check class-attr query  :-" + flexQuery.toString());
			resultClassAttrCode = getFlexResult(flexQuery, String.class);
			if (resultClassAttrCode != null && resultClassAttrCode.get(0) != null)
			{
				LOG.info("Productutil getClassificationAttributeCode resultclassattrcode");
				code = resultClassAttrCode.get(0);
			}
		}
		return code;
	}

	/**
	 *
	 */
	public ClassificationClassModel checkClassificationExists(final String classificationCode, final String pk)
	{
		List<Object> resultClassAttrCode = null;
		String flexQuery = "";
		Object attributePK = null;
		LOG.info("Productutil check attribute code");
		flexQuery = Config.getParameter("GET_EXISTING_CLASSIFICATION");
		if (flexQuery != null)
		{
			flexQuery = MessageFormat.format(flexQuery, classificationCode, pk);
			LOG.info("cross-check class-attr query  :-" + flexQuery.toString());
			resultClassAttrCode = getFlexResult(flexQuery, ClassificationClassModel.class);
			if (resultClassAttrCode != null && resultClassAttrCode.get(0) != null)
			{
				LOG.info("Productutil getClassificationAttributeCode resultclassattrcode");
				attributePK = resultClassAttrCode.get(0);
			}
		}
		return (ClassificationClassModel) attributePK;
	}

	public ClassAttributeAssignmentModel getAssignmentData(final String attribute, final String classificationClass)
	{
		List<Object> resultClassAttrCode = null;
		String flexQuery = "";
		Object attributePK = null;
		LOG.info("Productutil check attribute code");
		flexQuery = Config.getParameter("GET_ASSIGNMENT_MODEL");
		if (flexQuery != null)
		{
			flexQuery = MessageFormat.format(flexQuery, attribute, classificationClass);
			LOG.info("cross-check class-attr query  :-" + flexQuery.toString());
			resultClassAttrCode = getFlexResult(flexQuery, ClassAttributeAssignmentModel.class);
			if (resultClassAttrCode != null && resultClassAttrCode.get(0) != null)
			{
				LOG.info("Productutil getClassificationAttributeCode resultclassattrcode");
				attributePK = resultClassAttrCode.get(0);
			}
		}
		return (ClassAttributeAssignmentModel) attributePK;
	}

	public ClassificationAttributeModel checkAttributeExists(final String code, final String name, final String pk)
	{
		List<Object> resultClassAttrCode = null;
		String flexQuery = "";
		Object attributePK = null;
		LOG.info("Productutil check attribute code");
		flexQuery = Config.getParameter("GET_ATTRIBUTE_PK");
		if (flexQuery != null)
		{
			flexQuery = MessageFormat.format(flexQuery, code.toLowerCase(), pk);
			LOG.info("cross-check class-attr query  :-" + flexQuery.toString());
			resultClassAttrCode = getFlexResult(flexQuery, ClassificationAttributeModel.class);
			if (resultClassAttrCode != null && resultClassAttrCode.get(0) != null)
			{
				LOG.info("Productutil getClassificationAttributeCode resultclassattrcode");
				attributePK = resultClassAttrCode.get(0);
			}
		}
		return (ClassificationAttributeModel) attributePK;
	}

	/**
	 *
	 */
	public ClassificationSystemVersionModel getSystemVersion(final String query)
	{
		List<Object> resultClassAttrCode = null;
		Object attributePK = null;
		if (query != null)
		{

			resultClassAttrCode = getFlexResult(query, ClassificationSystemVersionModel.class);
			if (resultClassAttrCode != null && resultClassAttrCode.get(0) != null)
			{
				attributePK = resultClassAttrCode.get(0);
			}
		}
		return (ClassificationSystemVersionModel) attributePK;
	}

	/**
	 *
	 */
	public GlobalProductNavigationNodeModel getNavigationNode(final List<CategoryModel> parentCategory, final boolean isUS)
	{
		String flexQuery = null;
		if (BooleanUtils.isTrue(isUS)) {
			flexQuery = Config.getParameter("GET_NAVIGATION_NODE");
		}
		else {
			flexQuery = Config.getParameter("GET_NAVIGATION_NODE_CA");
		}
		final List<CategoryModel> categoryCodes = new ArrayList<>();

		for (final CategoryModel category : parentCategory)
		{
			if (category.getCode().startsWith("SH"))
			{
				categoryCodes.add(category);
			}
		}
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(flexQuery);
		fQuery.addQueryParameter("parentCategory", categoryCodes.get(0));
		final SearchResult<GlobalProductNavigationNodeModel> result = getFlexibleSearchService().search(fQuery);
		if (result != null && result.getCount() > 0)
		{
			return result.getResult().get(0);

		}
		else
		{
			return null;
		}
	}

	public SolrIndexedTypeModel getSolrIndexedType()
	{
		final String query = "SELECT {PK} FROM {SolrIndexedType} WHERE {identifier} = 'siteoneProductType'";

		LOG.info("Checking for Solr Indexed Type: ", query);

		final SearchResult<SolrIndexedTypeModel> result = flexibleSearchService.search(query);

		if (result.getResult() == null || result.getResult().isEmpty())
		{
			return null;
		}

		return result.getResult().get(0);
	}
	public SolrIndexedTypeModel getSolrCAIndexedType()
	{
		final String query = "SELECT {PK} FROM {SolrIndexedType} WHERE {identifier} = 'siteoneCAProductType'";

		LOG.info("Checking for Solr Indexed Type: ", query);

		final SearchResult<SolrIndexedTypeModel> result = flexibleSearchService.search(query);

		if (result.getResult() == null || result.getResult().isEmpty()) {
			return null;
		}

		return result.getResult().get(0);
	}

	public SolrIndexedPropertyModel getSolrIndexedProperty(final String code, final SolrIndexedTypeModel solrIndexedTypeModel)
	{
		final String query = "SELECT {PK} FROM {SolrIndexedProperty} WHERE {name} = '" + code + "' AND {solrIndexedType} = "
				+ solrIndexedTypeModel.getPk();

		LOG.info("Checking for Solr Indexed Property: ", query);

		final SearchResult<SolrIndexedPropertyModel> result = flexibleSearchService.search(query);

		if (result.getResult() == null || result.getResult().isEmpty())
		{
			return null;
		}

		return result.getResult().get(0);
	}

	public SolrIndexedPropertyModel createSolrIndexedProperty(final String code, final String name,
			final SolrIndexedTypeModel solrIndexedTypeModel)
	{
		final ModelService modelService = (ModelService) Registry.getApplicationContext().getBean("modelService");
		final SolrIndexedPropertyModel model = modelService.create(SolrIndexedPropertyModel.class);
		model.setName(code);
		model.setPriority(1181);
		model.setType(SolrPropertiesTypes.STRING);
		model.setDisplayName(name);
		model.setLocalized(true);
		model.setMultiValue(true);
		model.setCategoryField(false);
		model.setUseForAutocomplete(false);
		model.setCurrency(false);
		model.setUseForSpellchecking(false);
		model.setFieldValueProvider("siteOneClassificationCachingPropertyValueProvider");
		model.setFtsQuery(false);
		model.setFtsFuzzyQuery(false);
		model.setFtsWildcardQuery(false);
		model.setFtsPhraseQuery(false);
		model.setFacet(true);
		model.setFacetType(SolrIndexedPropertyFacetType.MULTISELECTOR);
		model.setIncludeInResponse(true);
		model.setVisible(true);
		model.setSolrIndexedType(solrIndexedTypeModel);
		modelService.save(model);
		final List<SolrIndexedPropertyModel> list = Lists.newArrayList(solrIndexedTypeModel.getSolrIndexedProperties());
		list.add(model);
		solrIndexedTypeModel.setSolrIndexedProperties(list);
		modelService.save(solrIndexedTypeModel);

		return model;
	}


	public ClassAttributeAssignmentModel getClassAttributeAssignment(final String classificationAttributeCode, 
			final String classificationClassCode, final String pk)
	{

		final String query = "SELECT {pk} FROM {ClassAttributeAssignment} WHERE {classificationclass} = " +
				"({{SELECT {pk} FROM {classificationclass} WHERE {code} = '" + classificationClassCode + "' AND {catalogversion} = '" + pk + "'}}) " +
				"AND {ClassificationAttribute} = ({{ SELECT {pk} FROM {ClassificationAttribute} WHERE {code} = '" + classificationAttributeCode + "' AND {ClassificationAttribute.systemVersion} = '" + pk + "'}})";

		LOG.info("Checking for ClassAttributeAssignmentModel Type: ", query);

		final SearchResult<ClassAttributeAssignmentModel> result = flexibleSearchService.search(query);

		if(null == result) {
			return null;
		}
		if (result.getResult() == null || result.getResult().isEmpty()) {
			return null;
		}

		return result.getResult().get(0);
	}
	public MediaModel removeMedia(String code, CatalogVersionModel cv) {
		final String query = "SELECT {pk} FROM {Media} WHERE {code}=?code and {catalogversion}=?cv";
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(query);
		fQuery.addQueryParameter("code", code);
		fQuery.addQueryParameter("cv", cv);		
		final SearchResult<MediaModel> result = flexibleSearchService.search(fQuery);
		if(result.getResult() != null && result.getResult().size() ==1) {
			return result.getResult().get(0);
		}
		return null;
	}

}