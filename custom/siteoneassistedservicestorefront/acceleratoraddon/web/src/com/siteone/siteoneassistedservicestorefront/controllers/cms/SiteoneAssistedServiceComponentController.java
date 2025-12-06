/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 *
 *
 */
package com.siteone.siteoneassistedservicestorefront.controllers.cms;

import de.hybris.platform.assistedservicefacades.AssistedServiceFacade;
import de.hybris.platform.assistedservicestorefront.controllers.cms.AssistedServiceComponentController;
import de.hybris.platform.assistedservicestorefront.security.AssistedServiceAgentAuthoritiesManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;


public class SiteoneAssistedServiceComponentController extends AssistedServiceComponentController {

    private static final String ASM_REDIRECT_URL_ATTRIBUTE = "redirect_url";

    @Resource(name = "assistedServiceFacade")
    private AssistedServiceFacade assistedServiceFacade;

    @Resource(name = "assistedServiceAgentAuthoritiesManager")
    private AssistedServiceAgentAuthoritiesManager authoritiesManager;

    @PostMapping("/logoutasm")
    public String logoutAssistedServiceAgent(final Model model, final HttpServletRequest request) {
        authoritiesManager.restoreInitialAuthorities();
        assistedServiceFacade.stopEmulateCustomer();
        refreshSpringSecurityToken();
        model.addAllAttributes(assistedServiceFacade.getAssistedServiceSessionAttributes());
        model.addAttribute(ASM_REDIRECT_URL_ATTRIBUTE, "/");

        return super.logoutAssistedServiceAgent(model, request);
    }

}