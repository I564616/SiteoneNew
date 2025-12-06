package com.siteone.integration.service.impl;
import com.siteone.integration.deliverycost.data.SiteOneWsDeliveryCostAPIResponseData;
import com.siteone.integration.services.ue.SiteOneDeliveryCostWebService;
import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.servicelayer.ServicelayerBaseTest;
import de.hybris.platform.servicelayer.model.ModelService;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import jakarta.annotation.Resource;
@IntegrationTest
public class DefaultSiteOneDeliveryCostWebServiceTest extends ServicelayerBaseTest {
    @Resource
    private SiteOneDeliveryCostWebService siteOneDeliveryCostWebService;

    @Resource
    private ModelService modelService;

    private static final Logger LOG = Logger.getLogger(DefaultSiteOneDeliveryCostWebServiceTest.class);


    @Test
    public void getDeliveryCost_Test1_OrderType_IRR_AllTrue() throws Exception {

        String branch = "";
        String division= "";
        String orderType = "IRR";
        boolean isGuestUser = true;
        boolean homeowners = true;
        boolean expedite = true;
        boolean lift = true;

        final SiteOneWsDeliveryCostAPIResponseData deliveryCostResponseData = siteOneDeliveryCostWebService.getDeliveryCost(branch,division,orderType, homeowners, isGuestUser, expedite, lift);

        Assert.assertNotNull(deliveryCostResponseData);
        Assert.assertNotNull(deliveryCostResponseData.getResponseCode());
        Assert.assertNotNull(deliveryCostResponseData.getResponseMessage());
        Assert.assertNotNull(deliveryCostResponseData.getCost());
        LOG.info("deliveryCostResponseData.getMessage()" + deliveryCostResponseData.getResponseCode());
        LOG.info("deliveryCostResponseData.getResponseMessage()" + deliveryCostResponseData.getResponseMessage());
        LOG.info("deliveryCostResponseData.getCost()" + deliveryCostResponseData.getCost());
        //Expected is $85.15
    }

    @Test
    public void getDeliveryCost_Test2_OrderType_IRR_NoLift_HomeNExpedite_True() throws Exception {

        String branch = "";
        String division = "";
        String orderType = "IRR";
        boolean isGuestUser = true;
        boolean homeowners = true;
        boolean expedite = true;
        boolean lift = false;

        final SiteOneWsDeliveryCostAPIResponseData deliveryCostResponseData = siteOneDeliveryCostWebService.getDeliveryCost(branch,division, orderType, homeowners, isGuestUser, expedite, lift);

        Assert.assertNotNull(deliveryCostResponseData);
        Assert.assertNotNull(deliveryCostResponseData.getResponseCode());
        Assert.assertNotNull(deliveryCostResponseData.getResponseMessage());
        Assert.assertNotNull(deliveryCostResponseData.getCost());
        LOG.info("deliveryCostResponseData.getMessage()" + deliveryCostResponseData.getResponseCode());
        LOG.info("deliveryCostResponseData.getResponseMessage()" + deliveryCostResponseData.getResponseMessage());
        LOG.info("deliveryCostResponseData.getCost()" + deliveryCostResponseData.getCost());
        //Expected is $60.15
    }

    @Test
    public void getDeliveryCost_Test3_OrderType_IRR_NoLift_N_NoHome_ExpediteTrue() throws Exception {

        String branch = "";
        String division = "";
        String orderType = "IRR";
        boolean isGuestUser = false;
        boolean homeowners = false;
        boolean expedite = true;
        boolean lift = false;

        final SiteOneWsDeliveryCostAPIResponseData deliveryCostResponseData = siteOneDeliveryCostWebService.getDeliveryCost(branch,division, orderType, homeowners, isGuestUser, expedite, lift);

        Assert.assertNotNull(deliveryCostResponseData);
        Assert.assertNotNull(deliveryCostResponseData.getResponseCode());
        Assert.assertNotNull(deliveryCostResponseData.getResponseMessage());
        Assert.assertNotNull(deliveryCostResponseData.getCost());
        LOG.info("deliveryCostResponseData.getMessage()" + deliveryCostResponseData.getResponseCode());
        LOG.info("deliveryCostResponseData.getResponseMessage()" + deliveryCostResponseData.getResponseMessage());
        LOG.info("deliveryCostResponseData.getCost()" + deliveryCostResponseData.getCost());
        //Expected is $35.05
    }

    @Test
    public void getDeliveryCost_Test4_OrderType_IRR_AllFalse() throws Exception {

        String branch = "";
        String division = "";
        String orderType = "IRR";
        boolean isGuestUser = false;
        boolean homeowners = false;
        boolean expedite = false;
        boolean lift = false;

        final SiteOneWsDeliveryCostAPIResponseData deliveryCostResponseData = siteOneDeliveryCostWebService.getDeliveryCost(branch,division,orderType, homeowners, isGuestUser, expedite, lift);

        Assert.assertNotNull(deliveryCostResponseData);
        Assert.assertNotNull(deliveryCostResponseData.getResponseCode());
        Assert.assertNotNull(deliveryCostResponseData.getResponseMessage());
        Assert.assertNotNull(deliveryCostResponseData.getCost());
        LOG.info("deliveryCostResponseData.getMessage()" + deliveryCostResponseData.getResponseCode());
        LOG.info("deliveryCostResponseData.getResponseMessage()" + deliveryCostResponseData.getResponseMessage());
        LOG.info("deliveryCostResponseData.getCost()" + deliveryCostResponseData.getCost());
        //Expected is $10.05
    }

    @Test
    public void getDeliveryCost_Test5_OrderType_Other_AllFalse() throws Exception {

        String branch = "";
        String division = "";
        String orderType = "Other";
        boolean isGuestUser = false;
        boolean homeowners = false;
        boolean expedite = false;
        boolean lift = false;

        final SiteOneWsDeliveryCostAPIResponseData deliveryCostResponseData = siteOneDeliveryCostWebService.getDeliveryCost(branch,division,orderType, homeowners, isGuestUser, expedite, lift);

        Assert.assertNotNull(deliveryCostResponseData);
        Assert.assertNotNull(deliveryCostResponseData.getResponseCode());
        Assert.assertNotNull(deliveryCostResponseData.getResponseMessage());
        Assert.assertNotNull(deliveryCostResponseData.getCost());
        LOG.info("deliveryCostResponseData.getMessage()" + deliveryCostResponseData.getResponseCode());
        LOG.info("deliveryCostResponseData.getResponseMessage()" + deliveryCostResponseData.getResponseMessage());
        LOG.info("deliveryCostResponseData.getCost()" + deliveryCostResponseData.getCost());
        //Expected is $25.05
    }

    @Test
    public void getDeliveryCost_Test6_OrderType_Other_AllTrue() throws Exception {

        String branch = "";
        String division = "";
        String orderType = "Other";
        boolean isGuestUser = true;
        boolean homeowners = true;
        boolean expedite = true;
        boolean lift = true;

        final SiteOneWsDeliveryCostAPIResponseData deliveryCostResponseData = siteOneDeliveryCostWebService.getDeliveryCost(branch,division,orderType, homeowners, isGuestUser, expedite, lift);

        Assert.assertNotNull(deliveryCostResponseData);
        Assert.assertNotNull(deliveryCostResponseData.getResponseCode());
        Assert.assertNotNull(deliveryCostResponseData.getResponseMessage());
        Assert.assertNotNull(deliveryCostResponseData.getCost());
        LOG.info("deliveryCostResponseData.getMessage()" + deliveryCostResponseData.getResponseCode());
        LOG.info("deliveryCostResponseData.getResponseMessage()" + deliveryCostResponseData.getResponseMessage());
        LOG.info("deliveryCostResponseData.getCost()" + deliveryCostResponseData.getCost());
        //Expected is $100.15
    }

    @Test
    public void getDeliveryCost_Test7_OrderType_Other_NoLift_HomeNExpedite_True() throws Exception {

        String branch = "";
        String division = "";
        String orderType = "Other";
        boolean isGuestUser = true;
        boolean homeowners = true;
        boolean expedite = true;
        boolean lift = false;

        final SiteOneWsDeliveryCostAPIResponseData deliveryCostResponseData = siteOneDeliveryCostWebService.getDeliveryCost(branch,division, orderType, homeowners, isGuestUser, expedite, lift);
        Assert.assertNotNull(deliveryCostResponseData);
        Assert.assertNotNull(deliveryCostResponseData.getResponseCode());
        Assert.assertNotNull(deliveryCostResponseData.getResponseMessage());
        Assert.assertNotNull(deliveryCostResponseData.getCost());
        LOG.info("deliveryCostResponseData.getMessage()" + deliveryCostResponseData.getResponseCode());
        LOG.info("deliveryCostResponseData.getResponseMessage()" + deliveryCostResponseData.getResponseMessage());
        LOG.info("deliveryCostResponseData.getCost()" + deliveryCostResponseData.getCost());
        //Expected is $75.10
    }

    @Test
    public void getDeliveryCost_Test6_OrderType_Other_NoLift_N_NoHome_NExpedite_True() throws Exception {

        String branch = "";
        String division = "";
        String orderType = "Other";
        boolean isGuestUser = true;
        boolean homeowners = false;
        boolean expedite = true;
        boolean lift = false;

        final SiteOneWsDeliveryCostAPIResponseData deliveryCostResponseData = siteOneDeliveryCostWebService.getDeliveryCost(branch,division,orderType, homeowners, isGuestUser, expedite, lift);

        Assert.assertNotNull(deliveryCostResponseData);
        Assert.assertNotNull(deliveryCostResponseData.getResponseCode());
        Assert.assertNotNull(deliveryCostResponseData.getResponseMessage());
        Assert.assertNotNull(deliveryCostResponseData.getCost());
        LOG.info("deliveryCostResponseData.getMessage()" + deliveryCostResponseData.getResponseCode());
        LOG.info("deliveryCostResponseData.getResponseMessage()" + deliveryCostResponseData.getResponseMessage());
        LOG.info("deliveryCostResponseData.getCost()" + deliveryCostResponseData.getCost());
        //Expected is $50.05
    }
}
