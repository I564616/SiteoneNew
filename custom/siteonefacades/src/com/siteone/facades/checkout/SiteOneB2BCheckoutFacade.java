/**
 *
 */
package com.siteone.facades.checkout;


import com.siteone.facades.checkout.form.SiteOneGCContactFacadeForm;
import com.siteone.facades.checkout.form.SiteOneOrderTypeFacadeForm;
import de.hybris.platform.commercefacades.ewallet.data.SiteOnePaymentUserData;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CustomerData;

import de.hybris.platform.commercefacades.user.data.SiteOneGuestUserData;

import de.hybris.platform.commercefacades.user.data.SiteOnePOAPaymentInfoData;
import de.hybris.platform.commercefacades.user.data.SiteOnePaymentInfoData;
import de.hybris.platform.commercefacades.user.data.SiteoneFulfilmentData;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.order.strategies.CartValidator;
import de.hybris.platform.commerceservices.enums.SalesApplication;
import de.hybris.platform.core.model.user.EmployeeModel;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import org.springframework.ui.Model;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Abdul Rahman Sheikh M
 *
 */
public interface SiteOneB2BCheckoutFacade extends CheckoutFacade
{

	/**
	 * Place order
	 *
	 * @return orderData representing the order
	 * @throws InvalidCartException
	 *            is thrown by underlying {@link CartValidator}
	 */
	OrderData placeOrder() throws InvalidCartException;

	/**
	 * Place order
	 *
	 * @return orderData representing the order
	 * @throws InvalidCartException
	 *            is thrown by underlying {@link CartValidator}
	 */
	CartData getCheckoutCart();

	void calculateTax();

	/**
	 * @param siteOnePaymentForm
	 * @return
	 */
	OrderData placeOrderWithOnlinePayment(SiteOnePaymentInfoData siteOnePaymentData) throws Exception;

	OrderData placeOrderWithPOAPayment(SiteOnePOAPaymentInfoData siteOnePaymentData) throws Exception;

	/**
	 * @param cartData
	 * @return
	 */
	String getPageTitle(CartData cartData);

	/**
	 * @return
	 */
	String getShippingRate(final CartData cartData);

	SiteOnePaymentUserData getPaymentOptions();

	String placeEwalletOrder(String vaultToken);

	//Mobile APP
	OrderData placeOrder(SalesApplication salesApplication) throws InvalidCartException;
	//Mobile APP
	OrderData placeOrderWithOnlinePayment(SiteOnePaymentInfoData siteOnePaymentData, SalesApplication salesApplication) throws Exception;
	//Mobile APP
	OrderData placeOrderWithPOAPayment(SiteOnePOAPaymentInfoData siteOnePaymentData, SalesApplication salesApplication) throws Exception;

	/**
	 * @param featureSwitchName
	 * @return
	 */
	public List<String> getShippingAvailableBranches(final String featureSwitchName);
	/**
	 * @param sessionPos , shippingState
	 * @return
	 */
	public boolean isShippingStateValid(final PointOfServiceData sessionPos , final String shippingState);


	public CartData updateCartDataBasedOnOrderType(final CartData cartData, final SiteOneOrderTypeFacadeForm siteOneOrderTypeForm)throws ResourceAccessException, Exception;
	public void populateFreightToCart(String deliveryFreightCharge,  String mode,CartData cartData);
	public boolean validateAddressForDelivery(final SiteOneOrderTypeFacadeForm siteOneOrderTypeForm);

	public boolean isCartValidForCheckout(final CartData cartData);

	public boolean checkStockLevelNLA(final String productCode, final long quantity);


	public boolean isAccountValidForCheckout(final CartData cartData);

	public boolean isPosValidForCheckout(final CartData cartData);

	public boolean isPONumberRequired();

	public String getFreightCharge(final CartData cartData, final boolean isHomeOwner, final boolean isGuest) throws IOException, RestClientException;
	
	public String getItemLevelShippingFee(final List<OrderEntryData> cartData);
	
	public boolean getIfItemLevelShippingFeeApplicable(final List<OrderEntryData> cartData);

	public  boolean validatePhoneNumber(final String phoneNumber);

	public void formGuestUserData(final CustomerData b2bCustomerData, final Model model);


	public String getOriginalEmail(final String displayUid);

	public CartData populatecartData(final CartData cartData);
	public CustomerData populateGuestUserData(final SiteOneGCContactFacadeForm siteOneGCContactForm);
	
	public CartData populateFreights(final CartData cartData, final Map<String, Boolean> fulfilmentStatus);

	public void updateShippingState(final CartData cartData , final SiteoneFulfilmentData siteoneFulfilmentData);
	
	void populateUserContact(final CartData cartData , final SiteOneGuestUserData siteOneGuestUserData, 
			final Boolean isSameaddressforParcelShip, final String state);
	
	boolean setShippingAddress(AddressData address);
	
	public Map<String, Boolean> populateFulfilmentStatus(final CartData cartData);

	public void updateDeliveryFee(final CartData cartData);

	public void updateASMAgentForOrder(EmployeeModel emp);
	
	public List<String> getStoreHolidayDates();        

	boolean getIsCCDisabledAtDC(CartData cartData);
	
	public double getThreshold(CartData cartData);
	
	public void saveSiteoneCCAuditLog(final CartData cartData, final String cardNum, final String zipCode);

	public boolean getCardBlockFlag(final CartData cartData);
	
}
