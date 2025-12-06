/**
 *
 */
package com.siteone.v2.controller;

import de.hybris.platform.b2bacceleratorfacades.api.cart.CheckoutFacade;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.storesession.StoreSessionFacade;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.order.CommerceCartRestorationException;
import de.hybris.platform.commerceservices.order.CommerceCartService;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdAndUserIdParam;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdUserIdAndCartIdParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import org.apache.log4j.Logger;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;

import com.google.gson.Gson;
import com.siteone.commerceservices.buyagain.data.DeliveryAddressResultDTO;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.facades.checkout.SiteOneB2BCheckoutFacade;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.exceptions.AddressNotCreatedOrUpdatedInUEException;
import com.siteone.facades.exceptions.ContactNotCreatedOrUpdatedInUEException;
import com.siteone.facades.exceptions.ServiceUnavailableException;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.utils.SiteOneAddressDataUtil;
import com.siteone.utils.SiteOneAddressForm;
import com.siteone.commerceservices.address.dto.SiteOneAddressVerificationWsDTO;
import com.siteone.facades.address.data.SiteOneAddressVerificationData;

/**
 * @author pelango
 *
 */
@Controller
@RequestMapping(value = "/{baseSiteId}/users/{userId}/checkout/multi/order-type")
@Tag(name = "Siteone Order Type")
public class PickupDeliveryCheckoutStepController extends BaseCommerceController
{
	private static final String DELIVERY = "DELIVERY";
	private static final String PICKUP = "PICKUP";
	private static final String ORDER_TYPE = "order-type";
	private static final String ADDRESS_FORM_ATTR = "siteOneAddressForm";
	private static final String REGIONS_ATTR = "regions";
	private static final String MIXEDCART_ENABLED_BRANCHES = "MixedCartEnabledBranches";


	@Resource(name = "b2bCheckoutFacade")
	private CheckoutFacade b2bCheckoutFacade;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;

	@Resource(name = "storeFinderFacade")
	private SiteOneStoreFinderFacade storeFinderFacade;

	@Resource(name = "defaultSiteoneAddressConverter")
	private SiteOneAddressDataUtil siteoneAddressDataUtil;
	
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;
	
	@Resource(name = "sessionService")
	private SessionService sessionService;
	
	@Resource(name = "userFacade")
	private UserFacade userFacade;
	@Resource(name = "commerceCartService")
	private CommerceCartService commerceCartService;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
	
	@Resource(name = "cartFacade")
	private CartFacade cartFacade;
	
	private static final Logger LOG = Logger.getLogger(PickupDeliveryCheckoutStepController.class);
	
	@PostMapping("/validate-address")
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@ResponseBody
	@Operation(operationId = "validate-address", summary = "Validate the Address", description = "Validate the Address")
	@ApiBaseSiteIdUserIdAndCartIdParam
	public SiteOneAddressVerificationWsDTO validateAddress(final SiteOneAddressForm addressForm, final String fields)
	{
		final AddressData addressData = siteoneAddressDataUtil.convertToAddressData(addressForm);
		SiteOneAddressVerificationData addressVerificationData = ((SiteOneCustomerFacade) customerFacade).validateAddress(addressData);
		return getDataMapper().map(addressVerificationData, SiteOneAddressVerificationWsDTO.class, fields);
	}

	@PostMapping("/add-address")
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@ResponseBody
	@Operation(operationId = "add-address", summary = "Add the new address information", description = "Add the new address information")
	@ApiBaseSiteIdUserIdAndCartIdParam
	public DeliveryAddressResultDTO addAddress(final SiteOneAddressForm addressForm, @Parameter(description = "unitId") @RequestParam(required = false) final String unitId) throws CommerceCartModificationException
	{
		DeliveryAddressResultDTO result = new DeliveryAddressResultDTO();
		try
		{
			final B2BUnitData unit;
			if (!StringUtils.isEmpty(unitId))
			{
				unit = b2bUnitFacade.getUnitForUid(unitId);
			} else {
				unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
			}
			addressForm.setUnitId(unit.getUid());
			AddressData newAddress = null;
			if (Boolean.TRUE.equals(addressForm.getSaveInAddressBook()))
			{
				newAddress = siteoneAddressDataUtil.convertToVisibleAddressData(addressForm);
			}
			else
			{
				newAddress = siteoneAddressDataUtil.convertToAddressData(addressForm);
			}
			final ArrayList returnObject = new ArrayList();
			((SiteOneCustomerFacade) customerFacade).addAddress(newAddress, addressForm.getUnitId());
			if(BooleanUtils.isTrue(newAddress.isDefaultAddress()))
			{
				((SiteOneCustomerFacade) customerFacade).setDefaultAddress(newAddress.getId(), addressForm.getUnitId());
			}
			if (Boolean.TRUE.equals(addressForm.getSaveInAddressBook()))
			{
				final Collection<AddressData> addressData = ((SiteOneCustomerFacade) customerFacade)
						.getShippingAddresssesForUnit(unit.getUid());
				result.setDeliveryAddress(addressData);
				for (final AddressData addressDataEntry : addressData)
				{
					if (addressDataEntry.getId().equalsIgnoreCase(newAddress.getId()))
					{
						returnObject.add(addressDataEntry);
						returnObject.add(new Gson().toJson(addressData));
						break;
					}

				}
			}
			else
			{
				final Collection<AddressData> allAddressData = ((SiteOneCustomerFacade) customerFacade)
						.getAllShippingAddresssesForUnit(unit.getUid());
				for (final AddressData addressDataEntry : allAddressData)
				{
					if (addressDataEntry.getId().equalsIgnoreCase(newAddress.getId()))
					{
						returnObject.add(addressDataEntry);
						break;
					}

				}
			}
			result.setReturnObject(returnObject);
			return result;
		}
		catch (final ServiceUnavailableException serviceUnavailableException)
		{
			LOG.error(serviceUnavailableException);
			throw serviceUnavailableException;
		}
		catch (final AddressNotCreatedOrUpdatedInUEException addressNotCreatedOrUpdatedInUEException)
		{
			LOG.error(addressNotCreatedOrUpdatedInUEException);
			throw addressNotCreatedOrUpdatedInUEException;
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method addAddress");
	    }
	}
	
	@GetMapping("/poNumber")
	@ResponseBody
	@Operation(operationId = "validatePoNumber", summary = "validating the PoNumber", description = "validating the PoNumber exist")
	@ApiBaseSiteIdAndUserIdParam
	public  boolean validatePoNumber(
			@RequestParam(value = "cartId", required = true) final String cartId,
			@RequestParam("poNumber") final String poNumber,
			@RequestParam(value = "storeId", required = true) final String storeId,
			@Parameter(description = "unitId") @RequestParam(required = false) final String unitId,
			@RequestParam(value = "isGuest", defaultValue="false") boolean isGuest
			)
	{
		try
		{
			final B2BUnitData unit;
			boolean result = false;
			if (!StringUtils.isEmpty(unitId))
			{
				unit = b2bUnitFacade.getUnitForUid(unitId);
			} else {
				unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
			}
			if(isGuest){
				sessionService.setAttribute("guestUser","guest");
			}
			storeSessionFacade.setSessionShipTo(unit);
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);
			if(!isGuest) {
				restoreSessionCart(null);
			}
			else {
				restoreSessionCart(cartId);
			}
			final CartData cartData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getCheckoutCart();
			if (!isGuest && cartData.getOrderingAccount().getIsPONumberRequired())
			{
				if (StringUtils.isEmpty(cartData.getOrderingAccount().getPoRegex())) {
					result = true;
				}
				else if (Pattern.matches(cartData.getOrderingAccount().getPoRegex(), poNumber))
				{
					result = true;
				}
				
			}
			else {
				result = true;
			}
			
			if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(MIXEDCART_ENABLED_BRANCHES,storeId) && result){
				
				cartData.setPurchaseOrderNumber(poNumber);
				b2bCheckoutFacade.updateCheckoutCart(cartData);
			}
			return result;
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method validatePoNumber");
	    }
		
	}
	
	public void restoreSessionCart(String cartId)
	{
		boolean restore = true;
		if (!cartFacade.hasSessionCart() && restore)
		{
			try
			{
				final CommerceCartParameter commerceCartParameter = new CommerceCartParameter();
				if(cartId == null) {
					commerceCartParameter.setCart(commerceCartService.getCartForGuidAndSiteAndUser(cartId,
							baseSiteService.getCurrentBaseSite(), userService.getCurrentUser()));
				}
				else {
					commerceCartParameter.setCart(commerceCartService.getCartForGuidAndSite(cartId,
							baseSiteService.getCurrentBaseSite()));
				}
				
				commerceCartService.restoreCart(commerceCartParameter);
			}
			catch (final CommerceCartRestorationException e)
			{
				LOG.error("Couldn't restore cart: " + e.getMessage());
				LOG.debug("Exception thrown: " + e);
			}
		}
		
	}
	
}