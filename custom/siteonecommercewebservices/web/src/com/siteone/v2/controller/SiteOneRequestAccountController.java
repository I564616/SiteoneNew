package com.siteone.v2.controller;

import de.hybris.platform.webservicescommons.mapping.DataMapper;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.Config;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestPart;

import com.siteone.commerceservice.register.data.SiteOneRequestAccountInputData;
import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.event.RequestAccountEvent;
import com.siteone.facade.email.BriteVerifyFacade;
import com.siteone.facade.location.SiteOneRegionFacade;
import com.siteone.facade.customer.info.SiteOneContrPrimaryBusinessData;
import com.siteone.facade.requestaccount.SiteoneRequestAccountFacade;
import com.siteone.facade.requestaccount.SiteoneContrPrimaryBusinessMapFacade;
import com.siteone.facade.SiteoneRequestAccountData;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.address.data.SiteOneAddressVerificationData;
import com.siteone.facades.constants.SiteoneFacadesConstants;
import com.siteone.commerceservices.address.dto.SiteOneAddressVerificationWsDTO;
import com.siteone.commerceservice.customer.dto.SiteOneContrPrimaryBusinessWsDTO;
import com.google.gson.Gson;
import com.siteone.commerceservice.customer.dto.SiteOneContrPrimaryBusinessListDTO;
import com.siteone.utils.SiteOneRequestAccountForm;
import com.siteone.utils.SiteOneAddressDataUtil;
import com.siteone.commerceservices.dto.createCustomer.SiteoneWsUpdateAccountInfoWsDTO;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.customer.createCustomer.data.SiteOneWsCreateCustomerResponseData;
import com.siteone.integration.customer.createCustomer.data.SiteOneWsSelfServeOnlineAccessRequestData;
import com.siteone.integration.customer.createCustomer.data.SiteOneWsSelfServeOnlineAccessResponseData;
import com.siteone.integration.customer.createCustomer.data.SiteoneWsSelfServeAccountInfoOnlineAccessData;
import com.siteone.integration.customer.createCustomer.data.SiteoneWsSelfServeContactInfoOnlineAccessData;
import com.siteone.commerceservices.dto.createCustomer.SiteoneCreateCustomerResponseWsDTO;
import com.siteone.commerceservices.dto.createCustomer.CreateCustomerResponseWsDTO;
import com.siteone.commerceservices.dto.onlineAccess.OnlineAccessResponseWsDTO;
import com.siteone.utils.SiteOneRequestAccountOnlineAccessForm;
import com.siteone.commerceservices.dto.quotes.SiteoneWsNotifyQuoteWsDTO;

@Controller
@RequestMapping(value = "/{baseSiteId}/request-account")
@Tag(name = "Siteone Request Account")
public class SiteOneRequestAccountController {

	private static final Logger LOG = Logger.getLogger(SiteOneRequestAccountController.class);
	private static final String HOMEOWNER_CODE = "homeOwner.trade.class.code";

	private static final String SUCCESS = "Success";
	private static final String FAILURE = "Failure";
	private UserService userService;

	@Resource(name = "eventService")
	private EventService eventService;

	@Resource(name = "siteOneRegionFacade")
	private SiteOneRegionFacade siteOneRegionFacade;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Resource(name = "briteVerifyFacade")
	private BriteVerifyFacade briteVerifyFacade;

	@Resource(name = "siteoneContrPrimaryBusinessMapFacade")
	private SiteoneContrPrimaryBusinessMapFacade siteoneContrPrimaryBusinessMapFacade;

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "siteoneRequestAccountFacade")
	private SiteoneRequestAccountFacade siteoneRequestAccountFacade;

	@Resource(name = "dataMapper")
	private DataMapper dataMapper;

	@Resource(name = "defaultSiteoneAddressConverter")
	private SiteOneAddressDataUtil defaultSiteoneAddressConverter;

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;
	
	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;

	private static final String OPERATION_TYPE_SHIP_TO_ONLINE = "ShipToOnlineAccess";

	@GetMapping("/form")
	@Operation(operationId = "getRequestAccountForm", summary = "Get the input forms", description = "Returns all values for reqeust account form")
	@ApiBaseSiteIdParam
	@ResponseBody
	public SiteOneRequestAccountInputData getRequestAccountPage()
	{
		try
		{
			SiteOneRequestAccountInputData requestInput = new SiteOneRequestAccountInputData();
			final List<RegionData> regionDataList = siteOneRegionFacade.getRegionsForCountryCode("US");
			regionDataList.sort(Comparator.comparing(RegionData::getName));
			String recaptchaPublicKey = Config.getString("recaptcha.publickey", null);
			Map<String, SiteOneContrPrimaryBusinessListDTO> primaryBusinessList = getPrimaryBusinessList();
			List<String> empCountList = getEmpCountList();
			requestInput.setBusinessList(primaryBusinessList);
			requestInput.setEmpCountList(empCountList);
			requestInput.setRecaptchaPublicKey(recaptchaPublicKey);
			requestInput.setRegionDataList(regionDataList);
			return requestInput;
		}
		catch(Exception ex)
		{
		    LOG.error(ex);
		    throw new RuntimeException("Exception occurred while calling through method getRequestAccountPage ");
		}
	}

	@PostMapping("/form")
	@ApiBaseSiteIdParam
	@Operation(operationId = "submitRequetAccount", summary = "Submit the Request Account", description = "Submit the entered input values")
	@ResponseBody
	public String editRequestAccountPage(@Parameter(description = "Register object", required = true) @RequestBody SiteOneRequestAccountForm siteOneRequestAccountForm,
										 final HttpServletRequest request)
	{
		try
		{
			siteoneRequestAccountFacade.populateSiteoneRequestAccountDataToModel(populateSiteoneAccountRequestData(siteOneRequestAccountForm, null, false));
			Gson gson = new Gson();
			return gson.toJson(SUCCESS);
		}
		catch(Exception ex)
		{
		    LOG.error(ex);
		    throw new RuntimeException("Exception occurred while calling through method editRequestAccountPage ");
		}
	}


	public RequestAccountEvent initializeEvent(final RequestAccountEvent event,
											   final SiteOneRequestAccountForm siteOneRequestAccountForm)
	{
		event.setCompanyName(siteOneRequestAccountForm.getCompanyName());
		event.setAccountNumber(siteOneRequestAccountForm.getAccountNumber());
		event.setFirstName(siteOneRequestAccountForm.getFirstName());
		event.setLastName(siteOneRequestAccountForm.getLastName());
		event.setAddressLine1(siteOneRequestAccountForm.getAddressLine1());
		event.setAddressLine2(siteOneRequestAccountForm.getAddressLine2());
		event.setCity(siteOneRequestAccountForm.getCity());
		event.setState(siteOneRequestAccountForm.getState());
		event.setZipcode(siteOneRequestAccountForm.getZipcode());
		event.setPhoneNumber(siteOneRequestAccountForm.getPhoneNumber());
		event.setHasAccountNumber(siteOneRequestAccountForm.getHasAccountNumber());
		event.setBaseStore(baseStoreService.getBaseStoreForUid("siteone"));
		event.setSite(baseSiteService.getBaseSiteForUID("siteone"));
		event.setEmailAddress(siteOneRequestAccountForm.getEmailAddress());
		event.setTypeOfCustomer(siteOneRequestAccountForm.getTypeOfCustomer());
		event.setContrYearsInBusiness(siteOneRequestAccountForm.getContrYearsInBusiness());
		event.setContrEmpCount(siteOneRequestAccountForm.getContrEmpCount());
		if (!StringUtils.isBlank(siteOneRequestAccountForm.getContrPrimaryBusiness()))
		{
			final String[] primaryBusiness = siteOneRequestAccountForm.getContrPrimaryBusiness().split(SiteoneFacadesConstants.PIPE);
			event.setContrPrimaryBusiness(primaryBusiness[SiteoneFacadesConstants.ONE]);
		}
		event.setLanguagePreference(siteOneRequestAccountForm.getLanguagePreference());
		event.setLanguage(getCommonI18NService().getCurrentLanguage());
		event.setIsAccountOwner(siteOneRequestAccountForm.getIsAccountOwner());
		event.setStoreNumber(siteOneRequestAccountForm.getStoreNumber());
		event.setEnrollInPartnersProgram(siteOneRequestAccountForm.getEnrollInPartnersProgram());
		return event;
	}

	@GetMapping("/briteVerifyValidate")
	@ResponseBody
	@Operation(operationId = "briteVerifyValidate", summary = "Validate mail id", description = "Validate the input mail and return response")
	@ApiBaseSiteIdParam
	public String briteverifyValidate(@RequestParam("email") final String email)
	{
		try
		{
			final String briteVerifyResponse = briteVerifyFacade.validateEmailId(email);
			Gson gson = new Gson();
			return gson.toJson(briteVerifyResponse);	
		}
		catch(Exception ex)
		{
		    LOG.error(ex);
		    throw new RuntimeException("Exception occurred while calling through method briteverifyValidate ");
		}
	}

	@PostMapping(value = "/isEmailAlreadyExists")
	@Operation(operationId = "isEmailAlreadyExists", summary = "Validate mail id", description = "Validate the input mail and return response")
	@ApiBaseSiteIdParam
	public @ResponseBody boolean isEmailAlreadyExists(@Parameter(description = "Register object", required = true) @RequestBody final SiteOneRequestAccountForm siteOneRequestAccountForm)
	{
		try
		{
			boolean isEmailAlreadyExists = ((SiteOneCustomerFacade) customerFacade).isUserAvailable(siteOneRequestAccountForm.getEmailAddress());
			if(isEmailAlreadyExists)
			{
				final RequestAccountEvent requestAccountEvent = new RequestAccountEvent();
				requestAccountEvent.setIsEmailUniqueInHybris(false);
				eventService.publishEvent(initializeEvent(requestAccountEvent, siteOneRequestAccountForm));
			}
			return isEmailAlreadyExists;
		}
		catch(Exception ex)
		{
		    LOG.error(ex);
		    throw new RuntimeException("Exception occurred while calling through method isEmailAlreadyExists ");
		}
	}

	@PostMapping(value = "/validateAddress")
	@Operation(operationId = "validate address", summary = "Validate form address", description = "Validate the address from the form and return response")
	@ApiBaseSiteIdParam
	public @ResponseBody SiteOneAddressVerificationWsDTO validateAddress(@Parameter(description = "Register object", required = true) @RequestBody final SiteOneRequestAccountForm siteOneRequestAccountForm,
																		 @RequestParam(defaultValue = "DEFAULT") final String fields)
	{
		try
		{
			final AddressData addressData = defaultSiteoneAddressConverter.convertToAddressData(siteOneRequestAccountForm);
			SiteOneAddressVerificationData addressVerificationData = ((SiteOneCustomerFacade) customerFacade).validateAddress(addressData);
			return getDataMapper().map(addressVerificationData, SiteOneAddressVerificationWsDTO.class, fields);
		}
		catch(Exception ex)
		{
		    LOG.error(ex);
		    throw new RuntimeException("Exception occurred while calling through method validateAddress ");
		}
	}

	@PostMapping(path = "/createCustomer")
	@Operation(operationId = "Create Customer", summary = "Create Customer", description = "Create Customer in OKTA")
	@ApiBaseSiteIdParam
	public @ResponseBody String createCustomer(@Parameter(description = "Register object", required = true) @RequestBody final SiteOneRequestAccountForm siteOneRequestAccountForm)
	{
		try
		{
			final SiteoneRequestAccountData siteoneRequestAccountData = populateSiteoneAccountRequestData(siteOneRequestAccountForm,null, false);
			String response = siteoneRequestAccountFacade.createCustomer(siteoneRequestAccountData,null,false);
			Gson gson = new Gson();
			return gson.toJson(response);
		}
		catch(Exception ex)
		{
		    LOG.error(ex);
		    throw new RuntimeException("Exception occurred while calling through method createCustomer ");
		}
	}	 
	
	@PostMapping(path = "/onlineAccess")
	@Operation(operationId = "Online Access", summary = "Online Access", description = "Check Customer Online Access")
	@ApiBaseSiteIdParam
	public @ResponseBody OnlineAccessResponseWsDTO requestOnlineAccess(
			final SiteOneRequestAccountOnlineAccessForm siteOneRequestOnlineAccessForm) {
		try
		{
			boolean isEmailExistInEcom = false;
			boolean accountNumberNotExists = false;
			String selfServeOnlineAccessResponse = "";
			if (null != siteOneRequestOnlineAccessForm.getEmailAddress()) {
				final B2BCustomerModel customerModel = (B2BCustomerModel) b2bCustomerService
						.getUserForUID(siteOneRequestOnlineAccessForm.getEmailAddress().trim().toLowerCase());
				final String accountNo = siteOneRequestOnlineAccessForm.getAccountNumber() + "_US";
				if (siteOneRequestOnlineAccessForm.getAccountNumber().contains("-")) {
					accountNumberNotExists = true;
				}
				if (customerModel != null) {
					if (customerModel.getDefaultB2BUnit().getUid()
							.equalsIgnoreCase(accountNo)) {
						isEmailExistInEcom = true;
					} else {
						accountNumberNotExists = true;
					}
				} else {
					final SiteOneWsSelfServeOnlineAccessRequestData siteOneWsSelfServeOnlineAccessRequestData = populateFormToRequestData(
							siteOneRequestOnlineAccessForm);
					selfServeOnlineAccessResponse = siteoneRequestAccountFacade
							.getSelfServeResponse(siteOneWsSelfServeOnlineAccessRequestData);
				}
			}

			OnlineAccessResponseWsDTO response = new OnlineAccessResponseWsDTO();
			response.setIsSuccess(selfServeOnlineAccessResponse);
			response.setIsEmailExistInEcom(isEmailExistInEcom);
			response.setAccountNumberNotExists(accountNumberNotExists);
			return response;
		}
		catch(Exception ex)
		{
		    LOG.error(ex);
		    throw new RuntimeException("Exception occurred while calling through method requestOnlineAccess ");
		}
	}


	@PostMapping(path = "/NotifyUECustomerOnlineAccessChange")
	@Operation(operationId = "enableOnlineAcess", summary = "", description = "" )
	@ApiBaseSiteIdParam public @ResponseBody CreateCustomerResponseWsDTO updateHybrisCustomer(@Parameter(description = "Customer Object", required = true) @RequestBody final SiteoneWsUpdateAccountInfoWsDTO siteoneWsUpdateAccountInfoWsDTO) {
		try
		{
			CreateCustomerResponseWsDTO response = siteoneRequestAccountFacade.validateRequest(siteoneWsUpdateAccountInfoWsDTO);
			response.setCorrelationId(siteoneWsUpdateAccountInfoWsDTO.getCorrelationId());

			if (response.getStatus().equalsIgnoreCase(String.valueOf(HttpStatus.OK.value()))) {
				try {
					if (null != siteoneWsUpdateAccountInfoWsDTO.getParentAccountInfo() && siteoneWsUpdateAccountInfoWsDTO.getOperationType().equalsIgnoreCase(OPERATION_TYPE_SHIP_TO_ONLINE)) {
						final SiteoneRequestAccountData siteoneParentRequestAccountData = populateSiteoneParentAccountRequestData(null, siteoneWsUpdateAccountInfoWsDTO, true);

						CreateCustomerResponseWsDTO parentResponse = siteoneRequestAccountFacade.getUpdateParentResponse(siteoneWsUpdateAccountInfoWsDTO, siteoneParentRequestAccountData);
						if (parentResponse.getStatus().equalsIgnoreCase(String.valueOf(HttpStatus.OK.value()))) {
							response = siteoneRequestAccountFacade.getUpdateCustomerResponse(siteoneWsUpdateAccountInfoWsDTO,
									populateSiteoneAccountRequestData(null, siteoneWsUpdateAccountInfoWsDTO, true));
							response.setCorrelationId(siteoneWsUpdateAccountInfoWsDTO.getCorrelationId());
						} else {
							response.setStatus(parentResponse.getStatus());
							response.setMessage(parentResponse.getMessage());
						}
					} else {
						response = siteoneRequestAccountFacade.getUpdateCustomerResponse(siteoneWsUpdateAccountInfoWsDTO,
								populateSiteoneAccountRequestData(null, siteoneWsUpdateAccountInfoWsDTO, true));
						response.setCorrelationId(siteoneWsUpdateAccountInfoWsDTO.getCorrelationId());
					}
					siteoneRequestAccountFacade.logRequestError(siteoneWsUpdateAccountInfoWsDTO,response);
				} catch (Exception e) {
				   LOG.error("Exception occured while enable online account : "+e);
					response.setStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
					response.setMessage(e.toString());
					siteoneRequestAccountFacade.logRequestError(siteoneWsUpdateAccountInfoWsDTO,response);
				}
			} else {
				siteoneRequestAccountFacade.logRequestError(siteoneWsUpdateAccountInfoWsDTO,response);
			}
			return response;
		}
		catch(Exception ex)
		{
		    LOG.error(ex);
		    throw new RuntimeException("Exception occurred while calling through method updateHybrisCustomer ");
		}
	}

	@PostMapping(path = "/NotifyQuoteEmail")
	@Operation(operationId = "NotifyQuoteStatus", summary = "Notify quote status to admin via email", description = "Notify quote status to admin via email" )
	@ApiBaseSiteIdParam 
	public @ResponseBody CreateCustomerResponseWsDTO sendQuoteStatusEmail(@Parameter(description = "Quote email", required = true) 
			@RequestBody final SiteoneWsNotifyQuoteWsDTO siteoneWsNotifyQuoteWsDTO) {
		try
		{
			CreateCustomerResponseWsDTO responseDTO = new CreateCustomerResponseWsDTO();
			String response = siteoneRequestAccountFacade.notifyQuoteEmailStatus(siteoneWsNotifyQuoteWsDTO);
			responseDTO.setCorrelationId(response);
			if(response!=null) {
				responseDTO.setStatus(String.valueOf(HttpStatus.OK.value()));
				responseDTO.setMessage("Notified Quote Email");
			}else {
				responseDTO.setStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
				responseDTO.setMessage(FAILURE);
			}
		siteoneRequestAccountFacade.logRequestError(siteoneWsNotifyQuoteWsDTO,responseDTO);
		return responseDTO;
		}
		catch(Exception ex)
		{
		    LOG.error(ex);
		    throw new RuntimeException("Exception occurred while calling through method sendQuoteStatusEmail ");
		}
	}
	
	@PostMapping(value = "/decryptData" , consumes="multipart/form-data")
	@ApiBaseSiteIdParam
	@Operation(operationId = "decryptData", summary = "get the decrypted data", description = "get the decrypted data")
	public @ResponseBody void decryptData(@RequestPart("csvFile") MultipartFile csvFile, final HttpServletResponse response, final HttpServletRequest request)
	{
		try {
			LOG.error("Inside decryptData");
			final InputStream inputStreamData = csvFile.getInputStream();
			StringBuilder csvContent = siteoneRequestAccountFacade.decryptData(inputStreamData);
			final File file = csvFileWrite("DecryptedData",csvContent);
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setHeader("Content-Disposition", "attachment;filename=Decrypted_Data.csv");
			response.setContentType("application/octet-stream");
			response.setContentLength((int) file.length());
			try (final InputStream inputStream = new BufferedInputStream(new FileInputStream(file)))
			{
				FileCopyUtils.copy(inputStream, response.getOutputStream());
			}
			catch (final FileNotFoundException fileNotFoundException)
			{
				LOG.error("Decrypt : Could not find file", fileNotFoundException);
			}
		} 
		catch(Exception ex)
		{
		    LOG.error(ex);
		    throw new RuntimeException("Exception occurred while calling through method decryptData ");
		}
	}
	
	public File csvFileWrite( String fileName, StringBuilder content ) throws FileNotFoundException
	{
	    File file = null;
	    PrintWriter pw = null;
	    try
	    {
	        file = new File( fileName );
	        pw = new PrintWriter( file );
	        pw.write( content.toString() );
	    }
	    catch ( FileNotFoundException e )
	    {
	        LOG.error("Exception occured while writing the file ", e);
	    }
	    finally
	    {
	        pw.flush();
	        pw.close();
	    }
	    
	    return file;
	}

	private SiteOneWsSelfServeOnlineAccessRequestData populateFormToRequestData(final SiteOneRequestAccountOnlineAccessForm siteOneRequestAccountOnlineAccessForm)
	{
		final SiteOneWsSelfServeOnlineAccessRequestData siteOneWsSelfServeOnlineAccessRequestData = new SiteOneWsSelfServeOnlineAccessRequestData();
		final SiteoneWsSelfServeAccountInfoOnlineAccessData accountInfo = new SiteoneWsSelfServeAccountInfoOnlineAccessData();
		accountInfo.setAccountNumber(siteOneRequestAccountOnlineAccessForm.getAccountNumber());
		final SiteoneWsSelfServeContactInfoOnlineAccessData contactInfo = new SiteoneWsSelfServeContactInfoOnlineAccessData();
		contactInfo.setContactEmail(siteOneRequestAccountOnlineAccessForm.getEmailAddress());
		siteOneWsSelfServeOnlineAccessRequestData.setAccountInfo(accountInfo);
		siteOneWsSelfServeOnlineAccessRequestData.setContactInfo(contactInfo);
		return siteOneWsSelfServeOnlineAccessRequestData;
	}
	
	public Map<String, SiteOneContrPrimaryBusinessListDTO> getPrimaryBusinessList()
	{
		Map<String, List<SiteOneContrPrimaryBusinessData>> primaryBusinessMap = siteoneContrPrimaryBusinessMapFacade
				.getPrimaryBusinessMap();
		Map<String, SiteOneContrPrimaryBusinessListDTO> primaryBusinessDTOMap=new HashMap<>();

		primaryBusinessMap.entrySet().forEach(map ->{
			List<SiteOneContrPrimaryBusinessWsDTO> primaryBusinessDTOList=getDataMapper().mapAsList(map.getValue(), SiteOneContrPrimaryBusinessWsDTO.class, "FULL");
			SiteOneContrPrimaryBusinessListDTO primaryBusinessListDTO=new SiteOneContrPrimaryBusinessListDTO();
			primaryBusinessListDTO.setSubBusinessList(primaryBusinessDTOList);
			primaryBusinessDTOMap.put(map.getKey(), primaryBusinessListDTO);
		});

		return primaryBusinessDTOMap;
	}

	private SiteoneRequestAccountData populateSiteoneAccountRequestData(final SiteOneRequestAccountForm siteOneRequestAccountForm,final SiteoneWsUpdateAccountInfoWsDTO siteoneWsUpdateAccountInfoWsDTO, final boolean isUECreatedAccount)
	{
		final SiteoneRequestAccountData siteoneRequestAccountData = new SiteoneRequestAccountData();
		siteoneRequestAccountData.setAccountNumber(isUECreatedAccount ?  siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getAccountNumber() : siteOneRequestAccountForm.getAccountNumber());
		siteoneRequestAccountData.setAddressLine1(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getAddressInfo().getAddressLine1() : siteOneRequestAccountForm.getAddressLine1());
		siteoneRequestAccountData.setAddressLine2(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getAddressInfo().getAddressLine2() : siteOneRequestAccountForm.getAddressLine2());
		siteoneRequestAccountData.setCity(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getAddressInfo().getCity() : siteOneRequestAccountForm.getCity());
		siteoneRequestAccountData.setCompanyName(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getCompanyName() : siteOneRequestAccountForm.getCompanyName());
		siteoneRequestAccountData.setContrEmpCount(isUECreatedAccount ? "10" : siteOneRequestAccountForm.getContrEmpCount());
		siteoneRequestAccountData.setContrPrimaryBusiness(isUECreatedAccount ? getPrimaryBuisness(siteoneWsUpdateAccountInfoWsDTO) : siteOneRequestAccountForm.getContrPrimaryBusiness());
		siteoneRequestAccountData.setContrYearsInBusiness(isUECreatedAccount ? "3" : siteOneRequestAccountForm.getContrYearsInBusiness());
		siteoneRequestAccountData.setEmailAddress(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getContactEmail() : siteOneRequestAccountForm.getEmailAddress().trim().toLowerCase());
		siteoneRequestAccountData.setFirstName(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getContactFirstName() : siteOneRequestAccountForm.getFirstName());
		siteoneRequestAccountData.setHasAccountNumber(isUECreatedAccount ? getHasAccountNumber(siteoneWsUpdateAccountInfoWsDTO) : siteOneRequestAccountForm.getHasAccountNumber());
		siteoneRequestAccountData.setIsAccountOwner(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getIsAccountOwner() : siteOneRequestAccountForm.getIsAccountOwner());
		siteoneRequestAccountData.setLanguagePreference(isUECreatedAccount ? "English" : siteOneRequestAccountForm.getLanguagePreference());
		siteoneRequestAccountData.setLastName(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getContactLastName() : siteOneRequestAccountForm.getLastName());
		siteoneRequestAccountData.setPhoneNumber(isUECreatedAccount ? null: siteOneRequestAccountForm.getPhoneNumber());
		siteoneRequestAccountData.setState(isUECreatedAccount ? getUpdateState(siteoneWsUpdateAccountInfoWsDTO) : siteOneRequestAccountForm.getState());
		siteoneRequestAccountData.setTypeOfCustomer(isUECreatedAccount ? getTypeOfCustomer(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getTradeClass()): siteOneRequestAccountForm.getTypeOfCustomer());
		siteoneRequestAccountData.setUuid(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getCorrelationId() : siteOneRequestAccountForm.getUuid());
		siteoneRequestAccountData.setZipcode(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getAddressInfo().getZip() : siteOneRequestAccountForm.getZipcode());
		siteoneRequestAccountData.setStoreNumber(isUECreatedAccount ? (siteoneWsUpdateAccountInfoWsDTO.getBranchInfo() == null ? "172" : siteoneWsUpdateAccountInfoWsDTO.getBranchInfo().getNumber()) : siteOneRequestAccountForm.getStoreNumber());
		siteoneRequestAccountData.setEnrollInPartnersProgram(isUECreatedAccount ? false : siteOneRequestAccountForm.getEnrollInPartnersProgram());
		siteoneRequestAccountData.setLandscapingIndustry(isUECreatedAccount ? false : Boolean.TRUE.equals(siteOneRequestAccountForm.getLandscapingIndustry()));
		siteoneRequestAccountData.setTradeClass(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getTradeClass() : siteOneRequestAccountForm.getTradeClass());
		siteoneRequestAccountData.setSubTradeClass(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getSubTradeClass() : siteOneRequestAccountForm.getSubTradeClass());
		siteoneRequestAccountData.setCreditCode(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getCreditCode() : siteOneRequestAccountForm.getCreditCode());
		siteoneRequestAccountData.setCreditTerms(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getCreditTerms() : siteOneRequestAccountForm.getCreditTerms());
		siteoneRequestAccountData.setAcctGroupCode(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getAcctGroupCode() : siteOneRequestAccountForm.getAcctGroupCode());
		return siteoneRequestAccountData;
	}

	private SiteoneRequestAccountData populateSiteoneParentAccountRequestData(final SiteOneRequestAccountForm siteOneRequestAccountForm,final SiteoneWsUpdateAccountInfoWsDTO siteoneWsUpdateAccountInfoWsDTO, final boolean isUECreatedAccount)
	{
		final SiteoneRequestAccountData siteoneRequestAccountData = new SiteoneRequestAccountData();
		siteoneRequestAccountData.setAccountNumber(isUECreatedAccount ?  siteoneWsUpdateAccountInfoWsDTO.getParentAccountInfo().getAccountNumber() : siteOneRequestAccountForm.getAccountNumber());
		siteoneRequestAccountData.setAddressLine1(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getAddressInfo().getAddressLine1() : siteOneRequestAccountForm.getAddressLine1());
		siteoneRequestAccountData.setAddressLine2(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getAddressInfo().getAddressLine2() : siteOneRequestAccountForm.getAddressLine2());
		siteoneRequestAccountData.setCity(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getAddressInfo().getCity() : siteOneRequestAccountForm.getCity());
		siteoneRequestAccountData.setCompanyName(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getParentAccountInfo().getCompanyName() : siteOneRequestAccountForm.getCompanyName());
		siteoneRequestAccountData.setContrEmpCount(isUECreatedAccount ? "10" : siteOneRequestAccountForm.getContrEmpCount());
		siteoneRequestAccountData.setContrPrimaryBusiness(isUECreatedAccount ? getPrimaryBuisness(siteoneWsUpdateAccountInfoWsDTO) : siteOneRequestAccountForm.getContrPrimaryBusiness());
		siteoneRequestAccountData.setContrYearsInBusiness(isUECreatedAccount ? "3" : siteOneRequestAccountForm.getContrYearsInBusiness());
		siteoneRequestAccountData.setEmailAddress(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getContactEmail() : siteOneRequestAccountForm.getEmailAddress().trim().toLowerCase());
		siteoneRequestAccountData.setFirstName(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getContactFirstName() : siteOneRequestAccountForm.getFirstName());
		siteoneRequestAccountData.setHasAccountNumber(isUECreatedAccount ? getHasAccountNumber(siteoneWsUpdateAccountInfoWsDTO) : siteOneRequestAccountForm.getHasAccountNumber());
		siteoneRequestAccountData.setIsAccountOwner(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getIsAccountOwner() : siteOneRequestAccountForm.getIsAccountOwner());
		siteoneRequestAccountData.setLanguagePreference(isUECreatedAccount ? "English" : siteOneRequestAccountForm.getLanguagePreference());
		siteoneRequestAccountData.setLastName(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getContactLastName() : siteOneRequestAccountForm.getLastName());
		siteoneRequestAccountData.setPhoneNumber(isUECreatedAccount ? null: siteOneRequestAccountForm.getPhoneNumber());
		siteoneRequestAccountData.setState(isUECreatedAccount ? getUpdateState(siteoneWsUpdateAccountInfoWsDTO) : siteOneRequestAccountForm.getState());
		siteoneRequestAccountData.setTypeOfCustomer(isUECreatedAccount ? getTypeOfCustomer(siteoneWsUpdateAccountInfoWsDTO.getParentAccountInfo().getTradeClass()): siteOneRequestAccountForm.getTypeOfCustomer());
		siteoneRequestAccountData.setUuid(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getCorrelationId() : siteOneRequestAccountForm.getUuid());
		siteoneRequestAccountData.setZipcode(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getAddressInfo().getZip() : siteOneRequestAccountForm.getZipcode());
		siteoneRequestAccountData.setStoreNumber(isUECreatedAccount ? (siteoneWsUpdateAccountInfoWsDTO.getBranchInfo() == null ? "172" : siteoneWsUpdateAccountInfoWsDTO.getBranchInfo().getNumber()) : siteOneRequestAccountForm.getStoreNumber());
		siteoneRequestAccountData.setEnrollInPartnersProgram(isUECreatedAccount ? false : siteOneRequestAccountForm.getEnrollInPartnersProgram());
		siteoneRequestAccountData.setTradeClass(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getParentAccountInfo().getTradeClass() : siteOneRequestAccountForm.getTradeClass());
		siteoneRequestAccountData.setSubTradeClass(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getParentAccountInfo().getSubTradeClass() : siteOneRequestAccountForm.getSubTradeClass());
		siteoneRequestAccountData.setCreditCode(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getParentAccountInfo().getCreditCode() : siteOneRequestAccountForm.getCreditCode());
		siteoneRequestAccountData.setCreditTerms(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getParentAccountInfo().getCreditTerms() : siteOneRequestAccountForm.getCreditTerms());
		siteoneRequestAccountData.setAcctGroupCode(isUECreatedAccount ? siteoneWsUpdateAccountInfoWsDTO.getParentAccountInfo().getAcctGroupCode() : siteOneRequestAccountForm.getAcctGroupCode());
		return siteoneRequestAccountData;
	}

	private String getUpdateState(final SiteoneWsUpdateAccountInfoWsDTO siteoneWsUpdateAccountInfoWsDTO) {
		final String countryCode = siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getDivisionId().toString().equalsIgnoreCase("1") ? "US" : "CA";
		return countryCode+"-"+siteoneWsUpdateAccountInfoWsDTO.getAddressInfo().getState();
	}
	public List<String> getEmpCountList()
	{
		final List<String> empCountList = SiteoneCoreConstants.REQUEST_ACCOUNT_CONTR_EMP_COUNT;
		return empCountList;
	}

	private String getTypeOfCustomer(final String tradeClass) {
		if(null!=tradeClass)
		{

		return ((getIsHomeOwner(tradeClass))? SiteoneintegrationConstants.CREATE_CUSTOMER_RETAIL : SiteoneintegrationConstants.CREATE_CUSTOMER_CONTRACTOR);
	}
		else
		{
			return null;
		}
	}

	private boolean getIsHomeOwner(final String tradeClass) {
		final String HOME_OWNER_CODE = configurationService.getConfiguration().getString(HOMEOWNER_CODE);
		return ((tradeClass.equalsIgnoreCase(HOME_OWNER_CODE))? true : false);
	}

	private boolean getHasAccountNumber(final SiteoneWsUpdateAccountInfoWsDTO siteoneWsUpdateAccountInfoWsDTO) {
		return (siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getAccountNumber() != null) ? true : false;
	}

	private String getPrimaryBuisness(final SiteoneWsUpdateAccountInfoWsDTO siteoneWsUpdateAccountInfoWsDTO) {
		Map<String, SiteOneContrPrimaryBusinessListDTO> primaryBusinessDTOMap = getPrimaryBusinessList();
		return siteoneRequestAccountFacade.getContrPrimaryBuisness(primaryBusinessDTOMap, siteoneWsUpdateAccountInfoWsDTO);

	}

	/**
	 * @return the commonI18NService
	 */
	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}



	/**
	 * @param commonI18NService
	 *           the commonI18NService to set
	 */
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

	protected DataMapper getDataMapper()
	{
		return dataMapper;
	}

	protected void setDataMapper(final DataMapper dataMapper)
	{
		this.dataMapper = dataMapper;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
