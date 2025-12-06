/**
 *
 */
package com.siteone.core.services;

import java.util.List;

import com.siteone.core.model.ProprietaryBrandConfigModel;


/**
 * @author 1091124
 *
 */
public interface SiteOneProprietaryBrandConfigService
{
	List<ProprietaryBrandConfigModel> getProprietaryBrandConfigByIndex(final String indexName);
}
