/**
 *
 */
package com.siteone.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.util.Config;

import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.siteone.core.customer.SiteOneCustomerAccountService;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.QuoteToOrderStatusProcessModel;
import com.siteone.integration.open.order.data.OpenOrdersInfoResponseData;
import com.siteone.integration.open.order.data.OpenOrdersLandingPageRequestData;
import com.siteone.integration.open.order.data.OpenOrdersLandingPageResponseData;
import com.siteone.integration.open.order.data.OpenOrdersShipmentInfoResponseData;


/**
 *
 */
public class QuoteToOrderStatusEmailContext extends AbstractEmailContext<QuoteToOrderStatusProcessModel>
{
	private static final Logger LOG = Logger.getLogger(QuoteToOrderStatusEmailContext.class);

	public static final String EMAIL_SUBJECT = "subject";
	private static final String QUOTE_NUMBER = "quoteNumber";
	private static final String ACCOUNTMANAGER_NAME = "accountManagerName";
	private static final String ACCOUNTMANAGER_PHONE = "accountManagerPhone";
	private static final String ACCOUNTMANAGER_EMAIL = "accountManagerEmail";
	private static final String JOB_NAME = "jobName";
	private static final String ORDER_NUMBER = "orderNumber";
	private static final String PO_NUMBER = "poNumber";
	private static final String QUOTE_WRITER = "quoteWriter";
	private static final String ORDER_DATE = "orderDate";
	private static final String ACCOUNT_ADMIN_EMAIL = "accountAdminEmail";
	private static final String COUNTRY_BASESITE_ID = "countryBaseSiteID";
	private static final String SHIPMENT_NUMBER = "shipmentNumber";
	private static final String ACCOUNT_NUMBER = "accountNumber";
	private static final String BRANCH_NUMBER = "branchNumber";
	private static final String SHIPMENT_COUNT = "shipmentCount";
	private static final String FIRST_NAME = "firstName";

	private static final String BOOMI_PLATFORM = "boomiPlatformLinux";

	@Resource(name = "customerAccountService")
	private CustomerAccountService customerAccountService;

	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;

	@Override
	public void init(final QuoteToOrderStatusProcessModel businessProcessModel, final EmailPageModel emailPageModel)
	{
		final BaseSiteModel baseSite = getSite(businessProcessModel);
		if (baseSite == null)
		{
			LOG.error("Failed to lookup Site for BusinessProcess [" + businessProcessModel + "]");
		}

		else
		{
			put(BASE_SITE, baseSite);
			// Lookup the site specific URLs
			put(BASE_URL, getSiteBaseUrlResolutionService().getWebsiteUrlForSite(baseSite, getUrlEncodingAttributes(), false, ""));
			put(BASE_THEME_URL, getSiteBaseUrlResolutionService().getWebsiteUrlForSite(baseSite, false, ""));
			put(SECURE_BASE_URL,
					getSiteBaseUrlResolutionService().getWebsiteUrlForSite(baseSite, getUrlEncodingAttributes(), true, ""));
			put(MEDIA_BASE_URL, getSiteBaseUrlResolutionService().getMediaUrlForSite(baseSite, false));
			put(MEDIA_SECURE_BASE_URL, getSiteBaseUrlResolutionService().getMediaUrlForSite(baseSite, true));

			put(THEME, baseSite.getTheme() != null ? baseSite.getTheme().getCode() : null);
		}

		put(FROM_EMAIL, emailPageModel.getFromEmail());
		put(FROM_DISPLAY_NAME, emailPageModel.getFromName());
		final LanguageModel language = businessProcessModel.getLanguage();
		LOG.info(businessProcessModel.getLanguage().getIsocode());
		if (language != null)
		{
			put(EMAIL_LANGUAGE, language);
		}
		OpenOrdersLandingPageResponseData orderResponse = null;
		String shipmentNumber = null;
		String branchNumber = null;
		int shipmentCount = 0;
		final OpenOrdersLandingPageRequestData openOrderRequest = new OpenOrdersLandingPageRequestData();

		openOrderRequest.setSortBy(Integer.valueOf("0"));
		openOrderRequest.setSearchPeriod(Integer.valueOf("30"));
		openOrderRequest.setSortDirection(Integer.valueOf("0"));
		openOrderRequest.setPage(Integer.valueOf("1"));
		openOrderRequest.setRows(Integer.valueOf("5"));
		openOrderRequest.setIncludeMobileProOrders(Boolean.TRUE);

		if (StringUtils.isNotBlank(businessProcessModel.getAccountNumber()))
		{
			orderResponse = ((SiteOneCustomerAccountService) customerAccountService).getOpenOrderList(openOrderRequest,
					businessProcessModel.getAccountNumber().trim().split("_")[0], Integer.valueOf(1),
					Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));

		}
		if (orderResponse != null && CollectionUtils.isNotEmpty(orderResponse.getData()))
		{
			for (final OpenOrdersInfoResponseData data : orderResponse.getData())
			{
				
				if (businessProcessModel.getOrderNumber() != null && data.getOrderNumber() != null
						&& data.getOrderNumber().equalsIgnoreCase(businessProcessModel.getOrderNumber()))
				{
					if (data.getBranchNumber() != null)
					{
						branchNumber = data.getBranchNumber();
					}

					if (CollectionUtils.isNotEmpty(data.getShipments()))
					{
						shipmentCount = data.getShipments().size();
						final OpenOrdersShipmentInfoResponseData shipment = data.getShipments().get(0);

						if (shipment.getShipmentNumber() != null)
						{
							shipmentNumber = shipment.getShipmentNumber();
						}
					}
				}
			}
		}
		if (shipmentNumber == null)
		{
			LOG.warn("Shipment Number is not found in the order Api response");
		}
		
		put(FIRST_NAME, businessProcessModel.getFirstName());
		put(SHIPMENT_NUMBER, shipmentNumber);
		put(ACCOUNT_NUMBER, businessProcessModel.getAccountNumber());
		put(BRANCH_NUMBER, branchNumber);
		put(SHIPMENT_COUNT, Integer.valueOf(shipmentCount));
		put(EMAIL, businessProcessModel.getToMails());
		put(DISPLAY_NAME, Config.getString("siteone.support.email", null));
		put(QUOTE_NUMBER, businessProcessModel.getQuoteNumber());
		put(ACCOUNTMANAGER_NAME, businessProcessModel.getAccountManager());
		put(ACCOUNTMANAGER_EMAIL, businessProcessModel.getAccountManagerEmail());
		put(ACCOUNTMANAGER_PHONE, businessProcessModel.getAccountManagerPhone());
		put(JOB_NAME, businessProcessModel.getJobName());
		put(ACCOUNT_ADMIN_EMAIL, businessProcessModel.getAccountAdminEmail());
		put(PO_NUMBER, businessProcessModel.getPoNumber());
		put(ORDER_NUMBER, businessProcessModel.getOrderNumber());
		put(QUOTE_WRITER, businessProcessModel.getQuoteWriter());
		Date now = new Date();
		put(ORDER_DATE, new SimpleDateFormat("MMMM dd, YYYY").format(now));
		put(COUNTRY_BASESITE_ID, baseSite.getUid());
	}

	@Override
	protected BaseSiteModel getSite(final QuoteToOrderStatusProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getSite();
	}


	@Override
	protected CustomerModel getCustomer(final QuoteToOrderStatusProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return null;
	}


	@Override
	protected LanguageModel getEmailLanguage(final QuoteToOrderStatusProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
	}
}
