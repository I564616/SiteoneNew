/**
 *
 */
package com.siteone.facade.requestaccount.impl;

import de.hybris.platform.servicelayer.dto.converter.Converter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import com.siteone.core.model.SiteOneContrPrimaryBusinessModel;
import com.siteone.core.requestaccount.service.SiteoneContrPrimaryBusinessMapService;
import com.siteone.facade.customer.info.SiteOneContrPrimaryBusinessData;
import com.siteone.facade.requestaccount.SiteoneContrPrimaryBusinessMapFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;


/**
 * @author SNavamani
 *
 */
public class DefaultSiteoneContrPrimaryBusinessMapFacade implements SiteoneContrPrimaryBusinessMapFacade
{
	@Resource(name = "siteoneContrPrimaryBusinessMapService")
	private SiteoneContrPrimaryBusinessMapService siteoneContrPrimaryBusinessMapService;
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;
	@Resource(name = "siteOneContrPrimaryBusinessDataConverter")
	private Converter<SiteOneContrPrimaryBusinessModel, SiteOneContrPrimaryBusinessData> siteOneContrPrimaryBusinessDataConverter;


	@Override
	public Map<String, List<SiteOneContrPrimaryBusinessData>> getPrimaryBusinessMap()
	{
		final List<SiteOneContrPrimaryBusinessModel> primaryBusinessMap = siteoneContrPrimaryBusinessMapService
				.getPrimaryBusinessMap();
		final LinkedHashMap lhmPrimaryBusinessMap = new LinkedHashMap();

		if (storeSessionFacade.getCurrentLanguage().getIsocode().equalsIgnoreCase("en"))
		{
			for (final SiteOneContrPrimaryBusinessModel primaryBusinessMapEntry : primaryBusinessMap)
			{
				final List<SiteOneContrPrimaryBusinessData> primaryBusinessDatas = new ArrayList<>();
				final List<SiteOneContrPrimaryBusinessModel> childPrimaryBusiness = siteoneContrPrimaryBusinessMapService
						.getChildPrimaryBusinessMap(primaryBusinessMapEntry.getCode());
				for (final SiteOneContrPrimaryBusinessModel child : childPrimaryBusiness)
				{
					final SiteOneContrPrimaryBusinessData siteOneContrPrimaryBusinessData = new SiteOneContrPrimaryBusinessData();
					primaryBusinessDatas.add(siteOneContrPrimaryBusinessDataConverter.convert(child, siteOneContrPrimaryBusinessData));
				}

				final StringBuilder builder = new StringBuilder();
				builder.append(primaryBusinessMapEntry.getCode());
				builder.append("|");
				builder.append(primaryBusinessMapEntry.getPrimaryBusinessL1());
				lhmPrimaryBusinessMap.put(builder.toString(), primaryBusinessDatas);

			}
			return lhmPrimaryBusinessMap;
		}


		else if (storeSessionFacade.getCurrentLanguage().getIsocode().equalsIgnoreCase("es"))
		{
			for (final SiteOneContrPrimaryBusinessModel primaryBusinessMapEntry : primaryBusinessMap)
			{
				final List<SiteOneContrPrimaryBusinessData> primaryBusinessDatas = new ArrayList<>();
				final List<SiteOneContrPrimaryBusinessModel> childPrimaryBusiness = siteoneContrPrimaryBusinessMapService
						.getChildPrimaryBusinessMap(primaryBusinessMapEntry.getCode());
				for (final SiteOneContrPrimaryBusinessModel child : childPrimaryBusiness)
				{
					final SiteOneContrPrimaryBusinessData siteOneContrPrimaryBusinessData = new SiteOneContrPrimaryBusinessData();
					primaryBusinessDatas.add(siteOneContrPrimaryBusinessDataConverter.convert(child, siteOneContrPrimaryBusinessData));
				}

				final StringBuilder builder = new StringBuilder();
				builder.append(primaryBusinessMapEntry.getCode());
				builder.append("|");
				builder.append(primaryBusinessMapEntry.getPrimaryBusinessL1_ES());
				lhmPrimaryBusinessMap.put(builder.toString(), primaryBusinessDatas);

			}
			return lhmPrimaryBusinessMap;
		}
		else
		{
			return null;
		}
	}
}
