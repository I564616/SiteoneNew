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

import de.hybris.platform.acceleratorfacades.csv.CsvFacade;
import de.hybris.platform.acceleratorfacades.flow.impl.SessionOverrideCheckoutFlowFacade;
import de.hybris.platform.acceleratorservices.controllers.page.PageType;
import de.hybris.platform.acceleratorservices.enums.CheckoutFlowEnum;
import de.hybris.platform.acceleratorservices.enums.CheckoutPciOptionEnum;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.ResourceBreadcrumbBuilder;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractCartPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.acceleratorstorefrontcommons.forms.LoginForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.SaveCartForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.UpdateQuantityForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.VoucherForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.validation.SaveCartFormValidator;
import de.hybris.platform.acceleratorstorefrontcommons.util.XSSFilterUtil;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.bag.data.BagInfoData;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.SaveCartFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.CartModificationData;
import de.hybris.platform.commercefacades.order.data.CommerceSaveCartParameterData;
import de.hybris.platform.commercefacades.order.data.CommerceSaveCartResultData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.quote.data.QuoteData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.voucher.VoucherFacade;
import de.hybris.platform.commercefacades.voucher.exceptions.VoucherOperationException;
import de.hybris.platform.commerceservices.order.CommerceCartCalculationStrategy;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.order.CommerceSaveCartException;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.core.enums.QuoteState;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.order.CartService;
import de.hybris.platform.order.DeliveryModeService;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.order.exceptions.CalculationException;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.util.Config;
import de.hybris.platform.variants.model.GenericVariantProductModel;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.enums.OrderTypeEnum;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.services.RegulatoryStatesCronJobService;
import com.siteone.core.services.SiteOnePromotionSourceRuleService;
import com.siteone.core.services.SiteOneStockLevelService;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.facade.CartToggleResponseData;
import com.siteone.facade.StoreLevelStockInfoData;
import com.siteone.facade.ThresholdCheckResponseData;
import com.siteone.facades.cart.SiteOneCartFacade;
import com.siteone.facades.customer.price.SiteOneCartCspPriceData;
import com.siteone.facades.exceptions.PdfNotAvailableException;
import com.siteone.facades.exceptions.ServiceUnavailableException;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.savedList.SiteoneSavedListFacade;
import com.siteone.facades.savedList.data.SavedListData;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.storefront.controllers.ControllerConstants;
import com.siteone.storefront.forms.SiteoneCartUploadForm;
import com.siteone.storefront.validator.SiteOneCSVUploadListValidator;
import com.siteone.integration.constants.SiteoneintegrationConstants;


/**
 * Controller for cart page
 */
@Controller
@RequestMapping(value = "/cart")
public class CartPageController extends AbstractCartPageController
{
	public static final String SHOW_CHECKOUT_STRATEGY_OPTIONS = "storefront.show.checkout.flows";
	public static final String ERROR_MSG_TYPE = "errorMsg";
	public static final String SUCCESSFUL_MODIFICATION_CODE = "success";
	public static final String VOUCHER_FORM = "voucherForm";
	public static final String SITE_QUOTES_ENABLED = "site.quotes.enabled.";

	private static final String REDIRECT_CART_URL = REDIRECT_PREFIX + "/cart";
	private static final String REDIRECT_QUOTE_EDIT_URL = REDIRECT_PREFIX + "/quote/%s/edit/";
	private static final String REDIRECT_QUOTE_VIEW_URL = REDIRECT_PREFIX + "/my-account/my-quotes/%s/";
	private static final String CART_CREATION_TIME_COOKIE = "cartCreationTime";
	private static final String ALGONOMYRECOMMFLAG = "AlgonomyProductRecommendationEnable";
	private static final String IMPORT_CSV_FILE_MAX_SIZE_BYTES_KEY = "import.csv.file.max.size.bytes";
	private static final String QUANTITY_ATTR = "quantity";
	private static final String CACHE_CONTROL = "Cache-Control";
	private static final String REVALIDATE = "must-revalidate, post-check=0, pre-check=0";
	private static final String PAGINATION_NUMBER_OF_RESULTS_COUNT = "pagination.number.results.count";
	private static final String PRAGMA = "Pragma";
	private static final String CSVCONTENT_ATTR = "csvContent";
	private static final String UPLOADCARTEMPTY = "uploadCartEmpty";
	private static final String PUBLIC = "public";
	private static final String CONTENT_DISPOSITION = "Content-Disposition";
	private static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
	private static final String EXCEPTION_PDF = "Exception while creating PDF : ";
	private static final Logger LOG = Logger.getLogger(CartPageController.class);

	@Resource(name = "siteOnePromotionSourceRuleService")
	private SiteOnePromotionSourceRuleService siteOnePromotionSourceRuleService;

	@Resource(name = "productService")
	private ProductService productService;

	@Resource(name = "siteOneCSVUploadListValidator")
	private SiteOneCSVUploadListValidator siteOneCSVUploadListValidator;

	@Resource(name = "productConverter")
	private Converter<ProductModel, ProductData> productConverter;

	@Resource(name = "productConfiguredPopulator")
	private ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator;

	@Resource(name = "simpleBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder resourceBreadcrumbBuilder;

	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;

	@Resource(name = "productVariantFacade")
	private ProductFacade productFacade;

	@Resource(name = "saveCartFacade")
	private SaveCartFacade saveCartFacade;

	@Resource(name = "saveCartFormValidator")
	private SaveCartFormValidator saveCartFormValidator;

	@Resource(name = "csvFacade")
	private CsvFacade csvFacade;

	@Resource(name = "voucherFacade")
	private VoucherFacade voucherFacade;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Resource(name = "cartService")
	private CartService cartService;

	@Resource(name = "cartFacade")
	private SiteOneCartFacade siteOneCartFacade;

	@Resource(name = "commerceCartCalculationStrategy")
	private CommerceCartCalculationStrategy commerceCartCalculationStrategy;

	@Resource(name = "siteOneStockLevelService")
	private SiteOneStockLevelService siteOneStockLevelService;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "storeFinderService")
	private SiteOneStoreFinderService storeFinderService;

	@Resource(name = "cartFacade")
	private CartFacade cartFacade;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "siteoneSavedListFacade")
	private SiteoneSavedListFacade siteoneSavedListFacade;

	@Resource(name = "regulatoryStatesCronJobService")
	private RegulatoryStatesCronJobService regulatoryStatesCronJobService;

	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;


	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;

	@Resource(name = "deliveryModeService")
	private DeliveryModeService deliveryModeService;

	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
	
	@Resource(name = "userFacade")
	private UserFacade userFacade;

	@Resource(name = "cartConverter")
	private Converter<CartModel, CartData> cartConverter;

	@ModelAttribute("showCheckoutStrategies")
	public boolean isCheckoutStrategyVisible()
	{
		return getSiteConfigService().getBoolean(SHOW_CHECKOUT_STRATEGY_OPTIONS, false);
	}

	@GetMapping
	public String showCart(final Model model) throws CMSItemNotFoundException
	{
		if (userService.isAnonymousUser(userService.getCurrentUser()))
		{
			model.addAttribute("isGuestCheckoutEnabled", ((SiteOneCartFacade) cartFacade).isGuestCheckoutEnabled());
		}
		return prepareCartUrl(model);
	}

	protected String prepareCartUrl(final Model model) throws CMSItemNotFoundException
	{
		/*
		 * final Optional<String> quoteEditUrl = getQuoteUrl(); if (quoteEditUrl.isPresent()) { return quoteEditUrl.get();
		 * } else {
		 */
		prepareDataForPage(model);
		model.addAttribute("csvFileMaxSize", getSiteConfigService().getLong(IMPORT_CSV_FILE_MAX_SIZE_BYTES_KEY, 0));
		final BaseSiteModel basesite = getBaseSiteService().getCurrentBaseSite();
		Boolean algonomyRecommendation = ((basesite.getUid().equalsIgnoreCase(SiteoneintegrationConstants.BASESITE_CA)) ?
				Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(SiteoneCoreConstants.ALGONOMYRECOMMFLAG_CA)) : 
					Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(ALGONOMYRECOMMFLAG)));
		
		model.addAttribute("algonomyRecommendationEnabled", algonomyRecommendation);
		return ControllerConstants.Views.Pages.Cart.CartPage;
		//}
	}

	protected Optional<String> getQuoteUrl()
	{
		final QuoteData quoteData = getCartFacade().getSessionCart().getQuoteData();

		return quoteData != null ? (QuoteState.BUYER_OFFER.equals(quoteData.getState())
				? Optional.of(String.format(REDIRECT_QUOTE_VIEW_URL, urlEncode(quoteData.getCode())))
				: Optional.of(String.format(REDIRECT_QUOTE_EDIT_URL, urlEncode(quoteData.getCode())))) : Optional.empty();
	}


	/** Guest User */

	@RequestMapping(value = "/guestUser")
	public @ResponseBody String guestUser(final ModelMap model, final String guestUser,
			final RedirectAttributes redirectAttributes)
			throws CMSItemNotFoundException, InvalidCartException, CommerceCartModificationException
	{
		getSessionService().setAttribute("guestUser", guestUser);
		model.addAttribute("isGuest", Boolean.TRUE);
		return ControllerConstants.Views.Pages.Cart.CartPage;
	}

	@GetMapping("/downloadCartCSVFile")
	public void createInvoicePDF(final Model model, final RedirectAttributes redirectModel, final HttpServletResponse response,
			final HttpServletRequest request) throws CMSItemNotFoundException
	{
		try
		{
			final byte[] csvContent = SiteOneInvoiceCSVUtils.createUploadListCSV();
			response.setHeader("Expires", "0");
			response.setHeader(CACHE_CONTROL, REVALIDATE);
			response.setHeader(PRAGMA, PUBLIC);

			response.setHeader(CONTENT_DISPOSITION, "attachment;filename=SiteOneUploadCartDownloadTemplate.csv");
			response.setContentType(APPLICATION_OCTET_STREAM);
			response.setContentLength(csvContent.length);
			response.getOutputStream().write(csvContent);
			response.getOutputStream().flush();

		}
		catch (final ServiceUnavailableException serviceUnavailableException)
		{
			LOG.error("Not able to access pdf", serviceUnavailableException);
		}
		catch (final PdfNotAvailableException pdfNotAvailableException)
		{
			LOG.error("Pdf is currently not available", pdfNotAvailableException);
		}
		catch (final IOException e)
		{
			LOG.error(EXCEPTION_PDF + e);
			LOG.error(e.getMessage(), e);
		}
	}

	@PostMapping("/uploadcartcsv")
	public @ResponseBody String importAddToCart(final Model model, @ModelAttribute("siteoneCartUploadForm")
	final SiteoneCartUploadForm siteoneCartUploadForm, final RedirectAttributes redirectModel, final BindingResult bindingResult,
			final HttpServletResponse response, final HttpServletRequest request)
			throws CommerceCartModificationException, IOException
	{
		final List<String> productCodes = new ArrayList<>();
		List<String> failedProducts = new ArrayList<>();
		LOG.error("Inside the method CSV file" + siteoneCartUploadForm.getCsvFile());
		if (siteoneCartUploadForm.getCsvFile() != null && !(siteoneCartUploadForm.getCsvFile().isEmpty()))
		{
			LOG.error("Inside the method CSV cart");
			siteOneCSVUploadListValidator.validate(siteoneCartUploadForm, bindingResult);
			final String csvContent = null;
			String entryCount = null;
			if (bindingResult.hasErrors())
			{
				final String errorMessage = getMessageSource().getMessage(bindingResult.getAllErrors().get(0).getCode(), null,
						getI18nService().getCurrentLocale());
				if (errorMessage.contains("The file uploaded is empty. Please select a new file and upload again."))
				{
					LOG.error("Inside the method empty cart");
					return UPLOADCARTEMPTY;
				}
				if (errorMessage.contains("The file uploaded exceeds 100 products limit. Please select a new file and upload again."))
				{
					LOG.error("Inside the method Exceeded cart");
					return "uploadCartExceeded";
				}
			}
			else
			{

				try (final InputStream inputStream = siteoneCartUploadForm.getCsvFile().getInputStream())
				{
					final StringBuilder listCode = siteoneSavedListFacade.createCartEntryFromUploadedCSV(inputStream);
					final String[] products = listCode.toString().split(";");
					LOG.error("products codes upload" + products);
					if (products.length > 0)
					{
						final String productsCodes = products[0].trim();
						if (productsCodes.equalsIgnoreCase("true"))
						{
							return "uploadCartExceeded";
						}
						if (productsCodes.equalsIgnoreCase("null"))
						{
							return UPLOADCARTEMPTY;
						}
						if (productsCodes.equalsIgnoreCase("null") || productsCodes.equals(""))
						{

							model.addAttribute(CSVCONTENT_ATTR, csvContent);
							return "uploadCartError";
						}
						if (StringUtils.isNotEmpty(products[1].trim()))
						{
							failedProducts = Arrays.asList(products[1].trim().toString().split(","));
						}
						if (StringUtils.isNotEmpty(products[2].trim()))
						{
							entryCount = products[2].trim();
						}
					}
					else
					{
						return UPLOADCARTEMPTY;
					}
				}

				catch (final IOException e)
				{
					if (LOG.isDebugEnabled())
					{
						LOG.debug(e.getMessage(), e);
					}

				}
				LOG.info("***********numberItemsInCart**********" + failedProducts);

			}

			return "uploadSuccess|" + entryCount + "|" + failedProducts;

		}
		return UPLOADCARTEMPTY;


	}

	/** CSP ajax call **/

	@RequestMapping(value = "/fetchCspOnload")
	@RequireHardLogIn
	public @ResponseBody SiteOneCartCspPriceData fetchCSP(@RequestParam(value = "sequenceNo")
	final String sequenceNo, @RequestParam(value = "sku")
	final String sku, final ModelMap model, final RedirectAttributes redirectAttributes)
			throws CMSItemNotFoundException, InvalidCartException, CommerceCartModificationException, CalculationException
	{

		final CartModel cartModel = cartService.getSessionCart();
		final SiteOneCartCspPriceData cspData = ((SiteOneCartFacade) cartFacade).fetchCSPOnLoad(cartModel, sku);
		cspData.setSequenceNo(sequenceNo);
		return cspData;
	}



	/**
	 * Handle the '/cart/checkout' request url. This method checks to see if the cart is valid before allowing the
	 * checkout to begin. Note that this method does not require the user to be authenticated and therefore allows us to
	 * validate that the cart is valid without first forcing the user to login. The cart will be checked again once the
	 * user has logged in.
	 *
	 * @return The page to redirect to
	 */
	@GetMapping("/checkout")
	@RequireHardLogIn
	public String cartCheck(final RedirectAttributes redirectModel) throws CommerceCartModificationException
	{
		SessionOverrideCheckoutFlowFacade.resetSessionOverrides();

		if (!getCartFacade().hasEntries())
		{
			LOG.info("Missing or empty cart");

			// No session cart or empty session cart. Bounce back to the cart page.
			return REDIRECT_CART_URL;
		}


		if (validateCart(redirectModel))
		{
			return REDIRECT_CART_URL;
		}

		// Redirect to the start of the checkout flow to begin the checkout process
		// We just redirect to the generic '/checkout' page which will actually select the checkout flow
		// to use. The customer is not necessarily logged in on this request, but will be forced to login
		// when they arrive on the '/checkout' page.
		return REDIRECT_PREFIX + "/checkout";
	}

	@GetMapping("/getProductVariantMatrix")
	public String getProductVariantMatrix(@RequestParam("productCode")
	final String productCode, @RequestParam(value = "readOnly", required = false, defaultValue = "false")
	final String readOnly, final Model model)
	{

		final ProductData productData = productFacade.getProductForCodeAndOptions(productCode,
				Arrays.asList(ProductOption.BASIC, ProductOption.CATEGORIES, ProductOption.VARIANT_MATRIX_BASE,
						ProductOption.VARIANT_MATRIX_PRICE, ProductOption.VARIANT_MATRIX_MEDIA, ProductOption.VARIANT_MATRIX_STOCK,
						ProductOption.VARIANT_MATRIX_URL));

		model.addAttribute("product", productData);
		model.addAttribute("readOnly", Boolean.valueOf(readOnly));

		return ControllerConstants.Views.Fragments.Cart.ExpandGridInCart;
	}

	// This controller method is used to allow the site to force the visitor through a specified checkout flow.
	// If you only have a static configured checkout flow then you can remove this method.
	@GetMapping("/checkout/select-flow")
	@RequireHardLogIn
	public String initCheck(@RequestParam(value = "flow", required = false)
	final String flow, @RequestParam(value = "pci", required = false)
	final String pci) throws CommerceCartModificationException
	{
		SessionOverrideCheckoutFlowFacade.resetSessionOverrides();

		if (!getCartFacade().hasEntries())
		{
			LOG.info("Missing or empty cart");

			// No session cart or empty session cart. Bounce back to the cart page.
			return REDIRECT_CART_URL;
		}

		// Override the Checkout Flow setting in the session
		if (StringUtils.isNotBlank(flow))
		{
			final CheckoutFlowEnum checkoutFlow = enumerationService.getEnumerationValue(CheckoutFlowEnum.class,
					StringUtils.upperCase(flow));
			SessionOverrideCheckoutFlowFacade.setSessionOverrideCheckoutFlow(checkoutFlow);
		}

		// Override the Checkout PCI setting in the session
		if (StringUtils.isNotBlank(pci))
		{
			final CheckoutPciOptionEnum checkoutPci = enumerationService.getEnumerationValue(CheckoutPciOptionEnum.class,
					StringUtils.upperCase(pci));
			SessionOverrideCheckoutFlowFacade.setSessionOverrideSubscriptionPciOption(checkoutPci);
		}

		// Redirect to the start of the checkout flow to begin the checkout process
		// We just redirect to the generic '/checkout' page which will actually select the checkout flow
		// to use. The customer is not necessarily logged in on this request, but will be forced to login
		// when they arrive on the '/checkout' page.
		return REDIRECT_PREFIX + "/checkout";
	}



	@PostMapping("/update")
	public String updateCartQuantities(@RequestParam("entryNumber")
	final long entryNumber, final Model model, @Valid
	final UpdateQuantityForm form, final BindingResult bindingResult, final HttpServletRequest request,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		if (bindingResult.hasErrors())
		{
			for (final ObjectError error : bindingResult.getAllErrors())
			{
				if ("typeMismatch".equals(error.getCode()))
				{
					GlobalMessages.addErrorMessage(model, "basket.error.quantity.invalid");
				}
				else
				{
					GlobalMessages.addErrorMessage(model, error.getDefaultMessage());
				}
			}
		}
		else if (getCartFacade().hasEntries())
		{
			try
			{
				final CartModificationData cartModification = getCartFacade().updateCartEntry(entryNumber,
						form.getQuantity().longValue());
				addFlashMessage(form, request, redirectModel, cartModification);

				//final QuoteData quoteData = getCartFacade().getSessionCart().getQuoteData();

				// Redirect to the cart page on update success so that the browser doesn't re-post again
				//return quoteData != null ? String.format(REDIRECT_QUOTE_EDIT_URL, urlEncode(quoteData.getCode())) : REDIRECT_CART_URL;
				return REDIRECT_CART_URL;
			}
			catch (final CommerceCartModificationException ex)
			{
				LOG.warn("Couldn't update product with the entry number: " + entryNumber + ".", ex);
			}
		}

		// if could not update cart, display cart/quote page again with error
		return

		prepareCartUrl(model);
	}

	@Override
	protected void prepareDataForPage(final Model model) throws CMSItemNotFoundException
	{
		final CartModel cartModel = cartService.getSessionCart();
		CartData cartData = cartConverter.convert(cartModel);
		final String freightCharge = ((SiteOneCartFacade) cartFacade).getFreight(cartModel, cartData);
		cartModel.setFreight(freightCharge);
		cartModel.setTotalTax(0.0d);
		boolean isMixedCart = false;
		boolean isBulkBigBag = false;
		boolean deliveryDisabled = false;
		final PointOfServiceData pos = storeSessionFacade.getSessionStore();

		if (null != pos && siteOneFeatureSwitchCacheService
				.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches", pos.getStoreId()))
		{
			isMixedCart = true;
		}

		boolean splitMixedCart = false;
		if(null != pos && !userFacade.isAnonymousUser() && siteOneFeatureSwitchCacheService
				.isBranchPresentUnderFeatureSwitch("SplitMixedPickupBranches", pos.getStoreId())
				&& !CollectionUtils.isEmpty(cartData.getPickupAndDeliveryEntries()) && !CollectionUtils.isEmpty(cartData.getShippingOnlyEntries())) 
		{
				splitMixedCart=true;
		}
		
		if (null != pos && siteOneFeatureSwitchCacheService
				.isBranchPresentUnderFeatureSwitch("BulkBigBag", pos.getStoreId()))
		{
			if (null != pos.getBigBag())
			{
				isBulkBigBag = pos.getBigBag();
			}
		}
		model.addAttribute("isBulkBigBagEnabled", isBulkBigBag);
		OrderTypeEnum initialOrderType = null;
		if(cartModel.getOrderType() != null)
		{
			initialOrderType = cartModel.getOrderType();
		}
		if ((null == cartModel.getOrderType() || CollectionUtils.isEmpty(cartModel.getEntries()))
				|| ((SiteOneCartFacade) cartFacade).getDefaultFulfillment(cartModel, cartData, initialOrderType, splitMixedCart))
		{
			if(initialOrderType != null && splitMixedCart)
			{
				cartModel.setOrderType(initialOrderType);
			}
			else
			{
				cartModel.setOrderType(OrderTypeEnum.PICKUP);
			}	
		}
		if (null != cartModel.getOrderType() && cartModel.getOrderType().getCode().equalsIgnoreCase("DELIVERY"))
		{
			if (null != pos && !(pos.getAddress().getRegion().getIsocodeShort().equalsIgnoreCase("CA")))
			{
				if (!((SiteOneCartFacade) cartFacade).isEligibleForDelivery(cartModel,cartData))
				{
					cartModel.setOrderType(OrderTypeEnum.PICKUP);
				}
			}
		}
		if (pos != null && BooleanUtils.isTrue(pos.getDeliveryfullfillment()) && BooleanUtils.isNotTrue(pos.getPickupfullfillment())
				&& BooleanUtils.isNotTrue(pos.getShippingfullfillment()))
		{
			cartModel.setOrderType(OrderTypeEnum.DELIVERY);
		}
		if ((isMixedCart || splitMixedCart ) && CollectionUtils.isEmpty(cartModel.getEntries()) || (CollectionUtils.isEmpty(cartModel.getEntries()
				.stream()
				.filter(
						entry -> null != entry.getDeliveryMode() && entry.getDeliveryMode().getCode().equalsIgnoreCase("standard-net"))
				.collect(Collectors.toList()))))
		{
			cartModel.setDeliverableItemTotal(0.0d);
		}
		if ((isMixedCart || splitMixedCart ) && CollectionUtils.isEmpty(cartModel.getEntries())
				|| (CollectionUtils.isEmpty(cartModel.getEntries().stream()
						.filter(entry -> null != entry.getDeliveryMode()
								&& entry.getDeliveryMode().getCode().equalsIgnoreCase(SiteoneCoreConstants.SHIPPING_DELIVERYMODE_CODE))
						.collect(Collectors.toList()))))
		{
			cartModel.setShippableItemTotal(0.0d);
		}
		if (!isMixedCart && null != pos)
		{
			for (final AbstractOrderEntryModel entry : cartModel.getEntries())
			{
				if (entry.getProduct() instanceof GenericVariantProductModel)
				{
					final ProductModel baseProduct = ((GenericVariantProductModel) entry.getProduct()).getBaseProduct();
					String deliveryAlwaysEnabledBranches = baseProduct.getDeliveryAlwaysEnabledBranches();
					if (baseProduct.getIsDeliverable() == Boolean.FALSE && deliveryAlwaysEnabledBranches != null &&
							!ArrayUtils.contains(deliveryAlwaysEnabledBranches.trim().split(","),pos.getStoreId()))
					{
						deliveryDisabled = true;
					}
					else if(baseProduct.getIsDeliverable() == Boolean.FALSE)
					{
						deliveryDisabled = true;
					}
				}
				else if (entry.getProduct().getIsDeliverable() == Boolean.FALSE)
				{
					deliveryDisabled = true;
					String deliveryAlwaysEnabledBranches = entry.getProduct().getDeliveryAlwaysEnabledBranches();
					if(deliveryAlwaysEnabledBranches != null && ArrayUtils.contains(deliveryAlwaysEnabledBranches.trim().split(","),pos.getStoreId()))
					{
						deliveryDisabled = false;
					}
				}
				if(!splitMixedCart) {
					if (null != cartModel.getOrderType() && cartModel.getOrderType().getCode().equalsIgnoreCase("PICKUP"))
					{
						entry.setDeliveryMode(deliveryModeService.getDeliveryModeForCode(SiteoneCoreConstants.PICKUP_DELIVERYMODE_CODE));
					}
					if (null != cartModel.getOrderType() && cartModel.getOrderType().getCode().equalsIgnoreCase("DELIVERY"))
					{
						entry.setDeliveryMode(deliveryModeService.getDeliveryModeForCode(SiteoneCoreConstants.DELIVERY_DELIVERYMODE_CODE));
					}
					if (null != cartModel.getOrderType() && cartModel.getOrderType().getCode().equalsIgnoreCase("PARCEL_SHIPPING"))
					{
						entry.setDeliveryMode(deliveryModeService.getDeliveryModeForCode(SiteoneCoreConstants.SHIPPING_DELIVERYMODE_CODE));
					}
				}
				else if(splitMixedCart && (entry.getFullfilledStoreType() == null || (entry.getFullfilledStoreType() != null && !entry.getFullfilledStoreType().equalsIgnoreCase("HubStore"))))
				{
					if (null != cartModel.getOrderType() && cartModel.getOrderType().getCode().equalsIgnoreCase("PICKUP"))
					{
						entry.setDeliveryMode(deliveryModeService.getDeliveryModeForCode(SiteoneCoreConstants.PICKUP_DELIVERYMODE_CODE));
					}
					if (null != cartModel.getOrderType() && cartModel.getOrderType().getCode().equalsIgnoreCase("DELIVERY"))
					{
						entry.setDeliveryMode(deliveryModeService.getDeliveryModeForCode(SiteoneCoreConstants.DELIVERY_DELIVERYMODE_CODE));
					}
				}
				else if(splitMixedCart && (entry.getFullfilledStoreType() != null && !entry.getFullfilledStoreType().equalsIgnoreCase("HubStore")))
				{
					if (null != cartModel.getOrderType() && cartModel.getOrderType().getCode().equalsIgnoreCase("PARCEL_SHIPPING"))
					{
						entry.setDeliveryMode(deliveryModeService.getDeliveryModeForCode(SiteoneCoreConstants.SHIPPING_DELIVERYMODE_CODE));
					}
				}
			}
		}
		if (deliveryDisabled)
		{
			model.addAttribute("cartDeliveryDisabled", "true");
		}
		final Cookie cookie = new Cookie(CART_CREATION_TIME_COOKIE,
				Long.valueOf(cartService.getSessionCart().getCreationtime().getTime()).toString());
		cookie.setMaxAge(60 * 24 * 60 * 60);
		cookie.setPath("/");
		cookie.setSecure(true);
		((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse().addCookie(cookie);
		final List<String> showVoucherList = new ArrayList<>();
		if (null != cartModel.getAppliedCouponCodes())
		{
			showVoucherList.addAll(cartModel.getAppliedCouponCodes());
		}
		final PointOfServiceModel cartPos = cartModel.getPointOfService();
		if (null != pos)
		{
			if (CollectionUtils.isNotEmpty(pos.getHubStores()))
			{
				model.addAttribute("hubStoreId", (pos.getHubStores()).stream().findFirst().get());
			}
		}
		final List<AbstractOrderEntryModel> entries = cartModel.getEntries();
		cartModel.setIsCartSizeExceeds(true);
		final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		final List<String> cartIdList = new ArrayList<>();
		if (null != b2bCustomerModel)
		{
			if (CollectionUtils.isNotEmpty(b2bCustomerModel.getRecentCartIds()))
			{
				if (!b2bCustomerModel.getRecentCartIds().contains(cartModel.getCode()))
				{

					cartIdList.addAll(b2bCustomerModel.getRecentCartIds());
					cartIdList.add(cartModel.getCode());
					b2bCustomerModel.setRecentCartIds(cartIdList);
				}
			}
			else
			{
				cartIdList.add(cartModel.getCode());
				b2bCustomerModel.setRecentCartIds(cartIdList);
			}
			if (CollectionUtils.isNotEmpty(b2bCustomerModel.getRecentCartIds()))
			{
				final List<String> cartIds = (List<String>) b2bCustomerModel.getRecentCartIds();

				final String currentCartId = cartIds.get(cartIds.size() - 1);
				b2bCustomerModel.setCurrentCarId(currentCartId);
			}

			modelService.save(b2bCustomerModel);
			modelService.refresh(b2bCustomerModel);
		}

		for (final AbstractOrderEntryModel abstractOrderEntryModel : entries)
		{
			final Boolean offlineProduct = abstractOrderEntryModel.getProduct().getIsProductOffline();
			final ProductData productData = getProductData(abstractOrderEntryModel.getProduct());
			if (abstractOrderEntryModel.getIsPromotionEnabled() != null)
			{
				if (abstractOrderEntryModel.getIsPromotionEnabled().booleanValue())
				{
					applyProductPromotion(productData);
				}
				else
				{
					removeProductPromotion(productData);
				}
			}
			if (null != productData.getCouponCode() && showVoucherList.contains(productData.getCouponCode()))
			{
				showVoucherList.remove(productData.getCouponCode());
			}
			if (null != offlineProduct && Boolean.TRUE.equals(offlineProduct))
			{
				GlobalMessages.addMessage(model, GlobalMessages.CONF_MESSAGES_HOLDER, "cart.product.nla.message", new Object[]
				{ abstractOrderEntryModel.getProduct().getProductShortDesc() });

				try
				{
					getCartFacade().updateCartEntry(abstractOrderEntryModel.getEntryNumber(), Long.valueOf(0));

				}
				catch (final CommerceCartModificationException e)
				{
					LOG.warn("Couldn't remove product with the entry number: " + abstractOrderEntryModel.getEntryNumber() + ".", e);
				}

			}

		}
		model.addAttribute("showVoucherList", showVoucherList);
		model.addAttribute("guestUsers", getSessionService().getAttribute("guestUser"));

		if (null != pos && null != pos.getStoreId())
		{
			if (null != cartPos && !cartPos.getStoreId().equalsIgnoreCase(pos.getStoreId())
					&& CollectionUtils.isNotEmpty(cartModel.getEntries()))
			{
				GlobalMessages.addErrorMessage(model, "checkout.orderreview.pricechanges");
				if (null == storeFinderService.getStoreForId(cartPos.getStoreId()))
				{
					GlobalMessages.addMessage(model, GlobalMessages.CONF_MESSAGES_HOLDER, "cart.store.nla.message", new Object[]
					{ pos.getStoreId(), pos.getAddress().getFormattedAddress() });
				}
			}
		}
		cartData = cartConverter.convert(cartModel);
		if (null != cartData.getStockAvailableOnlyHubStore() && cartData.getStockAvailableOnlyHubStore()
				&& !(((SiteOneCartFacade) cartFacade).getDefaultFulfillment(cartModel, cartData, initialOrderType, splitMixedCart)))
		{
			cartModel.setOrderType(OrderTypeEnum.PARCEL_SHIPPING);
		}
		if(cartModel.getOrderType() != null && splitMixedCart 
				&& !((SiteOneCartFacade) cartFacade).isAllShippingOnlyEntries(cartModel, cartData, initialOrderType))
		{
			if(initialOrderType != null)
			{
				cartModel.setOrderType(initialOrderType);
			}
			else
			{
				cartModel.setOrderType(OrderTypeEnum.PICKUP);
			}
		}
		((SiteOneCartFacade) cartFacade).updateShipToAndPOS(cartModel);
		((SiteOneCartFacade) cartFacade).updateFullfillmentDetails(cartModel, cartData);
		// parcel shipping
		if (!isMixedCart)
		{
			((SiteOneCartFacade) cartFacade).updateParcelShipping(cartModel);
		}

		try
		{
			commerceCartCalculationStrategy.recalculateCart(cartModel);
		}
		catch (final ResourceAccessException ex)
		{
			LOG.error("Unable to get customer specific price", ex);
			GlobalMessages.addMessage(model, GlobalMessages.ERROR_MESSAGES_HOLDER, "cart.price.unavailable", new Object[]
			{ pos.getAddress().getPhone() });
		}

		super.prepareDataForPage(model);
		if (!model.containsAttribute(VOUCHER_FORM))
		{
			model.addAttribute(VOUCHER_FORM, new VoucherForm());
		}

		getAllSavedList(model);
		// Because DefaultSiteConfigService.getProperty() doesn't set default boolean value for undefined property,
		// this property key was generated to use Config.getBoolean() method
		final String siteQuoteProperty = SITE_QUOTES_ENABLED.concat(getBaseSiteService().getCurrentBaseSite().getUid());
		model.addAttribute("siteQuoteEnabled", Config.getBoolean(siteQuoteProperty, Boolean.FALSE));
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, resourceBreadcrumbBuilder.getBreadcrumbs("breadcrumb.cart"));
		model.addAttribute("pageType", PageType.CART.name());
		model.addAttribute("cartPageSize", Integer.parseInt(siteOneFeatureSwitchCacheService.getValueForSwitch("CartPageSize")));
		final String productCountForCSPCall = siteOneFeatureSwitchCacheService.getValueForSwitch("ProductCountForCSPCall");
		model.addAttribute("cartPageCount", productCountForCSPCall != null ? Integer.parseInt(productCountForCSPCall) : 10);
		((SiteOneCartFacade) cartFacade).parcelShippingMessageForProducts(((SiteOneCartFacade) cartFacade).getSessionCart(), model);
		//model.addAttribute("cartData",((SiteOneCartFacade) cartFacade).updateDefaultFulfillmentdetails(cartModel, ((SiteOneCartFacade) cartFacade).getSessionCart()));
	}

	public void removeProductPromotion(final ProductData productData)
	{
		try
		{
			if (productData.getCouponCode() != null)
			{
				voucherFacade.releaseVoucher(productData.getCouponCode());
			}
		}
		catch (final VoucherOperationException e)
		{
			LOG.warn(e.getMessage(), e);
		}
	}

	protected void addFlashMessage(final UpdateQuantityForm form, final HttpServletRequest request,
			final RedirectAttributes redirectModel, final CartModificationData cartModification)
	{
		if (cartModification.getQuantity() == form.getQuantity().longValue())
		{
			// Success

			if (cartModification.getQuantity() == 0)
			{
				// Success in removing entry
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "basket.page.message.remove");
			}
			else
			{
				// Success in update quantity
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "basket.page.message.update");
			}
		}
		else if (cartModification.getQuantity() > 0)
		{
			// Less than successful
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER,
					"basket.page.message.update.reducedNumberOfItemsAdded.lowStock", new Object[]
					{ XSSFilterUtil.filter(cartModification.getEntry().getProduct().getName()),
							Long.valueOf(cartModification.getQuantity()), form.getQuantity(),
							request.getRequestURL().append(cartModification.getEntry().getProduct().getUrl()) });
		}
		else
		{
			// No more stock available
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER,
					"basket.page.message.update.reducedNumberOfItemsAdded.noStock", new Object[]
					{ XSSFilterUtil.filter(cartModification.getEntry().getProduct().getName()),
							request.getRequestURL().append(cartModification.getEntry().getProduct().getUrl()) });
		}

	}
	
	@PostMapping("/bigBagChecker")
	public @ResponseBody BagInfoData updateBigBagInfo(@RequestParam(value = "sku", required = true)
	final String sku, @RequestParam(value = "quantity", required = true)
	final Integer quantity, @RequestParam(value = "bigBagPrice", required = true)
	final Double bigBagPrice, @RequestParam(value = "UOM", required = true)
	final String UOM, @RequestParam(value = "isChecked", required = true)
	final Boolean isChecked, final Model model)
	{
		BagInfoData data = new BagInfoData();
		try
		{
			data = ((SiteOneCartFacade) cartFacade).updateBigBagInfo(sku,quantity,bigBagPrice,UOM,isChecked);
			return data;
		}
		catch (final Exception e) {
		    LOG.error("Exception occurred while updating BigBagInfo for SKU: {}" + sku);
		}
		
		return data;

	}

	@SuppressWarnings("boxing")
	@ResponseBody
	@PostMapping("/updateMultiD")
	public CartData updateCartQuantitiesMultiD(@RequestParam("entryNumber")
	final Integer entryNumber, @RequestParam("productCode")
	final String productCode, final Model model, @Valid
	final UpdateQuantityForm form, final BindingResult bindingResult)
	{
		if (bindingResult.hasErrors())
		{
			for (final ObjectError error : bindingResult.getAllErrors())
			{
				if ("typeMismatch".equals(error.getCode()))
				{
					GlobalMessages.addErrorMessage(model, "basket.error.quantity.invalid");
				}
				else
				{
					GlobalMessages.addErrorMessage(model, error.getDefaultMessage());
				}
			}
		}
		else
		{
			try
			{
				final CartModificationData cartModification = getCartFacade()
						.updateCartEntry(getOrderEntryData(form.getQuantity(), productCode, entryNumber));
				if (cartModification.getStatusCode().equals(SUCCESSFUL_MODIFICATION_CODE))
				{
					GlobalMessages.addMessage(model, GlobalMessages.CONF_MESSAGES_HOLDER, cartModification.getStatusMessage(), null);
				}
				else if (!model.containsAttribute(ERROR_MSG_TYPE))
				{
					GlobalMessages.addMessage(model, GlobalMessages.ERROR_MESSAGES_HOLDER, cartModification.getStatusMessage(), null);
				}
			}
			catch (final CommerceCartModificationException ex)
			{
				LOG.warn("Couldn't update product with the entry number: " + entryNumber + ".", ex);
			}

		}
		return getCartFacade().getSessionCart();
	}

	@SuppressWarnings("boxing")
	protected OrderEntryData getOrderEntryData(final long quantity, final String productCode, final Integer entryNumber)
	{
		final OrderEntryData orderEntry = new OrderEntryData();
		orderEntry.setQuantity(quantity);
		orderEntry.setProduct(new ProductData());
		orderEntry.getProduct().setCode(productCode);
		orderEntry.setEntryNumber(entryNumber);
		return orderEntry;
	}

	@PostMapping("/save")
	@RequireHardLogIn
	public String saveCart(final SaveCartForm form, final BindingResult bindingResult, final RedirectAttributes redirectModel)
			throws CommerceSaveCartException
	{
		saveCartFormValidator.validate(form, bindingResult);
		if (bindingResult.hasErrors())
		{
			for (final ObjectError error : bindingResult.getAllErrors())
			{
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, error.getCode());
			}
			redirectModel.addFlashAttribute("saveCartForm", form);
		}
		else
		{
			final CommerceSaveCartParameterData commerceSaveCartParameterData = new CommerceSaveCartParameterData();
			commerceSaveCartParameterData.setName(form.getName());
			commerceSaveCartParameterData.setDescription(form.getDescription());
			commerceSaveCartParameterData.setEnableHooks(true);
			try
			{
				final CommerceSaveCartResultData saveCartData = saveCartFacade.saveCart(commerceSaveCartParameterData);
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "basket.save.cart.on.success",
						new Object[]
						{ saveCartData.getSavedCartData().getName() });
			}
			catch (final CommerceSaveCartException csce)
			{
				LOG.error(csce.getMessage(), csce);
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "basket.save.cart.on.error",
						new Object[]
						{ form.getName() });
			}
		}
		return REDIRECT_CART_URL;
	}

	@GetMapping(value = "/export", produces = "text/csv")
	public String exportCsvFile(final HttpServletResponse response, final RedirectAttributes redirectModel) throws IOException
	{
		response.setHeader("Content-Disposition", "attachment;filename=cart.csv");

		try (final StringWriter writer = new StringWriter())
		{
			try
			{
				final List<String> headers = new ArrayList<String>();
				headers.add(getMessageSource().getMessage("basket.export.cart.item.sku", null, getI18nService().getCurrentLocale()));
				headers.add(
						getMessageSource().getMessage("basket.export.cart.item.quantity", null, getI18nService().getCurrentLocale()));
				headers.add(getMessageSource().getMessage("basket.export.cart.item.name", null, getI18nService().getCurrentLocale()));
				headers
						.add(getMessageSource().getMessage("basket.export.cart.item.price", null, getI18nService().getCurrentLocale()));

				final CartData cartData = getCartFacade().getSessionCartWithEntryOrdering(false);
				csvFacade.generateCsvFromCart(headers, true, cartData, writer);

				StreamUtils.copy(writer.toString(), StandardCharsets.UTF_8, response.getOutputStream());
			}
			catch (final IOException e)
			{
				LOG.error(e.getMessage(), e);
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "basket.export.cart.error", null);

				return REDIRECT_CART_URL;
			}

		}

		return null;
	}

	public void applyProductPromotion(final ProductData productData)
	{
		try
		{
			if (productData.getCouponCode() != null)
			{
				voucherFacade.applyVoucher(productData.getCouponCode());
			}
		}
		catch (final VoucherOperationException e)
		{
			e.printStackTrace();
		}
	}

	public ProductData getProductData(final ProductModel productModel)
	{
		final ProductData productData = new ProductData();
		PointOfServiceData storeId=null;
		final List<ProductOption> promotionOptions = new ArrayList<>(Arrays.asList(ProductOption.PROMOTIONS));
		if (null != ((PointOfServiceData) getSessionService().getAttribute("sessionStore")))
		{
			storeId = (PointOfServiceData) getSessionService().getAttribute("sessionStore");
			if (!(siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("RetailBranches",
					storeId.getStoreId()) && StringUtils.isNotBlank(storeId.getCustomerRetailId())))
			{
				promotionOptions.add(0, ProductOption.PRICE);
			}
		}
		
		getProductConfiguredPopulator().populate(productModel, productData, promotionOptions);
		return productData;
	}

	@PostMapping(path = "/apply/clipCoupon")
	public String applyClipCouponCart(@RequestParam(value = "promoProductCode", required = false)
	final String promoProductcode, @RequestParam(value = "isCouponEnabled", required = false)
	final boolean isCouponEnabled, final Model model)
	{
		((SiteOneCartFacade) cartFacade).enablePromotionToProduct(promoProductcode, isCouponEnabled);
		return REDIRECT_CART_URL;
	}

	@PostMapping("/voucher/apply")
	public String applyVoucherAction(@Valid
	final VoucherForm form, final BindingResult bindingResult, final RedirectAttributes redirectAttributes)
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

		return REDIRECT_CART_URL;
	}

	@PostMapping("/voucher/remove")
	public String removeVoucher(@Valid
	final VoucherForm form, final RedirectAttributes redirectModel)
	{
		try
		{
			voucherFacade.releaseVoucher(form.getVoucherCode());
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
		return REDIRECT_CART_URL;
	}

	@ResponseBody
	@GetMapping("/availability")
	public Boolean isStockAvailableAcrossNetwork(@RequestParam("productCode")
	final String productCode, @RequestParam(value = "quantity", defaultValue = "0")
	Long quantity, @RequestParam(value = "checkQuantity", defaultValue = "0")
	final Long checkQuantity)
	{
		Boolean isStockAvailable = Boolean.FALSE;
		if (quantity.equals(Long.valueOf(0)))
		{
			quantity = checkQuantity;
		}
		final List<StockLevelModel> stockLevelList = siteOneStockLevelService.getStockLevelsForQuantity(productCode, quantity);
		for (final StockLevelModel stockLevelModel : stockLevelList)
		{
			if (stockLevelModel.getAvailable() - stockLevelModel.getReserved() + stockLevelModel.getOverSelling() >= quantity)
			{
				isStockAvailable = Boolean.TRUE;
				break;
			}
		}

		return isStockAvailable;
	}

	@ResponseBody
	@GetMapping("/rupcheck")
	public Boolean isRupCheckForProduct(@RequestParam("productCode")
	final String productCode, @RequestParam("isRegulatory")
	final Boolean isRegulatory)
	{
		Boolean isRup = Boolean.FALSE;
		if (isRegulatory && storeSessionFacade.getSessionStore() != null)
		{
			final PointOfServiceModel pos = storeFinderService.getStoreForId(storeSessionFacade.getSessionStore().getStoreId());
			isRup = regulatoryStatesCronJobService.getRupBySkuAndState(productCode, pos.getAddress().getRegion());
		}
		return isRup;
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

	private void getAllSavedList(final Model model)
	{
		if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
		{

			final boolean isAssembly = false;
			final List<SavedListData> allWishlist = siteoneSavedListFacade.getAllSavedListForEdit(isAssembly);
			String wishlistName = null;
			if (CollectionUtils.isNotEmpty(allWishlist) && allWishlist.size() == 1)
			{
				wishlistName = allWishlist.get(0).getCode();

			}


			if (CollectionUtils.isEmpty(allWishlist))
			{
				model.addAttribute("createWishList", "true");
			}


			model.addAttribute("wishlistName", wishlistName);
			model.addAttribute("allWishlist", allWishlist);
		}

		final LoginForm loginForm = new LoginForm();
		model.addAttribute(loginForm);



	}

	@ResponseBody
	@GetMapping("/fullfillmentStatus")
	public Boolean cartFullfillmentStatus(@RequestParam("orderType")
	final String orderType, final Model model)
	{

		final Boolean isEligibleProduct = ((SiteOneCartFacade) cartFacade).setFullfillmentOrderType(orderType);

		return isEligibleProduct;
	}

	@GetMapping("/showNearbyOverlay")
	public @ResponseBody List<StoreLevelStockInfoData> showNearbyOverlay(@RequestParam(value = "code")
	final String code, final Model model)
	{
		final List<StoreLevelStockInfoData> storeLevelStockInfoDataList = siteOneProductFacade.populateStoreLevelStockInfoData(code,
				false);
		return (CollectionUtils.isEmpty(storeLevelStockInfoDataList) ? null : storeLevelStockInfoDataList);
	}

	@ResponseBody
	@GetMapping("/updateDeliveryMode")
	public ThresholdCheckResponseData updateDeliveryMode(final String deliveryMode, final String entryNumber, final String storeId)
	{
		return ((SiteOneCartFacade) cartFacade).updateDeliveryModes(deliveryMode, entryNumber, storeId,
				cartService.getSessionCart());
	}

	@ResponseBody
	@GetMapping("/getCartToggleResponse")
	public CartToggleResponseData getCartToggleResponse(@RequestParam(value = "fulfillmentType")
	final String fulfillmentType)
	{
		return ((SiteOneCartFacade) cartFacade).setCartToggleResponseData(null, fulfillmentType);
	}

	/**
	 * @return the productConfiguredPopulator
	 */
	public ConfigurablePopulator<ProductModel, ProductData, ProductOption> getProductConfiguredPopulator()
	{
		return productConfiguredPopulator;
	}

	/**
	 * @param productConfiguredPopulator
	 *           the productConfiguredPopulator to set
	 */
	public void setProductConfiguredPopulator(
			final ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator)
	{
		this.productConfiguredPopulator = productConfiguredPopulator;
	}

	@PostMapping(path = "/removeAllEntries")
	public String removeAllEntriesFromCart(final HttpServletRequest request, final RedirectAttributes redirectModel)
	{
		cartFacade.removeSessionCart();
		return REDIRECT_CART_URL;
	}

}
