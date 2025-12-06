/**
 *
 */
package com.siteone.core.rule.definitions.action;

import de.hybris.platform.ruleengineservices.rao.AbstractRuleActionRAO;
import de.hybris.platform.ruleengineservices.rao.DiscountRAO;
import de.hybris.platform.ruleengineservices.rao.OrderEntryRAO;
import de.hybris.platform.ruleengineservices.rao.RuleEngineResultRAO;
import de.hybris.platform.ruleengineservices.rule.evaluation.RuleActionContext;
import de.hybris.platform.ruleengineservices.rule.evaluation.actions.AbstractRuleExecutableSupport;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;

import com.siteone.core.services.SiteOneRuleEngineCalculationService;


/**
 * @author BS
 *
 */
public class RuleOrderEntryMaxMinThresholdPercentageDiscountRAOAction extends AbstractRuleExecutableSupport
{

	@Resource(name = "ruleEngineCalculationService")
	private SiteOneRuleEngineCalculationService siteoneruleEngineCalculationService;


	@Override
	public boolean performActionInternal(final RuleActionContext context)
	{
		boolean isPerformed = false;
		final Optional<BigDecimal> amount = this.extractAmountForCurrency(context, context.getParameter("value"));
		final Integer max = (Integer) context.getParameter("max");
		final Integer min = (Integer) context.getParameter("min");

		if (amount.isPresent())
		{
			final Set<OrderEntryRAO> orderEntries = context.getValues(OrderEntryRAO.class);
			int consumableThreshold = 0;
			if (CollectionUtils.isNotEmpty(orderEntries))

			{
				for (final OrderEntryRAO orderEntry : orderEntries)
				{
					consumableThreshold += orderEntry.getTotalPrice().intValue();
				}
				OrderEntryRAO orderEntryRAO;
				if ((max == 0 && min != 0 && consumableThreshold >= min)
						|| (max != 0 && min != 0 && consumableThreshold >= min && consumableThreshold <= max)
						|| (min == 0 && consumableThreshold <= max))
				{

					for (final Iterator var6 = orderEntries.iterator(); var6
							.hasNext(); isPerformed |= this.processOrderEntry(context, orderEntryRAO, 0, amount.get()))
					{
						orderEntryRAO = (OrderEntryRAO) var6.next();
					}
				}

			}


		}

		return isPerformed;

	}

	protected boolean processOrderEntry(final RuleActionContext context, final OrderEntryRAO orderEntryRao, final int adjustedQty,
			final BigDecimal value)
	{
		boolean isPerformed = false;
		final int consumableQuantity = this.getConsumptionSupport().getConsumableQuantity(orderEntryRao);
		if (consumableQuantity > 0)
		{
			DiscountRAO discount;
			if (adjustedQty != 0)
			{
				discount = getSiteoneruleEngineCalculationService().addOrderEntryLevelDiscountLimit(orderEntryRao, false, value,
						adjustedQty);
				this.setRAOMetaData(context, new AbstractRuleActionRAO[]
				{ discount });

				this.getConsumptionSupport().consumeOrderEntry(orderEntryRao, adjustedQty, discount);
			}
			else
			{
				discount = this.getRuleEngineCalculationService().addOrderEntryLevelDiscount(orderEntryRao, false, value);
				this.setRAOMetaData(context, new AbstractRuleActionRAO[]
				{ discount });
				this.getConsumptionSupport().consumeOrderEntry(orderEntryRao, consumableQuantity, discount);
			}
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
}
