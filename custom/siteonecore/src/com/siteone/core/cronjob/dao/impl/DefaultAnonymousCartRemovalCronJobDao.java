package com.siteone.core.cronjob.dao.impl;

import com.siteone.core.cronjob.dao.AnonymousCartRemovalCronJobDao;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.util.Config;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;


public class DefaultAnonymousCartRemovalCronJobDao extends AbstractItemDao implements AnonymousCartRemovalCronJobDao
{
	private static final Logger LOG = Logger.getLogger(DefaultAnonymousCartRemovalCronJobDao.class);
	private static final String EMPTY_ANONYMOUS_CART_DELETION_QUERY = Config.getString("empty.anonymous.cart.deletion.query", "");

	private JdbcTemplate jdbcTemplate;

	@Override
	public void removeObsoleteEmptyAnonymousCarts()
	{

		final int deleteCount = this.jdbcTemplate.update(EMPTY_ANONYMOUS_CART_DELETION_QUERY);
		LOG.info(deleteCount + " Empty Anonymous Carts Deleted ");

	}

	public void setJdbcTemplate(final JdbcTemplate jdbcTemplate)

	{
		this.jdbcTemplate = jdbcTemplate;
	}

}
