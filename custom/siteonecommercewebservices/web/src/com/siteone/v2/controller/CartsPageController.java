package com.siteone.v2.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;

import com.siteone.forms.VoucherForm;

import de.hybris.platform.acceleratorfacades.flow.impl.SessionOverrideCheckoutFlowFacade;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.CartModificationData;
import de.hybris.platform.commercefacades.bag.data.BagInfoData;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.voucher.VoucherFacade;
import de.hybris.platform.commercefacades.voucher.exceptions.VoucherOperationException;
import de.hybris.platform.commerceservices.order.CommerceCartCalculationStrategy;
import de.hybris.platform.commerceservices.order.CommerceCartMergingException;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.order.CommerceCartRestorationException;
import de.hybris.platform.commerceservices.order.CommerceCartService;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.commercewebservicescommons.dto.order.CartWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.product.ProductWsDTO;
import de.hybris.platform.commercewebservicescommons.errors.exceptions.CartException;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.order.DeliveryModeService;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.webservicescommons.cache.CacheControl;
import de.hybris.platform.webservicescommons.cache.CacheControlDirective;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdUserIdAndCartIdParam;
import de.hybris.platform.commercewebservicescommons.dto.bag.BagInfoWsDTO;
import com.siteone.core.constants.SiteoneCoreConstants;

import com.siteone.cart.impl.CommerceWebServicesCartFacade;
import com.siteone.commerceservice.savedList.data.SavedListDataDTO;
import com.siteone.commerceservices.cart.dto.AddToCartResponseWsDTO;
import com.siteone.commerceservices.dto.order.ThresholdCheckResponseWsDTO;
import com.siteone.facade.AddToCartResponseData;
import com.siteone.facade.StoreLevelStockInfoData;
import com.siteone.facade.ThresholdCheckResponseData;
import com.siteone.facade.CartToggleResponseData;
import com.siteone.commerceservices.store.dto.StoreLevelStockInfoDataWsDTO;
import com.siteone.core.enums.OrderTypeEnum;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import de.hybris.platform.servicelayer.session.SessionService;
import com.siteone.facade.email.SiteOneShareEmailFacade;
import com.siteone.facades.cart.SiteOneCartFacade;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.savedList.data.SavedListData;
import com.siteone.facades.savedList.SiteoneSavedListFacade;
import com.siteone.facades.store.SiteOneStoreDetailsFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.forms.SiteOneAddToCartForm;
import com.siteone.forms.UpdateQuantityForm;
import com.siteone.utils.XSSFilterUtil;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Date;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.apache.commons.lang3.BooleanUtils;
import com.google.gson.Gson;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/{baseSiteId}/users/{userId}/cart")
@CacheControl(directive = CacheControlDirective.NO_CACHE)
@Tag(name = "Siteone Carts")
public class CartsPageController extends BaseCommerceController{
	
	public static final String VOUCHER_FORM = "voucherForm";
	public static final String CART_DELIVERY_DISABLED = "cartDeliveryDisabled";
	
	@Resource(name = "cartService")
	private CartService cartService;
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;
	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;
	@Resource(name = "modelService")
	private ModelService modelService;
	@Resource(name = "voucherFacade")
	private VoucherFacade voucherFacade;
	@Resource(name = "cartFacade")
	private CartFacade cartFacade;
	@Resource(name = "commerceCartCalculationStrategy")
	private CommerceCartCalculationStrategy commerceCartCalculationStrategy;
	@Resource(name = "storeFinderService")
	private SiteOneStoreFinderService storeFinderService;
	@Resource(name = "productConfiguredPopulator")
	private ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator;
	@Resource(name = "siteOnestoreDetailsFacade")
	private SiteOneStoreDetailsFacade siteOnestoreDetailsFacade;
	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;
	@Resource(name = "userFacade")
	private UserFacade userFacade;
	@Resource(name = "commerceCartService")
	private CommerceCartService commerceCartService;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;
	@Resource(name = "cartFacade")
	private SiteOneCartFacade siteOneCartFacade;
	@Resource(name = "productService")
	private ProductService productService;
	@Resource(name = "i18nService")
	private I18NService i18nService;
	@Resource(name = "messageSource")
	private MessageSource messageSource;
	@Resource(name = "siteoneSavedListFacade")
	private SiteoneSavedListFacade siteoneSavedListFacade;
	@Resource(name = "storeFinderFacade")
	private SiteOneStoreFinderFacade storeFinderFacade;
	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;
	@Resource(name = "accProductFacade")
	private ProductFacade productFacade;
	@Resource(name = "siteOneShareEmailFacade")
	private SiteOneShareEmailFacade siteOneShareEmailFacade;
	@Resource(name = "sessionService")
	private SessionService sessionService;
	@Resource(name = "deliveryModeService")
	private DeliveryModeService deliveryModeService;
	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
	@Resource(name = "cartConverter")
	private Converter<CartModel, CartData> cartConverter;

	public static final String SUCCESS = "Success";
	private static final String MIXEDCART_ENABLED_BRANCHES = "MixedCartEnabledBranches";
	
	private static final Logger LOG = Logger.getLogger(CartsPageController.class);
	
	@GetMapping(value = "/{cartId}")
	@ResponseBody
	@Operation(operationId ="getCart", summary = "Get the current cart", description = "Returns the current cart.")
	@ApiBaseSiteIdUserIdAndCartIdParam
	public CartWsDTO getCart(@PathVariable("cartId") final String fromCartId, @Parameter(description = "Store id") @RequestParam(required = true) final String storeId, @Parameter(description = "unitId") @RequestParam(required = false) final String unitId,
			@Parameter(description = "The GUID of the user's cart that will be merged with the anonymous cart.") @RequestParam(required = false) final String toMergeCartGuid,
			@Parameter(description = "Response configuration. This is the list of fields that should be returned in the response body.", schema = @Schema(allowableValues = "BASIC, DEFAULT, FULL")) @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields,
			@RequestParam(value = "isGuest", defaultValue="false") boolean isGuest)
	{
		String errorMsg = "";

		try 
		{
			final PointOfServiceData pointOfServiceData = getPointOfServiceData(unitId, storeId, fromCartId, isGuest);
			
			if (!StringUtils.isEmpty(toMergeCartGuid))
			{
				if (!isUserCart(toMergeCartGuid))
				{
					throw new CartException("Cart is not current user's cart", CartException.CANNOT_RESTORE, toMergeCartGuid);
				}

				((SiteOneCartFacade) cartFacade).restoreCartMerge(fromCartId, toMergeCartGuid);
             }
			return displayCart(errorMsg, pointOfServiceData);

        }
		catch (final CommerceCartMergingException e)
		{
			throw new CartException("Couldn't merge carts", CartException.CANNOT_MERGE, e);
		}
		catch (final CommerceCartRestorationException e)
		{
			throw new CartException("Couldn't restore cart", CartException.CANNOT_RESTORE, e);
		}
		catch (final CartException e)
		{
			throw new CartException("Cart is not current user's cart", CartException.CANNOT_RESTORE, toMergeCartGuid);
 	    }
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method getCart");
	    }
		
	}
	
	@PostMapping(value = "/{cartId}/update")
	@ResponseBody
	@Operation(operationId ="updateCart", summary = "Update the current cart", description = "Returns the updated cart.")
	@ApiBaseSiteIdUserIdAndCartIdParam
	public CartWsDTO updateCartQuantities(@PathVariable("cartId") final String cartId, @Parameter(description = "Store id") @RequestParam(required = true) final String storeId,  @Parameter(description = "unitId") @RequestParam(required = false) final String unitId,
			@Parameter(description = "entryNumber") @RequestParam(required = true) final long entryNumber,
			@Parameter(description = "Update Cart object", required = true) @RequestBody UpdateQuantityForm form, final HttpServletRequest request,
			@Parameter(description = "Response configuration. This is the list of fields that should be returned in the response body.", schema = @Schema(allowableValues = "BASIC, DEFAULT, FULL")) @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields,
			@RequestParam(value = "isGuest", defaultValue="false") boolean isGuest)
    {
		
		try
		{
			final PointOfServiceData pointOfServiceData = getPointOfServiceData(unitId, storeId, cartId, isGuest);
			if (getCartFacade().hasEntries())
			{
				final CartModificationData cartModification = cartFacade.updateCartEntry(entryNumber,
							form.getQuantity().longValue());
				String errorMsg = addFlashMessage(form, request, cartModification);
				return displayCart(errorMsg, pointOfServiceData);


			} else {
				
				throw new RuntimeException("Could not update the cart"); 
            }

		}
		catch (final CommerceCartModificationException ex)
		{
			LOG.error("Couldn't update product with the entry number: " + entryNumber + ".", ex);
			throw new RuntimeException("Couldn't update product with the entry number"); 
		}
		catch (final RuntimeException ex)
		{
			LOG.error(ex);
			throw new RuntimeException("Could not update the cart"); 

		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method updateCartQuantities");
	    }

   }
	
	protected String addFlashMessage(final UpdateQuantityForm form, final HttpServletRequest request, final CartModificationData cartModification)
	{
		String errorMsg = "";
		if (cartModification.getQuantity() == form.getQuantity().longValue())
		{
			// Success

			if (cartModification.getQuantity() == 0)
			{
				// Success in removing entry
				errorMsg +=  getMessageSource().getMessage("basket.page.message.remove", null, getI18nService().getCurrentLocale());
				
			}
			else
			{
				// Success in update quantity
				errorMsg +=  getMessageSource().getMessage("basket.page.message.update", null, getI18nService().getCurrentLocale());
				
			}
		}
		else if (cartModification.getQuantity() > 0)
		{
			errorMsg +=  getMessageSource().getMessage("basket.page.message.update.reducedNumberOfItemsAdded.lowStock", new Object[]{ XSSFilterUtil.filter(cartModification.getEntry().getProduct().getName()),
							Long.valueOf(cartModification.getQuantity()), form.getQuantity(),request.getRequestURL().append(cartModification.getEntry().getProduct().getUrl())}, getI18nService().getCurrentLocale());
			
		}
		else
		{
			
			errorMsg +=  getMessageSource().getMessage("basket.page.message.update.reducedNumberOfItemsAdded.noStock", new Object[]{ XSSFilterUtil.filter(cartModification.getEntry().getProduct().getName()),
					request.getRequestURL().append(cartModification.getEntry().getProduct().getUrl())}, getI18nService().getCurrentLocale());
	

		}
		return errorMsg;

	}
	
	@PostMapping(value = "/{cartId}/bigBagChecker")
	@ResponseBody
	@Operation(operationId ="bigBagCartProducts", summary = "Update the big bag products", description = "Returns the big bag info")
	@ApiBaseSiteIdUserIdAndCartIdParam
	public BagInfoWsDTO updateBigBagInfo(@PathVariable("cartId") final String cartId, @RequestParam(value = "sku", required = true)
	final String sku, @RequestParam(value = "quantity", required = true)
	final Integer quantity, @RequestParam(value = "bigBagPrice", required = true)
	final Double bigBagPrice, @RequestParam(value = "UOM", required = true)
	final String UOM, @RequestParam(value = "isChecked", required = true)
	final Boolean isChecked, @RequestParam(value = "storeId", required = true)
	final String storeId, @RequestParam(value = "unitId", required = false)
	final String unitId, @RequestParam(value = "isGuest", defaultValue="false") boolean isGuest, final Model model) 
	{
		BagInfoData bagInfodata = new BagInfoData();
		BagInfoWsDTO data = new BagInfoWsDTO();
		
		try
		{
			getPointOfServiceData(unitId, storeId, cartId, isGuest);
			bagInfodata = ((SiteOneCartFacade) cartFacade).updateBigBagInfo(sku,quantity,bigBagPrice,UOM,isChecked);
			MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
			mapperFactory.classMap(BagInfoData.class, BagInfoWsDTO.class);
		    MapperFacade mapper = mapperFactory.getMapperFacade();
		    data = mapper.map(bagInfodata, BagInfoWsDTO.class);
		    return data;
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method updateBigBagInfo");
	    }

	}
	
	
	protected List<String> getVoucherDetails() 
	{
		final CartModel cartModel = cartService.getSessionCart();
		final List<AbstractOrderEntryModel> entries = cartModel.getEntries();
		final List<String> showVoucherList = new ArrayList<>();

		if (null != cartModel.getAppliedCouponCodes())
		{
			showVoucherList.addAll(cartModel.getAppliedCouponCodes());
		}
		return showVoucherList;
	}
	protected void prepareDataForPage(ExtendedModelMap model) 
	{
		String errorMsg = "";
		final CartModel cartModel = cartService.getSessionCart();
		CartData cartData = cartConverter.convert(cartModel);
		String freightCharge = ((SiteOneCartFacade) cartFacade).getFreight(cartModel,cartData);
		cartModel.setFreight(freightCharge);
		cartModel.setTotalTax(0.0d);
		boolean isMixedCart = false;
		boolean deliveryDisabled = false;
		PointOfServiceData pos = storeSessionFacade.getSessionStore();
		if(null != storeSessionFacade.getSessionStore()
				&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(MIXEDCART_ENABLED_BRANCHES,
						storeSessionFacade.getSessionStore().getStoreId()))
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
		OrderTypeEnum initialOrderType = null;
		if(cartModel.getOrderType() != null)
		{
			initialOrderType = cartModel.getOrderType();
		}
		if ((null == cartModel.getOrderType() || CollectionUtils.isEmpty(cartModel.getEntries())) || ((SiteOneCartFacade) cartFacade).getDefaultFulfillment(cartModel,cartData,initialOrderType,splitMixedCart))
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
		if(null != cartModel.getOrderType() && cartModel.getOrderType().getCode().equalsIgnoreCase("DELIVERY"))
		{
			if(!((SiteOneCartFacade) cartFacade).isEligibleForDelivery(cartModel,cartData))
			{
				cartModel.setOrderType(OrderTypeEnum.PICKUP);
			}
		}
		if(pos!=null && BooleanUtils.isTrue(pos.getDeliveryfullfillment()) && BooleanUtils.isNotTrue(pos.getPickupfullfillment()) && BooleanUtils.isNotTrue(pos.getShippingfullfillment()))
		{
			cartModel.setOrderType(OrderTypeEnum.DELIVERY);
		}
		if ((isMixedCart || splitMixedCart ) && CollectionUtils.isEmpty(cartModel.getEntries())
				|| (CollectionUtils.isEmpty(cartModel.getEntries().stream()
						.filter(entry -> null != entry.getDeliveryMode() && entry.getDeliveryMode().getCode().equalsIgnoreCase("standard-net"))
						.collect(Collectors.toList()))))
		{
			cartModel.setDeliverableItemTotal(0.0d);
		}
		if ((isMixedCart || splitMixedCart ) && CollectionUtils.isEmpty(cartModel.getEntries())
				|| (CollectionUtils.isEmpty(cartModel.getEntries().stream()
						.filter(entry -> null != entry.getDeliveryMode() && entry.getDeliveryMode().getCode().equalsIgnoreCase("free-standard-shipping"))
						.collect(Collectors.toList()))))
		{
			cartModel.setShippableItemTotal(0.0d);
		}
		
		if(!isMixedCart && null != storeSessionFacade.getSessionStore())
		{
			for(AbstractOrderEntryModel entry : cartModel.getEntries())
			{
				if (entry.getProduct().getIsDeliverable() == Boolean.FALSE)
				{
					deliveryDisabled = true;
				}
				if(!splitMixedCart)
				{
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
			model.addAttribute(CART_DELIVERY_DISABLED, "true");

		}
		final List<String> showVoucherList = new ArrayList<>();
		if (null != cartModel.getAppliedCouponCodes())
		{
			showVoucherList.addAll(cartModel.getAppliedCouponCodes());
		}
		final PointOfServiceModel cartPos = cartModel.getPointOfService();
		final PointOfServiceData sessionPos = storeSessionFacade.getSessionStore();
		final List<AbstractOrderEntryModel> entries = cartModel.getEntries();
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
			ProductData productData = getProductData(abstractOrderEntryModel.getProduct());
			if (abstractOrderEntryModel.getIsPromotionEnabled() != null) {
				if(abstractOrderEntryModel.getIsPromotionEnabled().booleanValue()) {
					applyProductPromotion(productData);
				} else {
					removeProductPromotion(productData);
				}
			}
			if (null != productData.getCouponCode()
					&& showVoucherList.contains(productData.getCouponCode()))
			{
				showVoucherList.remove(productData.getCouponCode());
			}
			if (null != offlineProduct && Boolean.TRUE.equals(offlineProduct))
			{
				errorMsg += getMessageSource().getMessage("cart.product.nla.message", new Object[]{ abstractOrderEntryModel.getProduct().getProductShortDesc() }, getI18nService().getCurrentLocale());		
				try
				{
					cartFacade.updateCartEntry(abstractOrderEntryModel.getEntryNumber(), 0);

				}
				catch (final CommerceCartModificationException e)
				{
					LOG.warn("Couldn't remove product with the entry number: " + abstractOrderEntryModel.getEntryNumber() + ".", e);
				}

			}

		}
		if (null != sessionPos && null != sessionPos.getStoreId())
		{
			if (null != cartPos && !cartPos.getStoreId().equalsIgnoreCase(sessionPos.getStoreId())
					&& CollectionUtils.isNotEmpty(cartModel.getEntries()))
			{
				errorMsg += getMessageSource().getMessage("checkout.orderreview.pricechanges", null, getI18nService().getCurrentLocale());
				if (null == storeFinderFacade.getStoreForId(cartPos.getStoreId()))
				{
					errorMsg +=  getMessageSource().getMessage("cart.store.nla.message", new Object[]{ sessionPos.getStoreId(), sessionPos.getAddress().getFormattedAddress() }, getI18nService().getCurrentLocale());
				}
			}
		}
		
		cartData = cartConverter.convert(cartModel);
		if (null != cartData.getStockAvailableOnlyHubStore() && cartData.getStockAvailableOnlyHubStore() 
				&& !((SiteOneCartFacade) cartFacade).getDefaultFulfillment(cartModel,cartData,initialOrderType,splitMixedCart))
		{
			cartModel.setOrderType(OrderTypeEnum.PARCEL_SHIPPING);
		}
		if(cartModel.getOrderType() != null && splitMixedCart 
				&& !((SiteOneCartFacade) cartFacade).isAllShippingOnlyEntries(cartModel, cartData,initialOrderType))
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
			errorMsg +=  getMessageSource().getMessage("cart.price.unavailable", new Object[]{  sessionPos.getAddress().getPhone() }, getI18nService().getCurrentLocale());
		}


		CartData sessionCartData = prepareParentDataForPage();
		Map<String, List<String>> stockStatusForParcelShippingList = ((SiteOneCartFacade) cartFacade).parcelShippingMessageForProducts(sessionCartData,model);
		model.addAttribute("cartData",sessionCartData);
		model.addAttribute("errorMsg",errorMsg);
		model.addAttribute("showVoucherList",showVoucherList);
		model.addAttribute("inStockparcelShippableProductList",stockStatusForParcelShippingList.get("InStock"));
		model.addAttribute("notInStockparcelShippableProductList",stockStatusForParcelShippingList.get("OutOfStock"));

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
			LOG.error("Unable to apply coupon code", e);
		}
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
			LOG.error("Unable to remove coupon code", e);
		}
	}
	
	public ProductData getProductData(final ProductModel productModel)
	{
		final ProductData productData = new ProductData();
		PointOfServiceData storeId=null;
		final List<ProductOption> promotionOptions = new ArrayList<>(Arrays.asList(ProductOption.PROMOTIONS));
		if (null != storeSessionFacade.getSessionStore())
		{
			storeId = storeSessionFacade.getSessionStore();
			if (!(siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("RetailBranches",
					storeId.getStoreId()) && StringUtils.isNotBlank(storeId.getCustomerRetailId())))
			{
				promotionOptions.add(0, ProductOption.PRICE);
			}
		}
		
		getProductConfiguredPopulator().populate(productModel, productData, promotionOptions);
		return productData;
	}
	
	protected CartData createProductList() 
	{
		final CartData cartData = cartFacade.getSessionCartWithEntryOrdering(false);
		createProductEntryList(cartData);
		return cartData;
	}
	
	protected void createProductEntryList(final CartData cartData)
	{
		boolean hasPickUpCartEntries = false;
		if (cartData.getEntries() != null && !cartData.getEntries().isEmpty())
		{
			for (final OrderEntryData entry : cartData.getEntries())
			{
				if (!hasPickUpCartEntries && entry.getDeliveryPointOfService() != null)
				{
					hasPickUpCartEntries = true;
				}
				
			}
		}
	}
	
	protected CartData prepareParentDataForPage()
	{
		return createProductList();
	}

	
	@PostMapping(value = "/{cartId}/add")
	@ResponseBody
	@Operation(operationId ="addToCart", summary = "add a product to cart.", description = "Add a product to cart.")
	@ApiBaseSiteIdUserIdAndCartIdParam
	public AddToCartResponseWsDTO addToCart(@PathVariable("cartId") final String cartId,
			@Parameter(description = "Response configuration. This is the list of fields that should be returned in the response body.", schema = @Schema(allowableValues = "BASIC, DEFAULT, FULL")) @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields,
			@Parameter(description = "productCodePost") @RequestParam(required = true) final String code,
			@Parameter(description = "promoProductCode") @RequestParam(required = false) final String promoProductcode,
			@Parameter(description = "isCouponEnabled") @RequestParam(required = false) final boolean isCouponEnabled,
			@Parameter(description = "productPostPLPQty") @RequestParam(defaultValue = "1", required = false) final int productPostPLPQty,
			@Parameter(description = "Store id") @RequestParam(required = true) final String storeId,
			@Parameter(description = "unitId") @RequestParam(required = false) final String unitId,
			@Parameter(description = "Add to Cart object", required = true) @RequestBody SiteOneAddToCartForm form)
	{
		String errorMsg = "";
		long qty = 0l;
		if (productPostPLPQty == 1)
		{
			qty = form.getQty();
		}
		else
		{
			qty = productPostPLPQty;
		}
		final String inventoryUOMId = form.getInventoryUomId();
		if (LOG.isDebugEnabled())
		{
			LOG.debug("getSessionCart");
		}
		boolean restore = true;
		AddToCartResponseData responseData = new AddToCartResponseData();
		try
		{
			getPointOfServiceData(unitId, storeId, cartId, false);
            final CartModificationData cartModification = siteOneCartFacade.addToCart(code, qty, inventoryUOMId, false, form.getStoreId());
			final CartModel cartModel = getCartService().getSessionCart();
			if (CollectionUtils.isNotEmpty(cartModel.getEntries()))
			{
				for (final AbstractOrderEntryModel entry : cartModel.getEntries())
				{
					if (null != promoProductcode && promoProductcode.equalsIgnoreCase(entry.getProduct().getCode()) && isCouponEnabled)
					{
						entry.setIsPromotionEnabled(isCouponEnabled);
						modelService.save(entry);
					}
				}
			}
			ProductData productData = updateParcelShippingforProduct(cartModification, inventoryUOMId, code);
			responseData = ((SiteOneCartFacade) cartFacade).getCartResponseData(productData, cartModification.getQuantityAdded());
			if (cartModification.getQuantityAdded() == 0L)
			{
				errorMsg += getMessageSource().getMessage("basket.information.quantity.noItemsAdded." + cartModification.getStatusCode(), null, getI18nService().getCurrentLocale());
				
			}
			else if (cartModification.getQuantityAdded() < qty)
			{
				errorMsg += getMessageSource().getMessage("basket.information.quantity.reducedNumberOfItemsAdded." + cartModification.getStatusCode(), null, getI18nService().getCurrentLocale());
		
			}
			responseData.setErrorMsg(errorMsg);
			return getDataMapper().map(responseData, AddToCartResponseWsDTO.class);
		}
		catch (final CommerceCartModificationException ex)
		{
			LOG.info("***********CommerceCartModificationException**********");
			LOG.info(ex);
			logDebugException(ex);
			errorMsg += getMessageSource().getMessage("basket.error.occurred", null, getI18nService().getCurrentLocale());
			responseData.setErrorMsg(errorMsg);
			return getDataMapper().map(responseData, AddToCartResponseWsDTO.class);
		}
		catch (final ResourceAccessException ex)
		{
			logDebugException(ex);
			errorMsg += "<div>Price not available please contact store</div>";
			responseData.setErrorMsg(errorMsg);
			return getDataMapper().map(responseData, AddToCartResponseWsDTO.class);

		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method addToCart");
	    }
		
	}
	
	@PostMapping(value = "/{cartId}/voucher/apply")
	@ResponseBody
	@Operation(operationId ="applyVoucher", summary = "apply voucher to cart.", description = "Apply voucher to cart.")
	@ApiBaseSiteIdUserIdAndCartIdParam
	public CartWsDTO applyVoucherAction(@PathVariable("cartId") final String cartId,
			@Parameter(description = "Response configuration. This is the list of fields that should be returned in the response body.", schema = @Schema(allowableValues = "BASIC, DEFAULT, FULL")) @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields,
			@Parameter(description = "Store id") @RequestParam(required = true) final String storeId,
		   @Parameter(description = "unitId") @RequestParam(required = false) final String unitId,
		   @Parameter(description = "Voucher object", required = true) @RequestBody VoucherForm form,
			@RequestParam(value = "isGuest", defaultValue="false") boolean isGuest)
	{
		
		String errorMsg = "";
		PointOfServiceData pointOfServiceData = null;
		try
		{
			pointOfServiceData = getPointOfServiceData(unitId, storeId, cartId, isGuest);
            voucherFacade.applyVoucher(form.getVoucherCode().toUpperCase());
			final CartModel cartModel = cartService.getSessionCart();
			final int couponAppliedCount = cartModel.getAppliedCouponCodes().size();

			if (couponAppliedCount == 1)
			{
					errorMsg += getMessageSource().getMessage("text.voucher.apply.applied.success", new Object[] { form.getVoucherCode() }, getI18nService().getCurrentLocale());
			}
			else
			{
				if (siteOneCartFacade.releaseAppliedCouponCodes(cartModel, form.getVoucherCode()))
				{
						
					errorMsg += getMessageSource().getMessage("text.voucher.no.more.apply.coupon.error", new Object[] { form.getVoucherCode() }, getI18nService().getCurrentLocale());
				}

			}
			final List<String> showVoucherList =  getVoucherDetails();
			
		}
		catch (final VoucherOperationException e)
		{
			errorMsg += getMessageSource().getMessage(e.getMessage(), null, 
					getMessageSource().getMessage("text.voucher.apply.invalid.error", null, getI18nService().getCurrentLocale()),
					getI18nService().getCurrentLocale());
         }
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method applyVoucherAction");
	    }
		try
		{
			return displayCart(errorMsg, pointOfServiceData);
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method applyVoucherAction");
	    }
		
	}
	
		
	private List<SavedListDataDTO> getAllSavedList()
	{
		if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
		{

			final boolean isAssembly = false;
			final List<SavedListData> allWishlist = siteoneSavedListFacade.getAllSavedListForEdit(isAssembly);
			MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
			mapperFactory.classMap(SavedListData.class, SavedListDataDTO.class);
		    MapperFacade mapper = mapperFactory.getMapperFacade();
		    List<SavedListDataDTO> savedListDTO = mapper.mapAsList(allWishlist, SavedListDataDTO.class);
		    return savedListDTO;
			
		}
		return null;
	}
	
	
	@PostMapping(value = "/{cartId}/voucher/remove")
	@Operation(operationId ="removeCartVoucher", summary = "Deletes a voucher defined for the current cart.", description = "Deletes a voucher based on the voucherId defined for the current cart.")
	@ApiBaseSiteIdUserIdAndCartIdParam
	@ResponseBody
	public CartWsDTO removeCartVoucher(@PathVariable("cartId") final String cartId,
					@Parameter(description = "Response configuration. This is the list of fields that should be returned in the response body.", schema = @Schema(allowableValues = "BASIC, DEFAULT, FULL")) @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields,
					@Parameter(description = "Store id") @RequestParam(required = true) final String storeId,
					@Parameter(description = "unitId") @RequestParam(required = false) final String unitId,
					@Parameter(description = "Voucher object", required = true) @RequestBody VoucherForm form,
					@RequestParam(value = "isGuest", defaultValue="false") boolean isGuest)
	{
		List<String> showVoucherList = null;
		String errorMsg = "";
		PointOfServiceData pointOfServiceData = null;
		try {
			pointOfServiceData = getPointOfServiceData(unitId, storeId, cartId, isGuest);
            voucherFacade.releaseVoucher(form.getVoucherCode());
			showVoucherList =  getVoucherDetails();
			return displayCart(errorMsg, pointOfServiceData);
			
        } catch (final VoucherOperationException e) {
				
			errorMsg += getMessageSource().getMessage("text.voucher.release.error", new Object[] { form.getVoucherCode() }, getI18nService().getCurrentLocale());

			return displayCart(errorMsg, pointOfServiceData);
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method removeCartVoucher");
	    }
	
	}	
	

	protected void logDebugException(final Exception ex)
	{
	if (LOG.isDebugEnabled())
	{
		LOG.debug(ex);
	}
	}

	public I18NService getI18nService() {
		return i18nService;
	}

	public void setI18nService(I18NService i18nService) {
		this.i18nService = i18nService;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	

	public CartService getCartService() {
		return cartService;
	}

	public void setCartService(CartService cartService) {
		this.cartService = cartService;
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
	public void setProductConfiguredPopulator(final ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator)
	{
		this.productConfiguredPopulator = productConfiguredPopulator;
	}
	
	protected boolean isUserCart(final String toMergeCartGuid)
	{
		if (getCartFacade() instanceof CommerceWebServicesCartFacade)
		{
			final CommerceWebServicesCartFacade commerceWebServicesCartFacade = (CommerceWebServicesCartFacade) getCartFacade();
			return commerceWebServicesCartFacade.isCurrentUserCart(toMergeCartGuid);
		}
		return true;
	}
	
	protected CartWsDTO displayCart(String errorMsg, PointOfServiceData pointOfServiceData) {
		boolean isGuestCheckoutEnabled = ((SiteOneCartFacade) cartFacade).isGuestCheckoutEnabled();
		boolean isSegmentLevelShippingEnabled = false;
		B2BUnitData unit = storeSessionFacade.getSessionShipTo();
		if(unit != null)
		{
			isSegmentLevelShippingEnabled = ((SiteOneB2BUnitFacade) b2bUnitFacade).setIsSegmentLevelShippingEnabled(unit.getTradeClass());
		}
		else
		{
			isSegmentLevelShippingEnabled = ((SiteOneB2BUnitFacade) b2bUnitFacade).setIsSegmentLevelShippingEnabled("guest");
		}
		ExtendedModelMap model = new ExtendedModelMap();
		prepareDataForPage(model);
		List<SavedListDataDTO> savedListDTO = getAllSavedList();
		Date creationTime = siteOneCartFacade.getCartCreationTime();
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		MapperFacade mapper = mapperFactory.getMapperFacade();
		mapperFactory.classMap(CartData.class,CartWsDTO.class);
		CartWsDTO cartWsDTO = mapper.map((CartData)model.get("cartData"), CartWsDTO.class);
		cartWsDTO.setCreationTime(creationTime);
		cartWsDTO.setPointOfService(pointOfServiceData);
		cartWsDTO.setErrorMsg(errorMsg +(String)model.get("errorMsg"));
		cartWsDTO.setAllWishlist(savedListDTO);
		cartWsDTO.setShowVoucherList((List<String>)model.get("showVoucherList"));
		cartWsDTO.setIsGuestCheckoutEnabled(isGuestCheckoutEnabled);
		cartWsDTO.setIsSegmentLevelShippingEnabled(isSegmentLevelShippingEnabled);
		if(StringUtils.isNotEmpty((String)model.get(CART_DELIVERY_DISABLED))) {
			cartWsDTO.setIsCartDeliveryDisabled((String)model.get(CART_DELIVERY_DISABLED));
		}		
		if (CollectionUtils.isNotEmpty((List<String>)model.get("inStockparcelShippableProductList"))) {
			cartWsDTO.setInStockparcelShippableProductList((List<String>)model.get("inStockparcelShippableProductList"));
		}
		if (CollectionUtils.isNotEmpty((List<String>)model.get("notInStockparcelShippableProductList"))) {
			cartWsDTO.setNotInStockparcelShippableProductList((List<String>)model.get("notInStockparcelShippableProductList"));
		}
		if (null != storeSessionFacade.getSessionStore() && siteOneFeatureSwitchCacheService
				.isBranchPresentUnderFeatureSwitch("BulkDeliveryBranches", storeSessionFacade.getSessionStore().getStoreId()))
		{
			cartWsDTO.setIsBulkDeliveryBranch(true);
		}
		else
		{
			cartWsDTO.setIsBulkDeliveryBranch(false);
		}
		boolean isBulkBigBag = false;
		if (null != storeSessionFacade.getSessionStore() && siteOneFeatureSwitchCacheService
				.isBranchPresentUnderFeatureSwitch("BulkBigBag", storeSessionFacade.getSessionStore().getStoreId()))
		{
			if (null != storeSessionFacade.getSessionStore().getBigBag())
			{
				isBulkBigBag = storeSessionFacade.getSessionStore().getBigBag();
			}
		}
		cartWsDTO.setIsBulkBigBagEnabled(isBulkBigBag);
		return cartWsDTO;
	}
	
	@GetMapping(value = "/{cartId}/fullfillmentStatus")
	@Operation(operationId ="fullfillmentStatus", summary = "Update the fulfillment type", description = "Update the fulfillment type")
	@ApiBaseSiteIdUserIdAndCartIdParam
	public @ResponseBody Boolean cartFullfillmentStatus(@RequestParam("orderType") final String orderType,
			@PathVariable("cartId") final String cartId, 
			@RequestParam("storeId") final String storeId,
			@Parameter(description = "unitId") @RequestParam(required = false) final String unitId)
	{
		try
		{
			getPointOfServiceData(unitId, storeId, cartId, false);
			
			final Boolean isEligibleProduct = ((SiteOneCartFacade) cartFacade).setFullfillmentOrderType(orderType);

			return isEligibleProduct;
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method cartFullfillmentStatus");
	    }
		
	}
	
	@GetMapping(value = "/shareCartEmail/{cartCode}")
	@Operation(operationId ="shareCart", summary="Share the Cart", description="Share the details of Cart")
	@ApiBaseSiteIdUserIdAndCartIdParam
	public @ResponseBody String shareCartEmail(@PathVariable("cartCode") final String code,
			@RequestParam(value = "email", required = true) final String emails,
			@RequestParam(value = "storeId", required = true) final String storeId,
			@Parameter(description = "unitId") @RequestParam(required = false) final String unitId)
	{
		try
		{
			getPointOfServiceData(unitId, storeId, code, false);
			
			siteOneShareEmailFacade.shareCartEmailForCode(code, emails);
			Gson gson = new Gson();
			return gson.toJson(SUCCESS);
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method shareCartEmail");
	    }
		
	}
	
	@PostMapping(value = "/{cartId}/clipCoupon/apply")
	@ResponseBody
	@Operation(operationId ="applyClipCouponCart", summary = "apply Clip Coupon to cart.", description = "Apply Clip Coupon to cart.")
	@ApiBaseSiteIdUserIdAndCartIdParam
	public CartWsDTO applyClipCouponCart(@PathVariable("cartId") final String cartId,
			@Parameter(description = "storeId") @RequestParam(required = true) final String storeId,
		   @Parameter(description = "unitId") @RequestParam(required = false) final String unitId,
		   @Parameter(description = "promoProductcode") @RequestParam(required = true) final String promoProductcode, 
		   @Parameter(description = "isCouponEnabled") @RequestParam(required = true) final boolean isCouponEnabled, 
		   @Parameter(description = "isGuest") @RequestParam(defaultValue = "false", required = false) final boolean isGuest)
	{
		String errorMsg="";
		try
		{
			final PointOfServiceData pointOfServiceData = getPointOfServiceData(unitId, storeId, cartId, isGuest);
			
			((SiteOneCartFacade) cartFacade).enablePromotionToProduct(promoProductcode, isCouponEnabled);
			
			return displayCart(errorMsg, pointOfServiceData);
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method applyClipCouponCart");
	    }
		
	}
	
	private PointOfServiceData getPointOfServiceData(final String unitId, final String storeId, final String cartId, final boolean isGuest) {
		final B2BUnitData unit;
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
		((SiteOneCartFacade) cartFacade).restoreSessionCart(cartId);
		return pointOfServiceData;
	}
	
	@ResponseBody
	@GetMapping(value = "/{cartId}/updateDeliveryMode")
	@Operation(operationId ="updateDeliveryMode", summary = "change the fulfilment options ", description = "Getting threshold value based on fulfillment")
	@ApiBaseSiteIdUserIdAndCartIdParam
	public ThresholdCheckResponseWsDTO updateDeliveryMode(
			@PathVariable("cartId") final String cartId,
			@Parameter(description = "deliveryMode") @RequestParam(value = "deliveryMode", required = true) final String deliveryMode, 
			@Parameter(description = "unitId") @RequestParam(required = false) final String unitId,
			@Parameter(description = "entryNumber") @RequestParam(required = true) final String entryNumber,  
			@Parameter(description = "storeId") @RequestParam(value = "storeId", required = true) final String storeId, 
			@Parameter(description = "isGuest") @RequestParam(value = "isGuest", defaultValue="false") boolean isGuest )
	{
		try
		{
			getPointOfServiceData(unitId, storeId, cartId, isGuest);
			
			ThresholdCheckResponseData thresholdCheckResponseData =  ((SiteOneCartFacade) cartFacade).updateDeliveryModes(deliveryMode,entryNumber,storeId,cartService.getSessionCart());
					
			return getDataMapper().map(thresholdCheckResponseData, ThresholdCheckResponseWsDTO.class);
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method updateDeliveryMode");
	    }
		
	}
	@GetMapping(value = "/{cartId}/showNearbyOverlay")
	@ResponseBody
	@Operation(operationId ="showNearbyOverlay", summary = "Show nearby OverLay for product", description = "Returns list of nearby stores for a product")
	@ApiBaseSiteIdUserIdAndCartIdParam
	public List<StoreLevelStockInfoDataWsDTO> showNearbyOverlay(@PathVariable("cartId") final String cartId, @RequestParam(value = "code") final String code, @RequestParam(value = "storeId", required = true) final String storeId)
	{
		try
		{
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);
			
			final List<StoreLevelStockInfoData> storeLevelStockInfoDataList = siteOneProductFacade.populateStoreLevelStockInfoData(code,false);
			List<StoreLevelStockInfoDataWsDTO> storeLevelStockInfoDataListDTO = new ArrayList<>();
						
			if(CollectionUtils.isNotEmpty(storeLevelStockInfoDataList) ) {
				storeLevelStockInfoDataListDTO = getDataMapper().mapAsList(storeLevelStockInfoDataList, StoreLevelStockInfoDataWsDTO.class,"FULL");
			}
					
			return storeLevelStockInfoDataListDTO;
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method showNearbyOverlay");
	    }
		
	}
	@GetMapping(value = "/{cartId}/addFromOverlay")
	@Operation(operationId ="addFromOverlay", summary = "Add product from overlay", description = "Add products from list of stores")
	@ApiBaseSiteIdUserIdAndCartIdParam
	public @ResponseBody boolean addToCartFromOverlay(@PathVariable("cartId") final String cartId,
			@Parameter(description = "productCodePost") @RequestParam(required = true) final String code,
			@Parameter(description = "quantity") @RequestParam(value = "quantity", defaultValue = "1", required= true)	final long quantity, 
			@Parameter(description = "storeId") @RequestParam(value = "storeId", required = true) final String storeId,
			@Parameter(description = "homeStoreId") @RequestParam(value = "homeStoreId", required = true) final String homeStoreId,
			@Parameter(description = "inventoryUOMId") @RequestParam(value = "inventoryUOMId") final String inventoryUOMId,
			@Parameter(description = "promoProductCode") @RequestParam(required = false) final String promoProductcode,
			@Parameter(description = "isCouponEnabled") @RequestParam(required = false) final boolean isCouponEnabled,
			@Parameter(description = "unitId") @RequestParam(required = false) final String unitId
			)
	
	{
		
		boolean result = false;

		
		try {
			getPointOfServiceData(unitId, homeStoreId, cartId, false);
            updateParcelShippingforProduct( siteOneCartFacade.addToCart(code, quantity, inventoryUOMId, false, storeId) , inventoryUOMId, code);			
			result= true;
			return result;

		}
		catch(final ResourceAccessException | CommerceCartModificationException ex) {
			LOG.info(ex);
			return false;
		}	
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method addToCartFromOverlay");
	    }

	}
	
	public ProductData updateParcelShippingforProduct(final CartModificationData cartModification, final String inventoryUOMId, final String code)
	{				
		ProductData productData = new ProductData();
		if (StringUtils.isNotBlank(inventoryUOMId))
		{
			final ProductModel productModel = productService.getProductForCode(code);
			productConfiguredPopulator.populate(productModel, productData,
					Arrays.asList(ProductOption.PRICE, ProductOption.STOCK, ProductOption.CUSTOMER_PRICE));
			
			productData = siteOneCartFacade.updateProductPrice(cartModification,productData);
			productConfiguredPopulator.populate(productModel, productData,
					Arrays.asList(ProductOption.BASIC, ProductOption.URL, ProductOption.PROMOTIONS, ProductOption.GALLERY));
			if (!siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(MIXEDCART_ENABLED_BRANCHES,
					storeSessionFacade.getSessionStore().getStoreId()))
			{
				final PointOfServiceData posStore = storeSessionFacade.getSessionStore();
				siteOneProductFacade.updateParcelShippingForProduct(productData, posStore != null ? posStore.getStoreId() : null);
			}
		}
		else
		{
			productData = productFacade.getProductForCodeAndOptions(code,
					Arrays.asList(ProductOption.BASIC, ProductOption.URL, ProductOption.PRICE, ProductOption.STOCK, ProductOption.CUSTOMER_PRICE,
							ProductOption.PROMOTIONS));
			if (!siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(MIXEDCART_ENABLED_BRANCHES,
					storeSessionFacade.getSessionStore().getStoreId()))
			{
				final PointOfServiceData posStore = storeSessionFacade.getSessionStore();
				siteOneProductFacade.updateParcelShippingForProduct(productData, posStore != null ? posStore.getStoreId() : null);
			}
		}
		return productData;
	}
	
	@PostMapping(value = "/{cartId}/removeAllEntries")
	@ResponseBody
	@Operation(operationId ="removeAllEntries", summary = "Deletes a cart with a given cart id.", description = "Deletes a cart with a given cart id.")
	@ApiBaseSiteIdUserIdAndCartIdParam
	public void emptyCart(
			@PathVariable("cartId") final String cartId, 
			@Parameter(description = "Store id") @RequestParam(required = true) final String storeId, 
			@Parameter(description = "unitId") @RequestParam(required = false) final String unitId,
			@Parameter(description = "isGuest") @RequestParam(value = "isGuest", defaultValue="false") boolean isGuest)
	{
		try
		{
			getPointOfServiceData(unitId, storeId, cartId, isGuest);
			getCartFacade().removeSessionCart();
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method emptyCart");
	    }
		
	}
	
	
	@GetMapping(value = "/{cartId}/getCartToggleResponse")
	@Operation(operationId ="getCartToggleResponse", summary = "Get the Cart Toggle Response", description = "Get the Cart Toggle Response")
	@ApiBaseSiteIdUserIdAndCartIdParam
	public @ResponseBody CartToggleResponseData getCartToggleResponse(@PathVariable("cartId") final String cartId, 
			@Parameter(description = "fulfillmentType") @RequestParam(required = true) final String fulfillmentType,
			@Parameter(description = "Store id") @RequestParam(required = true) final String storeId, 
			@Parameter(description = "unitId") @RequestParam(required = false) final String unitId,
			@Parameter(description = "isGuest") @RequestParam(value = "isGuest", defaultValue="false") boolean isGuest)
	{
		try
		{
			getPointOfServiceData(unitId, storeId, cartId, isGuest);
			return ((SiteOneCartFacade) cartFacade).setCartToggleResponseData(null, fulfillmentType);
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method getCartToggleResponse");
	    }
		
	}
}
