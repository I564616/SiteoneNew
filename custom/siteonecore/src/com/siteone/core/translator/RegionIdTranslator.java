/**
 *
 */
package com.siteone.core.translator;

import com.siteone.core.adapter.RegionIdImportAdapter;
import de.hybris.platform.core.Registry;
import de.hybris.platform.impex.jalo.header.StandardColumnDescriptor;
import de.hybris.platform.impex.jalo.translators.AbstractValueTranslator;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;


/**
 * @author 1099417
 *
 */
public class RegionIdTranslator extends AbstractValueTranslator
{
	private static final String DEFAULT_IMPORT_ADAPTER_NAME = "regionIdImportAdapter";
	private RegionIdImportAdapter regionIdImportAdapter;

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
		final String regionId = getRegionIdImportAdapter().getRegionId(parentId);

		return regionId;

	}

	@Override
	public void init(final StandardColumnDescriptor descriptor)
	{
		super.init(descriptor);
		regionIdImportAdapter = (RegionIdImportAdapter) Registry.getApplicationContext()
				.getBean(DEFAULT_IMPORT_ADAPTER_NAME);
	}

	/**
	 * @return the regionIdImportAdapter
	 */
	public RegionIdImportAdapter getRegionIdImportAdapter()
	{
		return regionIdImportAdapter;
	}

	/**
	 * @param regionIdImportAdapter
	 *           the regionIdImportAdapter to set
	 */
	public void setRegionIdImportAdapter(final RegionIdImportAdapter regionIdImportAdapter)
	{
		this.regionIdImportAdapter = regionIdImportAdapter;
	}

}


