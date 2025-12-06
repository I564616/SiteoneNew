/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.siteone.storefront.security.impl;

import de.hybris.platform.acceleratorstorefrontcommons.security.GUIDCookieStrategy;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.web.util.CookieGenerator;

import com.siteone.storefront.interceptors.beforecontroller.RequireHardLoginBeforeControllerHandler;


/**
 * Default implementation of {@link GUIDCookieStrategy}
 */
public class DefaultGUIDCookieStrategy implements GUIDCookieStrategy
{
	private static final Logger LOG = Logger.getLogger(DefaultGUIDCookieStrategy.class);

	private final SecureRandom random;
	private final MessageDigest sha;

	private CookieGenerator cookieGenerator;

	public DefaultGUIDCookieStrategy() throws NoSuchAlgorithmException
	{
		random = SecureRandom.getInstance("SHA1PRNG");
		sha = MessageDigest.getInstance("SHA-256");
		Assert.notNull(random, "must not be null");
		Assert.notNull(sha, "must not be null");
	}

	@Override
	public void setCookie(final HttpServletRequest request, final HttpServletResponse response)
	{
		if (!request.isSecure())
		{
			// We must not generate the cookie for insecure requests, otherwise there is not point doing this at all
			throw new IllegalStateException("Cannot set GUIDCookie on an insecure request!");
		}

		final String guid = createGUID();

		getCookieGenerator().addCookie(response, guid);
		request.getSession().setAttribute(RequireHardLoginBeforeControllerHandler.SECURE_GUID_SESSION_KEY, guid);

		if (LOG.isInfoEnabled())
		{
			LOG.info("Setting guid cookie and session attribute: " + guid);
		}
	}

	@Override
	public void deleteCookie(final HttpServletRequest request, final HttpServletResponse response)
	{
		if (!request.isSecure())
		{
			LOG.error("Cannot remove secure GUIDCookie during an insecure request. I should have been called from a secure page.");
		}
		else
		{
			// Its a secure page, we can delete the cookie
			getCookieGenerator().removeCookie(response);
		}
	}

	protected String createGUID()
	{
		final String randomNum = String.valueOf(getRandom().nextInt());
		final byte[] result = getSha().digest(randomNum.getBytes());
		final String encryptGuid = String.valueOf(Hex.encodeHex(result));
		String generatedSecuredGUIDHash = "";
		try
		{
			generatedSecuredGUIDHash = generateStorngGUIDHash(encryptGuid);
			if (StringUtils.isEmpty(generatedSecuredGUIDHash))
			{
				throw new IllegalStateException("generatedSecuredGUIDHash empty for the request!");
			}
		}
		catch (final NoSuchAlgorithmException noSuchAlgorithmException)
		{
			LOG.error(noSuchAlgorithmException.getMessage(), noSuchAlgorithmException);
		}
		catch (final InvalidKeySpecException invalidKeySpecException)
		{
			LOG.error(invalidKeySpecException.getMessage(), invalidKeySpecException);
		}
		return generatedSecuredGUIDHash;
	}

	protected CookieGenerator getCookieGenerator()
	{
		return cookieGenerator;
	}

	/**
	 * @param cookieGenerator
	 *           the cookieGenerator to set
	 */
	public void setCookieGenerator(final CookieGenerator cookieGenerator)
	{
		this.cookieGenerator = cookieGenerator;
	}


	protected SecureRandom getRandom()
	{
		return random;
	}

	protected MessageDigest getSha()
	{
		return sha;
	}

	private static String generateStorngGUIDHash(final String encryptGuid) throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		final int iterations = 1000;
		final char[] chars = encryptGuid.toCharArray();
		final byte[] salt = getSalt();

		final PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
		final SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		final byte[] hash = skf.generateSecret(spec).getEncoded();
		return iterations + ":" + toHex(salt) + ":" + toHex(hash);
	}

	private static byte[] getSalt() throws NoSuchAlgorithmException
	{
		final SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		final byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}

	private static String toHex(final byte[] array) throws NoSuchAlgorithmException
	{
		final BigInteger bi = new BigInteger(1, array);
		final String hex = bi.toString(16);
		final int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0)
		{
			return String.format("%0" + paddingLength + "d", 0) + hex;
		}
		else
		{
			return hex;
		}
	}

}
