/**
 *
 */
package com.siteone.facades.exceptions;

import de.hybris.platform.servicelayer.exceptions.SystemException;


/**
 * @author KARASAN
 *
 */
public class CardAlreadyPresentInUEException extends SystemException
{
	/**
	 * @param message
	 */
	public CardAlreadyPresentInUEException(final String message)
	{
		super(message);
	}
}
