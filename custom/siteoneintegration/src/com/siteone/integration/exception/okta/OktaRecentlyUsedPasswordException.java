package com.siteone.integration.exception.okta;

import de.hybris.platform.servicelayer.exceptions.SystemException;

public class OktaRecentlyUsedPasswordException extends SystemException{ 

	public OktaRecentlyUsedPasswordException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public OktaRecentlyUsedPasswordException(final String message)
	{
		super(message);
	}

	public OktaRecentlyUsedPasswordException(final Throwable cause)
	{
		super(cause);
	}

	
}
