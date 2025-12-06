/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.impex.model.cronjob.ImpExImportCronJobModel;
import de.hybris.platform.servicelayer.event.ClusterAwareEvent;
import de.hybris.platform.servicelayer.event.events.AbstractEvent;
import de.hybris.platform.store.BaseStoreModel;


/**
 * @author 1230514
 *
 */
public class SiteOneBatchNotificationEvent extends AbstractEvent implements ClusterAwareEvent
{

	private BaseStoreModel baseStore;
	private BaseSiteModel site;
	private String fileName;
	private String emailReceiver;
	private ImpExImportCronJobModel importCronjob;
	private String impexTransformerLog;
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

	/**
	 * @return the baseStore
	 */
	public BaseStoreModel getBaseStore()
	{
		return baseStore;
	}

	/**
	 * @param baseStore
	 *           the baseStore to set
	 */
	public void setBaseStore(final BaseStoreModel baseStore)
	{
		this.baseStore = baseStore;
	}

	/**
	 * @return the site
	 */
	public BaseSiteModel getSite()
	{
		return site;
	}

	/**
	 * @param site
	 *           the site to set
	 */
	public void setSite(final BaseSiteModel site)
	{
		this.site = site;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName()
	{
		return fileName;
	}

	/**
	 * @param fileName
	 *           the fileName to set
	 */
	public void setFileName(final String fileName)
	{
		this.fileName = fileName;
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

	/**
	 * @return the importCronjob
	 */
	public ImpExImportCronJobModel getImportCronjob()
	{
		return importCronjob;
	}

	/**
	 * @param importCronjob
	 *           the importCronjob to set
	 */
	public void setImportCronjob(final ImpExImportCronJobModel importCronjob)
	{
		this.importCronjob = importCronjob;
	}



	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.servicelayer.event.ClusterAwareEvent#publish(int, int)
	 */
	@Override
	public boolean publish(final int sourceNodeId, final int targetNodeId)
	{
		// YTODO Auto-generated method stub
		return targetNodeId == 0;
	}

	/**
	 * @return the impexTransformerLog
	 */
	public String getImpexTransformerLog()
	{
		return impexTransformerLog;
	}

	/**
	 * @param impexTransformerLog
	 *           the impexTransformerLog to set
	 */
	public void setImpexTransformerLog(final String impexTransformerLog)
	{
		this.impexTransformerLog = impexTransformerLog;
	}
}
