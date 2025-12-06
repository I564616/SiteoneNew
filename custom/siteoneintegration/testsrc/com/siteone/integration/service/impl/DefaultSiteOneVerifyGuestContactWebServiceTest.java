package com.siteone.integration.service.impl;

import com.siteone.integration.services.ue.SiteOneVerifyGuestContactWebService;
import com.siteone.integration.verifyguestcontact.data.SiteOneWsVerifyGuestContactResponseData;
import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.servicelayer.ServicelayerBaseTest;
import de.hybris.platform.servicelayer.model.ModelService;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import jakarta.annotation.Resource;

@IntegrationTest
public class DefaultSiteOneVerifyGuestContactWebServiceTest extends ServicelayerBaseTest {
    @Resource
    private SiteOneVerifyGuestContactWebService siteOneVerifyGuestContactWebService;

    @Resource
    private ModelService modelService;

    private static final Logger LOG = Logger.getLogger(DefaultSiteOneDeliveryCostWebServiceTest.class);

    @Test
    public void getVerifyGuestInUE() throws Exception {

        String uid = "xyz@gmail.com";

        SiteOneWsVerifyGuestContactResponseData verifyGuestContactResponseData = siteOneVerifyGuestContactWebService.verifyGuestInUE(uid, false);

        Assert.assertNotNull(verifyGuestContactResponseData);
        Assert.assertNotNull(verifyGuestContactResponseData.getStatus());
        Assert.assertNotNull(verifyGuestContactResponseData.getErrorMessage());
        Assert.assertNotNull(verifyGuestContactResponseData.getResult());
        LOG.info("verifyGuestContactResponseData.getIsSuccess()" + verifyGuestContactResponseData.getStatus());
        LOG.info("verifyGuestContactResponseData.getMessage()" + verifyGuestContactResponseData.getErrorMessage());
        LOG.info("verifyGuestContactResponseData.getResult()" + verifyGuestContactResponseData.getResult());
        //Expected is True found in UE
    }
    @Test
    public void getVerifyGuestInUENotFound() throws Exception {

        String uid = "ROMEROARIETA27@GMAIL.COM";

        SiteOneWsVerifyGuestContactResponseData verifyGuestContactResponseData = siteOneVerifyGuestContactWebService.verifyGuestInUE(uid, false);

        Assert.assertNotNull(verifyGuestContactResponseData);
        Assert.assertNotNull(verifyGuestContactResponseData.getStatus());
        Assert.assertNotNull(verifyGuestContactResponseData.getErrorMessage());
        Assert.assertNotNull(verifyGuestContactResponseData.getResult());
        LOG.info("verifyGuestContactResponseData.getIsSuccess()" + verifyGuestContactResponseData.getStatus());
        LOG.info("verifyGuestContactResponseData.getMessage()" + verifyGuestContactResponseData.getErrorMessage());
        LOG.info("verifyGuestContactResponseData.getResult()" + verifyGuestContactResponseData.getResult());
        //Expected is True found in UE
    }
}
