/**
 *
 */
package com.siteone.core.strategies.impl;

import de.hybris.platform.basecommerce.enums.InStockStatus;
import de.hybris.platform.commerceservices.stock.strategies.impl.DefaultCommerceAvailabilityCalculationStrategy;
import de.hybris.platform.ordersplitting.model.StockLevelModel;

import java.util.Collection;


/**
 * @author 1129929
 *
 */
public class DefaultSiteOneCommerceAvailabilityCalculationStrategy extends DefaultCommerceAvailabilityCalculationStrategy
{

	@Override
	public Long calculateAvailability(final Collection<StockLevelModel> stockLevels)
	{
		long totalActualAmount = 0;
		for (final StockLevelModel stockLevel : stockLevels)
		{
			// If any stock level is flagged as FORCEOUTOFSTOCK then we skip over it
			if (!InStockStatus.FORCEOUTOFSTOCK.equals(stockLevel.getInStockStatus()))
			{
				final long availableToSellQuantity = getAvailableToSellQuantity(stockLevel);
				if (availableToSellQuantity > 0 || !stockLevel.isTreatNegativeAsZero())
				{
					totalActualAmount += availableToSellQuantity;
				}
			}
		}
		return Long.valueOf(totalActualAmount);
	}

	@Override
	protected long getAvailableToSellQuantity(final StockLevelModel stockLevel)
	{
		return stockLevel.getAvailable() - stockLevel.getReserved() + stockLevel.getOverSelling();
	}
}
