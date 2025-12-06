/**
 *
 */
package com.siteone.core.translator;

import com.siteone.core.adapter.BigBagSizeAdapter;
import com.siteone.core.adapter.FirstTimeUserImportAdapter;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.impex.jalo.header.StandardColumnDescriptor;
import de.hybris.platform.impex.jalo.translators.AbstractValueTranslator;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;


/**
 * @author 1099417
 *
 */
public class BigBagSizeTranslator extends AbstractValueTranslator
{
	private static final String DEFAULT_IMPORT_ADAPTER_NAME = "bigBagSizeAdapter";
	private BigBagSizeAdapter bigBagSizeAdapter;


	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.impex.jalo.translators.AbstractValueTranslator#exportValue(java.lang.Object)
	 */
	@Override
	public String exportValue(final Object arg0) throws JaloInvalidParameterException
	{
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.impex.jalo.translators.AbstractValueTranslator#importValue(java.lang.String,
	 * de.hybris.platform.jalo.Item)
	 */
	@Override
	public ProductModel importValue(final String code, final Item b2bCustomer) throws JaloInvalidParameterException {
		return getBigBagSizeAdapter().getProductByCode(code);
	}

	@Override
	public void init(final StandardColumnDescriptor descriptor)
	{
		super.init(descriptor);
		bigBagSizeAdapter = (BigBagSizeAdapter) Registry.getApplicationContext()
				.getBean(DEFAULT_IMPORT_ADAPTER_NAME);
	}

	/**
	 * @return the bigBagSizeAdapter
	 */
	public BigBagSizeAdapter getBigBagSizeAdapter()
	{
		return bigBagSizeAdapter;
	}

	/**
	 * @param bigBagSizeAdapter
	 *           the bigBagSizeAdapter to set
	 */
	public void setBigBagSizeAdapter(final BigBagSizeAdapter bigBagSizeAdapter)
	{
		this.bigBagSizeAdapter = bigBagSizeAdapter;
	}


}


