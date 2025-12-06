package com.siteone.core.exceptions;

import de.hybris.platform.servicelayer.exceptions.SystemException;

public class OktaInvalidUserStatusException extends SystemException{
	
	public OktaInvalidUserStatusException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public OktaInvalidUserStatusException(final String message)
	{
		super(message);
	}

	public OktaInvalidUserStatusException(final Throwable cause)
	{
		super(cause);
	}
}
