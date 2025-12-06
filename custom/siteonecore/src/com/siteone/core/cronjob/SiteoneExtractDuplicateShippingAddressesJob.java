package com.siteone.core.cronjob;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.siteone.core.util.SiteoneSecurityUtils;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.core.PK;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.user.AddressModel;
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
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail2.jakarta.EmailAttachment;
import org.apache.commons.mail2.jakarta.HtmlEmail;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

/***
 * @author VD02162  vdevaruppala@siteone.com
 * @implNote : This Cronjob has been designed and implemented to extract a report of All Active ShipTo Accounts having Duplicate Shipping Addresses and making them Invisible from AddressBook.
 * IMPORTANT : At this release 4.1 this job is only turning the boolean flag(VisibleInAddressBook) to false to hide the addresses. We are not deleting them.
 *
 */
public class SiteoneExtractDuplicateShippingAddressesJob extends AbstractJobPerformable<CronJobModel> {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SiteoneExtractDuplicateShippingAddressesJob.class);
    private static final String FIND_SHIPTOS_QUERY = "SELECT {PK} FROM {b2bUnit} WHERE {active} = '1' and {reportingOrganization} IS NOT NULL and {reportingOrganization} = {PK} and {isBillingAccount} = '1'";
    private static final String CSV_FILE_LOCATION = Config.getString("acceleratorservices.export.basefolder", StringUtils.EMPTY) + "/";
    private static final String FILE_HEADER = "AccountID;AccountName;AddressGuid;Streetname;Streetnumber;City;State;PostalCode";
    private static final String FILENAME = "SiteoneDuplicateShippingAddresses.csv";
    @Override
    public PerformResult perform(final CronJobModel cronJob) {
        boolean error = false;
        final long startTime = System.currentTimeMillis();
        try {
            // Step 1 >
            final FlexibleSearchQuery b2bUnitsQuery = new FlexibleSearchQuery(FIND_SHIPTOS_QUERY);
            final SearchResult<B2BUnitModel> b2BUnitsSearchResults = flexibleSearchService.search(b2bUnitsQuery);
            List<B2BUnitModel> b2BUnitModelsWithAddresses = b2BUnitsSearchResults.getResult().stream().filter(b2BUnitModel -> !b2BUnitModel.getAddresses().isEmpty()).collect(Collectors.toList());
            // Step 2 && 3>
            final Collection<AddressModel> duplicatedAddresses = getDuplicateAddresses(b2BUnitModelsWithAddresses);
            if (CollectionUtils.isNotEmpty(duplicatedAddresses)) {
                Collection<AddressModel> nonDuplicateAddresses = duplicatedAddresses.stream()
                        .<Map<PK, AddressModel>> collect(HashMap::new,(m,e)->m.put(e.getPk(), e), Map::putAll)
                        .values();
                saveDuplicateAddresses(nonDuplicateAddresses); // Step 4
                error = generateReport(nonDuplicateAddresses); // Step 5
                LOGGER.debug("SiteoneExtractDuplicateShippingAddressesJob:TimeTaken="+(System.currentTimeMillis()-startTime));
                LOGGER.info("SiteoneExtractDuplicateShippingAddressesJob:TimeTaken="+(System.currentTimeMillis()-startTime)/60000 +":Minutes");
            }
            else if(CollectionUtils.isEmpty(duplicatedAddresses)){
                // no need of an attachment if no duplicate addresses found.
                error = true;
            }
        }catch(final Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = false;
        }
        return error ? new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED): new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
    }

    private Collection<AddressModel>  getDuplicateAddresses(final List<B2BUnitModel> b2BUnitList){
        LOGGER.info("b2BUnitList:size="+b2BUnitList.size());
        Collection<AddressModel> listDuplicatedAddresses = new ArrayList<>();
        try {
            // Step 2 > Iterate through b2bUnits(Customer) and get All Shipping Addresses and Store to a Map
            final Map<B2BUnitModel, List<AddressModel>> mapb2bUnitAddresses = new HashMap<>();
            for (B2BUnitModel b2bUnitParent : b2BUnitList) {
                //MAIN "PARENT SHIP TO" ACCOUNT
                if (b2bUnitParent.getReportingOrganization() == null || b2bUnitParent.getReportingOrganization().getUid().equalsIgnoreCase(b2bUnitParent.getUid())) {
                    populateB2BUnitAddresses(b2bUnitParent,mapb2bUnitAddresses);
                }
             }
           // Step 3 > Iterate through to see occurrences of Duplicate Addresses for every b2bUnit(Customer) and Store to a Collection
            if (org.apache.commons.collections4.MapUtils.isNotEmpty(mapb2bUnitAddresses)) {
                for (final Map.Entry<B2BUnitModel, List<AddressModel>> entry : mapb2bUnitAddresses.entrySet()) {
                    List<AddressModel> shippingAddressModels = new ArrayList<>(entry.getValue());
                    shippingAddressModels.sort(Comparator.comparing(AddressModel::getCreationtime).reversed());
                    for (int i = 0; i < shippingAddressModels.size(); i++) {
                        for (int j = i + 1; j < shippingAddressModels.size(); j++) {
                            if (isAddressEqual(shippingAddressModels.get(i), shippingAddressModels.get(j))) {
                                if (shippingAddressModels.get(j) != null) {
                                    listDuplicatedAddresses.add(shippingAddressModels.get(j));
                                }
                            }
                        }
                    }
                }
            }
            if(CollectionUtils.isNotEmpty(listDuplicatedAddresses))
                    return listDuplicatedAddresses;
        } catch (final Exception e){
                LOGGER.error("Error while getting Addresses"+e);
        }
        return listDuplicatedAddresses;
    }

    private void populateB2BUnitAddresses(B2BUnitModel b2bParentUnit, Map<B2BUnitModel, List<AddressModel>> mapb2bUnitAddresses){
        // Step 4 > Iterate through All Main and Children ShipTo Addresses and turn the IsVisibleInAddressBook to boolean "false" to have them not show up in AddressBook.
        final List<AddressModel> listB2BUnitAddresses = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(b2bParentUnit.getAddresses())) {
            List<AddressModel> parentShipToShippingAddresses = b2bParentUnit.getAddresses().stream().filter(address -> address.getShippingAddress() != null && Boolean.FALSE.equals(address.getBillingAddress()) && Boolean.TRUE.equals(address.getShippingAddress()) && Boolean.TRUE.equals(address.getVisibleInAddressBook()) && address.getLine1() != null && address.getTown() != null && address.getRegion() != null && address.getPostalcode() != null).collect(Collectors.toList());
            listB2BUnitAddresses.addAll(parentShipToShippingAddresses);
            //CHILDREN ACCOUNTS OF "PARENT SHIP TO".
            if(!b2bParentUnit.getMembers().isEmpty()){
                b2bParentUnit.getMembers().stream().forEach(member ->{
                    if (member instanceof B2BUnitModel)
                    {
                        final B2BUnitModel childShipTo = (B2BUnitModel) member;
                        List<AddressModel> childShipToShippingAddresses = childShipTo.getAddresses().stream().filter(address -> address.getShippingAddress() != null && Boolean.FALSE.equals(address.getBillingAddress()) && Boolean.TRUE.equals(address.getShippingAddress()) && Boolean.TRUE.equals(address.getVisibleInAddressBook()) && address.getLine1() != null && address.getTown() != null && address.getRegion() != null && address.getPostalcode() != null).collect(Collectors.toList());
                        listB2BUnitAddresses.addAll(childShipToShippingAddresses);
                    }
                });
            }
            mapb2bUnitAddresses.put(b2bParentUnit,listB2BUnitAddresses);
        }
    }


    private void saveDuplicateAddresses(Collection<AddressModel> duplicatedAddresses){
        // Step 4 > Iterate through Duplicate Addresses and turn the IsVisibleInAddressBook to boolean "false" to have them not show up in AddressBook.
        duplicatedAddresses.forEach(duplicated -> duplicated.setDuplicate(true));
        duplicatedAddresses.forEach(duplicated -> duplicated.setVisibleInAddressBook(false));
        modelService.saveAll(duplicatedAddresses);
    }
    private boolean generateReport(Collection<AddressModel> duplicatedAddresses) {
        boolean foundErrors  = true;
            // Step 5 > Finally generate the File and send as email Attachment.
            PrintWriter printWriter = null;
            try {
                printWriter = new PrintWriter(new FileWriter(SiteoneSecurityUtils.buildValidAvatarPath(CSV_FILE_LOCATION + FILENAME)));
                printWriter.println(FILE_HEADER);
                for (final AddressModel address : duplicatedAddresses) {
                    printWriter.println(getCustomerAccountNName(address) + ";" + Optional.ofNullable(address.getPk().toString()).orElse("")+";" +Optional.ofNullable(address.getStreetname()).orElse("")+ ";"+Optional.ofNullable(address.getStreetnumber()).orElse("")+";"+Optional.ofNullable(address.getTown()).orElse("")+";"+Optional.ofNullable(address.getRegion()).map(RegionModel::getIsocodeShort).orElse("")+";"+Optional.ofNullable(address.getPostalcode()).orElse(""));
                }
                sendReportMail(CSV_FILE_LOCATION, String.format(FILENAME, System.currentTimeMillis()));
            } catch (final Exception e) {
                LOGGER.error(e.getMessage(), e);
                foundErrors = false;
            } finally {
                //close the file
                printWriter.flush();
                printWriter.close();
                //Delete file
                File file = new File(SiteoneSecurityUtils.buildValidAvatarPath(CSV_FILE_LOCATION + FILENAME));
                if (file.delete()) {
                    LOGGER.info("Deleted the file: " + CSV_FILE_LOCATION + FILENAME);
                } else {
                    LOGGER.error("Failed to delete the file: " + CSV_FILE_LOCATION + FILENAME);
                }
            }
        return foundErrors;
    }
    private String getCustomerAccountNName(final AddressModel address)
    {
        String customerIdName = "";
        final ItemModel item = address.getOwner();
        if (item instanceof B2BUnitModel)
        {
            final B2BUnitModel b2bUnit = (B2BUnitModel) item;
            customerIdName = Joiner
                    .on(";")
                    .skipNulls()
                    .join(
                            Strings.emptyToNull(b2bUnit.getUid()),
                            Strings.emptyToNull(b2bUnit.getDisplayName()));
        }
        return customerIdName;
    }

    private boolean isAddressEqual(final AddressModel firstAddress, final AddressModel secondAddress)
    {
        if (firstAddress == null || secondAddress == null)
        {
            return false;
        }
        return  StringUtils.equals(firstAddress.getLine1(), secondAddress.getLine1()) &&
                StringUtils.equals(firstAddress.getTown(), secondAddress.getTown()) &&
                StringUtils.equals(firstAddress.getRegion().getIsocode(), secondAddress.getRegion().getIsocode()) &&
                StringUtils.equals(firstAddress.getPostalcode(), secondAddress.getPostalcode());
    }
    private void sendReportMail(final String filePath, final String fileName) {

        HtmlEmail htmlEmail = null;
        final String emailID = Config.getParameter("SiteoneExtractDuplicateShippingAddressesJob.ReportMailAddress");
        final String ccEmailID = Config.getParameter("SiteoneExtractDuplicateShippingAddressesJob.CCMailAddress");
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

            subject = Config.getParameter("SiteoneExtractDuplicateShippingAddressesJob.MailSubject");
            mailMessage = Config.getParameter("SiteoneExtractDuplicateShippingAddressesJob.MailMessage");

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
