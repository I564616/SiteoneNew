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

import java.util.List;
import java.util.ArrayList;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.annotation.Resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

import de.hybris.platform.cmsoccaddon.jaxb.adapters.components.ComponentListWsDTOAdapter;
import de.hybris.platform.cmsoccaddon.jaxb.adapters.components.ComponentListWsDTOAdapter.ListAdaptedComponents;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpStatus;

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;
import de.hybris.platform.cmsfacades.data.AbstractCMSComponentData;
import de.hybris.platform.cmsfacades.exception.ValidationException;
import de.hybris.platform.webservicescommons.errors.exceptions.WebserviceValidationException;
import de.hybris.platform.cmsoccaddon.mapping.CMSDataMapper;

import org.springframework.web.bind.annotation.ResponseStatus;

import com.siteone.facade.cmspage.SiteOnePageFacade;

import de.hybris.platform.cmsoccaddon.jaxb.adapters.pages.PageAdapterUtil;
import de.hybris.platform.cmsfacades.data.AbstractPageData;
import de.hybris.platform.cmsfacades.data.PageContentSlotData;
import de.hybris.platform.cmsfacades.items.ComponentItemFacade;
import de.hybris.platform.cmsfacades.pages.PageFacade;
import de.hybris.platform.cmsoccaddon.data.CMSPageWsDTO;
import de.hybris.platform.cmsoccaddon.jaxb.adapters.pages.PageWsDTOAdapter;
import de.hybris.platform.cmsoccaddon.data.ContentSlotListWsDTO;
import de.hybris.platform.cmsoccaddon.data.ContentSlotWsDTO;
import de.hybris.platform.cmsoccaddon.data.ComponentWsDTO;
import de.hybris.platform.cmsoccaddon.data.ComponentListWsDTO;


@Controller
@RequestMapping(value = "/{baseSiteId}/cms")
@Tag(name = "Siteone Page")
public class SiteonePageController
{
	private static final Logger LOG = Logger.getLogger(SiteonePageController.class);

	public static final String CONTENT_POSITION_SECTION3 = "Section3";
	public static final String COMPONENT_FLAG = "content";
	public static final String ROTATINGBANNER = "rotatingBanner";
	public static final String BANNERLIST = "bannerList";
	private static final String EXCEPTION = "Exception occurred while calling the method";

	
	@Resource(name = "cmsDataMapper")
	private CMSDataMapper cmsdataMapper;

	@Resource(name = "cmsPageFacade")
	private PageFacade pageFacade;
	
	@Resource(name = "componentItemFacade")
	private ComponentItemFacade componentItemFacade;

	@Resource(name = "siteOnePageFacade")
	private SiteOnePageFacade siteOnePageFacade;
	

	@GetMapping("/articlesContent/{pageId}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@Operation(summary = "Get page data for specific page id with a list of cms content slots",description = "Returns ContentSlot and Components", 
			operationId = "getPageById")
	@ApiBaseSiteIdParam
	public PageAdapterUtil.PageAdaptedData getPagesByPageId( 
			@PathVariable("pageId") final String pageId,
			@Parameter(description = "Response configuration (list of fields, which should be returned in response)", schema = @Schema(allowableValues = "BASIC, DEFAULT, FULL")) //
			@RequestParam(defaultValue = "DEFAULT") final String fields) throws CMSItemNotFoundException
	{
	
		try {
			final AbstractPageData pageData = pageFacade.getPageData(pageId);
			final CMSPageWsDTO page = (CMSPageWsDTO) cmsdataMapper.map(pageData, fields);
			ContentSlotListWsDTO contentSlotListDTO = getContentSlotList(pageData, fields);
			page.setContentSlots(contentSlotListDTO);
	
			final PageWsDTOAdapter adapter = new PageWsDTOAdapter();
			return adapter.marshal(page);
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" getpagesByPageId");
		}
		
	}
	
	
	protected ContentSlotListWsDTO getContentSlotList(final AbstractPageData pageData,final String fields ) {
		ContentSlotListWsDTO contentSlotListWsDTO = new ContentSlotListWsDTO();
		List<ContentSlotWsDTO> contentSlotListDTO = new ArrayList<ContentSlotWsDTO>();
		
		for(PageContentSlotData contentSlot : pageData.getContentSlots()) {
			if( contentSlot.getPosition().equals(CONTENT_POSITION_SECTION3)) {
				
				ContentSlotWsDTO contentSlotWsDTO = (ContentSlotWsDTO) cmsdataMapper.map(contentSlot, fields);
				ComponentListWsDTO componentListWsDTO = new ComponentListWsDTO();
				List<ComponentWsDTO> componentListDTO = new ArrayList<ComponentWsDTO>();
				
				for( AbstractCMSComponentData component : contentSlot.getComponents()) {
					if( component.getOtherProperties().containsKey(COMPONENT_FLAG)) {
	
						ComponentWsDTO componentWsDTO = (ComponentWsDTO) cmsdataMapper.map( component, fields);
						componentListDTO.add(componentWsDTO);
					}
				}
				if(CollectionUtils.isNotEmpty(componentListDTO))	{
					componentListWsDTO.setComponent(componentListDTO);
					contentSlotWsDTO.setComponents(componentListWsDTO);
					contentSlotListDTO.add(contentSlotWsDTO);
				}
			}
		}
		if(CollectionUtils.isNotEmpty(contentSlotListDTO)) {
			contentSlotListWsDTO.setContentSlot(contentSlotListDTO);
		}
		return contentSlotListWsDTO;
	}
	
	
	
	@GetMapping("/bannerComponent/{pageId}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@Operation(summary = "Get component data for specified page id and slot id with rotating banner component",description = "Returns Components", 
			operationId = "getComponentsByPageIdAndSlotId")
	@ApiBaseSiteIdParam
	public ListAdaptedComponents getComponentList( 
			@PathVariable("pageId") final String pageId,
			@RequestParam(value = "slotId") final String slotId,
			@Parameter(description = "Response configuration (list of fields, which should be returned in response)", schema = @Schema(allowableValues = "BASIC, DEFAULT, FULL")) //
			@RequestParam(defaultValue = "DEFAULT") final String fields) throws CMSItemNotFoundException
	{
		try {
		final AbstractPageData pageData = pageFacade.getPageData(pageId);
		List<ComponentWsDTO> componentWsDTOList = new ArrayList<ComponentWsDTO>();
		final ComponentListWsDTO componentListWsDTO = new ComponentListWsDTO();
		
		for(PageContentSlotData contentSlot : pageData.getContentSlots()) {
			if(contentSlot.getSlotId().equals(slotId)) {
				for( AbstractCMSComponentData component : contentSlot.getComponents()) {
					List<String> bannerList = new ArrayList<String>();
					if(component.getOtherProperties().containsKey(ROTATINGBANNER)) {
						bannerList = (ArrayList)component.getOtherProperties().get(ROTATINGBANNER);
					}
					else if(component.getOtherProperties().containsKey(BANNERLIST)) {
						bannerList = (ArrayList)component.getOtherProperties().get(BANNERLIST);
					}
					if(CollectionUtils.isNotEmpty(bannerList)) {
						for(String bannerUid : bannerList) {
							AbstractCMSComponentData componentData = componentItemFacade.getComponentById(bannerUid, null, null, null);
							ComponentWsDTO componentWsDTO = (ComponentWsDTO) cmsdataMapper.map(componentData, fields);
							componentWsDTOList.add(componentWsDTO);
						}
					}
					
				}
	
			}
		}
		componentListWsDTO.setComponent(componentWsDTOList);
		final ComponentListWsDTOAdapter adapter = new ComponentListWsDTOAdapter();
		final ListAdaptedComponents listAdaptedComponent = adapter.marshal(componentListWsDTO);
		return listAdaptedComponent;
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" getComponentList");
		}
	
	}
	
	@GetMapping("/siteonepages/{pageId}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@Operation(summary = "Get page data for specified page id with rotating banner component's child component details",description = "Returns Page Data", 
			operationId = "getSiteonePageDataByPageId")
	@ApiBaseSiteIdParam
	public List<PageContentSlotData> getPageDataByPageId( 
			@PathVariable("pageId") final String pageId,
			@RequestParam(value = "storeId", required = false) final String storeId,
			@Parameter(description = "Response configuration (list of fields, which should be returned in response)", schema = @Schema(allowableValues = "BASIC, DEFAULT, FULL")) //
			@RequestParam(defaultValue = "DEFAULT") final String fields) throws CMSItemNotFoundException
	{
		try {
		return siteOnePageFacade.prepareCMSPageData(pageId, storeId);
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" getPageDataByPageId");
		}
	}


}