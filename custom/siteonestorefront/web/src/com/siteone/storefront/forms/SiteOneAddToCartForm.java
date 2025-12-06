/**
 *
 */
package com.siteone.storefront.forms;

import de.hybris.platform.acceleratorstorefrontcommons.forms.AddToCartForm;


/**
 * @author 1091124
 *
 */
public class SiteOneAddToCartForm extends AddToCartForm
{

	private String inventoryUomId;
	private String storeId;

	/**
	 * @return the inventoryUomId
	 */
	public String getInventoryUomId()
	{
		return inventoryUomId;
	}

	/**
	 * @param inventoryUomId
	 *           the inventoryUomId to set
	 */
	public void setInventoryUomId(final String inventoryUomId)
	{
		this.inventoryUomId = inventoryUomId;
	}

	/**
	 * @return the storeId
	 */
	public String getStoreId()
	{
		return storeId;
	}

	/**
	 * @param storeId
	 *           the storeId to set
	 */
	public void setStoreId(final String storeId)
	{
		this.storeId = storeId;
	}



}
