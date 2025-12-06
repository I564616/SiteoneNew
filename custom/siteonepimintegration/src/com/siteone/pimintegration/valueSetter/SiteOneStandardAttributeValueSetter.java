/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.siteone.pimintegration.valueSetter;

import de.hybris.platform.b2b.company.B2BCommerceB2BUserGroupService;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.enums.ClassificationAttributeTypeEnum;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.catalog.model.classification.ClassAttributeAssignmentModel;
import de.hybris.platform.catalog.model.classification.ClassificationAttributeModel;
import de.hybris.platform.catalog.model.classification.ClassificationClassModel;
import de.hybris.platform.catalog.model.classification.ClassificationSystemVersionModel;
import de.hybris.platform.category.CategoryService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.model.navigation.CMSNavigationNodeModel;
import de.hybris.platform.cms2.servicelayer.services.CMSPageService;
import de.hybris.platform.commerceservices.category.CommerceCategoryService;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.media.MediaContainerModel;
import de.hybris.platform.core.model.media.MediaFolderModel;
import de.hybris.platform.core.model.media.MediaFormatModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.integrationservices.model.AttributeValueSetter;
import de.hybris.platform.integrationservices.model.TypeAttributeDescriptor;
import de.hybris.platform.media.services.MediaLocationHashService;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.product.impl.DefaultVariantsService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.solrfacetsearch.model.config.SolrIndexedPropertyModel;
import de.hybris.platform.solrfacetsearch.model.config.SolrIndexedTypeModel;
import de.hybris.platform.util.Config;
import de.hybris.platform.variants.model.GenericVariantProductModel;
import de.hybris.platform.variants.model.VariantCategoryModel;
import de.hybris.platform.variants.model.VariantTypeModel;
import de.hybris.platform.variants.model.VariantValueCategoryModel;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.site.BaseSiteService;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.siteone.core.enums.SavingsCenterEnum;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.GlobalProductNavigationNodeModel;
import com.siteone.core.model.InventoryUPCModel;
import com.siteone.pcm.enums.BrandNameEnum;
import com.siteone.pimintegration.model.SiteOneBrochuresModel;
import com.siteone.pimintegration.model.SiteOneCategoryAttributesModel;
import com.siteone.pimintegration.model.SiteOneCategoryLevelModel;
import com.siteone.pimintegration.model.SiteOneCategoryPayloadModel;
import com.siteone.pimintegration.model.SiteOneDataSheetModel;
import com.siteone.pimintegration.model.SiteOneInstallationInstructionsModel;
import com.siteone.pimintegration.model.SiteOneOwnersManualModel;
import com.siteone.pimintegration.model.SiteOnePartsDiagramModel;
import com.siteone.pimintegration.model.SiteOneProductImageModel;
import com.siteone.pimintegration.model.SiteOneProductItemImagesModel;
import com.siteone.pimintegration.model.SiteOneProductLabelSheetModel;
import com.siteone.pimintegration.model.SiteOneProductSafetyDataSheetModel;
import com.siteone.pimintegration.model.SiteOneProductSavingsCenterAttributeModel;
import com.siteone.pimintegration.model.SiteOneProductSpecificationAttributeModel;
import com.siteone.pimintegration.model.SiteOneProductSpecificationSheetModel;
import com.siteone.pimintegration.model.SiteOneProductTransformationModel;
import com.siteone.pimintegration.model.SiteOneTechnicalDrawingModel;
import com.siteone.pimintegration.model.SiteOneWarrantyInformationModel;
import com.siteone.pimintegration.util.ProductUtil;
import com.siteone.integration.constants.SiteoneintegrationConstants;


public class SiteOneStandardAttributeValueSetter implements AttributeValueSetter
{
	private static final Logger LOG = LoggerFactory.getLogger(SiteOneStandardAttributeValueSetter.class);
	private final TypeAttributeDescriptor attribute;
	private final ModelService modelService;
	private static final String OWNERS_MANUAL = "Owner's Manual";
	private static final String INSTALLATION_INSTRUCTION = "Installation Instructions";
	private static final String TECHNICAL_DRAWING = "Technical Drawing";
	private static final String PARTS_DIAGRAM = "Parts Diagram";
	private static final String WARRANTY_INFO = "Warranty Information";
	private static final String BROCHURES = "Brochures";
	private static final String PIECE_UOM_CODE = "PC";
	private static final String FOOT_UOM_CODE = "FT";
	private static final String PIPE_CATEGORY_CODE = "PH1424";
	private static final int ZERO = 0;
	private static final String NEW_CATEGORY = "newCategory";
	private static final String NEW_CATEGORY_CA = "newCategoryCA";
	private static final String CATEGORY_TITLE = " Supplies & Materials for Landscaping";
	private static String retrieveCodes = StringUtils.EMPTY;
	ProductModel convertedModel;


	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}
	

	private final FlexibleSearchService flexibleSearchService;
	private final UserService userService;
	private final CommerceCategoryService commerceCategoryService;
	private final EnumerationService enumerationService;
	private final ProductUtil productUtilObj = new ProductUtil();

	private final ProductService productService = (ProductService) Registry.getApplicationContext().getBean("productService");
	private final B2BCommerceB2BUserGroupService b2bCommerceB2BUserGroupService = (B2BCommerceB2BUserGroupService) Registry
			.getApplicationContext().getBean("b2bCommerceB2BUserGroupService");
	private final CatalogVersionService catalogVersionService = (CatalogVersionService) Registry.getApplicationContext()
			.getBean("catalogVersionService");
	private final DefaultVariantsService variantsService = (DefaultVariantsService) Registry.getApplicationContext()
			.getBean("variantsService");
	private final CategoryService categoryService = (CategoryService) Registry.getApplicationContext().getBean("categoryService");

	private final MediaLocationHashService mediaLocationHashService = (MediaLocationHashService) Registry.getApplicationContext()
			.getBean("mediaLocationHashService");
	private final SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService = (SiteOneFeatureSwitchCacheService) Registry
			.getApplicationContext().getBean("cachingSiteOneFeatureSwitchService");
	private final BaseSiteService baseSiteService = (BaseSiteService) Registry.getApplicationContext().getBean("baseSiteService");
	private MessageSource messageSource = (MessageSource) Registry.getApplicationContext().getBean("messageSource");
	private I18NService i18nService = (I18NService) Registry.getApplicationContext().getBean("i18nService");

	public SiteOneStandardAttributeValueSetter(final TypeAttributeDescriptor attribute, final ModelService service,
			final CommerceCategoryService commerceCategoryService, final UserService userService,
			final FlexibleSearchService flexibleSearchService, final EnumerationService enumerationService)
	{
		Preconditions.checkArgument(attribute != null, "Type attribute descriptor is required and cannot be null");
		Preconditions.checkArgument(service != null, "Model service is required and cannot be null");
		this.attribute = attribute;
		modelService = service;
		this.userService = userService;
		this.commerceCategoryService = commerceCategoryService;
		this.flexibleSearchService = flexibleSearchService;
		this.enumerationService = enumerationService;
	}

	private List<CategoryModel> getCategoryList(final List<CategoryModel> categories, final boolean isUs)
	{
		final List<CategoryModel> categoryUs = new ArrayList();
		final List<CategoryModel> categoryCa = new ArrayList();
		for (final CategoryModel category : categories)
		{
			if (category.getCatalogVersion() == catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online"))
			{
				categoryUs.add(category);
			}
			else
			{
				categoryCa.add(category);
			}
		}
		if (BooleanUtils.isTrue(isUs))
		{
			return categoryUs;
		}
		return categoryCa;

	}

	@Override
	public void setValue(final Object model, final Object value)
	{
		productUtilObj.setFlexibleSearchService(flexibleSearchService);
		productUtilObj.setUserService(userService);


		if (model instanceof SiteOneCategoryPayloadModel)
		{

			if (attribute.getAttributeName().equalsIgnoreCase("categoryLevel"))
			{
				List<CategoryModel> l1Category = null;
				List<CategoryModel> l2Category = null;
				List<CategoryModel> l3Category = null;
				List<CategoryModel> l4Category = null;

				GlobalProductNavigationNodeModel l2Node = null, l3Node = null;
				final GlobalProductNavigationNodeModel l4Node = null;

				GlobalProductNavigationNodeModel l2NodeCa = null, l3NodeCa = null;
				final GlobalProductNavigationNodeModel l4NodeCa = null;

				final List<SiteOneCategoryLevelModel> list = new ArrayList<>();
				list.addAll((Collection<? extends SiteOneCategoryLevelModel>) value);
				SiteOneCategoryLevelModel c2 = null, c3 = null, c4 = null;
				for (final SiteOneCategoryLevelModel category : list)
				{

					if (category.getType().equalsIgnoreCase("level1"))
					{
						l1Category = productUtilObj.categoryExist(category);
					}
					if (category.getType().equalsIgnoreCase("level1") && null == l1Category)
					{
						throw new RuntimeException("L1 Category does not exist in Hybris");
					}

					if (category.getType().equalsIgnoreCase("level2"))
					{
						c2 = category;
						l2Category = productUtilObj.categoryExist(category);
						if (l2Category != null)
						{
							l2Category = l2Category.stream().filter(cat -> cat.getCode().length() == 6).collect(Collectors.toList());
							LOG.info("l2Category length : " + l2Category.size());
						}
					}
					if (category.getType().equalsIgnoreCase("level3"))
					{

						c3 = category;
						l3Category = productUtilObj.categoryExist(category);
						if (l3Category != null)
						{
							l3Category = l3Category.stream().filter(cat -> cat.getCode().length() == 9).collect(Collectors.toList());
							LOG.info("l3Category length : " + l3Category.size());
						}
					}
					if (category.getType().equalsIgnoreCase("level4"))
					{

						c4 = category;
						l4Category = productUtilObj.categoryExist(category);
						if (l4Category != null)
						{
							l4Category = l4Category.stream().filter(cat -> cat.getCode().length() == 12).collect(Collectors.toList());
							LOG.info("l4Category length : " + l4Category.size());
						}
					}

				}


				if (l1Category != null && c2 != null)
				{
					// when Level2 category not available for both US and CA
					if (l2Category == null || l2Category.size() == 0)
					{
						l2Category = createCategory(model, c2, l1Category);
						l2Node = createNavNode(getCategoryList(l1Category, true), getCategoryList(l2Category, true), null, true);
						l2NodeCa = createNavNode(getCategoryList(l1Category, false), getCategoryList(l2Category, false), null, false);
					}
					// when Level2 category already available for US and need to create for CA
					if (l2Category.size() <= 2)
					{
						l2Category.addAll(l2Category.size(), createCategoryOnlyForCA(model, c2, l2Category, l1Category));
						l2NodeCa = createNavNode(getCategoryList(l1Category, false), getCategoryList(l2Category, false), null, false);
					}
				}
				if (l2Category != null && c3 != null)
				{
					// when Level3 category not available for both US and CA
					if (l3Category == null || l3Category.size() == 0)
					{
						l3Category = createCategory(model, c3, l2Category);
						l3Node = createNavNode(getCategoryList(l2Category, true), getCategoryList(l3Category, true), l2Node, true);
						l3NodeCa = createNavNode(getCategoryList(l2Category, false), getCategoryList(l3Category, false), l2NodeCa,
								false);
					}
					// when Level3 category already available for US and need to create for CA
					if (l3Category.size() <= 2)
					{
						l3Category.addAll(l3Category.size(), createCategoryOnlyForCA(model, c3, l3Category, l2Category));
						l3NodeCa = createNavNode(getCategoryList(l2Category, false), getCategoryList(l3Category, false), l2NodeCa,
								false);
					}
				}
				if (l3Category != null && c4 != null)
				{
					// when Level3 category not available for both US and CA
					if (l4Category == null || l4Category.size() == 0)
					{
						l4Category = createCategory(model, c4, l3Category);
					}
					// when Level4 category already available for US and need to create for CA
					if (l4Category.size() <= 2)
					{
						l4Category.addAll(l4Category.size(), createCategoryOnlyForCA(model, c4, l4Category, l3Category));
					}

				}
				switch (list.size())
				{
					case 1:
						modelService.setAttributeValue(model, NEW_CATEGORY, l1Category.get(0));
						modelService.setAttributeValue(model, NEW_CATEGORY_CA,
								l1Category.get(1) != null && l1Category.get(1).getCode().startsWith("PH") ? l1Category.get(1)
										: l1Category.get(2));
						break;
					case 2:
						modelService.setAttributeValue(model, NEW_CATEGORY, l2Category.get(0));
						modelService.setAttributeValue(model, NEW_CATEGORY_CA,
								l2Category.get(1) != null && l2Category.get(1).getCode().startsWith("PH") ? l2Category.get(1)
										: l2Category.get(2));
						break;
					case 3:
						modelService.setAttributeValue(model, NEW_CATEGORY, l3Category.get(0));
						modelService.setAttributeValue(model, NEW_CATEGORY_CA,
								l3Category.get(1) != null && l3Category.get(1).getCode().startsWith("PH") ? l3Category.get(1)
										: l3Category.get(2));
						break;
					case 4:
						modelService.setAttributeValue(model, NEW_CATEGORY, l4Category.get(0));
						modelService.setAttributeValue(model, NEW_CATEGORY_CA,
								l4Category.get(1) != null && l4Category.get(1).getCode().startsWith("PH") ? l4Category.get(1)
										: l4Category.get(2));
						break;
				}

				modelService.setAttributeValue(model, "categoryLevel", new ArrayList<SiteOneCategoryLevelModel>());

			}


			if (attribute.getAttributeName().equalsIgnoreCase("attributeValues"))
			{
				String variantCategoryList = "";
				final List<SiteOneCategoryAttributesModel> list = new ArrayList<>();
				CategoryModel categoryPHUs = null, categoryPHCa = null;
				list.addAll((Collection<? extends SiteOneCategoryAttributesModel>) value);
				ClassificationAttributeModel attributeData = null;
				final ClassificationSystemVersionModel versionModel = productUtilObj
						.getSystemVersion(Config.getParameter("GET_CLASSIFICATION_CLASS"));
				final ClassificationSystemVersionModel versionModelCA = productUtilObj
						.getSystemVersion(Config.getParameter("GET_CLASSIFICATION_CLASS_CA"));


				categoryPHUs = modelService.getAttributeValue(model, NEW_CATEGORY);
				categoryPHCa = modelService.getAttributeValue(model, NEW_CATEGORY_CA);

				if (null != categoryPHUs && null != categoryPHCa)
				{

					final List<CategoryModel> category = new ArrayList();
					category.add(categoryPHUs);
					category.add(categoryPHCa);

					for (final CategoryModel categoryPH : category)
					{
						List<ClassAttributeAssignmentModel> existingAssignment = new ArrayList<>();
						String classificationCode = "";
						ClassificationClassModel classificationModel = null;
						classificationCode = "CH01_".concat(classificationCode.concat(categoryPH.getCode().substring(2)));
						if (categoryPH.getCatalogVersion()
								.equals(catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online")))
						{
							classificationModel = productUtilObj.checkClassificationExists(classificationCode,
									versionModel.getPk().getLongValueAsString());
						}
						else
						{
							classificationModel = productUtilObj.checkClassificationExists(classificationCode,
									versionModelCA.getPk().getLongValueAsString());
						}
						if (null == classificationModel)
						{

							final ClassificationClassModel classificationClassModel = modelService
									.create(ClassificationClassModel.class);
							classificationClassModel.setCode(classificationCode);
							classificationClassModel.setName("Product Details");
							if (categoryPH.getCatalogVersion()
									.equals(catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online")))
							{
								classificationClassModel.setCatalogVersion(versionModel);
							}
							else
							{
								classificationClassModel.setCatalogVersion(versionModelCA);
							}

							classificationClassModel.setCategories(Arrays.asList(categoryPH));
							modelService.save(classificationClassModel);
							classificationModel = classificationClassModel;
						}
						if (categoryPH.getCatalogVersion()
								.equals(catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online")))
						{
							existingAssignment = new ArrayList<>(productUtilObj.removeAssignmentList(classificationCode, modelService,
									versionModel.getPk().getLongValueAsString()));
						}
						else
						{
							existingAssignment = new ArrayList<>(productUtilObj.removeAssignmentList(classificationCode, modelService,
									versionModelCA.getPk().getLongValueAsString()));
						}
						
						final String shortTextsUS = siteOneFeatureSwitchCacheService
								.getValueForSwitch("SolrIndexedPropertyToBeRemovedUS");
						Set<String> propertyNameToBeRemovedUS = new HashSet<String>();
						final String shortTextsCA = siteOneFeatureSwitchCacheService
								.getValueForSwitch("SolrIndexedPropertyToBeRemovedCA");
						Set<String> propertyNameToBeRemovedCA = new HashSet<String>();
						if (shortTextsUS != null)
						{
							propertyNameToBeRemovedUS = Arrays.stream(shortTextsUS.split(",")).map(String::trim)
									.collect(Collectors.toSet());
						}
						else
						{
							LOG.info("SolrIndexedPropertyToBeRemovedUS is not available in SiteOneFeatureSwitch");
						}

						if (shortTextsCA != null)
						{
							propertyNameToBeRemovedCA = Arrays.stream(shortTextsCA.split(",")).map(String::trim)
									.collect(Collectors.toSet());
						}
						else
						{
							LOG.info("SolrIndexedPropertyToBeRemovedCA is not available in SiteOneFeatureSwitch");
						}
						
						for (final SiteOneCategoryAttributesModel categoryAtrribute : list)
						{
							final String code = categoryAtrribute.getAttributeCode();
							final String name = categoryAtrribute.getAttributeName();
							final boolean facet = categoryAtrribute.getFacetAttribute();
							final boolean isHighlight = categoryAtrribute.getHighlightAttribute();
							if (BooleanUtils.isTrue(categoryAtrribute.getBaseVariantAttribute()))
							{
								variantCategoryList = variantCategoryList.concat(code).concat(",");
							}
							if (categoryPH.getCatalogVersion()
									.equals(catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online")))
							{
								attributeData = productUtilObj.checkAttributeExists(code, name,
										versionModel.getPk().getLongValueAsString());
							}
							else
							{
								attributeData = productUtilObj.checkAttributeExists(code, name,
										versionModelCA.getPk().getLongValueAsString());
							}
							if (null == attributeData)
							{

								final ClassificationAttributeModel attributeModel = modelService
										.create(ClassificationAttributeModel.class);
								attributeModel.setCode(code);
								attributeModel.setName(name);
								if (categoryPH.getCatalogVersion()
										.equals(catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online")))
								{
									attributeModel.setSystemVersion(versionModel);
								}
								else
								{
									attributeModel.setSystemVersion(versionModelCA);
								}
								modelService.save(attributeModel);
								attributeData = attributeModel;
							}

							final Optional<ClassAttributeAssignmentModel> existing = existingAssignment.stream()
							        .filter(assignment -> assignment.getClassificationAttribute().getCode().equalsIgnoreCase(code))
							        .findFirst();

							if (existing.isPresent()) {
							    //update highlight flag for existing assignment
							    ClassAttributeAssignmentModel assignmentModel = existing.get();
							    assignmentModel.setHighlight(isHighlight);
							    modelService.save(assignmentModel);

							    // remove it from  list 
							    existingAssignment.remove(assignmentModel);
							} else {
							    // create new assignment as before
							    final ClassAttributeAssignmentModel assignmentModel = modelService
							            .create(ClassAttributeAssignmentModel.class);
							    assignmentModel.setClassificationAttribute(attributeData);
							    assignmentModel.setAttributeType(ClassificationAttributeTypeEnum.STRING);
							    assignmentModel.setClassificationClass(classificationModel);
							    assignmentModel.setMultiValued(false);
							    assignmentModel.setFormatDefinition(Integer.toString(30));
							    assignmentModel.setHighlight(isHighlight);
							    modelService.save(assignmentModel);
							}
							boolean skipSolrProperty = false;

							if (categoryPH.getCatalogVersion()
									.equals(catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online")))
							{
								if (propertyNameToBeRemovedUS.contains(name))
								{
									skipSolrProperty = true;
									LOG.info("propertyNameToBeRemoved found in payload :{}", name);
								}
							}
							else if (categoryPH.getCatalogVersion()
									.equals(catalogVersionService.getCatalogVersion("siteoneCAProductCatalog", "Online")))
							{
								if (propertyNameToBeRemovedCA.contains(name))
								{
									skipSolrProperty = true;
									LOG.info("propertyNameToBeRemoved found in payload :{}", name);
								}
							}

							if (!skipSolrProperty)
							{
   							//fetch solr property and class assignment for the current classification attribute being processed.
   							SolrIndexedTypeModel solrIndexedTypeModel = null;
   							if (categoryPH.getCatalogVersion()
   									.equals(catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online")))
   							{
   								solrIndexedTypeModel = productUtilObj.getSolrIndexedType();
   							}
   							if (categoryPH.getCatalogVersion()
   									.equals(catalogVersionService.getCatalogVersion("siteoneCAProductCatalog", "Online")))
   							{
   								solrIndexedTypeModel = productUtilObj.getSolrCAIndexedType();
   							}
   							SolrIndexedPropertyModel solrIndexedPropertyModel = productUtilObj.getSolrIndexedProperty(code,
   									solrIndexedTypeModel);
   							final String classificationClassCode = "CH01_".concat(categoryPH.getCode().substring(2));
   							ClassAttributeAssignmentModel facetClassAttributeAssignmentModel = null;
   							if (categoryPH.getCatalogVersion()
   									.equals(catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online")))
   							{
   								facetClassAttributeAssignmentModel = productUtilObj.getClassAttributeAssignment(code,
   										classificationClassCode, versionModel.getPk().getLongValueAsString());
   							}
   							else
   							{
   								facetClassAttributeAssignmentModel = productUtilObj.getClassAttributeAssignment(code,
   										classificationClassCode, versionModelCA.getPk().getLongValueAsString());
   							}
   
   
   							// Create Solr property if it doesn't exist and facet is true
   							if (facet && solrIndexedPropertyModel == null)
   							{
   								solrIndexedPropertyModel = productUtilObj.createSolrIndexedProperty(code, name, solrIndexedTypeModel);
   							}
   
   							if (solrIndexedPropertyModel != null && facetClassAttributeAssignmentModel != null)
   							{
   								solrIndexedPropertyModel.setDisplayName(name);
   								final List<ClassAttributeAssignmentModel> facetClassAttributeAssignmentList = Lists
   										.newArrayList(solrIndexedPropertyModel.getClassAttributeAssignments());
   								final ClassAttributeAssignmentModel facetClassAttributeAssignmentModelValue = facetClassAttributeAssignmentModel;
   								if (facet)
   								{
   									solrIndexedPropertyModel.setFacet(true);
   									facetClassAttributeAssignmentList.add(facetClassAttributeAssignmentModelValue);
   								}
   								else
   								{
   									facetClassAttributeAssignmentList
   											.removeIf(assignment -> assignment.equals(facetClassAttributeAssignmentModelValue));
   								}
   
   								// Update SolrIndexedPropertyModel based on the assignment list
   								if (facetClassAttributeAssignmentList.isEmpty())
   								{
   									solrIndexedPropertyModel.setFacet(false);
   									solrIndexedPropertyModel.setIncludeInResponse(false);
   									solrIndexedPropertyModel.setClassAttributeAssignments(facetClassAttributeAssignmentList);
   								}
   								else
   								{
   									solrIndexedPropertyModel.setClassAttributeAssignments(
   											facetClassAttributeAssignmentList.stream().distinct().collect(Collectors.toList()));
   									solrIndexedPropertyModel.setIncludeInResponse(true);
   								}
   
   								modelService.save(solrIndexedPropertyModel);
   							}
							}
						}

						if (CollectionUtils.isNotEmpty(existingAssignment))
						{
							for (final ClassAttributeAssignmentModel assignments : existingAssignment)
							{
								modelService.removeAll(assignments);
							}
						}
						LOG.info("variant list attribute " + variantCategoryList);
						categoryPH.setVariantCategoryList(StringUtils.isNotEmpty(variantCategoryList)
								? variantCategoryList.substring(0, variantCategoryList.length() - 1)
								: variantCategoryList);
						modelService.save(categoryPH);
					}
				}
				modelService.setAttributeValue(model, "attributeValues", new ArrayList<SiteOneCategoryAttributesModel>());

			}

			if (isPropertyChanged(model, value))
			{
				modelService.setAttributeValue(model, attribute.getQualifier(), value);
			}
		}
		else
		{
			if (model instanceof ProductModel || model instanceof GenericVariantProductModel)
			{
				convertedModel = modelService.getAttributeValue(model, "convertedProduct");
			}
			if (null != convertedModel)
			{
				LOG.error("attribute name " + attribute.getAttributeName());
				if (!(modelService.getAttributeValue(model, "productKind").toString())
						.equalsIgnoreCase(modelService.getAttributeValue(convertedModel, "productKind"))
						&& (null == modelService.getAttributeValue(model, "isConverted")
								|| (null != modelService.getAttributeValue(model, "isConverted")
										&& BooleanUtils.isFalse(modelService.getAttributeValue(model, "isConverted")))))
				{

					modelService.setAttributeValue(convertedModel, "catalogVersion", ((ProductModel) model).getCatalogVersion());
					modelService.setAttributeValue(convertedModel, "productKind", value.toString());
					modelService.setAttributeValue(convertedModel, "itemNumber",
							modelService.getAttributeValue(model, "itemNumber").toString());

					modelService.setAttributeValue(model, "isConverted", true);


					if (null != modelService.getAttributeValue(model, "productType"))
					{
						modelService.setAttributeValue(convertedModel, "productType",
								modelService.getAttributeValue(model, "productType").toString());
					}

					if (null != modelService.getAttributeValue(model, "isLescoBranded"))
					{
						modelService.setAttributeValue(convertedModel, "isLescoBranded",
								(Boolean)modelService.getAttributeValue(model, "isLescoBranded"));
					}

					if (null != modelService.getAttributeValue(model, "isRegulatedItem"))
					{
						modelService.setAttributeValue(convertedModel, "isRegulatedItem",
								(Boolean)modelService.getAttributeValue(model, "isRegulatedItem"));
					}
					if (null != modelService.getAttributeValue(model, "isDirectShippedProduct"))
					{
						modelService.setAttributeValue(convertedModel, "isDirectShippedProduct",
								(Boolean)modelService.getAttributeValue(model, "isDirectShippedProduct"));
					}


					if (null != modelService.getAttributeValue(model, "productLongDesc"))
					{
						modelService.setAttributeValue(convertedModel, "productLongDesc",
								modelService.getAttributeValue(model, "productLongDesc").toString());
					}
					if (null != modelService.getAttributeValue(model, "allKeywords"))
					{
						modelService.setAttributeValue(convertedModel, "allKeywords",
								modelService.getAttributeValue(model, "allKeywords").toString());
					}
					if (null != modelService.getAttributeValue(model, "featureBullets"))
					{
						modelService.setAttributeValue(convertedModel, "featureBullets",
								modelService.getAttributeValue(model, "featureBullets").toString());
					}
					if (null != modelService.getAttributeValue(model, "salientBullets"))
					{
						modelService.setAttributeValue(convertedModel, "salientBullets",
								modelService.getAttributeValue(model, "salientBullets").toString());

					}
					if (null != modelService.getAttributeValue(model, "inventoryCheck"))
					{
						modelService.setAttributeValue(convertedModel, "inventoryCheck",
								(Boolean)modelService.getAttributeValue(model, "inventoryCheck"));

					}
					if (null != modelService.getAttributeValue(model, "isShippable"))
					{
						modelService.setAttributeValue(convertedModel, "isShippable",
								(Boolean)modelService.getAttributeValue(model, "isShippable"));

					}
					if (null != modelService.getAttributeValue(model, "isDeliverable"))
					{
						modelService.setAttributeValue(convertedModel, "isDeliverable",
								(Boolean)modelService.getAttributeValue(model, "isDeliverable"));

					}
					if (null != modelService.getAttributeValue(model, "hideCSP"))
					{
						modelService.setAttributeValue(convertedModel, "hideCSP",
								(Boolean)modelService.getAttributeValue(model, "hideCSP"));

					}
					if (null != modelService.getAttributeValue(model, "hideList"))
					{
						modelService.setAttributeValue(convertedModel, "hideList",
								(Boolean)modelService.getAttributeValue(model, "hideList"));

					}

				}

				if (convertedModel instanceof ProductModel || convertedModel instanceof GenericVariantProductModel)
				{
					if (null != modelService.getAttributeValue(convertedModel, "approvalStatus")
							&& modelService.getAttributeValue(convertedModel, "approvalStatus").toString().equalsIgnoreCase("CHECK"))
					{
						modelService.setAttributeValue(convertedModel, "approvalStatus", enumerationService
								.getEnumerationValue(de.hybris.platform.catalog.enums.ArticleApprovalStatus.class, "approved"));
					}
					if ((convertedModel instanceof ProductModel || convertedModel instanceof GenericVariantProductModel)
							&& attribute.getAttributeName().equalsIgnoreCase("baseUPCCode"))
					{
						modelService.setAttributeValue(convertedModel, "baseUPCCode", value.toString());
					}
					if (attribute.getAttributeName().equalsIgnoreCase("isDiscontinued"))
					{
						modelService.setAttributeValue(convertedModel, "isProductOffline", value);
						modelService.setAttributeValue(convertedModel, "isProductDiscontinued", value);
						modelService.setAttributeValue(model, "isProductOffline", true);
						modelService.setAttributeValue(model, "isProductDiscontinued", true);
					}
					if (attribute.getAttributeName().equalsIgnoreCase("bulkFlag"))
					{
						modelService.setAttributeValue(convertedModel, "bulkFlag", value);
					}
					if (attribute.getAttributeName().equalsIgnoreCase("weighAndPayEnabled"))
					{
						modelService.setAttributeValue(convertedModel, "weighAndPayEnabled", value);
					}
					if (attribute.getAttributeName().equalsIgnoreCase("upcData"))
					{
						final String strbaseUPCCode = modelService.getAttributeValue(convertedModel, "baseUPCCode");
						final List<CategoryModel> categories = modelService.getAttributeValue(convertedModel, "supercategories");
						boolean isQtyIntCat = false;
						if (categories != null)
						{
							isQtyIntCat = categories.stream().anyMatch(c -> c.getCode().contains(PIPE_CATEGORY_CODE));
						}
						Float qtyInterval = 0.0f;
						final List<InventoryUPCModel> inventoryUPCList = new ArrayList();
						inventoryUPCList.addAll((Collection<? extends InventoryUPCModel>) value);
						final Set<Float> visibleUPC = new HashSet<>();
						final List<String> visibleUOMs = new ArrayList<String>();
						final List<String> hiddenUOMs = new ArrayList<String>();
						final Map<String, InventoryUPCModel> upcMap = new HashMap<>();
						for (final InventoryUPCModel upcData : inventoryUPCList)
						{
							upcData.setBaseUPC(false);
							if (!upcData.getFromPayload() && upcData.getHideUPCOnline() != null && !upcData.getHideUPCOnline())
							{
								upcData.setHideUPCOnline(true);
							}
							if (upcData.getFromPayload())
							{
								if (upcData.getInventoryMultiplier() == 0.0)
								{
									throw new RuntimeException("upcData with upcMultiplier value 0 is not allowed.");
								}
								if (upcData.getHideUPCOnline() != null && !upcData.getHideUPCOnline())
								{
									visibleUPC.add(upcData.getInventoryMultiplier());
									visibleUOMs.add(upcData.getCode());
								}
								else
								{
									hiddenUOMs.add(upcData.getCode());
									if (PIECE_UOM_CODE.equalsIgnoreCase(upcData.getCode()))
									{
										qtyInterval = upcData.getInventoryMultiplier();
									}
								}
							}
							upcMap.put(upcData.getInventoryUPCID(), upcData);
						}
						if (CollectionUtils.isNotEmpty(upcMap.values())
								&& upcMap.values().stream().filter(upcData -> BooleanUtils.isNotTrue(upcData.getHideUPCOnline()))
										.collect(Collectors.toSet()).size() != visibleUPC.size())
						{
							throw new RuntimeException("No Duplicate Visible UPCs allowed. Please fix and resend");
						}
						if (isQtyIntCat)
						{
							if (hiddenUOMs.contains(PIECE_UOM_CODE) && visibleUOMs.contains(FOOT_UOM_CODE)
									&& !visibleUOMs.contains(PIECE_UOM_CODE))
							{
								final Integer qtyInt = Integer.valueOf(qtyInterval.intValue());
								modelService.setAttributeValue(convertedModel, "orderQuantityInterval", qtyInt);
							}
							else
							{
								modelService.setAttributeValue(convertedModel, "orderQuantityInterval", Integer.valueOf(ZERO));
							}
						}

						if (visibleUPC.isEmpty())
						{
							throw new RuntimeException("upcHidden is true for all the upcData.");
						}

						final ArrayList<InventoryUPCModel> newUPCDataList = new ArrayList<InventoryUPCModel>(upcMap.values());
						Set<InventoryUPCModel> upcDataNHidSet = new HashSet<InventoryUPCModel>();
						Set<InventoryUPCModel> upcDataHidSet = new HashSet<InventoryUPCModel>();
						upcDataNHidSet = newUPCDataList.stream()
								.filter(upcData -> strbaseUPCCode.equalsIgnoreCase(upcData.getCode()) && !upcData.getHideUPCOnline())
								.collect(Collectors.toSet());
						upcDataHidSet = newUPCDataList.stream().filter(upcData -> strbaseUPCCode.equalsIgnoreCase(upcData.getCode())
								&& upcData.getHideUPCOnline() && upcData.getFromPayload()).collect(Collectors.toSet());
						InventoryUPCModel upcData = null;
						if (CollectionUtils.isNotEmpty(upcDataNHidSet))
						{
							upcData = (InventoryUPCModel) CollectionUtils.get(upcDataNHidSet, 0);

						}
						else if (CollectionUtils.isNotEmpty(upcDataHidSet))
						{
							upcData = (InventoryUPCModel) CollectionUtils.get(upcDataHidSet, 0);

						}
						if (upcData != null)
						{
							if (null != upcData.getInventoryMultiplier() && upcData.getInventoryMultiplier() > 1)
							{
								throw new RuntimeException("Base UOM can't be greater than 1");
							}
							upcData.setBaseUPC(true);
							modelService.setAttributeValue(convertedModel, "vendorPartNumber", upcData.getVendorPartNumber());
							modelService.setAttributeValue(convertedModel, "inventoryUPCID", upcData.getInventoryUPCID());
						}
						else
						{
							throw new RuntimeException("There is no upcData with baseUPCCode");
						}
						newUPCDataList.stream().forEach(newupcData -> {
							newupcData.setFromPayload(false);
						});
						modelService.setAttributeValue(convertedModel, attribute.getQualifier(), newUPCDataList);
					}
				}
				if (convertedModel.getItemtype().equalsIgnoreCase("Product")
						&& attribute.getAttributeName().equalsIgnoreCase("superCategoryClassPath"))
				{
					LOG.info("siteonestandardattributevaluesetter superCategoryClassPath " + value.toString());
					//ProductUtil.setClassName(String.class);
					productUtilObj.setClassPath(value.toString());
					final List<CategoryModel> categoryModel = new ArrayList<>();
					LOG.info("SiteOneStandardAttributeValueSetter before retrievecode");
					final List<String> codes = productUtilObj.retrieveCode(value.toString(), "siteoneProductCatalog");
					for (final String categoryCode : codes)
					{
						final CategoryModel category = categoryService.getCategoryForCode(
								catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online"), categoryCode);
						LOG.info("siteonestandardattributevaluesetter superCategoryClassPath before nullcheck");
						if (category != null)
						{
							LOG.info("siteonestandardattributevaluesetter superCategoryClassPath #1");
							categoryModel.add(category);
						}
					}
					modelService.setAttributeValue(convertedModel, "supercategories", categoryModel);
				}
				else if (convertedModel.getItemtype().equalsIgnoreCase("GenericVariantProduct")
						&& attribute.getAttributeName().equalsIgnoreCase("baseProductCode"))
				{
					final ProductModel baseProduct = productService.getProductForCode(
							catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online"), value.toString());
					if (null != baseProduct)
					{
						modelService.setAttributeValue(convertedModel, "baseProduct", baseProduct);
					}
					else
					{
						throw new RuntimeException("Base Product is not available in Hybris");
					}
				}
				else if (attribute.getAttributeName().equalsIgnoreCase("specificationAttribute"))
				{
					if (convertedModel.getCatalogVersion().getCatalog().getId().equalsIgnoreCase("siteoneCAProductCatalog"))
					{
						final List<String> codes = convertedModel.getSupercategories().stream().map(CategoryModel::getCode)
								.collect(Collectors.toList());
						final List<CategoryModel> categoryModel = new ArrayList<>();
						for (final String categoryCode : codes)
						{
							final CategoryModel category = categoryService.getCategoryForCode(
									catalogVersionService.getCatalogVersion("siteoneCAProductCatalog", "Online"), categoryCode);
							if (category != null)
							{
								categoryModel.add(category);
							}
						}
						convertedModel.setSupercategories(new ArrayList());
						modelService.setAttributeValue(convertedModel, "supercategories", CollectionUtils.EMPTY_COLLECTION);
						modelService.setAttributeValue(convertedModel, "supercategories", categoryModel);
					}
					final String productKind = modelService.getAttributeValue(convertedModel, "productKind");
					//productUtilObj.setClassName(String.class);
					final List<SiteOneProductSpecificationAttributeModel> list = new ArrayList();
					list.addAll((Collection<? extends SiteOneProductSpecificationAttributeModel>) value);

					final StringBuilder variantAttributes = new StringBuilder();

					String variantCategoryattrList = null;
					if (productKind.equalsIgnoreCase("Variant"))
					{
						final ProductModel baseProduct = modelService.getAttributeValue(convertedModel, "baseProduct");
						variantCategoryattrList = baseProduct.getSupercategories().stream()
								.filter(category -> category.getCode().contains("PH")).findFirst().get().getVariantCategoryList();
					}
					//need to check if it is part of conversion will we change type to base product?
					if (productKind.equalsIgnoreCase("Base"))
					{
						final List<CategoryModel> categoryModel = modelService.getAttributeValue(convertedModel, "supercategories");
						variantCategoryattrList = categoryModel.stream().filter(category -> category.getCode().contains("PH"))
								.findFirst().get().getVariantCategoryList();
					}
					//end
					for (final SiteOneProductSpecificationAttributeModel attr : list)
					{
						final String attrValue = attr.getText();
						final String code = attr.getName();
						if (null != variantCategoryattrList && (variantCategoryattrList.toLowerCase()).contains(code.toLowerCase()))
						{
							variantAttributes.append(code).append("@");
						}

						if (code.contains("Maintenance_Item") || code.contains("hrdscpItem") || code.contains("irgItem")
								|| code.contains("lndscpItem") || code.contains("lghtItem") || code.contains("nrsryItem"))
						{
							modelService.setAttributeValue(convertedModel, "specificationItem", attrValue);
						}
						else if (code.contains("Maintenance_Type") || code.contains("hrdscpType") || code.contains("irgType")
								|| code.contains("lndscpType") || code.contains("lghtType") || code.contains("nrsryType"))
						{
							modelService.setAttributeValue(convertedModel, "specificationType", attrValue);
						}
						else if (code.contains("Maintenance_Series") || code.contains("hrdscpSeries") || code.contains("irgSeries")
								|| code.contains("lndscpSeries"))
						{
							modelService.setAttributeValue(convertedModel, "specificationSeries", attrValue);
						}

						if (code.contains("ProductBrandName"))
						{
							//ProductUtil.setClassName(BrandNameEnum.class);
							final BrandNameEnum productBrandName = (BrandNameEnum) productUtilObj.getBrandCode(attrValue);
							if (productBrandName != null)
							{
								modelService.setAttributeValue(convertedModel, "productBrandName", productBrandName);
							}
							else
							{
								if (StringUtils.isNotEmpty(attrValue) && (!attrValue.equalsIgnoreCase(" ")))
								{
									productUtilObj.setBrandEnum(attrValue);
									modelService.setAttributeValue(convertedModel, "productBrandName",
											productUtilObj.getBrandCode(attrValue));
								}
								else
								{
									modelService.setAttributeValue(convertedModel, "productBrandName",
											productUtilObj.getBrandCode("Coreproductbrandnamenull"));
								}
							}

						}
						else if (code.contains("SubBrand"))
						{
							modelService.setAttributeValue(convertedModel, "subBrand", attrValue);
						}
						else if (StringUtils.isNotEmpty(code))
						{

							convertedModel.getClassificationClasses().forEach(classes -> {
								for (final ClassAttributeAssignmentModel assignmentModel : classes
										.getAllClassificationAttributeAssignments())
								{
									if (assignmentModel.getClassificationAttribute() != null
											&& assignmentModel.getClassificationAttribute().getCode().equalsIgnoreCase(code))
									{
										LOG.info("siteonestandardattributevaluesetter specificationAttribute #1");
										productUtilObj.setProductFeature(convertedModel, assignmentModel, code, modelService, attrValue);
										break;
									}
								}
							});
						}

					}

					if (convertedModel.getItemtype().equalsIgnoreCase("GenericVariantProduct"))
					{
						parseVariantValues(convertedModel, list);
					}
					//need to check if base conversion is needed or not
					else if (productKind.equalsIgnoreCase("Base"))
					{
						parseVariantAttributes(convertedModel, variantAttributes.deleteCharAt(variantAttributes.length() - 1));
					}
					//need to check the above base conversion


					if (null != convertedModel.getFeatures())
					{
						modelService.saveAll(convertedModel.getFeatures());
						modelService.setAttributeValue(convertedModel, "features", convertedModel.getFeatures());
					}
					else
					{
						modelService.setAttributeValue(model, "specificationAttribute",
								new ArrayList<SiteOneProductSpecificationAttributeModel>());

						modelService.setAttributeValue(convertedModel, "specificationAttribute",
								new ArrayList<SiteOneProductSpecificationAttributeModel>());
					}

					LOG.error("VariantAttributes :" + variantAttributes.toString());
				}
				else if (attribute.getAttributeName().equalsIgnoreCase("shortDescription"))
				{
					final String shortTexts = siteOneFeatureSwitchCacheService.getValueForSwitch("PrdShortDescToBeRemoved");
					final String[] splitTexts = shortTexts.split(Pattern.quote("|"));
					String shortDesc = value.toString();
					for (final String splitText : splitTexts)
					{
						if (shortDesc.contains(splitText))
						{
							shortDesc = shortDesc.replace(splitText, "").trim();
						}
					}
					modelService.setAttributeValue(convertedModel, "productShortDesc", shortDesc);

				}

				else if (attribute.getAttributeName().equalsIgnoreCase("itemImage"))
				{
					final String productCode = modelService.getAttributeValue(convertedModel, "code");
					//productUtilObj.setClassName(String.class);
					final List<SiteOneProductItemImagesModel> list = new ArrayList();
					list.addAll((Collection<? extends SiteOneProductItemImagesModel>) value);
					List<MediaContainerModel> mediaContainerModelList = modelService.getAttributeValue(model, "galleryImages");
					if (mediaContainerModelList != null && !mediaContainerModelList.isEmpty())
					{
						for (final MediaContainerModel medicontainer : mediaContainerModelList)
						{
							modelService.removeAll(medicontainer.getMedias());
						}
						modelService.removeAll(mediaContainerModelList);
					}
					mediaContainerModelList = new ArrayList();
					final List<MediaFormatModel> mediaFormatList = productUtilObj.retrieveMediaFormat();
					final MediaFolderModel mediaFolderModel = productUtilObj.retrieveMediaFolder();
					boolean isPictureSet = false;
					for (final SiteOneProductItemImagesModel itemImage : list)
					{
						final List<MediaModel> mediaModelList = new ArrayList();
						final SiteOneProductImageModel imageModel = itemImage.getImage();
						final String id = imageModel.getId();
						final String fullPath = imageModel.getFullpath();
						final String mimeType = imageModel.getMimetype();
						final String priority = imageModel.getPriority().toString();
						final String videoId = imageModel.getVideoId();
						final String code = (modelService.getAttributeValue(convertedModel, "code")).toString().concat("_")
								.concat(priority);
						if (videoId != null)
						{
							final MediaModel youtubeMedia = createMediaModel(mediaFolderModel, code, fullPath, mimeType, videoId,
									"youtube", mediaFormatList, null, convertedModel.getCatalogVersion());
							mediaModelList.add(youtubeMedia);
						}
						final MediaModel mainMedia = createMediaModel(mediaFolderModel, code, fullPath, mimeType, videoId, null, null,
								null, convertedModel.getCatalogVersion());
						mediaModelList.add(mainMedia);
						if (!isPictureSet)
						{
							modelService.setAttributeValue(convertedModel, "picture", mainMedia);
							isPictureSet = true;
						}
						final List<SiteOneProductTransformationModel> list_1 = new ArrayList();
						list_1.addAll(imageModel.getTransformations());
						for (final SiteOneProductTransformationModel attr : list_1)
						{
							final String url = attr.getUrl();
							final String format = attr.getFormat();
							final MediaModel medias = createMediaModel(mediaFolderModel, code, url, mimeType, videoId, format,
									mediaFormatList, null, convertedModel.getCatalogVersion());
							mediaModelList.add(medias);
						}
						final MediaContainerModel mediaContainer = new MediaContainerModel();
						final String mediaContainerCode = (modelService.getAttributeValue(convertedModel, "code")).toString()
								.concat("_").concat(priority);
						mediaContainer.setMedias(mediaModelList);
						mediaContainer.setQualifier(mediaContainerCode);
						if (convertedModel.getCatalogVersion().getCatalog().getId().equalsIgnoreCase("siteoneCAProductCatalog"))
						{
							mediaContainer
									.setCatalogVersion(catalogVersionService.getCatalogVersion("siteoneCAProductCatalog", "Online"));
						}
						else
						{
							mediaContainer.setCatalogVersion(catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online"));
						}
						mediaContainerModelList.add(mediaContainer);
					}
					modelService.setAttributeValue(convertedModel, "itemImage", new ArrayList<SiteOneProductItemImagesModel>());
					modelService.setAttributeValue(convertedModel, "galleryImages", mediaContainerModelList);
				}
				else if (attribute.getAttributeName().equalsIgnoreCase("brandLogo"))
				{
					final String productCode = modelService.getAttributeValue(convertedModel, "code");
					//productUtilObj.setClassName(String.class);
					final List<SiteOneProductItemImagesModel> list = new ArrayList();
					list.addAll((Collection<? extends SiteOneProductItemImagesModel>) value);
					List<MediaContainerModel> mediaContainerModelList = modelService.getAttributeValue(convertedModel, "brandLogos");
					if (mediaContainerModelList != null && !mediaContainerModelList.isEmpty())
					{
						for (final MediaContainerModel medicontainer : mediaContainerModelList)
						{
							modelService.removeAll(medicontainer.getMedias());
						}
						modelService.removeAll(mediaContainerModelList);
					}
					mediaContainerModelList = new ArrayList();
					final List<MediaFormatModel> mediaFormatList = productUtilObj.retrieveMediaFormat();
					final MediaFolderModel mediaFolderModel = productUtilObj.retrieveMediaFolder();
					for (final SiteOneProductItemImagesModel itemImage : list)
					{
						final List<MediaModel> mediaModelList = new ArrayList();
						final SiteOneProductImageModel imageModel = itemImage.getImage();
						final String id = imageModel.getId();
						final String fullPath = imageModel.getFullpath();
						final String mimeType = imageModel.getMimetype();
						final String priority = imageModel.getPriority().toString();
						final String videoId = imageModel.getVideoId();
						final String code = (modelService.getAttributeValue(convertedModel, "code")).toString().concat("_")
								.concat(String.valueOf(priority)).concat("_").concat("501");
						if (videoId != null)
						{
							final MediaModel youtubeMedia = createMediaModel(mediaFolderModel, code, fullPath, mimeType, videoId,
									"youtube", mediaFormatList, null, convertedModel.getCatalogVersion());
							mediaModelList.add(youtubeMedia);
						}
						final MediaModel mainMedia = createMediaModel(mediaFolderModel, code, fullPath, mimeType, videoId, null, null,
								null, convertedModel.getCatalogVersion());
						mediaModelList.add(mainMedia);
						final List<SiteOneProductTransformationModel> list_1 = new ArrayList();
						list_1.addAll(imageModel.getTransformations());
						for (final SiteOneProductTransformationModel attr : list_1)
						{
							final String url = attr.getUrl();
							final String format = attr.getFormat();
							final MediaModel medias = createMediaModel(mediaFolderModel, code, url, mimeType, videoId, format,
									mediaFormatList, null, convertedModel.getCatalogVersion());
							mediaModelList.add(medias);
						}
						final MediaContainerModel mediaContainer = new MediaContainerModel();
						final String mediaContainerCode = (modelService.getAttributeValue(convertedModel, "code")).toString()
								.concat("_").concat(String.valueOf(priority)).concat("_").concat("502");
						mediaContainer.setMedias(mediaModelList);
						mediaContainer.setQualifier(mediaContainerCode);
						if (convertedModel.getCatalogVersion().getCatalog().getId().equalsIgnoreCase("siteoneCAProductCatalog"))
						{
							mediaContainer
									.setCatalogVersion(catalogVersionService.getCatalogVersion("siteoneCAProductCatalog", "Online"));
						}
						else
						{
							mediaContainer.setCatalogVersion(catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online"));
						}
						mediaContainerModelList.add(mediaContainer);
					}
					modelService.setAttributeValue(convertedModel, "brandLogo", new ArrayList<SiteOneProductItemImagesModel>());
					modelService.setAttributeValue(convertedModel, "brandLogos", mediaContainerModelList);

				}
				else if (attribute.getAttributeName().equalsIgnoreCase("lifeStyle"))
				{
					final String productCode = modelService.getAttributeValue(convertedModel, "code");
					//productUtilObj.setClassName(String.class);
					final List<SiteOneProductItemImagesModel> list = new ArrayList();
					list.addAll((Collection<? extends SiteOneProductItemImagesModel>) value);
					List<MediaContainerModel> mediaContainerModelList = modelService.getAttributeValue(convertedModel, "lifeStyles");
					if (mediaContainerModelList != null && !mediaContainerModelList.isEmpty())
					{
						for (final MediaContainerModel medicontainer : mediaContainerModelList)
						{
							modelService.removeAll(medicontainer.getMedias());
						}
						modelService.removeAll(mediaContainerModelList);
					}
					mediaContainerModelList = new ArrayList();
					final List<MediaFormatModel> mediaFormatList = productUtilObj.retrieveMediaFormat();
					final MediaFolderModel mediaFolderModel = productUtilObj.retrieveMediaFolder();
					for (final SiteOneProductItemImagesModel itemImage : list)
					{
						final List<MediaModel> mediaModelList = new ArrayList();
						final SiteOneProductImageModel imageModel = itemImage.getImage();
						final String id = imageModel.getId();
						final String fullPath = imageModel.getFullpath();
						final String mimeType = imageModel.getMimetype();
						final String priority = imageModel.getPriority().toString();
						final String videoId = imageModel.getVideoId();
						final String code = (modelService.getAttributeValue(convertedModel, "code")).toString().concat("_")
								.concat(String.valueOf(priority)).concat("_").concat("601");
						if (videoId != null)
						{
							final MediaModel youtubeMedia = createMediaModel(mediaFolderModel, code, fullPath, mimeType, videoId,
									"youtube", mediaFormatList, null, convertedModel.getCatalogVersion());
							mediaModelList.add(youtubeMedia);
						}
						final MediaModel mainMedia = createMediaModel(mediaFolderModel, code, fullPath, mimeType, videoId, null, null,
								null, convertedModel.getCatalogVersion());
						mediaModelList.add(mainMedia);
						final List<SiteOneProductTransformationModel> list_1 = new ArrayList();
						list_1.addAll(imageModel.getTransformations());
						for (final SiteOneProductTransformationModel attr : list_1)
						{
							final String url = attr.getUrl();
							final String format = attr.getFormat();
							final MediaModel medias = createMediaModel(mediaFolderModel, code, url, mimeType, videoId, format,
									mediaFormatList, null, convertedModel.getCatalogVersion());
							mediaModelList.add(medias);
						}
						final MediaContainerModel mediaContainer = new MediaContainerModel();
						final String mediaContainerCode = (modelService.getAttributeValue(convertedModel, "code")).toString()
								.concat("_").concat(String.valueOf(priority)).concat("_").concat("602");
						mediaContainer.setMedias(mediaModelList);
						mediaContainer.setQualifier(mediaContainerCode);
						if (((ProductModel) model).getCatalogVersion().getCatalog().getId().equalsIgnoreCase("siteoneCAProductCatalog"))
						{
							mediaContainer
									.setCatalogVersion(catalogVersionService.getCatalogVersion("siteoneCAProductCatalog", "Online"));
						}
						else
						{
							mediaContainer.setCatalogVersion(catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online"));
						}
						mediaContainerModelList.add(mediaContainer);
					}
					modelService.setAttributeValue(convertedModel, "lifeStyle", new ArrayList<SiteOneProductItemImagesModel>());
					modelService.setAttributeValue(convertedModel, "lifeStyles", mediaContainerModelList);

				}

				else if (attribute.getAttributeName().equalsIgnoreCase("swatchImage"))
				{
					final String productCode = modelService.getAttributeValue(convertedModel, "code");
					//productUtilObj.setClassName(String.class);
					final List<SiteOneProductItemImagesModel> list = new ArrayList();
					list.addAll((Collection<? extends SiteOneProductItemImagesModel>) value);
					List<MediaContainerModel> mediaContainerModelList = modelService.getAttributeValue(convertedModel, "swatchImages");
					if (mediaContainerModelList != null && !mediaContainerModelList.isEmpty())
					{
						for (final MediaContainerModel medicontainer : mediaContainerModelList)
						{
							modelService.removeAll(medicontainer.getMedias());
						}
						modelService.removeAll(mediaContainerModelList);
					}
					mediaContainerModelList = new ArrayList();
					final List<MediaFormatModel> mediaFormatList = productUtilObj.retrieveMediaFormat();
					final MediaFolderModel mediaFolderModel = productUtilObj.retrieveMediaFolder();
					for (final SiteOneProductItemImagesModel itemImage : list)
					{
						final List<MediaModel> mediaModelList = new ArrayList();
						final SiteOneProductImageModel imageModel = itemImage.getImage();
						final String id = imageModel.getId();
						final String fullPath = imageModel.getFullpath();
						final String mimeType = imageModel.getMimetype();
						final String priority = imageModel.getPriority().toString();
						final String videoId = imageModel.getVideoId();
						final String code = (modelService.getAttributeValue(convertedModel, "code")).toString().concat("_")
								.concat(String.valueOf(priority)).concat("_").concat("701");
						if (videoId != null)
						{
							final MediaModel youtubeMedia = createMediaModel(mediaFolderModel, code, fullPath, mimeType, videoId,
									"youtube", mediaFormatList, null, convertedModel.getCatalogVersion());
							mediaModelList.add(youtubeMedia);
						}
						final MediaModel mainMedia = createMediaModel(mediaFolderModel, code, fullPath, mimeType, videoId, null, null,
								null, convertedModel.getCatalogVersion());
						mediaModelList.add(mainMedia);
						final List<SiteOneProductTransformationModel> list_1 = new ArrayList();
						list_1.addAll(imageModel.getTransformations());
						for (final SiteOneProductTransformationModel attr : list_1)
						{
							final String url = attr.getUrl();
							final String format = attr.getFormat();
							final MediaModel medias = createMediaModel(mediaFolderModel, code, url, mimeType, videoId, format,
									mediaFormatList, null, convertedModel.getCatalogVersion());
							mediaModelList.add(medias);
						}
						final MediaContainerModel mediaContainer = new MediaContainerModel();
						final String mediaContainerCode = (modelService.getAttributeValue(convertedModel, "code")).toString()
								.concat("_").concat(String.valueOf(priority)).concat("_").concat("702");
						mediaContainer.setMedias(mediaModelList);
						mediaContainer.setQualifier(mediaContainerCode);
						if (((ProductModel) model).getCatalogVersion().getCatalog().getId().equalsIgnoreCase("siteoneCAProductCatalog"))
						{
							mediaContainer
									.setCatalogVersion(catalogVersionService.getCatalogVersion("siteoneCAProductCatalog", "Online"));
						}
						else
						{
							mediaContainer.setCatalogVersion(catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online"));
						}
						mediaContainerModelList.add(mediaContainer);
					}
					modelService.setAttributeValue(convertedModel, "swatchImage", new ArrayList<SiteOneProductItemImagesModel>());
					modelService.setAttributeValue(convertedModel, "swatchImages", mediaContainerModelList);

				}
				else if (attribute.getAttributeName().equalsIgnoreCase("specialImageType"))
				{
					final String productCode = modelService.getAttributeValue(convertedModel, "code");
					//productUtilObj.setClassName(String.class);
					final List<SiteOneProductItemImagesModel> list = new ArrayList();
					list.addAll((Collection<? extends SiteOneProductItemImagesModel>) value);
					List<MediaContainerModel> mediaContainerModelList = modelService.getAttributeValue(convertedModel,
							"specialImageTypes");
					if (mediaContainerModelList != null && !mediaContainerModelList.isEmpty())
					{
						for (final MediaContainerModel medicontainer : mediaContainerModelList)
						{
							modelService.removeAll(medicontainer.getMedias());
						}
						modelService.removeAll(mediaContainerModelList);
					}
					mediaContainerModelList = new ArrayList();
					final List<MediaFormatModel> mediaFormatList = productUtilObj.retrieveMediaFormat();
					final MediaFolderModel mediaFolderModel = productUtilObj.retrieveMediaFolder();
					for (final SiteOneProductItemImagesModel itemImage : list)
					{
						final List<MediaModel> mediaModelList = new ArrayList();
						final SiteOneProductImageModel imageModel = itemImage.getImage();
						final String id = imageModel.getId();
						final String fullPath = imageModel.getFullpath();
						final String mimeType = imageModel.getMimetype();
						final String priority = imageModel.getPriority().toString();
						final String videoId = imageModel.getVideoId();
						final String code = (modelService.getAttributeValue(convertedModel, "code")).toString().concat("_")
								.concat(String.valueOf(priority)).concat("_").concat("801");
						if (videoId != null)
						{
							final MediaModel youtubeMedia = createMediaModel(mediaFolderModel, code, fullPath, mimeType, videoId,
									"youtube", mediaFormatList, null, convertedModel.getCatalogVersion());
							mediaModelList.add(youtubeMedia);
						}
						final MediaModel mainMedia = createMediaModel(mediaFolderModel, code, fullPath, mimeType, videoId, null, null,
								null, convertedModel.getCatalogVersion());
						mediaModelList.add(mainMedia);
						if (imageModel.getTransformations() != null)
						{
							final List<SiteOneProductTransformationModel> list_1 = new ArrayList();
							list_1.addAll(imageModel.getTransformations());
							for (final SiteOneProductTransformationModel attr : list_1)
							{
								final String url = attr.getUrl();
								final String format = attr.getFormat();
								final MediaModel medias = createMediaModel(mediaFolderModel, code, url, mimeType, videoId, format,
										mediaFormatList, null, convertedModel.getCatalogVersion());
								mediaModelList.add(medias);
							}
						}
						final MediaContainerModel mediaContainer = new MediaContainerModel();
						final String mediaContainerCode = (modelService.getAttributeValue(convertedModel, "code")).toString()
								.concat("_").concat(String.valueOf(priority)).concat("_").concat("802");
						mediaContainer.setMedias(mediaModelList);
						mediaContainer.setQualifier(mediaContainerCode);
						if (convertedModel.getCatalogVersion().getCatalog().getId().equalsIgnoreCase("siteoneCAProductCatalog"))
						{
							mediaContainer
									.setCatalogVersion(catalogVersionService.getCatalogVersion("siteoneCAProductCatalog", "Online"));
						}
						else
						{
							mediaContainer.setCatalogVersion(catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online"));
						}
						mediaContainerModelList.add(mediaContainer);
					}
					modelService.setAttributeValue(convertedModel, "specialImageType", new ArrayList<SiteOneProductItemImagesModel>());
					modelService.setAttributeValue(convertedModel, "specialImageTypes", mediaContainerModelList);

				}

				else if (attribute.getAttributeName().equalsIgnoreCase("sheets"))
				{
					LOG.info("Data sheet entry 1");
					final String productCode = modelService.getAttributeValue(convertedModel, "code");
					//ProductUtil.setClassName(String.class);
					final SiteOneDataSheetModel list = (SiteOneDataSheetModel) value;
					final Collection<MediaModel> mediaModelList = modelService.getAttributeValue(convertedModel, "data_sheet");
					final Collection<MediaModel> setThisMediaModelList = new ArrayList();
					if (mediaModelList != null && !mediaModelList.isEmpty())
					{
						modelService.removeAll(mediaModelList);
					}
					final MediaFolderModel mediaFolderModel = productUtilObj.retrieveMediaFolder();
					if (CollectionUtils.isNotEmpty(list.getSpecificationSheet()))
					{
						for (final SiteOneProductSpecificationSheetModel specSheet : list.getSpecificationSheet())
						{
							LOG.info("Data sheet entry - Specification Sheet");
							final String fullPath = specSheet.getFullpath();
							final String mimeType = specSheet.getMimetype();
							final String priority = specSheet.getPriority();
							final String code = (modelService.getAttributeValue(convertedModel, "code")).toString().concat("_")
									.concat(String.valueOf(priority)).concat("_").concat("204");
							final MediaModel mediaModel = createMediaModel(mediaFolderModel, code, fullPath, mimeType, null, null, null,
									"Product Information Sheet", convertedModel.getCatalogVersion());
							setThisMediaModelList.add(mediaModel);
						}
					}
					if (CollectionUtils.isNotEmpty(list.getBrochures()))
					{
						for (final SiteOneBrochuresModel Brochures : list.getBrochures())
						{
							LOG.info("Data sheet entry - Brochures");
							final String fullPath = Brochures.getFullpath();
							final String mimeType = Brochures.getMimetype();
							final String priority = Brochures.getPriority();
							final String code = (modelService.getAttributeValue(convertedModel, "code")).toString().concat("_")
									.concat(String.valueOf(priority)).concat("_").concat("210");
							final MediaModel mediaModel = createMediaModel(mediaFolderModel, code, fullPath, mimeType, null, null, null,
									BROCHURES, convertedModel.getCatalogVersion());
							setThisMediaModelList.add(mediaModel);
						}
					}

					if (CollectionUtils.isNotEmpty(list.getSafetyDataSheet()))
					{
						for (final SiteOneProductSafetyDataSheetModel sdSheet : list.getSafetyDataSheet())
						{
							LOG.info("Data sheet entry - Safety Data Sheet");
							final String fullPath = sdSheet.getFullpath();
							final String mimeType = sdSheet.getMimetype();
							final String priority = sdSheet.getPriority();
							final String code = (modelService.getAttributeValue(convertedModel, "code")).toString().concat("_")
									.concat(String.valueOf(priority)).concat("_").concat("200");
							final MediaModel mediaModel = createMediaModel(mediaFolderModel, code, fullPath, mimeType, null, null, null,
									"SDS", convertedModel.getCatalogVersion());
							setThisMediaModelList.add(mediaModel);
						}
					}
					if (CollectionUtils.isNotEmpty(list.getLabelAsset()))
					{
						for (final SiteOneProductLabelSheetModel sdSheet : list.getLabelAsset())
						{
							LOG.info("Data sheet entry - Label Sheet");
							final String fullPath = sdSheet.getFullpath();
							final String mimeType = sdSheet.getMimetype();
							final String priority = sdSheet.getPriority();
							final String code = (modelService.getAttributeValue(convertedModel, "code")).toString().concat("_")
									.concat(String.valueOf(priority)).concat("_").concat("201");
							final MediaModel mediaModel = createMediaModel(mediaFolderModel, code, fullPath, mimeType, null, null, null,
									"Label", convertedModel.getCatalogVersion());
							setThisMediaModelList.add(mediaModel);
						}
					}
					if (CollectionUtils.isNotEmpty(list.getOwnersManual()))
					{
						for (final SiteOneOwnersManualModel ownersManual : list.getOwnersManual())
						{
							LOG.info("Data sheet entry - Owners Manual");
							final String fullPath = ownersManual.getFullpath();
							final String mimeType = ownersManual.getMimetype();
							final String priority = ownersManual.getPriority();
							final String code = (modelService.getAttributeValue(convertedModel, "code")).toString().concat("_")
									.concat(String.valueOf(priority)).concat("_").concat("205");
							final MediaModel mediaModel = createMediaModel(mediaFolderModel, code, fullPath, mimeType, null, null, null,
									OWNERS_MANUAL, convertedModel.getCatalogVersion());
							setThisMediaModelList.add(mediaModel);
						}
					}
					if (CollectionUtils.isNotEmpty(list.getInstallationInstructions()))
					{
						for (final SiteOneInstallationInstructionsModel installationInstructions : list.getInstallationInstructions())
						{
							LOG.info("Data sheet entry - Installation Instructions");
							final String fullPath = installationInstructions.getFullpath();
							final String mimeType = installationInstructions.getMimetype();
							final String priority = installationInstructions.getPriority();
							final String code = (modelService.getAttributeValue(convertedModel, "code")).toString().concat("_")
									.concat(String.valueOf(priority)).concat("_").concat("206");
							final MediaModel mediaModel = createMediaModel(mediaFolderModel, code, fullPath, mimeType, null, null, null,
									INSTALLATION_INSTRUCTION, convertedModel.getCatalogVersion());
							setThisMediaModelList.add(mediaModel);
						}
					}
					if (CollectionUtils.isNotEmpty(list.getTechnicalDrawing()))
					{
						for (final SiteOneTechnicalDrawingModel technicalDrawing : list.getTechnicalDrawing())
						{
							LOG.info("Data sheet entry - Technical Drawing");
							final String fullPath = technicalDrawing.getFullpath();
							final String mimeType = technicalDrawing.getMimetype();
							final String priority = technicalDrawing.getPriority();
							final String code = (modelService.getAttributeValue(convertedModel, "code")).toString().concat("_")
									.concat(String.valueOf(priority)).concat("_").concat("207");
							final MediaModel mediaModel = createMediaModel(mediaFolderModel, code, fullPath, mimeType, null, null, null,
									TECHNICAL_DRAWING, convertedModel.getCatalogVersion());
							setThisMediaModelList.add(mediaModel);
						}
					}
					if (CollectionUtils.isNotEmpty(list.getPartsDiagram()))
					{
						for (final SiteOnePartsDiagramModel partsDiagram : list.getPartsDiagram())
						{
							LOG.info("Data sheet entry - Parts Diagram");
							final String fullPath = partsDiagram.getFullpath();
							final String mimeType = partsDiagram.getMimetype();
							final String priority = partsDiagram.getPriority();
							final String code = (modelService.getAttributeValue(convertedModel, "code")).toString().concat("_")
									.concat(String.valueOf(priority)).concat("_").concat("208");
							final MediaModel mediaModel = createMediaModel(mediaFolderModel, code, fullPath, mimeType, null, null, null,
									PARTS_DIAGRAM, convertedModel.getCatalogVersion());
							setThisMediaModelList.add(mediaModel);
						}
					}
					if (CollectionUtils.isNotEmpty(list.getWarrantyInformation()))
					{
						for (final SiteOneWarrantyInformationModel warrantyInformation : list.getWarrantyInformation())
						{
							LOG.info("Data sheet entry - Warranty Information");
							final String fullPath = warrantyInformation.getFullpath();
							final String mimeType = warrantyInformation.getMimetype();
							final String priority = warrantyInformation.getPriority();
							final String code = (modelService.getAttributeValue(convertedModel, "code")).toString().concat("_")
									.concat(String.valueOf(priority)).concat("_").concat("209");
							final MediaModel mediaModel = createMediaModel(mediaFolderModel, code, fullPath, mimeType, null, null, null,
									WARRANTY_INFO, convertedModel.getCatalogVersion());
							setThisMediaModelList.add(mediaModel);
						}
					}
					modelService.setAttributeValue(convertedModel, "sheets", new SiteOneDataSheetModel());
					modelService.setAttributeValue(convertedModel, "data_sheet", setThisMediaModelList);
				}
				else if (attribute.getAttributeName().equalsIgnoreCase("productKind"))
				{
					modelService.setAttributeValue(convertedModel, "productKind", value.toString());
				}
				else if (attribute.getAttributeName().equalsIgnoreCase("online_status"))
				{
					modelService.setAttributeValue(convertedModel, "secretSku", false);
					modelService.setAttributeValue(convertedModel, "regionallyAssorted", false);
					final String onlineStatus = value.toString();
					if ("Phantom SKU".equalsIgnoreCase(onlineStatus))
					{
						modelService.setAttributeValue(convertedModel, "secretSku", true);
					}
					if ("Regional".equalsIgnoreCase(onlineStatus))
					{
						modelService.setAttributeValue(convertedModel, "regionallyAssorted", true);
					}
				}
				else if (attribute.getAttributeName().equalsIgnoreCase("savingsCenterAttribute"))
				{
					final List<SiteOneProductSavingsCenterAttributeModel> list = new ArrayList();
					list.addAll((Collection<? extends SiteOneProductSavingsCenterAttributeModel>) value);
					List<SiteOneProductSavingsCenterAttributeModel> existingItemsList = modelService.getAttributeValue(model, "savingsCenterAttribute");
					if (existingItemsList != null && !existingItemsList.isEmpty())
					{
						modelService.removeAll(existingItemsList);
					}
					modelService.setAttributeValue(model, "savingsCenterAttribute", list);
				}
				else if (isPropertyChanged(model, value))
				{
					LOG.error("inside property changed " + attribute.getQualifier());
					if (!attribute.getQualifier().equalsIgnoreCase("isProductDiscontinued"))
					{
						modelService.setAttributeValue(model, attribute.getQualifier(), value);
					}
					modelService.setAttributeValue(convertedModel, attribute.getQualifier(), value);
				}


			}
			else
			{
				if (model instanceof ProductModel || model instanceof GenericVariantProductModel)
				{
					if (modelService.getAttributeValue(model, "approvalStatus").toString().equalsIgnoreCase("CHECK"))
					{
						modelService.setAttributeValue(model, "approvalStatus", enumerationService
								.getEnumerationValue(de.hybris.platform.catalog.enums.ArticleApprovalStatus.class, "approved"));
					}
					if ((model instanceof ProductModel || model instanceof GenericVariantProductModel)
							&& attribute.getAttributeName().equalsIgnoreCase("baseUPCCode"))
					{
						modelService.setAttributeValue(model, "baseUPCCode", value.toString());
					}
					if (attribute.getAttributeName().equalsIgnoreCase("isDiscontinued"))
					{
						modelService.setAttributeValue(model, "isProductOffline", value);
					}
					if (attribute.getAttributeName().equalsIgnoreCase("bulkFlag"))
					{
						modelService.setAttributeValue(model, "bulkFlag", value);
					}
					if (attribute.getAttributeName().equalsIgnoreCase("weighAndPayEnabled"))
					{
						modelService.setAttributeValue(model, "weighAndPayEnabled", value);
					}

					if (attribute.getAttributeName().equalsIgnoreCase("upcData"))
					{
						final String strbaseUPCCode = modelService.getAttributeValue(model, "baseUPCCode");
						final List<CategoryModel> categories = modelService.getAttributeValue(model, "supercategories");
						boolean isQtyIntCat = false;
						if (categories != null)
						{
							isQtyIntCat = categories.stream().anyMatch(c -> c.getCode().contains(PIPE_CATEGORY_CODE));
						}
						Float qtyInterval = 0.0f;
						final List<InventoryUPCModel> inventoryUPCList = new ArrayList();
						inventoryUPCList.addAll((Collection<? extends InventoryUPCModel>) value);
						final Set<Float> visibleUPC = new HashSet<>();
						final List<String> visibleUOMs = new ArrayList<String>();
						final List<String> hiddenUOMs = new ArrayList<String>();
						final Map<String, InventoryUPCModel> upcMap = new HashMap<>();
						for (final InventoryUPCModel upcData : inventoryUPCList)
						{
							upcData.setBaseUPC(false);
							if (!upcData.getFromPayload() && upcData.getHideUPCOnline() != null && !upcData.getHideUPCOnline())
							{
								upcData.setHideUPCOnline(true);
							}
							if (upcData.getFromPayload())
							{
								if (upcData.getInventoryMultiplier() == 0.0)
								{
									throw new RuntimeException("upcData with upcMultiplier value 0 is not allowed.");
								}
								if (upcData.getHideUPCOnline() != null && !upcData.getHideUPCOnline())
								{
									visibleUPC.add(upcData.getInventoryMultiplier());
									visibleUOMs.add(upcData.getCode());
								}
								else
								{
									hiddenUOMs.add(upcData.getCode());
									if (PIECE_UOM_CODE.equalsIgnoreCase(upcData.getCode()))
									{
										qtyInterval = upcData.getInventoryMultiplier();
									}
								}
							}
							upcMap.put(upcData.getInventoryUPCID(), upcData);
						}
						if (CollectionUtils.isNotEmpty(upcMap.values())
								&& upcMap.values().stream().filter(upcData -> BooleanUtils.isNotTrue(upcData.getHideUPCOnline()))
										.collect(Collectors.toSet()).size() != visibleUPC.size())
						{
							throw new RuntimeException("No Duplicate Visible UPCs allowed. Please fix and resend");
						}
						if (isQtyIntCat)
						{
							if (hiddenUOMs.contains(PIECE_UOM_CODE) && visibleUOMs.contains(FOOT_UOM_CODE)
									&& !visibleUOMs.contains(PIECE_UOM_CODE))
							{
								final Integer qtyInt = Integer.valueOf(qtyInterval.intValue());
								modelService.setAttributeValue(model, "orderQuantityInterval", qtyInt);
							}
							else
							{
								modelService.setAttributeValue(model, "orderQuantityInterval", Integer.valueOf(ZERO));
							}
						}

						if (visibleUPC.isEmpty())
						{
							throw new RuntimeException("upcHidden is true for all the upcData.");
						}

						final ArrayList<InventoryUPCModel> newUPCDataList = new ArrayList<InventoryUPCModel>(upcMap.values());
						Set<InventoryUPCModel> upcDataNHidSet = new HashSet<InventoryUPCModel>();
						Set<InventoryUPCModel> upcDataHidSet = new HashSet<InventoryUPCModel>();
						upcDataNHidSet = newUPCDataList.stream()
								.filter(upcData -> strbaseUPCCode.equalsIgnoreCase(upcData.getCode()) && !upcData.getHideUPCOnline())
								.collect(Collectors.toSet());
						upcDataHidSet = newUPCDataList.stream().filter(upcData -> strbaseUPCCode.equalsIgnoreCase(upcData.getCode())
								&& upcData.getHideUPCOnline() && upcData.getFromPayload()).collect(Collectors.toSet());
						InventoryUPCModel upcData = null;
						if (CollectionUtils.isNotEmpty(upcDataNHidSet))
						{
							upcData = (InventoryUPCModel) CollectionUtils.get(upcDataNHidSet, 0);

						}
						else if (CollectionUtils.isNotEmpty(upcDataHidSet))
						{
							upcData = (InventoryUPCModel) CollectionUtils.get(upcDataHidSet, 0);

						}
						if (upcData != null)
						{
							if (null != upcData.getInventoryMultiplier() && upcData.getInventoryMultiplier() > 1)
							{
								throw new RuntimeException("Base UOM can't be greater than 1");
							}
							upcData.setBaseUPC(true);
							modelService.setAttributeValue(model, "vendorPartNumber", upcData.getVendorPartNumber());
							modelService.setAttributeValue(model, "inventoryUPCID", upcData.getInventoryUPCID());
						}
						else
						{
							throw new RuntimeException("There is no upcData with baseUPCCode");
						}
						newUPCDataList.stream().forEach(newupcData -> {
							newupcData.setFromPayload(false);
						});
						modelService.setAttributeValue(model, attribute.getQualifier(), newUPCDataList);
					}
				}

				if (((ItemModel) model).getItemtype().equalsIgnoreCase("Product")
						&& attribute.getAttributeName().equalsIgnoreCase("superCategoryClassPath"))
				{
					LOG.info("siteonestandardattributevaluesetter superCategoryClassPath " + value.toString());
					//ProductUtil.setClassName(String.class);
					productUtilObj.setClassPath(value.toString());
					final List<CategoryModel> categoryModel = new ArrayList<>();
					LOG.info("SiteOneStandardAttributeValueSetter before retrievecode");
					retrieveCodes = value.toString();
					final List<String> codes = productUtilObj.retrieveCode(value.toString(), "siteoneProductCatalog");
					if (null != codes)
					{
						for (final String categoryCode : codes)
						{
							final CategoryModel category = categoryService.getCategoryForCode(
									catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online"), categoryCode);
							LOG.info("siteonestandardattributevaluesetter superCategoryClassPath before nullcheck");
							if (category != null)
							{
								LOG.info("siteonestandardattributevaluesetter superCategoryClassPath #1");
								categoryModel.add(category);
							}
						}
					}
					modelService.setAttributeValue(model, "supercategories", categoryModel);
				}
				else if (((ItemModel) model).getItemtype().equalsIgnoreCase("GenericVariantProduct")
						&& attribute.getAttributeName().equalsIgnoreCase("baseProductCode"))
				{
					final ProductModel baseProduct = productService.getProductForCode(
							catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online"), value.toString());
					modelService.setAttributeValue(model, "baseProduct", baseProduct);
				}
				else if (attribute.getAttributeName().equalsIgnoreCase("specificationAttribute"))
				{
					LOG.info("###check catalogVersion###");
					LOG.info("Product Catalog Version after supercategories: #1 : "
							+ ((ProductModel) model).getCatalogVersion().getCatalog().getId());
					if (((ProductModel) model).getCatalogVersion().getCatalog().getId().equalsIgnoreCase("siteoneCAProductCatalog"))
					{
						final List<String> codes = ((ProductModel) model).getSupercategories().stream().map(CategoryModel::getCode)
								.collect(Collectors.toList());
						final List<CategoryModel> categoryModel = new ArrayList<>();
						for (final String categoryCode : codes)
						{
							final CategoryModel category = categoryService.getCategoryForCode(
									catalogVersionService.getCatalogVersion("siteoneCAProductCatalog", "Online"), categoryCode);
							LOG.info("siteonestandardattributevaluesetter superCategoryClassPath before nullcheck");
							if (category != null)
							{
								LOG.info("siteonestandardattributevaluesetter superCategoryClassPath #1");
								categoryModel.add(category);
							}
						}
						((ProductModel) model).setSupercategories(new ArrayList());
						modelService.setAttributeValue(model, "supercategories", CollectionUtils.EMPTY_COLLECTION);
						modelService.setAttributeValue(model, "supercategories", categoryModel);
					}
					final String productKind = modelService.getAttributeValue(model, "productKind");
					//productUtilObj.setClassName(String.class);
					final List<SiteOneProductSpecificationAttributeModel> list = new ArrayList();
					list.addAll((Collection<? extends SiteOneProductSpecificationAttributeModel>) value);

					final StringBuilder variantAttributes = new StringBuilder();

					String variantCategoryattrList = null;
					if (productKind.equalsIgnoreCase("Variant"))
					{
						final ProductModel baseProduct = modelService.getAttributeValue(model, "baseProduct");
						variantCategoryattrList = baseProduct.getSupercategories().stream()
								.filter(category -> category.getCode().contains("PH")).findFirst().get().getVariantCategoryList();
					}
					if (productKind.equalsIgnoreCase("Base"))
					{
						final List<CategoryModel> categoryModel = modelService.getAttributeValue(model, "supercategories");
						variantCategoryattrList = categoryModel.stream().filter(category -> category.getCode().contains("PH"))
								.findFirst().get().getVariantCategoryList();
					}

					for (final SiteOneProductSpecificationAttributeModel attr : list)
					{
						final String attrValue = attr.getText();
						final String code = attr.getName();
						if (null != variantCategoryattrList && (variantCategoryattrList.toLowerCase()).contains(code.toLowerCase()))
						{
							variantAttributes.append(code).append("@");
						}

						if (code.contains("Maintenance_Item") || code.contains("hrdscpItem") || code.contains("irgItem")
								|| code.contains("lndscpItem") || code.contains("lghtItem") || code.contains("nrsryItem"))
						{
							modelService.setAttributeValue(model, "specificationItem", attrValue);
						}
						else if (code.contains("Maintenance_Type") || code.contains("hrdscpType") || code.contains("irgType")
								|| code.contains("lndscpType") || code.contains("lghtType") || code.contains("nrsryType"))
						{
							modelService.setAttributeValue(model, "specificationType", attrValue);
						}
						else if (code.contains("Maintenance_Series") || code.contains("hrdscpSeries") || code.contains("irgSeries")
								|| code.contains("lndscpSeries"))
						{
							modelService.setAttributeValue(model, "specificationSeries", attrValue);
						}

						if (code.contains("ProductBrandName"))
						{
							//ProductUtil.setClassName(BrandNameEnum.class);
							final BrandNameEnum productBrandName = (BrandNameEnum) productUtilObj.getBrandCode(attrValue);
							if (productBrandName != null)
							{
								modelService.setAttributeValue(model, "productBrandName", productBrandName);
							}
							else
							{
								if (StringUtils.isNotEmpty(attrValue) && (!attrValue.equalsIgnoreCase(" ")))
								{
									productUtilObj.setBrandEnum(attrValue);
									modelService.setAttributeValue(model, "productBrandName", productUtilObj.getBrandCode(attrValue));
								}
								else
								{
									modelService.setAttributeValue(model, "productBrandName",
											productUtilObj.getBrandCode("Coreproductbrandnamenull"));
								}
							}

						}
						else if (code.contains("SubBrand"))
						{
							modelService.setAttributeValue(model, "subBrand", attrValue);
						}
						else if (StringUtils.isNotEmpty(code))
						{

							((ProductModel) model).getClassificationClasses().forEach(classes -> {
								for (final ClassAttributeAssignmentModel assignmentModel : classes
										.getAllClassificationAttributeAssignments())
								{
									if (assignmentModel.getClassificationAttribute() != null
											&& assignmentModel.getClassificationAttribute().getCode().equalsIgnoreCase(code))
									{
										LOG.info("siteonestandardattributevaluesetter specificationAttribute #1");
										productUtilObj.setProductFeature((ProductModel) model, assignmentModel, code, modelService,
												attrValue);
										break;
									}
								}
							});
						}

					}

					if (((ItemModel) model).getItemtype().equalsIgnoreCase("GenericVariantProduct"))
					{
						parseVariantValues(model, list);
					}
					else if (productKind.equalsIgnoreCase("Base"))
					{
						parseVariantAttributes(model, variantAttributes.deleteCharAt(variantAttributes.length() - 1));
					}
					if (null != ((ProductModel) model).getFeatures())
					{
						modelService.saveAll(((ProductModel) model).getFeatures());
						modelService.setAttributeValue(model, "features", ((ProductModel) model).getFeatures());
					}
					else
					{
						modelService.setAttributeValue(model, "specificationAttribute",
								new ArrayList<SiteOneProductSpecificationAttributeModel>());
					}

					LOG.error("VariantAttributes :" + variantAttributes.toString());
				}
				else if (attribute.getAttributeName().equalsIgnoreCase("shortDescription"))
				{
					final String shortTexts = siteOneFeatureSwitchCacheService.getValueForSwitch("PrdShortDescToBeRemoved");
					final String[] splitTexts = shortTexts.split(Pattern.quote("|"));
					String shortDesc = value.toString();
					for (final String splitText : splitTexts)
					{
						if (shortDesc.contains(splitText))
						{
							shortDesc = shortDesc.replace(splitText, "").trim();
						}
					}
					modelService.setAttributeValue(model, "productShortDesc", shortDesc);

				}
				else if (attribute.getAttributeName().equalsIgnoreCase("itemImage"))
				{
					final String productCode = modelService.getAttributeValue(model, "code");
					//productUtilObj.setClassName(String.class);
					final List<SiteOneProductItemImagesModel> list = new ArrayList();
					list.addAll((Collection<? extends SiteOneProductItemImagesModel>) value);
					List<MediaContainerModel> mediaContainerModelList = modelService.getAttributeValue(model, "galleryImages");
					if (mediaContainerModelList != null && !mediaContainerModelList.isEmpty())
					{
						for (final MediaContainerModel medicontainer : mediaContainerModelList)
						{
							LOG.error("Delete gallery images for code " + productCode + " and mediacontainer " + medicontainer);
							modelService.removeAll(medicontainer.getMedias());
						}
						modelService.removeAll(mediaContainerModelList);
					}

					mediaContainerModelList = new ArrayList();
					final List<MediaFormatModel> mediaFormatList = productUtilObj.retrieveMediaFormat();
					final MediaFolderModel mediaFolderModel = productUtilObj.retrieveMediaFolder();
					boolean isPictureSet = false;
					for (final SiteOneProductItemImagesModel itemImage : list)
					{
						final List<MediaModel> mediaModelList = new ArrayList();
						final SiteOneProductImageModel imageModel = itemImage.getImage();
						LOG.error("Gallery images for itemImage " + imageModel.getCode());
						final String id = imageModel.getId();
						final String fullPath = imageModel.getFullpath();
						final String mimeType = imageModel.getMimetype();
						final String priority = imageModel.getPriority().toString();
						final String videoId = imageModel.getVideoId();
						final String code = (modelService.getAttributeValue(model, "code")).toString().concat("_").concat(priority);
						if (videoId != null)
						{
							final MediaModel youtubeMedia = createMediaModel(mediaFolderModel, code, fullPath, mimeType, videoId,
									"youtube", mediaFormatList, null, ((ProductModel) model).getCatalogVersion());
							mediaModelList.add(youtubeMedia);
						}
						final MediaModel mainMedia = createMediaModel(mediaFolderModel, code, fullPath, mimeType, videoId, null, null,
								null, ((ProductModel) model).getCatalogVersion());
						mediaModelList.add(mainMedia);
						if (!isPictureSet)
						{
							modelService.setAttributeValue(model, "picture", mainMedia);
							isPictureSet = true;
						}
						final List<SiteOneProductTransformationModel> list_1 = new ArrayList();
						list_1.addAll(imageModel.getTransformations());
						for (final SiteOneProductTransformationModel attr : list_1)
						{
							final String url = attr.getUrl();
							final String format = attr.getFormat();
							final MediaModel medias = createMediaModel(mediaFolderModel, code, url, mimeType, videoId, format,
									mediaFormatList, null, ((ProductModel) model).getCatalogVersion());
							mediaModelList.add(medias);
						}
						final MediaContainerModel mediaContainer = new MediaContainerModel();
						final String mediaContainerCode = (modelService.getAttributeValue(model, "code")).toString().concat("_")
								.concat(priority);
						mediaContainer.setMedias(mediaModelList);
						mediaContainer.setQualifier(mediaContainerCode);
						if (((ProductModel) model).getCatalogVersion().getCatalog().getId().equalsIgnoreCase("siteoneCAProductCatalog"))
						{
							mediaContainer
									.setCatalogVersion(catalogVersionService.getCatalogVersion("siteoneCAProductCatalog", "Online"));
						}
						else
						{
							mediaContainer.setCatalogVersion(catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online"));
						}
						mediaContainerModelList.add(mediaContainer);
					}
					modelService.setAttributeValue(model, "itemImage", new ArrayList<SiteOneProductItemImagesModel>());
					modelService.setAttributeValue(model, "galleryImages", mediaContainerModelList);
				}
				else if (attribute.getAttributeName().equalsIgnoreCase("brandLogo"))
				{
					final String productCode = modelService.getAttributeValue(model, "code");
					//productUtilObj.setClassName(String.class);
					final List<SiteOneProductItemImagesModel> list = new ArrayList();
					list.addAll((Collection<? extends SiteOneProductItemImagesModel>) value);
					List<MediaContainerModel> mediaContainerModelList = modelService.getAttributeValue(model, "brandLogos");
					if (mediaContainerModelList != null && !mediaContainerModelList.isEmpty())
					{
						for (final MediaContainerModel medicontainer : mediaContainerModelList)
						{
							modelService.removeAll(medicontainer.getMedias());
						}
						modelService.removeAll(mediaContainerModelList);
					}
					mediaContainerModelList = new ArrayList();
					final List<MediaFormatModel> mediaFormatList = productUtilObj.retrieveMediaFormat();
					final MediaFolderModel mediaFolderModel = productUtilObj.retrieveMediaFolder();
					for (final SiteOneProductItemImagesModel itemImage : list)
					{
						final List<MediaModel> mediaModelList = new ArrayList();
						final SiteOneProductImageModel imageModel = itemImage.getImage();
						final String id = imageModel.getId();
						final String fullPath = imageModel.getFullpath();
						final String mimeType = imageModel.getMimetype();
						final String priority = imageModel.getPriority().toString();
						final String videoId = imageModel.getVideoId();
						final String code = (modelService.getAttributeValue(model, "code")).toString().concat("_")
								.concat(String.valueOf(priority)).concat("_").concat("501");
						if (videoId != null)
						{
							final MediaModel youtubeMedia = createMediaModel(mediaFolderModel, code, fullPath, mimeType, videoId,
									"youtube", mediaFormatList, null, ((ProductModel) model).getCatalogVersion());
							mediaModelList.add(youtubeMedia);
						}
						final MediaModel mainMedia = createMediaModel(mediaFolderModel, code, fullPath, mimeType, videoId, null, null,
								null, ((ProductModel) model).getCatalogVersion());
						mediaModelList.add(mainMedia);
						final List<SiteOneProductTransformationModel> list_1 = new ArrayList();
						list_1.addAll(imageModel.getTransformations());
						for (final SiteOneProductTransformationModel attr : list_1)
						{
							final String url = attr.getUrl();
							final String format = attr.getFormat();
							final MediaModel medias = createMediaModel(mediaFolderModel, code, url, mimeType, videoId, format,
									mediaFormatList, null, ((ProductModel) model).getCatalogVersion());
							mediaModelList.add(medias);
						}
						final MediaContainerModel mediaContainer = new MediaContainerModel();
						final String mediaContainerCode = (modelService.getAttributeValue(model, "code")).toString().concat("_")
								.concat(String.valueOf(priority)).concat("_").concat("502");
						mediaContainer.setMedias(mediaModelList);
						mediaContainer.setQualifier(mediaContainerCode);
						if (((ProductModel) model).getCatalogVersion().getCatalog().getId().equalsIgnoreCase("siteoneCAProductCatalog"))
						{
							mediaContainer
									.setCatalogVersion(catalogVersionService.getCatalogVersion("siteoneCAProductCatalog", "Online"));
						}
						else
						{
							mediaContainer.setCatalogVersion(catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online"));
						}
						mediaContainerModelList.add(mediaContainer);
					}
					modelService.setAttributeValue(model, "brandLogo", new ArrayList<SiteOneProductItemImagesModel>());
					modelService.setAttributeValue(model, "brandLogos", mediaContainerModelList);

				}
				else if (attribute.getAttributeName().equalsIgnoreCase("lifeStyle"))
				{
					final String productCode = modelService.getAttributeValue(model, "code");
					//productUtilObj.setClassName(String.class);
					final List<SiteOneProductItemImagesModel> list = new ArrayList();
					list.addAll((Collection<? extends SiteOneProductItemImagesModel>) value);
					List<MediaContainerModel> mediaContainerModelList = modelService.getAttributeValue(model, "lifeStyles");
					if (mediaContainerModelList != null && !mediaContainerModelList.isEmpty())
					{
						for (final MediaContainerModel medicontainer : mediaContainerModelList)
						{
							modelService.removeAll(medicontainer.getMedias());
						}
						modelService.removeAll(mediaContainerModelList);
					}
					mediaContainerModelList = new ArrayList();
					final List<MediaFormatModel> mediaFormatList = productUtilObj.retrieveMediaFormat();
					final MediaFolderModel mediaFolderModel = productUtilObj.retrieveMediaFolder();
					for (final SiteOneProductItemImagesModel itemImage : list)
					{
						final List<MediaModel> mediaModelList = new ArrayList();
						final SiteOneProductImageModel imageModel = itemImage.getImage();
						final String id = imageModel.getId();
						final String fullPath = imageModel.getFullpath();
						final String mimeType = imageModel.getMimetype();
						final String priority = imageModel.getPriority().toString();
						final String videoId = imageModel.getVideoId();
						final String code = (modelService.getAttributeValue(model, "code")).toString().concat("_")
								.concat(String.valueOf(priority)).concat("_").concat("601");
						if (videoId != null)
						{
							final MediaModel youtubeMedia = createMediaModel(mediaFolderModel, code, fullPath, mimeType, videoId,
									"youtube", mediaFormatList, null, ((ProductModel) model).getCatalogVersion());
							mediaModelList.add(youtubeMedia);
						}
						final MediaModel mainMedia = createMediaModel(mediaFolderModel, code, fullPath, mimeType, videoId, null, null,
								null, ((ProductModel) model).getCatalogVersion());
						mediaModelList.add(mainMedia);
						final List<SiteOneProductTransformationModel> list_1 = new ArrayList();
						list_1.addAll(imageModel.getTransformations());
						for (final SiteOneProductTransformationModel attr : list_1)
						{
							final String url = attr.getUrl();
							final String format = attr.getFormat();
							final MediaModel medias = createMediaModel(mediaFolderModel, code, url, mimeType, videoId, format,
									mediaFormatList, null, ((ProductModel) model).getCatalogVersion());
							mediaModelList.add(medias);
						}
						final MediaContainerModel mediaContainer = new MediaContainerModel();
						final String mediaContainerCode = (modelService.getAttributeValue(model, "code")).toString().concat("_")
								.concat(String.valueOf(priority)).concat("_").concat("602");
						mediaContainer.setMedias(mediaModelList);
						mediaContainer.setQualifier(mediaContainerCode);
						if (((ProductModel) model).getCatalogVersion().getCatalog().getId().equalsIgnoreCase("siteoneCAProductCatalog"))
						{
							mediaContainer
									.setCatalogVersion(catalogVersionService.getCatalogVersion("siteoneCAProductCatalog", "Online"));
						}
						else
						{
							mediaContainer.setCatalogVersion(catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online"));
						}
						mediaContainerModelList.add(mediaContainer);
					}
					modelService.setAttributeValue(model, "lifeStyle", new ArrayList<SiteOneProductItemImagesModel>());
					modelService.setAttributeValue(model, "lifeStyles", mediaContainerModelList);

				}

				else if (attribute.getAttributeName().equalsIgnoreCase("swatchImage"))
				{
					final String productCode = modelService.getAttributeValue(model, "code");
					//productUtilObj.setClassName(String.class);
					final List<SiteOneProductItemImagesModel> list = new ArrayList();
					list.addAll((Collection<? extends SiteOneProductItemImagesModel>) value);
					List<MediaContainerModel> mediaContainerModelList = modelService.getAttributeValue(model, "swatchImages");
					if (mediaContainerModelList != null && !mediaContainerModelList.isEmpty())
					{
						for (final MediaContainerModel medicontainer : mediaContainerModelList)
						{
							modelService.removeAll(medicontainer.getMedias());
						}
						modelService.removeAll(mediaContainerModelList);
					}
					mediaContainerModelList = new ArrayList();
					final List<MediaFormatModel> mediaFormatList = productUtilObj.retrieveMediaFormat();
					final MediaFolderModel mediaFolderModel = productUtilObj.retrieveMediaFolder();
					for (final SiteOneProductItemImagesModel itemImage : list)
					{
						final List<MediaModel> mediaModelList = new ArrayList();
						final SiteOneProductImageModel imageModel = itemImage.getImage();
						final String id = imageModel.getId();
						final String fullPath = imageModel.getFullpath();
						final String mimeType = imageModel.getMimetype();
						final String priority = imageModel.getPriority().toString();
						final String videoId = imageModel.getVideoId();
						final String code = (modelService.getAttributeValue(model, "code")).toString().concat("_")
								.concat(String.valueOf(priority)).concat("_").concat("701");
						if (videoId != null)
						{
							final MediaModel youtubeMedia = createMediaModel(mediaFolderModel, code, fullPath, mimeType, videoId,
									"youtube", mediaFormatList, null, ((ProductModel) model).getCatalogVersion());
							mediaModelList.add(youtubeMedia);
						}
						final MediaModel mainMedia = createMediaModel(mediaFolderModel, code, fullPath, mimeType, videoId, null, null,
								null, ((ProductModel) model).getCatalogVersion());
						mediaModelList.add(mainMedia);
						final List<SiteOneProductTransformationModel> list_1 = new ArrayList();
						list_1.addAll(imageModel.getTransformations());
						for (final SiteOneProductTransformationModel attr : list_1)
						{
							final String url = attr.getUrl();
							final String format = attr.getFormat();
							final MediaModel medias = createMediaModel(mediaFolderModel, code, url, mimeType, videoId, format,
									mediaFormatList, null, ((ProductModel) model).getCatalogVersion());
							mediaModelList.add(medias);
						}
						final MediaContainerModel mediaContainer = new MediaContainerModel();
						final String mediaContainerCode = (modelService.getAttributeValue(model, "code")).toString().concat("_")
								.concat(String.valueOf(priority)).concat("_").concat("702");
						mediaContainer.setMedias(mediaModelList);
						mediaContainer.setQualifier(mediaContainerCode);
						if (((ProductModel) model).getCatalogVersion().getCatalog().getId().equalsIgnoreCase("siteoneCAProductCatalog"))
						{
							mediaContainer
									.setCatalogVersion(catalogVersionService.getCatalogVersion("siteoneCAProductCatalog", "Online"));
						}
						else
						{
							mediaContainer.setCatalogVersion(catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online"));
						}
						mediaContainerModelList.add(mediaContainer);
					}
					modelService.setAttributeValue(model, "swatchImage", new ArrayList<SiteOneProductItemImagesModel>());
					modelService.setAttributeValue(model, "swatchImages", mediaContainerModelList);

				}
				else if (attribute.getAttributeName().equalsIgnoreCase("specialImageType"))
				{
					final String productCode = modelService.getAttributeValue(model, "code");
					//productUtilObj.setClassName(String.class);
					final List<SiteOneProductItemImagesModel> list = new ArrayList();
					list.addAll((Collection<? extends SiteOneProductItemImagesModel>) value);
					List<MediaContainerModel> mediaContainerModelList = modelService.getAttributeValue(model, "specialImageTypes");
					if (mediaContainerModelList != null && !mediaContainerModelList.isEmpty())
					{
						for (final MediaContainerModel medicontainer : mediaContainerModelList)
						{
							modelService.removeAll(medicontainer.getMedias());
						}
						modelService.removeAll(mediaContainerModelList);
					}
					mediaContainerModelList = new ArrayList();
					final List<MediaFormatModel> mediaFormatList = productUtilObj.retrieveMediaFormat();
					final MediaFolderModel mediaFolderModel = productUtilObj.retrieveMediaFolder();
					for (final SiteOneProductItemImagesModel itemImage : list)
					{
						final List<MediaModel> mediaModelList = new ArrayList();
						final SiteOneProductImageModel imageModel = itemImage.getImage();
						final String id = imageModel.getId();
						final String fullPath = imageModel.getFullpath();
						final String mimeType = imageModel.getMimetype();
						final String priority = imageModel.getPriority().toString();
						final String videoId = imageModel.getVideoId();
						final String code = (modelService.getAttributeValue(model, "code")).toString().concat("_")
								.concat(String.valueOf(priority)).concat("_").concat("801");
						if (videoId != null)
						{
							final MediaModel youtubeMedia = createMediaModel(mediaFolderModel, code, fullPath, mimeType, videoId,
									"youtube", mediaFormatList, null, ((ProductModel) model).getCatalogVersion());
							mediaModelList.add(youtubeMedia);
						}
						final MediaModel mainMedia = createMediaModel(mediaFolderModel, code, fullPath, mimeType, videoId, null, null,
								null, ((ProductModel) model).getCatalogVersion());
						mediaModelList.add(mainMedia);
						if (imageModel.getTransformations() != null)
						{
							final List<SiteOneProductTransformationModel> list_1 = new ArrayList();
							list_1.addAll(imageModel.getTransformations());
							for (final SiteOneProductTransformationModel attr : list_1)
							{
								final String url = attr.getUrl();
								final String format = attr.getFormat();
								final MediaModel medias = createMediaModel(mediaFolderModel, code, url, mimeType, videoId, format,
										mediaFormatList, null, ((ProductModel) model).getCatalogVersion());
								mediaModelList.add(medias);
							}
						}
						final MediaContainerModel mediaContainer = new MediaContainerModel();
						final String mediaContainerCode = (modelService.getAttributeValue(model, "code")).toString().concat("_")
								.concat(String.valueOf(priority)).concat("_").concat("802");
						mediaContainer.setMedias(mediaModelList);
						mediaContainer.setQualifier(mediaContainerCode);
						if (((ProductModel) model).getCatalogVersion().getCatalog().getId().equalsIgnoreCase("siteoneCAProductCatalog"))
						{
							mediaContainer
									.setCatalogVersion(catalogVersionService.getCatalogVersion("siteoneCAProductCatalog", "Online"));
						}
						else
						{
							mediaContainer.setCatalogVersion(catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online"));
						}
						mediaContainerModelList.add(mediaContainer);
					}
					modelService.setAttributeValue(model, "specialImageType", new ArrayList<SiteOneProductItemImagesModel>());
					modelService.setAttributeValue(model, "specialImageTypes", mediaContainerModelList);

				}

				else if (attribute.getAttributeName().equalsIgnoreCase("sheets"))
				{
					LOG.info("Data sheet entry 1");
					final String productCode = modelService.getAttributeValue(model, "code");
					//ProductUtil.setClassName(String.class);
					final SiteOneDataSheetModel list = (SiteOneDataSheetModel) value;
					final Collection<MediaModel> mediaModelList = modelService.getAttributeValue(model, "data_sheet");
					final Collection<MediaModel> setThisMediaModelList = new ArrayList();
					if (mediaModelList != null && !mediaModelList.isEmpty())
					{
						modelService.removeAll(mediaModelList);
					}
					final MediaFolderModel mediaFolderModel = productUtilObj.retrieveMediaFolder();
					if (CollectionUtils.isNotEmpty(list.getSpecificationSheet()))
					{
						for (final SiteOneProductSpecificationSheetModel specSheet : list.getSpecificationSheet())
						{
							LOG.info("Data sheet entry - Specification Sheet");
							final String fullPath = specSheet.getFullpath();
							final String mimeType = specSheet.getMimetype();
							final String priority = specSheet.getPriority();
							final String code = (modelService.getAttributeValue(model, "code")).toString().concat("_")
									.concat(String.valueOf(priority)).concat("_").concat("204");
							final MediaModel mediaModel = createMediaModel(mediaFolderModel, code, fullPath, mimeType, null, null, null,
									"Product Information Sheet", ((ProductModel) model).getCatalogVersion());
							setThisMediaModelList.add(mediaModel);
						}
					}
					if (CollectionUtils.isNotEmpty(list.getBrochures()))
					{
						for (final SiteOneBrochuresModel Brochures : list.getBrochures())
						{
							LOG.info("Data sheet entry - Brochures");
							final String fullPath = Brochures.getFullpath();
							final String mimeType = Brochures.getMimetype();
							final String priority = Brochures.getPriority();
							final String code = (modelService.getAttributeValue(model, "code")).toString().concat("_")
									.concat(String.valueOf(priority)).concat("_").concat("210");
							final MediaModel mediaModel = createMediaModel(mediaFolderModel, code, fullPath, mimeType, null, null, null,
									BROCHURES, ((ProductModel) model).getCatalogVersion());
							setThisMediaModelList.add(mediaModel);
						}
					}

					if (CollectionUtils.isNotEmpty(list.getSafetyDataSheet()))
					{
						for (final SiteOneProductSafetyDataSheetModel sdSheet : list.getSafetyDataSheet())
						{
							LOG.info("Data sheet entry - Safety Data Sheet");
							final String fullPath = sdSheet.getFullpath();
							final String mimeType = sdSheet.getMimetype();
							final String priority = sdSheet.getPriority();
							final String code = (modelService.getAttributeValue(model, "code")).toString().concat("_")
									.concat(String.valueOf(priority)).concat("_").concat("200");
							final MediaModel mediaModel = createMediaModel(mediaFolderModel, code, fullPath, mimeType, null, null, null,
									"SDS", ((ProductModel) model).getCatalogVersion());
							setThisMediaModelList.add(mediaModel);
						}
					}
					if (CollectionUtils.isNotEmpty(list.getLabelAsset()))
					{
						for (final SiteOneProductLabelSheetModel sdSheet : list.getLabelAsset())
						{
							LOG.info("Data sheet entry - Label Sheet");
							final String fullPath = sdSheet.getFullpath();
							final String mimeType = sdSheet.getMimetype();
							final String priority = sdSheet.getPriority();
							final String code = (modelService.getAttributeValue(model, "code")).toString().concat("_")
									.concat(String.valueOf(priority)).concat("_").concat("201");
							final MediaModel mediaModel = createMediaModel(mediaFolderModel, code, fullPath, mimeType, null, null, null,
									"Label", ((ProductModel) model).getCatalogVersion());
							setThisMediaModelList.add(mediaModel);
						}
					}
					if (CollectionUtils.isNotEmpty(list.getOwnersManual()))
					{
						for (final SiteOneOwnersManualModel ownersManual : list.getOwnersManual())
						{
							LOG.info("Data sheet entry - Owners Manual");
							final String fullPath = ownersManual.getFullpath();
							final String mimeType = ownersManual.getMimetype();
							final String priority = ownersManual.getPriority();
							final String code = (modelService.getAttributeValue(model, "code")).toString().concat("_")
									.concat(String.valueOf(priority)).concat("_").concat("205");
							final MediaModel mediaModel = createMediaModel(mediaFolderModel, code, fullPath, mimeType, null, null, null,
									OWNERS_MANUAL, ((ProductModel) model).getCatalogVersion());
							setThisMediaModelList.add(mediaModel);
						}
					}
					if (CollectionUtils.isNotEmpty(list.getInstallationInstructions()))
					{
						for (final SiteOneInstallationInstructionsModel installationInstructions : list.getInstallationInstructions())
						{
							LOG.info("Data sheet entry - Installation Instructions");
							final String fullPath = installationInstructions.getFullpath();
							final String mimeType = installationInstructions.getMimetype();
							final String priority = installationInstructions.getPriority();
							final String code = (modelService.getAttributeValue(model, "code")).toString().concat("_")
									.concat(String.valueOf(priority)).concat("_").concat("206");
							final MediaModel mediaModel = createMediaModel(mediaFolderModel, code, fullPath, mimeType, null, null, null,
									INSTALLATION_INSTRUCTION, ((ProductModel) model).getCatalogVersion());
							setThisMediaModelList.add(mediaModel);
						}
					}
					if (CollectionUtils.isNotEmpty(list.getTechnicalDrawing()))
					{
						for (final SiteOneTechnicalDrawingModel technicalDrawing : list.getTechnicalDrawing())
						{
							LOG.info("Data sheet entry - Technical Drawing");
							final String fullPath = technicalDrawing.getFullpath();
							final String mimeType = technicalDrawing.getMimetype();
							final String priority = technicalDrawing.getPriority();
							final String code = (modelService.getAttributeValue(model, "code")).toString().concat("_")
									.concat(String.valueOf(priority)).concat("_").concat("207");
							final MediaModel mediaModel = createMediaModel(mediaFolderModel, code, fullPath, mimeType, null, null, null,
									TECHNICAL_DRAWING, ((ProductModel) model).getCatalogVersion());
							setThisMediaModelList.add(mediaModel);
						}
					}
					if (CollectionUtils.isNotEmpty(list.getPartsDiagram()))
					{
						for (final SiteOnePartsDiagramModel partsDiagram : list.getPartsDiagram())
						{
							LOG.info("Data sheet entry - Parts Diagram");
							final String fullPath = partsDiagram.getFullpath();
							final String mimeType = partsDiagram.getMimetype();
							final String priority = partsDiagram.getPriority();
							final String code = (modelService.getAttributeValue(model, "code")).toString().concat("_")
									.concat(String.valueOf(priority)).concat("_").concat("208");
							final MediaModel mediaModel = createMediaModel(mediaFolderModel, code, fullPath, mimeType, null, null, null,
									PARTS_DIAGRAM, ((ProductModel) model).getCatalogVersion());
							setThisMediaModelList.add(mediaModel);
						}
					}
					if (CollectionUtils.isNotEmpty(list.getWarrantyInformation()))
					{
						for (final SiteOneWarrantyInformationModel warrantyInformation : list.getWarrantyInformation())
						{
							LOG.info("Data sheet entry - Warranty Information");
							final String fullPath = warrantyInformation.getFullpath();
							final String mimeType = warrantyInformation.getMimetype();
							final String priority = warrantyInformation.getPriority();
							final String code = (modelService.getAttributeValue(model, "code")).toString().concat("_")
									.concat(String.valueOf(priority)).concat("_").concat("209");
							final MediaModel mediaModel = createMediaModel(mediaFolderModel, code, fullPath, mimeType, null, null, null,
									WARRANTY_INFO, ((ProductModel) model).getCatalogVersion());
							setThisMediaModelList.add(mediaModel);
						}
					}
					modelService.setAttributeValue(model, "sheets", new SiteOneDataSheetModel());
					modelService.setAttributeValue(model, "data_sheet", setThisMediaModelList);
				}
				else if (attribute.getAttributeName().equalsIgnoreCase("productKind"))
				{
					modelService.setAttributeValue(model, "productKind", value.toString());
				}
				else if (attribute.getAttributeName().equalsIgnoreCase("online_status"))
				{
					modelService.setAttributeValue(model, "secretSku", false);
					modelService.setAttributeValue(model, "regionallyAssorted", false);
					final String onlineStatus = value.toString();
					if ("Phantom SKU".equalsIgnoreCase(onlineStatus))
					{
						modelService.setAttributeValue(model, "secretSku", true);
					}
					if ("Regional".equalsIgnoreCase(onlineStatus))
					{
						modelService.setAttributeValue(model, "regionallyAssorted", true);
					}
				}
				else if (attribute.getAttributeName().equalsIgnoreCase("savingsCenterAttribute"))
				{
					final List<SiteOneProductSavingsCenterAttributeModel> list = new ArrayList();
					list.addAll((Collection<? extends SiteOneProductSavingsCenterAttributeModel>) value);
					List<SiteOneProductSavingsCenterAttributeModel> existingItemsList = modelService.getAttributeValue(model, "savingsCenterAttribute");
					if (existingItemsList != null && !existingItemsList.isEmpty())
					{
						modelService.removeAll(existingItemsList);
					}
					modelService.setAttributeValue(model, "savingsCenterAttribute", list);
				}
				else if (isPropertyChanged(model, value))
				{
					modelService.setAttributeValue(model, attribute.getQualifier(), value);
				}
			}
		}
	}



	private String getFinalURL(final String urlPath, final String productCode, final String priority, final String format)
	{
		String imageCode = "";
		if (format != null)
		{
			imageCode = productCode.concat("-").concat(priority).concat("-").concat(format);
		}
		else
		{
			imageCode = productCode.concat("-").concat(priority);
		}
		final String mediaFinalUrl = "/medias/sys_master/PimProductImages/".concat(urlPath.substring(0, urlPath.lastIndexOf(".")))
				.concat("/").concat(imageCode).concat(urlPath.substring(urlPath.lastIndexOf(".")));

		return mediaFinalUrl;
	}

	private boolean isPropertyChanged(final Object model, final Object value)
	{
		if (attribute.isReadable())
		{
			final Object attributeValue = modelService.getAttributeValue(model, attribute.getQualifier());
			return !(Objects.equals(attributeValue, value));
		}
		return true;
	}

	public void parseVariantAttributes(final Object model, final StringBuilder value)
	{
		if (((ItemModel) model).getItemtype().equalsIgnoreCase("Product"))
		{
			LOG.info("siteonestandardattributevaluesetter parseVariantAttributes #2");
			//ProductUtil.setClassName(VariantCategoryModel.class);
			final List<VariantCategoryModel> variantCategories = productUtilObj.validateVariantAttributeCodePcm(value.toString());
			final StringBuilder internalvariantattribute = new StringBuilder();
			variantCategories.stream().forEach(categoryModel -> {
				if (categoryModel instanceof VariantCategoryModel)
				{
					internalvariantattribute.append(categoryModel.getCode()).append(",");
				}
			});
			if (StringUtils.isNotEmpty(internalvariantattribute.toString()))
			{
				LOG.info("siteonestandardattributevaluesetter parseVariantAttributes #3");
				internalvariantattribute.deleteCharAt(internalvariantattribute.length() - 1);
				modelService.setAttributeValue(model, "internalVariantAttribute", internalvariantattribute.toString());
				variantCategories.addAll(modelService.getAttributeValue(model, "supercategories"));
				final VariantTypeModel variantType = variantsService.getVariantTypeForCode("GenericVariantProduct");
				modelService.setAttributeValue(model, "variantType", variantType);
				modelService.setAttributeValue(model, "supercategories", variantCategories);
			}
		}
	}

	public void parseVariantValues(final Object model, final List<SiteOneProductSpecificationAttributeModel> list)
	{
		if (((ItemModel) model).getItemtype().equalsIgnoreCase("GenericVariantProduct"))
		{
			//ProductUtil.setClassName(VariantValueCategoryModel.class);

			final ProductModel baseProduct = modelService.getAttributeValue(model, "baseProduct");
			if (baseProduct != null)
			{
				String[] superCategories = null;
				if (baseProduct.getInternalVariantAttribute() != null
						&& !StringUtils.isBlank(baseProduct.getInternalVariantAttribute()))
				{
					superCategories = baseProduct.getInternalVariantAttribute().split(",");
				}
				final StringBuilder variantValues = new StringBuilder();

				for (final String category : superCategories)
				{
					list.stream().forEach(attr -> {
						if (category.equalsIgnoreCase(attr.getName()) && StringUtils.isNotEmpty(attr.getText()))
						{
							variantValues.append(attr.getText()).append("@");
						}
					});
				}
				LOG.error("VariantValues :" + variantValues.toString());
				final List<VariantValueCategoryModel> variantCategories = productUtilObj.validateVariantValueCodePcm(superCategories,
						variantValues.deleteCharAt(variantValues.length() - 1).toString(), baseProduct);
				modelService.saveAll(variantCategories);
				modelService.setAttributeValue(model, "supercategories", variantCategories);
			}
		}
	}

	private MediaModel createMediaModel(final MediaFolderModel mediaFolderModel, final String code, final String fullPath,
			final String mimeType, final String videoId, final String format, final List<MediaFormatModel> mediaFormats,
			final String sheetType, final CatalogVersionModel catalogversion)
	{
		final MediaModel mediaModel = modelService.create(MediaModel.class);
		mediaModel.setFolder(mediaFolderModel);
		if (format == null)
		{
			MediaModel media = productUtilObj.removeMedia(code, catalogversion);
			if(media != null) {
				modelService.remove(media);
			}
			mediaModel.setCode(code);
		}
		else
		{
			String media_code = code.concat("_").concat(format);
			MediaModel media = productUtilObj.removeMedia(media_code, catalogversion);
			if(media != null) {
				modelService.remove(media);
			}
			mediaModel.setCode(code.concat("_").concat(format));
			boolean isSet = false;
			for (final MediaFormatModel mediaFormat : mediaFormats)
			{
				if (mediaFormat.getQualifier().equalsIgnoreCase(format))
				{
					mediaModel.setMediaFormat(mediaFormat);
					isSet = true;
					break;
				}
			}
			if (!isSet)
			{
				final MediaFormatModel mediaFormat = new MediaFormatModel(format);
				mediaModel.setMediaFormat(mediaFormat);
			}
		}
		if (videoId != null)
		{
			final String videoURL = "https://www.youtube.com/embed/".concat(videoId);
			mediaModel.setAltText(videoURL);
		}
		//	mediaModel.setCatalogVersion(catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Staged"));
		if(catalogversion != null)
		{
		mediaModel.setCatalogVersion(catalogversion);
		}
		else
		{
	mediaModel.setCatalogVersion(catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online"));
			
		}
		
		mediaModel.setMime(mimeType);
		mediaModel.setRealFileName(fullPath.substring(fullPath.lastIndexOf("/") + 1));
		modelService.save(mediaModel);
		final String urlFullPath = fullPath.startsWith("/") ? fullPath.substring(1) : fullPath;
		mediaModel.setLocation(urlFullPath);
		mediaModel.setDataPK(mediaModel.getPk().getLong());
		mediaModel.setInternalURL("replicated273654712");
		mediaModel.setLocationHash(
				mediaLocationHashService.createHash(mediaFolderModel.getQualifier(), urlFullPath, 1000000L, mimeType));
		mediaModel.setSize(1000000L);
		if ("SDS".equalsIgnoreCase(sheetType))
		{
			mediaModel.setSds("SDS");
		}
		else if ("Label".equalsIgnoreCase(sheetType))
		{
			mediaModel.setLabel("Label");
		}
		else if ("Product Information Sheet".equalsIgnoreCase(sheetType))
		{
			mediaModel.setSds("Product Information Sheet");
		}
		else if (OWNERS_MANUAL.equalsIgnoreCase(sheetType))
		{
			mediaModel.setSds(OWNERS_MANUAL);
		}
		else if (INSTALLATION_INSTRUCTION.equalsIgnoreCase(sheetType))
		{
			mediaModel.setSds(INSTALLATION_INSTRUCTION);
		}
		else if (TECHNICAL_DRAWING.equalsIgnoreCase(sheetType))
		{
			mediaModel.setSds(TECHNICAL_DRAWING);
		}
		else if (PARTS_DIAGRAM.equalsIgnoreCase(sheetType))
		{
			mediaModel.setSds(PARTS_DIAGRAM);
		}
		else if (WARRANTY_INFO.equalsIgnoreCase(sheetType))
		{
			mediaModel.setSds(WARRANTY_INFO);
		}
		else if (BROCHURES.equalsIgnoreCase(sheetType))
		{
			mediaModel.setSds(BROCHURES);
		}
		modelService.save(mediaModel);
		return mediaModel;
	}

	private GlobalProductNavigationNodeModel createNavNode(final List<CategoryModel> parentCategory,
			final List<CategoryModel> nodeCategory, final GlobalProductNavigationNodeModel latestNode, final boolean isUS)
	{
		final GlobalProductNavigationNodeModel navNode = modelService.create(GlobalProductNavigationNodeModel.class);
		Integer sequenceNumber = 0;
		GlobalProductNavigationNodeModel parent = latestNode;
		if (null == latestNode)
		{
			parent = productUtilObj.getNavigationNode(parentCategory, isUS);
		}
		final String name = nodeCategory.get(0).getName().replaceAll(" ", "").concat(parent.getName().replaceAll(" ", ""));
		navNode.setName(name);
		navNode.setUid(name);
		navNode.setParent(parent);
		navNode.setCategory(nodeCategory.get(1));
		if (CollectionUtils.isNotEmpty(parent.getChildren()))
		{
			for (final CMSNavigationNodeModel cms : parent.getChildren())
			{
				final GlobalProductNavigationNodeModel temp = (GlobalProductNavigationNodeModel) cms;
				if (sequenceNumber < temp.getSequenceNumber())
				{
					sequenceNumber = temp.getSequenceNumber();
				}
			}
		}

		navNode.setSequenceNumber(sequenceNumber + 1);
		navNode.setCatalogVersion(parent.getCatalogVersion());
		LOG.info("catalog version if parent not null" + parent.getCatalogVersion());
		LOG.info("nav node code " + navNode.getSequenceNumber());
		modelService.save(navNode);
		return navNode;

	}

	// Creating category for CA that are already available in US website
	private List<CategoryModel> createCategoryOnlyForCA(final Object model, final SiteOneCategoryLevelModel category,
			final List<CategoryModel> lCategory, final List<CategoryModel> parentCategory)
	{
		final CatalogVersionModel catalogversion_us = catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online");
		final CatalogVersionModel catalogversion_ca = catalogVersionService.getCatalogVersion("siteoneCAProductCatalog", "Online");
		CategoryModel phCategory_ca = null, shCategory_ca = null, ph_ca = null, sh_ca = null;
		final Map<String, CategoryModel> categories = getCategory(lCategory);
		phCategory_ca = categories.get("phCategory");
		shCategory_ca = categories.get("shCategory");
		final Map<String, CategoryModel> parentCategories = getCategory(parentCategory);
		ph_ca = parentCategories.get("phCategory_ca");
		sh_ca = parentCategories.get("shCategory_ca");
		final List<CategoryModel> newCategories = new ArrayList<>();
		newCategories.add(createNewCategory("PH".concat(phCategory_ca.getCode().substring(2)), category, ph_ca,
				"SH".concat(phCategory_ca.getCode().substring(2)), catalogversion_ca, null));
		newCategories
				.add(createNewCategory("SH".concat(phCategory_ca.getCode().substring(2)), category, sh_ca, null, catalogversion_ca, null));
		return newCategories;
	}

	//Gets category code based on catalog version
	private Map<String, CategoryModel> getCategory(final List<CategoryModel> categories)
	{
		final Map<String, CategoryModel> categoryList = new HashMap();
		final CatalogVersionModel catalogversion_us = catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online");
		final CatalogVersionModel catalogversion_ca = catalogVersionService.getCatalogVersion("siteoneCAProductCatalog", "Online");
		for (final CategoryModel cat : categories)
		{
			if (cat.getCode().startsWith("PH") && cat.getCatalogVersion() == catalogversion_us)
			{
				categoryList.put("phCategory", cat);
			}
			if (cat.getCode().startsWith("SH") && cat.getCatalogVersion() == catalogversion_us)
			{
				categoryList.put("shCategory", cat);
			}
			if (cat.getCode().startsWith("PH") && cat.getCatalogVersion() == catalogversion_ca)
			{
				categoryList.put("phCategory_ca", cat);
			}
			if (cat.getCode().startsWith("SH") && cat.getCatalogVersion() == catalogversion_ca)
			{
				categoryList.put("shCategory_ca", cat);
			}
		}
		return categoryList;
	}

	private List<CategoryModel> createCategory(final Object model, final SiteOneCategoryLevelModel category,
			final List<CategoryModel> l1Category)
	{
		//l1Category.getSupercategories().get(0).getCode()
		//l1Category.getCategories().get(12).getCode()
		final CatalogVersionModel catalogversion_us = catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online");
		final CatalogVersionModel catalogversion_ca = catalogVersionService.getCatalogVersion("siteoneCAProductCatalog", "Online");
		final BaseSiteModel basesite_us = baseSiteService.getBaseSiteForUID(SiteoneintegrationConstants.BASESITE_US);
		final BaseSiteModel basesite_ca = baseSiteService.getBaseSiteForUID(SiteoneintegrationConstants.BASESITE_CA);
		CategoryModel phCategory = null, shCategory = null, phCategory_ca = null, shCategory_ca = null;
		final Map<String, CategoryModel> categories = getCategory(l1Category);
		phCategory = categories.get("phCategory");
		shCategory = categories.get("shCategory");
		phCategory_ca = categories.get("phCategory_ca");
		shCategory_ca = categories.get("shCategory_ca");

		final List<CategoryModel> newCategories = new ArrayList<>();
		final List<String> categoryCodes = phCategory.getCategories().stream().map(e -> e.getCode()).collect(Collectors.toList());
		Collections.sort(categoryCodes);
		String categoryCode = "";
		if (null != categoryCodes && CollectionUtils.isNotEmpty(categoryCodes))
		{
			final int code = Integer.parseInt(categoryCodes.get(categoryCodes.size() - 1).substring(2)) + 1;
			LOG.error("incremented category code :" + code);
			categoryCode = Integer.toString(code);
		}
		else
		{
			if (phCategory.getCode().substring(2).length() == 2)
			{
				categoryCode = categoryCode.concat(phCategory.getCode().substring(2).concat("10"));
			}
			if (phCategory.getCode().substring(2).length() == 4)
			{
				categoryCode = categoryCode.concat(phCategory.getCode().substring(2).concat("100"));
			}
			if (phCategory.getCode().substring(2).length() == 7)
			{
				categoryCode = categoryCode.concat(phCategory.getCode().substring(2).concat("100"));
			}
			LOG.error("incremented category code :" + categoryCode);
		}
		LOG.error("categoryCodes :" + categoryCodes);
		newCategories.add(
				createNewCategory("PH".concat(categoryCode), category, phCategory, "SH".concat(categoryCode), catalogversion_us, basesite_us.getName()));
		newCategories.add(createNewCategory("SH".concat(categoryCode), category, shCategory, null, catalogversion_us, basesite_us.getName()));
		newCategories.add(
				createNewCategory("PH".concat(categoryCode), category, phCategory_ca, "SH".concat(categoryCode), catalogversion_ca, basesite_ca.getName()));
		newCategories.add(createNewCategory("SH".concat(categoryCode), category, shCategory_ca, null, catalogversion_ca, basesite_ca.getName()));
		//return createNewCategory();
		return newCategories;
	}

	private CategoryModel createNewCategory(final String categoryCode, final SiteOneCategoryLevelModel category,
			final CategoryModel parentCategory, final String salesCategoryMapping, final CatalogVersionModel catalogversion, final String baseSiteName)
	{
		final CategoryModel categoryModel = modelService.create(CategoryModel.class);
		final UserGroupModel userGroupModel = b2bCommerceB2BUserGroupService.getUserGroupForUID("admingroup", UserGroupModel.class);
		categoryModel.setCode(categoryCode);
		categoryModel.setName(category.getName());
		categoryModel.setPimCategoryId(category.getId());
		categoryModel.setSupercategories(Arrays.asList(parentCategory));
		categoryModel.setPageTitle(category.getName() + CATEGORY_TITLE + " | " + baseSiteName);
		if (userGroupModel != null)
		{
			categoryModel.setAllowedPrincipals(Arrays.asList(userGroupModel));
		}

		categoryModel.setProductCount(0);
		if (null != salesCategoryMapping)
		{
			categoryModel.setSalesCategoryMapping(salesCategoryMapping);
		}
		categoryModel.setCatalogVersion(catalogversion);
		modelService.save(categoryModel);
		LOG.info("categoryCode : " + categoryModel.getCode() + " category-catalogversion : "
				+ categoryModel.getCatalogVersion().getCatalog().getId());
		return categoryModel;

	}
	
}
