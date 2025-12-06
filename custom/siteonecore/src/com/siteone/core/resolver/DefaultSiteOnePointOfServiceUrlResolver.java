/**
 *
 */
package com.siteone.core.resolver;

import de.hybris.platform.commerceservices.url.impl.DefaultPointOfServiceUrlResolver;
import de.hybris.platform.storelocator.model.PointOfServiceModel;


/**
 * @author 1124932
 *
 */
public class DefaultSiteOnePointOfServiceUrlResolver extends DefaultPointOfServiceUrlResolver
{
	@Override
	protected String resolveInternal(final PointOfServiceModel source)
	{
		// /store/{store-id}

		// Replace pattern values
		String url = getPattern();

		if (url.contains("{store-id}"))
		{
			url = url.replace("{store-id}", urlEncode(source.getStoreId()));
		}

		return url;
	}

}
