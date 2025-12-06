package com.siteone.integration.algonomy;

public interface SiteOneAlgonomyAuditLogService {
    public void saveAuditLog(final String serviceName, final String endPointUrl, String request, final String response, final String corrId);
}