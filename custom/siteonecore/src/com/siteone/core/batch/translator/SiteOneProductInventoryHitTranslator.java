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

import com.siteone.core.util.SiteOneDivisionUtil;


/**
 * @author prelango
 *
 */
public class SiteOneProductInventoryHitTranslator extends AbstractValueTranslator
{
	private static final Logger LOG = Logger.getLogger(SiteOneProductInventoryHitTranslator.class);
	private static final String NURSERY = "Nursery";
	private ProductService productService;
	private CatalogVersionService catalogVersionService;
	private SiteOneDivisionUtil siteOneDivisionUtil;

	@Override
	public void init(final StandardColumnDescriptor descriptor)
	{
		productService = (ProductService) Registry.getApplicationContext().getBean("productService");
		catalogVersionService = (CatalogVersionService) Registry.getApplicationContext().getBean("catalogVersionService");
		siteOneDivisionUtil = (SiteOneDivisionUtil) Registry.getApplicationContext().getBean("siteOneDivisionUtil");
	}

	@Override
	public Object importValue(final String cellValue, final Item orderEntry) throws JaloInvalidParameterException
	{
		final String[] splitValues = cellValue.split("_");
		final String productCode = splitValues[0];
		final int inventoryHit = Integer.parseInt(splitValues[1]);
		final String division = splitValues[2];
		ProductModel productModel = null;
		final CatalogVersionModel catalogVersion = catalogVersionService.getCatalogVersion("siteoneProductCatalog",
				CatalogManager.ONLINE_VERSION);
		final CatalogVersionModel catalogVersionCA = catalogVersionService.getCatalogVersion("siteoneCAProductCatalog",
				CatalogManager.ONLINE_VERSION);
		try
		{
			if (siteOneDivisionUtil.isUSContext(division))
			{
				productModel = productService.getProductForCode(catalogVersion, productCode);
			}
			else
			{
				productModel = productService.getProductForCode(catalogVersionCA, productCode);
			}
		}
		catch (final UnknownIdentifierException unknownIdentifierException)
		{
			LOG.info("Product is not available .. in exception block" + cellValue);
		}
		if (null != productModel && productModel.getProductType().equalsIgnoreCase(NURSERY))
		{
			return 0;
		}
		return inventoryHit;
	}

	@Override
	public String exportValue(final Object arg0) throws JaloInvalidParameterException
	{
		return null;
	}
}
