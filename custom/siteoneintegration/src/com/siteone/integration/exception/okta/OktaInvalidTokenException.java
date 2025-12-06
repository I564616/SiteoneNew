package com.siteone.integration.exception.okta;

import de.hybris.platform.servicelayer.exceptions.SystemException;

public class OktaInvalidTokenException extends SystemException {
	
	public OktaInvalidTokenException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public OktaInvalidTokenException(final String message)
	{
		super(message);
	}

	public OktaInvalidTokenException(final Throwable cause)
	{
		super(cause);
	}
}
