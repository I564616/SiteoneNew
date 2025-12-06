/**
 *
 */
package com.siteone.core.jobs.customer.dao;

import de.hybris.platform.core.model.order.CartModel;

import java.util.List;


/**
 * @author 1219341
 *
 */
public interface CartAbandonmentCronJobDao
{
	public List<CartModel> findInActiveCarts();
}
