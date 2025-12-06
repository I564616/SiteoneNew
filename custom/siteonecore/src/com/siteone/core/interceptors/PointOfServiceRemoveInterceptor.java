package com.siteone.core.interceptors;

import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.RemoveInterceptor;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

/**
 * PointOfService/Store deletion causes major issues in website.Since price, stock etc are dependant on store.
 * Instead of hard-delete, do soft-delete using active/inactive flag in PointOfService type.
 */
public class PointOfServiceRemoveInterceptor implements RemoveInterceptor {
    @Override
    public void onRemove(Object o, InterceptorContext interceptorContext) throws InterceptorException {
        if(o instanceof PointOfServiceModel){
            throw new InterceptorException("Cannot remove PointOfService. Use active/inactive flag instead");
        }

    }
}
