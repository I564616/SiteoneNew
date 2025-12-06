package com.siteone.core.cronjob.dao.impl;

import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.util.Config;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.siteone.core.cronjob.dao.OldInvoiceAddressRemovalCronJobDao;


public class OldInvoiceAddressRemovalCronJobDaoImpl extends AbstractItemDao implements OldInvoiceAddressRemovalCronJobDao
{

	private static final Logger LOG = Logger.getLogger(OldInvoiceAddressRemovalCronJobDaoImpl.class);
	private static final String INVOICE_TABLE_ADDRESS_REMOVAL_QUERY = Config.getString("invoice.table.address.removal.query", "");
	private JdbcTemplate jdbcTemplate;

	@Override
	public void removeOldInvoiceAddress()
	{
		final int deleteCount = this.jdbcTemplate.update(INVOICE_TABLE_ADDRESS_REMOVAL_QUERY);
		LOG.info(" Deleted " + deleteCount + " invoice-address older than 2 years");

	}

	public void setJdbcTemplate(final JdbcTemplate jdbcTemplate)

	{
		this.jdbcTemplate = jdbcTemplate;
	}

}
