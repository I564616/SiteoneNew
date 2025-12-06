/**
 *
 */
package com.siteone.core.strategies.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import de.hybris.platform.commerceservices.order.impl.DefaultCommerceCartCalculationStrategy;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.order.exceptions.CalculationException;
import de.hybris.platform.promotions.jalo.PromotionsManager.AutoApplyMode;

import org.springframework.web.client.ResourceAccessException;

import com.siteone.core.services.SiteOneCalculationService;


/**
 * @author 1219341
 *
 */
public class DefaultSiteOneCommerceCartCalculationStrategy extends DefaultCommerceCartCalculationStrategy
{
	private SiteOneCalculationService siteOneCalculationService;

	@Override
	@Deprecated
	public boolean recalculateCart(final CartModel cartModel) throws ResourceAccessException
	{
		final CommerceCartParameter parameter = new CommerceCartParameter();
		parameter.setEnableHooks(true);
		parameter.setCart(cartModel);
		return this.recalculateCart(parameter);
	}

	@Override
	public boolean recalculateCart(final CommerceCartParameter parameter) throws ResourceAccessException
	{
		final CartModel cartModel = parameter.getCart();
		//parameter.getCart().getEntries().get(1).getBasePrice();
		final SiteOneCalculationService calcService = getSiteOneCalculationService();
		try
		{
			parameter.setRecalculate(true);
			beforeCalculate(parameter);
			calcService.recalculate(cartModel, parameter);
			getPromotionsService().updatePromotions(getPromotionGroups(), cartModel, true, AutoApplyMode.APPLY_ALL,
					AutoApplyMode.APPLY_ALL, getTimeService().getCurrentTime());
		}
		catch (final CalculationException calculationException)
		{
			throw new IllegalStateException(String.format("Cart model %s was not calculated due to: %s ", cartModel.getCode(),
					calculationException.getMessage()), calculationException);
		}
		finally
		{
			afterCalculate(parameter);

		}

		getExternalTaxesService().calculateExternalTaxes(cartModel);
		return true;
	}

	@Override
	public boolean calculateCart(final CommerceCartParameter parameter) throws ResourceAccessException
	{
		final CartModel cartModel = parameter.getCart();

		validateParameterNotNull(cartModel, "Cart model cannot be null");

		final SiteOneCalculationService calcService = getSiteOneCalculationService();
		boolean recalculated = false;
		if (calcService.requiresCalculation(cartModel))
		{
			try
			{
				parameter.setRecalculate(false);
				beforeCalculate(parameter);
				calcService.calculate(cartModel, parameter);
				getPromotionsService().updatePromotions(getPromotionGroups(), cartModel, true, AutoApplyMode.APPLY_ALL,
						AutoApplyMode.APPLY_ALL, getTimeService().getCurrentTime());
			}
			catch (final CalculationException calculationException)
			{
				throw new IllegalStateException(
						"Cart model " + cartModel.getCode() + " was not calculated due to: " + calculationException.getMessage(),
						calculationException);
			}
			finally
			{
				afterCalculate(parameter);
			}
			recalculated = true;
		}
		getExternalTaxesService().calculateExternalTaxes(cartModel);
		return recalculated;
	}

	/**
	 * @return the siteOneCalculationService
	 */
	public SiteOneCalculationService getSiteOneCalculationService()
	{
		return siteOneCalculationService;
	}

	/**
	 * @param siteOneCalculationService
	 *           the siteOneCalculationService to set
	 */
	public void setSiteOneCalculationService(final SiteOneCalculationService siteOneCalculationService)
	{
		this.siteOneCalculationService = siteOneCalculationService;
	}
}
