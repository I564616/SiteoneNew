/**
 *
 */
package com.siteone.core.services.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import java.util.List;

import com.siteone.core.model.ProprietaryBrandConfigModel;
import com.siteone.core.product.dao.SiteOneProprietaryBrandConfigDao;
import com.siteone.core.services.SiteOneProprietaryBrandConfigService;


/**
 * @author 1091124
 *
 */
public class DefaultSiteOneProprietaryBrandConfigService implements SiteOneProprietaryBrandConfigService
{
	private SiteOneProprietaryBrandConfigDao siteOneProprietaryBrandConfigDao;


	public List<ProprietaryBrandConfigModel> getProprietaryBrandConfigByIndex(final String indexName)
	{
		validateParameterNotNull(indexName, "Parameter code must not be null");
		final List<ProprietaryBrandConfigModel> proprietaryBrandConfigModels = getSiteOneProprietaryBrandConfigDao()
				.findProprietaryBrandConfigByIndex(indexName);

		return proprietaryBrandConfigModels;

	}

	/**
	 * @return the siteOneProprietaryBrandConfigDao
	 */
	public SiteOneProprietaryBrandConfigDao getSiteOneProprietaryBrandConfigDao()
	{
		return siteOneProprietaryBrandConfigDao;
	}

	/**
	 * @param siteOneProprietaryBrandConfigDao
	 *           the siteOneProprietaryBrandConfigDao to set
	 */
	public void setSiteOneProprietaryBrandConfigDao(final SiteOneProprietaryBrandConfigDao siteOneProprietaryBrandConfigDao)
	{
		this.siteOneProprietaryBrandConfigDao = siteOneProprietaryBrandConfigDao;
	}




}
