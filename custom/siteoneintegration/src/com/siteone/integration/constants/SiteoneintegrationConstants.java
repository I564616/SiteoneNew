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
package com.siteone.integration.constants;

import java.util.HashMap;
import java.util.Map;

import de.hybris.platform.fraud.symptom.impl.FirstTimeOrderSymptom;

/**
 * Global class for all Siteoneintegration constants. You can add global constants for your extension into this class.
 */
public final class SiteoneintegrationConstants extends GeneratedSiteoneintegrationConstants
{
	public static final String EXTENSIONNAME = "siteoneintegration";

	private SiteoneintegrationConstants()
	{
		//empty to avoid instantiating this constant class
	}

	// implement here constants used by this extension

    public static final String PLATFORM_LOGO_CODE = "siteoneintegrationPlatformLogo";
    
    public static final String SLASH = "/";
	public static final int ZERO = 0;
	public static final int ONE = 1;
	public static final int LENGTH_LEVEL3_CATEGORY = 9;
    
 	// Operation Types
 	public static final String OPERATION_TYPE_CREATE = "CREATE";
 	public static final String OPERATION_TYPE_UPDATE = "UPDATE";
 	public static final String OPERATION_TYPE_DELETE = "DELETE";
 	
 	// CA Global payment
 	public static final String GLOBAL_APP_ID="ue.ca.global.payment.appid";
 	public static final String GLOBAL_API_KEY="ue.ca.global.payment.api.key";
 	public static final String GLOBAL_INTERNATIONL_API_KEY="ue.ca.global.payment.internation.api.key";
 	public static final String GLOBAL_API_SECRET_KEY="ue.ca.global.payment.api.secret.key";
 	public static final String GLOBAL_ACCOUNT_CREDS="ue.ca.global.payment.account.creds";
 	public static final String GLOBAL_INTERVAL_TO_EXPIRE="ue.ca.global.payment.expire.interval";
 	public static final String GLOBAL_BASE_URL="ue.ca.global.payment.url";
 	public static final String GLOBAL_VOID_PAYMENT_URL="ue.ca.global.void.url";
 	public static final String QUERY_PARAM_VOID_HEADER_ID= ":referenceId";
 	public static final String GLOBAL_ENV="ue.ca.global.payment.environment";
 	public static final String GLOBAL_PAYMENT_SERVICE_NAME = "GlobalVoidPayment";
 	public static final String FIRSTTIME_USER_CA_UPDATE="firsttime.user.ca.update";
 	//Rest Url Keys
 	public static final String CONTACT_SERVICE_URL_KEY =  "ue.contact.service.url";
 	public static final String CONTACT_SERVICE_NEW_URL_KEY =  "ue.new.contact.service.url";
 	public static final String ADDRESS_SERVICE_URL_KEY =  "ue.address.service.url";
 	public static final String ADDRESS_SERVICE_NEW_URL_KEY =  "ue.new.address.service.url";
 	public static final String ADDRESS_VERIFICATION_SERVICE_URL_KEY =  "ue.address.verification.service.url";
 	public static final String ADDRESS_VERIFICATION_API_KEY =  "ue.address.verification.key";
 	public static final String CUSTOMER_INFO_URL_KEY =  "ue.customer.info.url";
	public static final String CUSTOMER_INFO_NEW_URL_KEY = "ue.new.customer.info.url";
 	public static final String AUTHORIZATION_HEADER =  "Authorization";
 	public static final String AUTHORIZATION_VALUE =  "ApiKey";
 	public static final String AUTHORIZATION_TYPE_BASIC =  "Basic";
 	public static final String HTTP_ENTITY_KEY = "parameters";
 	public static final String PURCHASED_PRODUCT_SERVICE_URL_KEY =  "ue.purchased.product.service.url";
	public static final String DELIVERY_COST_SERVICE_URL_KEY =  "ue.deliverycost.service.url";
 	public static final String AUTHORIZATION_TYPE_SSWS =  "SSWS";
 	public static final String AUTHORIZATION_TYPE_BEARER =  "Bearer";
 	public static final String ACCEPT = "Accept";
 	public static final String CONTENT_TYPE = "Content-Type";
	public static final String CLIENT_IP_ADDR = "X-Forwarded-For";
	public static final String USER_AGENT = "User-Agent";
 	public static final String OKTA_API_KEY =  "okta.api.token";
 	public static final String OKTA_USER_ID_PLACEHOLDER =  ":id";
 	public static final String OKTA_API_BASE_URL =  "okta.domain.url";
 	public static final String OKTA_CREATE_USER_URL_KEY =  "okta.create.user.url";
 	public static final String OKTA_UPDATE_USER_URL_KEY =  "okta.update.user.url";
	public static final String OKTA_SUSPEND_USER_URL_KEY =  "okta.suspend.user.url";
	public static final String OKTA_UNSUSPEND_USER_URL_KEY =  "okta.unsuspend.user.url";
 	public static final String OKTA_CHANGE_PASSWORD_URL_KEY =  "okta.change.password.url";
 	public static final String OKTA_SET_PASSWORD_URL_KEY =  "okta.set.password.url";
 	public static final String OKTA_GET_USER_URL_KEY = "okta.get.user.url";
 	public static final String OKTA_ACTIVATE_USER_URL_KEY =  "okta.activate.user.url";
 	public static final String OKTA_UNLOCK_USER_URL_KEY =  "okta.unlock.user.url";
 	public static final String PRICE_SERVICE_URL_KEY =  "ue.price.service.url";
 	public static final String PRICE_SERVICE_NEW_URL_KEY =  "ue.new.price.service.url";
 	public static final String PRICE_SERVICE_D365_URL_KEY =  "d365.price.service.url";
	public static final String PRICE_SERVICE_MAX_PRODUCT =  "ue.price.service.max.product";
	public static final String PRICE_SERVICE_D365_NEW_URL_KEY =  "d365.price.newservice.url";
	public static final String PRICE_SERVICE_FALLBACK_URL_KEY =  "onprem.price.fallback.url";
	public static final String X_API_KEY_PRICE_FALLBACK =  "onprem.price.fallback.key";

 	public static final String OKTA_FORGOT_PASSWORD_URL_KEY =  "okta.forgot.password.url";
 	public static final String OKTA_VERIFY_RECOVERY_TOKEN_URL_KEY =  "okta.verify.recovery.token.url";
 	public static final String OKTA_RESET_PASSWORD_URL_KEY =  "okta.reset.password.url";
 	
 	public static final String OKTA_AUTH_ENDPOINT_USERS="okta.auth.endpoint.users";
 	public static final String OKTA_APP_LINKS = "okta.app.links";
 	
 	public static final String OKTA_AUTH_ENDPOINT_APPS="okta.auth.endpoint.apps";
 	public static final String OKTA_PROJECT_SERVICES_CLIENTID="okta.project.services.clientid";
 	public static final String OKTA_APP_USERS = "okta.app.users";
 	
 	public static final String OKTA_REDIRECT_URL_PART1 =  "okta.redirect.url1";
 	public static final String OKTA_REDIRECT_URL_PART2 =  "okta.redirect.url2";
 	
 	public static final String OKTA_SITEONE_APPNAME = "okta.siteone.appname";
 	public static final String OKTA_SITEONE_CA_APPNAME = "okta.siteone.ca.appname";
 	public static final String OKTA_BILLTRUST_APPNAME= "okta.billtrust.appname";
 	
 	public static final String OKTA_BILLTRUST_GROUPID="okta.billtrust.groupid"; 
 	
 	public static final String OKTA_NXTLEVEL_GROUPID="okta.nxtlevel.groupid";
 	public static final String OKTA_NXTLEVEL_APPNAME= "okta.nxtlevel.appname";
 	public static final String OKTA_NXTLEVEL_CLIENTID="okta.nxtlevel.clientid";
 	public static final String NEXT_LEVEL_ENDPOINT="nxtlevel.url.home";
 	
 	//Integration control key
 	public static final String INTEGRATION_ENABLE_KEY =  "integration.enable";
 	public static final String INTEGRATION_CSP_ENABLE_KEY =  "integration.csp.enable";
 	public static final String TRACK_RETAILCSP_ENABLE_KEY =  "track.retailCSP.pricing";
	
 	//Create Customer Service Call
	public static final String REQUEST_ACCOUNT_FORM_CONTRACTOR = "Contractor";
	public static final String CREATE_CUSTOMER_URL_KEY = "ue.create.customer.url";
	public static final String SELF_SERVE_URL_KEY = "ue.self.serve.url";
	public static final String CREATE_CUSTOMER_NEW_URL_KEY = "ue.new.create.customer.url";
	public static final String CREATE_CUSTOMER_SERVICE_NAME = "CREATE_CUSTOMER_SERVICE";
	public static final String SELF_SERVE_NEW_URL_KEY = "ue.new.self.serve.url";
	public static final String SELF_SERVE_SERVICE_NAME = "SELF_SERVE_SERVICE";
	public static final String CREATE_CUSTOMER_CONTRACTOR = "CONTRACTOR";
	public static final String CREATE_CUSTOMER_RETAIL = "RETAIL";
	public static final String ENROLL_CUSTOMER = "Enroll";
	public static final String REALTIME_ACCOUNT_LOG_TARGET_LOCATION = "realtimeaccount.target.location";

	//Link to pay and Quotes Service call
	public static final String LINK_TO_PAY_ORDER_URL_KEY = "ue.linktopay.order.url";
	public static final String LINK_TO_PAY_VERIFICATION_KEY = "ue.linktopay.verification.key";
	public static final String LINK_TO_PAY_ORDER_NEW_URL_KEY = "ue.new.linktopay.order.url";
	public static final String QUOTES_DETAIL_NEW_URL_KEY = "ue.quotes.detail.url";
	public static final String QUOTES_APPROVAL_HISTORY_NEW_URL_KEY = "ue.quotes.approval.history.url";
	public static final String QUOTES_REQUEST_NEW_URL_KEY = "ue.quotes.request.url";
	public static final String UPDATE_QUOTE_NEW_URL_KEY = "ue.update.quote.url";
	public static final String SUMMARY_QUOTE_NEW_URL_KEY = "ue.update.quote.summary.url";
	public static final String QUOTES_NEW_URL_KEY = "ue.quotes.url";
	public static final String LINK_TO_PAY_ORDER_SERVICE_NAME = "OrderTransactionDetails";
	public static final String QUOTES_SERVICE_NAME = "Quotes";
	public static final String QUOTES_DETAILS_SERVICE_NAME = "QuoteDetails";
	public static final String QUOTES_REQUEST_SERVICE_NAME = "QuoteRequest";
	
// Project Service call 
	public static final String PS_BEARER_TOKEN_URL = "ps.bearer.token.url";
	public static final String PS_BEARER_TOKEN_SERVICE_NAME = "BearerToken";
	public static final String PS_DASHBOARD_URL = "ps.dashboard.url";
	public static final String PS_DASHBOARD_SERVICE_NAME = "PsDashboard";
	public static final String PS_ADD_BID_URL = "ps.add.bid.url";
	public static final String PS_ADD_SERVICE_NAME = "PsBidAddition";
	public static final String PS_REMOVE_BID_URL = "ps.remove.bid.url";
	public static final String PS_REMOVE_SERVICE_NAME = "PsBidRemoval";
	public static final String PS_TOGGLE_HIDE_URL = "ps.hide.project.url";
	public static final String PS_HIDE_SERVICE_NAME = "PsHideProject";
	public static final String PS_PROJECT_ID = ":projectId";
	public static final String PS_IS_HIDDEN = ":isHidden";	
	public static final String PS_TOGGLE_FAV_URL = "ps.favorite.project.url";
	public static final String PS_FAV_SERVICE_NAME = "PsFavoriteProject";
	public static final String PS_IS_FAVORITE = ":isFavorite";
	public static final String PS_PREF_ID = ":preferenceId";
	public static final String PROJECT_SERVICES_SERVICE_KEY_NAME = "x-api-key";
	public static final String PROJECT_SERVICES_SERVICE_KEY = "project.services.api.key";
		
// Orders service call		
	public static final String OPEN_ORDERS_SERVICE_KEY_NAME = "x-api-key";
	public static final String OPEN_ORDERS_SERVICE_URL = "ue.open.orders.data.url";
	public static final String OPEN_ORDERS_SERVICE_KEY = "ue.open.orders.data.key";
	public static final String OPEN_ORDERS_NEW_SERVICE_URL = "ue.open.orders.new.url"; 
	public static final String OPEN_ORDERS_NEW_SERVICE_KEY = "boomi.open.orders.new.key"; 
	public static final String QUERY_PARAM_CUSTOMER_NUMBER_OO= ":customerNumber"; 		
	public static final String QUERY_PARAM_DIVISIONID_OO= ":divisionID";
	public static final String OPEN_ORDERS_SERVICE_NAME = "OPEN_ORDERS_DATA_SERVICE";

// Orders Details service call		
	public static final String ORDER_DETAIL_SERVICE_NAME = "ORDER_DETAIL_DATA_SERVICE";
    public static final String ORDER_DETAIL_SERVICE_URL = "ue.new.order.detail.url";
	public static final String ORDER_DETAIL_SERVICE_KEY_NAME = "x-api-key";
    public static final String ORDER_DETAIL_SERVICE_KEY = "ue.order.detail.data.api.key";
    public static final String ORDER_DETAIL_NEW_SERVICE_URL = "ue.new.order.detail.data.url";
	public static final String ORDER_DETAIL_NEW_SERVICE_KEY = "boomi.order.detail.api.newkey";
	public static final String QUERY_PARAM_ORDERID_OO= ":orderID";
	public static final String QUERY_PARAM_INVOICEID_OO= ":invoiceID";

	public static final String LINK_TO_PAY_PAYMENT_URL_KEY = "ue.linktopay.payment.url";
	public static final String LINK_TO_PAY_PAYMENT_NEW_URL_KEY = "ue.new.linktopay.payment.url";
	public static final String LINK_TO_PAY_PAYMENT_SERVICE_NAME = "OrderPaymentDetails";

	public static final String QUERY_PARAM_ORDER_NUMBER= ":orderNumber";
	public static final String QUERY_PARAM_CORRELATION_ID= ":correlationID";
	public static final String QUERY_PARAM_QUOTE_HEADER_ID= ":quoteHeaderID";
	public static final String QUERY_PARAM_QUOTE_DETAIL_ID= ":quoteDetailID";
 		
	// Enroll into TalonOne Service Call
	public static final String ENROLL_IN_TALONONE_URL_KEY = "ue.enroll.talonone.url";
	public static final String ENROLL_IN_TALONONE_NEW_URL_KEY = "ue.new.enroll.talonone.url";
	public static final String TALONONE_ENROLLMENT_SERVICE_NAME = "ENROLL_TALONONE_SERVICE";
 	//Service Name

	 public static final String X_API_PENDO_HEADER =  "x-pendo-integration-key";
    public static final String X_API_PENDO_KEY =  "pendo.api.header.key";
	public static final String PENDO_EVENT_URL = "pendo.event.url";
	
	public static final String DELIVERY_COST_SERVICE_NAME = "getDeliveryCost";
 	public static final String CONTACT_SERVICE_NAME = "CONTACT_SERVICE";
 	public static final String ADDRESS_SERVICE_NAME = "ADDRESS_SERVICE";
 	public static final String INVENTORY_SERVICE_NAME = "INVENTORY_SERVICE";
 	public static final String PRICE_SERVICE_NAME = "PRICE_SERVICE";
 	public static final String ADDRESS_VERIFICATION_SERVICE_NAME = "ADDRESS_VERIFICATION_SERVICE";
 	public static final String OKTA_CREATE_USER_SERVICE_NAME = "OKTA_CREATE_USER";
 	public static final String OKTA_UPDATE_USER_SERVICE_NAME = "OKTA_UPDATE_USER";
 	public static final String OKTA_CHANGE_PASSWORD_SERVICE_NAME = "OKTA_CHANGE_PASSWORD";
 	public static final String OKTA_SET_PASSWORD_SERVICE_NAME = "OKTA_SET_PASSWORD";
 	public static final String OKTA_ACTIVATE_USER_SERVICE_NAME = "OKTA_ACTIVATE_USER";
 	public static final String OKTA_UNLOCK_USER_SERVICE_NAME = "OKTA_UNLOCK_USER";
	public static final String OKTA_REMOVE_USER_SERVICE_NAME = "OKTA_REMOVE_USER";
	public static final String OKTA_SUSPEND_USER_SERVICE_NAME = "OKTA_SUSPEND_USER";
	public static final String OKTA_UNSUSPEND_USER_SERVICE_NAME = "OKTA_UNSUSPEND_USER";
 	public static final String CUSTOMER_INFO_SERVICE_NAME = "CUSTOMER_INFO";
	public static final String OKTA_FORGOT_PASSWORD_SERVICE_NAME = "OKTA_FORGOT_PASSWORD";
 	public static final String OKTA_VERIFY_RECOVERY_TOKEN_SERVICE_NAME = "OKTA_VERIFY_RECOVERY_TOKEN";
 	public static final String OKTA_RESET_PASSWORD_SERVICE_NAME = "OKTA_RESET_PASSWORD";
 	public static final String OKTA_GET_USER_SERVICE_NAME = "OKTA_GET_USER";
 	public static final String PURCHASED_PRODUCT_SERVICE_NAME = "PURCHASED_PRODUCT_SERVICE";
 	public static final String OKTA_CONFIG_APP_DETAILS_SERVICE_NAME = "OKTA_CONFIG_APP_DETAILS";
 	public static final String OKTA_CONFIG_USER_APP_DETAILS_SERVICE_NAME = "OKTA_CONFIG_USER_APP_DETAILS";
	public static final String ALGONOMY_SERVICE_NAME = "ALGONOMY_SERVICE";
 	
 	public static final String CUSTOMER_INFO_PLACEHOLDER =  ":guid";
 	

 	//Partner Points info
	public static final String PARTNERPOINTS_INFO_URL = "ue.partnerpoints.service.url";
	public static final String PARTNERPOINTS_INFO_NEW_URL = "ue.new.partnerpoints.service.url";
	public static final String PARTNERPOINTS_UNITID_PLACEHOLDER = ":unitId";
	public static final String PARTNERPOINTS_INFO_SERVICE_NAME = "PARTNER_POINTS_SERVICE";

	// 	Loyalty Progran Status
 	public static final String LOYALTY_PROGRAM_STATUS_SERVICE_NAME =  "LOYALTY_PROGRAM_STATUS";
 	public static final String LOYALTY_PROGRAM_STATUS_SERVICE_URL =  "ue.loyaltyprogram.status.url";
 	public static final String LOYALTY_PROGRAM_STATUS_NEW_SERVICE_URL =  "ue.new.loyaltyprogram.status.url";
 	public static final String LOYALTY_PROGRAM_STATUS_CUSTOMER_PLACEHOLDER =  ":customerNumber";
 	

 	public static final String OKTA_CHANGE_PASSWORD_ERROR_CODE="E0000014";
 	public static final String OKTA_SET_PASSWORD_ERROR_CODE="E0000001";
 	public static final String OKTA_USER_STATUS="ACTIVE";
 	public static final String OKTA_USER_STATUS_LOCKED_OUT = "LOCKED_OUT";
 	public static final String OKTA_VERIFY_RECOVERY_TOKEN_ERROR_CODE="E0000105";
 	public static final String OKTA_RESET_PASSWORD_ERROR_CODE1="E0000105";
 	public static final String OKTA_RESET_PASSWORD_ERROR_CODE2="E0000080";
 	public static final String OKTA_FORGOT_PASSWORD_ERROR_CODE="E0000095";
 	public static final String OKTA_FORGOT_PASSWORD_INVALID_STATUS="E0000034";
 	public static final String OKTA_GET_USER_UNKNOWN_ERROR_CODE="E0000007";
 	
 	
 	
 	public static final String PRICE_SERVICE_COMPANY_NAME = "JDL";
 	public static final String PRICE_SERVICE_COMPANY_NAME_CA = "JDLC";
 	public static final String PRICE_SERVICE_DOCUMENT_TYPE = "SalesOrder";
 	public static final String PRICE_SERVICE_FIELD_NAME = "TRANS_BRANCH";
 	public static final String PRICE_SERVICE_FIELD_NAME_INVENT_SITEID = "InventSiteId";
 	public static final String PRICE_SERVICE_ENTITY_TYPE = "Customer";
 	public static final String PRICE_SERVICE_COMPUTER_NAME = "Hybris";
 	public static final Integer PRICE_SESSION_ID_MIN = 900000000;
 	public static final Integer PRICE_SESSION_ID_MAX = 100000000;
 	public static final String DIVISION_US = "US";
 	public static final String DIVISION_CA = "CA";
 	public static final String INDEX_OF_US = "_US";
	public static final String INDEX_OF_CA = "_CA";
 	public static final String DEFAULT_UE_GUID ="00000000-0000-0000-0000-000000000000";

 	//AVS Query Parameters
 	public static final String QUERY_PARAM_STREET = "street";
 	public static final String QUERY_PARAM_STREET2 = "street2";
 	public static final String QUERY_PARAM_CITY = "city";
 	public static final String QUERY_PARAM_STATE = "state";
 	public static final String QUERY_PARAM_ZIPCODE = "zip";
 	public static final String QUERY_PARAM_CUSTTREENODEID = "CustTreeNodeId";
 	public static final String QUERY_PARAM_ISBILLINGNODE = "IsBillingNode";
	// VERIFY GUEST CONTACT REST URL
	public static final String VERIFYGUESTCONTACT_VERIFICATION_API_KEY =  "ue.verifyguestcontact.verification.key";
	public static final String VERIFYGUESTCONTACT_SERVICE_URL = "ue.verifyguestcontact.verification.service.url";
	public static final String VERIFYGUESTCONTACT_NEW_SERVICE_URL = "ue.new.verifyguestcontact.verification.service.url";
	public static final String VERIFYGUESTCONTACT_SERVICE_NAME= "getGuestCheckOut";
	public static final String QUERY_PARAM_GUEST_EMAIL = ":email";
	
	//Sales Data API URL
	public static final String SALESDATA_SERVICE_URL = "ue.salesdata.service.url";
	public static final String SALESDATA_SERVICE_NAME = "SalesData";


	//TMS Delivery Cost Calculation API Call Parameters and Constants
	public static final String QUERY_PARAM_BRANCH = "Branch";
	public static final String QUERY_PARAM_DIVISION = "Division";
	public static final String QUERY_PARAM_ORDER_TYPE = "OrderType";
	public static final String QUERY_PARAM_ORDER_ORIGIN = "OrderOrigin";
	public static final String QUERY_PARAM_LIFT = "Lift";
	public static final String QUERY_PARAM_HOMEOWNERS = "HomeOwners";
	public static final String QUERY_PARAM_ISGUESTUSER = "GuestUser";
	public static final String QUERY_PARAM_EXPEDITE = "Expedite";
	public static final String QUERY_VALUE_ORDER_ORIGIN = "Hybris";
	public static final String BOOLEAN_TRUE = "Yes";
	public static final String BOOLEAN_FALSE = "No";
	public static final String DELIVERYCOST_API_MAXRETRY_COUNT= "hybris.deliverycost.maxRetryCount";
	public static final String VERIFYGUESTCONTACT_API_MAXRETRY_COUNT= "hybris.verifyguestcontact.maxRetryCount";
	public static final String CART_TYPE_OTHER = "Other";
	public static final String CART_TYPE_IRRIGATION = "IRR";
	public static final String PRODUCT_TYPE_IRRIGATION = "Irrigation";

 	//Preference Center attributes
 	public static final String GRANT_TYPE = "grant_type";
 	public static final String REFRESH_TOKEN = "refresh_token";
 	public static final String CLIENT_ID = "client_id";
 	public static final String CLIENT_SECRET = "client_secret";


	public static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";

	public static final String SEPARATOR_UNDERSCORE = "_";
 	
	//Promotion Feed Constants
	public static final String PROMOTION_FEED_TARGET_LOCATION = "promotion.feed.target.location";
	
	//Product Feed Constants
	public static final String PRODUCT_FEED_TARGET_LOCATION = "product.feed.target.location";
	public static final String ALGONOMY_FEED_TARGET_LOCATION = "algonomy.feed.target.location";
	public static final String WEBSITE_SITEONE_SERVER="website.siteone.url";
	public static final String NURSERY_INVENTORY_FEED_TARGET_LOCATION = "nursery.feed.target.location";
	public static final String FULL_PRODUCT_FEED_TARGET_LOCATION = "fullproduct.feed.target.location";
	public static final String NURSERY_STORE  = "nursery.store.speciality";
	
	//First Time User Constants
	public static final String FIRST_TIME_USER_TARGET_LOCATION = "firsttimecustomer.target.location";

	public static final String TOKEN_VALIDITY_FORGOT_PASSWORD = "token.expiration.forgotpassword.time";
	public static final String TOKEN_VALIDITY_CREATE_PASSWORD = "token.expiration.createpassword.time";
	public static final String TOKEN_VALIDITY_UNLOCK_USER = "token.expiration.unlockuser.time";

	//Promotion Types
	public static final String PRODUCT_PERCENTAGE_DISCOUNT = "product_percentage_discount";
	public static final String PRODUCT_FIXED_PRICE = "product_fixed_price";
	public static final String PRODUCT_FIXED_DISCOUNT = "product_fixed_discount";
	public static final String TARGET_CUSTOMER_PERCENTAGE_DISCOUNT_CART = "target_customer_percentage_discount_cart";
	public static final String PRODUCT_BUY_X_GET_Y_FREE = "product_buy_x_get_y_free";
	public static final String PRODUCT_PERFECT_PARTNER_FIXED_PRICE = "product_perfect_partner_fixed_price";
	public static final String PRODUCT_MULTIBUY_FIXED_PRICE = "product_multibuy_fixed_price";
	public static final String PRODUCT_BUY_X_OF_A_GET_Y_OF_B_FREE = "product_buy_x_of_A_get_y_of_B_free";
	public static final String COUPON_CODE_PERCENTAGE_DISCOUNT_ON_CART = "coupon_code_percentage_discount_on_cart";
	public static final String COUPON_CODE_PERCENTAGE_DISCOUNT_ON_PRODUCTS = "coupon_code_percentage_discount_on_products";


   //Coupon Type ID
	public static final String PRODUCT_PERCENTAGE_DISCOUNT_ID = "product.percentagediscount.id";
	public static final String PRODUCT_FIXED_PRICE_ID = "product.fixedprice.id";
	public static final String PRODUCT_FIXED_DISCOUNT_ID = "product.fixeddiscount.id";
	public static final String TARGET_CUSTOMER_PERCENTAGE_DISCOUNT_CART_ID = "target.customerpercentagediscountcart.id";
	public static final String PRODUCT_BUY_X_GET_Y_FREE_ID = "product.buyxgetyfree.id";
	public static final String PRODUCT_PERFECT_PARTNER_FIXED_PRICE_ID = "product.perfectpartnerfixedprice.id";
	public static final String PRODUCT_MULTIBUY_FIXED_PRICE_ID = "product.multibuyfixedprice.id";
	public static final String PRODUCT_BUY_X_OF_A_GET_Y_OF_B_FREE_ID = "product.buyxofagetyofbfree.id";
	public static final String COUPON_CODE_PERCENTAGE_DISCOUNT_ON_CART_ID = "couponcode.percentagediscountoncart.id";
	public static final String COUPON_CODE_PERCENTAGE_DISCOUNT_ON_PRODUCTS_ID = "couponcode.percentagediscountonproducts.id";

	//submit order
	public static final String ORDER_SUBMIT_SERVICE_NAME = "ORDER_SUBMIT_SERVICE";
	public static final String ORDER_SUBMIT_URL_KEY = "ue.order.submit.url";
	public static final String ORDER_SUBMIT_NEW_URL_KEY = "ue.new.order.submit.url";
	
	//Ewallet Service Call
	public static final String EWALLET_SERVICE_NAME = "EWALLET_SERVICE";
	public static final String EWALLET_URL_KEY = "ue.ewallet.url";
	public static final String EWALLET_NEW_URL_KEY = "ue.new.ewallet.url";

	//Conditions
	public static final String PROMOTION_COND_DEF_Y_GROUP = "y_group";
	public static final String PROMOTION_COND_DEF_Y_QUALIFYING_PRODUCTS = "y_qualifying_products";
	public static final String PROMOTION_COND_DEF_Y_TARGET_CUSTOMERS = "y_target_customers";
	public static final String PROMOTION_COND_DEF_Y_QUALIFYING_COUPONS = "y_qualifying_coupons";
	public static final String PROMOTION_COND_DEF_Y_QUALIFYING_CAT = "y_qualifying_categories";
	public static final String PROMOTION_COND_DEF_Y_CONTAINER = "y_container";


	public static final String PROMOTION_COND_DEF_CONTAINER_X = "CONTAINER_X";
	public static final String PROMOTION_COND_DEF_CONTAINER_Y = "CONTAINER_Y";


	//Actions
	public static final String PROMOTION_ACT_DEF_Y_ORDER_PERCENTAGE_DISCOUNT = "y_order_percentage_discount";
	public static final String PROMOTION_ACT_DEF_Y_ORDER_ENTRY_PERCENTAGE_DISCOUNT = "y_order_entry_percentage_discount";
	public static final String PROMOTION_ACT_DEF_Y_ORDER_ENTRY_FIXED_PRICE = "y_order_entry_fixed_price";
	public static final String PROMOTION_ACT_DEF_Y_ORDER_ENTRY_FIXED_DISCOUNT = "y_order_entry_fixed_discount";
	public static final String PROMOTION_ACT_DEF_Y_TARGET_BUNDLE_PRICE = "y_target_bundle_price";
	public static final String PROMOTION_ACT_DEF_Y_PARTNER_ORDER_ENTRY_PERCENTAGE_DISCOUNT = "y_partner_order_entry_percentage_discount";
	public static final String PROMOTION_ACT_DEF_Y_PARTNER_ORDER_ENTRY_FIXED_PRICE = "y_partner_order_entry_fixed_price";

	public static final String PROMOTION_ACT_DEF_QUALIFYING_CONTAINERS = "qualifying_containers";
	public static final String PROMOTION_ACT_DEF_TARGET_CONTAINERS = "target_containers";


	//operations
	public static final String PROMOTION_COND_PARM_OPERATOR_CONTAINS_ANY = "CONTAINS_ANY";
	public static final String PROMOTION_COND_PARM_OPERATOR_CONTAINS_All = "CONTAINS_ALL";
	public static final String PROMOTION_COND_PARM_OPERATOR_DOES_NOT_CONTAIN = "NOT_CONTAINS";
	public static final String PROMOTION_COND_PARM_OPERATOR_GREATER_THAN_OR_EQUAL = "GREATER_THAN_OR_EQUAL";
	public static final String PROMOTION_COND_PARM_OPERATOR_LESS_THAN_OR_EQUAL = "LESS_THAN_OR_EQUAL";
	public static final String PROMOTION_COND_PARM_OPERATOR_GREATER_THAN = "GREATER_THAN";
	public static final String PROMOTION_COND_PARM_OPERATOR_LESS_THAN = "LESS_THAN";

	//Partner Program SSO
	public static final String PARTNER_PROGRAM_KEY = "totalpro.api.key";
	public static final String PARTNERPROGRAM_PILOT_KEY = "partnerprogram.pilot.key";


	//Vertex
	public static final String TAX_REQ_TRANSACTIONID = "N/A";
	public static final String TAX_REQ_COMPANY = "001";
	public static final String TAX_REQ_COMPANY_CA = "002";
	public static final String TAX_REQ_DIVISION_US = "JDL";
	public static final String TAX_REQ_DIVISION_CA = "JDLCA";

	public static final String USER_TYPE_ADMIN = "ADMIN";
	public static final String USER_TYPE_USER = "USER";

	//price
	public static final String PRICE_SERVICE_MARGIN_FLOOR = "MarginFloor";
	public static final String PRICE_SERVICE_REPLACEMENT_COST = "ReplacementCost";
	public static final String PRICE_SERVICE_DELIVERY_MODE = "DlvMode";
	public static final String PRICE_SERVICE_DELIVERY_MODE_VALUE = "1";
	public static final String PRICE_SERVICE_NURSERY = "NURSERY";
	public static final String PRICE_SERVICE_NURSERY_VALUE = "0.00000;9.99999;0.05|10.00000;999999999.99998;0.5";

	public static final String CUSTOMER_DATA_REPORT_TARGET_LOCATION = "customer.data.report.target.location";

	public static final String OKTA_HYBRIS_GROUPID = "okta.hybris.groupid";
	public static final String OKTA_HYBRIS_CA_GROUPID = "okta.hybris.ca.groupid";

	public static final String QUOTE_DISCOUNT_CODE = "QuoteDiscount";

	public static final String UE_INVOICE_KEY="ue.invoice.url";
	public static final String UE_INVOICE_VERIFICATION_KEY="ue.invoice.verification.key";
	public static final String UE_ORDER_VERIFICATION_KEY="ue.order.verification.key";
	public static final String UE_PDF_VERIFICATION_KEY="ue.sdspdf.verification.key";
	public static final String UE_SDSPDF_URL="ue.sdspdf.url";
	public static final String UE_SDSPDF_URL_OLD="ue.sdspdf.url.old";
	public static final String UE_ORDER =  "Sales Order";

	public static final String BRITEVERIFY_API_VERIFICATION_KEY =  "briteverify.api";
	public static final String BRITEVERIFY_API_VERIFICATION_URL =  "briteverify.url";

	public static final String OKTA_RATE_LIMIT_REMAINDER_THRESOLD_VALUE =  "okta.rate.limit.remainder.threshold.value";
	public static final String OKTA_SERVICE_CALL_DELAY_MS =  "okta.service.call.delay.ms";

	public static final int FLEXIBLE_FIELD_DELIVERY_METHOD_FIELD_ID = 1;
	public static final int FLEXIBLE_FIELD_ORDER_TYPE_FIELD_ID = 2;
	public static final int FLEXIBLE_FIELD_ORDER_NUMBER_FIELD_ID = 3;
	public static final int FLEXIBLE_FIELD_UOM_FIELD_ID = 5;
	
	public static final String BASESITE_CA = "siteone-ca";
	public static final String BASESITE_US = "siteone-us";

	//Audit Log Export Constants
	public static final String AUDITLOG_EXPORT_TARGET_LOCATION = "audit.export.target.location";
	public static final String UE_DEFAULT_ORDERTYPE ="Pick-up";
	public static final Map<String, String> UE_ORDERTYPES = new HashMap<String, String>() {{
		put("PICKUP","Pick-up");
		put("DELIVERY","Store Delivery");
		put("DIRECT_SHIP","Direct ship");
		put("FUTURE_PICKUP","Future Pick-up");
		put("STORE_DELIVERY","Store Delivery");
		put("PARCEL_SHIPPING","shipping");
		put("STANDARD-NET","delivery");
		put("FREE-STANDARD-SHIPPING","shipping");
	}};
	public static final Map<String, Integer> UE_INVOICESORTTYPES = new HashMap<String, Integer>() {{
		put("InvoiceDate",0);
		put("InvoiceNumber",1);
		put("OrderNumber",2);
		put("PONumber",3);
		put("InvoiceTotal",4);
	}};
	public static final Map<String, Integer> UE_INVOICESORTDIRECTION = new HashMap<String, Integer>() {{
		put("Descending",0);
		put("Ascending ",1);
	}};
	public static final Map<String, String> cardTypeShortName = new HashMap<String, String>() {{
		put("Unknown","Unknown");
		put("American Express","AX");
		put("Discover","DS");
		put("Mastercard","MC");
		put("MasterCard","MC");
		put("Visa","VS");
		put("Debit","DB");
		put("EBT","EBT");
		put("Wright Express (Fleet Card)","Wright Express");
		put("Voyager (Fleet Card / USBank Issued)","Voyager");
		put("JCB","JCB");
		put("China Union Pay","China Union Pay");
		put("LevelUp","LevelUp");
	}};
    
    public static final String PRICE_SERVICE_INVENTSITEID = "INVENTSITEID";
    public static final String MULTIPLE_INVENTSITEID_LABEL = "MULTIPLE_INVENTSITEID";
    public static final String MULTIPLE_INVENTSITEID_VALUE = "1";
    public static final String SALES_UNIT = "SalesUnit";

	public static final String CLIENT_CREDENTIALS = "client_credentials";
	
	public static final String ORDERTYPE_PICKUP = "Pickup";
	public static final String ORDERTYPE_DELIVERY = "Delivery";
	public static final String ORDERTYPE_FUTURE_PICKUP = "Future_Pickup";
	public static final String ORDERTYPE_STORE_DELIVERY = "Store_delivery";
	public static final String ORDERTYPE_DIRECT_SHIP = "DIRECT_SHIP";
	public static final String ORDERTYPE_PARCELSHIPPING  = "PARCEL_SHIPPING";
	public static final String ORDERTYPE_SHIPPING  = "shipping";
	public static final String EXTERNAL_SYSTEM_ID= "2";
	public static final String EXTERNAL_CA_SYSTEM_ID ="20";
	
	public static final String NOTIFICATIONHUB_ENDPOINT = "azure.notificationhub.connectionstring";
	public static final String NOTIFICATIONHUB_NAME = "azure.notificationhub.name";
	
	//Scan Product Service Call
	public static final String SCAN_PRODUCT_SERVICE_NAME = "SCAN_PRODUCT_SERVICE";
	public static final String SCAN_PRODUCT_SERVICE_URL = "boomi.scan.product.url";
	public static final String SCAN_PRODUCT_SERVICE_KEY_NAME = "x-api-key";
	public static final String SCAN_PRODUCT_SERVICE_KEY = "boomi.scan.product.api.key";
	public static final String SCAN_PRODUCT_SERVICE_RETRY = "scan.product.api.maxRetryCount";
	
	//Invoice Data Service Call
	public static final String INVOICE_SERVICE_NAME = "INVOICE_DATA_SERVICE";
	public static final String INVOICE_SERVICE_URL = "ue.invoice.data.url";
	public static final String INVOICE_SERVICE_KEY_NAME = "x-api-key";
	public static final String INVOICE_SERVICE_KEY = "ue.invoice.data.api.key";
	public static final String INVOICE_NEW_SERVICE_URL = "ue.new.invoice.data.url";
	public static final String INVOICE_NEW_SERVICE_KEY = "boomi.invoice.api.newkey";
	public static final String QUERY_PARAM_CUSTOMER_NUMBER= ":customerNumber";
	public static final String QUERY_PARAM_DIVISIONID= ":divisionID";
	//Invoice detail Data Service Call
		public static final String INVOICE_DETAIL_SERVICE_NAME = "INVOICE_DETAIL_DATA_SERVICE";
		public static final String INVOICE_DETAIL_SERVICE_URL = "ue.new.invoice.detail.url";
		public static final String INVOICE_DETAIL_SERVICE_KEY_NAME = "x-api-key";
		public static final String INVOICE_DETAIL_SERVICE_KEY = "ue.invoice.detail.data.api.key";
		public static final String INVOICE_DETAIL_NEW_SERVICE_URL = "ue.new.invoice.detail.data.url";
		public static final String INVOICE_DETAIL_NEW_SERVICE_KEY = "boomi.invoice.detail.api.newkey";
		public static final String QUERY_PARAM_INVOICEID= ":invoiceId";

//	Scan Product Service New Boomi Call
	public static final String SCAN_PRODUCT_NEW_SERVICE_URL = "boomi.new.scan.product.url";
	public static final String SCAN_PRODUCT_NEW_SERVICE_KEY = "boomi.scan.product.api.newkey";
	
	// Analytics UE and Hybris order number mapping location
	public static final String ANALYTICS_ORDER_NUMBER_MAPPING_LOCATION = "analytics.order.number.mapping.report.target.location";
	
	public static final String CONTENT_POSITION_SECTIONS1 = "SectionS1";
	public static final String CONTENT_POSITION_GLOBALMESSAGE = "GlobalMessage";
	public static final String ROTATINGBANNER = "rotatingBanner";
	public static final String BANNERLIST = "bannerList";
	public static final String ROTATINGBANNERDETAILS = "rotatingBannerDetails";
		
	public static final String DELIVERYMODE_PICKUP = "pickup";
	public static final String DELIVERYMODE_DELIVERY = "standard-net";
	public static final String DELIVERYMODE_SHIPPING = "free-standard-shipping";
	
	//New Boomi Environment key
	public static final String X_API_HEADER =  "X-API-Key";
	public static final String X_API_KEY_CUSTOMER=  "boomi.xApi.key";
	public static final String X_API_KEY_PRICE =  "boomi.xApi.key.price";
	public static final String X_API_KEY_LOYALTY =  "boomi.xApi.key.loyalty";
    public static final String X_API_KEY_QUOTES = "boomi.xApi.key.quotes";
    public static final String X_API_KEY_D365 =  "d365.xApi.key.price";
    
  //Agro AI 
  	public static final String AGRO_AI_API="agro.ai.api";
  	public static final String OAUTH_ACCESS_TOKEN = "oauthAccessToken";
  	public static final String OAUTH_REFRESH_TOKEN = "oauthRefreshToken";
  	public static final String USER_NAME="username";
  	public static final String PASSWORD="password";
	
	
	//Sales Feed Constant
	public static final String SALES_FEED_TARGET_LOCATION = "sales.feed.target.location";
	public static final String SALES_FEED_CONTAINER_NAMWE = "azure.hotfolder.storage.container.name";
	public static final String ARCHIVE_SALES_FEED_TARGET_LOCATION = "sales.feed.archive.location";
	
	//Google Translation
  	public static final String GOOGLE_TRANSLATE_SERVICE_NAME = "GOOGLE_TRANSLATE_SERVICE";
  	public static final String GOOGLE_TRANSLATE_API_KEY = "google.translate.api.key";
  	public static final String GOOGLE_TRANSLATE_SERVICE_URL = "google.translate.service.url";
  	public static final String GOOGLE_TRANSLATE_ENABLED = "google.translate.enabled";

}

