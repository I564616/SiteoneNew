/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.commerceservices.event.ForgottenPwdEvent;


/**
 * @author 1099417
 *
 */
public class SiteOneForgottenPwdEvent extends ForgottenPwdEvent
{

	private long expirationTimeInSeconds;



	public SiteOneForgottenPwdEvent(final String token, final long expirationTimeInSeconds)
	{
		super();
		this.expirationTimeInSeconds = expirationTimeInSeconds;
		setToken(token);
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
