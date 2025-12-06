/**
 *
 */
package com.siteone.storefront.controllers.pages.checkout.steps;

import de.hybris.platform.acceleratorstorefrontcommons.annotations.PreValidateCheckoutStep;
import de.hybris.platform.acceleratorstorefrontcommons.checkout.steps.CheckoutStep;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.checkout.steps.AbstractCheckoutStepController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.acceleratorstorefrontcommons.forms.VoucherForm;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2bacceleratorfacades.exception.EntityValidationException;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.ewallet.data.SiteOneEwalletData;
import de.hybris.platform.commercefacades.order.data.AbstractOrderData;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.data.SiteOnePOAPaymentInfoData;
import de.hybris.platform.commercefacades.user.data.SiteOnePaymentInfoData;
import de.hybris.platform.commercefacades.voucher.VoucherFacade;
import de.hybris.platform.commercefacades.voucher.exceptions.VoucherOperationException;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.user.EmployeeModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.siteone.core.asm.dao.ASMAgentRetrieveDao;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.ewallet.service.SiteOneEwalletService;
import com.siteone.facades.cart.SiteOneCartFacade;
import com.siteone.facades.checkout.SiteOneB2BCheckoutFacade;
import com.siteone.facades.checkout.impl.DefaultSiteOneB2BCheckoutFacade;
import com.siteone.facades.constants.SiteoneFacadesConstants;
import com.siteone.facades.ewallet.SiteOneEwalletFacade;
import com.siteone.facades.populators.SiteOneProductPromotionsPopulator;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.storefront.controllers.ControllerConstants;
import com.siteone.storefront.util.SiteOneCheckoutUtils;
import com.siteone.storefront.util.SiteOnePaymentInfoUtil;


/**
 * @author 1190626
 *
 */
@Controller
@RequestMapping(value = "/checkout/multi/order-summary")
public class SiteOneOrderSummaryCheckoutStepController extends AbstractCheckoutStepController
{
	private static final Logger LOG = Logger.getLogger(SiteOneOrderSummaryCheckoutStepController.class);

	private static final String SUMMARY = "summary";
	public static final String VOUCHER_FORM = "voucherForm";
	private static final String REDIRECT_TO_ORDERTYPE_PAGE = REDIRECT_PREFIX + "/checkout/multi/order-type/choose";
	private static final String REDIRECT_TO_CART_PAGE = REDIRECT_PREFIX + "/cart";
	private static final String REDIRECT_TO_ORDER_PAGE = REDIRECT_PREFIX + "/checkout/multi/order-summary/view";
	private static final String REDIRECT_TO_PAYMENT_PAGE = REDIRECT_PREFIX + "/checkout/multi/siteone-payment/add";
	private static final String PAYMENTTYPE_THREE = "3";
	private static final String CHECKOUT_PAGE = REDIRECT_PREFIX + "/checkout/multi/siteOne-checkout";
	private static final String PAYMENTTYPE_ONE = "1";
	private static final String PAYMENTTYPE_TWO = "2";

	@Resource(name = "b2bCheckoutFacade")
	private DefaultSiteOneB2BCheckoutFacade b2bCheckoutFacade;

	@Resource(name = "asmAgentRetrieveDao")
	private ASMAgentRetrieveDao asmAgentRetrieveDao;

	@Resource(name = "voucherFacade")
	private VoucherFacade voucherFacade;
	
	@Resource(name = "siteOneEwalletFacade")
	private SiteOneEwalletFacade siteOneEwalletFacade;

	@Resource(name = "siteOneCheckoutUtils")
	private SiteOneCheckoutUtils siteOneCheckoutUtils;

	@Resource(name = "cartService")
	private CartService cartService;

	@Resource(name = "cartFacade")
	private SiteOneCartFacade siteOneCartFacade;

	@Resource(name = "siteOnePaymentInfoUtil")
	private SiteOnePaymentInfoUtil siteOnePaymentInfoUtil;

	@Resource(name = "ewalletService")
	private SiteOneEwalletService ewalletService;

	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "productPromotionsPopulator")
	private SiteOneProductPromotionsPopulator siteOneProductPromotionsPopulator;

	@Resource(name = "productService")
	private ProductService productService;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@GetMapping("/view")
	@Override
	@PreValidateCheckoutStep(checkoutStep = SUMMARY)
	public String enterStep(final Model model, final RedirectAttributes redirectAttributes)
			throws CMSItemNotFoundException, CommerceCartModificationException
	{
		final CartData cartData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getCheckoutCart();
		final SiteOneEwalletData ewalletData = getSessionService().getAttribute("ewalletData");
		final String termsCode = getSessionService().getAttribute("termsCode");

		if (null != ewalletData && null != ewalletData.getValutToken()
				|| getSessionService().getAttribute("globalTemporaryToken") != null)
		{
			final SiteOnePaymentInfoData siteOnePaymentData = new SiteOnePaymentInfoData();
			siteOnePaymentData.setPaymentType(PAYMENTTYPE_THREE);
			if(getSessionService().getAttribute("globalCardType") != null)
			{
				siteOnePaymentData.setCardNumber(getSessionService().getAttribute("globalCardNumber"));
				siteOnePaymentData.setApplicationLabel(getSessionService().getAttribute("globalCardType"));
			}
			else {
		
			siteOnePaymentData.setCardNumber(ewalletData.getLast4CreditcardDigits());
			siteOnePaymentData.setApplicationLabel(ewalletData.getCreditCardType());
			}
			cartData.setSiteOnePaymentInfoData(siteOnePaymentData);
		}

		else if (null != termsCode && "" != termsCode)
		{
			final SiteOnePOAPaymentInfoData siteOnePaymentData = new SiteOnePOAPaymentInfoData();
			siteOnePaymentData.setPaymentType(PAYMENTTYPE_ONE);
			siteOnePaymentData.setTermsCode(termsCode);
			cartData.setSiteOnePOAPaymentInfoData(siteOnePaymentData);
		}
		else
		{
			final SiteOnePaymentInfoData siteOnePaymentData = new SiteOnePaymentInfoData();
			siteOnePaymentData.setPaymentType(PAYMENTTYPE_TWO);
			cartData.setSiteOnePaymentInfoData(siteOnePaymentData);
		}
		final List<String> showVoucherList = new ArrayList<>();
		if (null != cartData.getAppliedVouchers())
		{
			showVoucherList.addAll(cartData.getAppliedVouchers());
		}
		final String redirectUrl = validateCheckoutCart(cartData, redirectAttributes);

		if (null != redirectUrl)
		{
			return redirectUrl;
		}
		if (cartData.getEntries() != null && !cartData.getEntries().isEmpty())
		{
			for (final OrderEntryData entry : cartData.getEntries())
			{
				final String productCode = entry.getProduct().getCode();
				getSiteOneProductPromotionsPopulator().populate(getProductService().getProductForCode(productCode),
						entry.getProduct());
				if (null != entry.getProduct().getCouponCode() && showVoucherList.contains(entry.getProduct().getCouponCode()))
				{
					showVoucherList.remove(entry.getProduct().getCouponCode());
				}

			}
		}
		if(!CollectionUtils.isEmpty(cartData.getPickupAndDeliveryEntries()) && !CollectionUtils.isEmpty(cartData.getShippingOnlyEntries()))
		{
			final Map<String, Boolean> fulfilmentStatus = b2bCheckoutFacade.populateFulfilmentStatus(cartData);
			b2bCheckoutFacade.populateFreights(cartData, fulfilmentStatus);
		}
		model.addAttribute("showVoucherList", showVoucherList);
		model.addAttribute("guestUsers", getSessionService().getAttribute("guestUser"));
		model.addAttribute("cartData", cartData);
		model.addAttribute("allItems", cartData.getEntries());
		model.addAttribute("deliveryAddress", cartData.getDeliveryAddress());
		model.addAttribute(VOUCHER_FORM, new VoucherForm());
		model.addAttribute("isReturning", "returning");
		model.addAttribute("paymentInfo", cartData.getSiteOnePaymentInfoData());
		
		model.addAttribute("taxCA", ((null != getSessionService().getAttribute("taxCA")) ? getSessionService().getAttribute("taxCA") : null));
		
		storeCmsPageInModel(model, getContentPageForLabelOrId("orderSummaryPage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("orderSummaryPage"));
		getSessionService().setAttribute("orderGuestContactPerson", cartData.getGuestContactPerson());
		//GlobalMessages.addErrorMessage(model, "checkout.orderreview.pricechanges");
		model.addAttribute("metaRobots", "noindex,nofollow");
		setCheckoutStepLinksForModel(model, getCheckoutStep());
		return ControllerConstants.Views.Pages.MultiStepCheckout.OrderSummaryPage;

	}

	@RequestMapping(value = "/placeOrder")
	public String placeOrder(final Model model, final RedirectAttributes redirectModel, final HttpServletRequest request)
			throws CMSItemNotFoundException, InvalidCartException, CommerceCartModificationException
	{
		final CartData cartData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getCheckoutCart();
		if(null != getSessionService().getAttribute("taxCA"))
		{
			getSessionService().removeAttribute("taxCA");
		}
		

		final String redirectUrl = validateCheckoutCart(cartData, redirectModel);

		if (null != redirectUrl)
		{
			return redirectUrl;
		}

		if (validateOrderForm(model, cartData))
		{
			return enterStep(model, redirectModel);
		}

		if (getSessionService().getAttribute("vaultToken") != null || getSessionService().getAttribute("globalTemporaryToken")!= null )
		{
			return placeOnlinePaymentOrder(model, cartData, redirectModel);
		}

		if (getSessionService().getAttribute("termsCode") != null)
		{
			final SiteOnePOAPaymentInfoData siteOnePaymentData = new SiteOnePOAPaymentInfoData();
			final String termsCode = getSessionService().getAttribute("termsCode");
			siteOnePaymentData.setPaymentType(PAYMENTTYPE_ONE);
			siteOnePaymentData.setTermsCode(termsCode);
			cartData.setSiteOnePOAPaymentInfoData(siteOnePaymentData);
			return placePOAPaymentOrder(model, cartData);
		}

		if (null != getSessionService().getAttribute(SiteoneFacadesConstants.ASM_SESSION_PARAMETER))
		{
			final B2BCustomerModel b2bCustomer = (B2BCustomerModel) userService.getCurrentUser();
			final List<EmployeeModel> agentList = getAsmAgentRetrieveDao().getAgentPk(b2bCustomer.getPk().toString());
			if (!agentList.isEmpty())
			{
				((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).updateASMAgentForOrder(agentList.get(0));
			}
		}

		final AbstractOrderData orderData;
		try
		{
			orderData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).placeOrder();
			orderData.setAppliedVouchers(cartData.getAppliedVouchers());
			getSessionService().setAttribute("orderCodeforConfirmation", orderData.getCode());

		}
		catch (final EntityValidationException e)
		{
			LOG.error("Failed to place Order", e);
			GlobalMessages.addErrorMessage(model, e.getLocalizedMessage());
			return enterStep(model, redirectModel);
		}
		catch (final Exception e)
		{
			LOG.error("Failed to place Order", e);
			GlobalMessages.addErrorMessage(model, "checkout.placeOrder.failed");
			return enterStep(model, redirectModel);
		}
		model.addAttribute("isReturning", "returning");
		return redirectToOrderConfirmationPage(orderData);
	}

	protected String placePOAPaymentOrder(final Model model, final CartData cartData)
	{
		final SiteOnePOAPaymentInfoData siteOnePaymentData;
		final String termsCode = getSessionService().getAttribute("termsCode");
		siteOnePaymentData = cartData.getSiteOnePOAPaymentInfoData();
		getSessionService().removeAttribute("termsCode");

		if (null != getSessionService().getAttribute(SiteoneFacadesConstants.ASM_SESSION_PARAMETER))
		{
			final B2BCustomerModel b2bCustomer = (B2BCustomerModel) userService.getCurrentUser();
			final List<EmployeeModel> agentList = getAsmAgentRetrieveDao().getAgentPk(b2bCustomer.getPk().toString());
			if (!agentList.isEmpty())
			{
				((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).updateASMAgentForOrder(agentList.get(0));
			}
		}

		AbstractOrderData orderData;
		try
		{
			orderData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).placeOrderWithPOAPayment(siteOnePaymentData);
			orderData.setAppliedVouchers(cartData.getAppliedVouchers());
			getSessionService().setAttribute("orderCodeforConfirmation", orderData.getCode());
		}
		catch (final Exception e)
		{
			LOG.error("Failed to place Order", e);
			return REDIRECT_TO_PAYMENT_PAGE;
		}
		model.addAttribute("isReturning", "returning");
		return redirectToOrderConfirmationPage(orderData);
	}

	protected String placeOnlinePaymentOrder(final Model model, final CartData cartData,
			final RedirectAttributes redirectAttributes)
	{
		final PointOfServiceData pos = sessionService.getAttribute("sessionStore");
		final boolean flag = pos != null && pos.getHubStores() != null && pos.getHubStores().get(0) != null
				&& cartData.getOrderType() != null && cartData.getOrderType().equalsIgnoreCase("PARCEL_SHIPPING")
				&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("CCEnabledDCShippingBranches",
						pos.getHubStores().get(0));
		final String vaultToken = getSessionService().getAttribute("vaultToken");
		SiteOnePaymentInfoData siteOnePaymentData = null;
		if(!flag) 
		{
		if(null!=vaultToken)
		{
		 siteOnePaymentData = siteOnePaymentInfoUtil.processPaymentInfoData(vaultToken, cartData);
		getSessionService().removeAttribute("vaultToken");
		getSessionService().removeAttribute("ewalletData");
		if (null == siteOnePaymentData)
		{
			LOG.error("Invalid cayan response");
			GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.ERROR_MESSAGES_HOLDER,
					"text.payment.error.name.cayan.title");
			return CHECKOUT_PAGE;
		}
		}else {
			final String token = getSessionService().getAttribute("globalTemporaryToken");
			siteOnePaymentData = siteOneEwalletFacade.addGlobalPaymentDetails(token);
			getSessionService().removeAttribute("globalTemporaryToken");
		if (null == siteOnePaymentData )
		{
			LOG.error("Invalid Global Payment response");
			GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.ERROR_MESSAGES_HOLDER,
					"text.payment.error.name.cayan.title");
			return CHECKOUT_PAGE;
		}
		}
		}
		if (null != getSessionService().getAttribute(SiteoneFacadesConstants.ASM_SESSION_PARAMETER))
		{
			final B2BCustomerModel b2bCustomer = (B2BCustomerModel) userService.getCurrentUser();
			final List<EmployeeModel> agentList = getAsmAgentRetrieveDao().getAgentPk(b2bCustomer.getPk().toString());
			if (!agentList.isEmpty())
			{
				((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).updateASMAgentForOrder(agentList.get(0));
			}
		}

		AbstractOrderData orderData;
		try
		{
			orderData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).placeOrderWithOnlinePayment(siteOnePaymentData);
			orderData.setAppliedVouchers(cartData.getAppliedVouchers());
			getSessionService().setAttribute("orderCodeforConfirmation", orderData.getCode());
		}
		catch (final EntityValidationException e)
		{
			LOG.error("Failed to place Order", e);
			GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.ERROR_MESSAGES_HOLDER,
					"text.payment.error.name.cayan.title");
			if (siteOnePaymentData.getGlobalAuthToken() != null)
			{
				siteOneEwalletFacade.voidGlobalPaymentDetails(siteOnePaymentData);
			}
			return CHECKOUT_PAGE;
		}
		catch (final Exception e)
		{
			LOG.error("Failed to place Order", e);
			GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.ERROR_MESSAGES_HOLDER,
					"text.payment.error.name.cayan.title");
			if (siteOnePaymentData.getGlobalAuthToken() != null)
			{
				siteOneEwalletFacade.voidGlobalPaymentDetails(siteOnePaymentData);
			}
			return CHECKOUT_PAGE;
		}
		model.addAttribute("isReturning", "returning");
		return redirectToOrderConfirmationPage(orderData);
	}

	protected String redirectToOrderConfirmationPage(final AbstractOrderData orderData)
	{
		return REDIRECT_URL_ORDER_CONFIRMATION;
	}

	protected String validateCheckoutCart(final CartData checkoutCart, final RedirectAttributes redirectAttributes)
	{
		if (!b2bCheckoutFacade.isCartValidForCheckout(checkoutCart))
		{
			GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.ERROR_MESSAGES_HOLDER,
					"cart.checkout.disable.message");
			LOG.info("User is not able to checkout beacause of recently changed store");
			return REDIRECT_TO_CART_PAGE;
		}

		if (!b2bCheckoutFacade.isPosValidForCheckout(checkoutCart))
		{
			GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.INFO_MESSAGES_HOLDER, "checkout.multi.pos.invalid");
			LOG.info("User is not able to checkout beacause of recently changed Store");
			return REDIRECT_TO_CART_PAGE;
		}

		if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
		{
			if (!b2bCheckoutFacade.isAccountValidForCheckout(checkoutCart))
			{
				GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.INFO_MESSAGES_HOLDER,
						"checkout.error.invalid.shipto");
				LOG.info("User is not able to checkout beacause of recently changed Account");
				return REDIRECT_TO_CART_PAGE;
			}
			if (checkoutCart.getContactPerson() == null)
			{
				GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.INFO_MESSAGES_HOLDER,
						"checkout.multi.contactPerson.notprovided");
				return CHECKOUT_PAGE;
			}
		}
		else
		{
			if (checkoutCart.getGuestContactPerson() == null)
			{
				GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.INFO_MESSAGES_HOLDER,
						"checkout.multi.contactPerson.notprovided");
				return CHECKOUT_PAGE;
			}
		}

		if (checkoutCart.getOrderType() == null)
		{
			GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.INFO_MESSAGES_HOLDER,
					"checkout.multi.orderType.notprovided");
			return CHECKOUT_PAGE;
		}

		return null;
	}

	/**
	 * Validates the order form before to filter out invalid order states
	 *
	 * @param model
	 *           A spring Model
	 * @return True if the order form is invalid and false if everything is valid.
	 */
	protected boolean validateOrderForm(final Model model, final CartData cartData)
	{
		boolean invalid = false;

		/*
		 * if (!getCheckoutFacade().containsTaxValues()) { LOG.error(String.format(
		 * "Cart %s does not have any tax values, which means the tax cacluation was not properly done, placement of order can't continue"
		 * , cartData.getCode())); GlobalMessages.addErrorMessage(model, "checkout.error.tax.missing"); invalid = true; }
		 */

		if (!cartData.isCalculated())
		{
			LOG.error(
					String.format("Cart %s has a calculated flag of FALSE, placement of order can't continue", cartData.getCode()));
			GlobalMessages.addErrorMessage(model, "checkout.error.cart.notcalculated");
			invalid = true;
		}

		return invalid;
	}

	@PostMapping("/voucher/apply")
	public String applyVoucherAction(@Valid
	final VoucherForm form, final BindingResult bindingResult, final RedirectAttributes redirectAttributes,
			final HttpServletRequest request)
	{
		try
		{
			if (bindingResult.hasErrors())
			{
				redirectAttributes.addFlashAttribute("errorMsg",
						getMessageSource().getMessage("text.voucher.apply.invalid.error", null, getI18nService().getCurrentLocale()));
			}
			else
			{
				voucherFacade.applyVoucher(form.getVoucherCode().toUpperCase());
				final CartModel cartModel = cartService.getSessionCart();
				final int couponAppliedCount = cartModel.getAppliedCouponCodes().size();

				if (couponAppliedCount == 1)
				{
					redirectAttributes.addFlashAttribute("successMsg",
							getMessageSource().getMessage("text.voucher.apply.applied.success", new Object[]
							{ form.getVoucherCode() }, getI18nService().getCurrentLocale()));
				}
				else
				{
					if (siteOneCartFacade.releaseAppliedCouponCodes(cartModel, form.getVoucherCode()))
					{
						redirectAttributes.addFlashAttribute("errorMsg",
								getMessageSource().getMessage("text.voucher.no.more.apply.coupon.error", new Object[]
								{ form.getVoucherCode() }, getI18nService().getCurrentLocale()));
					}

				}
				((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).calculateTax();
			}
		}
		catch (final VoucherOperationException e)
		{
			redirectAttributes.addFlashAttribute(VOUCHER_FORM, form);
			redirectAttributes.addFlashAttribute("errorMsg",
					getMessageSource().getMessage(e.getMessage(), null,
							getMessageSource().getMessage("text.voucher.apply.invalid.error", null, getI18nService().getCurrentLocale()),
							getI18nService().getCurrentLocale()));
			if (LOG.isDebugEnabled())
			{
				LOG.debug(e.getMessage(), e);
			}

		}

		return getReturnRedirectUrl(request);
	}

	@PostMapping("/voucher/remove")
	public String removeVoucher(@Valid
	final VoucherForm form, final RedirectAttributes redirectModel)
	{
		try
		{
			voucherFacade.releaseVoucher(form.getVoucherCode());
			((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).calculateTax();
		}
		catch (final VoucherOperationException e)
		{
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "text.voucher.release.error",
					new Object[]
					{ form.getVoucherCode() });
			if (LOG.isDebugEnabled())
			{
				LOG.debug(e.getMessage(), e);
			}

		}
		return REDIRECT_TO_ORDER_PAGE;
	}


	@GetMapping("/next")
	@Override
	public String next(final RedirectAttributes redirectAttributes)
	{
		return getCheckoutStep().nextStep();
	}

	@GetMapping("/back")
	@Override
	public String back(final RedirectAttributes redirectAttributes)
	{
		return getCheckoutStep().previousStep();
	}

	protected CheckoutStep getCheckoutStep()
	{
		return getCheckoutStep(SUMMARY);
	}

	protected String getReturnRedirectUrl(final HttpServletRequest request)
	{
		final String referer = request.getHeader("Referer");
		if (referer != null && !referer.isEmpty())
		{
			return REDIRECT_PREFIX + referer;
		}
		return REDIRECT_PREFIX + '/';
	}

	public SiteOneProductPromotionsPopulator getSiteOneProductPromotionsPopulator()
	{
		return siteOneProductPromotionsPopulator;
	}

	public void setSiteOneProductPromotionsPopulator(final SiteOneProductPromotionsPopulator siteOneProductPromotionsPopulator)
	{
		this.siteOneProductPromotionsPopulator = siteOneProductPromotionsPopulator;
	}

	public ProductService getProductService()
	{
		return productService;
	}

	public void setProductService(final ProductService productService)
	{
		this.productService = productService;
	}

	public ASMAgentRetrieveDao getAsmAgentRetrieveDao()
	{
		return asmAgentRetrieveDao;
	}

	public void setAsmAgentRetrieveDao(final ASMAgentRetrieveDao asmAgentRetrieveDao)
	{
		this.asmAgentRetrieveDao = asmAgentRetrieveDao;
	}
}
