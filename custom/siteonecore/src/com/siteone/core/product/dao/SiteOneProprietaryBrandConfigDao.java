/**
 *
 */
package com.siteone.core.product.dao;

import java.util.List;

import com.siteone.core.model.ProprietaryBrandConfigModel;


/**
 * @author 1091124
 *
 */
public interface SiteOneProprietaryBrandConfigDao
{
	public List<ProprietaryBrandConfigModel> findProprietaryBrandConfigByIndex(final String indexName);
}
