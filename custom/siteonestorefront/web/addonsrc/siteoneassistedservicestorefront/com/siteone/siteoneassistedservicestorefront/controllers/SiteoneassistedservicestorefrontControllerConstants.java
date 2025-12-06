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
package com.siteone.siteoneassistedservicestorefront.controllers;

/**
 */
public interface SiteoneassistedservicestorefrontControllerConstants
{
    String ADDON_PREFIX = "addon:/siteoneassistedservicestorefront/";

    // implement here controller constants used by this extension

    interface Views
    {

        interface Fragments
        {

            interface CustomerListComponent
            {
                String ASMCustomerListTable = ADDON_PREFIX + "fragments/asmCustomerListTable";
            }

        }
    }

}
