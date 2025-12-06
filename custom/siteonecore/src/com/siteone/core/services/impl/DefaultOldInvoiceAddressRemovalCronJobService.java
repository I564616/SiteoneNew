package com.siteone.core.services.impl;

import com.siteone.core.cronjob.dao.OldInvoiceAddressRemovalCronJobDao;
import com.siteone.core.services.OldInvoiceAddressRemovalCronJobService;


public class DefaultOldInvoiceAddressRemovalCronJobService implements OldInvoiceAddressRemovalCronJobService
{
	private OldInvoiceAddressRemovalCronJobDao oldInvoiceAddressRemovalCronJobDao;

	@Override
	public void removeOldInvoiceAddress()
	{
		oldInvoiceAddressRemovalCronJobDao.removeOldInvoiceAddress();
	}

	public OldInvoiceAddressRemovalCronJobDao getOldInvoiceAddressRemovalCronJobDao()
	{
		return oldInvoiceAddressRemovalCronJobDao;
	}

	public void setOldInvoiceAddressRemovalCronJobDao(final OldInvoiceAddressRemovalCronJobDao oldInvoiceAddressRemovalCronJobDao)
	{
		this.oldInvoiceAddressRemovalCronJobDao = oldInvoiceAddressRemovalCronJobDao;
	}


}
