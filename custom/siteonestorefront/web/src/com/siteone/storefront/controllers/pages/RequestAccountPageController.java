/**
 *
 */
package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.util.Config;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.event.RequestAccountEvent;
import com.siteone.core.recaptcha.util.VerifyRecaptchaUtils;
import com.siteone.facade.SiteoneRequestAccountData;
import com.siteone.facade.customer.info.SiteOneContrPrimaryBusinessData;
import com.siteone.facade.email.BriteVerifyFacade;
import com.siteone.facade.location.SiteOneRegionFacade;
import com.siteone.facade.requestaccount.SiteoneContrPrimaryBusinessMapFacade;
import com.siteone.facade.requestaccount.SiteoneRequestAccountFacade;
import com.siteone.facades.address.data.SiteOneAddressVerificationData;
import com.siteone.facades.constants.SiteoneFacadesConstants;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.integration.customer.createCustomer.data.SiteOneWsSelfServeOnlineAccessRequestData;
import com.siteone.integration.customer.createCustomer.data.SiteoneWsSelfServeAccountInfoOnlineAccessData;
import com.siteone.integration.customer.createCustomer.data.SiteoneWsSelfServeContactInfoOnlineAccessData;
import com.siteone.storefront.controllers.ControllerConstants;
import com.siteone.storefront.forms.SiteOneRequestAccountForm;
import com.siteone.storefront.forms.SiteOneRequestAccountOnlineAccessForm;
import com.siteone.storefront.util.SiteOneAddressDataUtil;
import com.siteone.storefront.validator.SiteOneRequestAccountValidator;


/**
 * @author 1197861
 *
 */
@Controller
@RequestMapping("/request-account")
public class RequestAccountPageController extends AbstractSearchPageController
{

	private static final String FORM_GLOBAL_ERROR = "form.global.error";
	private static final String EMAIL_ALREADY_EXISTS = "isEmailAlreadyExists";

	@Resource(name = "eventService")
	private EventService eventService;

	@Resource(name = "siteOneRegionFacade")
	private SiteOneRegionFacade siteOneRegionFacade;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;

	@Resource(name = "siteOneRequestAccountValidator")
	private SiteOneRequestAccountValidator siteOneRequestAccountValidator;

	@Resource(name = "briteVerifyFacade")
	private BriteVerifyFacade briteVerifyFacade;

	@Resource(name = "verifyRecaptchaUtils")
	private VerifyRecaptchaUtils verifyRecaptchaUtils;

	@Resource(name = "siteoneContrPrimaryBusinessMapFacade")
	private SiteoneContrPrimaryBusinessMapFacade siteoneContrPrimaryBusinessMapFacade;

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "siteoneRequestAccountFacade")
	private SiteoneRequestAccountFacade siteoneRequestAccountFacade;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "siteoneAddressDataUtil")
	private SiteOneAddressDataUtil siteoneAddressDataUtil;

	@Resource(name = "storeFinderFacade")
	private StoreFinderFacade storeFinderFacade;

	@GetMapping("/form")
	public String getRequestAccountPage(@ModelAttribute("siteOneRequestAccountForm")
	final SiteOneRequestAccountForm siteOneRequestAccountForm, final Model model) throws CMSItemNotFoundException // NOSONAR
	{
		if ((sessionService.getAttribute("guestUser") != null
				&& sessionService.getAttribute("guestUser").toString().equalsIgnoreCase("guest")))
		{
			if (sessionService.getAttribute("orderGuestContactPerson") != null)
			{
				final CustomerData cust = sessionService.getAttribute("orderGuestContactPerson");
				siteOneRequestAccountForm.setFirstName(cust.getDefaultAddress().getFirstName());
				siteOneRequestAccountForm.setEmailAddress(cust.getEmail());
				siteOneRequestAccountForm.setAddressLine1(cust.getDefaultAddress().getLine1());
				siteOneRequestAccountForm.setLastName(cust.getDefaultAddress().getLastName());
				siteOneRequestAccountForm.setCity(cust.getDefaultAddress().getTown());
				siteOneRequestAccountForm.setZipcode(cust.getDefaultAddress().getPostalCode());
				siteOneRequestAccountForm.setState(cust.getDefaultAddress().getRegion().getName());
				siteOneRequestAccountForm.setPhoneNumber(cust.getContactNumber());
			}
		}
		 List<RegionData> regionDataList= new ArrayList<RegionData>();
		final BaseSiteModel basesite = baseSiteService.getCurrentBaseSite();
		if (basesite.getUid().equalsIgnoreCase("siteone-us"))
		{
		 regionDataList = siteOneRegionFacade.getRegionsForCountryCode("US");
		}
		else {
			 regionDataList = siteOneRegionFacade.getRegionsForCountryCode("CA");
		}
		regionDataList.sort(Comparator.comparing(RegionData::getName));
		model.addAttribute("states", regionDataList);
		model.addAttribute("recaptchaPublicKey", Config.getString("recaptcha.publickey", null));
		model.addAttribute("primaryBusinessList", getPrimaryBusinessList());
		model.addAttribute("empCountList", getEmpCountList());
		storeCmsPageInModel(model, getContentPageForLabelOrId("requestaccount"));
		return ControllerConstants.Views.Pages.Account.RequestAccountPage;
	}

	@GetMapping("/onlineAccess")
	public String getRequestAccountOnlineAccessPage(@ModelAttribute("siteOneRequestOnlineAccessForm")
	final SiteOneRequestAccountOnlineAccessForm siteOneRequestAccountOnlineAccessForm, final Model model)
			throws CMSItemNotFoundException // NOSONAR
	{
		model.addAttribute("recaptchaPublicKey", Config.getString("recaptcha.publickey", null));
		storeCmsPageInModel(model, getContentPageForLabelOrId("requestAccountOnlineAccess"));
		return ControllerConstants.Views.Pages.Account.RequestAccountOnlineAccessPage;
	}

	@PostMapping(path = "/onlineAccessResponse")
	public @ResponseBody String editRequestAccountOnlineAccessPage(
			final SiteOneRequestAccountOnlineAccessForm siteOneRequestAccountOnlineAccessForm, final HttpServletRequest request,
			final Model model) throws CMSItemNotFoundException // NOSONAR
	{
		final String recaptchaResponse = request.getParameter("recaptcha");

		boolean verify = false;
		if (StringUtils.isNotEmpty(recaptchaResponse))
		{
			verify = verifyRecaptchaUtils.checkAnswer(recaptchaResponse);
		}

		if (!verify)
		{
			model.addAttribute("recaptchaChallengeAnswered", Boolean.valueOf(verify));
			GlobalMessages.addErrorMessage(model, FORM_GLOBAL_ERROR);
			return getRequestAccountOnlineAccessPage(siteOneRequestAccountOnlineAccessForm, model);
		}

		String selfServeOnlineAccessResponse = "";
		if (null != siteOneRequestAccountOnlineAccessForm.getEmailAddress())
		{
			final B2BCustomerModel customerModel = (B2BCustomerModel) b2bCustomerService
					.getUserForUID(siteOneRequestAccountOnlineAccessForm.getEmailAddress().trim().toLowerCase());
			 String accountNo =null;
			final BaseSiteModel basesite = baseSiteService.getCurrentBaseSite();
			if (basesite.getUid().equalsIgnoreCase("siteone-us"))
			{
			 accountNo = siteOneRequestAccountOnlineAccessForm.getAccountNumber() + "_US";
			}
			else {
				accountNo = siteOneRequestAccountOnlineAccessForm.getAccountNumber() + "_CA";
			}
			if (siteOneRequestAccountOnlineAccessForm.getAccountNumber().contains("-"))
			{
				return "accountNumberNotExists";
			}
			if (customerModel != null)
			{
				if (customerModel.getDefaultB2BUnit().getUid().equalsIgnoreCase(accountNo))
				{
					return "isEmailExist";
				}
				else
				{
					return "accountNumberNotExists";
				}
			}
			else
			{
				final SiteOneWsSelfServeOnlineAccessRequestData siteOneWsSelfServeOnlineAccessRequestData = populateFormToRequestData(
						siteOneRequestAccountOnlineAccessForm);
				selfServeOnlineAccessResponse = siteoneRequestAccountFacade
						.getSelfServeResponse(siteOneWsSelfServeOnlineAccessRequestData);
			}
		}
		return selfServeOnlineAccessResponse;
	}

	private SiteOneWsSelfServeOnlineAccessRequestData populateFormToRequestData(
			final SiteOneRequestAccountOnlineAccessForm siteOneRequestAccountOnlineAccessForm)
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

	@PostMapping(path = "/validate-address")
	public @ResponseBody SiteOneAddressVerificationData validateAddress(final SiteOneRequestAccountForm siteOneRequestAccountForm)
	{
		final AddressData addressData = siteoneAddressDataUtil.convertToAddressData(siteOneRequestAccountForm);
		return ((SiteOneCustomerFacade) customerFacade).validateAddress(addressData);

	}

	@PostMapping(path = "/createCustomer")
	public @ResponseBody String createCustomer(final SiteOneRequestAccountForm siteOneRequestAccountForm)
	{
		final SiteoneRequestAccountData siteoneRequestAccountData = populateSiteoneAccountRequestData(siteOneRequestAccountForm);
		siteoneRequestAccountFacade.populateSiteoneRequestAccountDataToModel(siteoneRequestAccountData);
		return siteoneRequestAccountFacade.createCustomer(siteoneRequestAccountData, null, false);
	}


	@PostMapping("/form")
	public String editRequestAccountPage(final SiteOneRequestAccountForm siteOneRequestAccountForm,
			final BindingResult bindingResult, final HttpServletRequest request, final Model model) throws CMSItemNotFoundException // NOSONAR
	{
		sessionService.getAttribute(EMAIL_ALREADY_EXISTS);
		siteOneRequestAccountValidator.validate(siteOneRequestAccountForm, bindingResult);
		final String recaptchaResponse = request.getParameter("recaptcha");

		boolean verify = false;
		if (StringUtils.isNotEmpty(recaptchaResponse))
		{
			verify = verifyRecaptchaUtils.checkAnswer(recaptchaResponse);
		}

		if (!verify || bindingResult.hasErrors())
		{
			model.addAttribute("recaptchaChallengeAnswered", Boolean.valueOf(verify));
			GlobalMessages.addErrorMessage(model, FORM_GLOBAL_ERROR);
			return getRequestAccountPage(siteOneRequestAccountForm, model);
		}

		siteoneRequestAccountFacade
				.populateSiteoneRequestAccountDataToModel(populateSiteoneAccountRequestData(siteOneRequestAccountForm));
		model.addAttribute("siteOneRequestAccountForm", siteOneRequestAccountForm);
		model.addAttribute(EMAIL_ALREADY_EXISTS, sessionService.getAttribute(EMAIL_ALREADY_EXISTS));
		storeCmsPageInModel(model, getContentPageForLabelOrId("requestaccountsuccess"));
		return ControllerConstants.Views.Pages.Account.RequestAccountSuccessPage;
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
		if (baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase(SiteoneCoreConstants.BASESITE_US))
      {
      	event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_US_BASESTORE));
      	event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_US));
       }
      else
      {
      	event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_CA_BASESTORE));
      	event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_CA));
      }
	
		event.setEmailAddress(siteOneRequestAccountForm.getEmailAddress());
		event.setTypeOfCustomer(siteOneRequestAccountForm.getTypeOfCustomer());
		event.setContrYearsInBusiness(siteOneRequestAccountForm.getContrYearsInBusiness());
		event.setContrEmpCount(siteOneRequestAccountForm.getContrEmpCount());
		if (siteOneRequestAccountForm.getContrPrimaryBusiness() != null)
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
	public @ResponseBody String briteverifyValidate(@RequestParam("email")
	final String email, final Model model, final RedirectAttributes redirectModel, final HttpServletResponse response,
			final HttpServletRequest request) throws CMSItemNotFoundException
	{
		final String briteVerifyResponse = briteVerifyFacade.validateEmailId(email);
		return briteVerifyResponse;
	}

	@PostMapping(path = "/isEmailAlreadyExists")
	public @ResponseBody boolean isEmailAlreadyExists(final SiteOneRequestAccountForm siteOneRequestAccountForm)
	{
		final boolean isEmailAlreadyExists = ((SiteOneCustomerFacade) customerFacade)
				.isUserAvailable(siteOneRequestAccountForm.getEmailAddress());
		if (isEmailAlreadyExists)
		{
			final RequestAccountEvent requestAccountEvent = new RequestAccountEvent();
			requestAccountEvent.setIsEmailUniqueInHybris(false);
			sessionService.setAttribute(EMAIL_ALREADY_EXISTS, true);
			eventService.publishEvent(initializeEvent(requestAccountEvent, siteOneRequestAccountForm));
		}
		return isEmailAlreadyExists;
	}

	public Map<String, List<SiteOneContrPrimaryBusinessData>> getPrimaryBusinessList()
	{
		return siteoneContrPrimaryBusinessMapFacade.getPrimaryBusinessMap();

	}


	public List<String> getEmpCountList()
	{
		final List<String> empCountList = SiteoneCoreConstants.REQUEST_ACCOUNT_CONTR_EMP_COUNT;
		return empCountList;
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

	private SiteoneRequestAccountData populateSiteoneAccountRequestData(final SiteOneRequestAccountForm siteOneRequestAccountForm)
	{
		final SiteoneRequestAccountData siteoneRequestAccountData = new SiteoneRequestAccountData();
		siteoneRequestAccountData.setAccountNumber(siteOneRequestAccountForm.getAccountNumber());
		siteoneRequestAccountData.setAddressLine1(siteOneRequestAccountForm.getAddressLine1());
		siteoneRequestAccountData.setAddressLine2(siteOneRequestAccountForm.getAddressLine2());
		siteoneRequestAccountData.setCity(siteOneRequestAccountForm.getCity());
		siteoneRequestAccountData.setCompanyName(siteOneRequestAccountForm.getCompanyName());
		siteoneRequestAccountData.setContrEmpCount(siteOneRequestAccountForm.getContrEmpCount());
		siteoneRequestAccountData.setContrPrimaryBusiness(siteOneRequestAccountForm.getContrPrimaryBusiness());
		siteoneRequestAccountData.setContrYearsInBusiness(siteOneRequestAccountForm.getContrYearsInBusiness());
		siteoneRequestAccountData.setEmailAddress(siteOneRequestAccountForm.getEmailAddress().trim().toLowerCase());
		siteoneRequestAccountData.setFirstName(siteOneRequestAccountForm.getFirstName());
		siteoneRequestAccountData.setLastName(siteOneRequestAccountForm.getLastName());
		siteoneRequestAccountData.setHasAccountNumber(siteOneRequestAccountForm.getHasAccountNumber());
		siteoneRequestAccountData.setIsAccountOwner(siteOneRequestAccountForm.getIsAccountOwner());
		siteoneRequestAccountData.setLanguagePreference(siteOneRequestAccountForm.getLanguagePreference());
		siteoneRequestAccountData.setPhoneNumber(siteOneRequestAccountForm.getPhoneNumber());
		siteoneRequestAccountData.setState(siteOneRequestAccountForm.getState());
		siteoneRequestAccountData.setTypeOfCustomer(siteOneRequestAccountForm.getTypeOfCustomer());
		siteoneRequestAccountData.setUuid(siteOneRequestAccountForm.getUuid());
		siteoneRequestAccountData.setZipcode(siteOneRequestAccountForm.getZipcode());
		siteoneRequestAccountData.setStoreNumber(siteOneRequestAccountForm.getStoreNumber());
		siteoneRequestAccountData.setEnrollInPartnersProgram(siteOneRequestAccountForm.getEnrollInPartnersProgram());
		siteoneRequestAccountData.setLandscapingIndustry(siteOneRequestAccountForm.getLandscapingIndustry());
		siteoneRequestAccountData.setUuidType(siteOneRequestAccountForm.getUeType());
		return siteoneRequestAccountData;
	}



	@GetMapping(path = "/getStoreData")
	public @ResponseBody List<PointOfServiceData> getStoreData(@RequestParam(value = "zipcode")
	final String zipcode, @RequestParam(value = "miles", required = false)
	final String miles)
	{
		final String[] postalcode = zipcode.split("-");
		return ((SiteOneStoreFinderFacade) storeFinderFacade).getStoresForZipcode(postalcode[0], Double.parseDouble(miles));
	}
}
