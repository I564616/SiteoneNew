/**
 *
 */
package com.siteone.storefront.controllers.cms;

import de.hybris.platform.acceleratorfacades.device.ResponsiveMediaFacade;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commerceservices.i18n.CommerceCommonI18NService;

import java.util.List;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.siteone.core.model.HomePageResponsiveBannerComponentModel;
import com.siteone.storefront.controllers.ControllerConstants;


/**
 * @author 965504
 *
 */
@Controller("HomePageResponsiveBannerComponentController")
@RequestMapping(value = ControllerConstants.Actions.Cms.HomePageResponsiveBannerComponent)
public class HomePageResponsiveBannerComponentController
		extends AbstractAcceleratorCMSComponentController<HomePageResponsiveBannerComponentModel>
{
	@Resource(name = "responsiveMediaFacade")
	private ResponsiveMediaFacade responsiveMediaFacade;

	@Resource(name = "commerceCommonI18NService")
	private CommerceCommonI18NService commerceCommonI18NService;

	@Override
	protected void fillModel(final HttpServletRequest request, final Model model,
			final HomePageResponsiveBannerComponentModel component)
	{
		final List<ImageData> mediaDataList = responsiveMediaFacade
				.getImagesFromMediaContainer(component.getMedia(commerceCommonI18NService.getCurrentLocale()));
		model.addAttribute("medias", mediaDataList);
		model.addAttribute("urlLink", component.getUrlLink());
		model.addAttribute("buttonName", component.getButtonName());
	}
}
