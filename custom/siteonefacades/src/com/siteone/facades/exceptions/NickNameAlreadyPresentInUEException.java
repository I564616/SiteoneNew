/**
 *
 */
package com.siteone.facades.exceptions;

import de.hybris.platform.servicelayer.exceptions.SystemException;


/**
 * @author KARASAN
 *
 */
public class NickNameAlreadyPresentInUEException extends SystemException
{
	/**
	 * @param message
	 */
	public NickNameAlreadyPresentInUEException(final String message)
	{
		super(message);
	}
}
