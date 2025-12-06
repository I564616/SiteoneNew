package com.siteone.forms;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class SiteOneAddToCartForm {
	
	@NotNull(message = "{basket.error.quantity.notNull}")
	@Min(value = 0, message = "{basket.error.quantity.invalid}")
	@Digits(fraction = 0, integer = 10, message = "{basket.error.quantity.invalid}")
	private long qty = 1L;
	private String storeId;
	
	private String inventoryUomId;

	public void setQty(final long quantity)
	{
		this.qty = quantity;
	}

	public long getQty()
	{
		return qty;
	}
	
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