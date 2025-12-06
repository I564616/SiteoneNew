/**
 *
 */
package com.siteone.facades.exceptions;

import de.hybris.platform.servicelayer.exceptions.SystemException;


/**
 * @author RSUBATHR
 *
 */
public class EwalletNotCreatedOrUpdatedInCayanException extends SystemException
{
	/**
	 * @param message
	 */
	public EwalletNotCreatedOrUpdatedInCayanException(final String message)
	{
		super(message);
	}
}

