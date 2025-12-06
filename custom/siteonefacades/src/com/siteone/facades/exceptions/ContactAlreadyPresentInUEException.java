/**
 *
 */
package com.siteone.facades.exceptions;

import de.hybris.platform.servicelayer.exceptions.SystemException;


/**
 * @author 1099417
 *
 */
public class ContactAlreadyPresentInUEException extends SystemException
{
	/**
	 * @param message
	 */
	public ContactAlreadyPresentInUEException(final String message)
	{
		super(message);
	}
}
