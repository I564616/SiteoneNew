/**
 *
 */
package com.siteone.core.cronjob.service.impl;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.site.BaseSiteService;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.siteone.core.cronjob.dao.NoVisibleUomCronJobDao;
import com.siteone.core.cronjob.service.NoVisibleUomCronJobService;
import com.siteone.core.event.NoVisibleUomEvent;
import com.siteone.core.model.NoVisibleUomCronJobModel;


/**
 * @author HR03708
 *
 */
public class DefaultNoVisibleUomCronJobService implements NoVisibleUomCronJobService
{
	@Resource(name = "noVisibleUomCronJobDao")
	private NoVisibleUomCronJobDao noVisibleUomCronJobDao;

	public static final String FILENAME = "NoVisibleUomProductReportOn_";
	private static final String DATE_FORMATTER = "MMddyy_hhmms";
	private static final Logger LOG = Logger.getLogger(DefaultNoVisibleUomCronJobService.class);
	public static final String CSV = ".csv";
	private static final String FILE_HEADER = "Product Code,Remark";

	private EventService eventService;
	private CommonI18NService commonI18NService;
	private ModelService modelService;
	private BaseSiteService baseSiteService;

	@Override
	public void getNoVisibleUomProducts(final NoVisibleUomCronJobModel model) throws IOException
	{

		final List<String> productsList = noVisibleUomCronJobDao.getAllProducts();
		if (!CollectionUtils.isEmpty(productsList))
		{
			final File file = new File(FILENAME + CSV);
			try (PrintWriter printWriter = new PrintWriter(file))
			{
				printWriter.println(FILE_HEADER);
				for (final String pCode : productsList)
				{
					if (noVisibleUomCronJobDao.isNoVisibleUomProduct(pCode))
					{
						printWriter.println(pCode + ",No Visible UOM");
					}
					if (noVisibleUomCronJobDao.isZeroMultiplierProduct(pCode))
					{
						printWriter.println(pCode + ",Multiplier with 0 value");
					}
				}
			}

			catch (final IOException ioException)
			{
				LOG.error("Exception occured in novsibleUom report File : " + FILENAME, ioException);
				model.setResult(CronJobResult.FAILURE);
				model.setStatus(CronJobStatus.ABORTED);
			}
			finally
			{
				final Date date = new Date();
				model.setLastExecutionTime(date);
				getModelService().save(model);
				getEventService().publishEvent(initializeEvent(new NoVisibleUomEvent(), file));

			}
		}
	}

	private NoVisibleUomEvent initializeEvent(final NoVisibleUomEvent event, final File file)
	{
		DataInputStream noVisibleUomDataStream = null;
		try
		{
			noVisibleUomDataStream = new DataInputStream(new FileInputStream(file));
		}
		catch (final FileNotFoundException e)
		{
			LOG.error("Exception occured in novsibleUom report File " + FILENAME, e);
		}
		event.setSite(getBaseSiteService().getBaseSiteForUID("siteone"));
		event.setFileName(getFileName());
		event.setLanguage(getCommonI18NService().getCurrentLanguage());
		event.setNoVisibleUomDataStream(noVisibleUomDataStream);
		return event;

	}

	private String getFileName()
	{
		final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMATTER);
		final String currentServerDate = dateFormat.format(new DateTime().toDate());
		return (FILENAME + currentServerDate + CSV);
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
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
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







}


