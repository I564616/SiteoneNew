/**
 *
 */
package com.siteone.contentsearch.exception;

/**
 *
 * @author 965504
 *
 *         This is the only exception that comes out of tikaengine extension. Throwable argument of the second
 *         constructor will have the actual exception occurred
 *
 */
public class TikaEngineException extends Exception
{
	public TikaEngineException(final String message)
	{
		super(message);
	}

	public TikaEngineException(final String message, final Throwable cause)
	{
		super(message, cause);
	}
}
