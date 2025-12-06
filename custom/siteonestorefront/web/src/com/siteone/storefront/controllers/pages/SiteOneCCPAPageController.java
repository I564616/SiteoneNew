/**
 *
 */
package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.util.Config;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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

import com.siteone.core.event.CCPACustomerEvent;
import com.siteone.core.event.CCPAEvent;
import com.siteone.core.recaptcha.util.VerifyRecaptchaUtils;
import com.siteone.facade.email.BriteVerifyFacade;
import com.siteone.facade.location.SiteOneRegionFacade;
import com.siteone.storefront.forms.CCPAForm;
import com.siteone.storefront.validator.SiteOneCCPAValidator;
import com.siteone.core.constants.SiteoneCoreConstants;


/**
 * @author BS
 *
 */
@Controller
@RequestMapping("/privacyrequest")
public class SiteOneCCPAPageController extends AbstractSearchPageController
{

	private static final String FORM_GLOBAL_ERROR = "form.global.error";

	private static final String FORM_STATE_ERROR = "form.state.error";
	
	private static final String CALIFORNIA = "California";
	
	@Resource(name = "eventService")
	private EventService eventService;

	@Resource(name = "siteOneRegionFacade")
	private SiteOneRegionFacade siteOneRegionFacade;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Resource(name = "siteoneCCPAValidator")
	private SiteOneCCPAValidator siteoneCCPAValidator;

	@Resource(name = "briteVerifyFacade")
	private BriteVerifyFacade briteVerifyFacade;

	@Resource(name = "verifyRecaptchaUtils")
	private VerifyRecaptchaUtils verifyRecaptchaUtils;

	@Resource(name = "userFacade")
	private UserFacade userFacade;


	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

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

	private static final Logger LOG = Logger.getLogger(SiteOneCCPAPageController.class);
	private static final String PRIVACY_REQUEST_PAGE = "ccpa";
	private static final String PRIVACY_REQUEST_CONFIRMATION = "ccpaConfirmation";

	@GetMapping
	public String getCCPAPage(final CCPAForm siteoneCCPAForm, final Model model, final RedirectAttributes redirectAttributes)
			throws CMSItemNotFoundException // NOSONAR
	{
		if (userFacade.isAnonymousUser())
		{
			GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.CONF_MESSAGES_HOLDER,
					"privacyrequest.message.anonymous", null);
		}
		populateStates(model);
		final List<String> requestType = setRequestTypes();
		model.addAttribute("siteoneCCPAForm", siteoneCCPAForm);
		model.addAttribute("requestType", requestType);
		model.addAttribute("recaptchaPublicKey", Config.getString("recaptcha.publickey", null));
		setUpPrivacyRequestBreadcrumb(model);
		storeCmsPageInModel(model, getContentPageForLabelOrId(PRIVACY_REQUEST_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(PRIVACY_REQUEST_PAGE));
		return getViewForPage(model);
	}



	@PostMapping
	public String editCCPAPage(@ModelAttribute("siteoneCCPAForm")
	final CCPAForm siteoneCCPAForm, final BindingResult bindingResult, final HttpServletRequest request, final Model model,
			final RedirectAttributes redirectAttributes) throws CMSItemNotFoundException // NOSONAR
	{
		siteoneCCPAValidator.validate(siteoneCCPAForm, bindingResult);
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
			return getCCPAPage(siteoneCCPAForm, model, redirectAttributes);
		}

		eventService.publishEvent(initializeEvent(new CCPACustomerEvent(), siteoneCCPAForm));
		eventService.publishEvent(initializeEvent(new CCPAEvent(), siteoneCCPAForm));
		model.addAttribute("siteoneCCPAForm", siteoneCCPAForm);
		setUpPrivacyRequestBreadcrumb(model);
		storeCmsPageInModel(model, getContentPageForLabelOrId("ccpaConfirmation"));
		storeCmsPageInModel(model, getContentPageForLabelOrId(PRIVACY_REQUEST_CONFIRMATION));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(PRIVACY_REQUEST_CONFIRMATION));
		return getViewForPage(model);
	}

	private CCPACustomerEvent initializeEvent(final CCPACustomerEvent CCPACustomerEvent, final CCPAForm siteoneCCPAForm)
	{
		CCPACustomerEvent.setFirstName(siteoneCCPAForm.getFirstName());
		CCPACustomerEvent.setEmailAddress(siteoneCCPAForm.getEmailAddress());
		if (baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase(SiteoneCoreConstants.BASESITE_US))
		{
			CCPACustomerEvent.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_US));
		}
		else {
			CCPACustomerEvent.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_CA));
		}
		
		CCPACustomerEvent.setLanguage(getCommonI18NService().getCurrentLanguage());
		return CCPACustomerEvent;
	}

	public CCPAEvent initializeEvent(final CCPAEvent event, final CCPAForm siteoneCCPAForm)
	{
		event.setCompanyName(siteoneCCPAForm.getCompanyName());
		event.setAccountNumber(siteoneCCPAForm.getAccountNumber());
		event.setFirstName(siteoneCCPAForm.getFirstName());
		event.setLastName(siteoneCCPAForm.getLastName());
		event.setAddressLine1(siteoneCCPAForm.getAddressLine1());
		event.setAddressLine2(siteoneCCPAForm.getAddressLine2());
		event.setCity(siteoneCCPAForm.getCity());
		event.setState(siteoneCCPAForm.getState());
		event.setZipcode(siteoneCCPAForm.getZipcode());
		event.setPhoneNumber(siteoneCCPAForm.getPhoneNumber());
		event.setPrivacyRequestType(siteoneCCPAForm.getPrivacyRequestType());
		if (baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase(SiteoneCoreConstants.BASESITE_US))
		{
			event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_US_BASESTORE));
			event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_US));
		}
		else {
			event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_CA_BASESTORE));
			event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_CA));
		}
		event.setLanguage(getCommonI18NService().getCurrentLanguage());
		event.setEmailAddress(siteoneCCPAForm.getEmailAddress());
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


	private List<String> setRequestTypes()
	{
		final List<String> requestType = new ArrayList<>();
		requestType.add(getMessageSource().getMessage("privacy.get.info", null, getI18nService().getCurrentLocale()));
		requestType.add(getMessageSource().getMessage("privacy.delete.info", null, getI18nService().getCurrentLocale()));
		requestType.add(getMessageSource().getMessage("privacy.opt.info", null, getI18nService().getCurrentLocale()));

		return requestType;
	}

	private void setUpPrivacyRequestBreadcrumb(final Model model)
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<>();

		final Breadcrumb breadcrumbPrivacy = new Breadcrumb("/privacypolicy",
				getMessageSource().getMessage("breadcrumb.privacy", null, getI18nService().getCurrentLocale()), null);
		final Breadcrumb breadcrumbCCPACompliance = new Breadcrumb("/privacyrequest",
				getMessageSource().getMessage("breadcrumb.ccpaCompliance", null, getI18nService().getCurrentLocale()), null);

		breadcrumbs.add(breadcrumbPrivacy);
		breadcrumbs.add(breadcrumbCCPACompliance);
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadcrumbs);

	}

	private void populateStates(final Model model)
	{
		final List<String> regionCode = new ArrayList<String>();
		List<RegionData> regionDataList = new ArrayList<RegionData>();
		if (baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase(SiteoneCoreConstants.BASESITE_US))
		{
			regionDataList=siteOneRegionFacade.getRegionsForCountryCode(SiteoneCoreConstants.US_ISO_CODE);
		}
		else {
			regionDataList=siteOneRegionFacade.getRegionsForCountryCode(SiteoneCoreConstants.CA_ISO_CODE);
		}
		for (final RegionData data : regionDataList)
		{
			regionCode.add(data.getName());
		}
		model.addAttribute("states", regionCode);
	}

}
