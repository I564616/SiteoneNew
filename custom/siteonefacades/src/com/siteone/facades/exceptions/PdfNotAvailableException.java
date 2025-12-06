/**
 *
 */
package com.siteone.facades.exceptions;

import de.hybris.platform.servicelayer.exceptions.SystemException;


/**
 * @author 1099417
 *
 */
public class PdfNotAvailableException extends SystemException
{

	/**
	 * @param message
	 */
	public PdfNotAvailableException(final String message)
	{
		super(message);
	}

}
