/**
 *
 */
package com.siteone.core.jobs.customer;

import com.granule.json.JSONException;
import com.granule.json.JSONObject;
import com.siteone.core.model.StoreTimeZoneStatusCronJobModel;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author BS
 *
 */
public class StoreTimeZoneStatusCronjob extends AbstractJobPerformable<StoreTimeZoneStatusCronJobModel> {

    private static final Logger LOG = Logger.getLogger(StoreTimeZoneStatusCronjob.class);

    private SiteOneStoreFinderService siteOneStoreFinderService;

    private ModelService modelService;

    @SuppressWarnings("boxing")
    @Override
    public PerformResult perform(final StoreTimeZoneStatusCronJobModel storeTimeZoneStatusCronjobModel) {
        LOG.info("StoreTimeZoneStatusCronjob :: starttime - " + System.currentTimeMillis());
        final Map<String, Integer> map = new ConcurrentHashMap<>();

        try {
            final Collection<PointOfServiceModel> stores = getSiteOneStoreFinderService().getStores();
            if (CollectionUtils.isNotEmpty(stores)) {
                for (PointOfServiceModel store : stores) {
                    try {
                        double timestamp = System.currentTimeMillis() / 1000;
                        final String response = getSiteOneStoreFinderService().getTimeZoneResponse(store.getLatitude(), store.getLongitude(),
                                timestamp);

                        JSONObject jsonObj = new JSONObject(response);
                        String timezoneId = jsonObj.getString("timeZoneId");

                        LOG.info("POS # " + store.getName() + "      timezoneId = " + timezoneId);
                        store.setTimezoneId(timezoneId);
                    } catch (final JSONException e) {
                        LOG.error(e.getMessage(), e);
                    }
                }
            }

            //Update the store display name
            LOG.info("Updating Display Name for Stores");
            updateStoreNameHashTable(stores, map);
            updateDisplayNameForStores(stores, map);

            //Save changes to ALL modified PointOfServiceModel objects
            getModelService().saveAll(stores);

            LOG.info("Store Display Name Update Complete");
            LOG.info("StoreTimeZoneStatusCronjob executed successfully!");
        } catch (final Exception exception) {
            LOG.error("Exception occured in setting status in store", exception);
            storeTimeZoneStatusCronjobModel.setResult(CronJobResult.FAILURE);
            storeTimeZoneStatusCronjobModel.setStatus(CronJobStatus.ABORTED);
        }
        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    private String getStoreNameValue(final PointOfServiceModel theStore) {
        StringBuilder stringBuilder = new StringBuilder();
        AddressModel theStoreAddress = theStore.getAddress();

        if (null != theStoreAddress) {
            stringBuilder.append(theStoreAddress.getTown());
            RegionModel regionModel = theStoreAddress.getRegion();
            if (null != regionModel) {
                stringBuilder.append(" ").append(regionModel.getIsocodeShort());
            }
            if(theStore.getStoreId() != null)
            {
               stringBuilder.append(" ").append("#").append(theStore.getStoreId());
            }
        }
        return stringBuilder.toString();
    }

    private void updateStoreNameHashTable(final Collection<PointOfServiceModel> stores, final Map<String, Integer> map) {
        for (final PointOfServiceModel store : stores) {
            String storeCityState = getStoreNameValue(store);
            Integer count = map.get(storeCityState);
            map.put(storeCityState, (count == null ? 1 : count + 1));
        }
    }

    private void updateDisplayNameForStores(final Collection<PointOfServiceModel> stores, final Map<String, Integer> map) {
        for (final PointOfServiceModel store : stores) {
            String storeCityState = getStoreNameValue(store);
            Integer count = map.get(storeCityState);
            store.setDisplayName(count != null && count > 1 ? store.getName() : storeCityState);
            LOG.info("POS # " + store.getStoreId() + "      Display Name = " + store.getDisplayName());
        }
    }


    /**
     * @return the modelService
     */
    public ModelService getModelService() {
        return modelService;
    }

    /**
     * @param modelService
     *           the modelService to set
     */
    @Override
    public void setModelService(final ModelService modelService) {
        this.modelService = modelService;
    }

    /**
     * @return the siteOneStoreFinderService
     */
    public SiteOneStoreFinderService getSiteOneStoreFinderService() {
        return siteOneStoreFinderService;
    }


    /**
     * @param siteOneStoreFinderService
     *           the siteOneStoreFinderService to set
     */
    public void setSiteOneStoreFinderService(SiteOneStoreFinderService siteOneStoreFinderService) {
        this.siteOneStoreFinderService = siteOneStoreFinderService;
    }

}

