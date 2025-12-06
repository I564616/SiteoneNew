/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2017 SAP SE
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * Hybris ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with SAP Hybris.
 */
package com.siteone.pcm.exception;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.siteone.pcm.constants.SiteonepcmConstants;
import com.siteone.pcm.enums.BrandNameEnum;
import com.siteone.pcm.util.CommonUtil;

import de.hybris.platform.acceleratorservices.dataimport.batch.converter.ImpexRowFilter;
import de.hybris.platform.acceleratorservices.dataimport.batch.converter.impl.DefaultImpexConverter;
import de.hybris.platform.acceleratorservices.dataimport.batch.converter.impl.NullImpexRowFilter;

import de.hybris.platform.catalog.CatalogVersionService;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.enumeration.EnumerationValueModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.exceptions.SystemException;

import de.hybris.platform.servicelayer.model.ModelService;

import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.session.Session;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.variants.model.VariantProductModel;

public class PcmBaseVariantProductImpexConverter extends DefaultImpexConverter {

	
	private Map<String, String> brandEnumKeyValue;
	private CommonUtil CommonUtil=new CommonUtil();
	private FlexibleSearchService flexibleSearchService;
	private EnumerationService enumerationService;
	private ModelService modelService;
	private UserService userService;
	private ProductService productService;
	private SessionService sessionService;
	private static final Logger logger = Logger.getLogger(PcmBaseVariantProductImpexConverter.class);

	public SessionService getSessionService()
	{
		return sessionService;
	}

	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}
	
	private UserModel getUserModel(final String userForBatch)
	{
		// TODO Auto-generated method stub
		final UserModel userModel = userService.getUserForUID(userForBatch);
		if (userModel != null)
		{
			return userModel;
		}
		else
		{
			return null;
		}

	}
	
	public ProductService getProductService() {
		this.productService = (ProductService) Registry.getApplicationContext().getBean(SiteonepcmConstants.PRODUCTSERVICE);
		return this.productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	private CatalogVersionService catalogVersionService;
	
	public CatalogVersionService getCatalogVersionService() {
		this.catalogVersionService = (CatalogVersionService) Registry.getApplicationContext().getBean(SiteonepcmConstants.CATALOGVERSIONSERVICE);
		return this.catalogVersionService;
	}

	public void setCatalogVersionService(CatalogVersionService catalogVersionService) {
		this.catalogVersionService = catalogVersionService;
	}

	
	public UserService getUserService() {
		userService = (UserService) Registry.getApplicationContext().getBean(SiteonepcmConstants.USERSERVICE);
		return this.userService;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

	public ModelService getModelService() {
		modelService = (ModelService) Registry.getApplicationContext().getBean(SiteonepcmConstants.MODELSERVICE);
		return this.modelService;
	}

	public void setModelService(final ModelService modelService) {
		this.modelService = modelService;
	}

	public FlexibleSearchService getFlexibleSearchService() {
		flexibleSearchService = (FlexibleSearchService) Registry.getApplicationContext()
				.getBean(SiteonepcmConstants.FLEXIBLESEARCHSERVICE);
		return this.flexibleSearchService;
	}

	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService) {
		this.flexibleSearchService = flexibleSearchService;
	}

	protected EnumerationService getEnumerationService() {
		enumerationService = (EnumerationService) Registry.getApplicationContext()
				.getBean(SiteonepcmConstants.ENUMERATIONSERVICE);
		return this.enumerationService;
	}

	public void setEnumerationService(final EnumerationService enumerationService) {
		this.enumerationService = enumerationService;
	}

	private String header;
	private String impexRow;
	private String type;
	private ImpexRowFilter rowFilter = new NullImpexRowFilter();
	private StringBuilder errorbuilder;
	private StringBuilder brandnameerrorbuilder;

	@Override
	public String convert(final Map<Integer, String> row, final Long sequenceId) {
		String result = SiteonepcmConstants.EMPTY_STRING;
		errorbuilder = new StringBuilder();
		brandnameerrorbuilder= new StringBuilder();
		if (!MapUtils.isEmpty(row)) {
			final StringBuilder builder = new StringBuilder();

			int copyIdx = 0;
			int idx = impexRow.indexOf(SiteonepcmConstants.BRACKET_START);
			while (idx > -1) {
				final int endIdx = impexRow.indexOf(SiteonepcmConstants.BRACKET_END, idx);
				processRow(row, sequenceId, builder, copyIdx, idx, endIdx);
				copyIdx = endIdx + 1;
				idx = impexRow.indexOf(SiteonepcmConstants.BRACKET_START, endIdx);
			}

			if (copyIdx < impexRow.length()) {
				builder.append(impexRow.substring(copyIdx));
			}
			result = builder.toString();
		}
		return escapeQuotes(result);
	}

	@Override
	protected void processRow(final Map<Integer, String> row, final Long sequenceId, final StringBuilder builder,
			final int copyIdx, final int idx, final int endIdx) {
		if (endIdx < 0) {
			throw new SystemException("Invalid row syntax [brackets not closed]: " + impexRow);
		}
		builder.append(impexRow.substring(copyIdx, idx));
		if (impexRow.charAt(idx + 1) == SiteonepcmConstants.SEQUENCE_CHAR) {
			builder.append(sequenceId);
		} else {
			processValues(row, builder, idx, endIdx);
		}
	}


	@Override
	protected void processValues(final Map<Integer, String> row, final StringBuilder builder, final int idx,
			final int endIdx) {

		final boolean mandatory = impexRow.charAt(idx + 1) == SiteonepcmConstants.PLUS_CHAR;
		Integer mapIdx = null;
		try {
			mapIdx = Integer.valueOf(impexRow.substring(mandatory ? idx + 2 : idx + 1, endIdx));
		} catch (final NumberFormatException e) {
			throw new SystemException("Invalid row syntax [invalid column number]: " + impexRow, e);
		}
		final String colValue = row.get(mapIdx);
		if (row.size()!=37  || row==null )
		{
			throw new IllegalArgumentException(
					  ("Column/Delimiter Mismatch".toString().trim()));
		}
		else
		{
			if(!CommonUtil.checkAttributeDataType(Integer.toString(mapIdx.intValue()), colValue))
			{
				errorbuilder.append(CommonUtil.checkAttributeDatatype(Integer.toString(mapIdx.intValue()))+"  "+colValue+"  "+ CommonUtil.checkAttributeName(Integer.toString(mapIdx.intValue())));
				errorbuilder.append(" ");
				errorbuilder.append(SiteonepcmConstants.AMPERSAND);
			}
		if (CommonUtil.checkLengthForColvalues(colValue, Integer.toString(mapIdx.intValue()))) {
				errorbuilder.append("Length exceeded:");
				errorbuilder.append(CommonUtil.checkAttributeName(Integer.toString(mapIdx.intValue())));
				errorbuilder.append(" ");
				errorbuilder.append(SiteonepcmConstants.AMPERSAND);
			}
		if (mapIdx.intValue() == 14) {
			setBrandEnumCode(builder,colValue,Integer.toString(mapIdx.intValue()));
		}
		else if(mapIdx.intValue()==0)
		{
			setProductSkuId(builder,colValue,Integer.toString(mapIdx.intValue()),mandatory);
		}
		
		//InventoryUOMID Check starts
				else if(mapIdx.intValue()==33)
				{
					if (mandatory && StringUtils.isBlank(colValue) && !colValue.contains(";")) {
						errorbuilder.append(" Missing value:" );
						errorbuilder.append(CommonUtil.checkAttributeName(Integer.toString(mapIdx.intValue())));
						errorbuilder.append(SiteonepcmConstants.AMPERSAND);
					}
					else
					{
					String uomValue=CommonUtil.getinventoryUOMValue(colValue);
					if(!StringUtils.isBlank(uomValue) || uomValue!=null )
					{
						builder.append(uomValue);
						
					}else {
						errorbuilder.append("'"+colValue+"'"+" provided does not exist:" + CommonUtil.checkAttributeName(Integer.toString(mapIdx.intValue())));
						errorbuilder.append(" ");
						errorbuilder.append(SiteonepcmConstants.AMPERSAND);
					}
				}
				}
				//InventoryUOMID check ends
			// Attribute Length check

		/*else if (CommonUtil.checkLengthForColvalues(colValue, Integer.toString(mapIdx.intValue()))) {
				errorbuilder.append("Length of the column" + mapIdx + " than the allowed length  column Values " + colValue);
				errorbuilder.append(" ");
				errorbuilder.append(SiteonepcmConstants.AMPERSAND);
			}
*/
		else if(mapIdx.intValue() == 2)
		{
			if (mandatory && StringUtils.isBlank(colValue)) {
				errorbuilder.append(" Missing value:" );
				errorbuilder.append(CommonUtil.checkAttributeName(Integer.toString(mapIdx.intValue())));
				errorbuilder.append(" ");
				errorbuilder.append(SiteonepcmConstants.AMPERSAND);
			}
			else  {
				String baseProductCode = row.get(1);
				 ProductModel baseProduct=null;
				 final Session localSession = getSessionService().createNewSession();
				 Map<Integer,String> superCategoriesName=new HashMap<Integer,String>();
				 final UserModel batchUser = getUserModel("admin");
					if (batchUser != null)
					{
						userService.setCurrentUser(batchUser);
					}
					try
					{
						
							try {
								baseProduct =(ProductModel) getProductService().getProductForCode(getCatalogVersionService().getCatalogVersion("siteoneProductCatalog", "Staged"),baseProductCode );
								baseProduct.getSupercategories();
							} catch (Exception e){
								baseProduct = null;
							}
						
					}
					finally
					{
						getSessionService().closeSession(localSession);
					}
				
				 if(baseProduct!=null? baseProduct.getCode().equalsIgnoreCase(baseProductCode):false )
				 {
					 String[] superCategories =null;
					 if( baseProduct.getInternalVariantAttribute()!=null && !StringUtils.isBlank(baseProduct.getInternalVariantAttribute()))
					 {
						
						superCategories = baseProduct.getInternalVariantAttribute().split(",");
						
					 }
					
					int i=1;
					 if(superCategories !=null)
					{
					 for (String SuperCategoriesCode :superCategories){
						 superCategoriesName.put(i, SuperCategoriesCode);
						 i++;
					}
					}
					 logger.info(superCategoriesName);
				
				 
				 String variantValueCode=SiteonepcmConstants.EMPTY_STRING;
				
					 variantValueCode=CommonUtil.validateVariantValueCodePcm(superCategoriesName,colValue);
					 if(variantValueCode==null  || variantValueCode.equalsIgnoreCase(SiteonepcmConstants.NOSUPERCATEGORIES) || variantValueCode.equalsIgnoreCase(SiteonepcmConstants.SUPERCATEGORIESDOESNOTMACTHES) || variantValueCode.startsWith("Auto") )
					 {
						 	errorbuilder.append(variantValueCode);
							errorbuilder.append(" ");
							errorbuilder.append(CommonUtil.checkAttributeName(Integer.toString(mapIdx.intValue())));
							errorbuilder.append(SiteonepcmConstants.AMPERSAND);
					 }
					 else
					 {
						 builder.append(variantValueCode);
					 }
				}
				 else
				 {
					 	errorbuilder.append(SiteonepcmConstants.BASEPRODUCTDOESNOTEXISTS + CommonUtil.checkAttributeName(Integer.toString(mapIdx.intValue())));
						errorbuilder.append(" ");
						errorbuilder.append(SiteonepcmConstants.AMPERSAND);
				 }
				 
		} 
			
		}

		else {
			if (mandatory && StringUtils.isBlank(colValue)) {
				errorbuilder.append(" Missing value:" );
				errorbuilder.append(CommonUtil.checkAttributeName(Integer.toString(mapIdx.intValue())));
				errorbuilder.append(" ");
				errorbuilder.append(SiteonepcmConstants.AMPERSAND);

			} else {
				if (colValue.contains(";")) {
					errorbuilder.append(" Invalid character \";\":" );
					errorbuilder.append(CommonUtil.checkAttributeName(Integer.toString(mapIdx.intValue())));
					errorbuilder.append(" ");
					errorbuilder.append(SiteonepcmConstants.AMPERSAND);
				} else {
					builder.append(colValue);
				}
			}

		}
		
		  if (mapIdx.intValue() == 33 && (errorbuilder.length() != 0 ||
		  !errorbuilder.toString().equals("")) ||
		  (brandnameerrorbuilder.length() != 0 ||
		  !brandnameerrorbuilder.toString().equals(""))) {
		  
		  throw new IllegalArgumentException(
		  (brandnameerrorbuilder.append(errorbuilder.deleteCharAt(errorbuilder.
		  length() - 1)).toString().trim())); }
		 

	}
}

	
	

	@Override
	protected String escapeQuotes(final String input) {
		final String[] splitedInput = StringUtils.splitPreserveAllTokens(input, SiteonepcmConstants.SEMICOLON_CHAR);
		final List<String> tmp = new ArrayList<String>();
		for (final String string : splitedInput) {
			if (doesNotContainNewLine(string)) {
				tmp.add(StringEscapeUtils.escapeCsv(string));
			} else {
				tmp.add(string);
			}
		}
		return StringUtils.join(tmp, SiteonepcmConstants.SEMICOLON_CHAR);
	}

	@Override
	protected boolean doesNotContainNewLine(final String string) {
		return !StringUtils.contains(string, CharUtils.LF);
	}

	/**
	 * @see de.hybris.platform.acceleratorservices.dataimport.batch.converter.ImpexConverter#filter(java.util.Map)
	 */
	@Override
	public boolean filter(final Map<Integer, String> row) {
		return rowFilter.filter(row);
	}

	/**
	 * @see de.hybris.platform.acceleratorservices.dataimport.batch.converter.ImpexConverter#getHeader()
	 */
	@Override
	public String getHeader() {
		return header;
	}

	/**
	 * @param header
	 *            the header to set
	 */
	@Override
	public void setHeader(final String header) {
		Assert.hasText(header, "must have text; it must not be null, empty, or blank");
		this.header = header;
	}

	/**
	 * @param impexRow
	 *            the impexRow to set
	 */
	@Override
	public void setImpexRow(final String impexRow) {
		Assert.hasText(impexRow, "must have text; it must not be null, empty, or blank");
		this.impexRow = impexRow;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	@Override
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	@Override
	public String getType() {
		return type;
	}

	/**
	 * @param rowFilter
	 *            the rowFilter to set
	 */
	@Override
	public void setRowFilter(final ImpexRowFilter rowFilter) {
		Assert.notNull(rowFilter, "must not be null");
		this.rowFilter = rowFilter;
	}

	public Map<String, String> getBrandEnumCode() {
		if (brandEnumKeyValue == null ){
			final List<BrandNameEnum> brandNameEnumValue = getEnumerationService()
					.getEnumerationValues(BrandNameEnum.class);

			brandEnumKeyValue = new LinkedHashMap<String, String>();
			for (final BrandNameEnum temp : brandNameEnumValue) {
				final String brandName = getEnumerationService().getEnumerationName(temp);
				brandEnumKeyValue.put(brandName.toLowerCase(), temp.getCode());
			}

		}
		return brandEnumKeyValue;
	}
	private void setBrandEnumCode(StringBuilder builder, String colValue, String mapIdx) {
		final Map<String, String> brandNameEnumCode = getBrandEnumCode();

		if (!StringUtils.isBlank(colValue)) {
			if (!brandNameEnumCode.containsKey(colValue.toLowerCase())) {
				try
				{
				final EnumerationValueModel model = getModelService().create("BrandNameEnum");
				model.setCode("Coreproductbrandname".concat(colValue.toLowerCase().replaceAll("\\s+", "_")));
				model.setName(colValue);
				modelService.save(model);
				builder.append("Coreproductbrandname".concat(colValue.toLowerCase().replaceAll("\\s+", "_")));
				brandEnumKeyValue.put(colValue, "Coreproductbrandname".concat(colValue.toLowerCase().replaceAll("\\s+", "_")));
				}
				catch (Exception e) {
					String brandCode=CommonUtil.getBrandCode(colValue.trim());
							if(brandCode!=null)
							{
								builder.append(brandCode);
								
							}
							else {
					errorbuilder.append("Brand Code Conflict  ");
					errorbuilder.append(CommonUtil.checkAttributeName(mapIdx));
					errorbuilder.append(" ");
					errorbuilder.append(SiteonepcmConstants.AMPERSAND);
							}
					
				}
			} else {
				if(brandNameEnumCode.get(colValue.toLowerCase())!=null)
				{
					builder.append(brandNameEnumCode.get(colValue.toLowerCase()));
				}
				else
				{
					errorbuilder.append("Brand Code " );
					errorbuilder.append(CommonUtil.checkAttributeName(mapIdx));
					errorbuilder.append(" ");
					errorbuilder.append(SiteonepcmConstants.AMPERSAND);
				}
					
					
			}
			
		} 
}
	private void setProductSkuId(final StringBuilder builder,String productSkuId,String mapIdx,boolean mandatory ) {
		if (mandatory && StringUtils.isBlank(productSkuId) && !productSkuId.contains(";")) {
			errorbuilder.append(" Missing value:" );
			errorbuilder.append(CommonUtil.checkAttributeName(mapIdx));
			errorbuilder.append(SiteonepcmConstants.AMPERSAND);
		}
		else
		{
		String productModelType = null;
		ProductModel productType = null;
		VariantProductModel variantProduct = null;
		final Session localSession = getSessionService().createNewSession();
		final UserModel batchUser = getUserModel("admin");
		if (batchUser != null) {
			userService.setCurrentUser(batchUser);
		}
		try {

			try {
				variantProduct = (VariantProductModel) getProductService().getProductForCode(
						getCatalogVersionService().getCatalogVersion("siteoneProductCatalog", "Staged"),
						productSkuId);
				variantProduct.getBaseProduct();
				productModelType = "VariantProductModel";
			} catch (Exception e) {
			}
			if (productModelType == null) {
				try {
					productType = (ProductModel) getProductService()
							.getProductForCode(getCatalogVersionService()
									.getCatalogVersion("siteoneProductCatalog", "Staged"), productSkuId);
					if (productType.getVariantType() != null) {
						productModelType = "BaseProduct";
					} else {
						productModelType = "SimpleProduct";
					}
				} catch (Exception e) {
				}
			}

		} finally {
			getSessionService().closeSession(localSession);
		}

		if (productModelType == null || productModelType.equalsIgnoreCase("VariantProductModel")) {
			builder.append(productSkuId);
		} else {
			errorbuilder.append("'");
			errorbuilder.append(productSkuId);
			errorbuilder.append("  Provided ");
			errorbuilder.append(productModelType);
			errorbuilder.append(" sku id cannot be updated/created as VariantProduct ");
			errorbuilder.append(CommonUtil.checkAttributeName(mapIdx));
			errorbuilder.append(" ");
			errorbuilder.append(SiteonepcmConstants.AMPERSAND);

		}
		}
	}
}
