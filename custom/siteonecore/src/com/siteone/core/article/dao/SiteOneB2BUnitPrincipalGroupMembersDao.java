/**
 *
 */
package com.siteone.core.article.dao;

import de.hybris.platform.commerceservices.model.OrgUnitModel;
import de.hybris.platform.core.model.security.PrincipalModel;
import de.hybris.platform.core.model.user.UserGroupModel;

import java.util.List;


/**
 * @author SMondal
 *
 */
public interface SiteOneB2BUnitPrincipalGroupMembersDao
{
	public <T extends PrincipalModel> List<T> findMembersByType(final UserGroupModel parent, final Class<T> memberType,
			final String searchTerm, final String searchType);

	public OrgUnitModel getDivisionForUnit(String unitId);
}
