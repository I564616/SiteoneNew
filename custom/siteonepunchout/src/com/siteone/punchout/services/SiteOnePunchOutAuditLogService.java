package com.siteone.punchout.services;

import java.util.Date;

import org.cxml.CXML;

public interface SiteOnePunchOutAuditLogService {
	public void saveAuditLog(final CXML request, final CXML response, final String emailId,final String operation, final String sessionId, final Date requestTimeStamp);

}
