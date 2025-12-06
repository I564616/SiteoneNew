/**
 *
 */
package com.siteone.core.impex.transformer;

import de.hybris.platform.acceleratorservices.dataimport.batch.BatchHeader;
import de.hybris.platform.acceleratorservices.dataimport.batch.task.CleanupHelper;
import de.hybris.platform.acceleratorservices.dataimport.batch.task.ImpexTransformerTask;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.util.CSVConstants;
import de.hybris.platform.util.Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.siteone.core.event.SiteOneBatchNotificationEvent;


public class SiteOneImpexSevenTransformerTask extends ImpexTransformerTask
{
	private final String encoding = CSVConstants.HYBRIS_ENCODING;
	private CleanupHelper cleanupHelper;
	private BaseStoreService baseStoreService;
	private BaseSiteService baseSiteService;
	private EventService eventService;
	private static final Logger LOG = Logger.getLogger(SiteOneImpexSevenTransformerTask.class);

	public static final String HYPHEN = "-".intern();
	public static final String SEMI_COLON = ";".intern();

	@Override
	public BatchHeader execute(final BatchHeader header) throws UnsupportedEncodingException, FileNotFoundException
	{
		final File file = header.getFile();
		Assert.notNull(header, "must not be null");
		Assert.notNull(file, "must not be null");

		header.setEncoding(encoding);

		PrintWriter writer = null;
		final PrintWriter errorWriter = null;

		BufferedReader objReader = null;
		final File impexFile = getImpexFile(file, 1);
		final Map<String, String> convertFileException = new HashMap<String, String>();

		try
		{
			String strCurrentLine;

			objReader = new BufferedReader(new FileReader(file));
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(impexFile), encoding)));

			while ((strCurrentLine = objReader.readLine()) != null)
			{
				boolean result = false;


				if (StringUtils.isNotEmpty(strCurrentLine) && strCurrentLine.contains("INSERT_UPDATE"))
				{
					strCurrentLine = "INSERT_UPDATE PurchProductAndOrders;uniqueKeyPurchasedProduct[unique=true];productCode;productName;itemNumber;isStoreProduct[default=false][translator=com.siteone.core.batch.translator.IsStoreProductTranslator];orderId";
					result = true;
				}
				else if (StringUtils.isNotEmpty(strCurrentLine) && StringUtils.countMatches(strCurrentLine, SEMI_COLON) == 5)
				{
					final StringBuilder sb = new StringBuilder();
					final String[] values = strCurrentLine.split(SEMI_COLON);

					if (StringUtils.isNotEmpty(values[4]) && StringUtils.isNotEmpty(values[5]))
					{
						sb.append(SEMI_COLON).append(values[5]).append(HYPHEN).append(values[4]).append(strCurrentLine);
						strCurrentLine = sb.toString();
						result = true;
					}
					else
					{
						writeErrorFile(errorWriter, file, null, strCurrentLine);
					}
				}
				else if (StringUtils.isNotBlank(strCurrentLine))
				{
					writeErrorFile(errorWriter, file, null, strCurrentLine);
				}

				try
				{
					if (result)
					{
						writer.println(strCurrentLine);
					}
				}
				catch (final IllegalArgumentException exc)
				{
					writeErrorFile(errorWriter, file, exc, strCurrentLine);
				}
			}

			header.addTransformedFile(impexFile);
		}
		catch (final Exception exception)
		{
			LOG.error(exception.getMessage());
			if (null != impexFile)
			{
				convertFileException.put(impexFile.getPath(), exception.getMessage());
			}
		}
		finally
		{
			IOUtils.closeQuietly(writer);
			IOUtils.closeQuietly(errorWriter);
			IOUtils.closeQuietly(objReader);
		}

		if (!convertFileException.isEmpty())
		{
			LOG.error("Batch Import Failure.");
			final StringBuilder stringBuilder = new StringBuilder();
			convertFileException.forEach((impexFileName, exception) -> {
				stringBuilder.append(impexFileName + "  exception : " + exception + System.getProperty("line.separator"));
			});
			if (Config.getBoolean("siteone.batch.notification.enable", false)
					&& StringUtils.isNotEmpty(Config.getString("siteone.batch.notification.toEmail", null)))
			{
				getEventService().publishEvent(initializeEvent(new SiteOneBatchNotificationEvent(), file.getName(), stringBuilder));
			}

		}
		return header;
	}

	private void writeErrorFile(PrintWriter errorWriter, final File file, final IllegalArgumentException exc,
			final String strCurrentLine) throws UnsupportedEncodingException, FileNotFoundException
	{
		if (StringUtils.isNotEmpty(strCurrentLine) && StringUtils.isNotEmpty(strCurrentLine.trim()))
		{
			LOG.error("NotProcessedForImpex7 " + HYPHEN + strCurrentLine);
			if (errorWriter == null)
			{
				errorWriter = new PrintWriter(
						new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getErrorFile(file)), encoding)));
			}

			if (exc != null)
			{
				errorWriter.println(exc.getMessage());
				errorWriter.println(": ");
			}
			errorWriter.println(strCurrentLine);
			//errorWriter.write("test");
		}
	}

	SiteOneBatchNotificationEvent initializeEvent(final SiteOneBatchNotificationEvent event, final String fileName,
			final StringBuilder stringBuilder)
	{
		event.setFileName(fileName);
		event.setImpexTransformerLog(stringBuilder.toString());
		event.setEmailReceiver(Config.getString("siteone.batch.notification.toEmail", null));
		event.setBaseStore(getBaseStoreService().getBaseStoreForUid("siteone"));
		event.setSite(getBaseSiteService().getBaseSiteForUID("siteone"));
		return event;

	}

	/**
	 * @return the cleanupHelper
	 */
	@Override
	public CleanupHelper getCleanupHelper()
	{
		return cleanupHelper;
	}

	/**
	 * @param cleanupHelper
	 *           the cleanupHelper to set
	 */
	@Override
	public void setCleanupHelper(final CleanupHelper cleanupHelper)
	{
		this.cleanupHelper = cleanupHelper;
	}

	/**
	 * @return the baseStoreService
	 */
	public BaseStoreService getBaseStoreService()
	{
		return baseStoreService;
	}

	/**
	 * @param baseStoreService
	 *           the baseStoreService to set
	 */
	public void setBaseStoreService(final BaseStoreService baseStoreService)
	{
		this.baseStoreService = baseStoreService;
	}

	/**
	 * @return the baseSiteService
	 */
	public BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	/**
	 * @param baseSiteService
	 *           the baseSiteService to set
	 */
	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}

	/**
	 * @return the eventService
	 */
	public EventService getEventService()
	{
		return eventService;
	}

	/**
	 * @param eventService
	 *           the eventService to set
	 */
	public void setEventService(final EventService eventService)
	{
		this.eventService = eventService;
	}


}
