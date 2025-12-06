/**
 *
 */
package com.siteone.facades.exceptions;

import de.hybris.platform.servicelayer.exceptions.SystemException;


/**
 * @author 1099417
 *
 */
public class AddressNotCreatedOrUpdatedInUEException extends SystemException
{
	/**
	 * @param message
	 */
	public AddressNotCreatedOrUpdatedInUEException(final String message)
	{
		super(message);
	}
}
