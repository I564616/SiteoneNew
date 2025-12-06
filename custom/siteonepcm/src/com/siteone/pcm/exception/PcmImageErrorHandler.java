
/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2017 SAP SE
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * Hybris ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with SAP Hybris.
 */
package com.siteone.pcm.exception;

import com.siteone.integration.services.dataimport.SiteOneBlobDataImportService;
import com.siteone.pcm.constants.SiteonepcmConstants;
import de.hybris.platform.acceleratorservices.dataimport.batch.converter.ImpexRowFilter;
import de.hybris.platform.acceleratorservices.dataimport.batch.converter.impl.DefaultImpexConverter;
import de.hybris.platform.acceleratorservices.dataimport.batch.converter.impl.NullImpexRowFilter;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.exceptions.SystemException;
import de.hybris.platform.util.Config;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.util.Assert;

/**
 *
 */
public class PcmImageErrorHandler extends DefaultImpexConverter {

    private static final char SEMICOLON_CHAR = ';';
    private static final char PLUS_CHAR = '+';
    private static final char SEQUENCE_CHAR = 'S';
    private static final String EMPTY_STRING = "";
    private static final char BRACKET_END = '}';
    private static final char BRACKET_START = '{';
    private static final char AMPERSAND = '&';

    private String header;
    private String impexRow;
    private String type;
    private ImpexRowFilter rowFilter = new NullImpexRowFilter();

    private StringBuilder errorbuilder;

    /* The Blob dataImport service*/
    private SiteOneBlobDataImportService blobDataImportService;

    /* The configuration service*/
    private ConfigurationService configurationService;

    @Override public String convert(final Map<Integer, String> row, final Long sequenceId) {
        String result = EMPTY_STRING;
        errorbuilder = new StringBuilder();
        if (!MapUtils.isEmpty(row)) {
            final StringBuilder builder = new StringBuilder();

            int copyIdx = 0;
            int idx = impexRow.indexOf(BRACKET_START);
            while (idx > -1) {
                final int endIdx = impexRow.indexOf(BRACKET_END, idx);
                processRow(row, sequenceId, builder, copyIdx, idx, endIdx);
                copyIdx = endIdx + 1;
                idx = impexRow.indexOf(BRACKET_START, endIdx);
            }

            if (copyIdx < impexRow.length()) {
                builder.append(impexRow.substring(copyIdx));
            }
            result = builder.toString();
        }
        return escapeQuotes(result);

    }

    @Override protected void processRow(final Map<Integer, String> row, final Long sequenceId, final StringBuilder builder,
            final int copyIdx, final int idx, final int endIdx) {
        if (endIdx < 0) {
            throw new SystemException("Invalid row syntax [brackets not closed]: " + impexRow);
        }
        builder.append(impexRow.substring(copyIdx, idx));
        if (impexRow.charAt(idx + 1) == SEQUENCE_CHAR) {
            builder.append(sequenceId);
        } else {
            processValues(row, builder, idx, endIdx);
        }
    }

    @Override protected void processValues(final Map<Integer, String> row, final StringBuilder builder, final int idx, final int endIdx) {

        final boolean mandatory = impexRow.charAt(idx + 1) == PLUS_CHAR;
        Integer mapIdx = null;
        try {
            mapIdx = Integer.valueOf(impexRow.substring(mandatory ? idx + 2 : idx + 1, endIdx));
        } catch (final NumberFormatException e) {
            throw new SystemException("Invalid row syntax [invalid column number]: " + impexRow, e);
        }
        if (mapIdx.intValue() == 0) {
            String Skuid = row.get(0);
            String imageName = row.get(1);
            String urlLinks = row.get(2);
            String priority = row.get(3);
            String mimeType = row.get(4);
            String sds = row.get(5);
            String label = row.get(6);

            if (!(StringUtils.isBlank(sds) && StringUtils.isBlank(label)) && (mimeType.equals("image/jpeg") || mimeType.equals("image/jpg")
                    || mimeType.equals("video/mp4"))) {
                errorbuilder.append("Image & video/mp4 cannot have sds/label::");
                errorbuilder.append(" ");
                errorbuilder.append(AMPERSAND);

            }
            if (mimeType.equals("video/mp4") && StringUtils.isBlank(urlLinks)) {
                errorbuilder.append("Missing Values URL Links :: ");
                errorbuilder.append(" ");
                errorbuilder.append(AMPERSAND);

            }
            if (StringUtils.isBlank(Skuid)) {
                errorbuilder.append("Missing Values Sku id 	::");
                errorbuilder.append(" ");
                errorbuilder.append(AMPERSAND);

            }
            if (StringUtils.isBlank(priority)) {
                errorbuilder.append("Missing Values priority ::");
                errorbuilder.append(" ");
                errorbuilder.append(AMPERSAND);

            }
            if (!StringUtils.isBlank(sds) && !StringUtils.isBlank(label) && (mimeType.equals("application/pdf"))) {
                errorbuilder.append("application/pdf cannot contain both sds/label::");
                errorbuilder.append(" ");
                errorbuilder.append(AMPERSAND);

            }
            if ((StringUtils.isBlank(imageName) && StringUtils.isBlank(urlLinks)) && (mimeType.equals("application/pdf"))) {
                errorbuilder.append("imageName/URL Links Values Missing  ::");
                errorbuilder.append(" ");
                errorbuilder.append(AMPERSAND);

            }
            // physical path and url both given
            if (!StringUtils.isBlank(imageName) && !StringUtils.isBlank(urlLinks)) {
                errorbuilder.append("Image & url cannot have same priority: ");
                errorbuilder.append(" ");
                errorbuilder.append(AMPERSAND);

            }
            // minetype error
            if (StringUtils.isBlank(mimeType) || !(mimeType.equals("image/jpeg") || mimeType.equals("image/jpg") || mimeType
                    .equals("application/pdf") || mimeType.equals("video/mp4"))) {
                errorbuilder.append("Invalid/Missing MIME Type: ");
                errorbuilder.append(" ");
                errorbuilder.append(AMPERSAND);
            }
            //physical file not found
            if (!StringUtils.isBlank(imageName)) {
                String baseDec = Config.getParameter("media.physicalpath");
                Boolean found = findImageInZip(imageName, getBlobDataImportService().readBlobsToFiles(
                        getConfigurationService().getConfiguration().getString(SiteonepcmConstants.PCM_ERROR_REPORT_LOCATION),
                        baseDec + SiteonepcmConstants.PCM_PROCESSING_LOCATION));

                if (!found) {
                    errorbuilder.append(" Image/PDF Not Found ");
                    errorbuilder.append(" ");
                    errorbuilder.append(AMPERSAND);
                }
            }
            //missing values for image name when mimeType (image/jpg /image/jpeg)
            if (StringUtils.isBlank(imageName) && (mimeType.equalsIgnoreCase("image/jpeg") || mimeType.equalsIgnoreCase("image/jpg"))) {
                errorbuilder.append(" imageName Missing  " + 1);
                errorbuilder.append(" ");
                errorbuilder.append(AMPERSAND);
            }
        }

        if (mapIdx.intValue() == 0 && (errorbuilder.length() != 0 || !errorbuilder.toString().equals(""))) {

            throw new IllegalArgumentException((errorbuilder.deleteCharAt(errorbuilder.length() - 1).toString().trim()));
        }
    }

    private boolean findImageInZip(final String searchFile, final List<File> files) {
        Enumeration<? extends ZipEntry> e = null;
        try {
            e = new ZipFile(files.get(0)).entries();
            while (e.hasMoreElements()) {

                ZipEntry entry = e.nextElement();
                if (entry.getName().contains(searchFile)) {
                    return true;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override protected String escapeQuotes(final String input) {
        final String[] splitedInput = StringUtils.splitPreserveAllTokens(input, SEMICOLON_CHAR);
        final List<String> tmp = new ArrayList<String>();
        for (final String string : splitedInput) {
            if (doesNotContainNewLine(string)) {
                tmp.add(StringEscapeUtils.escapeCsv(string));
            } else {
                tmp.add(string);
            }
        }
        return StringUtils.join(tmp, SEMICOLON_CHAR);
    }

    @Override protected boolean doesNotContainNewLine(final String string) {
        return !StringUtils.contains(string, CharUtils.LF);
    }

    /**
     * @see de.hybris.platform.acceleratorservices.dataimport.batch.converter.ImpexConverter#filter(java.util.Map)
     */
    @Override public boolean filter(final Map<Integer, String> row) {
        return rowFilter.filter(row);
    }

    /**
     * @see de.hybris.platform.acceleratorservices.dataimport.batch.converter.ImpexConverter#getHeader()
     */
    @Override public String getHeader() {
        return header;
    }

    /**
     * @param header the header to set
     */
    @Override public void setHeader(final String header) {
        Assert.hasText(header, "must have text; it must not be null, empty, or blank");
        this.header = header;
    }

    /**
     * @param impexRow the impexRow to set
     */
    @Override public void setImpexRow(final String impexRow) {
        Assert.hasText(impexRow, "must have text; it must not be null, empty, or blank");
        this.impexRow = impexRow;
    }

    /**
     * @param type the type to set
     */
    @Override public void setType(final String type) {
        this.type = type;
    }

    /**
     * @return the type
     */
    @Override public String getType() {
        return type;
    }

    /**
     * @param rowFilter the rowFilter to set
     */
    @Override public void setRowFilter(final ImpexRowFilter rowFilter) {
        Assert.notNull(rowFilter, "must not be null");
        this.rowFilter = rowFilter;
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
     * @param blobDataImportService the blobDataImportService to set
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
     * @param configurationService the configurationService to set
     */
    public void setConfigurationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

}
