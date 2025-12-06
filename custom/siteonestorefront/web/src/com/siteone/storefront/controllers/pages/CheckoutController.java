/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorfacades.flow.impl.SessionOverrideCheckoutFlowFacade;
import de.hybris.platform.acceleratorservices.controllers.page.PageType;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.checkout.steps.CheckoutStep;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.checkout.steps.AbstractCheckoutStepController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.acceleratorstorefrontcommons.forms.GuestRegisterForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.validation.GuestRegisterValidator;
import de.hybris.platform.acceleratorstorefrontcommons.security.AutoLoginStrategy;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.commercefacades.coupon.data.CouponData;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.order.OrderFacade;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.product.data.PromotionResultData;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.util.ResponsiveUtils;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.kount.service.impl.DefaultSiteOneKountService;
import com.siteone.core.order.services.SiteOneOrderService;
import com.siteone.facades.cart.SiteOneCartFacade;
import com.siteone.facades.populators.SiteOneProductPromotionsPopulator;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.storefront.controllers.ControllerConstants;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.core.constants.SiteoneCoreConstants;


/**
 * CheckoutController
 */
@Controller
// FRAMEWORK_UPDATE - TODO - AntPathMatcher was replaced with PathPatternParser as the new default path parser in Spring 6. Adjust this path to the new matching rules or re-enable deprecated AntPathMatcher. Consult "Adapting to PathPatternParser new default URL Matcher" JDK21 Upgrade Step in SAP Help documentation.
@RequestMapping(value = "/**/checkout")
public class CheckoutController extends AbstractCheckoutStepController
{
	private static final Logger LOG = Logger.getLogger(CheckoutController.class);
	/**
	 * We use this suffix pattern because of an issue with Spring 3.1 where a Uri value is incorrectly extracted if it
	 * contains on or more '.' characters. Please see https://jira.springsource.org/browse/SPR-6164 for a discussion on
	 * the issue and future resolution.
	 */
	private static final String ORDER_CODE_PATH_VARIABLE_PATTERN = "{orderCode:.*}";

	private static final String CHECKOUT_ORDER_CONFIRMATION_CMS_PAGE_LABEL = "orderConfirmation";
	private static final String CONTINUE_URL_KEY = "continueUrl";
	private static final String ORDERCONFIRMATION = "orderConfirmation";
	private static final String ALGONOMYRECOMMFLAG = "AlgonomyProductRecommendationEnable";
	@Resource(name = "productFacade")
	private ProductFacade productFacade;

	@Resource(name = "orderFacade")
	private OrderFacade orderFacade;
	
	@Resource(name = "priceDataFactory")
	private PriceDataFactory priceDataFactory;
	
	@Resource(name = "siteOneOrderService")
	private SiteOneOrderService siteOneOrderService;

	@Resource(name = "checkoutFacade")
	private CheckoutFacade checkoutFacade;

	@Resource(name = "guestRegisterValidator")
	private GuestRegisterValidator guestRegisterValidator;

	@Resource(name = "autoLoginStrategy")
	private AutoLoginStrategy autoLoginStrategy;

	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;

	@Resource(name = "siteOneKountService")
	private DefaultSiteOneKountService defaultSiteOneKountService;

	@Resource(name = "cartFacade")
	private SiteOneCartFacade siteOneCartFacade;

	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;

	@Resource(name = "productPromotionsPopulator")
	private SiteOneProductPromotionsPopulator siteOneProductPromotionsPopulator;

	@Resource(name = "productService")
	private ProductService productService;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
	
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;


	@ExceptionHandler(ModelNotFoundException.class)
	public String handleModelNotFoundException(final ModelNotFoundException exception, final HttpServletRequest request)
	{
		request.setAttribute("message", exception.getMessage());
		return FORWARD_PREFIX + "/404";
	}

	@GetMapping
	public String checkout(final RedirectAttributes redirectModel)
	{
		if (getCheckoutFlowFacade().hasValidCart())
		{
			if (validateCart(redirectModel))
			{
				return REDIRECT_PREFIX + "/cart";
			}
			else
			{
				checkoutFacade.prepareCartForCheckout();
				return getCheckoutRedirectUrl();
			}
		}

		LOG.info("Missing, empty or unsupported cart");

		// No session cart or empty session cart. Bounce back to the cart page.
		return REDIRECT_PREFIX + "/cart";
	}

	@GetMapping("/orderConfirmation")
	@RequireHardLogIn
	@Override
	public String enterStep(final Model model, final RedirectAttributes redirectModel)
			throws CMSItemNotFoundException, CommerceCartModificationException
	{
		final String orderCode = getSessionService().getAttribute("orderCodeforConfirmation");
		try
		{
			defaultSiteOneKountService.updateKountDetails(orderCode);
		}
		catch (final Exception genericException)
		{
			LOG.error("Exception occured in processOrderCode - updateKountDetails " + genericException.getMessage());
		}
		SessionOverrideCheckoutFlowFacade.resetSessionOverrides();
		return processOrderCode(orderCode, model, redirectModel);
	}


	@PostMapping("/orderConfirmation")
	public String orderConfirmation(final GuestRegisterForm form, final BindingResult bindingResult, final Model model,
			final HttpServletRequest request, final HttpServletResponse response, final RedirectAttributes redirectModel)
			throws CMSItemNotFoundException
	{
		getGuestRegisterValidator().validate(form, bindingResult);
		return processRegisterGuestUserRequest(form, bindingResult, model, request, response, redirectModel);
	}

	protected String processRegisterGuestUserRequest(final GuestRegisterForm form, final BindingResult bindingResult,
			final Model model, final HttpServletRequest request, final HttpServletResponse response,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		if (bindingResult.hasErrors())
		{
			GlobalMessages.addErrorMessage(model, "form.global.error");
			return processOrderCode(form.getOrderCode(), model, redirectModel);
		}
		try
		{
			getCustomerFacade().changeGuestToCustomer(form.getPwd(), form.getOrderCode());
			getAutoLoginStrategy().login(getCustomerFacade().getCurrentCustomer().getUid(), form.getPwd(), request, response);
			getSessionService().removeAttribute(WebConstants.ANONYMOUS_CHECKOUT);
		}
		catch (final DuplicateUidException e)
		{
			// User already exists
			LOG.warn("guest registration failed: " + e);
			model.addAttribute(new GuestRegisterForm());
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER,
					"guest.checkout.existingaccount.register.error", new Object[]
					{ form.getUid() });
			return REDIRECT_PREFIX + request.getHeader("Referer");
		}

		return REDIRECT_PREFIX + "/";
	}

	protected String processOrderCode(final String orderCode, final Model model, final RedirectAttributes redirectModel)
			throws CMSItemNotFoundException
	{
		final OrderData orderDetails;
		final OrderModel orderModel;
		final List<String> showVoucherList = new ArrayList<>();
		setCheckoutStepLinksForModel(model, getCheckoutStep());
		try
		{
			orderDetails = orderFacade.getOrderDetailsForCode(orderCode);
			orderModel = siteOneOrderService.getOrderForCode(orderCode);
		
			for (final AbstractOrderEntryModel entry : orderModel.getEntries()) {
			if (orderDetails.getTotalPrice() != null) {
			 BigDecimal subTotal = BigDecimal.valueOf(orderDetails.getSubTotal().getValue().doubleValue());
		    BigDecimal totalPrice = BigDecimal.valueOf(orderDetails.getTotalPriceWithTax().getValue().doubleValue());
		    BigDecimal bigBagPrice = (entry.getBigBagInfo() != null 
		          && Boolean.TRUE.equals(entry.getBigBagInfo().getIsChecked()) 
		          && entry.getBigBagInfo().getLocalTotal() != null) 
		      ? entry.getBigBagInfo().getLocalTotal() 
		      : BigDecimal.ZERO;
		    
		    orderDetails.setSubTotal(createPrice(orderModel, subTotal.add(bigBagPrice).doubleValue()));
		    orderDetails.setTotalPriceWithTax(createPrice(orderModel, totalPrice.add(bigBagPrice).doubleValue()));		}
			}			

			if (null != orderDetails.getAppliedVouchers())
			{
				showVoucherList.addAll(orderDetails.getAppliedVouchers());
			}
		}
		catch (final UnknownIdentifierException e)
		{
			LOG.error(e.getMessage(), e);
			LOG.warn("Attempted to load an order confirmation that does not exist or is not visible. Redirect to home page.");
			return REDIRECT_PREFIX + ROOT;
		}


		if (orderDetails.getEntries() != null && !orderDetails.getEntries().isEmpty())
		{
			final StringBuilder availabiltyStatus = new StringBuilder();
			int count = 1;
			for (final OrderEntryData entry : orderDetails.getEntries())
			{
				final String productCode = entry.getProduct().getCode();
				/* SE-9752 analytics fix starts */
				if (null != entry.getProduct().getIsEligibleForBackorder() && entry.getProduct().getIsEligibleForBackorder())
				{
					if (null != entry.getProduct().getIsBackorderAndShippable() && entry.getProduct().getIsBackorderAndShippable()
							&& (orderDetails.getOrderType()!=null && orderDetails.getOrderType().equalsIgnoreCase("PARCEL_SHIPPING")))
					{
						availabiltyStatus.append("regular");
					}
					else
					{
						availabiltyStatus.append("back order");
					}
				}
				else
				{
					availabiltyStatus.append("regular");
				}
				if (orderDetails.getEntries().size() > count)
				{
					availabiltyStatus.append("|");
					count++;
				}
				getSiteOneProductPromotionsPopulator().populate(getProductService().getProductForCode(productCode), entry.getProduct());

				if (null != entry.getProduct().getCouponCode() && showVoucherList.contains(entry.getProduct().getCouponCode()))
				{
					showVoucherList.remove(entry.getProduct().getCouponCode());

				}
				if(null != entry.getDeliveryPointOfService() && 
					null != entry.getProduct().getPickupHomeStoreInfo() && 
					null != entry.getProduct().getPickupNearbyStoreInfo() && 
					null != entry.getProduct().getDeliveryStoreInfo() && 
					null != entry.getProduct().getShippingStoreInfo() &&
					((!entry.getDeliveryPointOfService().getStoreId().equalsIgnoreCase(entry.getProduct().getPickupHomeStoreInfo().getStoreId())) || 
					(!entry.getDeliveryPointOfService().getStoreId().equalsIgnoreCase(entry.getProduct().getPickupNearbyStoreInfo().getStoreId())) || 
					(!entry.getDeliveryPointOfService().getStoreId().equalsIgnoreCase(entry.getProduct().getDeliveryStoreInfo().getStoreId())) ||
					(!entry.getDeliveryPointOfService().getStoreId().equalsIgnoreCase(entry.getProduct().getShippingStoreInfo().getStoreId()))))
				{
					siteOneCartFacade.updateFullfillmentMessage(entry.getProduct(), entry.getDeliveryPointOfService());
					siteOneCartFacade.updateShippingStore(entry.getProduct(), entry.getDeliveryPointOfService());
				}

			}
			model.addAttribute("availabiltyStatus", availabiltyStatus.toString());
		}
		//uom enhancements changes
		if (CollectionUtils.isNotEmpty(orderDetails.getUnconsignedEntries()))
		{
			siteOneProductFacade.getPriceUpdateForHideUomEntry(orderDetails, false);
		}	

		final Map<String, String> shippingFee = siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping("DCShippingFeeBranches");
		if(orderDetails.getPointOfService() != null)
		{
			if(shippingFee.containsKey(orderDetails.getPointOfService().getStoreId()))
			{
				model.addAttribute("isDCShippingOrder", true);
			}
		}

		model.addAttribute("showVoucherList", showVoucherList);
		model.addAttribute("orderCode", orderCode);
		model.addAttribute("orderData", orderDetails);
		model.addAttribute("allItems", orderDetails.getEntries());
		model.addAttribute("deliveryAddress", orderDetails.getDeliveryAddress());
		model.addAttribute("deliveryMode", orderDetails.getDeliveryMode());
		model.addAttribute("paymentInfo", orderDetails.getPaymentInfo());
		model.addAttribute("pageType", PageType.ORDERCONFIRMATION.name());
		model.addAttribute("isReturning", "returning");
		model.addAttribute("cartAge", Long.valueOf(siteOneCartFacade.getCartAge()));

		final Optional<PromotionResultData> optional = orderDetails.getAppliedOrderPromotions().stream()
				.filter(x -> CollectionUtils.isNotEmpty(x.getGiveAwayCouponCodes())).findFirst();
		if (optional.isPresent())
		{
			final PromotionResultData giveAwayCouponPromotion = optional.get();
			final List<CouponData> giftCoupons = giveAwayCouponPromotion.getGiveAwayCouponCodes();
			model.addAttribute("giftCoupon", giftCoupons.get(0));

			orderDetails.getAppliedOrderPromotions().removeIf(x -> CollectionUtils.isNotEmpty(x.getGiveAwayCouponCodes()));
		}

		processEmailAddress(model, orderDetails);

		final String continueUrl = (String) getSessionService().getAttribute(WebConstants.CONTINUE_URL);
		model.addAttribute(CONTINUE_URL_KEY, (continueUrl != null && !continueUrl.isEmpty()) ? continueUrl : ROOT);

		final AbstractPageModel cmsPage = getContentPageForLabelOrId(CHECKOUT_ORDER_CONFIRMATION_CMS_PAGE_LABEL);
		storeCmsPageInModel(model, cmsPage);
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CHECKOUT_ORDER_CONFIRMATION_CMS_PAGE_LABEL));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		final BaseSiteModel basesite = getBaseSiteService().getCurrentBaseSite();
		Boolean algonomyRecommendation = ((basesite.getUid().equalsIgnoreCase(SiteoneintegrationConstants.BASESITE_CA)) ?
				Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(SiteoneCoreConstants.ALGONOMYRECOMMFLAG_CA)) : 
					Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(ALGONOMYRECOMMFLAG)));
		model.addAttribute("algonomyRecommendationEnabled", algonomyRecommendation);
		if (ResponsiveUtils.isResponsive())
		{
			return getViewForPage(model);
		}

		return ControllerConstants.Views.Pages.Checkout.CheckoutConfirmationPage;
	}
	
	protected PriceData createPrice(final AbstractOrderModel source, final Double val)
	{
		if (source == null)
		{
			throw new IllegalArgumentException("source order must not be null");
		}

		final CurrencyModel currency = source.getCurrency();
		if (currency == null)
		{
			throw new IllegalArgumentException("source order currency must not be null");
		}

		// Get double value, handle null as zero
		final double priceValue = val != null ? val.doubleValue() : 0d;

		return priceDataFactory.create(PriceDataType.BUY, BigDecimal.valueOf(priceValue), currency);
	}

	protected void processEmailAddress(final Model model, final OrderData orderDetails)
	{
		String uid = null;

		if (!orderDetails.isGuestCustomer())
		{
			uid = orderDetails.getUser().getUid();
		}
		else
		{
			uid = orderDetails.getGuestContactPerson().getEmail();
		}

		model.addAttribute("email", uid);
	}

	protected CheckoutStep getCheckoutStep()
	{
		return getCheckoutStep(ORDERCONFIRMATION);
	}

	protected GuestRegisterValidator getGuestRegisterValidator()
	{
		return guestRegisterValidator;
	}

	protected AutoLoginStrategy getAutoLoginStrategy()
	{
		return autoLoginStrategy;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.checkout.steps.CheckoutStepController#back(org.
	 * springframework.web.servlet.mvc.support.RedirectAttributes)
	 */
	@Override
	public String back(final RedirectAttributes redirectAttributes)
	{
		// YTODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.checkout.steps.CheckoutStepController#next(org.
	 * springframework.web.servlet.mvc.support.RedirectAttributes)
	 */
	@Override
	public String next(final RedirectAttributes redirectAttributes)
	{
		// YTODO Auto-generated method stub
		return null;
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


	@Override
	public BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}

}
