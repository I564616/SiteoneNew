/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.siteone.utils;

import java.util.regex.Pattern;

/**
 * Filters given string to prevent cross-site scripting
 */
public final class XSSFilterUtil
{

	private XSSFilterUtil()
	{
		//Utility classes, which are a collection of static members, are not meant to be instantiated
	}

	/**
	 *
	 * @param value
	 *           to be sanitized
	 * @return sanitized content
	 */
	public static String filter(final String value)
	{
		if (value == null)
		{
			return null;
		}
		String sanitized = value;
		sanitized = sanitized.replaceAll("eval\\((.*)\\)", "");
		sanitized = sanitized.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		sanitized = sanitized.replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "");
		sanitized = sanitized.replace("<", "&lt;").replace(">", "&gt;");
		sanitized = sanitized.replace("(", "&#40;").replace(")", "&#41;");
		sanitized = sanitized.replaceAll("'", "&#39;");
		sanitized = sanitized.replaceAll("\"", "");
		sanitized = sanitized.replaceAll("`", "");
		sanitized = sanitizeString(sanitized, "alert");
		sanitized = sanitizeString(sanitized, "img");
		sanitized = sanitizeString(sanitized, "autofocus");
		sanitized = sanitizeStringWithRegex(sanitized, "onerror(.*?)=");
		sanitized = sanitizeStringWithRegex(sanitized, "onfocus(.*?)=");
		sanitized = sanitizeStringWithRegex(sanitized, "onclick(.*?)=");
		sanitized = sanitizeStringWithRegex(sanitized, "src[\\r\\n]*=[\\r\\n]*\\\\\\'(.*?)\\\\\\'");
		return sanitized;
	}

	protected static String sanitizeString(String value, final String pattern)
	{
		while (value.contains(pattern))
		{
			value = value.replace(pattern, "");
		}
		return value;
	}

	protected static String sanitizeStringWithRegex(String value, final String pattern)
	{
		final Pattern scriptPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		while (scriptPattern.matcher(value).find())
		{
			value = scriptPattern.matcher(value).replaceAll("");
		}
		return value;
	}
}
