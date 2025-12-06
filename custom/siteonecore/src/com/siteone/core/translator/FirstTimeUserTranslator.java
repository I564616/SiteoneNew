/**
 *
 */
package com.siteone.core.translator;

import de.hybris.platform.core.Registry;
import de.hybris.platform.impex.jalo.header.StandardColumnDescriptor;
import de.hybris.platform.impex.jalo.translators.AbstractValueTranslator;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;

import com.siteone.core.adapter.FirstTimeUserImportAdapter;


/**
 * @author 1099417
 *
 */
public class FirstTimeUserTranslator extends AbstractValueTranslator
{
	private static final String DEFAULT_IMPORT_ADAPTER_NAME = "firstTimeUserImportAdapter";
	private FirstTimeUserImportAdapter firstTimeUserImportAdapter;


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
	public Object importValue(final String userId, final Item b2bCustomer) throws JaloInvalidParameterException
	{

		final boolean isFirstTimeUser = getFirstTimeUserImportAdapter().isFirstTimeUser(userId);

		return Boolean.valueOf(isFirstTimeUser);

	}

	@Override
	public void init(final StandardColumnDescriptor descriptor)
	{
		super.init(descriptor);
		firstTimeUserImportAdapter = (FirstTimeUserImportAdapter) Registry.getApplicationContext()
				.getBean(DEFAULT_IMPORT_ADAPTER_NAME);
	}

	/**
	 * @return the firstTimeUserImportAdapter
	 */
	public FirstTimeUserImportAdapter getFirstTimeUserImportAdapter()
	{
		return firstTimeUserImportAdapter;
	}

	/**
	 * @param firstTimeUserImportAdapter
	 *           the firstTimeUserImportAdapter to set
	 */
	public void setFirstTimeUserImportAdapter(final FirstTimeUserImportAdapter firstTimeUserImportAdapter)
	{
		this.firstTimeUserImportAdapter = firstTimeUserImportAdapter;
	}


}


