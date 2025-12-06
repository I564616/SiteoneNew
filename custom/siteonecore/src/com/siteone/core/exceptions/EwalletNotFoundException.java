/**
 *
 */
package com.siteone.core.exceptions;

import de.hybris.platform.servicelayer.exceptions.SystemException;


/**
 * @author RSUBATHR
 *
 */
public class EwalletNotFoundException extends SystemException
{
	/**
	 * @param message
	 */
	public EwalletNotFoundException(final String message)
	{
		super(message);
	}
}

