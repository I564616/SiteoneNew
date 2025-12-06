/**
 *
 */
package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.ResourceBreadcrumbBuilder;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.QuoteFacade;
import de.hybris.platform.commerceservices.enums.QuoteAction;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.event.ContactSellerEvent;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.facade.order.SiteoneQuotesFacade;
import com.siteone.facades.quote.data.QuoteApprovalItemData;
import com.siteone.facades.quote.data.QuoteDetailsData;
import com.siteone.facades.quote.data.QuotesData;
import com.siteone.facades.quote.data.ShiptoitemData;


/**
 * @author AA04994
 *
 */
@Controller
// FRAMEWORK_UPDATE - TODO - AntPathMatcher was replaced with PathPatternParser as the new default path parser in Spring 6. Adjust this path to the new matching rules or re-enable deprecated AntPathMatcher. Consult "Adapting to PathPatternParser new default URL Matcher" JDK21 Upgrade Step in SAP Help documentation.
@RequestMapping(value = "/**/my-account")
public class MyQuotesController extends AbstractSearchPageController
{
	private static final Logger LOG = Logger.getLogger(MyQuotesController.class);

	private static final String MY_QUOTES_CMS_PAGE = "my-quotes";
	private static final String QUOTE_DETAILS_CMS_PAGE = "quote-detail";
	private static final String REDIRECT_QUOTE_LIST_URL = REDIRECT_PREFIX + "/my-account/my-quotes/";
	private static final String SYSTEM_ERROR_PAGE_NOT_FOUND = "system.error.page.not.found";
	private static final String QUOTE_CART_INSUFFICIENT_ACCESS_RIGHTS = "quote.cart.insufficient.access.rights.error";
	private static final String PAGINATION_NUMBER_OF_COMMENTS = "quote.pagination.numberofcomments";
	private static final String ALLOWED_ACTIONS = "allowedActions";
	protected static final String URL_MY_QUOTES = "/my-account/my-quotes";

	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;

	@Resource(name = "cartFacade")
	private CartFacade cartFacade;

	@Resource(name = "quoteFacade")
	private QuoteFacade quoteFacade;

	@Resource(name = "siteoneQuotesFacade")
	private SiteoneQuotesFacade siteoneQuotesFacade;

	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;

	@Resource(name = "eventService")
	private EventService eventService;

	@GetMapping("/my-quotes")
	@RequireHardLogIn
	public String quotes(@RequestParam(value = "page", defaultValue = "0")
	final int page, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, @RequestParam(value = "sort", defaultValue = "byDate")
	final String sortCode, final Model model) throws CMSItemNotFoundException, ParseException
	{
		List<QuotesData> quotesData = new ArrayList<QuotesData>();
		final B2BUnitModel unit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
		boolean quoteFeature = false;

		if (siteOneFeatureSwitchCacheService.isAllPresentUnderFeatureSwitch("QuotesFeatureSwitch")
				|| (null != unit && null != unit.getUid()
						&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("QuotesFeatureSwitch", unit.getUid())))
		{
			LOG.error("inside Quote Feature");
			quoteFeature = true;
			quotesData = getSiteoneQuotesFacade().getQuotes(null, "0", null);
		}
		//quotes toggle feature switch
		model.addAttribute("quotesFeatureSwitch", quoteFeature);
		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/my-account/my-quotes",
				getMessageSource().getMessage("text.account.manageQuotes.breadcrumb", null, getI18nService().getCurrentLocale()),
				null));
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadcrumbs);
		model.addAttribute("quoteData", quotesData);
		storeCmsPageInModel(model, getContentPageForLabelOrId(MY_QUOTES_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MY_QUOTES_CMS_PAGE));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		if (null != unit && null != unit.getUid())
		{
			model.addAttribute("unitId", unit.getUid());
		}


		return getViewForPage(model);
	}

	@GetMapping("/my-quotes/{quoteCode}")
	@RequireHardLogIn
	public String quoteDetails(final Model model, final RedirectAttributes redirectModel, @PathVariable("quoteCode")
	final String quoteCode) throws CMSItemNotFoundException
	{
		try
		{
			final QuoteDetailsData quoteDetailsData = getSiteoneQuotesFacade().getQuoteDetails(quoteCode);
			String quoteNumber = String.valueOf(quoteDetailsData.getQuoteNumber());
			LOG.error(quoteDetailsData.getQuoteNumber());
			LOG.error(quoteDetailsData.getDateSubmitted());
			model.addAttribute("quoteData", quoteDetailsData);
			loadCommentsShown(model);

			storeCmsPageInModel(model, getContentPageForLabelOrId(QUOTE_DETAILS_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(QUOTE_DETAILS_CMS_PAGE));

			//setAllowedActions(model, quoteCode);

			boolean quoteFeature = false;
			final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
			breadcrumbs.add(new Breadcrumb("/my-account/my-quotes",
					getMessageSource().getMessage("text.account.manageQuotes.breadcrumb", null, getI18nService().getCurrentLocale()),
					null));
			breadcrumbs.add(new Breadcrumb(URL_MY_QUOTES + "/" + urlEncode(quoteCode),
					getMessageSource().getMessage("breadcrumb.quote.view", new Object[]
					{ quoteNumber }, "{0}", getI18nService().getCurrentLocale()), null));
			model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadcrumbs);
			model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
			final B2BUnitModel unit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
			if (null != unit && null != unit.getUid())
			{
				model.addAttribute("unitId", unit.getUid());
			}

			if (siteOneFeatureSwitchCacheService.isAllPresentUnderFeatureSwitch("QuotesFeatureSwitch")
					|| (null != unit && null != unit.getUid()
							&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("QuotesFeatureSwitch", unit.getUid())))
			{
				quoteFeature = true;
			}

			//quotes toggle feature switch
			model.addAttribute("quotesFeatureSwitch", quoteFeature);
			return getViewForPage(model);
		}
		catch (final UnknownIdentifierException e)
		{
			LOG.warn("Unable to load cart for quote " + quoteCode, e);
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, SYSTEM_ERROR_PAGE_NOT_FOUND, null);
			return REDIRECT_QUOTE_LIST_URL;
		}
		catch (final ModelNotFoundException e)
		{
			LOG.warn("Attempted to load a quote that does not exist or is not accessible by current user:" + quoteCode, e);
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER,
					QUOTE_CART_INSUFFICIENT_ACCESS_RIGHTS, new Object[]
					{ getCartFacade().getSessionCart().getCode() });
			return REDIRECT_QUOTE_LIST_URL;
		}
		catch (final ParseException e)
		{
			LOG.error(e);
			return REDIRECT_QUOTE_LIST_URL;
		}

	}

	@PostMapping("/quoteListEmail")
	public @ResponseBody String quoteEmail(@RequestParam(value = "quoteNumber", required = false)
	final String quoteNumber, @RequestParam(value = "itemCount", required = false)
	final String itemCount, @RequestParam(value = "quoteId", required = false)
	final String quoteId, @RequestParam(value = "accountManagerEmail", required = false)
	final String accountManagerEmail, @RequestParam(value = "branchManagerEmail", required = false)
	final String branchManagerEmail, @RequestParam(value = "writerEmail", required = false)
	final String writerEmail, @RequestParam(value = "pricerEmail", required = false)
	final String pricerEmail, @RequestParam(value = "writer", required = false)
	final String writer, @RequestParam(value = "accountManager", required = false)
	final String accountManager, @RequestParam(value = "poNumber", required = false)
	final String poNumber, @RequestParam(value = "productList", required = false)
	final String productList, @RequestParam(value = "optionalNotes", required = false)
	final String optionalNotes, @RequestParam(value = "quotesBr", required = false)
	final String quotesBr, @RequestParam(value = "customerNumber", required = false)
	final String customerNumber) throws CMSItemNotFoundException // NOSONAR
	{

		siteoneQuotesFacade.quoteListEmail(quoteNumber, itemCount, productList, accountManagerEmail, branchManagerEmail,
				writerEmail, pricerEmail, quoteId, writer, accountManager, poNumber, optionalNotes, quotesBr, customerNumber);
		if (itemCount != null)
		{
			if (itemCount.equals("full"))
			{
				siteoneQuotesFacade.updateQuote(quoteNumber, productList);
			}
			else
			{
				siteoneQuotesFacade.updateQuoteDetail(quoteNumber, productList);
			}
		}

		return "Success";
	}
	
	@PostMapping("/updateExpiredQuote")
	public @ResponseBody String quoteExpEmail(@RequestParam(value = "quoteNumber", required = true)
	final String quoteNumber, @RequestParam(value = "customerNumber", required = false)
	final String customerNumber, @RequestParam(value = "notes", required = false)
	final String notes, @RequestParam(value = "quoteTotal", required = false)
	final String quoteTotal) throws CMSItemNotFoundException, ParseException // NOSONAR
	{

		String message = siteoneQuotesFacade.expiredQuoteUpdFlow(quoteNumber, notes, customerNumber, quoteTotal);

		return message;
	}

	@PostMapping("/requestQuote")
	public @ResponseBody String requestQuote(@RequestParam(value = "listCode", required = false)
	final String listCode, @RequestParam(value = "jobName", required = false)
	final String jobName, @RequestParam(value = "jobStartDate", required = false)
	final String jobStartDate, @RequestParam(value = "jobDescription", required = false)
	final String jobDescription, @RequestParam(value = "branch", required = false)
	final String branch, @RequestParam(value = "notes", required = false)
	final String notes, @RequestParam(value = "comments", required = false)
	final String comments, @RequestParam(value = "productsList", required = false)
	final String productsList, @RequestParam(value = "selectedProductList", required = false)
	final String selectedProductList) throws CMSItemNotFoundException, ParseException // NOSONAR
	{

		return siteoneQuotesFacade.requestQuote(jobName, jobStartDate, jobDescription, branch, notes, comments,
				productsList, listCode, selectedProductList);
	}
	
	@GetMapping("/shipping-Quotes")
	public @ResponseBody List<QuotesData> shippingQuotes(@RequestParam(value = "customerNumber", required = false)
	final String customerNumber, final Model model, final Integer skipCount, final String toggle) throws CMSItemNotFoundException, ParseException // NOSONAR
	{
		List<QuotesData> quotesData = new ArrayList<QuotesData>();
		String sCount = null;
		if(skipCount != null)
		{
		sCount = skipCount.toString();
		}
		if (StringUtils.isNotBlank(sCount))
		{
		quotesData = getSiteoneQuotesFacade().getQuotes(customerNumber, sCount, toggle);
		}
		else
		{
		quotesData = getSiteoneQuotesFacade().getQuotes(customerNumber, null, toggle);
		}
		LOG.error("shipto resp given" + quotesData);
		model.addAttribute("quoteData", quotesData);
		return quotesData;
	}

	@GetMapping("/shiptoQuote")
	public @ResponseBody ShiptoitemData shiptoQuote(@RequestParam(value = "showOnline", required = false)
	final boolean showOnline, @RequestParam(value = "quotesStatus", required = false)
	final String quotesStatus, final Model model) throws CMSItemNotFoundException, ParseException // NOSONAR
	{
		ShiptoitemData shiptodata = new ShiptoitemData();
      shiptodata = siteoneQuotesFacade.shiptoQuote(showOnline, quotesStatus);
      model.addAttribute("ShiptoitemData", shiptodata);
      return shiptodata;
	}
	
	@GetMapping("/quoteApprovalHistory")
	public @ResponseBody QuoteApprovalItemData approvalHistory(@RequestParam(value = "quoteDetailID", required = true)
	final String quoteDetailID) throws ParseException
	{
		QuoteApprovalItemData historyData = new QuoteApprovalItemData();
		historyData = siteoneQuotesFacade.quoteApprovalHistory(quoteDetailID);
		return historyData;
	}

	@PostMapping("/contactSellerEmail")
	public @ResponseBody String sellerEmail(@RequestParam(value = "quoteNumber", required = false)
	final String quoteNumber, @RequestParam(value = "quoteComments", required = false)
	final String quoteComments, @RequestParam(value = "quoteId", required = false)
	final String quoteId, @RequestParam(value = "accountManagerEmail", required = false)
	final String accountManagerEmail, @RequestParam(value = "branchManagerEmail", required = false)
	final String branchManagerEmail, @RequestParam(value = "writerEmail", required = false)
	final String writerEmail, @RequestParam(value = "pricerEmail", required = false)
	final String pricerEmail, @RequestParam(value = "writer", required = false)
	final String writer, @RequestParam(value = "accountManager", required = false)
	final String accountManager, @RequestParam(value = "quotesBr", required = false)
	final String quotesBr, @RequestParam(value = "customerNumber", required = false)
	final String customerNumber) throws CMSItemNotFoundException // NOSONAR
	{
		eventService.publishEvent(
				siteoneQuotesFacade.initializeEvent(new ContactSellerEvent(), quoteNumber, quoteComments, accountManagerEmail, branchManagerEmail, writerEmail, pricerEmail, quoteId, writer, accountManager, quotesBr, customerNumber));
		return "Success";
	}

	protected void loadCommentsShown(final Model model)
	{
		final int commentsShown = getSiteConfigService().getInt(PAGINATION_NUMBER_OF_COMMENTS, 5);
		model.addAttribute("commentsShown", Integer.valueOf(commentsShown));

	}

	protected void setAllowedActions(final Model model, final String quoteCode)
	{
		final Set<QuoteAction> actionSet = getQuoteFacade().getAllowedActions(quoteCode);

		if (actionSet != null)
		{
			final Map<String, Boolean> actionsMap = actionSet.stream()
					.collect(Collectors.toMap((v) -> v.getCode(), (v) -> Boolean.TRUE));
			model.addAttribute(ALLOWED_ACTIONS, actionsMap);
		}
	}

	/**
	 * @return the accountBreadcrumbBuilder
	 */
	public ResourceBreadcrumbBuilder getAccountBreadcrumbBuilder()
	{
		return accountBreadcrumbBuilder;
	}

	/**
	 * @param accountBreadcrumbBuilder
	 *           the accountBreadcrumbBuilder to set
	 */
	public void setAccountBreadcrumbBuilder(final ResourceBreadcrumbBuilder accountBreadcrumbBuilder)
	{
		this.accountBreadcrumbBuilder = accountBreadcrumbBuilder;
	}

	/**
	 * @param cartFacade
	 *           the cartFacade to set
	 */
	public void setCartFacade(final CartFacade cartFacade)
	{
		this.cartFacade = cartFacade;
	}

	/**
	 * @param siteoneQuotesFacade
	 *           the siteoneQuotesFacade to set
	 */
	public void setQuoteFacade(final QuoteFacade quoteFacade)
	{
		this.quoteFacade = quoteFacade;
	}

	protected QuoteFacade getQuoteFacade()
	{
		return quoteFacade;
	}

	protected CartFacade getCartFacade()
	{
		return cartFacade;
	}

	/**
	 * @return the siteoneQuotesFacade
	 */
	public SiteoneQuotesFacade getSiteoneQuotesFacade()
	{
		return siteoneQuotesFacade;
	}

	/**
	 * @param siteoneQuotesFacade
	 *           the siteoneQuotesFacade to set
	 */
	public void setSiteoneQuotesFacade(final SiteoneQuotesFacade siteoneQuotesFacade)
	{
		this.siteoneQuotesFacade = siteoneQuotesFacade;
	}

}
