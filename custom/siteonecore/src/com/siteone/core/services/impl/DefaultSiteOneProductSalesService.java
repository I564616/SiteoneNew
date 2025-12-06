/**
 *
 */
package com.siteone.core.services.impl;

import com.siteone.core.model.ProductSalesInfoModel;
import com.siteone.core.product.dao.SiteOneProductSalesDao;
import com.siteone.core.services.SiteOneProductSalesService;
import de.hybris.platform.product.impl.DefaultProductService;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;


/**
 * @author 1091124
 *
 */
public class DefaultSiteOneProductSalesService implements SiteOneProductSalesService
{
	private SiteOneProductSalesDao siteOneProductSalesDao;


	public List<ProductSalesInfoModel> getSalesByProductCode(final String code)
	{
		validateParameterNotNull(code, "Parameter code must not be null");
		final List<ProductSalesInfoModel> productSalesInfo = getSiteOneProductSalesDao().findSalesByProductCode(code);

		return productSalesInfo;

	}

	/**
	 * @return the siteOneProductSalesDao
	 */
	public SiteOneProductSalesDao getSiteOneProductSalesDao()
	{
		return siteOneProductSalesDao;
	}

	/**
	 * @param siteOneProductSalesDao
	 *           the siteOneProductSalesDao to set
	 */
	public void setSiteOneProductSalesDao(final SiteOneProductSalesDao siteOneProductSalesDao)
	{
		this.siteOneProductSalesDao = siteOneProductSalesDao;
	}




}
