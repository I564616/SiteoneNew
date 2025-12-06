package com.siteone.core.batch.translator;


import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.impex.jalo.header.StandardColumnDescriptor;
import de.hybris.platform.impex.jalo.translators.AbstractValueTranslator;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.util.SiteOneDivisionUtil;


public class StocklevelDivisionTranslator extends AbstractValueTranslator
{
	private static final Logger LOG = Logger.getLogger(StocklevelDivisionTranslator.class);
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
		final String[] splitValues = cellValue.split(":");
		final String productCode = splitValues[0];
		final String division = splitValues[1];
		ProductModel productModel = null;
		if (siteOneDivisionUtil.isUSContext(division))
		{
			final CatalogVersionModel catalogVersion = catalogVersionService.getCatalogVersion(SiteoneCoreConstants.CATALOG_US, SiteoneCoreConstants.CATALOG_VERSION);
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
		else if (siteOneDivisionUtil.isCanadaContext(division))
		{
			final CatalogVersionModel catalogVersion = catalogVersionService.getCatalogVersion(SiteoneCoreConstants.CATALOG_CA, SiteoneCoreConstants.CATALOG_VERSION);
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
