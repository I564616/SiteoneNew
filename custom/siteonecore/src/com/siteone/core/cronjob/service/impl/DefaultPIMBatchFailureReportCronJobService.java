/**
 *
 */
package com.siteone.core.cronjob.service.impl;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.variants.model.GenericVariantProductModel;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.siteone.core.cronjob.dao.PIMBatchFailureReportCronJobDao;
import com.siteone.core.cronjob.service.PIMBatchFailureReportCronJobService;
import com.siteone.core.event.SiteonePIMBatchFailureReportEvent;
import com.siteone.core.model.PIMBatchFailureReportCronJobModel;
import com.siteone.core.model.process.PimBatchFailureEmailProcessModel;
import com.siteone.core.pimmessage.dao.PIMBatchFailureReportMessageDao;



/**
 * @author SR02012
 *
 */
public class DefaultPIMBatchFailureReportCronJobService implements PIMBatchFailureReportCronJobService
{
	private PIMBatchFailureReportCronJobDao pimBatchFailureReportCronjobDao;
	private PIMBatchFailureReportMessageDao pimBatchFailureReportMessageDao;
	private ModelService modelService;
	private BaseSiteService baseSiteService;

	@Resource(name = "productService")
	private ProductService productService;

	@Resource(name = "catalogVersionService")
	private CatalogVersionService catalogVersionService;
	

	public static final String FILENAME = "PIMBatchFailureReportOn_";
	private static final String DATE_FORMATTER = "MMddyy_hhmms";
	private static final String FIELD_DELIMITER = ",";
	private static final Logger LOG = Logger.getLogger(DefaultPIMBatchFailureReportCronJobService.class);
	public static final String CSV = ".csv";
	private static final String[] FILE_HEADER =
	{ "SKU_ID", "Item number", "Division", "Error", "Load time" };
	private EventService eventService;
	private CommonI18NService commonI18NService;
	protected static final Map<String, String> errorAndUserFriendlyMessage = new HashMap<>();

	@Override
	public void getFailedBatchForDay(final PIMBatchFailureReportCronJobModel model)
	{
		final List<PimBatchFailureEmailProcessModel> failedBatchDetails = getPimBatchFailureReportCronjobDao()
				.getFailedBatchForDay();
		if (!CollectionUtils.isEmpty(failedBatchDetails))
		{
			PrintWriter printWriter = null;
			File file = null;
			try
			{
				file = File.createTempFile(FILENAME, CSV);
				printWriter = new PrintWriter(file);
				printHeader(printWriter);
				printWriter.println();
				for (final PimBatchFailureEmailProcessModel failedBatch : failedBatchDetails)
				{
						if (failedBatch.getFailedBatch().contains("processing of the integration object"))
						{
							printMessage("object",failedBatch,printWriter);
						}
						else
						{
							printMessage("errors:",failedBatch,printWriter);
					   }
				  }
			  }
			
			catch (final IOException ioException)
			{
				LOG.error("Exception occured in PIM Batch Failure report File : " + FILENAME, ioException);
				model.setResult(CronJobResult.FAILURE);
				model.setStatus(CronJobStatus.ABORTED);
			}
			finally
			{
				final Date date = new Date();
				model.setLastExecutionTime(date);
				getModelService().save(model);
				if (null != printWriter)
				{
					printWriter.flush();
					generateErrorEmail(file);
				}
			}
		}
	}
 private void printMessage(String keyword,PimBatchFailureEmailProcessModel failedBatch,PrintWriter printWriter)
 {
	   int index, barIndex;
		String extractedErrorMessage, productCode, itemNumber, errorMessage,baseProductCode,userFriendlyMessage,constraint, division;
		baseProductCode=productCode = itemNumber = errorMessage = userFriendlyMessage = division = null;
		String[] splittedErrorMessage = null;
		ProductModel productModel = null;
		if(keyword.equalsIgnoreCase("object"))
		{
			constraint = ":";
		}
		else
		{
			constraint = "|";
		}
	   index = failedBatch.getFailedBatch().indexOf(keyword);
		extractedErrorMessage = failedBatch.getFailedBatch().substring(index + 13);
		splittedErrorMessage = extractedErrorMessage.split("<br>");
		for (final String entry : splittedErrorMessage)
		{
		if ((!StringUtils.isEmpty(entry)) && (entry.contains(constraint)))
		{
		   if(constraint.equals("|") && entry.contains(":"))
		   {
		     constraint=":";
		   }
			barIndex = entry.lastIndexOf("|");
			if(constraint.equals(":"))
			{
			final int colonIndex = entry.indexOf(":");
			productCode = entry.substring(barIndex + 1, colonIndex).trim();
			errorMessage = entry.substring(colonIndex + 1).trim();
			}
			if(entry.contains("siteoneCAProductCatalog"))
			{
				division = "CA";
			}
			if(entry.contains("siteoneProductCatalog"))
			{
				division = "US";
			}
			else
			{
			productCode = entry.substring(barIndex + 1).trim();
			}
			try
			{
			productModel = productService.getProductForCode(
					catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online"), productCode);
			itemNumber = productModel.getItemNumber();
			}
			catch(Exception e)
			{
				LOG.error(e.getMessage(), e);
			}
	if (!StringUtils.isEmpty(errorMessage))
   {
	if (errorMessage.contains(productCode))
	{
		errorMessage = errorMessage.replaceAll(productCode, "{productCode}");
	}
	if (productModel instanceof GenericVariantProductModel && ((GenericVariantProductModel) productModel).getBaseProduct() != null
				&& ((GenericVariantProductModel) productModel).getBaseProduct().getCode() != null)
		{
			baseProductCode = ((GenericVariantProductModel) productModel).getBaseProduct().getCode();
			if (errorMessage.contains(baseProductCode))
			{
				errorMessage = errorMessage.replaceAll(baseProductCode, "{baseProductCode}");
			}
		}
if(errorAndUserFriendlyMessage.containsKey(errorMessage) && (!errorAndUserFriendlyMessage.get(errorMessage).contains("We will analyse and update")))
{
	userFriendlyMessage = errorAndUserFriendlyMessage.get(errorMessage);
}
else
{
userFriendlyMessage = getPimBatchFailureReportMessageDao().getUserFriendlyMessage(errorMessage.trim());
if (StringUtils.isEmpty(userFriendlyMessage))
{
	userFriendlyMessage = "Internal Hybris Error : \' " + errorMessage
			+ " \' .We will analyse and update the rootcause for this error.";
}
errorAndUserFriendlyMessage.put(errorMessage,userFriendlyMessage);
}
if(productCode.contains(":")) {
	productCode = productCode.split(":")[0];
}
printWriter.println(encodeMessage(productCode) + FIELD_DELIMITER + encodeMessage(itemNumber) + FIELD_DELIMITER
		+ encodeMessage(division) + FIELD_DELIMITER + encodeMessage(userFriendlyMessage) + FIELD_DELIMITER + failedBatch.getCreationtime());
errorMessage=null;		
	}
	}
	else if ((!StringUtils.isEmpty(entry)) && (StringUtils.isEmpty(errorMessage)) && constraint.equals("|"))
	{
			errorMessage = entry.trim();
	}
  }
 }
	private void printHeader(final PrintWriter printWriter)
	{
		for (final String header : FILE_HEADER)
		{
			printWriter.print(header + FIELD_DELIMITER);
		}
	}

	private void generateErrorEmail(final File file)
	{
		getEventService().publishEvent(initializeEvent(new SiteonePIMBatchFailureReportEvent(), file));
	}

	private SiteonePIMBatchFailureReportEvent initializeEvent(final SiteonePIMBatchFailureReportEvent event, final File file)
	{
		DataInputStream failedBatchDataStream = null;
		try
		{
			failedBatchDataStream = new DataInputStream(new FileInputStream(file));
		}
		catch (final FileNotFoundException e)
		{
			LOG.error("Exception occured in PIM Batch Failure report File " + FILENAME, e);
		}
		event.setFailedBatchDataStream(failedBatchDataStream);
		event.setSite(getBaseSiteService().getBaseSiteForUID("siteone"));
		event.setFileName(getFileName());
		event.setLanguage(getCommonI18NService().getCurrentLanguage());
		return event;

	}

	private static String encodeMessage(final String message)
	{
		return "\"" + message + "\"";
	}

	private String getFileName()
	{
		final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMATTER);
		final String currentServerDate = dateFormat.format(new DateTime().toDate());
		return (FILENAME + currentServerDate+CSV);
	}

	public BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}

	public PIMBatchFailureReportCronJobDao getPimBatchFailureReportCronjobDao()
	{
		return pimBatchFailureReportCronjobDao;
	}

	public void setPimBatchFailureReportCronjobDao(final PIMBatchFailureReportCronJobDao pimBatchFailureReportCronjobDao)
	{
		this.pimBatchFailureReportCronjobDao = pimBatchFailureReportCronjobDao;
	}

	/**
	 * @return the pimBatchFailureReportMessageDao
	 */
	public PIMBatchFailureReportMessageDao getPimBatchFailureReportMessageDao()
	{
		return pimBatchFailureReportMessageDao;
	}

	/**
	 * @param pimBatchFailureReportMessageDao
	 *           the pimBatchFailureReportMessageDao to set
	 */
	public void setPimBatchFailureReportMessageDao(final PIMBatchFailureReportMessageDao pimBatchFailureReportMessageDao)
	{
		this.pimBatchFailureReportMessageDao = pimBatchFailureReportMessageDao;
	}

	public ModelService getModelService()
	{
		return modelService;
	}

	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	public EventService getEventService()
	{
		return eventService;
	}

	public void setEventService(final EventService eventService)
	{
		this.eventService = eventService;
	}

	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}
}
