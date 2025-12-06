/**
 *
 */
package com.siteone.core.batch.translator;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.jalo.CatalogManager;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.impex.jalo.header.StandardColumnDescriptor;
import de.hybris.platform.impex.jalo.translators.AbstractValueTranslator;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;

import org.apache.log4j.Logger;


/**
 * @author 1085284
 *
 */
public class IsStoreProductTranslator extends AbstractValueTranslator
{
	private static final Logger LOG = Logger.getLogger(IsStoreProductTranslator.class);
	private ProductService productService;
	private CatalogVersionService catalogVersionService;

	@Override
	public void init(final StandardColumnDescriptor descriptor)
	{
		productService = (ProductService) Registry.getApplicationContext().getBean("productService");
		catalogVersionService = (CatalogVersionService) Registry.getApplicationContext().getBean("catalogVersionService");
	}

	@Override
	public Object importValue(final String cellValue, final Item orderEntry) throws JaloInvalidParameterException
	{
		ProductModel productModel = null;
		final CatalogVersionModel catalogVersion = catalogVersionService.getCatalogVersion("siteoneProductCatalog",
				CatalogManager.ONLINE_VERSION);
		try
		{
			productModel = productService.getProductForCode(catalogVersion, cellValue);
		}
		catch (final UnknownIdentifierException unknownIdentifierException)
		{
			LOG.error(unknownIdentifierException);
		}
		if (null == productModel)
		{
			return true;
		}
		return false;
	}


	@Override
	public String exportValue(final Object arg0) throws JaloInvalidParameterException
	{

		return null;
	}


}
