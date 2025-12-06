/**
 *
 */
package com.siteone.core.services.impl;

import de.hybris.order.calculation.domain.Order;
import de.hybris.platform.ruleengineservices.calculation.NumberedLineItem;
import de.hybris.platform.ruleengineservices.calculation.impl.DefaultRuleEngineCalculationService;
import de.hybris.platform.ruleengineservices.rao.AbstractOrderRAO;
import de.hybris.platform.ruleengineservices.rao.DiscountRAO;
import de.hybris.platform.ruleengineservices.rao.OrderEntryRAO;
import de.hybris.platform.order.CartService;
import de.hybris.platform.core.model.order.CartModel;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jakarta.annotation.Resource;

import com.google.common.base.Preconditions;
import com.siteone.core.services.SiteOneRuleEngineCalculationService;
import com.siteone.core.util.SiteOneConsumableQuantityUtil;


/**
 * @author BS
 *
 */
public class DefaultSiteOneRuleEngineCalculationService extends DefaultRuleEngineCalculationService
		implements SiteOneRuleEngineCalculationService
{

	@Resource(name = "cartService")
	private CartService cartService;
	
	@Override
	public DiscountRAO addOrderEntryLevelDiscountLimit(final OrderEntryRAO orderEntryRao, final boolean absolute,
			final BigDecimal amount, final int consumedQty)
	{
		Preconditions.checkArgument(consumedQty >= 0, "consumed quantity can't be negative");
		final Order cart = this.getAbstractOrderRaoToOrderConverter().convert(orderEntryRao.getOrder());
		final NumberedLineItem lineItem = this.getLineItemLookupStrategy().lookup(cart, orderEntryRao);
		int consumableQuantity = 0;
		final CartModel cartModel = getCartService().getSessionCart();
		consumableQuantity = SiteOneConsumableQuantityUtil.calculateQuantity(cartModel, orderEntryRao);
		int inventoryMultiplier = SiteOneConsumableQuantityUtil.inventoryMultiplier(cartModel, orderEntryRao);
		final int qty = consumableQuantity - consumedQty;
		BigDecimal adjustedAmount = BigDecimal.valueOf(0);
		if(absolute) {
			adjustedAmount = amount.multiply(BigDecimal.valueOf(qty));
		}
		else {
			BigDecimal perUnitPrice = lineItem.getBasePrice().getAmount().divide(BigDecimal.valueOf(inventoryMultiplier), 4, RoundingMode.DOWN);
			BigDecimal valueToDiscount = perUnitPrice.multiply(BigDecimal.valueOf(qty));
			BigDecimal fraction = amount.divide(BigDecimal.valueOf(100), 4, RoundingMode.DOWN);
			adjustedAmount = valueToDiscount.multiply(fraction);
		}
		final DiscountRAO discountRAO = this.createAbsoluteDiscountRAO(lineItem, adjustedAmount, qty, true);
		this.getRaoUtils().addAction(orderEntryRao, discountRAO);
		final AbstractOrderRAO cartRao = orderEntryRao.getOrder();
		this.recalculateTotals(cartRao, cart);
		return discountRAO;
	}

	/**
	 * @return the cartService
	 */
	public CartService getCartService()
	{
		return cartService;
	}


	/**
	 * @param cartService the cartService to set
	 */
	public void setCartService(final CartService cartService)
	{
		this.cartService = cartService;
	}
}
