/**
 *
 */
package com.siteone.facades.exceptions;

import de.hybris.platform.servicelayer.exceptions.SystemException;


/**
 * @author 1099417
 *
 */
public class AddressNotRemovedInUEException extends SystemException
{
	/**
	 * @param message
	 */
	public AddressNotRemovedInUEException(final String message)
	{
		super(message);
	}
}
