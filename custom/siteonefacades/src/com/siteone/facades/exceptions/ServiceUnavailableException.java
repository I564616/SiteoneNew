/**
 *
 */
package com.siteone.facades.exceptions;

import de.hybris.platform.servicelayer.exceptions.SystemException;


/**
 * @author 1099417
 *
 */
public class ServiceUnavailableException extends SystemException
{

	/**
	 * @param message
	 */
	public ServiceUnavailableException(final String message)
	{
		super(message);
	}

}
