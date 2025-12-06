/**
 *
 */
package com.siteone.core.requestaccount.service;

import java.util.List;

import com.siteone.core.model.SiteOneContrPrimaryBusinessModel;


/**
 * @author SNavamani
 *
 */
public interface SiteoneContrPrimaryBusinessMapService
{
	List<SiteOneContrPrimaryBusinessModel> getPrimaryBusinessMap();

	List<SiteOneContrPrimaryBusinessModel> getChildPrimaryBusinessMap(final String code);
}
