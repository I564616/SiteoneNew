/**
 *
 */
package com.siteone.backoffice.dataupload;


import de.hybris.platform.servicelayer.config.ConfigurationService;

import java.io.File;

import org.apache.log4j.Logger;


/**
 * @author 1099417
 *
 */
public class SiteOneBackOfficeDataUpload
{
	private static final Logger LOG = Logger.getLogger(SiteOneBackOfficeDataUpload.class);
	//private static final String SLASH = "/";

	private ConfigurationService configurationService;

	/**
	 * @return the configurationService
	 */
	public ConfigurationService getConfigurationService()
	{
		return configurationService;
	}

	/**
	 * @param configurationService
	 *           the configurationService to set
	 */

	public void setConfigurationService(final ConfigurationService configurationService)
	{
		this.configurationService = configurationService;
	}

	public boolean uploadFile(final File file, final String filePath, final boolean evt)
	{
		return uploadTargetLocation(file, filePath, evt);
	}

	/**
	 * This method is used to upload the file into target location
	 *
	 * @param file
	 * @param filePath
	 * @param evt
	 * @return
	 */
	private boolean uploadTargetLocation(final File file, final String filePath, final boolean evt)
	{
		boolean isTrue = false;
		try
		{
			if (evt)
			{

				if (null != filePath)
				{
					final String basePath = getConfigurationService().getConfiguration().getString("siteone.batch.impex.basefolder");



					final String fullPath = basePath + filePath;

					LOG.info("basePath " + basePath);

					LOG.info("backoffice file Path " + filePath);


					LOG.info("Target fullPath  " + fullPath);

					final File tempFile = new File(fullPath + File.separator + file.getName() + ".tmp");

					LOG.info(file.getName() + " tmp file Copied Successfully  ");

					file.renameTo(tempFile);


					//moving the file to target folder


					isTrue = tempFile.renameTo(new File(fullPath + File.separator + file.getName()));

					if (isTrue)
					{
						LOG.info(file.getName() + " Copied Successfully in target Folder ");
					}
					else
					{
						LOG.info(file.getName() + " not Copied Successfully in target Folder ");
					}


				}
			}
			else
			{
				if (null != filePath)
				{
					LOG.info("Target fullPath  " + filePath);

					final File tempFile = new File(filePath + File.separator + file.getName() + ".tmp");

					LOG.info(file.getName() + " tmp file Copied Successfully  ");

					file.renameTo(tempFile);


					//moving the file to target folder


					isTrue = tempFile.renameTo(new File(filePath + File.separator + file.getName()));

					if (isTrue)
					{
						LOG.info(file.getName() + " Copied Successfully in target Folder ");
					}
					else
					{
						LOG.info(file.getName() + " not Copied Successfully in target Folder ");
					}


				}
			}
		}
		catch (final Exception e)
		{

			LOG.error("File was not moved successfully in Target folder" + e.getMessage());
			LOG.error("File Moving Error", e);
		}
		return isTrue;

	}


}


