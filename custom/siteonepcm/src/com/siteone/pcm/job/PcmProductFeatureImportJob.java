package com.siteone.pcm.job;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.impex.jalo.ImpExException;
import de.hybris.platform.impex.jalo.MultiThreadedImporter;
import de.hybris.platform.impex.jalo.imp.MultiThreadedImpExImportReader;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.util.CSVReader;
import de.hybris.platform.util.CSVWriter;
import de.hybris.platform.util.Config;
import de.hybris.platform.impex.jalo.imp.DumpHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import com.siteone.pcm.constants.SiteonepcmConstants;
import de.hybris.platform.util.ThreadUtilities;
import jakarta.validation.ValidationException;
import com.siteone.core.util.SiteoneSecurityUtils;

public class PcmProductFeatureImportJob extends AbstractJobPerformable<CronJobModel> {

	public static final Logger logger = Logger.getLogger(PcmProductFeatureImportJob.class.getName());

	/**
	 *
	 * Loading the configurations
	 *
	 **/
	public static final String inputFolder = Config.getParameter("PcmImportJob.InputFolder");
	public static final String impexFolder = Config.getParameter("PcmImportJob.impexfolder");
	public static final String impexArchiveFolder = Config.getParameter("PcmImportJob.impexArchiveFolder");
	public static final String impexErrorFolder = Config.getParameter("PcmImportJob.impexErrorFolder");
	public static final String inputArchiveFolder = Config.getParameter("PcmImportJob.InputArchiveFolder");
	public static final String executedFolder = Config.getParameter("PcmImportJob.ExecutedFolder");
	public static final String dumpFolder=Config.getParameter("PcmImportJob.dumpFolder");
	

	
	/**
	 *
	 * Validating the configurations
	 *
	 **/
	private File validateFolders(final String folderPath, final boolean isExistingFolder)  {
		final File inputFolder = new File(SiteoneSecurityUtils.buildValidAvatarPath(folderPath));
		logger.info(" inputFolder - validateFolders :: " +inputFolder.toString());
		if (!inputFolder.isDirectory()) {
			inputFolder.mkdir();
		}
		if (isExistingFolder && inputFolder.listFiles()!=null) {
			logger.info(" isExistingFolder :: " +inputFolder.toString());
			logger.info("Input file dir -  - " + inputFolder.toString());
			logger.info("inputFolder -  - " + inputFolder.toString());
			if (inputFolder.listFiles().length == 0) {
				logger.info("No files present in the directory : " + inputFolder);
				// throw new Exception("No files present in the folder");
			}

		}
		return inputFolder;

	}

	private Set<Integer> mandatoryColumns(final String strMandatoryColumnHeader) {
		Set<Integer> mandatoryColumns;
		mandatoryColumns = new HashSet<Integer>();
		int iColumn = 0;
		for (final String columnType : strMandatoryColumnHeader.split(SiteonepcmConstants.PFSINGLEPIPE, -1)) {
			if (!(columnType.equals("Optional") || columnType.equals("0"))) {
				mandatoryColumns.add(iColumn);
			}
			iColumn++;
		}
		return mandatoryColumns;
	}

	private List<String> getHeaderList(final String headerString) {
		List<String> headerList;
		headerList = new ArrayList<>();
		for (final String columnName : headerString.split(SiteonepcmConstants.PFSINGLEPIPE, -1)) {
			headerList.add(columnName);
		}
		return headerList;
	}

	private boolean validateProductFeature(final Set<Integer> mandatoryColumn, final String rowData) {
		int iColumnNumber = 0;
		for (final String columnData : rowData.split(SiteonepcmConstants.PFSINGLEPIPE, -1)) {
			if (mandatoryColumn.contains(iColumnNumber) && (columnData == null || columnData.trim().equals(""))) {
				return false;
			}
			iColumnNumber++;
		}
		return true;

	}

	@SuppressWarnings({ "resource" })
	private Map<String, List<String>> readProductFeatureFile(final String fileAddress) throws IOException {
		BufferedReader br = null;
		String strLine = "";
		int lineNumber = 1;
		Set<Integer> mandatoryColumn = new HashSet<Integer>();
		Map<String, List<String>> dataMap = new HashMap<String, List<String>>();

		br = new BufferedReader(new FileReader(SiteoneSecurityUtils.buildValidAvatarPath(fileAddress)));;
		try
		{
		while ((strLine = br.readLine()) != null) {
			if (lineNumber == 3) {
				mandatoryColumn = mandatoryColumns(strLine);
			} else if (lineNumber == 4) {
				dataMap.put("HeaderList", getHeaderList(strLine));
			} else if (lineNumber >= 6) {
				if (dataMap == null) {
					dataMap = new HashMap<>();
				}
				if (validateProductFeature(mandatoryColumn, strLine)) {
					if (dataMap.get("ValidRecords") == null) {
						dataMap.put("ValidRecords", new ArrayList<String>());
					}
					if (!(strLine.startsWith(SiteonepcmConstants.SEMICOLON))
							&& strLine.contains(SiteonepcmConstants.SINGLEPIPE)) {
						String replacedstrLine = strLine.replace(SiteonepcmConstants.SINGLEPIPE,SiteonepcmConstants.SEMICOLON);
						dataMap.get("ValidRecords")
								.add(SiteonepcmConstants.SEMICOLON + replacedstrLine + SiteonepcmConstants.SEMICOLON);
					}
				} else {
					if (dataMap.get("ErrorRecords") == null) {
						dataMap.put("ErrorRecords", new ArrayList<String>());
					}
					if (!(strLine.startsWith(SiteonepcmConstants.SEMICOLON))
							&& strLine.contains(SiteonepcmConstants.SINGLEPIPE)) {
						String replacedstrLine = strLine.replace("|", ";");
						dataMap.get("ErrorRecords")
								.add(SiteonepcmConstants.SEMICOLON + replacedstrLine + SiteonepcmConstants.SEMICOLON);
					}
				}
			}
			lineNumber++;
		}
	}
	finally
				{
		br.close();
	}
		return dataMap;
	}

	private String createImpexHeader(final List<String> headerList) {
		final StringBuffer headerName = new StringBuffer();
		final String headerline1 = Config.getParameter("PcmProductFeatureImportJob.headerline1");
		final String headerline2 = Config.getParameter("PcmProductFeatureImportJob.headerline2");
		final String headerline3 = Config.getParameter("PcmProductFeatureImportJob.headerline3");
		headerName.append(headerline1) ;
		headerName.append("\n");
		headerName.append(headerline2 );
		headerName.append("\n");
		headerName.append(headerline3);
		headerName.append("\n");
		StringBuffer header = new StringBuffer("UPDATE Product;code[unique=true];");
		for (final String headerData : headerList) {
			if (!headerData.equals("#skuId")) {
				headerName.append("$" + headerData.trim() + "=@" + headerData.trim() + "[$clAttrModifiers];\n");
				header = header.append("$" + headerData.trim() + SiteonepcmConstants.SEMICOLON);
			}
		}
		header = header.append("$catalogVersion");
		headerName.append(header);

		return headerName.toString();

	}

	private  List <String> createImpexFile(final String impexFileName, final String impexHeader, final List<String> impexRowList,
			final boolean appendHeader) throws IOException {
		if (impexRowList != null && impexRowList.size() > 0) {
			
			
			return createSplittedImpexFiles(impexFolder, impexFileName, impexHeader, impexRowList,
					appendHeader);
			/*
			final File impexFile = new File(impexFileName);
			final CSVWriter impexFileWriter = new CSVWriter(impexFile, "UTF-8", true);
			if (appendHeader) {
				impexFileWriter.writeSrcLine(impexHeader);
			}
			for (final String impexRow : impexRowList) {
				impexFileWriter.writeSrcLine(impexRow);
			}
			impexFileWriter.close();*/
		}
		
		return Collections.emptyList();

	}

	/**
	 * Function to execute impex from file. Input File name Output Execution
	 * status;
	 *
	 * @throws IOException
	 */
	private  Boolean executeImpexFromFile(final String fileName, final List<String> errorList) throws IOException {
		logger.severe("PcmProductFeatureImportJob: Entering in to  execute impex method Method");
		final String path = impexFolder + fileName;
		final File file = new File(SiteoneSecurityUtils.buildValidAvatarPath(path));
		
		logger.info("splitted file name before execution"+impexFolder + fileName);
		
		int maxthreads = Config.getInt("pcm.product.feature.job.threads", 16);
				
		final CSVReader importScriptReader = new CSVReader(file, "UTF-8");
		final MultiThreadedImpExImportReader multiThreadedImpExImportReader = new MultiThreadedImpExImportReader(importScriptReader);
		final MultiThreadedImporter importer = new MultiThreadedImporter(multiThreadedImpExImportReader, maxthreads);
	
			if (file.exists()) {
			try {					
				importer.importAll();
				logger.info("Moving impex file to impex archive");
				moveFile(fileName, impexFolder, impexArchiveFolder);
				logger.info("PcmProductFeatureImportJob: End of executeImpexFromFile Method");
				if (errorList != null)
				{
					createImpexFile(SiteoneSecurityUtils.buildValidAvatarPath(path), null, errorList, false);
					moveFile(fileName, impexFolder, impexErrorFolder);
					return false;
				}
				return true;
			} catch (final ImpExException e) 
			{				
				DumpHandler dh=importer.getDumpHandler();
				File fileddh=dh.getDumpAsFile();
				
				createImpexFile(path, null, errorList, false);
				
				File dumpfile= new File(SiteoneSecurityUtils.buildValidAvatarPath(dumpFolder + fileddh.getName().toString()));
				logger.severe("PcmProductFeatureImportJob :Error File Name:"+dumpfile.toString());
				if(dumpfile.exists())
				{
					BufferedReader br =  new BufferedReader(new FileReader(dumpfile));
					FileWriter fw= new FileWriter(SiteoneSecurityUtils.buildValidAvatarPath(impexErrorFolder+fileName));
					BufferedWriter bw = new BufferedWriter(fw);
					String strLine = "";
					while ((strLine = br.readLine()) != null)
					{
						bw.write(strLine);
						bw.write("\n");
					}
					bw.close();
					fw.close();
					br.close();					
					return true;
				}
				
				return false;
			} catch (final FileNotFoundException e) {
				return false;
			}
		} else {
			logger.info("splitted File does not exist" + file.getAbsolutePath());
			createImpexFile(path, null, errorList, false);
			moveFile(fileName, impexFolder, impexErrorFolder);
		}
		return false;

	}

	/*
	 * Function to move file from provided source to destination
	 */
	private  Boolean moveFile(final String fileName, final String sourceFolder, final String destFolder) {
		logger.info("PcmProductFeatureImportJob : Inside movefile function : FileName : " + fileName
				+ " Source :" + sourceFolder + " Destination : " + destFolder);
		try {
			final File afile = new File(SiteoneSecurityUtils.buildValidAvatarPath(sourceFolder + fileName));
			if (afile.exists() && afile.renameTo(new File(SiteoneSecurityUtils.buildValidAvatarPath(destFolder + fileName)))) {
					logger.info("PcmProductFeatureImportJob Inside movefile function : FileName : " + fileName
							+ " moved to " + destFolder);
					return true;
				
			}
			return false;
		} catch (final Exception e) {
			// e.printStackTrace();
			logger.severe("Failed to move file Error : " + e.getMessage());
			logger.throwing(e.getMessage(), "", e.getCause());
			logger.info("Exception" + e);
			return false;
		}
	}

	@Override
	public PerformResult perform(final CronJobModel cronJob) {

		File inputFileDir;
		String impexHeader;
		List<String> impexRowData;
		boolean isErrorFound = false;

		try {
			// Validating & Creating Folders
			inputFileDir = validateFolders(inputFolder, true);
			validateFolders(impexFolder, false);
			validateFolders(impexArchiveFolder, false);
			validateFolders(impexErrorFolder, false);
			validateFolders(inputArchiveFolder, false);
			validateFolders(executedFolder, false);
			for (final File file : inputFileDir.listFiles()) {
				if (file.isFile() && file.getName() != null
						&& file.getName().contains(Config.getParameter("file.categoryPrefix"))
						&& file.getName().startsWith("PH")) {
					logger.info(" PcmProductFeatureImportJob Starting to read file : " + file.getName());
					final Map<String, List<String>> dataMap = readProductFeatureFile(
							SiteoneSecurityUtils.buildValidAvatarPath(Path.of(inputFolder + file.getName()).toString()));
					logger.info(" PcmProductFeatureImportJob completed reading file : " + file.getName());

					logger.info(
							" PcmProductFeatureImportJob - Creating Impex Header for file : " + file.getName());
					impexRowData = dataMap.get("ValidRecords");
					impexHeader = createImpexHeader(dataMap.get("HeaderList"));

					logger.info("PcmProductFeatureImportJob - Creating Impex File for file : " + file.getName());
					final Long timeinMillisecs = (new Timestamp(System.currentTimeMillis())).getTime();
					final String impexFileName = file.getName().substring(0,
							file.getName().indexOf(SiteonepcmConstants.DOT)) + SiteonepcmConstants.HYPEN
							+ timeinMillisecs + file.getName().substring(
									file.getName().indexOf(SiteonepcmConstants.DOT), file.getName().length());
					logger.info("PcmProductFeatureImportJob - Creating Impex File : " + impexFileName);
					List<String> fileNameList = createSplittedImpexFiles(impexFolder,impexFileName, impexHeader, impexRowData, true);
					if (dataMap.get("ErrorRecords") != null && dataMap.get("ErrorRecords").size() > 0) {
						isErrorFound = true;
					}
					logger.info("Impex Execution starts " + impexFileName);
					
					for(String splittedImpexFileName:fileNameList){
					if (executeImpexFromFile(splittedImpexFileName, dataMap.get("ErrorRecords"))) {
						logger.info("Impex Execution successfull moving " + splittedImpexFileName + " file to Archive folder ("
								+ impexArchiveFolder + ")");
						moveFile(splittedImpexFileName, impexFolder, impexArchiveFolder);
						moveFile(file.getName(), inputFolder, executedFolder);

					} else {

						logger.warning("Some error occured during Impex Execution : moving " + impexFileName
								+ " file to Error folder (" + impexErrorFolder + ")");
						moveFile(impexFileName, impexFolder, impexErrorFolder);
						moveFile(file.getName(), inputFolder, executedFolder);
					}
					}

				}
			}
			if (isErrorFound) {
				logger.severe("Mandatory Values not present error. Please see the error Folder for details");
			}

		} catch (final Exception e) {
			logger.warning("Error message :" + e.getMessage());
			// e.printStackTrace();
			cronJob.setStatus(CronJobStatus.ABORTED);
			cronJob.setResult(CronJobResult.FAILURE);
						
			logger.info(" PcmProductFeatureImportJob Completed With errors" + e);
			return new PerformResult(CronJobResult.FAILURE, CronJobStatus.ABORTED);
		}

		cronJob.setStatus(CronJobStatus.FINISHED);
		cronJob.setResult(CronJobResult.SUCCESS);
		logger.info(" PcmProductFeatureImportJob Completed successfully");
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}
	
	private  List<String> createSplittedImpexFiles(final String impexFileFolder, final String impexFileName, final String impexHeader, final List<String> impexRowList,
			final boolean appendHeader) throws IOException {
		
		ArrayList<String> filenames = new ArrayList<>();
		int maxthreads = Config.getInt("pcm.product.feature.job.threads", 16);
		if (impexRowList != null && impexRowList.size() > 0) {
				double n = Math.ceil(impexRowList.size()/maxthreads);
				for(int i =0;i<=n;i++){
					CSVWriter impexFileWriter = null;
					try{
					logger.info("value of i-"+i);
					final File impexFile = new File(SiteoneSecurityUtils.buildValidAvatarPath(impexFileFolder+i+"_"+impexFileName));

					impexFileWriter = new CSVWriter(impexFile, "UTF-8", true);
					if (appendHeader) {
						impexFileWriter.writeSrcLine(impexHeader);
					}
					int toIndex = (i+1)*maxthreads;
					logger.info("value of toIndex-"+toIndex);
					logger.info("impexRowList.size(-"+impexRowList.size());
					if(toIndex>impexRowList.size()){
					toIndex=impexRowList.size();
					}
					for (final String impexRow : impexRowList.subList(i*maxthreads, toIndex)) {
						impexFileWriter.writeSrcLine(impexRow);
					}
					filenames.add(i+"_"+impexFileName);
					logger.info(" splitted file name-"+i+"_"+impexFileName);
					
					}catch(Exception exc){
						logger.severe("An Error occured during pcm feature import load"+exc.getMessage());
						if(exc instanceof IOException){
							throw new IOException(exc);
						}
						}finally{
						impexFileWriter.close();
					}
					
				}
				
			}

		for(String names:filenames){
			logger.info("#final splitted file names-"+names);
		}
		return filenames;
	}
	
}
