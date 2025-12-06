package com.siteone.integration.exception.okta;

import de.hybris.platform.servicelayer.exceptions.SystemException;

public class OktaUnknownUserException extends SystemException {
	
	public OktaUnknownUserException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public OktaUnknownUserException(final String message)
	{
		super(message);
	}

	public OktaUnknownUserException(final Throwable cause)
	{
		super(cause);
	}

 

}
