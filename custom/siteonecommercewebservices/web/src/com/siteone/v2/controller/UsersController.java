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

import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.customergroups.CustomerGroupFacade;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.user.data.RegisterData;
import de.hybris.platform.commercefacades.user.data.UserGroupDataList;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commercewebservicescommons.dto.user.UserGroupListWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.user.UserSignUpWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.user.UserWsDTO;
import de.hybris.platform.commercewebservicescommons.errors.exceptions.RequestParameterException;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.webservicescommons.cache.CacheControl;
import de.hybris.platform.webservicescommons.cache.CacheControlDirective;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdAndUserIdParam;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;
import de.hybris.platform.webservicescommons.swagger.ApiFieldsParam;
import com.siteone.constants.YcommercewebservicesConstants;
import com.siteone.populator.HttpRequestCustomerDataPopulator;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriUtils;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Hidden;

@Controller
@RequestMapping(value = "/{baseSiteId}/users")
@CacheControl(directive = CacheControlDirective.PRIVATE)
@Tag(name = "Users")
@Hidden
public class UsersController extends BaseCommerceController
{
	private static final Logger LOG = LoggerFactory.getLogger(UsersController.class);

	@Resource(name = "wsCustomerFacade")
	private CustomerFacade customerFacade;
	@Resource(name = "wsCustomerGroupFacade")
	private CustomerGroupFacade customerGroupFacade;
	@Resource(name = "httpRequestCustomerDataPopulator")
	private HttpRequestCustomerDataPopulator httpRequestCustomerDataPopulator;
	@Resource(name = "HttpRequestUserSignUpDTOPopulator")
	private Populator<HttpServletRequest, UserSignUpWsDTO> httpRequestUserSignUpDTOPopulator;
	@Resource(name = "putUserDTOValidator")
	private Validator putUserDTOValidator;
	@Resource(name = "userSignUpDTOValidator")
	private Validator userSignUpDTOValidator;
	@Resource(name = "guestConvertingDTOValidator")
	private Validator guestConvertingDTOValidator;
	@Resource(name = "passwordStrengthValidator")
	private Validator passwordStrengthValidator;

	@Secured({ "ROLE_CLIENT", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	@ResponseBody
	@Operation(hidden = true, summary = " Registers a customer", description =
			"Registers a customer. There are two options for registering a customer. The first option requires "
					+ "the following parameters: login, password, firstName, lastName, titleCode. The second option converts a guest to a customer. In this case, the required parameters are: guid, password.")
	@ApiBaseSiteIdParam
	public UserWsDTO createUser(
			@Parameter(description = "Customer's login. Customer login is case insensitive.") @RequestParam(required = false) final String login,
			@Parameter(description = "Customer's password.", required = true) @RequestParam final String password,
			@Parameter(description = "Customer's title code. For a list of codes, see /{baseSiteId}/titles resource") @RequestParam(required = false) final String titleCode,
			@Parameter(description = "Customer's first name.") @RequestParam(required = false) final String firstName,
			@Parameter(description = "Customer's last name.") @RequestParam(required = false) final String lastName,
			@Parameter(description = "Guest order's guid.") @RequestParam(required = false) final String guid,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields,
			final HttpServletRequest httpRequest, final HttpServletResponse httpResponse) throws DuplicateUidException
	{
		final UserSignUpWsDTO user = new UserSignUpWsDTO();
		httpRequestUserSignUpDTOPopulator.populate(httpRequest, user);
		CustomerData customer = null;
		final String userId;
		if (guid != null)
		{
			validate(user, "user", guestConvertingDTOValidator);
			convertToCustomer(password, guid);
			customer = customerFacade.getCurrentCustomer();
			userId = customer.getUid();
		}
		else
		{
			validate(user, "user", userSignUpDTOValidator);
			registerNewUser(login, password, titleCode, firstName, lastName);
			userId = login.toLowerCase(Locale.ENGLISH);
			customer = customerFacade.getUserForUID(userId);
		}
		httpResponse.setHeader(YcommercewebservicesConstants.LOCATION, getAbsoluteLocationURL(httpRequest, userId));
		return getDataMapper().map(customer, UserWsDTO.class, fields);
	}

	protected void convertToCustomer(final String password, final String guid)
			throws DuplicateUidException
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("convertToCustomer: guid=" + sanitize(guid));
		}

		try
		{
			customerFacade.changeGuestToCustomer(password, guid);
		}
		catch (final UnknownIdentifierException | IllegalArgumentException ex)
		{
			/* IllegalArgumentException - occurs when order does not belong to guest user.
			For security reasons it's better to treat it as "unknown identifier" error */

			throw new RequestParameterException("Order with guid " + sanitize(guid) + " not found in current BaseStore",
					RequestParameterException.UNKNOWN_IDENTIFIER, "guid", ex);
		}
	}

	protected void registerNewUser(final String login, final String password, final String titleCode, final String firstName,
	                               final String lastName) throws DuplicateUidException
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("registerUser: login=" + sanitize(login));
		}

		if (!EmailValidator.getInstance().isValid(login))
		{
			throw new RequestParameterException("Login [" + sanitize(login) + "] is not a valid e-mail address!",
					RequestParameterException.INVALID, "login");
		}

		final RegisterData registerData = createRegisterData(login, password, titleCode, firstName, lastName);
		customerFacade.register(registerData);
	}

	private RegisterData createRegisterData(final String login, final String password, final String titleCode,
	                                        final String firstName, final String lastName)
	{
		final RegisterData registerData = new RegisterData();
		registerData.setFirstName(firstName);
		registerData.setLastName(lastName);
		registerData.setLogin(login);
		registerData.setPassword(password);
		registerData.setTitleCode(titleCode);
		return registerData;
	}


	@Secured({ "ROLE_CLIENT", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PostMapping( consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseStatus(value = HttpStatus.CREATED)
	@ResponseBody
	@Operation(operationId = "createUser", summary = " Registers a customer", description = "Registers a customer. Requires the following "
			+ "parameters: login, password, firstName, lastName, titleCode.")
	@ApiBaseSiteIdParam
	public UserWsDTO createUser(@Parameter(description = "User's object.", required = true) @RequestBody final UserSignUpWsDTO user,
	                            @ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields,
	                            final HttpServletRequest httpRequest, final HttpServletResponse httpResponse)
	{
		validate(user, "user", userSignUpDTOValidator);
		final RegisterData registerData = getDataMapper()
				.map(user, RegisterData.class, "login,password,titleCode,firstName,lastName");
		boolean userExists = false;
		try
		{
			customerFacade.register(registerData);
		}
		catch (final DuplicateUidException ex)
		{
			userExists = true;
			LOG.debug("Duplicated UID", ex);
		}
		final String userId = user.getUid().toLowerCase(Locale.ENGLISH);
		httpResponse.setHeader(YcommercewebservicesConstants.LOCATION, StringUtils.normalizeSpace(getAbsoluteLocationURL(httpRequest, userId))); //NOSONAR
		final CustomerData customerData = getCustomerData(registerData, userExists, userId);
		return getDataMapper().map(customerData, UserWsDTO.class, fields);
	}

	protected CustomerData getCustomerData(final RegisterData registerData, final boolean userExists, final String userId)
	{
		final CustomerData customerData;
		if (userExists)
		{
			customerData = customerFacade.nextDummyCustomerData(registerData);
		}
		else
		{
			customerData = customerFacade.getUserForUID(userId);
		}
		return customerData;
	}

	protected String getAbsoluteLocationURL(final HttpServletRequest httpRequest, final String uid)
	{
		final String requestURL = httpRequest.getRequestURL().toString();
		final StringBuilder absoluteURLSb = new StringBuilder(requestURL);
		if (!requestURL.endsWith(YcommercewebservicesConstants.SLASH))
		{
			absoluteURLSb.append(YcommercewebservicesConstants.SLASH);
		}
		absoluteURLSb.append(UriUtils.encodePathSegment(uid, StandardCharsets.UTF_8.name()));
		return absoluteURLSb.toString();
	}


	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/{userId}")
	@ResponseBody
	@Operation(operationId = "getUser", summary = "Get customer profile", description = "Returns customer profile.")
	@ApiBaseSiteIdAndUserIdParam
	public UserWsDTO getUser(@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		final CustomerData customerData = customerFacade.getCurrentCustomer();
		return getDataMapper().map(customerData, UserWsDTO.class, fields);
	}

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PutMapping("/{userId}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(hidden = true, summary = "Updates customer profile", description = "Updates customer profile. Attributes not provided in the request body will be defined again (set to null or default).")
	@Parameter(name = "baseSiteId", description = "Base site identifier", required = true, schema = @Schema(type = "string"), in = ParameterIn.PATH)
	@Parameter(name = "userId", description = "User identifier.", required = true, schema = @Schema(type = "string"), in = ParameterIn.PATH)
	@Parameter(name = "language", description = "Customer's language.", required = false, schema = @Schema(type = "string"), in = ParameterIn.QUERY)
	@Parameter(name = "currency", description = "Customer's currency.", required = false, schema = @Schema(type = "string"), in = ParameterIn.QUERY) 
	public void replaceUser(@Parameter(description = "Customer's first name.", required = true) @RequestParam final String firstName,
			@Parameter(description = "Customer's last name.", required = true) @RequestParam final String lastName,
			@Parameter(description = "Customer's title code. For a list of codes, see /{baseSiteId}/titles resource", required = false) @RequestParam final String titleCode,
			final HttpServletRequest request) throws DuplicateUidException
	{
		final CustomerData customer = customerFacade.getCurrentCustomer();
		if (LOG.isDebugEnabled())
		{
			LOG.debug("putCustomer: userId={}", customer.getUid());
		}
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setTitleCode(titleCode);
		customer.setLanguage(null);
		customer.setCurrency(null);
		httpRequestCustomerDataPopulator.populate(request, customer);

		customerFacade.updateFullProfile(customer);
	}

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PutMapping(value = "/{userId}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseStatus(HttpStatus.OK)
	@Operation(operationId = "replaceUser", summary = "Updates customer profile", description = "Updates customer profile. Attributes not provided in the request body will be defined again (set to null or default).")
	@ApiBaseSiteIdAndUserIdParam
	public void replaceUser(@Parameter(description = "User's object", required = true) @RequestBody final UserWsDTO user)
			throws DuplicateUidException
	{
		validate(user, "user", putUserDTOValidator);

		final CustomerData customer = customerFacade.getCurrentCustomer();
		if (LOG.isDebugEnabled())
		{
			LOG.debug("replaceUser: userId={}", customer.getUid());
		}

		getDataMapper().map(user, customer, "firstName,lastName,titleCode,currency(isocode),language(isocode)", true);
		customerFacade.updateFullProfile(customer);
	}

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PatchMapping("/{userId}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(hidden = true, summary = "Updates customer profile", description = "Updates customer profile. Only attributes provided in the request body will be changed.")
	@Parameter(name = "baseSiteId", description = "Base site identifier", required = true, schema = @Schema(type = "string"), in = ParameterIn.PATH)
	@Parameter(name = "userId", description = "User identifier", required = true, schema = @Schema(type = "string"), in = ParameterIn.PATH)
	@Parameter(name = "firstName", description = "Customer's first name", required = false, schema = @Schema(type = "string"), in = ParameterIn.QUERY)
	@Parameter(name = "lastName", description = "Customer's last name", required = false, schema = @Schema(type = "string"), in = ParameterIn.QUERY)
	@Parameter(name = "titleCode", description = "Customer's title code. Customer's title code. For a list of codes, see /{baseSiteId}/titles resource", required = false, schema = @Schema(type = "string"), in = ParameterIn.QUERY)
	@Parameter(name = "language", description = "Customer's language", required = false, schema = @Schema(type = "string"), in = ParameterIn.QUERY)
	@Parameter(name = "currency", description = "Customer's currency", required = false, schema = @Schema(type = "string"), in = ParameterIn.QUERY)
	public void updateUser(final HttpServletRequest request) throws DuplicateUidException
	{
		final CustomerData customer = customerFacade.getCurrentCustomer();
		if (LOG.isDebugEnabled())
		{
			LOG.debug("updateUser: userId={}", customer.getUid());
		}
		httpRequestCustomerDataPopulator.populate(request, customer);
		customerFacade.updateFullProfile(customer);
	}

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PatchMapping(value = "/{userId}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseStatus(HttpStatus.OK)
	@Operation(operationId = "updateUser", summary = "Updates customer profile", description = "Updates customer profile. Only attributes provided in the request body will be changed.")
	@ApiBaseSiteIdAndUserIdParam
	public void updateUser(@Parameter(description = "User's object.", required = true) @RequestBody final UserWsDTO user)
			throws DuplicateUidException
	{
		final CustomerData customer = customerFacade.getCurrentCustomer();
		if (LOG.isDebugEnabled())
		{
			LOG.debug("updateUser: userId={}", customer.getUid());
		}

		getDataMapper().map(user, customer, "firstName,lastName,titleCode,currency(isocode),language(isocode)", false);
		customerFacade.updateFullProfile(customer);
	}

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(operationId = "removeUser", summary = "Delete customer profile.", description = "Removes customer profile.")
	@ApiBaseSiteIdAndUserIdParam
	public void removeUser()
	{
		final CustomerData customer = customerFacade.closeAccount();
		if (LOG.isDebugEnabled())
		{
			LOG.debug("removeUser: userId={}", customer.getUid());
		}
	}

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PutMapping("/{userId}/login")
	@ResponseStatus(HttpStatus.OK)
	@Operation(operationId = "replaceUserLogin", summary = "Changes customer's login name.", description = "Changes a customer's login name. Requires the customer's current password.")
	@ApiBaseSiteIdAndUserIdParam
	public void replaceUserLogin(
			@Parameter(description = "Customer's new login name. Customer login is case insensitive.", required = true) @RequestParam final String newLogin,
			@Parameter(description = "Customer's current password.", required = true) @RequestParam final String password)
			throws DuplicateUidException
	{
		if (!EmailValidator.getInstance().isValid(newLogin))
		{
			throw new RequestParameterException("Login [" + newLogin + "] is not a valid e-mail address!",
					RequestParameterException.INVALID, "newLogin");
		}
		customerFacade.changeUid(newLogin, password);
	}

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PutMapping("/{userId}/password")
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	@Operation(operationId = "replaceUserPassword", summary = "Changes customer's password", description = "Changes customer's password.")
	@ApiBaseSiteIdAndUserIdParam
	public void replaceUserPassword(@Parameter(description = "User identifier.", required = true) @PathVariable final String userId,
			@Parameter(description = "Old password. Required only for ROLE_CUSTOMERGROUP") @RequestParam(required = false) final String old,
			@Parameter(description = "New password.", required = true) @RequestParam(value = "new") final String newPassword)
	{
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final UserSignUpWsDTO customer = new UserSignUpWsDTO();
		customer.setPassword(newPassword);
		validate(customer, "password", passwordStrengthValidator);
		if (containsRole(auth, "ROLE_TRUSTED_CLIENT") || containsRole(auth, "ROLE_CUSTOMERMANAGERGROUP"))
		{
			customerFacade.setPassword(userId, newPassword);
		}
		else
		{
			if (StringUtils.isEmpty(old))
			{
				throw new RequestParameterException("Request parameter 'old' is missing.", RequestParameterException.MISSING, "old");
			}
			customerFacade.changePassword(old, newPassword);
		}
	}

	protected boolean containsRole(final Authentication auth, final String role)
	{
		for (final GrantedAuthority ga : auth.getAuthorities())
		{
			if (ga.getAuthority().equals(role))
			{
				return true;
			}
		}
		return false;
	}

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/{userId}/customergroups")
	@Operation(operationId = "getUserCustomerGroups", summary = "Get all customer groups of a customer.", description = "Returns all customer groups of a customer.")
	@ApiBaseSiteIdAndUserIdParam
	@ResponseBody
	public UserGroupListWsDTO getUserCustomerGroups(
			@Parameter(description = "User identifier.", required = true) @PathVariable final String userId,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		final UserGroupDataList userGroupDataList = new UserGroupDataList();
		userGroupDataList.setUserGroups(customerGroupFacade.getCustomerGroupsForUser(userId));
		return getDataMapper().map(userGroupDataList, UserGroupListWsDTO.class, fields);
	}
}
