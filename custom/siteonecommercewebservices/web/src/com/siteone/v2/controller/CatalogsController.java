/*
 * [y] hybris Platform
 *
 * Copyright (c) 2018 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.siteone.v2.controller;

import de.hybris.platform.catalog.CatalogService;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.jalo.CatalogManager;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.cms2.model.navigation.CMSNavigationNodeModel;
import de.hybris.platform.commercefacades.catalog.CatalogFacade;
import de.hybris.platform.commercefacades.catalog.CatalogOption;
import de.hybris.platform.commercefacades.catalog.PageOption;
import de.hybris.platform.commercefacades.catalog.data.CatalogData;
import de.hybris.platform.commercefacades.catalog.data.CatalogVersionData;
import de.hybris.platform.commercefacades.catalog.data.CatalogsData;
import de.hybris.platform.commercefacades.catalog.data.CategoryHierarchyData;
import de.hybris.platform.commercewebservicescommons.dto.catalog.CatalogListWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.catalog.CatalogVersionWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.catalog.CatalogWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.catalog.CategoryHierarchyWsDTO;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.media.MediaService;

import de.hybris.platform.webservicescommons.mapping.DataMapper;
import de.hybris.platform.webservicescommons.mapping.FieldSetBuilder;
import de.hybris.platform.webservicescommons.mapping.impl.FieldSetBuilderContext;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;
import de.hybris.platform.webservicescommons.swagger.ApiFieldsParam;

import jakarta.annotation.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.siteone.core.category.service.SiteOneCategoryService;
import com.siteone.facades.category.SiteOneCategoryFacade;
import com.siteone.core.dto.navigation.GlobalProductNavigationNodeData;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
@RequestMapping(value = "/{baseSiteId}/catalogs")
@Tag(name = "Catalogs")
public class CatalogsController extends BaseController
{
	private static final Set<CatalogOption> OPTIONS;
	private static final String CATALOGVERSIONNAME = "Online";
	private static final String CATALOGROOT = "SH1";
	private static final String HARDSCAPENAVNODE = "HardscapeNavNode";
	private static final String HARDSCAPE_MEDIACODE = "L1_Mobile_Hardscapes";
	private static final String CATALOGID = "siteoneContentCatalog";
	private static final String HARDSCAPES_URL = "/hardscapes";
	private static final String HARDSCAPES_HEADLINE = "Hardscapes & Outdoor Living";
	private static final String EXCEPTION = "Exception occurred while calling the method";

	
	static
	{
		OPTIONS = getOptions();
	}

	@Resource(name = "cwsCatalogFacade")
	private CatalogFacade catalogFacade;
	@Resource(name = "fieldSetBuilder")
	private FieldSetBuilder fieldSetBuilder;
	@Resource(name = "categoryService")
	private SiteOneCategoryService categoryService;
	@Resource(name = "siteOnecategoryFacade")
	private SiteOneCategoryFacade siteOnecategoryFacade;
	@Resource(name = "mediaService")
	private MediaService mediaService;
	@Resource(name = "catalogVersionService")
	private CatalogVersionService catalogVersionService;
	
	private static final Logger LOG = LoggerFactory.getLogger(CatalogsController.class);
	protected static Set<CatalogOption> getOptions()
	{
		final Set<CatalogOption> opts = new HashSet<>();
		opts.add(CatalogOption.BASIC);
		opts.add(CatalogOption.CATEGORIES);
		opts.add(CatalogOption.SUBCATEGORIES);
		return opts;
	}

	@GetMapping
	@ResponseBody
	@Operation(operationId ="getCatalogs", summary = "Get a list of catalogs", description = "Returns all catalogs with versions defined for the base store.")
	@ApiBaseSiteIdParam
	@Hidden
	public CatalogListWsDTO getCatalogs(@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		final List<CatalogData> catalogDataList = catalogFacade.getAllProductCatalogsForCurrentSite(OPTIONS);
		final CatalogsData catalogsData = new CatalogsData();
		catalogsData.setCatalogs(catalogDataList);

		final FieldSetBuilderContext context = new FieldSetBuilderContext();
		context.setRecurrencyLevel(countRecurrecyLevel(catalogDataList));
		final Set<String> fieldSet = fieldSetBuilder
				.createFieldSet(CatalogListWsDTO.class, DataMapper.FIELD_PREFIX, fields, context);

		return getDataMapper().map(catalogsData, CatalogListWsDTO.class, fieldSet);
	}

	@GetMapping("/{catalogId}")
	@ResponseBody
	@Operation(operationId ="getCatalog", summary = "Get a catalog", description = "Returns information about a catalog based on its ID, along with the versions defined for the current base store.")
	@ApiBaseSiteIdParam
	@Hidden
	public CatalogWsDTO getCatalog(@Parameter(description = "Catalog identifier", required = true) @PathVariable final String catalogId,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		final CatalogData catalogData = catalogFacade.getProductCatalogForCurrentSite(catalogId, OPTIONS);

		final FieldSetBuilderContext context = new FieldSetBuilderContext();
		context.setRecurrencyLevel(countRecurrencyForCatalogData(catalogData));
		final Set<String> fieldSet = fieldSetBuilder.createFieldSet(CatalogWsDTO.class, DataMapper.FIELD_PREFIX, fields, context);

		return getDataMapper().map(catalogData, CatalogWsDTO.class, fieldSet);
	}

	@GetMapping("/{catalogId}/{catalogVersionId}")
	@ResponseBody
	@Operation(operationId ="getCatalogVersion", summary = "Get information about catalog version", description = "Returns information about the catalog version that exists for the current base store.")
	@ApiBaseSiteIdParam
	@Hidden
	public CatalogVersionWsDTO getCatalogVersion(
			@Parameter(description = "Catalog identifier", required = true) @PathVariable final String catalogId,
			@Parameter(description = "Catalog version identifier", required = true) @PathVariable final String catalogVersionId,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		final CatalogVersionData catalogVersionData = catalogFacade
				.getProductCatalogVersionForTheCurrentSite(catalogId, catalogVersionId, OPTIONS);

		final FieldSetBuilderContext context = new FieldSetBuilderContext();
		context.setRecurrencyLevel(countRecurrencyForCatalogVersionData(catalogVersionData));
		final Set<String> fieldSet = fieldSetBuilder
				.createFieldSet(CatalogVersionWsDTO.class, DataMapper.FIELD_PREFIX, fields, context);

		return getDataMapper().map(catalogVersionData, CatalogVersionWsDTO.class, fieldSet);
	}

	@GetMapping("/{catalogId}/{catalogVersionId}/categories/{categoryId}")
	@ResponseBody
	@Operation(operationId ="getCategories", summary = "Get information about catagory in a catalog version", description = "Returns information about a specified category that exists in a catalog version available for the current base store.")
	@ApiBaseSiteIdParam
	@Hidden
	public CategoryHierarchyWsDTO getCategories(
			@Parameter(description = "Catalog identifier", required = true) @PathVariable final String catalogId,
			@Parameter(description = "Catalog version identifier", required = true) @PathVariable final String catalogVersionId,
			@Parameter(description = "Category identifier", required = true) @PathVariable final String categoryId,
			@ApiFieldsParam @RequestParam(defaultValue = "DEFAULT") final String fields)
	{
		final PageOption page = PageOption.createForPageNumberAndPageSize(0, 10);
		final CategoryHierarchyData categoryHierarchyData = catalogFacade
				.getCategoryById(catalogId, catalogVersionId, categoryId, page, OPTIONS);

		final FieldSetBuilderContext context = new FieldSetBuilderContext();
		context.setRecurrencyLevel(countRecurrencyForCategoryHierarchyData(1, categoryHierarchyData));
		final Set<String> fieldSet = fieldSetBuilder
				.createFieldSet(CategoryHierarchyWsDTO.class, DataMapper.FIELD_PREFIX, fields, context);

		return getDataMapper().map(categoryHierarchyData, CategoryHierarchyWsDTO.class, fieldSet);
	}

	protected int countRecurrecyLevel(final List<CatalogData> catalogDataList)
	{
		int recurrencyLevel = 1;
		int value;
		for (final CatalogData catalog : catalogDataList)
		{
			value = countRecurrencyForCatalogData(catalog);
			if (value > recurrencyLevel)
			{
				recurrencyLevel = value;
			}
		}
		return recurrencyLevel;
	}

	protected int countRecurrencyForCatalogData(final CatalogData catalog)
	{
		int retValue = 1;
		int value;
		for (final CatalogVersionData version : catalog.getCatalogVersions())
		{
			value = countRecurrencyForCatalogVersionData(version);
			if (value > retValue)
			{
				retValue = value;
			}
		}
		return retValue;
	}

	protected int countRecurrencyForCatalogVersionData(final CatalogVersionData catalogVersion)
	{
		int retValue = 1;
		int value;
		for (final CategoryHierarchyData hierarchy : catalogVersion.getCategoriesHierarchyData())
		{
			value = countRecurrencyForCategoryHierarchyData(1, hierarchy);
			if (value > retValue)
			{
				retValue = value;
			}
		}
		return retValue;
	}

	protected int countRecurrencyForCategoryHierarchyData(final int currentValue, final CategoryHierarchyData hierarchy)
	{
		int calculatedValue = currentValue + 1;
		int subcategoryRecurrencyValue;
		for (final CategoryHierarchyData subcategory : hierarchy.getSubcategories())
		{
			subcategoryRecurrencyValue = countRecurrencyForCategoryHierarchyData(calculatedValue, subcategory);
			if (subcategoryRecurrencyValue > calculatedValue)
			{
				calculatedValue = subcategoryRecurrencyValue;
			}
		}
		return calculatedValue;
	}
	
	
	@GetMapping("/navigationnode")
	@ResponseBody
	@ApiBaseSiteIdParam
	@Operation(operationId ="navigationNode", summary = "Display the Navigation node for Catalog categories", description = "Display the Navigation node for Catalog categories")
	public List<GlobalProductNavigationNodeData> getGlobalProductNodes()
	{
		try {
		List<GlobalProductNavigationNodeData> NavNodeList = siteOnecategoryFacade.getGlobalNavigationCategories(CATALOGROOT);
		//NavNodeList.add(getHardscapeHavNode());
		return NavNodeList;
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" getGlobalProductNodes");
		}
	}
	
	protected GlobalProductNavigationNodeData getHardscapeHavNode() {
		GlobalProductNavigationNodeData HardscapeNode = new GlobalProductNavigationNodeData();
		HardscapeNode.setUid(HARDSCAPENAVNODE);
		HardscapeNode.setCategoryName(HARDSCAPES_HEADLINE);
		HardscapeNode.setUrl(HARDSCAPES_URL);
		HardscapeNode.setVisible(true);
		final CatalogVersionModel catalogVersion = catalogVersionService.getCatalogVersion(CATALOGID, CatalogManager.ONLINE_VERSION);
		final MediaModel media = mediaService.getMedia(catalogVersion, HARDSCAPE_MEDIACODE);
		if (null != media)
		{
			HardscapeNode.setImageUrl(media.getURL());
		}
		return HardscapeNode;
	}
	
	
}
