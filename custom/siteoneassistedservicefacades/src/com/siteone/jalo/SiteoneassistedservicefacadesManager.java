package com.siteone.jalo;

import com.siteone.facades.constants.SiteoneassistedservicefacadesConstants;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import org.apache.log4j.Logger;

public class SiteoneassistedservicefacadesManager extends GeneratedSiteoneassistedservicefacadesManager
{
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger( SiteoneassistedservicefacadesManager.class.getName() );
	
	public static final SiteoneassistedservicefacadesManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (SiteoneassistedservicefacadesManager) em.getExtension(SiteoneassistedservicefacadesConstants.EXTENSIONNAME);
	}
	
}
