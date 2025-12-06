/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;
import de.hybris.platform.servicelayer.event.ClusterAwareEvent;
import de.hybris.platform.store.BaseStoreModel;

import java.util.List;
import java.util.Map;


/**
 * @author rpalanisamy
 *
 */
public class SiteOneFeedFileNotificationEvent extends AbstractCommerceUserEvent implements ClusterAwareEvent
{

	private BaseStoreModel baseStore;
	private BaseSiteModel site;
	private List<Map<String, String>> feedFiles;
	private String emailReceiver;

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.servicelayer.event.ClusterAwareEvent#publish(int, int)
	 */
	@Override
	public boolean publish(final int sourceNodeId, final int targetNodeId)
	{
		return targetNodeId == 0;
	}


	/**
	 * @return the feedFiles
	 */
	public List<Map<String, String>> getFeedFiles()
	{
		return feedFiles;
	}


	/**
	 * @param feedFiles
	 *           the feedFiles to set
	 */
	public void setFeedFiles(final List<Map<String, String>> feedFiles)
	{
		this.feedFiles = feedFiles;
	}


	/**
	 * @return the baseStore
	 */
	@Override
	public BaseStoreModel getBaseStore()
	{
		return baseStore;
	}

	/**
	 * @param baseStore
	 *           the baseStore to set
	 */
	@Override
	public void setBaseStore(final BaseStoreModel baseStore)
	{
		this.baseStore = baseStore;
	}


	/**
	 * @return the site
	 */
	@Override
	public BaseSiteModel getSite()
	{
		return site;
	}

	/**
	 * @param site
	 *           the site to set
	 */
	@Override
	public void setSite(final BaseSiteModel site)
	{
		this.site = site;
	}

	/**
	 * @return the emailReceiver
	 */
	public String getEmailReceiver()
	{
		return emailReceiver;
	}

	/**
	 * @param emailReceiver
	 *           the emailReceiver to set
	 */
	public void setEmailReceiver(final String emailReceiver)
	{
		this.emailReceiver = emailReceiver;
	}



}
