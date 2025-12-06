/**
 *
 */
package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.siteone.storefront.controllers.ControllerConstants;


/**
 * @author 1129929
 *
 */
@Controller
public class MonthlyFlyerController extends AbstractSearchPageController
{

	@GetMapping("/flyer")
	public String getMonthlyFlyerPage(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute("isMonthlyFlyerPage", Boolean.TRUE);
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("monthlyFlyerPage"));
		storeCmsPageInModel(model, getContentPageForLabelOrId("monthlyFlyerPage"));
		return ControllerConstants.Views.Pages.Promotion.MonthlyFlyerPage;
	}
}
