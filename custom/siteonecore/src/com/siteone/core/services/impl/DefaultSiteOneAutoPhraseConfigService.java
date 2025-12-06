/**
 *
 */
package com.siteone.core.services.impl;

import com.siteone.core.model.AutoPhraseConfigModel;
import com.siteone.core.product.dao.SiteOneAutoPhraseConfigDao;
import com.siteone.core.services.SiteOneAutoPhraseConfigService;

import java.util.List;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;


/**
 * @author 1091124
 *
 */
public class DefaultSiteOneAutoPhraseConfigService implements SiteOneAutoPhraseConfigService
{
	private SiteOneAutoPhraseConfigDao siteOneAutoPhraseConfigDao;


	public List<AutoPhraseConfigModel> getAutoPhraseConfigByIndex(final String indexName)
	{
		validateParameterNotNull(indexName, "Parameter code must not be null");
		final List<AutoPhraseConfigModel> autoPhraseConfigModels = getSiteOneAutoPhraseConfigDao().findAutoPhraseConfigByIndex(indexName);

		return autoPhraseConfigModels;

	}

	/**
	 * @return the siteOneAutoPhraseConfigDao
	 */
	public SiteOneAutoPhraseConfigDao getSiteOneAutoPhraseConfigDao()
	{
		return siteOneAutoPhraseConfigDao;
	}

	/**
	 * @param siteOneAutoPhraseConfigDao
	 *           the siteOneAutoPhraseConfigDao to set
	 */
	public void setSiteOneAutoPhraseConfigDao(final SiteOneAutoPhraseConfigDao siteOneAutoPhraseConfigDao)
	{
		this.siteOneAutoPhraseConfigDao = siteOneAutoPhraseConfigDao;
	}




}
