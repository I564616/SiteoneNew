/**
 *
 */
package com.siteone.core.product.dao;

import java.util.List;

import com.siteone.core.model.InventoryUPCModel;


/**
 * @author 1091124
 *
 */
public interface SiteOneProductUOMDao
{
	public List<InventoryUPCModel> getSellableUOMsById(final String code);

	public InventoryUPCModel getInventoryUOMById(final String id);
}
