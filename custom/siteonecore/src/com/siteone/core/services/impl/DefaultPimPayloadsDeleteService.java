/**
 *
 */
package com.siteone.core.services.impl;

import de.hybris.platform.servicelayer.config.ConfigurationService;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.*;
import com.siteone.core.services.PimPayloadsDeleteService;
import com.siteone.integration.services.dataimport.SiteOneBlobDataImportService;


/**
 * @author SM04392
 *
 */
public class DefaultPimPayloadsDeleteService implements PimPayloadsDeleteService
{

	private static final Logger LOG = LoggerFactory.getLogger(DefaultPimPayloadsDeleteService.class);
	private ConfigurationService configurationService;
	private SiteOneBlobDataImportService blobDataImportService;


	@Override
	public List<String> getBlobPath(final String containerName, final String filePath)
	{
		// YTODO Auto-generated method stub
		final List<String> folders = new ArrayList<>();
		try
		{
			final CloudStorageAccount storageAccount = settingBlobConnection();
			final CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
			final CloudBlobContainer container = blobClient.getContainerReference(containerName);
			final CloudBlobDirectory path = container.getDirectoryReference(filePath);

			if (container.exists())
			{
				for (final ListBlobItem files : path.listBlobs(null, true, EnumSet.noneOf(BlobListingDetails.class), null, null))
				{
					final String folder = files.getUri().getPath();
					folders.add(folder);

				}
			}
			return folders;
		}
		catch (StorageException | URISyntaxException | InvalidKeyException ex)
		{
			LOG.info("context", ex);
		}
		return Collections.emptyList();
	}


	@Override
	public void deleteBlobs(final List<String> folders)
	{
		// YTODO Auto-generated method stub
		for (final String payload : folders)
		{
			try
			{
				final int position = payload.lastIndexOf("/");
				final String dateToCheck = payload.substring(position - 10, position);
				LOG.info("The path in environment is [{}]", payload);
				final Date fileDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateToCheck);
				if (fileDate.before(DateTime.now().minusDays(30).toDate()))
				{
					final String pimFile = getConfigurationService().getConfiguration().getString("pimbatchpayload.filepath");
					final String pimFilePath = pimFile.concat("/").concat(payload.substring(position - 21, position));
					getBlobDataImportService().deleteBlob("outbound", pimFilePath, 0);
				}
			}
			catch (final ParseException ex)
			{
				LOG.info("context", ex);
			}
		}

	}

	private CloudStorageAccount settingBlobConnection() throws InvalidKeyException, URISyntaxException
	{

		final String storageConnectionString = configurationService.getConfiguration()
				.getString("azure.hotfolder.storage.account.connection-string");
		return CloudStorageAccount.parse(storageConnectionString);

	}


	public ConfigurationService getConfigurationService()
	{
		return configurationService;
	}


	public void setConfigurationService(final ConfigurationService configurationService)
	{
		this.configurationService = configurationService;
	}


	public SiteOneBlobDataImportService getBlobDataImportService()
	{
		return blobDataImportService;
	}


	public void setBlobDataImportService(final SiteOneBlobDataImportService blobDataImportService)
	{
		this.blobDataImportService = blobDataImportService;
	}
}