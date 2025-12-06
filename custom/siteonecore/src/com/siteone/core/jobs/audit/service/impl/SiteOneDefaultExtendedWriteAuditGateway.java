package com.siteone.core.jobs.audit.service.impl;

import com.siteone.core.jobs.audit.service.SiteOneExtendedWriteAuditGateway;
import com.siteone.core.util.SiteoneSecurityUtils;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.services.dataimport.SiteOneBlobDataImportService;
import de.hybris.platform.persistence.audit.AuditScopeInvalidator;
import de.hybris.platform.persistence.audit.gateway.impl.DefaultWriteAuditGateway;
import de.hybris.platform.servicelayer.config.ConfigurationService;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class SiteOneDefaultExtendedWriteAuditGateway extends DefaultWriteAuditGateway implements SiteOneExtendedWriteAuditGateway {
    private static final Logger LOG = Logger.getLogger(SiteOneDefaultExtendedWriteAuditGateway.class);
    private static final String FIELD_DELIMITER = "|";
    private static final String[] FILE_HEADER = { "CHANGINGUSER", "PAYLOADBEFORE", "PAYLOADAFTER", "CURRENTTIMESTAMP"};

    private JdbcTemplate jdbcTemplate;
    private AuditScopeInvalidator auditScopeInvalidator;

    /* The Blob dataImport service*/
    private SiteOneBlobDataImportService blobDataImportService;

    /* The configuration service*/
    private ConfigurationService configurationService;

    /**
     * This method delete all audit records for a given type, created before given date.
     */
    @Override
    public int removeAuditRecordsForTypeAndDate(final String type, final Date date)
    {
        Objects.requireNonNull(type, "type is required");
        Objects.requireNonNull(date, "date is required");
        auditScopeInvalidator.clearCurrentAuditForType(type);
        StringBuilder dltStmt = new StringBuilder();
        StringBuilder dltFrom = new StringBuilder();
        dltFrom.append("DELETE FROM ");
        StringBuilder whereClause = new StringBuilder();
        if (type.equalsIgnoreCase("LINKTOPAYAUDITLOG"))
    		{

    			whereClause.append(" WHERE p_timestamp < ?");
    		}
    		else
    		{
    			whereClause.append(" WHERE timestamp < ?");
    		}

        switch(type)
        {
                case "USERS4SN":
                    dltStmt =  dltFrom.append("USERS4SN");
                    break;
                case "USERGROUPS5SN":
                    dltStmt =  dltFrom.append("USERGROUPS5SN");
                    break;
                case "USERAUDIT6SN":
                    dltStmt =  dltFrom.append("USERAUDIT6SN");
                    break;
                case "PGRELS201SN":
                   dltStmt =  dltFrom.append("PGRELS201SN");
                    break;
                case "ABSTRACTCONTACT26SN":
                   dltStmt =  dltFrom.append("ABSTRACTCONTACT26SN");
                    break;
                case "ADDRESSES23SN":
                   dltStmt =  dltFrom.append("ADDRESSES23SN");
                    break;
                case "PAYMENTINFOS42SN":
                   dltStmt =  dltFrom.append("PAYMENTINFOS42SN");
                    break;
                case "CMSSITE1064SN":
                    dltStmt =  dltFrom.append("CMSSITE1064SN");
                    break;
                case "LINKTOPAYAUDITLOG":
        				dltStmt = dltFrom.append("LINKTOPAYAUDITLOG");
        				break;
        }
        return  jdbcTemplate.update(dltStmt.append(whereClause).toString(), new Object[] {date} );
     }

    @Override
    public void fetchAuditRecordsForTypeAndDate(final String type, final Date date) {
        Objects.requireNonNull(type, "type is required");
        Objects.requireNonNull(date, "date is required");
        auditScopeInvalidator.clearCurrentAuditForType(type);
        StringBuilder sltStmt = new StringBuilder();
        sltStmt.append("SELECT CHANGINGUSER, PAYLOADBEFORE, PAYLOADAFTER, CURRENTTIMESTAMP FROM ");

        switch (type) {
            case "USERS4SN":
                sltStmt.append("USERS4SN");
                break;
            case "USERGROUPS5SN":
                sltStmt.append("USERGROUPS5SN");
                break;
            case "USERAUDIT6SN":
                sltStmt.append("USERAUDIT6SN");
                break;
            case "PGRELS201SN":
                sltStmt.append("PGRELS201SN");
                break;
            case "ABSTRACTCONTACT26SN":
                sltStmt.append("ABSTRACTCONTACT26SN");
                break;
            case "ADDRESSES23SN":
                sltStmt.append("ADDRESSES23SN");
                break;
            case "PAYMENTINFOS42SN":
                sltStmt.append("PAYMENTINFOS42SN");
                break;
            case "CMSSITE1064SN":
                sltStmt.append("CMSSITE1064SN");
                break;
            case "LINKTOPAYAUDITLOG":
   				sltStmt.append("LINKTOPAYAUDITLOG");
   				break;
        }

        if (type.equalsIgnoreCase("LINKTOPAYAUDITLOG"))
  		{
  			sltStmt.append(" WHERE p_timestamp < ?");
  		}
  		else
  		{
  			sltStmt.append(" WHERE timestamp < ?");
  		}

        Object[] parameters = new Object[]{date};
        SqlRowSet srs = jdbcTemplate.queryForRowSet(sltStmt.toString(), parameters);

        PrintWriter printWriter = null;
        File file=null;

        try {

            file= File.createTempFile(getFileName(type),".txt");
            printWriter = new PrintWriter(file);

            printHeader(printWriter);

            while (srs.next()) {
                printWriter.println(srs.getString("CURRENTTIMESTAMP") + FIELD_DELIMITER +
                        srs.getString("CHANGINGUSER") + FIELD_DELIMITER + srs.getString("PAYLOADBEFORE") +
                        FIELD_DELIMITER + srs.getString("PAYLOADAFTER"));
            }
        } catch (Exception e) {
            LOG.error("Exception occurred while parsing json", e);
        } finally {
            if (null != printWriter) {
                printWriter.close();
            }
        }
        // Migration | Write Blob
        final String auditLogContainer= getConfigurationService().getConfiguration().getString(SiteoneintegrationConstants.AUDITLOG_EXPORT_TARGET_LOCATION);
        getBlobDataImportService().writeBlob(file,auditLogContainer);

    }

    private int removeAuditRecords(final Function<JdbcTemplate, Integer> template)
    {
        return template.apply(this.jdbcTemplate).intValue();
    }

    private String getFileName(String type) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy_hhmmss");
        String currentServerDate = dateFormat.format(cal.getTime());
        String fileName = "auditlog" + SiteoneintegrationConstants.SEPARATOR_UNDERSCORE + type
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

    @Override
    public void setAuditScopeInvalidator(final AuditScopeInvalidator auditScopeInvalidator)
    {
        this.auditScopeInvalidator = auditScopeInvalidator;
    }

    @Override
    public void setJdbcTemplate(final JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
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
