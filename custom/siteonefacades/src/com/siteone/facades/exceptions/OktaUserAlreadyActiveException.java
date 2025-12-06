/**
 *
 */
package com.siteone.facades.exceptions;

import de.hybris.platform.servicelayer.exceptions.SystemException;


/**
 * @author 1099417
 *
 */
public class OktaUserAlreadyActiveException extends SystemException
{

	public OktaUserAlreadyActiveException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public OktaUserAlreadyActiveException(final String message)
	{
		super(message);
	}

	public OktaUserAlreadyActiveException(final Throwable cause)
	{
		super(cause);
	}

}
