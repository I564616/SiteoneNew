package com.siteone.facade.order;

import de.hybris.platform.commercefacades.order.data.AbstractOrderData;

import java.text.ParseException;
import java.util.List;

import com.siteone.core.event.ContactSellerEvent;
import com.siteone.core.event.RequestQuoteEvent;
import com.siteone.core.model.QuoteItemDetailsModel;
import com.siteone.facades.quote.data.QuoteApprovalItemData;
import com.siteone.facades.quote.data.QuoteDetailsData;
import com.siteone.facades.quote.data.QuotesData;
import com.siteone.facades.quote.data.ShiptoitemData;
import com.siteone.integration.order.data.SiteOneRequestQuoteRequestData;


/**
 * @author AA04994
 *
 */
public interface SiteoneQuotesFacade
{
	/**
	 * @param orderData
	 */
	void sortComments(AbstractOrderData orderData);

	/**
	 * @param isNewBoomiEnv
	 * @param mongoID
	 * @return
	 * @throws ParseException
	 * @throwsParseException
	 */
	QuoteDetailsData getQuoteDetails(String quoteHeaderID) throws ParseException;

	List<QuotesData> getQuotes(String customerNumber, String skipCount, String toggle) throws ParseException;

	void quoteListEmail(String quoteNumber, String itemCount, String productList, String accountManagerEmail,
			String branchManagerEmail, String writerEmail, String pricerEmail, String quoteId, String writer, String accountManager,
			String poNumber, String optionalNotes, String quotesBr, String customerNumber);

	ContactSellerEvent initializeEvent(ContactSellerEvent event, String quoteNumber, String quoteComments,
			String accountManagerEmail, String branchManagerEmail, String writerEmail, String pricerEmail, String quoteId,
			String writer, String accountManager, String quotesBr, String customerNumber);

	/**
	 * @param quoteNumber
	 * @param jobName
	 * @param jobStartDate
	 * @param branch
	 * @param notes
	 * @param productsList
	 * @param listCode
	 */

	String requestQuote(String jobName, String jobStartDate, String jobDescription, String branch, String notes, String comments,
			String productsList, String listCode, String selectedProductList) throws ParseException;

	/**
	 * @param event
	 * @param quoteNumber
	 * @param quoteComments
	 * @param quoteId
	 * @param writer
	 * @return
	 */

	RequestQuoteEvent initializeEvent(RequestQuoteEvent event, String mongoID, SiteOneRequestQuoteRequestData quotesRequest,
			List<QuoteItemDetailsModel> requestQuoteCustomInfo, List<QuoteItemDetailsModel> requestQuoteProductInfo, String comments)
			throws ParseException;

	/**
	 * @param productList 
	 * @param quoteCode
	 * @return
	 */
	void updateQuote(String quoteHeaderID, String productList);

	/**
	 * @param quoteCode
	 * @param productList
	 */
	void updateQuoteDetail(String quoteHeaderID, String productList);

	/**
	 * @param customerNumber
	 * @param showOnline
	 * @return
	 */
	ShiptoitemData shiptoQuote(boolean showOnline, String quotesStatus);

	/**
	 * @param quoteDetailID
	 * @return
	 * @throws ParseException 
	 */
	QuoteApprovalItemData quoteApprovalHistory(String quoteDetailID) throws ParseException;

	/**
	 * @param quoteNumber
	 * @param notes 
	 * @param customerNumber 
	 * @param quoteTotal 
	 * @return 
	 * @throws ParseException 
	 */
	String expiredQuoteUpdFlow(String quoteNumber, String notes, String customerNumber, String quoteTotal) throws ParseException;

	/**
	 * @return
	 */
}
