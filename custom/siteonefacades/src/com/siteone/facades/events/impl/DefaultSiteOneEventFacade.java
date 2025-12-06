/**
 *
 */
package com.siteone.facades.events.impl;

import de.hybris.platform.servicelayer.dto.converter.Converter;

import jakarta.annotation.Resource;

import com.siteone.core.events.service.SiteOneEventService;
import com.siteone.core.model.SiteOneEventModel;
import com.siteone.facade.EventData;
import com.siteone.facades.events.SiteOneEventFacade;


/**
 * @author 965504
 *
 */
public class DefaultSiteOneEventFacade implements SiteOneEventFacade
{

	@Resource(name = "siteOneEventService")
	private SiteOneEventService siteOneEventService;

	@Resource(name = "siteoneEventConverter")
	private Converter<SiteOneEventModel, EventData> siteoneEventConverter;




	@Override
	public EventData getEventByCode(final String eventCode)
	{
		final SiteOneEventModel eventModel = siteOneEventService.getEventByCode(eventCode);
		return siteoneEventConverter.convert(eventModel);
	}




	/**
	 * @return the siteoneEventConverter
	 */
	public Converter<SiteOneEventModel, EventData> getSiteoneEventConverter()
	{
		return siteoneEventConverter;
	}




	/**
	 * @param siteoneEventConverter
	 *           the siteoneEventConverter to set
	 */
	public void setSiteoneEventConverter(final Converter<SiteOneEventModel, EventData> siteoneEventConverter)
	{
		this.siteoneEventConverter = siteoneEventConverter;
	}



}
