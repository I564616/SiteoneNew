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
package com.siteone.core.constants;

import java.util.Arrays;
import java.util.List;


/**
 * Global class for all SiteoneCore constants. You can add global constants for your extension into this class.
 */
public final class SiteoneCoreConstants extends GeneratedSiteoneCoreConstants
{
	public static final String EXTENSIONNAME = "siteonecore";


	private SiteoneCoreConstants()
	{
		//empty
	}

	// implement here constants used by this extension
	public static final String QUOTE_BUYER_PROCESS = "quote-buyer-process";
	public static final String QUOTE_SALES_REP_PROCESS = "quote-salesrep-process";
	public static final String QUOTE_USER_TYPE = "QUOTE_USER_TYPE";
	public static final String QUOTE_SELLER_APPROVER_PROCESS = "quote-seller-approval-process";
	public static final String US_ISO_CODE = "US";
	public static final String CURRENCY_CODE_CA = "USD";
	public static final String ADDRESS_VERIFICATION_NULL_RESPONSE_CODE = "404";
	public static final String OKTA_USER_STATUS_ACTIVE = "ACTIVE";
	public static final String OKTA_USER_STATUS_PROVISIONED = "PROVISIONED";
	public static final String OKTA_USER_STATUS_SUSPENDED = "SUSPENDED";
	public static final String OKTA_USER_STATUS_STAGED = "STAGED";
	public static final String OKTA_USER_STATUS_LOCKED_OUT = "LOCKED_OUT";
	public static final String PARTNERPROGRAM_REDIRECT_URL = "partnerprogram.redirect.url";
	public static final String PARTNERPROGRAM_PILOT_REDIRECT_URL = "partnerprogram.pilot.redirect.url";
	public static final String PARTNERPROGRAM_PILOT_KEY = "partnerprogram.pilot.key";
	public static final String UNITED_STATES_CODE = "UNITED STATES";
	public static final String AMERICA_CODE = "AMERICA";
	public static final String UNITED_STATES_AMERICA_CODE = "UNITED STATES OF AMERICA";
	public static final String CANADA_CODE = "CANADA";
	public static final String COMMA = ",";
	public static final String SEPARATOR_UNDERSCORE = "_";
	public static final String DASH = "-";


	//UE order status
	public static final String OPEN = "Open";
	public static final String PROCESSING = "Processing";
	public static final String READY_FOR_PICKUP = "Ready for Pickup";
	public static final String READY_FOR_DELIVERY = "Ready for Delivery";
	public static final String INVOICED = "Invoiced";
	public static final String CANCELLED = "Cancelled";
	public static final String CLOSED = "Closed";
	public static final String SHIPPED = "Shipped";
	public static final String CA_ISO_CODE = "CA";
	public static final String SITEONE_US_BASESTORE = "siteone";
	public static final String SITEONE_CA_BASESTORE = "siteoneCA";
	public static final String SCHEDULED_FOR_DELIVERY = "Scheduled for delivery";
	public static final String RESPONSE_SUCCESS = "SUCCESS";
	public static final String RESPONSE_FAILURE = "FAILURE";

	//TMS UE Consignment Status
	public static final String RECEIVED = "Received";
	public static final String SCHEDULED = "Scheduled";
	public static final String IN_TRANSIT = "In Transit";
	public static final String FINISHED = "Finished";
	public static final String COMPLETED = "COMPLETED";
	public static final String SUBMITTED = "SUBMITTED";
	public static final String NEW = "NEW";
	public static final String STARTED = "STARTED";

	public static final String PICKUP_PAGE = "Pick-up Contact Information";
	public static final String DELIVERY_PAGE = "Delivery Contact Information";
	public static final String SHIPPING_PAGE = "Shipping Contact Information";


	public static final String ORDERTYPE_PICKUP = "PICKUP";
	public static final String ORDERTYPE_PICKUP_HOME = "PICKUPHOME";
	public static final String ORDERTYPE_PICKUP_NEARBY = "PICKUPNEARBY";
	public static final String ORDERTYPE_DELIVERY = "DELIVERY";
	public static final String ORDERTYPE_SHIPPING = "PARCEL_SHIPPING";
	public static final String ORDERTYPE_SHIPPING_NAME = "SHIPPING";
	public static final String ORDERTYPE_FUTURE_PICKUP = "FUTURE_PICKUP";
	public static final String ORDERTYPE_STORE_DELIVERY = "STORE_DELIVERY";
	public static final String ORDERTYPE_DIRECT_SHIP = "DIRECT_SHIP";
	public static final String ORDERTYPE_MULTIPLE_DELIVERIES = "MULTIPLE_DELIVERIES";

	public static final String DELIVERYMODE_PICKUP = "pickup";
	public static final String DELIVERYMODE_DELIVERY = "standard-net";
	public static final String DELIVERYMODE_SHIPPING = "free-standard-shipping";
	public static final String HUBSTORE_SHIPPING_FEE_BRANCHES = "HubStoreShippingFeeBranches";
	public static final String HUB_SHIPPING_THRESHOLD = "HubShippingThreshold";
	public static final String DELIVERY_COST_THRESHOLD_BRANCEHES = "BranchesDeliveryThreshold";
	public static final String ITEM_LEVEL_SHIPPING_FEE_BRANCHES = "ItemLevelShippingFeeBranches";

	public static final String ENABLE_BO_LOGIC_P3 = "EnableP3BackorderLogic";
	public static final String ENABLE_BO_LOGIC_P2 = "EnableP2BackorderLogic";
	
	public static final String NURSERY_BACKORDER_LOGIC = "NurseryBackorderLogic";

	public static final String OKTAUSERSTATUS_KEY = "OKTAUSERSTATUS";

	public static final String OKTAUSER_UNAUTHORIZED = "UNAUTHORIZED";
	public static final String OKTAUSER_LOCKED_OUT = "LOCKED_OUT";
	public static final String OKTA_UNAVAILABLE = "UNAVAILABLE";

	public static final String OKTA_SESSION_TOKEN = "oktaSessionToken";
	public static final String OKTA_ISBT_APP = "isBTApp";
	public static final String OKTA_ISPS_APP = "isProjectServicesApp";
	public static final String OKTA_NXTLevel_APP = "isNextLevelApp";
	public static final String OKTA_API_CREATE_USER_STATUS = "STAGED";

	public static final String REQUEST_ACCOUNT_FORM_CONTRACTOR = "Contractor";
	public static final List<String> REQUEST_ACCOUNT_CONTR_EMP_COUNT = Arrays.asList("0-2", "3-9", "10+");

	public static final List<String> PRODUCT_VARIANT_WITH_UNITS_CATEGORY_NAME = Arrays.asList("Size", "Coil Length",
			"Emitter Spacing", "Flow Rate", "Container Size", "Dimensions", "Height", "Grate Size", "length", "Diameter");

	//	Kount service constants
	public static final String INQUIRY_RESPONSE_SESSION_ATTRIBUTE = "kountInquiryResponse";
	public static final String KOUNT_SESSION_ID_ATTRIBUTE = "kountSessionId";
	public static final String KOUNT_AUTO_APPROVED_STATUS_CODE = "A";
	public static final String CAYAN_PAYMENT_APPROVED_STATUS = "APPROVED";
	public static final String MERCHANT_ID = "payments.fraud.client.id";
	public static final String CONTRACTOR_SITE_ID = "kount.contractor.site.id";
	public static final String RETAIL_SITE_ID = "kount.retail.site.id";
	public static final String GUEST_SITE_ID = "kount.guest.site.id";
	public static final String SITE_ID = "kount.site.id";
	public static final String LINK_TO_PAY_SITE_ID = "kount.link.to.pay.site.id";
	public static final String API_URL = "payments.fraud.api.endpoint";
	public static final String API_KEY = "payments.fraud.api.key";
	public static final String KOUNT_CONGIF_KEY = "kount.config.key";
	public static final String MIGRATION_MODE_ENABLED = "migration.mode.enabled";
	public static final String AUTH_URL = "payments.fraud.auth.endpoint";
	
	public static final String NEARBY_SESSION_STORES = "sessionNearbyStores";
	public static final String EXTENDED_NEARBY_SESSION_STORES = "sessionExtendedNearbyStores";
	public static final String NURSERY_NEARBY_SESSION_STORES = "sessionNurseryNearbyStores";
	public static final String INSTOCK_FILTER_SELECTED = "instockFilterSelected";

	public static final List<String> BUYITAGAIN_FILTER = Arrays.asList("Past 30 Days", "Past 60 Days", "Past 90 Days",
			"Past 6 months", "Past Year");
	public static final String PICKUP_DELIVERYMODE_CODE = "pickup";
	public static final String DELIVERY_DELIVERYMODE_CODE = "standard-net";
	public static final String SHIPPING_DELIVERYMODE_CODE = "free-standard-shipping";

	public static final String CATALOG_VERSION = "Online";
	public static final String CATALOG_US = "siteoneProductCatalog";
	public static final String CATALOG_CA = "siteoneCAProductCatalog";
	public static final String PARENT_CONTENT_CATALOG = "siteoneContentCatalog";
	public static final String CONTENT_CATALOG_US = "siteoneUSContentCatalog";
	public static final String CONTENT_CATALOG_CA = "siteoneCAContentCatalog";
	public static final String INDEX_OF_US = "_US";
	public static final String INDEX_OF_CA = "_CA";
	public static final String BASESITE_CA = "siteone-ca";
	public static final String BASESITE_US = "siteone-us";
	public static final double TAX_ZERO = 0.0;
	public static final String GST_HST = "GST/HST";
	public static final String GST = "GST";
	public static final String HST = "HST";
	public static final String PST = "PST";
	public static final String OTHER = "other";
	public static final String ALGONOMYRECOMMFLAG_CA = "AlgonomyCAProductRecommendationEnable";
	public static final String BRAND_BOOST_VALUE = "brand.boost.value";
	public static final float TIE_VALUE = (float) 0.1;
}
