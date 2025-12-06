/**
 *
 */
package com.siteone.core.services;

import java.util.List;

import com.siteone.core.model.InventoryUPCModel;


/**
 * @author 1091124
 *
 */
public interface SiteOneProductUOMService
{
	List<InventoryUPCModel> getSellableUOMsById(final String code);

	InventoryUPCModel getInventoryUOMById(final String id);

}
