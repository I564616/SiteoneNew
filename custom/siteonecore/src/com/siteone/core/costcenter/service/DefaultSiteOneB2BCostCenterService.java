/**
 *
 */
package com.siteone.core.costcenter.service;

import de.hybris.platform.b2b.services.impl.DefaultB2BCostCenterService;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.user.UserModel;

import java.util.Collections;
import java.util.Set;


/**
 * @author PElango
 *
 */
public class DefaultSiteOneB2BCostCenterService extends DefaultB2BCostCenterService
{

	@Override
	public Set<CurrencyModel> getAvailableCurrencies(final UserModel user)
	{
		return Collections.emptySet();
	}
}
