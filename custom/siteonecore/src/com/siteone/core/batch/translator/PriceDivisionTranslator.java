/**
 *  1778849
 */
package com.siteone.core.batch.translator;


import java.util.Map;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.jalo.CatalogManager;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.impex.jalo.header.StandardColumnDescriptor;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.impex.jalo.translators.AbstractValueTranslator;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;



public class PriceDivisionTranslator extends AbstractValueTranslator
{
	private static final Logger LOG = Logger.getLogger(PriceDivisionTranslator.class);
	private static final String CATALOG_VERSION = "Online";
	private static final String CATALOG_US = "siteoneProductCatalog";
	private static final String CATALOG_CA = "siteoneCAProductCatalog";
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
		final String[] splitValues = cellValue.split(":");
		final String productCode = splitValues[0];
		final String division = splitValues[1];
		ProductModel productModel = null;
		if ("1".equalsIgnoreCase(division) || "US".equalsIgnoreCase(division) || "JDL".equalsIgnoreCase(division))
		{
			final CatalogVersionModel catalogVersion = catalogVersionService.getCatalogVersion(CATALOG_US, CATALOG_VERSION);
			try
			{
				productModel = productService.getProductForCode(catalogVersion, productCode);
			}
			catch (final UnknownIdentifierException unknownIdentifierException)
			{
				LOG.error(unknownIdentifierException);
			}
			
			LOG.info("US product catalog");
			if (null != productModel)
			{
				return productCode;
			}
			else
			{
				return StringUtils.EMPTY;
			}
		}
		else if ("2".equalsIgnoreCase(division) || "CA".equalsIgnoreCase(division)
				|| "JDLC".equalsIgnoreCase(division))
		{
			final CatalogVersionModel catalogVersion = catalogVersionService.getCatalogVersion(CATALOG_CA, CATALOG_VERSION);
			try
			{
				productModel = productService.getProductForCode(catalogVersion, productCode);
			}
			catch (final UnknownIdentifierException unknownIdentifierException)
			{
				LOG.error(unknownIdentifierException);
			}
			
			LOG.info("CA product catalog");
			if (null != productModel)
			{
				return productCode;
			}
			else
			{
				return StringUtils.EMPTY;
			}
		}
		else 
		{
			return StringUtils.EMPTY;
		}
	}

	@Override
	public String exportValue(final Object arg0) throws JaloInvalidParameterException
	{
		return null;
	}
}
