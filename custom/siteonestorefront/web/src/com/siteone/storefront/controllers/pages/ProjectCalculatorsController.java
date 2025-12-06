/**
 *
 */
package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.siteone.storefront.controllers.ControllerConstants;


/**
 * Controller for calculators page
 *
 */
@Controller
@RequestMapping("/projectcalculators")
public class ProjectCalculatorsController extends AbstractPageController
{

	private static final Logger LOG = Logger.getLogger(ProjectCalculatorsController.class);

	private static final String BREADCRUMBS_ATTR = "breadcrumbs";
	private static final String FLAG_STONE_CMS_PAGE_LABEL = "flagstone";
	private static final String STONE_WALLS_CMS_PAGE_LABEL = "stonewalls";
	private static final String ROAD_BASEFILL_CMS_PAGE_LABEL = "roadbasefill";
	private static final String DECORATIVE_ROCK_CMS_PAGE_LABEL = "decorativerock";
	private static final String TOP_SOIL_CMS_PAGE_LABEL = "topsoil";
	private static final String BARK_MULCH_CMS_PAGE_LABEL = "barkmulch";
	private static final String VOLTAGE_DROP_CMS_PAGE_LABEL = "voltagedrop";
	private static final String HOLIDAY_LIGHTING_CMS_PAGE_LABEL ="holidaylighting";
	private static final String PLANT_SPACING_CMS_PAGE_LABEL = "plantspacing";


	@GetMapping(value = "/flagstone")
	public String flagStone(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute(BREADCRUMBS_ATTR, getBreadcrumbs(FLAG_STONE_CMS_PAGE_LABEL));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.INDEX_FOLLOW);
		storeCmsPageInModel(model, getContentPage(FLAG_STONE_CMS_PAGE_LABEL));
		setUpMetaDataForContentPage(model, (ContentPageModel) getContentPage(FLAG_STONE_CMS_PAGE_LABEL));
		return ControllerConstants.Views.Pages.Calculator.FlagStone;
	}

	@GetMapping(value = "/stonewalls")
	public String stoneWalls(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute(BREADCRUMBS_ATTR, getBreadcrumbs(STONE_WALLS_CMS_PAGE_LABEL));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.INDEX_FOLLOW);
		storeCmsPageInModel(model, getContentPage(STONE_WALLS_CMS_PAGE_LABEL));
		setUpMetaDataForContentPage(model, (ContentPageModel) getContentPage(STONE_WALLS_CMS_PAGE_LABEL));
		return ControllerConstants.Views.Pages.Calculator.StoneWalls;
	}

	@GetMapping(value = "/roadbasefill")
	public String roadBasefill(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute(BREADCRUMBS_ATTR, getBreadcrumbs(ROAD_BASEFILL_CMS_PAGE_LABEL));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.INDEX_FOLLOW);
		storeCmsPageInModel(model, getContentPage(ROAD_BASEFILL_CMS_PAGE_LABEL));
		setUpMetaDataForContentPage(model, (ContentPageModel) getContentPage(ROAD_BASEFILL_CMS_PAGE_LABEL));
		return ControllerConstants.Views.Pages.Calculator.RoadBasefill;
	}

	@GetMapping(value = "/decorativerock")
	public String decorativeRock(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute(BREADCRUMBS_ATTR, getBreadcrumbs(DECORATIVE_ROCK_CMS_PAGE_LABEL));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.INDEX_FOLLOW);
		storeCmsPageInModel(model, getContentPage(DECORATIVE_ROCK_CMS_PAGE_LABEL));
		setUpMetaDataForContentPage(model, (ContentPageModel) getContentPage(DECORATIVE_ROCK_CMS_PAGE_LABEL));
		return ControllerConstants.Views.Pages.Calculator.DecorativeRock;
	}

	@GetMapping(value = "/topsoil")
	public String topSoil(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute(BREADCRUMBS_ATTR, getBreadcrumbs(TOP_SOIL_CMS_PAGE_LABEL));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.INDEX_FOLLOW);
		storeCmsPageInModel(model, getContentPage(TOP_SOIL_CMS_PAGE_LABEL));
		setUpMetaDataForContentPage(model, (ContentPageModel) getContentPage(TOP_SOIL_CMS_PAGE_LABEL));
		return ControllerConstants.Views.Pages.Calculator.TopSoil;

	}

	@GetMapping(value = "/barkmulch")
	public String barkMulch(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute(BREADCRUMBS_ATTR, getBreadcrumbs(BARK_MULCH_CMS_PAGE_LABEL));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.INDEX_FOLLOW);
		storeCmsPageInModel(model, getContentPage(BARK_MULCH_CMS_PAGE_LABEL));
		setUpMetaDataForContentPage(model, (ContentPageModel) getContentPage(BARK_MULCH_CMS_PAGE_LABEL));
		return ControllerConstants.Views.Pages.Calculator.BarkMulch;
	}

	@GetMapping(value = "/voltagedrop")
	public String voltageDrop(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute(BREADCRUMBS_ATTR, getBreadcrumbs(VOLTAGE_DROP_CMS_PAGE_LABEL));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.INDEX_FOLLOW);
		storeCmsPageInModel(model, getContentPage(VOLTAGE_DROP_CMS_PAGE_LABEL));
		setUpMetaDataForContentPage(model, (ContentPageModel) getContentPage(VOLTAGE_DROP_CMS_PAGE_LABEL));
		return ControllerConstants.Views.Pages.Calculator.VoltageDrop;
	}
	
	@GetMapping(value = "/holidaylighting")
	public String holidayLighting(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute(BREADCRUMBS_ATTR, getBreadcrumbs(HOLIDAY_LIGHTING_CMS_PAGE_LABEL));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.INDEX_FOLLOW);
		storeCmsPageInModel(model, getContentPage(HOLIDAY_LIGHTING_CMS_PAGE_LABEL));
		setUpMetaDataForContentPage(model, (ContentPageModel) getContentPage(HOLIDAY_LIGHTING_CMS_PAGE_LABEL));
		return ControllerConstants.Views.Pages.Calculator.HolidayLighting;
	}
	@GetMapping(value = "/plantspacing")
	public String plantSpacing(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute(BREADCRUMBS_ATTR, getBreadcrumbs(PLANT_SPACING_CMS_PAGE_LABEL));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.INDEX_FOLLOW);
		storeCmsPageInModel(model, getContentPage(PLANT_SPACING_CMS_PAGE_LABEL));
		setUpMetaDataForContentPage(model, (ContentPageModel) getContentPage(PLANT_SPACING_CMS_PAGE_LABEL));
		return ControllerConstants.Views.Pages.Calculator.PlantSpacing;
	}



	protected AbstractPageModel getContentPage(final String pageLabel) throws CMSItemNotFoundException
	{
		return getContentPageForLabelOrId(pageLabel);
	}

	@Override
	protected void setUpMetaDataForContentPage(final Model model, final ContentPageModel contentPage)
	{
		setUpMetaData(model, contentPage.getKeywords(), contentPage.getDescription());
	}

	protected List<Breadcrumb> getBreadcrumbs(final String pageLabel)
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<>();

		final Breadcrumb breadcrumbHead = new Breadcrumb("/projectcalculators",
				getMessageSource().getMessage("breadcrumb.calculator", null, getI18nService().getCurrentLocale()), null);
		Breadcrumb breadcrumbPage = null;
		if (FLAG_STONE_CMS_PAGE_LABEL.equalsIgnoreCase(pageLabel))
		{
			breadcrumbPage = new Breadcrumb("/projectcalculators/flagstone",
					getMessageSource().getMessage("breadcrumb.flagstone", null, getI18nService().getCurrentLocale()), null);
		}
		if (STONE_WALLS_CMS_PAGE_LABEL.equalsIgnoreCase(pageLabel))
		{
			breadcrumbPage = new Breadcrumb("/projectcalculators/stonewalls",
					getMessageSource().getMessage("breadcrumb.stonewalls", null, getI18nService().getCurrentLocale()), null);
		}
		if (ROAD_BASEFILL_CMS_PAGE_LABEL.equalsIgnoreCase(pageLabel))
		{
			breadcrumbPage = new Breadcrumb("/projectcalculators/roadbasefill",
					getMessageSource().getMessage("breadcrumb.roadbasefill", null, getI18nService().getCurrentLocale()), null);
		}
		if (DECORATIVE_ROCK_CMS_PAGE_LABEL.equalsIgnoreCase(pageLabel))
		{
			breadcrumbPage = new Breadcrumb("/projectcalculators/decorativerock",
					getMessageSource().getMessage("breadcrumb.decorativerock", null, getI18nService().getCurrentLocale()), null);
		}
		if (TOP_SOIL_CMS_PAGE_LABEL.equalsIgnoreCase(pageLabel))
		{
			breadcrumbPage = new Breadcrumb("/projectcalculators/topsoil",
					getMessageSource().getMessage("breadcrumb.topsoil", null, getI18nService().getCurrentLocale()), null);
		}
		if (BARK_MULCH_CMS_PAGE_LABEL.equalsIgnoreCase(pageLabel))
		{
			breadcrumbPage = new Breadcrumb("/projectcalculators/barkmulch",
					getMessageSource().getMessage("breadcrumb.barkmulch", null, getI18nService().getCurrentLocale()), null);
		}
		if (VOLTAGE_DROP_CMS_PAGE_LABEL.equalsIgnoreCase(pageLabel))
		{
			breadcrumbPage = new Breadcrumb("/projectcalculators/voltagedrop",
					getMessageSource().getMessage("breadcrumb.voltagedrop", null, getI18nService().getCurrentLocale()), null);
		}
		if (HOLIDAY_LIGHTING_CMS_PAGE_LABEL.equalsIgnoreCase(pageLabel))
		{
			breadcrumbPage = new Breadcrumb("/projectcalculators/holidaylighting",
					getMessageSource().getMessage("breadcrumb.holidaylighting", null, getI18nService().getCurrentLocale()), null);
		}
		if (PLANT_SPACING_CMS_PAGE_LABEL.equalsIgnoreCase(pageLabel))
		{
			breadcrumbPage = new Breadcrumb("/projectcalculators/plantspacing",
					getMessageSource().getMessage("breadcrumb.plantspacing", null, getI18nService().getCurrentLocale()), null);
		}

		breadcrumbs.add(breadcrumbHead);
		breadcrumbs.add(breadcrumbPage);
		return breadcrumbs;
	}

}
