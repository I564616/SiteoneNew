package com.siteone.core.rule.definitions.action;

import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.ruleengineservices.rao.AbstractRuleActionRAO;
import de.hybris.platform.ruleengineservices.rao.CartRAO;
import de.hybris.platform.ruleengineservices.rao.DiscountRAO;
import de.hybris.platform.ruleengineservices.rao.OrderEntryRAO;
import de.hybris.platform.ruleengineservices.rao.RuleEngineResultRAO;
import de.hybris.platform.ruleengineservices.rule.evaluation.RuleActionContext;
import de.hybris.platform.ruleengineservices.rule.evaluation.actions.AbstractRuleExecutableSupport;


import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;


/**
 * @author BS
 *
 */
public class RuleOrderEntryMaxLimitDiscountRAOAction extends AbstractRuleExecutableSupport
{

	@Resource(name = "cartService")
	private CartService cartService;
	@Override
	public boolean performActionInternal(final RuleActionContext context)
	{
		boolean isPerformed = false;
		BigDecimal discountvalue = BigDecimal.valueOf(0);
		final Map<String, BigDecimal> values = (Map) context.getParameter("max");
		final CartRAO cartRAO = context.getCartRao();
		final BigDecimal discountValueForCartCurrency = values.get(cartRAO.getCurrencyIsoCode());
		final Optional<BigDecimal> amount = this.extractAmountForCurrency(context, context.getParameter("value"));
		final MathContext mc = new MathContext(2);
		if (amount.isPresent())
		{
			final Set<OrderEntryRAO> orderEntries = context.getValues(OrderEntryRAO.class);
			if (CollectionUtils.isNotEmpty(orderEntries))

			{
				for (final OrderEntryRAO orderEntry : orderEntries)
				{
					final int consumableQuantity = this.getConsumptionSupport().getConsumableQuantity(orderEntry);
					if (consumableQuantity > 0)
					{
						final CartRAO cartrao = (CartRAO) orderEntry.getOrder();
						BigDecimal updatedTotalPrice = orderEntry.getTotalPrice();
						CartModel cart = cartService.getSessionCart();
					    
					    if (!CollectionUtils.isEmpty(cart.getEntries()))
					    {
					    Optional<AbstractOrderEntryModel> entryOpt = cart.getEntries().stream()
					            .filter(entry -> entry.getProduct().getCode().equalsIgnoreCase(orderEntry.getProductCode()))
					            .findFirst();

					    if (entryOpt.isPresent()) {
					        AbstractOrderEntryModel entry = entryOpt.get();
					        if (entry.getBigBagInfo() != null && Boolean.TRUE.equals(entry.getBigBagInfo().getIsChecked()))
								{
									BigDecimal bigBagTotal = entry.getBigBagInfo().getLocalTotal();
									if (bigBagTotal == null)
									{
										bigBagTotal = BigDecimal.ZERO;
									}
									updatedTotalPrice = updatedTotalPrice.add(bigBagTotal);
								 }
					    }
					    }
						final BigDecimal discountamount = amount.get().multiply(updatedTotalPrice)
								.divide(BigDecimal.valueOf(100));
						final BigDecimal discountAmountPerQty = discountamount.divide(BigDecimal.valueOf(orderEntry.getQuantity()), 4,
								RoundingMode.CEILING);
						if (discountamount.compareTo(discountValueForCartCurrency) >= 0)
						{
							final BigDecimal disValue = (cartrao.getTotal().subtract(cartrao.getSubTotal())).round(mc);
							if (disValue.compareTo(discountValueForCartCurrency) < 0)
							{
								discountvalue = discountvalue.add(discountamount);
								if (discountvalue.compareTo(discountValueForCartCurrency) > 0)
								{
									final BigDecimal remainingDiscValue = (discountValueForCartCurrency
											.subtract(discountvalue.subtract(discountamount)))
													.divide(BigDecimal.valueOf(orderEntry.getQuantity()), 4, RoundingMode.DOWN);
									isPerformed = this.processOrderEntry(context, orderEntry, remainingDiscValue, true);

								}
								else
								{
									isPerformed = this.processOrderEntry(context, orderEntry, discountValueForCartCurrency
											.divide(BigDecimal.valueOf(orderEntry.getQuantity()), 4, RoundingMode.CEILING), true);
								}
							}
						}
						else
						{
							discountvalue = discountvalue.add(discountamount);
							final BigDecimal disValue = (cartrao.getTotal().subtract(cartrao.getSubTotal())).round(mc);
							if (disValue.compareTo(discountValueForCartCurrency) < 0)
							{
								if (discountvalue.compareTo(discountValueForCartCurrency) < 0)
								{
									isPerformed = this.processOrderEntry(context, orderEntry, discountAmountPerQty, true);

								}
								else
								{
									//final BigDecimal discValue = (cartRAO.getOriginalTotal().subtract(cartRAO.getSubTotal())).round(mc);
									final BigDecimal remainingDiscValue = (discountValueForCartCurrency
											.subtract(discountvalue.subtract(discountamount)))
													.divide(BigDecimal.valueOf(orderEntry.getQuantity()), 4, RoundingMode.CEILING);
									isPerformed = this.processOrderEntry(context, orderEntry, remainingDiscValue, true);
								}
							}

						}
					}
				}
			}
		}
		return isPerformed;

	}

	protected boolean processOrderEntry(final RuleActionContext context, final OrderEntryRAO orderEntryRao, final BigDecimal value,
			final boolean isFixedDisc)
	{
		boolean isPerformed = false;
		final int consumableQuantity = this.getConsumptionSupport().getConsumableQuantity(orderEntryRao);
		if (consumableQuantity > 0)
		{
			final DiscountRAO discount = this.getRuleEngineCalculationService().addOrderEntryLevelDiscount(orderEntryRao,
					isFixedDisc, value);
			if (isFixedDisc)
			{
				discount.setValue(value);
			}
			this.setRAOMetaData(context, new AbstractRuleActionRAO[]
			{ discount });
			this.getConsumptionSupport().consumeOrderEntry(orderEntryRao, consumableQuantity, discount);
			final RuleEngineResultRAO result = context.getValue(RuleEngineResultRAO.class);
			result.getActions().add(discount);
			context.scheduleForUpdate(new Object[]
			{ orderEntryRao, orderEntryRao.getOrder(), result });
			context.insertFacts(new Object[]
			{ discount });
			context.insertFacts(discount.getConsumedEntries());
			isPerformed = true;
		}

		return isPerformed;
	}

	protected boolean processFixedOrderEntry(final RuleActionContext context, final OrderEntryRAO orderEntryRao,
			final BigDecimal valueForCurrency)
	{
		boolean isPerformed = false;
		final int consumableQuantity = this.getConsumptionSupport().getConsumableQuantity(orderEntryRao);
		if (consumableQuantity > 0)
		{
			isPerformed = true;
			final DiscountRAO discount = this.getRuleEngineCalculationService().addFixedPriceEntryDiscount(orderEntryRao,
					valueForCurrency);
			if (Objects.nonNull(discount))
			{
				this.addDiscount(context, orderEntryRao, discount);
			}
		}

		return isPerformed;
	}

	protected void addDiscount(final RuleActionContext context, final OrderEntryRAO orderEntryRao, final DiscountRAO discount)
	{
		this.addDiscount(context, orderEntryRao, orderEntryRao.getQuantity(), discount);
	}

	protected void addDiscount(final RuleActionContext context, final OrderEntryRAO orderEntryRao, final int quantity,
			final DiscountRAO discount)
	{
		final RuleEngineResultRAO result = context.getRuleEngineResultRao();
		result.getActions().add(discount);
		this.setRAOMetaData(context, new AbstractRuleActionRAO[]
		{ discount });
		this.getConsumptionSupport().consumeOrderEntry(orderEntryRao, quantity, discount);
		context.scheduleForUpdate(new Object[]
		{ orderEntryRao, orderEntryRao.getOrder(), result });
		context.insertFacts(new Object[]
		{ discount });
		context.insertFacts(discount.getConsumedEntries());
	}


}