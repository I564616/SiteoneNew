/**
 *
 */
package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.util.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.siteone.core.event.ContactUsCustomerEvent;
import com.siteone.core.event.ContactUsEvent;
import com.siteone.core.recaptcha.util.VerifyRecaptchaUtils;
import com.siteone.facade.location.SiteOneRegionFacade;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.store.SiteOneStoreDetailsFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.storefront.forms.SiteOneContactUsForm;
import com.siteone.core.constants.SiteoneCoreConstants;




/**
 * @author 1003567
 *
 */
@Controller
@RequestMapping("/contactus")
public class SiteoneContactUsController extends AbstractSearchPageController
{
	@Resource(name = "eventService")
	private EventService eventService;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "verifyRecaptchaUtils")
	private VerifyRecaptchaUtils verifyRecaptchaUtils;

	@Resource(name = "i18nService")
	private I18NService i18nService;

	@Resource(name = "messageSource")
	private MessageSource messageSource;

	@Resource(name = "siteOneRegionFacade")
	private SiteOneRegionFacade siteOneRegionFacade;


	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;
	
	@Resource(name = "siteOnestoreDetailsFacade")
	private SiteOneStoreDetailsFacade siteOnestoreDetailsFacade;

	/**
	 * @return the i18nService
	 */
	@Override
	public I18NService getI18nService()
	{
		return i18nService;
	}

	/**
	 * @param i18nService
	 *           the i18nService to set
	 */
	public void setI18nService(final I18NService i18nService)
	{
		this.i18nService = i18nService;
	}

	/**
	 * @return the messageSource
	 */
	@Override
	public MessageSource getMessageSource()
	{
		return messageSource;
	}

	/**
	 * @param messageSource
	 *           the messageSource to set
	 */
	public void setMessageSource(final MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}

	private static final Logger LOG = Logger.getLogger(SiteoneContactUsController.class);

	private static final String CONTACT_US_PAGE = "contactUs";
	private static final String CONTACT_US_CA_PAGE = "contactUs-ca";
	private static final String[] REASON_FOR_CONTACT =
	{ "Accounts receivable / credit", "Become a SiteOne customer", "Nursery","Partners Points", "Product support", "Request a quote",
			"Sales and orders", "SiteOne account help", "Web site feedback", "Other" };

	private static final String CONFIRMATION_PAGE = "contactUsConfirmation";

	private static final String CONTACT_US_FULLPAGEPATH = "analytics.fullpath.contact.us";
	private static final String CONTACT_US_CONFIRMATION_FULLPAGEPATH = "analytics.fullpath.contact.us.confirmation";
	private static final String HARDSCAPES_FULLPAGEPATH = "analytics.fullpath.contact.us.hardscapes";
	private static final String HARDSCAPES_CONFIRMATION_FULLPAGEPATH = "analytics.fullpath.contact.us.confirmation.hardscapes";
	private static final String REASONFORCONTACT = "reasonForContact";
	private static final String DRAINAGE_FULLPAGEPATH = "analytics.fullpath.contact.us.drainage";
	private static final String DRAINAGE_CONFIRMATION_FULLPAGEPATH = "analytics.fullpath.contact.us.confirmation.drainage";
	private static final String LIGHTING_FULLPAGEPATH = "analytics.fullpath.contact.us.lighting";
	private static final String LIGHTING_CONFIRMATION_FULLPAGEPATH = "analytics.fullpath.contact.us.confirmation.lighting";
	private static final String RECAPTCHA_PUBLICKEY = "recaptchaPublicKey";
	private static final String PHASEOF = "phaseOf";
	private static final String RECAPTCHA_CHALLENGE_ANSWERED = "recaptchaChallengeAnswered";
	private static final String RECAPTCHA = "recaptcha";
	private static final String RECAPTCHA_KEY = "recaptcha.publickey";
	private static final String PROJECT = "project";
	private static final String BUDGET = "budget";
	private static final String IDENTITY = "identity";

	@GetMapping
	public String getContactUsPage(@ModelAttribute("siteOneContactUsForm") final SiteOneContactUsForm siteOneContactUsForm, 
			final Model model, @RequestParam(value = "storeId", required = false) final String storeId,
			final HttpServletResponse response, @RequestParam(value = "isMobileApp", required = false) final boolean isMobileApp) throws CMSItemNotFoundException
	{
		PointOfServiceData sessionPos = null;
		if(StringUtils.isNotEmpty(storeId)){
			sessionPos = siteOnestoreDetailsFacade.fetchSiteOnePointOfService(storeId);
		}else{
			sessionPos = ((SiteOneCustomerFacade) customerFacade).getPreferredStore();
			if (sessionPos == null)
			{
				sessionPos = storeSessionFacade.getSessionStore();
			}
		}
		
		if (isMobileApp)
		{
			final Cookie newCookie = new Cookie("isMobileApp", "true");
			newCookie.setMaxAge(60 * 60);
			newCookie.setPath("/");
			newCookie.setSecure(true);
			response.addCookie(newCookie);
		}
		
		final List<String> reasonForContact = setReasonForContactUs();
		model.addAttribute(REASONFORCONTACT, reasonForContact);
		model.addAttribute("hardscapesContactUs", Boolean.FALSE);
		populateStates(model);
		model.addAttribute(RECAPTCHA_PUBLICKEY, Config.getString(RECAPTCHA_KEY, null));
		LOG.info(sessionPos);
		model.addAttribute("sessionPos", sessionPos);
		setUpContactUsBreadcrumb(model);
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.INDEX_FOLLOW);
		if (baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase("siteone-ca"))
		{
			getContentPageForLabelOrId(CONTACT_US_CA_PAGE).setFullPagePath(
					getMessageSource().getMessage(CONTACT_US_FULLPAGEPATH, null, getI18nService().getCurrentLocale()));
			storeCmsPageInModel(model, getContentPageForLabelOrId(CONTACT_US_CA_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CONTACT_US_CA_PAGE));
			return getViewForPage(model);
		}
		else
		{
			getContentPageForLabelOrId(CONTACT_US_PAGE).setFullPagePath(
					getMessageSource().getMessage(CONTACT_US_FULLPAGEPATH, null, getI18nService().getCurrentLocale()));
			storeCmsPageInModel(model, getContentPageForLabelOrId(CONTACT_US_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CONTACT_US_PAGE));
			return getViewForPage(model);
		}
	}

	@PostMapping
	public String sendContactUsEmail(@Valid
	final SiteOneContactUsForm siteOneContactUsForm, final BindingResult bindingResult, final HttpServletRequest request,
			final Model model) throws CMSItemNotFoundException
	{
		PointOfServiceData sessionPos = ((SiteOneCustomerFacade) customerFacade).getPreferredStore();

		final String recaptchaResponse = request.getParameter(RECAPTCHA);

		boolean verify = false;
		if (StringUtils.isNotEmpty(recaptchaResponse))
		{
			verify = verifyRecaptchaUtils.checkAnswer(recaptchaResponse);
		}
		if (!verify)
		{
			model.addAttribute(RECAPTCHA_CHALLENGE_ANSWERED, Boolean.FALSE);
			return getContactUsPage(siteOneContactUsForm, model, null, null, false);
		}
		if (sessionPos == null)
		{
			sessionPos = storeSessionFacade.getSessionStore();
		}
		LOG.info(sessionPos);
		model.addAttribute("sessionPos", sessionPos);
		eventService.publishEvent(initializeEvent(new ContactUsEvent(), siteOneContactUsForm));
		eventService.publishEvent(initializeEvent(new ContactUsCustomerEvent(), siteOneContactUsForm));
		setUpContactUsBreadcrumb(model);
		getContentPageForLabelOrId(CONFIRMATION_PAGE).setFullPagePath(
				getMessageSource().getMessage(CONTACT_US_CONFIRMATION_FULLPAGEPATH, null, getI18nService().getCurrentLocale()));
		storeCmsPageInModel(model, getContentPageForLabelOrId(CONFIRMATION_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CONFIRMATION_PAGE));

		return getViewForPage(model);
	}

	@GetMapping("/hardscapes")
	public String getContactUsHardscapePage(@ModelAttribute("siteOneContactUsForm")
	final SiteOneContactUsForm siteOneContactUsForm, final Model model) throws CMSItemNotFoundException
	{
		final List<String> reasonForContact = new ArrayList<>();
		reasonForContact
				.add(getMessageSource().getMessage("help.contactUs.reason.hardscapes", null, getI18nService().getCurrentLocale()));

		final String typeContactUs = "hardscapesContactUs";
		return getViewForModel(typeContactUs, HARDSCAPES_FULLPAGEPATH, model, reasonForContact);
	}

	@PostMapping("/hardscapes")
	public String getContactUsHardscapePage(@Valid
	final SiteOneContactUsForm siteOneContactUsForm, final BindingResult bindingResult, final HttpServletRequest request,
			final Model model) throws CMSItemNotFoundException
	{
		return getContactUsPageDetails(HARDSCAPES_CONFIRMATION_FULLPAGEPATH, siteOneContactUsForm, request, model);
	}

	@GetMapping(path = "/drainage")
	public String getContactUsDrainagePage(@ModelAttribute("siteOneContactUsForm")
	final SiteOneContactUsForm siteOneContactUsForm, final Model model) throws CMSItemNotFoundException
	{
		final String typeContactUs = "drainageContactUs";
		final List<String> reasonForContact = new ArrayList<>();
		reasonForContact
				.add(getMessageSource().getMessage("help.contactUs.reason.callBack", null, getI18nService().getCurrentLocale()));
		reasonForContact
				.add(getMessageSource().getMessage("help.contactUs.reason.support", null, getI18nService().getCurrentLocale()));

		return getViewForModel(typeContactUs, DRAINAGE_FULLPAGEPATH, model, reasonForContact);

	}

	@PostMapping(path = "/drainage")
	public String getContactUsDrainagePage(@Valid
	final SiteOneContactUsForm siteOneContactUsForm, final BindingResult bindingResult, final HttpServletRequest request,
			final Model model) throws CMSItemNotFoundException
	{
		return getContactUsPageDetails(DRAINAGE_CONFIRMATION_FULLPAGEPATH, siteOneContactUsForm, request, model);
	}



	/** Start Adding for SE-9768 **/

	@GetMapping(path = "/lighting")
	public String getContactUsLightingPage(@ModelAttribute("siteOneContactUsForm")
	final SiteOneContactUsForm siteOneContactUsForm, final Model model) throws CMSItemNotFoundException
	{
		final String typeContactUs = "lightingEmailContactUs";
		final List<String> reasonForContact = setLightingReasonOf();

		return getViewForModel(typeContactUs, LIGHTING_FULLPAGEPATH, model, reasonForContact);
	}

	@PostMapping(path = "/lighting")
	public String getContactUsLightingPage(@Valid
	final SiteOneContactUsForm siteOneContactUsForm, final BindingResult bindingResult, final HttpServletRequest request,
			final Model model) throws CMSItemNotFoundException
	{
		return getContactUsPageDetails(LIGHTING_CONFIRMATION_FULLPAGEPATH, siteOneContactUsForm, request, model);
	}

	@GetMapping(path = "/lighting/outdoor")
	@ResponseBody
	public Map<String, List<String>> getContactUsLightingOutdoorPage()
	{
		final Map<String, List<String>> outdoor = new HashMap<>();
		outdoor.put(PHASEOF, setOutdoorPhaseOf());
		outdoor.put(PROJECT, setOutdoorProject());
		return outdoor;
	}

	/** End Adding for SE-9768 **/

	private void populateStates(final Model model)
	{
		final List<String> regionCode = new ArrayList<String>();
		 List<RegionData> regionDataList = new ArrayList<RegionData>();
				if (baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase("siteone-us"))
				{
					regionDataList=siteOneRegionFacade.getRegionsForCountryCode("US");
				}
				else {
					regionDataList=siteOneRegionFacade.getRegionsForCountryCode("CA");
				}
		for (final RegionData data : regionDataList)
		{
			regionCode.add(data.getIsocodeShort());
		}
		model.addAttribute("states", regionCode);
	}

	private ContactUsCustomerEvent initializeEvent(final ContactUsCustomerEvent contactUsCustomerEvent,
			final SiteOneContactUsForm siteOneContactUsForm)
	{
		contactUsCustomerEvent.setFirstName(siteOneContactUsForm.getFirstName());
		contactUsCustomerEvent.setEmailAddress(siteOneContactUsForm.getEmail());
		if (baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase("siteone-us"))
		{
			contactUsCustomerEvent.setSite(baseSiteService.getBaseSiteForUID("siteone-us"));
		}
		else 
		{
			contactUsCustomerEvent.setSite(baseSiteService.getBaseSiteForUID("siteone-ca"));
		}
		contactUsCustomerEvent.setLanguage(getCommonI18NService().getCurrentLanguage());
		return contactUsCustomerEvent;
	}

	protected ContactUsEvent initializeEvent(final ContactUsEvent contactUsEvent, final SiteOneContactUsForm siteOneContactUsForm)
	{
		contactUsEvent.setCustomerNumber(siteOneContactUsForm.getCustomerNumber());
		contactUsEvent.setFirstName(siteOneContactUsForm.getFirstName());
		contactUsEvent.setLastName(siteOneContactUsForm.getLastName());
		contactUsEvent.setEmail(siteOneContactUsForm.getEmail());
		contactUsEvent.setReasonForContact(siteOneContactUsForm.getReasonForContact());
		contactUsEvent.setPhoneNumber(siteOneContactUsForm.getPhoneNumber());
		contactUsEvent.setTypeOfCustomer(siteOneContactUsForm.getTypeOfCustomer());
		contactUsEvent.setMessage(siteOneContactUsForm.getMessage());
		if (baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase("siteone-us"))
		{
			contactUsEvent.setSite(baseSiteService.getBaseSiteForUID("siteone-us"));
		}
		else 
		{
			contactUsEvent.setSite(baseSiteService.getBaseSiteForUID("siteone-ca"));
		}
		contactUsEvent.setLanguage(getCommonI18NService().getCurrentLanguage());
		contactUsEvent.setCustomerCity(siteOneContactUsForm.getCustomerCity());
		contactUsEvent.setCustomerState(siteOneContactUsForm.getCustomerState());
		contactUsEvent.setProjectZipcode(siteOneContactUsForm.getProjectZipcode());
		if (StringUtils.isNotEmpty(siteOneContactUsForm.getStreetAddress()))
		{
			contactUsEvent.setStreetAddress(siteOneContactUsForm.getStreetAddress());
		}
		if ((contactUsEvent.getReasonForContact().equalsIgnoreCase(
				getMessageSource().getMessage("help.contactUs.reason.hardscapes", null, getI18nService().getCurrentLocale())))
				|| setLightingReasonOf().contains(contactUsEvent.getReasonForContact())
				|| contactUsEvent.getReasonForContact().equalsIgnoreCase(
						getMessageSource().getMessage("help.contactUs.reason.callBack", null, getI18nService().getCurrentLocale()))
				|| contactUsEvent.getReasonForContact().equalsIgnoreCase(
						getMessageSource().getMessage("help.contactUs.reason.support", null, getI18nService().getCurrentLocale())))
		{
			contactUsEvent.setProjectStartDate(siteOneContactUsForm.getProjectStartDate());
			contactUsEvent.setInPhaseOf(siteOneContactUsForm.getInPhaseOf());
			contactUsEvent.setMyProject(siteOneContactUsForm.getMyProject());
			contactUsEvent.setMyBudget(siteOneContactUsForm.getMyBudget());
			contactUsEvent.setIdentity(siteOneContactUsForm.getIdentity());
			contactUsEvent.setIdentityName(siteOneContactUsForm.getIdentityName());
		}
		return contactUsEvent;
	}

	private void setUpContactUsBreadcrumb(final Model model)
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<>();
		final Breadcrumb breadcrumb = new Breadcrumb("#",
				getMessageSource().getMessage("breadcrumb.customerService", null, getI18nService().getCurrentLocale()), null);

		breadcrumbs.add(breadcrumb);

		model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadcrumbs);

	}

	private List<String> setReasonForContactUs()
	{
		final List<String> reasonForContact = new ArrayList<>();
		reasonForContact
				.add(getMessageSource().getMessage("help.contactUs.reason.option1", null, getI18nService().getCurrentLocale()));
		reasonForContact
				.add(getMessageSource().getMessage("help.contactUs.reason.option2", null, getI18nService().getCurrentLocale()));
		if (baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase("siteone-us")) {
		  reasonForContact
				.add(getMessageSource().getMessage("help.contactUs.reason.option12", null, getI18nService().getCurrentLocale()));
		}
		reasonForContact
				.add(getMessageSource().getMessage("help.contactUs.reason.option3", null, getI18nService().getCurrentLocale()));
		reasonForContact
				.add(getMessageSource().getMessage("help.contactUs.reason.option4", null, getI18nService().getCurrentLocale()));
		reasonForContact
				.add(getMessageSource().getMessage("help.contactUs.reason.option5", null, getI18nService().getCurrentLocale()));
		reasonForContact
				.add(getMessageSource().getMessage("help.contactUs.reason.option6", null, getI18nService().getCurrentLocale()));
		reasonForContact
				.add(getMessageSource().getMessage("help.contactUs.reason.option7", null, getI18nService().getCurrentLocale()));
		reasonForContact
				.add(getMessageSource().getMessage("help.contactUs.reason.option8", null, getI18nService().getCurrentLocale()));
		reasonForContact
				.add(getMessageSource().getMessage("help.contactUs.reason.option9", null, getI18nService().getCurrentLocale()));
		reasonForContact
				.add(getMessageSource().getMessage("help.contactUs.reason.option11", null, getI18nService().getCurrentLocale()));
		reasonForContact
				.add(getMessageSource().getMessage("help.contactUs.reason.option10", null, getI18nService().getCurrentLocale()));
		return reasonForContact;
	}

	/* Add for SE-9768 */
	private List<String> setLightingReasonOf()
	{
		final List<String> lightingReasonOf = new ArrayList<>();
		lightingReasonOf.add(
				getMessageSource().getMessage("help.contactUs.reason.lighting.callback", null, getI18nService().getCurrentLocale()));
		lightingReasonOf
				.add(getMessageSource().getMessage("help.contactUs.reason.lighting.demo", null, getI18nService().getCurrentLocale()));
		lightingReasonOf.add(
				getMessageSource().getMessage("help.contactUs.reason.lighting.support", null, getI18nService().getCurrentLocale()));
		lightingReasonOf.add(
				getMessageSource().getMessage("help.contactUs.reason.lighting.outdoor", null, getI18nService().getCurrentLocale()));
		return lightingReasonOf;
	}

	private List<String> setOutdoorPhaseOf()
	{
		final List<String> phaseOf = new ArrayList<>();
		phaseOf.add(getMessageSource().getMessage("help.contactUs.reason.lighting.outdoor.phaseOf", null,
				getI18nService().getCurrentLocale()));
		return phaseOf;
	}

	private List<String> setOutdoorProject()
	{
		final List<String> project = new ArrayList<>();
		project.add(getMessageSource().getMessage("help.contactUs.reason.lighting.outdoor.project.option1", null,
				getI18nService().getCurrentLocale()));
		project.add(getMessageSource().getMessage("help.contactUs.reason.lighting.outdoor.project.option2", null,
				getI18nService().getCurrentLocale()));
		project.add(getMessageSource().getMessage("help.contactUs.reason.lighting.outdoor.project.option3", null,
				getI18nService().getCurrentLocale()));
		return project;
	}

	/* End of SE-9768 */
	private List<String> setPhaseOf()
	{
		final List<String> phaseOf = new ArrayList<>();
		phaseOf.add(getMessageSource().getMessage("help.contactUs.phase.option1", null, getI18nService().getCurrentLocale()));
		phaseOf.add(getMessageSource().getMessage("help.contactUs.phase.option2", null, getI18nService().getCurrentLocale()));
		phaseOf.add(getMessageSource().getMessage("help.contactUs.phase.option3", null, getI18nService().getCurrentLocale()));
		return phaseOf;
	}

	private List<String> setProject()
	{
		final List<String> phaseOf = new ArrayList<>();
		phaseOf.add(getMessageSource().getMessage("help.contactUs.project.option1", null, getI18nService().getCurrentLocale()));
		phaseOf.add(getMessageSource().getMessage("help.contactUs.project.option2", null, getI18nService().getCurrentLocale()));
		phaseOf.add(getMessageSource().getMessage("help.contactUs.project.option3", null, getI18nService().getCurrentLocale()));
		return phaseOf;
	}

	private List<String> setBudget()
	{
		final List<String> phaseOf = new ArrayList<>();
		phaseOf.add(getMessageSource().getMessage("help.contactUs.budget.option1", null, getI18nService().getCurrentLocale()));
		phaseOf.add(getMessageSource().getMessage("help.contactUs.budget.option2", null, getI18nService().getCurrentLocale()));
		phaseOf.add(getMessageSource().getMessage("help.contactUs.budget.option3", null, getI18nService().getCurrentLocale()));
		return phaseOf;
	}

	private List<String> setIdentity()
	{
		final List<String> phaseOf = new ArrayList<>();
		phaseOf.add(getMessageSource().getMessage("help.contactUs.identity.option1", null, getI18nService().getCurrentLocale()));
		phaseOf.add(getMessageSource().getMessage("help.contactUs.identity.option2", null, getI18nService().getCurrentLocale()));
		phaseOf.add(getMessageSource().getMessage("help.contactUs.identity.option3", null, getI18nService().getCurrentLocale()));
		phaseOf.add(getMessageSource().getMessage("help.contactUs.identity.option4", null, getI18nService().getCurrentLocale()));
		return phaseOf;
	}

	private void populateModelAttributes(final Model model, final List<String> reasonForContact)
	{
		PointOfServiceData sessionPos = ((SiteOneCustomerFacade) customerFacade).getPreferredStore();

		if (sessionPos == null)
		{
			sessionPos = storeSessionFacade.getSessionStore();
		}
		populateStates(model);
		model.addAttribute(REASONFORCONTACT, reasonForContact);
		final List<String> phaseOf = setPhaseOf();
		model.addAttribute(PHASEOF, phaseOf);
		final List<String> project = setProject();
		model.addAttribute(PROJECT, project);
		final List<String> budget = setBudget();
		model.addAttribute(BUDGET, budget);
		final List<String> identity = setIdentity();
		model.addAttribute(IDENTITY, identity);
		model.addAttribute(RECAPTCHA_PUBLICKEY, Config.getString(RECAPTCHA_KEY, null));
		LOG.info(sessionPos);
		model.addAttribute("sessionPos", sessionPos);
		setUpContactUsBreadcrumb(model);
	}

	public String getViewForModel(final String typeContactUs, final String typeFullPath, final Model model,
			final List<String> reasonForContact) throws CMSItemNotFoundException
	{

		populateModelAttributes(model, reasonForContact);
		model.addAttribute(typeContactUs, Boolean.TRUE);
		getContentPageForLabelOrId(CONTACT_US_PAGE)
				.setFullPagePath(getMessageSource().getMessage(typeFullPath, null, getI18nService().getCurrentLocale()));
		storeCmsPageInModel(model, getContentPageForLabelOrId(CONTACT_US_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CONTACT_US_PAGE));
		return getViewForPage(model);
	}

	public String getContactUsPageDetails(final String fullPath, final SiteOneContactUsForm siteOneContactUsForm,
			final HttpServletRequest request, final Model model) throws CMSItemNotFoundException
	{
		PointOfServiceData sessionPos = ((SiteOneCustomerFacade) customerFacade).getPreferredStore();

		final String recaptchaResponse = request.getParameter(RECAPTCHA);

		boolean verify = false;
		if (StringUtils.isNotEmpty(recaptchaResponse))
		{
			verify = verifyRecaptchaUtils.checkAnswer(recaptchaResponse);
		}
		if (!verify)
		{
			model.addAttribute(RECAPTCHA_CHALLENGE_ANSWERED, Boolean.FALSE);
			return getContactUsHardscapePage(siteOneContactUsForm, model);
		}
		if (sessionPos == null)
		{
			sessionPos = storeSessionFacade.getSessionStore();
		}
		LOG.info(sessionPos);
		model.addAttribute("sessionPos", sessionPos);
		eventService.publishEvent(initializeEvent(new ContactUsEvent(), siteOneContactUsForm));
		eventService.publishEvent(initializeEvent(new ContactUsCustomerEvent(), siteOneContactUsForm));
		setUpContactUsBreadcrumb(model);
		getContentPageForLabelOrId(CONFIRMATION_PAGE)
				.setFullPagePath(getMessageSource().getMessage(fullPath, null, getI18nService().getCurrentLocale()));
		storeCmsPageInModel(model, getContentPageForLabelOrId(CONFIRMATION_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CONFIRMATION_PAGE));

		return getViewForPage(model);
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
}
