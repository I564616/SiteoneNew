package com.siteone.integration.jobs.datareport.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.siteone.core.model.CustomerDataReportCronJobModel;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.jobs.datareport.dao.SiteOneCustomerDataReportDao;
import com.siteone.integration.jobs.datareport.service.SiteOneCustomerDataReportService;
import com.siteone.integration.services.dataimport.SiteOneBlobDataImportService;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.util.Config;

public class DefaultSiteOneCustomerDataReportService implements SiteOneCustomerDataReportService {

	private SiteOneCustomerDataReportDao siteOneCustomerDataReportDao;
	
	private ModelService modelService;

	/* The Blob dataImport service*/
	private SiteOneBlobDataImportService blobDataImportService;

	/* The configuration service*/
	private ConfigurationService configurationService;

	private static final Logger LOG = Logger.getLogger(DefaultSiteOneCustomerDataReportService.class);

	private static final String[] FILE_HEADER = { "CustomerId", "EmailId", "Status", "LastLoginDate" };

	private static final String FIELD_DELIMITER = "|";

	@Override
	public void exportCustomerData(CustomerDataReportCronJobModel customerDataReportCronJobModel) {

		PrintWriter printWriter = null;
		File file=null;
		final String customerReportContainer= getConfigurationService().getConfiguration().getString(SiteoneintegrationConstants.CUSTOMER_DATA_REPORT_TARGET_LOCATION);

		try {
			file=File.createTempFile(getFileName(),".txt");
			printWriter = new PrintWriter(file);
			printHeader(printWriter);
			Map<String, String> customerDataRow = new HashMap<String, String>();
			List<B2BCustomerModel> b2BCustomerModel = getSiteOneCustomerDataReportDao().getAllB2BCustomers();
			if (null != b2BCustomerModel) {
				for (B2BCustomerModel b2BCustomer : b2BCustomerModel) {
					customerDataRow.put("CustomerId", b2BCustomer.getGuid());
					customerDataRow.put("EmailId", b2BCustomer.getEmail());					
					if (b2BCustomer.getActive()) {
						if (null != b2BCustomer.getLastLogin()) {
							customerDataRow.put("Status", "Active");
						} else {
							customerDataRow.put("Status", "Staged");
						}
					} else {
						customerDataRow.put("Status", "Suspended");
					}					
					customerDataRow.put("LastLoginDate",
							b2BCustomer.getLastLogin() != null ? b2BCustomer.getLastLogin().toString() : null);
					printRow(customerDataRow, printWriter);
					customerDataRow.clear();
				}
			}
		} catch (IOException ioException) {
			LOG.error("Exception occured in customer data report File " + (Config.getString(SiteoneintegrationConstants.CUSTOMER_DATA_REPORT_TARGET_LOCATION,
					StringUtils.EMPTY) + getFileName()), ioException);
			customerDataReportCronJobModel.setResult(CronJobResult.FAILURE);
			customerDataReportCronJobModel.setStatus(CronJobStatus.ABORTED);
		} finally {
			Date date = new Date();
			customerDataReportCronJobModel.setLastExecutionTime(date);
			getModelService().save(customerDataReportCronJobModel);
			if (null != printWriter) {
				printWriter.close();
			}
		}
		// Migration | Write Blob
		getBlobDataImportService().writeBlob(file,customerReportContainer);
	}

	private String getFileName() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy_hhmmss");
		String currentServerDate = dateFormat.format(cal.getTime());
		String fileName = Config.getString("customer.data.report.fileName", "customerdatareport")
				+ SiteoneintegrationConstants.SEPARATOR_UNDERSCORE + currentServerDate;
		return fileName;
	}

	private void printHeader(PrintWriter printWriter) {
		StringBuffer fileHeader = new StringBuffer();
		for (String header : FILE_HEADER) {
			if (fileHeader.length() != 0) {
				fileHeader.append(FIELD_DELIMITER);
			}
			fileHeader.append(header);
		}
		printWriter.println(fileHeader);
	}

	private void printRow(Map<String, String> customerDataRow, PrintWriter printWriter) {
		StringBuffer feedRow = new StringBuffer();
		for (String header : FILE_HEADER) {
			
			if (feedRow.length() != 0) {
				feedRow.append(FIELD_DELIMITER);
			}
			feedRow.append((customerDataRow.get(header) != null) ? customerDataRow.get(header) : " ");
		}
		printWriter.println(feedRow);
		
	}

	public ModelService getModelService() {
		return modelService;
	}

	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}

	public SiteOneCustomerDataReportDao getSiteOneCustomerDataReportDao() {
		return siteOneCustomerDataReportDao;
	}

	public void setSiteOneCustomerDataReportDao(SiteOneCustomerDataReportDao siteOneCustomerDataReportDao) {
		this.siteOneCustomerDataReportDao = siteOneCustomerDataReportDao;
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