/**
 *
 */
package com.siteone.core.url.impl;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commerceservices.url.impl.DefaultCategoryModelUrlResolver;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


/**
 * @author 1091124
 *
 */
public class DefaultSiteOneCategoryModelUrlResolver extends DefaultCategoryModelUrlResolver
{

	private String primaryHierarchy;
	private static final String URL_PATTERN = "/{category-path}/c/{category-code}";
	private static final String HARDSCAPE_URL = "/hardscapes";
	private static final String SHOP_BY_IND_URL = "/";
	private static final String GOLF_URL = "/golfsales";
	private static final String SPORTS_FIELD_URL = "/sportsturf";
	private static final String PEST_MGMT_URL = "/pestmanagement";
	private static final String SNOW_EQUIP_PARTS_URL = "/snow-equipment-and-parts";
	private static final String BULK_PRODUCTS_URL = "/bulk";
	private static final String COMM_IRRIGATION_URL = "https://greentech.siteone.com/";
	private static final String SPEC_URL = "/spec";
	protected static final Logger LOG = Logger.getLogger(DefaultSiteOneCategoryModelUrlResolver.class);

	@Override
	protected String resolveInternal(final CategoryModel source)
	{
		// Work out values

		// Replace pattern values
		String url = getPattern();
		if (source.getCode().equalsIgnoreCase("sh15"))
		{
			url = url.replace(URL_PATTERN, HARDSCAPE_URL);
		}
		else if (source.getCode().equalsIgnoreCase("sh18"))
		{
			url = url.replace(URL_PATTERN, SHOP_BY_IND_URL);
		}
		else if (source.getCode().equalsIgnoreCase("sh1811"))
		{
			url = url.replace(URL_PATTERN, GOLF_URL);
		}
		else if (source.getCode().equalsIgnoreCase("sh1812"))
		{
			url = url.replace(URL_PATTERN, SPORTS_FIELD_URL);
		}
		else if (source.getCode().equalsIgnoreCase("sh1813"))
		{
			url = url.replace(URL_PATTERN, PEST_MGMT_URL);
		}
		else if (source.getCode().equalsIgnoreCase("sh1814"))
		{
			url = url.replace(URL_PATTERN, SNOW_EQUIP_PARTS_URL);
		}
		else if (source.getCode().equalsIgnoreCase("sh1815"))
		{
			url = url.replace(URL_PATTERN, BULK_PRODUCTS_URL);
		}
		else if (source.getCode().equalsIgnoreCase("sh1816"))
		{
			url = COMM_IRRIGATION_URL;
		}
		else if (source.getCode().equalsIgnoreCase("sh1817"))
		{
			url = url.replace(URL_PATTERN, SPEC_URL);
		}
		else
		{

			if (url.contains("{baseSite-uid}"))
			{
				url = url.replace("{baseSite-uid}", getBaseSiteUid());
			}
			if (url.contains("{category-path}"))
			{
				final String categoryPath = buildPathString(getCategoryPath(source));
				url = url.replace("{category-path}", categoryPath);
			}
			if (url.contains("{category-code}"))
			{
				final String categoryCode = urlEncode(source.getCode()).replaceAll("\\+", "%20");
				url = url.replace("{category-code}", categoryCode);
			}
			if (url.contains("{catalog-id}"))
			{
				url = url.replace("{catalog-id}", source.getCatalogVersion().getCatalog().getId());
			}
			if (url.contains("{catalogVersion}"))
			{
				url = url.replace("{catalogVersion}", source.getCatalogVersion().getVersion());
			}
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
		text = StringUtils.stripAccents(text);
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


	@Override
	protected String buildPathString(final List<CategoryModel> path)
	{
		final StringBuilder result = new StringBuilder();
		boolean firstEntryAdded = false;
		int categoryCount = 0;
		for (int i = 0; i < path.size(); i++)
		{
			if (firstEntryAdded)
			{
				result.append('-');
			}
			if (categoryCount <= 2 && !primaryHierarchy.equalsIgnoreCase(path.get(i).getCode()))
			{
				result.append(urlSafe(path.get(i).getName()));
				firstEntryAdded = true;
				categoryCount = categoryCount + 1;
			}
			if (categoryCount == 2)
			{
				break;
			}
		}

		return result.toString();
	}

	/**
	 * @return the primaryHierarchy
	 */
	public String getPrimaryHierarchy()
	{
		return primaryHierarchy;
	}

	/**
	 * @param primaryHierarchy
	 *           the primaryHierarchy to set
	 */
	public void setPrimaryHierarchy(final String primaryHierarchy)
	{
		this.primaryHierarchy = primaryHierarchy;
	}

}
