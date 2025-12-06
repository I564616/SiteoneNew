/**
 *
 */
package com.siteone.core.cronjob.dao;



import java.util.Date;
import java.util.List;

import com.siteone.core.model.SiteOneInvoiceModel;


/**
 *
 */
public interface OldInvoiceRemovalJobDao
{

	/**
	 * @param dateToRemoveRecords
	 * @param batchSize
	 * @return
	 */
	public List<SiteOneInvoiceModel> getInvoicesByDate(Date dateToRemoveRecords, int batchSize);

	

}
