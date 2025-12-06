/**
 *
 */
package com.siteone.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.util.Config;

import org.apache.log4j.Logger;

import com.siteone.core.model.PointsForEquipmentProcessModel;


/**
 * @author 1003567
 *
 */
public class PointsForEquipmentEmailContext extends AbstractEmailContext<PointsForEquipmentProcessModel>
{
	private static final Logger LOG = Logger.getLogger(PointsForEquipmentEmailContext.class);
	public static final String EMAIL_SUBJECT = "subject";
	private static final String DEALER_CONTACT_NAME = "dealerContactName";
	private static final String DEALER_NAME = "dealerName";
	private static final String DEALER_ADDRESS_LINE1 = "dealerAddressLine1";
	private static final String DEALER_ADDRESS_LINE2 = "dealerAddressLine2";
	private static final String DEALER_CITY = "dealerCity";
	private static final String DEALER_STATE = "dealerState";
	private static final String DEALER_ZIP_CODE = "dealerZipCode";
	private static final String CUSTOMER_CONTACT_NAME = "customerContactName";
	private static final String COMPANY_NAME = "companyName";
	private static final String JDL_ACCOUNT_NUMBER = "jdlAccountNumber";
	private static final String CUSTOMER_ADDRESS_LINE1 = "customerAddressLine1";
	private static final String CUSTOMER_ADDRESS_LINE2 = "customerAddressLine2";
	private static final String CUSTOMER_CITY = "customerCity";
	private static final String CUSTOMER_STATE = "customerState";
	private static final String CUSTOMER_ZIP_CODE = "customerZipCode";
	private static final String EMAIL_ADDRESS = "emailAddress";
	private static final String PHONE_NUMBER = "phoneNumber";
	private static final String FAX_NUMBER = "faxNumber";
	private static final String DATE_OF_PUR_PRODUCT1 = "dateOfPurProduct1";
	private static final String DATE_OF_PUR_PRODUCT2 = "dateOfPurProduct2";
	private static final String DATE_OF_PUR_PRODUCT3 = "dateOfPurProduct3";
	private static final String DATE_OF_PUR_PRODUCT4 = "dateOfPurProduct4";
	private static final String DATE_OF_PUR_PRODUCT5 = "dateOfPurProduct5";
	private static final String ITEM_DESC_PRODUCT1 = "itemDescProduct1";
	private static final String ITEM_DESC_PRODUCT2 = "itemDescProduct2";
	private static final String ITEM_DESC_PRODUCT3 = "itemDescProduct3";
	private static final String ITEM_DESC_PRODUCT4 = "itemDescProduct4";
	private static final String ITEM_DESC_PRODUCT5 = "itemDescProduct5";
	private static final String SERIAL_NUMBER_PRODUCT1 = "serialNumberProduct1";
	private static final String SERIAL_NUMBER_PRODUCT2 = "serialNumberProduct2";
	private static final String SERIAL_NUMBER_PRODUCT3 = "serialNumberProduct3";
	private static final String SERIAL_NUMBER_PRODUCT4 = "serialNumberProduct4";
	private static final String SERIAL_NUMBER_PRODUCT5 = "serialNumberProduct5";
	private static final String INVOICE_COST_PRODUCT1 = "invoiceCostProduct1";
	private static final String INVOICE_COST_PRODUCT2 = "invoiceCostProduct2";
	private static final String INVOICE_COST_PRODUCT3 = "invoiceCostProduct3";
	private static final String INVOICE_COST_PRODUCT4 = "invoiceCostProduct4";
	private static final String INVOICE_COST_PRODUCT5 = "invoiceCostProduct5";


	@Override
	public void init(final PointsForEquipmentProcessModel businessProcessModel, final EmailPageModel emailPageModel)
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
		put(COMPANY_NAME, businessProcessModel.getCompanyName());
		put(DEALER_CONTACT_NAME, businessProcessModel.getDealerContactName());
		put(DEALER_NAME, businessProcessModel.getDealerName());
		put(DEALER_ADDRESS_LINE1, businessProcessModel.getDealerAddressLine1());
		put(DEALER_ADDRESS_LINE2, businessProcessModel.getDealerAddressLine2());
		put(DEALER_CITY, businessProcessModel.getDealerCity());
		put(DEALER_STATE, businessProcessModel.getDealerState());
		put(DEALER_ZIP_CODE, businessProcessModel.getDealerZipCode());
		put(CUSTOMER_CONTACT_NAME, businessProcessModel.getCustomerContactName());
		put(PHONE_NUMBER, businessProcessModel.getPhoneNum());
		put(EMAIL_ADDRESS, businessProcessModel.getCustEmailAddress());
		put(JDL_ACCOUNT_NUMBER, businessProcessModel.getJdlAccountNumber());
		put(CUSTOMER_ADDRESS_LINE1, businessProcessModel.getCustomerAddressLine1());
		put(CUSTOMER_ADDRESS_LINE2, businessProcessModel.getCustomerAddressLine2());
		put(CUSTOMER_CITY, businessProcessModel.getCustomerCity());
		put(CUSTOMER_STATE, businessProcessModel.getCustomerState());
		put(CUSTOMER_ZIP_CODE, businessProcessModel.getCustomerZipCode());
		put(FAX_NUMBER, businessProcessModel.getFaxNum());
		put(DATE_OF_PUR_PRODUCT1, businessProcessModel.getDateOfPurProduct1());
		put(DATE_OF_PUR_PRODUCT2, businessProcessModel.getDateOfPurProduct2());
		put(DATE_OF_PUR_PRODUCT3, businessProcessModel.getDateOfPurProduct3());
		put(DATE_OF_PUR_PRODUCT4, businessProcessModel.getDateOfPurProduct4());
		put(DATE_OF_PUR_PRODUCT5, businessProcessModel.getDateOfPurProduct5());
		put(ITEM_DESC_PRODUCT1, businessProcessModel.getItemDescProduct1());
		put(ITEM_DESC_PRODUCT2, businessProcessModel.getItemDescProduct2());
		put(ITEM_DESC_PRODUCT3, businessProcessModel.getItemDescProduct3());
		put(ITEM_DESC_PRODUCT4, businessProcessModel.getItemDescProduct4());
		put(ITEM_DESC_PRODUCT5, businessProcessModel.getItemDescProduct5());
		put(SERIAL_NUMBER_PRODUCT1, businessProcessModel.getSerialNumberProduct1());
		put(SERIAL_NUMBER_PRODUCT2, businessProcessModel.getSerialNumberProduct2());
		put(SERIAL_NUMBER_PRODUCT3, businessProcessModel.getSerialNumberProduct3());
		put(SERIAL_NUMBER_PRODUCT4, businessProcessModel.getSerialNumberProduct4());
		put(SERIAL_NUMBER_PRODUCT5, businessProcessModel.getSerialNumberProduct5());
		put(INVOICE_COST_PRODUCT1, businessProcessModel.getInvoiceCostProduct1());
		put(INVOICE_COST_PRODUCT2, businessProcessModel.getInvoiceCostProduct2());
		put(INVOICE_COST_PRODUCT3, businessProcessModel.getInvoiceCostProduct3());
		put(INVOICE_COST_PRODUCT4, businessProcessModel.getInvoiceCostProduct4());
		put(INVOICE_COST_PRODUCT5, businessProcessModel.getInvoiceCostProduct5());

		put(EMAIL, Config.getString("siteone.support.email", null));
		put(DISPLAY_NAME, Config.getString("siteone.support.email", null));
		put(EMAIL_SUBJECT, "Points For Equipment notification");
	}

	@Override
	protected BaseSiteModel getSite(final PointsForEquipmentProcessModel businessProcessModel)
	{
		return businessProcessModel.getSite();
	}

	@Override
	protected CustomerModel getCustomer(final PointsForEquipmentProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return null;
	}


	@Override
	protected LanguageModel getEmailLanguage(final PointsForEquipmentProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
	}

}
