/**
 *
 */
package com.siteone.core.services;

import com.siteone.core.model.ProductSalesInfoModel;
import com.siteone.core.model.UomRewriteConfigModel;

import java.util.List;


/**
 * @author 1091124
 *
 */
public interface SiteOneUomRewriteConfigService
{
	List<UomRewriteConfigModel> getUomRewriteConfigByIndex(final String indexName);
}
