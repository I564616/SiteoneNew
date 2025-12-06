/**
 *
 */
package com.siteone.facades.exceptions;

import de.hybris.platform.servicelayer.exceptions.SystemException;


/**
 * @author 1099417
 *
 */
public class ContactNotEnabledOrDisabledInUEException extends SystemException
{
	/**
	 * @param message
	 */
	public ContactNotEnabledOrDisabledInUEException(final String message)
	{
		super(message);
	}
}
