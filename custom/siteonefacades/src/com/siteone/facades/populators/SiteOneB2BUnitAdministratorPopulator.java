/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.b2b.constants.B2BConstants;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;


/**
 * @author pelango
 *
 */
public class SiteOneB2BUnitAdministratorPopulator implements Populator<B2BUnitModel, B2BUnitData>
{

	private B2BUnitService<B2BUnitModel, B2BCustomerModel> b2BUnitService;
	private Converter<B2BCustomerModel, CustomerData> b2BCustomerConverter;

	@Override
	public void populate(final B2BUnitModel source, final B2BUnitData target) throws ConversionException
	{

		final Collection<B2BCustomerModel> administrators = getB2BUnitService().getUsersOfUserGroup(source,
				B2BConstants.B2BADMINGROUP, false);
		if (CollectionUtils.isNotEmpty(administrators))
		{
			target.setAdministrators(Converters.convertAll(administrators, getB2BCustomerConverter()));
		}
	}

	public B2BUnitService<B2BUnitModel, B2BCustomerModel> getB2BUnitService()
	{
		return b2BUnitService;
	}

	public void setB2BUnitService(final B2BUnitService<B2BUnitModel, B2BCustomerModel> b2BUnitService)
	{
		this.b2BUnitService = b2BUnitService;
	}

	public Converter<B2BCustomerModel, CustomerData> getB2BCustomerConverter()
	{
		return b2BCustomerConverter;
	}

	public void setB2BCustomerConverter(final Converter<B2BCustomerModel, CustomerData> b2BCustomerConverter)
	{
		this.b2BCustomerConverter = b2BCustomerConverter;
	}

}
