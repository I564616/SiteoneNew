/**
 *
 */
package com.siteone.core.adapter.impl;

import com.siteone.core.adapter.BigBagSizeAdapter;
import com.siteone.core.product.dao.SiteOneProductDao;
import de.hybris.platform.core.model.product.ProductModel;
import org.apache.log4j.Logger;

import jakarta.annotation.Resource;
import java.util.Collections;
import java.util.List;


/**
 * @author 1099417
 *
 */
public class DefaultBigBagSizeAdapter implements BigBagSizeAdapter
{
	private static final Logger LOGGER = Logger.getLogger(DefaultBigBagSizeAdapter.class);

	@Resource(name = "siteOneProductDao")
	private SiteOneProductDao siteOneProductDao;

	@Override
	public ProductModel getProductByCode(final String code) {
		try {
			List<ProductModel> products = getSiteOneProductDao().findAllProductsByCodes(Collections.singletonList(code));
			for (ProductModel product : products) {
				if ("Online".equals(product.getCatalogVersion().getVersion())) {
					return product;
				}
			}
			return null; // No product with 'Online' version found
		} catch (final Exception exception) {
			LOGGER.error(exception);
			return null;
		}
	}

	/**
	 * @return the siteOneProductDao
	 */
	public SiteOneProductDao getSiteOneProductDao()
	{
		return siteOneProductDao;
	}

	/**
	 * @param siteOneProductDao
	 *           the siteOneProductDao to set
	 */
	public void setSiteOneProductDao(final SiteOneProductDao siteOneProductDao)
	{
		this.siteOneProductDao = siteOneProductDao;
	}



}
