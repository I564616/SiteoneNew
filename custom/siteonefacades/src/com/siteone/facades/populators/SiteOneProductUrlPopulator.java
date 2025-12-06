/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.product.converters.populator.ProductUrlPopulator;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;


/**
 * @author 1124932
 *
 */
public class SiteOneProductUrlPopulator extends ProductUrlPopulator
{

	@Override
	public void populate(final ProductModel source, final ProductData target)
	{
		super.populate(source, target);
		target.setName(source.getProductShortDesc().trim());
	}
}
