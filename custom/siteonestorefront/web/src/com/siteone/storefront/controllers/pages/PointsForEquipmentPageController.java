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

import com.siteone.core.event.PointsForEquipmentEvent;
import com.siteone.core.recaptcha.util.VerifyRecaptchaUtils;
import com.siteone.facade.location.SiteOneRegionFacade;
import com.siteone.storefront.controllers.ControllerConstants;
import com.siteone.storefront.forms.SiteOnePointsForEquipmentForm;


/**
 * @author 1003567
 *
 */
@Controller
@RequestMapping("/pointsForEquipment")
public class PointsForEquipmentPageController extends AbstractSearchPageController
{
	private static final String POINTSFOREQUIPMENT = "pointsForEquipment";
	private static final String FORM_GLOBAL_ERROR = "form.global.error";

	@Resource(name = "eventService")
	private EventService eventService;

	@Resource(name = "siteOneRegionFacade")
	private SiteOneRegionFacade siteOneRegionFacade;

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

	@GetMapping
	public String getPointsForEquipmentPage(
			@ModelAttribute("siteOnePointsForEquipmentForm") final SiteOnePointsForEquipmentForm siteOnePointsForEquipmentForm,
			final Model model) throws CMSItemNotFoundException
	{
		final List<String> regionCode = new ArrayList<String>();
		final List<RegionData> regionDataList = siteOneRegionFacade.getRegionsForCountryCode("US");
		for (final RegionData data : regionDataList)
		{
			regionCode.add(data.getName());

		}
		model.addAttribute("states", regionCode);
		model.addAttribute("recaptchaPublicKey", Config.getString("recaptcha.publickey", null));
		setUpBreadcrumb(model);

		storeCmsPageInModel(model, getContentPageForLabelOrId(POINTSFOREQUIPMENT));
		return ControllerConstants.Views.Pages.PartnersProgram.PointsForEquipmentPage;
	}

	@PostMapping("/form")
	public String editRequestAccountPage(@Valid final SiteOnePointsForEquipmentForm siteOnePointsForEquipmentForm,
			final BindingResult bindingResult, final HttpServletRequest request, final Model model) throws CMSItemNotFoundException // NOSONAR
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
			return getPointsForEquipmentPage(siteOnePointsForEquipmentForm, model);
		}

		eventService.publishEvent(initializeEvent(new PointsForEquipmentEvent(), siteOnePointsForEquipmentForm));
		model.addAttribute("siteOnePointsForEquipmentForm", siteOnePointsForEquipmentForm);
		setUpBreadcrumb(model);
		storeCmsPageInModel(model, getContentPageForLabelOrId("pointsForEquipmentSuccess"));
		return ControllerConstants.Views.Pages.PartnersProgram.PointsForEquipmentSuccessPage;
	}

	public PointsForEquipmentEvent initializeEvent(final PointsForEquipmentEvent event,
			final SiteOnePointsForEquipmentForm siteOnePointsForEquipmentForm)
	{
		event.setDealerContactName(siteOnePointsForEquipmentForm.getDealerContactName());
		event.setDealerName(siteOnePointsForEquipmentForm.getDealerName());
		event.setDealerAddressLine1(siteOnePointsForEquipmentForm.getDealerAddressLine1());
		event.setDealerAddressLine2(siteOnePointsForEquipmentForm.getDealerAddressLine2());
		event.setDealerCity(siteOnePointsForEquipmentForm.getDealerCity());
		event.setDealerState(siteOnePointsForEquipmentForm.getDealerName());
		event.setDealerZipCode(siteOnePointsForEquipmentForm.getDealerZipCode());
		event.setCompanyName(siteOnePointsForEquipmentForm.getCompanyName());
		event.setCustomerContactName(siteOnePointsForEquipmentForm.getCustomerContactName());
		event.setCustomerAddressLine1(siteOnePointsForEquipmentForm.getCustomerAddressLine1());
		event.setCustomerAddressLine2(siteOnePointsForEquipmentForm.getCustomerAddressLine2());
		event.setCustomerCity(siteOnePointsForEquipmentForm.getCustomerCity());
		event.setCustomerState(siteOnePointsForEquipmentForm.getCustomerState());
		event.setCustomerZipCode(siteOnePointsForEquipmentForm.getCustomerZipCode());
		event.setJdlAccountNumber(siteOnePointsForEquipmentForm.getJdlAccountNumber());
		event.setEmailAddress(siteOnePointsForEquipmentForm.getEmailAddress());
		event.setPhoneNumber(siteOnePointsForEquipmentForm.getPhoneNumber());
		event.setLanguage(getCommonI18NService().getCurrentLanguage());
		if (siteOnePointsForEquipmentForm.getFaxNumber() != null)
		{
			event.setFaxNumber(siteOnePointsForEquipmentForm.getFaxNumber());
		}
		event.setDateOfPurProduct1(siteOnePointsForEquipmentForm.getDateOfPurProduct1());
		event.setItemDescProduct1(siteOnePointsForEquipmentForm.getItemDescProduct1());
		event.setSerialNumberProduct1(siteOnePointsForEquipmentForm.getSerialNumberProduct1());
		event.setInvoiceCostProduct1(siteOnePointsForEquipmentForm.getInvoiceCostProduct1());
		if (siteOnePointsForEquipmentForm.getDateOfPurProduct2() != null)
		{
			event.setDateOfPurProduct2(siteOnePointsForEquipmentForm.getDateOfPurProduct2());
		}
		if (siteOnePointsForEquipmentForm.getItemDescProduct2() != null)
		{
			event.setItemDescProduct2(siteOnePointsForEquipmentForm.getItemDescProduct2());
		}
		if (siteOnePointsForEquipmentForm.getSerialNumberProduct2() != null)
		{
			event.setSerialNumberProduct2(siteOnePointsForEquipmentForm.getSerialNumberProduct2());
		}
		if (siteOnePointsForEquipmentForm.getInvoiceCostProduct2() != null)
		{
			event.setInvoiceCostProduct2(siteOnePointsForEquipmentForm.getInvoiceCostProduct2());
		}
		if (siteOnePointsForEquipmentForm.getDateOfPurProduct3() != null)
		{
			event.setDateOfPurProduct3(siteOnePointsForEquipmentForm.getDateOfPurProduct3());
		}
		if (siteOnePointsForEquipmentForm.getItemDescProduct3() != null)
		{
			event.setItemDescProduct3(siteOnePointsForEquipmentForm.getItemDescProduct3());
		}
		if (siteOnePointsForEquipmentForm.getSerialNumberProduct3() != null)
		{
			event.setSerialNumberProduct3(siteOnePointsForEquipmentForm.getSerialNumberProduct3());
		}
		if (siteOnePointsForEquipmentForm.getInvoiceCostProduct3() != null)
		{
			event.setInvoiceCostProduct3(siteOnePointsForEquipmentForm.getInvoiceCostProduct3());
		}
		if (siteOnePointsForEquipmentForm.getDateOfPurProduct4() != null)
		{
			event.setDateOfPurProduct4(siteOnePointsForEquipmentForm.getDateOfPurProduct4());
		}
		if (siteOnePointsForEquipmentForm.getItemDescProduct4() != null)
		{
			event.setItemDescProduct4(siteOnePointsForEquipmentForm.getItemDescProduct4());
		}
		if (siteOnePointsForEquipmentForm.getSerialNumberProduct4() != null)
		{
			event.setSerialNumberProduct4(siteOnePointsForEquipmentForm.getSerialNumberProduct4());
		}
		if (siteOnePointsForEquipmentForm.getInvoiceCostProduct4() != null)
		{
			event.setInvoiceCostProduct4(siteOnePointsForEquipmentForm.getInvoiceCostProduct4());
		}
		if (siteOnePointsForEquipmentForm.getDateOfPurProduct5() != null)
		{
			event.setDateOfPurProduct5(siteOnePointsForEquipmentForm.getDateOfPurProduct5());
		}
		if (siteOnePointsForEquipmentForm.getItemDescProduct5() != null)
		{
			event.setItemDescProduct5(siteOnePointsForEquipmentForm.getItemDescProduct5());
		}
		if (siteOnePointsForEquipmentForm.getSerialNumberProduct5() != null)
		{
			event.setSerialNumberProduct5(siteOnePointsForEquipmentForm.getSerialNumberProduct5());
		}
		if (siteOnePointsForEquipmentForm.getInvoiceCostProduct5() != null)
		{
			event.setInvoiceCostProduct5(siteOnePointsForEquipmentForm.getInvoiceCostProduct5());
		}
		event.setSite(baseSiteService.getBaseSiteForUID("siteone"));
		return event;
	}

	private void setUpBreadcrumb(final Model model)
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<>();

		final Breadcrumb breadcrumbHead = new Breadcrumb("/",
				getMessageSource().getMessage("breadcrumb.contractorServices", null, getI18nService().getCurrentLocale()), null);
		final Breadcrumb breadcrumbParnter = new Breadcrumb("/partnerprogram",
				getMessageSource().getMessage("breadcrumb.partnersProgram", null, getI18nService().getCurrentLocale()), null);

		breadcrumbs.add(breadcrumbHead);
		breadcrumbs.add(breadcrumbParnter);
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadcrumbs);

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
