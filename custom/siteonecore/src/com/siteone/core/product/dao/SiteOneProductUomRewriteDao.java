/**
 *
 */
package com.siteone.core.product.dao;

import com.siteone.core.model.ProductSalesInfoModel;
import com.siteone.core.model.UomRewriteConfigModel;

import java.util.List;


/**
 * @author 1091124
 *
 */
public interface SiteOneProductUomRewriteDao
{
	public List<UomRewriteConfigModel> findUomConfigByIndex(final String indexName);
}
