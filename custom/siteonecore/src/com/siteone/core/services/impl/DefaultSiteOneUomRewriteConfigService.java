/**
 *
 */
package com.siteone.core.services.impl;

import com.siteone.core.model.ProductSalesInfoModel;
import com.siteone.core.model.UomRewriteConfigModel;
import com.siteone.core.product.dao.SiteOneProductSalesDao;
import com.siteone.core.product.dao.SiteOneProductUomRewriteDao;
import com.siteone.core.services.SiteOneProductSalesService;
import com.siteone.core.services.SiteOneUomRewriteConfigService;

import java.util.List;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;


/**
 * @author 1091124
 *
 */
public class DefaultSiteOneUomRewriteConfigService implements SiteOneUomRewriteConfigService
{
	private SiteOneProductUomRewriteDao siteOneProductUomRewriteDao;


	public List<UomRewriteConfigModel> getUomRewriteConfigByIndex(final String indexName)
	{
		validateParameterNotNull(indexName, "Parameter code must not be null");
		final List<UomRewriteConfigModel> productSalesInfo = getSiteOneProductUomRewriteDao().findUomConfigByIndex(indexName);

		return productSalesInfo;

	}

	/**
	 * @return the siteOneProductUomRewriteDao
	 */
	public SiteOneProductUomRewriteDao getSiteOneProductUomRewriteDao()
	{
		return siteOneProductUomRewriteDao;
	}

	/**
	 * @param siteOneProductUomRewriteDao
	 *           the siteOneProductUomRewriteDao to set
	 */
	public void setSiteOneProductUomRewriteDao(final SiteOneProductUomRewriteDao siteOneProductUomRewriteDao)
	{
		this.siteOneProductUomRewriteDao = siteOneProductUomRewriteDao;
	}




}
