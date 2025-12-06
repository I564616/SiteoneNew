/**
 *
 */
package com.siteone.core.requestaccount.dao;

import java.util.List;

import com.siteone.core.model.SiteOneContrPrimaryBusinessModel;


/**
 * @author SNavamani
 *
 */
public interface SiteoneContrPrimaryBusinessMapDao
{
	List<SiteOneContrPrimaryBusinessModel> getPrimaryBusinessMap();

	List<SiteOneContrPrimaryBusinessModel> getChildPrimaryBusinessMap(final String code);
}
