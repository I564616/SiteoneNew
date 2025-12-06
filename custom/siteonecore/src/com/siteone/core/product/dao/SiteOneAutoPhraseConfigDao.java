/**
 *
 */
package com.siteone.core.product.dao;

import com.siteone.core.model.AutoPhraseConfigModel;
import com.siteone.core.model.UomRewriteConfigModel;

import java.util.List;


/**
 * @author 1091124
 *
 */
public interface SiteOneAutoPhraseConfigDao
{
	public List<AutoPhraseConfigModel> findAutoPhraseConfigByIndex(final String indexName);
}
