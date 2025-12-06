/**
 *
 */
package com.siteone.core.rule.definitions.action;

import de.hybris.platform.ruleengineservices.rao.DiscountRAO;
import de.hybris.platform.ruleengineservices.rao.EntriesSelectionStrategyRPD;
import de.hybris.platform.ruleengineservices.rao.OrderEntryRAO;
import de.hybris.platform.ruleengineservices.rule.evaluation.RuleActionContext;
import de.hybris.platform.ruleengineservices.rule.evaluation.actions.impl.RulePartnerOrderEntryPercentageDiscountRAOAction;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import com.google.common.collect.Lists;


/**
 * @author BS
 *
 */
public class SiteOneRulePartnerProductsOrderEntryDiscountRAOAction extends RulePartnerOrderEntryPercentageDiscountRAOAction
{
	public static final String QUALIFYING_CONTAINERS_PARAM = "qualifying_containers";
	public static final String PARTNER_CONTAINERS_PARAM = "target_containers";

	private static final Logger LOG = Logger.getLogger(SiteOneRulePartnerProductsOrderEntryDiscountRAOAction.class);

	@Override
	protected boolean performAction(final RuleActionContext context,
			final List<EntriesSelectionStrategyRPD> entriesSelectionStrategyRPDs, final BigDecimal amount)
	{
		boolean isPerformed = false;

		final Map<String, Integer> qualifyingProductsContainers = (Map) context.getParameter("qualifying_containers");
		final Collection<Integer> qtys = qualifyingProductsContainers.values();
		int consumableQuantity = 0;
		int qualifyingQty = 0;
		if (CollectionUtils.isNotEmpty(entriesSelectionStrategyRPDs.get(0).getOrderEntries()))
		{
			for (final OrderEntryRAO orderEntry : entriesSelectionStrategyRPDs.get(0).getOrderEntries())
			{
				consumableQuantity += orderEntry.getQuantity();
			}
		}
		if (CollectionUtils.isNotEmpty(qtys))
		{
			for (final Integer qty : qtys)
			{
				qualifyingQty += qty.intValue();
			}
		}
		LOG.info(qualifyingQty + "  " + consumableQuantity);
		if (consumableQuantity >= qualifyingQty)
		{
			this.validateSelectionStrategy(entriesSelectionStrategyRPDs, context);

			if (this.getConsumptionSupport().hasEnoughQuantity(context, entriesSelectionStrategyRPDs))
			{
				isPerformed = true;
				this.getConsumptionSupport().adjustStrategyQuantity(entriesSelectionStrategyRPDs);
				final List<EntriesSelectionStrategyRPD> selectionStrategyRPDsForAction = Lists.newArrayList();
				final List<EntriesSelectionStrategyRPD> selectionStrategyRPDsForTriggering = Lists.newArrayList();
				this.splitEntriesSelectionStrategies(entriesSelectionStrategyRPDs, selectionStrategyRPDsForAction,
						selectionStrategyRPDsForTriggering);
				final List<DiscountRAO> discounts = this.addDiscountAndConsume(context, selectionStrategyRPDsForAction, false,
						amount);
				if (CollectionUtils.isNotEmpty(selectionStrategyRPDsForTriggering))
				{
					this.getConsumptionSupport().consumeOrderEntries(context, selectionStrategyRPDsForTriggering,
							discounts.isEmpty() ? null : (DiscountRAO) discounts.get(0));
					this.updateFactsWithOrderEntries(context, selectionStrategyRPDsForTriggering);
				}
			}
		}
		return isPerformed;
	}

}