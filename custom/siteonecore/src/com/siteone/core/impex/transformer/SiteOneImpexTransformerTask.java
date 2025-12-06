/**
 *
 */
package com.siteone.core.impex.transformer;

import de.hybris.platform.acceleratorservices.dataimport.batch.BatchHeader;
import de.hybris.platform.acceleratorservices.dataimport.batch.converter.ImpexConverter;
import de.hybris.platform.acceleratorservices.dataimport.batch.task.CleanupHelper;
import de.hybris.platform.acceleratorservices.dataimport.batch.task.ImpexTransformerTask;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.util.CSVConstants;
import de.hybris.platform.util.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.siteone.core.event.SiteOneBatchNotificationEvent;
import com.siteone.core.model.SiteOneFeedFileInfoModel;


/**
 * @author 1085284
 *
 */
public class SiteOneImpexTransformerTask extends ImpexTransformerTask
{
	private final String encoding = CSVConstants.HYBRIS_ENCODING;
	private CleanupHelper cleanupHelper;
	private BaseStoreService baseStoreService;
	private BaseSiteService baseSiteService;
	private EventService eventService;
	private ModelService modelService;

	private static final Logger LOG = Logger.getLogger(SiteOneImpexTransformerTask.class);

	@Override
	public BatchHeader execute(final BatchHeader header) throws UnsupportedEncodingException, FileNotFoundException
	{
		Assert.notNull(header, "must not be null");
		Assert.notNull(header.getFile(), "must not be null");
		final File file = header.getFile();
		header.setEncoding(encoding);

		try
		{
			// Insert file meta data information
			String feedType = "";
			if (file.getName().contains("_"))
			{
				final SiteOneFeedFileInfoModel feedFileInfo = getModelService().create(SiteOneFeedFileInfoModel.class);
				feedType = file.getName().split("_")[0];
				feedFileInfo.setFileType(feedType);
				feedFileInfo.setFileName(file.getName());
				getModelService().save(feedFileInfo);
			}
		}
		catch (final Exception ex)
		{
			LOG.error("Error occured while saving SiteOneFeedFileInfoMode fileName -" + file.getName() + ex.getMessage());
		}

		final List<ImpexConverter> converters = getConverters(file);
		int position = 1;

		final Map<String, String> convertFileException = new HashMap<String, String>();
		for (final ImpexConverter converter : converters)
		{
			File impexFile = null;
			try
			{
				impexFile = getImpexFile(file, position++);
				if (convertFile(header, file, impexFile, converter))
				{
					header.addTransformedFile(impexFile);
				}
				else
				{
					cleanupHelper.cleanupFile(impexFile);
				}
			}
			catch (final Exception exception)
			{
				LOG.error(exception);
				if (null != impexFile)
				{
					convertFileException.put(impexFile.getPath(), exception.getMessage());
				}

			}

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

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

}
