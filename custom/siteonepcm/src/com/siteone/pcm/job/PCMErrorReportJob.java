package com.siteone.pcm.job;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.util.Config;
import de.hybris.platform.util.mail.MailUtils;
import com.siteone.integration.services.dataimport.SiteOneBlobDataImportService;
import com.siteone.pcm.constants.SiteonepcmConstants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail2.jakarta.EmailAttachment;
import org.apache.commons.mail2.jakarta.HtmlEmail;
import jakarta.validation.ValidationException;



public class PCMErrorReportJob extends AbstractJobPerformable<CronJobModel> {
	private static final Logger logger = Logger.getLogger(PCMErrorReportJob.class.getName());

	private static String exceptionErrorFolder = null;

	/* The Blob dataImport service*/
	private SiteOneBlobDataImportService blobDataImportService;

	/* The configuration service*/
	private ConfigurationService configurationService;

	private void readexceptionerrorFile(final String[] job) throws IOException, ValidationException {
		for (final String path : job) {
			List<File> files=getBlobDataImportService().readBlobsToFiles(getConfigurationService().getConfiguration().getString(SiteonepcmConstants.PCM_ERROR_REPORT_LOCATION),path);
			for (final File file : files) {
				String errorfileName = "";
				boolean isRead = false;
				BufferedReader br = null;

				errorfileName = file.getName();
				if (file.isFile() && errorfileName != null) {

					logger.info(" PcmErrorReportJob Starting to read file : " + errorfileName);
					try {
						String strLine = "";
						br = new BufferedReader(new FileReader(file));
						strLine = br.readLine();
						while (strLine != null) {
							isRead = true;
							// collection
							logger.info("File getting read");
							break;
						}
					} finally {
						br.close();
					}
					if (isRead) {
						sendReportMail(file, file.getName(),path);
						// moveFile(file.getName(), exceptionErrorFolder,
						// exceptionErrorFolder+"mailed_archive\\");
					}
				}

			}
		}

	}

	private void moveFile(final String sourceFolder, final String destFolder,File file) {
		logger.info("PcmErrorReportJob : Inside movefile function : FileName : " + file.getName() + " Source :" + sourceFolder
				+ " Destination : " + destFolder);
		//Migration |  Begins  operation to start copying one blob content to another
		blobDataImportService.moveBlob(getConfigurationService().getConfiguration().getString("azure.hotfolder.storage.container.name"),file,sourceFolder,destFolder);
	}

	private void sendReportMail(final File file, final String fileName, String path) {
		HtmlEmail htmlEmail = null;
		final String emailID = Config.getParameter("PcmErrorReportJob.ExceptionMailAddress");
		final String ccEmailID = Config.getParameter("PcmErrorReportJob.CCMailAddress");
		String mailMessage = null, subject = null;
		try {
			htmlEmail = (HtmlEmail) MailUtils.getPreConfiguredEmail();
			for (final String receipt : StringUtils.split(emailID, ", ")) {
				htmlEmail.addTo(receipt);
			}
			if (ccEmailID != null && !ccEmailID.isEmpty()) {
				for (final String cc : StringUtils.split(ccEmailID, ", ")) {
					htmlEmail.addCc(cc);
				}
			}
			// Mail Subject
			subject = Config.getParameter("PcmErrorReportJob.MailSubject");
			// Mail Body
			mailMessage = Config.getParameter("PcmErrorReportJob.MailMessage");
			htmlEmail.attach(file);
			logger.info(subject);
			htmlEmail.setSubject(subject);
			logger.info(mailMessage);
			htmlEmail.setHtmlMsg(mailMessage);
			htmlEmail.send();
			moveFile(path, path + "mailed-archive/",file);
		} catch (final Exception e) {
			logger.info(e.getMessage());
		}

	}

	@Override
	public PerformResult perform(final CronJobModel cronJob) {
		try {
			logger.info("<----------Exception Report Job Starts-------------->");
			final String[] exceptionErrorFolderPath = new String[2];
			exceptionErrorFolderPath[0] = Config.getParameter("PcmErrorReportJob.ExceptionErrorFolder");
			exceptionErrorFolderPath[1] = Config.getParameter("PcmErrorReportJob.prodFeatureErrorFolder");
			readexceptionerrorFile(exceptionErrorFolderPath);

		} catch (final Exception e) {
			logger.warning("Error message :" + e.getMessage());
		}
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
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