/**
 *
 */
package com.siteone.facades.exceptions;

import de.hybris.platform.servicelayer.exceptions.SystemException;


/**
 * @author RSUBATHR
 *
 */
public class EwalletNotCreatedOrUpdatedInUEException extends SystemException
{
	/**
	 * @param message
	 */
	public EwalletNotCreatedOrUpdatedInUEException(final String message)
	{
		super(message);
	}
}

