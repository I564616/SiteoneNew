/**
 *
 */
package com.siteone.facade.cmspage;

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cmsfacades.data.PageContentSlotData;

import java.util.List;


/**
 * @author PElango
 *
 */
public interface SiteOnePageFacade
{
	List<PageContentSlotData> prepareCMSPageData(final String pageId, final String storeId) throws CMSItemNotFoundException;
}
