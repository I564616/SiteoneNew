/**
 *
 */
package com.siteone.facades.customer;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.user.data.SiteOneGuestUserData;
import de.hybris.platform.commercefacades.user.exceptions.PasswordMismatchException;
import de.hybris.platform.commerceservices.customer.TokenInvalidatedException;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commercewebservicescommons.dto.store.PointOfServiceWsDTO;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.user.exceptions.PasswordPolicyViolationException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import org.json.JSONException;
import com.siteone.commerceservices.dto.user.CustomerSpecificWsDTO;
import com.siteone.core.model.LinkToPayCayanResponseModel;
import com.siteone.core.model.SiteoneCCPaymentAuditLogModel;
import com.siteone.core.model.SiteoneKountDataModel;
import com.siteone.facade.LeadGenarationData;
import com.siteone.facades.address.data.SiteOneAddressVerificationData;
import com.siteone.facades.exceptions.ContactNotCreatedOrUpdatedInUEException;
import com.siteone.facades.exceptions.OktaUserAlreadyActiveException;
import com.siteone.facades.exceptions.RecentlyUsedPasswordException;
import com.siteone.facades.exceptions.ServiceUnavailableException;
import com.siteone.integration.customer.data.UserEmailData;
import com.siteone.integration.linktopay.order.data.SiteOneWsLinkToPayOrderResponseData;



/**
 * @author 965504
 *
 */
public interface SiteOneCustomerFacade
{

	public void syncSessionStore(final String geoLocatedStoreId);

	public Collection<PointOfServiceData> getMyStores();

	public PointOfServiceData getPreferredStore();

	public void makeMyStore(String storeId);

	public void removeFromMyStore(String storeId, String geoLoactedStoreId);

	void setDefaultStore();

	/**
	 * @param geoLocatedStoreId
	 */
	void setGeoLocatedStore(String geoLocatedStoreId);

	public List<String> getMyStoresIdList();

	public List<AddressData> getAddressBookForUnit(String unitId);

	Collection<AddressData> getBillingAddressesForUnit(String unitId);

	Collection<AddressData> getShippingAddresssesForUnit(String unitId);

	Collection<AddressData> getAllShippingAddresssesForUnit(String unitId);

	Collection<AddressData> getDefaultBillingAddressesForUnit(String unitId);

	AddressData getDefaultBillingAddressForUnit(String unitId);

	AddressData getDefaultShippingAddressForUnit(String unitId);

	boolean isAddressBookEmpty(String unitId);

	void addAddress(AddressData addressData, String unitId);

	void setDefaultAddress(String addressData, String unitId);

	void editAddress(AddressData addressData, String unitId);

	void removeAddress(AddressData addressData, String unitId) throws ServiceUnavailableException;

	public SiteOneAddressVerificationData validateAddress(final AddressData addressData);

	public String saveUserPreference(final String emailType, final String emailTopicPreference, final String orderPromo);

	boolean isCreditCodeValid(final String userName);

	boolean isPayBillEnabled(String username);

	public CustomerData fetchPersonalDetails();

	public UserEmailData fetchPreferences();

	/**
	 * @param pageableData
	 * @param unitId
	 * @param object
	 * @return
	 */
	SearchPageData<B2BUnitData> getPagedB2BUnits(PageableData pageableData, String unitId, String SearchParam);

	CustomerData getCustomerForId(String uid);

	boolean isUserAvailable(String uid);



	String verifyRecoveryToken(String token)
			throws InvalidTokenException, ResourceAccessException, TokenInvalidatedException, IllegalArgumentException;

	String resetPassword(String stateToken, String newPassword) throws InvalidTokenException, PasswordPolicyViolationException,
			ResourceAccessException, RecentlyUsedPasswordException, TokenInvalidatedException;

	String setPasswordForUser(String token, String password)
			throws TokenInvalidatedException, IllegalArgumentException, PasswordPolicyViolationException;

	boolean verifySetPasswordToken(String token, long tokenValiditySeconds)
			throws TokenInvalidatedException, IllegalArgumentException;

	public void createPassword(final String uid) throws OktaUserAlreadyActiveException;

	void changePassword(String oldPassword, String newPassword) throws PasswordMismatchException, PasswordPolicyViolationException;

	/**
	 * @return
	 */
	Boolean isHavingInvoicePermissions();

	/**
	 * @return
	 */


	/**
	 * @return
	 */
	Boolean isHavingPartnerProgramPermission();

	/**
	 * @return
	 */
	Boolean isHavingAccountOverviewForParent();

	Boolean isHavingAccountOverviewForShipTos();

	Boolean isHavingPayBillOnline();

	Boolean isHavingProjectServices();

	Boolean isHavingNxtLevel();

	Boolean isHavingPlaceOrder();

	public void unlockUserRequest(String email);

	Boolean isEnableAddModifyDeliveryAddress();

	/**
	 * @param token
	 */
	public String unlockUser(String token) throws TokenInvalidatedException, IllegalArgumentException;

	void activateUser(String token) throws TokenInvalidatedException, IllegalArgumentException;

	public String saveLeadGenerationData(final LeadGenarationData leadGenerationData)
			throws ResourceAccessException, IOException, RestClientException;

	public boolean isEmailOpted();

	public void customerLastLogin(String userId);

	void addPhoneNumber(String phoneNumber, String uid)
			throws ServiceUnavailableException, ContactNotCreatedOrUpdatedInUEException, ContactNotCreatedOrUpdatedInUEException;

	void updateLanguagePreference(final String langPreference);

	SearchPageData<B2BUnitData> getPagedB2BDefaultUnits(PageableData pageableData, String SearchParam);

	public void getClientInformation(HttpServletRequest request);

	String getCustomerTypeByOrderCreation();

	/**
	 * @param addressData
	 * @return
	 */
	public CustomerModel saveGuestUserDetails(CustomerData customerData, final Boolean isSameaddressforParcelShip);

	/**
	 * @param addressModel
	 * @param isSameaddressforParcelShip
	 */
	void updateCartWithGuestForAnonymousCheckout(CustomerModel customerModel, CartData cartData,
			Boolean isSameaddressforParcelShip);

	/**
	 * @param customerdata
	 * @param currentCustomer
	 * @return
	 */
	public CustomerModel editGuestUserDetails(CustomerData customerdata, CustomerModel currentCustomer,
			final Boolean isSameaddressforParcelShip);

	/**
	 * @param uid
	 * @return
	 */
	public CustomerModel getCustomerforUID(String uid);

	/**
	 * @param lowerCase
	 * @return
	 */
	public boolean isUserAlreadyExists(String email);

	/**
	 * @param cartData
	 * @param customerdata
	 * @return
	 */
	public SiteOneGuestUserData populateGuestUserData(CartData cartData, CustomerData customerdata,
			final Boolean isSameaddressforParcelShip);

	/**
	 * @param customerdata
	 * @param currentCustomer
	 */
	public void saveAlternateContactDetails(CustomerData customerdata, String mode);

	boolean isUserAccountLocked(String userId);

	/**
	 * @param customerData
	 * @param isSameaddressforParcelShip
	 * @param isDelivery
	 * @param isShipping
	 * @param isSameaddressforParcelShip2
	 * @return
	 */
	public CustomerModel saveGuestUserDetails(CustomerData customerData, CartData cartData, Boolean isSameaddressforParcelShip2);

	/**
	 * @param customerData
	 * @param mode
	 */
	public CustomerModel editGuestUserDetails(final CustomerData customerData, final CustomerModel currentCustomer,
			final CartData cartdata, final Boolean isSameaddressforParcelShip);

	public CustomerSpecificWsDTO getCustomerSpecificInfo(String unitId, B2BCustomerModel customer);

	public String getKountValue(final CartData cartData, final HttpServletRequest request);

	public void updateKountDetails(final String orderCode, final String transactionId, final String kountSessionId);

	/**
	 * @return
	 */
	PointOfServiceData getHomeBranch();

	/**
	 * @return
	 */
	boolean enrollCustomerInTalonOne();

	PointOfServiceWsDTO getCustomerStoreData(final PointOfServiceWsDTO pointOfServiceWsDTO, final String storeId);

	String getPartnerProgramRedirectlUrl();

	String encrypt(final String stringToEncrypt, final String secret);

	String getCurrentUserData();

	boolean getAdminUserStatus();

	Map<String, String> getListOfShiptTos(String shipToUnit);

	/**
	 * @param token
	 * @return
	 */
	boolean isTokenExpired(final String payload, final String header, final String signature)
			throws JSONException, NoSuchAlgorithmException;

	String decode(final String encodedString);

	/**
	 * @param orderNumber
	 * @param isNewBoomiEnv
	 * @return
	 */
	SiteOneWsLinkToPayOrderResponseData getOrderDetails(String orderNumber);

	String linkToPayEvaluateInquiry(final SiteOneWsLinkToPayOrderResponseData linkToPayOrderResponseData,
			final String kountSessionId);

	/**
	 * @param status
	 * @param avsStatus
	 * @param cvv
	 */
	public void updateLinkToPayKountDetails(String status, String avsStatus, String cvv, String orderNum);

	/**
	 * @param boardCardRespForm
	 */
	public void linkToPayPaymentSubmitToUE(final LinkToPayCayanResponseModel cayanResponseModel);

	void saveStore(String storeId);

	public SiteoneCCPaymentAuditLogModel getSiteoneCCAuditDetails(final String orderNumber);

	public SiteoneKountDataModel getKountInquiryCallDetails(final String orderNumber);

	public SiteoneKountDataModel updateKountInquiryCallDetails(final String orderNumber, final String customerEmailId,
			final String kountStatus, final String isDeclineEmailSent);

	public String fetchOrderDetailsResponse(final SiteOneWsLinkToPayOrderResponseData linkToPayOrderResponseData,
			final String orderNumber, final HttpServletRequest request, final String kountSessionId, final CartData cartData,
			final String orderAmount);

	public Boolean updateOktaCustomerProfile();

	/**
	 * @param type
	 * @param event
	 * @param userAgent
	 * @param url
	 * @param emailId
	 * @param timestamp
	 * @param ip
	 * @param title
	 * @param orderAmount
	 * @param orderNumber
	 * @param kountSessionId
	 * @param phone
	 * @param selectedProductList 
	 * @param quoteNumber 
	 */
	public String generatePendoEvent(String type, String event, String userAgent, String url, String emailId, String timestamp,
			String ip, String title, String phone, String kountSessionId, String orderNumber, String orderAmount, String quoteNumber,String listCode);
}

