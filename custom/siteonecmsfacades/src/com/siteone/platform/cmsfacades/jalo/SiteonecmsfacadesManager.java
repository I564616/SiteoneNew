package com.siteone.platform.cmsfacades.jalo;

import com.siteone.platform.cmsfacades.constants.SiteonecmsfacadesConstants;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import org.apache.log4j.Logger;

public class SiteonecmsfacadesManager extends GeneratedSiteonecmsfacadesManager
{
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger( SiteonecmsfacadesManager.class.getName() );
	
	public static final SiteonecmsfacadesManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (SiteonecmsfacadesManager) em.getExtension(SiteonecmsfacadesConstants.EXTENSIONNAME);
	}
	
}
