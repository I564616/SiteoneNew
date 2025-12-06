/**
 *
 */
package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.util.Config;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.siteone.core.event.HomeOwnerEvent;
import com.siteone.core.recaptcha.util.VerifyRecaptchaUtils;
import com.siteone.facade.location.SiteOneRegionFacade;
import com.siteone.storefront.forms.SiteOneHomeOwnerForm;


@Controller
@RequestMapping("/homeowner")
public class HomeOwnerController extends AbstractSearchPageController
{
	@Resource(name = "siteOneRegionFacade")
	private SiteOneRegionFacade siteOneRegionFacade;

	@Resource(name = "eventService")
	private EventService eventService;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Resource(name = "verifyRecaptchaUtils")
	private VerifyRecaptchaUtils verifyRecaptchaUtils;
	@Resource(name = "i18nService")
	private I18NService i18nService;
	@Resource(name = "messageSource")
	private MessageSource messageSource;


	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

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

	private static final String FORM_GLOBAL_ERROR = "form.global.error";

	@GetMapping
	public String getHomeOwnerPage(@ModelAttribute("siteOneHomeOwnerForm") final SiteOneHomeOwnerForm siteOneHomeOwnerForm,
			final Model model) throws CMSItemNotFoundException
	{
		setUpHomeOwnerBreadcrumb(model);

		populateLOVs(model);
		model.addAttribute("recaptchaPublicKey", Config.getString("recaptcha.publickey", null));
		if (baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase("siteone-ca"))
		{
			storeCmsPageInModel(model, getContentPageForLabelOrId("homeOwnerPageForCanada"));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId("homeOwnerPageForCanada"));
		}
		if (baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase("siteone-us"))
		{
			storeCmsPageInModel(model, getContentPageForLabelOrId("homeOwnerPage"));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId("homeOwnerPage"));
		}

		return "pages/homeowner/homeOwnerPage";
	}

	@PostMapping("/form")
	public String updateCustomerInfo(@Valid final SiteOneHomeOwnerForm siteOneHomeOwnerForm, final BindingResult bindingResult,
			final HttpServletRequest request, final Model model) throws CMSItemNotFoundException
	{
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
			return getHomeOwnerPage(siteOneHomeOwnerForm, model);
		}

		setUpHomeOwnerBreadcrumb(model);

		eventService.publishEvent(initializeEvent(new HomeOwnerEvent(), siteOneHomeOwnerForm));

		model.addAttribute("siteOneHomeOwnerForm", siteOneHomeOwnerForm);

		storeCmsPageInModel(model, getContentPageForLabelOrId("homeOwnerSuccess"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("homeOwnerSuccess"));

		return "pages/homeowner/homeOwnerSuccess";
	}

	private void setUpHomeOwnerBreadcrumb(final Model model)
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<>();

		final Breadcrumb breadcrumbTools = new Breadcrumb("#",
				getMessageSource().getMessage("breadcrumb.toolAndResources", null, getI18nService().getCurrentLocale()), null);
		final Breadcrumb breadcrumbHomeOwner = new Breadcrumb("/homeowner",
				getMessageSource().getMessage("breadcrumb.homeOwners", null, getI18nService().getCurrentLocale()), null);

		breadcrumbs.add(breadcrumbTools);
		breadcrumbs.add(breadcrumbHomeOwner);
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadcrumbs);

	}

	private void populateLOVs(final Model model)
	{
		final List<String> regionCode = new ArrayList<String>();
		final List<String> bestTimeToCallLOV = new ArrayList<String>();
		final List<String> serviceTypeLOV = new ArrayList<String>();
		final List<String> referralNoLOV = new ArrayList<String>();


		 List<RegionData> regionDataList = new ArrayList<RegionData>();
		
		if (baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase("siteone-us"))
		{
			regionDataList=siteOneRegionFacade.getRegionsForCountryCode("US");
		}
		else {
			regionDataList=siteOneRegionFacade.getRegionsForCountryCode("CA");
		}
		regionCode.add(getMessageSource().getMessage("homeowner.select", null, getI18nService().getCurrentLocale()));
		for (final RegionData data : regionDataList)
		{
			regionCode.add(data.getIsocodeShort());

		}

		bestTimeToCallLOV.add(getMessageSource().getMessage("homeowner.select", null, getI18nService().getCurrentLocale()));
		bestTimeToCallLOV.add(getMessageSource().getMessage("homeowner.morning", null, getI18nService().getCurrentLocale()));
		bestTimeToCallLOV.add(getMessageSource().getMessage("homeowner.afternoon", null, getI18nService().getCurrentLocale()));
		bestTimeToCallLOV.add(getMessageSource().getMessage("homeowner.after", null, getI18nService().getCurrentLocale()));

		serviceTypeLOV.add(getMessageSource().getMessage("homeowner.select", null, getI18nService().getCurrentLocale()));
		serviceTypeLOV.add(getMessageSource().getMessage("homeowner.new.install", null, getI18nService().getCurrentLocale()));
		serviceTypeLOV.add(getMessageSource().getMessage("homeowner.existing.feature", null, getI18nService().getCurrentLocale()));

		referralNoLOV.add(getMessageSource().getMessage("homeowner.select", null, getI18nService().getCurrentLocale()));
		referralNoLOV.add("1");
		referralNoLOV.add("2");
		referralNoLOV.add("3");

		model.addAttribute("states", regionCode);
		model.addAttribute("bestTimeToCall", bestTimeToCallLOV);
		model.addAttribute("serviceType", serviceTypeLOV);
		model.addAttribute("referralNo", referralNoLOV);
	}

	private HomeOwnerEvent initializeEvent(final HomeOwnerEvent event, final SiteOneHomeOwnerForm siteOneHomeOwnerForm)
	{
		event.setFirstName(siteOneHomeOwnerForm.getFirstName());
		event.setLastName(siteOneHomeOwnerForm.getLastName());
		event.setEmailAddr(siteOneHomeOwnerForm.getEmailAddr());
		event.setPhone(siteOneHomeOwnerForm.getPhone());
		event.setAddress(siteOneHomeOwnerForm.getAddress());
		event.setCustomerCity(siteOneHomeOwnerForm.getCustomerCity());
		event.setCustomerState(siteOneHomeOwnerForm.getCustomerState());
		event.setCustomerZipCode(siteOneHomeOwnerForm.getCustomerZipCode());

		event.setBestTimeToCall(siteOneHomeOwnerForm.getBestTimeToCall());
		event.setServiceType(siteOneHomeOwnerForm.getServiceType());
		event.setReferalsNo(siteOneHomeOwnerForm.getReferalsNo());
		event.setLanguage(getCommonI18NService().getCurrentLanguage());
		StringBuilder lookingFor = null;
		for (final String l : siteOneHomeOwnerForm.getLookingFor())
		{
			if (lookingFor == null)
			{
				lookingFor = new StringBuilder();
				lookingFor.append(l);
			}
			else
			{
				lookingFor.append(", ");
				lookingFor.append(l);
			}
		}


		if (StringUtils.isNotEmpty(siteOneHomeOwnerForm.getLookingForOthers()))
		{
			event.setLookingForOthers(siteOneHomeOwnerForm.getLookingForOthers());
		}
		else
		{
			event.setLookingForOthers("");
		}

		if (lookingFor == null)
		{
			event.setLookingFor("");
		}
		else
		{
			event.setLookingFor(lookingFor.toString());
		}


		event.setSite(baseSiteService.getCurrentBaseSite());
		return event;
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
