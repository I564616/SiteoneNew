package com.siteone.core.jobs.audit.service;

import de.hybris.platform.persistence.audit.gateway.WriteAuditGateway;

import java.util.Date;


public interface SiteOneExtendedWriteAuditGateway extends WriteAuditGateway
{
	int removeAuditRecordsForTypeAndDate(final String type, final Date date);
	void fetchAuditRecordsForTypeAndDate(final String type, final Date date);
}
