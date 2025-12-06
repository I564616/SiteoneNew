/**
 *
 */
package com.siteone.core.services;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.order.CalculationService;
import de.hybris.platform.order.exceptions.CalculationException;

import com.siteone.facades.customer.price.SiteOneCartCspPriceData;


/**
 * @author 1091124
 *
 */
public interface SiteOneCalculationService extends CalculationService
{
	void calculate(AbstractOrderModel order, CommerceCartParameter parameter) throws CalculationException;

	void recalculate(AbstractOrderModel order, CommerceCartParameter parameter) throws CalculationException;

	public void calculateEntries(final AbstractOrderModel order, final boolean forceRecalculate,
			final CommerceCartParameter parameter) throws CalculationException;

	/**
	 * @param entry
	 * @param inventoryMultiplier
	 * @return
	 */
	ProductData getProductByUOM(AbstractOrderEntryModel entry, float inventoryMultiplier);

	public SiteOneCartCspPriceData fetchCartCSPPrice(final AbstractOrderModel order, String sku) throws CalculationException;
}
