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

import de.hybris.platform.addonsupport.config.bundlesources.JavaScriptMessageResourcesAccessor;
import de.hybris.platform.addonsupport.config.javascript.BeforeViewJsPropsHandlerAdaptee;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import org.springframework.ui.ModelMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;


import java.util.Map;


public class SiteoneExportJsPropertiesBeforeViewHandler extends BeforeViewJsPropsHandlerAdaptee {

    private JavaScriptMessageResourcesAccessor messageSource;

    @Override
    public String beforeViewJsProps(final HttpServletRequest request, final HttpServletResponse response, final ModelMap model,
                                    final String viewName)
    {
        if((viewName.equalsIgnoreCase("pages/account/accountDashboardPage"))||
           (viewName.equalsIgnoreCase("addon:/siteoneorgaddon/pages/company/myCompanyManageUserAddEditFormPage"))) {
           Map<String, Object> result =  model.entrySet().stream()
                                               .filter(entry->entry.getKey().equalsIgnoreCase("user"))
                                                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
           if(!result.isEmpty()){
             CustomerData customerData = (CustomerData)result.get("user");
             model.addAttribute("contPayBillOnline",customerData.getPayBillOnline() != null && customerData.getPayBillOnline());
           }
        }
        return viewName;
    }


    @Override
    public JavaScriptMessageResourcesAccessor getMessageSource() {
        return messageSource;
    }

    @Override
    public void setMessageSource(JavaScriptMessageResourcesAccessor messageSource) {
        this.messageSource = messageSource;
    }

}