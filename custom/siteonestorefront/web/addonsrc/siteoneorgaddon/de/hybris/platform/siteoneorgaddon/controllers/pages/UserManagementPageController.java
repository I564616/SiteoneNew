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
package de.hybris.platform.siteoneorgaddon.controllers.pages;

import com.siteone.facades.company.SiteOneB2BUserFacade;
import com.siteone.facades.exceptions.ContactNotEnabledOrDisabledInUEException;
import com.siteone.facades.exceptions.ServiceUnavailableException;
//import com.siteone.storefront.breadcrumbs.impl.SiteOneAccountBreadcrumbBuilder;
//import com.siteone.storefront.util.SiteoneXSSFilterUtil;
//import com.siteone.storefront.validator.SiteOneProfileValidator;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.b2b.constants.B2BConstants;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2bapprovalprocessfacades.company.data.B2BPermissionData;
import de.hybris.platform.b2bcommercefacades.company.B2BUserFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BSelectionData;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUserGroupData;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.order.OrderFacade;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.siteoneorgaddon.controllers.ControllerConstants;
import de.hybris.platform.siteoneorgaddon.forms.B2BCustomerForm;
import de.hybris.platform.siteoneorgaddon.forms.B2BPermissionForm;
import de.hybris.platform.siteoneorgaddon.forms.CustomerResetPasswordForm;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.ArrayList;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2b.services.B2BUnitService;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.facade.order.SiteOneOrderFacade;

import de.hybris.platform.b2b.services.B2BCustomerService;
import java.util.Locale;
import com.siteone.facades.customer.SiteOneCustomerFacade;


/**
 * Controller defines routes to manage Users within My Company section.
 */
@Controller
@RequestMapping("/my-company/organization-management/manage-users")
public class UserManagementPageController extends MyCompanyPageController	
{
	
	
	private static final Logger LOG = Logger.getLogger(UserManagementPageController.class);
	
//	@Resource(name = "siteOneAccountBreadcrumbBuilder")
//	private SiteOneAccountBreadcrumbBuilder siteOneAccountBreadcrumbBuilder;
	
	private static final String BREADCRUMBS_ATTR = "breadcrumbs";
	

	private static final String UNIT_PATH_VARIABLE_PATTERN = "/{unit:.*}";
	
	private static final String ALL_UNITS = "All";
	
	private static final String MY_COMPANY_MANAGE_USER_URL = "/my-company/organization-management/manage-users";
	
	private static final String TEXT_STORE_DATEFORMAT_KEY = "text.store.dateformat";
	private static final String DEFAULT_DATEFORMAT = "MM/dd/yyyy";

	
//	@Resource(name = "siteOneProfileValidator")
//	private SiteOneProfileValidator siteOneProfileValidator;
	
	@Resource(name = "b2bUserFacade")
	private B2BUserFacade b2bUserFacade;
	
	@Resource(name="b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;
	
	@Resource(name="b2bUnitService")
	private B2BUnitService b2bUnitService;
	
	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;
	
	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;
	
	@Resource(name = "orderFacade")
	private OrderFacade orderFacade;
	
	@RequestMapping(value=UNIT_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String manageUsers(@PathVariable ("unit") final String unit,@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "searchParam", required = false) final String searchParam,
			@RequestParam(value = "shiptounit", required = false) String shipToUnit,
			@RequestParam(value = "pagesize", defaultValue = "10") final String pageSize,
			@RequestParam(value = "filterAdmin", defaultValue= "false", required = false) final Boolean filterAdmin,
			@RequestParam(value = "sort", defaultValue = B2BCustomerModel.NAME) final String sortCode, final Model model)
			throws CMSItemNotFoundException
	{
		//final String sanitizedunitId = SiteoneXSSFilterUtil.filter(unit);
		List<String> userUnitIds = new ArrayList<String>();
		Map<String, String> listOfShipTos = new LinkedHashMap<>();
			
	  if(shipToUnit != null && !shipToUnit.isEmpty() && !shipToUnit.equalsIgnoreCase("All")){
			B2BUnitModel  b2BUnitModel=(B2BUnitModel) b2bUnitService.getUnitForUid(shipToUnit);
			listOfShipTos.put(b2BUnitModel.getUid(), b2BUnitModel.getUid().split("_US")[0]+' '+b2BUnitModel.getName());
	  }
  	listOfShipTos.putAll(((SiteOneB2BUserFacade) b2bUserFacade).getListOfShipTos());
  	if(null!= shipToUnit && shipToUnit.equalsIgnoreCase("shipToopenPopupNew")){
	  	//shipToUnit = sanitizedunitId;
  	}
  	Boolean shipToUnitFlag = (null != shipToUnit && !shipToUnit.equalsIgnoreCase("All")) ? true : false;
	String userUnit = (null != shipToUnit) ? shipToUnit : ALL_UNITS;
	
	int viewByPageSize = getSiteConfigService().getInt("siteoneorgaddon.search.pageSize", 10);
	String trimmedSearchParam = null;
	if (null != searchParam)
	{
		trimmedSearchParam = searchParam.trim();
	}
	if (null != pageSize)
	{
		viewByPageSize = Integer.parseInt(pageSize);
	}
	
	
	// Handle paged search results
	final PageableData pageableData = createPageableData(page, viewByPageSize, sortCode, showMode);
	SearchPageData<CustomerData> searchPageData = null;
	
	if(userUnit == ALL_UNITS || userUnit.equalsIgnoreCase("All"))
	{
		B2BUnitModel defaultUnit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
		searchPageData = ((SiteOneB2BUserFacade) b2bUserFacade).getPagedUserDataForUnit(pageableData,
				defaultUnit.getUid(),trimmedSearchParam,sortCode,filterAdmin,shipToUnitFlag);
	}
	else
	{
		searchPageData = ((SiteOneB2BUserFacade) b2bUserFacade)
				.getPagedUserDataForUnit(pageableData, userUnit, trimmedSearchParam, sortCode, filterAdmin,shipToUnitFlag);
	}
	
 
       
	populateModel(model, searchPageData, showMode);

	storeCmsPageInModel(model, getContentPageForLabelOrId(MY_COMPANY_CMS_PAGE));
	setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MY_COMPANY_CMS_PAGE));
	
	//final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.getBreadcrumbs(null);
//	final List<Breadcrumb> breadcrumbs = siteOneAccountBreadcrumbBuilder.getBreadcrumbsForAccountDashboardPage(sanitizedunitId);
//	model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);
	//model.addAttribute("unit",sanitizedunitId);
	model.addAttribute("shiptounit",userUnit);
	model.addAttribute("searchParam", trimmedSearchParam);
	model.addAttribute("sort", sortCode);
	model.addAttribute("pagesize", viewByPageSize);
	model.addAttribute("listOfShipTos", listOfShipTos);
	model.addAttribute("totalCount", searchPageData.getPagination().getTotalNumberOfResults());
	model.addAttribute("filterAdmin", filterAdmin);
	model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
	return ControllerConstants.Views.Pages.MyCompany.MyCompanyManageUsersPage;
}

	@Override
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	@RequireHardLogIn
	public String manageUserDetail(@RequestParam("user") final String user, final Model model, @RequestParam("unit") final String unit) throws CMSItemNotFoundException
	{
		model.addAttribute("action", "manageUsers");
		model.addAttribute("unit", unit);
		return super.manageUserDetail(user, model, unit);
	}

	@Override
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	@RequireHardLogIn
	public String editUser(@RequestParam("user") final String user, final Model model, @RequestParam("unit") final String unitId) throws CMSItemNotFoundException
	{
		model.addAttribute("action", "manageUsers");
		model.addAttribute("unit", unitId);
		final B2BUnitModel parent = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
		model.addAttribute("featureSwitch", ((SiteOneB2BUnitFacade) b2bUnitFacade).checkIsUnitForApproveOrders(parent.getUid()));
		return super.editUser(user, model, unitId);
	}

	@RequestMapping(value = "/edit-approver", method = RequestMethod.GET)
	@RequireHardLogIn
	public String editUsersApprover(@RequestParam("user") final String user, @RequestParam("approver") final String approver,
			final Model model, final HttpServletRequest request) throws CMSItemNotFoundException
	{
		model.addAttribute("cancelUrl", getCancelUrl(MANAGE_USER_DETAILS_URL, request.getContextPath(), user));
		model.addAttribute(
				"saveUrl",
				String.format("%s/my-company/organization-management/manage-users/edit-approver?user=%s&approver=%s",
						request.getContextPath(), urlEncode(user), urlEncode(approver)));
		final String editUserUrl = super.editUser(approver, model, null);
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user, null);
		breadcrumbs.add(new Breadcrumb(String.format(
				"/my-company/organization-management/manage-units/edit-approver?user=%s&approver=%s", urlEncode(user),
				urlEncode(approver)), getMessageSource().getMessage("text.company.manageusers.edit", new Object[]
		{ approver }, "Edit {0} User", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		return editUserUrl;
	}

	@RequestMapping(value = "/edit-approver", method = RequestMethod.POST)
	@RequireHardLogIn
	public String editUsersApprover(@RequestParam("user") final String user, @RequestParam("approver") final String approver,
			@Valid final B2BCustomerForm b2BCustomerForm, final BindingResult bindingResult, final Model model,
			final HttpServletRequest request, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		model.addAttribute("cancelUrl", getCancelUrl(MANAGE_USER_DETAILS_URL, request.getContextPath(), user));
		model.addAttribute(
				"saveUrl",
				String.format("%s/my-company/organization-management/manage-users/edit-approver?user=%s&approver=%s",
						request.getContextPath(), urlEncode(user), urlEncode(approver)));

		final String editUserUrl = super.editUser(user, b2BCustomerForm, bindingResult, model, redirectModel, null, request);
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user, null);
		breadcrumbs.add(new Breadcrumb(String.format(
				"/my-company/organization-management/manage-units/edit-approver?user=%s&approver=%s", urlEncode(user),
				urlEncode(approver)), getMessageSource().getMessage("text.company.manageusers.edit", new Object[]
		{ approver }, "Edit {0} User", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		if (bindingResult.hasErrors() || model.containsAttribute(GlobalMessages.ERROR_MESSAGES_HOLDER))
		{
			return editUserUrl;
		}
		else
		{
			return String.format(REDIRECT_TO_USER_DETAILS, urlEncode(user));
		}
	}

	@RequestMapping(value = "/approvers/remove", method =
	{ RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String removeApproverFromCustomer(@RequestParam("user") final String user,
			@RequestParam("approver") final String approver, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		b2bApproverFacade.removeApproverFromCustomer(user, approver);
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "text.confirmation.approver.removed");
		return String.format(REDIRECT_TO_USER_DETAILS, urlEncode(user));
	}

	@Override
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@RequireHardLogIn
	public String editUser(@RequestParam("user") final String user, @Valid final B2BCustomerForm b2BCustomerForm,
			final BindingResult bindingResult, final Model model, final RedirectAttributes redirectModel, @RequestParam("unit") final String unitId,final HttpServletRequest request)
			throws CMSItemNotFoundException
	{
		//siteOneProfileValidator.validate(b2BCustomerForm, bindingResult);
		model.addAttribute("unit", unitId);
		return super.editUser(user, b2BCustomerForm, bindingResult, model, redirectModel, unitId, request);
	}

	@Override
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	@RequireHardLogIn
	public String createUser(final Model model, @RequestParam("unit") final String unit) throws CMSItemNotFoundException
	{
		model.addAttribute("action", "manageUsers");
		model.addAttribute("unit", unit);
		final B2BUnitModel parent = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
		model.addAttribute("featureSwitch", ((SiteOneB2BUnitFacade) b2bUnitFacade).checkIsUnitForApproveOrders(parent.getUid()));
		return super.createUser(model, unit);
	}

	@Override
	@RequestMapping(value = "/create" , method = RequestMethod.POST)
	@RequireHardLogIn
	public String createUser(@Valid final B2BCustomerForm b2BCustomerForm, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectModel, @RequestParam("unit") final String unit, final HttpServletRequest request) throws CMSItemNotFoundException
	{
		//
        // siteOneProfileValidator.validate(b2BCustomerForm, bindingResult);
		model.addAttribute("action", "manageUsers");
		model.addAttribute("unit", unit);
		return super.createUser(b2BCustomerForm, bindingResult, model, redirectModel, unit, request);
	}

	@RequestMapping(value ="/disable", method = RequestMethod.GET)
	@RequireHardLogIn
	public String disableUserConfirmation(@RequestParam("unit") final String unitId,@RequestParam("user") final String user, final Model model)
			throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user, unitId);
		breadcrumbs.add(new Breadcrumb(String.format("/my-company/organization-management/manage-users/details/?unit="+unitId+"&user=%s",
				urlEncode(user)), getMessageSource().getMessage("text.company.manage.units.disable.breadcrumb", new Object[]
		{ user }, "Disable {0} Customer", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);

		final CustomerData customerData = b2bUserFacade.getCustomerForUid(user);
		model.addAttribute("customerData", customerData);
		model.addAttribute("unit", unitId);
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return ControllerConstants.Views.Pages.MyCompany.MyCompanyManageUserDisbaleConfirmPage;
	}

	@RequestMapping(value = "/disable", method = RequestMethod.POST)
	@RequireHardLogIn
	public String disableUser(@RequestParam("unit") final String unitId,@RequestParam("user") final String user, final Model model, final RedirectAttributes redirectModel,final HttpServletRequest request)
			throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		//final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUnitsDetailsBreadcrumbs(user);\
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user, unitId);
		breadcrumbs.add(new Breadcrumb(String.format("/my-company/organization-management/manage-users/details?unit="+ unitId +"&user=%s",
				urlEncode(user)), getMessageSource().getMessage("text.company.manageusers.disable.breadcrumb", new Object[]
		{ user }, "Disable {0}  Customer ", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		try {
			((SiteOneCustomerFacade) customerFacade).getClientInformation(request);
			b2bUserFacade.disableCustomer(user);
		}catch (final ServiceUnavailableException serviceUnavailableException)
		{
			LOG.error(serviceUnavailableException);
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "form.sytem.down.disable.contact.error");
			String redirectUrl = REDIRECT_PREFIX + "/my-company/organization-management/manage-users/details?unit="+ unitId +"&user=%s";
			return String.format(redirectUrl, urlEncode(user));
		}
		catch (final ContactNotEnabledOrDisabledInUEException contactNotEnabledOrDisabledInUEException)
		{
			LOG.error(contactNotEnabledOrDisabledInUEException);
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "form.sytem.down.disable.contact.error");
			String redirectUrl = REDIRECT_PREFIX + "/my-company/organization-management/manage-users/details?unit="+ unitId +"&user=%s";
			return String.format(redirectUrl, urlEncode(user));
		}
		
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "text.confirmation.user.disable");
		String redirectUrl = REDIRECT_PREFIX + "/my-company/organization-management/manage-users/"+ unitId ;
		return String.format(redirectUrl);
	}

	@RequestMapping(value = "/enable", method = RequestMethod.POST)
	@RequireHardLogIn
	public String enableUser(@RequestParam("unit") final String unitId, @RequestParam("user") final String user, final RedirectAttributes redirectModel, HttpServletRequest request)
			throws CMSItemNotFoundException
	{
		try {
			((SiteOneCustomerFacade) customerFacade).getClientInformation(request);
			b2bUserFacade.enableCustomer(user);
		}
		catch (final ServiceUnavailableException serviceUnavailableException)
		{
			LOG.error(serviceUnavailableException);
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "form.sytem.down.enable.contact.error");
			String redirectUrl = REDIRECT_PREFIX + "/my-company/organization-management/manage-users/details?unit="+ unitId +"&user=%s";
			return String.format(redirectUrl, urlEncode(user));
		}catch (final ContactNotEnabledOrDisabledInUEException contactNotEnabledOrDisabledInUEException)
		{
			LOG.error(contactNotEnabledOrDisabledInUEException);
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "form.sytem.down.disable.contact.error");
			String redirectUrl = REDIRECT_PREFIX + "/my-company/organization-management/manage-users/details?unit="+ unitId +"&user=%s";
			return String.format(redirectUrl, urlEncode(user));
		}
		
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "text.confirmation.user.enable");
		String redirectUrl = REDIRECT_PREFIX + "/my-company/organization-management/manage-users/details?unit="+ unitId +"&user=%s";
		return String.format(redirectUrl, urlEncode(user));
	}

	@RequestMapping(value = "/resetpassword", method = RequestMethod.GET)
	@RequireHardLogIn
	public String updatePassword(@RequestParam("unit") final String unitId, @RequestParam("user") final String user, final Model model) throws CMSItemNotFoundException
	{
		if (!model.containsAttribute("customerResetPasswordForm"))
		{
			final CustomerResetPasswordForm customerResetPasswordForm = new CustomerResetPasswordForm();
			customerResetPasswordForm.setUid(user);
			model.addAttribute("customerResetPasswordForm", customerResetPasswordForm);
		}
		model.addAttribute("unit", unitId);
		storeCmsPageInModel(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user, unitId);
		String url = "/my-company/organization-management/manage-users/resetpassword?unit=" + unitId + "&user=%s";
		breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage("text.company.manageusers.restpassword.breadcrumb", new Object[]
		{ user }, "Reset Password {0}  User ", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("unit", unitId);
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return ControllerConstants.Views.Pages.MyCompany.MyCompanyManageUserResetPasswordPage;
	}

	@RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
	@RequireHardLogIn
	public String updatePassword(@RequestParam("unit") final String unitId, @RequestParam("user") final String user,
			@Valid final CustomerResetPasswordForm customerResetPasswordForm, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		if (bindingResult.hasErrors())
		{
			model.addAttribute(customerResetPasswordForm);
			GlobalMessages.addErrorMessage(model, "form.global.error");
			return updatePassword(unitId ,customerResetPasswordForm.getUid(), model);
		}

		if (customerResetPasswordForm.getNewPassword().equals(customerResetPasswordForm.getCheckNewPassword()))
		{

			b2bUserFacade.resetCustomerPassword(customerResetPasswordForm.getUid(), customerResetPasswordForm.getNewPassword());
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "text.confirmation.password.updated");
		}
		else
		{
			model.addAttribute(customerResetPasswordForm);
			bindingResult.rejectValue("checkNewPassword", "validation.checkPwd.equals", new Object[] {},
					"validation.checkPwd.equals");
			GlobalMessages.addErrorMessage(model, "form.global.error");
			return updatePassword(unitId, customerResetPasswordForm.getUid(), model);
		}

		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUnitsDetailsBreadcrumbs(customerResetPasswordForm.getUid());
		String url = "/my-company/organization-management/manage-users/resetpassword?unit=" + unitId +"&user=%s";
		breadcrumbs.add(new Breadcrumb(String.format(url, urlEncode(customerResetPasswordForm.getUid())), getMessageSource().getMessage(
				"text.company.manageusers.restpassword.breadcrumb", new Object[]
				{ customerResetPasswordForm.getUid() }, "Reset Password {0}  Customer ", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		String redirectUrl = REDIRECT_PREFIX + "/my-company/organization-management/manage-users/details?unit="+ unitId +"&?user=%s";
		return String.format(redirectUrl, urlEncode(customerResetPasswordForm.getUid()));
	}

	@RequestMapping(value = "/approvers", method = RequestMethod.GET)
	@RequireHardLogIn
	public String getPagedApproversForCustomer(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final AbstractSearchPageController.ShowMode showMode,
			@RequestParam(value = "sort", defaultValue = UserModel.NAME) final String sortCode,
			@RequestParam("user") final String user, final Model model, final HttpServletRequest request)
			throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(MY_COMPANY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MY_COMPANY_CMS_PAGE));

		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user, null);
		breadcrumbs.add(new Breadcrumb(String.format("/my-company/organization-management/manage-users/approvers?user=%s",
				urlEncode(user)), getMessageSource().getMessage("text.company.manageUsers.approvers.breadcrumb", new Object[]
		{ user }, "Customer {0} Approvers", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		final B2BUnitData unit = b2bUnitFacade.getUnitForUid(user);
		model.addAttribute("unit", unit);

		// Handle paged search results
		final PageableData pageableData = createPageableData(page, getSearchPageSize(), sortCode, showMode);
		final SearchPageData<CustomerData> searchPageData = b2bApproverFacade.getPagedApproversForCustomer(pageableData, user);
		populateModel(model, searchPageData, showMode);
		model.addAttribute("action", "approvers");
		model.addAttribute("baseUrl", "/my-company/organization-management/manage-users");
		model.addAttribute("cancelUrl", getCancelUrl(MANAGE_USER_DETAILS_URL, request.getContextPath(), user));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return ControllerConstants.Views.Pages.MyCompany.MyCompanyManageUserCustomersPage;
	}

	@ResponseBody
	@RequestMapping(value = "/approvers/select", method =
	{ RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public B2BSelectionData selectApproverForCustomer(@RequestParam("user") final String user,
			@RequestParam("approver") final String approver) throws CMSItemNotFoundException
	{
		return populateDisplayNamesForRoles(b2bApproverFacade.addApproverForCustomer(user, approver));
	}

	@ResponseBody
	@RequestMapping(value = "/approvers/deselect", method =
	{ RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public B2BSelectionData deselectApproverForCustomer(@RequestParam("user") final String user,
			@RequestParam("approver") final String approver) throws CMSItemNotFoundException
	{
		return populateDisplayNamesForRoles(b2bApproverFacade.removeApproverFromCustomer(user, approver));
	}

	@RequestMapping(value = "/permissions", method = RequestMethod.GET)
	@RequireHardLogIn
	public String getPagedPermissionsForCustomer(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final AbstractSearchPageController.ShowMode showMode,
			@RequestParam(value = "sort", defaultValue = UserModel.NAME) final String sortCode,
			@RequestParam("user") final String user, final Model model, final HttpServletRequest request)
			throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(MY_COMPANY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MY_COMPANY_CMS_PAGE));

		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user, null);
		breadcrumbs.add(new Breadcrumb(String.format("/my-company/organization-management/manage-users/permissions?user=%s",
				urlEncode(user)), getMessageSource().getMessage("text.company.manage.units.permissions.breadcrumb", new Object[]
		{ user }, "Customer {0} Permissions", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);

		// Handle paged search results
		final PageableData pageableData = createPageableData(page, getSearchPageSize(), sortCode, showMode);
		final SearchPageData<B2BPermissionData> searchPageData = b2bPermissionFacade.getPagedPermissionsForCustomer(pageableData,
				user);
		populateModel(model, searchPageData, showMode);
		model.addAttribute("action", "permissions");
		model.addAttribute("baseUrl", "/my-company/organization-management/manage-users");
		model.addAttribute("cancelUrl", getCancelUrl(MANAGE_USER_DETAILS_URL, request.getContextPath(), user));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return ControllerConstants.Views.Pages.MyCompany.MyCompanyManageUserPermissionsPage;
	}

	@ResponseBody
	@RequestMapping(value = "/permissions/select", method =
	{ RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public B2BSelectionData selectPermissionForCustomer(@RequestParam("user") final String user,
			@RequestParam("permission") final String permission) throws CMSItemNotFoundException
	{
		return b2bPermissionFacade.addPermissionToCustomer(user, permission);
	}

	@ResponseBody
	@RequestMapping(value = "/permissions/deselect", method =
	{ RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public B2BSelectionData deselectPermissionForCustomer(@RequestParam("user") final String user,
			@RequestParam("permission") final String permission) throws CMSItemNotFoundException
	{
		return b2bPermissionFacade.removePermissionFromCustomer(user, permission);
	}

	@RequestMapping(value = "/permissions/remove", method =
	{ RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String removeCustomersPermission(@RequestParam("user") final String user,
			@RequestParam("permission") final String permission, final RedirectAttributes redirectModel)
			throws CMSItemNotFoundException
	{
		b2bPermissionFacade.removePermissionFromCustomer(user, permission);
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "text.confirmation.permission.removed");
		return String.format(REDIRECT_TO_USER_DETAILS, urlEncode(user));
	}

	@RequestMapping(value = "/permissions/confirm/remove", method =
	{ RequestMethod.GET })
	@RequireHardLogIn
	public String confirmRemovePermissionFromUser(@RequestParam("user") final String user,
			@RequestParam("permission") final String permission, final Model model, final HttpServletRequest request)
			throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user, null);
		breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage("text.company.users.remove.permission.confirmation",
				new Object[]
				{ permission }, "Remove Permission {0}", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("arguments", String.format("%s, %s", permission, user));
		model.addAttribute("page", "users");
		model.addAttribute("role", "permission");
		model.addAttribute("disableUrl", String.format(request.getContextPath()
				+ "/my-company/organization-management/manage-users/permissions/remove/?user=%s&permission=%s", urlEncode(user),
				urlEncode(permission)));
		model.addAttribute("cancelUrl", getCancelUrl(MANAGE_USER_DETAILS_URL, request.getContextPath(), user));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return ControllerConstants.Views.Pages.MyCompany.MyCompanyRemoveDisableConfirmationPage;
	}

	@RequestMapping(value = "/approvers/confirm/remove", method =
	{ RequestMethod.GET })
	@RequireHardLogIn
	public String confirmRemoveApproverFromUser(@RequestParam("user") final String user,
			@RequestParam("approver") final String approver, final Model model, final HttpServletRequest request)
			throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));

		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user, null);
		breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage(
				String.format("text.company.users.remove.%s.confirmation", B2BConstants.B2BAPPROVERGROUP), new Object[]
				{ approver }, "Remove B2B Approver {0}", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("arguments", String.format("%s, %s", approver, user));
		model.addAttribute("page", "users");
		model.addAttribute("role", B2BConstants.B2BAPPROVERGROUP);
		model.addAttribute("disableUrl", String.format(request.getContextPath()
				+ "/my-company/organization-management/manage-users/approvers/remove/?user=%s&approver=%s", urlEncode(user),
				urlEncode(approver)));
		model.addAttribute("cancelUrl", getCancelUrl(MANAGE_USER_DETAILS_URL, request.getContextPath(), user));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return ControllerConstants.Views.Pages.MyCompany.MyCompanyRemoveDisableConfirmationPage;
	}

	@RequestMapping(value = "/usergroups", method = RequestMethod.GET)
	@RequireHardLogIn
	public String getPagedB2BUserGroupsForCustomer(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final AbstractSearchPageController.ShowMode showMode,
			@RequestParam(value = "sort", defaultValue = UserModel.NAME) final String sortCode,
			@RequestParam("user") final String user, final Model model, final HttpServletRequest request)
			throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(MY_COMPANY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MY_COMPANY_CMS_PAGE));

		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user, null);
		breadcrumbs.add(new Breadcrumb(String.format("/my-company/organization-management/manage-users/usergroups?user=%s",
				urlEncode(user)), getMessageSource().getMessage("text.company.manageUsers.usergroups.breadcrumb", new Object[]
		{ user }, "Customer {0} User Groups", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);

		// Handle paged search results
		final PageableData pageableData = createPageableData(page, getSearchPageSize(), sortCode, showMode);
		final SearchPageData<B2BUserGroupData> searchPageData = b2bUserFacade.getPagedB2BUserGroupsForCustomer(pageableData, user);
		populateModel(model, searchPageData, showMode);
		model.addAttribute("action", "usergroups");
		model.addAttribute("baseUrl", "/my-company/organization-management/manage-users");
		model.addAttribute("cancelUrl", getCancelUrl(MANAGE_USER_DETAILS_URL, request.getContextPath(), user));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return ControllerConstants.Views.Pages.MyCompany.MyCompanyManageUserB2BUserGroupsPage;
	}

	@ResponseBody
	@RequestMapping(value = "/usergroups/select", method =
	{ RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public B2BSelectionData selectB2BUserGroupForCustomer(@RequestParam("user") final String user,
			@RequestParam("usergroup") final String usergroup) throws CMSItemNotFoundException
	{
		return b2bUserFacade.addB2BUserGroupToCustomer(user, usergroup);
	}

	@ResponseBody
	@RequestMapping(value = "/usergroups/deselect", method =
	{ RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public B2BSelectionData deselectB2BUserGroupForCustomer(@RequestParam("user") final String user,
			@RequestParam("usergroup") final String usergroup) throws CMSItemNotFoundException
	{
		return b2bUserFacade.deselectB2BUserGroupFromCustomer(user, usergroup);
	}

	@RequestMapping(value = "/usergroups/confirm/remove", method =
	{ RequestMethod.GET })
	@RequireHardLogIn
	public String confirmRemoveUserGroupFromUser(@RequestParam("user") final String user,
			@RequestParam("usergroup") final String usergroup, final Model model, final HttpServletRequest request)
			throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user, null);
		breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage("text.company.users.remove.usergroup.confirmation",
				new Object[]
				{ usergroup }, "Remove User group {0}", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("arguments", String.format("%s, %s", user, usergroup));
		model.addAttribute("page", "users");
		model.addAttribute("role", "usergroup");
		model.addAttribute("disableUrl", String.format(request.getContextPath()
				+ "/my-company/organization-management/manage-users/usergroups/remove/?user=%s&usergroup=%s", urlEncode(user),
				urlEncode(usergroup)));
		model.addAttribute("cancelUrl", getCancelUrl(MANAGE_USER_DETAILS_URL, request.getContextPath(), user));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return ControllerConstants.Views.Pages.MyCompany.MyCompanyRemoveDisableConfirmationPage;
	}

	@RequestMapping(value = "/usergroups/remove", method =
	{ RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String removeCustomersUserGroup(@RequestParam("user") final String user,
			@RequestParam("usergroup") final String usergroup, final RedirectAttributes redirectModel, HttpServletRequest request)
			throws CMSItemNotFoundException
	{
		try
		{
			((SiteOneCustomerFacade) customerFacade).getClientInformation(request);
			b2bUserFacade.removeB2BUserGroupFromCustomerGroups(user, usergroup);
			GlobalMessages
					.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "text.confirmation.usergroup.removed");
		}
		catch (final UnknownIdentifierException e)
		{
			if (LOG.isDebugEnabled())
			{
				LOG.debug("can not remove b2b user '" + user + "' from group '" + usergroup + "' due to, " + e.getMessage(), e);
			}
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "usergroup.notfound");
		}
		return String.format(REDIRECT_TO_USER_DETAILS, urlEncode(user));
	}

	@RequestMapping(value = "/edit-permission", method = RequestMethod.GET)
	@RequireHardLogIn
	public String editUsersPermission(@RequestParam("user") final String user,
			@RequestParam("permission") final String permission, final Model model, final HttpServletRequest request)
			throws CMSItemNotFoundException
	{
		model.addAttribute("cancelUrl", getCancelUrl(MANAGE_USER_DETAILS_URL, request.getContextPath(), user));
		model.addAttribute(
				"saveUrl",
				String.format("%s/my-company/organization-management/manage-users/edit-permission?user=%s&permission=%s",
						request.getContextPath(), urlEncode(user), urlEncode(permission)));

		final String editPermissionUrl = editPermission(permission, model);

		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user, null);
		breadcrumbs.add(new Breadcrumb(String.format(
				"/my-company/organization-management/manage-users/edit-permission?user=%s&permission=%s", urlEncode(user),
				urlEncode(permission)), getMessageSource().getMessage("text.company.manageusers.permission.edit", new Object[]
		{ permission }, "Edit Permission {0}", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		return editPermissionUrl;
	}

	@RequestMapping(value = "/edit-permission", method = RequestMethod.POST)
	@RequireHardLogIn
	public String editUsersPermission(@RequestParam("user") final String user,
			@RequestParam("permission") final String permission, @Valid final B2BPermissionForm b2BPermissionForm,
			final BindingResult bindingResult, final Model model, final HttpServletRequest request,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException, ParseException
	{
		model.addAttribute("cancelUrl", getCancelUrl(MANAGE_USER_DETAILS_URL, request.getContextPath(), user));
		model.addAttribute(
				"saveUrl",
				String.format("%s/my-company/organization-management/manage-users/edit-permission?user=%s&permission=%s",
						request.getContextPath(), urlEncode(user), urlEncode(permission)));

		final String editPermissionUrl = editPermission(b2BPermissionForm, bindingResult, model, redirectModel);
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user, null);
		breadcrumbs.add(new Breadcrumb(String.format(
				"/my-company/organization-management/manage-users/edit-permission?user=%s&permission=%s", urlEncode(user),
				urlEncode(permission)), getMessageSource().getMessage("text.company.manageusers.permission.edit", new Object[]
		{ permission }, "Edit Permission {0}", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		if (bindingResult.hasErrors())
		{
			return editPermissionUrl;
		}
		else
		{
			return String.format(REDIRECT_TO_USER_DETAILS, urlEncode(user));
		}
	}
	
	@RequestMapping(value = "/all-ship-to-popup/" + UNIT_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String getShipToPagePopupManageUsers(@PathVariable("unit") final String unit,
			@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "searchParam", required = false) final String searchParam,
			@RequestParam(value = "shiptounit", required = false) final String shipToUnit,
			@RequestParam(value = "pagesize", defaultValue = "3") final String pageSize,
			@RequestParam(value = "filterAdmin", defaultValue= "false", required = false) final Boolean filterAdmin,
			@RequestParam(value = "sort", defaultValue = B2BCustomerModel.NAME) final String sortCode, final Model model)
			throws CMSItemNotFoundException
	{
	int viewByPageSize = getSiteConfigService().getInt("siteoneorgaddon.searchPopup.pageSize", 10);
	final PageableData pageableData = createPageableData(page, viewByPageSize, sortCode, showMode);
	String trimmedSearchParam = null;
	if (null != searchParam)
	{
		trimmedSearchParam = searchParam.trim();
	}
	if (null != pageSize)
	{
		viewByPageSize = Integer.parseInt(pageSize);
	}
	String userUnit = (null != shipToUnit) ? shipToUnit : unit;
	
	Map<String, String> listOfShipTos = ((SiteOneB2BUserFacade) b2bUserFacade).getListOfShipTos();
	
	
	final SearchPageData<B2BUnitData> searchPageData = ((SiteOneCustomerFacade) getCustomerFacade()).getPagedB2BDefaultUnits(pageableData, trimmedSearchParam);
	
	
	
	populateModel(model, searchPageData, showMode);
	model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
	model.addAttribute("searchParam", trimmedSearchParam);
	model.addAttribute("sortShipToPopupManageUsers", sortCode);
	model.addAttribute("unit", unit);
	model.addAttribute("shiptounit", userUnit);
	model.addAttribute("sort", sortCode);
	model.addAttribute("pagesize", viewByPageSize);
	model.addAttribute("listOfShipTos", listOfShipTos);
	model.addAttribute("totalCount", searchPageData.getPagination().getTotalNumberOfResults());
	model.addAttribute("searchPageData", searchPageData);
	model.addAttribute("filterAdmin", filterAdmin);
	model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
	storeCmsPageInModel(model, getContentPageForLabelOrId("userShipToPage"));
	setUpMetaDataForContentPage(model, getContentPageForLabelOrId("userShipToPage"));
	return ControllerConstants.Views.Pages.MyCompany.MyCompanyManageUsersShipToPagePopup;
	}
}