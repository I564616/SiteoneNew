/**
 *
 */
package com.siteone.storefront.controllers.cms;

import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.siteone.core.model.SiteOneCuratedPLPStoreComponentModel;
import com.siteone.facades.store.SiteOneStoreDetailsFacade;
import com.siteone.storefront.controllers.ControllerConstants;


/**
 * @author BS
 *
 */
@Controller("SiteOneCuratedPLPStoreComponentController")
@RequestMapping(value = ControllerConstants.Actions.Cms.SiteOneCuratedPLPStoreComponent)
public class SiteOneCuratedPLPStoreComponentController
		extends AbstractAcceleratorCMSComponentController<SiteOneCuratedPLPStoreComponentModel>
{
	private static final Logger LOG = Logger.getLogger(SiteOneCuratedPLPComponentController.class);

	@Resource(name = "siteOnestoreDetailsFacade")
	private SiteOneStoreDetailsFacade siteOnestoreDetailsFacade;

	@Override
	protected void fillModel(final HttpServletRequest request, final Model model,
			final SiteOneCuratedPLPStoreComponentModel component)
	{
		final List<String> urls = Arrays.asList(component.getButtonUrl().split(","));
		final List<String> storeIds = Arrays.asList(component.getStoresList().replace(" ", "").split(","));
		final List<PointOfServiceData> stores = siteOnestoreDetailsFacade.fetchListofPointOfService(storeIds);
		if (CollectionUtils.isNotEmpty(stores))
		{
			Collections.sort(stores, Comparator.comparing(PointOfServiceData::getName));
			model.addAttribute("stores", stores);
			model.addAttribute("buttonUrls", urls);
		}
	}

}
