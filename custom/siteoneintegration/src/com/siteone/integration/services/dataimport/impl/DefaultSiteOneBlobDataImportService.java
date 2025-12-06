/*
 * Copyright (c) 2020 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.siteone.integration.services.dataimport.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.*;

import com.siteone.integration.services.dataimport.SiteOneBlobDataImportService;
import de.hybris.platform.servicelayer.config.ConfigurationService;

import java.io.IOException;
import java.net.URISyntaxException;

import java.security.InvalidKeyException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Default implementation of {@link SiteOneBlobDataImportService}
 *
 * @author madhura.shetty
 */
public class DefaultSiteOneBlobDataImportService implements SiteOneBlobDataImportService {

    /* The Constant LOG.*/
    private static final Logger LOG = LoggerFactory.getLogger(DefaultSiteOneBlobDataImportService.class);

    private static final String MEDIA_CONTAINER =  "sys-master-";

    /* The configuration service.*/
    private ConfigurationService configurationService;

    @Override
    public File readBlobToFile(String fileName,final String directory, final String containerName) {

        validateParameterNotNull(fileName, "Parameter fileName must not be null for read blob");
        validateParameterNotNull(directory, "Parameter directory must not be null for read blob");
        validateParameterNotNull(containerName, "Parameter containerName must not be null for read blob");

        File sourceFile =null;

        try {
            CloudStorageAccount storageAccount = settingBlobConnection();
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
            //Getting a blob reference
            CloudBlobContainer container = blobClient.getContainerReference(containerName);
            CloudBlobDirectory cloudBlobDirectory=container.getDirectoryReference(directory);

            for(ListBlobItem blobItem : cloudBlobDirectory.listBlobs()) {
                if(blobItem instanceof CloudBlockBlob) {
                    CloudBlockBlob blockBlob=(CloudBlockBlob)blobItem;
                    String uriString = blockBlob.getUri().toString();

                    String fileNameInBlob = uriString.substring(uriString.lastIndexOf('/')+1);
                    //Validating the filename before reading the file
                    if(fileNameInBlob.startsWith(FilenameUtils.getBaseName(fileName))&& fileNameInBlob.endsWith(FilenameUtils.getExtension(fileName))){
                        sourceFile = File.createTempFile(FilenameUtils.getBaseName(fileNameInBlob),FilenameUtils.getExtension(fileNameInBlob));
                        LOG.info("Download a blob storing the contents in a file [{}] ",sourceFile);
                        blockBlob.downloadToFile(sourceFile.toString());
                        break;
                    }
                }
            }

        } catch (URISyntaxException | StorageException | IOException |  InvalidKeyException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
        return sourceFile;
    }

    @Override
    public List<File> readBlobsToFiles(final String containerName,final String directory) {

        validateParameterNotNull(directory, "Parameter directory must not be null for read blobs");
        validateParameterNotNull(containerName, "Parameter containerName must not be null for read blobs");


        List<File> files=new ArrayList<>();
        try {
            CloudStorageAccount storageAccount = settingBlobConnection();
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
            CloudBlobContainer container = blobClient.getContainerReference(containerName);

            LOG.info("Reading files from Container [{}] Directory [{}]",container.getName(),directory);
            CloudBlobDirectory cloudBlobDirectory= container.getDirectoryReference(directory);
            for(ListBlobItem blobItem : cloudBlobDirectory.listBlobs())
            {
                if(blobItem instanceof CloudBlockBlob)
                {
                    CloudBlockBlob blockBlob=(CloudBlockBlob)blobItem;
                    String uriString = blockBlob.getUri().toString();
                    String fileNameInBlob = uriString.substring(uriString.lastIndexOf('/')+1);
                    if(StringUtils.isNotEmpty(fileNameInBlob)) {
                        File sourceFile = new File(fileNameInBlob);
                        LOG.info("Download a blob storing the contents in a file [{}] ",sourceFile);
                        blockBlob.downloadToFile(sourceFile.toString());
                        files.add(sourceFile);
                    }
                }
            }
        }
        catch (URISyntaxException  | StorageException | IOException | InvalidKeyException  e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
        return files;
    }

    @Override
    public int deleteBlob(final String containerName,final String directory,final int keepArchiveDays)
    {
        validateParameterNotNull(containerName, "Parameter containerName must not be null for delete blob");
        validateParameterNotNull(directory, "Parameter directory must not be null for delete blob");

        int deleteCount = 0;

        try {
            CloudStorageAccount storageAccount = settingBlobConnection();
            CloudBlobClient   blobClient = storageAccount.createCloudBlobClient();
            //Getting a blob reference
            CloudBlobContainer  container = blobClient.getContainerReference(containerName);
            LOG.info("Container name: [{}] ",container.getName());
            CloudBlobDirectory cloudBlobDirectory=container.getDirectoryReference(directory);

            for (ListBlobItem blobItem : cloudBlobDirectory.listBlobs()) {
                if (blobItem instanceof CloudBlob) {
                    CloudBlob blob = (CloudBlob) blobItem;
                    //check for time interval when the file was last modified
                    if (DateUtils.addDays(new Date(), keepArchiveDays * -1).after(blob.getProperties().getLastModified())) {
                        LOG.info("Blob to be deleted [{}] ",blob.getName());
                        blob.delete();
                        deleteCount++;
                    }
                }
            }
        } catch (URISyntaxException | StorageException  | InvalidKeyException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
        return deleteCount;
    }


    @Override
    public void moveBlob(final String containerName,final File file,final String sourceFolder, final String destFolder)
    {
        validateParameterNotNull(containerName, "Parameter containerName must not be null");
        validateParameterNotNull(file, "Parameter file must not be null");
        validateParameterNotNull(sourceFolder, "Parameter sourceFolder must not be null");
        validateParameterNotNull(destFolder, "Parameter destFolder must not be null");


        try {
            CloudStorageAccount storageAccount = settingBlobConnection();
            CloudBlobClient   blobClient = storageAccount.createCloudBlobClient();
            //Getting a blob reference
            CloudBlobContainer  container = blobClient.getContainerReference(containerName);
            LOG.info("Container :[{}] " ,container.getName());
            LOG.info("File [{}] to be moved from source [{}] to destination [{}] " , file.getName(),sourceFolder,destFolder);

            CloudBlockBlob blob = container.getBlockBlobReference(destFolder+file.getName());
            LOG.info("Uploading  file [{}] to the Blob service  ",file.getName());
            blob.uploadFromFile(file.getAbsolutePath());
            CloudBlockBlob fileTobeDeleted = container.getBlockBlobReference(sourceFolder+file.getName());
            LOG.info("Deleting blob [{}] from the source [{}]",file.getName(),sourceFolder);
            fileTobeDeleted.delete();

        } catch (URISyntaxException | StorageException |IOException | InvalidKeyException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void writeBlob(final File fileToBeWritten,final String directory) {


        final String containerName=configurationService.getConfiguration().getString("azure.outbound.storage.container.name");
        try {
            CloudStorageAccount storageAccount = settingBlobConnection();
            CloudBlobClient   blobClient = storageAccount.createCloudBlobClient();
            CloudBlobContainer  container = blobClient.getContainerReference(containerName);

            LOG.info("Container name:[{}] ", container.getName());
            // Create the container if it does not exist with public access.
            container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new OperationContext());
            LOG.info("File to be written [{}]" , fileToBeWritten);
            //Getting a blob reference
            CloudBlockBlob blob = container.getBlockBlobReference(StringUtils.defaultString(directory)+fileToBeWritten.getName());
            LOG.info("Uploading the file [{}] to the Blob service  ",fileToBeWritten.getName());
            blob.uploadFromFile(fileToBeWritten.getAbsolutePath());
        }
        catch (StorageException | URISyntaxException | IOException | InvalidKeyException ex) {
            LOG.error(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void writeBlob(final File fileToBeWritten,final String directory, final  String containerName) {


        try {
            CloudStorageAccount storageAccount = settingBlobConnection();
            CloudBlobClient   blobClient = storageAccount.createCloudBlobClient();
            CloudBlobContainer  container = blobClient.getContainerReference(containerName);

            LOG.info("Container name:[{}] ", container.getName());
            // Create the container if it does not exist with public access.
            container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new OperationContext());
            LOG.info("File to be written [{}]" , fileToBeWritten);
            //Getting a blob reference
            CloudBlockBlob blob = container.getBlockBlobReference(StringUtils.defaultString(directory)+fileToBeWritten.getName());
            LOG.info("Uploading the file [{}] to the Blob service  ",fileToBeWritten.getName());
            blob.uploadFromFile(fileToBeWritten.getAbsolutePath());
        }
        catch (StorageException | URISyntaxException | IOException | InvalidKeyException ex) {
            LOG.error(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public String getMediaContainer(final String mediaFolder)
    {
        validateParameterNotNull(mediaFolder, "Parameter mediaFolder must not be null");
        String mediaSourceFolder = mediaFolder.replace("_","-");

        return MEDIA_CONTAINER.concat(mediaSourceFolder);
    }

    @Override
    public void deleteMediaBlob(final String containerName, final String fileName, final String directory)
    {
        validateParameterNotNull(containerName, "Parameter containerName must not be null");
        validateParameterNotNull(fileName, "Parameter fileName must not be null");
        validateParameterNotNull(directory, "Parameter directory must not be null");


        try {
            CloudStorageAccount storageAccount = settingMediaBlobConnection();
            CloudBlobClient   blobClient = storageAccount.createCloudBlobClient();
            //Getting a blob reference
            CloudBlobContainer  container = blobClient.getContainerReference(containerName);
            LOG.info("Container name: [{}]" , container.getName());
            LOG.info("File [{}] to be removed from  [{}] " ,fileName,directory);
            CloudBlobDirectory cloudBlobDirectory=container.getDirectoryReference(directory);

            for (ListBlobItem blobItem : cloudBlobDirectory.listBlobs()) {
                if (blobItem instanceof CloudBlob) {
                    CloudBlob blockBlob = (CloudBlob) blobItem;
                    String uriString = blockBlob.getUri().toString();

                    String fileNameInBlob = uriString.substring(uriString.lastIndexOf('/')+1);
                    if(fileName.equals(fileNameInBlob)) {
                        blockBlob.delete();
                    }
                }
            }

        } catch (URISyntaxException | StorageException | InvalidKeyException  e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Setting blob connection providing authorization information required to access media in an Azure Storage account
     *
     * @return the ConfigurationService
     */
    private CloudStorageAccount settingMediaBlobConnection() throws InvalidKeyException, URISyntaxException{

        String storageConnectionString = configurationService.getConfiguration().getString("media.globalSettings.cloudAzureBlobStorageStrategy.connection");
        LOG.info("Authorization information required to access Media blob storage - Account  [{}] ",storageConnectionString);
        return  CloudStorageAccount.parse(storageConnectionString);
    }


    /**
     * Setting blob connection providing authorization information required to access data in an Azure Storage account
     *
     * @return the ConfigurationService
     */
    private CloudStorageAccount settingBlobConnection()  throws InvalidKeyException,URISyntaxException{

        String storageConnectionString = configurationService.getConfiguration().getString("azure.hotfolder.storage.account.connection-string");
        LOG.info("Authorization information required to access data in an Azure Storage account - Storage Connection [{}] ",storageConnectionString);

        return CloudStorageAccount.parse(storageConnectionString);
    }


    /**
     * Getter method for configurationService
     *
     * @return the ConfigurationService
     */
    public ConfigurationService getConfigurationService() {
        return configurationService;
    }

    /**
     * Setter method for  configurationService
     *
     * @param configurationService
     *            the ConfigurationService to set
     */
    public void setConfigurationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }


}
