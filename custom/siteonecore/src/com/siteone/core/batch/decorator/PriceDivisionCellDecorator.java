/**
 *
 */
package com.siteone.core.batch.decorator;

import de.hybris.platform.util.CSVCellDecorator;

import java.util.Map;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.jalo.CatalogManager;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.impex.jalo.header.StandardColumnDescriptor;

import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import com.siteone.core.constants.SiteoneCoreConstants;



import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


/**
 * @author 1099417
 *
 */
public class PriceDivisionCellDecorator implements CSVCellDecorator
{
	private static final Logger LOG = Logger.getLogger(PriceDivisionCellDecorator.class);
	
	
	private ProductService productService;
	private CatalogVersionService catalogVersionService;

	
	@Override
	public String decorate(final int position, final Map<Integer, String> srcLine)
	{

		final String storeDivision = srcLine.get(Integer.valueOf(position));

		//log.info("StoreDivision :" + storeDivision);

		final String[] divisionId = storeDivision.split(":");
		
		ProductModel productModel = null;
		

		if ("1".equalsIgnoreCase(divisionId[1]) || "US".equalsIgnoreCase(divisionId[1]) || "JDL".equalsIgnoreCase(divisionId[1]))
		{
			final CatalogVersionModel catalogVersion = catalogVersionService.getCatalogVersion("siteoneProductCatalog",
					CatalogManager.ONLINE_VERSION);
			try
			{
				productModel = productService.getProductForCode(catalogVersion, divisionId[0]);
			}
			catch (final UnknownIdentifierException unknownIdentifierException)
			{
				LOG.error(unknownIdentifierException);
			}
			
			return divisionId[0];
		}
		else if ("2".equalsIgnoreCase(divisionId[1]) || "CA".equalsIgnoreCase(divisionId[1])
				|| "JDLC".equalsIgnoreCase(divisionId[1]))
		{
			final CatalogVersionModel catalogVersion = catalogVersionService.getCatalogVersion("siteoneCAProductCatalog",
					CatalogManager.ONLINE_VERSION);
			try
			{
				productModel = productService.getProductForCode(catalogVersion, divisionId[0]);
			}
			catch (final UnknownIdentifierException unknownIdentifierException)
			{
				LOG.error(unknownIdentifierException);
			}
			
			return divisionId[0];
		}
		else 
		{
			return StringUtils.EMPTY;
		}
	}

}
