package com.siteone.integration.jobs.product.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import jakarta.annotation.Resource;
import org.apache.log4j.Logger;

import com.siteone.core.model.SalesFeedCronJobModel;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.jobs.product.service.SalesFeedCronJobService;

import de.hybris.platform.servicelayer.impex.ImpExResource;
import de.hybris.platform.servicelayer.impex.ImportConfig;
import de.hybris.platform.servicelayer.impex.ImportResult;
import de.hybris.platform.servicelayer.impex.ImportService;
import de.hybris.platform.servicelayer.impex.impl.StreamBasedImpExResource;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.util.mail.MailUtils;

import jakarta.validation.ValidationException;


import com.siteone.integration.services.dataimport.SiteOneBlobDataImportService;


public class DefaultSalesFeedCronJobService implements SalesFeedCronJobService {

	private static final Logger LOG = Logger.getLogger(DefaultSalesFeedCronJobService.class);
	
	private ModelService modelService;
		
	@Resource(name = "blobDataImportService")
	private SiteOneBlobDataImportService blobDataImportService;

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	/**
     * Getter method for blobDataImportService
     *
     * @return the blobDataImportService
     */
    public SiteOneBlobDataImportService getBlobDataImportService() {
        return blobDataImportService;
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
	 * @return the modelService
	 */
	public ModelService getModelService() {
		return modelService;
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
	 * Setter method for  configurationService
	 *
	 * @param configurationService
	 *            the configurationService to set
	 */
	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}
	/**
	 * @param modelService the modelService to set
	 */
	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}

	
	@Override
	public void generateSalesDataImport(SalesFeedCronJobModel salesFeedCronJobModel)  throws IOException, ValidationException {
		// TODO Auto-generated method stub
		//  Read the data from CVS file and and generate the impex 
		// Run the import impex service to load the data into DB. 
		LOG.info("DefaultSalesFeedCronJobService.generateSalesDataImport is called ");
		String containerName = getConfigurationService().getConfiguration().getString(SiteoneintegrationConstants.SALES_FEED_CONTAINER_NAMWE);
		String salesFeedDirectory = getConfigurationService().getConfiguration().getString(SiteoneintegrationConstants.SALES_FEED_TARGET_LOCATION);
		String archiveSalesFeedDirectory = getConfigurationService().getConfiguration().getString(SiteoneintegrationConstants.ARCHIVE_SALES_FEED_TARGET_LOCATION);
		List<File> csvFileList= getBlobDataImportService().readBlobsToFiles(containerName,salesFeedDirectory);
		LOG.info("DefaultSalesFeedCronJobService.generateSalesDataImport file list  " + csvFileList.toString());
		if (csvFileList == null || csvFileList.size() == 0) {
	        LOG.info("No CSV files to process.");
	    }
	    for (File csvFile : csvFileList) {
	        try {
	            String impex = convertCSVToImpex(csvFile);
	            LOG.info("impex file data" + impex);
				boolean jobStatus = importImpex(impex);
	            if (jobStatus) {
		            getBlobDataImportService().moveBlob(containerName, csvFile, salesFeedDirectory, archiveSalesFeedDirectory);
	            } else { 
	            	LOG.info("DefaultSalesFeedCronJobService.generateSalesDataImport job is Failed, not able to archive file " + csvFile.getName());
	            }
	        } catch (Exception e) {
	            LOG.info("Failed to process CSV file: " + csvFile.getName());
	        }
	    }
	    LOG.info("DefaultSalesFeedCronJobService.generateSalesDataImport is ended ");
	}

	private  String convertCSVToImpex(File csvFile) throws IOException {

	    LOG.info("DefaultSalesFeedCronJobService.convertCSVToImpex is started ");
	    // Use Apache POI to read Excel and generate Impex lines
	    StringBuilder impex = new StringBuilder();
	    impex.append("INSERT_UPDATE ProductSalesInfo;productCode[unique=true];region[unique=true];ytdSales;lastYtdSales");
		impex.append(System.lineSeparator());
	    String line;
	    String csvSplitBy = ","; // Delimiter
	    String region = "";
	    String productCode = "";
	    String lastYtdSales = "";
	    String ytdSales = "";
	    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	        // Read header line
	        String headerLine = br.readLine();
	        // Read data lines
	        while ((line = br.readLine()) != null) {
	            List<String> values = Arrays.asList(line.split(csvSplitBy));
	            region = "";
	            productCode = "";
	            lastYtdSales = "";
	            ytdSales = "";
	            region = values.get(0);
	            productCode = values.get(1);
	            if (values.size() > 5 ) {
	                lastYtdSales = values.get(values.size()-1).replaceAll("[()$]", "");
	                ytdSales = values.get(values.size()-2).replaceAll("[()$]", "");
	                try {
	                    double dytdSales = Double.parseDouble(ytdSales);
	                } catch (NumberFormatException nfe) {
	                    ytdSales = "";
	                }
	            } else {
	                lastYtdSales = "";
	                ytdSales = values.get(values.size()-1).replaceAll("[()$]", "");
	            }

	            impex.append(";" + productCode + ";" + region + ";" + ytdSales + ";" + lastYtdSales );
				impex.append(System.lineSeparator());
	        }
	    } catch (IOException e) {
	        //e.printStackTrace();
	    	LOG.error("DefaultSalesFeedCronJobService.convertCSVToImpex error while read the file. " + csvFile.getName());
	    }		
	    LOG.info("DefaultSalesFeedCronJobService.convertCSVToImpex is ended ");
	    return impex.toString();
	}

	
	private boolean importImpex(String impexContent) {
		LOG.info("DefaultSalesFeedCronJobService.importImpex is STARTED ");
		boolean jobStatus = false;
		ImportService importService = (ImportService) de.hybris.platform.core.Registry.getApplicationContext().getBean("importService"); 
		
		// Configure the import
		ImportConfig importConfig = new ImportConfig();
		importConfig.setScript(new StreamBasedImpExResource(new ByteArrayInputStream(impexContent.getBytes()), "UTF-8")); // Using String example
		importConfig.setLegacyMode(false);
		importConfig.setSynchronous(true);
		importConfig.setLocale(Locale.ENGLISH);
		// Perform the import
		ImportResult importResult = importService.importData(importConfig);

		// Handle the result
		if (importResult.isSuccessful()) {
			LOG.info("DefaultSalesFeedCronJobService.importImpex is Success ");
			jobStatus = true;
		} else {
			LOG.info("DefaultSalesFeedCronJobService.importImpex is Failed ");
		    // You can get details about the errors, e.g., unresolved lines
		    if (importResult.hasUnresolvedLines()) {
		    	LOG.info("DefaultSalesFeedCronJobService.importImpex is Failed due to Unresolved lines in ImpEx ");
		     }
		}
		LOG.info("DefaultSalesFeedCronJobService.importImpex is ENDED ");
		return jobStatus;
	}
	
	
}