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

import java.util.Calendar;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;

import com.siteone.core.model.RequestAccountProcessModel;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;




/**
 * @author 1197861
 *
 */
public class RequestAccountEmailContext extends AbstractEmailContext<RequestAccountProcessModel>
{

	private static final Logger LOG = Logger.getLogger(RequestAccountEmailContext.class);

	public static final String EMAIL_SUBJECT = "subject";
	private static final String COMPANY_NAME = "companyName";
	private static final String ACCOUNT_NUMBER = "accountNumber";
	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";
	private static final String ADDRESS_LINE1 = "addressLine1";
	private static final String ADDRESS_LINE2 = "addressLine2";
	private static final String CITY = "city";
	private static final String STATE = "state";
	private static final String ZIPCODE = "zipcode";
	private static final String PHONE_NUMBER = "phoneNumber";
	private static final String EMAIL_ADDRESS = "emailAddress";
	private static final String HAS_ACCOUNT_NUMBER = "hasAccountNumber";
	public static final String CUSTOMER_EMAIL = "customeremail";
	private static final String COPY_RIGHT_YEAR = "copyRightYear";
	private static final String TYPE_OF_CUSTOMER = "typeOfCustomer";
	private static final String YEARS_IN_BUSINESS = "yearsInBusiness";
	private static final String NUMBER_OF_EMPLOYEES = "numberOfEmployees";
	private static final String PRIMARY_BUSINESS = "primaryBusiness";
	private static final String LANGUAGE_PREFERENCE = "languagePreference";
	private static final String IS_ACCOUNT_OWNER = "isAccountOwner";
	private static final String SERVICE_TYPES = "serviceTypes";

	private static final String CANDIDATES = "candidates";
	private static final String DUNS_RESPONSE = "dunsResponse";
	private static final String STORE_NAME = "storeName";
	private static final String IS_ENROLLED = "isEnrolled";
	private static final String COUNTRY_BASESITE_ID = "countryBaseSiteID";

	@Resource(name = "storeFinderService")
	private SiteOneStoreFinderService storeFinderService;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Override
	public void init(final RequestAccountProcessModel businessProcessModel, final EmailPageModel emailPageModel)
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
			put(COUNTRY_BASESITE_ID, baseSite.getUid());
		}
		if(baseSite.getUid().equalsIgnoreCase(SiteoneCoreConstants.BASESITE_CA)) {
			storeSessionFacade.setCurrentBaseStore(SiteoneCoreConstants.CA_ISO_CODE);
		} else {
			storeSessionFacade.setCurrentBaseStore(SiteoneCoreConstants.US_ISO_CODE);
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
		put(ACCOUNT_NUMBER, businessProcessModel.getAccountNumber());
		put(ADDRESS_LINE1, businessProcessModel.getAddressLine1());
		put(ADDRESS_LINE2, businessProcessModel.getAddressLine2());
		put(CITY, businessProcessModel.getCity());
		put(STATE, businessProcessModel.getProvince());
		put(ZIPCODE, businessProcessModel.getZipcode());
		put(PHONE_NUMBER, businessProcessModel.getPhoneNumber());
		put(EMAIL_ADDRESS, businessProcessModel.getEmailAddress());
		put(YEARS_IN_BUSINESS, businessProcessModel.getContrYearsInBusiness());
		put(NUMBER_OF_EMPLOYEES, businessProcessModel.getContrEmpCount());
		put(PRIMARY_BUSINESS, businessProcessModel.getContrPrimaryBusiness());
		put(SERVICE_TYPES, businessProcessModel.getServiceTypes());
		put(LANGUAGE_PREFERENCE, businessProcessModel.getLanguagePreference());
		put(COPY_RIGHT_YEAR, "©" + Calendar.getInstance().get(Calendar.YEAR) + " ");
		put(IS_ENROLLED, BooleanUtils.isTrue(businessProcessModel.getEnrollInPartnersProgram()));
		final Boolean branchNotification = businessProcessModel.getBranchNotification() != null
				? businessProcessModel.getBranchNotification()
				: Boolean.FALSE;
		if (Boolean.TRUE.equals(branchNotification))
		{
			put(EMAIL, businessProcessModel.getBranchManagerEmail());
			put(DISPLAY_NAME, businessProcessModel.getEmailAddress());
			if(null != storeFinderService.getStoreForId(businessProcessModel.getStoreNumber()))
			{
				put(STORE_NAME, (storeFinderService.getStoreForId(businessProcessModel.getStoreNumber())).getName());
			}
		}
		else
		{
			if ("Contractor".equalsIgnoreCase(businessProcessModel.getTypeOfCustomer())
					&& businessProcessModel.getDunsResponse() != null
					&& (businessProcessModel.getDunsResponse().intValue() == 3
							|| businessProcessModel.getDunsResponse().intValue() == 4
							|| businessProcessModel.getDunsResponse().intValue() == 5))
			{
				if (businessProcessModel.getDunsResponse().intValue() != 5)
				{
					put(CANDIDATES, businessProcessModel.getDunsMatchCandidates());
				}
				put(DUNS_RESPONSE, businessProcessModel.getDunsResponse());
			}
			put(EMAIL, Config.getString("siteone.info.email", null));
			put(DISPLAY_NAME, Config.getString("siteone.info.email", null));
			put(FIRST_NAME, businessProcessModel.getFirstName());
			put(LAST_NAME, businessProcessModel.getLastName());
			put(IS_ACCOUNT_OWNER, businessProcessModel.getIsAccountOwner());
			put(HAS_ACCOUNT_NUMBER, businessProcessModel.getHasAccountNumber());
			put(TYPE_OF_CUSTOMER, businessProcessModel.getTypeOfCustomer());
			if(null != storeFinderService.getStoreForId(businessProcessModel.getStoreNumber()))
			{
				put(STORE_NAME, (storeFinderService.getStoreForId(businessProcessModel.getStoreNumber())).getName());
			}
		}
		storeSessionFacade.removeCurrentBaseStore();
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getSite(de.hybris.platform.
	 * processengine.model.BusinessProcessModel)
	 */
	@Override
	protected BaseSiteModel getSite(final RequestAccountProcessModel businessProcessModel)
	{
		return businessProcessModel.getSite();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getCustomer(de.hybris.platform.
	 * processengine.model.BusinessProcessModel)
	 */
	@Override
	protected CustomerModel getCustomer(final RequestAccountProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getEmailLanguage(de.hybris.
	 * platform.processengine.model.BusinessProcessModel)
	 */
	@Override
	protected LanguageModel getEmailLanguage(final RequestAccountProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
	}


}
