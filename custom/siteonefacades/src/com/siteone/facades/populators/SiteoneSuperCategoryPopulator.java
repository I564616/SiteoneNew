/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.product.converters.populator.CategoryPopulator;
import de.hybris.platform.commercefacades.product.data.CategoryData;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.Assert;


/**
 * @author 1003567
 *
 */
public class SiteoneSuperCategoryPopulator extends CategoryPopulator
{
	@Override
	public void populate(final CategoryModel source, final CategoryData target)
	{
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");
		super.populate(source, target);
		if (CollectionUtils.isNotEmpty(source.getSupercategories()))
		{
			target.setParentCategoryName(source.getSupercategories().get(0).getName());
		}
	}

}
