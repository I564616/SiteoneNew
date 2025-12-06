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
package com.siteone.storefront.controllers.misc;

import de.hybris.platform.acceleratorfacades.product.data.ProductWrapperData;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.AbstractController;
import de.hybris.platform.acceleratorstorefrontcommons.forms.AddToCartOrderForm;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.commercefacades.order.converters.populator.GroupCartModificationListPopulator;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.CartModificationData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.StockData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.util.Config;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;

import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.facades.cart.SiteOneCartFacade;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.storefront.controllers.ControllerConstants;
import com.siteone.storefront.forms.SiteOneAddToCartForm;
import com.siteone.core.constants.SiteoneCoreConstants;


/**
 * Controller for Add to Cart functionality which is not specific to a certain page.
 */
@Controller
public class AddToCartController extends AbstractController
{
	private static final String QUANTITY_ATTR = "quantity";
	private static final String TYPE_MISMATCH_ERROR_CODE = "typeMismatch";
	private static final String ERROR_MSG_TYPE = "errorMsg";
	private static final String QUANTITY_INVALID_BINDING_MESSAGE_KEY = "basket.error.quantity.invalid.binding";
	private static final String SHOWN_PRODUCT_COUNT = "siteonestorefront.storefront.minicart.shownProductCount";
	private static final String ALGONOMYRECOMMFLAG = "AlgonomyProductRecommendationEnable";
	

	private static final Logger LOG = Logger.getLogger(AddToCartController.class);

	/*
	 * @Resource(name = "cartFacade") private CartFacade cartFacade;
	 */
	@Resource(name = "cartFacade")
	private SiteOneCartFacade siteOneCartFacade;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "accProductFacade")
	private ProductFacade productFacade;

	@Resource(name = "groupCartModificationListPopulator")
	private GroupCartModificationListPopulator groupCartModificationListPopulator;

	@Resource
	private CommonI18NService commonI18NService;

	@Resource(name = "productService")
	private ProductService productService;

	@Resource(name = "cartService")
	private CartService cartService;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;

	@Resource(name = "storeFinderService")
	private SiteOneStoreFinderService storeFinderService;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource
	private ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator;
	
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@PostMapping(value = "/cart/add", produces = "application/json")
	public String addToCart(final HttpServletRequest request, @RequestParam("productCodePost") final String code,
			@RequestParam(value = "promoProductCode", required = false) final String promoProductcode,
			@RequestParam(value = "isCouponEnabled", required = false) final boolean isCouponEnabled, final Model model,
			@RequestParam(value = "productPostPLPQty", defaultValue = "1") final long productPostPLPQty,
			@RequestParam(value = "storeId", required = false) String storeId,
			@Valid final SiteOneAddToCartForm form, final BindingResult bindingErrors)
	{
		long qty = 0l;
		if (bindingErrors.hasErrors())
		{
			return getViewWithBindingErrorMessages(model, bindingErrors);
		}

		if (productPostPLPQty == 1)
		{
			qty = form.getQty();
		}
		else
		{
			qty = productPostPLPQty;
		}

		String inventoryUOMId = null;
		if (StringUtils.isNotEmpty(form.getInventoryUomId()))
		{
			inventoryUOMId = form.getInventoryUomId();
		}

		PointOfServiceData sessionStore = storeSessionFacade.getSessionStore();
		if (!StringUtils.isNotEmpty(storeId) && sessionStore != null && StringUtils.isNotEmpty(sessionStore.getStoreId())) {
			storeId = sessionStore.getStoreId();
			LOG.error("AddToCartController-addToCart: New Session Store ID: " + storeId);
		}

		if (qty <= 0)
		{
			model.addAttribute(ERROR_MSG_TYPE, "basket.error.quantity.invalid");
			model.addAttribute(QUANTITY_ATTR, Long.valueOf(0L));
		}
		else
		{
			try
			{
				LOG.error("AddToCartController-addToCart: Cart: " + ((getCartService().getSessionCart()!=null)?getCartService().getSessionCart().getCode():"") + " Product: " + code + " - Qty: " + qty + " - inventoryUOMId: " + inventoryUOMId + " - StoreId: " + storeId);
				final CartModificationData cartModification = siteOneCartFacade.addToCart(code, qty, inventoryUOMId, false, storeId);
				final CartModel cartModel = getCartService().getSessionCart();
				if (CollectionUtils.isNotEmpty(cartModel.getEntries()))
				{
					for (final AbstractOrderEntryModel entry : cartModel.getEntries())
					{
						if (null != promoProductcode && promoProductcode.equalsIgnoreCase(entry.getProduct().getCode()))
						{
							entry.setIsPromotionEnabled(isCouponEnabled);
							modelService.save(entry);
						}
					}
				}

				model.addAttribute(QUANTITY_ATTR, Long.valueOf(cartModification.getQuantityAdded()));
				model.addAttribute("entry", cartModification.getEntry());
				model.addAttribute("cartCode", cartModification.getCartCode());
				model.addAttribute("isQuote",
						siteOneCartFacade.getSessionCart().getQuoteData() != null ? Boolean.TRUE : Boolean.FALSE);

				if (null != inventoryUOMId)
				{
					final ProductData productData = new ProductData();
					final ProductModel productModel = productService.getProductForCode(code);
					productConfiguredPopulator.populate(productModel, productData,
							Arrays.asList(ProductOption.PRICE, ProductOption.STOCK, ProductOption.CUSTOMER_PRICE));
					if(null != productData.getPrice())
					{
						productData.getPrice().setValue(cartModification.getEntry().getBasePrice().getValue());
					}
					else if(null != cartModification.getEntry() && null != cartModification.getEntry().getBasePrice())
					{
						productData.setPrice(cartModification.getEntry().getBasePrice());
					}
					if(null != productData.getCustomerPrice())
					{
						productData.getCustomerPrice().setValue(cartModification.getEntry().getBasePrice().getValue());
					}
					productConfiguredPopulator.populate(productModel, productData,
							Arrays.asList(ProductOption.BASIC, ProductOption.URL, ProductOption.PROMOTIONS));
					if (!siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",
							storeSessionFacade.getSessionStore().getStoreId()))
					{
						final PointOfServiceData posStore = storeSessionFacade.getSessionStore();
						siteOneProductFacade.updateParcelShippingForProduct(productData,
								posStore != null ? posStore.getStoreId() : null);
					}
					model.addAttribute("product", productData);
					model.addAttribute("totalPrice", siteOneCartFacade.getTotalPriceOfAddedProduct(productData, cartModification.getQuantityAdded()));
				}
				else
				{
					final ProductData productData = productFacade.getProductForCodeAndOptions(code,
							Arrays.asList(ProductOption.BASIC, ProductOption.URL, ProductOption.PRICE, ProductOption.STOCK,
									ProductOption.CUSTOMER_PRICE, ProductOption.PROMOTIONS));
					if (!siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",
							storeSessionFacade.getSessionStore().getStoreId()))
					{
						final PointOfServiceData posStore = storeSessionFacade.getSessionStore();
						siteOneProductFacade.updateParcelShippingForProduct(productData,
								posStore != null ? posStore.getStoreId() : null);
					}
					model.addAttribute("product", productData);
					model.addAttribute("totalPrice", siteOneCartFacade.getTotalPriceOfAddedProduct(productData, cartModification.getQuantityAdded()));
				}
				if (cartModification.getQuantityAdded() == 0L)
				{
					model.addAttribute(ERROR_MSG_TYPE, "basket.information.quantity.noItemsAdded." + cartModification.getStatusCode());
				}
				else if (cartModification.getQuantityAdded() < qty)
				{
					model.addAttribute(ERROR_MSG_TYPE,
							"basket.information.quantity.reducedNumberOfItemsAdded." + cartModification.getStatusCode());
				}
				final BaseSiteModel basesite = getBaseSiteService().getCurrentBaseSite();
				Boolean algonomyRecommendation = ((basesite.getUid().equalsIgnoreCase(SiteoneintegrationConstants.BASESITE_CA)) ?
						Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(SiteoneCoreConstants.ALGONOMYRECOMMFLAG_CA)) : 
							Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(ALGONOMYRECOMMFLAG)));
				
				model.addAttribute("algonomyRecommendationEnabled", algonomyRecommendation);	
				if(algonomyRecommendation) {
   				String placementPage="atcPage";
   				final String sessionId = request.getSession().getId();
   				final String pagePosition = "atcPage";
   				final Object[] recommProduct = siteOneProductFacade.getRecommendedProductsToDisplay(placementPage, null, code,
   						sessionId, pagePosition);
   
   				List<ProductData> productList = new ArrayList<>();
   
   				if (recommProduct[1] != null)
   				{
   					productList = (List<ProductData>) recommProduct[1];
   				}
   				model.addAttribute("categoryProduct", productList);
   				model.addAttribute("recommendationTitle", recommProduct[2]);
				}
			}
			catch (final CommerceCartModificationException ex)
			{
				LOG.info("***********CommerceCartModificationException**********");
				LOG.info(ex);
				logDebugException(ex);
				model.addAttribute(ERROR_MSG_TYPE, "basket.error.occurred");
				model.addAttribute(QUANTITY_ATTR, Long.valueOf(0L));
			}
			catch (final ResourceAccessException ex)
			{
				logDebugException(ex);
				model.addAttribute(ERROR_MSG_TYPE, "Price not available please contact store");
				model.addAttribute(QUANTITY_ATTR, Long.valueOf(0L));
			}
		}

		model.addAttribute("numberItemsInCart", Integer.valueOf(1));
		model.addAttribute("numberShowing", Integer.valueOf(1));
		model.addAttribute("pageSection", Boolean.TRUE);

		return ControllerConstants.Views.Fragments.Cart.AddToCartPopup;
	}

	@GetMapping("/cart/addFromOverlay")
	public @ResponseBody boolean addToCartFromOverlay(@RequestParam("code")
	final String code, @RequestParam(value = "quantity", defaultValue = "1")
	final long quantity, @RequestParam("storeId")
	final String storeId, @RequestParam("inventoryUOMId")
	final String inventoryUOMId, final Model model)
	{
		boolean result = false;
		if (quantity <= 0)
		{
			model.addAttribute(ERROR_MSG_TYPE, "basket.error.quantity.invalid");
			model.addAttribute(QUANTITY_ATTR, Long.valueOf(0L));
		}
		else
		{
			try
			{
				final CartModificationData cartModification = siteOneCartFacade.addToCart(code, quantity, inventoryUOMId, false,
						storeId);
				model.addAttribute(QUANTITY_ATTR, Long.valueOf(cartModification.getQuantityAdded()));
				model.addAttribute("entry", cartModification.getEntry());
				model.addAttribute("cartCode", cartModification.getCartCode());
				model.addAttribute("isQuote",
						siteOneCartFacade.getSessionCart().getQuoteData() != null ? Boolean.TRUE : Boolean.FALSE);
				if (cartModification.getQuantityAdded() == 0L)
				{
					model.addAttribute(ERROR_MSG_TYPE, "basket.information.quantity.noItemsAdded." + cartModification.getStatusCode());
				}
				else if (cartModification.getQuantityAdded() < quantity)
				{
					model.addAttribute(ERROR_MSG_TYPE,
							"basket.information.quantity.reducedNumberOfItemsAdded." + cartModification.getStatusCode());
				}
				result = true;
			}
			catch (final CommerceCartModificationException ex)
			{
				LOG.info("***********CommerceCartModificationException**********");
				LOG.info(ex);
				logDebugException(ex);
				model.addAttribute(ERROR_MSG_TYPE, "basket.error.occurred");
				model.addAttribute(QUANTITY_ATTR, Long.valueOf(0L));
			}
			catch (final ResourceAccessException ex)
			{
				logDebugException(ex);
				model.addAttribute(ERROR_MSG_TYPE, "Price not available please contact store");
				model.addAttribute(QUANTITY_ATTR, Long.valueOf(0L));
			}
		}

		return result;
	}

	protected String getViewWithBindingErrorMessages(final Model model, final BindingResult bindingErrors)
	{
		for (final ObjectError error : bindingErrors.getAllErrors())
		{
			if (isTypeMismatchError(error))
			{
				model.addAttribute(ERROR_MSG_TYPE, QUANTITY_INVALID_BINDING_MESSAGE_KEY);
			}
			else
			{
				model.addAttribute(ERROR_MSG_TYPE, error.getDefaultMessage());
			}
		}
		return ControllerConstants.Views.Fragments.Cart.AddToCartPopup;
	}

	protected boolean isTypeMismatchError(final ObjectError error)
	{
		return error.getCode().equals(TYPE_MISMATCH_ERROR_CODE);
	}

	@PostMapping(value = "/cart/addGrid", produces = MediaType.APPLICATION_JSON_VALUE)
	public final String addGridToCart(@RequestBody final AddToCartOrderForm form, final Model model)
	{
		final Set<String> multidErrorMsgs = new HashSet<String>();
		final List<CartModificationData> modificationDataList = new ArrayList<CartModificationData>();

		for (final OrderEntryData cartEntry : form.getCartEntries())
		{
			if (!isValidProductEntry(cartEntry))
			{
				LOG.error("Error processing entry");
			}
			else if (!isValidQuantity(cartEntry))
			{
				multidErrorMsgs.add("basket.error.quantity.invalid");
			}
			else
			{
				final String errorMsg = addEntryToCart(modificationDataList, cartEntry, true);
				if (StringUtils.isNotEmpty(errorMsg))
				{
					multidErrorMsgs.add(errorMsg);
				}

			}
		}

		if (CollectionUtils.isNotEmpty(modificationDataList))
		{
			groupCartModificationListPopulator.populate(null, modificationDataList);

			model.addAttribute("modifications", modificationDataList);
		}

		if (CollectionUtils.isNotEmpty(multidErrorMsgs))
		{
			model.addAttribute("multidErrorMsgs", multidErrorMsgs);
		}

		model.addAttribute("numberShowing", Integer.valueOf(Config.getInt(SHOWN_PRODUCT_COUNT, 10)));


		return ControllerConstants.Views.Fragments.Cart.AddToCartPopup;
	}

	@PostMapping(value = "/cart/addQuickOrder", produces = MediaType.APPLICATION_JSON_VALUE)
	public final String addQuickOrderToCart(@RequestBody final AddToCartOrderForm form, final Model model)
	{
		final List<CartModificationData> modificationDataList = new ArrayList();
		final List<ProductWrapperData> productWrapperDataList = new ArrayList();
		final int maxQuickOrderEntries = Config.getInt("siteonestorefront.quick.order.rows.max", 25);
		final int sizeOfCartEntries = CollectionUtils.size(form.getCartEntries());
		form.getCartEntries().stream().limit(Math.min(sizeOfCartEntries, maxQuickOrderEntries)).forEach(cartEntry -> {
			String errorMsg = StringUtils.EMPTY;
			final String sku = !isValidProductEntry(cartEntry) ? StringUtils.EMPTY : cartEntry.getProduct().getCode();
			if (StringUtils.isEmpty(sku))
			{
				errorMsg = "text.quickOrder.product.code.invalid";
			}
			else if (!isValidQuantity(cartEntry))
			{
				errorMsg = "text.quickOrder.product.quantity.invalid";
			}
			else
			{
				try
				{
					errorMsg = addEntryToCart(modificationDataList, cartEntry, false);
				}
				catch (final ResourceAccessException ex)
				{
					logDebugException(ex);
					model.addAttribute(ERROR_MSG_TYPE, "Price not available please contact store");
					model.addAttribute(QUANTITY_ATTR, Long.valueOf(0L));
				}
			}

			if (StringUtils.isNotEmpty(errorMsg))
			{
				productWrapperDataList.add(createProductWrapperData(sku, errorMsg));
			}
		});

		if (CollectionUtils.isNotEmpty(productWrapperDataList))
		{
			model.addAttribute("quickOrderErrorData", productWrapperDataList);
			model.addAttribute("quickOrderErrorMsg", "basket.quick.order.error");
		}

		if (CollectionUtils.isNotEmpty(modificationDataList))
		{
			model.addAttribute("modifications", modificationDataList);
		}


		final CartData cartData = siteOneCartFacade.getSessionCart();
		final List entries = cartData.getEntries();

		model.addAttribute("numberShowing", Integer.valueOf(sizeOfCartEntries));
		model.addAttribute("numberItemsInCart", Integer.valueOf(entries.size()));

		model.addAttribute("subTotal", addTotal(modificationDataList));


		return ControllerConstants.Views.Fragments.Cart.AddToCartPopup;
	}

	@ResponseBody
	@PostMapping("/cart/updateQuantities")
	public boolean updateQuantities(@RequestParam("productQty") final long productQty,
			@RequestParam("productCode") final String productCode)
	{
		boolean flag = false;
		for (final OrderEntryData orderEntryData : siteOneCartFacade.getSessionCart().getEntries())
		{
			if (orderEntryData.getProduct().getCode().equalsIgnoreCase(productCode))
			{
				if (updateQuantityToCart(orderEntryData.getEntryNumber().longValue(), productQty) == true)
				{
					flag = true;
				}
				break;
			}
		}
		return flag;
	}

	protected BigDecimal addTotal(final List<CartModificationData> modificationDataList)
	{
		BigDecimal totalPrice = new BigDecimal(0);

		for (int i = 0; i < modificationDataList.size(); i++)
		{
			BigDecimal basePrice = modificationDataList.get(i).getEntry().getBasePrice().getValue();
			final String salePrice = modificationDataList.get(i).getEntry().getProduct().getSalePrice();
			final BigDecimal qty = BigDecimal.valueOf(modificationDataList.get(i).getQuantityAdded());

			if (salePrice != null)
			{
				final BigDecimal discountPrice = new BigDecimal(salePrice);
				basePrice = discountPrice;
			}
			totalPrice = totalPrice.add(basePrice.multiply(qty));
			totalPrice = totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		return totalPrice;
	}

	protected ProductWrapperData createProductWrapperData(final String sku, final String errorMsg)
	{
		final ProductWrapperData productWrapperData = new ProductWrapperData();
		final ProductData productData = new ProductData();
		productData.setCode(sku);
		productWrapperData.setProductData(productData);
		productWrapperData.setErrorMsg(errorMsg);
		return productWrapperData;
	}

	protected void logDebugException(final Exception ex)
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug(ex);
		}
	}

	protected String addEntryToCart(final List<CartModificationData> modificationDataList, final OrderEntryData cartEntry,
			final boolean isReducedQtyError)
	{
		String errorMsg = StringUtils.EMPTY;
		try
		{
			final long qty = cartEntry.getQuantity().longValue();
			final String inventoryUOMId = StringUtils.isNotEmpty(cartEntry.getUomId()) ? cartEntry.getUomId() : null;
			final CartModificationData cartModificationData = siteOneCartFacade.addToCart(cartEntry.getProduct().getCode(), qty,
					inventoryUOMId, true, storeSessionFacade.getSessionStore().getStoreId());
			if (cartModificationData.getQuantityAdded() == 0L)
			{
				errorMsg = "basket.information.quantity.noItemsAdded." + cartModificationData.getStatusCode();
			}
			else if (cartModificationData.getQuantityAdded() < qty && isReducedQtyError)
			{
				errorMsg = "basket.information.quantity.reducedNumberOfItemsAdded." + cartModificationData.getStatusCode();
			}

			modificationDataList.add(cartModificationData);

		}
		catch (final CommerceCartModificationException ex)
		{
			errorMsg = "basket.error.occurred";
			logDebugException(ex);
		}
		return errorMsg;
	}

	protected boolean updateQuantityToCart(final long entryNumber, final long productQty)
	{
		boolean flag = false;
		try
		{
			siteOneCartFacade.updateCartEntry(entryNumber, productQty);
			flag = true;

		}
		catch (final CommerceCartModificationException ex)
		{
			LOG.error("basket.error.occurred");
			logDebugException(ex);
		}
		return flag;
	}

	protected boolean isValidProductEntry(final OrderEntryData cartEntry)
	{
		return cartEntry.getProduct() != null && StringUtils.isNotBlank(cartEntry.getProduct().getCode());
	}

	protected boolean isValidQuantity(final OrderEntryData cartEntry)
	{
		return cartEntry.getQuantity() != null && cartEntry.getQuantity().longValue() >= 1L;
	}

	/**
	 * @return the commonI18NService
	 */
	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	/**
	 * @param commonI18NService
	 *           the commonI18NService to set
	 */
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

	/**
	 * @return the cartService
	 */
	public CartService getCartService()
	{
		return cartService;
	}

	/**
	 * @param cartService
	 *           the cartService to set
	 */
	public void setCartService(final CartService cartService)
	{
		this.cartService = cartService;
	}
	
	public BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}

}
