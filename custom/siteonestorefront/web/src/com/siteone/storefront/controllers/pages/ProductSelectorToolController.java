package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.util.Config;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
// FRAMEWORK_UPDATE - TODO - AntPathMatcher was replaced with PathPatternParser as the new default path parser in Spring 6. Adjust this path to the new matching rules or re-enable deprecated AntPathMatcher. Consult "Adapting to PathPatternParser new default URL Matcher" JDK21 Upgrade Step in SAP Help documentation.
@RequestMapping("/**/agronomicsguide")
public class ProductSelectorToolController extends AbstractSearchPageController
{
	//@Resource(name = "i18nService")
	//private I18NService i18nService;

	private static final String EXCENTOS_URL_EN = "/siteonelandscape/DE/app_Agronomics/en_US/loadadvisor?theme=siteone-river";

	//private static final String EXCENTOS_URL_ES = "/siteonelandscape/DE/app_Agronomics/es_US/loadadvisor?theme=siteone-river";

	@GetMapping("/")
	public String getProductSelectorPage(final Model model) throws CMSItemNotFoundException
	{
		/*
		 * model.addAttribute("excentosUrl", Config.getString("excentos.url", "") +
		 * ((getI18nService().getCurrentLocale().getLanguage().equalsIgnoreCase(Locale.ENGLISH.getLanguage())) ?
		 * EXCENTOS_URL_EN : EXCENTOS_URL_ES));
		 */

		model.addAttribute("excentosUrl", Config.getString("excentos.url", "") + EXCENTOS_URL_EN);

		storeCmsPageInModel(model, getContentPageForLabelOrId("productSelectorToolPage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("productSelectorToolPage"));

		return "pages/category/productSelectorToolPage";
	}
}
