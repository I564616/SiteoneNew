/**
 *
 */
package com.siteone.core.unit.dao;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commerceservices.search.dao.PagedGenericDao;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;


/**
 * @author 1190626
 *
 */
public interface SiteOnePagedB2BUnitDao<M> extends PagedGenericDao<M>
{
	SearchPageData<B2BUnitModel> findPagedUnits(String sortCode, PageableData pageableData, B2BUnitModel parent,
			String searchParam);


}
