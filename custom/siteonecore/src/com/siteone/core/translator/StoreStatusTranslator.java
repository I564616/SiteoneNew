/**
 *
 */
package com.siteone.core.translator;

import de.hybris.platform.core.Registry;
import de.hybris.platform.impex.jalo.header.StandardColumnDescriptor;
import de.hybris.platform.impex.jalo.translators.AbstractValueTranslator;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import org.apache.commons.lang3.BooleanUtils;

import com.siteone.core.adapter.PointOfServiceAdaptor;


/**
 * @author pelango
 *
 */
public class StoreStatusTranslator extends AbstractValueTranslator
{
	private static final String DEFAULT_IMPORT_ADAPTER_NAME = "pointOfServiceAdaptor";
	private PointOfServiceAdaptor pointOfServiceAdaptor;

	@Override
	public String exportValue(final Object arg0) throws JaloInvalidParameterException
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public Object importValue(final String value, final Item pointOfService) throws JaloInvalidParameterException
	{
		boolean isActiveFlag = false;
		if (value.equalsIgnoreCase("true"))
		{
			isActiveFlag = true;
		}
		else if(pointOfService != null)
		{
			final PointOfServiceModel posModel = getPointOfServiceAdaptor().getStoreDetailByPK(pointOfService.getPK().toString());
			if (posModel != null && BooleanUtils.isTrue(posModel.getIsDCBranch()))
			{
				isActiveFlag = posModel.getIsActive();
			}
			else
			{
				isActiveFlag = false;
			}
		}

		return isActiveFlag;

	}

	@Override
	public void init(final StandardColumnDescriptor descriptor)
	{
		super.init(descriptor);
		pointOfServiceAdaptor = (PointOfServiceAdaptor) Registry.getApplicationContext().getBean(DEFAULT_IMPORT_ADAPTER_NAME);
	}

	/**
	 * @return the pointOfServiceAdaptor
	 */
	public PointOfServiceAdaptor getPointOfServiceAdaptor()
	{
		return pointOfServiceAdaptor;
	}

	/**
	 * @param pointOfServiceAdaptor
	 *           the pointOfServiceAdaptor to set
	 */
	public void setPointOfServiceAdaptor(final PointOfServiceAdaptor pointOfServiceAdaptor)
	{
		this.pointOfServiceAdaptor = pointOfServiceAdaptor;
	}

}


