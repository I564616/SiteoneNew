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
package com.siteone.facades.constants;

/**
 * Global class for all SiteoneFacades constants.
 */
@SuppressWarnings("PMD")
public class SiteoneFacadesConstants extends GeneratedSiteoneFacadesConstants
{
	public static final String EXTENSIONNAME = "siteonefacades";

	public static final String PIPE = "\\|";
	public static final String SLASH = "/";
	public static final int ZERO = 0;
	public static final int ONE = 1;
	public static final int TWO = 2;
	public static final int THREE = 3;
	public static final int FOUR = 4;

	public static final String CAYAN_PAYMENT_MERCHANT_NAME = "cayan.payment.merchantName";
	public static final String CAYAN_PAYMENT_MERCHANT_SITE_ID = "cayan.payment.merchantSiteId";
	public static final String CAYAN_PAYMENT_MERCHANT_KEY = "cayan.payment.merchantKey";
	public static final String CAYAN_PAYMENT_SOURCE_BOARDCARD = "cayan.payment.source.boardcard";
	public static final String CAYAN_PAYMENT_SOURCE = "cayan.payment.source";
	public static final String CAYAN_TRANSPORT_REDIRECT_URL = "cayan.boardcard.redirect.url";
	public static final String CAYAN_TRANSPORT_REDIRECT_LOGO = "cayan.iframe.siteone.logo";
	public static final String CAYAN_TRANSPORT_AUTHORIZE_REDIRECT_URL = "cayan.authorize.redirect.url";
	public static final String WEBSITE_SITEONE_SERVER = "website.siteone.url";
	public static final String CAYAN_STATUS_CANCELLED = "User_Cancelled";
	public static final String CAYAN_TRANSPORT_AUTHORIZE_LINK_TO_PAY_REDIRECT_URL = "cayan.authorize.link.pay.redirect.url";

	public static final String CAYAN_TRANSPORT_KEY_BOARDCARD = "BOARDCARD";
	public static final String CAYAN_TRANSPORT_KEY_DBA = "Siteone Update DBA";
	public static final String CAYAN_TRANSPORT_KEY_CLERKID = "Online";
	public static final String CAYAN_TRANSPORT_KEY_SOFTWARE_NAME = "POS Software X";
	public static final String CAYAN_TRANSPORT_KEY_SOFTWARE_VER = "v1.0.0.1";
	public static final String CAYAN_TRANSPORT_KEY_PREAUTH = "PREAUTH";
	public static final String NICK_NAME = "nickName";
	public static final String SAVECARD = "saveCard";
	public static final String SUCCESS = "Success";
	public static final String HYRIS_SYSTEM_ID = "2";
	public static final String CAYAN_STATUS_APPROVED = "APPROVED";
	public static final String ADD_EWALLET_OPERATION = "ADD";
	public static final String TRUE = "true";
	public static final String GRANT = "GRANT";
	public static final String REVOKE = "REVOKE";
	public static final String UPDATE = "UPDATE";
	public static final String DELETE = "DELETE";
	public static final String EDIT = "EDIT";
	public static final String ACCESSTM = "ACCESSTM";
	public static final String ACCESSADMIN = "ACCESSADMIN";

	//SE-7488-Capture Customer Buyer Type
	public static final String CUSTOMER_TYPE_NEW_READY_TO_ORDER = "New Online Customer, Ready to Order";
	public static final String CUSTOMER_TYPE_NEW_FIRST_TIME_PURCHASE = "New Online Customer, First time Purchase";
	public static final String CUSTOMER_TYPE_LAPSED_REACTIVATED = "Lapsed/Reactivated";
	public static final String CUSTOMER_TYPE_REPEAT_LESS_THAN = "Repeat Customer <90 Days";
	public static final String CUSTOMER_TYPE_REPEAT_GREATER_THAN = "Repeat Customer >90 Days";

	public static final String POA_CREDIT_CODE = "C,A,B,R,S,X";

	public static final String CREDIT_PAYMENTTYPE = "3";

	public static final String POA_PAYMENTTYPE = "1";

	public static final String CREDITCARD_EWALLET_TYPE = "Credit Card/Ewallet";

	public static final String POA_TYPE = "SiteOne Online Account";

	public static final String PAY_AT_BRANCH_TYPE = "Pay At Branch";

	public static final String ASM_SESSION_PARAMETER = "ASM";

	// Mobile App
	public static final String WEBSITE_SITEONE_AKAMAI_SERVER = "website.siteone.akamai.url";
	public static final String CAYAN_TRANSPORT_MOBILE_REDIRECT_URL = "cayan.boardcard.redirect.url.mobile";

	private SiteoneFacadesConstants()
	{
		//empty
	}
}
