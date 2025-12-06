/**
 *
 */
package com.siteone.core.services;

import com.siteone.core.model.ProductSalesInfoModel;

import java.util.List;


/**
 * @author 1091124
 *
 */
public interface SiteOneProductSalesService
{
	List<ProductSalesInfoModel> getSalesByProductCode(final String code);
}
