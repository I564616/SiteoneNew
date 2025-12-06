/**
 *
 */
package com.siteone.core.batch.translator;

import de.hybris.platform.core.Registry;
import de.hybris.platform.impex.jalo.header.SpecialColumnDescriptor;
import de.hybris.platform.impex.jalo.translators.AbstractSpecialValueTranslator;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;

import com.siteone.core.batch.adapter.StoreSpecialtyAdapter;


/**
 * @author PElango
 *
 */
public class StoreSpecialtyTranslator extends AbstractSpecialValueTranslator
{

	private static final String DEFAULT_IMPORT_ADAPTER_NAME = "storeSpecialtyAdapter";

	private StoreSpecialtyAdapter storeSpecialtyAdapter;

	@Override
	public void init(final SpecialColumnDescriptor columnDescriptor)
	{

		storeSpecialtyAdapter = (StoreSpecialtyAdapter) Registry.getApplicationContext().getBean(DEFAULT_IMPORT_ADAPTER_NAME);

	}

	@Override
	public void performImport(final String cellValue, final Item processedItem) throws JaloInvalidParameterException
	{
		storeSpecialtyAdapter.performImport(cellValue, processedItem);
	}
}
