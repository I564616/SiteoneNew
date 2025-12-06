/**
 *
 */
package com.siteone.core.exceptions;

import de.hybris.platform.servicelayer.exceptions.SystemException;


/**
 * @author 1099417
 *
 */
public class OktaRecoveryTokenNotFoundException extends SystemException
{

	public OktaRecoveryTokenNotFoundException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public OktaRecoveryTokenNotFoundException(final String message)
	{
		super(message);
	}

	public OktaRecoveryTokenNotFoundException(final Throwable cause)
	{
		super(cause);
	}
}
