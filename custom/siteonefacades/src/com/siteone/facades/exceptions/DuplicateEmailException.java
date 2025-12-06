/**
 *
 */
package com.siteone.facades.exceptions;

import de.hybris.platform.servicelayer.exceptions.SystemException;


/**
 * @author 1085284
 *
 */
public class DuplicateEmailException extends SystemException
{

	/**
	 * @param message
	 */
	public DuplicateEmailException(final String message)
	{
		super(message);

	}

}
