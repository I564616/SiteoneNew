/**
 *
 */
package com.siteone.facades.exceptions;

import de.hybris.platform.servicelayer.exceptions.SystemException;


/**
 * @author Karasan
 *
 */
public class ContactAlreadyPresentInBoomiException extends SystemException
{
	/**
	 * @param message
	 */
	public ContactAlreadyPresentInBoomiException(final String message)
	{
		super(message);
	}
}
