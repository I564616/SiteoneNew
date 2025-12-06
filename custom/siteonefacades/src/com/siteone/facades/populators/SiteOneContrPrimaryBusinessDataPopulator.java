/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import jakarta.annotation.Resource;

import com.siteone.core.model.SiteOneContrPrimaryBusinessModel;
import com.siteone.facade.customer.info.SiteOneContrPrimaryBusinessData;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;


/**
 * @author BS
 *
 */
public class SiteOneContrPrimaryBusinessDataPopulator
		implements Populator<SiteOneContrPrimaryBusinessModel, SiteOneContrPrimaryBusinessData>
{
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;


	@Override
	public void populate(final SiteOneContrPrimaryBusinessModel source, final SiteOneContrPrimaryBusinessData target)
			throws ConversionException
	{
		target.setCode(source.getCode());
		if (storeSessionFacade.getCurrentLanguage().getIsocode().equalsIgnoreCase("en"))
		{
			target.setDescription(source.getPrimaryBusinessL1());
		}
		else if (storeSessionFacade.getCurrentLanguage().getIsocode().equalsIgnoreCase("es"))
		{
			target.setDescription(source.getPrimaryBusinessL1_ES());
		}

	}
}
