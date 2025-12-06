package com.siteone.v2.controller;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.siteone.core.exceptions.EwalletNotFoundException;
import com.siteone.core.model.SiteoneEwalletCreditCardModel;
import com.siteone.forms.SiteOneEwalletForm;
import com.siteone.utils.CayanBoarcardResponseForm;
import com.siteone.facades.company.SiteOneB2BUserFacade;
import com.siteone.facades.constants.SiteoneFacadesConstants;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.ewallet.SiteOneEwalletFacade;
import com.siteone.facades.exceptions.CardAlreadyPresentInUEException;
import com.siteone.facades.exceptions.EwalletNotCreatedOrUpdatedInCayanException;
import com.siteone.facades.exceptions.EwalletNotCreatedOrUpdatedInUEException;
import com.siteone.facades.exceptions.NickNameAlreadyPresentInUEException;
import com.siteone.facades.exceptions.ServiceUnavailableException;
import com.siteone.facades.savedList.data.SavedListData;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.google.gson.Gson;
import com.siteone.commerceservice.savedList.data.SavedListDataDTO;
import com.siteone.commerceservices.dto.ewallet.EwalletCustomersListWsDTO;
import com.siteone.commerceservices.dto.ewallet.EwalletSearchPageListWsDTO;
import com.siteone.commerceservices.dto.ewallet.EwalletSearchResultListWsDTO;
import com.siteone.utils.XSSFilterUtil;

import de.hybris.platform.acceleratorservices.config.SiteConfigService;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.ewallet.data.SiteOneEwalletData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commercewebservicescommons.dto.user.UserWsDTO;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;


@Controller
@RequestMapping(value = "/{baseSiteId}/my-account")
@Tag(name = "Siteone Ewallet Controller")
public class SiteoneEwalletController extends AbstractSearchPageController{
	
	public static final int MAX_PAGE_LIMIT = 100;
	private static final String PAGINATION_NUMBER_OF_RESULTS_COUNT = "pagination.number.results.count";
	private static final String EWALLET_SUCCESS = "ewalletAddSuccess";
	private static final String EWALLET_FAILURE = "ewalletAddError";
	
	private static final Logger LOG = Logger.getLogger(SiteoneEwalletController.class);
	
	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;
	
	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;
	
	@Resource(name = "siteOneEwalletFacade")
	private SiteOneEwalletFacade siteOneEwalletFacade;
	
	@Resource(name = "siteConfigService")
	private SiteConfigService siteConfigService;
	
	@Resource(name = "sessionService")
	private SessionService sessionService;
	
	@Resource(name = "messageSource")
	private MessageSource messageSource;
	
	@Resource(name = "i18nService")
	private I18NService i18nService;
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping(value = "/ewallet/{unitId}")
	@ApiBaseSiteIdParam
	@Operation(operationId = "displayEwalletCards", summary = "Display list All cards", description = "Display Ewallet Cards")
	@ResponseBody
	public EwalletSearchPageListWsDTO getEwalletsData(@PathVariable("unitId") final String unitId,
			@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "searchParam", required = false) final String searchParam,
			@RequestParam(value = "shiptounit", required = false) String shipToUnit,
			@RequestParam(value = "pagesize", defaultValue = "10") final String pageSize,
			@RequestParam(value = "sort", defaultValue = SiteoneEwalletCreditCardModel.NAMEONCARD) final String sortCode) throws CMSItemNotFoundException {
		
		EwalletSearchPageListWsDTO ewalletSearchPageListWsDTO = new EwalletSearchPageListWsDTO();
			
		try
		{
			setSessionShipTo(unitId);
			
			boolean isAdminUser = ((SiteOneCustomerFacade) customerFacade).getAdminUserStatus();
			
			if (isAdminUser)
			{
				final String sanitizedunitId = XSSFilterUtil.filter(unitId);
				final String sanitizedsearchParam = XSSFilterUtil.filter(searchParam);
				String userEmail = null;
				String[] userUnitSplit = null;
				String userUnitId = null;
				
				ewalletSearchPageListWsDTO.setShipTos(((SiteOneCustomerFacade) customerFacade).getListOfShiptTos(shipToUnit));
				

				if (null != shipToUnit && shipToUnit.equalsIgnoreCase("shipToopenPopupNew"))
				{
					shipToUnit = sanitizedunitId;
				}
				final String userUnit = (null != shipToUnit) ? shipToUnit : sanitizedunitId;
				final Boolean shipToUnitFlag = (null != shipToUnit && !shipToUnit.equalsIgnoreCase("All")) ? true : false;

				final int viewByPageSize = (null != pageSize) ? Integer.parseInt(pageSize) : siteConfigService.getInt("siteoneorgaddon.search.pageSize", 10);
				final String trimmedSearchParam = (null != sanitizedsearchParam) ? sanitizedsearchParam.trim() : null;

				// Handle paged search results
				final PageableData pageableData = createPageableData(page, viewByPageSize, sortCode, showMode);
				final SearchPageData<SiteOneEwalletData> searchPageData = siteOneEwalletFacade.getPagedEWalletDataForUnit(pageableData, userUnit, trimmedSearchParam, sortCode, shipToUnitFlag);

				EwalletSearchResultListWsDTO ewalletSearchResultListWsDTO = new EwalletSearchResultListWsDTO();
				ewalletSearchResultListWsDTO.setResults(searchPageData.getResults());
				ewalletSearchResultListWsDTO.setSorts(searchPageData.getSorts());
				ewalletSearchResultListWsDTO.setPagination(searchPageData.getPagination());
				
				 final CustomerData customerData = customerFacade.getCurrentCustomer();
				userEmail = customerData.getEmail();
				
				if (Boolean.FALSE.equals(shipToUnitFlag))
				{
					userUnitSplit = sanitizedunitId.split("_");
					userUnitId = userUnitSplit[0];
				}
				else
				{
					userUnitSplit = userUnit.split("_");
					userUnitId = userUnitSplit[0];
				}
				if (sessionService.getAttribute(EWALLET_SUCCESS) != null)
				{
					ewalletSearchPageListWsDTO.setEwalletAddSuccess(sessionService.getAttribute(EWALLET_SUCCESS).toString());
					sessionService.removeAttribute(EWALLET_SUCCESS);
					sessionService.removeAttribute("userEmail");
				}
				else if (sessionService.getAttribute(EWALLET_FAILURE) != null)
				{
					ewalletSearchPageListWsDTO.setEwalletAddSuccess(sessionService.getAttribute(EWALLET_FAILURE).toString());
					sessionService.removeAttribute(EWALLET_FAILURE);
				}
				
				ewalletSearchPageListWsDTO.setSearchPageData(ewalletSearchResultListWsDTO);
				ewalletSearchPageListWsDTO.setSearchParam(trimmedSearchParam);
				ewalletSearchPageListWsDTO.setSortCode(sortCode);
				ewalletSearchPageListWsDTO.setPageSize(viewByPageSize);
				ewalletSearchPageListWsDTO.setUserUnitId(userUnitId);
				ewalletSearchPageListWsDTO.setUserEmail(userEmail);
				ewalletSearchPageListWsDTO.setTotalUnitCount(searchPageData.getPagination().getTotalNumberOfResults());
				ewalletSearchPageListWsDTO.setUserUnit(userUnit);
				ewalletSearchPageListWsDTO.setIsShowAllAllowed(calculateShowAll(searchPageData, showMode));
				ewalletSearchPageListWsDTO.setIsShowPageAllowed(calculateShowPaged(searchPageData, showMode));
				ewalletSearchPageListWsDTO.setIsAdminUser(isAdminUser);
				final int numberPagesShown = siteConfigService.getInt(PAGINATION_NUMBER_OF_RESULTS_COUNT, 5);
				ewalletSearchPageListWsDTO.setNumberPagesShown(Integer.valueOf(numberPagesShown));
			
				return ewalletSearchPageListWsDTO;
				
				
			}

			return null;
		}
		catch(Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method getEwalletsData ");
	    }
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PostMapping(value = "/assign-revoke-ewallet/{unitId}")
	@ApiBaseSiteIdParam
	@Operation(operationId = "ModifyCustomerEwallet", summary = "Assign or Revoke Ewallet API", description = "Assign or Revoke Ewallet API")
	public @ResponseBody String updateEwalletDetails( @PathVariable("unitId") final String unitId,
			@RequestParam(value = "listOfCustIds") final String custUids,
			@RequestParam(value = "vaultToken") final String vaultToken,
			@RequestParam(value = "operationType") final String operationType) throws CMSItemNotFoundException, Exception
	{
		try
		{
			setSessionShipTo(unitId);
			String response = siteOneEwalletFacade.getUsersRevokeEwalletData(custUids,vaultToken,operationType);
			Gson gson = new Gson();
			return gson.toJson(response);
		}
		catch(Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method updateEwalletDetails ");
	    }
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PostMapping(value = "/editEwallet/{unitId}")
	@ApiBaseSiteIdParam
	@Operation(operationId = "Edit Ewallet", summary = "Edit data on  Ewallet API", description = "Return data on Edited Ewallet API")
	public @ResponseBody String editEwallet(@PathVariable("unitId") final String unitId,
			@RequestParam(value = "vaultToken") final String vaultToken,
			@RequestParam(value = "nickName") final String nickName, 
			@RequestParam(value = "expDate") final String expDate) throws CMSItemNotFoundException, ResourceAccessException, EwalletNotCreatedOrUpdatedInCayanException,
			EwalletNotCreatedOrUpdatedInUEException, ModelSavingException, ServiceUnavailableException
	{
		try
		{
			setSessionShipTo(unitId);
			String response = siteOneEwalletFacade.editEwalletData(vaultToken, nickName, expDate);
			Gson gson = new Gson();
			return gson.toJson(response);
		}
		catch(Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method editEwallet ");
	    }
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PostMapping(value = "/delete-card/{unitId}")
	@ApiBaseSiteIdParam
	@Operation(operationId = "Delete Ewallet", summary = "Delete card on Ewallet", description = "Delete card on Ewallet List")
	public @ResponseBody String deleteEwallet(@PathVariable("unitId") final String unitId,
			@RequestParam(value = "vaultToken") final String vaultToken) {
		try
		{
			setSessionShipTo(unitId);
			String response = siteOneEwalletFacade.deleteCardForShipTo(vaultToken);
			Gson gson = new Gson();
			return gson.toJson(response);
		}
		catch(Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method deleteEwallet ");
	    }
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping(value = "/users-assignRevoke/{unitId}")
	@ApiBaseSiteIdParam
	@Operation(operationId = "List Of Customer Ewallet", summary = "List Of Customer Ewallet", description = "List Of Customer Ewallet")
	public @ResponseBody EwalletCustomersListWsDTO getListOfCustomer(@PathVariable("unitId") final String unitId,
			@RequestParam(value = "operationType", required = true) final String operationType,
			@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "pageSize", defaultValue = "5") final int viewByPageSize,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "vaultToken") final String vaultToken
			){
		EwalletCustomersListWsDTO ewalletCustomersListWsDTO = new EwalletCustomersListWsDTO();
		
		try
		{
			setSessionShipTo(unitId);

			// Handle paged search results
			final PageableData pageableData = createPageableData(page, viewByPageSize, SiteoneEwalletCreditCardModel.NAMEONCARD, showMode);
			
			SearchPageData<CustomerData> searchPageableData = siteOneEwalletFacade.getPagedAssignRevokeUsers(pageableData, unitId, SiteoneEwalletCreditCardModel.NAMEONCARD, operationType, vaultToken);
			
			List<UserWsDTO> usersList = getDataMapper().mapAsList(searchPageableData.getResults(), UserWsDTO.class, "FULL");
			ewalletCustomersListWsDTO.setCustomers(usersList);
			ewalletCustomersListWsDTO.setPagination(searchPageableData.getPagination());
			
			return ewalletCustomersListWsDTO;
		}
		catch(Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method getListOfCustomer ");
	    }
	}
	
	private void setSessionShipTo(final String unitId) {
		B2BUnitData unit;
		if (!StringUtils.isEmpty(unitId))
		{
			unit = b2bUnitFacade.getUnitForUid(unitId);
		} else {
			unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
		}
		storeSessionFacade.setSessionShipTo(unit);
	}
	
	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public I18NService getI18nService() {
		return i18nService;
	}

	public void setI18nService(I18NService i18nService) {
		this.i18nService = i18nService;
	}

	
}