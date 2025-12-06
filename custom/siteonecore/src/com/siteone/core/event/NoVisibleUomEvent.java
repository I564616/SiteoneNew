/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.servicelayer.event.events.AbstractEvent;
import de.hybris.platform.store.BaseStoreModel;

import java.io.DataInputStream;


/**
 * @author HR03708
 *
 */
public class NoVisibleUomEvent extends AbstractEvent
{
	private BaseSiteModel site;
	private LanguageModel language;
	private BaseStoreModel baseStore;
	private String fileName;
	private DataInputStream noVisibleUomDataStream;


	/**
	 * @return the noVisibleUomDataStream
	 */
	public DataInputStream getNoVisibleUomDataStream()
	{
		return noVisibleUomDataStream;
	}

	/**
	 * @param noVisibleUomDataStream
	 *           the noVisibleUomDataStream to set
	 */
	public void setNoVisibleUomDataStream(final DataInputStream noVisibleUomDataStream)
	{
		this.noVisibleUomDataStream = noVisibleUomDataStream;
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





}
