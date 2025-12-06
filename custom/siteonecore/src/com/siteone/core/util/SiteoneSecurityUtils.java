/**
 *
 */
package com.siteone.core.util;

import jakarta.validation.ValidationException;


/**
 * @author Smondal
 *
 */
public final class SiteoneSecurityUtils
{
	/* Private constructor for avoiding instantiation. */

	private SiteoneSecurityUtils()
	{
		//empty
	}

	public static String buildValidAvatarPath(final String path) throws ValidationException
	{
		if (!path.matches("^(.*/)?(?:$|(.+?)(?:(\\.[^.]*$)|$))"))
		{
			throw new ValidationException("Invalid path");
		}
		final String finalPath = path.toString();

		return finalPath;
	}
}
