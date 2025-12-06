/**
 *
 */
package com.siteone.facades.exceptions;

import de.hybris.platform.servicelayer.exceptions.SystemException;


/**
 * @author 1099417
 *
 */
public class ContactNotCreatedOrUpdatedInUEException extends SystemException
{
	/**
	 * @param message
	 */
	public ContactNotCreatedOrUpdatedInUEException(final String message)
	{
		super(message);
	}
}
