/**
 *
 */
package com.siteone.facades.exceptions;

import de.hybris.platform.servicelayer.exceptions.SystemException;


/**
 * @author 1099417
 *
 */
public class RecentlyUsedPasswordException extends SystemException
{
	public RecentlyUsedPasswordException(final String message)
	{
		super(message);
	}

	public RecentlyUsedPasswordException(final Throwable cause)
	{
		super(cause);
	}

	public RecentlyUsedPasswordException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

}
