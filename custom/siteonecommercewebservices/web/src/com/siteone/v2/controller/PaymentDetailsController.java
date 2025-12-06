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

import de.hybris.platform.commercefacades.order.data.CCPaymentInfoData;
import de.hybris.platform.commercefacades.order.data.CCPaymentInfoDatas;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercewebservicescommons.dto.order.PaymentDetailsListWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.order.PaymentDetailsWsDTO;
import de.hybris.platform.commercewebservicescommons.errors.exceptions.RequestParameterException;
import de.hybris.platform.core.PK.PKException;
import de.hybris.platform.webservicescommons.cache.CacheControl;
import de.hybris.platform.webservicescommons.cache.CacheControlDirective;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdAndUserIdParam;
import de.hybris.platform.webservicescommons.swagger.ApiFieldsParam;
import com.siteone.populator.options.PaymentInfoOption;
import com.siteone.swagger.ApiBaseSiteIdAndUserIdAndPaymentDetailsParams;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Hidden;

@Controller
@RequestMapping(value = "/{baseSiteId}/users/{userId}/paymentdetails")
@CacheControl(directive = CacheControlDirective.PRIVATE)
@Tag(name = "Payment Details")
@Hidden
public class PaymentDetailsController extends BaseCommerceController
{
	private static final Logger LOG = LoggerFactory.getLogger(UsersController.class);

	private static final String OBJECT_NAME_PAYMENT_DETAILS = "paymentDetails";

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping
	@ResponseBody
	@Operation(operationId = "getPaymentDetailsList", summary = "Get customer's credit card payment details list.", description = "Return customer's credit card payment details list.")
	@ApiBaseSiteIdAndUserIdParam
	public PaymentDetailsListWsDTO getPaymentDetailsList(
			@Parameter(description = "Type of payment details.") @RequestParam(defaultValue = "false") final boolean saved,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("getPaymentDetailsList");
		}

		final CCPaymentInfoDatas paymentInfoDataList = new CCPaymentInfoDatas();
		paymentInfoDataList.setPaymentInfos(getUserFacade().getCCPaymentInfos(saved));

		return getDataMapper().map(paymentInfoDataList, PaymentDetailsListWsDTO.class, fields);
	}


	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/{paymentDetailsId}")
	@ResponseBody
	@Operation(operationId = "getPaymentDetails", summary = "Get customer's credit card payment details.", description = "Returns a customer's credit card payment details for the specified paymentDetailsId.")
	@ApiBaseSiteIdAndUserIdParam
	public PaymentDetailsWsDTO getPaymentDetails(
			@Parameter(description = "Payment details identifier.", required = true) @PathVariable final String paymentDetailsId,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		return getDataMapper().map(getPaymentInfo(paymentDetailsId), PaymentDetailsWsDTO.class, fields);
	}

	public CCPaymentInfoData getPaymentInfo(final String paymentDetailsId)
	{
		LOG.debug("getPaymentInfo : id = " + sanitize(paymentDetailsId));
		try
		{
			final CCPaymentInfoData paymentInfoData = getUserFacade().getCCPaymentInfoForCode(paymentDetailsId);
			if (paymentInfoData == null)
			{
				throw new RequestParameterException("Payment details [" + sanitize(paymentDetailsId) + "] not found.",
						RequestParameterException.UNKNOWN_IDENTIFIER, "paymentDetailsId");
			}
			return paymentInfoData;
		}
		catch (final PKException e)
		{
			throw new RequestParameterException("Payment details [" + sanitize(paymentDetailsId) + "] not found.",
					RequestParameterException.UNKNOWN_IDENTIFIER, "paymentDetailsId", e);
		}
	}

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@DeleteMapping("/{paymentDetailsId}")
	@Operation(operationId = "removePaymentDetails", summary = "Deletes customer's credit card payment details.", description = "Deletes a customer's credit card payment details based on a specified paymentDetailsId.")
	@ApiBaseSiteIdAndUserIdParam
	@ResponseStatus(HttpStatus.OK)
	public void removePaymentDetails(
			@Parameter(description = "Payment details identifier.", required = true) @PathVariable final String paymentDetailsId)
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("removePaymentDetails: id = {}", sanitize(paymentDetailsId));
		}
		getPaymentInfo(paymentDetailsId);
		getUserFacade().removeCCPaymentInfo(paymentDetailsId);
	}

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PatchMapping("/{paymentDetailsId}")
	@Operation(hidden = true, summary = "Updates existing customer's credit card payment details. ", description =
			"Updates an existing customer's credit card payment "
					+ "details based on the specified paymentDetailsId. Only those attributes provided in the request will be updated.")
	@ApiBaseSiteIdAndUserIdAndPaymentDetailsParams
	@ResponseStatus(HttpStatus.OK)
	public void updatePaymentDetails(
			@Parameter(description = "Payment details identifier.", required = true) @PathVariable final String paymentDetailsId,
			final HttpServletRequest request)
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("updatePaymentDetails: id = {}", sanitize(paymentDetailsId));
		}

		final CCPaymentInfoData paymentInfoData = getPaymentInfo(paymentDetailsId);

		final boolean isAlreadyDefaultPaymentInfo = paymentInfoData.isDefaultPaymentInfo();
		final Collection<PaymentInfoOption> options = new ArrayList<>();
		options.add(PaymentInfoOption.BASIC);
		options.add(PaymentInfoOption.BILLING_ADDRESS);

		getHttpRequestPaymentInfoPopulator().populate(request, paymentInfoData, options);
		validate(paymentInfoData, OBJECT_NAME_PAYMENT_DETAILS, getCcPaymentInfoValidator());

		getUserFacade().updateCCPaymentInfo(paymentInfoData);
		if (paymentInfoData.isSaved() && !isAlreadyDefaultPaymentInfo && paymentInfoData.isDefaultPaymentInfo())
		{
			getUserFacade().setDefaultPaymentInfo(paymentInfoData);
		}
	}

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PatchMapping(value = "/{paymentDetailsId}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@Operation(operationId = "updatePaymentDetails", summary = "Updates existing customer's credit card payment details.", description =
			"Updates an existing customer's credit card payment details based "
					+ "on the specified paymentDetailsId. Only those attributes provided in the request will be updated.")
	@ApiBaseSiteIdAndUserIdParam
	@ResponseStatus(HttpStatus.OK)
	public void updatePaymentDetails(
			@Parameter(description = "Payment details identifier.", required = true) @PathVariable final String paymentDetailsId,
			@Parameter(description = "Payment details object", required = true) @RequestBody final PaymentDetailsWsDTO paymentDetails)
	{
		final CCPaymentInfoData paymentInfoData = getPaymentInfo(paymentDetailsId);
		final boolean isAlreadyDefaultPaymentInfo = paymentInfoData.isDefaultPaymentInfo();

		getDataMapper().map(paymentDetails, paymentInfoData,
				"accountHolderName,cardNumber,cardType,issueNumber,startMonth,expiryMonth,startYear,expiryYear,subscriptionId,defaultPaymentInfo,saved,"
						+ "billingAddress(firstName,lastName,titleCode,line1,line2,town,postalCode,region(isocode),country(isocode),defaultAddress)",
				false);
		validate(paymentInfoData, OBJECT_NAME_PAYMENT_DETAILS, getCcPaymentInfoValidator());

		getUserFacade().updateCCPaymentInfo(paymentInfoData);
		if (paymentInfoData.isSaved() && !isAlreadyDefaultPaymentInfo && paymentInfoData.isDefaultPaymentInfo())
		{
			getUserFacade().setDefaultPaymentInfo(paymentInfoData);
		}
	}

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PutMapping("/{paymentDetailsId}")
	@Operation(hidden = true, summary = "Updates existing customer's credit card payment details. ", description =
			"Updates existing customer's credit card payment "
					+ "info based on the payment info ID. Attributes not given in request will be defined again (set to null or default).")
	@ApiBaseSiteIdAndUserIdAndPaymentDetailsParams
	@ResponseStatus(HttpStatus.OK)
	public void replacePaymentDetails(
			@Parameter(description = "Payment details identifier.", required = true) @PathVariable final String paymentDetailsId,
			final HttpServletRequest request)
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("replacePaymentDetails: id = {}", sanitize(paymentDetailsId));
		}

		final CCPaymentInfoData paymentInfoData = getPaymentInfo(paymentDetailsId);

		final boolean isAlreadyDefaultPaymentInfo = paymentInfoData.isDefaultPaymentInfo();
		paymentInfoData.setAccountHolderName(null);
		paymentInfoData.setCardNumber(null);
		paymentInfoData.setCardType(null);
		paymentInfoData.setExpiryMonth(null);
		paymentInfoData.setExpiryYear(null);
		paymentInfoData.setDefaultPaymentInfo(false);
		paymentInfoData.setSaved(false);

		paymentInfoData.setIssueNumber(null);
		paymentInfoData.setStartMonth(null);
		paymentInfoData.setStartYear(null);
		paymentInfoData.setSubscriptionId(null);

		final AddressData address = paymentInfoData.getBillingAddress();
		address.setFirstName(null);
		address.setLastName(null);
		address.setCountry(null);
		address.setLine1(null);
		address.setLine2(null);
		address.setPostalCode(null);
		address.setRegion(null);
		address.setTitle(null);
		address.setTown(null);

		final Collection<PaymentInfoOption> options = new ArrayList<>();
		options.add(PaymentInfoOption.BASIC);
		options.add(PaymentInfoOption.BILLING_ADDRESS);

		getHttpRequestPaymentInfoPopulator().populate(request, paymentInfoData, options);
		validate(paymentInfoData, OBJECT_NAME_PAYMENT_DETAILS, getCcPaymentInfoValidator());

		getUserFacade().updateCCPaymentInfo(paymentInfoData);
		if (paymentInfoData.isSaved() && !isAlreadyDefaultPaymentInfo && paymentInfoData.isDefaultPaymentInfo())
		{
			getUserFacade().setDefaultPaymentInfo(paymentInfoData);
		}
	}

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PutMapping(value = "/{paymentDetailsId}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@Operation(operationId = "replacePaymentDetails", summary = "Updates existing customer's credit card payment info.", description =
			"Updates existing customer's credit card payment info based on the "
					+ "payment info ID. Attributes not given in request will be defined again (set to null or default).")
	@ApiBaseSiteIdAndUserIdParam
	@ResponseStatus(HttpStatus.OK)
	public void replacePaymentDetails(
			@Parameter(description = "Payment details identifier.", required = true) @PathVariable final String paymentDetailsId,
			@Parameter(description = "Payment details object.", required = true) @RequestBody final PaymentDetailsWsDTO paymentDetails)
	{
		final CCPaymentInfoData paymentInfoData = getPaymentInfo(paymentDetailsId);
		final boolean isAlreadyDefaultPaymentInfo = paymentInfoData.isDefaultPaymentInfo();

		validate(paymentDetails, OBJECT_NAME_PAYMENT_DETAILS, getPaymentDetailsDTOValidator());
		getDataMapper().map(paymentDetails, paymentInfoData,
				"accountHolderName,cardNumber,cardType,issueNumber,startMonth,expiryMonth,startYear,expiryYear,subscriptionId,defaultPaymentInfo,saved,billingAddress"
						+ "(firstName,lastName,titleCode,line1,line2,town,postalCode,region(isocode),country(isocode),defaultAddress)",
				true);

		getUserFacade().updateCCPaymentInfo(paymentInfoData);
		if (paymentInfoData.isSaved() && !isAlreadyDefaultPaymentInfo && paymentInfoData.isDefaultPaymentInfo())
		{
			getUserFacade().setDefaultPaymentInfo(paymentInfoData);
		}
	}
}
