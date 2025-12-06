/*
 * Copyright (c) 2020 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.siteone.integration.services.dataimport;

import java.io.File;
import java.util.List;

/**
 * Service to manage the Blobs
 *
 * @author madhura.shetty
 */
public interface SiteOneBlobDataImportService {

     /**
      * Downloads the contents of a blob to a file.
      * @param fileName                 File to be read
      * @param directory                Directory Structure
      * @param containerName            Cloud Blob Container
      * @return The blob read
      */
     File readBlobToFile(String fileName, final String directory,final String containerName);

     /**
      * Downloads the contents of multiple blobs to  files.
      * @param containerName         Cloud Blob Container
      * @param directory             Directory Structure
      * @return List of  blobs read
      */
     List<File> readBlobsToFiles(final String containerName,final String directory) ;

     /**
      * Write  Blob
      * @param fileToBeWritten          File to be written
      * @param directory             Directory Structure
      * @param containerName container name
      */
     public void writeBlob(final File fileToBeWritten,final String directory, final  String containerName);

     /**
      * Write  Blob
      * @param fileToBeWritten          File to be written
      * @param directory             Directory Structure
      */
     void writeBlob(final File fileToBeWritten,final String directory);

     /**
      * Filters and remove files older than the  cutoff time
      * @param containerName         Cloud Blob Container
      * @param directory             Directory Structure
      * @param keepArchiveDays       cut off days to retain the file
      * @return count of blobs deleted.
      */
     int deleteBlob(final String containerName,final String directory,final int keepArchiveDays);

     /**
      * Move one blob content to another
      * @param containerName         Cloud Blob Container
      * @param file                  File to be read
      * @param sourceFolder          Source Folder
      * @param destFolder            Destination Folder
      */
     void moveBlob(final String containerName,final File file,final String sourceFolder, final String destFolder);

     /**
      * Delete media blob
      * @param containerName         Cloud Blob Container
      * @param fileName              File to be deleted
      * @param directory             Directory Structure
      */
     void deleteMediaBlob(final String containerName, final String fileName, final String directory);

     /**
      * Get Media Container
      * @param mediaFolder           Media Folder Qualifier
      * @return containerName
      */
     String getMediaContainer(final String mediaFolder);

}
