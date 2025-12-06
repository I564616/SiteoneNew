/**
 *
 */
package com.siteone.v2.controller;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;
import com.google.gson.Gson;

import org.apache.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.StringUtils;

import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.siteone.commerceservices.dto.company.B2BUnitWsDTO;
import com.siteone.commerceservices.quotes.data.QuotesWsDTO;
import com.siteone.commerceservices.quotes.data.ShiptoitemDTO;
import com.siteone.commerceservices.quotes.data.QuoteApprovalItemDTO;
import com.siteone.commerceservices.quotes.data.SiteOneQuotesDTO;
import com.siteone.commerceservices.quotes.data.QuoteDetailsDTO;
import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.event.ContactSellerEvent;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.facade.order.SiteoneQuotesFacade;
import com.siteone.facades.quote.data.QuotesData;
import com.siteone.facades.quote.data.ShiptoitemData;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.quote.data.QuoteApprovalItemData;
import com.siteone.facades.quote.data.QuoteDetailsData;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;


/**
 * @author AA04994
 *
 */
@Controller
@RequestMapping(value = "/{baseSiteId}/my-account")
@Tag(name = "Siteone Quotes")
public class SiteOneQuotesController extends BaseController
{
	private static final Logger LOG = Logger.getLogger(SiteOneQuotesController.class);

	@Resource(name = "siteoneQuotesFacade")
	private SiteoneQuotesFacade siteoneQuotesFacade;

	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;
	
	@Resource(name = "eventService")
	private EventService eventService;
	
	@Resource(name = "storeFinderFacade")
	private SiteOneStoreFinderFacade storeFinderFacade;
	
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;
	
	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;
	
	public static final String SUCCESS = "Success";
	public static final String FAILURE = "Failure";

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/my-quotes")
	@Operation(operationId = "getQuotesData", summary = "Get the quotes data", description = "Returns all the available quotes data for the request account")
	@ApiBaseSiteIdParam
	@ResponseBody
	public SiteOneQuotesDTO quotes(final Integer skipCount) throws CMSItemNotFoundException, ParseException
	{
		List<QuotesData> quotesData = new ArrayList<QuotesData>();
		SiteOneQuotesDTO siteOneQuotesDTO = new SiteOneQuotesDTO();
		final B2BUnitModel unit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
		boolean quoteFeature = false;

		//quotes toggle feature switch
		try
		{
		if (siteOneFeatureSwitchCacheService.isAllPresentUnderFeatureSwitch("QuotesFeatureSwitch")
				|| (null != unit && null != unit.getUid()
						&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("QuotesFeatureSwitch", unit.getUid())))
		{
			LOG.error("inside Quote Feature");
			quoteFeature = true;
			quotesData = getSiteoneQuotesFacade().getQuotes(null, "0", null);
		}
		
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		mapperFactory.classMap(QuotesData.class, QuotesWsDTO.class);
	    MapperFacade mapper = mapperFactory.getMapperFacade();
	    List<QuotesWsDTO> quotesListDTO = mapper.mapAsList(quotesData, QuotesWsDTO.class);
		siteOneQuotesDTO.setIsQuotesEnabled(quoteFeature);
		siteOneQuotesDTO.setQuoteData(quotesListDTO);

		return siteOneQuotesDTO;
		}
		catch(Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method quotes ");
	    }
		
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/my-quotes/{quoteCode}")
	@Operation(operationId = "getQuoteDetailsData", summary = "Get the quote details data", description = "Returns all the available quote details data for the request quote")
	@ApiBaseSiteIdParam
	@ResponseBody
	public QuoteDetailsDTO quoteDetails(@PathVariable("quoteCode") final String quoteCode) throws CMSItemNotFoundException, ParseException
	{
		try
		{
		final QuoteDetailsData quoteDetailsData = getSiteoneQuotesFacade().getQuoteDetails(quoteCode);
	    QuoteDetailsDTO quoteDetailsDTO = getDataMapper().map(quoteDetailsData, QuoteDetailsDTO.class, "FULL");

		boolean quoteFeature = false;
		final B2BUnitModel unit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
		if (siteOneFeatureSwitchCacheService.isAllPresentUnderFeatureSwitch("QuotesFeatureSwitch")
				|| (null != unit && null != unit.getUid()
						&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("QuotesFeatureSwitch", unit.getUid())))
		{
			quoteFeature = true;
		}
		quoteDetailsDTO.setIsQuotesEnabled(quoteFeature);

		//quotes toggle feature switch
		
		return quoteDetailsDTO;
		}
		catch(Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method quoteDetails ");
	    }

	}
	
	
	@PostMapping("/quoteApproveEmail")
	@Operation(operationId = "postQuoteApproveEmail", summary = "Send the quote approve email", description = "Sends the quote approve request email")
	@ApiBaseSiteIdParam
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
	final String poNumber, @RequestParam(value = "optionalNotes", required = false)
	final String optionalNotes, @RequestParam(value = "productList", required = false)
	final String productList, @RequestParam(value = "quotesBr", required = false)
	final String quotesBr, @RequestParam(value = "customerNumber", required = false)
	final String customerNumber) throws CMSItemNotFoundException // NOSONAR
	{
      try
      {
		siteoneQuotesFacade.quoteListEmail(quoteNumber, itemCount, productList, accountManagerEmail, branchManagerEmail, writerEmail, pricerEmail, quoteId, writer, accountManager, poNumber, optionalNotes, quotesBr, customerNumber);
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
		Gson gson = new Gson();
		return gson.toJson(SUCCESS);
      }
		catch(Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method quoteEmail ");
	    }
	}
	
	@PostMapping("/updateExpiredQuote")
	@Operation(operationId = "updateExpiryDate", summary = "Updates Expiry date of a quote", description = "Updates Expiry date of a quote and sends email")
	@ApiBaseSiteIdParam
	public @ResponseBody String updateQuoteExpiry(@RequestParam(value = "quoteNumber", required = true)
	final String quoteNumber, @RequestParam(value = "customerNumber", required = false)
	final String customerNumber, @RequestParam(value = "notes", required = false)
	final String notes, @RequestParam(value = "quoteTotal", required = false)
	final String quoteTotal) throws CMSItemNotFoundException, ParseException // NOSONAR
	{
      try
      {
    	  String message = siteoneQuotesFacade.expiredQuoteUpdFlow(quoteNumber, notes, customerNumber, quoteTotal);
    	  Gson gson = new Gson();
  		  return gson.toJson(message);
      }
      catch (Exception ex)
      {
    	  LOG.error(ex);
	      throw new RuntimeException("Exception occurred while calling through method updateQuoteExpiry ");
      }
	}
	
	@PostMapping("/requestQuote")
	@Operation(operationId = "postRequestAQuote", summary = "Request a quote pop up", description = "Request a quote")
	@ApiBaseSiteIdParam
	public @ResponseBody String requestQuote(@RequestParam(value = "listCode", required = false)
	final String listCode, @RequestParam(value = "jobName", required = false)
	final String jobName, @RequestParam(value = "jobStartDate", required = false)
	final String jobStartDate, @RequestParam(value = "jobDescription", required = false)
	final String jobDescription, @RequestParam(value = "branch", required = false)
	final String branch, @RequestParam(value = "notes", required = false)
	final String notes, @RequestParam(value = "comments", required = false)
	final String comments, @RequestParam(value = "unitId", required = false)
	final String unitId, @RequestParam(value = "productsList", required = false)
	final String productsList, @RequestParam(value = "selectedProductList", required = false)
	final String selectedProductList) throws CMSItemNotFoundException, ParseException // NOSONAR
	{		
		try
		{
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(branch);
			storeSessionFacade.setSessionStore(pointOfServiceData);		
			final B2BUnitData unit;
			if (!StringUtils.isEmpty(unitId))
			  {
				unit = b2bUnitFacade.getUnitForUid(unitId);
			  } 
			else 
			  {
				unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
			  }
			storeSessionFacade.setSessionShipTo(unit);
		final String result = siteoneQuotesFacade.requestQuote(jobName, jobStartDate, jobDescription, branch, notes, comments,
				productsList, listCode,selectedProductList);
		if(result.equalsIgnoreCase("Success"))
		{
			Gson gson = new Gson();
			return gson.toJson(SUCCESS);
		}
		else
		{
			Gson gson = new Gson();
			return gson.toJson(FAILURE);
		}
		}
		catch(Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method requestQuote ");
	    }
	}
	
	@GetMapping("/shipping-Quotes")
	@Operation(operationId = "shipping-AccountQuotes", summary = "Shipping quotes for acnt", description = "Display shipping quotes for account")
	@ApiBaseSiteIdParam
	public @ResponseBody SiteOneQuotesDTO shippingQuotes(@RequestParam(value = "customerNumber", required = false)
	final String customerNumber, @RequestParam(value = "skipCount", required = false)
	final Integer skipCount, @RequestParam(value = "toggle", required = false) final String toggle) throws CMSItemNotFoundException, ParseException // NOSONAR
	{
		List<QuotesData> quotesData = new ArrayList<QuotesData>();
		SiteOneQuotesDTO siteOneQuotesDTO = new SiteOneQuotesDTO();
		try
		{
		String sCount = null;
		if(skipCount != null)
		{
		sCount = skipCount.toString();
		}
		if (StringUtils.isNotBlank(sCount) || StringUtils.isNotBlank(toggle))
		{
		quotesData = getSiteoneQuotesFacade().getQuotes(customerNumber, sCount, toggle);
		}
		else
		{
		quotesData = getSiteoneQuotesFacade().getQuotes(customerNumber, null, null);
		}
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		mapperFactory.classMap(QuotesData.class, QuotesWsDTO.class);
	    MapperFacade mapper = mapperFactory.getMapperFacade();
	    List<QuotesWsDTO> quotesListDTO = mapper.mapAsList(quotesData, QuotesWsDTO.class);
	    siteOneQuotesDTO.setQuoteData(quotesListDTO);
		LOG.error("shipto resp given via mobile " + quotesData);
		return siteOneQuotesDTO;
		}
		catch(Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method shippingQuotes ");
	    }
	}
	
	@GetMapping("/shiptoQuote")
	@Operation(operationId = "shipToAccountQuotes", summary = "Quotes for ship to acnt", description = "Display quotes for ship to account")
	@ApiBaseSiteIdParam
	public @ResponseBody ShiptoitemDTO shiptoQuote(@RequestParam(value = "showOnline", required = false)
	final boolean showOnline, @RequestParam(value = "quotesStatus", required = false)
	final String quotesStatus, final Model model) throws CMSItemNotFoundException, ParseException // NOSONAR
	{
	  ShiptoitemData shiptodata = new ShiptoitemData();
	  try
	  {
      shiptodata = siteoneQuotesFacade.shiptoQuote(showOnline, quotesStatus);
	  ShiptoitemDTO shiptoitemDTO = getDataMapper().map(shiptodata, ShiptoitemDTO.class, "FULL");
      model.addAttribute("ShiptoitemData", shiptodata);
      return shiptoitemDTO;
	  }
		catch(Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method shiptoQuote ");
	    }
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/quoteApprovalHistory")
	@Operation(operationId = "quoteApprovalHistoryData", summary = "Get the quote approval history data", description = "Returns all approval history of a item under quote")
	@ApiBaseSiteIdParam
	public @ResponseBody QuoteApprovalItemDTO approvalHistory(@RequestParam(value = "quoteDetailID", required = true)
	final String quoteDetailID) throws ParseException
	{
		QuoteApprovalItemData historyData = new QuoteApprovalItemData();
		try
		{
			historyData = siteoneQuotesFacade.quoteApprovalHistory(quoteDetailID);
			QuoteApprovalItemDTO quoteApprovalItemDTO = getDataMapper().map(historyData, QuoteApprovalItemDTO.class, "FULL");
		    return quoteApprovalItemDTO;
		}
		catch(Exception ex)
		{
			LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method approvalHistory ");
		}
	}

	@PostMapping("/contactSellerEmail")
	@Operation(operationId = "postEmailToSeller", summary = "Post the comments to seller", description = "Post the comments to the seller in mail")
	@ApiBaseSiteIdParam
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
		try
		{
		eventService.publishEvent(
				siteoneQuotesFacade.initializeEvent(new ContactSellerEvent(), quoteNumber, quoteComments, accountManagerEmail, branchManagerEmail, writerEmail, pricerEmail, quoteId, writer, accountManager, quotesBr, customerNumber));
		Gson gson = new Gson();
		return gson.toJson(SUCCESS);
		}
		catch(Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method sellerEmail ");
	    }
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
