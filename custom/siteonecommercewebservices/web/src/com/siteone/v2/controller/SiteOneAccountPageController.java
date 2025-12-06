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
package com.siteone.v2.controller;

import de.hybris.platform.acceleratorservices.config.SiteConfigService;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.B2BUserFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.i18n.I18NFacade;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.order.OrderFacade;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.TitleData;
import de.hybris.platform.commercefacades.user.exceptions.PasswordMismatchException;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commercewebservicescommons.dto.order.OrderWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.product.ProductWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.user.UserWsDTO;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.servicelayer.user.exceptions.PasswordPolicyViolationException;
import de.hybris.platform.util.Config;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Collection;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.siteone.utils.XSSFilterUtil;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.siteone.commerceservices.account.data.InvoiceDetailsWsDTO;
import com.siteone.commerceservices.account.data.InvoiceResultsDTO;
import com.siteone.commerceservices.account.data.SearchPageDTO;
import com.siteone.commerceservices.account.data.UpdateProfileWsDTO;
import com.siteone.commerceservices.dto.account.AccountPartnerProgramDTO;
import com.siteone.commerceservices.dto.account.AccountDashboardWsDTO;
import com.siteone.commerceservices.dto.account.AccountInformationWsDTO;
import com.siteone.commerceservices.dto.account.AddressBookWsDTO;
import com.siteone.commerceservices.dto.account.SiteOneAddressFormWsDTO;
import com.siteone.commerceservices.dto.account.TitleDataListDTO;
import com.siteone.commerceservices.dto.account.UpdateSiteonePreferenceDTO;
import com.siteone.commerceservices.dto.company.B2BUnitWsDTO;
import com.siteone.commerceservices.dto.company.SearchPageB2BUnitWsDTO;
import com.siteone.commerceservices.address.dto.SiteOneAddressVerificationWsDTO;
import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.facades.address.data.SiteOneAddressVerificationData;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.customer.SiteOneOktaFacade;
import com.siteone.facades.ewallet.SiteOneEwalletFacade;
import com.siteone.facades.exceptions.AddressNotCreatedOrUpdatedInUEException;
import com.siteone.facades.exceptions.AddressNotRemovedInUEException;
import com.siteone.facades.exceptions.ContactNotCreatedOrUpdatedInUEException;
import com.siteone.facades.exceptions.PdfNotAvailableException;
import com.siteone.facades.exceptions.RecentlyUsedPasswordException;
import com.siteone.facades.exceptions.ServiceUnavailableException;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import de.hybris.platform.core.model.user.CustomerModel;
import com.siteone.forms.UpdatePasswordForm;
import com.siteone.facade.InvoiceData;
import com.siteone.facades.customer.SiteOneOktaFacade;
import com.siteone.facade.customer.info.SiteOneCustInfoData;
import com.siteone.facade.email.SiteOneShareEmailFacade;
import com.siteone.facade.invoice.InvoiceFacade;
import com.siteone.facade.order.SiteOneOrderFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.commerceservices.dto.account.AccountOverviewWsDTO;

import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.customer.data.CustomFields;
import com.siteone.integration.customer.data.UserEmailData;
import com.siteone.integration.invoice.order.data.SiteoneInvoiceRequestData;
import com.siteone.integration.okta.data.SiteOneOktaSessionData;
import com.siteone.integration.services.dataimport.SiteOneBlobDataImportService;
import com.siteone.utils.SiteOneAddressDataUtil;
import com.siteone.utils.SiteOneAddressForm;
import com.siteone.integration.azure.pushnotification.SendNotificationApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siteone.commerceservices.dto.user.CustomerSpecificWsDTO;

import com.windowsazure.messaging.NotificationHub;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;

@Controller
@RequestMapping(value = "/{baseSiteId}/my-account")
@Tag(name = "Siteone My Account")
public class SiteOneAccountPageController extends AbstractSearchPageController
{
	private static final Logger LOG = Logger.getLogger(SiteOneAccountPageController.class);
	
	private static final String PAGINATION_NUMBER_OF_RESULTS_COUNT = "pagination.number.results.count";
	
	private static final String INVOICE_NUMBER_PATH_VARIABLE_PATTERN = "{invoiceNumber:.*}"; 
	
	private static final String ORDER_ID_PATH_VARIABLE_PATTERN = "{orderShipmentActualId:.*}";

	private static final String UNIT_ID_PATH_VARIABLE_PATTERN = "{unitId:.*}";
	
	private static final String ADDRESS_CODE_PATH_VARIABLE_PATTERN = "{addressCode:.*}";
	
	private static final String DEFAULT_DATEFORMAT = "MM/dd/yyyy";

	public static final int MAX_PAGE_LIMIT = 100; // should be configured
	
	public static final String SUCCESS = "Success";
	
	private static final String ACCOUNT_CMS_PAGE = "account";
	
	private static final String RESELLER_TRADECLASS = "204029";
	private static final String GOVERNMENT_TRADECLASS = "26000";
	private static final String RETAIL_TRADECLASS = "409033";
	private static final String EXCEPTION = "Exception occurred while calling the method";
	
	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;
	
	@Resource(name = "siteConfigService")
	private SiteConfigService siteConfigService;
	
	@Resource(name = "siteOneShareEmailFacade")
	private SiteOneShareEmailFacade siteOneShareEmailFacade;
	
	@Resource(name = "b2BCustomerConverter")
	private Converter<CustomerModel, CustomerData> b2BCustomerConverter;
	
	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;
	
	@Resource(name = "b2bUnitFacade")
	protected B2BUnitFacade b2bUnitFacade;

	@Resource(name = "orderFacade")
	private OrderFacade orderFacade;
	
	@Resource(name = "i18nService")
	protected I18NService i18nService;
	
	@Resource(name = "invoiceFacade")
	private InvoiceFacade invoiceFacade;
	
	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;
	
	@Resource(name = "userFacade")
	private UserFacade userFacade;
	
	@Resource(name = "acceleratorCheckoutFacade")
	private CheckoutFacade checkoutFacade;
	
	@Resource(name = "i18NFacade")
	private I18NFacade i18NFacade;
	
	@Resource(name = "defaultSiteoneAddressConverter")
	private SiteOneAddressDataUtil siteoneAddressDataUtil;
	
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;	
	@Resource(name = "storeFinderFacade")
	private SiteOneStoreFinderFacade storeFinderFacade;
	@Resource(name = "siteOneEwalletFacade")
	private SiteOneEwalletFacade siteOneEwalletFacade;
	@Resource(name = "siteOneOktaFacade")
	private SiteOneOktaFacade siteOneOktaFacade;
	@Resource(name = "sessionService")
	private SessionService sessionService;
	@Resource(name = "sendNotificationApi")
	private SendNotificationApi sendNotificationApi;
	
	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
	
	@Resource(name = "blobDataImportService")
	private SiteOneBlobDataImportService blobDataImportService;
	
	public I18NFacade getI18NFacade() {
		return i18NFacade;
	}

	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/all-ship-to/{unitId}")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "all-ship-to", summary = "Get all the ShipTo for a account", description = "Get all the ShipTo for a account")
	public SearchPageB2BUnitWsDTO getShipToPage(@PathVariable("unitId") final String unitId,
			@RequestParam(value = "searchParam", required = false) final String searchParam,
			@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "sort", defaultValue = B2BUnitModel.NAME) final String sortCode)
	{
		try
		{
			String trimmedSearchParam = null;
			if (null != searchParam)
			{
				trimmedSearchParam = searchParam.trim();
			}
			final PageableData pageableData = createPageableData(page, this.getSearchPageSize(), sortCode, showMode);
			final SearchPageData<B2BUnitData> searchPageData = ((SiteOneCustomerFacade) customerFacade)
					.getPagedB2BUnits(pageableData, unitId, trimmedSearchParam);
			
			final int numberPagesShown = siteConfigService.getInt(PAGINATION_NUMBER_OF_RESULTS_COUNT, 5);

			SearchPageB2BUnitWsDTO result = new SearchPageB2BUnitWsDTO();
			result.setPagination(searchPageData.getPagination());
			result.setSorts(searchPageData.getSorts());
			MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
			mapperFactory.classMap(B2BUnitData.class, B2BUnitWsDTO.class);
		    MapperFacade mapper = mapperFactory.getMapperFacade();
		    List<B2BUnitWsDTO> b2bUnitListDTO = mapper.mapAsList(searchPageData.getResults(), B2BUnitWsDTO.class);
			result.setResults(b2bUnitListDTO);
			
			return result;
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" all-Ship-To");
		}
	}
	

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/listofshipto")
	@ApiBaseSiteIdParam
	@Operation(operationId = "shipTO", summary = "Get the selected shipTO details", description = "Get the selected shipTO details")
	public @ResponseBody List<B2BUnitWsDTO> getListOfShipTo(@RequestParam(value = "unitId") final String unitId)
	{
		try
		{
			final List<B2BUnitData> childs = ((SiteOneB2BUnitFacade) b2bUnitFacade).getShipTosForUnitBasedOnCustomer(unitId);
			if (CollectionUtils.isNotEmpty(childs))
			{
				final Gson gson = new Gson();
				MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
				mapperFactory.classMap(B2BUnitData.class, B2BUnitWsDTO.class);
			    MapperFacade mapper = mapperFactory.getMapperFacade();
			    List<B2BUnitWsDTO> b2bUnitListDTO = mapper.mapAsList(childs, B2BUnitWsDTO.class);
				return b2bUnitListDTO;
			}
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" listOfShipTo");
		}
		return null;
	}


	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/ship-to/{unitId}")
	@ApiBaseSiteIdParam
	@Operation(operationId = "shipTo", summary = "Get the selected shipTO details", description = "Get the selected shipTO details")
	public @ResponseBody AccountDashboardWsDTO getShipToDetails(@PathVariable("unitId") final String unitId)
	{
		try
		{
			Gson gson = new Gson();
			B2BUnitData unit = b2bUnitFacade.getUnitForUid(unitId);
			PointOfServiceData b2bUnitStore = (unit.getHomeBranch() != null ? storeFinderFacade.getStoreForId(unit.getHomeBranch()) : null);
			MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
			MapperFacade mapper = mapperFactory.getMapperFacade();
			mapperFactory.classMap(B2BUnitData.class,B2BUnitWsDTO.class);
			B2BUnitWsDTO unitWsDTO = mapper.map(unit, B2BUnitWsDTO.class);
			final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
			unitWsDTO.setHomeBranchName((b2bUnitStore != null ? b2bUnitStore.getName() : ""));
			AccountDashboardWsDTO accountWsDTO = new AccountDashboardWsDTO();
			accountWsDTO.setUnit(unitWsDTO);
			return accountWsDTO;
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" getShipToDetails");
		}
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PostMapping("/account-dashboard")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "account-dashboard", summary = "Get Account Dashboard details", description = "Get Account Dashboard details")
	public AccountDashboardWsDTO getAccountDashboardPage(@Parameter(description = "Register object", required = false) @RequestBody UpdatePasswordForm updatePasswordForm,
			@RequestParam(value = "storeId",required = false) final String storeId)
	{
		if(null != storeId) {
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);
		}
		try
		{
			B2BUnitData unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
			MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
			MapperFacade mapper = mapperFactory.getMapperFacade();
			mapperFactory.classMap(B2BUnitData.class,B2BUnitWsDTO.class);
			B2BUnitWsDTO unitWsDTO = mapper.map(unit, B2BUnitWsDTO.class);
			final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
			final CustomerData customerData = b2BCustomerConverter.convert(customer);
			mapperFactory.classMap(CustomerData.class,UserWsDTO.class);
			UserWsDTO userWsDTO = mapper.map(customerData, UserWsDTO.class);
			String customerType =  ((SiteOneCustomerFacade) customerFacade).getCustomerTypeByOrderCreation();
			SiteOneOktaSessionData sessionData = null;
			if (null != updatePasswordForm && null != updatePasswordForm.getCurrentPassword() && !(updatePasswordForm.getCurrentPassword().equals("")))	{
				sessionData = siteOneOktaFacade.doAuth(customer.getUid(), updatePasswordForm.getCurrentPassword());
				if (null!= sessionData && null != sessionData.getIsBTApp())
				{
					sessionService.setAttribute(SiteoneCoreConstants.OKTA_ISBT_APP, sessionData.getIsBTApp());
				}
				if (null!= sessionData && null != sessionData.getIsProjectServicesApp())
				{
					sessionService.setAttribute(SiteoneCoreConstants.OKTA_ISPS_APP, sessionData.getIsProjectServicesApp());
				}				
			}
			final Boolean projectServices = ((SiteOneCustomerFacade) customerFacade).isHavingProjectServices();
			final Boolean contPayBillOnline = ((SiteOneCustomerFacade) customerFacade).isHavingPayBillOnline();
			final Boolean acctPayBillOnline = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer().getPayBillOnline();
			AccountDashboardWsDTO accountWsDTO = new AccountDashboardWsDTO();
			if (null != acctPayBillOnline && null != contPayBillOnline && acctPayBillOnline.booleanValue()
					&& contPayBillOnline.booleanValue() && null!= sessionData && null != sessionData.getSessionToken())
			{
				final String billTrustUrl = Config.getString(SiteoneintegrationConstants.OKTA_REDIRECT_URL_PART1, StringUtils.EMPTY)+ sessionData.getSessionToken() + Config.getString(SiteoneintegrationConstants.OKTA_REDIRECT_URL_PART2, StringUtils.EMPTY);
				accountWsDTO.setBillTrustUrl(billTrustUrl);
			}
			String partnerProgramUrl = "";
			Boolean isUserUpdated = Boolean.FALSE;
			
			String oktaEndPoint = Config.getString(SiteoneintegrationConstants.OKTA_API_BASE_URL, StringUtils.EMPTY)
					+ Config.getString(SiteoneintegrationConstants.NEXT_LEVEL_ENDPOINT, StringUtils.EMPTY)
					+ Config.getString(SiteoneintegrationConstants.OKTA_NXTLEVEL_APPNAME, StringUtils.EMPTY)
					+ Config.getString(SiteoneintegrationConstants.OKTA_NXTLEVEL_GROUPID, StringUtils.EMPTY)+"/"
					+ Config.getString(SiteoneintegrationConstants.OKTA_NXTLEVEL_CLIENTID, StringUtils.EMPTY);	
			
			if (null!= sessionData && null != sessionData.getSessionToken() && null != sessionData.getIsNextLevelApp() && sessionData.getIsNextLevelApp().equals(Boolean.TRUE))		
			{
				partnerProgramUrl = Config.getString(SiteoneintegrationConstants.OKTA_REDIRECT_URL_PART1, StringUtils.EMPTY)+ 
						sessionData.getSessionToken() + "&redirectUrl=" + oktaEndPoint ;
			}
			else 
			{
				isUserUpdated = ((SiteOneCustomerFacade) customerFacade).updateOktaCustomerProfile();
				if(isUserUpdated.equals(Boolean.TRUE))
				{
				partnerProgramUrl = Config.getString(SiteoneintegrationConstants.OKTA_REDIRECT_URL_PART1, StringUtils.EMPTY)+ 
						sessionData.getSessionToken() + "&redirectUrl=" + oktaEndPoint ;
			    }
			}
			accountWsDTO.setPartnersProgramUrl(partnerProgramUrl);
			Boolean quoteFeature = false;
			final B2BUnitModel unit_fs = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
			if (siteOneFeatureSwitchCacheService.isAllPresentUnderFeatureSwitch("QuotesFeatureSwitch")
					|| (null != unit_fs && null != unit_fs.getUid()
							&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("QuotesFeatureSwitch", unit_fs.getUid())))
			{
				quoteFeature = true;
			}
			if(!unit_fs.getActive())
			{
				accountWsDTO.setIsCreditCodeValid(true);
			}
			else
			{
				accountWsDTO.setIsCreditCodeValid(((SiteOneCustomerFacade) customerFacade).isCreditCodeValid(customer.getUid()));
				accountWsDTO.setIsPayBillEnabled(((SiteOneCustomerFacade) customerFacade).isPayBillEnabled(customer.getUid()));
			}
			accountWsDTO.setUnit(unitWsDTO);
			accountWsDTO.setCustomerType(customerType);
			accountWsDTO.setCustomer(userWsDTO);
			final ExtendedModelMap model = new ExtendedModelMap();
			siteOneEwalletFacade.populatePaymentconfig(ACCOUNT_CMS_PAGE, model);
			accountWsDTO.setShowManageEwallet((Boolean)model.get("showManageEwallet"));
			accountWsDTO.setIsProjectServices(projectServices);
			accountWsDTO.setContPayBillOnline(contPayBillOnline);
			accountWsDTO.setAcctPayBillOnline(acctPayBillOnline);
			accountWsDTO.setIsAdmin(((SiteOneB2BUnitService) b2bUnitService).isAdminUser());
			accountWsDTO.setEncryptedEmail(((SiteOneCustomerFacade) customerFacade).encrypt(customer.getUid(), Config.getString("encryption.aes.secret", null)));
			accountWsDTO.setIsQuotesEnabled(quoteFeature);
			return accountWsDTO;
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" accountDashboard");
		}
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/ordersInTransit")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "ordersInTransit", summary = "Get Orders in Transit", description = "Get Orders in Transit")
	public AccountDashboardWsDTO getOrdersInTransit(@RequestParam(value = "storeId") final String storeId) 
	{
		try
		{
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);
			List<OrderData> ordersInTransit = ((SiteOneOrderFacade) orderFacade).ordersInTransit();
			List<OrderWsDTO> orderListWsDTO = getDataMapper().mapAsList(ordersInTransit, OrderWsDTO.class, "FULL");
			
			final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
			final CustomerData customerData = b2BCustomerConverter.convert(customer);
			UserWsDTO userWsDTO = getDataMapper().map(customerData, UserWsDTO.class, "FULL");
			AccountDashboardWsDTO accountWsDTO = new AccountDashboardWsDTO();
			accountWsDTO.setOrdersInTransit(orderListWsDTO);
			accountWsDTO.setCustomer(userWsDTO);
			return accountWsDTO;
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" ordersinTransit");
		}
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/account-information/{unitId}")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "account-information", summary = "Get Account Information", description = "Get Account Information details")
	public AccountInformationWsDTO getAccountInformationPage(@PathVariable("unitId") final String unitId)
	{
		try
		{
			final String sanitizedunitId = XSSFilterUtil.filter(unitId);
			//To get Company information and admin
			B2BUnitData unit = b2bUnitFacade.getUnitForUid(sanitizedunitId);
			Set<CustomerData> admin = ((SiteOneB2BUnitFacade) b2bUnitFacade).getAdminUserForUnit(sanitizedunitId);
			AccountInformationWsDTO accountInfoWsDTO = new AccountInformationWsDTO();
			MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
			MapperFacade mapper = mapperFactory.getMapperFacade();
			mapperFactory.classMap(B2BUnitData.class,B2BUnitWsDTO.class);
			B2BUnitWsDTO unitWsDTO = mapper.map(unit, B2BUnitWsDTO.class);
			
			List<CustomerData> adminListData = new ArrayList<CustomerData>(); 
			adminListData.addAll(admin); 
			List<UserWsDTO> adminListWsData = mapper.mapAsList(adminListData, UserWsDTO.class);
			accountInfoWsDTO.setUnit(unitWsDTO);
			accountInfoWsDTO.setAdmin(adminListWsData);
			return accountInfoWsDTO;
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" accountInformation");
		}
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/address-book/" + UNIT_ID_PATH_VARIABLE_PATTERN)
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "address-book", summary = "Get Saved Addresses for the account", description = "Get Account Addresses")
	public AddressBookWsDTO getAddressBook(@PathVariable("unitId") final String unitId)
	{
		try
		{
			final String sanitizedunitId = XSSFilterUtil.filter(unitId);
			final Collection<AddressData> deliveryAddresses = ((SiteOneCustomerFacade) customerFacade).getShippingAddresssesForUnit(sanitizedunitId);
			return getAddressBookDTO(deliveryAddresses, sanitizedunitId);
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" addressBook");
		}
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PostMapping("/validate-address")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "validate-address", summary = "Validate the Address", description = "Validate the Address")
	public SiteOneAddressVerificationWsDTO validateAddress(final SiteOneAddressForm addressForm, final String fields)
	{
		try
		{
			final AddressData addressData = siteoneAddressDataUtil.convertToAddressData(addressForm);
			SiteOneAddressVerificationData addressVerificationData = ((SiteOneCustomerFacade) customerFacade).validateAddress(addressData);
			return getDataMapper().map(addressVerificationData, SiteOneAddressVerificationWsDTO.class, fields);
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" validateAddress");
		}
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/add-address/" + UNIT_ID_PATH_VARIABLE_PATTERN)
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "add address form", summary = "Get the add address form", description = "Get Address form")
	public SiteOneAddressFormWsDTO addAddress(@PathVariable("unitId") final String unitId)
	{
		try
		{
			final CustomerData customerData = customerFacade.getCurrentCustomer();
			if (customerData.getRoles().contains("b2badmingroup") || (customerData.getEnableAddModifyDeliveryAddress()))
			{
				final SiteOneAddressForm addressForm = new SiteOneAddressForm();
				addressForm.setFirstName(customerData.getFirstName());
				addressForm.setLastName(customerData.getLastName());
				addressForm.setTitleCode(customerData.getTitleCode());
				return siteoneAddressDataUtil.convertToAddressFormDTO(addressForm);
			}
			else
			{
				return null;
			}
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" addAdrress-Get");
		}
	}

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PostMapping("/add-address/" + UNIT_ID_PATH_VARIABLE_PATTERN)
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "Add address", summary = "Add the address", description = "Add Address for customer")
	public AddressBookWsDTO addAddress(@PathVariable("unitId") final String unitId, final SiteOneAddressForm addressForm)
	{
		try
		{
			final String sanitizedunitId = XSSFilterUtil.filter(unitId);
		    final AddressData newAddress = siteoneAddressDataUtil.convertToVisibleAddressData(addressForm);
		    if (((SiteOneCustomerFacade) customerFacade).isAddressBookEmpty(addressForm.getUnitId()))
			{
				newAddress.setDefaultAddress(true);
			}
		    else
			{
				newAddress.setDefaultAddress(addressForm.getDefaultAddress() != null && addressForm.getDefaultAddress().booleanValue());
			}
		    ((SiteOneCustomerFacade) customerFacade).addAddress(newAddress, addressForm.getUnitId());
			if(BooleanUtils.isTrue(newAddress.isDefaultAddress()))
			{
				((SiteOneCustomerFacade) customerFacade).setDefaultAddress(newAddress.getId(), unitId);
			}
			final Collection<AddressData> deliveryAddresses = ((SiteOneCustomerFacade) customerFacade).getShippingAddresssesForUnit(sanitizedunitId);
			return getAddressBookDTO(deliveryAddresses, sanitizedunitId);
		}
		catch (final ServiceUnavailableException serviceUnavailableException)
		{
			LOG.error("Service unavailable exception while adding address from UE" + serviceUnavailableException);
			throw serviceUnavailableException;
		}
		catch (final AddressNotCreatedOrUpdatedInUEException addressNotCreatedOrUpdatedInUEException)
		{
			LOG.error("Address not created in UE exception while adding address from UE" + addressNotCreatedOrUpdatedInUEException);
			throw addressNotCreatedOrUpdatedInUEException;
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" addAdrress-Post");
		}

	}

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/edit-address/" + UNIT_ID_PATH_VARIABLE_PATTERN + "/"
	+ ADDRESS_CODE_PATH_VARIABLE_PATTERN)
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "edit-address", summary = "Get Edit Address Page", description = "Get Page for Editing Addresses")
	public SiteOneAddressFormWsDTO editAddress(@PathVariable("addressCode")
	final String addressCode, @PathVariable("unitId") final String unitId)
	{
		try
		{
			final CustomerData customerData = customerFacade.getCurrentCustomer();
			final SiteOneAddressForm addressForm = new SiteOneAddressForm();
			if (customerData.getRoles().contains("b2badmingroup") || (customerData.getEnableAddModifyDeliveryAddress()))
			{
				final Collection<AddressData> addressBook = ((SiteOneCustomerFacade) customerFacade)
						.getShippingAddresssesForUnit(unitId);

				for (final AddressData addressData : addressBook)
				{
					if (addressData.getId() != null && addressData.getId().equals(addressCode))
					{
						siteoneAddressDataUtil.convert(addressData, addressForm);
						addressForm.setUnitId(addressForm.getUnitId());
						if (BooleanUtils.isTrue(addressData.getDefaultUserAddress()))
						{
							addressForm.setDefaultAddress(Boolean.TRUE);
						}
						else
						{
							addressForm.setDefaultAddress(Boolean.FALSE);
						}
						break;
					}
				}
				return siteoneAddressDataUtil.convertToAddressFormDTO(addressForm);
			}
			else
			{
				return null;
			}
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" editAdrress-Get");
		}
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PostMapping("/edit-address/" + UNIT_ID_PATH_VARIABLE_PATTERN + "/"
			+ ADDRESS_CODE_PATH_VARIABLE_PATTERN)
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "edit-address", summary = "Edit the Address", description = "Edit the selected address")
	public AddressBookWsDTO editAddress(@PathVariable("addressCode")
	final String addressCode, @PathVariable("unitId") final String unitId, final SiteOneAddressForm addressForm)
	{
		try
		{
			final String sanitizedunitId = XSSFilterUtil.filter(unitId);
			final AddressData newAddress = siteoneAddressDataUtil.convertToVisibleAddressData(addressForm);
			final Collection<AddressData> unitAddresses = ((SiteOneCustomerFacade) customerFacade)
					.getShippingAddresssesForUnit(addressForm.getUnitId());

			if (CollectionUtils.isEmpty(unitAddresses))
			{
				newAddress.setDefaultAddress(true);
			}
			((SiteOneCustomerFacade) customerFacade).editAddress(newAddress, unitId);
			if(Boolean.TRUE.equals(addressForm.getDefaultAddress()))
			{
				((SiteOneCustomerFacade) customerFacade).setDefaultAddress(addressCode, unitId);
			}
			final Collection<AddressData> deliveryAddresses = ((SiteOneCustomerFacade) customerFacade).getShippingAddresssesForUnit(sanitizedunitId);
			return getAddressBookDTO(deliveryAddresses, sanitizedunitId);
		}
		catch (final ServiceUnavailableException serviceUnavailableException)
		{
			LOG.error("Service unavailable exception while editing address" + serviceUnavailableException);
			throw serviceUnavailableException;
		}
		catch (final AddressNotCreatedOrUpdatedInUEException addressNotCreatedOrUpdatedInUEException)
		{
			LOG.error("Address not edited in UE exception while editing address in UE" + addressNotCreatedOrUpdatedInUEException);
			throw addressNotCreatedOrUpdatedInUEException;
		}
		catch (final AddressNotRemovedInUEException addressNotRemovedInUEException)
		{
			LOG.error("Address not removed in UE exception while editing address from UE" + addressNotRemovedInUEException);
			throw addressNotRemovedInUEException;
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" editAddress-Post");
		}
				
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PostMapping("/set-default-address/" + UNIT_ID_PATH_VARIABLE_PATTERN + "/"
			+ ADDRESS_CODE_PATH_VARIABLE_PATTERN)
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "Default Address", summary = "Set Default Address", description = "Set the address as default for Customer")
	public AddressBookWsDTO setDefaultAddress(@PathVariable("addressCode") final String addressCode, @PathVariable("unitId") final String unitId)
	{
		try
		{
			final String sanitizedunitId = XSSFilterUtil.filter(unitId);
			((SiteOneCustomerFacade) customerFacade).setDefaultAddress(addressCode, unitId);
			final Collection<AddressData> deliveryAddresses = ((SiteOneCustomerFacade) customerFacade).getShippingAddresssesForUnit(sanitizedunitId);
			return getAddressBookDTO(deliveryAddresses, sanitizedunitId);
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" setDefaultAddress");
		}
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PostMapping("/remove-address/" + ADDRESS_CODE_PATH_VARIABLE_PATTERN + "/" + UNIT_ID_PATH_VARIABLE_PATTERN)
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "Remove Address", summary = "Remove selected Address", description = "Remove the selected address for the Customer")
	public AddressBookWsDTO removeAddress(@PathVariable("addressCode") final String addressCode, @PathVariable("unitId") final String unitId)
	{
		final String sanitizedunitId = XSSFilterUtil.filter(unitId);
		final CustomerData customerData = customerFacade.getCurrentCustomer();
		if (customerData.getRoles().contains("b2badmingroup") || (customerData.getEnableAddModifyDeliveryAddress()))
		{
			final AddressData addressData = new AddressData();
			addressData.setId(addressCode);
			try
			{
				((SiteOneCustomerFacade) customerFacade).removeAddress(addressData, unitId);
				final Collection<AddressData> deliveryAddresses = ((SiteOneCustomerFacade) customerFacade).getShippingAddresssesForUnit(sanitizedunitId);
				return getAddressBookDTO(deliveryAddresses, sanitizedunitId);
			}
			catch (final ServiceUnavailableException serviceUnavailableException)
			{
				LOG.error("Service unavailable exception while removing address from UE" + serviceUnavailableException);
				throw serviceUnavailableException;
			}
			catch (final AddressNotRemovedInUEException addressNotRemovedInUEException)
			{
				LOG.error("Address not removed in UE exception while removing address from UE" + addressNotRemovedInUEException);
				throw addressNotRemovedInUEException;
			}
			catch (final Exception ex)
			{
				LOG.error(ex.getMessage(), ex);
				throw new RuntimeException(EXCEPTION +" removeAddress");
			}
		}
		else
		{
			return null;
		}
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/invoices/" + UNIT_ID_PATH_VARIABLE_PATTERN)
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "invoices", summary = "get all the invoices", description = "get all the invoices")
	public InvoiceResultsDTO getInvoiceListingPage(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "sort", required = false,defaultValue = "InvoiceDate") String sortCode, @PathVariable("unitId") final String unitId,
			@RequestParam(value = "searchParam", required = false) final String searchParam,
			@RequestParam(value = "oSearchParam", required = false)final String oSearchParam, 
			@RequestParam(value = "iSearchParam", required = false)final String iSearchParam, 
			@RequestParam(value = "pnSearchParam", required = false)final String pnSearchParam,
			@RequestParam(value = "dateFrom", required = false) String dateFrom,
			@RequestParam(value = "pagesize", required = false) final String pageSize,
			@RequestParam(value = "dateTo", required = false) String dateTo,
			@RequestParam(value = "datarange", required = false) final String datarange,
			@RequestParam(value = "invoiceShiptos", required = false) final String invoiceShiptos) 
	{

		final SiteoneInvoiceRequestData requestData = new SiteoneInvoiceRequestData();
		int viewByPageSize = siteConfigService.getInt("siteone.invoicedefault.pageSize", 25);
		if (sortCode.equalsIgnoreCase("byDate"))
		{
			sortCode = "InvoiceDate";
		}
		final Calendar f = Calendar.getInstance();
		f.setTime(new Date());
		f.add(Calendar.MONTH, -3);


		String daysArgs = null;
		daysArgs = datarange;

		String oneTwentyStrDate = null;
		String nowStrDate = null;
		Date datenow = null;
		Date dateoneTwenty = null;
		final LocalDate now = LocalDate.now();
		if (((SiteOneCustomerFacade) customerFacade).isHavingInvoicePermissions())
		{
			final Locale currentLocale = i18nService.getCurrentLocale();
			final String formatString = DEFAULT_DATEFORMAT;
			final DateFormat dateFormat = new SimpleDateFormat(formatString, currentLocale);
			Date dateFromFinal = null, dateToFinal = null;
			String dateFromInvoice = null, dateToInvoice = null;

				try
				{
					datenow = new Date();
					dateoneTwenty = new Date();

					final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
					datenow = java.sql.Date.valueOf(now);
					nowStrDate = formatter.format(datenow);
					dateoneTwenty = java.sql.Date.valueOf(now.minusDays(90));
					oneTwentyStrDate = formatter.format(dateoneTwenty);
					if (dateFrom != null && !dateFrom.isEmpty())
					{
						dateFromFinal = dateFormat.parse(dateFrom.trim());
						dateFromInvoice = formatter.format(dateFromFinal);
					}
					else
					{
						dateFromInvoice = oneTwentyStrDate.trim();
					}
					if (dateTo != null && !dateTo.isEmpty())
					{
						f.setTime(formatter.parse(dateTo));
						f.add(Calendar.DATE, 1);
						dateToFinal = dateFormat.parse(formatter.format(f.getTime()).trim());
						dateToInvoice = formatter.format(f.getTime()).trim();

					}
					else
					{
						dateToInvoice = nowStrDate.trim();
					}


				}
				catch (final ParseException e)
				{
					LOG.error(e);
				}
				String orderSearchParam = null;
				String invoiceSearchParam = null;
				String poNumberSearchParam = null;

				if (StringUtils.isNotBlank(oSearchParam))
				{
					orderSearchParam = oSearchParam.trim();
				}
				if (StringUtils.isNotBlank(iSearchParam))
				{
					invoiceSearchParam = iSearchParam.trim();
				}
				if (StringUtils.isNotBlank(pnSearchParam))
				{
					poNumberSearchParam = pnSearchParam.trim();
				}
			
			if (null != pageSize)
			{
				viewByPageSize = Integer.parseInt(pageSize);
			}
			if (null != dateFromInvoice)
			{
				requestData.setStartDate(dateFromInvoice);
			}
			else
			{
				requestData.setStartDate(oneTwentyStrDate.trim());
			}
			if (null != dateToInvoice)
			{
				requestData.setEndDate(dateToInvoice);
			}
			else
			{
				requestData.setEndDate(nowStrDate.trim());
			}
			requestData.setPage(page+1);
			requestData.setSort(sortCode);
			requestData.setRows(viewByPageSize);
			requestData.setSortDirection("Descending");
			requestData.setInvoiceNumber(invoiceSearchParam);
			LOG.error("The orderSearchParam outside is " + orderSearchParam);
			requestData.setOrderNumber(orderSearchParam);			
			requestData.setPoNumber(poNumberSearchParam);
			try
			{
				final PageableData pageableData = createPageableData(page, viewByPageSize, sortCode, showMode);
				SearchPageData<InvoiceData> searchPageData = null;
				
				if (invoiceShiptos != null)
				{
					if ("shipToopenPopupNew".equals(invoiceShiptos))
					{

						requestData.setIncludeShipTos(false);
						searchPageData = invoiceFacade.getListOfInvoiceData(requestData, unitId);
					}
					else
					{
						final String trimmedInvoiceShiptos = invoiceShiptos.trim();
						if ("All".equalsIgnoreCase(trimmedInvoiceShiptos))
						{
							requestData.setIncludeShipTos(true);
							searchPageData = invoiceFacade.getListOfInvoiceData(requestData, unitId);

						}
						else
						{
							final String[] shipToNumber = trimmedInvoiceShiptos.split("\\s+");
							final String shipToUid = shipToNumber[0];
							requestData.setIncludeShipTos(false);
							searchPageData = invoiceFacade.getListOfInvoiceData(requestData, shipToUid.concat("_US"));
						}
					}
				}

				else
				{

					requestData.setIncludeShipTos(true);
					searchPageData = invoiceFacade.getListOfInvoiceData(requestData, unitId);
				}
				searchPageData.getPagination().setSort(sortCode);
				SearchPageDTO searchPageDTO = new SearchPageDTO();
				searchPageDTO.setResults(searchPageData.getResults());
				searchPageDTO.setPagination(searchPageData.getPagination());
				searchPageDTO.setSorts(searchPageData.getSorts());
				
				final B2BUnitModel parentUnit = ((SiteOneB2BUnitService) b2bUnitService).getParentUnitForCustomer();
				final List<B2BUnitData> childs = ((SiteOneB2BUnitFacade) b2bUnitFacade).getShipTosForUnit(parentUnit.getUid());
				final Map<String, String> shipTosList = new LinkedHashMap<>();
				final Map<String, String> shipToListUpdated = new LinkedHashMap<>();
				if (invoiceShiptos != null && !invoiceShiptos.isEmpty() && !invoiceShiptos.equalsIgnoreCase("All"))
				{
					final B2BUnitModel b2BUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(unitId);
					final String shipToNameUnitID = b2BUnitModel.getUid().split("_US")[0] + ' ' + b2BUnitModel.getName();
					shipTosList.put(b2BUnitModel.getUid(), shipToNameUnitID);
				}
				for (final B2BUnitData child : childs)
				{
					final String shipToID = child.getUid().substring(0, child.getUid().indexOf("_US"));
					final String shipToNameID = child.getUid().substring(0, child.getUid().indexOf("_US")) + " " + child.getName();

					shipToListUpdated.put(shipToID, shipToNameID);
				}
				shipTosList.putAll(shipToListUpdated);
				final Set<String> listOfShipTos = new LinkedHashSet<String>(shipTosList.values());
				InvoiceResultsDTO invoiceResultsDTO= new InvoiceResultsDTO();
				invoiceResultsDTO.setOSearchParam(orderSearchParam);
				invoiceResultsDTO.setISearchParam(invoiceSearchParam);
				invoiceResultsDTO.setPnSearchParam(poNumberSearchParam);
				invoiceResultsDTO.setDateFrom(dateFrom);
				invoiceResultsDTO.setDateTo(dateTo);
				invoiceResultsDTO.setInvoiceShiptos(invoiceShiptos);
				invoiceResultsDTO.setListOfShipTos(listOfShipTos.toString());
				invoiceResultsDTO.setDaysArgs(daysArgs);
				invoiceResultsDTO.setIsShowAllAllowed(calculateShowAll(searchPageData, showMode));
				invoiceResultsDTO.setIsShowPageAllowed(calculateShowPaged(searchPageData, showMode));
			    invoiceResultsDTO.setSearchResult(searchPageDTO);
				invoiceResultsDTO.setNumberPagesShown(page);
				return invoiceResultsDTO;
			}
			catch(Exception e)
			{
				LOG.error(e.getMessage(), e);
				throw new RuntimeException(EXCEPTION +" invoices");
			}
		}
		return null;
	
	}

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/invoice/" + UNIT_ID_PATH_VARIABLE_PATTERN + "/"
			+ INVOICE_NUMBER_PATH_VARIABLE_PATTERN)
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "invoiceDetail", summary = "get the invoice details", description = "get the invoice details")
	public InvoiceDetailsWsDTO getInvoiceForCode(@PathVariable("invoiceNumber") final String code, @PathVariable("unitId") final String unitId)
	{
		InvoiceDetailsWsDTO invoiceDetailsDTO= new InvoiceDetailsWsDTO();
		try
		{
			final InvoiceData invoiceDetails = invoiceFacade.getInvoiceDetailsForCode(code,unitId,null,Boolean.FALSE);
			String paymentType = "";
			String cardNumber = "";
			String cardType = "";
			String termsCode = "";
			
			if (invoiceDetails.getSiteOnePaymentInfoData() != null)
			{
				paymentType = invoiceDetails.getSiteOnePaymentInfoData().getPaymentType();
				cardNumber = "XX" + invoiceDetails.getSiteOnePaymentInfoData().getCardNumber();
				cardType = invoiceDetails.getSiteOnePaymentInfoData().getApplicationLabel();
			}
			else if (invoiceDetails.getSiteOnePOAPaymentInfoData() != null)
			{
				paymentType = invoiceDetails.getSiteOnePOAPaymentInfoData().getPaymentType();
				final SiteOneCustInfoData custInfoData = ((SiteOneB2BUnitFacade) b2bUnitFacade).getAccountInformation(unitId);
				termsCode = custInfoData.getCustomerCreditInfo().getCreditTermCode();
			}
			else
			{
				paymentType = "2";
			}
			invoiceDetailsDTO.setCardNumber(cardNumber);
			invoiceDetailsDTO.setCardType(cardType);
			invoiceDetailsDTO.setInvoiceData(invoiceDetails);
			invoiceDetailsDTO.setPaymentType(paymentType);
			invoiceDetailsDTO.setTermsCode(termsCode);
			
			return invoiceDetailsDTO;
		}
		catch(ServiceUnavailableException ex)
		{
			LOG.error("Not able to fetch account information ", ex);
			throw new RuntimeException(EXCEPTION +" invoiceDetail");
		}
		catch (final PdfNotAvailableException e) {
			invoiceDetailsDTO.setCardNumber("");
			invoiceDetailsDTO.setCardType("");
			invoiceDetailsDTO.setInvoiceData( new InvoiceData());
			invoiceDetailsDTO.setPaymentType("");
			invoiceDetailsDTO.setTermsCode("");
			return invoiceDetailsDTO;
			}
		catch(Exception e)
		{
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(EXCEPTION +" invoiceDetail");
		}
	}
		
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })	
	@GetMapping("/invoicePDF/" + INVOICE_NUMBER_PATH_VARIABLE_PATTERN + "/" + ORDER_ID_PATH_VARIABLE_PATTERN)
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "invoicePDF", summary = "get the unique invoicePDF", description = "get the unique invoicePDF")
	public void createInvoicePDF(@PathVariable("invoiceNumber") final String invoiceNumber, 
			@PathVariable("orderShipmentActualId") final String orderShipmentActualId,
			final HttpServletResponse response, final HttpServletRequest request)
	{
		try
		{
			final byte[] pdfContent = invoiceFacade.getInvoiceByOrderShipmentActualId(orderShipmentActualId);

			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			final String encodedInvoiceNumber = StringUtils.normalizeSpace(invoiceNumber);
			response.setHeader("Content-Disposition", "attachment; filename=invoice_" + encodedInvoiceNumber + ".pdf");
			response.setContentType("application/octet-stream");
			response.setContentLength(pdfContent.length);
			response.getOutputStream().write(pdfContent);
			response.getOutputStream().flush();
		}
		catch (final ServiceUnavailableException serviceUnavailableException)
		{
			LOG.error("Not able to access pdf", serviceUnavailableException);
		}
		catch (final PdfNotAvailableException pdfNotAvailableException)
		{
			LOG.error("Pdf is currently not available", pdfNotAvailableException);
		}
		catch (final IOException e)
		{
			LOG.error("Exception while creating PDF : " + e);
			LOG.error(e.getMessage(), e);
		}

	}
	
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })	
	@GetMapping(value = "/downloadNurseryInventoryCSV")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "downloadNurseryInventoryCSV", summary = "get the NurseryInventoryCSV", description = "get the NurseryInventoryCSV")
	public void inventoryFeedPDF(@RequestParam(value = "storeId") final String storeId, final HttpServletResponse response, final HttpServletRequest request)
	{
		final String inventoryFeedContainer = Config.getString(SiteoneintegrationConstants.NURSERY_INVENTORY_FEED_TARGET_LOCATION,StringUtils.EMPTY);
		final String containerName = Config.getString("azure.outbound.storage.container.name",StringUtils.EMPTY);

		final File file = getBlobDataImportService().readBlobToFile("Nursery_Item_Inventory_" + storeId + ".xlsx",
				inventoryFeedContainer, containerName);
		try (final InputStream inputStream = new BufferedInputStream(new FileInputStream(file)))
		{
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setHeader("Content-Disposition", "attachment;filename=Nursery_Item_Inventory_" + storeId + ".xlsx");
			response.setContentType("application/octet-stream");
			response.setContentLength((int) file.length());
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			}
			catch (final FileNotFoundException fileNotFoundException)
			{
				LOG.error("Could not find file", fileNotFoundException);
			}
		    catch (final IOException e)
		    {
			    LOG.error(e.getMessage(), e);
		    }
	}
	

	
	public SiteOneBlobDataImportService getBlobDataImportService() {
		return blobDataImportService;
	}


	public void setBlobDataImportService(SiteOneBlobDataImportService blobDataImportService) {
		this.blobDataImportService = blobDataImportService;
	}


	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/invoiceDetailsEmail/" + INVOICE_NUMBER_PATH_VARIABLE_PATTERN)
	@ApiBaseSiteIdParam
	@Operation(operationId = "shareEmailInvoice",summary = "Share the Invoice", description= "Send the details of particular invoice")
	public @ResponseBody String shareInvoiceDetailsEmail(@PathVariable("invoiceNumber") final String code,
			@RequestParam(value = "email", required = false) final String emails, @RequestParam(value = "uid", required = false) final String uid)
	{
		Gson gson = new Gson();
		try
		{
			siteOneShareEmailFacade.shareInvoiceEmailForCode(code, emails, uid);
		}
		catch(Exception e)
		{
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(EXCEPTION +" invoiceDetailsEmail");
		}
		return gson.toJson(SUCCESS);
	}
	
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PostMapping("/update-password")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "update-password", summary = "Change password", description = "Change password")
	public String updatePassword(@Parameter(description = "Register object", required = true) @RequestBody UpdatePasswordForm updatePasswordForm, 
			 final HttpServletRequest request)
	{
		Gson gson = new Gson();
		try
		{
			((SiteOneCustomerFacade) customerFacade).getClientInformation(request);
			((SiteOneCustomerFacade) customerFacade).changePassword(updatePasswordForm.getCurrentPassword(),
					updatePasswordForm.getNewPassword());
		}
		catch (final PasswordMismatchException localException)
		{
			LOG.error(localException.getMessage(), localException);
			return gson.toJson("Please enter your current password.");
		}
		catch (final PasswordPolicyViolationException passwordPolicyViolationException)
		{
			LOG.error(passwordPolicyViolationException);
			return gson.toJson("The password you entered is not valid. Please enter a valid password.");
		}
		catch (final RecentlyUsedPasswordException oktaRecentlyUsedPasswordException)
		{
			LOG.error(oktaRecentlyUsedPasswordException);
			return gson.toJson("Your new password cannot match any of your last 13 passwords. Please enter another password.");
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" updatePassword");
		}

		return gson.toJson(SUCCESS);
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/update-siteoneprofile")
	@ApiBaseSiteIdParam
	@Operation(operationId = "update-siteoneprofile", summary = "Get the profile details", description = "Get the profile details")
	public @ResponseBody UpdateProfileWsDTO editPersonalDetails()
	{
		try
		{
			final CustomerData customerData = ((SiteOneCustomerFacade) customerFacade).fetchPersonalDetails();
			final UpdateProfileWsDTO updateProfile = new UpdateProfileWsDTO();
			updateProfile.setName(customerData.getName());
			updateProfile.setContactNumber(customerData.getContactNumber());

			updateProfile.setEmail(customerData.getEmail());
			updateProfile.setPassword(customerData.getEncodedPass());

			updateProfile.setIsEmailOpt(customerData.getEmailOptIn());

			updateProfile.setIsSMSOpt(customerData.getSmsOptIn());

			updateProfile.setIsAdmin(customerData.getIsAdmin());

			final UserEmailData userData = ((SiteOneCustomerFacade) customerFacade).fetchPreferences();

			if (null != userData && null != userData.getCustomFields())
			{
				for (final CustomFields custField : userData.getCustomFields())
				{
					if (custField.getName().equalsIgnoreCase("emailPromo"))
					{
						updateProfile.setEmailSubscribe(custField.getValue());
					}
				}
			}
			
			if (customerData.getLangPreference() != null)
			{
				updateProfile.setLanguagePreference(customerData.getLangPreference().getName(Locale.ENGLISH));
			}
			else
			{
				updateProfile.setLanguagePreference(null);
			}
			Gson gson = new Gson();
			System.out.println("updateProfileForm : "+gson.toJson(updateProfile));
			return updateProfile;
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" editPersonalDetails");
		}
	}

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PostMapping("/update-language")
	@ApiBaseSiteIdParam
	@Operation(operationId = "update-language", summary = "Update the language details", description = "Update the language details")
	public @ResponseBody String updatePreferredLanguage(@RequestParam(value = "language", required = false) final String language)
	{
		Gson gson = new Gson();
		try
		{
			if(null != language && !language.isEmpty()){
				((SiteOneCustomerFacade) customerFacade).updateLanguagePreference(language);
			}else{
				return gson.toJson("Please select a language to update.");
			}
		}
		catch (final ServiceUnavailableException serviceUnavailableException)
		{
			LOG.error("Service Unavailable Exception Occured", serviceUnavailableException);
			return gson.toJson("Unable to update language preference at this time.");
		}
		catch (final ContactNotCreatedOrUpdatedInUEException contactNotCreatedOrUpdatedInUEException)
		{
			LOG.error("Contact Not Created/Updated In UE", contactNotCreatedOrUpdatedInUEException);
			return gson.toJson("Unable to update language preference at this time.");
		}
		catch (final UnknownIdentifierException unknownIdentifierException)
		{
			LOG.error("unknown Identifier Exception: ", unknownIdentifierException);
			return gson.toJson("Unable to update language preference at this time.");
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" updatePreferredLanguage");
		}

		return gson.toJson("Language preference has been updated.");
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/update-siteonepreference")
	@Operation(operationId = "updateSiteonepreference", summary = "Get Siteone user preference page", description = "Get the Siteone user preference page in my-account")
	@ApiBaseSiteIdParam
	@ResponseBody
	public UpdateSiteonePreferenceDTO editPreferenceDetails()
	{
		try
		{
			final ObjectMapper mapper = new ObjectMapper();
			
			String json = "";

			UpdateSiteonePreferenceDTO updateSiteonePreferenceDTO= new UpdateSiteonePreferenceDTO();
			
			final UserEmailData userData = ((SiteOneCustomerFacade) customerFacade).fetchPreferences();
			if (null != userData && CollectionUtils.isNotEmpty(userData.getCustomFields()))
			{
				for (final CustomFields custField : userData.getCustomFields())
				{
					if (custField.getName().equalsIgnoreCase("EmailTopic") )
					{
						final List<String> items = new ArrayList<String>(Arrays.asList(custField.getValue().split("\\|")));
						updateSiteonePreferenceDTO.setEmailTopic(items.toString());
					}

					if (custField.getName().equalsIgnoreCase("EmailTopicPreference"))
					{
						final List<String> preference = new ArrayList<String>(Arrays.asList(custField.getValue().split("\\|")));
						try
						{
							json = mapper.writeValueAsString(preference);
						}
						catch (final Exception e)
						{
							LOG.error(e.getMessage(), e);
						}
						updateSiteonePreferenceDTO.setEmailTopicPreference(json.toString());
					}

					if (custField.getName().equalsIgnoreCase("orderInfo"))
					{
						updateSiteonePreferenceDTO.setOrderInfo(custField.getValue());
					}

					if (custField.getName().equalsIgnoreCase("emailPromo"))
					{
						updateSiteonePreferenceDTO.setEmailPromo(custField.getValue());
					}


					if (custField.getName().equalsIgnoreCase("orderPromo"))
					{
						updateSiteonePreferenceDTO.setOrderPromo(custField.getValue());
						
					}

					if (custField.getName().equalsIgnoreCase("isAdmin"))
					{
						updateSiteonePreferenceDTO.setIsAdmin(custField.getValue());
					}
					
				}
			}

			return updateSiteonePreferenceDTO;
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" editPreferenceDetails");
		}
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PostMapping("/update-siteonepreference")
	@Operation(operationId = "updatePreference", summary = "Update the User Preference", description = "Update the User Preference")
	@ApiBaseSiteIdParam
	@ResponseBody
	public String savePreference(@RequestParam(value = "emailType") final String emailType,
			@RequestParam(value = "emailTopicPreference") final String emailTopicPreference,
			@RequestParam(value = "emailPromo") final String emailPromo)
	{
		try
		{
			LOG.debug("Method to update");
			final String pNameParameter = emailType;
			final String lNameParameter = emailTopicPreference;

			((SiteOneCustomerFacade) customerFacade).saveUserPreference(pNameParameter, lNameParameter, emailPromo);
			Gson gson = new Gson();
			return gson.toJson(SUCCESS);
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" savePreference");
		}
	}
	
	protected TitleData findTitleForCode(final List<TitleData> titles, final String code)
	{
		if (code != null && !code.isEmpty() && titles != null && !titles.isEmpty())
		{
			for (final TitleData title : titles)
			{
				if (code.equals(title.getCode()))
				{
					return title;
				}
			}
		}
		return null;
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/accountPartnerProgram")
	@Operation(operationId = "accountPartnerProgram", summary = "Get Account Partner Program Details", description = "Get the values of account partner program")
	@ApiBaseSiteIdParam
	@ResponseBody
	public AccountPartnerProgramDTO getAccountPartnerProgram() 
	{
		AccountPartnerProgramDTO accountPartnerProgramDTO  =new AccountPartnerProgramDTO();
		SiteOneCustInfoData siteOneCustInfoData = null;
		try {
            final B2BUnitModel defaultUnit = ((SiteOneB2BUnitService) b2bUnitService)
					.getDefaultUnitForCurrentCustomer();
			siteOneCustInfoData = ((SiteOneB2BUnitFacade) b2bUnitFacade).getLoyaltyInformation(defaultUnit.getUid());

			if (null != siteOneCustInfoData) {
				accountPartnerProgramDTO.setRewardPoints(siteOneCustInfoData.getCustomerRewardsPointsInfo());
				accountPartnerProgramDTO.setLoyaltyProgramPoints(siteOneCustInfoData.getLoyaltyProgramInfo());
			}
		}
		catch (final ServiceUnavailableException serviceUnavailableException)
		{
			LOG.error(serviceUnavailableException);
			accountPartnerProgramDTO.setRewardPoints(null);
			accountPartnerProgramDTO.setLoyaltyProgramPoints(null);
		}

		try 
		{
			final List<TitleData> titles = userFacade.getTitles();

			final CustomerData customerData = customerFacade.getCurrentCustomer();
			if (customerData.getTitleCode() != null)
			{
				TitleData titledata =  findTitleForCode(titles, customerData.getTitleCode());
				TitleDataListDTO titledatalistDTO = getDataMapper().map(titledata,TitleDataListDTO.class, "FULL");
				accountPartnerProgramDTO.setTitle(titledata);
			}
			boolean isPartnerProgramAdminEmail = false;
			if (null != siteOneCustInfoData && null != siteOneCustInfoData.getProfile()
					&& siteOneCustInfoData.getProfile().getAttributes().getPartnerProgramAdminEmail() != null && siteOneCustInfoData
							.getProfile().getAttributes().getPartnerProgramAdminEmail().equalsIgnoreCase(customerData.getUid()))
			{
				isPartnerProgramAdminEmail = true;
			}
			accountPartnerProgramDTO.setIsPartnerProgramAdminEmail(isPartnerProgramAdminEmail);
			boolean isAdminUser = false;
			boolean isPartnerProgramEnrolled = false;
			boolean isPartnersProgramRetail=false;
			final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
			if (null != customer)
			{
				isAdminUser = customer.getGroups().stream().anyMatch(group -> group.getUid().equalsIgnoreCase("b2badmingroup"));
				isPartnerProgramEnrolled = customer.getPartnerProgramPermissions();
				if (customer.getDefaultB2BUnit().getTradeClass() != null)
				{
					if (customer.getDefaultB2BUnit().getTradeClass().equals(RESELLER_TRADECLASS)
							|| customer.getDefaultB2BUnit().getTradeClass().equals(GOVERNMENT_TRADECLASS)
							|| customer.getDefaultB2BUnit().getTradeClass().equals(RETAIL_TRADECLASS))
					{
						isPartnersProgramRetail = true;
					}
					else
					{
						isPartnersProgramRetail = false;
					}
				}
			}

			accountPartnerProgramDTO.setIsAdminUser(isAdminUser);
			accountPartnerProgramDTO.setIsPartnerProgramEnrolled(isPartnerProgramEnrolled);
			accountPartnerProgramDTO.setIsPartnerProgramRetail(isPartnersProgramRetail);

			return accountPartnerProgramDTO;
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" accountPartnerProgram");
		}
	}

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/account-overview")
	@Operation(operationId = "account-overview", summary = "Get Account overview Details", description = "Get Account overview Details")
	@ApiBaseSiteIdParam
	public @ResponseBody AccountOverviewWsDTO getAccountOverviewPage(@RequestParam(value = "accountId", required = false) final String accountId)
	{
		try
		{
			final AccountOverviewWsDTO accountInfo = new AccountOverviewWsDTO();
			if (((SiteOneCustomerFacade) customerFacade).isHavingAccountOverviewForParent())
			{
				populateAccountOverview(accountId,accountInfo);
			}
			else
			{
				accountInfo.setErrorMessage("You do not have the permission to access Account Overview Of Parent");
			}
			return accountInfo;
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" account-overview");
		}
	}
	
	protected void populateAccountOverview(final String uid,final AccountOverviewWsDTO accountInfo)
	{

		String unitId = null;
		B2BUnitData b2bUnitData = null;
		if (null != uid)
		{
			b2bUnitData = b2bUnitFacade.getUnitForUid(uid);
			unitId = uid;
		}
		else
		{
			b2bUnitData = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
			unitId = b2bUnitData.getUid();
		}
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		mapperFactory.classMap(B2BUnitData.class, B2BUnitWsDTO.class);
	    MapperFacade mapper = mapperFactory.getMapperFacade();
	    B2BUnitWsDTO b2bUnitWsDTO = mapper.map(b2bUnitData, B2BUnitWsDTO.class);
	    accountInfo.setUnit(b2bUnitWsDTO);
		try
		{
			final SiteOneCustInfoData custInfoData = ((SiteOneB2BUnitFacade) b2bUnitFacade).getAccountInformation(unitId);
			accountInfo.setCustInfoData(custInfoData);
			accountInfo.setCurrency(storeSessionFacade.getCurrentCurrency().getSymbol());
		}
		catch (final ServiceUnavailableException serviceUnavailableException)
		{
			LOG.error(serviceUnavailableException);
			accountInfo.setErrorMessage("Data Not Available");
		}
		accountInfo.setCurrentYear(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR));
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/partner-program")
	@ApiBaseSiteIdParam
	@Operation(operationId = "partner-program", summary = "Get partner program redirection url", description = "Get partner program redirection url")
	public @ResponseBody String partnerProgram()
	{
		try
		{
			String response = ((SiteOneCustomerFacade) customerFacade).getPartnerProgramRedirectlUrl();
			Gson gson = new Gson();
			return gson.toJson(response);
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" partnerProgram");
		}
	}	
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/send-tag-notification")
	@ApiBaseSiteIdParam
	@Operation(operationId = "send-tag-notification", summary = "Send Test Notification", description = "Send Test Notification")
	@Hidden
	public void sendTestNotification(@RequestParam(value = "tag") final String tag)
	{
		try 
		{
			NotificationHub hub = sendNotificationApi.createNotificationHubClient();
			sendNotificationApi.sendTestNotification(hub,tag);
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" sendTestNotification");
		}
	}
	
	@GetMapping("/isUserAccountLocked")
	@ApiBaseSiteIdParam
	@Operation(operationId = "isUserAccountLocked", summary = "Check user account status", description = "Check user account status")
	public @ResponseBody boolean isUserAccountLocked(@RequestParam(value = "userId") final String userId)
	{
		try 
		{
			return ((SiteOneCustomerFacade) customerFacade).isUserAccountLocked(userId);
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" isUserAccountLocked");
		}
	}	
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping(value = "/customerSpecificData")
	@ApiBaseSiteIdParam
	@Operation(operationId = "customer specific data", summary = "Get Customer Specific HighLevel Data", description = "Get Customer Specific HighLevel Data")
	public @ResponseBody CustomerSpecificWsDTO getCustomerSpecificData(@RequestParam(value = "unitId", required = false) String unitId)
	{
		try 
		{
			B2BUnitData b2bUnitData=null;
			if (StringUtils.isEmpty(unitId))
			{
				b2bUnitData = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
				unitId = b2bUnitData.getUid();
			}
			
			final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();		
			return ((SiteOneCustomerFacade) customerFacade).getCustomerSpecificInfo(unitId, customer);
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" customerSpecificData");
		}
		
	}
		
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PostMapping("/enrollCustomerInTalonOne")
	@ApiBaseSiteIdParam
	@Operation(operationId = "existing customer enrollment in talonone", summary = "existing customer enrollment in talonone", description = "existing customer enrollment in talonone")
	public @ResponseBody boolean enrollCustomerInTalonOne()
	{
		try 
		{
			return ((SiteOneCustomerFacade) customerFacade).enrollCustomerInTalonOne();
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" enrollCustomerInTalonOne");
		}
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping(value = "/getTalonOneLoyaltyStatus")
	@ApiBaseSiteIdParam
	@Operation(operationId = "getTalonOneLoyaltyStatus", summary = "Get Customer loyalty program status", description = "Get Customer loyalty Enrollment program status")
	@ResponseBody
	public boolean getCustomerLoyaltyProgramEnrollmentStatus()
	{
		try 
		{
			return ((SiteOneB2BUnitFacade) b2bUnitFacade).getLoyaltyProgramInfoStatus();
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" getCustomerLoyaltyProgramEnrollmentStatus");
		}	
	}
	
	private AddressBookWsDTO getAddressBookDTO(final Collection<AddressData> deliveryAddresses, final String sanitizedunitId)
	{
		AddressBookWsDTO addressBookDTO = new AddressBookWsDTO();
		List<AddressData> deliveryAddressData = new ArrayList<>(); 
		if(CollectionUtils.isNotEmpty(deliveryAddresses))
		{
			deliveryAddressData.addAll(deliveryAddresses); 
		}
		addressBookDTO.setUnitId(sanitizedunitId);
		addressBookDTO.setDeliveryAddress(deliveryAddressData);
		if(((B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer()).getEnableAddModifyDeliveryAddress() != null)
		{
			addressBookDTO.setIsEditEnabled(((B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer()).getEnableAddModifyDeliveryAddress());			
		}
		else
		{
			addressBookDTO.setIsEditEnabled(false);
		}
		return addressBookDTO;
	}
	
	protected boolean isDefaultAddress(final String addressId, final String unitId)
	{
		final AddressData defaultAddress = ((SiteOneCustomerFacade) customerFacade).getDefaultShippingAddressForUnit(unitId);
		return defaultAddress != null && defaultAddress.getId() != null && defaultAddress.getId().equals(addressId);
	}

}