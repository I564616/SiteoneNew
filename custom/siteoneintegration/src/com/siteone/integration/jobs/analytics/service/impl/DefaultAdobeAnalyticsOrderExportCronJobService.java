package com.siteone.integration.jobs.analytics.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hybris.platform.servicelayer.config.ConfigurationService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import com.siteone.core.model.AdobeAnalyticsOrderExportCronJobModel;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.jobs.analytics.dao.AdobeAnalyticsOrderExportCronJobDao;
import com.siteone.integration.jobs.analytics.service.AdobeAnalyticsOrderExportCronJobService;
import com.siteone.integration.services.dataimport.SiteOneBlobDataImportService;

import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.model.ModelService;

public class DefaultAdobeAnalyticsOrderExportCronJobService
		implements AdobeAnalyticsOrderExportCronJobService {

	private AdobeAnalyticsOrderExportCronJobDao adobeAnalyticsOrderExportCronJobDao;
	private static final Logger LOG = Logger
			.getLogger(DefaultAdobeAnalyticsOrderExportCronJobService.class);
	public static final String FILENAME = "Order";
	public static final String TAB = ".tab";
	private static final String FIELD_DELIMITER = "\t";
	private static final String DATE_FORMATTER = "ddMMyy";
	private ModelService modelService;
	private static final String[] FILE_HEADER = { "## SCSiteCatalyst SAINT Import Filev:2.1",
			"## SC'## SC' indicates a SiteCatalyst pre-process header. Please do not remove these lines.",
			"## SCD:2021-07-23 05:40:01A:300090212:155", "Key\tUE Order ID" };

	/* The Blob dataImport service*/
	private SiteOneBlobDataImportService blobDataImportService;

	/* The configuration service*/
	private ConfigurationService configurationService;

	public void exportOrderMappingReport(AdobeAnalyticsOrderExportCronJobModel cronJobModel)
			throws IOException {
		List<OrderModel> listOfOrdersPlacedYesterday = getAdobeAnalyticsOrderExportCronJobDao()
				.getListOfOnlineOrdersPlacedYesterday();
		if (!CollectionUtils.isEmpty(listOfOrdersPlacedYesterday)) {
			PrintWriter printWriter = null;
			File file=null;
			final String analyticsReportContainer= getConfigurationService().getConfiguration().getString(SiteoneintegrationConstants.ANALYTICS_ORDER_NUMBER_MAPPING_LOCATION);
			try {
				file=File.createTempFile(getFileName(),TAB);
				printWriter = new PrintWriter(file);
				printHeader(printWriter);
				for (OrderModel order : listOfOrdersPlacedYesterday) {
					printWriter.println(order.getHybrisOrderNumber() + FIELD_DELIMITER + order.getCode());
				}
			} catch (IOException ioException) {
				LOG.error("Exception occured in analytics report File " + getFileName(), ioException);
				cronJobModel.setResult(CronJobResult.FAILURE);
				cronJobModel.setStatus(CronJobStatus.ABORTED);
			} finally {
				Date date = new Date();
				cronJobModel.setLastExecutionTime(date);
				getModelService().save(cronJobModel);
				if (null != printWriter) {
					printWriter.close();
				}
			}

			// Migration | Write  Blob
			getBlobDataImportService().writeBlob(file,analyticsReportContainer);
		}
	}

	public AdobeAnalyticsOrderExportCronJobDao getAdobeAnalyticsOrderExportCronJobDao() {
		return adobeAnalyticsOrderExportCronJobDao;
	}

	public void setAdobeAnalyticsOrderExportCronJobDao(
			AdobeAnalyticsOrderExportCronJobDao adobeAnalyticsOrderExportCronJobDao) {
		this.adobeAnalyticsOrderExportCronJobDao = adobeAnalyticsOrderExportCronJobDao;
	}


	private String getFileName() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMATTER);
		String currentServerDate = dateFormat.format(cal.getTime());
		return (FILENAME + SiteoneintegrationConstants.SEPARATOR_UNDERSCORE + currentServerDate);

	}

	private void printHeader(PrintWriter printWriter) {
		for (String header : FILE_HEADER) {
			printWriter.println(header);
		}
	}

	public ModelService getModelService() {
		return modelService;
	}

	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}

	/**
	 * Getter method for blobDataImportService
	 *
	 * @return the blobDataImportService
	 */
	public SiteOneBlobDataImportService getBlobDataImportService() {
		return blobDataImportService;
	}

	/**
	 * Setter method for  blobDataImportService
	 *
	 * @param blobDataImportService
	 *            the blobDataImportService to set
	 */
	public void setBlobDataImportService(SiteOneBlobDataImportService blobDataImportService) {
		this.blobDataImportService = blobDataImportService;
	}

	/**
	 * Getter method for configurationService
	 *
	 * @return the configurationService
	 */
	public ConfigurationService getConfigurationService() {
		return configurationService;
	}

	/**
	 * Setter method for  configurationService
	 *
	 * @param configurationService
	 *            the configurationService to set
	 */
	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

}
