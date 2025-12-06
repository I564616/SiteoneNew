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
package com.siteone.pcm.constants;

/**
 * Global class for all Siteonepcm constants. You can add global constants for your extension into this class.
 */
public final class SiteonepcmConstants extends GeneratedSiteonepcmConstants
{
	public static final String EXTENSIONNAME = "siteonepcm";
	public static final String PRODUCTSERVICE = "productService";
	public static final String MEDIASERVICE = "mediaService";
	public static final String CATALOGSERVICE = "catalogService";
	public static final String CATEGORYSERVICE = "categoryService";
	public static final String CATALOGVERSIONSERVICE="catalogVersionService";
	public static final String FLEXIBLESEARCHSERVICE = "flexibleSearchService";
	public static final String MODELSERVICE = "modelService";
	
	public static final String DOT = ".";
	public static final String HYPEN = "-";
	public static final String SEMICOLON = ";";
	public static final String SINGLEPIPE = "|";
	public static final String PFSINGLEPIPE = "\\|";

	private SiteonepcmConstants()
	{
		//empty to avoid instantiating this constant class
	}

	// implement here constants used by this extension

    public static final String PLATFORM_LOGO_CODE = "siteonepcmPlatformLogo";
	public static final String ENUMERATIONSERVICE = "enumerationService";
	public static final String USERSERVICE = "userService";
	public static final String VALIDRECORDS = "ValidRecords";
	public static final String HEADERLIST = "HeaderList";
	public static final String SKUID="#skuId";
	public static final String DOLLAR="$";
	public static final String SYMBOL="=@";
	public static final String CLATTRMODIFIERS="[$clAttrModifiers];\n";
	public static final String CATALOGVERSION="$catalogVersion";
	public static final char SEMICOLON_CHAR = ';';
	public static final char PLUS_CHAR = '+';
	public static final char SEQUENCE_CHAR = 'S';
	public static final String EMPTY_STRING = "";
	public static final char BRACKET_END = '}';
	public static final char BRACKET_START = '{';
	public static final char AMPERSAND = '&';
	public static final String GREATER_THAN = ">";
	public static final String COMMA = ",";
	public static final String QUERYFINDER ="siteOne.queryFinder";
	public static final String QUERY_CATEGORY_PATH = "QUERY_CATEGORY_PATH";
	public static final String QUERY_VARIANT_ATTRIBUTE_CODE = "QUERY_VARIANT_ATTRIBUTE_CODE";
	public static final String QUERY_FOR_SHCODE = "QUERY_FOR_SHCODE";
	public static final String QUERY_FOR_BRANDCODE = "QUERY_FOR_BRANDCODE"; 
	public static final String QUERY_FOR_INVENTORYUOM="QUERY_FOR_INVENTORYUOM";
	public static final String QUERY_VARIANT_VALUES_CODE = "QUERY_VARIANT_VALUES_CODE";
	public static final String QUERY_PRODUCT_MODEL= "QUERY_FOR_PRODUCTMODEL";
	public static final String QUERY_FOR_MAXSEQUENCE="QUERY_FOR_MAXSEQUENCE";
	public static final String QUERY_FOR_CODECHECKING="QUERY_FOR_CODECHECKING";
	public static final String PERCENTAGE = "%";
	//Base product and variant product validaiton
	public static final String GENERICVARIANTPRODUCT = "GenericVariantProduct";
	public static final String SIMPLEPRODUCT = "SimpleProduct";
	public static final String INVALIDVARIANTATTRIBUTE = "Invalid Product :--Variant attribute does not exist:-  ";
	public static final String NOSUPERCATEGORIES = "Invalid Base Product ID";
	public static final String BASEPRODUCTDOESNOTEXISTS = "Invalid Product :--No Base Product Found:-  ";
	public static final String  SUPERCATEGORIESDOESNOTMACTHES= "Invalid Product :--Variant Attribute/Value Mismatch:-  ";
	public static final String DELIMITER = "variantValuesCode.delimiter";
	public static final String VARIANTVALUEHEADER1 = "variantvalueheader.header1";
	public static final String VARIANTVALUEHEADER2 = "variantvalueheader.header2";
	public static final String VARIANTVALUEHEADER3 = "variantvalueheader.header3";
	public static final String VARIANTVALUEHEADER4 = "variantvalueheader.header4";
	public static final String VARIANTVALUEHEADER5 = "variantvalueheader.header5";
	public static final String VARIANTVALUEHEADER6 = "variantvalueheader.header6";
	public static final String VARIANTVALUEHEADER7 = "variantvalueheader.header7";
	public static final String SPL_CHAR_REGEX = "lov.splcharRegex";
	public static final String PATTERNFORMATTERS = "lov.patternFormatters";
	public static final String PATTERN_SEPARATOR = "SEP";
	public static final String MAPPING_SEPARATOR = "DEM";
	public static final String VARIANTVALUEFILEPATH = "variantvalueautocode.filepath";
	public static final String TRUE = "TRUE";
	public static final String PCM_ERROR_REPORT_LOCATION = "azure.hotfolder.storage.container.name";
	public static final String PCM_PROCESSING_LOCATION = "processing";
}
