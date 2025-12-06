/**
 * 
 */
package com.siteone.core.util;

import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.ruleengineservices.rao.OrderEntryRAO;

/**
 * 
 */
public class SiteOneConsumableQuantityUtil
{
	private SiteOneConsumableQuantityUtil()
	{
		//empty
	}
	
	public static int calculateQuantity(final CartModel cartModel, final OrderEntryRAO orderEntry) 
	{
		int consumableQuantity = 0;
		consumableQuantity = orderEntry.getQuantity();
		for (final AbstractOrderEntryModel entry : cartModel.getEntries()) {
			if ((orderEntry.getProductCode()).equalsIgnoreCase(entry.getProduct().getCode())) {
				int invMulti=1;
				if(entry.getInventoryUOM()!=null) {
					invMulti = entry.getInventoryUOM().getInventoryMultiplier().intValue();
					consumableQuantity = orderEntry.getQuantity() * invMulti;
				}
			}
		}
		return consumableQuantity;
	}
	
	public static int inventoryMultiplier(final CartModel cartModel, final OrderEntryRAO orderEntry) 
	{
		int inventoryMultiplier = 0;
		for (final AbstractOrderEntryModel entry : cartModel.getEntries()) {
			if ((orderEntry.getProductCode()).equalsIgnoreCase(entry.getProduct().getCode())) {
				if(entry.getInventoryUOM()!=null) {
					inventoryMultiplier = entry.getInventoryUOM().getInventoryMultiplier().intValue();
				}
			}
		}
		return inventoryMultiplier;
	}

}
