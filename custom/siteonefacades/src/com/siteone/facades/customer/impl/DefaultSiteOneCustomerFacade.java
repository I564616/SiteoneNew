/**
 *
 */
package com.siteone.facades.customer.impl;

import com.siteone.customer.event.PendoContextData;
import com.siteone.customer.event.PendoEventRequest;
import com.siteone.customer.event.PendoPropertiesData;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.B2BUserFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.cms2.servicelayer.services.CMSSiteService;
import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.storesession.data.CurrencyData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.user.data.SiteOneGuestUserData;
import de.hybris.platform.commercefacades.user.exceptions.PasswordMismatchException;
import de.hybris.platform.commercefacades.util.CommerceUtils;
import de.hybris.platform.commerceservices.customer.TokenInvalidatedException;
import de.hybris.platform.commerceservices.enums.CustomerType;
import de.hybris.platform.commerceservices.i18n.CommerceCommonI18NService;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.commercewebservicescommons.dto.store.PointOfServiceWsDTO;
import de.hybris.platform.core.enums.PhoneContactInfoType;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.PhoneContactInfoModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.order.exceptions.CalculationException;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.event.events.AbstractEvent;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.servicelayer.user.exceptions.PasswordPolicyViolationException;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.util.Config;
import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.json.JSONException;
import jakarta.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.util.Assert;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import com.siteone.commerceservices.dto.user.CustomerSpecificWsDTO;
import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.customer.SiteOneCustomerAccountService;
import com.siteone.core.event.DeclinedCardAttemptEmailEvent;
import com.siteone.core.event.KountDeclineEvent;
import com.siteone.core.event.PasswordChangedEvent;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.kount.service.impl.DefaultSiteOneKountService;
import com.siteone.core.model.LinkToPayCayanResponseModel;
import com.siteone.core.model.LinkToPayPaymentProcessModel;
import com.siteone.core.model.SiteoneCCPaymentAuditLogModel;
import com.siteone.core.model.SiteoneKountDataModel;
import com.siteone.core.model.TalonOneEnrollmentModel;
import com.siteone.core.order.services.SiteOneOrderService;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.facade.LeadGenarationData;
import com.siteone.facade.payment.cayan.SiteOneCayanTransportFacade;
import com.siteone.facades.address.data.SiteOneAddressVerificationData;
import com.siteone.facades.address.data.SiteOneAddressVerificationStatusData;
import com.siteone.facades.cart.SiteOneCartFacade;
import com.siteone.facades.checkout.SiteOneB2BCheckoutFacade;
import com.siteone.facades.company.SiteOneB2BUserFacade;
import com.siteone.facades.constants.SiteoneFacadesConstants;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.exceptions.AddressNotCreatedOrUpdatedInUEException;
import com.siteone.facades.exceptions.AddressNotRemovedInUEException;
import com.siteone.facades.exceptions.ContactNotCreatedOrUpdatedInUEException;
import com.siteone.facades.exceptions.OktaUserAlreadyActiveException;
import com.siteone.facades.exceptions.RecentlyUsedPasswordException;
import com.siteone.facades.exceptions.ServiceUnavailableException;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.populators.SiteOneAddressReversePopulator;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.integration.address.data.SiteOneWsAddressResponseData;
import com.siteone.integration.addressverification.data.SiteOneWsAddressVerificationResponseData;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.customer.data.CustomFields;
import com.siteone.integration.customer.data.SiteOneWsB2BCustomerResponseData;
import com.siteone.integration.customer.data.UserEmailData;
import com.siteone.integration.customer.talonOneLoyaltyEnrollment.data.SiteOneWsTalonOneLoyaltyEnrollmentResponseData;
import com.siteone.integration.exception.okta.OktaInvalidPasswordException;
import com.siteone.integration.exception.okta.OktaInvalidTokenException;
import com.siteone.integration.exception.okta.OktaPasswordMismatchException;
import com.siteone.integration.exception.okta.OktaRecentlyUsedPasswordException;
import com.siteone.integration.linktopay.order.data.SiteOneWsLinkToPayOrderResponseData;
import com.siteone.integration.okta.OKTAAPI;
import com.siteone.integration.services.okta.data.OktaChangePasswordResponseData;
import com.siteone.integration.services.okta.data.OktaCreateOrUpdateUserResponseData;
import com.siteone.integration.services.okta.data.OktaResetPasswordResponseData;
import com.siteone.integration.services.okta.data.OktaSetPasswordResponseData;
import com.siteone.integration.services.okta.data.OktaUnlockUserResponseData;
import com.siteone.integration.services.okta.data.OktaVerifyRecoveryTokenResponseData;
import com.siteone.integration.services.partnerprogram.PartnerProgramSSOWebService;
import com.siteone.integration.services.ue.SiteOneAddressVerificationWebService;
import com.siteone.integration.services.ue.SiteOneAddressWebService;
import com.siteone.integration.services.ue.SiteOneContactWebService;
import com.siteone.integration.services.ue.SiteOneLinkToPayWebService;
import com.siteone.integration.services.ue.SiteOneTalonOneLoyaltyEnrollmentWebService;
import com.siteone.integration.services.ue.SiteOneVerifyGuestContactWebService;
import com.siteone.integration.verifyguestcontact.data.SiteOneWsVerifyGuestContactResponseData;
import com.siteone.integration.watson.SiteOneLeadGenerationService;
import com.siteone.integration.watson.SiteOneUserPreferenceService;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * @author Abdul Rahman Sheikh M
 *
 */
public class DefaultSiteOneCustomerFacade extends DefaultCustomerFacade implements SiteOneCustomerFacade
{
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneCustomerFacade.class);


	private static final String BOOMI_PLATFORM = "boomiPlatformLinux";
	private static final String SECRET_KEY = "app.secret.key";
	private static final String[] creditCodeList =
	{ "A", "S", "B", "F", "H", "N", "R", "U", "X" };
	private static final String[] payBillEnableList =
	{ "F", "H", "N", "U" }; 
	
   private static final String RESPONSE_DECLINED = "D";
   private static final String RESPONSE_APPROVED = "A";
	private static final String DECLINED = "Decline";

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "storeFinderFacade")
	private SiteOneStoreFinderFacade storeFinderFacade;

	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;


	@Resource(name = "pointOfServiceConverter")
	private Converter<PointOfServiceModel, PointOfServiceData> pointOfServiceConverter;

	@Resource(name = "cmsSiteService")
	private CMSSiteService cmsSiteService;

	@Resource(name = "storeFinderService")
	private SiteOneStoreFinderService storeFinderService;

	@Resource(name = "b2bUnitConverter")
	private Converter<B2BUnitModel, B2BUnitData> b2bUnitConverter;

	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;

	@Resource(name = "siteOneAddressWebService")
	private SiteOneAddressWebService siteOneAddressWebService;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "siteOneAddressVerificationWebService")
	private SiteOneAddressVerificationWebService siteOneAddressVerificationWebService;

	private Converter<SiteOneWsAddressVerificationResponseData, SiteOneAddressVerificationData> siteOneAddressVerificationResponseConverter;

	@Resource(name = "siteOneUserPreferenceService")
	private SiteOneUserPreferenceService siteOneUserPreferenceService;

	@Resource(name = "userService")
	private UserService userService;

	private Converter<B2BCustomerModel, CustomerData> siteonecustomerConverter;

	private OKTAAPI oktaAPI;

	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;

	@Resource(name = "siteOneLeadGenerationService")
	private SiteOneLeadGenerationService leadGenerationService;

	@Resource(name = "partnerProgramSSOWebService")
	private PartnerProgramSSOWebService partnerProgramSSOWebService;

	@Resource(name = "cartFacade")
	private CartFacade cartFacade;

	@Resource(name = "eventService")
	private EventService eventService;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Resource(name = "b2bCheckoutFacade")
	private CheckoutFacade b2bCheckoutFacade;

	@Resource(name = "siteOneContactWebService")
	private SiteOneContactWebService siteOneContactWebService;

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "b2bUserFacade")
	private B2BUserFacade b2bUserFacade;

	private final String YES = "Yes";

	private final String Result = "false";

	private final String IS_ADMIN = "isAdmin";

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "siteOneOrderService")
	private SiteOneOrderService siteOneOrderService;

	@Resource(name = "siteOneVerifyGuestContactWebService")
	private SiteOneVerifyGuestContactWebService siteOneVerifyGuestContactWebService;

	@Resource(name = "siteOneKountService")
	private DefaultSiteOneKountService defaultSiteOneKountService;

	@Resource(name = "siteOneTalonOneLoyaltyEnrollmentWebService")
	private SiteOneTalonOneLoyaltyEnrollmentWebService siteOneTalonOneLoyaltyEnrollmentWebService;


	@Resource(name = "siteOneLinkToPayWebService")
	private SiteOneLinkToPayWebService siteOneLinkToPayWebService;

	@Resource(name = "customerAccountService")
	private SiteOneCustomerAccountService siteOneCustomerAccountService;

	@Resource(name = "commerceCommonI18NService")
	private CommerceCommonI18NService commerceCommonI18NService;
	
	@Resource(name = "siteOneCayanTransportFacade")
	private SiteOneCayanTransportFacade siteOneCayanTransportFacade;

	@Resource(name = "siteOnePointOfServiceConverter")
	private Converter<PointOfServiceModel, PointOfServiceData> siteOnePointOfServiceConverter;
	
	/**
	 * @return the siteonecustomerConverter
	 */
	public Converter<B2BCustomerModel, CustomerData> getSiteonecustomerConverter()
	{
		return siteonecustomerConverter;
	}

	/**
	 * @param siteonecustomerConverter
	 *           the siteonecustomerConverter to set
	 */
	public void setSiteonecustomerConverter(final Converter<B2BCustomerModel, CustomerData> siteonecustomerConverter)
	{
		this.siteonecustomerConverter = siteonecustomerConverter;
	}

	/**
	 * @return the siteOneUserPreferenceService
	 */
	public SiteOneUserPreferenceService getSiteOneUserPreferenceService()
	{
		return siteOneUserPreferenceService;
	}

	/**
	 * @param siteOneUserPreferenceService
	 *           the siteOneUserPreferenceService to set
	 */
	public void setSiteOneUserPreferenceService(final SiteOneUserPreferenceService siteOneUserPreferenceService)
	{
		this.siteOneUserPreferenceService = siteOneUserPreferenceService;
	}
	
	/**
	 * @return the commerceCommonI18NService
	 */
	public CommerceCommonI18NService getCommerceCommonI18NService()
	{
		return commerceCommonI18NService;
	}

	/**
	 * @param commerceCommonI18NService the commerceCommonI18NService to set
	 */
	public void setCommerceCommonI18NService(CommerceCommonI18NService commerceCommonI18NService)
	{
		this.commerceCommonI18NService = commerceCommonI18NService;
	}


	@Override
	public void syncSessionStore(final String geoLocatedStoreId)
	{
		final PointOfServiceModel preferredStore = ((SiteOneCustomerAccountService) getCustomerAccountService())
				.getCustomerPreferredStore();

		if (null != preferredStore && BooleanUtils.isTrue(preferredStore.getIsActive()))
		{
			storeSessionFacade.setSessionStore(pointOfServiceConverter.convert(preferredStore));
		}
		else if (null != geoLocatedStoreId)
		{
			this.setGeoLocatedStore(geoLocatedStoreId);
		}
		else
		{
			this.setDefaultStore();
		}
	}

	/**
	 *
	 */
	@Override
	public void setDefaultStore()
	{
		final CMSSiteModel siteModel = cmsSiteService.getCurrentSite();
		if (null != siteModel && null != siteModel.getDefaultStore())
		{
			storeSessionFacade.setSessionStore(pointOfServiceConverter.convert(siteModel.getDefaultStore()));
		}
		else
		{
			storeSessionFacade.setSessionStore(null);
		}
	}

	/**
	 * @param geoLocatedStoreId
	 *
	 */
	@Override
	public void setGeoLocatedStore(final String geoLocatedStoreId)
	{
		final PointOfServiceModel geoLocatedPos = storeFinderService.getStoreForId(geoLocatedStoreId);

		if (null != geoLocatedPos && geoLocatedPos.getIsActive())
		{
			storeSessionFacade.setSessionStore(pointOfServiceConverter.convert(geoLocatedPos));
		}
		else
		{
			this.setDefaultStore();
		}
	}

	@Override
	public Collection<PointOfServiceData> getMyStores()
	{
		final Collection<PointOfServiceData> myStores = new ArrayList<>();

		final Set<PointOfServiceModel> myStoresList = ((SiteOneCustomerAccountService) getCustomerAccountService())
				.getCustomerStoreList();

		if (CollectionUtils.isNotEmpty(myStoresList))
		{
			myStoresList.stream().forEach(myStore -> {
				myStores.add(siteOnePointOfServiceConverter.convert(myStore));
			});
		}
		return myStores;
	}


	@Override
	public List<String> getMyStoresIdList()
	{
		final Set<PointOfServiceModel> myStoresList = ((SiteOneCustomerAccountService) getCustomerAccountService())
				.getCustomerStoreList();
		final List<String> myStoresIdList = new ArrayList<>();

		if (CollectionUtils.isNotEmpty(myStoresList))
		{
			myStoresList.stream().forEach(myStore -> {
				myStoresIdList.add(myStore.getStoreId());
			});
		}
		return myStoresIdList;
	}

	@Override
	public PointOfServiceData getPreferredStore()
	{
		PointOfServiceData posData = null;

		final PointOfServiceModel pointOfServiceModel = ((SiteOneCustomerAccountService) getCustomerAccountService())
				.getCustomerPreferredStore();

		if (null != pointOfServiceModel)
		{
			posData = pointOfServiceConverter.convert(pointOfServiceModel);
		}
		return posData;
	}

	@Override
	public PointOfServiceData getHomeBranch()
	{
		PointOfServiceData posData = null;

		final PointOfServiceModel pointOfServiceModel = ((SiteOneCustomerAccountService) getCustomerAccountService())
				.getCustomerHomeBranch();

		if (null != pointOfServiceModel)
		{
			posData = pointOfServiceConverter.convert(pointOfServiceModel);
		}

		return posData;
	}

	@Override
	public void makeMyStore(final String storeId)
	{
		final PointOfServiceModel pointOfServiceModel = ((SiteOneCustomerAccountService) getCustomerAccountService())
				.makeMyStore(storeId);
		if (null != pointOfServiceModel)
		{
			final PointOfServiceData pointOfServiceData = pointOfServiceConverter.convert(pointOfServiceModel);
			storeSessionFacade.setSessionStore(pointOfServiceData);
		}
		else
		{
			this.setDefaultStore();
		}
	}

	@Override
	public void saveStore(final String storeId)
	{
		((SiteOneCustomerAccountService) getCustomerAccountService()).saveStore(storeId);
	}

	@Override
	public void removeFromMyStore(final String storeId, final String geoLocatedStoreId)
	{
		((SiteOneCustomerAccountService) getCustomerAccountService()).removeFromMyStoreList(storeId);

		final PointOfServiceModel prefferedStore = ((SiteOneCustomerAccountService) getCustomerAccountService())
				.getCustomerPreferredStore();

		PointOfServiceData pointOfServiceData = null;
		if (null != geoLocatedStoreId)
		{
			pointOfServiceData = storeFinderFacade.getStoreForId(geoLocatedStoreId);
		}

		if (null != prefferedStore && prefferedStore.getStoreId().equalsIgnoreCase(storeId))
		{
			((SiteOneCustomerAccountService) getCustomerAccountService()).removeFromMyStore();

			if (null != pointOfServiceData && pointOfServiceData.getIsActive())
			{
				storeSessionFacade.setSessionStore(pointOfServiceData);
			}
			else
			{
				this.setDefaultStore();
			}
		}
		else if (null == b2bCustomerService.getCurrentB2BCustomer())
		{
			if (null != pointOfServiceData && pointOfServiceData.getIsActive())
			{
				storeSessionFacade.setSessionStore(pointOfServiceData);
			}
			else
			{
				this.setDefaultStore();
			}
		}
	}

	@Override
	public List<AddressData> getAddressBookForUnit(final String unitId)
	{
		final B2BUnitModel b2bUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(unitId);

		// Get the Unit addresses
		final Collection<AddressModel> addresses = ((SiteOneCustomerAccountService) getCustomerAccountService())
				.getAddressBookForUnit(b2bUnitModel);

		if (CollectionUtils.isNotEmpty(addresses))
		{
			final List<AddressData> result = new ArrayList<AddressData>();

			for (final AddressModel address : addresses)
			{
				// Filter out invalid addresses for the site
				final AddressData addressData = getAddressConverter().convert(address);
				final AddressData defaultAddress = this.getDefaultShippingAddressForUnit(unitId);
				if (defaultAddress != null && defaultAddress.getId() != null && defaultAddress.getId().equals(addressData.getId()))
				{
					addressData.setDefaultAddress(true);
					result.add(0, addressData);
				}
				else
				{
					result.add(addressData);
				}
			}
			return result;
		}
		return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facades.customer.SiteOneCustomerFacade#getBillingAddressesForUnit(java.lang.String)
	 */
	@Override
	public Collection<AddressData> getBillingAddressesForUnit(final String unitId)
	{
		final B2BUnitModel b2bUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(unitId);
		final Collection<AddressModel> billingAddresses = ((SiteOneCustomerAccountService) getCustomerAccountService())
				.getBillingAddressesForUnit(b2bUnitModel);
		if (CollectionUtils.isNotEmpty(billingAddresses))
		{
			final List<AddressData> result = new ArrayList<AddressData>();

			for (final AddressModel address : billingAddresses)
			{
				final AddressData addressData = getAddressConverter().convert(address);
				result.add(addressData);
			}
			return result;
		}
		return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facades.customer.SiteOneCustomerFacade#getBillingAddressesForUnit(java.lang.String)
	 */
	@Override
	public Collection<AddressData> getDefaultBillingAddressesForUnit(final String unitId)
	{
		/* SE-6461 The ask was to show only 1 default Billing Address on My Billing Addresses from MyAccount Dashboard */
		final List<AddressData> result = new ArrayList<AddressData>();
		final AddressData addressData = getDefaultBillingAddressForUnit(unitId);
		if (null != addressData)
		{
			result.add(addressData);
			return result;
		}
		return Collections.emptyList();
	}

	@Override
	public Collection<AddressData> getShippingAddresssesForUnit(final String unitId)
	{
		final B2BUnitModel b2bUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(unitId);
		final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();

		final Collection<AddressModel> shippingAddresses = ((SiteOneCustomerAccountService) getCustomerAccountService())
				.getShippingAddressesForUnit(b2bUnitModel);
		final String defaultAddress = customer.getDefaultShipmentAddress()!=null?customer.getDefaultShipmentAddress().getPk().getLongValueAsString():null;
		
		

		if (CollectionUtils.isNotEmpty(shippingAddresses))
		{
			final List<AddressData> result = new ArrayList<>();
			for (final AddressModel address : shippingAddresses)
			{
				/**
				 * Start of Modification for Defect SE-6461 : Do not show Duplicate Delivery Addresses for the Unit in the
				 * Dashboard and as well in the Checkout Flow
				 */
				if (address.getVisibleInAddressBook() != null && address.getVisibleInAddressBook().booleanValue()
						&& !address.getBillingAddress().booleanValue())
				{
				
					final AddressData addressData = getAddressConverter().convert(address);
					if(null != defaultAddress && addressData !=null && defaultAddress.equalsIgnoreCase(addressData.getId()))
					{
						addressData.setDefaultUserAddress(Boolean.TRUE);
						result.add(0, addressData);
					}
					else
					{
					result.add(addressData);
					}
				}
			}
			return result;
		}
		return Collections.emptyList();
	}

	@Override
	public Collection<AddressData> getAllShippingAddresssesForUnit(final String unitId)
	{
		final B2BUnitModel b2bUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(unitId);

		final Collection<AddressModel> shippingAddresses = ((SiteOneCustomerAccountService) getCustomerAccountService())
				.getShippingAddressesForUnit(b2bUnitModel);

		if (CollectionUtils.isNotEmpty(shippingAddresses))
		{
			final List<AddressData> result = new ArrayList<AddressData>();

			for (final AddressModel address : shippingAddresses)
			{
				final AddressData addressData = getAddressConverter().convert(address);
				result.add(addressData);

			}
			return result;
		}
		return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facades.customer.SiteOneCustomerFacade#getDefaultBillingAddressForUnit(java.lang.String)
	 */
	@Override
	public AddressData getDefaultBillingAddressForUnit(final String unitId)
	{
		AddressData addressData = null;
		final B2BUnitModel b2bUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(unitId);

		final AddressModel billingAddress = ((SiteOneCustomerAccountService) getCustomerAccountService())
				.getBillingAddressForUnit(b2bUnitModel);

		if (null != billingAddress)
		{
			addressData = getAddressConverter().convert(billingAddress);
		}

		return addressData;
	}

	@Override
	public AddressData getDefaultShippingAddressForUnit(final String unitId)
	{
		AddressData addressData = null;
		final B2BUnitModel b2bUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(unitId);

		final AddressModel shippingAddress = ((SiteOneCustomerAccountService) getCustomerAccountService())
				.getShippingAddressForUnit(b2bUnitModel);

		if (null != shippingAddress)
		{
			addressData = getAddressConverter().convert(shippingAddress);
		}

		return addressData;
	}

	@Override
	public boolean isAddressBookEmpty(final String unitId)
	{
		final Collection<AddressData> addresses = this.getShippingAddresssesForUnit(unitId);

		return addresses == null || addresses.isEmpty();
	}


	/**
	 * To validate siteone user address using mellisa
	 *
	 * @param addressData
	 *           - siteone user address details
	 * @return siteOneAddressVerificationData - verified address details
	 */
	@Override
	public SiteOneAddressVerificationData validateAddress(final AddressData addressData)
	{

		final SiteOneWsAddressVerificationResponseData siteOneWsAddressVerificationResponseData = getSiteOneAddressVerificationWebService()
				.validateAddress(addressData);

		SiteOneAddressVerificationData siteOneAddressVerificationData;
		if (null != siteOneWsAddressVerificationResponseData)
		{
			siteOneAddressVerificationData = getSiteOneAddressVerificationResponseConverter()
					.convert(siteOneWsAddressVerificationResponseData);
			return siteOneAddressVerificationData;
		}
		else
		{
			//Setting error code for null response
			siteOneAddressVerificationData = new SiteOneAddressVerificationData();
			final SiteOneAddressVerificationStatusData siteOneAddressVerificationStatusData = new SiteOneAddressVerificationStatusData();
			siteOneAddressVerificationStatusData.setCode(SiteoneCoreConstants.ADDRESS_VERIFICATION_NULL_RESPONSE_CODE);
			siteOneAddressVerificationData.setStatus(siteOneAddressVerificationStatusData);
		}

		return siteOneAddressVerificationData;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facades.customer.SiteOneCustomerFacade#addAddress(de.hybris.platform.commercefacades.user.data.
	 * AddressData, java.lang.String)
	 */
	@Override
	public void addAddress(final AddressData addressData, final String unitId)
	{

		final B2BUnitModel b2bUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(unitId);

		final boolean makeThisAddressTheDefault = this.getDefaultShippingAddressForUnit(unitId) == null 
				&& addressData.isVisibleInAddressBook();

		// Create the new address model
		final AddressModel newAddress = getModelService().create(AddressModel.class);
		((SiteOneAddressReversePopulator) getAddressReversePopulator()).populate(addressData, newAddress);


		//Call to Address Webservice to create address in UE
		SiteOneWsAddressResponseData addressResponseData = null;
		try
		{
			addressResponseData = getSiteOneAddressWebService().createOrUpdateAddress(newAddress, b2bUnitModel,
					SiteoneintegrationConstants.OPERATION_TYPE_CREATE,
					Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
		}
		catch (final ResourceAccessException resourceAccessException)
		{
			LOG.error("Not able to establish connection with UE to create address", resourceAccessException);
			throw new ServiceUnavailableException("404");
		}


		if (null == addressResponseData || null == addressResponseData.getAddressId())
		{
			LOG.error("Not able to create address in UE");
			throw new AddressNotCreatedOrUpdatedInUEException("Address not created in UE");
		}
		else
		{
			newAddress.setGuid(addressResponseData.getAddressId().toUpperCase());
		}



		// Store the address against the Unit
		((SiteOneCustomerAccountService) getCustomerAccountService()).saveAddressEntry(b2bUnitModel, newAddress);

		// Update the address ID in the newly created address
		addressData.setId(newAddress.getPk().toString());

		if (makeThisAddressTheDefault)
		{
			((SiteOneCustomerAccountService) getCustomerAccountService()).setDefaultAddressEntry(b2bUnitModel, newAddress);
		}



	}

	@Override
	public void setDefaultAddress(final String addressData, final String unitId)
	{
		if(unitId !=null)
		{
		final B2BUnitModel b2bUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(unitId);
		final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		final AddressModel addressModel = ((SiteOneCustomerAccountService) getCustomerAccountService())
				.getAddressForCode(b2bUnitModel, addressData);
		if (addressModel != null)
		{
			customer.setDefaultShipmentAddress(addressModel);
			getModelService().save(customer);
		}
		}
		
	
		
	}

	@Override
	public void editAddress(final AddressData addressData, final String unitId)
	{
		if (unitId.equalsIgnoreCase(addressData.getUnitId()))
		{
			final B2BUnitModel b2bUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(unitId);

			final AddressModel addressModel = ((SiteOneCustomerAccountService) getCustomerAccountService())
					.getAddressForCode(b2bUnitModel, addressData.getId());

			addressModel.setRegion(null);
			getAddressReversePopulator().populate(addressData, addressModel);


			//Calling Address Webservice to update address.
			SiteOneWsAddressResponseData addressResponseData = null;
			try
			{
				addressResponseData = getSiteOneAddressWebService().createOrUpdateAddress(addressModel, b2bUnitModel,
						SiteoneintegrationConstants.OPERATION_TYPE_UPDATE,
						Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
			}
			catch (final ResourceAccessException resourceAccessException)
			{
				LOG.error("Not able to establish connection with UE to update address", resourceAccessException);
				throw new ServiceUnavailableException("404");
			}

			if (null == addressResponseData)
			{
				LOG.error("Not able to update address in UE");
				throw new AddressNotCreatedOrUpdatedInUEException("Address not updated in UE");
			}



			// Store the address against the Unit
			((SiteOneCustomerAccountService) getCustomerAccountService()).saveAddressEntry(b2bUnitModel, addressModel);
			if (addressData.isDefaultAddress())
			{
				((SiteOneCustomerAccountService) getCustomerAccountService()).setDefaultAddressEntry(b2bUnitModel, addressModel);
			}
			else if (addressModel.equals(b2bUnitModel.getShippingAddress()))
			{
				((SiteOneCustomerAccountService) getCustomerAccountService()).clearDefaultAddressEntry(b2bUnitModel);
			}
		}
		else

		{
			final B2BUnitModel oldUnit = (B2BUnitModel) b2bUnitService.getUnitForUid(unitId);
			final B2BUnitModel newUnit = (B2BUnitModel) b2bUnitService.getUnitForUid(addressData.getUnitId());

			final AddressModel oldAddressModel = ((SiteOneCustomerAccountService) getCustomerAccountService())
					.getAddressForCode(oldUnit, addressData.getId());

			final String oldAddressGuid = oldAddressModel.getGuid();

			final AddressModel oldDefaultAddress = ((SiteOneCustomerAccountService) getCustomerAccountService())
					.getShippingAddressForUnit(oldUnit);

			if (null != oldDefaultAddress
					&& oldDefaultAddress.getPk().toString().equalsIgnoreCase(oldAddressModel.getPk().toString()))
			{
				oldUnit.setShippingAddress(null);
			}

			final List<AddressModel> removedAddressList = oldUnit.getAddresses().stream()
					.filter(address -> !address.getPk().toString().equalsIgnoreCase(addressData.getId())).collect(Collectors.toList());

			oldUnit.setAddresses(removedAddressList);



			//Calling Address webservice to remove address from old unit.
			SiteOneWsAddressResponseData siteOneWsAddressResponseData = null;
			try
			{
				siteOneWsAddressResponseData = siteOneAddressWebService.deleteAddress(oldAddressGuid, oldUnit.getGuid(),
						Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
			}
			catch (final ResourceAccessException resourceAccessException)
			{
				LOG.error("Not able to establish connection with UE to remove address", resourceAccessException);
				throw new ServiceUnavailableException("404");
			}

			if (null == siteOneWsAddressResponseData)
			{
				//isAddressDeleted = false;
				LOG.error("Not able to remove address in UE");
				throw new AddressNotRemovedInUEException("Address not removed in UE");
			}
			getModelService().save(oldUnit);
			getModelService().refresh(oldUnit);

			final AddressModel newAddress = getModelService().create(AddressModel.class);

			getAddressReversePopulator().populate(addressData, newAddress);


			//Calling Address Webservice to update address.


			SiteOneWsAddressResponseData addressResponseData = null;
			try
			{
				addressResponseData = getSiteOneAddressWebService().createOrUpdateAddress(newAddress, newUnit,
						SiteoneintegrationConstants.OPERATION_TYPE_CREATE,
						Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
			}
			catch (final ResourceAccessException resourceAccessException)
			{
				LOG.error("Not able to establish connection with UE to update address", resourceAccessException);
				throw new ServiceUnavailableException("404");
			}

			if (null == addressResponseData || null == addressResponseData.getAddressId())
			{
				LOG.error("Not able to update address in UE");
				throw new AddressNotCreatedOrUpdatedInUEException("Address not updated in UE");

			}
			else
			{
				newAddress.setGuid(addressResponseData.getAddressId().toUpperCase());
			}

			// Store the address against the Unit
			((SiteOneCustomerAccountService) getCustomerAccountService()).saveAddressEntry(newUnit, newAddress);
			if (addressData.isDefaultAddress())
			{
				((SiteOneCustomerAccountService) getCustomerAccountService()).setDefaultAddressEntry(newUnit, newAddress);
			}
			else if (newAddress.equals(newUnit.getShippingAddress()))
			{
				((SiteOneCustomerAccountService) getCustomerAccountService()).clearDefaultAddressEntry(newUnit);
			}

			addressData.setId(newAddress.getPk().toString());



		}

	}


	@Override
	public void removeAddress(final AddressData addressData, final String unitId) throws ServiceUnavailableException
	{
		final B2BUnitModel b2bUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(unitId);
		for (final AddressModel addressModel : ((SiteOneCustomerAccountService) getCustomerAccountService())
				.getAddressBookForUnit(b2bUnitModel))
		{
			if (addressData.getId().equals(addressModel.getPk().getLongValueAsString()))
			{
				final String addressGuid = addressModel.getGuid();

				SiteOneWsAddressResponseData responseData = null;

				try
				{

					responseData = siteOneAddressWebService.deleteAddress(addressGuid, b2bUnitModel.getGuid(),
							Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
				}
				catch (final ResourceAccessException resourceAccessException)
				{
					LOG.error("Not able to establish connection with UE to remove address", resourceAccessException);
					throw new ServiceUnavailableException("404");
				}
				if (null != responseData)
				{
					((SiteOneCustomerAccountService) getCustomerAccountService()).deleteAddressEntry(b2bUnitModel, addressModel);
				}
				else
				{
					LOG.error("Not able to remove address in UE");
					throw new AddressNotRemovedInUEException("Address not removed in UE");
				}

				break;
			}
		}

	}


	@Override
	public CustomerData fetchPersonalDetails()
	{
		final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();


		final CustomerData custData = getSiteonecustomerConverter().convert(customer);

		return custData;
	}


	@Override
	public UserEmailData fetchPreferences()
	{
		final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();

		final CustomerData custData = getSiteonecustomerConverter().convert(customer);

		//final String email = customer.getContactEmail();
		UserEmailData userData = null;
		try
		{
			userData = getSiteOneUserPreferenceService().fetchPreferenceData(customer);
		}
		catch (final ResourceAccessException | IOException e)
		{
			// YTODO Auto-generated catch block
			LOG.error(e.getMessage(), e);
		}
		catch (final RestClientException e)
		{
			// YTODO Auto-generated catch block
			LOG.error(e.getMessage(), e);
		}

		final List<CustomFields> customFields = userData != null ? userData.getCustomFields() : null;
		if (null != customFields)
		{
			final CustomFields fieldIsAdmin = new CustomFields();
			fieldIsAdmin.setName(IS_ADMIN);
			if (null != custData.getIsAdmin())
			{
				fieldIsAdmin.setValue(custData.getIsAdmin().toString());
			}
			customFields.add(fieldIsAdmin);
		}

		return userData;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facades.customer.SiteOneCustomerFacade#saveUserPreference(java.lang.String, java.lang.String)
	 */
	@Override
	public String saveUserPreference(final String emailType, final String emailTopicPreference, final String orderPromo)

	{

		final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();

		final String email = customer.getContactEmail();


		String response = null;
		try
		{
			response = getSiteOneUserPreferenceService().saveEmailPreference(emailType, email, emailTopicPreference, orderPromo);
		}
		catch (final ResourceAccessException | IOException e)
		{
			// YTODO Auto-generated catch block
			LOG.error(e.getMessage(), e);
		}
		catch (final RestClientException e)
		{
			// YTODO Auto-generated catch block
			LOG.error(e.getMessage(), e);
		}

		if (orderPromo.equals(YES))
		{
			customer.setOrderPromoOption(Boolean.TRUE);
			modelService.save(customer);
		}
		else
		{
			customer.setOrderPromoOption(Boolean.FALSE);
			modelService.save(customer);
		}


		return response;
	}

	@Override
	public SearchPageData<B2BUnitData> getPagedB2BUnits(final PageableData pageableData, final String unitId,
			final String searchParam)
	{
		final SearchPageData<B2BUnitModel> b2bUnitsPageData = ((SiteOneCustomerAccountService) getCustomerAccountService())
				.getPagedB2BUnits(pageableData, unitId, searchParam);
		return CommerceUtils.convertPageData(b2bUnitsPageData, this.getB2bUnitConverter());
	}

	@Override
	public void loginSuccess()
	{

		//super.loginSuccess();
		storeSessionFacade.setSessionShipTo(((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer());
		getSessionService().removeAttribute("guestUser");
		loginSuccessInternal();

	}

	/**
	 * @return the b2bUnitConverter
	 */
	public Converter<B2BUnitModel, B2BUnitData> getB2bUnitConverter()
	{
		return b2bUnitConverter;
	}

	/**
	 * @param b2bUnitConverter
	 *           the b2bUnitConverter to set
	 */
	public void setB2bUnitConverter(final Converter<B2BUnitModel, B2BUnitData> b2bUnitConverter)
	{
		this.b2bUnitConverter = b2bUnitConverter;
	}

	@Override
	public boolean isUserAvailable(final String uid)
	{
		boolean found = false;
		B2BCustomerModel customer = null;
		try
		{
			try
			{
				customer = (B2BCustomerModel) getUserService().getUserForUID(uid);
			}
			catch (final ClassCastException e)
			{
				found = false;
				LOG.error("User Id does not relates to B2BCustomer" + e);
			}
			if (null != customer && null != customer.getDefaultB2BUnit())
			{
				if (customer.getActive().booleanValue() && customer.getDefaultB2BUnit().getActive().booleanValue())
				{
					found = true;
				}
			}
		}
		catch (final UnknownIdentifierException e)
		{
			found = false;
			LOG.error(e);
		}

		return found;
	}

	private boolean isValidGuestEmail(final String uid)
	{
		boolean found = false;
		final SiteOneWsVerifyGuestContactResponseData guestContactResponseData = getSiteOneVerifyGuestContactWebService()
				.verifyGuestInUE(uid, Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
		if (guestContactResponseData != null && null != guestContactResponseData.getStatus()
				&& null != guestContactResponseData.getResult())
		{
			if (Boolean.valueOf(guestContactResponseData.getResult()))
			{
				found = true;
			}
		}
		return found;
	}

	@Override
	public void changePassword(final String oldPassword, final String newPassword)
			throws PasswordMismatchException, PasswordPolicyViolationException
	{
		// YTODO Auto-generated method stub
		final UserModel currentUser = getCurrentUser();

		OktaChangePasswordResponseData response = null;
		try
		{

			response = getOktaAPI().changePasswordForUser(oldPassword, newPassword, currentUser.getUid());
		}
		catch (final OktaPasswordMismatchException oktaPasswordMismatchException)
		{
			LOG.error(oktaPasswordMismatchException.getMessage(), oktaPasswordMismatchException);
			throw new PasswordMismatchException(oktaPasswordMismatchException.getMessage());
		}
		catch (final OktaInvalidPasswordException oktaInvalidPasswordException)
		{
			LOG.error(oktaInvalidPasswordException.getMessage(), oktaInvalidPasswordException);
			throw new PasswordPolicyViolationException(oktaInvalidPasswordException.getPasswordPolicyViolation());
		}
		catch (final OktaRecentlyUsedPasswordException oktaRecentlyUsedPasswordException)
		{
			LOG.error(oktaRecentlyUsedPasswordException.getMessage(), oktaRecentlyUsedPasswordException);
			throw new RecentlyUsedPasswordException(oktaRecentlyUsedPasswordException.getMessage());
		}


		if (null != response && null != response.getPassword())
		{
			getEventService().publishEvent(initializeEvent(new PasswordChangedEvent(), (CustomerModel) currentUser));

		}


	}

	/**
	 * @param event
	 * @param currentUser
	 * @return
	 */
	private AbstractEvent initializeEvent(final PasswordChangedEvent event, final CustomerModel currentUser)
	{
		event.setSite(getBaseSiteService().getCurrentBaseSite());
		event.setCustomer(currentUser);
		event.setLanguage(getCommonI18NService().getCurrentLanguage());
		event.setCurrency(getCommonI18NService().getCurrentCurrency());
		return event;
	}

	@Override
	public void forgottenPassword(final String uid)
	{
		Assert.hasText(uid, "The field [uid] cannot be empty");
		final B2BCustomerModel b2bCustomerModel = this.getUserService().getUserForUID(uid.toLowerCase(), B2BCustomerModel.class);

		if (null != b2bCustomerModel)
		{
			getCustomerAccountService().forgottenPassword(b2bCustomerModel);

		}
		else
		{
			throw new UnknownIdentifierException(uid);
		}
	}

	@Override
	public void createPassword(final String uid) throws OktaUserAlreadyActiveException
	{
		Assert.hasText(uid, "The field [uid] cannot be empty");
		final B2BCustomerModel b2bCustomerModel = this.getUserService().getUserForUID(uid.toLowerCase(), B2BCustomerModel.class);

		if (null != b2bCustomerModel)
		{
			if (b2bCustomerModel.getIsActiveInOkta() != null && b2bCustomerModel.getIsActiveInOkta().booleanValue())
			{

				throw new OktaUserAlreadyActiveException(uid);
			}
			else
			{
				final OktaCreateOrUpdateUserResponseData oktaUserResponseData = oktaAPI.getUser(uid);

				if (null != oktaUserResponseData)
				{
					if (SiteoneCoreConstants.OKTA_USER_STATUS_PROVISIONED.equalsIgnoreCase(oktaUserResponseData.getStatus())
							|| SiteoneCoreConstants.OKTA_USER_STATUS_STAGED.equalsIgnoreCase(oktaUserResponseData.getStatus()))
					{
						((SiteOneCustomerAccountService) getCustomerAccountService()).createPassword(b2bCustomerModel);
					}
					else
					{
						throw new OktaUserAlreadyActiveException(uid);
					}
				}
				else
				{
					((SiteOneCustomerAccountService) getCustomerAccountService()).createPassword(b2bCustomerModel);
				}


			}
		}
		else
		{
			throw new UnknownIdentifierException(uid);
		}
	}

	@Override
	public String setPasswordForUser(final String token, final String password)
			throws TokenInvalidatedException, IllegalArgumentException, PasswordPolicyViolationException
	{

		final long tokenValiditySeconds = Config.getLong(SiteoneintegrationConstants.TOKEN_VALIDITY_CREATE_PASSWORD, 1800);
		final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) ((SiteOneCustomerAccountService) getCustomerAccountService())
				.getCustomerFromToken(token, tokenValiditySeconds);

		try
		{
			final OktaSetPasswordResponseData response = getOktaAPI().setPasswordForUser(b2bCustomerModel, password);


			if (null == response)
			{
				throw new ResourceAccessException(StringUtils.EMPTY);
			}

			if (SiteoneCoreConstants.OKTA_USER_STATUS_ACTIVE.equalsIgnoreCase(response.getStatus()))
			{
				b2bCustomerModel.setIsActiveInOkta(Boolean.valueOf(true));
				modelService.save(b2bCustomerModel);

			}
			return response.getStatus();
		}
		catch (final OktaInvalidPasswordException oktaInvalidPasswordException)
		{
			LOG.error(oktaInvalidPasswordException.getMessage(), oktaInvalidPasswordException);
			throw new PasswordPolicyViolationException(oktaInvalidPasswordException.getPasswordPolicyViolation());
		}
	}


	@Override
	public boolean verifySetPasswordToken(final String token, long tokenValiditySeconds)
			throws TokenInvalidatedException, IllegalArgumentException
	{

		tokenValiditySeconds = Config.getLong(SiteoneintegrationConstants.TOKEN_VALIDITY_CREATE_PASSWORD, 1800);
		final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) ((SiteOneCustomerAccountService) getCustomerAccountService())
				.getCustomerFromToken(token, tokenValiditySeconds);

		return null != b2bCustomerModel;

	}

	@Override
	public void unlockUserRequest(final String email)
	{

		Assert.hasText(email, "The field [uid] cannot be empty");
		final B2BCustomerModel b2bCustomerModel = this.getUserService().getUserForUID(email.toLowerCase(), B2BCustomerModel.class);

		if (null != b2bCustomerModel)
		{
			((SiteOneCustomerAccountService) getCustomerAccountService()).unlockUserRequest(b2bCustomerModel);

		}
		else
		{
			throw new UnknownIdentifierException(email);
		}

	}

	@Override
	public String unlockUser(final String token) throws TokenInvalidatedException, IllegalArgumentException
	{

		final long tokenValiditySeconds = Config.getLong(SiteoneintegrationConstants.TOKEN_VALIDITY_UNLOCK_USER, 1800);
		final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) ((SiteOneCustomerAccountService) getCustomerAccountService())
				.getCustomerFromToken(token, tokenValiditySeconds);

		if (null != b2bCustomerModel)
		{

			final OktaUnlockUserResponseData oktaUnlockUserResponseData = getOktaAPI().unlockUser(b2bCustomerModel.getUid());

			if (null != oktaUnlockUserResponseData && "SUCCESS".equalsIgnoreCase(oktaUnlockUserResponseData.getStatus()))
			{
				return b2bCustomerModel.getUid();
			}
		}

		return null;
	}

	@Override
	public void activateUser(final String token) throws TokenInvalidatedException, IllegalArgumentException
	{
		final long tokenValiditySeconds = Config.getLong(SiteoneintegrationConstants.TOKEN_VALIDITY_CREATE_PASSWORD, 1800);
		final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) ((SiteOneCustomerAccountService) getCustomerAccountService())
				.getCustomerFromToken(token, tokenValiditySeconds);

		getOktaAPI().activateUser(b2bCustomerModel.getUid());

	}

	@Override
	public CustomerData getCustomerForId(final String uid)
	{
		final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) userService.getUserForUID(uid);
		final CustomerData customerData = getSiteonecustomerConverter().convert(b2bCustomerModel);
		return customerData;

	}

	@Override
	public Boolean isHavingInvoicePermissions()
	{
		final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		return b2bCustomerModel.getInvoicePermissions();
	}


	@Override
	public Boolean isHavingPartnerProgramPermission()
	{
		final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		return b2bCustomerModel.getPartnerProgramPermissions();
	}

	@Override
	public Boolean isHavingAccountOverviewForParent()
	{
		final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		return b2bCustomerModel.getAccountOverviewForParent();
	}

	@Override
	public Boolean isHavingAccountOverviewForShipTos()
	{
		final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		return b2bCustomerModel.getAccountOverviewForShipTos();
	}

	@Override
	public Boolean isHavingPayBillOnline()
	{
		final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		if (null != b2bCustomerModel.getPayBillOnline() && b2bCustomerModel.getPayBillOnline())
		{
			 boolean isBTApp = (null != getSessionService().getAttribute(SiteoneCoreConstants.OKTA_ISBT_APP))
					? getSessionService().getAttribute(SiteoneCoreConstants.OKTA_ISBT_APP)
					: false;
        if (null != getSessionService().getAttribute(SiteoneFacadesConstants.ASM_SESSION_PARAMETER)
					&& b2bCustomerService.getCurrentB2BCustomer() != null)
			{
				isBTApp=true;
			}
			return isBTApp;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public Boolean isHavingProjectServices()
	{
		final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		if (b2bCustomerModel.getIsProjectServicesEnabled() != null && b2bCustomerModel.getActive() && b2bCustomerModel.getIsProjectServicesEnabled())
		{
			final boolean isProjectServicesApp = (null != getSessionService().getAttribute(SiteoneCoreConstants.OKTA_ISPS_APP))
					? getSessionService().getAttribute(SiteoneCoreConstants.OKTA_ISPS_APP)
					: false;

			return isProjectServicesApp;
		}
		else
		{
			return false;
		}
	}

	@Override
	public Boolean isHavingNxtLevel()
	{
		final boolean isNxtLevelApp = (null != getSessionService().getAttribute(SiteoneCoreConstants.OKTA_NXTLevel_APP))
				? getSessionService().getAttribute(SiteoneCoreConstants.OKTA_NXTLevel_APP)
				: false;
		return isNxtLevelApp;
	}
	
	@Override
	public Boolean isHavingPlaceOrder()
	{
		final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		return b2bCustomerModel.getPlaceOrder();
	}

	@Override
	public Boolean isEnableAddModifyDeliveryAddress()
	{
		final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		return b2bCustomerModel.getEnableAddModifyDeliveryAddress();
	}

	public SiteOneVerifyGuestContactWebService getSiteOneVerifyGuestContactWebService()
	{
		return siteOneVerifyGuestContactWebService;
	}

	public void setSiteOneVerifyGuestContactWebService(
			final SiteOneVerifyGuestContactWebService siteOneVerifyGuestContactWebService)
	{
		this.siteOneVerifyGuestContactWebService = siteOneVerifyGuestContactWebService;
	}

	/**
	 * @return the siteOneAddressWebService
	 */
	public SiteOneAddressWebService getSiteOneAddressWebService()
	{
		return siteOneAddressWebService;
	}

	/**
	 * @param siteOneAddressWebService
	 *           the siteOneAddressWebService to set
	 */
	public void setSiteOneAddressWebService(final SiteOneAddressWebService siteOneAddressWebService)
	{
		this.siteOneAddressWebService = siteOneAddressWebService;
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
	public String saveLeadGenerationData(final LeadGenarationData leadGenerationData)
			throws ResourceAccessException, IOException, RestClientException
	{
		String response = null;
		response = leadGenerationService.saveLeadGenerationData(leadGenerationData);
		return response;
	}

	@Override
	public boolean isEmailOpted()
	{
		final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		return ((SiteOneCustomerAccountService) getCustomerAccountService()).isEmailOpted(b2bCustomerModel.getEmail());

	}

	public void loginSuccessInternal()
	{
		final CustomerData userData = getCurrentCustomer();

		customerLastLogin(userData.getUid().toLowerCase());
		// First thing to do is to try to change the user on the session cart
		if (getCartService().hasSessionCart())
		{
			getCartService().changeCurrentCartUser(getCurrentUser());
		}

		// Update the session currency (which might change the cart currency)
		if (!updateSessionCurrencyValue(userData.getCurrency(), getStoreSessionFacade().getDefaultCurrency()))
		{
			// Update the user
			getUserFacade().syncSessionCurrency();
		}

		// Update the user
		getUserFacade().syncSessionLanguage();

		// Calculate the cart after setting everything up
		if (getCartService().hasSessionCart())
		{
			final CartModel sessionCart = getCartService().getSessionCart();
			//Update sessionShipTo
			((SiteOneCartFacade) cartFacade).updateShipToAndPOS(sessionCart);

			// Clean the existing info on the cart if it does not beling to the current user
			getCartCleanStrategy().cleanCart(sessionCart);
		}
	}

	protected boolean updateSessionCurrencyValue(final CurrencyData preferredCurrency, final CurrencyData defaultCurrency)
	{
		if (preferredCurrency != null)
		{
			final String currencyIsoCode = preferredCurrency.getIsocode();

			// Get the available currencies and check if the currency iso code is supported
			final Collection<CurrencyData> currencies = getStoreSessionFacade().getAllCurrencies();
			for (final CurrencyData currency : currencies)
			{
				if (StringUtils.equals(currency.getIsocode(), currencyIsoCode))
				{
					// Set the current currency
					setCurrentCurrency(currencyIsoCode);
					return true;
				}
			}
		}

		// Fallback to the default
		setCurrentCurrency(defaultCurrency.getIsocode());
		return false;
	}
	
	protected void setCurrentCurrency(String isocode) {
		Collection<CurrencyModel> currencies = getCommerceCommonI18NService().getAllCurrencies();
		if (currencies.isEmpty())
		{
			LOG.debug("No supported currencies found for the current site, look for all session currencies instead.");
			currencies = getCommonI18NService().getAllCurrencies();
		}
		Assert.notEmpty(currencies,
				"No supported currencies found for the current site. Please create currency for proper base store.");
		CurrencyModel currencyModel = null;
		for (final CurrencyModel currency : currencies)
		{
			if (StringUtils.equals(currency.getIsocode(), isocode))
			{
				currencyModel = currency;
			}
		}
		validateParameterNotNull(currencyModel, "Currency to set is not supported.");

		if (getCommonI18NService().getCurrentCurrency() != null && currencyModel != null)
		{
			if (!getCommonI18NService().getCurrentCurrency().getIsocode().equals(currencyModel.getIsocode()))
			{
				getCommonI18NService().setCurrentCurrency(currencyModel);
			}
		}
		else
		{
			getCommonI18NService().setCurrentCurrency(currencyModel);
		}

	}
	
	@Override
	public void customerLastLogin(final String userId)
	{

		if (null != userId)
		{
			final B2BCustomerModel customer = (B2BCustomerModel) getUserService().getUserForUID(userId);
			final Date date = new Date();
			customer.setLastLogin(date);
			modelService.save(customer);
		}
	}


	@Override
	public String verifyRecoveryToken(final String token)
			throws InvalidTokenException, ResourceAccessException, TokenInvalidatedException, IllegalArgumentException
	{

		String stateToken = StringUtils.EMPTY;
		try
		{

			final long tokenValiditySeconds = Config.getLong(SiteoneintegrationConstants.TOKEN_VALIDITY_FORGOT_PASSWORD, 1800);
			final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) ((SiteOneCustomerAccountService) getCustomerAccountService())
					.getCustomerFromToken(token, tokenValiditySeconds);

			if (null == b2bCustomerModel)
			{
				throw new TokenInvalidatedException("Invalid token");
			}
			final OktaVerifyRecoveryTokenResponseData response = getOktaAPI()
					.recoverytokenVerification(b2bCustomerModel.getRecoveryToken());

			if (null == response)
			{
				throw new ResourceAccessException(StringUtils.EMPTY);
			}
			if (StringUtils.isNotBlank(response.getStateToken()))
			{
				stateToken = response.getStateToken();
			}
		}
		catch (final OktaInvalidTokenException oktaInvalidTokenException)
		{
			LOG.error(oktaInvalidTokenException);
			throw new InvalidTokenException(oktaInvalidTokenException.getMessage());
		}
		return stateToken;
	}

	@Override
	public String resetPassword(final String token, final String newPassword) throws InvalidTokenException,
			PasswordPolicyViolationException, ResourceAccessException, RecentlyUsedPasswordException, TokenInvalidatedException
	{
		try
		{

			final long tokenValiditySeconds = Config.getLong(SiteoneintegrationConstants.TOKEN_VALIDITY_FORGOT_PASSWORD, 1800);
			final B2BCustomerModel customerModel = (B2BCustomerModel) ((SiteOneCustomerAccountService) getCustomerAccountService())
					.getCustomerFromToken(token, tokenValiditySeconds);

			final OktaVerifyRecoveryTokenResponseData tokenResponse = getOktaAPI()
					.recoverytokenVerification(customerModel.getRecoveryToken());
			final String stateToken = tokenResponse.getStateToken();
			final OktaResetPasswordResponseData response = getOktaAPI().resetPasswordForUser(stateToken, newPassword);
			if (null == response)
			{
				throw new ResourceAccessException(StringUtils.EMPTY);
			}
			return response.getStatus();
		}
		catch (final OktaInvalidTokenException oktaInvalidTokenException)
		{
			LOG.error(oktaInvalidTokenException);
			throw new InvalidTokenException(oktaInvalidTokenException.getMessage());
		}
		catch (final OktaInvalidPasswordException oktaInvalidPasswordException)
		{
			LOG.error(oktaInvalidPasswordException);
			throw new PasswordPolicyViolationException(oktaInvalidPasswordException.getPasswordPolicyViolation());
		}
		catch (final OktaRecentlyUsedPasswordException oktaRecentlyUsedPasswordException)
		{
			LOG.error(oktaRecentlyUsedPasswordException);
			throw new RecentlyUsedPasswordException(oktaRecentlyUsedPasswordException.getMessage());
		}
		catch (final TokenInvalidatedException tokenInvalidatedException)
		{
			LOG.error(tokenInvalidatedException);
			throw new TokenInvalidatedException(tokenInvalidatedException.getMessage());
		}


	}

	@Override
	public void addPhoneNumber(final String phoneNumber, final String uid)
			throws ServiceUnavailableException, ContactNotCreatedOrUpdatedInUEException, ContactNotCreatedOrUpdatedInUEException
	{
		Assert.hasText(uid, "The field [uid] cannot be empty");
		final B2BCustomerModel b2bCustomerModel = this.getUserService().getUserForUID(uid.toLowerCase(), B2BCustomerModel.class);

		if (null != b2bCustomerModel)
		{
			//Adding phoneNumber to model
			final PhoneContactInfoModel phoneModel = getModelService().create(PhoneContactInfoModel.class);
			phoneModel.setUser(b2bCustomerModel);
			phoneModel.setCode(b2bCustomerModel.getUid());
			phoneModel.setPhoneNumber(phoneNumber);
			phoneModel.setType(PhoneContactInfoType.MOBILE);
			phoneModel.setPhoneId(UUID.randomUUID().toString().toUpperCase());
			b2bCustomerModel.setContactInfos(Collections.singletonList(phoneModel));

			SiteOneWsB2BCustomerResponseData customerResponseData = null;
			try
			{
				customerResponseData = getSiteOneContactWebService().updateB2BCustomer(b2bCustomerModel,
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

			getModelService().save(b2bCustomerModel);

		}
		else
		{
			throw new UnknownIdentifierException(uid);
		}
	}



	/**
	 * @return the eventService
	 */
	@Override
	public EventService getEventService()
	{
		return eventService;
	}

	/**
	 * @param eventService
	 *           the eventService to set
	 */
	@Override
	public void setEventService(final EventService eventService)
	{
		this.eventService = eventService;
	}

	/**
	 * @return the baseSiteService
	 */
	@Override
	public BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	/**
	 * @param baseSiteService
	 *           the baseSiteService to set
	 */
	@Override
	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}

	public SiteOneAddressVerificationWebService getSiteOneAddressVerificationWebService()
	{
		return siteOneAddressVerificationWebService;
	}

	/**
	 * @param siteOneAddressVerificationWebService
	 *           the siteOneAddressVerificationWebService to set
	 */
	public void setSiteOneAddressVerificationWebService(
			final SiteOneAddressVerificationWebService siteOneAddressVerificationWebService)
	{
		this.siteOneAddressVerificationWebService = siteOneAddressVerificationWebService;
	}

	/**
	 * @return the siteOneAddressVerificationResponseConverter
	 */
	public Converter<SiteOneWsAddressVerificationResponseData, SiteOneAddressVerificationData> getSiteOneAddressVerificationResponseConverter()
	{
		return siteOneAddressVerificationResponseConverter;
	}

	/**
	 * @param siteOneAddressVerificationResponseConverter
	 *           the siteOneAddressVerificationResponseConverter to set
	 */
	public void setSiteOneAddressVerificationResponseConverter(
			final Converter<SiteOneWsAddressVerificationResponseData, SiteOneAddressVerificationData> siteOneAddressVerificationResponseConverter)
	{
		this.siteOneAddressVerificationResponseConverter = siteOneAddressVerificationResponseConverter;
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
	public void updateLanguagePreference(final String langPreference)
	{
		final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		if (langPreference.toLowerCase().startsWith("en"))
		{
			customer.setLanguagePreference(getCommonI18NService().getLanguage("en"));
		}
		else
		{
			customer.setLanguagePreference(getCommonI18NService().getLanguage("es"));
		}

		SiteOneWsB2BCustomerResponseData customerResponseData = null;
		try
		{
			customerResponseData = getSiteOneContactWebService().updateB2BCustomer(customer,
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

		getModelService().save(customer);

	}

	//return the newContextPath based on languagePreference
	public String getContextPathBasedOnLanguagePreference(final String username)
	{
		final CustomerData customerData = getCustomerForId(StringUtils.lowerCase(username));
		//check user's language preference
		if (customerData.getLangPreference() != null && customerData.getLangPreference().getIsocode().equals("es"))
		{
			return "/es";
		}
		else
		{
			return "/en";
		}
	}

	@Override
	public SearchPageData<B2BUnitData> getPagedB2BDefaultUnits(final PageableData pageableData, final String searchParam)
	{
		final SearchPageData<B2BUnitModel> b2bUnitsPageData = ((SiteOneCustomerAccountService) getCustomerAccountService())
				.getPagedB2BDefaultUnits(pageableData, searchParam);
		return CommerceUtils.convertPageData(b2bUnitsPageData, this.getB2bUnitConverter());
	}


	private String getClientIpAddr(final HttpServletRequest request)
	{
		String ip = request.getHeader("X-Forwarded-For");
		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip))
		{
			ip = ip.split(",")[0];
		}
		else
		{
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	private String getClientUserAgent(final HttpServletRequest request)
	{

		return request.getHeader("User-Agent");
	}

	@Override
	public void getClientInformation(final HttpServletRequest request)
	{
		final String clientIp = getClientIpAddr(request);
		final String clientUserAgent = getClientUserAgent(request);

		sessionService.setAttribute("clientIp", clientIp);
		sessionService.setAttribute("userAgent", clientUserAgent);
	}

	@Override
	public String getCustomerTypeByOrderCreation()
	{
		String customerType = null;
		final UserModel userModel = userService.getCurrentUser();

		if (userModel != null)
		{
			final List<OrderModel> orderModelCollection = siteOneOrderService.getLastTwoHybrisOrdersForCustomer(userModel);
			LOG.debug("Total Number of Orders == " + orderModelCollection.size());

			if (orderModelCollection.isEmpty())
			{
				return SiteoneFacadesConstants.CUSTOMER_TYPE_NEW_READY_TO_ORDER;
			}
			else if (orderModelCollection.size() == 1)
			{
				final long diffInMillies = Math
						.abs(orderModelCollection.get(0).getCreationtime().getTime() - System.currentTimeMillis());
				final long daysDifference = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
				if (daysDifference <= 365)
				{
					return SiteoneFacadesConstants.CUSTOMER_TYPE_NEW_FIRST_TIME_PURCHASE;
				}

			}
			else
			{
				final long diffInMillies = Math.abs(orderModelCollection.get(0).getCreationtime().getTime()
						- orderModelCollection.get(1).getCreationtime().getTime());
				final long daysDifference = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
				if (daysDifference >= 365)
				{
					customerType = SiteoneFacadesConstants.CUSTOMER_TYPE_LAPSED_REACTIVATED;
				}
				else if (daysDifference >= 0 & daysDifference <= 90)
				{
					customerType = SiteoneFacadesConstants.CUSTOMER_TYPE_REPEAT_LESS_THAN;
				}
				else
				{
					customerType = SiteoneFacadesConstants.CUSTOMER_TYPE_REPEAT_GREATER_THAN;
				}
			}
		}
		return customerType;
	}

	@Override
	public CustomerModel saveGuestUserDetails(final CustomerData customerData, final Boolean isSameaddressforParcelShip)
	{
		final CustomerModel customerModel = getModelService().create(CustomerModel.class);

		populateGuestUserData(customerModel, customerData);
		getModelService().save(customerModel);
		getModelService().refresh(customerModel);

		final AddressModel newAddressModel = getModelService().create(AddressModel.class);
		((SiteOneAddressReversePopulator) getAddressReversePopulator()).populate(customerData.getDefaultAddress(), newAddressModel);
		newAddressModel.setEmail(customerData.getEmail());
		newAddressModel.setOwner(customerModel);
		getModelService().save(newAddressModel);
		getModelService().refresh(newAddressModel);

		final List<AddressModel> addressList = new ArrayList<AddressModel>();
		addressList.add(newAddressModel);
		customerModel.setAddresses(addressList);
		getModelService().save(customerModel);
		getModelService().refresh(customerModel);
		updateCartWithGuestForAnonymousCheckout(customerModel, null, isSameaddressforParcelShip);
		return customerModel;
	}

	/**
	 * @param isSameaddressforParcelShip2
	 * @param isShipping
	 * @param isDelivery
	 * @param addresses
	 * @return
	 */
	@Override
	public CustomerModel saveGuestUserDetails(final CustomerData customerData, final CartData cartData,
			final Boolean isSameaddressforParcelShip)

	{
		final CustomerModel customerModel = getModelService().create(CustomerModel.class);

		populateGuestUserData(customerModel, customerData);
		getModelService().save(customerModel);
		getModelService().refresh(customerModel);

		final AddressModel newAddressModel = getModelService().create(AddressModel.class);
		((SiteOneAddressReversePopulator) getAddressReversePopulator()).populate(customerData.getDefaultAddress(), newAddressModel);
		newAddressModel.setEmail(customerData.getEmail());
		newAddressModel.setOwner(customerModel);
		getModelService().save(newAddressModel);
		getModelService().refresh(newAddressModel);

		final List<AddressModel> addressList = new ArrayList<AddressModel>();
		addressList.add(newAddressModel);
		customerModel.setAddresses(addressList);
		getModelService().save(customerModel);
		getModelService().refresh(customerModel);
		updateCartWithGuestForAnonymousCheckout(customerModel, cartData, isSameaddressforParcelShip);
		return customerModel;
	}

	/**
	 * @param customerModel
	 * @param customerData
	 */
	private void populateGuestUserData(final CustomerModel customerModel, final CustomerData customerData)
	{
		final String guid = UUID.randomUUID().toString();
		customerModel.setName(customerData.getFirstName() + " " + customerData.getLastName());
		customerModel.setUid(guid + "|" + customerData.getEmail());
		customerModel.setCustomerID(guid);
		customerModel.setType(CustomerType.valueOf(CustomerType.GUEST.getCode()));

	}

	/**
	 * @param customerModel
	 * @param customerData
	 */
	private void populateexistingGuestUserData(final CustomerModel currentCustomer, final CustomerData customerData)
	{
		final String guid = UUID.randomUUID().toString();
		currentCustomer.setUid(guid + "|" + customerData.getEmail());
		currentCustomer.setCustomerID(guid);
		currentCustomer.setName(customerData.getFirstName() + " " + customerData.getLastName());

	}

	@Override
	public CustomerModel editGuestUserDetails(final CustomerData customerData, final CustomerModel currentCustomer,
			final Boolean isSameaddressforParcelShip)
	{

		populateexistingGuestUserData(currentCustomer, customerData);
		getModelService().save(currentCustomer);
		getModelService().refresh(currentCustomer);

		final AddressModel newAddressModel = getModelService().create(AddressModel.class);
		((SiteOneAddressReversePopulator) getAddressReversePopulator()).populate(customerData.getDefaultAddress(), newAddressModel);
		newAddressModel.setEmail(customerData.getEmail());
		newAddressModel.setOwner(currentCustomer);
		getModelService().save(newAddressModel);
		getModelService().refresh(newAddressModel);

		final List<AddressModel> newAddressList = new ArrayList<AddressModel>();
		newAddressList.add(newAddressModel);
		currentCustomer.setAddresses(newAddressList);
		getModelService().save(currentCustomer);
		getModelService().refresh(currentCustomer);
		updateCartWithGuestForAnonymousCheckout(currentCustomer, null, isSameaddressforParcelShip);
		return currentCustomer;
	}

	@Override
	public CustomerModel getCustomerforUID(final String uid)
	{
		final CustomerModel customerModel = (CustomerModel) getUserService().getUserForUID(uid);
		return customerModel;
	}

	@Override
	public void updateCartWithGuestForAnonymousCheckout(final CustomerModel customerModel, final CartData cartData,
			final Boolean isSameaddressforParcelShip)
	{
		Boolean isPickup = false;
		Boolean isDelivery = false;
		Boolean isShipping = false;
		if (cartData != null)
		{
			final Map<String, Boolean> fulfilmentStatus = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade)
					.populateFulfilmentStatus(cartData);
			isPickup = fulfilmentStatus.get(SiteoneCoreConstants.DELIVERYMODE_PICKUP);
			isDelivery = fulfilmentStatus.get(SiteoneCoreConstants.DELIVERYMODE_DELIVERY);
			isShipping = fulfilmentStatus.get(SiteoneCoreConstants.DELIVERYMODE_SHIPPING);
		}
		final CustomerData guestCustomerData = getCustomerConverter().convert(customerModel);
		// First thing to do is to try to change the user on the session cart
		if (getCartService().hasSessionCart())
		{
			getCartService().changeCurrentCartUser(getUserService().getUserForUID(guestCustomerData.getUid()));
		}

		// Update the session currency (which might change the cart currency)
		if (!updateSessionCurrency(guestCustomerData.getCurrency(), getStoreSessionFacade().getDefaultCurrency()))
		{
			// Update the user
			getUserFacade().syncSessionCurrency();
		}

		if (!updateSessionLanguage(guestCustomerData.getLanguage(), getStoreSessionFacade().getDefaultLanguage()))
		{
			// Update the user
			getUserFacade().syncSessionLanguage();
		}

		// Calculate the cart after setting everything up
		if (getCartService().hasSessionCart())
		{
			final CartModel sessionCart = getCartService().getSessionCart();

			sessionCart.setGuestContactPerson(customerModel);
			final List<AddressModel> addressList = (List<AddressModel>) customerModel.getAddresses();
			// Clear the delivery address, delivery mode, payment info before starting the guest checkout.
			if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",
					storeSessionFacade.getSessionStore().getStoreId()))
			{
				if (isPickup.booleanValue())
				{
					if (null != sessionCart.getPointOfService())
					{
						sessionCart.setPickupAddress(sessionCart.getPointOfService().getAddress());
					}
					else
					{
						sessionCart.setPickupAddress(addressList.get(0));
					}

				}
				if (isDelivery.booleanValue())
				{

					sessionCart.setDeliveryAddress(addressList.get(0));
					getSessionService().setAttribute("isSameaddressforParcelShip", isSameaddressforParcelShip);
					sessionCart.setIsSameaddressforParcelShip(isSameaddressforParcelShip);
				}
				if (isShipping.booleanValue())
				{
					sessionCart.setShippingAddress(addressList.get(0));
					getSessionService().setAttribute("isSameaddressforParcelShip", isSameaddressforParcelShip);
					sessionCart.setIsSameaddressforParcelShip(isSameaddressforParcelShip);
				}
			}
			else
			{
				if (sessionCart.getOrderType().getCode() == "PICKUP")
				{
					if (sessionCart.getPointOfService() != null)
					{
						sessionCart.setDeliveryAddress(sessionCart.getPointOfService().getAddress());
					}
					else
					{
						sessionCart.setDeliveryAddress(addressList.get(0));
					}
				}
				else
				{
					sessionCart.setDeliveryAddress(addressList.get(0));
					getSessionService().setAttribute("isSameaddressforParcelShip", isSameaddressforParcelShip);
					sessionCart.setIsSameaddressforParcelShip(isSameaddressforParcelShip);
				}
			}
			sessionCart.setDeliveryMode(null);
			sessionCart.setPaymentInfo(null);
			getCartService().saveOrder(sessionCart);


			try
			{
				final CommerceCartParameter parameter = new CommerceCartParameter();
				parameter.setEnableHooks(true);
				parameter.setCart(sessionCart);
				getCommerceCartService().recalculateCart(parameter);
			}
			catch (final CalculationException ex)
			{
				LOG.error("Failed to recalculate order [" + sessionCart.getCode() + "]", ex);
			}
		}
	}

	@Override
	public boolean isUserAlreadyExists(final String email)
	{
		//checking in hybris if user is available
		boolean isMailAvailable = isUserAvailable(email.toLowerCase());
		if (!isMailAvailable)
		{
			//Check if user exists in UE and return value
			final boolean allowForCheckout = isValidGuestEmail(email);
			if (!allowForCheckout)
			{
				isMailAvailable = true;
			}
		}
		return isMailAvailable;
	}

	@Override
	public SiteOneGuestUserData populateGuestUserData(final CartData cartData, final CustomerData customerdata,
			final Boolean isSameaddressforParcelShip)
	{
		final SiteOneGuestUserData siteOneGuestUserData = new SiteOneGuestUserData();
		CustomerModel customerModel = null;


		if (null != cartData.getB2bCustomerData() && null != cartData.getB2bCustomerData().getDisplayUid())

		{
			final CustomerModel currentCustomer = getCustomerforUID(cartData.getB2bCustomerData().getUid());

			if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",
					storeSessionFacade.getSessionStore().getStoreId()))
			{
				customerModel = editGuestUserDetails(customerdata, currentCustomer, cartData, isSameaddressforParcelShip);
			}
			else
			{
				customerModel = editGuestUserDetails(customerdata, currentCustomer, isSameaddressforParcelShip);

			}
		}
		else
		{
			if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",
					storeSessionFacade.getSessionStore().getStoreId()))
			{
				customerModel = saveGuestUserDetails(customerdata, cartData, isSameaddressforParcelShip);
			}
			else
			{
				customerModel = saveGuestUserDetails(customerdata, isSameaddressforParcelShip);
			}
		}

		if (null != customerModel)
		{
			final List<AddressModel> addressList = (List<AddressModel>) customerModel.getAddresses();
			final String addressId = addressList.get(0).getPk().toString();

			siteOneGuestUserData.setCustomerData(customerdata);

			final AddressData addressData = customerdata.getDefaultAddress();
			addressData.setId(addressId);

			siteOneGuestUserData.setAddressData(addressData);
			getSessionService().setAttribute("guestAddressData", customerdata.getDefaultAddress());
		}
		return siteOneGuestUserData;
	}

	@Override
	public void saveAlternateContactDetails(final CustomerData customerData, final String mode)
	{
		final CustomerModel customerModel = getModelService().create(CustomerModel.class);

		populateGuestUserData(customerModel, customerData);
		getModelService().save(customerModel);
		getModelService().refresh(customerModel);

		final AddressModel newAddressModel = getModelService().create(AddressModel.class);
		((SiteOneAddressReversePopulator) getAddressReversePopulator()).populate(customerData.getDefaultAddress(), newAddressModel);
		newAddressModel.setEmail(customerData.getEmail());
		newAddressModel.setOwner(customerModel);
		getModelService().save(newAddressModel);
		getModelService().refresh(newAddressModel);

		if (customerModel != null)
		{
			final List<AddressModel> addressList = new ArrayList<AddressModel>();
			addressList.add(newAddressModel);
			customerModel.setAddresses(addressList);

			final String addressId = addressList.get(0).getPk().toString();
			final AddressData addressData = customerData.getDefaultAddress();
			addressData.setId(addressId);

		}

		getModelService().save(customerModel);
		getModelService().refresh(customerModel);
		getSessionService().setAttribute("guestAddressData", customerData.getDefaultAddress());
		updateCartWithGuestForAnonymousCheckout(customerModel, mode);

	}

	/**
	 * @param customerModel
	 */
	private void updateCartWithGuestForAnonymousCheckout(final CustomerModel customerModel, final String mode)
	{
		// Calculate the cart after setting everything up
		if (getCartService().hasSessionCart())
		{
			final CartModel sessionCart = getCartService().getSessionCart();
			final List<AddressModel> addressList = (List<AddressModel>) customerModel.getAddresses();
			if (mode != null)
			{
				if (mode.equalsIgnoreCase("standard-net"))
				{
					sessionCart.setGuestDeliveryContactPerson(customerModel);
					sessionCart.setDeliveryAddress(addressList.get(0));
				}
				else
				{

					sessionCart.setGuestShippingContactPerson(customerModel);
					sessionCart.setShippingAddress(addressList.get(0));
				}
			}
			else
			{
				// Clear delivery mode, payment info before starting the guest checkout.
				sessionCart.setGuestContactPerson(customerModel);
				sessionCart.setDeliveryAddress(addressList.get(0));
			}
			getCartService().saveOrder(sessionCart);

			try
			{
				final CommerceCartParameter parameter = new CommerceCartParameter();
				parameter.setEnableHooks(true);
				parameter.setCart(sessionCart);
				getCommerceCartService().recalculateCart(parameter);
			}
			catch (final CalculationException ex)
			{
				LOG.error("Failed to recalculate order [" + sessionCart.getCode() + "]", ex);
			}
		}
	}

	@Override
	public boolean isUserAccountLocked(final String userId)
	{
		if (isUserAvailable(userId.toLowerCase()))
		{
			final OktaCreateOrUpdateUserResponseData oktaUserResponse = oktaAPI.getUser(userId);
			if (oktaUserResponse != null && StringUtils.isNotEmpty(oktaUserResponse.getStatus())
					&& SiteoneCoreConstants.OKTA_USER_STATUS_LOCKED_OUT.equalsIgnoreCase(oktaUserResponse.getStatus()))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public CustomerModel editGuestUserDetails(final CustomerData customerData, final CustomerModel currentCustomer,
			final CartData cartdata, final Boolean isSameaddressforParcelShip)
	{

		populateexistingGuestUserData(currentCustomer, customerData);
		getModelService().save(currentCustomer);
		getModelService().refresh(currentCustomer);

		final AddressModel newAddressModel = getModelService().create(AddressModel.class);
		((SiteOneAddressReversePopulator) getAddressReversePopulator()).populate(customerData.getDefaultAddress(), newAddressModel);
		newAddressModel.setEmail(customerData.getEmail());
		newAddressModel.setOwner(currentCustomer);
		getModelService().save(newAddressModel);
		getModelService().refresh(newAddressModel);

		final List<AddressModel> newAddressList = new ArrayList<AddressModel>();
		newAddressList.add(newAddressModel);
		currentCustomer.setAddresses(newAddressList);
		getModelService().save(currentCustomer);
		getModelService().refresh(currentCustomer);
		updateCartWithGuestForAnonymousCheckout(currentCustomer, cartdata, isSameaddressforParcelShip);
		return currentCustomer;
	}

	@Override
	public CustomerSpecificWsDTO getCustomerSpecificInfo(final String unitId, final B2BCustomerModel customer)
	{
		final PointOfServiceData pointOfServiceData = getPreferredStore();

		((SiteOneCartFacade) cartFacade).restoreSessionCart(null);

		final int cartItemsCount = ((SiteOneCartFacade) cartFacade).getTotalItems();

		final boolean isBuyAgainExists = ((SiteOneCustomerAccountService) getCustomerAccountService())
				.isBuyAgainProductExists(unitId);

		final CustomerSpecificWsDTO customerSpecificWsDTO = new CustomerSpecificWsDTO();
		customerSpecificWsDTO.setBuyAgainExists(isBuyAgainExists);
		customerSpecificWsDTO.setCartItemsCount(cartItemsCount);
		if (null != pointOfServiceData)
		{
			final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
			final MapperFacade mapper = mapperFactory.getMapperFacade();
			mapperFactory.classMap(PointOfServiceData.class, PointOfServiceWsDTO.class);
			final PointOfServiceWsDTO pointOfServiceWsDTO = mapper.map(pointOfServiceData, PointOfServiceWsDTO.class);	
			if(!getUserFacade().isAnonymousUser())
			{
				pointOfServiceWsDTO.setIsSplitMixedCartEnabledBranch(
						siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("SplitMixedPickupBranches",pointOfServiceWsDTO.getStoreId()));
			}
			customerSpecificWsDTO.setPreferredStore(pointOfServiceWsDTO);
		}

		final OrderModel orderModel = ((SiteOneCustomerAccountService) getCustomerAccountService())
				.getRecentOrderForAccount(unitId);
		if (null != orderModel)
		{
			customerSpecificWsDTO.setRecentOrderExists(true);
		}

		return customerSpecificWsDTO;
	}

	@Override
	public String getKountValue(final CartData cartData, final HttpServletRequest request)
	{
		String kountAuto = null;
		try
		{
			getClientInformation(request);
			kountAuto = defaultSiteOneKountService.evaluateInquiry(cartData);
		}
		catch (final Exception exception)
		{
			LOG.error("Exception occured in KountValidationCheckoutStepController - evaluateInquiry(): " + exception.getMessage());
		}

		return kountAuto;
	}

	@Override
	public void updateKountDetails(final String orderCode, final String transactionId, final String kountSessionId)
	{

		try
		{
			defaultSiteOneKountService.updateUserKountDetails(orderCode, transactionId, kountSessionId);
		}
		catch (final Exception genericException)
		{
			LOG.error("Exception occured in processOrderCode - updateKountDetails " + genericException.getMessage());
		}
	}


	@Override
	public boolean enrollCustomerInTalonOne()
	{
		final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		if (null != customer)
		{
			final TalonOneEnrollmentModel talonOneEnrollmentModel = new TalonOneEnrollmentModel();
			if (!StringUtils.isBlank(customer.getGuid()))
			{
				talonOneEnrollmentModel.setContactId(customer.getGuid());
			}
			if (!StringUtils.isBlank(customer.getContactEmail()))
			{
				talonOneEnrollmentModel.setEmail(customer.getContactEmail());
			}
			if (null != customer.getDefaultB2BUnit())
			{
				if (!StringUtils.isBlank(customer.getDefaultB2BUnit().getGuid()))
				{
					talonOneEnrollmentModel.setCustTreeNodeId(customer.getDefaultB2BUnit().getGuid());
				}
				if (!StringUtils.isBlank(customer.getDefaultB2BUnit().getUid()))
				{
					final String[] division = (customer.getDefaultB2BUnit().getUid()).split("_");
					if (division[1].equalsIgnoreCase("US"))
					{
						talonOneEnrollmentModel.setDivisionId("1");
					}
					else if (division[1].equalsIgnoreCase("CA"))
					{
						talonOneEnrollmentModel.setDivisionId("2");
					}

				}
			}
			final SiteOneWsTalonOneLoyaltyEnrollmentResponseData response = getSiteOneTalonOneLoyaltyEnrollmentWebService()
					.enrollCustomerInTalonOne(talonOneEnrollmentModel,
							Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
			if (null != response && null != response.getResult() && null != response.getResult().getLoyaltyProgramInfo()
					&& null != response.getResult().getLoyaltyProgramInfo().getLoyaltyProgramStatus()
					&& response.getResult().getLoyaltyProgramInfo().getLoyaltyProgramStatus().equalsIgnoreCase("INC"))
			{
				customer.setPartnerProgramPermissions(Boolean.TRUE);
				modelService.save(customer);
				return true;
			}
		}

		return false;
	}

	@Override
	public PointOfServiceWsDTO getCustomerStoreData(final PointOfServiceWsDTO pointOfServiceWsDTO, final String storeId)
	{
		pointOfServiceWsDTO.setIsMixedCartEnabled(
				siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches", storeId));
		if(!getUserFacade().isAnonymousUser())
		{
			pointOfServiceWsDTO.setIsSplitMixedCartEnabledBranch(
					siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("SplitMixedPickupBranches",pointOfServiceWsDTO.getStoreId()));
		}
			

		return pointOfServiceWsDTO;
	}

	public SiteOneTalonOneLoyaltyEnrollmentWebService getSiteOneTalonOneLoyaltyEnrollmentWebService()
	{
		return siteOneTalonOneLoyaltyEnrollmentWebService;
	}

	public void setSiteOneTalonOneLoyaltyEnrollmentWebService(
			final SiteOneTalonOneLoyaltyEnrollmentWebService siteOneTalonOneLoyaltyEnrollmentWebService)
	{
		this.siteOneTalonOneLoyaltyEnrollmentWebService = siteOneTalonOneLoyaltyEnrollmentWebService;
	}

	@Override
	public String getPartnerProgramRedirectlUrl()
	{
		final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		if (customer != null && customer.getContactEmail() != null)
		{
			final String accessToken = partnerProgramSSOWebService.openSession(customer.getContactEmail());
			if (null != accessToken)
			{
				return Config.getString(SiteoneCoreConstants.PARTNERPROGRAM_PILOT_REDIRECT_URL, StringUtils.EMPTY)
						+ "?accessToken=" + accessToken + "&username=" + customer.getContactEmail();
			}
		}
		return StringUtils.EMPTY;
	}

	@Override
	public String encrypt(final String stringToEncrypt, final String secret)
	{
		final byte[] decodedKey = Base64.getDecoder().decode(secret);

		try
		{
			final Cipher cipher = Cipher.getInstance("AES");
			// rebuild key using SecretKeySpec
			final SecretKey originalKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
			cipher.init(Cipher.ENCRYPT_MODE, originalKey);
			final byte[] cipherText = cipher.doFinal(stringToEncrypt.getBytes("UTF-8"));
			return Base64.getEncoder().encodeToString(cipherText);
		}
		catch (final Exception e)
		{
			LOG.error("Error while encrypting: " + e.toString());
		}
		return null;

	}

	@Override
	public String getCurrentUserData()
	{

		final B2BCustomerModel currentUser = (B2BCustomerModel) getUserService().getCurrentUser();
		return currentUser.getHomeBranch();
	}

	@Override
	public boolean getAdminUserStatus()
	{
		final B2BCustomerModel customerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		return customerModel.getGroups().stream().anyMatch(group -> group.getUid().equalsIgnoreCase("b2badmingroup"));
	}

	@Override
	public Map<String, String> getListOfShiptTos(final String shipToUnit)
	{

		final Map<String, String> listOfShipTos = new LinkedHashMap<>();

		if (shipToUnit != null && !shipToUnit.isEmpty() && !shipToUnit.equalsIgnoreCase("All"))
		{
			final B2BUnitModel b2BUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(shipToUnit);
			if(b2BUnitModel.getUid().contains("_US"))
			{
			listOfShipTos.put(b2BUnitModel.getUid(), b2BUnitModel.getUid().split("_US")[0] + ' ' + b2BUnitModel.getName());
			}
			else {
				listOfShipTos.put(b2BUnitModel.getUid(), b2BUnitModel.getUid().split("_CA")[0] + ' ' + b2BUnitModel.getName());
			}
		}
		listOfShipTos.putAll(((SiteOneB2BUserFacade) b2bUserFacade).getListOfShipTos());
		return listOfShipTos;
	}


	@Override
	public boolean isTokenExpired(final String payload, final String header, final String signature)
			throws JSONException, NoSuchAlgorithmException
	{
		final JSONObject entry = new JSONObject(new String(this.decode(payload)));
		return (entry.getLong("exp") > (LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)))
				&& signature.equals(calculatedSignature(header + "." + payload, Config.getString(SECRET_KEY, null)));

	}

	@Override
	public String decode(final String encodedString)
	{
		return new String(Base64.getUrlDecoder().decode(encodedString));
	}

	@Override
	public SiteOneWsLinkToPayOrderResponseData getOrderDetails(final String orderNumber)
	{
		SiteOneWsLinkToPayOrderResponseData orderDetailResponseData = null;
		final String correlationID = UUID.randomUUID().toString();
		if (null != orderNumber)
		{
			orderDetailResponseData = siteOneLinkToPayWebService.getOrderDetails(orderNumber,
					Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)), correlationID);
		}
		return orderDetailResponseData;
	}

	@Override
	public String linkToPayEvaluateInquiry(final SiteOneWsLinkToPayOrderResponseData linkToPayOrderResponseData,
			final String kountSessionID)
	{
		return defaultSiteOneKountService.linkToPayEvaluateInquiry(linkToPayOrderResponseData, kountSessionID);
	}

	private String encode(final byte[] bytes)
	{
		return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
	}

	private String calculatedSignature(final String data, final String key)
	{
		try
		{
			final byte[] hash = key.getBytes(StandardCharsets.UTF_8);
			final Mac sha256Hmac = Mac.getInstance("HmacSHA256");
			final SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");
			sha256Hmac.init(secretKey);
			final byte[] signedBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
			return encode(signedBytes);
		}
		catch (NoSuchAlgorithmException | InvalidKeyException ex)
		{
			LOG.error(ex.getMessage());
			return null;
		}
	}

	@Override
	public void updateLinkToPayKountDetails(final String status, final String avsStatus, final String cvv,
			final String orderNumber)
	{

		try
		{
			defaultSiteOneKountService.updateKountDetails(status, avsStatus, cvv, orderNumber);
		}
		catch (final Exception genericException)
		{
			LOG.error("Exception occured in processOrderCode - updateKountDetails " + genericException.getMessage());
		}
	}

	@Override
	public void linkToPayPaymentSubmitToUE(final LinkToPayCayanResponseModel linktoPayCayanResponseModel)
	{
		siteOneCustomerAccountService.saveLinkToPayCayanResponseModel(linktoPayCayanResponseModel);
		final LinkToPayPaymentProcessModel businessProcessModel = (LinkToPayPaymentProcessModel) getBusinessProcessService()
				.createProcess("LinkToPayPaymentProcess" + "-" + linktoPayCayanResponseModel.getOrderNumber() + "-"
						+ System.currentTimeMillis(), "linktopay-payment-process");
		businessProcessModel.setCayanResponseForm(linktoPayCayanResponseModel);
		LOG.error("Inside LTP Business process model ");
		getModelService().save(businessProcessModel);
		getBusinessProcessService().startProcess(businessProcessModel);
	}

	@Override
	public boolean isCreditCodeValid(final String username)
	{
		boolean isCreditCodeValid = false;
		final B2BCustomerModel customer = (B2BCustomerModel) getUserService().getUserForUID(username);
		if (null != customer && null != customer.getDefaultB2BUnit())
		{
			if (customer.getActive().booleanValue() && null != customer.getDefaultB2BUnit().getCreditCode())
			{
				isCreditCodeValid = Arrays.stream(creditCodeList).anyMatch(customer.getDefaultB2BUnit().getCreditCode()::equalsIgnoreCase);
			}
		}
		return isCreditCodeValid;
	}

	@Override
	public boolean isPayBillEnabled(final String username)
	{
		boolean isPayBillEnabled = false;
		final B2BCustomerModel customer = (B2BCustomerModel) getUserService().getUserForUID(username);
		if (null != customer && null != customer.getDefaultB2BUnit())
		{
			if (customer.getActive().booleanValue() && null != customer.getDefaultB2BUnit().getCreditCode() && isHavingPayBillOnline() && customer.getDefaultB2BUnit().getPayBillOnline())
			{
				isPayBillEnabled = Arrays.stream(payBillEnableList)
						.anyMatch(customer.getDefaultB2BUnit().getCreditCode()::equalsIgnoreCase);
			}
		}
		return isPayBillEnabled;
	}

	@Override
	public SiteoneCCPaymentAuditLogModel getSiteoneCCAuditDetails(final String orderNumber)
	{
		return defaultSiteOneKountService.getSiteoneCCAuditDetails(orderNumber);
	}

	@Override
	public SiteoneKountDataModel getKountInquiryCallDetails(String orderNumber)
	{
		return defaultSiteOneKountService.getKountInquiryCallDetails(orderNumber);
	}
	
	@Override
	public SiteoneKountDataModel updateKountInquiryCallDetails(final String orderNumber,final String customerEmailId,final String kountStatus,final String isDeclineEmailSent)
	{
		
		SiteoneKountDataModel kountDetailModel = defaultSiteOneKountService.getKountInquiryCallDetails(orderNumber);
		
		if(null == kountDetailModel)
		{
			kountDetailModel = getModelService().create(SiteoneKountDataModel.class);			
			kountDetailModel.setOrderNumber(orderNumber);
			kountDetailModel.setCustomerEmailId(customerEmailId);
			kountDetailModel.setKountStatus(kountStatus);
			kountDetailModel.setIsDeclineEmailSent(isDeclineEmailSent);
			getModelService().save(kountDetailModel);
			getModelService().refresh(kountDetailModel);			
		}
		else 
		{		
			kountDetailModel.setKountStatus(kountStatus);
			kountDetailModel.setIsDeclineEmailSent(isDeclineEmailSent);
			getModelService().save(kountDetailModel);
			getModelService().refresh(kountDetailModel);
		}
		
		return kountDetailModel;
	}

	@Override
	public String fetchOrderDetailsResponse(SiteOneWsLinkToPayOrderResponseData linkToPayOrderResponseData, String orderNumber,
			HttpServletRequest request,String kountSessionId, CartData cartData,String orderAmount)
	{
		String userEmail = null;
		if (linkToPayOrderResponseData.getResult().getContactInfo() != null)
		{
			userEmail = linkToPayOrderResponseData.getResult().getContactInfo().getContactEmail();
		}					
		
		try
		{				
			String kountAuto = null;			
			String kountCallStatus = null;
			SiteoneKountDataModel kountDetails = getKountInquiryCallDetails(orderNumber);
			if(null == kountDetails)
			{
				getClientInformation(request);
				kountAuto = linkToPayEvaluateInquiry(linkToPayOrderResponseData,kountSessionId);
				LOG.error("kount response for inquiry call " + kountAuto);
				if(null != kountAuto)
					{
						final String[]	splitResponse = kountAuto.split("_");
						kountCallStatus = splitResponse[0];
					}
				else
					{
						LOG.error("kount response is null for inquiry call");							
					}				
				kountDetails = updateKountInquiryCallDetails(orderNumber,userEmail,kountCallStatus,null);				
			}
			else
			{
				kountCallStatus = kountDetails.getKountStatus();
			}
			
				if (null != kountCallStatus && kountCallStatus.equalsIgnoreCase(RESPONSE_DECLINED))
				{
					LOG.error("kount response declined for inquiry call for order # " + orderNumber);
					SiteoneKountDataModel kountDetailsNew = getKountInquiryCallDetails(orderNumber);
					if(null != kountDetailsNew)
					{
						if (kountDetailsNew.getIsDeclineEmailSent() == null || StringUtils.isEmpty(kountDetailsNew.getIsDeclineEmailSent()))
						{
							final KountDeclineEvent kountDeclineEvent = new KountDeclineEvent();
							eventService.publishEvent(initializeEventGuest(kountDeclineEvent, linkToPayOrderResponseData));
							LOG.error("kount decline event email sent for order # " + orderNumber);
							kountDetails = updateKountInquiryCallDetails(orderNumber, userEmail, kountCallStatus, "True");
						}
					}					
					return DECLINED;
				}
				else if (null != kountCallStatus && kountCallStatus.equalsIgnoreCase(RESPONSE_APPROVED))
				{
					LOG.error("kount response approved for inquiry call for order # " + orderNumber);
					final SiteoneCCPaymentAuditLogModel auditModel = getSiteoneCCAuditDetails(orderNumber);
					if (null != auditModel)
					{
						final int declineCountLimit = Integer.parseInt(siteOneFeatureSwitchCacheService.getValueForSwitch("PaymentDeclineCount"));
						final int declineCount = auditModel.getDeclineCount().intValue();
						LOG.error("declineCountLimit :"+declineCountLimit + "declineCount for link to pay : "+ declineCount);
						if (declineCount < declineCountLimit)
						{
							final String transportKey = siteOneCayanTransportFacade.getAuthorizeTransportKey(cartData);
							if (null != transportKey)
							{
								return transportKey;
							}
							else
							{
								LOG.error("transport key not generated");
								return DECLINED;
							}
						}
						else
						{
							LOG.error("Cayan response is declined for link to pay");
							final DeclinedCardAttemptEmailEvent declineEvent = new DeclinedCardAttemptEmailEvent();
							eventService.publishEvent(initializeCCDeclinedEmail(declineEvent, linkToPayOrderResponseData,orderNumber,orderAmount));
							return DECLINED;
						}
					}
					else
					{
						final String transportKey = siteOneCayanTransportFacade.getAuthorizeTransportKey(cartData);
						if (null != transportKey)
						{
							return transportKey;
						}
						else
						{
							LOG.error("transport key not generated");
							return DECLINED;
						}
					}
				}
				else
				{
					LOG.error("kount response is null for inquiry call for order # " + orderNumber);
					return DECLINED;
				}
			}		
		catch (final Exception e)
		{
			LOG.error("Exception occurred while getting kount response " + e);
		}
		return DECLINED;
	}
	
	private KountDeclineEvent initializeEventGuest(final KountDeclineEvent event,
			final SiteOneWsLinkToPayOrderResponseData linkToPayOrderResponseData)
	{
		event.setAccountName("LinkToPay");
		if (linkToPayOrderResponseData.getResult().getContactInfo().getCustomerName() != null)
		{
			event.setCustomerName(linkToPayOrderResponseData.getResult().getContactInfo().getCustomerName());
		}
		if (linkToPayOrderResponseData.getResult().getContactInfo().getContactEmail() != null)
		{
			event.setEmailAddress(linkToPayOrderResponseData.getResult().getContactInfo().getContactEmail());
		}
		event.setSite(baseSiteService.getBaseSiteForUID("siteone"));
		event.setOrderNumber(linkToPayOrderResponseData.getResult().getOrderNumber());
		return event;
	}
	

	private DeclinedCardAttemptEmailEvent initializeCCDeclinedEmail(final DeclinedCardAttemptEmailEvent event,
			final SiteOneWsLinkToPayOrderResponseData linkToPayOrderResponseData, String orderNumber, String orderAmount)
	{
		event.setOrderNumber(orderNumber);
		event.setOrderAmount(orderAmount);
		if (linkToPayOrderResponseData.getResult().getContactInfo().getCustomerName() != null)
		{
			event.setCustomerName(linkToPayOrderResponseData.getResult().getContactInfo().getCustomerName());
		}
		if (null != linkToPayOrderResponseData.getResult() && null!= linkToPayOrderResponseData.getResult().getBillingInfo()
				 && StringUtils.isNotEmpty(linkToPayOrderResponseData.getResult().getBillingInfo().getEnteredByEmail()))
		{
			event.setEmailAddress(linkToPayOrderResponseData.getResult().getBillingInfo().getEnteredByEmail());
		}		
		else
		{
			event.setEmailAddress("CustomerAndProductSupport@siteone.com");
		}
		event.setSite(baseSiteService.getBaseSiteForUID("siteone"));
		final DateFormat dateFormat = DateFormat.getDateInstance();
		final Calendar cals = Calendar.getInstance();
		final String currentDate = dateFormat.format(cals.getTime());
		event.setDate(currentDate);
		return event;
	}

	@Override
	public Boolean updateOktaCustomerProfile()
	{
		Boolean isUserUpdated = Boolean.FALSE;
		final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		if (null != b2bCustomerModel && (b2bCustomerModel.getPartnerProgramPermissions()).equals(Boolean.TRUE))
		{
			try
			{
				final OktaCreateOrUpdateUserResponseData OktaCreateOrUpdateUserResponseData = oktaAPI
						.getUser(b2bCustomerModel.getUid());
				if (null != OktaCreateOrUpdateUserResponseData && null != OktaCreateOrUpdateUserResponseData.getId())
				{
					final String userOktaId = OktaCreateOrUpdateUserResponseData.getId();
					LOG.debug("user_okta_id found == " + userOktaId);
					oktaAPI.updateUser(b2bCustomerModel);
					isUserUpdated = Boolean.TRUE;
					LOG.error("okta user profile updated for uid = " + b2bCustomerModel.getUid());
				}
			}
			catch (final ResourceAccessException resourceAccessException)
			{
				LOG.error("Userid not found in Okta...:" + b2bCustomerModel.getUid());
			}
			catch (final Exception e)
			{
				LOG.error(e.getMessage());
			}
		}
		return isUserUpdated;
	}

	@Override
	public String generatePendoEvent(String type, String event, String userAgent, String url, String emailId, String timestamp,
			String ip, String title, String phone, String kountSessionId, String orderNumber, String orderAmount, String quoteNumber,String listCode)
	{

		final PendoEventRequest request = new PendoEventRequest();
		B2BCustomerModel customer = null;
		if (StringUtils.isNotEmpty(emailId) && (!event.equalsIgnoreCase("3XGUEST")))
		{
			customer = (B2BCustomerModel) getUserService().getUserForUID(emailId);

		}
		if (null != customer)
		{
			request.setEmailId(emailId);
			request.setVisitorId(customer.getGuid());
			request.setAccountId(customer.getDefaultB2BUnit().getUid());
			
		}
		if(event.equalsIgnoreCase("3XLINK2PAY")|| event.equalsIgnoreCase("LINK2PAYFAIL")) {
			final OrderModel orderModel = siteOneOrderService.getOrderForCode(orderNumber);
			if (null != orderModel)
			{
				final B2BCustomerModel user = (B2BCustomerModel) orderModel.getUser();
				request.setVisitorId(user.getGuid());
				request.setEmailId(user.getUid());
		}
		}
		if(event.equalsIgnoreCase("3XGUEST"))
		{
			request.setVisitorId("GUESTUSER");
		}
		final PendoPropertiesData properties = new PendoPropertiesData();
		request.setTimestamp(Long.parseLong(timestamp));
			request.setType(type);
		if(StringUtils.isNotEmpty(emailId))
		{
		properties.setEmailId(emailId);
		}
		if(StringUtils.isNotEmpty(kountSessionId))
		{
		properties.setKountSessionId(kountSessionId);
		}
		if(StringUtils.isNotEmpty(listCode))
   		{
   		properties.setListCode(listCode);
   		}
		if(StringUtils.isNotEmpty(orderAmount))
		{
		properties.setOrderAmount(orderAmount);
		}
		if(StringUtils.isNotEmpty(orderNumber))
		{
		properties.setOrderNumber(orderNumber);
		}
		if(StringUtils.isNotEmpty(phone))
		{
			properties.setPhone(phone);
		}
		if(StringUtils.isNotEmpty(quoteNumber))
		{
			properties.setQuoteNumber(quoteNumber);
		}
		request.setProperties(properties);
		request.setEvent(event);
		final PendoContextData context = new PendoContextData();
		context.setIp(ip);
		context.setTitle(title);
		context.setUrl(url);
		context.setUserAgent(userAgent);
		request.setContext(context);
		final String responseStatus = getSiteOneContactWebService().generatePendoEvent(request);
		if (null != responseStatus && responseStatus.contains("OK"))
		{
			return "Success";
		}
		else
		{
			return "Failure";
		}

	}

}
