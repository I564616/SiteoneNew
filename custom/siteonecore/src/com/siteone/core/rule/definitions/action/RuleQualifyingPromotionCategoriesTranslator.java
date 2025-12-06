/**
 *
 */
package com.siteone.core.rule.definitions.action;

import de.hybris.platform.ruledefinitions.conditions.AbstractRuleConditionTranslator;
import de.hybris.platform.ruledefinitions.conditions.builders.IrConditions;
import de.hybris.platform.ruledefinitions.conditions.builders.RuleIrAttributeConditionBuilder;
import de.hybris.platform.ruledefinitions.conditions.builders.RuleIrAttributeRelConditionBuilder;
import de.hybris.platform.ruledefinitions.conditions.builders.RuleIrGroupConditionBuilder;
import de.hybris.platform.ruleengineservices.compiler.RuleCompilerContext;
import de.hybris.platform.ruleengineservices.compiler.RuleIrAttributeOperator;
import de.hybris.platform.ruleengineservices.compiler.RuleIrCondition;
import de.hybris.platform.ruleengineservices.compiler.RuleIrExistsCondition;
import de.hybris.platform.ruleengineservices.compiler.RuleIrGroupCondition;
import de.hybris.platform.ruleengineservices.compiler.RuleIrGroupOperator;
import de.hybris.platform.ruleengineservices.compiler.RuleIrLocalVariablesContainer;
import de.hybris.platform.ruleengineservices.compiler.RuleIrNotCondition;
import de.hybris.platform.ruleengineservices.definitions.AmountOperator;
import de.hybris.platform.ruleengineservices.definitions.CollectionOperator;
import de.hybris.platform.ruleengineservices.rao.CartRAO;
import de.hybris.platform.ruleengineservices.rao.OrderEntryRAO;
//import de.hybris.platform.ruleengineservices.rao.ProductRAO;
import de.hybris.platform.ruleengineservices.rule.data.RuleConditionData;
import de.hybris.platform.ruleengineservices.rule.data.RuleConditionDefinitionData;
import de.hybris.platform.ruleengineservices.rule.data.RuleParameterData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.collect.Lists;
import com.siteone.core.model.PromotionProductCategoryModel;
import com.siteone.core.services.SiteOnePromotionSourceRuleService;


/**
 * @author BS
 *
 */
public class RuleQualifyingPromotionCategoriesTranslator extends AbstractRuleConditionTranslator
{
	private SiteOnePromotionSourceRuleService siteOnePromotionSourceRuleService;

	@Override
	public RuleIrCondition translate(final RuleCompilerContext context, final RuleConditionData condition,
			final RuleConditionDefinitionData conditionDefinition)
	{
		final Map<String, RuleParameterData> conditionParameters = condition.getParameters();
		final RuleParameterData operatorParameter = conditionParameters.get("operator");
		final RuleParameterData quantityParameter = conditionParameters.get("quantity");
		final RuleParameterData categoriesOperatorParameter = conditionParameters.get("categories_operator");
		final RuleParameterData categoriesParameter = conditionParameters.get("categories");
		if (this.verifyAllPresent(new Object[]
		{ operatorParameter, quantityParameter, categoriesOperatorParameter, categoriesParameter }))
		{
			final AmountOperator operator = (AmountOperator) operatorParameter.getValue();
			final Integer quantity = (Integer) quantityParameter.getValue();
			final CollectionOperator categoriesOperator = (CollectionOperator) categoriesOperatorParameter.getValue();
			final PromotionProductCategoryModel category = this.siteOnePromotionSourceRuleService
					.getPromotionCategoryForCode(categoriesParameter.getValue());
			final List<String> products = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(category.getProductsCode()))
			{
				products.addAll(category.getProductsCode());

			}
			if (this.verifyAllPresent(new Object[]
			{ operator, quantity, categoriesOperator, products }))
			{
				return this.getQualifyingProductsCondition(context, operator, quantity, categoriesOperator, products);
			}
		}

		return IrConditions.empty();
	}


	protected RuleIrGroupCondition getQualifyingProductsCondition(final RuleCompilerContext context, final AmountOperator operator,
			final Integer quantity, final CollectionOperator productsOperator, final List<String> products)
	{
		final RuleIrGroupCondition irQualifyingProductsCondition = RuleIrGroupConditionBuilder
				.newGroupConditionOf(RuleIrGroupOperator.AND).build();
		this.addQualifyingProductsCondition(context, operator, quantity, productsOperator, products, irQualifyingProductsCondition);
		return irQualifyingProductsCondition;
	}

	protected void addQualifyingProductsCondition(final RuleCompilerContext context, final AmountOperator operator,
			final Integer quantity, final CollectionOperator productsOperator, final List<String> products,
			final RuleIrGroupCondition irQualifyingProductsCondition)
	{
		final String orderEntryRaoVariable = context.generateVariable(OrderEntryRAO.class);
		final String cartRaoVariable = context.generateVariable(CartRAO.class);
		final List<RuleIrCondition> irConditions = Lists.newArrayList();
		final RuleIrGroupCondition baseProductOrGroupCondition = RuleIrGroupConditionBuilder
				.newGroupConditionOf(RuleIrGroupOperator.OR).build();
		baseProductOrGroupCondition.getChildren()
				.add(RuleIrAttributeConditionBuilder.newAttributeConditionFor(orderEntryRaoVariable).withAttribute("productCode")
						.withOperator(RuleIrAttributeOperator.IN).withValue(products).build());
		final Iterator var13 = products.iterator();

		while (var13.hasNext())
		{
			final String product = (String) var13.next();
			baseProductOrGroupCondition.getChildren()
					.add(RuleIrAttributeConditionBuilder.newAttributeConditionFor(orderEntryRaoVariable)
							.withAttribute("baseProductCodes").withOperator(RuleIrAttributeOperator.CONTAINS).withValue(product)
							.build());
		}

		irConditions.add(baseProductOrGroupCondition);
		irConditions.add(RuleIrAttributeConditionBuilder.newAttributeConditionFor(orderEntryRaoVariable).withAttribute("quantity")
				.withOperator(RuleIrAttributeOperator.valueOf(operator.name())).withValue(quantity).build());
		irConditions
				.add(RuleIrAttributeRelConditionBuilder.newAttributeRelationConditionFor(cartRaoVariable).withAttribute("entries")
						.withOperator(RuleIrAttributeOperator.CONTAINS).withTargetVariable(orderEntryRaoVariable).build());
		this.evaluateProductsOperator(context, operator, quantity, productsOperator, products, irQualifyingProductsCondition,
				irConditions, orderEntryRaoVariable);
	}

	protected void evaluateProductsOperator(final RuleCompilerContext context, final AmountOperator operator,
			final Integer quantity, final CollectionOperator productsOperator, final List<String> products,
			final RuleIrGroupCondition irQualifyingProductsCondition, final List<RuleIrCondition> irConditions,
			final String orderEntryRaoVariable)
	{
		if (!CollectionOperator.CONTAINS_ALL.equals(productsOperator))
		{
			irConditions.addAll(this.getConsumptionSupport().newProductConsumedCondition(context, orderEntryRaoVariable));
		}

		if (CollectionOperator.NOT_CONTAINS.equals(productsOperator))
		{
			final RuleIrNotCondition irNotProductCondition = new RuleIrNotCondition();
			irNotProductCondition.setChildren(irConditions);
			irQualifyingProductsCondition.getChildren().add(irNotProductCondition);
		}
		else
		{
			irQualifyingProductsCondition.getChildren().addAll(irConditions);
			if (CollectionOperator.CONTAINS_ALL.equals(productsOperator))
			{
				this.addContainsAllProductsConditions(context, operator, quantity, products, irQualifyingProductsCondition);
			}
		}

	}

	protected void addContainsAllProductsConditions(final RuleCompilerContext context, final AmountOperator operator,
			final Integer quantity, final List<String> products, final RuleIrGroupCondition irQualifyingProductsCondition)
	{
		final String cartRaoVariable = context.generateVariable(CartRAO.class);
		final Iterator var8 = products.iterator();

		while (var8.hasNext())
		{
			final String product = (String) var8.next();
			final RuleIrLocalVariablesContainer variablesContainer = context.createLocalContainer();
			final String containsOrderEntryRaoVariable = context.generateLocalVariable(variablesContainer, OrderEntryRAO.class);
			final List<RuleIrCondition> irConditions = Lists.newArrayList();
			irConditions.add(RuleIrAttributeConditionBuilder.newAttributeConditionFor(containsOrderEntryRaoVariable)
					.withAttribute("productCode").withOperator(RuleIrAttributeOperator.EQUAL).withValue(product).build());
			irConditions.add(
					RuleIrAttributeConditionBuilder.newAttributeConditionFor(containsOrderEntryRaoVariable).withAttribute("quantity")
							.withOperator(RuleIrAttributeOperator.valueOf(operator.name())).withValue(quantity).build());
			irConditions
					.add(RuleIrAttributeRelConditionBuilder.newAttributeRelationConditionFor(cartRaoVariable).withAttribute("entries")
							.withOperator(RuleIrAttributeOperator.CONTAINS).withTargetVariable(containsOrderEntryRaoVariable).build());
			irConditions.addAll(this.getConsumptionSupport().newProductConsumedCondition(context, containsOrderEntryRaoVariable));
			final RuleIrExistsCondition irExistsProductCondition = new RuleIrExistsCondition();
			irExistsProductCondition.setVariablesContainer(variablesContainer);
			irExistsProductCondition.setChildren(irConditions);
			irQualifyingProductsCondition.getChildren().add(irExistsProductCondition);
		}

	}



	/**
	 * @return the siteOnePromotionSourceRuleService
	 */
	public SiteOnePromotionSourceRuleService getSiteOnePromotionSourceRuleService()
	{
		return siteOnePromotionSourceRuleService;
	}


	/**
	 * @param siteOnePromotionSourceRuleService
	 *           the siteOnePromotionSourceRuleService to set
	 */
	public void setSiteOnePromotionSourceRuleService(final SiteOnePromotionSourceRuleService siteOnePromotionSourceRuleService)
	{
		this.siteOnePromotionSourceRuleService = siteOnePromotionSourceRuleService;
	}
}
