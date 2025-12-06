/**
 *
 */
package com.siteone.core.services;

import com.siteone.core.model.AutoPhraseConfigModel;
import com.siteone.core.model.UomRewriteConfigModel;

import java.util.List;


/**
 * @author 1091124
 *
 */
public interface SiteOneAutoPhraseConfigService
{
	List<AutoPhraseConfigModel> getAutoPhraseConfigByIndex(final String indexName);
}
