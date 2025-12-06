/**
 *
 */
package com.siteone.core.batch.translator;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.impex.jalo.header.StandardColumnDescriptor;
import de.hybris.platform.impex.jalo.translators.AbstractValueTranslator;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;

import org.apache.log4j.Logger;

import com.siteone.core.constants.SiteoneCoreConstants;


/**
 * @author 1085284
 *
 */
public class OrderBillingAddressTranslator extends AbstractValueTranslator
{
	private static final Logger LOG = Logger.getLogger(IsStoreProductTranslator.class);
	private B2BUnitService b2bUnitService;

	@Override
	public void init(final StandardColumnDescriptor descriptor)
	{
		b2bUnitService = (B2BUnitService) Registry.getApplicationContext().getBean("b2bUnitService");

	}

	@Override
	public Object importValue(final String cellValue, final Item item) throws JaloInvalidParameterException
	{
		final String b2bUnit = cellValue;
		if (b2bUnit == null || b2bUnit.length() == 0)
		{
			return b2bUnit;
		}

		final String[] b2BUnit = b2bUnit.split("_");

		if ("1".equalsIgnoreCase(b2BUnit[1]) || "US".equalsIgnoreCase(b2BUnit[1]) || "JDL".equalsIgnoreCase(b2BUnit[1]))
		{
			b2BUnit[1] = SiteoneCoreConstants.US_ISO_CODE;
		}
		else if ("2".equalsIgnoreCase(b2BUnit[1]) || "CA".equalsIgnoreCase(b2BUnit[1]) || "JDLC".equalsIgnoreCase(b2BUnit[1]))
		{
			b2BUnit[1] = SiteoneCoreConstants.CA_ISO_CODE;
		}

		final String unitUid = b2BUnit[0] + "_" + b2BUnit[1];

		B2BUnitModel b2bUnitModel = null;
		AddressModel addressModel = null;
		try
		{
			b2bUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(unitUid);
			if (null != b2bUnitModel)
			{
				addressModel = b2bUnitModel.getReportingOrganization().getBillingAddress();
				if (null != addressModel)
				{
					return addressModel;
				}
			}
		}
		catch (final UnknownIdentifierException unknownIdentifierException)
		{
			LOG.error(unknownIdentifierException);
		}
		return null;
	}


	@Override
	public String exportValue(final Object arg0) throws JaloInvalidParameterException
	{
		return null;
	}


}

