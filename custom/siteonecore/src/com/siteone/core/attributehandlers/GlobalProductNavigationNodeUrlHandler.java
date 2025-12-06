/**
 *
 */
package com.siteone.core.attributehandlers;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commerceservices.url.UrlResolver;
import de.hybris.platform.servicelayer.model.attribute.DynamicAttributeHandler;

import jakarta.annotation.Resource;

import com.siteone.core.model.GlobalProductNavigationNodeModel;


/**
 * + * @author 1129929
 *
 */
public class GlobalProductNavigationNodeUrlHandler implements DynamicAttributeHandler<String, GlobalProductNavigationNodeModel>
{

	@Resource(name = "categoryModelUrlResolver")
	private UrlResolver<CategoryModel> categoryModelUrlResolver;

	@Override
	public String get(final GlobalProductNavigationNodeModel node)
	{

		if (node.getCategory() != null)
		{
			return getCategoryModelUrlResolver().resolve(node.getCategory());
		}
		else
		{
			return null;
		}

	}

	@Override
	public void set(final GlobalProductNavigationNodeModel paramMODEL, final String paramVALUE)
	{
		// Ignore
	}

	public UrlResolver<CategoryModel> getCategoryModelUrlResolver()
	{
		return categoryModelUrlResolver;
	}

}