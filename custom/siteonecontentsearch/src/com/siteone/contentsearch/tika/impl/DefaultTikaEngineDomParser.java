/**
 *
 */
package com.siteone.contentsearch.tika.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.log4j.Logger;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xml.sax.SAXException;

import com.siteone.contentsearch.exception.TikaEngineException;
import com.siteone.contentsearch.tika.TikaEngineDomParser;



/**
 * @author Abdul Rahman Sheikh M
 *
 *         Dom parser for site urls. As of now parses only httpget urls.
 *
 */
public class DefaultTikaEngineDomParser implements TikaEngineDomParser
{

	private static final Logger LOG = Logger.getLogger(DefaultTikaEngineDomParser.class);

	private static final String COOKIE_ID = "JSESSIONID";

	/**
	 * @param String
	 * @throws TikaEngineException
	 * @return BodyContentHandler
	 *
	 *         This method get the URL of the web page as argument, and parses it using tika parser then return the DOM of
	 *         the response page in the object BodyContentHandler.
	 *
	 */
	@Override
	public BodyContentHandler parseDom(final String url) throws TikaEngineException
	{
		try
		{
			final HttpGet httpget = new HttpGet(url);
			HttpEntity entity = null;

			final CloseableHttpClient client = HttpClients.custom().build();
			HttpResponse response;
			response = client.execute(httpget);
			entity = response.getEntity();
			if (entity != null)
			{
				InputStream instream;
				instream = entity.getContent();
				final BodyContentHandler handler = new BodyContentHandler();
				final Metadata metadata = new Metadata();
				final Parser parser = new AutoDetectParser();
				parser.parse(instream, handler, metadata, new ParseContext());
				return handler;
			}
			else
			{
				throw new TikaEngineException("No response found for the url --> " + url);
			}
		}
		catch (final IOException ioe)
		{
			throw new TikaEngineException("Failed to load url --> " + url, ioe);
		}
		catch (final SAXException saxe)
		{
			throw new TikaEngineException("Failed to load url --> " + url, saxe);
		}
		catch (final TikaException te)
		{
			throw new TikaEngineException("Failed to load url --> " + url, te);
		}
		catch (final IllegalStateException ise)
		{
			throw new TikaEngineException("Failed to load url --> " + url, ise);
		}
		catch (final Exception e)
		{
			throw new TikaEngineException("Failed to load url --> " + url, e);
		}
	}

	class ProxyAuthenticator extends Authenticator
	{

		private final String user, password;

		public ProxyAuthenticator(final String user, final String password)
		{
			this.user = user;
			this.password = password;
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication()
		{
			return new PasswordAuthentication(user, password.toCharArray());
		}
	}


	@Override
	public Map<String, String> getSiteCookiesToCrawl(final String url) throws TikaEngineException
	{
		
		try
		{
			final Connection.Response conn = Jsoup.connect(url).timeout(30000).ignoreHttpErrors(true).execute();

			final Map<String, String> cookies = conn.cookies();

			return cookies;
		}
		catch (final IOException e)
		{
			LOG.error(e.getMessage(), e);
			throw new TikaEngineException("Failed to get site cookies --> ");
		}
	}

	/**
	 * @param String
	 * @throws TikaEngineException
	 * @return BodyContentHandler
	 *
	 *         This method get the URL of the web page as argument, and parses it using tika parser then return the DOM of
	 *         the response page in the object BodyContentHandler.
	 *
	 */
	@Override
	public Document parseDomUsingJSoup(final String url, final Map<String, String> cookies) throws TikaEngineException
	{
		try
		{
			

			Document doc = null;
			Response res = null;

			if (cookies != null)
			{
				res = Jsoup.connect(url).cookie(COOKIE_ID, cookies.get(COOKIE_ID)).timeout(30000).ignoreHttpErrors(true).execute();
			}
			else
			{
				res = Jsoup.connect(url).timeout(30000).ignoreHttpErrors(true).execute();
			}
			if (res.statusCode() == 200)
			{
				LOG.debug("Crawled successfully! Url -->" + url);
				doc = res.parse();
				return doc;
			}
			else
			{
				LOG.error("HTTP error code " + res.statusCode() + " received for url -->" + url);
				return null;
			}

		}
		catch (final IOException ioe)
		{
			throw new TikaEngineException("Failed to load url --> " + url, ioe);
		}
		catch (final IllegalStateException ise)
		{
			throw new TikaEngineException("Failed to load url --> " + url, ise);
		}
		catch (final Exception e)
		{
			throw new TikaEngineException("Failed to load url --> " + url, e);
		}
	}

}
