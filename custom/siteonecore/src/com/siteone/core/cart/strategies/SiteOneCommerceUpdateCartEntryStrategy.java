/**
 *
 */
package com.siteone.core.cart.strategies;

import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceUpdateCartEntryStrategy;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;


/**
 * @author 1091124
 *
 */
public interface SiteOneCommerceUpdateCartEntryStrategy extends CommerceUpdateCartEntryStrategy
{
	CommerceCartModification modifyEntry(final CartModel cartModel, final AbstractOrderEntryModel entryToUpdate,
			final long actualAllowedQuantityChange, final long newQuantity);
}
