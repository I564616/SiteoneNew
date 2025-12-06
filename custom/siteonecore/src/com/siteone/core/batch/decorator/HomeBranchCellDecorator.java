package com.siteone.core.batch.decorator;

import com.siteone.core.store.services.SiteOneStoreUtilityService;
import de.hybris.platform.core.Registry;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException;
import de.hybris.platform.util.CSVCellDecorator;
import org.apache.log4j.Logger;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class HomeBranchCellDecorator implements CSVCellDecorator {

    private static final Logger LOG = Logger.getLogger(HomeBranchCellDecorator.class);

    @Override
    public String decorate(final int position, final Map srcLine)
    {
        final String supplyChainNodeId = (String) srcLine.get(Integer.valueOf(position));
        if(!StringUtils.isEmpty(supplyChainNodeId))
        {
            try
            {
                return (getStoreUtilityService().getStoreForSupplyChainNodeId(supplyChainNodeId));
            }
            catch (NullPointerException e)
            {
                LOG.error(e);
            }
            catch (final ModelNotFoundException e)
            {
                LOG.error(e);
            }
            catch (final AmbiguousIdentifierException e)
            {
                LOG.error(e);
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * @return the storeFinderService
     */
    protected SiteOneStoreUtilityService getStoreUtilityService()
    {
        return (SiteOneStoreUtilityService) Registry.getApplicationContext().getBean("siteOneStoreUtilityService");
    }
}
