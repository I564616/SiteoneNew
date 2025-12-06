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

import de.hybris.platform.commercefacades.consent.ConsentFacade;
import de.hybris.platform.commercefacades.consent.data.ConsentTemplateData;
import de.hybris.platform.commerceservices.consent.exceptions.CommerceConsentGivenException;
import de.hybris.platform.commerceservices.consent.exceptions.CommerceConsentWithdrawnException;
import de.hybris.platform.commercewebservicescommons.dto.consent.ConsentTemplateListWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.consent.ConsentTemplateWsDTO;
import de.hybris.platform.commercewebservicescommons.errors.exceptions.ConsentWithdrawnException;
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException;
import de.hybris.platform.webservicescommons.errors.exceptions.AlreadyExistsException;
import de.hybris.platform.webservicescommons.errors.exceptions.NotFoundException;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdAndUserIdParam;
import de.hybris.platform.webservicescommons.swagger.ApiFieldsParam;
import com.siteone.consent.data.ConsentTemplateDataList;

import jakarta.annotation.Resource;
import jakarta.ws.rs.ForbiddenException;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Hidden;


@Controller
@RequestMapping(value = "/{baseSiteId}/users/{userId}")
@Tag(name = "Consents")
@Hidden
public class ConsentsController extends BaseCommerceController
{
	private static final Logger LOG = Logger.getLogger(ConsentsController.class);

	@Resource(name = "consentFacade")
	private ConsentFacade consentFacade;

	@GetMapping("/consenttemplates")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@Operation(operationId = "getConsentTemplates", summary = "Fetch the list of consents", description = "If user has not given or withdrawn consent to any of the template, no given or withdraw date is returned.")
	@ApiBaseSiteIdAndUserIdParam
	public ConsentTemplateListWsDTO getConsentTemplates(
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		final ConsentTemplateDataList consentTemplateDataList = new ConsentTemplateDataList();
		final List<ConsentTemplateData> consentTemplateDatas = getUserFacade().isAnonymousUser() ?
				consentFacade.getConsentTemplatesWithConsents().stream().filter(ConsentTemplateData::isExposed)
						.collect(Collectors.toList()) : consentFacade.getConsentTemplatesWithConsents();
		consentTemplateDataList.setConsentTemplates(consentTemplateDatas);
		return getDataMapper().map(consentTemplateDataList, ConsentTemplateListWsDTO.class, fields);
	}

	@PostMapping("/consents")
	@ResponseBody
	@Operation(operationId = "doGiveConsent", summary = "A user can give consent.")
	@ApiBaseSiteIdAndUserIdParam
	public ResponseEntity<ConsentTemplateWsDTO> doGiveConsent(
			@Parameter(description = "Consent template ID.", required = true) @RequestParam final String consentTemplateId,
			@Parameter(description = "Consent template version.", required = true) @RequestParam final Integer consentTemplateVersion)
	{
		if (getUserFacade().isAnonymousUser())
		{
			throw new ForbiddenException("An anonymous user can't give a consent");
		}

		try
		{
			consentFacade.giveConsent(consentTemplateId, consentTemplateVersion);
		}
		catch (final CommerceConsentGivenException e)
		{
			LOG.warn(e.getMessage(), e);
			throw new AlreadyExistsException(e.getMessage());
		}
		final ConsentTemplateData consentTemplate = consentFacade.getLatestConsentTemplate(consentTemplateId);
		final ConsentTemplateWsDTO consentTemplateWsDto = getDataMapper().map(consentTemplate, ConsentTemplateWsDTO.class);

		final String uriLocation = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").replaceQuery(StringUtils.EMPTY)
				.buildAndExpand(consentTemplateWsDto.getId()).toUriString().replace("/consents", "/consenttemplates");

		return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, uriLocation).body(consentTemplateWsDto);
	}

	@GetMapping("/consenttemplates/{consentTemplateId}")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@Operation(operationId = "getConsentTemplate", summary = "Fetch the consent.", description = "If user has not given or withdrawn consent to the template, no given or withdraw date is returned.")
	@ApiBaseSiteIdAndUserIdParam
	public ConsentTemplateWsDTO getConsentTemplate(
			@Parameter(description = "Consent template id.", required = true) @PathVariable final String consentTemplateId,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{

		final ConsentTemplateData latestConsentTemplate = consentFacade.getLatestConsentTemplate(consentTemplateId);
		if (getUserFacade().isAnonymousUser() && !latestConsentTemplate.isExposed())
		{
			throw new NotFoundException("This consent template is not exposed to anonymous user");
		}
		return getDataMapper().map(latestConsentTemplate, ConsentTemplateWsDTO.class, fields);
	}

	@DeleteMapping("/consents/{consentCode}")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@Operation(operationId = "removeConsent", summary = "Withdraw the user consent for a given consent code.", description = "If the user consent was given, the consent is withdrawn. If consent was already withdrawn then returns consent already withdrawn error. If there is no such consent then returns not found. If the current user is an anonymous user then returns access denied error.")
	@ApiBaseSiteIdAndUserIdParam
	public void removeConsent(
			@Parameter(description = "Consent code.", required = true) @PathVariable(value = "consentCode") final String consentCode)
	{
		if (getUserFacade().isAnonymousUser())
		{
			throw new AccessDeniedException("Anonymous user cannot withdraw consent");
		}

		final String consentNotFoundMessage = "Consent with code [%s] was not found";
		try
		{
			consentFacade.withdrawConsent(consentCode);
		}
		catch (final CommerceConsentWithdrawnException e)
		{
			LOG.warn(e.getMessage(), e);
			throw new ConsentWithdrawnException(e.getMessage(), ConsentWithdrawnException.CONSENT_WITHDRAWN);
		}
		catch (final IllegalArgumentException e)
		{
			LOG.warn(String.format(consentNotFoundMessage, consentCode), e);
			throw new NotFoundException(String.format(consentNotFoundMessage, consentCode));
		}
		catch (final ModelNotFoundException e)
		{
			throw new NotFoundException(String.format(consentNotFoundMessage, consentCode));
		}
		catch (final AccessDeniedException e)
		{
			throw e;
		}
	}

}
