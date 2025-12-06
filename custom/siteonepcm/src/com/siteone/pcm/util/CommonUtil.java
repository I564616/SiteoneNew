package com.siteone.pcm.util;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import com.siteone.pcm.constants.SiteonepcmConstants;
import de.hybris.platform.core.Registry;
import de.hybris.platform.impex.jalo.Importer;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.search.exceptions.FlexibleSearchException;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.CSVReader;
import de.hybris.platform.util.Config;
import de.hybris.platform.util.CSVWriter;

public class CommonUtil {
	private FlexibleSearchService flexibleSearchService;
	private UserService userService;
	private static Map<String, String> attributeLength;
	private static Map<String, String> attributeName;
	private static List<String> attributeBoolean = getAttributeBoolean();
	private static Map<String, String> attributeDecimal = getAttributeDecimal();
	private static List<String> attributeInteger = getAttributeInteger();
	private static List<String> attributeBooleanValues = getAttributeBooleanValues();
	private static Map<String, String> attributeDateType = getAttributeDataType();

	public static final Logger logger = Logger.getLogger(CommonUtil.class.getName());

	/*
	 * public static String getQuery(String queryName) { final Properties prop = new
	 * Properties(); String propValue = null; final String propFile = Config
	 * .getParameter(SiteonepcmConstants.QUERYFINDER); final InputStream
	 * propFileValue = CommonUtil.class.getResourceAsStream(propFile); try {
	 * prop.load(propFileValue); propValue = prop.getProperty(queryName); }
	 * 
	 * catch(IOException e) { logger.info("Error in load GetQuery method");
	 * logger.info("propValue get query message"+propValue); e.printStackTrace(); }
	 * 
	 * return propValue; }
	 */
	public String getPrimaryHierarchyCode(String colValue) {

		String searchResult = SiteonepcmConstants.EMPTY_STRING;
		if (!colValue.isEmpty()) {
			searchResult = retrieveCode(colValue);
			
		}
		return searchResult;
	}

	public String getSalesHierarchyCode(String colValue) {

		String searchResult = SiteonepcmConstants.EMPTY_STRING;
		if (!colValue.isEmpty()) {
			searchResult = retrieveSHCode(colValue);
			// System.out.println(searchResult);
		}
		return searchResult;
	}

	public String getBrandCode(String colValue) {

		String searchResult = SiteonepcmConstants.EMPTY_STRING;
		if (!colValue.isEmpty()) {
			searchResult = retrieveBrandCode(colValue);
			// System.out.println(searchResult);
		}
		return searchResult;
	}

	private String retrieveBrandCode(String colValue) {
		List<String> resultBrandCode = null;
		String flexQuery = SiteonepcmConstants.EMPTY_STRING;
		String brandCode = null;

		flexQuery = Config.getParameter(SiteonepcmConstants.QUERY_FOR_BRANDCODE);
		if (flexQuery != null) {
			if (colValue.contains("'")) {
				colValue = colValue.replaceAll("'", "''");
			}
			flexQuery = MessageFormat.format(flexQuery, colValue);
			logger.info("cross-check brand query  :-" + flexQuery.toString());
			resultBrandCode = getFlexResult(flexQuery);
			if (resultBrandCode != null && resultBrandCode.get(0) != null) {
				brandCode = resultBrandCode.get(0);
			}
		}
		return brandCode;

	}

	private String retrieveSHCode(String colValue) {
		List<String> resultSHCode = null;
		String flexQuery = SiteonepcmConstants.EMPTY_STRING;
		String SHCode = null;

		flexQuery = Config.getParameter(SiteonepcmConstants.QUERY_FOR_SHCODE);
		if (flexQuery != null) {
			flexQuery = MessageFormat.format(flexQuery, colValue);
			resultSHCode = getFlexResult(flexQuery);
			if (resultSHCode != null && resultSHCode.get(0) != null) {
				SHCode = resultSHCode.get(0);
			}
		}
		return SHCode;

	}

	// Inventoryuomid Check starts
	public String getinventoryUOMValue(String colValue) {
		String searchUOMValue = SiteonepcmConstants.EMPTY_STRING;
		if (!colValue.isEmpty()) {
			searchUOMValue = retrieveUOMValue(colValue);
			// System.out.println(searchUOMValue);
		}
		return searchUOMValue;
	}

	private String retrieveUOMValue(String colValue) {

		List<String> resultUOMList = null;
		String flexQuery = SiteonepcmConstants.EMPTY_STRING;
		String UOMValue = null;
		flexQuery = Config.getParameter(SiteonepcmConstants.QUERY_FOR_INVENTORYUOM);
		if (flexQuery != null) {
			flexQuery = MessageFormat.format(flexQuery, colValue);
			resultUOMList = getFlexResult(flexQuery);
			if (resultUOMList != null && resultUOMList.get(0) != null) {
				UOMValue = resultUOMList.get(0);
			}
		}
		return UOMValue;
	}

	// Inventoryuomid check ends
	private String retrieveCode(String colValue) {

		String phCode = "PH1";
		String flexQuery = SiteonepcmConstants.EMPTY_STRING;
		List<String> resultList = null;
		String[] splitNames = colValue.split(SiteonepcmConstants.GREATER_THAN);

		// L1>L2>L3>L4 code fetch
		for (String phName : splitNames) {
			flexQuery = Config.getParameter(SiteonepcmConstants.QUERY_CATEGORY_PATH);
			if (flexQuery != null) {
				flexQuery = MessageFormat.format(flexQuery, phCode + SiteonepcmConstants.PERCENTAGE, phName.trim());
				resultList = getFlexResult(flexQuery);
				if (resultList != null && resultList.get(0) != null) {
					phCode = resultList.get(0);
				} else {
					return "";
				}
			}
		}
		return phCode;
	}

	public List<String> getFlexResult(final String query) {

		List<String> resultList = null;
		try {
			final FlexibleSearchQuery flexQuery = new FlexibleSearchQuery(query);
			flexQuery.setUser(getUserService().getAdminUser());
			flexQuery.setResultClassList(Arrays.asList(String.class));
			final SearchResult<String> searchResult = getFlexibleSearchService().search(flexQuery);
			if (null != searchResult && searchResult.getCount() != 0) {
				resultList = searchResult.getResult();
			}
		} catch (final FlexibleSearchException e) {
			logger.info(e.getMessage());
		}

		return resultList;
	}

	public String retrieveVariantCode(String colValue, String attribueCode) {
		List<String> resultList = null;
		String flexQuery = SiteonepcmConstants.EMPTY_STRING;
		String VariantCode = null;
		flexQuery = Config.getParameter(SiteonepcmConstants.QUERY_VARIANT_ATTRIBUTE_CODE);
		if (flexQuery != null) {
			flexQuery = MessageFormat.format(flexQuery, colValue.trim(), attribueCode + SiteonepcmConstants.PERCENTAGE);
			resultList = getFlexResult(flexQuery);
			if (resultList != null && resultList.get(0) != null) {
				VariantCode = resultList.get(0);
			}
		}
		return VariantCode;
	}

	public String retrieveValueCode(String attributeCode, String attributeValue) {
		List<String> resultList = null;
		String flexQuery = SiteonepcmConstants.EMPTY_STRING;
		String VariantCode = null;
		flexQuery = Config.getParameter(SiteonepcmConstants.QUERY_VARIANT_VALUES_CODE);
		if (flexQuery != null) {
			if (attributeValue.contains("'")) {
				attributeValue = attributeValue.replaceAll("'", "''");
			}
			flexQuery = MessageFormat.format(flexQuery, attributeCode + SiteonepcmConstants.PERCENTAGE,
					attributeValue.trim());
			resultList = getFlexResult(flexQuery);
			if (resultList != null && resultList.get(0) != null) {
				VariantCode = resultList.get(0);
			}
		}
		return VariantCode;
	}

	public UserService getUserService() {
		userService = (UserService) Registry.getApplicationContext().getBean(SiteonepcmConstants.USERSERVICE);
		return this.userService;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

	public FlexibleSearchService getFlexibleSearchService() {
		flexibleSearchService = (FlexibleSearchService) Registry.getApplicationContext()
				.getBean(SiteonepcmConstants.FLEXIBLESEARCHSERVICE);
		return this.flexibleSearchService;
	}

	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService) {
		this.flexibleSearchService = flexibleSearchService;
	}

	public String validateProductType(String variantAttribute, String productType) {
		String productValidateMsg = SiteonepcmConstants.EMPTY_STRING;
		// variant Attribute null && product Type is null
		if (StringUtils.isBlank(variantAttribute) && StringUtils.isBlank(productType)) {
			productValidateMsg = SiteonepcmConstants.SIMPLEPRODUCT;
		}
		// variant Attribute null && product Type is a GenericVariantProduct
		else if (StringUtils.isBlank(variantAttribute) && !StringUtils.isBlank(productType)
				&& !productType.equalsIgnoreCase(SiteonepcmConstants.GENERICVARIANTPRODUCT)) {
			productValidateMsg = "Invalid Product :--Base Product without Variant Attribute";
		}
		// variant Attribute is not null && product Type is not a GenericVariantProduct
		else if (!StringUtils.isBlank(variantAttribute) && !StringUtils.isBlank(productType)
				&& !productType.equalsIgnoreCase(SiteonepcmConstants.GENERICVARIANTPRODUCT)) {
			productValidateMsg = "Invalid Product :--Not a Valid Variant Type ";
		}
		// variant Attribute is not null && product Type is null
		else if (!StringUtils.isBlank(variantAttribute) && StringUtils.isBlank(productType)) {
			productValidateMsg = "Invalid Product :--Simple Product with Variant Attribute";
		}
		// variant Attribute is not null && product Type is GenericVariantProduct
		else if (!StringUtils.isBlank(variantAttribute) && !StringUtils.isBlank(productType)
				&& productType.equalsIgnoreCase(SiteonepcmConstants.GENERICVARIANTPRODUCT)) {
			productValidateMsg = SiteonepcmConstants.GENERICVARIANTPRODUCT;
		} else if (StringUtils.isBlank(variantAttribute) && !StringUtils.isBlank(productType)
				&& productType.equalsIgnoreCase(SiteonepcmConstants.GENERICVARIANTPRODUCT)) {
			productValidateMsg = "Invalid Product :--Base Product Without Variant Attribute but ProductType GenericVariantProduct ";
		}

		return productValidateMsg;

	}

	public String validateVariantAttributeCodePcm(String variantAttribute, String classPath) {
		String catogriesName = classPath.substring(0, classPath.indexOf('>')).toLowerCase().replaceAll("\\s+", "");
		String catogriesAttributeCode = Config.getParameter(catogriesName);
		String variantAttributeCodeMsg = SiteonepcmConstants.EMPTY_STRING;
		if (!variantAttribute.contains(Config.getParameter(SiteonepcmConstants.DELIMITER))) {
			variantAttributeCodeMsg = retrieveVariantCode(variantAttribute.trim(), catogriesAttributeCode);
			if (variantAttributeCodeMsg == null) {
				variantAttributeCodeMsg = SiteonepcmConstants.INVALIDVARIANTATTRIBUTE;
			}
		} else {
			String[] variantAttributeArray = variantAttribute.split(Config.getParameter(SiteonepcmConstants.DELIMITER));
			StringBuilder variantCode = new StringBuilder("");
			for (String attribute : variantAttributeArray) {
				variantAttributeCodeMsg = retrieveVariantCode(attribute.trim(), catogriesAttributeCode);
				if (variantAttributeCodeMsg == null) {
					return SiteonepcmConstants.INVALIDVARIANTATTRIBUTE;
				} else {
					if (StringUtils.isBlank(variantCode.toString()) || variantCode.toString().isEmpty()) {
						variantCode.append(variantAttributeCodeMsg);
					} else {
						variantCode.append(SiteonepcmConstants.COMMA);
						variantCode.append(variantAttributeCodeMsg);
					}

				}
			}
			variantAttributeCodeMsg = variantCode.toString();
		}
		return variantAttributeCodeMsg;

	}

	public String validateVariantValueCodePcm(Map<Integer, String> superCategoriesName, String variantAttributeValues) {

		String variantvalueCodeMsg = SiteonepcmConstants.EMPTY_STRING;
		int numberOfAttribute = numberofSuperCatorgiesValue(variantAttributeValues);
		if (superCategoriesName.size() == 0) {
			return SiteonepcmConstants.NOSUPERCATEGORIES;
		}
		boolean validSuperCatogries = validateSuperCategories(superCategoriesName.size(), numberOfAttribute);
		if (validSuperCatogries) {
			if (numberOfAttribute == 1) {
				variantvalueCodeMsg = retrieveValueCode(superCategoriesName.get(1), variantAttributeValues);
				if (variantvalueCodeMsg == null) {
					variantvalueCodeMsg = autoCreationValues(superCategoriesName.get(1), variantAttributeValues.trim());
				}
				return variantvalueCodeMsg;
			} else {
				String[] variantAttributeArray = variantAttributeValues
						.split(Config.getParameter(SiteonepcmConstants.DELIMITER));
				StringBuilder variantCode = new StringBuilder("");
				int i = 1;
				for (String attribute : variantAttributeArray) {
					variantvalueCodeMsg = retrieveValueCode(superCategoriesName.get(i), attribute);
					if (variantvalueCodeMsg == null) {
						variantvalueCodeMsg = autoCreationValues(superCategoriesName.get(i), attribute.trim());
						if (variantvalueCodeMsg.startsWith("Auto")) {
							return variantvalueCodeMsg;
						}

					}
					if (StringUtils.isBlank(variantCode.toString()) || variantCode.toString().isEmpty()) {
						variantCode.append(variantvalueCodeMsg);
					} else {
						variantCode.append(SiteonepcmConstants.COMMA);
						variantCode.append(variantvalueCodeMsg);
					}
					i = i + 1;

				}

				variantvalueCodeMsg = variantCode.toString();
			}
		} else {
			return SiteonepcmConstants.SUPERCATEGORIESDOESNOTMACTHES;
		}
		return variantvalueCodeMsg;
	}

	public boolean validateSuperCategories(int superCatorgies, int Attribute) {
		if (superCatorgies == Attribute) {
			return true;
		} else {
			return false;
		}
	}

	public int numberofSuperCatorgiesValue(String AttributeValues) {
		if (AttributeValues.contains(Config.getParameter(SiteonepcmConstants.DELIMITER))) {
			return AttributeValues.split(Config.getParameter(SiteonepcmConstants.DELIMITER)).length;
		} else {
			return 1;
		}
	}

	private String getFormattedLovCode(final String lovName, final String lovCategoriesCode) {

		final StringBuilder lovCode = new StringBuilder(lovCategoriesCode);
		final Map<Character, String> formatMap = new HashMap<Character, String>();

		try {

			// Splitting the pattern based on 'SEP'
			final String patternFormatters = Config.getParameter(SiteonepcmConstants.PATTERNFORMATTERS);
			final int counter = patternFormatters.split(SiteonepcmConstants.PATTERN_SEPARATOR).length;
			final String[] splitPatternn = patternFormatters.split(SiteonepcmConstants.PATTERN_SEPARATOR);

			// Splitting the Array Patterns and Mapping to map based on 'DEM'

			for (int i = 0; i < counter; i++) {
				final String[] strings = splitPatternn[i].split(SiteonepcmConstants.MAPPING_SEPARATOR);
				if (strings.length == 2) {
					formatMap.put(strings[0].charAt(0), strings[1]);
				}
			}

			// Iterating the pattern Map for replacing the special characters to
			// a definition
			final Iterator<Map.Entry<Character, String>> mapIterator = formatMap.entrySet().iterator();
			while (mapIterator.hasNext()) {
				final Map.Entry<Character, String> entry = mapIterator.next();
				replacement[entry.getKey()] = entry.getValue();
			}
			for (int i = 0; i < lovName.length(); i++) {
				lovCode.append(replacement[lovName.charAt(i)]);
			}
		} catch (final Exception e) {
			return "Please Check the Property File" + e.getMessage();
		}

		String finalLovCode = lovCode.toString();
		finalLovCode = finalLovCode.replaceAll("[^a-zA-Z0-9_]", "");
		return StringUtils.deleteWhitespace(finalLovCode);
	}

	final static String[] replacement = new String[Character.MAX_VALUE + 1];
	static {

		for (int i = Character.MIN_VALUE; i <= Character.MAX_VALUE; i++) {
			replacement[i] = Character.toString(Character.toLowerCase((char) i));
		}
	}

	private boolean checkSplChar(final String lovName) {
		boolean isSplCharPresent = false;
		final Pattern pattern = Pattern.compile("[ a-zxA-Z0-9$#%=!{},`~&*()/'<>?._|/é®\u2122-]*[^@\\t\\r\\n]*");

		// Pattern.compile(Config.getParameter(SiteonepcmConstants.SPL_CHAR_REGEX));
		final Matcher matcher = pattern.matcher(lovName);
		final boolean matchStatus = matcher.matches();
		if (!matchStatus) {
			isSplCharPresent = true;
		}
		return isSplCharPresent;

	}

	public String autoCreationValues(String superCategories, String lovValues) {
		String variantLovCode = SiteonepcmConstants.EMPTY_STRING;
		int sequence = retrieveMaxSequence(superCategories);
		boolean isSplCharPresent;
		if (variantLovCode.isEmpty()) {
			isSplCharPresent = checkSplChar(lovValues);
			if (!isSplCharPresent) {
				variantLovCode = getFormattedLovCode(lovValues, superCategories);
			} else {
				return "Auto generation Code cannot be Resloved for the given Value due to special character ";
			}
			boolean isCodePresent = checkForCodeExit(superCategories, variantLovCode);
			if (!isCodePresent) {
				/*
				 * String
				 * impexFileName=Config.getParameter(SiteonepcmConstants.VARIANTVALUEFILEPATH)+(
				 * new Timestamp(System.currentTimeMillis())).getTime();; final File impexFile =
				 * new File(impexFileName);
				 */
				File impexFile = new File(Config.getParameter(SiteonepcmConstants.VARIANTVALUEFILEPATH));
				if (!impexFile.isDirectory()) {
					impexFile.mkdir();
				}
				String impexFileName = Config.getParameter(SiteonepcmConstants.VARIANTVALUEFILEPATH)
						+ "AutoVariantAttributeCreation_" + (new Timestamp(System.currentTimeMillis())).getTime()
						+ ".csv";
				impexFile = new File(impexFileName);

				CSVWriter impexFileWriter = null;
				try {
					impexFileWriter = new CSVWriter(impexFile, "UTF-8", true);
				} catch (UnsupportedEncodingException | FileNotFoundException e1) {
					// TODO Auto-generated catch block
					logger.info(e1.getMessage());
				}
				try {
					if (impexFileWriter != null) {
						impexFileWriter.writeSrcLine(Config.getParameter(SiteonepcmConstants.VARIANTVALUEHEADER1));
						impexFileWriter.writeSrcLine(Config.getParameter(SiteonepcmConstants.VARIANTVALUEHEADER2));
						impexFileWriter.writeSrcLine(Config.getParameter(SiteonepcmConstants.VARIANTVALUEHEADER3));
						impexFileWriter.writeSrcLine(Config.getParameter(SiteonepcmConstants.VARIANTVALUEHEADER4));
						impexFileWriter.writeSrcLine(Config.getParameter(SiteonepcmConstants.VARIANTVALUEHEADER5));
						impexFileWriter.writeSrcLine(Config.getParameter(SiteonepcmConstants.VARIANTVALUEHEADER6));
						impexFileWriter.writeSrcLine(Config.getParameter(SiteonepcmConstants.VARIANTVALUEHEADER7));
						impexFileWriter.writeSrcLine(
								";" + variantLovCode + ";" + lovValues + ";" + superCategories + ";" + sequence + ";;");
						impexFileWriter.close();
					}
				} catch (IOException e) {
					return "Auto generation code due some technical error" + lovValues;
				}
				CSVReader importScriptReader = null;
				try {
					importScriptReader = new CSVReader(impexFile, "UTF-8");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return "Auto generation code due some technical error" + lovValues;
				}
				final Importer importer = new Importer(importScriptReader);
				try {
					importer.importAll();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return "Auto generation of code due to impex failure error" + lovValues;
				}
			} else {
				return "Auto Variant Value/clashed   :-" + lovValues;
			}
		}
		return variantLovCode;

	}

	public int retrieveMaxSequence(String superCategories) {
		List<String> resultList = null;
		int maxSequence = 0;
		String flexQuery = SiteonepcmConstants.EMPTY_STRING;
		flexQuery = Config.getParameter(SiteonepcmConstants.QUERY_FOR_MAXSEQUENCE);
		if (flexQuery != null) {

			flexQuery = MessageFormat.format(flexQuery, superCategories.trim() + SiteonepcmConstants.PERCENTAGE);
			resultList = getFlexResult(flexQuery);
			if (resultList != null && resultList.get(0) != null) {
				maxSequence = Integer.parseInt(resultList.get(0));
			}
		}
		maxSequence = maxSequence + 1;
		return maxSequence;
	}

	public boolean checkForCodeExit(String superCategories, String valueCode) {
		List<String> resultList = null;
		boolean isCodePresent = false;
		String flexQuery = SiteonepcmConstants.EMPTY_STRING;
		flexQuery = Config.getParameter(SiteonepcmConstants.QUERY_FOR_CODECHECKING);
		if (flexQuery != null) {

			if (valueCode.contains("'")) {
				valueCode = valueCode.replaceAll("'", "''");
			}
			flexQuery = MessageFormat.format(flexQuery, valueCode,
					superCategories.trim() + SiteonepcmConstants.PERCENTAGE);
			resultList = getFlexResult(flexQuery);
			if (resultList != null && resultList.get(0) != null) {
				isCodePresent = true;
			}
		}
		return isCodePresent;
	}

	private Map<String, String> getAttributeLength() {
		Map<String, String> attributeLength = new HashMap<>();
		String[] splitAttribute = Config.getParameter("StringlengthValidation").split(",");
		for (String attribute : splitAttribute) {
			String[] attLength = attribute.split(":");
			attributeLength.put(attLength[0], attLength[1]);
		}
		return attributeLength;

	}

	public boolean checkLengthForColvalues(String colvalue, String index) {
		if (attributeLength == null) {
			attributeLength = getAttributeLength();
		}
		if (attributeLength.containsKey(index)) {
			int maxLegth = Integer.parseInt(attributeLength.get(index));
			if (colvalue.length() > maxLegth) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private Map<String, String> getAttributeName() {
		Map<String, String> attributeName = new HashMap<>();
		String[] splitAttribute = Config.getParameter("AttributeNameList").split(",");
		for (String attribute : splitAttribute) {
			String[] attName = attribute.split(":");
			attributeName.put(attName[0], attName[1]);
		}
		return attributeName;
	}

	public static List<String> getAttributeBoolean() {
		List<String> attributeBoolean = new ArrayList<>();
		String[] splitAttribute = Config.getParameter("booleanAttribute").split(",");
		for (String attribute : splitAttribute) {
			attributeBoolean.add(attribute);
		}
		return attributeBoolean;
	}

	public static Map<String, String> getAttributeDecimal() {
		Map<String, String> attributeDeciaml = new HashMap<>();
		String[] splitAttribute = Config.getParameter("decimalAttribute").split(",");
		for (String attribute : splitAttribute) {
			String[] attName = attribute.split(":");
			attributeDeciaml.put(attName[0], attName[1]);
		}
		return attributeDeciaml;
	}

	public static List<String> getAttributeBooleanValues() {
		List<String> attributeBooleanValues = new ArrayList<>();
		attributeBooleanValues.add("true");
		attributeBooleanValues.add("false");
		attributeBooleanValues.add("0");
		attributeBooleanValues.add("1");
		return attributeBooleanValues;

	}

	public static List<String> getAttributeInteger() {
		List<String> attributeInteger = new ArrayList<>();
		String[] splitAttribute = Config.getParameter("integerNumberAttributes").split(",");
		for (String attribute : splitAttribute) {
			attributeInteger.add(attribute);
		}
		return attributeInteger;

	}

	public static boolean isNumberWithprecision(String string, String precision) {
		if (string.contains(".")) {
			return string.matches("^\\d{0," + precision + "}\\.\\d{0,15}$");
		} else {
			return string.matches("^\\d{0," + precision + "}");
		}
	}

	public boolean checkAttributeDataType(String index, String colvalue) {
		if (!StringUtils.isBlank(colvalue)) {
			if (attributeBoolean.contains(index)) {
				if (attributeBooleanValues.contains(colvalue.toLowerCase())) {
					return true;
				} else {
					return false;
				}
			}
			if (attributeInteger.contains(index)) {
				if (!colvalue.contains(".")) {
					return isNumberWithprecision(colvalue, "59");
				} else {
					return false;
				}
			}
			if (attributeDecimal.containsKey(index)) {

				return isNumberWithprecision(colvalue, attributeDecimal.get(index));
			}
		}

		return true;
	}

	public String checkAttributeName(String index) {
		String attname = "";
		if (attributeName == null) {
			attributeName = getAttributeName();
		}
		if (attributeName.containsKey(index)) {
			// int maxLegth = Integer.parseInt(attributeLength.get(index));
			attname = attributeName.get(index);

		}
		return attname;
	}

	private static Map<String, String> getAttributeDataType() {
		Map<String, String> attributeDataType = new HashMap<>();
		String[] splitAttribute = Config.getParameter("attributeDataType").split(",");
		for (String attribute : splitAttribute) {
			String[] attName = attribute.split(":");
			attributeDataType.put(attName[0], attName[1]);
		}
		return attributeDataType;
	}

	public String checkAttributeDatatype(String index) {
		String attname = "";
		if (attributeDateType.containsKey(index)) {
			// int maxLegth = Integer.parseInt(attributeLength.get(index));
			attname = attributeDateType.get(index);
		}
		return attname;
	}
}
