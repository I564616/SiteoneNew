/**
 *
 */
package com.siteone.core.customer.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;
import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.customer.TokenInvalidatedException;
import de.hybris.platform.commerceservices.customer.dao.CustomerAccountDao;
import de.hybris.platform.commerceservices.customer.impl.DefaultCustomerAccountService;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;
import de.hybris.platform.commerceservices.event.ForgottenPwdEvent;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.security.SecureToken;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.util.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.PredicateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.client.ResourceAccessException;

import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.customer.SiteOneCustomerAccountService;
import com.siteone.core.customer.dao.SiteOnePagedB2BCustomerDao;
import com.siteone.core.enums.InvoiceStatus;
import com.siteone.core.event.CreatePasswordEvent;
import com.siteone.core.event.UnlockUserEvent;
import com.siteone.core.exceptions.OktaInvalidUserStatusException;
import com.siteone.core.exceptions.OktaRecoveryTokenNotFoundException;
import com.siteone.core.model.LinkToPayCayanResponseModel;
import com.siteone.core.model.PurchProductAndOrdersModel;
import com.siteone.core.model.SiteOneInvoiceModel;
import com.siteone.core.order.dao.SiteOneCustomerAccountDao;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.core.unit.dao.SiteOnePagedB2BUnitDao;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.exception.okta.OktaUnknownUserException;
import com.siteone.integration.okta.OKTAAPI;
import com.siteone.integration.open.order.data.OpenOrdersLandingPageRequestData;
import com.siteone.integration.open.order.data.OpenOrdersLandingPageResponseData;
import com.siteone.integration.open.order.data.SiteoneOrderDetailsResponseData;
import com.siteone.integration.rest.client.SiteOneRestClient;
import com.siteone.integration.services.okta.data.OktaCreateOrUpdateUserResponseData;
import com.siteone.integration.services.okta.data.OktaForgotPwdResponseData;


/**
 * @author Abdul Rahman Sheikh M
 *
 */
public class DefaultSiteOneCustomerAccountService extends DefaultCustomerAccountService implements SiteOneCustomerAccountService
{

	private final static Logger LOG = Logger.getLogger(DefaultSiteOneCustomerAccountService.class.getName());


	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	private B2BCustomerService b2bCustomerService;

	private B2BUnitService b2bUnitService;

	private SiteOneStoreFinderService siteOneStoreFinderService;

	private SiteOnePagedB2BUnitDao<B2BUnitModel> b2bUnitModelDao;

	private OKTAAPI oktaAPI;

	private CustomerAccountDao customerAccountDao;

	private SiteOnePagedB2BCustomerDao siteOnePagedB2BCustomerDao;

	private SiteOneRestClient<OpenOrdersLandingPageRequestData, OpenOrdersLandingPageResponseData> siteOneRestClientForOrders;

	private SiteOneRestClient<?, SiteoneOrderDetailsResponseData> siteOneRestClient;

	/**
	 * @return the siteOnePagedB2BCustomerDao
	 */
	public SiteOnePagedB2BCustomerDao getSiteOnePagedB2BCustomerDao()
	{
		return siteOnePagedB2BCustomerDao;
	}


	/**
	 * @param siteOnePagedB2BCustomerDao
	 *           the siteOnePagedB2BCustomerDao to set
	 */
	public void setSiteOnePagedB2BCustomerDao(final SiteOnePagedB2BCustomerDao siteOnePagedB2BCustomerDao)
	{
		this.siteOnePagedB2BCustomerDao = siteOnePagedB2BCustomerDao;
	}


	@Override
	public CustomerAccountDao getCustomerAccountDao()
	{
		return customerAccountDao;
	}


	@Override
	public void setCustomerAccountDao(final CustomerAccountDao customerAccountDao)
	{
		this.customerAccountDao = customerAccountDao;
	}

	/**
	 * @return the b2bCustomerService
	 */
	public B2BCustomerService getB2bCustomerService()
	{
		return b2bCustomerService;
	}

	/**
	 * @return the siteOneStoreFinderService
	 */
	public SiteOneStoreFinderService getSiteOneStoreFinderService()
	{
		return siteOneStoreFinderService;
	}


	/**
	 * @param siteOneStoreFinderService
	 *           the siteOneStoreFinderService to set
	 */
	public void setSiteOneStoreFinderService(final SiteOneStoreFinderService siteOneStoreFinderService)
	{
		this.siteOneStoreFinderService = siteOneStoreFinderService;
	}


	/**
	 * @param b2bCustomerService
	 *           the b2bCustomerService to set
	 */
	public void setB2bCustomerService(final B2BCustomerService b2bCustomerService)
	{
		this.b2bCustomerService = b2bCustomerService;
	}


	@Override
	public Boolean isPreferredStore(final PointOfServiceModel pointOfServiceModel)
	{
		boolean isPreferred = false;
		final PointOfServiceModel prefferedStore = this.getCustomerPreferredStore();

		if (null != prefferedStore && prefferedStore.getStoreId().equalsIgnoreCase(pointOfServiceModel.getStoreId()))
		{
			isPreferred = true;
		}

		return isPreferred;
	}

	@Override
	public Boolean isInMyStore(final PointOfServiceModel pointOfServiceModel)
	{
		boolean isInMyStore = false;
		final Set<PointOfServiceModel> myStores = this.getCustomerStoreList();

		if (CollectionUtils.isNotEmpty(myStores))
		{
			for (final PointOfServiceModel myStore : myStores)
			{
				if (myStore.getStoreId().equalsIgnoreCase(pointOfServiceModel.getStoreId()))
				{
					isInMyStore = true;
				}
			}
		}

		return isInMyStore;
	}


	@Override
	public PointOfServiceModel getCustomerPreferredStore()
	{
		// YTODO Auto-generated method stub
		final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		if (null != b2bCustomerModel)
		{
			if (b2bCustomerModel.getPreferredStore() != null)
			{
				return b2bCustomerModel.getPreferredStore();
			}
			else if (b2bCustomerModel.getHomeBranch() != null)
			{
				return makeMyStore(b2bCustomerModel.getHomeBranch());
			}
		}
		return null;
	}

	@Override
	public PointOfServiceModel getCustomerHomeBranch()
	{
		final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		if (null != b2bCustomerModel && b2bCustomerModel.getHomeBranch() != null)
		{
			return siteOneStoreFinderService.getStoreForId(b2bCustomerModel.getHomeBranch());
		}
		return null;
	}

	@Override
	public Set<PointOfServiceModel> getCustomerStoreList()
	{
		// YTODO Auto-generated method stub
		final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		Set<PointOfServiceModel> myStores = null;

		if (null != b2bCustomerModel)
		{
			myStores = b2bCustomerModel.getStores();

			if (CollectionUtils.isNotEmpty(myStores))
			{
				return myStores.stream().filter(store -> BooleanUtils.isTrue(store.getIsActive())).collect(Collectors.toSet());
			}
		}

		return myStores;
	}

	@Override
	public PointOfServiceModel makeMyStore(final String storeId)
	{
		final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		final PointOfServiceModel pointOfServiceModel = siteOneStoreFinderService.getStoreForId(storeId);
		final Set<PointOfServiceModel> myStores = new HashSet();
		if (null != b2bCustomerModel && null != pointOfServiceModel)
		{
			myStores.addAll(b2bCustomerModel.getStores());
			if (null != b2bCustomerModel.getPreferredStore())
			{
				myStores.add(b2bCustomerModel.getPreferredStore());
			}
			b2bCustomerModel.setPreferredStore(pointOfServiceModel);
			myStores.add(pointOfServiceModel);
			b2bCustomerModel.setStores(myStores);

			getModelService().save(b2bCustomerModel);
		}
		return pointOfServiceModel;
	}

	@Override
	public PointOfServiceModel saveStore(final String storeId)
	{
		final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		final PointOfServiceModel pointOfServiceModel = siteOneStoreFinderService.getStoreForId(storeId);
		final Set<PointOfServiceModel> myStores = new HashSet<>();
		if (null != b2bCustomerModel && null != pointOfServiceModel)
		{
			myStores.addAll(b2bCustomerModel.getStores());
			if (null != b2bCustomerModel.getPreferredStore())
			{
				myStores.add(b2bCustomerModel.getPreferredStore());
			}
			myStores.add(pointOfServiceModel);
			b2bCustomerModel.setStores(myStores);

			getModelService().save(b2bCustomerModel);
		}
		return pointOfServiceModel;
	}

	@Override
	public void removeFromMyStore()
	{
		final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		if (null != b2bCustomerModel)
		{
			b2bCustomerModel.setPreferredStore(null);
			getModelService().save(b2bCustomerModel);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 *
	 * @see com.siteone.core.store.SiteOneCustomerAccountService#removeFromMyAccount(java.lang.String)
	 */
	@Override
	public void removeFromMyStoreList(final String storeId)
	{
		final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();

		if (null != b2bCustomerModel)
		{
			final Set<PointOfServiceModel> stores = b2bCustomerModel.getStores();

			if (CollectionUtils.isNotEmpty(stores))
			{
				final Set<PointOfServiceModel> removedSet = stores.stream()
						.filter(store -> !(store.getStoreId().equalsIgnoreCase(storeId))).collect(Collectors.toSet());
				b2bCustomerModel.setStores(removedSet);
				getModelService().save(b2bCustomerModel);
			}
		}


	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.customer.SiteOneCustomerAccountService#getAddressBookForUnit(java.lang.String)
	 */
	@Override
	public Collection<AddressModel> getAddressBookForUnit(final B2BUnitModel b2bUnitModel)
	{
		try
		{
			if (null != b2bUnitModel)
			{
				return b2bUnitModel.getAddresses();
			}
		}
		catch (final Exception e)
		{
			LOG.error(e);
		}

		return null;

	}


	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.customer.SiteOneCustomerAccountService#getBillingAddressForUnit(java.lang.String)
	 */
	@Override
	public AddressModel getBillingAddressForUnit(final B2BUnitModel b2bUnitModel)
	{
		try
		{
			if (null != b2bUnitModel)
			{
				return b2bUnitModel.getBillingAddress();
			}
		}
		catch (final Exception e)
		{
			LOG.error(e);
		}

		return null;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.customer.SiteOneCustomerAccountService#getShippingAddressForUnit(java.lang.String)
	 */
	@Override
	public AddressModel getShippingAddressForUnit(final B2BUnitModel b2bUnitModel)
	{
		try
		{
			if (null != b2bUnitModel)
			{
				return b2bUnitModel.getShippingAddress();
			}
		}
		catch (final Exception e)
		{
			LOG.error(e);
		}

		return null;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.customer.SiteOneCustomerAccountService#getBillingAddressesForUnit(java.lang.String)
	 */
	@Override
	public Collection<AddressModel> getBillingAddressesForUnit(final B2BUnitModel b2bUnitModel)
	{
		Collection<AddressModel> addresses = new ArrayList<>();

		Collection<AddressModel> unitAddresses = this.getAddressBookForUnit(b2bUnitModel);
		addresses = unitAddresses.stream().filter(address -> address.getBillingAddress()).collect(Collectors.toList());

		if (CollectionUtils.isNotEmpty(addresses))
		{
			return addresses;
		}
		else
		{
			unitAddresses = this.getAddressBookForUnit(b2bUnitModel.getReportingOrganization());
			addresses = unitAddresses.stream().filter(address -> address.getBillingAddress()).collect(Collectors.toList());
		}

		return addresses;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.customer.SiteOneCustomerAccountService#getShippingAddressesForUnit(java.lang.String)
	 */
	@Override
	public Collection<AddressModel> getShippingAddressesForUnit(final B2BUnitModel b2bUnitModel)
	{
		Collection<AddressModel> addresses = new ArrayList<>();
		final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();

		final Collection<UserGroupModel> userGroups = CollectionUtils.select(customer.getGroups(),
				PredicateUtils.instanceofPredicate(UserGroupModel.class));

		if (((SiteOneB2BUnitService) b2bUnitService).getParentUnitForCustomer().equals(b2bUnitModel)
				&& userGroups.stream().anyMatch(group -> group.getUid().equalsIgnoreCase("b2badmingroup")))
		{
			final Collection<AddressModel> childAddresses = this.getAddressesForChildUnits(b2bUnitModel);

			if (CollectionUtils.isNotEmpty(childAddresses))
			{
				addresses.addAll(childAddresses);
			}
		}
		else
		{
			addresses = this.getAddressBookForUnit(b2bUnitModel);
		}

		if (CollectionUtils.isNotEmpty(addresses))
		{
			return addresses.stream().filter(address -> address.getShippingAddress()).collect(Collectors.toList());
		}

		return addresses;
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


	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.customer.SiteOneCustomerAccountService#getAddressForCode(de.hybris.platform.b2b.model.
	 * B2BUnitModel, java.lang.String)
	 */
	@Override
	public AddressModel getAddressForCode(final B2BUnitModel b2bUnitModel, final String code)
	{

		final Collection<AddressModel> addresses = this.getAddressBookForUnit(b2bUnitModel);
		for (final AddressModel addressModel : addresses)
		{
			if (addressModel.getPk().getLongValueAsString().equals(code))
			{
				return addressModel;
			}
		}

		return null;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.customer.SiteOneCustomerAccountService#setDefaultAddressEntry(de.hybris.platform.b2b.model.
	 * B2BUnitModel, de.hybris.platform.core.model.user.AddressModel)
	 */
	@Override
	public void setDefaultAddressEntry(final B2BUnitModel b2bUnitModel, final AddressModel addressModel)
	{
		{
			if (b2bUnitModel.getAddresses().contains(addressModel))
			{
				b2bUnitModel.setShippingAddress(addressModel);
			}
			else
			{
				final AddressModel clone = getModelService().clone(addressModel);
				clone.setOwner(b2bUnitModel);
				getModelService().save(clone);
				final List<AddressModel> unitAddresses = new ArrayList<AddressModel>();
				unitAddresses.addAll(b2bUnitModel.getAddresses());
				unitAddresses.add(clone);
				b2bUnitModel.setAddresses(unitAddresses);
				b2bUnitModel.setShippingAddress(clone);
			}
			getModelService().save(b2bUnitModel);
			getModelService().refresh(b2bUnitModel);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.siteone.core.customer.SiteOneCustomerAccountService#clearDefaultAddressEntry(de.hybris.platform.b2b.model.
	 * B2BUnitModel)
	 */
	@Override
	public void clearDefaultAddressEntry(final B2BUnitModel b2bUnitModel)
	{
		b2bUnitModel.setShippingAddress(null);
		getModelService().save(b2bUnitModel);
		getModelService().refresh(b2bUnitModel);
	}

	@Override
	public void saveAddressEntry(final B2BUnitModel b2bUnitModel, final AddressModel addressModel)
	{
		final List<AddressModel> unitAddresses = new ArrayList<AddressModel>();
		unitAddresses.addAll(b2bUnitModel.getAddresses());
		if (b2bUnitModel.getAddresses().contains(addressModel))
		{
			getModelService().save(addressModel);
		}
		else
		{
			addressModel.setOwner(b2bUnitModel);
			getModelService().save(addressModel);
			getModelService().refresh(addressModel);
			unitAddresses.add(addressModel);
		}
		b2bUnitModel.setAddresses(unitAddresses);

		getModelService().save(b2bUnitModel);
		getModelService().refresh(b2bUnitModel);

	}


	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.customer.SiteOneCustomerAccountService#deleteAddressEntry(de.hybris.platform.b2b.model.
	 * B2BUnitModel, de.hybris.platform.core.model.user.AddressModel)
	 */
	@Override
	public void deleteAddressEntry(final B2BUnitModel b2bUnitModel, final AddressModel addressModel)
	{

		if (b2bUnitModel.getAddresses().contains(addressModel))
		{
			final boolean changeDefaultAddress = addressModel.equals(this.getShippingAddressForUnit(b2bUnitModel));

			getModelService().remove(addressModel);
			getModelService().refresh(b2bUnitModel);

			final Iterator<AddressModel> addressIterator = b2bUnitModel.getAddresses().iterator();
			if (changeDefaultAddress && addressIterator.hasNext())
			{
				setDefaultAddressEntry(b2bUnitModel, addressIterator.next());
			}
		}
		else
		{
			throw new IllegalArgumentException(
					"Address " + addressModel + " does not belong to the unit " + b2bUnitModel + " and will not be removed.");
		}
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.siteone.core.customer.SiteOneCustomerAccountService#getAddressesForChildUnits(de.hybris.platform.b2b.model.
	 * B2BUnitModel)
	 */
	@Override
	public Collection<AddressModel> getAddressesForChildUnits(final B2BUnitModel b2bUnitModel)
	{
		final Collection<AddressModel> addresses = new ArrayList<>();

		final Collection<B2BUnitModel> childUnits = b2bUnitService.getBranch(b2bUnitModel);

		childUnits.forEach(unit -> {
			addresses.addAll(unit.getAddresses());
		});

		return addresses;
	}

	@Override
	public SearchPageData<B2BUnitModel> getPagedB2BUnits(final PageableData pageableData, final String unitId,
			final String searchParam)
	{
		final B2BUnitModel parentUnit = ((SiteOneB2BUnitService) b2bUnitService).getParentUnitForCustomer();
		return b2bUnitModelDao.findPagedUnits(pageableData.getSort(), pageableData, parentUnit, searchParam);
	}


	@Override
	public void forgottenPassword(final CustomerModel customerModel)
	{
		validateParameterNotNullStandardMessage("customerModel", customerModel);
		try
		{
			final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) customerModel;
			if (customerModel instanceof B2BCustomerModel)
			{

				if (b2bCustomerModel != null && b2bCustomerModel.getDefaultB2BUnit() != null
						&& b2bCustomerModel.getDefaultB2BUnit().getIsPunchOutAccount() != null
						&& b2bCustomerModel.getDefaultB2BUnit().getIsPunchOutAccount().booleanValue())
				{
					LOG.info("PunchOut customer. " + customerModel.getUid() + ", please contact SiteOne");
					throw new OktaInvalidUserStatusException(
							"PunchOut customer. " + customerModel.getUid() + ", please contact SiteOne");
				}
			}

			final OktaCreateOrUpdateUserResponseData oktaUserResponseData = getOktaAPI().getUser(customerModel.getUid());

			if (null != oktaUserResponseData
					&& (SiteoneCoreConstants.OKTA_USER_STATUS_STAGED.equalsIgnoreCase(oktaUserResponseData.getStatus())
							|| SiteoneCoreConstants.OKTA_USER_STATUS_PROVISIONED.equalsIgnoreCase(oktaUserResponseData.getStatus())))
			{
				createPassword(customerModel);
			}
			else
			{
				if (null != b2bCustomerModel && b2bCustomerModel.getActive().booleanValue())
				{
					final OktaForgotPwdResponseData response = getOktaAPI().forgotPasswordForUser(customerModel.getUid());
					if (null != response)
					{

						if (SiteoneintegrationConstants.OKTA_FORGOT_PASSWORD_INVALID_STATUS.equalsIgnoreCase(response.getErrorCode()))
						{
							LOG.info("Invalid User Status Exception for user" + customerModel.getUid());
							throw new OktaInvalidUserStatusException(response.getErrorSummary());
						}

						if (null == response.getRecoveryToken())
						{
							LOG.info("Recovery Token not found Exception for user" + customerModel.getUid());
							throw new OktaRecoveryTokenNotFoundException("Recovery not found" + customerModel.getUid());
						}

						final long tokenValiditySeconds = Config.getLong(SiteoneintegrationConstants.TOKEN_VALIDITY_FORGOT_PASSWORD,
								1800);
						final long timeStamp = tokenValiditySeconds > 0L ? new Date().getTime() : 0L;
						final SecureToken data = new SecureToken(customerModel.getUid(), timeStamp);
						final String token = getSecureTokenService().encryptData(data);

						final String recoveryToken = response.getRecoveryToken();
						customerModel.setToken(token);
						customerModel.setRecoveryToken(recoveryToken);
						getModelService().save(customerModel);
						getEventService().publishEvent(initializeEvent(new ForgottenPwdEvent(token), customerModel));
					}
				}
			}



		}
		catch (final OktaUnknownUserException oktaUnknownUserException)
		{
			LOG.error(oktaUnknownUserException);
			throw new UnknownIdentifierException(oktaUnknownUserException.getMessage());
		}
	}

	@Override
	public CustomerModel getCustomerFromToken(final String token, final long tokenValiditySeconds)
			throws TokenInvalidatedException, IllegalArgumentException
	{
		Assert.hasText(token, "The field [token] cannot be empty");

		final SecureToken data = getSecureTokenService().decryptData(token);
		if (tokenValiditySeconds > 0L)
		{
			final long delta = new Date().getTime() - data.getTimeStamp();
			if (delta / 1000 > tokenValiditySeconds)
			{
				throw new IllegalArgumentException("token expired");
			}
		}

		final CustomerModel customer = getUserService().getUserForUID(data.getData(), CustomerModel.class);
		if (customer == null)
		{
			throw new IllegalArgumentException("user for token not found");
		}
		if (!token.equals(customer.getToken()))
		{
			throw new TokenInvalidatedException();
		}
		return customer;
	}


	@Override
	public void createPassword(final CustomerModel customerModel)
	{
		validateParameterNotNullStandardMessage("customerModel", customerModel);
		final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) customerModel;
		final long tokenValiditySeconds = Config.getLong(SiteoneintegrationConstants.TOKEN_VALIDITY_CREATE_PASSWORD, 1800);
		final long timeStamp = tokenValiditySeconds > 0L ? new Date().getTime() : 0L;
		final SecureToken data = new SecureToken(b2bCustomerModel.getUid(), timeStamp);
		final String token = getSecureTokenService().encryptData(data);
		b2bCustomerModel.setToken(token);
		getModelService().save(b2bCustomerModel);
		getEventService().publishEvent(initializeEventCustom(new CreatePasswordEvent(token, tokenValiditySeconds), b2bCustomerModel));
	}

	public AbstractCommerceUserEvent initializeEventCustom(final AbstractCommerceUserEvent event,
														   final B2BCustomerModel b2BCustomerModel)
	{
		if (null != b2BCustomerModel.getDefaultB2BUnit()
				&& b2BCustomerModel.getDefaultB2BUnit().getUid().contains(SiteoneCoreConstants.INDEX_OF_US))
		{
			event.setBaseStore(getBaseStoreService().getBaseStoreForUid(SiteoneCoreConstants.SITEONE_US_BASESTORE));
			event.setSite(getBaseSiteService().getBaseSiteForUID(SiteoneCoreConstants.BASESITE_US));
		}
		else
		{

			event.setBaseStore(getBaseStoreService().getBaseStoreForUid(SiteoneCoreConstants.SITEONE_CA_BASESTORE));
			event.setSite(getBaseSiteService().getBaseSiteForUID(SiteoneCoreConstants.BASESITE_CA));
		}
		event.setCustomer(b2BCustomerModel);
		event.setLanguage(getCommonI18NService().getCurrentLanguage());
		event.setCurrency(getCommonI18NService().getCurrentCurrency());
		return event;
	}

	@Override
	public void createPassword(final String uid)
	{
		// YTODO Auto-generated method stub
		final B2BCustomerModel b2bCustomerModel = this.getUserService().getUserForUID(uid.toLowerCase(), B2BCustomerModel.class);
		if (null != b2bCustomerModel)
		{
			createPassword(b2bCustomerModel);
		}
	}

	@Override
	public void unlockUserRequest(final B2BCustomerModel b2bCustomerModel)
	{
		validateParameterNotNullStandardMessage("customerModel", b2bCustomerModel);


		final long tokenValiditySeconds = Config.getLong(SiteoneintegrationConstants.TOKEN_VALIDITY_UNLOCK_USER, 1800);
		final long timeStamp = tokenValiditySeconds > 0L ? new Date().getTime() : 0L;
		final SecureToken data = new SecureToken(b2bCustomerModel.getUid(), timeStamp);
		final String token = getSecureTokenService().encryptData(data);

		b2bCustomerModel.setToken(token);
		getModelService().save(b2bCustomerModel);
		getEventService().publishEvent(initializeEvent(new UnlockUserEvent(token, tokenValiditySeconds), b2bCustomerModel));
	}

	/**
	 * @return the b2bUnitModelDao
	 */
	public SiteOnePagedB2BUnitDao<B2BUnitModel> getB2bUnitModelDao()
	{
		return b2bUnitModelDao;
	}

	/**
	 * @param b2bUnitModelDao
	 *           the b2bUnitModelDao to set
	 */
	public void setB2bUnitModelDao(final SiteOnePagedB2BUnitDao<B2BUnitModel> b2bUnitModelDao)
	{
		this.b2bUnitModelDao = b2bUnitModelDao;
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


	@Override
	public OrderModel getRecentOrderForAccount(final String unitId)
	{
		final B2BCustomerModel customerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		return ((SiteOneCustomerAccountDao) customerAccountDao).findRecentOrderForAccount(getAllSubAccountsAndShiptosList(unitId),
				customerModel);
	}
	
	@Override
	public List<OrderModel> getHeldOrdersForUnit(String unitId)
	{
		List<OrderModel> orders = new ArrayList();
		if(unitId != null)
		{
			orders = ((SiteOneCustomerAccountDao) customerAccountDao).findHeldOrdersForCustomer(getAllSubAccountsAndShiptosList(unitId));
		}
		return orders;
	}


	@Override
	public SearchPageData<PurchProductAndOrdersModel> getPagedPurchasedOrder(final PageableData pageableData, final String unitId)
	{
		return ((SiteOneCustomerAccountDao) customerAccountDao).findPagedPurchasedProducts(getAllSubAccountsAndShiptosList(unitId),
				pageableData);
	}

	@Override
	public SearchPageData<OrderModel> getOrderList(final BaseStoreModel store, final OrderStatus[] status,
			final PageableData pageableData, final String unitId, final String pageId, final String trimmedSearchParam,
			final String dateSort, final String paymentType)
	{
		validateParameterNotNull(store, "Store must not be null");
		validateParameterNotNull(pageableData, "PageableData must not be null");


		final List<B2BUnitModel> shipTos = Arrays.asList((B2BUnitModel) getB2bUnitService().getUnitForUid(unitId));
		return ((SiteOneCustomerAccountDao) customerAccountDao).findOrdersByUnit(store, status, pageableData, shipTos, pageId,
				trimmedSearchParam, dateSort, paymentType);
	}

	@Override
	public OpenOrdersLandingPageResponseData getOpenOrderList(final OpenOrdersLandingPageRequestData openOrderRequest,
			final String customerNumber, final Integer divisionId, final boolean isNewBoomiEnv) throws ResourceAccessException
	{

		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(SiteoneintegrationConstants.HTTP_HEADER_CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		OpenOrdersLandingPageResponseData response = null;
		if (isNewBoomiEnv)
		{
			httpHeaders.set(SiteoneintegrationConstants.OPEN_ORDERS_SERVICE_KEY_NAME,
					Config.getString(SiteoneintegrationConstants.OPEN_ORDERS_NEW_SERVICE_KEY, null));
			response = executeOpenOrders(openOrderRequest, httpHeaders, customerNumber, divisionId, isNewBoomiEnv);
		}
		else
		{
			httpHeaders.set(SiteoneintegrationConstants.OPEN_ORDERS_SERVICE_KEY_NAME,
					Config.getString(SiteoneintegrationConstants.OPEN_ORDERS_SERVICE_KEY, null));
			response = executeOpenOrders(openOrderRequest, httpHeaders, customerNumber, divisionId, isNewBoomiEnv);
		}

		return response;
	}

	private OpenOrdersLandingPageResponseData executeOpenOrders(final OpenOrdersLandingPageRequestData openOrderRequest,
			final HttpHeaders httpHeaders, final String customerNumber, final Integer divisionId, final boolean isNewBoomiEnv)
	{
		OpenOrdersLandingPageResponseData response = null;

		String openOrdersApiUrl = (isNewBoomiEnv
				? Config.getString(SiteoneintegrationConstants.OPEN_ORDERS_NEW_SERVICE_URL, StringUtils.EMPTY)
				: Config.getString(SiteoneintegrationConstants.OPEN_ORDERS_SERVICE_URL, StringUtils.EMPTY));
		openOrdersApiUrl = openOrdersApiUrl.replace(SiteoneintegrationConstants.QUERY_PARAM_CUSTOMER_NUMBER_OO, customerNumber);
		openOrdersApiUrl = openOrdersApiUrl.replace(SiteoneintegrationConstants.QUERY_PARAM_DIVISIONID_OO, divisionId.toString());
		try
		{
			response = getSiteOneRestClientForOrders().execute(openOrdersApiUrl, HttpMethod.POST, openOrderRequest,
					OpenOrdersLandingPageResponseData.class, UUID.randomUUID().toString(),
					SiteoneintegrationConstants.OPEN_ORDERS_SERVICE_NAME, httpHeaders);
		}
		catch (final ResourceAccessException resourceAccessException)
		{
			LOG.error("Not able to establish connection with open order API ", resourceAccessException);
		}
		return response;
	}

	public SiteOneRestClient<OpenOrdersLandingPageRequestData, OpenOrdersLandingPageResponseData> getSiteOneRestClientForOrders()
	{
		return siteOneRestClientForOrders;
	}

	public void setSiteOneRestClientForOrders(
			final SiteOneRestClient<OpenOrdersLandingPageRequestData, OpenOrdersLandingPageResponseData> siteOneRestClientForOrders)
	{
		this.siteOneRestClientForOrders = siteOneRestClientForOrders;
	}

	@Override
	public SiteoneOrderDetailsResponseData getOrderDetailsData(final String customerNumber, final String invoiceNumber,
			final boolean isNewBoomiEnv) throws ResourceAccessException
	{
		{
			final BaseSiteModel basesite = baseSiteService.getCurrentBaseSite();
			String divisionId = "1";
			if (basesite.getUid().equalsIgnoreCase("siteone-us"))
			{
				divisionId = "1";
			}
			else
			{
				divisionId = "2";
			}
			String orderUrl = (isNewBoomiEnv
					? Config.getString(SiteoneintegrationConstants.ORDER_DETAIL_SERVICE_URL, StringUtils.EMPTY)
					: Config.getString(SiteoneintegrationConstants.ORDER_DETAIL_SERVICE_URL, StringUtils.EMPTY));
			orderUrl = orderUrl.replace(SiteoneintegrationConstants.QUERY_PARAM_CUSTOMER_NUMBER_OO, customerNumber);
			orderUrl = orderUrl.replace(SiteoneintegrationConstants.QUERY_PARAM_INVOICEID_OO, invoiceNumber);
			orderUrl = orderUrl.replace(SiteoneintegrationConstants.QUERY_PARAM_DIVISIONID_OO, divisionId);
			final HttpHeaders httpHeaders = new HttpHeaders();
			if (isNewBoomiEnv)
			{

				httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER,
						Config.getString(SiteoneintegrationConstants.OPEN_ORDERS_NEW_SERVICE_KEY, null));
				return getSiteOneRestClient().execute(orderUrl, HttpMethod.GET, null, SiteoneOrderDetailsResponseData.class,
						UUID.randomUUID().toString(), SiteoneintegrationConstants.ORDER_DETAIL_SERVICE_NAME, httpHeaders);
			}
			else
			{
				httpHeaders.set(SiteoneintegrationConstants.AUTHORIZATION_HEADER, SiteoneintegrationConstants.AUTHORIZATION_TYPE_BASIC
						+ " " + Config.getString(SiteoneintegrationConstants.UE_ORDER_VERIFICATION_KEY, null));
				return getSiteOneRestClient().execute(orderUrl, HttpMethod.GET, null, SiteoneOrderDetailsResponseData.class,
						UUID.randomUUID().toString(), SiteoneintegrationConstants.ORDER_DETAIL_SERVICE_NAME, httpHeaders);
			}

		}
	}

	/**
	 * @return the siteOneRestClient
	 */
	public SiteOneRestClient<?, SiteoneOrderDetailsResponseData> getSiteOneRestClient()
	{
		return siteOneRestClient;
	}


	/**
	 * @param siteOneRestClient
	 *           the siteOneRestClient to set
	 */
	public void setSiteOneRestClient(final SiteOneRestClient<?, SiteoneOrderDetailsResponseData> siteOneRestClient)
	{
		this.siteOneRestClient = siteOneRestClient;
	}


	@Override
	public SearchPageData<OrderModel> getOrderListForAll(final BaseStoreModel store, final OrderStatus[] status,
			final PageableData pageableData, final String unitId, final String pageId, final String trimmedSearchParam,
			final String dateSort, final String paymentType)
	{
		validateParameterNotNull(store, "Store must not be null");
		validateParameterNotNull(pageableData, "PageableData must not be null");

		final List<B2BUnitModel> listOfAllAccounts = getAllSubAccountsAndShiptosList(unitId);


		return ((SiteOneCustomerAccountDao) customerAccountDao).findOrdersByUnit(store, status, pageableData, listOfAllAccounts,
				pageId, trimmedSearchParam, dateSort, paymentType);

	}

	@Override
	public SearchPageData<SiteOneInvoiceModel> getInvoiceList(final String shipToId, final InvoiceStatus[] status,
			final PageableData pageableData, final String trimmedSearchParam, final Date dateFromFinal, final Date dateToFinal)
	{

		validateParameterNotNull(shipToId, "ShipToId must not be null");
		validateParameterNotNull(pageableData, "PageableData must not be null");

		final List shipTos = Arrays.asList(shipToId);

		return getSiteOnePagedB2BCustomerDao().findInvoicesBasedOnShipToS(shipTos, status, pageableData, trimmedSearchParam,
				dateFromFinal, dateToFinal);
	}


	@Override
	public SearchPageData<SiteOneInvoiceModel> getInvoiceListForAll(final String shipToId, final InvoiceStatus[] status,
			final PageableData pageableData, final String trimmedSearchParam, final Date dateFromFinal, final Date dateToFinal)
	{

		validateParameterNotNull(shipToId, "ShipToId must not be null");
		validateParameterNotNull(pageableData, "PageableData must not be null");

		final List<String> shipTos = new ArrayList<String>();
		final List<B2BUnitModel> listOfAllAccounts = getAllSubAccountsAndShiptosList(shipToId);

		listOfAllAccounts.forEach((b2bunit) -> {
			shipTos.add(b2bunit.getUid());
		});

		return getSiteOnePagedB2BCustomerDao().findInvoicesBasedOnShipToS(shipTos, status, pageableData, trimmedSearchParam,
				dateFromFinal, dateToFinal);

	}

	@Override
	public SiteOneInvoiceModel getInvoiceForCode(final String code)
	{
		validateParameterNotNull(code, "Invoice code cannot be null");
		return ((SiteOneCustomerAccountDao) customerAccountDao).findInvoiceDetailsByCode(code);
	}

	@Override
	public B2BCustomerModel getCustomerByPK(final String pk)
	{
		validateParameterNotNull(pk, "Customer PK cannot be null");
		return ((SiteOneCustomerAccountDao) customerAccountDao).getCustomerByPK(pk);
	}

	@Override
	public boolean isAdmin(final B2BCustomerModel customer)
	{
		return customer.getGroups().stream().anyMatch(group -> group.getUid().equalsIgnoreCase("b2badmingroup"));
	}

	@Override
	public boolean isEmailOpted(final String email)
	{
		return ((SiteOneCustomerAccountDao) customerAccountDao).isEmailOpted(email);

	}


	public List<B2BUnitModel> getAllSubAccountsAndShiptosList(final String unitId)
	{

		final B2BUnitModel parentUnit = ((SiteOneB2BUnitService) b2bUnitService).getParentUnitForCustomer();
		List<B2BUnitModel> listOfAllSubAccountsAndShiptos = new ArrayList<B2BUnitModel>();

		if (null != parentUnit)
		{
			listOfAllSubAccountsAndShiptos = ((SiteOneCustomerAccountDao) customerAccountDao)
					.getAllAccountIDsInclundingInactive(parentUnit).stream().distinct().collect(Collectors.toList());

		}
		else
		{
			listOfAllSubAccountsAndShiptos.add((B2BUnitModel) b2bUnitService.getUnitForUid(unitId));

		}
		return listOfAllSubAccountsAndShiptos;
	}

	@Override
	public SearchPageData<B2BUnitModel> getPagedB2BDefaultUnits(final PageableData pageableData, final String searchParam)
	{
		final B2BUnitModel defaultUnit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
		return b2bUnitModelDao.findPagedUnits(pageableData.getSort(), pageableData, defaultUnit, searchParam);


	}

	@Override
	public List<OrderModel> getOrdersInTransit()
	{
		final B2BCustomerModel customerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		return ((SiteOneCustomerAccountDao) customerAccountDao).findOrdersInTransit(customerModel);
	}


	@Override
	public String getGuestCustomerUID(final String orderNumber)
	{
		return ((SiteOneCustomerAccountDao) customerAccountDao).getGuestCustomerUID(orderNumber);

	}

	@Override
	public List<OrderModel> getRecentOrders(final String unitId, final Integer numberOfOrders)
	{

		final List<B2BUnitModel> shipTos = Arrays.asList((B2BUnitModel) getB2bUnitService().getUnitForUid(unitId));

		return ((SiteOneCustomerAccountDao) customerAccountDao).getRecentOrders(shipTos, numberOfOrders);
	}

	@Override
	public List<OrderModel> getOrdersWithSameHybrisOrderNumber(final String hybrisOrderNumber)
	{
		return ((SiteOneCustomerAccountDao) customerAccountDao).getOrdersWithSameHybrisOrderNumber(hybrisOrderNumber);
	}
	
	@Override
	public List<OrderModel> getHeldOrderWithOrderNumber(final String orderNumber)
	{
		return ((SiteOneCustomerAccountDao) customerAccountDao).getHeldOrderWithOrderNumber(orderNumber);
	}

	@Override
	public boolean isBuyAgainProductExists(final String unitId)
	{
		return ((SiteOneCustomerAccountDao) customerAccountDao).isBuyAgainProductExists(unitId);
	}

	@Override
	public void saveLinkToPayCayanResponseModel(final LinkToPayCayanResponseModel linkToPayCayanResponseModel)
	{
		LOG.info("Inside saveLinkToPayCayanResponseModel" + linkToPayCayanResponseModel.getOrderNumber());
		getModelService().save(linkToPayCayanResponseModel);
	}


	@Override
	public void getDuplicateApprovalOrdersToRemove(final OrderModel hybrisOrderNumber)
	{
		final List<OrderModel> orders = ((SiteOneCustomerAccountDao) customerAccountDao)
				.getDuplicateApprovalOrders(hybrisOrderNumber.getCode());
		if (CollectionUtils.isNotEmpty(orders))
		{
			for (final OrderModel order : orders)
			{
				if (!(order.getPk().toString().equalsIgnoreCase(hybrisOrderNumber.getPk().toString())))
				{
					getModelService().remove(order);
				}
			}
		}
	}


	@Override
	public BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}


	@Override
	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}
}