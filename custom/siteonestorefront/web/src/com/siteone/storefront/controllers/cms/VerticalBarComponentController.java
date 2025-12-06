/**
 *
 */
package com.siteone.storefront.controllers.cms;

import de.hybris.platform.acceleratorfacades.device.DeviceDetectionFacade;
import de.hybris.platform.acceleratorfacades.device.data.DeviceData;
import de.hybris.platform.cms2.model.navigation.CMSNavigationNodeModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.siteone.core.model.GlobalProductNavigationNodeModel;
import com.siteone.core.model.VerticalBarComponentModel;
import com.siteone.core.services.SiteOneProductService;
import com.siteone.storefront.controllers.ControllerConstants;


/**
 * @author 1129929
 *
 */

@Controller("VerticalBarComponentController")
@RequestMapping(value = ControllerConstants.Actions.Cms.VerticalBarComponent)
public class VerticalBarComponentController extends AbstractAcceleratorCMSComponentController<VerticalBarComponentModel>
{
	@Resource(name = "deviceDetectionFacade")
	private DeviceDetectionFacade deviceDetectionFacade;
	
	@Resource(name = "siteOneProductService")
	private SiteOneProductService siteOneProductService;



	@Override
	protected void fillModel(final HttpServletRequest request, final Model model, final VerticalBarComponentModel component)
	{
		final List<GlobalProductNavigationNodeModel> navNodes = new ArrayList<>();
		VerticalBarComponentModel com = siteOneProductService.getVerticalBarComponent();
		if(null != com) {
			navNodes.addAll(com.getGlobalProductNodes());
		}
		else {
		  navNodes.addAll(component.getGlobalProductNodes());
		}
		//Level 1 sorting
		Collections.sort(navNodes, (o1, o2) -> o1.getSequenceNumber().compareTo(o2.getSequenceNumber()));

		for (final CMSNavigationNodeModel level1 : navNodes)
		{
			final List<CMSNavigationNodeModel> level2Nodes = new ArrayList<>();
			level2Nodes.addAll(level1.getChildren());
			//Level 2 sorting
			Collections.sort(level2Nodes, (o1, o2) -> ((GlobalProductNavigationNodeModel) o1).getSequenceNumber()
					.compareTo(((GlobalProductNavigationNodeModel) o2).getSequenceNumber()));
			level1.setChildren(level2Nodes);

			for (final CMSNavigationNodeModel level2 : level2Nodes)
			{
				final List<CMSNavigationNodeModel> level3Nodes = new ArrayList<>();
				level3Nodes.addAll(level2.getChildren());
				//Level 3 sorting
				Collections.sort(level3Nodes, (o1, o2) -> ((GlobalProductNavigationNodeModel) o1).getSequenceNumber()
						.compareTo(((GlobalProductNavigationNodeModel) o2).getSequenceNumber()));
				level2.setChildren(level3Nodes);
			}
		}

		model.addAttribute("globalProductNodes", navNodes);
		model.addAttribute("title", component.getTitle());

		final DeviceData deviceData = deviceDetectionFacade.getCurrentDetectedDevice();

		model.addAttribute("deviceData", deviceData);
	}





}
