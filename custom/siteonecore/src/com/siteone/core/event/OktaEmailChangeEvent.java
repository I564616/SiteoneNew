/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;


/**
 * @author ASaha
 *
 */
public class OktaEmailChangeEvent extends AbstractCommerceUserEvent<BaseSiteModel>
{
	private String token;

	public OktaEmailChangeEvent()
	{
		super();
	}

	public OktaEmailChangeEvent(final String token)
	{
		super();
		this.token = token;
	}


	/**
	 * @return the token
	 */
	public String getToken()
	{
		return token;
	}

	/**
	 * @param token
	 *           the token to set
	 */
	public void setToken(final String token)
	{
		this.token = token;
	}


}