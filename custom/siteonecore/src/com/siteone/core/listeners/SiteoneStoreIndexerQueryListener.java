/**
 *
 */
package com.siteone.core.listeners;


import de.hybris.platform.search.restriction.impl.DefaultSearchRestrictionService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.solrfacetsearch.indexer.IndexerQueryContext;
import de.hybris.platform.solrfacetsearch.indexer.IndexerQueryListener;
import de.hybris.platform.solrfacetsearch.indexer.exceptions.IndexerException;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;


/**
 * @author BS
 *
 */
public class SiteoneStoreIndexerQueryListener implements IndexerQueryListener
{

	private static final Logger LOG = Logger.getLogger(SiteoneStoreIndexerQueryListener.class);

	@Resource(name = "sessionService")
	private SessionService sessionService;
	@Resource(name = "searchRestrictionService")
	private DefaultSearchRestrictionService searchRestrictionService;

	@Override
	public void beforeQuery(final IndexerQueryContext var1) throws IndexerException
	{
		boolean isenabled = searchRestrictionService.isSearchRestrictionsEnabled();
		searchRestrictionService.disableSearchRestrictions();
		isenabled = searchRestrictionService.isSearchRestrictionsEnabled();
		LOG.error("Disable Restriction" + isenabled);

	}

	@Override
	public void afterQuery(final IndexerQueryContext var1) throws IndexerException
	{
		LOG.error("Enable Restriction");
		searchRestrictionService.enableSearchRestrictions();

	}

	@Override
	public void afterQueryError(final IndexerQueryContext var1) throws IndexerException
	{
		// YTODO Auto-generated method stub

	}

}
