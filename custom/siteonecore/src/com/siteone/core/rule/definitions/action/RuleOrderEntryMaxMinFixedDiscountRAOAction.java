/**
 *
 */
package com.siteone.core.rule.definitions.action;

import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.ruleengineservices.rao.AbstractRuleActionRAO;
import de.hybris.platform.ruleengineservices.rao.DiscountRAO;
import de.hybris.platform.ruleengineservices.rao.OrderEntryRAO;
import de.hybris.platform.ruleengineservices.rao.RuleEngineResultRAO;
import de.hybris.platform.ruleengineservices.rule.evaluation.RuleActionContext;
import de.hybris.platform.ruleengineservices.rule.evaluation.actions.AbstractRuleExecutableSupport;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import jakarta.annotation.Resource;


import org.apache.commons.collections4.CollectionUtils;

import com.siteone.core.services.SiteOneRuleEngineCalculationService;
import com.siteone.core.util.SiteOneConsumableQuantityUtil;




/**
 * @author BS
 *
 */
public class RuleOrderEntryMaxMinFixedDiscountRAOAction extends AbstractRuleExecutableSupport
{

	@Resource(name = "ruleEngineCalculationService")
	private SiteOneRuleEngineCalculationService siteoneruleEngineCalculationService;

	@Resource(name = "cartService")
	private CartService cartService;

	@Override
	public boolean performActionInternal(final RuleActionContext context)
	{
		final Set<OrderEntryRAO> orderEntries = context.getValues(OrderEntryRAO.class);
		final Map<String, BigDecimal> values = (Map) context.getParameter("value");
		final Integer max = (Integer) context.getParameter("max");
		final Integer min = (Integer) context.getParameter("min");
		boolean isPerformed = false;
		int consumableQuantity = 0;
		if (CollectionUtils.isNotEmpty(orderEntries))

		{
			for (final OrderEntryRAO orderEntry : orderEntries)
			{
				final CartModel cartModel = getCartService().getSessionCart();
				consumableQuantity = SiteOneConsumableQuantityUtil.calculateQuantity(cartModel, orderEntry);
				if ((max == 0 && min != 0 && consumableQuantity >= min)
						|| (max != 0 && min != 0 && consumableQuantity >= min && consumableQuantity <= max)
						|| (min == 0 && consumableQuantity <= max))
				{
					final BigDecimal valueForCurrency = values.get(orderEntry.getCurrencyIsoCode());
					if (Objects.nonNull(valueForCurrency))
					{
						isPerformed = this.performAction(context, orderEntry, 0, consumableQuantity, valueForCurrency);
					}
				}
				else if ((min == 0 && consumableQuantity > max)
						|| max != 0 && min != 0 && consumableQuantity >= min && consumableQuantity >= max)
				{
					final BigDecimal valueForCurrency = values.get(orderEntry.getCurrencyIsoCode());
					if (consumableQuantity == max)
					{
						if (Objects.nonNull(valueForCurrency))
						{

							isPerformed = this.performAction(context, orderEntry, 0, consumableQuantity, valueForCurrency);
						}
					}
					else
					{
						final int adjustedQty = consumableQuantity - max.intValue();
						final BigDecimal valueForCurrency1 = values.get(orderEntry.getCurrencyIsoCode());
						if (Objects.nonNull(valueForCurrency1))
						{
							isPerformed = this.performAction(context, orderEntry, adjustedQty, consumableQuantity, valueForCurrency1);
						}

					}
				}
			}
		}
		return isPerformed;

	}


	protected boolean performAction(final RuleActionContext context, final OrderEntryRAO orderEntryRao, final int adjustedQty,
			final int consumableQuantity, final BigDecimal valueForCurrency)
	{
		boolean isPerformed = false;
		if (consumableQuantity > 0)
		{
			DiscountRAO discount;
			if (adjustedQty != 0)
			{
				isPerformed = true;
				discount = getSiteoneruleEngineCalculationService().addOrderEntryLevelDiscountLimit(orderEntryRao, true,
						valueForCurrency, adjustedQty);
				this.setRAOMetaData(context, new AbstractRuleActionRAO[]
				{ discount });
				this.getConsumptionSupport().consumeOrderEntry(orderEntryRao, consumableQuantity, discount);
			}
			else
			{
				isPerformed = true;
				discount = getSiteoneruleEngineCalculationService().addOrderEntryLevelDiscountLimit(orderEntryRao, true,
						valueForCurrency, 0);
				this.setRAOMetaData(context, new AbstractRuleActionRAO[]
				{ discount });
				this.getConsumptionSupport().consumeOrderEntry(orderEntryRao, consumableQuantity, discount);
			}
			final RuleEngineResultRAO result = context.getRuleEngineResultRao();
			result.getActions().add(discount);
			context.scheduleForUpdate(new Object[]
			{ orderEntryRao, orderEntryRao.getOrder(), result });
			context.insertFacts(new Object[]
			{ discount });
			context.insertFacts(discount.getConsumedEntries());
		}

		return isPerformed;
	}

	/**
	 * @return the siteoneruleEngineCalculationService
	 */
	public SiteOneRuleEngineCalculationService getSiteoneruleEngineCalculationService()
	{
		return siteoneruleEngineCalculationService;
	}

	/**
	 * @param siteoneruleEngineCalculationService
	 *           the siteoneruleEngineCalculationService to set
	 */
	public void setSiteoneruleEngineCalculationService(
			final SiteOneRuleEngineCalculationService siteoneruleEngineCalculationService)
	{
		this.siteoneruleEngineCalculationService = siteoneruleEngineCalculationService;
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


}