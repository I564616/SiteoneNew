/**
 *
 */
package com.siteone.storefront.controllers.pages.checkout.steps;


import de.hybris.platform.acceleratorstorefrontcommons.annotations.PreValidateCheckoutStep;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.checkout.steps.CheckoutStep;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.checkout.steps.AbstractCheckoutStepController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.b2bacceleratorfacades.api.cart.CheckoutFacade;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.storesession.StoreSessionFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.user.UserService;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import org.apache.log4j.Logger;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.siteone.core.enums.MeridianTimeEnum;
import com.siteone.core.enums.OrderTypeEnum;
import com.siteone.facades.address.data.SiteOneAddressVerificationData;
import com.siteone.facades.checkout.SiteOneB2BCheckoutFacade;
import com.siteone.facades.checkout.form.SiteOneOrderTypeFacadeForm;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.exceptions.AddressNotCreatedOrUpdatedInUEException;
import com.siteone.facades.exceptions.ContactNotCreatedOrUpdatedInUEException;
import com.siteone.facades.exceptions.ServiceUnavailableException;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.storefront.checkout.steps.validation.impl.SiteOneOrderTypeFormValidator;
import com.siteone.storefront.controllers.ControllerConstants;
import com.siteone.storefront.forms.SiteOneAddPhoneNumberForm;
import com.siteone.storefront.forms.SiteOneAddressForm;
import com.siteone.storefront.forms.SiteOneOrderTypeForm;
import com.siteone.storefront.util.SiteOneAddressDataUtil;
import com.siteone.storefront.util.SiteOneCheckoutUtils;
import com.siteone.storefront.validator.SiteOneAddressValidator;


/**
 * @author 1190626
 *
 */
@Controller
@RequestMapping(value = "/checkout/multi/order-type")
public class PickupDeliveryCheckoutStepController extends AbstractCheckoutStepController
{
	private static final String DELIVERY = "DELIVERY";
	private static final String PICKUP = "PICKUP";
	private static final String ORDER_TYPE = "order-type";
	private static final String ADDRESS_FORM_ATTR = "siteOneAddressForm";
	private static final String REGIONS_ATTR = "regions";


	@Resource(name = "b2bCheckoutFacade")
	private CheckoutFacade b2bCheckoutFacade;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "storeSessionFacade")
	protected StoreSessionFacade storeSessionFacade;

	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;

	@Resource(name = "storeFinderFacade")
	private StoreFinderFacade storeFinderFacade;

	@Resource(name = "siteOneCheckoutUtils")
	private SiteOneCheckoutUtils siteOneCheckoutUtils;

	@Resource(name = "orderTypeFormValidator")
	private SiteOneOrderTypeFormValidator orderTypeFormValidator;

	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;

	@Resource(name = "siteoneAddressValidator")
	private SiteOneAddressValidator siteOneAddressValidator;

	@Resource(name = "siteoneAddressDataUtil")
	private SiteOneAddressDataUtil siteoneAddressDataUtil;

	private final Pattern PHONE_NUMBER_REGEX = Pattern.compile("\\d{3}?[-]\\d{3}?[-]\\d{4}");

	private static final Logger LOG = Logger.getLogger(PickupDeliveryCheckoutStepController.class);

	@ModelAttribute("orderType")
	public List<SelectOption> getOrderType()
	{
		final List<SelectOption> selectOptions = new ArrayList<SelectOption>();
		selectOptions.add(new SelectOption(OrderTypeEnum.PICKUP.getCode(), getMessageSource()
				.getMessage("choosePickupDeliveryMethodPage.radio.pickup", null, getI18nService().getCurrentLocale())));
		selectOptions.add(new SelectOption(OrderTypeEnum.DELIVERY.getCode(), getMessageSource()
				.getMessage("choosePickupDeliveryMethodPage.radio.delivery", null, getI18nService().getCurrentLocale())));
		return selectOptions;
	}

	@ModelAttribute("meridianTime")
	public List<SelectOption> getMeridianTime()
	{
		final List<SelectOption> selectOptions = new ArrayList<SelectOption>();
		selectOptions
				.add(new SelectOption(MeridianTimeEnum.AM.getCode(), enumerationService.getEnumerationName(MeridianTimeEnum.AM)));
		selectOptions
				.add(new SelectOption(MeridianTimeEnum.PM.getCode(), enumerationService.getEnumerationName(MeridianTimeEnum.PM)));
		return selectOptions;
	}


	@GetMapping("/choose")
	@RequireHardLogIn
	@PreValidateCheckoutStep(checkoutStep = ORDER_TYPE)
	public String enterStep(final Model model, final RedirectAttributes redirectAttributes)
			throws CMSItemNotFoundException, CommerceCartModificationException
	{
		final CartData cartData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getCheckoutCart();
		prepareForm(cartData, model);
		storeCmsPageInModel(model, getContentPageForLabelOrId("choosePickupDeliveryMethodPage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("choosePickupDeliveryMethodPage"));
		return ControllerConstants.Views.Pages.MultiStepCheckout.ChoosePickupDeliveryMethodPage;
	}

	protected void prepareForm(final CartData cartData, final Model model)
	{
		final PointOfServiceData posData = ((SiteOneStoreFinderFacade) storeFinderFacade)
				.getStoreForId(((SiteOneStoreSessionFacade) storeSessionFacade).getSessionStore().getStoreId());
		final CustomerData currentCustomer = customerFacade.getCurrentCustomer();
		final B2BUnitData sessionShipTo = ((SiteOneStoreSessionFacade) storeSessionFacade).getSessionShipTo();

		if (!model.containsAttribute("siteOneOrderTypeForm"))
		{
			final SiteOneOrderTypeForm siteOneOrderTypeForm = new SiteOneOrderTypeForm();

			if (null != cartData.getOrderType())
			{
				siteOneOrderTypeForm.setOrderType(cartData.getOrderType());
			}
			siteOneOrderTypeForm.setStoreId(posData.getStoreId());

			if (null != cartData.getDeliveryAddress())
			{
				siteOneOrderTypeForm.setAddressId(cartData.getDeliveryAddress().getId());
			}

			final List<ProductData> backorderEligibleProducts = new ArrayList<>();
			final List<ProductData> nearbyProducts = new ArrayList<>();
			for (final OrderEntryData entry : cartData.getEntries())
			{

				if (null != entry.getProduct().getIsEligibleForBackorder() && entry.getProduct().getIsEligibleForBackorder() == true)
				{
					backorderEligibleProducts.add(entry.getProduct());
				}
			}
			model.addAttribute("backorderEligibleProducts", backorderEligibleProducts);
			for (final OrderEntryData entry : cartData.getEntries())
			{

				if (null != entry.getProduct().getIsStockInNearbyBranch() && entry.getProduct().getIsStockInNearbyBranch() == true)
				{
					nearbyProducts.add(entry.getProduct());
				}
			}
			model.addAttribute("nearbyProducts", nearbyProducts);
			if (null != cartData.getContactPerson())
			{
				siteOneOrderTypeForm.setContactId(cartData.getContactPerson().getEmail().trim().toLowerCase());
			}
			else
			{
				siteOneOrderTypeForm.setContactId(currentCustomer.getDisplayUid().toLowerCase());
			}
			if (null != cartData.getRequestedMeridian())
			{
				siteOneOrderTypeForm.setRequestedMeridian(cartData.getRequestedMeridian());
			}
			if (null != cartData.getRequestedDate())
			{
				siteOneOrderTypeForm.setRequestedDate(cartData.getRequestedDate());
			}

			siteOneOrderTypeForm.setSpecialInstruction(cartData.getSpecialInstruction());
			siteOneOrderTypeForm.setPurchaseOrderNumber(cartData.getPurchaseOrderNumber());

			model.addAttribute("siteOneOrderTypeForm", siteOneOrderTypeForm);
		}
		if (currentCustomer.getRoles().contains("b2badmingroup"))
		{
			model.addAttribute(REGIONS_ATTR, getI18NFacade().getRegionsForCountryIso("US"));
			final SiteOneAddressForm addressForm = getPreparedAddressForm();
			addressForm.setUnitId(sessionShipTo.getUid());
			model.addAttribute(ADDRESS_FORM_ATTR, addressForm);
		}

		setCheckoutStepLinksForModel(model, getCheckoutStep());
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		model.addAttribute("pointOfService", posData);
		model.addAttribute("cartData", cartData);
		model.addAttribute("currentCustomer", currentCustomer);
		model.addAttribute("deliveryAddresses",
				((SiteOneCustomerFacade) customerFacade).getShippingAddresssesForUnit(sessionShipTo.getUid()));
		model.addAttribute("customers", ((SiteOneB2BUnitFacade) b2bUnitFacade).getCustomersForUnit(sessionShipTo.getUid()));
	}

	@PostMapping("/submit")
	@RequireHardLogIn
	public String submit(final SiteOneOrderTypeForm siteOneOrderTypeForm, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectAttributes) throws CMSItemNotFoundException, CommerceCartModificationException
	{
		final CartData cartData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getCheckoutCart();
		orderTypeFormValidator.validate(siteOneOrderTypeForm, bindingResult);
		if (bindingResult.hasErrors())
		{
			model.addAttribute("siteOneOrderTypeForm", siteOneOrderTypeForm);
			prepareForm(cartData, model);
			for (final Object object : bindingResult.getAllErrors())
			{
				if (object instanceof FieldError)
				{
					final FieldError fieldError = (FieldError) object;
					if (fieldError.getField().equalsIgnoreCase("addressId"))
					{
						GlobalMessages.addErrorMessage(model, "checkout.error.address.formentry.invalid");
					}
					else if (fieldError.getField().equalsIgnoreCase("contactId"))
					{
						GlobalMessages.addErrorMessage(model, "checkout.error.contact.formentry.invalid");
					}
				}
			}
			storeCmsPageInModel(model, getContentPageForLabelOrId("choosePickupDeliveryMethodPage"));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId("choosePickupDeliveryMethodPage"));
			return ControllerConstants.Views.Pages.MultiStepCheckout.ChoosePickupDeliveryMethodPage;
		}

		try
		{
			((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).updateCartDataBasedOnOrderType(cartData,
					getFacadeForm(siteOneOrderTypeForm));
		}
		catch (final ResourceAccessException ex)
		{
			LOG.error("Unable to connect to UE with Delivery Cost", ex);
			return getCheckoutStep().previousStep();
		}
		catch (final Exception ex)
		{
			LOG.error("Unable to get delivery charge Please try again" + ex);
			return getCheckoutStep().previousStep();
		}
		try
		{
			b2bCheckoutFacade.updateCheckoutCart(cartData);
		}
		catch (final ResourceAccessException ex)
		{
			LOG.error("Unable to get customer specific price", ex);
			GlobalMessages.addMessage(model, GlobalMessages.ERROR_MESSAGES_HOLDER, "cart.price.unavailable", new Object[]
			{ ((SiteOneStoreSessionFacade) storeSessionFacade).getSessionStore().getAddress().getPhone() });
			return getCheckoutStep().previousStep();
		}


		return getCheckoutStep().nextStep();
	}


	
	@PostMapping("/validate-address")
	public @ResponseBody SiteOneAddressVerificationData validateAddress(final SiteOneAddressForm addressForm)
	{
		final AddressData addressData = siteoneAddressDataUtil.convertToAddressData(addressForm);
		return ((SiteOneCustomerFacade) customerFacade).validateAddress(addressData);
	}
	
	@PostMapping("/add-address")
	@RequireHardLogIn
	public @ResponseBody ArrayList addAddress(final SiteOneAddressForm addressForm, final BindingResult bindingResult,
			final Model model, final RedirectAttributes redirectAttributes)
			throws CMSItemNotFoundException, CommerceCartModificationException
	{
		final B2BUnitData sessionShipTo = ((SiteOneStoreSessionFacade) storeSessionFacade).getSessionShipTo();
		addressForm.setUnitId(sessionShipTo.getUid());
		getSiteOneAddressValidator().validate(addressForm, bindingResult);
		AddressData newAddress = null;
		if (Boolean.TRUE.equals(addressForm.getSaveInAddressBook()))
		{
			newAddress = siteoneAddressDataUtil.convertToVisibleAddressData(addressForm);
		}
		else
		{
			newAddress = siteoneAddressDataUtil.convertToAddressData(addressForm);
		}

		try
		{
			final ArrayList returnObject = new ArrayList();
			((SiteOneCustomerFacade) customerFacade).addAddress(newAddress, addressForm.getUnitId());
			if(BooleanUtils.isTrue(newAddress.isDefaultAddress()))
			{
				((SiteOneCustomerFacade) customerFacade).setDefaultAddress(newAddress.getId(), addressForm.getUnitId());
			}
			if (Boolean.TRUE.equals(addressForm.getSaveInAddressBook()))
			{
				final Collection<AddressData> addressData = ((SiteOneCustomerFacade) customerFacade)
						.getShippingAddresssesForUnit(sessionShipTo.getUid());
				model.addAttribute("deliveryAddresses", addressData);
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
						.getAllShippingAddresssesForUnit(sessionShipTo.getUid());
				for (final AddressData addressDataEntry : allAddressData)
				{
					if (addressDataEntry.getId().equalsIgnoreCase(newAddress.getId()))
					{
						returnObject.add(addressDataEntry);
						break;
					}

				}
			}
			return returnObject;
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

	}

	@GetMapping("/next")
	@RequireHardLogIn
	@Override
	public String next(final RedirectAttributes redirectAttributes)
	{
		return getCheckoutStep().nextStep();
	}

	@GetMapping("/back")
	@RequireHardLogIn
	@Override
	public String back(final RedirectAttributes redirectAttributes)
	{
		return getCheckoutStep().previousStep();
	}

	protected CheckoutStep getCheckoutStep()
	{
		return getCheckoutStep(ORDER_TYPE);
	}

	@GetMapping("/add-phone")
	public String getAddPhoneNumber(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute(new SiteOneAddPhoneNumberForm());
		return ControllerConstants.Views.Fragments.Checkout.AddPhoneNumberPopup;
	}

	@PostMapping("/add-phone")
	public String addPhoneNumber(@Valid
	final SiteOneAddPhoneNumberForm form, final BindingResult bindingResult, final Model model) throws CMSItemNotFoundException
	{
		if (validatePhoneNumber(form.getPhoneNumber()))
		{
			bindingResult.rejectValue("phoneNumber", "address.phone.invalid");
		}

		if (bindingResult.hasErrors())
		{
			return ControllerConstants.Views.Fragments.Checkout.AddPhoneNumberPopup;
		}
		else
		{
			//call to add  phone number

			final String phoneNumber = form.getPhoneNumber();
			final String uid = form.getEmailId();

			try
			{
				((SiteOneCustomerFacade) customerFacade).addPhoneNumber(phoneNumber, uid);
				GlobalMessages.addInfoMessage(model, "checkout.phonenumber.updated");
			}
			catch (final ServiceUnavailableException serviceUnavailableException)
			{
				LOG.error("Service Unavailable Exception Occured", serviceUnavailableException);
				model.addAttribute("isPhoneNumberUpdated", false);
				return ControllerConstants.Views.Fragments.Checkout.AddPhoneNumberPopup;
			}
			catch (final ContactNotCreatedOrUpdatedInUEException contactNotCreatedOrUpdatedInUEException)
			{
				LOG.error("Contact Not Created/Updated In UE", contactNotCreatedOrUpdatedInUEException);
				model.addAttribute("isPhoneNumberUpdated", false);
				return ControllerConstants.Views.Fragments.Checkout.AddPhoneNumberPopup;
			}
			catch (final UnknownIdentifierException unknownIdentifierException)
			{
				LOG.error("unknown Identifier Exception: " + uid, unknownIdentifierException);
				model.addAttribute("isPhoneNumberUpdated", false);
				return ControllerConstants.Views.Fragments.Checkout.AddPhoneNumberPopup;
			}

		}

		return ControllerConstants.Views.Fragments.Checkout.AddPhoneNumberSuccessPopup;
	}

	protected boolean validatePhoneNumber(final String phoneNumber)
	{
		boolean status = false;

		final Matcher matcher = PHONE_NUMBER_REGEX.matcher(phoneNumber);
		final String number = phoneNumber.replaceAll("[()\\s-]+", "");
		if (!(matcher.matches()) || number.length() != 10)
		{
			status = true;
		}
		return status;
	}

	protected SiteOneAddressForm getPreparedAddressForm()
	{
		final CustomerData currentCustomerData = customerFacade.getCurrentCustomer();
		final SiteOneAddressForm addressForm = new SiteOneAddressForm();
		addressForm.setFirstName(currentCustomerData.getFirstName());
		addressForm.setLastName(currentCustomerData.getLastName());
		addressForm.setTitleCode(currentCustomerData.getTitleCode());
		return addressForm;
	}

	protected SiteOneAddressValidator getSiteOneAddressValidator()
	{
		return siteOneAddressValidator;
	}

	@GetMapping("/poNumber")
	public @ResponseBody boolean validatePoNumber(@RequestParam(value = "poNumber")
	final String poNumber, final Model model) throws CMSItemNotFoundException
	{
		final CartData cartData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getCheckoutCart();
		if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
		{
			if (cartData.getOrderingAccount().getIsPONumberRequired())
			{
				if (!StringUtils.isBlank(cartData.getOrderingAccount().getPoRegex()) && Pattern.matches(cartData.getOrderingAccount().getPoRegex(), poNumber))
				{
					return updatePONumber(cartData,poNumber);
				}
				else if(StringUtils.isBlank(cartData.getOrderingAccount().getPoRegex())){
					return updatePONumber(cartData,poNumber);
				}
			}
			else
			{
				cartData.setPurchaseOrderNumber(poNumber);

			}
		}
		else
		{
			cartData.setPurchaseOrderNumber(poNumber);
		}
		b2bCheckoutFacade.updateCheckoutCart(cartData);
		return false;
	}

	protected boolean updatePONumber(final CartData cartData,final String poNumber ) {
		cartData.setPurchaseOrderNumber(poNumber);
		b2bCheckoutFacade.updateCheckoutCart(cartData);
		return true;
	}
	protected SiteOneOrderTypeFacadeForm getFacadeForm(final SiteOneOrderTypeForm siteOneOrderTypeForm)
	{
		final SiteOneOrderTypeFacadeForm siteOneOrderTypeFormToFacade = new SiteOneOrderTypeFacadeForm();
		final BeanWrapper wrappedSource = new BeanWrapperImpl(siteOneOrderTypeForm);
		final String[] nullProps = Stream.of(wrappedSource.getPropertyDescriptors()).map(FeatureDescriptor::getName)
				.filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null).toArray(String[]::new);
		BeanUtils.copyProperties(siteOneOrderTypeForm, siteOneOrderTypeFormToFacade, nullProps);
		return siteOneOrderTypeFormToFacade;
	}


}