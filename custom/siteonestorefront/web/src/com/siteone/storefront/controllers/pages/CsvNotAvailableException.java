/**
 * 
 */
package com.siteone.storefront.controllers.pages;

import de.hybris.platform.servicelayer.exceptions.SystemException;
/**
 * @author NJoshi
 *
 */
public class CsvNotAvailableException extends SystemException
{
	public CsvNotAvailableException(final String message)
	{
		super(message);
	}
}
