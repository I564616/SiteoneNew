package com.siteone.facades.aspect;

import com.siteone.facades.customer.SiteOneCustomerFacade;
import de.hybris.platform.servicelayer.session.SessionService;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;


/**
 * Created by Abdul Rahman Sheikh M on 11/14/2017.
 *
 */
@Aspect
public class SiteOneSessionAttributeRequestAspect
{

    private static final Logger LOG = Logger.getLogger(SiteOneSessionAttributeRequestAspect.class);
    private static final String GEO_LOCATED_STORE_COOKIE = "gls";
    private static final String CONFIRM_STORE_COOKIE = "csc";
    private static final String SESSION_STORE = "sessionStore";

    @Resource(name = "sessionService")
    private SessionService sessionService;

    @Resource(name = "customerFacade")
    private SiteOneCustomerFacade customerFacade;

    @Before("execution(* com.siteone.facades.customer.impl.DefaultSiteOneCustomerFacade.loginSuccess())")
    public void loggingAdvice(JoinPoint joinPoint)
    {
        if(null == sessionService.getAttribute(SESSION_STORE))
        {
            String backupStoreID = null;

            final Cookie cscCookie = WebUtils.getCookie(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest(), CONFIRM_STORE_COOKIE);
            if(null != cscCookie)
            {
                backupStoreID = cscCookie.getValue();
                customerFacade.makeMyStore(backupStoreID);
                LOG.info("Session POS found null. Invoked make my store with store id -> "+ backupStoreID);
            }
            else
            {
                final Cookie glsCookie = WebUtils.getCookie(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest(), GEO_LOCATED_STORE_COOKIE);
                if(null != glsCookie)
                {
                    backupStoreID = glsCookie.getValue();
                }
                customerFacade.syncSessionStore(backupStoreID);
                LOG.info("Session POS found null. Invoked sync session store!");
            }

        }
    }
}
