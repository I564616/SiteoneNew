/**
 *
 */
package com.siteone.facades.exceptions;

import de.hybris.platform.servicelayer.exceptions.SystemException;


/**
 * @author 1099417
 *
 */
public class ContactNotCreatedOrUpdatedInOktaException extends SystemException
{
	/**
	 * @param message
	 */
	public ContactNotCreatedOrUpdatedInOktaException(final String message)
	{
		super(message);
	}
}
