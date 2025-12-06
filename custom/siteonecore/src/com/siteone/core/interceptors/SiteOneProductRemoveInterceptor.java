package com.siteone.core.interceptors;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.RemoveInterceptor;
import org.apache.log4j.Logger;

import java.util.Date;

public class SiteOneProductRemoveInterceptor implements RemoveInterceptor<ProductModel> {

    private Logger LOG = Logger.getLogger(SiteOneProductRemoveInterceptor.class);

    @Override
    public void onRemove(ProductModel product, InterceptorContext interceptorContext) throws InterceptorException {
        StringBuilder message = new StringBuilder("Product : ");
        message.append(product.getCode());
        message.append(" [");
        message.append(product.getPk());
        message.append("] ");
        message.append("catalog version : ");
        message.append(product.getCatalogVersion().getVersion());
        message.append(" is removed time : ");
        message.append(new Date().toString());
        LOG.error(message);
    }

}
