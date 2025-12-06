/**
 *
 */
package com.siteone.facade.requestaccount.impl;

import com.siteone.integration.exception.okta.OktaUnknownUserException;
import de.hybris.platform.b2b.constants.B2BConstants;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.util.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;

import jakarta.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.ResourceAccessException;

import com.siteone.commerceservice.customer.dto.SiteOneContrPrimaryBusinessListDTO;
import com.siteone.commerceservice.customer.dto.SiteOneContrPrimaryBusinessWsDTO;
import com.siteone.commerceservices.dto.createCustomer.CreateCustomerResponseWsDTO;
import com.siteone.commerceservices.dto.createCustomer.SiteoneAccountInfoWsDTO;
import com.siteone.commerceservices.dto.createCustomer.SiteoneCreateCustomerResponseWsDTO;
import com.siteone.commerceservices.dto.createCustomer.SiteoneWsUpdateAccountInfoWsDTO;
import com.siteone.commerceservices.dto.quotes.SiteoneWsNotifyQuoteWsDTO;
import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.customer.SiteOneCustomerAccountService;
import com.siteone.core.event.NotifyQuoteStatusEvent;
import com.siteone.core.event.RequestAccountEvent;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.SiteoneRequestAccountModel;
import com.siteone.core.requestaccount.service.SiteoneRequestAccountService;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.facade.SiteoneRequestAccountData;
import com.siteone.facade.customer.info.SiteOneContrPrimaryBusinessData;
import com.siteone.facade.requestaccount.SiteoneContrPrimaryBusinessMapFacade;
import com.siteone.facade.requestaccount.SiteoneRequestAccountFacade;
import com.siteone.facades.company.SiteOneB2BUserFacade;
import com.siteone.facades.constants.SiteoneFacadesConstants;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.exceptions.ContactNotCreatedOrUpdatedInOktaException;
import com.siteone.facades.exceptions.ServiceUnavailableException;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.customer.createCustomer.data.SiteOneWsAccountInfoData;
import com.siteone.integration.customer.createCustomer.data.SiteOneWsBranchInfoData;
import com.siteone.integration.customer.createCustomer.data.SiteOneWsContactInfoData;
import com.siteone.integration.customer.createCustomer.data.SiteOneWsCreateCustomerResponseData;
import com.siteone.integration.customer.createCustomer.data.SiteOneWsMatchCandidatesResponseData;
import com.siteone.integration.customer.createCustomer.data.SiteOneWsPriceClassInfoData;
import com.siteone.integration.customer.createCustomer.data.SiteOneWsResultResponseData;
import com.siteone.integration.customer.createCustomer.data.SiteOneWsSelfServeOnlineAccessRequestData;
import com.siteone.integration.customer.createCustomer.data.SiteOneWsSelfServeOnlineAccessResponseData;
import com.siteone.integration.okta.OKTAAPI;
import com.siteone.integration.services.okta.data.OktaCreateOrUpdateUserResponseData;
import com.siteone.integration.services.ue.SiteOneCreateCustomerWebService;
import com.siteone.integration.services.ue.SiteOneSelfServeWebService;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;


/**
 * @author SMondal
 *
 */
public class DefaultSiteoneRequestAccountFacade implements SiteoneRequestAccountFacade
{
	private static final Logger LOG = Logger.getLogger(DefaultSiteoneRequestAccountFacade.class);
	private static final String BOOMI_PLATFORM = "boomiPlatformLinux";

	private static final String HOMEOWNER_CODE = "homeOwner.trade.class.code";

	private static final String OPERATION_TYPE_CUSTOMER_ONLINE = "CustomerOnlineAccess";
	private static final String OPERATION_TYPE_CONTACT_ONLINE = "ContactOnlineAccess";
	private static final String OPERATION_TYPE_PAYBILL_ONLINE = "PayBillOnlineAccess";
	private static final String OPERATION_TYPE_ADMIN_ONLINE = "OnlineAdminAccess";

	private static final String OPERATION_TYPE_SHIP_TO_ONLINE = "ShipToOnlineAccess";
	private static final String CUSTOMER_ONLINE_ACCESS_EXIST = "Customer already have online access";

	private OKTAAPI oktaAPI;
	private CustomerAccountService customerAccountService;

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "siteoneRequestAccountReverseConverter")
	private Converter<SiteoneRequestAccountData, SiteoneRequestAccountModel> siteoneRequestAccountReverseConverter;

	@Resource(name = "siteoneRequestAccountService")
	private SiteoneRequestAccountService siteoneRequestAccountService;

	@Resource(name = "siteoneContrPrimaryBusinessMapFacade")
	private SiteoneContrPrimaryBusinessMapFacade siteoneContrPrimaryBusinessMapFacade;

	@Resource(name = "siteOneCreateCustomerWebService")
	private SiteOneCreateCustomerWebService siteOneCreateCustomerWebService;

	@Resource(name = "b2bUserFacade")
	protected SiteOneB2BUserFacade b2bUserFacade;

	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;

	@Resource(name = "eventService")
	private EventService eventService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	@Resource(name = "i18nService")
	private I18NService i18nService;

	@Resource(name = "messageSource")
	private MessageSource messageSource;

	@Resource(name = "storeFinderService")
	private SiteOneStoreFinderService storeFinderService;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "siteOneSelfServeWebService")
	private SiteOneSelfServeWebService siteOneSelfServeWebService;


	@Override
	public void populateSiteoneRequestAccountDataToModel(final SiteoneRequestAccountData siteoneRequestAccountData)
	{
		final SiteoneRequestAccountModel siteoneRequestAccountModel = new SiteoneRequestAccountModel();
		siteoneRequestAccountReverseConverter.convert(siteoneRequestAccountData, siteoneRequestAccountModel);
		siteoneRequestAccountService.saveSiteoneRequestAccountModel(siteoneRequestAccountModel);
	}

	@Override
	public String getSelfServeResponse(final SiteOneWsSelfServeOnlineAccessRequestData siteOneWsSelfServeOnlineAccessRequestData)
	{
		SiteOneWsSelfServeOnlineAccessResponseData selfServeOnlineAccessResponseData = null;
		String response = "";
		siteOneWsSelfServeOnlineAccessRequestData.setCorrelationID(UUID.randomUUID().toString());
		if (null != siteOneWsSelfServeOnlineAccessRequestData)
		{
			selfServeOnlineAccessResponseData = siteOneSelfServeWebService.selfServe(siteOneWsSelfServeOnlineAccessRequestData,
					Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
		}
		if (null != selfServeOnlineAccessResponseData)
		{
			if (selfServeOnlineAccessResponseData.getIsSuccess() && selfServeOnlineAccessResponseData.getResult() != null
					&& selfServeOnlineAccessResponseData.getResult().getOperationType()
							.equalsIgnoreCase(OPERATION_TYPE_CUSTOMER_ONLINE))
			{
				final SiteoneRequestAccountData siteoneRequestAccountData = populateSiteoneAccountRequestData(
						selfServeOnlineAccessResponseData);
				populateSiteoneRequestAccountDataToModel(siteoneRequestAccountData);
				final SiteOneWsCreateCustomerResponseData siteOneWsCreateCustomerResponseData = populateCustomerResponseData(
						selfServeOnlineAccessResponseData);
				response = createCustomer(siteoneRequestAccountData, siteOneWsCreateCustomerResponseData, true);

			}
			else
			{
				response = SiteoneCoreConstants.RESPONSE_FAILURE;

			}
		}
		else
		{
			response = SiteoneCoreConstants.RESPONSE_FAILURE;
		}
		return response;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facade.requestaccount.SiteoneRequestAccountFacade#createCustomer(com.siteone.facade.
	 * SiteoneRequestAccountData)
	 */
	@Override
	public String createCustomer(final SiteoneRequestAccountData siteoneRequestAccountData,
			SiteOneWsCreateCustomerResponseData siteOneWsCreateCustomerResponseData, final boolean isAccountExistsInUE)
	{
		String response = "";
		B2BUnitModel b2bUnitModel = null;
		final SiteoneRequestAccountModel siteoneRequestAccountModel = new SiteoneRequestAccountModel();
		siteoneRequestAccountReverseConverter.convert(siteoneRequestAccountData, siteoneRequestAccountModel);
		final RequestAccountEvent event = new RequestAccountEvent();
		if (!isAccountExistsInUE && StringUtils.isNotBlank(siteoneRequestAccountData.getAccountNumber())
				&& !siteoneRequestAccountData.getHasAccountNumber().booleanValue())
		{
			response = SiteoneCoreConstants.RESPONSE_FAILURE;
			event.setIsCreateCustomerFail(Boolean.TRUE);
			event.setAccountNumber(siteoneRequestAccountModel.getAccountNumber());
			eventService.publishEvent(initializeEvent(event, siteoneRequestAccountModel));
			return response;
		}
		else
		{
			if (!isAccountExistsInUE)
			{
				siteOneWsCreateCustomerResponseData = siteOneCreateCustomerWebService.createCustomer(siteoneRequestAccountModel,
						Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
				if (siteOneWsCreateCustomerResponseData == null) {
					siteOneWsCreateCustomerResponseData.setIsSuccess("false");
				}
				LOG.error("createCustomer: " + ((siteOneWsCreateCustomerResponseData==null)? "Customer: " + siteoneRequestAccountModel.getTypeOfCustomer() + " - " + siteoneRequestAccountModel.getEmailAddress() + " - Request NULL" :
						"Customer: " + siteoneRequestAccountModel.getTypeOfCustomer() + " - " + siteoneRequestAccountModel.getEmailAddress() + " - Is Success: " + siteOneWsCreateCustomerResponseData.getIsSuccess()));
			}
			else
			{
				siteOneWsCreateCustomerResponseData.setIsSuccess("true");
			}
			if (siteOneWsCreateCustomerResponseData != null
					&& siteOneWsCreateCustomerResponseData.getIsSuccess() != null && siteOneWsCreateCustomerResponseData.getIsSuccess().equalsIgnoreCase("true"))
			{
				if (null != siteOneWsCreateCustomerResponseData.getResult().getAccountInfo().getAccountNumber())
				{
					b2bUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(getParentUnitID(
							siteOneWsCreateCustomerResponseData));
					if (null == b2bUnitModel)
					{
						b2bUnitModel = ((SiteOneB2BUnitFacade) b2bUnitFacade).updateB2BUnit(siteoneRequestAccountModel,
								siteOneWsCreateCustomerResponseData, isAccountExistsInUE);
					}
					else {
						// Update B2BUnit
						b2bUnitModel.setActive(Boolean.TRUE);
						if (StringUtils.isNotBlank(siteoneRequestAccountData.getCompanyName()))
						{
							b2bUnitModel.setLocName(siteoneRequestAccountData.getCompanyName(), getI18nService().getCurrentLocale());
							b2bUnitModel.setName(siteoneRequestAccountData.getCompanyName());
						}
						if (StringUtils.isNotBlank(siteoneRequestAccountData.getTradeClass()))
						{
							b2bUnitModel.setTradeClass(siteoneRequestAccountData.getTradeClass());
						}
						if (StringUtils.isNotBlank(siteoneRequestAccountData.getSubTradeClass()))
						{
							b2bUnitModel.setSubTradeClass(siteoneRequestAccountData.getSubTradeClass());
						}
						if (StringUtils.isNotBlank(siteoneRequestAccountData.getCreditCode()))
						{
							b2bUnitModel.setCreditCode(siteoneRequestAccountData.getCreditCode());
						}
						if (StringUtils.isNotBlank(siteoneRequestAccountData.getCreditTerms()))
						{
							b2bUnitModel.setCreditTermCode(siteoneRequestAccountData.getCreditTerms());
						}
						if (StringUtils.isNotBlank(siteoneRequestAccountData.getAcctGroupCode()))
						{
							b2bUnitModel.setAccountGroupCode(siteoneRequestAccountData.getAcctGroupCode());
						}
					}
				}

				final B2BCustomerModel b2bCustomerModel = b2bUserFacade.createCustomer(siteoneRequestAccountData,
						siteOneWsCreateCustomerResponseData, b2bUnitModel);

				if (b2bUnitModel == null && b2bCustomerModel == null && !isAccountExistsInUE)
				{
					event.setIsCreateCustomerFail(Boolean.TRUE);
					eventService.publishEvent(initializeEvent(event, siteoneRequestAccountModel));
					response = SiteoneCoreConstants.RESPONSE_FAILURE;
					return response;
				}

				if (isAccountExistsInUE)
				{
					b2bCustomerModel
							.setPayBillOnline(siteOneWsCreateCustomerResponseData.getResult().getContactInfo().getPayBillOnline());
					b2bUnitModel.setPayBillOnline(siteOneWsCreateCustomerResponseData.getResult().getContactInfo().getPayBillOnline());

					try
					{
						modelService.save(b2bCustomerModel);
						modelService.refresh(b2bCustomerModel);
						modelService.save(b2bUnitModel);
						modelService.refresh(b2bUnitModel);
					}
					catch (final ModelSavingException ex)
					{
						LOG.info("Saving Error" + ex);
						return SiteoneCoreConstants.RESPONSE_FAILURE;
					}

				}
				PointOfServiceModel preferredStore = new PointOfServiceModel();
				if (null != siteoneRequestAccountData.getStoreNumber())
				{
					//set preferred store for b2bcustomermodel from data
					preferredStore = storeFinderService.getStoreForId(siteoneRequestAccountData.getStoreNumber());
				}
				else
				{
					//check response data
					preferredStore = storeFinderService
							.getStoreForId(siteOneWsCreateCustomerResponseData.getResult().getBranchInfo().getNumber());
				}
				b2bCustomerModel.setPreferredStore(preferredStore);
				if (preferredStore != null && StringUtils.isNotEmpty(preferredStore.getStoreId())) {
					b2bCustomerModel.setHomeBranch(preferredStore.getStoreId());
					siteoneRequestAccountModel.setStoreNumber(preferredStore.getStoreId());
					final HashSet<PointOfServiceModel> stores = new HashSet<>();
					stores.add(preferredStore);
					b2bCustomerModel.setStores(stores);
				}

				if (b2bCustomerModel.getGuid() != null)
				{
					response = SiteoneCoreConstants.RESPONSE_SUCCESS;//call to okta api to create a user at okta.
					OktaCreateOrUpdateUserResponseData responseData = null;
					boolean success = false;
					int count = 0;
					final int maxTries = Config.getInt("hybris.realtime.account.maxRetryCount", 1);
					while (!success && count++ < maxTries)
					{
						try
						{
							responseData = getOktaAPI().createUser(b2bCustomerModel);
							success = true;
						}
						catch (final ResourceAccessException resourceAccessException)
						{
							LOG.error("Not able to establish connection with okta to create contact", resourceAccessException);
							event.setIsCreateCustomerFail(Boolean.TRUE);
							eventService.publishEvent(initializeEvent(event, siteoneRequestAccountModel));
							throw new ServiceUnavailableException("404");
						}
					}
					if (null != responseData)
					{
						if (SiteoneCoreConstants.OKTA_USER_STATUS_STAGED.equalsIgnoreCase(responseData.getStatus()))
						{
							//Send create password email to user
							((SiteOneCustomerAccountService) getCustomerAccountService()).createPassword(b2bCustomerModel);
							if ("CONTRACTOR".equalsIgnoreCase(siteoneRequestAccountData.getTypeOfCustomer()))
							{
								event.setAccountNumber(
										siteOneWsCreateCustomerResponseData.getResult().getAccountInfo().getAccountNumber());
								event.setBranchManagerEmail(
										siteOneWsCreateCustomerResponseData.getResult().getBranchInfo().getManagerEmail());
								event.setBranchNotification(Boolean.TRUE);
								if (null != siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse())
								{
									event.setDunsResponse(siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse()
											.getIdentityResolutionResponseCode());
								}
								eventService.publishEvent(initializeEvent(event, siteoneRequestAccountModel));
							}
						}
					}
					else
					{
						LOG.error("Not able to create contact in Okta");
						event.setIsCreateCustomerFail(Boolean.TRUE);
						eventService.publishEvent(initializeEvent(event, siteoneRequestAccountModel));
						throw new ContactNotCreatedOrUpdatedInOktaException("Contact not created in Okta");
					}
				}

			}
			else
			{
				if (SiteoneCoreConstants.REQUEST_ACCOUNT_FORM_CONTRACTOR
						.equalsIgnoreCase(siteoneRequestAccountData.getTypeOfCustomer()) && siteOneWsCreateCustomerResponseData != null
						&& siteOneWsCreateCustomerResponseData.getResult() != null
						&& siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse() != null
						&& siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse()
								.getIdentityResolutionResponseCode() != null
						&& (siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse()
								.getIdentityResolutionResponseCode().intValue() == 3
								|| siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse()
										.getIdentityResolutionResponseCode().intValue() == 4))
				{

					final Map<Integer, List<String>> candidates = new HashMap<>();

					for (final SiteOneWsMatchCandidatesResponseData matchCanditade : siteOneWsCreateCustomerResponseData.getResult()
							.getIdentityResolutionResponse().getMatchCandidates())
					{
						final List<String> entries = new ArrayList<>();
						entries.add(matchCanditade.getConfidenceCode() != null ? matchCanditade.getConfidenceCode().toString() : "");
						entries.add(matchCanditade.getDuns() != null ? matchCanditade.getDuns().toString() : "");
						entries.add(matchCanditade.getMatchGrade() != null ? matchCanditade.getMatchGrade() : "");
						entries.add(matchCanditade.getOrganizationDetails().getName() != null
								? matchCanditade.getOrganizationDetails().getName()
								: "");
						entries.add(matchCanditade.getOrganizationDetails().getTelephoneNumber1() != null
								? matchCanditade.getOrganizationDetails().getTelephoneNumber1()
								: "");
						entries.add(matchCanditade.getOrganizationDetails().getTelephoneNumber2() != null
								? matchCanditade.getOrganizationDetails().getTelephoneNumber2()
								: "");
						entries.add(matchCanditade.getOrganizationDetails().getAddress().getAddressLocality() != null
								? matchCanditade.getOrganizationDetails().getAddress().getAddressLocality()
								: "");
						entries.add(matchCanditade.getOrganizationDetails().getAddress().getAddressRegion() != null
								? matchCanditade.getOrganizationDetails().getAddress().getAddressRegion()
								: "");
						entries.add(matchCanditade.getOrganizationDetails().getAddress().getCountryCode() != null
								? matchCanditade.getOrganizationDetails().getAddress().getCountryCode()
								: "");
						entries.add(matchCanditade.getOrganizationDetails().getAddress().getPostalCode() != null
								? matchCanditade.getOrganizationDetails().getAddress().getPostalCode()
								: "");
						entries.add(matchCanditade.getOrganizationDetails().getAddress().getPostalCodeExtension() != null
								? matchCanditade.getOrganizationDetails().getAddress().getPostalCodeExtension()
								: "");
						entries.add(matchCanditade.getOrganizationDetails().getAddress().getStreetAddressLine1() != null
								? matchCanditade.getOrganizationDetails().getAddress().getStreetAddressLine1()
								: "");
						entries.add(matchCanditade.getOrganizationDetails().getAddress().getStreetAddressLine2() != null
								? matchCanditade.getOrganizationDetails().getAddress().getStreetAddressLine2()
								: "");


						candidates.put(matchCanditade.getDisplaySequence(), entries);
					}


					event.setCandidates(candidates);
					event.setDunsResponse(siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse()
							.getIdentityResolutionResponseCode());

				}
				else
				{
					if (SiteoneCoreConstants.REQUEST_ACCOUNT_FORM_CONTRACTOR
							.equalsIgnoreCase(siteoneRequestAccountData.getTypeOfCustomer())
							&& siteOneWsCreateCustomerResponseData != null && siteOneWsCreateCustomerResponseData.getResult() != null
							&& siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse() != null
							&& siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse()
									.getIdentityResolutionResponseCode() != null
							&& siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse()
									.getIdentityResolutionResponseCode().intValue() == 5)
					{
						event.setDunsResponse(siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse()
								.getIdentityResolutionResponseCode());
					}
					else
					{
						event.setIsCreateCustomerFail(Boolean.TRUE);
					}
				}
				eventService.publishEvent(initializeEvent(event, siteoneRequestAccountModel));
				response = SiteoneCoreConstants.RESPONSE_FAILURE;
			}
		}
		return response;
	}

	@Override
	public String createParentCustomer(final SiteoneRequestAccountData siteoneRequestAccountData,
			SiteOneWsCreateCustomerResponseData siteOneWsCreateCustomerResponseData, final boolean isAccountExistsInUE)
	{

		String response = "";
		final SiteoneRequestAccountModel siteoneRequestAccountModel = new SiteoneRequestAccountModel();
		siteoneRequestAccountReverseConverter.convert(siteoneRequestAccountData, siteoneRequestAccountModel);
		final RequestAccountEvent event = new RequestAccountEvent();
		if (!isAccountExistsInUE && StringUtils.isNotBlank(siteoneRequestAccountData.getAccountNumber()))
		{
			response = SiteoneCoreConstants.RESPONSE_FAILURE;
			event.setIsCreateCustomerFail(Boolean.TRUE);
			event.setAccountNumber(siteoneRequestAccountModel.getAccountNumber());
			eventService.publishEvent(initializeEvent(event, siteoneRequestAccountModel));
			return response;
		}
		else
		{
			if (!isAccountExistsInUE)
			{
				siteOneWsCreateCustomerResponseData = siteOneCreateCustomerWebService.createCustomer(siteoneRequestAccountModel,
						Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
				if (siteOneWsCreateCustomerResponseData == null) {
					siteOneWsCreateCustomerResponseData.setIsSuccess("false");
				}
				LOG.error("createCustomer: " + ((siteOneWsCreateCustomerResponseData==null)? "Customer: " + siteoneRequestAccountModel.getTypeOfCustomer() + " - " + siteoneRequestAccountModel.getEmailAddress() + " - Request NULL" :
						"Customer: " + siteoneRequestAccountModel.getTypeOfCustomer() + " - " + siteoneRequestAccountModel.getEmailAddress() + " - Is Success: " + siteOneWsCreateCustomerResponseData.getIsSuccess()));
			}
			else
			{
				siteOneWsCreateCustomerResponseData.setIsSuccess("true");
			}
			if (siteOneWsCreateCustomerResponseData != null
					&& siteOneWsCreateCustomerResponseData.getIsSuccess().equalsIgnoreCase("true"))
			{
				final B2BUnitModel b2bUnitModel = ((SiteOneB2BUnitFacade) b2bUnitFacade)
						.updateParentB2BUnit(siteoneRequestAccountModel, siteOneWsCreateCustomerResponseData, isAccountExistsInUE);
				if (isAccountExistsInUE)
				{
					b2bUnitModel.setPayBillOnline(siteOneWsCreateCustomerResponseData.getResult().getContactInfo().getPayBillOnline());
					try
					{
						modelService.save(b2bUnitModel);
						modelService.refresh(b2bUnitModel);
						response = SiteoneCoreConstants.RESPONSE_SUCCESS;
					}
					catch (final ModelSavingException ex)
					{
						LOG.info("Saving Error" + ex);
						return SiteoneCoreConstants.RESPONSE_FAILURE;
					}

				}
				PointOfServiceModel preferredStore = new PointOfServiceModel();
				if (null != siteoneRequestAccountData.getStoreNumber())
				{
					//set preferred store for b2bcustomermodel from data
					preferredStore = storeFinderService.getStoreForId(siteoneRequestAccountData.getStoreNumber());
				}
				else
				{
					//check response data
					preferredStore = storeFinderService
							.getStoreForId(siteOneWsCreateCustomerResponseData.getResult().getBranchInfo().getNumber());
				}
				final HashSet<PointOfServiceModel> stores = new HashSet<>();
				stores.add(preferredStore);
			}
			else
			{
				if (SiteoneCoreConstants.REQUEST_ACCOUNT_FORM_CONTRACTOR
						.equalsIgnoreCase(siteoneRequestAccountData.getTypeOfCustomer()) && siteOneWsCreateCustomerResponseData != null
						&& siteOneWsCreateCustomerResponseData.getResult() != null
						&& siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse() != null
						&& siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse()
								.getIdentityResolutionResponseCode() != null
						&& (siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse()
								.getIdentityResolutionResponseCode() == 3
								|| siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse()
										.getIdentityResolutionResponseCode() == 4))
				{

					final Map<Integer, List<String>> candidates = new HashMap<>();

					for (final SiteOneWsMatchCandidatesResponseData matchCanditade : siteOneWsCreateCustomerResponseData.getResult()
							.getIdentityResolutionResponse().getMatchCandidates())
					{
						final List<String> entries = new ArrayList<>();
						entries.add(matchCanditade.getConfidenceCode() != null ? matchCanditade.getConfidenceCode().toString() : "");
						entries.add(matchCanditade.getDuns() != null ? matchCanditade.getDuns().toString() : "");
						entries.add(matchCanditade.getMatchGrade() != null ? matchCanditade.getMatchGrade() : "");
						entries.add(matchCanditade.getOrganizationDetails().getName() != null
								? matchCanditade.getOrganizationDetails().getName()
								: "");
						entries.add(matchCanditade.getOrganizationDetails().getTelephoneNumber1() != null
								? matchCanditade.getOrganizationDetails().getTelephoneNumber1()
								: "");
						entries.add(matchCanditade.getOrganizationDetails().getTelephoneNumber2() != null
								? matchCanditade.getOrganizationDetails().getTelephoneNumber2()
								: "");
						entries.add(matchCanditade.getOrganizationDetails().getAddress().getAddressLocality() != null
								? matchCanditade.getOrganizationDetails().getAddress().getAddressLocality()
								: "");
						entries.add(matchCanditade.getOrganizationDetails().getAddress().getAddressRegion() != null
								? matchCanditade.getOrganizationDetails().getAddress().getAddressRegion()
								: "");
						entries.add(matchCanditade.getOrganizationDetails().getAddress().getCountryCode() != null
								? matchCanditade.getOrganizationDetails().getAddress().getCountryCode()
								: "");
						entries.add(matchCanditade.getOrganizationDetails().getAddress().getPostalCode() != null
								? matchCanditade.getOrganizationDetails().getAddress().getPostalCode()
								: "");
						entries.add(matchCanditade.getOrganizationDetails().getAddress().getPostalCodeExtension() != null
								? matchCanditade.getOrganizationDetails().getAddress().getPostalCodeExtension()
								: "");
						entries.add(matchCanditade.getOrganizationDetails().getAddress().getStreetAddressLine1() != null
								? matchCanditade.getOrganizationDetails().getAddress().getStreetAddressLine1()
								: "");
						entries.add(matchCanditade.getOrganizationDetails().getAddress().getStreetAddressLine2() != null
								? matchCanditade.getOrganizationDetails().getAddress().getStreetAddressLine2()
								: "");


						candidates.put(matchCanditade.getDisplaySequence(), entries);
					}


					event.setCandidates(candidates);
					event.setDunsResponse(siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse()
							.getIdentityResolutionResponseCode());

				}
				else
				{
					if (SiteoneCoreConstants.REQUEST_ACCOUNT_FORM_CONTRACTOR
							.equalsIgnoreCase(siteoneRequestAccountData.getTypeOfCustomer())
							&& siteOneWsCreateCustomerResponseData != null && siteOneWsCreateCustomerResponseData.getResult() != null
							&& siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse() != null
							&& siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse()
									.getIdentityResolutionResponseCode() != null
							&& siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse()
									.getIdentityResolutionResponseCode() == 5)
					{
						event.setDunsResponse(siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse()
								.getIdentityResolutionResponseCode());
					}
					else
					{
						event.setIsCreateCustomerFail(Boolean.TRUE);
					}
				}
				eventService.publishEvent(initializeEvent(event, siteoneRequestAccountModel));
				response = SiteoneCoreConstants.RESPONSE_FAILURE;
			}
		}
		return response;
	}

	public RequestAccountEvent initializeEvent(final RequestAccountEvent event,
			final SiteoneRequestAccountModel siteoneRequestAccountModel)
	{
		event.setFirstName(siteoneRequestAccountModel.getFirstName());
		event.setLastName(siteoneRequestAccountModel.getLastName());
		event.setCompanyName(siteoneRequestAccountModel.getCompanyName());
		event.setAddressLine1(siteoneRequestAccountModel.getAddressLine1());
		event.setAddressLine2(siteoneRequestAccountModel.getAddressLine2());
		event.setCity(siteoneRequestAccountModel.getCity());
		event.setState(siteoneRequestAccountModel.getState());
		event.setZipcode(siteoneRequestAccountModel.getZipcode());
		event.setPhoneNumber(siteoneRequestAccountModel.getPhoneNumber());
		event.setEmailAddress(siteoneRequestAccountModel.getEmailAddress());
		event.setIsAccountOwner(siteoneRequestAccountModel.getIsAccountOwner());
		if (SiteoneCoreConstants.REQUEST_ACCOUNT_FORM_CONTRACTOR.equalsIgnoreCase(siteoneRequestAccountModel.getTypeOfCustomer()))
		{
			event.setContrPrimaryBusiness(siteoneRequestAccountModel.getTradeClassName());
			event.setServiceTypes(siteoneRequestAccountModel.getSubTradeClassName());
			event.setContrYearsInBusiness(siteoneRequestAccountModel.getContrYearsInBusiness());
			event.setContrEmpCount(siteoneRequestAccountModel.getContrEmpCount());
		}

		event.setTypeOfCustomer(siteoneRequestAccountModel.getTypeOfCustomer());
		event.setLanguagePreference(siteoneRequestAccountModel.getLanguagePreference());
		PointOfServiceModel preferredStore = new PointOfServiceModel();
		preferredStore = storeFinderService.getStoreForId(siteoneRequestAccountModel.getStoreNumber());
		if (preferredStore != null) {
			if (preferredStore.getBaseStore().getUid().equalsIgnoreCase(SiteoneCoreConstants.SITEONE_CA_BASESTORE)) {
				event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_CA_BASESTORE));
				event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_CA));
			} else {
				event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_US_BASESTORE));
				event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_US));
			}
		}
		else {
			B2BCustomerModel b2BCustomerModel = (B2BCustomerModel) b2bCustomerService
					.getUserForUID(siteoneRequestAccountModel.getEmailAddress());
			if (null != b2BCustomerModel && null != b2BCustomerModel.getDefaultB2BUnit()
					&& b2BCustomerModel.getDefaultB2BUnit().getUid().contains(SiteoneCoreConstants.INDEX_OF_US))
			{
				event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_US_BASESTORE));
				event.setSite(getBaseSiteService().getBaseSiteForUID(SiteoneCoreConstants.BASESITE_US));
			}
			else
			{
				event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_CA_BASESTORE));
				event.setSite(getBaseSiteService().getBaseSiteForUID(SiteoneCoreConstants.BASESITE_CA));
			}
		}
		event.setLanguage(getCommonI18NService().getCurrentLanguage());
		event.setStoreNumber(siteoneRequestAccountModel.getStoreNumber());
		event.setEnrollInPartnersProgram(BooleanUtils.isTrue(siteoneRequestAccountModel.getEnrollInPartnersProgram()));
		return event;
	}


	/**
	 * @return the commonI18NService
	 */
	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}


	/**
	 * @param commonI18NService the commonI18NService to set
	 */
	public void setCommonI18NService(CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

	/**
	 * @return the oktaAPI
	 */
	public OKTAAPI getOktaAPI()
	{
		return oktaAPI;
	}

	/**
	 * @param oktaAPI
	 *           the oktaAPI to set
	 */
	public void setOktaAPI(final OKTAAPI oktaAPI)
	{
		this.oktaAPI = oktaAPI;
	}

	/**
	 * @return the customerAccountService
	 */
	public CustomerAccountService getCustomerAccountService()
	{
		return customerAccountService;
	}

	/**
	 * @param customerAccountService
	 *           the customerAccountService to set
	 */
	public void setCustomerAccountService(final CustomerAccountService customerAccountService)
	{
		this.customerAccountService = customerAccountService;
	}

	@Override
	public List<String> getLightingReasonOf()
	{
		final List<String> lightingReasonOf = new ArrayList<>();
		lightingReasonOf.add(
				getMessageSource().getMessage("help.contactUs.reason.lighting.callback", null, getI18nService().getCurrentLocale()));
		lightingReasonOf
				.add(getMessageSource().getMessage("help.contactUs.reason.lighting.demo", null, getI18nService().getCurrentLocale()));
		lightingReasonOf.add(
				getMessageSource().getMessage("help.contactUs.reason.lighting.support", null, getI18nService().getCurrentLocale()));
		lightingReasonOf.add(
				getMessageSource().getMessage("help.contactUs.reason.lighting.outdoor", null, getI18nService().getCurrentLocale()));
		return lightingReasonOf;
	}

	@Override
	public String getReasonForContact(final String reason)
	{

		if (reason.equalsIgnoreCase("support"))
		{
			return getMessageSource().getMessage("help.contactUs.reason.support", null, getI18nService().getCurrentLocale());
		}
		else
		{
			return getMessageSource().getMessage("help.contactUs.reason.callBack", null, getI18nService().getCurrentLocale());

		}
	}


	/**
	 * @return the i18nService
	 */
	public I18NService getI18nService()
	{
		return i18nService;
	}

	/**
	 * @param i18nService
	 *           the i18nService to set
	 */
	public void setI18nService(final I18NService i18nService)
	{
		this.i18nService = i18nService;
	}

	/**
	 * @return the messageSource
	 */
	public MessageSource getMessageSource()
	{
		return messageSource;
	}

	/**
	 * @param messageSource
	 *           the messageSource to set
	 */
	public void setMessageSource(final MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}

	@Override
	public CreateCustomerResponseWsDTO getUpdateCustomerResponse(
			final SiteoneWsUpdateAccountInfoWsDTO siteoneWsUpdateAccountInfoWsDTO,
			final SiteoneRequestAccountData siteoneRequestAccountData)
	{
		final CreateCustomerResponseWsDTO response = new CreateCustomerResponseWsDTO();
		response.setCorrelationId(siteoneWsUpdateAccountInfoWsDTO.getCorrelationId());

		final SiteoneCreateCustomerResponseWsDTO siteoneCreateCustomerResponseWsDTO = new SiteoneCreateCustomerResponseWsDTO();
		siteoneCreateCustomerResponseWsDTO.setResult(siteoneWsUpdateAccountInfoWsDTO);
		siteoneCreateCustomerResponseWsDTO.setCorrelationId(siteoneWsUpdateAccountInfoWsDTO.getCorrelationId());


		final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		mapperFactory.classMap(SiteoneCreateCustomerResponseWsDTO.class, SiteOneWsCreateCustomerResponseData.class);
		final MapperFacade mapper = mapperFactory.getMapperFacade();

		final SiteOneWsCreateCustomerResponseData siteOneWsCreateCustomerResponseData = mapper
				.map(siteoneCreateCustomerResponseWsDTO, SiteOneWsCreateCustomerResponseData.class);

		final B2BCustomerModel customerModel = (B2BCustomerModel) b2bCustomerService
				.getUserForUID(siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getContactEmail().trim().toLowerCase());

		final B2BUnitModel unitModel = (B2BUnitModel) b2bUnitService
				.getUnitForUid(getParentUnitID(siteoneWsUpdateAccountInfoWsDTO));

		if ((!siteoneWsUpdateAccountInfoWsDTO.getOperationType().equalsIgnoreCase(OPERATION_TYPE_CUSTOMER_ONLINE)
				&& customerModel != null
				&& !siteoneWsUpdateAccountInfoWsDTO.getOperationType().equalsIgnoreCase(OPERATION_TYPE_SHIP_TO_ONLINE))
				|| (siteoneWsUpdateAccountInfoWsDTO.getOperationType().equalsIgnoreCase(OPERATION_TYPE_CUSTOMER_ONLINE)
						&& unitModel != null && customerModel == null)
				|| (!siteoneWsUpdateAccountInfoWsDTO.getOperationType().equalsIgnoreCase(OPERATION_TYPE_CUSTOMER_ONLINE)
						&& unitModel != null && customerModel == null)
				|| (siteoneWsUpdateAccountInfoWsDTO.getOperationType().equalsIgnoreCase(OPERATION_TYPE_SHIP_TO_ONLINE)
						&& unitModel != null && customerModel != null)
				|| (siteoneWsUpdateAccountInfoWsDTO.getOperationType().equalsIgnoreCase(OPERATION_TYPE_ADMIN_ONLINE)
						&& unitModel != null && customerModel != null)
				|| (siteoneWsUpdateAccountInfoWsDTO.getOperationType().equalsIgnoreCase(OPERATION_TYPE_ADMIN_ONLINE)
						&& unitModel == null && customerModel != null))
		{
			final CreateCustomerResponseWsDTO updateCustomerResponse = updateCustomerOnlineAccess(siteoneWsUpdateAccountInfoWsDTO,
					siteOneWsCreateCustomerResponseData, siteoneRequestAccountData);
			response.setStatus(updateCustomerResponse.getStatus());
			response.setMessage(updateCustomerResponse.getMessage());
			return response;
		}


		else if (((siteoneWsUpdateAccountInfoWsDTO.getOperationType().equalsIgnoreCase(OPERATION_TYPE_CUSTOMER_ONLINE)
				|| siteoneWsUpdateAccountInfoWsDTO.getOperationType().equalsIgnoreCase(OPERATION_TYPE_SHIP_TO_ONLINE))
				&& customerModel == null)
				|| ((!siteoneWsUpdateAccountInfoWsDTO.getOperationType().equalsIgnoreCase(OPERATION_TYPE_CUSTOMER_ONLINE)
						|| siteoneWsUpdateAccountInfoWsDTO.getOperationType().equalsIgnoreCase(OPERATION_TYPE_SHIP_TO_ONLINE))
						&& unitModel == null && customerModel == null)
				|| (siteoneWsUpdateAccountInfoWsDTO.getOperationType().equalsIgnoreCase(OPERATION_TYPE_SHIP_TO_ONLINE)
						&& unitModel == null && customerModel == null)
				|| (siteoneWsUpdateAccountInfoWsDTO.getOperationType().equalsIgnoreCase(OPERATION_TYPE_ADMIN_ONLINE)
						&& unitModel == null && customerModel == null)
				|| (siteoneWsUpdateAccountInfoWsDTO.getOperationType().equalsIgnoreCase(OPERATION_TYPE_CUSTOMER_ONLINE)
				&& unitModel == null && customerModel == null))

		{
			//	populate
			populateSiteoneRequestAccountDataToModel(siteoneRequestAccountData);

			//	Create Okta Account
			final String createResult = createCustomer(siteoneRequestAccountData, siteOneWsCreateCustomerResponseData, true);
			if (createResult.equalsIgnoreCase(SiteoneCoreConstants.RESPONSE_SUCCESS))
			{
				response.setStatus(String.valueOf(HttpStatus.OK.value()));
				response.setMessage("Created Customer in Ecomm Successfully.");
				return response;
			}
		}
		else if (siteoneWsUpdateAccountInfoWsDTO.getOperationType().equalsIgnoreCase(OPERATION_TYPE_CUSTOMER_ONLINE)
				&& unitModel != null)
		{
			if (!unitModel.getActive())
			{
				b2bUnitService.enableUnit(unitModel);
			}
			//	populate
			populateSiteoneRequestAccountDataToModel(siteoneRequestAccountData);
			response.setStatus(String.valueOf(HttpStatus.OK.value()));
			response.setMessage("Customer Updated in Ecomm Successfully.");
			return response;
		}

		else if (siteoneWsUpdateAccountInfoWsDTO.getOperationType().equalsIgnoreCase(OPERATION_TYPE_SHIP_TO_ONLINE)
				&& unitModel == null && customerModel != null)
		{
			//	populate
			populateSiteoneRequestAccountDataToModel(siteoneRequestAccountData);

			final CreateCustomerResponseWsDTO updateCustomerResponse = updateCustomerOnlineAccess(siteoneWsUpdateAccountInfoWsDTO,
					siteOneWsCreateCustomerResponseData, siteoneRequestAccountData);
			response.setStatus(updateCustomerResponse.getStatus());
			response.setMessage(updateCustomerResponse.getMessage());
			return response;
		}

		response.setStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
		response.setMessage("Unable to Create customer in Ecomm");
		return response;
	}

	@Override
	public CreateCustomerResponseWsDTO getUpdateParentResponse(
			final SiteoneWsUpdateAccountInfoWsDTO siteoneWsUpdateAccountInfoWsDTO,
			final SiteoneRequestAccountData siteoneRequestAccountData)
	{
		final CreateCustomerResponseWsDTO response = new CreateCustomerResponseWsDTO();
		response.setCorrelationId(siteoneWsUpdateAccountInfoWsDTO.getCorrelationId());

		final SiteoneCreateCustomerResponseWsDTO siteoneCreateCustomerResponseWsDTO = new SiteoneCreateCustomerResponseWsDTO();
		siteoneCreateCustomerResponseWsDTO.setResult(siteoneWsUpdateAccountInfoWsDTO);
		siteoneCreateCustomerResponseWsDTO.setCorrelationId(siteoneWsUpdateAccountInfoWsDTO.getCorrelationId());


		final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		mapperFactory.classMap(SiteoneCreateCustomerResponseWsDTO.class, SiteOneWsCreateCustomerResponseData.class);
		final MapperFacade mapper = mapperFactory.getMapperFacade();

		final SiteOneWsCreateCustomerResponseData siteOneWsCreateCustomerResponseData = mapper
				.map(siteoneCreateCustomerResponseWsDTO, SiteOneWsCreateCustomerResponseData.class);

		final B2BUnitModel unitModel = (B2BUnitModel) b2bUnitService
				.getUnitForUid(getUnitParentIDPerShipTo(siteoneWsUpdateAccountInfoWsDTO));

		if (unitModel == null)
		{
			//	populate
			populateSiteoneRequestAccountDataToModel(siteoneRequestAccountData);

			//	Create Okta Account
			final String createResult = createParentCustomer(siteoneRequestAccountData, siteOneWsCreateCustomerResponseData, true);
			if (createResult.equalsIgnoreCase(SiteoneCoreConstants.RESPONSE_SUCCESS))
			{
				response.setStatus(String.valueOf(HttpStatus.OK.value()));
				response.setMessage("Created Parent Account and Customer in Ecomm Successfully.");
				return response;
			}
		}
		else
		{
			b2bUnitService.enableUnit(unitModel);
			response.setStatus(String.valueOf(HttpStatus.OK.value()));
			response.setMessage("Parent enabled successfully.");
			return response;
		}

		response.setStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
		response.setMessage("Unable to Create Parent Account in Ecomm");
		return response;
	}

	protected Set<PrincipalGroupModel> removeUsergroupFromGroups(final String usergroup, final Set<PrincipalGroupModel> groups)
	{
		final Set<PrincipalGroupModel> groupsWithoutUsergroup = new HashSet<PrincipalGroupModel>(groups);
		groupsWithoutUsergroup.removeIf(group -> !!StringUtils.equals(usergroup, group.getUid()));
		return groupsWithoutUsergroup;
	}


	private CreateCustomerResponseWsDTO updateCustomerOnlineAccess(
			final SiteoneWsUpdateAccountInfoWsDTO siteoneWsUpdateAccountInfoWsDTO,
			final SiteOneWsCreateCustomerResponseData siteOneWsCreateCustomerResponseData,
			final SiteoneRequestAccountData siteoneRequestAccountData)
	{
		final CreateCustomerResponseWsDTO response = new CreateCustomerResponseWsDTO();
		B2BCustomerModel b2bCustomerModel = null;
		B2BUnitModel b2bUnitModel = null;
		try
		{
			b2bCustomerModel = (B2BCustomerModel) getUserService()
					.getUserForUID(siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getContactEmail().trim().toLowerCase());
			if (b2bCustomerModel != null)
			{
				b2bUnitModel = b2bCustomerModel.getDefaultB2BUnit();
			}

		}
		catch (final UnknownIdentifierException e)
		{

			if (siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getAccountNumber() != null)
			{
				b2bUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(getParentUnitID(siteoneWsUpdateAccountInfoWsDTO));
			}
		}

		//				USER exists in hybris
		if (b2bUnitModel != null)
		{
			if (b2bCustomerModel != null)
			{
				if (siteoneWsUpdateAccountInfoWsDTO.getContactInfo() != null)
				{
					if (b2bCustomerModel.getGuid() != null && siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getContactId() != null
							&& !b2bCustomerModel.getGuid()
									.equalsIgnoreCase(siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getContactId()))
					{
						final String uid = b2bCustomerModel.getUid();
						if (oktaAPI.isUserInOkta(uid))
						{
							oktaAPI.deleteUser(uid);
						}

						final String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
						final String newUid = "bak" + timestamp + "_" + uid;
						b2bCustomerModel.setUid(newUid);
						b2bCustomerModel.setName("Disabled-" + b2bCustomerModel.getName());
						b2bCustomerModel.setActive(Boolean.FALSE);
						modelService.save(b2bCustomerModel);
						LOG.error("Customer Uid changed from " + uid + " to " + newUid);

						final String createResult = createCustomer(siteoneRequestAccountData, siteOneWsCreateCustomerResponseData,
								true);
						if (createResult.equalsIgnoreCase(SiteoneCoreConstants.RESPONSE_SUCCESS))
						{
							response.setStatus(String.valueOf(HttpStatus.OK.value()));
							response.setMessage("Created Customer in Ecomm Successfully.");
						}
					}
					else
					{
						if (b2bCustomerModel.getUuid() == null && null != siteoneWsUpdateAccountInfoWsDTO.getCorrelationId())
						{
							b2bCustomerModel.setUuid(siteoneWsUpdateAccountInfoWsDTO.getCorrelationId());
						}
						final Set<PrincipalGroupModel> groups = b2bCustomerModel.getGroups();
						if (siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getIsOnlineAdmin() != null)
						{
							if (Boolean.FALSE.equals(siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getIsOnlineAdmin())
									&& siteoneWsUpdateAccountInfoWsDTO.getOperationType().equalsIgnoreCase(OPERATION_TYPE_ADMIN_ONLINE)
									&& b2bCustomerModel.getGroups().stream()
											.anyMatch(group -> group.getUid().equalsIgnoreCase("b2badmingroup")))
							{
								final Set<PrincipalGroupModel> updatedGroups = removeUsergroupFromGroups("b2badmingroup", groups);
								updatedGroups.add(getUserService().getUserGroupForUID(B2BConstants.B2BCUSTOMERGROUP));
								b2bCustomerModel.setGroups(updatedGroups);
								LOG.error("inside admin to team member");


							}
							else if (Boolean.FALSE.equals(siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getIsOnlineAdmin())
									&& b2bCustomerModel.getGroups().stream()
											.anyMatch(group -> group.getUid().equalsIgnoreCase("b2badmingroup")))
							{
								final Set<PrincipalGroupModel> updatedGroups = removeUsergroupFromGroups("b2badmingroup", groups);
								updatedGroups.add(getUserService().getUserGroupForUID(B2BConstants.B2BCUSTOMERGROUP));
								b2bCustomerModel.setGroups(updatedGroups);
							}
							else if (Boolean.TRUE.equals(siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getIsOnlineAdmin())
									&& b2bCustomerModel.getGroups().stream()
											.anyMatch(group -> group.getUid().equalsIgnoreCase("b2bcustomergroup")))
							{
								final Set<PrincipalGroupModel> updatedGroups = removeUsergroupFromGroups("b2bcustomergroup", groups);
								updatedGroups.add(getUserService().getUserGroupForUID(B2BConstants.B2BADMINGROUP));
								b2bCustomerModel.setGroups(updatedGroups);
							}
						}

						// Update B2BUnit
						if (StringUtils.isNotBlank(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getCompanyName()))
						{
							b2bUnitModel.setLocName(siteoneRequestAccountData.getCompanyName(), getI18nService().getCurrentLocale());
							b2bUnitModel.setName(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getCompanyName());
						}
						if (StringUtils.isNotBlank(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getTradeClass()))
						{
							b2bUnitModel.setTradeClass(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getTradeClass());
						}
						if (StringUtils.isNotBlank(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getSubTradeClass()))
						{
							b2bUnitModel.setSubTradeClass(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getSubTradeClass());
						}
						if (StringUtils.isNotBlank(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getCreditCode()))
						{
							b2bUnitModel.setCreditCode(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getCreditCode());
						}
						if (StringUtils.isNotBlank(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getCreditTerms()))
						{
							b2bUnitModel.setCreditTermCode(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getCreditTerms());
						}
						if (StringUtils.isNotBlank(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getAcctGroupCode()))
						{
							b2bUnitModel.setAccountGroupCode(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getAcctGroupCode());
						}

						if (siteoneWsUpdateAccountInfoWsDTO.getOperationType().equalsIgnoreCase(OPERATION_TYPE_CONTACT_ONLINE)
								|| siteoneWsUpdateAccountInfoWsDTO.getOperationType().equalsIgnoreCase(OPERATION_TYPE_SHIP_TO_ONLINE)) {
							if (Boolean.TRUE.equals(siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getOnlineAccessEnabled())) {
								b2bCustomerModel.setFirstName(siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getContactFirstName());
								b2bCustomerModel.setLastName(siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getContactLastName());
								b2bCustomerModel.setName(siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getContactFirstName() + " " +
										siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getContactLastName());
								b2bCustomerModel.setActive(true);
								b2bCustomerModel.setLoginDisabled(Boolean.FALSE);
								b2bUnitModel.setActive(true);
								b2bUnitModel.setIsOrderingAccount(true);
								b2bUnitModel.setIsBillingAccount(true);
								b2bCustomerModel.setPayBillOnline(siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getPayBillOnline());
								b2bUnitModel.setPayBillOnline(siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getPayBillOnline());
								String result = "";
								if (b2bCustomerModel.getGuid() != null)
								{
									result = SiteoneCoreConstants.RESPONSE_SUCCESS;
									OktaCreateOrUpdateUserResponseData responseData = null;
									boolean success = false;
									int count = 0;
									final int maxTries = Config.getInt("hybris.realtime.account.maxRetryCount", 1);
									while (!success && count++ < maxTries) {
										try {
											if (!getOktaAPI().isUserInOkta(b2bCustomerModel.getUid())) {
												responseData = getOktaAPI().createUser(b2bCustomerModel);
											} else {
												responseData = getOktaAPI().getUser(b2bCustomerModel.getUid());
												if (SiteoneCoreConstants.OKTA_USER_STATUS_SUSPENDED.equalsIgnoreCase(responseData.getStatus())) {
													if (getOktaAPI().unSuspendUser(b2bCustomerModel.getUid())) {
														responseData = getOktaAPI().getUser(b2bCustomerModel.getUid());
													}
												}
											}
											success = true;
										}
									 catch (OktaUnknownUserException oktaUnknownUserException) {
											LOG.error("OktaUnknownUserException: Customer " + b2bCustomerModel.getUid() + " does not exist in okta and could not be created - " + responseData.getErrorId());
										}
										catch (final ResourceAccessException resourceAccessException) {
											LOG.error("Not able to establish connection with okta to get contact", resourceAccessException);
											throw new ServiceUnavailableException("404");
										}
									}
									if (null != responseData) {
										if (!SiteoneCoreConstants.OKTA_USER_STATUS_ACTIVE.equalsIgnoreCase(responseData.getStatus()) && !SiteoneCoreConstants.OKTA_USER_STATUS_SUSPENDED.equalsIgnoreCase(responseData.getStatus())) {
											((SiteOneCustomerAccountService) getCustomerAccountService()).createPassword(b2bCustomerModel);
										}
									}
									response.setStatus(String.valueOf(HttpStatus.OK.value()));
									response.setMessage("Enabled Online Access of the Customer");
								}
							} else {
								try {
									if (!getOktaAPI().suspendUser(b2bCustomerModel.getUid())) {
										LOG.error("Not able to suspend user in okta while disabling online access for " + b2bCustomerModel.getUid());
									}
								}
								catch (final ResourceAccessException resourceAccessException) {
									LOG.error("Not able to establish connection with okta to get contact", resourceAccessException);
									throw new ServiceUnavailableException("404");
								}
								b2bCustomerModel.setActive(false);
								b2bCustomerModel.setPayBillOnline(false);
								response.setStatus(String.valueOf(HttpStatus.OK.value()));
								response.setMessage("Disabled Online Access of the Customer");
							}
						} else if (b2bCustomerModel.getActive()
								&& siteoneWsUpdateAccountInfoWsDTO.getOperationType().equalsIgnoreCase(OPERATION_TYPE_PAYBILL_ONLINE)) {
							b2bCustomerModel.setFirstName(siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getContactFirstName());
							b2bCustomerModel.setLastName(siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getContactLastName());
							b2bCustomerModel.setName(siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getContactFirstName() + " " +
									siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getContactLastName());
							b2bCustomerModel.setActive(true);
							b2bUnitModel.setActive(true);
							if (getOktaAPI().isUserInOkta(b2bCustomerModel.getUid())) {
								OktaCreateOrUpdateUserResponseData oktaUserResponseData = getOktaAPI().getUser(b2bCustomerModel.getUid());
								String groupId = Config.getString(SiteoneintegrationConstants.OKTA_BILLTRUST_GROUPID, StringUtils.EMPTY);
								String userOktaId = oktaUserResponseData.getId();
								if (StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(userOktaId)) {
									if (Boolean.TRUE.equals((siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getPayBillOnline()))) {
										b2bCustomerModel.setPayBillOnline(true);
										b2bUnitModel.setPayBillOnline(true);
										response.setStatus(String.valueOf(HttpStatus.OK.value()));
										response.setMessage("Updated PayBill Online");
										b2bCustomerModel.setLoginDisabled(Boolean.FALSE);
										getOktaAPI().addUserToGroup(groupId, userOktaId);

									} else {
										b2bCustomerModel.setPayBillOnline(false);
										b2bUnitModel.setPayBillOnline(false);
										response.setStatus(String.valueOf(HttpStatus.OK.value()));
										response.setMessage("Disabled PayBill Online");
										getOktaAPI().removeUserFromGroup(groupId, userOktaId);

									}
								}
							}
							else {
								if (Boolean.TRUE.equals((siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getPayBillOnline()))) {
									OktaCreateOrUpdateUserResponseData responseData = null;
									b2bCustomerModel.setPayBillOnline(true);
									b2bUnitModel.setPayBillOnline(true);
									b2bCustomerModel.setLoginDisabled(Boolean.FALSE);
									responseData = getOktaAPI().createUser(b2bCustomerModel);
									if (null != responseData) {
										if (!SiteoneCoreConstants.OKTA_USER_STATUS_ACTIVE.equalsIgnoreCase(responseData.getStatus()) && !SiteoneCoreConstants.OKTA_USER_STATUS_SUSPENDED.equalsIgnoreCase(responseData.getStatus())) {
											((SiteOneCustomerAccountService) getCustomerAccountService()).createPassword(b2bCustomerModel);
										}
									}
									response.setStatus(String.valueOf(HttpStatus.OK.value()));
									response.setMessage("Updated PayBill Online");
								}
								else {
									b2bCustomerModel.setPayBillOnline(false);
									b2bUnitModel.setPayBillOnline(false);
									response.setStatus(String.valueOf(HttpStatus.OK.value()));
									response.setMessage("Disabled PayBill Online");
								}
							}
						}
						else if (Boolean.FALSE.equals(siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getIsOnlineAdmin())
								&& siteoneWsUpdateAccountInfoWsDTO.getOperationType().equalsIgnoreCase(OPERATION_TYPE_ADMIN_ONLINE))
						{
							response.setStatus(String.valueOf(HttpStatus.OK.value()));
							response.setMessage("Admin to Team Member Group Updated for the Customer");
						}
						else
						{
							response.setStatus(String.valueOf(HttpStatus.OK.value()));
							response.setMessage("Customer doesn't have online access in Ecomm");

						}
					}
				}
				modelService.save(b2bCustomerModel);
				modelService.refresh(b2bCustomerModel);
				modelService.save(b2bUnitModel);
				modelService.refresh(b2bUnitModel);
				LOG.error("customer saved");
				return response;
			}
			else
			{
				try
				{
					final SiteoneRequestAccountModel siteoneRequestAccountModel = new SiteoneRequestAccountModel();
					siteoneRequestAccountReverseConverter.convert(siteoneRequestAccountData, siteoneRequestAccountModel);
					final RequestAccountEvent event = new RequestAccountEvent();

					b2bUnitModel.setActive(Boolean.TRUE);
					b2bCustomerModel = b2bUserFacade.createCustomer(siteoneRequestAccountData, siteOneWsCreateCustomerResponseData,
							b2bUnitModel);

					// Update B2BUnit
					if (StringUtils.isNotBlank(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getCompanyName()))
					{
						b2bUnitModel.setLocName(siteoneRequestAccountData.getCompanyName(), getI18nService().getCurrentLocale());
						b2bUnitModel.setName(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getCompanyName());
					}
					if (StringUtils.isNotBlank(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getTradeClass()))
					{
						b2bUnitModel.setTradeClass(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getTradeClass());
					}
					if (StringUtils.isNotBlank(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getSubTradeClass()))
					{
						b2bUnitModel.setSubTradeClass(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getSubTradeClass());
					}
					if (StringUtils.isNotBlank(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getCreditCode()))
					{
						b2bUnitModel.setCreditCode(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getCreditCode());
					}
					if (StringUtils.isNotBlank(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getCreditTerms()))
					{
						b2bUnitModel.setCreditTermCode(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getCreditTerms());
					}
					if (StringUtils.isNotBlank(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getAcctGroupCode()))
					{
						b2bUnitModel.setAccountGroupCode(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getAcctGroupCode());
					}

					try
					{
						b2bCustomerModel.setPayBillOnline(siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getPayBillOnline());

						if (siteOneWsCreateCustomerResponseData.getResult().getBranchInfo() != null) {

							PointOfServiceModel preferredStore = new PointOfServiceModel();

							preferredStore = storeFinderService
									.getStoreForId(siteOneWsCreateCustomerResponseData.getResult().getBranchInfo().getNumber());

							if (preferredStore != null && StringUtils.isNotEmpty(preferredStore.getStoreId())) {
								b2bCustomerModel.setPreferredStore(preferredStore);
								b2bCustomerModel.setHomeBranch(preferredStore.getStoreId());
							}
						}
						/*
						 * final HashSet<PointOfServiceModel> stores = new HashSet<>(); stores.add(preferredStore);
						 * b2bCustomerModel.setStores(stores);
						 */
						modelService.save(b2bCustomerModel);
						modelService.refresh(b2bCustomerModel);
						modelService.save(b2bUnitModel);
						modelService.refresh(b2bUnitModel);
					}
					catch (final Exception ex)
					{
						LOG.error("Exception Occure on saving store: " + ex);
					}

					String result = "";
					if (b2bCustomerModel.getGuid() != null)
					{
						result = SiteoneCoreConstants.RESPONSE_SUCCESS;//call to okta api to create a user at okta.
						OktaCreateOrUpdateUserResponseData responseData = null;
						boolean success = false;
						int count = 0;
						final int maxTries = Config.getInt("hybris.realtime.account.maxRetryCount", 1);
						while (!success && count++ < maxTries)
						{
							try
							{
								responseData = getOktaAPI().createUser(b2bCustomerModel);
								success = true;
							}
							catch (final ResourceAccessException resourceAccessException)
							{
								LOG.error("Not able to establish connection with okta to create contact", resourceAccessException);
								throw new ServiceUnavailableException("404");
							}
						}
						if (null != responseData)
						{
							if (SiteoneCoreConstants.OKTA_USER_STATUS_STAGED.equalsIgnoreCase(responseData.getStatus()))
							{
								//Send create password email to user
								if (!siteoneWsUpdateAccountInfoWsDTO.getOperationType().equalsIgnoreCase(OPERATION_TYPE_PAYBILL_ONLINE))
								{
									((SiteOneCustomerAccountService) getCustomerAccountService()).createPassword(b2bCustomerModel);
								}
								if ("CONTRACTOR".equalsIgnoreCase(siteoneRequestAccountData.getTypeOfCustomer()))
								{
									event.setAccountNumber(
											siteOneWsCreateCustomerResponseData.getResult().getAccountInfo().getAccountNumber());
									event.setBranchManagerEmail(
											siteOneWsCreateCustomerResponseData.getResult().getBranchInfo().getManagerEmail());
									event.setBranchNotification(Boolean.TRUE);
									eventService.publishEvent(initializeEvent(event, siteoneRequestAccountModel));
								}
							}
						}
						else
						{
							LOG.error("Not able to create contact in Okta");
							throw new ContactNotCreatedOrUpdatedInOktaException("Contact not created in Okta");
						}
					}

					response.setStatus(String.valueOf(HttpStatus.OK.value()));
					response.setMessage("Created Customer in Ecomm");
					return response;
				}
				catch (final Exception ex)
				{
					LOG.error(ex);
					response.setStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
					response.setMessage(ex.getMessage());
				}

			}

		}
		else
		{
			final SiteoneRequestAccountModel siteoneRequestAccountModel = new SiteoneRequestAccountModel();
			siteoneRequestAccountReverseConverter.convert(siteoneRequestAccountData, siteoneRequestAccountModel);

			b2bUnitModel = ((SiteOneB2BUnitFacade) b2bUnitFacade).updateB2BUnit(siteoneRequestAccountModel,
					siteOneWsCreateCustomerResponseData, true);

			b2bCustomerModel.setDefaultB2BUnit(b2bUnitModel);
			b2bCustomerModel.setActive(true);
			b2bCustomerModel.setPayBillOnline(siteoneWsUpdateAccountInfoWsDTO.getContactInfo().getPayBillOnline());
			modelService.save(b2bCustomerModel);

			response.setStatus(String.valueOf(HttpStatus.OK.value()));
			response.setMessage("Customer created successfully");
			return response;

		}

		//				USER not exists in Hybris
		response.setStatus(String.valueOf(HttpStatus.OK.value()));
		response.setMessage("Unable to Create customer in Ecomm");
		return response;
	}

	private SiteOneWsCreateCustomerResponseData populateCustomerResponseData(
			final SiteOneWsSelfServeOnlineAccessResponseData selfServerOnline)
	{
		final SiteOneWsCreateCustomerResponseData customerData = new SiteOneWsCreateCustomerResponseData();
		customerData.setCorrelationId(selfServerOnline.getCorrelationId());
		final SiteOneWsResultResponseData results = new SiteOneWsResultResponseData();
		final SiteOneWsAccountInfoData accountInfo = new SiteOneWsAccountInfoData();
		accountInfo.setAccountNumber(selfServerOnline.getResult().getAccountInfo().getAccountNumber());
		accountInfo.setBillingAddressID(selfServerOnline.getResult().getAccountInfo().getBillingAddressID());
		accountInfo.setCustTreeNodeID(selfServerOnline.getResult().getAccountInfo().getCustTreeNodeID());
		accountInfo.setDivisionId(selfServerOnline.getResult().getAccountInfo().getDivisionId().toString());
		accountInfo.setPhoneID(selfServerOnline.getResult().getAccountInfo().getPhoneID());
		results.setAccountInfo(accountInfo);
		final SiteOneWsBranchInfoData branchInfo = new SiteOneWsBranchInfoData();
		branchInfo.setManagerEmail(selfServerOnline.getResult().getBranchInfo().getManagerEmail());
		branchInfo.setManagerName(selfServerOnline.getResult().getBranchInfo().getManagerName());
		branchInfo.setName(selfServerOnline.getResult().getBranchInfo().getName());
		branchInfo.setNumber(selfServerOnline.getResult().getBranchInfo().getNumber());
		branchInfo.setPrimaryPhone(selfServerOnline.getResult().getBranchInfo().getPrimaryPhone());
		results.setBranchInfo(branchInfo);
		final SiteOneWsContactInfoData contactInfo = new SiteOneWsContactInfoData();
		contactInfo.setContactEmail(selfServerOnline.getResult().getContactInfo().getContactEmail());
		contactInfo.setContactFirstName(selfServerOnline.getResult().getContactInfo().getContactFirstName());
		contactInfo.setContactId(selfServerOnline.getResult().getContactInfo().getContactId());
		contactInfo.setContactLastName(selfServerOnline.getResult().getContactInfo().getContactLastName());
		contactInfo.setIsOnlineAdmin(selfServerOnline.getResult().getContactInfo().getIsOnlineAdmin());
		contactInfo.setOnlineAccessEnabled(selfServerOnline.getResult().getContactInfo().getOnlineAccessEnabled());
		contactInfo.setPayBillOnline(selfServerOnline.getResult().getContactInfo().getPayBillOnline());
		results.setContactInfo(contactInfo);
		final SiteOneWsPriceClassInfoData priceInfo = new SiteOneWsPriceClassInfoData();
		priceInfo.setNurseryClassCode(selfServerOnline.getResult().getPriceClass().getNurseryClassCode());
		priceInfo.setPriceClassCode(selfServerOnline.getResult().getPriceClass().getPriceClassCode());
		results.setPriceClass(priceInfo);
		customerData.setResult(results);
		return customerData;
	}

	private SiteoneRequestAccountData populateSiteoneAccountRequestData(
			final SiteOneWsSelfServeOnlineAccessResponseData siteoneWsUpdateAccountInfoWsDTO)
	{
		final SiteoneRequestAccountData siteoneRequestAccountData = new SiteoneRequestAccountData();
		siteoneRequestAccountData.setAccountNumber(siteoneWsUpdateAccountInfoWsDTO.getResult().getAccountInfo().getAccountNumber());
		siteoneRequestAccountData.setAddressLine1(siteoneWsUpdateAccountInfoWsDTO.getResult().getAddressInfo().getAddressLine1());
		siteoneRequestAccountData.setAddressLine2(siteoneWsUpdateAccountInfoWsDTO.getResult().getAddressInfo().getAddressLine2());
		siteoneRequestAccountData.setCity(siteoneWsUpdateAccountInfoWsDTO.getResult().getAddressInfo().getCity());
		siteoneRequestAccountData.setCompanyName(siteoneWsUpdateAccountInfoWsDTO.getResult().getAccountInfo().getCompanyName());
		siteoneRequestAccountData.setContrEmpCount("10");
		siteoneRequestAccountData.setContrPrimaryBusiness(getPrimaryBuisness(siteoneWsUpdateAccountInfoWsDTO));
		siteoneRequestAccountData.setContrYearsInBusiness("3");
		siteoneRequestAccountData.setEmailAddress(siteoneWsUpdateAccountInfoWsDTO.getResult().getContactInfo().getContactEmail());
		siteoneRequestAccountData.setFirstName(siteoneWsUpdateAccountInfoWsDTO.getResult().getContactInfo().getContactFirstName());
		siteoneRequestAccountData.setHasAccountNumber(getHasAccountNumber(siteoneWsUpdateAccountInfoWsDTO));
		siteoneRequestAccountData
				.setIsAccountOwner(siteoneWsUpdateAccountInfoWsDTO.getResult().getContactInfo().getIsAccountOwner());
		siteoneRequestAccountData.setLanguagePreference("English");
		siteoneRequestAccountData.setLastName(siteoneWsUpdateAccountInfoWsDTO.getResult().getContactInfo().getContactLastName());
		siteoneRequestAccountData.setPhoneNumber(null);
		siteoneRequestAccountData.setState(getUpdateState(siteoneWsUpdateAccountInfoWsDTO));
		if (null != siteoneWsUpdateAccountInfoWsDTO.getResult().getAccountInfo().getTradeClass())
		{
			siteoneRequestAccountData.setTypeOfCustomer(
					getTypeOfCustomer(siteoneWsUpdateAccountInfoWsDTO.getResult().getAccountInfo().getTradeClass()));
		}
		siteoneRequestAccountData.setUuid(siteoneWsUpdateAccountInfoWsDTO.getCorrelationId());
		siteoneRequestAccountData.setZipcode(siteoneWsUpdateAccountInfoWsDTO.getResult().getAddressInfo().getZip());
		siteoneRequestAccountData.setStoreNumber(siteoneWsUpdateAccountInfoWsDTO.getResult().getBranchInfo().getNumber());
		siteoneRequestAccountData.setEnrollInPartnersProgram(false);
		return siteoneRequestAccountData;
	}

	private String getUpdateState(final SiteOneWsSelfServeOnlineAccessResponseData siteoneWsUpdateAccountInfoWsDTO)
	{
		final String countryCode = siteoneWsUpdateAccountInfoWsDTO.getResult().getAccountInfo().getDivisionId().toString()
				.equalsIgnoreCase("1") ? "US" : "CA";
		return countryCode + "-" + siteoneWsUpdateAccountInfoWsDTO.getResult().getAddressInfo().getState();
	}

	private String getTypeOfCustomer(final String tradeClass)
	{

		return ((getIsHomeOwner(tradeClass)) ? SiteoneintegrationConstants.CREATE_CUSTOMER_RETAIL
				: SiteoneintegrationConstants.CREATE_CUSTOMER_CONTRACTOR);
	}

	private boolean getIsHomeOwner(final String tradeClass)
	{
		final String HOME_OWNER_CODE = configurationService.getConfiguration().getString(HOMEOWNER_CODE);
		return ((tradeClass.equalsIgnoreCase(HOME_OWNER_CODE)) ? true : false);
	}

	private boolean getHasAccountNumber(final SiteOneWsSelfServeOnlineAccessResponseData siteoneWsUpdateAccountInfoWsDTO)
	{
		return (siteoneWsUpdateAccountInfoWsDTO.getResult().getAccountInfo().getAccountNumber() != null) ? true : false;
	}

	private String getPrimaryBuisness(final SiteOneWsSelfServeOnlineAccessResponseData siteoneWsUpdateAccountInfoWsDTO)
	{
		final Map<String, SiteOneContrPrimaryBusinessListDTO> primaryBusinessDTOMap = getPrimaryBusinessList();
		final SiteoneWsUpdateAccountInfoWsDTO updateAccountInfo = new SiteoneWsUpdateAccountInfoWsDTO();
		final SiteoneAccountInfoWsDTO accountInfo = new SiteoneAccountInfoWsDTO();
		if (null != siteoneWsUpdateAccountInfoWsDTO.getResult().getAccountInfo().getTradeClass())
		{
			accountInfo.setTradeClass(siteoneWsUpdateAccountInfoWsDTO.getResult().getAccountInfo().getTradeClass());
		}
		accountInfo.setSubTradeClass(siteoneWsUpdateAccountInfoWsDTO.getResult().getAccountInfo().getSubTradeClass());
		updateAccountInfo.setAccountInfo(accountInfo);
		return getContrPrimaryBuisness(primaryBusinessDTOMap, updateAccountInfo);

	}

	public Map<String, SiteOneContrPrimaryBusinessListDTO> getPrimaryBusinessList()
	{
		final Map<String, List<SiteOneContrPrimaryBusinessData>> primaryBusinessMap = siteoneContrPrimaryBusinessMapFacade
				.getPrimaryBusinessMap();
		final Map<String, SiteOneContrPrimaryBusinessListDTO> primaryBusinessDTOMap = new HashMap<>();

		final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		mapperFactory.classMap(SiteOneContrPrimaryBusinessData.class, SiteOneContrPrimaryBusinessWsDTO.class);
		final MapperFacade mapper = mapperFactory.getMapperFacade();

		primaryBusinessMap.entrySet().forEach(map -> {
			final List<SiteOneContrPrimaryBusinessWsDTO> primaryBusinessDTOList = mapper.mapAsList(map.getValue(),
					SiteOneContrPrimaryBusinessWsDTO.class);
			final SiteOneContrPrimaryBusinessListDTO primaryBusinessListDTO = new SiteOneContrPrimaryBusinessListDTO();
			primaryBusinessListDTO.setSubBusinessList(primaryBusinessDTOList);
			primaryBusinessDTOMap.put(map.getKey(), primaryBusinessListDTO);
		});

		return primaryBusinessDTOMap;
	}

	private String getParentUnitID(final SiteoneWsUpdateAccountInfoWsDTO siteoneWsUpdateAccountInfoWsDTO)
	{
		final String b2bUnitIdSuffix = siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getDivisionId().toString()
				.equalsIgnoreCase("1") ? "_US" : "_CA";
		return (siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getAccountNumber() + b2bUnitIdSuffix);
	}

	private String getParentUnitID(final SiteOneWsCreateCustomerResponseData siteOneWsCreateCustomerResponseData)
	{
		final String b2bUnitIdSuffix = siteOneWsCreateCustomerResponseData.getResult().getAccountInfo().getDivisionId()
				.equalsIgnoreCase("1") ? "_US" : "_CA";
		return (siteOneWsCreateCustomerResponseData.getResult().getAccountInfo().getAccountNumber() + b2bUnitIdSuffix);
	}

	private String getUnitParentIDPerShipTo(final SiteoneWsUpdateAccountInfoWsDTO siteoneWsUpdateAccountInfoWsDTO)
	{
		final String b2bUnitIdSuffix = siteoneWsUpdateAccountInfoWsDTO.getParentAccountInfo().getDivisionId().toString()
				.equalsIgnoreCase("1") ? "_US" : "_CA";
		return (siteoneWsUpdateAccountInfoWsDTO.getParentAccountInfo().getAccountNumber() + b2bUnitIdSuffix);
	}

	protected UserService getUserService()
	{
		return userService;
	}

	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	@Override
	public String getContrPrimaryBuisness(final Map<String, SiteOneContrPrimaryBusinessListDTO> primaryBusinessDTOMap,
			final SiteoneWsUpdateAccountInfoWsDTO siteoneWsUpdateAccountInfoWsDTO)
	{
		String[] tradeClass;
		if (null != siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getTradeClass())
		{
			if (!(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getTradeClass())
					.equalsIgnoreCase(configurationService.getConfiguration().getString(HOMEOWNER_CODE)))
			{
				try
				{
					for (final String entryKey : primaryBusinessDTOMap.keySet())
					{

						tradeClass = entryKey.split("\\|");
						if (tradeClass[0].equalsIgnoreCase(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getTradeClass()))
						{
							final SiteOneContrPrimaryBusinessListDTO siteOneContrPrimaryBusinessListDTO = primaryBusinessDTOMap
									.get(entryKey);

							for (final SiteOneContrPrimaryBusinessWsDTO entryValue : siteOneContrPrimaryBusinessListDTO
									.getSubBusinessList())
							{
								if (entryValue.getCode()
										.equalsIgnoreCase(siteoneWsUpdateAccountInfoWsDTO.getAccountInfo().getSubTradeClass()))
								{
									return tradeClass[0] + "/" + entryValue.getCode() + "|" + tradeClass[1] + "/"
											+ entryValue.getDescription();
								}
							}
						}
					}
				}
				catch (final Exception ex)
				{
					LOG.info(ex);
				}
			}
		}
		return null;
	}

	@Override
	public void logRequestError(final Object siteoneWsUpdateAccountInfoWsDTO, final CreateCustomerResponseWsDTO response)
	{
		siteoneRequestAccountService.exportRealtimeLogFile(siteoneWsUpdateAccountInfoWsDTO, response);
	}

	@Override
	public CreateCustomerResponseWsDTO validateRequest(final SiteoneWsUpdateAccountInfoWsDTO siteoneWsUpdateAccountInfoWsDTO)
	{

		final CreateCustomerResponseWsDTO response = new CreateCustomerResponseWsDTO();
		if (siteoneWsUpdateAccountInfoWsDTO == null)
		{
			response.setStatus(String.valueOf(HttpStatus.BAD_REQUEST.value()));
			response.setMessage("The request is empty");
			return response;
		}
		else
		{
			response.setCorrelationId(siteoneWsUpdateAccountInfoWsDTO.getCorrelationId());
			if (siteoneWsUpdateAccountInfoWsDTO.getOperationType().equalsIgnoreCase(OPERATION_TYPE_SHIP_TO_ONLINE)
					&& siteoneWsUpdateAccountInfoWsDTO.getParentAccountInfo() == null)
			{
				response.setStatus(String.valueOf(HttpStatus.BAD_REQUEST.value()));
				response.setMessage("ParentAccountInfo is empty in the request");
			}
			else if (siteoneWsUpdateAccountInfoWsDTO.getAccountInfo() == null)
			{
				response.setStatus(String.valueOf(HttpStatus.BAD_REQUEST.value()));
				response.setMessage("AccountInfo is empty in the request");
			}
			else if (siteoneWsUpdateAccountInfoWsDTO.getAddressInfo() == null)
			{
				response.setStatus(String.valueOf(HttpStatus.BAD_REQUEST.value()));
				response.setMessage("AddressInfo is empty in the request");
			}
			else if (siteoneWsUpdateAccountInfoWsDTO.getContactInfo() == null)
			{
				response.setStatus(String.valueOf(HttpStatus.BAD_REQUEST.value()));
				response.setMessage("ContactInfo is empty in the request");
			}
			else
			{
				response.setStatus(String.valueOf(HttpStatus.OK.value()));
			}
			return response;
		}
	}

	@Override
	public String notifyQuoteEmailStatus(final SiteoneWsNotifyQuoteWsDTO siteoneWsNotifyQuoteWsDTO)
	{
		final List<String> b2bActiveAdminGroupUsers = new ArrayList<>();
		final List<B2BCustomerModel> allAdminGroups = new ArrayList<>();
		final B2BUnitModel unit = getUnitForCustomer(siteoneWsNotifyQuoteWsDTO.getCustomerNumber());
		if (unit != null)
		{
			final NotifyQuoteStatusEvent event = new NotifyQuoteStatusEvent();
			event.setAccountAdminEmail(siteoneWsNotifyQuoteWsDTO.getNotificationEmail());
			if (event.getAccountAdminEmail().equalsIgnoreCase("All"))
			{
				final List<B2BCustomerModel> b2bAdminGroupUsers = new ArrayList(
						b2bUnitService.getUsersOfUserGroup(unit, B2BConstants.B2BADMINGROUP, true));
				allAdminGroups.addAll(b2bAdminGroupUsers);
				if (unit.getReportingOrganization() != null && unit != unit.getReportingOrganization())
				{
					final List<B2BCustomerModel> b2bAllAdminGroupUsers = new ArrayList(
							b2bUnitService.getUsersOfUserGroup(unit.getReportingOrganization(), B2BConstants.B2BADMINGROUP, true));
					allAdminGroups.addAll(b2bAllAdminGroupUsers);
				}
				for (final B2BCustomerModel admins : allAdminGroups)
				{
					if (BooleanUtils.isTrue(admins.getActive()))
					{
						b2bActiveAdminGroupUsers.add(admins.getUid());
					}
				}
			}
			else
			{
				final String admins = event.getAccountAdminEmail();
				final String[] adminMails = admins.split(",");
				for (int i = 0; i < adminMails.length; i++)
				{
					b2bActiveAdminGroupUsers.add(adminMails[i]);
				}
			}
			if (unit.getUid() != null)
			{
				if (unit.getUid().contains(SiteoneCoreConstants.INDEX_OF_US))
				{
					event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_US));
				}
				else
				{
					event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_CA));
				}
			 }
			event.setLanguage(getCommonI18NService().getCurrentLanguage());
			event.setQuoteNumber(siteoneWsNotifyQuoteWsDTO.getQuoteNumber());
			event.setAccountManagerName(siteoneWsNotifyQuoteWsDTO.getAccountManager());
			event.setAccountManagerMobile(siteoneWsNotifyQuoteWsDTO.getAccountManagerPhoneNumber());
			event.setAccountManagerMail(siteoneWsNotifyQuoteWsDTO.getAccountManagerEmail());
			event.setJobName(siteoneWsNotifyQuoteWsDTO.getJobName());
			event.setDateSubmitted(siteoneWsNotifyQuoteWsDTO.getDateSubmitted());
			event.setExpDate(siteoneWsNotifyQuoteWsDTO.getDateExpired());
			event.setToMail(StringUtils.join(b2bActiveAdminGroupUsers, ";"));
			event.setMongoId(siteoneWsNotifyQuoteWsDTO.getMongoId());
			event.setCustomMessage(siteoneWsNotifyQuoteWsDTO.getQuoteReadyMessage());
			eventService.publishEvent(event);
			return unit.getGuid().toLowerCase();
		}
		return null;
	}

	private B2BUnitModel getUnitForCustomer(String customerNumber)
	{
		B2BUnitModel unit = (B2BUnitModel) b2bUnitService.getUnitForUid(customerNumber.concat("_US"));
		if (unit == null)
		{
			unit = (B2BUnitModel) b2bUnitService.getUnitForUid(customerNumber.concat("_CA"));
		}
		return unit;
	}

	@Override
	public StringBuilder decryptData(final InputStream inputStream) throws IOException
	{
		final StringBuilder decryptedData = new StringBuilder();
		final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		try
		{
			final Stream<String> lines = bufferedReader.lines();
			final String header = "Encrypted Value" + "," + "Decrypted Mail";
			decryptedData.append(header + "\n");
			lines.filter(line -> StringUtils.isNotBlank(line)).forEach(line -> {
				final String[] listAttributes = line.split(",");
				if (!StringUtils.isEmpty(listAttributes[SiteoneFacadesConstants.ZERO]))
				{
					final String encryptedMail = StringUtils.trim(listAttributes[SiteoneFacadesConstants.ZERO]);
					if (!encryptedMail.equalsIgnoreCase("Encrypted Value"))
					{
						final String decryptedMail = decrypt(encryptedMail);
						final String product = encryptedMail + "," + decryptedMail;
						decryptedData.append(product + "\n");
					}
				}
			});
		}
		finally
		{
			bufferedReader.close();
		}

		return decryptedData;
	}
	public String decrypt(final String encryptedString)
	{
		final String secret = Config.getString("encryption.aes.secret", null);
		final byte[] decodedKey = Base64.getDecoder().decode(secret);
		try
		{
			final Cipher cipher = Cipher.getInstance("AES");
			final SecretKey originalKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
			cipher.init(Cipher.DECRYPT_MODE, originalKey);
			final byte[] cipherText = cipher.doFinal(Base64.getDecoder().decode(encryptedString));
			return new String(cipherText);
		}
		catch (final Exception e)
		{
			LOG.error("Error occured while decrypting data", e);
			return "Unable to decrypt";
		}
	}

	public String setCustomerNoWithDivision(final String customerNumber)
	{
		final BaseSiteModel basesite = baseSiteService.getCurrentBaseSite();
		if (basesite.getUid().equalsIgnoreCase("siteone-us"))
		{
			return customerNumber.concat("_US");
		}
		else if(basesite.getUid().equalsIgnoreCase("siteone-ca"))
		{
			return customerNumber.concat("_CA");
		}
		else {
			return customerNumber.concat("_US");
		}
	}
	 
		/**
		 * @return the siteoneRequestAccountReverseConverter
		 */
		public Converter<SiteoneRequestAccountData, SiteoneRequestAccountModel> getSiteoneRequestAccountReverseConverter()
		{
			return siteoneRequestAccountReverseConverter;
		}

		/**
		 * @param siteoneRequestAccountReverseConverter the siteoneRequestAccountReverseConverter to set
		 */
		public void setSiteoneRequestAccountReverseConverter(
				Converter<SiteoneRequestAccountData, SiteoneRequestAccountModel> siteoneRequestAccountReverseConverter)
		{
			this.siteoneRequestAccountReverseConverter = siteoneRequestAccountReverseConverter;
		}

		/**
		 * @return the siteOneCreateCustomerWebService
		 */
		public SiteOneCreateCustomerWebService getSiteOneCreateCustomerWebService()
		{
			return siteOneCreateCustomerWebService;
		}

		/**
		 * @param siteOneCreateCustomerWebService the siteOneCreateCustomerWebService to set
		 */
		public void setSiteOneCreateCustomerWebService(SiteOneCreateCustomerWebService siteOneCreateCustomerWebService)
		{
			this.siteOneCreateCustomerWebService = siteOneCreateCustomerWebService;
		}

		/**
		 * @return the siteOneFeatureSwitchCacheService
		 */
		public SiteOneFeatureSwitchCacheService getSiteOneFeatureSwitchCacheService()
		{
			return siteOneFeatureSwitchCacheService;
		}

		/**
		 * @param siteOneFeatureSwitchCacheService the siteOneFeatureSwitchCacheService to set
		 */
		public void setSiteOneFeatureSwitchCacheService(SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService)
		{
			this.siteOneFeatureSwitchCacheService = siteOneFeatureSwitchCacheService;
		}
		
		/**
		 * @return the baseSiteService
		 */
		public BaseSiteService getBaseSiteService()
		{
			return baseSiteService;
		}

		/**
		 * @param baseSiteService the baseSiteService to set
		 */
		public void setBaseSiteService(BaseSiteService baseSiteService)
		{
			this.baseSiteService = baseSiteService;
		}
		
		/**
		 * @return the eventService
		 */
		public EventService getEventService()
		{
			return eventService;
		}

		/**
		 * @param eventService the eventService to set
		 */
		public void setEventService(EventService eventService)
		{
			this.eventService = eventService;
		}
		
		/**
		 * @return the storeFinderService
		 */
		public SiteOneStoreFinderService getStoreFinderService()
		{
			return storeFinderService;
		}

		/**
		 * @param storeFinderService the storeFinderService to set
		 */
		public void setStoreFinderService(SiteOneStoreFinderService storeFinderService)
		{
			this.storeFinderService = storeFinderService;
		}
		
		/**
		 * @return the b2bCustomerService
		 */
		public B2BCustomerService getB2bCustomerService()
		{
			return b2bCustomerService;
		}

		/**
		 * @param b2bCustomerService the b2bCustomerService to set
		 */
		public void setB2bCustomerService(B2BCustomerService b2bCustomerService)
		{
			this.b2bCustomerService = b2bCustomerService;
		}
		
		/**
		 * @return the baseStoreService
		 */
		public BaseStoreService getBaseStoreService()
		{
			return baseStoreService;
		}

		/**
		 * @param baseStoreService the baseStoreService to set
		 */
		public void setBaseStoreService(BaseStoreService baseStoreService)
		{
			this.baseStoreService = baseStoreService;
		}
}
