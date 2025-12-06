/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.siteone.core.batch.translator;

import de.hybris.platform.core.Registry;
import de.hybris.platform.impex.jalo.header.SpecialColumnDescriptor;
import de.hybris.platform.impex.jalo.translators.AbstractSpecialValueTranslator;
import de.hybris.platform.jalo.Item;

import org.apache.commons.lang3.StringUtils;

import com.siteone.core.batch.adapter.WeekDayOpeningDayImportAdapter;


/**
 * Translator for create or update the WeekDayOpeningDay.
 */
public class WeekDayOpeningDayTranslator extends AbstractSpecialValueTranslator
{
	private static final String MODIFIER_NAME_ADAPTER = "adapter";
	private static final String DEFAULT_IMPORT_ADAPTER_NAME = "weekDayOpeningDayImportAdapter";
	private WeekDayOpeningDayImportAdapter weekDayOpeningDayImportAdapter;

	@Override
	public void init(final SpecialColumnDescriptor columnDescriptor)
	{
		String beanName = columnDescriptor.getDescriptorData().getModifier(MODIFIER_NAME_ADAPTER);
		if (StringUtils.isBlank(beanName))
		{
			beanName = DEFAULT_IMPORT_ADAPTER_NAME;
		}
		weekDayOpeningDayImportAdapter = (WeekDayOpeningDayImportAdapter) Registry.getApplicationContext().getBean(beanName);
	}

	@Override
	public void performImport(final String cellValue, final Item processedItem)
	{
		weekDayOpeningDayImportAdapter.performImport(cellValue, processedItem);
	}


	public void setWeekDayOpeningDayImportAdapter(final WeekDayOpeningDayImportAdapter weekDayOpeningDayImportAdapter)
	{
		this.weekDayOpeningDayImportAdapter = weekDayOpeningDayImportAdapter;
	}
}
