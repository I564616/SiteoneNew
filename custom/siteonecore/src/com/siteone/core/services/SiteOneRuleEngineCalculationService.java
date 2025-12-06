/**
 *
 */
package com.siteone.core.services;

import de.hybris.platform.ruleengineservices.calculation.RuleEngineCalculationService;
import de.hybris.platform.ruleengineservices.rao.DiscountRAO;
import de.hybris.platform.ruleengineservices.rao.OrderEntryRAO;

import java.math.BigDecimal;


/**
 * @author BS
 *
 */
public interface SiteOneRuleEngineCalculationService extends RuleEngineCalculationService
{
	public DiscountRAO addOrderEntryLevelDiscountLimit(OrderEntryRAO paramOrderEntryRAO, boolean paramBoolean,
			BigDecimal paramBigDecimal, int qty);
}
