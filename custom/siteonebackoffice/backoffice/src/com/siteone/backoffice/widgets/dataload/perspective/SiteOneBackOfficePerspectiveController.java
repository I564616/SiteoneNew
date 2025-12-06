/**
 *
 */
package com.siteone.backoffice.widgets.dataload.perspective;

import de.hybris.platform.servicelayer.config.ConfigurationService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.Logger;
import org.zkoss.util.media.Media;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Textbox;

import com.hybris.cockpitng.annotations.ViewEvent;
import com.hybris.cockpitng.util.DefaultWidgetController;
import com.siteone.backoffice.dataupload.SiteOneBackOfficeDataUpload;


public class SiteOneBackOfficePerspectiveController extends DefaultWidgetController
{

	private static final Logger LOG = Logger.getLogger(SiteOneBackOfficePerspectiveController.class);

	private static final String SLASH = "/";
	private static final String SPACE = " ";

	private Messagebox messagebox;



	@WireVariable
	private SiteOneBackOfficeDataUpload siteOneBackOfficeDataUpload;


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


	@Wire
	private Textbox searchResult;


	@Wire
	private Textbox searchLinesTextbox;

	@Wire

	@ViewEvent(componentID = "up", eventName = Events.ON_UPLOAD)
	public void updata(final UploadEvent evt) throws InterruptedException, IOException
	{

		LOG.info("begin-->updata()");

		final Media media = evt.getMedia();
		final FileOutputStream fop = null;

		final String fileName = media.getName();
		final String fileData = media.getStringData();

		final File source = new File(fileName);

		processUpload(fop, fileName, fileData);


		final boolean response = siteOneBackOfficeDataUpload.uploadFile(source, getFilePath(fileName),
				processUpload(fop, fileName, fileData));

		if (response)
		{
			messagebox.show("File Uploaded Successfully");
		}
		else
		{
			Messagebox.show("File is not  Uploaded Successfully: " + fileName, "Error", Messagebox.OK, Messagebox.ERROR);

		}

	}



	/**
	 * process the given uploaded data then move to filestore() method.
	 *
	 * @param fop
	 * @param fileName
	 * @param fileData
	 */
	private boolean processUpload(FileOutputStream fop, final String fileName, final String fileData)
	{
		boolean isOnload = true;

		if (this.getWidgetSettings().getBoolean("useHybrisDataDirectory"))
		{
			if (fileName != null)
			{
				try
				{
					if (findExtension(fileName))
					{
						fop = fileStoreData(fileName, fileData);

						isOnload = true;
					}
					else
					{
						Messagebox.show("Please Upload the TXT file: " + fileName, "Error", Messagebox.OK, Messagebox.ERROR);


					}


				}
				catch (final Exception e)
				{
					LOG.error("File Upload is failed" + e.getMessage() +"File Upload is failed Cause is " + e.getCause()+"FIle Upload Error", e);
				}
				finally
				{
					try
					{
						if (fop != null)
						{
							fop.close();
						}
					}
					catch (final IOException e)
					{

						LOG.error("IO Error", e);
					}
				}
			}
			else
			{

				LOG.error("Upload the correct file : " + fileName);

			}
		}
		else
		{
			if (fileName != null)
			{
				try
				{
					if (findExtension(fileName))
					{
						fop = fileStoreData(fileName, fileData);
						isOnload = false;
					}
					else
					{
						Messagebox.show("Please Upload the TXT file: " + fileName, "Error", Messagebox.OK, Messagebox.ERROR);

					}


				}
				catch (final Exception e)
				{
					LOG.error("File Upload is failed" + e.getMessage() +"File Upload is failed Cause is " + e.getCause()+"FIle Upload Error", e);
				}

				finally
				{
					try
					{
						if (fop != null)
						{
							fop.close();
						}
					}
					catch (final IOException e)
					{
						LOG.error("IO Error", e);
					}
				}

			}
			else
			{
				LOG.error("Check the File Location Path in  backoffice: " + fileName);

			}
		}
		return isOnload;
	}


	/**
	 * Store the data in the file uploaded the file.
	 *
	 * @param fileName
	 * @param fileData
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private FileOutputStream fileStoreData(final String fileName, final String fileData)
	{
		FileOutputStream fop = null;
		try
		{
			File source;
			source = new File(fileName);


			LOG.info("Source File Path :" + source.getAbsolutePath());

			fop = new FileOutputStream(source);


			// if file doesnt exists, then create it
			/*
			 * if (!source.exists()) { source.createNewFile(); }
			 */
			// get the content in bytes
			final byte[] contentInBytes = fileData.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();




		}
		catch (final FileNotFoundException fe)
		{
			Messagebox.show(" Not able to store the data in the file : " + fileName, "Error", Messagebox.OK, Messagebox.ERROR);
			LOG.error("Not able to store the data in the file " + fe.getMessage()+"FIle Upload Error", fe);
		}
		catch (final IOException io)
		{
			LOG.error("IO Exception was caught" + io.getMessage()+"IO Error", io);
		}
		return fop;
	}

	/**
	 * find the extension of the file
	 *
	 * @param fileName
	 * @return
	 */
	private boolean findExtension(final String fileName)
	{

		LOG.info(" begin-->findExtension() ");

		boolean isExt = false;

		final int dot = fileName.lastIndexOf(".");


		final String extensionName = fileName.substring(dot + 1);

		LOG.info("file extension is: " + extensionName);


		isExt = extensionName.equalsIgnoreCase("txt");

		return isExt;

	}

	/**
	 * get the value of back office key.It will be used in upload() method
	 *
	 * @param fileName
	 * @return
	 */
	public String getFilePath(final String fileName)
	{

		LOG.info("begin -->getFilePath()");

		String filePath = null;

		final String key = findKey(fileName);

		filePath = this.getWidgetSettings().getString(key);

		if (null != filePath)
		{

			LOG.info("backoffice key is :" + key);

			LOG.info("backoffice keyvalue is: " + filePath);


		}
		else
		{

			Messagebox.show(" Key value is not correct for given upload file : " + fileName, "Error", Messagebox.OK,
					Messagebox.ERROR);
			LOG.error("Key value is not found in the backoffice for uploaded file");



		}
		return filePath;
	}

	/**
	 * Form the key of given uploaded file.
	 *
	 * @param fileName
	 * @return
	 */
	private String findKey(final String fileName)
	{

		LOG.info("begin -->findKey()");

		String fname = fileName;
		final int pos = fname.lastIndexOf(".");
		if (pos > 0)
		{
			fname = fname.substring(0, pos);
		}

		LOG.info("fname is :" + fname);


		final String delimiter = this.getWidgetSettings().getString("filename_delimiter");

		//final String fullDemiter = delimiter;

		final String[] sptList = fname.split(delimiter);

		LOG.info("delimiter is  :" + delimiter);

		LOG.info("sptList val is :" + sptList[0]);

		final String key = "filename_";
		final String fullKey = key + sptList[0];

		LOG.info("key is :" + fullKey);

		return fullKey;

	}




	//search for log file

	@ViewEvent(componentID = "search", eventName = Events.ON_CLICK)
	public void submit() throws InterruptedException
	{

		LOG.info("search box");

		final String lineno = searchLinesTextbox.getValue();

		final String command = "tail -" + lineno + "f";

		LOG.info("lineno " + lineno);

		LOG.info("command " + command);

		if (this.getWidgetSettings().getString("logfilelocation") != null)
		{

			final String searchResultData = searchResult(command);

			searchResult.setValue(searchResultData);

			searchResult.setVisible(true);

			//			lbl.setValue(searchResultData);
			//			lbl.setVisible(true);
		}

	}

	/**
	 * Current date,it will be used in search() method
	 *
	 * @return
	 */
	private String currentDate()
	{

		final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		final LocalDateTime now = LocalDateTime.now();

		final String currDate = dtf.format(now);

		return currDate;

	}

	/**
	 * pass the log command then it will be give the current date search result of given number of lines.
	 *
	 * @param command
	 * @return
	 */
	private String searchResult(final String command) throws InterruptedException
	{

		LOG.info(" searchResult()-->begin");

		String line = null;
		String searchData = null;


		final String logBasePath = getConfigurationService().getConfiguration().getString("log.directory");


		final String logName = "console-" + currentDate() + ".log";

		final String logPath = this.getWidgetSettings().getString("logfilelocation");

		final String fullLogName = logBasePath + SLASH + logPath + SLASH + logName;

		final String fullCommand = command + SPACE + fullLogName;

		LOG.info("logName is " + logName);
		LOG.info(" Command->" + command);
		LOG.info("fullLogName is " + fullLogName);

		LOG.info("fullCommand is " + fullCommand);

		try
		{

			//command + "\\" + fullLogName
			//"cmd /c type D:\\hybris\\log\\tomcat\\console-20161213.log"
			final Process p = Runtime.getRuntime().exec(fullCommand.split(" "));
			p.waitFor();
			final BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			while ((line = reader.readLine()) != null)
			{
				searchData = line;
			}

			LOG.info("search Result Data is " + searchData);

		}
		catch (final IOException exception)
		{
			LOG.warn("IOException is " + exception.getMessage());

			searchResult.setValue(exception.getMessage());
			searchResult.setVisible(true);

			LOG.error("IO Error", exception);
		}
		//		catch (final InterruptedException InterruptedEx)
		//		{
		//		    LOG.info("Interrupted Error", InterruptedEx);
		//			LOG.warn("Exception is " + InterruptedEx.getMessage());
		//
		//			searchResult.setValue(InterruptedEx.getMessage());
		//			searchResult.setVisible(true);
		//
		//
		//		}


		return searchData;


	}

}
