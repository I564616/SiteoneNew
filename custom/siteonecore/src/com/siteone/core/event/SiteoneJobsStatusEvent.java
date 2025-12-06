/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.event.events.AbstractEvent;
import de.hybris.platform.store.BaseStoreModel;

import java.util.List;


/**
 * @author NMangal
 *
 */
public class SiteoneJobsStatusEvent extends AbstractEvent
{


	private List<CronJobModel> cronJobList;
	private String emailReceiver;
	private BaseStoreModel baseStore;
	private BaseSiteModel site;
	private LanguageModel language;



	/**
	 * @return the language
	 */
	public LanguageModel getLanguage()
	{
		return language;
	}

	/**
	 * @param language
	 *           the language to set
	 */
	public void setLanguage(final LanguageModel language)
	{
		this.language = language;
	}

	public String getEmailReceiver()
	{
		return emailReceiver;
	}

	public void setEmailReceiver(final String emailReceiver)
	{
		this.emailReceiver = emailReceiver;
	}

	public List<CronJobModel> getCronJobList()
	{
		return cronJobList;
	}

	public void setCronJobList(final List<CronJobModel> cronJobList)
	{
		this.cronJobList = cronJobList;
	}

	public BaseSiteModel getSite()
	{
		return site;
	}

	public void setSite(final BaseSiteModel site)
	{
		this.site = site;
	}

	public BaseStoreModel getBaseStore()
	{
		return baseStore;
	}

	public void setBaseStore(final BaseStoreModel baseStore)
	{
		this.baseStore = baseStore;
	}



}
