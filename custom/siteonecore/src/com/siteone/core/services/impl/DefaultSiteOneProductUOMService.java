/**
 *
 */
package com.siteone.core.services.impl;

import java.util.List;

import com.siteone.core.model.InventoryUPCModel;
import com.siteone.core.product.dao.SiteOneProductUOMDao;
import com.siteone.core.services.SiteOneProductUOMService;


/**
 * @author 1091124
 *
 */
public class DefaultSiteOneProductUOMService implements SiteOneProductUOMService
{
	private SiteOneProductUOMDao siteOneProductUOMDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.services.SiteOneProductUOMService#getInventoryUOMById(java.lang.Integer)
	 */
	@Override
	public InventoryUPCModel getInventoryUOMById(final String id)
	{
		return getSiteOneProductUOMDao().getInventoryUOMById(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.services.SiteOneProductUOMService#getInventoryUOMByCode(java.lang.Integer)
	 */
	@Override
	public List<InventoryUPCModel> getSellableUOMsById(final String code)
	{
		return getSiteOneProductUOMDao().getSellableUOMsById(code);
	}

	/**
	 * @return the siteOneProductUOMDao
	 */
	public SiteOneProductUOMDao getSiteOneProductUOMDao()
	{
		return siteOneProductUOMDao;
	}

	/**
	 * @param siteOneProductUOMDao
	 *           the siteOneProductUOMDao to set
	 */
	public void setSiteOneProductUOMDao(final SiteOneProductUOMDao siteOneProductUOMDao)
	{
		this.siteOneProductUOMDao = siteOneProductUOMDao;
	}

}
