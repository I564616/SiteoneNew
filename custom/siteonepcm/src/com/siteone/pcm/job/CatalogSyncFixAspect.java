package com.siteone.pcm.job;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import org.apache.log4j.Logger;

@Aspect
public class CatalogSyncFixAspect
{
    private static final Logger LOG = Logger.getLogger(String.valueOf(CatalogSyncFixAspect.class));

    @Pointcut("execution(private * de.hybris.platform.catalog.jalo.synchronization.GenericCatalogCopyContext.preProcessMultiValuedProductFeatues(..))")
    public void invokeMethod()
    {
    }

    @Around("invokeMethod()")
    public Object method(final ProceedingJoinPoint pjp) throws Throwable
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("Preventing removal of multivalued ProductFeatures ...");
        }

        return null;
    }
}





