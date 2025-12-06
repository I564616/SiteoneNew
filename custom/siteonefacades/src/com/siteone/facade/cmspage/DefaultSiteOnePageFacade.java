/**
 *
 */
package com.siteone.facade.cmspage;

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cmsfacades.data.AbstractCMSComponentData;
import de.hybris.platform.cmsfacades.data.AbstractPageData;
import de.hybris.platform.cmsfacades.data.PageContentSlotData;
import de.hybris.platform.cmsfacades.items.ComponentItemFacade;
import de.hybris.platform.cmsfacades.pages.PageFacade;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.personalizationservices.service.CxService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;

import com.siteone.integration.constants.SiteoneintegrationConstants;


/**
 * @author PElango
 *
 */
public class DefaultSiteOnePageFacade implements SiteOnePageFacade
{

	@Resource(name = "componentItemFacade")
	private ComponentItemFacade componentItemFacade;
	
	@Resource(name = "cmsPageFacade")
	private PageFacade pageFacade;
	
	@Resource(name = "cxService")
	private CxService cxService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "userFacade")
	private UserFacade userFacade;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Override
	public List<PageContentSlotData> prepareCMSPageData(final String pageId, final String storeId)
			throws CMSItemNotFoundException
	{
		if (userFacade.isAnonymousUser())
		{
			sessionService.setAttribute("gls", storeId);
		}
		cxService.calculateAndLoadPersonalizationInSession(userService.getCurrentUser());
		final AbstractPageData pageData = pageFacade.getPageData(pageId);
		
		List<AbstractCMSComponentData> parentComponentDataList = new ArrayList<AbstractCMSComponentData>();
		final List<PageContentSlotData> contentSlotList = new ArrayList<PageContentSlotData>();

		for (final PageContentSlotData contentSlot : pageData.getContentSlots())
		{
			if (contentSlot.getPosition().equals(SiteoneintegrationConstants.CONTENT_POSITION_SECTIONS1)
					|| contentSlot.getPosition().equals(SiteoneintegrationConstants.CONTENT_POSITION_GLOBALMESSAGE))
			{

				parentComponentDataList = new ArrayList<AbstractCMSComponentData>();
				final PageContentSlotData contentSlotNew = new PageContentSlotData();
				contentSlotNew.setPageId(pageId);
				contentSlotNew.setSlotId(contentSlot.getSlotId());
				contentSlotNew.setPosition(contentSlot.getPosition());
				contentSlotNew.setName(contentSlot.getName());

				for (final AbstractCMSComponentData component : contentSlot.getComponents())
				{
					getCMSComponentDetails(component);
					parentComponentDataList.add(component);
				}
				if (CollectionUtils.isNotEmpty(parentComponentDataList))
				{
					contentSlotNew.setComponents(parentComponentDataList);
					contentSlotList.add(contentSlotNew);
				}
			}
		}

		return contentSlotList;
	}
	
	public void getCMSComponentDetails(final AbstractCMSComponentData component) throws CMSItemNotFoundException
	{
		List<AbstractCMSComponentData> componentDataList = new ArrayList<AbstractCMSComponentData>();
		List<String> bannerList = new ArrayList<String>();
		if (component.getOtherProperties().containsKey(SiteoneintegrationConstants.ROTATINGBANNER))
		{
			bannerList = (ArrayList) component.getOtherProperties().get(SiteoneintegrationConstants.ROTATINGBANNER);
		}
		else if (component.getOtherProperties().containsKey(SiteoneintegrationConstants.BANNERLIST))
		{
			bannerList = (ArrayList) component.getOtherProperties().get(SiteoneintegrationConstants.BANNERLIST);
		}
		if (CollectionUtils.isNotEmpty(bannerList))
		{
			for (final String bannerUid : bannerList)
			{
				final AbstractCMSComponentData componentData = componentItemFacade.getComponentById(bannerUid, null, null,
						null);
				componentDataList.add(componentData);
			}
			component.getOtherProperties().put(SiteoneintegrationConstants.ROTATINGBANNERDETAILS, componentDataList);
		}
	}


}
