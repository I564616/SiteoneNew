package com.siteone.core.strategies.impl;

import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;


public interface SiteOneCommerceCartEntryRemovalStrategy
{
    void removeCartEntry(final AbstractOrderModel cart);
}
