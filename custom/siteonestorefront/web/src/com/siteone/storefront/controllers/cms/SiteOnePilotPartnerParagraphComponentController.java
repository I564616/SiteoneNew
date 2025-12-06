/**
 *
 */
package com.siteone.storefront.controllers.cms;

import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.siteone.core.model.SiteOnePilotPartnerParagraphComponentModel;
import com.siteone.facade.customer.info.MyAccountUserInfo;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.storefront.controllers.ControllerConstants;


/**
 * @author BS
 *
 */
@Controller("SiteOnePilotPartnerParagraphComponentController")
@RequestMapping(value = ControllerConstants.Actions.Cms.SiteOnePilotPartnerParagraphComponent)
public class SiteOnePilotPartnerParagraphComponentController
		extends AbstractAcceleratorCMSComponentController<SiteOnePilotPartnerParagraphComponentModel>
{
	private static final String ANONYMOUS_FLAG = "isAnonymous";
	private static final String IS_PARTNERPROGRAM_ENROLLED = "isPartnerProgramEnrolled";
	private static final String IS_PARTNERSPROGRAM_RETAIL = "isPartnersProgramRetail";
	private static final String BREADCRUMBS_ATTR = "breadcrumbs";
	@Resource(name = "messageSource")
	private MessageSource messageSource;

	@Resource(name = "i18nService")
	private I18NService i18nService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Override
	protected void fillModel(final HttpServletRequest request, final Model model,
			final SiteOnePilotPartnerParagraphComponentModel component)
	{
		final boolean isAnonymousUser = userService.getCurrentUser() != null
				&& userService.isAnonymousUser(userService.getCurrentUser());
		model.addAttribute(ANONYMOUS_FLAG, isAnonymousUser);
		final MyAccountUserInfo myAccountUserInfo = ((SiteOneB2BUnitFacade) b2bUnitFacade).getUserAccountInfo();
		if (!isAnonymousUser)
		{
			model.addAttribute(IS_PARTNERPROGRAM_ENROLLED, (null != customerFacade.getCurrentCustomer()
					&& customerFacade.getCurrentCustomer().getPartnerProgramPermissions()));
			model.addAttribute(IS_PARTNERSPROGRAM_RETAIL, myAccountUserInfo.getIsPartnersProgramRetail());
		}
		final List<Breadcrumb> breadcrumbs = new ArrayList<>();

		final Breadcrumb breadcrumbHead = new Breadcrumb("/",
				getMessageSource().getMessage("breadcrumb.contractorServices", null, getI18nService().getCurrentLocale()), null);
		final Breadcrumb breadcrumbParnter = new Breadcrumb("/partnersprogram",
				getMessageSource().getMessage("breadcrumb.partnersProgram", null, getI18nService().getCurrentLocale()), null);

		breadcrumbs.add(breadcrumbHead);
		breadcrumbs.add(breadcrumbParnter);
		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);
	}

	/**
	 * @return the messageSource
	 */
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

	/**
	 * @return the i18nService
	 */
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
	 * @return the userService
	 */
	public UserService getUserService()
	{
		return userService;
	}

	/**
	 * @param userService
	 *           the userService to set
	 */
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	/**
	 * @return the b2bUnitFacade
	 */
	public B2BUnitFacade getB2bUnitFacade()
	{
		return b2bUnitFacade;
	}

	/**
	 * @param b2bUnitFacade
	 *           the b2bUnitFacade to set
	 */
	public void setB2bUnitFacade(final B2BUnitFacade b2bUnitFacade)
	{
		this.b2bUnitFacade = b2bUnitFacade;
	}

	/**
	 * @return the customerFacade
	 */
	public CustomerFacade getCustomerFacade()
	{
		return customerFacade;
	}

	/**
	 * @param customerFacade
	 *           the customerFacade to set
	 */
	public void setCustomerFacade(final CustomerFacade customerFacade)
	{
		this.customerFacade = customerFacade;
	}

}