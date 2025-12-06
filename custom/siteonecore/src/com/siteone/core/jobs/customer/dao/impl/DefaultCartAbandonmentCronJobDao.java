/**
 *
 */
package com.siteone.core.jobs.customer.dao.impl;

import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.util.Config;

import java.util.List;

import com.siteone.core.jobs.customer.dao.CartAbandonmentCronJobDao;


/**
 * @author 1219341
 *
 */
public class DefaultCartAbandonmentCronJobDao extends AbstractItemDao implements CartAbandonmentCronJobDao
{
	@Override
	public List<CartModel> findInActiveCarts()
	{
		return getFlexibleSearchService().<CartModel> search(Config.getString("cart.abandonment.cronjob.query", null)).getResult();
	}

}
