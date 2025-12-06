package com.siteone.integration.jobs.analytics.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.siteone.integration.services.dataimport.SiteOneBlobDataImportService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.siteone.core.model.AdobeAnalyticsCustomerExportCronJobModel;
import com.siteone.core.model.AdobeAnalyticsRealtimeCustomerExportCronJobModel;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.customer.data.SiteoneAnalyticsCustomerMappingData;
import com.siteone.integration.jobs.analytics.AdobeAnalyticsRealtimeCustomerExportCronJob;
import com.siteone.integration.jobs.analytics.dao.AdobeAnalyticsCustomerExportCronJobDao;
import com.siteone.integration.customer.data.SiteoneAnalyticsRealtimeCustomerMappingData;
import com.siteone.integration.jobs.analytics.service.AdobeAnalyticsCustomerExportCronJobService;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.model.ModelService;

public class DefaultAdobeAnalyticsCustomerExportCronJobService implements AdobeAnalyticsCustomerExportCronJobService {

	private AdobeAnalyticsCustomerExportCronJobDao adobeAnalyticsCustomerExportCronJobDao;
	private ModelService modelService;
	private static final Logger LOG = Logger.getLogger(DefaultAdobeAnalyticsCustomerExportCronJobService.class);
	public static final String FILENAME = "AccountApplication";
	public static final String TAB = ".tab";
	private static final String FIELD_DELIMITER = "\t";
	private static final String DATE_FORMATTER = "ddMMyy";
	private static final String[] FILE_HEADER = { "## SC	SiteCatalyst SAINT Import File	v:2.1",
			"## SC	'## SC' indicates a SiteCatalyst pre-process header. Please do not remove these lines.",
			"## SC	D:2021-09-12 23:12:50	A:300085769:282",
			"Key\tcustomer ID\taccount request time\taccount creation time\tAccount Type\tPrimary business\tUE Account number\tpreferred Language" };
	public static final String FILENAME1 = "RealtimeAccountApplication";
	private static final String[] FILE_HEADER1 = { "## SC	SiteCatalyst SAINT Import File	v:2.1",
			"## SC	'## SC' indicates a SiteCatalyst pre-process header. Please do not remove these lines.",
			"## SC	D:2021-09-12 23:12:50	A:300085769:282",
			"Key\tcustomer ID\taccount request time\taccount creation time\tAccount Type\tPrimary business\tUE Account number\tpreferred Language \tUUID Type" };

	/* The Blob dataImport service*/
	private SiteOneBlobDataImportService blobDataImportService;

	/* The configuration service*/
	private ConfigurationService configurationService;

	@Override
	public void exportCustomerMappingReport(AdobeAnalyticsCustomerExportCronJobModel cronJobModel) throws IOException {

		List<SiteoneAnalyticsCustomerMappingData> siteoneAnalyticsCustomerMappingList = getAdobeAnalyticsCustomerExportCronJobDao()
				.getNewlyCreatedCustomersList();
		if (!CollectionUtils.isEmpty(siteoneAnalyticsCustomerMappingList)) {
			PrintWriter printWriter = null;
			File file=null;
			final String analyticsReportContainer= getConfigurationService().getConfiguration().getString(SiteoneintegrationConstants.ANALYTICS_ORDER_NUMBER_MAPPING_LOCATION);
			try {
					file=File.createTempFile(getFileName(),TAB);
					printWriter = new PrintWriter(file);

					printHeader(printWriter);
					for (SiteoneAnalyticsCustomerMappingData data : siteoneAnalyticsCustomerMappingList) {
						printWriter.println(checkIfBlank(data.getUuid()) + FIELD_DELIMITER + convertToHex(checkIfBlank(data.getEmail()))
								+ FIELD_DELIMITER + checkIfBlank(data.getRequestedTime()).split("\\.")[0] + FIELD_DELIMITER
								+ checkIfBlank(data.getCreationTime()).split("\\.")[0] + FIELD_DELIMITER
								+ checkIfBlank(data.getAccountType()) + FIELD_DELIMITER
								+ checkIfBlank(data.getPrimarybusiness()) + FIELD_DELIMITER
								+ checkIfBlank(data.getUeAccountNumber()
										.split(SiteoneintegrationConstants.SEPARATOR_UNDERSCORE)[0])
								+ FIELD_DELIMITER + checkIfBlank(data.getPreferredLanguage()));
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

	private void printHeader(PrintWriter printWriter) {
		for (String header : FILE_HEADER) {
			printWriter.println(header);
		}
	}
	private void printHeaderRealTime(PrintWriter printWriter) {
		for (String header : FILE_HEADER1) {
			printWriter.println(header);
		}
	}

	private String getFileName() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMATTER);
		String currentServerDate = dateFormat.format(cal.getTime());
		return (FILENAME + SiteoneintegrationConstants.SEPARATOR_UNDERSCORE + currentServerDate);
	}
	private String getFileNameRealTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMATTER);
		String currentServerDate = dateFormat.format(cal.getTime());
		return (FILENAME + SiteoneintegrationConstants.SEPARATOR_UNDERSCORE + currentServerDate);
	}

	private String checkIfBlank(String input) {
		return (!StringUtils.isBlank(input) ? input : "NA");
	}
	
	@Override
	public String convertToHex(String text)
	{
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			LOG.error("Exception occured while hashing" + e);
		}
		if(null != md)
		{
			md.update(text.getBytes(StandardCharsets.UTF_8));
			byte[] digest = md.digest();
			return String.format("%064x", new BigInteger(1, digest));
		}
		return "NA";
	}

	public AdobeAnalyticsCustomerExportCronJobDao getAdobeAnalyticsCustomerExportCronJobDao() {
		return adobeAnalyticsCustomerExportCronJobDao;
	}

	public void setAdobeAnalyticsCustomerExportCronJobDao(
			AdobeAnalyticsCustomerExportCronJobDao adobeAnalyticsCustomerExportCronJobDao) {
		this.adobeAnalyticsCustomerExportCronJobDao = adobeAnalyticsCustomerExportCronJobDao;
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
	
	
	
    @Override
	public void exportRealtimeCustomerMappingReport(AdobeAnalyticsRealtimeCustomerExportCronJobModel cronJobModel) throws IOException {

		List<SiteoneAnalyticsRealtimeCustomerMappingData> siteoneAnalyticsRealtimeCustomerMappingList = getAdobeAnalyticsCustomerExportCronJobDao()
				.getNewlyCreatedRealtimeCustomersList();
		if (!CollectionUtils.isEmpty(siteoneAnalyticsRealtimeCustomerMappingList)) {
			PrintWriter printWriter = null;
			File file=null;
			final String analyticsReportContainer= getConfigurationService().getConfiguration().getString(SiteoneintegrationConstants.ANALYTICS_ORDER_NUMBER_MAPPING_LOCATION);
			try {
					file=File.createTempFile(getFileNameRealTime(),TAB);
					printWriter = new PrintWriter(file);

					printHeaderRealTime(printWriter);
					for (SiteoneAnalyticsRealtimeCustomerMappingData data : siteoneAnalyticsRealtimeCustomerMappingList) {
						printWriter.println(checkIfBlank(data.getUuid()) + FIELD_DELIMITER + convertToHex(checkIfBlank(data.getEmail()))
								+ FIELD_DELIMITER + checkIfBlank(data.getRequestedTime()).split("\\.")[0] + FIELD_DELIMITER
								+ checkIfBlank(data.getCreationTime()).split("\\.")[0] + FIELD_DELIMITER
								+ checkIfBlank(data.getAccountType()) + FIELD_DELIMITER
								+ checkIfBlank(data.getPrimarybusiness()) + FIELD_DELIMITER
								+ checkIfBlank(data.getUeAccount()
										.split(SiteoneintegrationConstants.SEPARATOR_UNDERSCORE)[0])
								+ FIELD_DELIMITER + checkIfBlank(data.getPreferredLanguage())+FIELD_DELIMITER+checkIfBlank(data.getUuidType()));
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
    
}


