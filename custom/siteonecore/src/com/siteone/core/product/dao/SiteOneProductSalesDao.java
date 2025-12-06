/**
 *
 */
package com.siteone.core.product.dao;

import com.siteone.core.model.ProductSalesInfoModel;
import de.hybris.platform.core.model.product.ProductModel;

import java.util.List;


/**
 * @author 1091124
 *
 */
public interface SiteOneProductSalesDao
{
	public List<ProductSalesInfoModel> findSalesByProductCode(final String code);
}
