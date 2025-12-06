package com.siteone.core.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;

import org.apache.log4j.Logger;

import com.siteone.core.constants.SiteoneCoreConstants;


@SuppressWarnings("PMD")
public class SiteoneCoreManager extends GeneratedSiteoneCoreManager
{
	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(SiteoneCoreManager.class.getName());

	public static final SiteoneCoreManager getInstance()
	{
		final ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (SiteoneCoreManager) em.getExtension(SiteoneCoreConstants.EXTENSIONNAME);
	}

}
