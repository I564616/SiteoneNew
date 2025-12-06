/**
 *
 */
package com.siteone.core.recaptcha.util;


import de.hybris.platform.util.Config;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author BS
 *
 */

public class VerifyRecaptchaUtils
{

	private static final Logger LOG = Logger.getLogger(VerifyRecaptchaUtils.class);
	private static final String RECAPTCHA_SECRET_KEY_PROPERTY = "recaptcha.privatekey";
	private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
	@Resource(name = "restTemplate")
	private RestTemplate restTemplate;

	public boolean checkAnswer(final String recaptchaResponse)
	{
		final String secret = Config.getString(RECAPTCHA_SECRET_KEY_PROPERTY, null);
		final MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("secret", secret);
		map.add("response", recaptchaResponse);

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		final HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		try
		{
			final String response = restTemplate.postForObject(RECAPTCHA_VERIFY_URL, requestEntity, String.class);
			final JSONObject jsonResponse = new JSONObject(response);
			return jsonResponse.getBoolean("success");
		}
		catch (final Exception e)
		{
			LOG.error("Rest client exception occured while connecting to recaptch", e);
			return false;
		}

	}

}