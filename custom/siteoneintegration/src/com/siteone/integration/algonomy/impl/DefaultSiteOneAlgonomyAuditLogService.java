package com.siteone.integration.algonomy.impl;

import com.siteone.core.model.WebserviceAuditLogModel;
import com.siteone.integration.algonomy.SiteOneAlgonomyAuditLogService;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.Config;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import jakarta.annotation.Resource;
import java.util.Arrays;

public class DefaultSiteOneAlgonomyAuditLogService implements SiteOneAlgonomyAuditLogService {
    private static final Logger LOG = Logger.getLogger(DefaultSiteOneAlgonomyAuditLogService.class);

    @Resource(name = "modelService")
    private ModelService modelService;

    @Override
    public void saveAuditLog(String serviceName, String endPointUrl, String request, String response, String corrId) {
        final String auditableServicesString = Config.getString("audit.log.services.list", null);

        //logging only certain request/responses to WebServiceAuditLog table.
        if (StringUtils.contains(auditableServicesString, serviceName)) {
            String[] requestDisabledServices = Config.getString("request.disabled.services", StringUtils.EMPTY).split(",");

            if (null != requestDisabledServices && Arrays.asList(requestDisabledServices).contains(serviceName)) {
                request = StringUtils.EMPTY;
            }

            final WebserviceAuditLogModel webserviceAuditLog = modelService.create(WebserviceAuditLogModel.class);

            webserviceAuditLog.setServiceName(serviceName);
            webserviceAuditLog.setEndPointUrl(endPointUrl);
            webserviceAuditLog.setRequestLog(request);
            webserviceAuditLog.setResponseLog(response);
            webserviceAuditLog.setCorrelationId(corrId);

            try {
                modelService.save(webserviceAuditLog);
            } catch (ModelSavingException modelSavingException) {
                LOG.error("Not able to save in webserviceAuditLog for " + serviceName + " and correlationId is " + corrId, modelSavingException);
            }
        }
    }
}
