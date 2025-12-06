package com.siteone.facades.populators;


import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.Config;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.codehaus.plexus.util.StringUtils;

import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.facades.customer.price.SiteOneCspResponse;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.integration.constants.SiteoneintegrationConstants;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import de.hybris.platform.commercefacades.product.PriceDataFactory;
import java.math.BigDecimal;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
/**
 * @author 1091124
 *
 */
public class SiteOneProductCSPPopulator implements Populator<ProductModel, ProductData>
{
	private SiteOneProductFacade siteOneProductFacade;
	private UserFacade userFacade;
	private SiteOneStoreSessionFacade storeSessionFacade;
	private UserService userService;
	private static final Logger LOG = Logger.getLogger(SiteOneProductCSPPopulator.class);
	@Resource(name = "priceDataFactory")
	private PriceDataFactory priceDataFactory;


	@Resource(name = "cartService")
	private CartService cartService;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	/**
	 * @return the siteOneProductFacade
	 */
	public SiteOneProductFacade getSiteOneProductFacade()
	{
		return siteOneProductFacade;
	}

	/**
	 * @param siteOneProductFacade
	 *           the siteOneProductFacade to set
	 */
	public void setSiteOneProductFacade(final SiteOneProductFacade siteOneProductFacade)
	{
		this.siteOneProductFacade = siteOneProductFacade;
	}

	/**
	 * @return the userFacade
	 */
	public UserFacade getUserFacade()
	{
		return userFacade;
	}

	/**
	 * @param userFacade
	 *           the userFacade to set
	 */
	public void setUserFacade(final UserFacade userFacade)
	{
		this.userFacade = userFacade;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final ProductModel productModel, final ProductData productData) throws ConversionException
	{
		if (Config.getBoolean(SiteoneintegrationConstants.INTEGRATION_CSP_ENABLE_KEY, true) && !userFacade.isAnonymousUser()
				&& (productData.getVariantCount() == null
						|| (productData.getVariantCount() != null && productData.getVariantCount().intValue() == 0))) {
			String storeId = null;
			if (null != storeSessionFacade.getSessionStore()) {
				if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",
						storeSessionFacade.getSessionStore().getStoreId()) && productData.getStock() != null) {
					if (StringUtils.isNotEmpty(productData.getStock().getFullfillmentStoreId())) {
						storeId = productData.getStock().getFullfillmentStoreId();
					} else {
						storeId = storeSessionFacade.getSessionStore().getStoreId();
					}
				} else {
					storeId = storeSessionFacade.getSessionStore().getStoreId();
				}
			}
			Long actualQuantity = 1L;
			//04/04/22: This change is to stop multiple pricing /csp calls , where this product already has the csp price by this time.
			boolean isCspPriceexists = false;
			if (cartService.hasSessionCart()) {
			double basePrice = 0.00;
			final CartModel cartModel = cartService.getSessionCart();
			final List<AbstractOrderEntryModel> entries = cartModel.getEntries();
			for (final AbstractOrderEntryModel abstractOrderEntryModel : entries) {
				if (productModel.getCode() !=null && productModel.getCode().equals(abstractOrderEntryModel.getProduct().getCode())) {
					actualQuantity = abstractOrderEntryModel.getQuantity();
					if (abstractOrderEntryModel.getBasePrice() != null && abstractOrderEntryModel.getBasePrice() > 0
							&& BooleanUtils.isTrue(abstractOrderEntryModel.getIsCustomerPrice())
							&& (null != abstractOrderEntryModel.getDeliveryPointOfService()
							&& storeId.equalsIgnoreCase(abstractOrderEntryModel.getDeliveryPointOfService().getStoreId()))
					){
						isCspPriceexists = true;
						DecimalFormat decimalFormat = new DecimalFormat("#.###");
						decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
						if(null != abstractOrderEntryModel.getInventoryUOM())
						{
							if(null != abstractOrderEntryModel.getInventoryUOM().getInventoryMultiplier())
							{
							basePrice = abstractOrderEntryModel.getBasePrice()/abstractOrderEntryModel.getInventoryUOM().getInventoryMultiplier().floatValue();
							}
							else
							{
								basePrice = abstractOrderEntryModel.getBasePrice();
							}
						}
						else
						{
							basePrice = abstractOrderEntryModel.getBasePrice();
						}
						PriceData customerPrice = createPrice(BigDecimal.valueOf(basePrice), cartModel.getCurrency());
						String roundedPrice = decimalFormat.format(customerPrice.getValue());
						customerPrice.setValue(new BigDecimal(roundedPrice));
						productData.setCustomerPrice(customerPrice);
					}
					break;
				}
			}
			}
			if (!isCspPriceexists) {
				final SiteOneCspResponse cspResponse = siteOneProductFacade.getPriceForCustomer(productModel.getCode(),
						Integer.valueOf(Math.toIntExact(actualQuantity)), storeId,"");
				final B2BCustomerModel b2bCustomer = (B2BCustomerModel) getUserService().getCurrentUser();
				LOG.info("SiteOneProductCSPPopulator : productCode - " + productModel.getCode() + " CSP Response - "
						+ cspResponse.isIsSuccess() + " for Store - " + storeId + " and customer - " + b2bCustomer.getEmail());
				if (cspResponse.isIsSuccess() && null != cspResponse.getPrice()) {
					productData.setCustomerPrice(cspResponse.getPrice());
				}
			}
		}
	}

	/**
	 * @return the storeSessionFacade
	 */
	public SiteOneStoreSessionFacade getStoreSessionFacade()
	{
		return storeSessionFacade;
	}

	/**
	 * @param storeSessionFacade
	 *           the storeSessionFacade to set
	 */
	public void setStoreSessionFacade(final SiteOneStoreSessionFacade storeSessionFacade)
	{
		this.storeSessionFacade = storeSessionFacade;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService()
	{
		return userService;
	}

	/**
	 * @param userService
	 *           the userService to set
	 */
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}


	public CartService getCartService() {
		return cartService;
	}

	public void setCartService(CartService cartService) {
		this.cartService = cartService;
	}
	protected PriceData createPrice(final BigDecimal val, final CurrencyModel currencyIso)
	{
		return priceDataFactory.create(PriceDataType.BUY, val, currencyIso);
	}

}
