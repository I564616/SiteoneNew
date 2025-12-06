/**
 * 
 */
package com.siteone.core.cms.dao.impl;

import de.hybris.platform.cms2.servicelayer.daos.impl.DefaultCMSContenSlotDao;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

/**
 * @author nmangal
 *
 */
public class DefaultSiteoneCMSContentSlotDao extends DefaultCMSContenSlotDao
{

	/* (non-Javadoc)
	 * @see de.hybris.platform.cms2.servicelayer.daos.impl.DefaultCMSContenSlotDao#findAllMultiCountryContentSlotsByOriginalSlots(java.util.List, java.util.List)
	 */
	@Override
	public List findAllMultiCountryContentSlotsByOriginalSlots(final List contentSlots, final List catalogVersions)
	{
		
		if (CollectionUtils.isEmpty(contentSlots))
		{
		return Collections.EMPTY_LIST;
		}
		return super.findAllMultiCountryContentSlotsByOriginalSlots(contentSlots, catalogVersions);
	}

}
