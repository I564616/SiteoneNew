/**
 *
 */
package com.siteone.core.batch.task;

import de.hybris.platform.acceleratorservices.dataimport.batch.BatchHeader;
import de.hybris.platform.acceleratorservices.dataimport.batch.task.AbstractImpexRunnerTask;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.impex.model.ImpExMediaModel;
import de.hybris.platform.impex.model.cronjob.ImpExImportCronJobModel;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.impex.ImpExResource;
import de.hybris.platform.servicelayer.impex.ImportConfig;
import de.hybris.platform.servicelayer.impex.ImportResult;
import de.hybris.platform.servicelayer.impex.impl.StreamBasedImpExResource;
import de.hybris.platform.servicelayer.session.Session;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.util.CSVConstants;
import de.hybris.platform.util.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.siteone.core.event.SiteOneBatchNotificationEvent;


/**
 * @author 1230514
 *
 */
public abstract class SiteOneImpexRunnerTask extends AbstractImpexRunnerTask
{
	private static final Logger LOG = Logger.getLogger(SiteOneImpexRunnerTask.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.acceleratorservices.dataimport.batch.task. AbstractImpexRunnerTask#getImportConfig()
	 */
	@Autowired
	private UserService userService;

	private String user;
	private BaseStoreService baseStoreService;
	private BaseSiteService baseSiteService;
	private EventService eventService;
	private CommonI18NService commonI18NService;

	/**
	 * @return the commonI18NService
	 */
	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	/**
	 * @param commonI18NService
	 *           the commonI18NService to set
	 */
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
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
	 * @return the user
	 */
	public String getUser()
	{
		return user;
	}

	/**
	 * @param user
	 *           the user to set
	 */
	public void setUser(final String user)
	{
		this.user = user;
	}

	@Override
	public BatchHeader execute(final BatchHeader header) throws FileNotFoundException
	{
		LOG.error("Inside SiteOneImpexRunnerTask-1");
			
		if (header.getTransformedFiles() == null && header.getFile() != null && header.getFile().getName().endsWith(".impex"))
		{
			header.setEncoding(CSVConstants.HYBRIS_ENCODING);
			header.addTransformedFile(header.getFile());
		}

		Assert.notNull(header, "must not be null");
		Assert.notNull(header.getEncoding(), "must not be null");
		if (CollectionUtils.isNotEmpty(header.getTransformedFiles()))
		{
			final Session localSession = getSessionService().createNewSession();

			final UserModel batchUser = getUserModel(getUser());
			if (batchUser != null)
			{
				LOG.debug("Set User:" + batchUser.getUid());
				userService.setCurrentUser(batchUser);
			}
			try
			{
				for (final File file : header.getTransformedFiles())
				{
					LOG.info("Impex Import for file :" + file.getName() + " Started.");
					processFile(file, header.getEncoding());
					LOG.info("Impex Import for file :" + file.getName() + " Ended.");
				}
			}catch (final Exception e)
			{
				LOG.error("Exception occured in SiteOneImpexRunnerTask", e);
			}
			finally
			{
				getSessionService().closeSession(localSession);
			}
		}
		return header;
	}

	/**
	 * @param user2
	 * @return
	 */
	private UserModel getUserModel(final String userForBatch)
	{
		// TODO Auto-generated method stub
		final UserModel userModel = userService.getUserForUID(userForBatch);
		if (userModel != null)
		{
			return userModel;
		}
		else
		{
			return null;
		}

	}

	@Override
	public abstract ImportConfig getImportConfig();

	@Override
	protected void processFile(final File file, final String encoding) throws FileNotFoundException
	{
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(file);
			final ImportConfig config = getImportConfig();
			if (config == null)
			{
				LOG.error(String.format("Import config not found. The file %s won't be imported.", file.getName()));
				return;
			}
			final ImpExResource resource = new StreamBasedImpExResource(fis, encoding);
			config.setScript(resource);
			final ImportResult importResult = getImportService().importData(config);
			if (importResult.isError() && importResult.hasUnresolvedLines())
			{
				importResult.getUnresolvedLines().getPreview();
				LOG.error(importResult.getUnresolvedLines().getPreview());

				if (Config.getBoolean("siteone.batch.notification.enable", false)
						&& StringUtils.isNotEmpty(Config.getString("siteone.batch.notification.toEmail", null)))
				{
					getEventService().publishEvent(initializeEvent(new SiteOneBatchNotificationEvent(), file.getName(),
							importResult.getCronJob(), importResult.getUnresolvedLines()));
				}
			}
		}
		finally
		{
			IOUtils.closeQuietly(fis);
		}
	}

	protected SiteOneBatchNotificationEvent initializeEvent(final SiteOneBatchNotificationEvent event, final String fileName,
			final ImpExImportCronJobModel cronjob, final ImpExMediaModel impExMediaModel)
	{
		event.setFileName(fileName);
		event.setImportCronjob(cronjob);
		event.setEmailReceiver(Config.getString("siteone.batch.notification.toEmail", null));
		event.setBaseStore(getBaseStoreService().getBaseStoreForUid("siteone"));
		event.setSite(getBaseSiteService().getBaseSiteForUID("siteone"));
		event.setLanguage(getCommonI18NService().getCurrentLanguage());
		return event;

	}

}
