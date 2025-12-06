package com.siteone.core.cronjob.dao;

import com.siteone.core.model.WebserviceAuditLogModel;

import java.util.Date;
import java.util.List;

public interface WebServiceAuditLogCleanUpCronJobDao {
	
	
  public List<WebserviceAuditLogModel> getAuditLogsByDate(Date dateToRemoveRecords, int batchSize);


}
