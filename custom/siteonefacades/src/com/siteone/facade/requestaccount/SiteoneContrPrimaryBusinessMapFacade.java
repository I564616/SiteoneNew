/**
 *
 */
package com.siteone.facade.requestaccount;

import java.util.List;
import java.util.Map;

import com.siteone.facade.customer.info.SiteOneContrPrimaryBusinessData;


/**
 * @author SNavamani
 *
 */
public interface SiteoneContrPrimaryBusinessMapFacade
{
	Map<String, List<SiteOneContrPrimaryBusinessData>> getPrimaryBusinessMap();
}
