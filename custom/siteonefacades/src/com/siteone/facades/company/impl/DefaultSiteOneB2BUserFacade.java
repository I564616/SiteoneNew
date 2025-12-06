/**
 *
 */
package com.siteone.facades.company.impl;

import de.hybris.platform.b2b.constants.B2BConstants;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.b2bcommercefacades.company.impl.DefaultB2BUserFacade;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.util.CommerceUtils;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.enums.PhoneContactInfoType;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.AbstractContactInfoModel;
import de.hybris.platform.core.model.user.PhoneContactInfoModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.i18n.daos.LanguageDao;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.util.Config;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.web.client.ResourceAccessException;

import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.company.SiteOneB2BCommerceUserService;
import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.customer.SiteOneCustomerAccountService;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.facade.SiteoneRequestAccountData;
import com.siteone.facades.company.SiteOneB2BUserFacade;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.exceptions.ContactAlreadyPresentInBoomiException;
import com.siteone.facades.exceptions.ContactAlreadyPresentInUEException;
import com.siteone.facades.exceptions.ContactNotCreatedOrUpdatedInOktaException;
import com.siteone.facades.exceptions.ContactNotCreatedOrUpdatedInUEException;
import com.siteone.facades.exceptions.ContactNotEnabledOrDisabledInUEException;
import com.siteone.facades.exceptions.DuplicateEmailException;
import com.siteone.facades.exceptions.ServiceUnavailableException;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.customer.createCustomer.data.SiteOneWsCreateCustomerResponseData;
import com.siteone.integration.customer.data.SiteOneWsB2BCustomerResponseData;
import com.siteone.integration.okta.OKTAAPI;
import com.siteone.integration.services.okta.data.OktaCreateOrUpdateUserResponseData;
import com.siteone.integration.services.ue.SiteOneContactWebService;


/**
 * @author 1190626
 *
 */
public class DefaultSiteOneB2BUserFacade extends DefaultB2BUserFacade implements SiteOneB2BUserFacade
{

	private static final Logger LOG = Logger.getLogger(DefaultSiteOneB2BUserFacade.class);
	private static final String BOOMI_PLATFORM = "boomiPlatformLinux";

	private SiteOneContactWebService siteOneContactWebService;
	private Converter<B2BCustomerModel, CustomerData> b2BUserConverter;
	private OKTAAPI oktaAPI;
	private CustomerAccountService customerAccountService;
	private B2BUnitService b2bUnitService;
	private LanguageDao languageDao;

	@Resource(name = "eventService")
	private EventService eventService;

	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "storeFinderService")
	private SiteOneStoreFinderService storeFinderService;

	/**
	 * @return the b2BUserConverter
	 */
	public Converter<B2BCustomerModel, CustomerData> getB2BUserConverter()
	{
		return b2BUserConverter;
	}

	/**
	 * @param b2bUserConverter
	 *           the b2BUserConverter to set
	 */
	public void setB2BUserConverter(final Converter<B2BCustomerModel, CustomerData> b2bUserConverter)
	{
		b2BUserConverter = b2bUserConverter;
	}

	/**
	 * @return the languageDao
	 */
	public LanguageDao getLanguageDao()
	{
		return languageDao;
	}


	/**
	 * @param languageDao
	 *           the languageDao to set
	 */
	public void setLanguageDao(final LanguageDao languageDao)
	{
		this.languageDao = languageDao;
	}


	@Override
	public void updateCustomer(final CustomerData customerData)
			throws ServiceUnavailableException, ContactNotCreatedOrUpdatedInUEException, ContactNotCreatedOrUpdatedInOktaException,
			ContactAlreadyPresentInUEException, DuplicateEmailException
	{
		ServicesUtil.validateParameterNotNullStandardMessage("customerData", customerData);
		Assert.hasText(customerData.getFirstName(), "The field [FirstName] cannot be empty");
		Assert.hasText(customerData.getLastName(), "The field [LastName] cannot be empty");
		B2BCustomerModel customerModel;


		if (StringUtils.isEmpty(customerData.getUid()))
		{
			final boolean isUserAlreadyExist = this.getUserService().isUserExisting(customerData.getEmail().toLowerCase());

			if (isUserAlreadyExist)
			{
				throw new DuplicateEmailException("Email already exist");
			}


			customerModel = (B2BCustomerModel) this.getModelService().create(B2BCustomerModel.class);
		}
		else
		{
			customerModel = this.getUserService().getUserForUID(customerData.getUid().toLowerCase(), B2BCustomerModel.class);
		}

		PhoneContactInfoModel phoneInfo = null;
		if (StringUtils.isNotEmpty(customerData.getContactNumber()))
		{
			final Collection<AbstractContactInfoModel> contactInfos = customerModel.getContactInfos();

			if (CollectionUtils.isNotEmpty(contactInfos))
			{
				for (final AbstractContactInfoModel info : contactInfos)
				{
					if (info instanceof PhoneContactInfoModel)
					{
						phoneInfo = (PhoneContactInfoModel) info;
						if (null == phoneInfo.getPhoneId())
						{
							phoneInfo.setPhoneId(UUID.randomUUID().toString().toUpperCase());
						}
						if (PhoneContactInfoType.MOBILE.equals(phoneInfo.getType()))
						{
							phoneInfo.setPhoneNumber(customerData.getContactNumber());
						}
					}
				}
			}

			else
			{
				phoneInfo = getModelService().create(PhoneContactInfoModel.class);
				phoneInfo.setUser(customerModel);
				phoneInfo.setCode(customerModel.getUid());
				phoneInfo.setPhoneNumber(customerData.getContactNumber());
				phoneInfo.setType(PhoneContactInfoType.MOBILE);
				phoneInfo.setPhoneId(UUID.randomUUID().toString().toUpperCase());
				customerModel.setContactInfos(Collections.singletonList(phoneInfo));
			}

		}

		this.getB2BCustomerReverseConverter().convert(customerData, customerModel);

		updateGroupAndStoreForPunchoutCustomer(customerData, customerModel);

		//Call to contact service to create/update B2B Customer in UE
		if (StringUtils.isEmpty(customerData.getUid()))

		{

			SiteOneWsB2BCustomerResponseData customerResponseData = null;

			try
			{
				customerResponseData = getSiteOneContactWebService().createB2BCustomer(customerModel,
						Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
			}
			catch (final ResourceAccessException resourceAccessException)
			{
				LOG.error("Not able to establish connection with UE to create contact", resourceAccessException);
				throw new ServiceUnavailableException("404");
			}

			if (null == customerResponseData || !customerResponseData.getIsSuccess() || null == customerResponseData.getContactId())
			{

				if (null != customerResponseData && null != customerResponseData.getMessage())
				{
					if (customerResponseData.getMessage().contains("Received EmailAddress")
							&& customerResponseData.getMessage().contains("already exists in UE"))
					{
						LOG.error("Contact already present in UE");
						throw new ContactAlreadyPresentInUEException("contact already present in UE");

					}
					else if (customerResponseData.getMessage().contains("Received email")
							&& customerResponseData.getMessage().contains("already exists for different customer"))
					{
						LOG.error("Contact already present in Boomi");
						throw new ContactAlreadyPresentInBoomiException("contact already present in Boomi");
					}

				}
				LOG.error("Not able to create contact in UE");
				throw new ContactNotCreatedOrUpdatedInUEException("Contact not created in UE");
			}
			else
			{
				customerModel.setGuid(customerResponseData.getContactId().toUpperCase());
			}
			if (customerData.getIsPunchoutFlow() == null || !customerData.getIsPunchoutFlow())
			{
				//call to okta api to create a user at okta.
				OktaCreateOrUpdateUserResponseData responseData = null;
				try
				{
					responseData = getOktaAPI().createUser(customerModel);
				}
				catch (final ResourceAccessException resourceAccessException)
				{
					LOG.error("Not able to establish connection with okta to create contact", resourceAccessException);
					throw new ServiceUnavailableException("404");
				}


				if (null != responseData)
				{
					if (SiteoneCoreConstants.OKTA_USER_STATUS_STAGED.equalsIgnoreCase(responseData.getStatus()))
					{
						//Send create password email to user
						((SiteOneCustomerAccountService) getCustomerAccountService()).createPassword(customerModel);
					}
				}
				else
				{
					LOG.error("Not able to create contact in Okta");
					throw new ContactNotCreatedOrUpdatedInOktaException("Contact not created in Okta");
				}
			}

		}
		else
		{

			SiteOneWsB2BCustomerResponseData customerResponseData = null;
			try
			{
				customerResponseData = getSiteOneContactWebService().updateB2BCustomer(customerModel,
						Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
			}
			catch (final ResourceAccessException resourceAccessException)
			{
				LOG.error("Not able to establish connection with UE to update contact", resourceAccessException);
				throw new ServiceUnavailableException("404");
			}

			if (null == customerResponseData || !customerResponseData.getIsSuccess())
			{
				LOG.error("Not able to update contact in UE");
				throw new ContactNotCreatedOrUpdatedInUEException("Contact not updated in UE");
			}
			else
			{
				// the customer response will have the phone id passsed in result
				phoneInfo.setPhoneId(customerResponseData.getResult());
			}


			//call to okta api to create a user at okta.
			final OktaCreateOrUpdateUserResponseData responseData = getOktaAPI().updateUser(customerModel);
			final OktaCreateOrUpdateUserResponseData oktaUserResponseData = getOktaAPI().getUser(customerModel.getUid());
			final String groupId = Config.getString(SiteoneintegrationConstants.OKTA_BILLTRUST_GROUPID, StringUtils.EMPTY);
			LOG.info("Group Id :" + groupId);
			final String userOktaId = oktaUserResponseData.getId();
			LOG.info("user_okta_id==" + userOktaId);
			try
			{
				if (null != customerData.getPayBillOnline()
						&& (StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(userOktaId)))
				{

					if (customerData.getPayBillOnline().booleanValue())
					{
						getOktaAPI().addUserToGroup(groupId, userOktaId);
					}
					else
					{
						getOktaAPI().removeUserFromGroup(groupId, userOktaId);

					}
				}
				else
				{
					LOG.error("Unable to add or remove the user - " + customerModel.getUid() + " with UserOktaId - " + userOktaId
							+ " to or from the BillTrust group - " + groupId);
				}
			}

			catch (final ResourceAccessException resourceAccessException)
			{
				LOG.error("Not able to establish connection with okta to update user ", resourceAccessException);
				throw new ServiceUnavailableException("404");
			}


			if (null == responseData)
			{
				LOG.error("Not able to update contact in Okta");
				throw new ContactNotCreatedOrUpdatedInOktaException("Contact not updated in Okta");
			}
		}


		try
		{

			if (null != phoneInfo)
			{
				this.getModelService().save(phoneInfo);
			}

			this.getModelService().save(customerModel);
		}
		catch (final ModelSavingException modelSavingException)
		{
			LOG.error(modelSavingException.getMessage(), modelSavingException);
		}

	}


	@Override
	public B2BCustomerModel createCustomer(final SiteoneRequestAccountData siteoneRequestAccountData,
			final SiteOneWsCreateCustomerResponseData response, final B2BUnitModel unit) throws DuplicateEmailException
	{
		Assert.hasText(siteoneRequestAccountData.getFirstName(), "The field [FirstName] cannot be empty");
		Assert.hasText(siteoneRequestAccountData.getLastName(), "The field [LastName] cannot be empty");
		B2BCustomerModel customerModel = null;

		if (StringUtils.isNotEmpty(siteoneRequestAccountData.getEmailAddress()))
		{
			customerModel = (B2BCustomerModel) this.getModelService().create(B2BCustomerModel.class);

			if (unit != null)
			{
				final Set<PrincipalGroupModel> groups = new HashSet<>();
				groups.add(unit);
				if (null != response.getResult().getContactInfo() && response.getResult().getContactInfo().getIsOnlineAdmin() != null)
				{
					if (Boolean.TRUE.equals(response.getResult().getContactInfo().getIsOnlineAdmin()))
					{
						groups.add(getUserService().getUserGroupForUID(B2BConstants.B2BADMINGROUP));
					}
					else
					{
						groups.add(getUserService().getUserGroupForUID(B2BConstants.B2BCUSTOMERGROUP));
					}
				}
				else
				{
					groups.add(getUserService().getUserGroupForUID(B2BConstants.B2BADMINGROUP));
				}
				customerModel.setGroups(groups);
			}
			if (null != response.getResult().getAccountInfo().getContactID())
			{
				customerModel.setGuid(response.getResult().getAccountInfo().getContactID().toUpperCase());
			}
			else
			{
				customerModel.setGuid(response.getResult().getContactInfo().getContactId().toUpperCase());
			}
			if (null != response.getCorrelationId())
			{
				customerModel.setUuid(response.getCorrelationId());
			}
			customerModel.setUid(siteoneRequestAccountData.getEmailAddress());
			customerModel.setFirstName(siteoneRequestAccountData.getFirstName());
			customerModel.setLastName(siteoneRequestAccountData.getLastName());
			customerModel.setEmail(siteoneRequestAccountData.getEmailAddress());
			if (null != siteoneRequestAccountData.getUuidType())
			{
				customerModel.setUeType(siteoneRequestAccountData.getUuidType());
			}
			else
			{
				customerModel.setUeType("UE");
			}
			customerModel.setName(siteoneRequestAccountData.getFirstName() + " " + siteoneRequestAccountData.getLastName());
			customerModel.setActive(Boolean.TRUE);
			customerModel.setLoginDisabled(Boolean.FALSE);
			customerModel.setIsRealtimeAccount(Boolean.TRUE);
			if (siteoneRequestAccountData.getLanguagePreference().equalsIgnoreCase("English"))
			{
				customerModel.setLanguagePreference(languageDao.findLanguagesByCode("en").get(0));
			}
			else
			{
				customerModel.setLanguagePreference(languageDao.findLanguagesByCode("es").get(0));
			}

			customerModel.setPartnerProgramPermissions(null != response.getResult().getLoyaltyProgramInfo()
					&& null != response.getResult().getLoyaltyProgramInfo().getLoyaltyProgramStatus()
					&& response.getResult().getLoyaltyProgramInfo().getLoyaltyProgramStatus().equalsIgnoreCase("INC"));

			customerModel.setOriginalUid(siteoneRequestAccountData.getEmailAddress());
			customerModel.setDefaultB2BUnit(unit);
			customerModel.setIsActiveInOkta(Boolean.FALSE);
			customerModel.setIsFirstTimeUser(Boolean.FALSE);
			customerModel.setPasswordEncoding("okta");
			PhoneContactInfoModel phoneInfo = null;
			if (StringUtils.isNotEmpty(siteoneRequestAccountData.getPhoneNumber()))
			{
				phoneInfo = getModelService().create(PhoneContactInfoModel.class);
				phoneInfo.setUser(customerModel);
				phoneInfo.setCode(customerModel.getUid());
				phoneInfo.setPhoneNumber(siteoneRequestAccountData.getPhoneNumber());
				phoneInfo.setType(PhoneContactInfoType.MOBILE);
				phoneInfo.setPhoneId(response.getResult().getAccountInfo().getPhoneID());
				customerModel.setContactInfos(Collections.singletonList(phoneInfo));
			}
			try
			{
				if (null != phoneInfo)
				{
					this.getModelService().save(phoneInfo);
				}

				this.getModelService().save(customerModel);
			}
			catch (final ModelSavingException modelSavingException)
			{
				LOG.error(modelSavingException.getMessage(), modelSavingException);
			}
		}
		return customerModel;


	}


	@Override
	public void disableCustomer(final String customerUid)
			throws ServiceUnavailableException, ContactNotEnabledOrDisabledInUEException
	{
		ServicesUtil.validateParameterNotNullStandardMessage("customerUid", customerUid);

		final B2BCustomerModel b2bCustomerModel = this.getUserService().getUserForUID(customerUid.toLowerCase(),
				B2BCustomerModel.class);

		if (null != b2bCustomerModel)
		{
			SiteOneWsB2BCustomerResponseData customerResponse = null;
			try
			{
				b2bCustomerModel.setActive(Boolean.FALSE);
				customerResponse = getSiteOneContactWebService().disableOrEnableB2BCustomer(b2bCustomerModel,
						Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
			}
			catch (final ResourceAccessException resourceAccessException)
			{
				LOG.error("Not able to establish connection with UE to diable contact", resourceAccessException);
				throw new ServiceUnavailableException("404");
			}
			if (null != customerResponse && customerResponse.getIsSuccess())
			{
				this.getB2BCommerceUserService().disableCustomer(customerUid);
			}
			else
			{
				LOG.error("Not able to disable contact in UE");
				throw new ContactNotEnabledOrDisabledInUEException("Contact not disabled in UE");
			}
		}


	}

	@Override
	public void enableCustomer(final String customerUid)
			throws ServiceUnavailableException, ContactNotEnabledOrDisabledInUEException
	{
		ServicesUtil.validateParameterNotNullStandardMessage("customerUid", customerUid);

		final B2BCustomerModel b2bCustomerModel = this.getUserService().getUserForUID(customerUid.toLowerCase(),
				B2BCustomerModel.class);

		if (null != b2bCustomerModel)
		{
			SiteOneWsB2BCustomerResponseData customerResponse = null;
			try
			{
				b2bCustomerModel.setActive(Boolean.TRUE);
				customerResponse = getSiteOneContactWebService().disableOrEnableB2BCustomer(b2bCustomerModel,
						Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
			}
			catch (final ResourceAccessException resourceAccessException)
			{
				LOG.error("Not able to establish connection with UE to enable contact", resourceAccessException);
				throw new ServiceUnavailableException("404");
			}
			if (null != customerResponse && customerResponse.getIsSuccess())
			{
				this.getB2BCommerceUserService().enableCustomer(customerUid);
			}
			else
			{
				LOG.error("Not able to enable contact in UE");
				throw new ContactNotEnabledOrDisabledInUEException("Contact not enabled in UE");
			}
		}


	}

	/**
	 * @return the siteOneContactWebService
	 */
	public SiteOneContactWebService getSiteOneContactWebService()
	{
		return siteOneContactWebService;
	}


	/**
	 * @param siteOneContactWebService
	 *           the siteOneContactWebService to set
	 */
	public void setSiteOneContactWebService(final SiteOneContactWebService siteOneContactWebService)
	{
		this.siteOneContactWebService = siteOneContactWebService;
	}


	@Override
	public SearchPageData<CustomerData> getPagedUserDataForUnit(final PageableData pageableData, final List<String> userUnitIds,
			final String trimmedSearchParam, final String sortCode, final Boolean isAdmin)
	{
		final SearchPageData<B2BCustomerModel> customers = ((SiteOneB2BCommerceUserService) getB2BCommerceUserService())
				.getPagedUsersForUnit(pageableData, userUnitIds, trimmedSearchParam, sortCode, isAdmin);

		return CommerceUtils.convertPageData(customers, getB2BUserConverter());
	}

	@Override
	public SearchPageData<CustomerData> getPagedUserDataForUnit(final PageableData pageableData, final String userUnitId,
			final String trimmedSearchParam, final String sortCode, final Boolean isAdmin, final Boolean shipToUnitFlag)
	{
		final SearchPageData<B2BCustomerModel> customers = ((SiteOneB2BCommerceUserService) getB2BCommerceUserService())
				.getPagedUsersForUnit(pageableData, userUnitId, trimmedSearchParam, sortCode, isAdmin, shipToUnitFlag);

		return CommerceUtils.convertPageData(customers, getB2BUserConverter());
	}

	@Override
	public Map<String, String> getListOfShipTos()
	{
		B2BUnitModel defaultUnit = null;
		final B2BCustomerModel cust = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		Map<String, String> listOfShipTos = null;

		defaultUnit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
		listOfShipTos = new LinkedHashMap<>();
		final List<B2BUnitData> childs = ((SiteOneB2BUnitFacade) b2bUnitFacade).getShipTosForUnit(defaultUnit.getUid());

		for (final B2BUnitData child : childs)
		{
			final String shipID = child.getUid();
			final String shipToNameID = child.getUid().endsWith(SiteoneCoreConstants.INDEX_OF_CA) ?
					child.getUid().substring(0, child.getUid().indexOf(SiteoneCoreConstants.INDEX_OF_CA)) + " " + child.getName() :
						child.getUid().substring(0, child.getUid().indexOf(SiteoneCoreConstants.INDEX_OF_US)) + " " + child.getName();
			listOfShipTos.put(shipID, shipToNameID);
		}

		return listOfShipTos;

	}

	@Override
	public boolean isPunchOutCustomer(final String uid)
	{
		B2BCustomerModel customer = null;
		boolean isPunchOutCustomer = false;
		try
		{
			customer = (B2BCustomerModel) this.getUserService().getUserForUID(uid.toLowerCase());

			final B2BUnitModel parentUnit = ((SiteOneB2BUnitService) b2bUnitService)
					.getMainAccountForUnit(customer.getDefaultB2BUnit().getUid());

			if (parentUnit != null && parentUnit.getIsPunchOutAccount() != null && parentUnit.getIsPunchOutAccount().booleanValue())
			{
				isPunchOutCustomer = true;
			}
		}
		catch (final UnknownIdentifierException e)
		{
			LOG.error(e);
		}
		return isPunchOutCustomer;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facades.company.SiteOneB2BUserFacade#getPagedUserDataForUnit(de.hybris.platform.commerceservices.
	 * search.pagedata.PageableData, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public SearchPageData<CustomerData> getPagedUserDataForUnit(final PageableData pageableData, final String unitId,
			final String trimmedSearchParam, final String sortCode)
	{
		final SearchPageData<B2BCustomerModel> customers = ((SiteOneB2BCommerceUserService) getB2BCommerceUserService())
				.getPagedUsersForUnit(pageableData, unitId, trimmedSearchParam, sortCode);

		return CommerceUtils.convertPageData(customers, getB2BUserConverter());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facades.company.SiteOneB2BUserFacade#getCustomers(java.util.List)
	 */
	@Override
	public List<B2BCustomerModel> getCustomers(final List<String> custUIDs)
	{
		final List<B2BCustomerModel> customers = ((SiteOneB2BCommerceUserService) getB2BCommerceUserService())
				.getCustomers(custUIDs);
		return customers;
	}

	@Override
	public List<B2BCustomerModel> getUserDataForUnit(final String unitId)
	{
		final List<B2BCustomerModel> customers = ((SiteOneB2BCommerceUserService) getB2BCommerceUserService())
				.getUsersForUnit(unitId);
		return customers;
	}

	public void updateGroupAndStoreForPunchoutCustomer(final CustomerData customerData, final B2BCustomerModel customerModel)
	{
		if (customerData.getIsPunchoutFlow() != null && customerData.getIsPunchoutFlow().booleanValue())
		{

			if (null != customerData.getHomeBranch())
			{
				final PointOfServiceModel preferredStore = storeFinderService.getStoreForId(customerData.getHomeBranch());
				customerModel.setPreferredStore(preferredStore);
				customerModel.setHomeBranch(preferredStore.getStoreId());
				final HashSet<PointOfServiceModel> stores = new HashSet<>();
				stores.add(preferredStore);
				customerModel.setStores(stores);
			}
			customerModel.setLanguagePreference(languageDao.findLanguagesByCode("en").get(0));
		}

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

	/**
	 * @return the b2bUnitService
	 */
	public B2BUnitService getB2bUnitService()
	{
		return b2bUnitService;
	}

	/**
	 * @param b2bUnitService
	 *           the b2bUnitService to set
	 */
	public void setB2bUnitService(final B2BUnitService b2bUnitService)
	{
		this.b2bUnitService = b2bUnitService;
	}

}


