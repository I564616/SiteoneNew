/**
 *
 */
package com.siteone.core.pim.storage;

import de.hybris.platform.azure.media.storage.WindowsAzureBlobStorageStrategy;
import de.hybris.platform.media.storage.MediaStorageConfigService.MediaFolderConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author SM04392
 *
 */
public class SiteOneWindowsAzureBlobStorageStrategy extends WindowsAzureBlobStorageStrategy
{

	private static final Logger LOG = LoggerFactory.getLogger(SiteOneWindowsAzureBlobStorageStrategy.class);

	@Override
	public void delete(final MediaFolderConfig config, final String location)
	{
		LOG.info("Inside overriden delete method " + config.getFolderQualifier());
	}


}
