/**
 *
 */
package com.siteone.facades.cart;

import de.hybris.platform.commercefacades.bag.data.BagInfoData;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.CartModificationData;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.voucher.exceptions.VoucherOperationException;
import de.hybris.platform.commerceservices.order.CommerceCartMergingException;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.order.CommerceCartRestorationException;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.order.exceptions.CalculationException;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.siteone.core.enums.OrderTypeEnum;
import com.siteone.facade.AddToCartResponseData;
import com.siteone.facade.CartToggleResponseData;
import com.siteone.facade.ThresholdCheckResponseData;
import com.siteone.facades.customer.price.SiteOneCartCspPriceData;


/**
 * @author Abdul Rahman Sheikh M
 *
 */
public interface SiteOneCartFacade extends CartFacade
{
	void updateShipToAndPOS(final CartModel cart);

	/*
	 * populatePromotion flag is used to call promotion populator explicitly.
	 *
	 */
	public CartModificationData addToCart(final String code, final long quantity, final String inventoryUOMId,
			final boolean populatePromotion, final String storeId) throws CommerceCartModificationException;

	public boolean releaseAppliedCouponCodes(final CartModel cartModel, String couponCode) throws VoucherOperationException;

	public int getTotalItems();

	public boolean setFullfillmentOrderType(final String orderType);
	
	public boolean isGuestCheckoutEnabled(String storeId);

	public boolean isGuestCheckoutEnabled();

	public SiteOneCartCspPriceData fetchCSPOnLoad(final CartModel cart, String sku) throws CalculationException;
	
	public BagInfoData updateBigBagInfo(String sku, Integer quantity, Double bigBagPrice, String uOM, Boolean isChecked);


	/**
	 * @return
	 */
	long getCartAge();

	Date getCartCreationTime();

	void updateParcelShipping(final CartModel cartModel);

	/**
	 * @param cartData
	 * @return
	 */
	Map<String, List<String>> parcelShippingMessageForProducts(CartData cartData, Model model);

	/**
	 * @param cartModel,cartData
	 * @return
	 */
	void updateFullfillmentDetails(final CartModel cartModel, final CartData cartData);

	void restoreSessionCart(String guid);

	boolean isEligibleForDelivery(final CartModel cartModel, final CartData cartData);

	void restoreCartMerge(String fromMergeCartGuid, String toMergeCartGuid)
			throws CommerceCartRestorationException, CommerceCartMergingException;

	void enablePromotionToProduct(String productCode, boolean isCouponEnabled);

	boolean isGuestOrHomeowner(final CartModel cartModel);

	public ThresholdCheckResponseData updateDeliveryModes(final String deliveryMode, final String entryNumber,
			final String storeId, final CartModel cartModel);

	PriceData getTotalPriceOfAddedProduct(final ProductData productData, final long quantity);

	PriceData getTotalPriceOfAddedProduct(final ProductData productData, final long quantity, final String inventoryUomId);

	AddToCartResponseData getCartResponseData(final ProductData productData, final long quantity);

	public ProductData updateProductPrice(final CartModificationData cartModification, final ProductData productData);

	public boolean showDeliveryFeeMessage(final CartData cartData, final CartModel cartModel);

	void updateFullfillmentMessage(final ProductData product, final PointOfServiceData store);

	void updateShippingStore(final ProductData product, final PointOfServiceData store);

	public CommerceCartParameter populateParameterData(final CartModel cartModel, final long quantity, final String inventoryUOMId,
			final String storeId, final ProductModel product);

	void addMultipleProductsToCart(final List<CommerceCartParameter> parameterList);

	CartToggleResponseData setCartToggleResponseData(CartData cartData, String fulfillmentType);

	public boolean getDefaultFulfillment(CartModel cartModel, CartData cartData, OrderTypeEnum initialOrderType, boolean splitMixedCart);
	
	public boolean isAllShippingOnlyEntries(CartModel cartModel, CartData cartData, OrderTypeEnum initialOrderType);

	/**
	 * @param cartModel
	 * @return
	 */
	public String getFreight(CartModel cartModel, CartData cartData);
	
	public void restoreCartAndMergeForLogin(HttpServletRequest request);

	/**
	 * @param sku
	 * @param quantity
	 * @param UOM
	 * @return
	 */
	public Integer getNumberOfBigBagCalculation(String sku, Integer quantity, String UOM);

}
