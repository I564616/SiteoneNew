/**
 *
 */
package com.siteone.core.translator;

import com.siteone.core.adapter.AreaImportAdapter;
import de.hybris.platform.core.Registry;
import de.hybris.platform.impex.jalo.header.StandardColumnDescriptor;
import de.hybris.platform.impex.jalo.translators.AbstractValueTranslator;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;


/**
 * @author 1099417
 *
 */
public class AreaTranslator extends AbstractValueTranslator
{
	private static final String DEFAULT_IMPORT_ADAPTER_NAME = "areaImportAdapter";
	private AreaImportAdapter areaImportAdapter;

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
	public Object importValue(final String parentId, final Item pointOfService) throws JaloInvalidParameterException
	{
		final String area = getAreaImportAdapter().getArea(parentId);

		return area;

	}

	@Override
	public void init(final StandardColumnDescriptor descriptor)
	{
		super.init(descriptor);
		areaImportAdapter = (AreaImportAdapter) Registry.getApplicationContext()
				.getBean(DEFAULT_IMPORT_ADAPTER_NAME);
	}

	/**
	 * @return the areaImportAdapter
	 */
	public AreaImportAdapter getAreaImportAdapter()
	{
		return areaImportAdapter;
	}

	/**
	 * @param areaImportAdapter
	 *           the areaImportAdapter to set
	 */
	public void setAreaImportAdapter(final AreaImportAdapter areaImportAdapter)
	{
		this.areaImportAdapter = areaImportAdapter;
	}

}


