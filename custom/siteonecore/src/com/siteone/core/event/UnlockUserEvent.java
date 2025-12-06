/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;


/**
 * @author 1099417
 *
 */
public class UnlockUserEvent extends AbstractCommerceUserEvent<BaseSiteModel>
{
	private String token;

	private long expirationTimeInSeconds;

	/**
	 * Default constructor
	 */
	public UnlockUserEvent()
	{
		super();
	}

	/**
	 * Parameterized Constructor
	 *
	 * @param token
	 */
	public UnlockUserEvent(final String token, final long expirationTimeInSeconds)
	{
		super();
		this.token = token;
		this.expirationTimeInSeconds = expirationTimeInSeconds;
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

	/**
	 * @return the expirationTimeInSeconds
	 */
	public long getExpirationTimeInSeconds()
	{
		return expirationTimeInSeconds;
	}

	/**
	 * @param expirationTimeInSeconds
	 *           the expirationTimeInSeconds to set
	 */
	public void setExpirationTimeInSeconds(final long expirationTimeInSeconds)
	{
		this.expirationTimeInSeconds = expirationTimeInSeconds;
	}


}
