package com.siteone.pcm.job;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.util.Config;
import de.hybris.platform.util.mail.MailUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail2.jakarta.EmailAttachment;

import org.apache.commons.mail2.jakarta.HtmlEmail;
import org.apache.log4j.Logger;

import com.siteone.core.util.SiteoneSecurityUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class PcmExtractProductListJob extends AbstractJobPerformable<CronJobModel> {

    private static final Logger LOGGER = Logger.getLogger(PcmExtractProductListJob.class);
    private static final String FILE_HEADER = "SKU,ITEM_NUMBER,IS_PRODUCT_DISCONTINUED";
    private static final String FILENAME = "Hybris-Product-List.csv";
    private static final String FILE_LOCATION = Config.getString("acceleratorservices.export.basefolder", StringUtils.EMPTY) + "/";

    private void sendReportMail(final String filePath, final String fileName) {

        HtmlEmail htmlEmail = null;
        final String emailID = Config.getParameter("PcmExtractProductListJob.ReportMailAddress");
        final String ccEmailID = Config.getParameter("PcmExtractProductListJob.CCMailAddress");
        String mailMessage = null, subject = null;

        try {
            htmlEmail = (HtmlEmail) MailUtils.getPreConfiguredEmail();
            for (final String receipt : StringUtils.split(emailID, ", ")) {
                htmlEmail.addTo(receipt);
            }
            if (StringUtils.isNotEmpty(ccEmailID)) {
                for (final String cc : StringUtils.split(ccEmailID, ", ")) {
                    htmlEmail.addCc(cc);
                }
            }

            subject = Config.getParameter("PcmExtractProductListJob.MailSubject");
            mailMessage = Config.getParameter("PcmExtractProductListJob.MailMessage");

            final EmailAttachment emailAttachment = new EmailAttachment();
            emailAttachment.setPath(filePath + fileName);
            htmlEmail.attach(emailAttachment);

            htmlEmail.setSubject(subject);

            htmlEmail.setHtmlMsg(mailMessage);

            htmlEmail.send();

        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);

        }

    }

    private void printHeader(PrintWriter printWriter) {
        printWriter.println(FILE_HEADER);
    }

    @Override
    public PerformResult perform(final CronJobModel cronJob) {
        CronJobStatus JOB_STATUS = CronJobStatus.FINISHED;
        CronJobResult JOB_RESULT = CronJobResult.SUCCESS;

        SearchResult<ProductModel> result = null;

        try {
            final String SEARCH_QUERY = "SELECT {p.pk} " +
                    "FROM   {Product as p}, {Catalog as c}, {CatalogVersion as cv} " +
                    "WHERE  {c.pk} = {cv.catalog} " +
                    "AND    {c.id} = 'siteoneProductCatalog' " +
                    "AND    {cv.version} = 'Online' " +
                    "AND    {p.catalog} = {c.pk} " +
                    "AND    {p.catalogversion} = {cv.pk} " +
                    "AND    {p.code} != '9999999' " +
                    "AND    {p.code} NOT LIKE '%_a' " +
                    "AND    {p.code} NOT LIKE '%_b' ";

            final FlexibleSearchQuery query = new FlexibleSearchQuery(SEARCH_QUERY);
            result = getFlexibleSearchService().search(query);

        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            JOB_STATUS = CronJobStatus.ABORTED;
            JOB_RESULT = CronJobResult.ERROR;
        }

        List<ProductModel> productModels = result.getResult();
        PrintWriter printWriter = null;
        try {

            printWriter = new PrintWriter(new FileWriter(SiteoneSecurityUtils.buildValidAvatarPath(FILE_LOCATION + FILENAME)));

            printHeader(printWriter);
            for (ProductModel productModel : productModels) {
                printWriter.println(productModel.getCode() + "," + productModel.getItemNumber() + "," + (productModel.getIsProductDiscontinued()==true? "Y":"N"));
            }

            sendReportMail(FILE_LOCATION, FILENAME);
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            JOB_STATUS = CronJobStatus.ABORTED;
            JOB_RESULT = CronJobResult.ERROR;
        } finally {
            //close the file
            printWriter.flush();
            printWriter.close();

            //Delete file
            File file = new File(SiteoneSecurityUtils.buildValidAvatarPath(FILE_LOCATION + FILENAME));
            if (file.delete()) {
                LOGGER.info("Deleted the file: " + FILE_LOCATION + FILENAME);
            } else {
                LOGGER.error("Failed to delete the file: " + FILE_LOCATION + FILENAME);
            }
        }

        return new PerformResult(CronJobResult.SUCCESS, JOB_STATUS);
    }

    /**
     * @return the flexibleSearchService
     */
    public FlexibleSearchService getFlexibleSearchService() {
        return flexibleSearchService;
    }

    /**
     * @param flexibleSearchService the flexibleSearchService to set
     */
    @Override
    public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }
}