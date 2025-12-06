/**
 *
 */
package com.siteone.storefront.controllers.cms;

import de.hybris.platform.acceleratorcms.model.components.FooterNavigationComponentModel;
import de.hybris.platform.acceleratorfacades.device.DeviceDetectionFacade;
import de.hybris.platform.acceleratorfacades.device.data.DeviceData;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.cms.AbstractCMSComponentController;
import de.hybris.platform.cms2.model.navigation.CMSNavigationNodeModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.siteone.core.footer.service.SiteoneFooterNavigationService;
import com.siteone.core.model.GlobalProductNavigationNodeModel;
import com.siteone.core.model.VerticalBarComponentModel;
import com.siteone.core.services.SiteOneProductService;
import com.siteone.storefront.controllers.ControllerConstants;


/**
 * @author 1129929
 *
 */

@Controller("FooterNavigationComponentController")
@RequestMapping(value = ControllerConstants.Actions.Cms.FooterNavigationComponent)
public class FooterNavigationComponentController extends AbstractAcceleratorCMSComponentController<FooterNavigationComponentModel>
{
	private static final Logger LOGGER = Logger.getLogger(FooterNavigationComponentController.class);
	
	@Resource(name = "deviceDetectionFacade")
	private DeviceDetectionFacade deviceDetectionFacade;
	
	@Resource(name = "siteoneFooterNavigationService")
	private SiteoneFooterNavigationService siteoneFooterNavigationService;



	@Override
	protected void fillModel(final HttpServletRequest request, final Model model, final FooterNavigationComponentModel component)
	{
		FooterNavigationComponentModel com = siteoneFooterNavigationService.getFooterNode();
		if(null == com) {
			com = component;
		}
		model.addAttribute("component", com);
		model.addAttribute("name", com.getName());

		final DeviceData deviceData = deviceDetectionFacade.getCurrentDetectedDevice();

		model.addAttribute("deviceData", deviceData);
	}





}
