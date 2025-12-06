package com.siteone.pcm.dataimport.batch.zip.task;

import com.siteone.integration.services.dataimport.SiteOneBlobDataImportService;
import de.hybris.platform.acceleratorservices.dataimport.batch.converter.ImpexConverter;
import de.hybris.platform.cloud.hotfolder.dataimport.batch.zip.ZipBatchHeader;
import de.hybris.platform.cloud.hotfolder.dataimport.batch.zip.task.ZipHeaderTransformerTask;
import de.hybris.platform.util.Config;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 * The type Site one zip header transformer task.
 *
 * @author i319924
 */
public class SiteOneZipHeaderTransformerTask extends ZipHeaderTransformerTask {

    private SiteOneBlobDataImportService blobDataImportService;

    protected void convertCsv(final ZipBatchHeader header, final File file) throws IOException {
        final List<ImpexConverter> converters = getConverters(file);
        int position = 1;
        for (final ImpexConverter converter : converters) {
            final File impexFile = getImpexFile(file, position++);
            if (convertFile(header, file, impexFile, converter)) {
                header.addTransformedFile(impexFile);
                header.addOriginalToTransformedEntry(file.getName(), impexFile.getName());
            } else {

                //Copy the error rows to outbound blob
                String path = new File(file.getParent()).getParent() + "/error";
                if (Files.exists(new File(path).toPath())) {
                    final File[] files = new File(path).listFiles();
                    for (File errorFile : files) {
                        final String errorPath = errorFile.toString()+"."+ Instant.now();
                        File newFile = new File(errorPath);
                        errorFile.renameTo(new File(errorPath));
                        blobDataImportService.writeBlob(newFile, Config.getParameter("media.physicalpath")+"error/",Config.getParameter("azure.hotfolder.storage.container.name"));
                        newFile.delete();
                    }
                }
                getCleanupHelper().cleanupFile(impexFile);
            }
        }
    }

    /**
     * Gets blob data import service.
     *
     * @return the blob data import service
     */
    public SiteOneBlobDataImportService getBlobDataImportService() {
        return blobDataImportService;
    }

    /**
     * Sets blob data import service.
     *
     * @param blobDataImportService the blob data import service
     */
    public void setBlobDataImportService(SiteOneBlobDataImportService blobDataImportService) {
        this.blobDataImportService = blobDataImportService;
    }
}
