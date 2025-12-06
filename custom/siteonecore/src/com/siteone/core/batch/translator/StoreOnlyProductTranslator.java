/**
 *
 */
package com.siteone.core.batch.translator;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.b2b.services.impl.DefaultB2BOrderService;
import de.hybris.platform.catalog.jalo.CatalogManager;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.impex.jalo.header.StandardColumnDescriptor;
import de.hybris.platform.impex.jalo.translators.AbstractValueTranslator;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


/**
 * @author 1085284
 *
 */
public class StoreOnlyProductTranslator extends AbstractValueTranslator
{
	private static final Logger LOG = Logger.getLogger(StoreOnlyProductTranslator.class);
	private static final String MODIFIER_NAME_ADAPTER = "adapter";
	private static final String DEFAULT_IMPORT_ADAPTER_NAME = "storeOnlyProductAdapter";
	private ProductService productService;
	private CatalogVersionService catalogVersionService;
	private DefaultB2BOrderService b2bOrderService;

	@Override
	public void init(final StandardColumnDescriptor descriptor)
	{
		String beanName = descriptor.getDescriptorData().getModifier(MODIFIER_NAME_ADAPTER);
		if (StringUtils.isBlank(beanName))
		{
			beanName = DEFAULT_IMPORT_ADAPTER_NAME;
		}

		productService = (ProductService) Registry.getApplicationContext().getBean("productService");
		catalogVersionService = (CatalogVersionService) Registry.getApplicationContext().getBean("catalogVersionService");
		b2bOrderService = (DefaultB2BOrderService) Registry.getApplicationContext().getBean("b2bOrderService");
		
	}

	@Override
	public Object importValue(final String cellValue, final Item orderEntry) throws JaloInvalidParameterException
	{
		LOG.error("cellValue =" + cellValue);
		final String[] inputOrderProduct = cellValue.split("_");
		ProductModel productModel = null;
		final CatalogVersionModel catalogVersion = catalogVersionService.getCatalogVersion("siteoneProductCatalog",
				CatalogManager.ONLINE_VERSION);
		final CatalogVersionModel catalogCAVersion = catalogVersionService.getCatalogVersion("siteoneCAProductCatalog",
				CatalogManager.ONLINE_VERSION);
		try
		{
			final AbstractOrderModel orderModel = b2bOrderService.getAbstractOrderForCode(inputOrderProduct[1]);
			if(null!=orderModel && null!=orderModel.getCurrency().getIsocode() && orderModel.getCurrency().getIsocode().equalsIgnoreCase("CAD"))
			{
			productModel = productService.getProductForCode(catalogCAVersion, inputOrderProduct[0]);
			}
			else {
				productModel = productService.getProductForCode(catalogVersion, inputOrderProduct[0]);
			}
		}
		catch (final UnknownIdentifierException unknownIdentifierException)
		{
			LOG.info("Product is not available .. in exception block" + cellValue);
		}
		if (null == productModel)
		{
			LOG.info("Product is not available ..");
			productModel = productService.getProductForCode(catalogVersion, "9999999");
		}
		return productModel;
	}

	@Override
	public String exportValue(final Object arg0) throws JaloInvalidParameterException
	{
		return null;
	}
}
