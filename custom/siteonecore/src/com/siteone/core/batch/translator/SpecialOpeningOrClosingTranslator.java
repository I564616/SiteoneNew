/**
 *
 */
package com.siteone.core.batch.translator;

import de.hybris.platform.core.Registry;
import de.hybris.platform.impex.jalo.header.SpecialColumnDescriptor;
import de.hybris.platform.impex.jalo.translators.AbstractSpecialValueTranslator;
import de.hybris.platform.jalo.Item;

import org.apache.commons.lang3.StringUtils;

import com.siteone.core.batch.adapter.SpecialOpeningOrClosingAdapter;


/**
 * @author 1085284
 *
 */
public class SpecialOpeningOrClosingTranslator extends AbstractSpecialValueTranslator
{
	private static final String MODIFIER_NAME_ADAPTER = "adapter";
	private static final String DEFAULT_IMPORT_ADAPTER_NAME = "specialOpeningOrClosingAdapter";
	private SpecialOpeningOrClosingAdapter specialOpeningOrClosingAdapter;

	@Override
	public void init(final SpecialColumnDescriptor columnDescriptor)
	{
		String beanName = columnDescriptor.getDescriptorData().getModifier(MODIFIER_NAME_ADAPTER);
		if (StringUtils.isBlank(beanName))
		{
			beanName = DEFAULT_IMPORT_ADAPTER_NAME;
		}
		specialOpeningOrClosingAdapter = (SpecialOpeningOrClosingAdapter) Registry.getApplicationContext().getBean(beanName);
	}

	@Override
	public void performImport(final String cellValue, final Item processedItem)
	{
		specialOpeningOrClosingAdapter.performImport(cellValue, processedItem);
	}


	public void setSpecialOpeningOrClosingAdapter(final SpecialOpeningOrClosingAdapter specialOpeningOrClosingAdapter)
	{
		this.specialOpeningOrClosingAdapter = specialOpeningOrClosingAdapter;
	}
}
