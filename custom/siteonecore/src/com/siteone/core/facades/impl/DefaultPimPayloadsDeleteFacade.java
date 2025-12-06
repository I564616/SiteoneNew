/**
*
*/
package com.siteone.core.facades.impl;

import de.hybris.platform.servicelayer.config.ConfigurationService;

import java.util.List;

import com.siteone.core.facade.PimPayloadsDeleteFacade;
import com.siteone.core.services.PimPayloadsDeleteService;


/**
 * @author SM04392
 *
 */
public class DefaultPimPayloadsDeleteFacade implements PimPayloadsDeleteFacade
{

	private ConfigurationService configurationService;
	private PimPayloadsDeleteService pimPayloadsDeleteService;

	public PimPayloadsDeleteService getPimPayloadsDeleteService()
	{
		return pimPayloadsDeleteService;
	}

	public void setPimPayloadsDeleteService(final PimPayloadsDeleteService pimPayloadsDeleteService)
	{
		this.pimPayloadsDeleteService = pimPayloadsDeleteService;
	}

	public ConfigurationService getConfigurationService()
	{
		return configurationService;
	}

	public void setConfigurationService(final ConfigurationService configurationService)
	{
		this.configurationService = configurationService;
	}

	@Override
	public void deletePayloadsOlderThanTwoWeeks()
	{
		// YTODO Auto-generated method stub
		final String pimPath = getConfigurationService().getConfiguration().getString("pimbatchpayload.filepath");
		final List<String> folders = pimPayloadsDeleteService.getBlobPath("outbound", pimPath);
		pimPayloadsDeleteService.deleteBlobs(folders);

	}
}