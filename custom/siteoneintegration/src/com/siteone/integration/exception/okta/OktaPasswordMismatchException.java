package com.siteone.integration.exception.okta;

import de.hybris.platform.servicelayer.exceptions.SystemException;

public class OktaPasswordMismatchException extends SystemException{

	public OktaPasswordMismatchException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public OktaPasswordMismatchException(final String message)
	{
		super(message);
	}

	public OktaPasswordMismatchException(final Throwable cause)
	{
		super(cause);
	}

}
