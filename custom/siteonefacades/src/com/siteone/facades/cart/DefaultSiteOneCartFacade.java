/**
 *
 */
package com.siteone.facades.cart;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.bag.data.BagInfoData;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.CartModificationData;
import de.hybris.platform.commercefacades.order.data.CartRestorationData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.order.impl.DefaultCartFacade;
import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.converters.populator.ProductPromotionsPopulator;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.StockData;
import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.voucher.VoucherFacade;
import de.hybris.platform.commercefacades.voucher.exceptions.VoucherOperationException;
import de.hybris.platform.commerceservices.i18n.CommerceCommonI18NService;
import de.hybris.platform.commerceservices.order.CommerceAddToCartStrategy;
import de.hybris.platform.commerceservices.order.CommerceCartMergingException;
import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.order.CommerceCartRestoration;
import de.hybris.platform.commerceservices.order.CommerceCartRestorationException;
import de.hybris.platform.commerceservices.order.CommerceCartService;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.commerceservices.storefinder.StoreFinderService;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.order.DeliveryModeService;
import de.hybris.platform.order.exceptions.CalculationException;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.promotions.PromotionsService;
import de.hybris.platform.promotions.jalo.PromotionsManager.AutoApplyMode;
import de.hybris.platform.promotions.model.PromotionGroupModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.time.TimeService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.util.Config;
import de.hybris.platform.util.DiscountValue;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.enums.OrderTypeEnum;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.BagInfoModel;
import com.siteone.core.model.InventoryUPCModel;
import com.siteone.core.services.SiteOneCalculationService;
import com.siteone.core.services.SiteOneProductService;
import com.siteone.core.services.SiteOneProductUOMService;
import com.siteone.core.services.SiteOneStockLevelService;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.facade.AddToCartResponseData;
import com.siteone.facade.CartToggleResponseData;
import com.siteone.facade.StoreLevelStockInfoData;
import com.siteone.facade.ThresholdCheckResponseData;
import com.siteone.facades.checkout.impl.DefaultSiteOneB2BCheckoutFacade;
import com.siteone.facades.customer.price.SiteOneCartCspPriceData;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.populators.SiteOneStockPopulator;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.product.data.InventoryUOMData;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;


/**
 * @author Abdul Rahman Sheikh M
 *
 */
public class DefaultSiteOneCartFacade extends DefaultCartFacade implements SiteOneCartFacade
{
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneCartFacade.class);
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "storeFinderService")
	private SiteOneStoreFinderService storeFinderService;
	
	@Resource(name = "defaultSiteOneStoreFinderService")
	private SiteOneStoreFinderService siteOneStoreFinderService;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;

	@Resource(name = "siteOneProductUOMService")
	private SiteOneProductUOMService siteOneProductUOMService;

	@Resource(name = "productPromotionsPopulator")
	private ProductPromotionsPopulator siteOneProductPromotionPopulator;

	@Resource(name = "productConfiguredPopulator")
	private ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator;

	@Resource(name = "voucherFacade")
	private VoucherFacade voucherFacade;

	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;

	@Resource(name = "productService")
	private ProductService productService;

	@Resource(name = "productConverter")
	private Converter<ProductModel, ProductData> productConverter;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "stockPopulator")
	private SiteOneStockPopulator stockPopulator;

	@Resource(name = "siteOneProductService")
	private SiteOneProductService siteOneProductService;

	@Resource(name = "calculationService")
	private SiteOneCalculationService calculationService;

	@Resource(name = "siteOneStockLevelService")
	private SiteOneStockLevelService siteOneStockLevelService;

	@Resource(name = "promotionsService")
	private PromotionsService promotionsService;

	@Resource(name = "timeService")
	private TimeService timeService;

	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;
	@Resource(name = "cartFacade")
	private CartFacade cartFacade;
	@Resource(name = "userFacade")
	private UserFacade userFacade;

	@Resource(name = "b2bCheckoutFacade")
	private DefaultSiteOneB2BCheckoutFacade b2bCheckoutFacade;

	@Resource(name = "deliveryModeService")
	private DeliveryModeService deliveryModeService;

	@Resource(name = "storeFinderFacade")
	private StoreFinderFacade storeFinderFacade;

	private MessageSource messageSource;

	@Resource(name = "i18nService")
	private I18NService i18nService;

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "priceDataFactory")
	private PriceDataFactory priceDataFactory;

	@Resource(name = "cartService")
	private CartService cartService;

	@Resource(name = "commerceAddToCartStrategy")
	private CommerceAddToCartStrategy commerceAddToCartStrategy;
	
	@Resource(name = "commerceCartService")
	private CommerceCartService commerceCartService;
	@Resource(name = "commerceCommonI18NService")
	private CommerceCommonI18NService commerceCommonI18NService;

	private static final String CART_CREATION_TIME_COOKIE = "cartCreationTime";
	private static final String HOMEOWNER_CODE = "homeOwner.trade.class.code";
	private static final String CART_RESTORATION = "cart_restoration";
	
   private static final String DC_SHIPPING_THRESHOLD = "DCShippingThreshold";
	private static final String CART_RESTORATION_ERROR_STATUS = "restorationError";

	private static final String CART_RESTORATION_SHOW_MESSAGE = "showRestoration";

	private static final String CART_MERGED = "cartMerged";
	
	@Override
	public CartModificationData addToCart(final String code, final long quantity, final String inventoryUOMId,
			final boolean populatePromotion, final String storeId) throws CommerceCartModificationException, ResourceAccessException
	{
		final ProductModel product = getProductService().getProductForCode(code);
		boolean isMixedCart = false;
		if (null != storeSessionFacade.getSessionStore() && siteOneFeatureSwitchCacheService
				.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches", storeSessionFacade.getSessionStore().getStoreId()))
		{
			isMixedCart = true;
		}
		final CartModel cartModel = getCartService().getSessionCart();
		this.updateShipToAndPOS(cartModel);
		//final String finalStoreID = (isMixedCart ? storeId : null);
		final CommerceCartParameter parameter = populateParameterData(cartModel, quantity, inventoryUOMId, storeId, product);
		final CommerceCartModification modification = getCommerceCartService().addToCart(parameter);
		modification.getEntry().setDeliveryMode(deliveryModeService.getDeliveryModeForCode("pickup"));
		modelService.save(modification.getEntry());

		final CartModificationData cartModificationData = getCartModificationConverter().convert(modification);

		if (populatePromotion)
		{
			siteOneProductPromotionPopulator.populate(product, cartModificationData.getEntry().getProduct());
		}
		final List<InventoryUOMData> inventoryUOMListData = cartModificationData.getEntry().getProduct().getSellableUoms();
		final List<InventoryUOMData> inventoryUOMListnew = new ArrayList();

		if (CollectionUtils.isNotEmpty(inventoryUOMListData))
		{
			for (final InventoryUOMData inventoryUOMData1 : inventoryUOMListData)
			{

				if (!inventoryUOMData1.getHideUOMOnline() && cartModificationData.getEntry().getProduct().getHideUom())
				{
					final AbstractOrderEntryModel cartEntry = cartModel.getEntries()
							.get(cartModificationData.getEntry().getEntryNumber());
					final ProductData productDataUom = calculationService.getProductByUOM(cartEntry,
							Float.valueOf(inventoryUOMData1.getInventoryMultiplier()));
					inventoryUOMData1.setNewUomPrice(productDataUom.getPrice().getValue().floatValue());
					if (productDataUom.getCustomerPrice() != null)
					{
						cartModificationData.getEntry().getBasePrice().setValue(productDataUom.getCustomerPrice().getValue());
						cartModificationData.getEntry().getBasePrice()
								.setFormattedValue("$".concat(productDataUom.getCustomerPrice().getValue().toString()));
						/*
						 * BigDecimal lPrice = new BigDecimal(productDataUom.getCustomerPrice().getValue()); lPrice =
						 * lPrice.setScale(2, BigDecimal.ROUND_HALF_UP); target.setListPrice(lPrice);
						 */
						cartModificationData.getEntry().setListPrice(productDataUom.getCustomerPrice().getValue());
					}
					else
					{
						cartModificationData.getEntry().getBasePrice().setValue(productDataUom.getPrice().getValue());
						cartModificationData.getEntry().getBasePrice()
								.setFormattedValue("$".concat(productDataUom.getPrice().getValue().toString()));
						cartModificationData.getEntry().setListPrice(productDataUom.getPrice().getValue());
					}
					cartModificationData.getEntry().setUomMeasure(inventoryUOMData1.getMeasure());
					cartModificationData.getEntry().setUomPrice(productDataUom.getPrice().getValue());
				}
				inventoryUOMListnew.add(inventoryUOMData1);
			}
			if (cartModificationData.getEntry().getProduct().getHideUom() != null
					&& cartModificationData.getEntry().getProduct().getHideUom())
			{
				cartModificationData.getEntry().getProduct().getSellableUoms().clear();
				cartModificationData.getEntry().getProduct().setSellableUoms(inventoryUOMListnew);
			}
		}

		return cartModificationData;
	}


	/*
	 * @Override public CartModificationData addToCart(final String code, final long quantity) throws
	 * CommerceCartModificationException { final ProductModel product = getProductService().getProductForCode(code);
	 * final CartModel cartModel = getCartService().getSessionCart(); this.updateShipToAndPOS(cartModel); final
	 * CommerceCartParameter parameter = new CommerceCartParameter(); parameter.setEnableHooks(true);
	 * parameter.setCart(cartModel); parameter.setQuantity(quantity); parameter.setProduct(product);
	 * parameter.setUnit(product.getUnit()); parameter.setCreateNewEntry(false);
	 *
	 * final CommerceCartModification modification = getCommerceCartService().addToCart(parameter);
	 *
	 * return getCartModificationConverter().convert(modification); }
	 */

	/**
	 * @param cartModel
	 */
	@Override
	public void updateShipToAndPOS(final CartModel cartModel)
	{
		final PointOfServiceData sessionPos = storeSessionFacade.getSessionStore();
		/*
		 * if (null != sessionPos) { if (null != cartModel.getPointOfService()) { if
		 * (!cartModel.getPointOfService().getStoreId().equalsIgnoreCase(sessionPos. getStoreId())) {
		 * cartModel.setPointOfService(storeFinderService.getStoreForId(sessionPos. getStoreId())); } } else {
		 * cartModel.setPointOfService(storeFinderService.getStoreForId(sessionPos. getStoreId())); } }
		 */

		final B2BUnitData sessionShipTo = storeSessionFacade.getSessionShipTo();
		if (null != sessionShipTo)
		{
			if (null != cartModel.getOrderingAccount())
			{
				if (!cartModel.getOrderingAccount().getUid().equalsIgnoreCase(sessionShipTo.getUid()))
				{
					final B2BUnitModel b2bUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(sessionShipTo.getUid());
					cartModel.setOrderingAccount(b2bUnitModel);
				}
			}
			else
			{
				final B2BUnitModel b2bUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(sessionShipTo.getUid());
				cartModel.setOrderingAccount(b2bUnitModel);
			}
		}


		modelService.save(cartModel);
		modelService.refresh(cartModel);
	}

	@Override
	public CartModificationData updateCartEntry(final long entryNumber, final long quantity)
			throws CommerceCartModificationException
	{
		final CartModel cartModel = getCartService().getSessionCart();
		if (quantity == 0)
		{
			final String coupon = getProductData(cartModel.getEntries().get((int) entryNumber).getProduct()).getCouponCode();
			for (final AbstractOrderEntryModel entry : cartModel.getEntries())
			{
				if (entry.getEntryNumber().longValue() == entryNumber)
				{
					entry.setIsPromotionEnabled(Boolean.FALSE);
					try
					{
						if (null != getProductData(entry.getProduct()).getCouponCode())
						{
							voucherFacade.releaseVoucher(getProductData(entry.getProduct()).getCouponCode());
						}
					}
					catch (final VoucherOperationException e)
					{
						e.printStackTrace();

					}
					modelService.save(entry);
					modelService.refresh(entry);
				}
				else
				{
					if (null != getProductData(entry.getProduct()).getCouponCode()
							&& (getProductData(entry.getProduct()).getCouponCode()).equalsIgnoreCase(coupon))
					{
						try
						{
							voucherFacade.applyVoucher(getProductData(entry.getProduct()).getCouponCode());
						}
						catch (final VoucherOperationException e)
						{
							LOG.error(e);
						}
					}
				}
			}
		}
		this.updateShipToAndPOS(cartModel);
		final CommerceCartParameter parameter = new CommerceCartParameter();
		parameter.setEnableHooks(true);
		parameter.setCart(cartModel);
		parameter.setEntryNumber(entryNumber);
		parameter.setQuantity(quantity);
		String deliveryMode = null;
		Double previousPrice = Double.valueOf(0.0);
		if (CollectionUtils.isNotEmpty(cartModel.getEntries()) && null != cartModel.getEntries().get((int) entryNumber))
		{
			if (null != cartModel.getEntries().get((int) entryNumber).getTotalPrice())
			{
				previousPrice = cartModel.getEntries().get((int) entryNumber).getTotalPrice();
			}

			if (null != cartModel.getEntries().get((int) entryNumber).getDeliveryMode())
			{
				deliveryMode = cartModel.getEntries().get((int) entryNumber).getDeliveryMode().getCode();
			}
		}
		final CommerceCartModification modification = getCommerceCartService().updateQuantityForCartEntry(parameter);
		if (null != deliveryMode)
		{
			if (deliveryMode.equalsIgnoreCase(SiteoneCoreConstants.DELIVERY_DELIVERYMODE_CODE))
			{
				cartModel.setDeliverableItemTotal(cartModel.getDeliverableItemTotal() - previousPrice
						+ (quantity != 0 ? modification.getEntry().getTotalPrice() : 0.0d));
			}
			if (deliveryMode.equalsIgnoreCase(SiteoneCoreConstants.SHIPPING_DELIVERYMODE_CODE))
			{
				cartModel.setShippableItemTotal(cartModel.getShippableItemTotal() - previousPrice
						+ (quantity != 0 ? modification.getEntry().getTotalPrice() : 0.0d));
			}
		}
		modelService.save(cartModel);
		modelService.refresh(cartModel);
		return getCartModificationConverter().convert(modification);
	}

	public ProductData getProductData(final ProductModel productModel)
	{
		final ProductData productData = new ProductData();
		final List<ProductOption> promotionOptions = Arrays.asList(ProductOption.PRICE, ProductOption.PROMOTIONS);
		getProductConfiguredPopulator().populate(productModel, productData, promotionOptions);
		return productData;
	}
	
	@Override
	public Integer getNumberOfBigBagCalculation(String sku, Integer quantity, String UOM)
	{
		final PointOfServiceData sessionStore = storeSessionFacade.getSessionStore();
		if(sessionStore == null || sessionStore.getStoreId() == null)
		{
			return 1;
		}
      
      PointOfServiceModel posModel = siteOneStoreFinderService.getStoreDetailForId(sessionStore.getStoreId());
		final ProductModel product = getProductService().getProductForCode(sku);
		
		if (quantity == null || UOM == null || product == null || posModel == null || posModel.getBigBagSize() == null)
		{
			return 1;
		}
		
		String productDesc = posModel.getBigBagSize().getProductShortDesc();
		if (StringUtils.isBlank(productDesc))
		{
			return 1;
		}

		double weight = 0.0;
		boolean validWeight = false;
		if (product.getUpcData() != null)
		{
			for (InventoryUPCModel upc : product.getUpcData())
	    	  {
	    		  if (StringUtils.isNotBlank(upc.getWeight()) && StringUtils.isNotBlank(upc.getInventoryUPCDesc()))
	    		  {
	    			  if (upc.getInventoryUPCDesc().equalsIgnoreCase(UOM))
	    			  {
	    				 try 
		    			  {
	    					  String weightStr = upc.getWeight().trim();
		    				  Pattern pt = Pattern.compile("[0-9]+\\.?[0-9]*");
		    				  Matcher mt = pt.matcher(weightStr);    				  
		    				  if (mt.find())
		    				  {
		    					  weight = Double.parseDouble(mt.group()); 
		    					  validWeight = true;
		    					  break;
		    				  }   				  
		    			  }
		    			  catch (NumberFormatException e)
		    			  {
		    				 LOG.error("Invalid weight format: " + upc.getWeight(), e); 
		    			  }
	    			  }
	    		  }
	    	  }
		} 	  
	   
   	  if (!validWeight)
     	  {
     		  return 1;
     	  }
		
		 double tonMultiplier = 1.0;		
		 Pattern pattern = Pattern.compile("(\\d+(\\.\\d+)?)\\s*Ton", Pattern.CASE_INSENSITIVE);
   	 Matcher matcher = pattern.matcher(productDesc);
   	 if (matcher.find()) {
	        tonMultiplier = Double.parseDouble(matcher.group(1));
	    }    
		
		double baseBags = 0;		
		Pattern netTonPattern = Pattern.compile("Net Ton", Pattern.CASE_INSENSITIVE);
		Pattern cubicYardPattern = Pattern.compile("Cubic Yard", Pattern.CASE_INSENSITIVE);
		Matcher netTonMatcher = netTonPattern.matcher(UOM);
		Matcher cubicYardMatcher = cubicYardPattern.matcher(UOM);
		
		if (cubicYardMatcher.find())
		{
			if (weight <= 2000)
			{
				baseBags = quantity;
			}
			else
			{
				baseBags = (quantity * weight) / 2000.0;
			}
		}
		else if (netTonMatcher.find())
		{
			baseBags = (quantity * weight) / 2000.0;
		}
		
		int finalBags = (int) Math.ceil(baseBags / tonMultiplier);
		return Math.max(1, finalBags);
	}
	
	public BagInfoData updateBigBagInfo(String sku, Integer quantity, Double bigBagPrice, String UOM, Boolean isChecked) 
	{
		Integer CURRENCY_PERUNIT_PRICE_DIGITS = Integer.valueOf(Config.getInt("currency.totalprice.digits", 3));
	    CartModel cart = getCartService().getSessionCart();
	    
	    if (cart == null || CollectionUtils.isEmpty(cart.getEntries()))
	    {
	   	 LOG.warn("Cart is null or has no entries");
	   	 return null;
	    }
	    Optional<AbstractOrderEntryModel> entryOpt = cart.getEntries().stream()
	   	    .filter(entry -> entry.getProduct() != null && entry.getProduct().getCode() != null
	   	        && entry.getProduct().getCode().equalsIgnoreCase(sku))
	   	    .findFirst();

	    if (entryOpt.isPresent()) {
	        AbstractOrderEntryModel entry = entryOpt.get();

	        if (entry.getBigBagInfo() == null) {
	            entry.setBigBagInfo(getModelService().create(BagInfoModel.class));
	        }

	        BagInfoModel bigBagInfo = entry.getBigBagInfo();

	        if (BooleanUtils.isTrue(isChecked)) {
	      	   bigBagInfo.setNumberOfBags(getNumberOfBigBagCalculation(sku, quantity, UOM)); // Default to 1
	            if (bigBagPrice != null)
	            {
	            	bigBagInfo.setUnitPrice(String.format("%.2f", bigBagPrice));
		            Double totalPrice = bigBagPrice * bigBagInfo.getNumberOfBags();
		            bigBagInfo.setTotalPrice(String.format("%.2f", totalPrice));
		            BigDecimal bgValue = BigDecimal.valueOf(totalPrice);
		            bigBagInfo.setLocalTotal(bgValue.setScale(3, RoundingMode.DOWN));
		            bigBagInfo.setListPrice(bigBagPrice);
	            }
	            else
	            {
	            	bigBagInfo.setUnitPrice(null);
	            	bigBagInfo.setTotalPrice(null);
	            }	            
	            bigBagInfo.setUOM(UOM);
	            bigBagInfo.setQuantity(quantity.toString());
	            bigBagInfo.setIsChecked(isChecked);
	            final PointOfServiceData sessionStore = storeSessionFacade.getSessionStore();
	            PointOfServiceModel posModel = null;
	            String code = null;
	            if (sessionStore != null && sessionStore.getStoreId() != null) 
	            {
	            	posModel = siteOneStoreFinderService.getStoreDetailForId(sessionStore.getStoreId());
	            }
	            if (posModel != null && posModel.getBigBagSize() != null && posModel.getBigBagSize().getCode() != null) 
	            {
	            	code = posModel.getBigBagSize().getCode();
	            }
	            if (code != null)
	            {
	            	bigBagInfo.setSkuId(code);
	            }
	        } else if (BooleanUtils.isFalse(isChecked)) {
	      	   if (bigBagPrice != null)
	            {
	      		  bigBagInfo.setUnitPrice(String.format("%.2f", bigBagPrice));
	      		  Double totalPrice = bigBagPrice * quantity;
	      		  BigDecimal bgValue = BigDecimal.valueOf(totalPrice);
		           bigBagInfo.setLocalTotal(bgValue.setScale(3, RoundingMode.DOWN));
	            }
	      	   else
	            {
	            	bigBagInfo.setUnitPrice(null);
	            }
	            bigBagInfo.setNumberOfBags(null);
	            bigBagInfo.setTotalPrice(null);
	            bigBagInfo.setUOM(UOM);
	            bigBagInfo.setIsChecked(isChecked);
	        }

	        getModelService().save(bigBagInfo);
	        getModelService().save(entry);
	        getModelService().refresh(entry);

	        BagInfoData bagInfoData = new BagInfoData();

	        if (entry.getBigBagInfo() != null) {  
	      	    bagInfoData.setUnitPrice(entry.getBigBagInfo().getUnitPrice() != null ? 
	      	   		 truncatePrice(Double.valueOf(entry.getBigBagInfo().getUnitPrice()), CURRENCY_PERUNIT_PRICE_DIGITS): null);
	      	           
	      	    bagInfoData.setNumberOfBags(entry.getBigBagInfo().getNumberOfBags() != null ?  
	      	        entry.getBigBagInfo().getNumberOfBags() : null);  

	      	    bagInfoData.setTotalPrice(entry.getBigBagInfo().getTotalPrice() != null ?  
	      	   		 truncatePrice(Double.valueOf(entry.getBigBagInfo().getTotalPrice()), CURRENCY_PERUNIT_PRICE_DIGITS): null);  

	      	    bagInfoData.setUOM(entry.getBigBagInfo().getUOM() != null ?  
	      	        entry.getBigBagInfo().getUOM() : null);  

	      	    if (entry.getBigBagInfo().getIsChecked() == null)
	      	    {
	      	   	 bagInfoData.setIsChecked(null);
	      	    }
	      	    else
	      	    {
	      	   	 bagInfoData.setIsChecked(entry.getBigBagInfo().getIsChecked());
	      	    }
	      	} 

	        return bagInfoData;
	    }

	    return null; 
	}
	
	private String truncatePrice(final Double priceToTruncate, final int toDigits)
	{
		if (null != priceToTruncate)
		{
			final DecimalFormat decimalFormat;
			if (toDigits == 3)
			{
				decimalFormat = new DecimalFormat("#,##0.000");
			}
			else
			{
				decimalFormat = new DecimalFormat("#,##0.00");
			}
			final String truncatedPrice = decimalFormat
					.format(BigDecimal.valueOf(priceToTruncate.doubleValue()).setScale(toDigits, BigDecimal.ROUND_HALF_UP));
			return truncatedPrice;
		}
		else
		{
			return null;
		}
	}

	@Override
	public long getCartAge()
	{

		final Cookie cookie = WebUtils.getCookie(
				((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest(), CART_CREATION_TIME_COOKIE);
		return (null != cookie
				? (TimeUnit.DAYS.convert((Math.abs(Long.valueOf(cookie.getValue()) - System.currentTimeMillis())),
						TimeUnit.MILLISECONDS))
				: 0);

	}

	public Date getCartCreationTime()
	{

		final Date cartCreationTime = (getCartService().getSessionCart() != null
				? getCartService().getSessionCart().getCreationtime()
				: null);
		return cartCreationTime;

	}

	/**
	 * @return the siteOneProductUOMService
	 */
	public SiteOneProductUOMService getSiteOneProductUOMService()
	{
		return siteOneProductUOMService;
	}

	/**
	 * @param siteOneProductUOMService
	 *           the siteOneProductUOMService to set
	 */
	public void setSiteOneProductUOMService(final SiteOneProductUOMService siteOneProductUOMService)
	{
		this.siteOneProductUOMService = siteOneProductUOMService;
	}

	public boolean releaseAppliedCouponCodes(final CartModel cartModel, final String couponCode) throws VoucherOperationException
	{
		final int couponAppliedCount = cartModel.getAppliedCouponCodes().size();

		if (couponAppliedCount > 1)
		{
			final Collection<String> appliedCouponCodes = cartModel.getAppliedCouponCodes();
			for (final String appliedCode : appliedCouponCodes)
			{
				if (!appliedCode.equalsIgnoreCase(couponCode))
				{
					voucherFacade.releaseVoucher(appliedCode.toUpperCase());
					return true;
				}
			}

		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facades.cart.SiteOneCartFacade#getTotalItems()
	 */
	@Override
	public int getTotalItems()
	{
		int totalCartItems = 0;
		final CartModel cartModel = getCartService().getSessionCart();
		if (null != cartModel && CollectionUtils.isNotEmpty(cartModel.getEntries()))
		{
			totalCartItems = cartModel.getEntries().size();
		}

		return totalCartItems;
	}

	protected Integer calcTotalItems(final AbstractOrderModel source)
	{
		return Integer.valueOf(source.getEntries().size());
	}

	@Override
	public boolean setFullfillmentOrderType(final String orderType)
	{
		final CartModel cartModel = getCartService().getSessionCart();
		String freightCharge = null;
		
		boolean splitMixedCart = false;
		PointOfServiceData pos= storeSessionFacade.getSessionStore();
		PointOfServiceModel posModel = null;
		if(null != pos && !userFacade.isAnonymousUser()) {
			posModel = storeFinderService.getStoreForId(pos.getStoreId());
			if(siteOneFeatureSwitchCacheService
					.isBranchPresentUnderFeatureSwitch("SplitMixedPickupBranches", pos.getStoreId())) {
				splitMixedCart=true;
				LOG.error("mixed cart splitMixedCart : "+splitMixedCart);
			}
		}
		
		if (orderType != null)
		{
			if (orderType.equalsIgnoreCase(OrderTypeEnum.PARCEL_SHIPPING.getCode()))
			{
				cartModel.setOrderType(OrderTypeEnum.PARCEL_SHIPPING);
				for (final AbstractOrderEntryModel entry : cartModel.getEntries())
				{
					if(pos!=null && pos.getHubStores()!=null) 
					{
						entry.setDeliveryPointOfService(null);		
						entry.setFullfilledStoreType("Hubstore");
						entry.setFullfillmentStoreId(pos.getHubStores().get(0));
					}
					entry.setDeliveryMode(deliveryModeService.getDeliveryModeForCode(SiteoneCoreConstants.SHIPPING_DELIVERYMODE_CODE));
					modelService.save(entry);
				}

			}
			else if (orderType.equalsIgnoreCase(OrderTypeEnum.DELIVERY.getCode()))
			{
				cartModel.setOrderType(OrderTypeEnum.DELIVERY);
				for (final AbstractOrderEntryModel entry : cartModel.getEntries())
				{
					if(splitMixedCart)
					{
						if(BooleanUtils.isNotTrue(entry.getIsShippingOnlyProduct())) {
							LOG.error("mixed cart delivery : "+entry.getProduct().getCode());
							entry.setDeliveryPointOfService(null);	
							entry.setFullfilledStoreType("HomeStore");
							entry.setFullfillmentStoreId(pos.getStoreId());
							entry.setDeliveryMode(deliveryModeService.getDeliveryModeForCode(SiteoneCoreConstants.DELIVERY_DELIVERYMODE_CODE));
							modelService.save(entry);
						}
						else if(pos!=null && pos.getHubStores()!=null)
						{
							entry.setDeliveryPointOfService(null);	
							entry.setFullfilledStoreType("Hubstore");
							entry.setFullfillmentStoreId(pos.getHubStores().get(0));
							entry.setDeliveryMode(deliveryModeService.getDeliveryModeForCode(SiteoneCoreConstants.SHIPPING_DELIVERYMODE_CODE));
							modelService.save(entry);
						}
					}
					else {
						entry.setDeliveryPointOfService(null);
						entry.setDeliveryMode(deliveryModeService.getDeliveryModeForCode(SiteoneCoreConstants.DELIVERY_DELIVERYMODE_CODE));
						modelService.save(entry);
					}
				}
				freightCharge = getFreight(cartModel,null);
			}
			else
			{
				cartModel.setOrderType(OrderTypeEnum.PICKUP);
				for (final AbstractOrderEntryModel entry : cartModel.getEntries())
				{
					if(splitMixedCart)
					{
						if(BooleanUtils.isNotTrue(entry.getIsShippingOnlyProduct())) {
							LOG.error("mixed cart pickup : "+entry.getProduct().getCode());
							entry.setDeliveryPointOfService(null);	
							entry.setFullfilledStoreType("HomeStore");
							entry.setFullfillmentStoreId(pos.getStoreId());
							entry.setDeliveryMode(deliveryModeService.getDeliveryModeForCode(SiteoneCoreConstants.PICKUP_DELIVERYMODE_CODE));
							modelService.save(entry);
						}
						else
						{
							entry.setDeliveryPointOfService(null);	
							entry.setFullfilledStoreType("Hubstore");
							entry.setFullfillmentStoreId(pos.getHubStores().get(0));
							entry.setDeliveryMode(deliveryModeService.getDeliveryModeForCode(SiteoneCoreConstants.SHIPPING_DELIVERYMODE_CODE));
							modelService.save(entry);
						}
					}
					else {
						entry.setDeliveryPointOfService(null);
						entry.setDeliveryMode(deliveryModeService.getDeliveryModeForCode(SiteoneCoreConstants.PICKUP_DELIVERYMODE_CODE));
						modelService.save(entry);
					}
				}
			}
			if (freightCharge != null)
			{
				cartModel.setFreight(freightCharge);
			}
			else
			{
				cartModel.setFreight(null);
			}
			modelService.save(cartModel);
			modelService.refresh(cartModel);
		}
		return true;

	}

	/**
	 * Updates the parcel shipping for cart entry products
	 *
	 */
	public void updateParcelShipping(final CartModel cartModel)
	{
		if (cartModel != null && CollectionUtils.isNotEmpty(cartModel.getEntries()))
		{
			final List<ProductData> productDataList = getProductDataList(cartModel);
			final PointOfServiceData posStore = storeSessionFacade.getSessionStore();
			if (posStore != null && CollectionUtils.isNotEmpty(productDataList))
			{
				//call to parcel shipping eligibility for all products
				siteOneProductFacade.updateParcelShippingForProducts(productDataList, posStore.getStoreId());
				cartModel.getEntries().forEach(entry -> {
					if (entry.getProduct() != null)
					{
						productDataList.forEach(product -> {
							if (product.getCode().equalsIgnoreCase(entry.getProduct().getCode()))
							{
								entry.getProduct().setIsShippable(product.getIsShippable());
							}
						});
					}

				});
			}
		}
	}

	public void updateFullfillmentDetails(final CartModel cartModel, final CartData cartData)
	{
		if (cartModel != null && CollectionUtils.isNotEmpty(cartModel.getEntries()))
		{
			boolean mixedCart = false;
			PointOfServiceData pos= storeSessionFacade.getSessionStore();
			if(null != pos && !userFacade.isAnonymousUser()) {
				if(siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("SplitMixedPickupBranches", pos.getStoreId()))
				{
					mixedCart=true;
				}
			}
			String modelProductCode = null;
			for (final AbstractOrderEntryModel entry : cartModel.getEntries())
			{
				modelProductCode = entry.getProduct().getCode();
				PointOfServiceModel posModel = null;
				if (cartData != null && CollectionUtils.isNotEmpty(cartData.getEntries()))
				{
					for (final OrderEntryData cartDataEntry : cartData.getEntries())
					{
						if (cartDataEntry.getProduct() != null && cartDataEntry.getProduct().getCode().equals(modelProductCode))
						{
							if (cartDataEntry.getProduct().getFullfillmentStoreId() != null)
							{
								entry.setFullfillmentStoreId(cartDataEntry.getProduct().getFullfillmentStoreId());
								entry.setDeliveryPointOfService(null);	
							}
							if (cartDataEntry.getProduct().getFullfilledStoreType() != null)
							{
								entry.setFullfilledStoreType(cartDataEntry.getProduct().getFullfilledStoreType());
							}
							if(cartDataEntry.getProduct().getStockAvailableOnlyHubStore() != null 
									&& BooleanUtils.isTrue(cartDataEntry.getProduct().getStockAvailableOnlyHubStore()))
							{
								entry.setIsShippingOnlyProduct(true);
							}
							else if( cartModel.getOrderType().getCode().equalsIgnoreCase("PICKUP"))
							{
								entry.setDeliveryMode(deliveryModeService.getDeliveryModeForCode(SiteoneCoreConstants.PICKUP_DELIVERYMODE_CODE));	
								entry.setIsShippingOnlyProduct(false);
							}
							else
							{
								entry.setIsShippingOnlyProduct(false);
							}
							if(BooleanUtils.isNotTrue(mixedCart))
							{
								entry.setDeliveryPointOfService(null);
							}
							break;
						}
					}

				}
				modelService.save(entry);
				modelService.refresh(entry);
			}
		}
	}

	public Map<String, List<String>> parcelShippingMessageForProducts(final CartData cartData, final Model model)
	{
		final List<OrderEntryData> inStockparcelShippableProduct = new ArrayList<OrderEntryData>();
		final List<OrderEntryData> notInStockparcelShippableProduct = new ArrayList<OrderEntryData>();
		final List<String> inStockparcelShippableProductList = new ArrayList<String>();
		final List<String> notInStockparcelShippableProductList = new ArrayList<String>();
		final List<ProductData> productCodeList = new ArrayList<ProductData>();
		Map<String, List<ProductData>> list = new HashMap();
		final Map<String, List<String>> stockStatusForParcelShippingList = new HashMap();

		if (cartData != null && CollectionUtils.isNotEmpty(cartData.getEntries()))
		{

			for (final OrderEntryData entry : cartData.getEntries())
			{
				if (entry.getProduct().getIsShippable() == Boolean.TRUE)
				{

					productCodeList.add(entry.getProduct());
					entry.getProduct().setIsAvailableInHub(true);
				}
				else
				{
					notInStockparcelShippableProduct.add(entry);
					notInStockparcelShippableProductList.add(entry.getProduct().getCode());
					entry.getProduct().setIsAvailableInHub(false);
				}
			}


			if (storeSessionFacade.getSessionStore() != null && CollectionUtils.isNotEmpty(productCodeList))
			{
				//call to parcel shipping eligibility for products
				list = siteOneProductFacade.parcelShippingMessageForProducts(productCodeList,
						storeSessionFacade.getSessionStore().getStoreId());
			}
		}
		if (MapUtils.isNotEmpty(list))
		{
			for (final OrderEntryData entry : cartData.getEntries())
			{
				if (CollectionUtils.isNotEmpty(list.get("outOfStock")))
				{
					for (final ProductData productData : list.get("outOfStock"))
					{
						if (productData.getCode().equalsIgnoreCase(entry.getProduct().getCode()))
						{
							notInStockparcelShippableProduct.add(entry);
							notInStockparcelShippableProductList.add(productData.getCode());
						}
					}
				}
				if (CollectionUtils.isNotEmpty(list.get("inStock")))
				{
					for (final ProductData productData : list.get("inStock"))
					{
						if (productData.getCode().equalsIgnoreCase(entry.getProduct().getCode()))
						{
							inStockparcelShippableProduct.add(entry);
							inStockparcelShippableProductList.add(productData.getCode());
						}
					}
				}
			}
		}

		model.addAttribute("inStockparcelShippableProduct", inStockparcelShippableProduct);
		model.addAttribute("notInStockparcelShippableProduct", notInStockparcelShippableProduct);
		stockStatusForParcelShippingList.put("InStock", inStockparcelShippableProductList);
		stockStatusForParcelShippingList.put("OutOfStock", notInStockparcelShippableProductList);
		return stockStatusForParcelShippingList;


	}




	/**
	 *
	 */
	private List<ProductData> getProductDataList(final CartModel cartModel)
	{
		final List<ProductData> productDataList = new ArrayList<>();
		cartModel.getEntries().forEach(entry -> {
			final ProductModel productModel = entry.getProduct();
			if (productModel != null && productModel.getIsShippable() != null && productModel.getIsShippable())
			{
				final ProductData productData = new ProductData();
				productData.setCode(productModel.getCode());
				productData.setIsShippable(productModel.getIsShippable());
				productData.setStock(new StockData());
				stockPopulator.populate(productModel, productData.getStock());
				productDataList.add(productData);
			}

		});
		return productDataList;
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

	/**
	 * To identify whether Guestcheckout is enabled or disabled
	 */
	@Override
	public boolean isGuestCheckoutEnabled()
	{
		final String guestCheckoutAccessFlagValue = siteOneFeatureSwitchCacheService.getValueForSwitch("GuestCheckoutFlag");
		boolean isGuestCheckoutEnabled = false;
		if (guestCheckoutAccessFlagValue.equalsIgnoreCase("true"))
		{
			if (siteOneFeatureSwitchCacheService.getValueForSwitch("GuestChekoutEnabledBranches").equalsIgnoreCase("All"))
			{
				isGuestCheckoutEnabled = true;
			}
			else
			{
				final List<String> arrayOfBranches = new ArrayList<>();
				final String selectedBranches = siteOneFeatureSwitchCacheService.getValueForSwitch("GuestChekoutEnabledBranches");
				if (null != selectedBranches)
				{
					arrayOfBranches.addAll(Arrays.asList(selectedBranches.split(",")));

					if (!CollectionUtils.isEmpty(arrayOfBranches))
					{
						final PointOfServiceData sessionStore = storeSessionFacade.getSessionStore();

						if (null != sessionStore)
						{
							if (arrayOfBranches.stream().anyMatch(t -> t.equalsIgnoreCase(sessionStore.getStoreId())))
							{
								isGuestCheckoutEnabled = true;
							}
							else
							{
								return false;
							}
						}
					}
				}
			}
			if (isGuestCheckoutEnabled == true)
			{
				final List<String> arrayOfBranches = new ArrayList<>();
				final String disabledBranches = siteOneFeatureSwitchCacheService.getValueForSwitch("GuestOrRetailCheckoutDisabledBranches");
				if (null != disabledBranches)
				{
					arrayOfBranches.addAll(Arrays.asList(disabledBranches.split(",")));

					if (!CollectionUtils.isEmpty(arrayOfBranches))
					{
						final PointOfServiceData sessionStore = storeSessionFacade.getSessionStore();

						if (null != sessionStore)
						{
							if (arrayOfBranches.stream().anyMatch(t -> t.equalsIgnoreCase(sessionStore.getStoreId())))
							{
								return false;
							}
							else
							{
								return true;
							}
						}
					}
			   }
		   }

		}
		return false;
   }
	
	@Override
	public boolean isGuestCheckoutEnabled(String storeId)
	{
		final String guestCheckoutAccessFlagValue = siteOneFeatureSwitchCacheService.getValueForSwitch("GuestCheckoutFlag");
		boolean isGuestCheckoutEnabled = false;
		if (guestCheckoutAccessFlagValue.equalsIgnoreCase("true"))
		{
			if (siteOneFeatureSwitchCacheService.getValueForSwitch("GuestChekoutEnabledBranches").equalsIgnoreCase("All"))
			{
				isGuestCheckoutEnabled = true;
			}
			else
			{
				final List<String> arrayOfBranches = new ArrayList<>();
				final String selectedBranches = siteOneFeatureSwitchCacheService.getValueForSwitch("GuestChekoutEnabledBranches");
				if (null != selectedBranches)
				{
					arrayOfBranches.addAll(Arrays.asList(selectedBranches.split(",")));

					if (!CollectionUtils.isEmpty(arrayOfBranches))
					{
						if (null != storeId)
						{
							if (arrayOfBranches.stream().anyMatch(t -> t.equalsIgnoreCase(storeId)))
							{
								isGuestCheckoutEnabled = true;
							}
							else
							{
								return false;
							}
						}
					}
				}
			}
			if (isGuestCheckoutEnabled == true)
			{
				final List<String> arrayOfBranches = new ArrayList<>();
				final String disabledBranches = siteOneFeatureSwitchCacheService.getValueForSwitch("GuestOrRetailCheckoutDisabledBranches");
				if (null != disabledBranches)
				{
					arrayOfBranches.addAll(Arrays.asList(disabledBranches.split(",")));

					if (!CollectionUtils.isEmpty(arrayOfBranches))
					{
						if (null != storeId)
						{
							if (arrayOfBranches.stream().anyMatch(t -> t.equalsIgnoreCase(storeId)))
							{
								return false;
							}
							else
							{
								return true;
							}
						}
					}
			   }
		   }

		}
		return false;
   }

	@Override
	public boolean isEligibleForDelivery(final CartModel cartModel, final CartData cartData)
	{
		final PointOfServiceData sessionStore =  storeSessionFacade.getSessionStore();
		double cartTotal = 0.0d;
		if(cartData.getDeliveryEligibleTotal()!=null)
		{
			cartTotal = cartData.getDeliveryEligibleTotal();
		}
		else
		{
			cartTotal = cartModel.getTotalPrice();
		}
		if ((null != sessionService.getAttribute("guestUser")
				&& sessionService.getAttribute("guestUser").toString().equalsIgnoreCase("guest"))
				|| (null != cartModel.getUnit() && null != cartModel.getUnit().getTradeClass()
						&& cartModel.getUnit().getTradeClass()
								.equalsIgnoreCase(configurationService.getConfiguration().getString(HOMEOWNER_CODE)))
				|| (null != cartModel.getOrderingAccount() && null != cartModel.getOrderingAccount().getTradeClass()
						&& cartModel.getOrderingAccount().getTradeClass()
								.equalsIgnoreCase(configurationService.getConfiguration().getString(HOMEOWNER_CODE))))
		{
			final Map<String, String> branchDeliveryThreshold = siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping(SiteoneCoreConstants.DELIVERY_COST_THRESHOLD_BRANCEHES);
			if(null != sessionStore && null != sessionStore.getDeliveryThresholdForHomeownerOrGuest() && !sessionStore.getDeliveryThresholdForHomeownerOrGuest().isEmpty())
			{
				return (cartTotal  >= Double
						.valueOf(sessionStore.getDeliveryThresholdForHomeownerOrGuest()));
			}
			if(null != sessionStore && null != branchDeliveryThreshold.get(sessionStore.getStoreId()) )
			{
				return (cartTotal  >= Double
						.valueOf(branchDeliveryThreshold.get(sessionStore.getStoreId())));
			}
			else if (null != siteOneFeatureSwitchCacheService.getValueForSwitch("DeliveryCostThreshold"))
			{
				return (cartTotal  >= Double
						.valueOf(siteOneFeatureSwitchCacheService.getValueForSwitch("DeliveryCostThreshold")));
			}
		}
		else if(null != sessionStore && null != sessionStore.getDeliveryThresholdForContractor() && !sessionStore.getDeliveryThresholdForContractor().isEmpty())
		{
			return (cartTotal  >= Double.valueOf(sessionStore.getDeliveryThresholdForContractor()));
		}
		else
		{
			return true;
		}
		return false;
	}

	@Override
	public void restoreSessionCart(final String guid)
	{
		CartModel cartModel = null;
		try
		{
			if (!userFacade.isAnonymousUser())
			{
				cartModel = getCommerceCartService().getCartForGuidAndSiteAndUser(null, baseSiteService.getCurrentBaseSite(),
						userService.getCurrentUser());
			}
			else if (userFacade.isAnonymousUser() && (guid != null && !guid.equals("current")))
			{
				cartModel = getCommerceCartService().getCartForGuidAndSite(guid, baseSiteService.getCurrentBaseSite());
			}
			getCartService().setSessionCart(cartModel);
		}
		catch (final Exception e)
		{
			LOG.error("Couldn't restore cart: " + e.getMessage());
			LOG.error("Exception thrown: " + e);
		}
	}

	@Override
	public void restoreCartMerge(final String fromMergeCartGuid, final String toMergeCartGuid)
			throws CommerceCartRestorationException, CommerceCartMergingException
	{
		if (!isCartAnonymous(fromMergeCartGuid))
		{
			final CartModel fromCart = getCommerceCartService().getCartForGuidAndSite(fromMergeCartGuid,
					getBaseSiteService().getCurrentBaseSite());
			fromCart.setUser(userService.getAnonymousUser());
			getModelService().save(fromCart);
		}

		cartFacade.restoreAnonymousCartAndMerge(fromMergeCartGuid, toMergeCartGuid);
	}

	protected boolean isCartAnonymous(final String cartGuid)
	{
		final CartModel cart = getCommerceCartService().getCartForGuidAndSiteAndUser(cartGuid,
				getBaseSiteService().getCurrentBaseSite(), getUserService().getAnonymousUser());
		return cart != null;
	}

	@Override
	public void enablePromotionToProduct(final String productCode, final boolean isCouponEnabled)
	{
		final CartModel cartModel = getCartService().getSessionCart();
		String coupon = null;
		if (CollectionUtils.isNotEmpty(cartModel.getEntries()))
		{
			for (final AbstractOrderEntryModel entry : cartModel.getEntries())
			{
				if (null != productCode && productCode.equalsIgnoreCase(entry.getProduct().getCode()))
				{
					entry.setIsPromotionEnabled(isCouponEnabled);
					coupon = getProductData(entry.getProduct()).getCouponCode();
					modelService.save(entry);
				}
			}
			if (coupon != null)
			{
				for (final AbstractOrderEntryModel entry : cartModel.getEntries())
				{
					if (!productCode.equalsIgnoreCase(entry.getProduct().getCode())
							&& coupon.equalsIgnoreCase(getProductData(entry.getProduct()).getCouponCode()))
					{
						entry.setIsPromotionEnabled(isCouponEnabled);
						modelService.save(entry);
					}
				}
			}
		}
	}

	@Override
	public boolean isGuestOrHomeowner(final CartModel cartModel)
	{
		return ((null != sessionService.getAttribute("guestUser")
				&& sessionService.getAttribute("guestUser").toString().equalsIgnoreCase("guest"))
				|| (null == cartModel.getOrderingAccount())
				|| (null != cartModel.getOrderingAccount() && null != cartModel.getOrderingAccount().getTradeClass()
						&& cartModel.getOrderingAccount().getTradeClass()
								.equalsIgnoreCase(configurationService.getConfiguration().getString(HOMEOWNER_CODE))));
	}

	@Override
	public ThresholdCheckResponseData updateDeliveryModes(final String deliveryMode, final String entryNumber,
			final String storeId, final CartModel cartModel)
	{
		final ThresholdCheckResponseData thresholdCheckResponseData = new ThresholdCheckResponseData();
		final boolean isGuestOrHomeowner = ((SiteOneCartFacade) cartFacade).isGuestOrHomeowner(cartModel);
		double productDiscounts = 0.0d;
		double discountedBasePrice = 0.0d;
		double subTotalValue = 0.0d;
		double discountValue = 0.0d;
		if (null != cartModel && CollectionUtils.isNotEmpty(cartModel.getEntries()))
		{
			for (final AbstractOrderEntryModel entry : cartModel.getEntries())
			{
				if (entry.getEntryNumber().equals(Integer.valueOf(entryNumber)))
				{
					if (null != entry.getDeliveryPointOfService()
							&& !entry.getDeliveryPointOfService().getStoreId().equalsIgnoreCase(storeId))
					{
						modelService.save(entry);
						final Collection<PromotionGroupModel> promotionGroupModels = new ArrayList<PromotionGroupModel>();
						if (getBaseSiteService().getCurrentBaseSite() != null
								&& getBaseSiteService().getCurrentBaseSite().getDefaultPromotionGroup() != null)
						{
							promotionGroupModels.add(getBaseSiteService().getCurrentBaseSite().getDefaultPromotionGroup());
						}
						entry.setIsCustomerPrice(false);
						promotionsService.updatePromotions(promotionGroupModels, cartModel, true, AutoApplyMode.APPLY_ALL,
								AutoApplyMode.APPLY_ALL, timeService.getCurrentTime());
					}
					final List<DiscountValue> productsDiscountsAmount = entry.getDiscountValues();
					if (!productsDiscountsAmount.isEmpty())
					{
						for (final DiscountValue value : productsDiscountsAmount)
						{
							productDiscounts += value.getAppliedValue();
							discountValue += value.getValue();
						}
						discountedBasePrice = entry.getBasePrice() - discountValue;
					}
					subTotalValue = cartModel.getSubtotal() + productDiscounts;
					if (deliveryMode.equalsIgnoreCase(SiteoneCoreConstants.ORDERTYPE_PICKUP_HOME)
							|| deliveryMode.equalsIgnoreCase(SiteoneCoreConstants.ORDERTYPE_PICKUP_NEARBY))
					{
						if (isGuestOrHomeowner && (null != entry.getDeliveryMode()
								&& entry.getDeliveryMode().getCode().equalsIgnoreCase(SiteoneCoreConstants.DELIVERY_DELIVERYMODE_CODE)
								&& null != cartModel.getDeliverableItemTotal()))
						{
							cartModel.setDeliverableItemTotal(cartModel.getDeliverableItemTotal() - entry.getTotalPrice());
						}
						if (null != entry.getDeliveryMode()
								&& entry.getDeliveryMode().getCode().equalsIgnoreCase(SiteoneCoreConstants.SHIPPING_DELIVERYMODE_CODE)
								&& null != cartModel.getShippableItemTotal())
						{
							cartModel.setShippableItemTotal(cartModel.getShippableItemTotal() - entry.getTotalPrice());
							thresholdCheckResponseData.setMaxShippingMessage("");
							checkOtherEntriesForMaxShipping(cartModel, entry, thresholdCheckResponseData);
						}
						entry.setDeliveryMode(
								deliveryModeService.getDeliveryModeForCode(SiteoneCoreConstants.PICKUP_DELIVERYMODE_CODE));
					}
					if (deliveryMode.equalsIgnoreCase(SiteoneCoreConstants.ORDERTYPE_DELIVERY))
					{
						if (null != entry.getDeliveryMode()
								&& entry.getDeliveryMode().getCode().equalsIgnoreCase(SiteoneCoreConstants.SHIPPING_DELIVERYMODE_CODE)
								&& null != cartModel.getShippableItemTotal())
						{
							cartModel.setShippableItemTotal(cartModel.getShippableItemTotal() - entry.getTotalPrice());
							thresholdCheckResponseData.setMaxShippingMessage("");
							checkOtherEntriesForMaxShipping(cartModel, entry, thresholdCheckResponseData);
						}
						entry.setDeliveryMode(
								deliveryModeService.getDeliveryModeForCode(SiteoneCoreConstants.DELIVERY_DELIVERYMODE_CODE));

						if (isGuestOrHomeowner)
						{
							cartModel.setDeliverableItemTotal((null != cartModel.getDeliverableItemTotal()
									? (cartModel.getDeliverableItemTotal() + entry.getTotalPrice())
									: entry.getTotalPrice()));
						}
					}
					if (deliveryMode.equalsIgnoreCase(SiteoneCoreConstants.ORDERTYPE_SHIPPING_NAME))
					{
						if (isGuestOrHomeowner && (null != entry.getDeliveryMode()
								&& entry.getDeliveryMode().getCode().equalsIgnoreCase(SiteoneCoreConstants.DELIVERY_DELIVERYMODE_CODE)
								&& null != cartModel.getDeliverableItemTotal()))
						{
							cartModel.setDeliverableItemTotal(cartModel.getDeliverableItemTotal() - entry.getTotalPrice());
						}
						entry.setDeliveryMode(
								deliveryModeService.getDeliveryModeForCode(SiteoneCoreConstants.SHIPPING_DELIVERYMODE_CODE));
						cartModel.setShippableItemTotal(
								(null != cartModel.getShippableItemTotal() ? (cartModel.getShippableItemTotal() + entry.getTotalPrice())
										: entry.getTotalPrice()));
						updateMaxShippableMessageDetails(cartModel, entry, thresholdCheckResponseData);
					}
					modelService.save(entry);
					modelService.save(cartModel);
					final DecimalFormat df = new DecimalFormat("#.00");
					final PointOfServiceData sessionStore =  storeSessionFacade.getSessionStore();
					if (isGuestOrHomeowner)
					{
						thresholdCheckResponseData.setSelectedItemTotal(Double.valueOf(df.format(cartModel.getDeliverableItemTotal())));
						
						final Map<String, String> branchDeliveryThreshold = siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping(SiteoneCoreConstants.DELIVERY_COST_THRESHOLD_BRANCEHES);
						
						double differenceAmount = 0.00;
						if(null != sessionStore && sessionStore.getDeliveryThresholdForHomeownerOrGuest() != null 
								&& !sessionStore.getDeliveryThresholdForHomeownerOrGuest().isEmpty())
						{
							differenceAmount =  (Double.valueOf(sessionStore.getDeliveryThresholdForHomeownerOrGuest())
									-cartModel.getDeliverableItemTotal());
						}
						else if(null != sessionStore && null != branchDeliveryThreshold.get(sessionStore.getStoreId()) )
						{
							differenceAmount =  (Double.valueOf(branchDeliveryThreshold.get(sessionStore.getStoreId()))
									-cartModel.getDeliverableItemTotal());
						}
						else if (null != siteOneFeatureSwitchCacheService.getValueForSwitch("DeliveryCostThreshold"))
						{
							differenceAmount = (Double.valueOf(siteOneFeatureSwitchCacheService.getValueForSwitch("DeliveryCostThreshold"))
									- cartModel.getDeliverableItemTotal());
						}
						thresholdCheckResponseData.setDifferenceAmount(Double.valueOf(df.format(differenceAmount)));
					}
					else if(null != sessionStore && sessionStore.getDeliveryThresholdForContractor() != null && !sessionStore.getDeliveryThresholdForContractor().isEmpty())
					{
						thresholdCheckResponseData.setDifferenceAmount(Double.valueOf(df.format((Double.valueOf(sessionStore.getDeliveryThresholdForContractor())
								-cartModel.getDeliverableItemTotal()))));
					}
					final NumberFormat fmt = NumberFormat.getCurrencyInstance(Locale.US);
					final PriceData basePrice = createPrice(BigDecimal.valueOf(entry.getBasePrice()), cartModel.getCurrency());
					basePrice.setFormattedValue(fmt.format(BigDecimal.valueOf(entry.getBasePrice())));
					final PriceData entryTotalPrice = createPrice(BigDecimal.valueOf(entry.getTotalPrice()), cartModel.getCurrency());
					entryTotalPrice.setFormattedValue(fmt.format(BigDecimal.valueOf(entry.getTotalPrice())));
					final PriceData subTotal = createPrice(BigDecimal.valueOf(subTotalValue), cartModel.getCurrency());
					subTotal.setFormattedValue(fmt.format(BigDecimal.valueOf(subTotalValue)));
					final PriceData totalDiscounts = createPrice(BigDecimal.valueOf(cartModel.getTotalDiscounts() + productDiscounts),
							cartModel.getCurrency());
					totalDiscounts.setFormattedValue(fmt.format(BigDecimal.valueOf(cartModel.getTotalDiscounts() + productDiscounts)));
					final PriceData discountBasePrice = createPrice(BigDecimal.valueOf(discountedBasePrice), cartModel.getCurrency());
					discountBasePrice.setFormattedValue(fmt.format(BigDecimal.valueOf(discountedBasePrice)));
					final PriceData cartTotalPrice = createPrice(BigDecimal.valueOf(cartModel.getTotalPrice()),
							cartModel.getCurrency());
					cartTotalPrice.setFormattedValue(fmt.format(BigDecimal.valueOf(cartModel.getTotalPrice())));
					thresholdCheckResponseData.setBasePrice(basePrice);
					thresholdCheckResponseData.setEntryTotalPrice(entryTotalPrice);
					thresholdCheckResponseData.setSubTotal(subTotal);
					thresholdCheckResponseData.setTotalDiscounts(totalDiscounts);
					thresholdCheckResponseData.setDiscountBasePrice(discountBasePrice);
					thresholdCheckResponseData.setCartTotalPrice(cartTotalPrice);
					thresholdCheckResponseData
							.setSelectedItemTotalShipping(Double.valueOf(df.format(cartModel.getShippableItemTotal())));
					thresholdCheckResponseData
							.setShowDeliveryFeeMessage(showDeliveryFeeMessage(cartFacade.getSessionCart(), cartModel));
					String threshold = null;
					CartData cartData = cartFacade.getSessionCart();
					if (sessionStore != null && null != sessionStore.getHubStores() && null != sessionStore.getHubStores().get(0))
					{
						final PointOfServiceData pos = ((SiteOneStoreFinderFacade) storeFinderFacade).getStoreForId(sessionStore.getHubStores().get(0));
						final B2BUnitData sessionShipTo = storeSessionFacade.getSessionShipTo();
						if(sessionShipTo != null && sessionShipTo.getShippingThreshold() != null && !sessionShipTo.getShippingThreshold().isEmpty()
								&& (cartData.getIsLABranch() || cartData.getIsTampaBranch() || cartData.getIsShippingFeeBranch()))
						{
							threshold = sessionShipTo.getShippingThreshold();
						}
						else if (pos != null && isGuestOrHomeowner  && pos.getShippingThresholdForHomeownerOrGuest() != null && !pos.getShippingThresholdForHomeownerOrGuest().isEmpty())
                  {
                  	threshold = pos.getShippingThresholdForHomeownerOrGuest();
                  }
                  else if (pos != null && !isGuestOrHomeowner && pos.getShippingThresholdForContractor() != null && !pos.getShippingThresholdForContractor().isEmpty())
                  {
                  	threshold = pos.getShippingThresholdForContractor();
                  }
                  else if (cartData.getIsShippingFeeBranch())
						{
							final Map<String, String> flatrate = siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping(DC_SHIPPING_THRESHOLD);
							final Map<String, String> hubflatrate = siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping(SiteoneCoreConstants.HUB_SHIPPING_THRESHOLD);
							if (null != flatrate.get(storeSessionFacade.getSessionStore().getHubStores().get(0)))
				      	{
								threshold = flatrate.get(storeSessionFacade.getSessionStore().getHubStores().get(0));
				      	}
							else if (null != hubflatrate.get(storeSessionFacade.getSessionStore().getHubStores().get(0)))
							{
								threshold = hubflatrate.get(storeSessionFacade.getSessionStore().getHubStores().get(0));
							}
						}		
						else
						{
							threshold = siteOneFeatureSwitchCacheService.getValueForSwitch("FreeShippingThreshold");
						}
					}
					else
					{
						threshold = siteOneFeatureSwitchCacheService.getValueForSwitch("FreeShippingThreshold");
					}
					final double differenceAmountShipping = ((null != threshold)
									? (Double.valueOf(threshold)
											- cartModel.getShippableItemTotal())
									: 0.00);
					thresholdCheckResponseData.setDifferenceAmountShipping(Double.valueOf(df.format(differenceAmountShipping)));
				}
			}
		}
		return thresholdCheckResponseData;
	}


	protected void updateMaxShippableMessageDetails(final CartModel cartModel, final AbstractOrderEntryModel entry,
			final ThresholdCheckResponseData thresholdCheckResponseData)
	{
		long quantity = entry.getQuantity();
		double price = entry.getTotalPrice();
		for (final AbstractOrderEntryModel cartEntry : cartModel.getEntries())
		{
			if (null != entry.getProduct() && null != entry.getProduct().getCode()
					&& entry.getProduct().getCode().equalsIgnoreCase(cartEntry.getProduct().getCode())
					&& null != cartEntry.getDeliveryMode()
					&& cartEntry.getDeliveryMode().getCode().equalsIgnoreCase(SiteoneCoreConstants.SHIPPING_DELIVERYMODE_CODE))
			{
				quantity += cartEntry.getQuantity();
				price += cartEntry.getTotalPrice();
			}
		}

		if (null != entry.getProduct().getMaxShippableQuantity()
				&& quantity > Long.valueOf(entry.getProduct().getMaxShippableQuantity()))
		{
			thresholdCheckResponseData
					.setMaxShippingMessage(getMessageSource().getMessage("cart.message.product.maximumQuantityExceeded", new Object[]
					{ entry.getProduct().getMaxShippableQuantity() }, i18nService.getCurrentLocale()));
		}
		if (null != entry.getProduct().getMaxShippableDollarAmount() && price > entry.getProduct().getMaxShippableDollarAmount())
		{
			BigDecimal formattedFinalPrice = BigDecimal.valueOf(entry.getProduct().getMaxShippableDollarAmount());
			formattedFinalPrice = formattedFinalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
			thresholdCheckResponseData.setMaxShippingMessage(
					getMessageSource().getMessage("cart.message.product.maximumDollarAmountExceeded", new Object[]
					{ "$".concat(formattedFinalPrice.toString()) }, i18nService.getCurrentLocale()));
		}
	}

	protected void checkOtherEntriesForMaxShipping(final CartModel cartModel, final AbstractOrderEntryModel entry,
			final ThresholdCheckResponseData thresholdCheckResponseData)
	{
		long quantity = 0;
		double price = 0;
		for (final AbstractOrderEntryModel cartEntry : cartModel.getEntries())
		{
			if (null != entry.getProduct() && null != entry.getProduct().getCode()
					&& entry.getProduct().getCode().equalsIgnoreCase(cartEntry.getProduct().getCode())
					&& null != cartEntry.getDeliveryMode()
					&& cartEntry.getDeliveryMode().getCode().equalsIgnoreCase(SiteoneCoreConstants.SHIPPING_DELIVERYMODE_CODE)
					&& !cartEntry.getEntryNumber().equals(entry.getEntryNumber()))
			{
				quantity += cartEntry.getQuantity();
				price += cartEntry.getTotalPrice();
			}
		}
		if ((null != entry.getProduct().getMaxShippableQuantity()
				&& quantity > Long.valueOf(entry.getProduct().getMaxShippableQuantity()))
				|| (null != entry.getProduct().getMaxShippableDollarAmount()
						&& price > entry.getProduct().getMaxShippableDollarAmount()))
		{
			thresholdCheckResponseData.setIsMaxShippingExceeded(true);
		}
	}

	@Override
	public AddToCartResponseData getCartResponseData(final ProductData productData, final long quantity)
	{
		final CartModel cartModel = getCartService().getSessionCart();
		final AddToCartResponseData response = new AddToCartResponseData();
		response.setLineItemsCount(getTotalItems());
		response.setGuid(cartModel.getGuid());
		response.setCode(cartModel.getCode());
		response.setProduct(productData);
		response.setIsGuestCheckoutEnabled(isGuestCheckoutEnabled());
		response.setTotalPrice(getTotalPriceOfAddedProduct(productData, quantity));
		return response;
	}


	@Override
	public PriceData getTotalPriceOfAddedProduct(final ProductData productData, final long quantity, final String inventoryUOMId)
	{
		Integer CURRENCY_PERUNIT_PRICE_DIGITS = Integer.valueOf(Config.getInt("currency.totalprice.digits", 3));
		Integer CURRENCY_UNIT_PRICE_DIGITS = Integer.valueOf(Config.getInt("currency.totalprice.digits", 2));
		BigDecimal totalPrice = new BigDecimal(0);
		final InventoryUPCModel inventoryUPCModel = siteOneProductUOMService.getInventoryUOMById(inventoryUOMId);
		final float inventoryMultiplier = inventoryUPCModel.getInventoryMultiplier();
		if (productData.getCustomerPrice() != null)
		{
			final double uomPrice = commonI18NService.roundCurrency(
					productData.getCustomerPrice().getValue().floatValue() * inventoryMultiplier, CURRENCY_PERUNIT_PRICE_DIGITS);
			totalPrice = BigDecimal.valueOf(uomPrice).setScale(CURRENCY_PERUNIT_PRICE_DIGITS, RoundingMode.HALF_UP);
		}
		else if (productData.getPrice() != null)
		{
			totalPrice = productData.getPrice().getValue();
		}
		totalPrice = totalPrice.multiply(new BigDecimal(quantity)); 
		totalPrice = totalPrice.setScale(CURRENCY_UNIT_PRICE_DIGITS, RoundingMode.HALF_UP);
		
		return createPrice(totalPrice, commonI18NService.getCurrentCurrency());
	}

	/**
	 * @param productData
	 * @param quantity
	 * @return
	 */
	@Override
	public PriceData getTotalPriceOfAddedProduct(final ProductData productData, final long quantity)
	{
		Integer CURRENCY_UNIT_PRICE_DIGITS = Integer.valueOf(Config.getInt("currency.totalprice.digits", 2));
		BigDecimal totalPrice = new BigDecimal(0);
		if (productData.getCustomerPrice() != null)
		{
			totalPrice = productData.getCustomerPrice().getValue();
		}
		else if (productData.getPrice() != null)
		{
			totalPrice = productData.getPrice().getValue();
		}
		totalPrice = totalPrice.multiply(new BigDecimal(quantity));
		totalPrice = totalPrice.setScale(CURRENCY_UNIT_PRICE_DIGITS, RoundingMode.HALF_UP);
		
		return createPrice(totalPrice, commonI18NService.getCurrentCurrency());
	}

	/**
	 * @return the messageSource
	 */
	public MessageSource getMessageSource()
	{
		return messageSource;
	}


	/**
	 * @param messageSource
	 *           the messageSource to set
	 */
	public void setMessageSource(final MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}


	/**
	 * @return the i18nService
	 */
	public I18NService getI18nService()
	{
		return i18nService;
	}


	/**
	 * @param i18nService
	 *           the i18nService to set
	 */
	public void setI18nService(final I18NService i18nService)
	{
		this.i18nService = i18nService;
	}

	protected PriceData createPrice(final BigDecimal val, final CurrencyModel currencyIso)
	{
		return priceDataFactory.create(PriceDataType.BUY, val, currencyIso);
	}

	public ProductData updateProductPrice(final CartModificationData cartModification, final ProductData productData)
	{
		if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
		{
			if (null != productData.getCustomerPrice())
			{
				productData.getCustomerPrice()
						.setFormattedValue("$".concat(cartModification.getEntry().getBasePrice().getValue().toString()));
				productData.getCustomerPrice().setValue(cartModification.getEntry().getBasePrice().getValue());
			}
			else if (null != cartModification.getEntry() && null != cartModification.getEntry().getBasePrice())
			{
				productData.setCustomerPrice(cartModification.getEntry().getBasePrice());
			}
			if (null != productData.getPrice())
			{
				productData.getPrice().setFormattedValue("$".concat(cartModification.getEntry().getUomPrice().toString()));
				productData.getPrice().setValue(cartModification.getEntry().getUomPrice());
			}
			else if (null != cartModification.getEntry() && null != cartModification.getEntry().getUomPrice())
			{
				final PriceData priceData = new PriceData();
				priceData.setValue((cartModification.getEntry().getUomPrice()).setScale(2, RoundingMode.HALF_EVEN));
				productData.setPrice(priceData);
			}
		}
		else
		{
			if (null != productData.getPrice())
			{
				productData.getPrice()
						.setFormattedValue("$".concat(cartModification.getEntry().getBasePrice().getValue().toString()));
				productData.getPrice().setValue(cartModification.getEntry().getBasePrice().getValue());
			}
			else if (null != cartModification.getEntry() && null != cartModification.getEntry().getBasePrice())
			{
				productData.setPrice(cartModification.getEntry().getBasePrice());
			}
		}

		return productData;
	}

	@Override
	public void updateFullfillmentMessage(final ProductData product, final PointOfServiceData store)
	{
		final List<List<Object>> stockLevelData = siteOneStockLevelService.getStockLevelsForNearByStores(product.getCode(),
				new ArrayList<>(Arrays.asList(store)));
		for (final List<Object> stockLevelRowData : stockLevelData)
		{
			if (null != product.getPickupNearbyStoreInfo())
			{
				if (((stockLevelRowData.get(2) != null && (int) stockLevelRowData.get(2) > 0)
						|| (BooleanUtils.isNotTrue(product.getInventoryCheck()) && stockLevelRowData.get(5) != null
								&& BooleanUtils.isTrue((Boolean) stockLevelRowData.get(5)))))
				{
					final long onHandQuantity = (int) stockLevelRowData.get(2);
					product.getPickupNearbyStoreInfo()
							.setStockAvailablityMessage(getMessageSource().getMessage("pickup.nearby.info1", new Object[]
							{ product.getPickupNearbyStoreInfo().getStoreName(), product.getPickupNearbyStoreInfo().getStoreId() },
									i18nService.getCurrentLocale()));
					product.getPickupNearbyStoreInfo().setOnHandQuantity(onHandQuantity);
					product.getDeliveryStoreInfo()
							.setStockAvailablityMessage(getMessageSource().getMessage("delivery.info", new Object[]
							{ product.getPickupNearbyStoreInfo().getStoreName(), product.getPickupNearbyStoreInfo().getStoreId() },
									i18nService.getCurrentLocale()));
					product.getDeliveryStoreInfo().setOnHandQuantity(onHandQuantity);
				}
				else
				{
					if (stockLevelRowData.get(1) != null && (int) stockLevelRowData.get(1) > 4)
					{
						product.getPickupNearbyStoreInfo()
								.setStockAvailablityMessage(getMessageSource().getMessage("pickup.nearby.info2", new Object[]
								{ product.getPickupNearbyStoreInfo().getStoreName(), product.getPickupNearbyStoreInfo().getStoreId() },
										i18nService.getCurrentLocale()));
						product.getPickupNearbyStoreInfo().setOnHandQuantity(0);
						product.getDeliveryStoreInfo()
								.setStockAvailablityMessage(getMessageSource().getMessage("delivery.info2", new Object[]
								{ product.getPickupNearbyStoreInfo().getStoreName(), product.getPickupNearbyStoreInfo().getStoreId() },
										i18nService.getCurrentLocale()));
						product.getDeliveryStoreInfo().setOnHandQuantity(0);
					}
				}
			}
		}
		if (product.getIsDeliverable() == Boolean.FALSE)
		{
			product.getDeliveryStoreInfo().setOnHandQuantity(0);
			product.getDeliveryStoreInfo().setStockAvailablityMessage(
					getMessageSource().getMessage("delivery.unavailable", null, i18nService.getCurrentLocale()));
			product.getDeliveryStoreInfo().setIsEnabled(false);
		}
	}

	@Override
	public void updateShippingStore(final ProductData product, final PointOfServiceData store)
	{
		final ProductModel productModel = getProductService().getProductForCode(product.getCode());
		boolean forceInStock = false;
		if (BooleanUtils.isNotTrue(productModel.getInventoryCheck()))
		{
			forceInStock = true;
		}
		if (BooleanUtils.isTrue(productModel.getIsShippable()) && null != store)
		{
			String hubstore = null;
			final StoreLevelStockInfoData storeLevelStockInfoData = siteOneProductFacade
					.populateHubStoresStockInfoData(productModel.getCode(), true, forceInStock);
			if (storeLevelStockInfoData != null && storeLevelStockInfoData.getStoreId() != null)
			{
				hubstore = storeLevelStockInfoData.getStoreId();
			}
			if (null != hubstore)
			{
				final PointOfServiceData hubStore = ((SiteOneStoreFinderFacade) storeFinderFacade).getStoreForId(hubstore);
				if (null != hubStore)
				{
					final List<List<Object>> stockLevelRowData = siteOneStockLevelService
							.getStockLevelsForNearByStores(product.getCode(), new ArrayList<>(Arrays.asList(hubStore)));
					if ((!CollectionUtils.isEmpty(stockLevelRowData) && !CollectionUtils.isEmpty(stockLevelRowData.get(0)))
							&& ((stockLevelRowData.get(0).get(2) != null && (int) stockLevelRowData.get(0).get(2) > 0)
									|| (stockLevelRowData.get(0).get(1) != null && (int) stockLevelRowData.get(0).get(1) > 4)
									|| (BooleanUtils.isNotTrue(product.getInventoryCheck()) && stockLevelRowData.get(0).get(5) != null
											&& BooleanUtils.isTrue((Boolean) stockLevelRowData.get(0).get(5)))))
					{
						final StoreLevelStockInfoData shippingStoreInfo = new StoreLevelStockInfoData();
						if ((String) stockLevelRowData.get(0).get(0) != null
								&& StringUtils.isNotBlank((String) stockLevelRowData.get(0).get(0)))
						{
							shippingStoreInfo.setStoreId((String) stockLevelRowData.get(0).get(0));
						}
						if (stockLevelRowData.get(0).get(2) != null)
						{
							shippingStoreInfo.setOnHandQuantity((int) stockLevelRowData.get(0).get(2));
						}
						if (stockLevelRowData.get(0).get(1) == null)
						{
							shippingStoreInfo.setInventoryHit(0);
						}
						else
						{
							shippingStoreInfo.setInventoryHit((int) stockLevelRowData.get(0).get(1));
						}
						product.setShippingStoreInfo(shippingStoreInfo);
						product.getShippingStoreInfo().setIsEnabled(true);
					}
				}
			}
		}
	}

	@Override
	public boolean showDeliveryFeeMessage(final CartData cartData, final CartModel cartModel)
	{
		boolean nonLATampaBranch = true;
		boolean thresholdExceeded = true;
		boolean deliveryExists = false;
		final boolean isGuestOrHomeOwner = isGuestOrHomeowner(cartModel);
		boolean showDeliveryFeeMessage = false;
		Double deliveryThreshold = 0.0d;
		boolean isMixedCart = false;
		boolean isSplitMixedEnabled = false;
		if (null != storeSessionFacade.getSessionStore() && siteOneFeatureSwitchCacheService
				.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches", storeSessionFacade.getSessionStore().getStoreId()))
		{
			isMixedCart = true;
		}
		if (null != storeSessionFacade.getSessionStore() && siteOneFeatureSwitchCacheService
				.isBranchPresentUnderFeatureSwitch("SplitMixedPickupBranches", storeSessionFacade.getSessionStore().getStoreId()))
		{
			isSplitMixedEnabled = true;
		}
		if (null != storeSessionFacade.getSessionStore() && siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(
				"SiteoneDeliveryFeeFeatureSwitch", storeSessionFacade.getSessionStore().getStoreId()))
		{
			nonLATampaBranch = false;
		}

		if (null != cartModel && !CollectionUtils.isEmpty(cartModel.getEntries()))
		{
			for (final AbstractOrderEntryModel entry : cartModel.getEntries())
			{
				if (null != entry.getDeliveryMode()
						&& entry.getDeliveryMode().getCode().equalsIgnoreCase(SiteoneCoreConstants.DELIVERY_DELIVERYMODE_CODE))
				{
					deliveryExists = true;
					break;
				}
			}
			if (!isMixedCart && null != cartModel.getOrderType() && OrderTypeEnum.DELIVERY.equals(cartModel.getOrderType()))
			{
				deliveryExists = true;
			}
			final PointOfServiceData sessionStore =  storeSessionFacade.getSessionStore();
			boolean branchThreshold = false;
			if(isGuestOrHomeOwner && null != sessionStore && sessionStore.getDeliveryThresholdForHomeownerOrGuest() != null 
					&& !sessionStore.getDeliveryThresholdForHomeownerOrGuest().isEmpty())
			{
				deliveryThreshold = Double.valueOf(sessionStore.getDeliveryThresholdForHomeownerOrGuest());
				branchThreshold = true;
			}
			else if(!isGuestOrHomeOwner && null != sessionStore && sessionStore.getDeliveryThresholdForContractor() != null 
					&& !sessionStore.getDeliveryThresholdForContractor().isEmpty())
			{
				deliveryThreshold = Double.valueOf(sessionStore.getDeliveryThresholdForContractor());
				branchThreshold = true;
			}
			final Map<String, String> branchDeliveryThreshold = siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping(SiteoneCoreConstants.DELIVERY_COST_THRESHOLD_BRANCEHES);
			if(null != storeSessionFacade.getSessionStore() && null != branchDeliveryThreshold.get(storeSessionFacade.getSessionStore().getStoreId()) && isGuestOrHomeOwner 
					&& !branchThreshold)
			{
				deliveryThreshold = Double
						.valueOf(branchDeliveryThreshold.get(storeSessionFacade.getSessionStore().getStoreId()));
			}
			else if (null != siteOneFeatureSwitchCacheService.getValueForSwitch("DeliveryCostThreshold") && isGuestOrHomeOwner && !branchThreshold)
			{
				deliveryThreshold = Double
						.valueOf(siteOneFeatureSwitchCacheService.getValueForSwitch("DeliveryCostThreshold"));
			}
			
			if ((isGuestOrHomeOwner || branchThreshold ) && (((isMixedCart || isSplitMixedEnabled) && null != cartModel.getDeliverableItemTotal()
					&& ((deliveryThreshold - cartModel.getDeliverableItemTotal()) > 0))
					|| (!isMixedCart && null != cartModel.getTotalPrice() && ((deliveryThreshold - cartModel.getTotalPrice()) > 0))))
			{
				thresholdExceeded = false;
			}
			if (deliveryExists && nonLATampaBranch && thresholdExceeded)
			{
				showDeliveryFeeMessage = true;
			}
		}
		return showDeliveryFeeMessage;
	}

	@Override
	public CommerceCartParameter populateParameterData(final CartModel cartModel, final long quantity, final String inventoryUOMId,
			final String storeId, final ProductModel product)
	{
		final CommerceCartParameter parameter = new CommerceCartParameter();
		parameter.setEnableHooks(true);
		parameter.setCart(cartModel);
		parameter.setQuantity(quantity);
		parameter.setProduct(product);
		parameter.setUnit(product.getUnit());
		parameter.setCreateNewEntry(false);
	
		if (null != inventoryUOMId)
		{
			parameter.setIsBaseUom(Boolean.FALSE);
			final InventoryUPCModel inventoryUOM = product.getUpcData().stream()
					.filter(upc -> upc.getInventoryUPCID().equals(inventoryUOMId)).findFirst().orElse(null);
			parameter.setInventoryUOM(inventoryUOM);
		}
		else
		{
			parameter.setIsBaseUom(Boolean.TRUE);
			parameter.setInventoryUOM(
					product.getUpcData().stream().filter(upc -> upc.getBaseUPC().booleanValue()).findFirst().orElse(null));
		}
		return parameter;
	}

	@Override
	public void addMultipleProductsToCart(final List<CommerceCartParameter> parameterList)
	{
		try
		{
			commerceAddToCartStrategy.addToCart(parameterList);
			final CartModel cartModel = cartService.getSessionCart();
			int i = 0;
			for (final AbstractOrderEntryModel entry : cartModel.getEntries())
			{
				if (!(cartModel.getEntries().get(i).getEntryNumber().equals(Integer.valueOf(i))))
				{
					cartModel.getEntries().get(i).setEntryNumber(Integer.valueOf(i));
					modelService.save(entry);
				}
				i++;
			}
		}
		catch (final CommerceCartMergingException e)
		{
			LOG.error(e.getMessage(), e);
		}
	}

	@Override
	public boolean getDefaultFulfillment(final CartModel cartModel, final CartData cartData, final OrderTypeEnum initialOrderType, final boolean splitMixedCart)
	{

		final Set<Integer> shippingOnlyAlertEntries = new HashSet<>();
		final Set<Integer> pickupOrDeliveryOnlyAlertEntries = new HashSet<>();
		if (null != cartData
				&& (!(BooleanUtils.isTrue(cartData.getIsTampaBranch()) || BooleanUtils.isTrue(cartData.getIsLABranch()))))
		{
			if (!CollectionUtils.isEmpty(cartData.getEntries()))
			{
				for (final OrderEntryData entry : cartData.getEntries())
				{
					if (BooleanUtils.isTrue(entry.getProduct().getStockAvailableOnlyHubStore()))
					{
						shippingOnlyAlertEntries.add(entry.getEntryNumber());
					}
					else
					{
						if (!BooleanUtils.isTrue(entry.getProduct().getOutOfStockImage())
								&& BooleanUtils.isNotTrue(entry.getProduct().getIsShippable()))
						{
							pickupOrDeliveryOnlyAlertEntries.add(entry.getEntryNumber());
						}
					}
				}
			}
		}

		if (!CollectionUtils.isEmpty(shippingOnlyAlertEntries) && !CollectionUtils.isEmpty(pickupOrDeliveryOnlyAlertEntries))
		{
			if(initialOrderType != null && splitMixedCart)
			{
				cartModel.setOrderType(initialOrderType);
			}
			else
			{
				cartModel.setOrderType(OrderTypeEnum.PICKUP);
			}
			modelService.save(cartModel);
			modelService.refresh(cartModel);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isAllShippingOnlyEntries(CartModel cartModel, CartData cartData, OrderTypeEnum initialOrderType)
	{
		final Set<Integer> shippingOnlyAlertEntries = new HashSet<>();
		if (null != cartData
				&& (!(BooleanUtils.isTrue(cartData.getIsTampaBranch()) || BooleanUtils.isTrue(cartData.getIsLABranch()))))
		{
			if (!CollectionUtils.isEmpty(cartData.getEntries()))
			{
				for (final OrderEntryData entry : cartData.getEntries())
				{
					if (BooleanUtils.isTrue(entry.getProduct().getStockAvailableOnlyHubStore()))
					{
						shippingOnlyAlertEntries.add(entry.getEntryNumber());
					}
				}
			}
			if(!CollectionUtils.isEmpty(cartData.getEntries()) && !CollectionUtils.isEmpty(shippingOnlyAlertEntries) 
					&& (cartData.getEntries().size() == shippingOnlyAlertEntries.size()))
			{
				cartModel.setOrderType(OrderTypeEnum.PARCEL_SHIPPING);
				modelService.save(cartModel);
				modelService.refresh(cartModel);
				return true;
			}
		}
		if(initialOrderType != null)
		{
			cartModel.setOrderType(initialOrderType);
		}
		else
		{
			cartModel.setOrderType(OrderTypeEnum.PICKUP);
		}
		modelService.save(cartModel);
		modelService.refresh(cartModel);
		return false;
	}

	@Override
	public CartToggleResponseData setCartToggleResponseData(CartData cartData, final String fulfillmentType)
	{
		final CartModel cartModel = cartService.getSessionCart();
		if (null == cartData)
		{
			cartData = getCartConverter().convert(cartModel);
		}
		final Set<Integer> shippingOnlyAlertEntries = new HashSet<>();
		final Set<Integer> pickupOrDeliveryOnlyAlertEntries = new HashSet<>();
		final Set<Integer> shippingEligibleEntries = new HashSet<>();
		boolean pickupOnly = false;
		double deliveryThresholdTotal = 0.0d;
		DecimalFormat df = new DecimalFormat("#.00");
		if (null != cartData
				&& (!(BooleanUtils.isTrue(cartData.getIsTampaBranch()) || BooleanUtils.isTrue(cartData.getIsLABranch())))
				&& StringUtils.isNotBlank(fulfillmentType))
		{
			final CartToggleResponseData cartToggleResponseData = new CartToggleResponseData();
			if (!CollectionUtils.isEmpty(cartData.getEntries()))
			{
				for (final OrderEntryData entry : cartData.getEntries())
				{
					if (BooleanUtils.isTrue(entry.getProduct().getStockAvailableOnlyHubStore()))
					{
						shippingOnlyAlertEntries.add(entry.getEntryNumber());
						cartModel.getEntries().stream().forEach(entries -> 
						{
   						if(entries.getProduct().getCode().equalsIgnoreCase(entry.getProduct().getCode()))
   						{
   							entries.setIsShippingOnlyProduct(true);
   						}
   						modelService.save(entries);
   						modelService.refresh(entries);
						});
					}
					else
					{
						if ((!BooleanUtils.isTrue(entry.getProduct().getOutOfStockImage()) && BooleanUtils.isNotTrue(entry.getProduct().getIsShippable())))
						{
							pickupOrDeliveryOnlyAlertEntries.add(entry.getEntryNumber());
						}
						else if (!BooleanUtils.isTrue(entry.getProduct().getOutOfStockImage()))
						{
							shippingEligibleEntries.add(entry.getEntryNumber());
						}
					}
					if (BooleanUtils.isFalse(entry.getProduct().getIsDeliverable()))
					{
						pickupOnly = true;
					}
					if(((entry.getFullfilledStoreType() !=null && !entry.getFullfilledStoreType().equalsIgnoreCase("HubStore")) 
							|| entry.getFullfilledStoreType() == null) && BooleanUtils.isTrue(entry.getProduct().getIsDeliverable()))
					{
						deliveryThresholdTotal += entry.getTotalPrice().getValue().doubleValue();
					}
				}
				cartData.setDeliveryEligibleTotal(Double.valueOf(df.format(deliveryThresholdTotal)));
			}

			if (!CollectionUtils.isEmpty(shippingOnlyAlertEntries))
			{
				if (fulfillmentType.equalsIgnoreCase(SiteoneCoreConstants.ORDERTYPE_SHIPPING))
				{
					cartToggleResponseData.setShowNationalShippingFeeMsg(true);
					cartToggleResponseData.setShowShippingThresholdMsg(false);
					if (CollectionUtils.isEmpty(pickupOrDeliveryOnlyAlertEntries) && !CollectionUtils.isEmpty(shippingOnlyAlertEntries) 
							&& CollectionUtils.isEmpty(shippingEligibleEntries))
					{
						cartToggleResponseData.setEnableDelivery(false);
						cartToggleResponseData.setEnablePickup(false);
						cartToggleResponseData.setShowDeliveryThresholdMsg(false);
					}
					else
					{
						cartToggleResponseData.setEnablePickup(true);
						final boolean isEligibleForDelivery = isEligibleForDelivery(cartModel,cartData);
						cartToggleResponseData.setEnableDelivery(isEligibleForDelivery && (!pickupOnly));
						cartToggleResponseData.setShowDeliveryThresholdMsg((!(isEligibleForDelivery || pickupOnly)));
						if(!CollectionUtils.isEmpty(pickupOrDeliveryOnlyAlertEntries))
						{
							cartToggleResponseData.setPickupOrDeliveryOnlyAlertEntries(pickupOrDeliveryOnlyAlertEntries);
						}
					}
				}
				else
				{
					cartToggleResponseData.setShowNationalShippingFeeMsg(false);
					cartToggleResponseData.setShowShippingThresholdMsg(false);
					if (CollectionUtils.isEmpty(shippingOnlyAlertEntries))
					{
						final boolean isEligibleForDelivery = isEligibleForDelivery(cartModel,cartData);
						cartToggleResponseData.setEnableDelivery(isEligibleForDelivery && (!pickupOnly));
						cartToggleResponseData.setShowDeliveryThresholdMsg((!(isEligibleForDelivery || pickupOnly)));
						cartToggleResponseData.setEnablePickup(true);
					}
					else
					{
						cartToggleResponseData.setEnablePickup(true);
						final boolean isEligibleForDelivery = isEligibleForDelivery(cartModel,cartData);
						cartToggleResponseData.setEnableDelivery(isEligibleForDelivery && (!pickupOnly));
						cartToggleResponseData.setShowDeliveryThresholdMsg((!(isEligibleForDelivery || pickupOnly)));
						cartToggleResponseData.setShippingOnlyAlertEntries(shippingOnlyAlertEntries);
					}
				}
				return cartToggleResponseData;
			}
		}
		return null;
	}


	@Override
	public SiteOneCartCspPriceData fetchCSPOnLoad(final CartModel cart, final String sku) throws CalculationException
	{
		final SiteOneCartCspPriceData cartData = calculationService.fetchCartCSPPrice(cart, sku);
		return cartData;
	}


	@Override
	public String getFreight(final CartModel cartModel, CartData cartData)
	{
		if(cartData==null) {
			cartData = getCartConverter().convert(cartModel);
		}
		final boolean isGuest = ((null != sessionService.getAttribute("guestUser")
				&& sessionService.getAttribute("guestUser").toString().equalsIgnoreCase("guest"))
				|| (userService.getCurrentUser() != null && userService.isAnonymousUser(userService.getCurrentUser())));
		final boolean isHomeOwner = (null != cartModel.getOrderingAccount()
				&& null != cartModel.getOrderingAccount().getTradeClass() && cartModel.getOrderingAccount().getTradeClass()
						.equalsIgnoreCase(configurationService.getConfiguration().getString(HOMEOWNER_CODE)));
		String freightCharge = null;
		try
		{
			freightCharge = b2bCheckoutFacade.getFreightCharge(cartData, isHomeOwner, isGuest);
			LOG.error("The frieght charge at fullfillment selection is " + freightCharge);
		}
		catch (final IOException e)
		{
			LOG.error(e);
		}
		catch (final RestClientException e)
		{
			LOG.error(e);
		}
		if (freightCharge != null)
		{
			return freightCharge;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void restoreCartAndMergeForLogin(HttpServletRequest request)
	{
			// no need to merge if current cart has no entry
			if (!cartFacade.hasEntries())
			{
				sessionService.setAttribute(CART_RESTORATION_SHOW_MESSAGE, Boolean.TRUE);
				try
				{
					sessionService.setAttribute(CART_RESTORATION, restoreSavedCartForLogin(null));
				}
				catch (Exception e)
				{
					LOG.error(e);
					sessionService.setAttribute(CART_RESTORATION_ERROR_STATUS,
							CART_RESTORATION_ERROR_STATUS);
				}
			}
			else
			{
				final String sessionCartGuid = cartFacade.getSessionCartGuid();
				final String mostRecentSavedCartGuid = cartFacade.getMostRecentCartGuidForUser(Arrays.asList(sessionCartGuid));
				if (StringUtils.isNotEmpty(mostRecentSavedCartGuid))
				{
					sessionService.setAttribute(CART_RESTORATION_SHOW_MESSAGE, Boolean.TRUE);
					try
					{
						sessionService.setAttribute(CART_RESTORATION,
								mergeCart(mostRecentSavedCartGuid, sessionCartGuid));
						request.setAttribute(CART_MERGED, Boolean.TRUE);
					}
					catch (final CommerceCartRestorationException e)
					{
						LOG.error(e);
						sessionService.setAttribute(CART_RESTORATION_ERROR_STATUS,
								CART_RESTORATION_ERROR_STATUS);
					}
					catch (final CommerceCartMergingException e)
					{
						LOG.error("User saved cart could not be merged", e);
					}
				}
			}
	}
	
	public CartRestorationData restoreSavedCartForLogin(final String guid) throws CommerceCartRestorationException
	{
		if (!hasEntries() && !hasEntryGroups())
		{
			getCartService().setSessionCart(null);
		}

		final CommerceCartParameter parameter = new CommerceCartParameter();
		parameter.setEnableHooks(true);
		final CartModel cartForGuidAndSiteAndUser = getCommerceCartService().getCartForGuidAndSiteAndUser(guid,
				getBaseSiteService().getCurrentBaseSite(), getUserService().getCurrentUser());
		parameter.setCart(cartForGuidAndSiteAndUser);

		return getCartRestorationConverter().convert(restoreCart(parameter));
	}
	private CartRestorationData mergeCart(final String fromUserCartGuid, final String toUserCartGuid)
			throws CommerceCartRestorationException, CommerceCartMergingException
	{
		final BaseSiteModel currentBaseSite = getBaseSiteService().getCurrentBaseSite();
		final CartModel fromCart = getCommerceCartService().getCartForGuidAndSiteAndUser(fromUserCartGuid, currentBaseSite,
				getUserService().getCurrentUser());

		final CartModel toCart = getCommerceCartService().getCartForGuidAndSiteAndUser(toUserCartGuid, currentBaseSite,
				getUserService().getCurrentUser());

		if (fromCart == null && toCart != null)
		{
			return restoreSavedCartForLogin(toUserCartGuid);
		}

		if (fromCart != null && toCart == null)
		{
			return restoreSavedCartForLogin(fromUserCartGuid);
		}

		if (fromCart == null && toCart == null)
		{
			return null;
		}

		final CommerceCartParameter parameter = new CommerceCartParameter();
		parameter.setEnableHooks(true);
		parameter.setCart(toCart);

		final CommerceCartRestoration restoration = restoreCart(parameter);
		parameter.setCart(getCartService().getSessionCart());
		commerceCartService.mergeCarts(fromCart, parameter.getCart(), restoration.getModifications());

		final CommerceCartRestoration commerceCartRestoration = restoreCart(parameter);
		commerceCartRestoration.setModifications(restoration.getModifications());
		getCartService().changeCurrentCartUser(getUserService().getCurrentUser());
		return getCartRestorationConverter().convert(commerceCartRestoration);
	}
	
	private CommerceCartRestoration restoreCart(final CommerceCartParameter parameter)
   {
       final CartModel cartModel = parameter.getCart();
       final CommerceCartRestoration restoration = new CommerceCartRestoration();
       final List<CommerceCartModification> modifications = new ArrayList<>();
       if (cartModel != null)
       {
           if (getBaseSiteService().getCurrentBaseSite().equals(cartModel.getSite()))
           {
               if (LOG.isDebugEnabled())
               {
                   LOG.debug("Restoring from cart " + cartModel.getCode() + ".");
               }
               cartModel.setCalculated(Boolean.FALSE);

               getModelService().save(cartModel);
               getCartService().setSessionCart(cartModel);

               if (LOG.isDebugEnabled())
               {
                   LOG.debug("Cart " + cartModel.getCode() + " was found to be valid and was restored to the session.");
               }
               
               commerceCommonI18NService.setCurrentCurrency(cartModel.getCurrency());
           }
           else
           {
               LOG.warn(String.format("Current Site %s does not equal to cart %s Site %s",
                       getBaseSiteService().getCurrentBaseSite(), cartModel, cartModel.getSite()));
           }
       }
       restoration.setModifications(modifications);
       return restoration;
   }
	
	/**
	 * @return the storeSessionFacade
	 */
	public SiteOneStoreSessionFacade getStoreSessionFacade()
	{
		return storeSessionFacade;
	}


	/**
	 * @param storeSessionFacade the storeSessionFacade to set
	 */
	public void setStoreSessionFacade(SiteOneStoreSessionFacade storeSessionFacade)
	{
		this.storeSessionFacade = storeSessionFacade;
	}


	/**
	 * @return the siteOneStoreFinderService
	 */
	public SiteOneStoreFinderService getSiteOneStoreFinderService()
	{
		return siteOneStoreFinderService;
	}


	/**
	 * @param siteOneStoreFinderService the siteOneStoreFinderService to set
	 */
	public void setSiteOneStoreFinderService(SiteOneStoreFinderService siteOneStoreFinderService)
	{
		this.siteOneStoreFinderService = siteOneStoreFinderService;
	}
	
	public ModelService getModelService()
	{
		return modelService;
	}

	public void setModelService(ModelService modelService)
	{
		this.modelService = modelService;
	}
	
	public SiteOneFeatureSwitchCacheService getSiteOneFeatureSwitchCacheService()
	{
		return siteOneFeatureSwitchCacheService;
	}

	public void setSiteOneFeatureSwitchCacheService(SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService)
	{
		this.siteOneFeatureSwitchCacheService = siteOneFeatureSwitchCacheService;
	}
	
	public ConfigurationService getConfigurationService()
	{
		return configurationService;
	}

	public void setConfigurationService(ConfigurationService configurationService)
	{
		this.configurationService = configurationService;
	}
	
	public SessionService getSessionService()
	{
		return sessionService;
	}
	
	public void setSessionService(SessionService sessionService)
	{
		this.sessionService = sessionService;
	}
	
}
