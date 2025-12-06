/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 *
 *
 */
package com.siteone.siteoneassistedservicestorefront.controllers;

import de.hybris.platform.assistedservicestorefront.controllers.CustomerListController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Controller to handle querying requests for ASM and handling customer lists implementations
 */
public class SiteoneCustomerListController extends CustomerListController {


    /**
     * Responsible for getting list of customers based on a customer List UId and handle pagination and sorting of this
     * list as well
     *
     * @param model           to hold populated data
     * @param page            page number in case we have more than 1 page of data
     * @param showMode        either to show all or to show pages (default is page)
     * @param sortCode        the sort code for the list of customers
     * @param customerListUid the customer list UId to get customers for
     * @param query           the query provided by the user to filter the results
     * @return paginated view with customer data
     */

    @GetMapping("/listCustomers")
    public String listPaginatedCustomers(final Model model, @RequestParam(value = "page", defaultValue = "0") final int page,
                                         @RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
                                         @RequestParam(value = "sort", required = false) final String sortCode,
                                         @RequestParam(value = "customerListUId", required = false) final String customerListUid,
                                         @RequestParam(value = "query", required = false) final String query) {
        super.listPaginatedCustomers(model, page, showMode, sortCode, customerListUid, query);

        return SiteoneassistedservicestorefrontControllerConstants.Views.Fragments.CustomerListComponent.ASMCustomerListTable;

    }

}
