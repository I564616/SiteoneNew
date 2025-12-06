/**
 *
 */
package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.customer.CustomerFacade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import com.siteone.facade.LeadGenarationData;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.storefront.controllers.ControllerConstants;
import com.siteone.storefront.forms.SiteOneLeadGenerationForm;


/**
 * @author 1129929
 *
 */
@Controller
public class PromotionsPageController extends AbstractSearchPageController
{
	private static final String PROMOTION_ID_PATH_VARIABLE_PATTERN = "{promotionId:.*}";
	private static final String BREADCRUMBS_ATTR = "breadcrumbs";

	private static final Logger LOG = Logger.getLogger(PromotionsPageController.class);

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@GetMapping("/promotions")
	public String getPromotionPage(final Model model) throws CMSItemNotFoundException // NOSONAR
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<>();
		breadcrumbs.add(new Breadcrumb(null,
				getMessageSource().getMessage("promotionBreadcrumb", null, getI18nService().getCurrentLocale()), null));
		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);
		storeCmsPageInModel(model, getContentPageForLabelOrId("promotionPage"));
		return ControllerConstants.Views.Pages.Promotion.PromotionPage;
	}

	@GetMapping("/lead/" + PROMOTION_ID_PATH_VARIABLE_PATTERN)
	public String getLeadGenerationPage(@PathVariable("promotionId") final String promotionId, final Model model)
			throws CMSItemNotFoundException
	{
		prepareForm(model, promotionId);
		final List<Breadcrumb> breadcrumbs = new ArrayList<>();
		breadcrumbs.add(new Breadcrumb(null,
				getMessageSource().getMessage("promotionBreadcrumb", null, getI18nService().getCurrentLocale()), null));
		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("leadGenerationPage"));
		storeCmsPageInModel(model, getContentPageForLabelOrId("leadGenerationPage"));
		return ControllerConstants.Views.Pages.Promotion.LeadGenerationPage;
	}

	@PostMapping("/lead/" + PROMOTION_ID_PATH_VARIABLE_PATTERN)
	public String getLeadGenerationPage(@PathVariable("promotionId") final String promotionId,
			@Valid final SiteOneLeadGenerationForm siteOneLeadGenerationForm, final BindingResult bindingResult, final Model model)
			throws CMSItemNotFoundException
	{
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("leadGenerationPage"));
		storeCmsPageInModel(model, getContentPageForLabelOrId("leadGenerationPage"));
		if (bindingResult.hasErrors())
		{
			model.addAttribute("siteOneLeadGenerationForm", siteOneLeadGenerationForm);
			GlobalMessages.addErrorMessage(model, "form.global.error");
			return ControllerConstants.Views.Pages.Promotion.LeadGenerationPage;
		}
		else
		{
			final LeadGenarationData leadGenerationData = new LeadGenarationData();
			BeanUtils.copyProperties(siteOneLeadGenerationForm, leadGenerationData);
			try
			{
				((SiteOneCustomerFacade) customerFacade).saveLeadGenerationData(leadGenerationData);
				model.addAttribute("success", "success");
				return getLeadGenerationPage(promotionId, model);
			}
			catch (final ResourceAccessException | IOException e)
			{
				LOG.error(e);
				model.addAttribute("siteOneLeadGenerationForm", siteOneLeadGenerationForm);
				GlobalMessages.addErrorMessage(model, "service.global.unavailable");
				return ControllerConstants.Views.Pages.Promotion.LeadGenerationPage;
			}
			catch (final RestClientException e)
			{
				LOG.error(e);
				model.addAttribute("siteOneLeadGenerationForm", siteOneLeadGenerationForm);
				GlobalMessages.addErrorMessage(model, "service.global.unavailable");
				return ControllerConstants.Views.Pages.Promotion.LeadGenerationPage;
			}

		}
	}

	protected void prepareForm(final Model model, final String promotionId)
	{
		if (!model.containsAttribute("siteOneLeadGenerationForm"))
		{
			final SiteOneLeadGenerationForm siteOneLeadGenerationForm = new SiteOneLeadGenerationForm();
			siteOneLeadGenerationForm.setPromotionId(promotionId);
			model.addAttribute("siteOneLeadGenerationForm", siteOneLeadGenerationForm);
		}
	}


}
