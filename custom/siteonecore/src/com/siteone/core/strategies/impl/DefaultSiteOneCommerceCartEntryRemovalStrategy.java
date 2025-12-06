package com.siteone.core.strategies.impl;

import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.servicelayer.model.ModelService;

public class DefaultSiteOneCommerceCartEntryRemovalStrategy implements SiteOneCommerceCartEntryRemovalStrategy
{
   private ModelService modelService;

    @Override
    public void removeCartEntry(AbstractOrderModel cart)
    {
        if(cart instanceof CartModel)
        {
            cart.setEntries(null);
            getModelService().save(cart);
            getModelService().refresh(cart);
        }
    }

    public ModelService getModelService() {
        return modelService;
    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }
}
