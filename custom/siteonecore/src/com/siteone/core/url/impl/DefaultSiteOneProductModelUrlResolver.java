/**
 *
 */
package com.siteone.core.url.impl;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.url.impl.DefaultProductModelUrlResolver;
import de.hybris.platform.core.model.product.ProductModel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


/**
 * @author 1091124
 *
 */
public class DefaultSiteOneProductModelUrlResolver extends DefaultProductModelUrlResolver
{
	private String primaryHierarchy;

	protected static final Logger LOG = Logger.getLogger(DefaultSiteOneProductModelUrlResolver.class);

	@Override
	protected String resolveInternal(final ProductModel source)
	{
		final ProductModel baseProduct = getProductAndCategoryHelper().getBaseProduct(source);
		final BaseSiteModel currentBaseSite = getBaseSiteService().getCurrentBaseSite();

		String url = getPattern();

		if (currentBaseSite != null && url.contains("{baseSite-uid}"))
		{
			url = url.replace("{baseSite-uid}", currentBaseSite.getUid());
		}
		if (url.contains("{item-number}"))
		{
			url = url.replace("{item-number}", urlSafe(source.getItemNumber()));
		}
		if (url.contains("{product-name}"))
		{
			url = url.replace("{product-name}",
					StringUtils.substring(urlSafe(source.getProductShortDesc()), 0, source.getProductShortDesc().length()));
		}
		if (url.contains("{product-code}"))
		{
			url = url.replace("{product-code}", source.getCode());
		}

		return url.toLowerCase();
	}


	@Override
	protected String urlSafe(String text)
	{
		if (text == null || text.isEmpty())
		{
			return "";
		}

		text = text.replaceAll("[^\\w-\\s]+", "");
		text = text.replaceAll("\\s+", " ");
		text = text.replaceAll("_", "");
		String encodedText;
		try
		{
			encodedText = URLEncoder.encode(text, "utf-8");
		}
		catch (final UnsupportedEncodingException encodingException)
		{
			LOG.info("Un Supported Encoding exception occured" + encodingException);
			encodedText = text;
		}

		// Cleanup the text
		String cleanedText = encodedText;
		cleanedText = cleanedText.replaceAll("%2F", "/");
		cleanedText = cleanedText.replaceAll("[^%A-Za-z0-9\\-]+", "-");
		cleanedText = cleanedText.replaceAll("--+", "-");
		return cleanedText;
	}


	public String getPrimaryHierarchy()
	{
		return primaryHierarchy;
	}

	public void setPrimaryHierarchy(final String primaryHierarchy)
	{
		this.primaryHierarchy = primaryHierarchy;
	}
}
